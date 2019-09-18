package com.viseo.fes.java;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.flogger.FluentLogger;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
public class MyController {
	private static final FluentLogger logger = FluentLogger.forEnclosingClass();
	private final MyAwesomeService awesomeService;

	@ResponseBody
	@GetMapping("/")
	public int foo() {
		logger.atInfo().log("Log message with: %s", "plouf");
		return awesomeService.bar();
	}
}
