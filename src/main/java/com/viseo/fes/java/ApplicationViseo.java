package com.viseo.fes.java;

import com.viseo.fes.java.amqp.RabbitManager;

public class ApplicationViseo {
	public static void main(String[] args) throws Exception {
		RabbitManager manager = new RabbitManager("amqp://localhost/%2F");
		manager.load(RabbitHandler.class);
		manager.start();
	}
}
