package com.weblearn.bytebuddy.delegating;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class InterceptorUtils {

	@SuppressWarnings("unchecked")
	public static <T> T createBeanByTarget(Class interceptor, Class target) {
		T t = null;
		try {
			t = (T) new ByteBuddy()
					  .subclass(target)
					  .method(ElementMatchers.isDeclaredBy(target)).intercept(MethodDelegation.to(interceptor))
					  .make()
					  .load(target.getClassLoader())
					  .getLoaded()
					  .newInstance();
		} catch (InstantiationException e) {			
			e.printStackTrace();
		} catch (IllegalAccessException e) {		
			e.printStackTrace();
		}
		return t;
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T createBeanByTarget(String methodName, Class interceptor, Class target) {
		T t = null;
		try {
			t = (T) new ByteBuddy()
					  .subclass(target)
					  .method(ElementMatchers.named(methodName)).intercept(MethodDelegation.to(interceptor))
					  .make()
					  .load(target.getClassLoader())
					  .getLoaded()
					  .newInstance();
		} catch (InstantiationException e) {			
			e.printStackTrace();
		} catch (IllegalAccessException e) {		
			e.printStackTrace();
		}
		return t;
		
	}
	
}
