package com.viseo.fes.java;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MyAwesomeServiceTest {
	@Test
	void plouf() {
		// GIVEN
		MyAwesomeService svc = new MyAwesomeService();
		// WHEN
		int bar = svc.bar();
		// THEN
		assertThat(bar).isEqualTo(42);
	}
}