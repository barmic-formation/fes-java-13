package com.viseo.fes.java;

import com.google.common.flogger.FluentLogger;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Present lambda, invokedynameik, typing
 */
class Lambda {
	private static final FluentLogger logger = FluentLogger.forEnclosingClass();
	@Test
	void foo() {
		List.of(12, 13, 14).stream()
				.map(i -> Integer.toString(i))
				.forEach(logger.atInfo()::log);
	}
}
