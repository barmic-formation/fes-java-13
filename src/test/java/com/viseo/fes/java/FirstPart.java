package com.viseo.fes.java;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

class FirstPart {
	private static Logger log = LoggerFactory.getLogger(FirstPart.class);

	public static class YetAnotherClass {
		public void yetAnotherMethod(YetAnotherClass this) {
		}
	}

	@Test
	void foo() {
		new YetAnotherClass().yetAnotherMethod(); // WTF?!!!
		List.of(12, 13, 14).stream()
				.map(i -> Integer.toString(i))
				.forEach(log::info);
	}
}
