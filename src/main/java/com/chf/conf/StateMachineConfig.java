package com.chf.conf;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.zookeeper.ZookeeperStateMachinePersist;

//@Configuration
//@EnableStateMachine
public class StateMachineConfig
		extends StateMachineConfigurerAdapter<String, String> {

	@Override
	public void configure(
			StateMachineConfigurationConfigurer<String, String> config)
					throws Exception {
		config.withConfiguration().autoStartup(true).listener(listener());
	}

	@Override
	public void configure(StateMachineStateConfigurer<String, String> states)
			throws Exception {
		states.withStates().initial("SI")
				.states(new HashSet<String>(Arrays.asList("SI", "S1", "S2")));
	}

	@Override
	public void configure(
			StateMachineTransitionConfigurer<String, String> transitions)
					throws Exception {
		transitions.withExternal().source("SI").target("S1").event("E1").and()
				.withExternal().source("S1").target("S2").event("E2");
	}

	@Bean
	public StateMachineListener<String, String> listener() {
		return new StateMachineListenerAdapter<String, String>() {
			@Override
			public void stateChanged(State<String, String> from,
					State<String, String> to) {
				System.out.println("State change to " + to.getId());
			}
		};
	}
}
