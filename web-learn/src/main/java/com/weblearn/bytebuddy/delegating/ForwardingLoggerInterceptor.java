package com.weblearn.bytebuddy.delegating;

import java.util.List;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Pipe;
import net.bytebuddy.matcher.ElementMatchers;

public class ForwardingLoggerInterceptor {

	private final MemoryDatabase memoryDatabase;

	public ForwardingLoggerInterceptor(MemoryDatabase memoryDatabase) {
		this.memoryDatabase = memoryDatabase;
	}

	/*
	public List<String> log(@Pipe Forwarder<List<String>, MemoryDatabase> pipe, @Argument(0) Object param) {
		System.out.println("Calling database - param : " + param);
		try {
			memoryDatabase.setName("test");
			return pipe.to(memoryDatabase);
		} finally {
			System.out.println("Returned from database");
		}
	}
	*/

	public List<String> log(@Pipe Forwarder<List<String>, MemoryDatabase> pipe, @AllArguments Object[] param) {
		System.out.println("Calling database - param : " + param[0]);
		try {
			memoryDatabase.setName("test");
			return pipe.to(memoryDatabase);
		} finally {
			System.out.println("Returned from database");
		}
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {

		MemoryDatabase loggingDatabase = new ByteBuddy().subclass(MemoryDatabase.class)
				.method(ElementMatchers.isDeclaredBy(MemoryDatabase.class))		//在所有方法
				.intercept(MethodDelegation.withDefaultConfiguration().withBinders(Pipe.Binder.install(Forwarder.class))
						.to(new ForwardingLoggerInterceptor(new MemoryDatabase())))
				.make()
				.load(ForwardingLoggerInterceptor.class.getClassLoader())
				.getLoaded()
				.newInstance();

		loggingDatabase.load("info");

	}
}
