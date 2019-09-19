package com.viseo.fes.java;

import com.viseo.fes.java.amqp.RabbitManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication(scanBasePackages = "com.viseo.fes.java")
public class ApplicationViseo {
	public static void main(String[] args) throws URISyntaxException, IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, IllegalAccessException {
//		SpringApplication.run(ApplicationViseo.class, args);
		RabbitManager manager = new RabbitManager("");
		RabbitHandler handler = manager.load(RabbitHandler.class);
		System.out.println(handler.plouf(new Object()));
	}
}
