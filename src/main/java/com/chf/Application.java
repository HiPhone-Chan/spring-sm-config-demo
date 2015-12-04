package com.chf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
@ImportResource("spring.xml")
public class Application implements CommandLineRunner {

	@Autowired
	private StateMachine<String, String> stateMachine;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {
		stateMachine.sendEvent("E1");
		stateMachine.sendEvent("E2");
	}

}
