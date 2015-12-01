package com.chf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.statemachine.StateMachine;

import com.chf.enumeration.Events;
import com.chf.enumeration.States;

@SpringBootApplication
@ImportResource("spring.xml")
public class Application implements CommandLineRunner {

	@Autowired
	private StateMachine<States, Events> stateMachine;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication
				.run(Application.class, args);
		System.out.println(context.getBean("tranconfig"));
	}

	public void run(String... args) throws Exception {
		stateMachine.sendEvent(Events.E1);
		stateMachine.sendEvent(Events.E2);
	}

}
