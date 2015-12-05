package com.chf;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import com.chf.domain.StateMachineInfo;
import com.chf.persisit.MyStateMachinePersist;
import com.chf.repository.StateMachineInfoRepository;

@SpringBootApplication
@ImportResource("spring.xml")
public class Application implements CommandLineRunner {

	@Autowired
	private StateMachine<String, String> stateMachine;

	@Autowired
	private StateMachineInfoRepository stateMachineInfoRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	public void run(String... args) throws Exception {
		stateMachine.sendEvent("E1");
		stateMachine.sendEvent("E2");

		DefaultStateMachineContext<String, String> context = new DefaultStateMachineContext<String, String>(
				null, "S1", null, null, stateMachine.getExtendedState());

		MyStateMachinePersist<String, String> persist = new MyStateMachinePersist<>();
		persist.setStateMachineInfoRepository(stateMachineInfoRepository);
		StateMachineInfo stat = new StateMachineInfo();
		persist.write(context, stat);

		StateMachineContext<String, String> contextR = persist.read(stat);
		System.out.println(contextR);
	}

}
