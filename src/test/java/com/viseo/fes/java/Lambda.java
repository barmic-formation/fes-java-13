package com.viseo.fes.java;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Present lambda, invokedynameik, typing
 */
class Lambda {
	private static Logger log = LoggerFactory.getLogger(Lambda.class);
	@Test
	void foo() {
		List.of(12, 13, 14).stream()
				.map(i -> Integer.toString(i))
				.forEach(log::info);
	}
}
