package com.weblearn.stream;

import java.util.stream.Stream;

public class ParallelSample {

	public static void main(String[] args) {
		Stream.iterate(0, n -> n + 2).limit(100).forEach(System.out::println);
		
		//斐波纳契数列
		Stream.iterate(new long[]{0, 1}, t -> new long[]{t[1], t[0] + t[1]})
		.limit(50).map(t -> t[0]).forEach(System.out::println); 
		
		long total = Stream.iterate(1L, i -> i + 1).limit(100)
				.parallel().reduce(0L, Long::sum);
		System.out.println(total);
	}
}
