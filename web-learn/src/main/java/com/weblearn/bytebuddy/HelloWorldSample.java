package com.weblearn.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class HelloWorldSample {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String className = "com.weblearn.bytebuddy.Sample";
		Class<?> dynamicType = new ByteBuddy() 
				  .subclass(Object.class)
				  .name(className)
				  .method(ElementMatchers.named("toString"))
				  .intercept(FixedValue.value("--- Hello World!"))
				  .make()
				  .load(Object.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
				  .getLoaded();
		System.out.println(dynamicType.getName());
		
		Object obj = Class.forName(className).newInstance();
		System.out.println(obj.toString());
		

		//new ByteBuddy().subclass(BuddySample.class);
		//new ByteBuddy().redefine(BuddySample.class);
		//new ByteBuddy().rebase(BuddySample.class); //相当于创建代理类
		
		
		
	}
}
