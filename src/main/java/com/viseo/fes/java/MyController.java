package com.viseo.fes.java;

import com.google.common.flogger.FluentLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	private static final FluentLogger logger = FluentLogger.forEnclosingClass();
	private final MyAwesomeService awesomeService;

	@Autowired
	public MyController(MyAwesomeService awesomeService) {
		this.awesomeService = awesomeService;
	}

	@ResponseBody
	@GetMapping("/")
	public int foo() {
		logger.atInfo().log("Log message with: %s", "plouf");
		return awesomeService.bar();
	}
}
