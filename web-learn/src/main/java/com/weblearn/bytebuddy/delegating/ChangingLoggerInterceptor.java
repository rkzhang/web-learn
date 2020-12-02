package com.weblearn.bytebuddy.delegating;

import java.util.List;

import net.bytebuddy.implementation.bind.annotation.Super;

public class ChangingLoggerInterceptor {

	public static List<String> log(String info, @Super MemoryDatabase zuper) {
		System.out.println("Calling database");
		try {
			return zuper.load(info + " (logged access)");
		} finally {
			System.out.println("Returned from database");
		}
	}
}
