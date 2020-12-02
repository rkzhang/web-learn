package com.weblearn.bytebuddy.delegating;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

public class LoggerInterceptor {

	public static List<String> log(@SuperCall Callable<List<String>> zuper, @AllArguments Object[] param, @Origin Method method) throws Exception {
		System.out.println("Calling database - param : " + param[0] + " - method : " + method.getName());
		try {
			return zuper.call();
		} finally {
			System.out.println("Returned from database");
		}
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		/*
		MemoryDatabase loggingDatabase = new ByteBuddy()
				  .subclass(MemoryDatabase.class)
				  .method(ElementMatchers.named("load")).intercept(MethodDelegation.to(LoggerInterceptor.class))
				  .make()
				  .load(LoggerInterceptor.class.getClassLoader())
				  .getLoaded()
				  .newInstance();
		*/
		MemoryDatabase loggingDatabase = InterceptorUtils.createBeanByTarget(LoggerInterceptor.class, MemoryDatabase.class);
		loggingDatabase.load("aaa");
	}
}
