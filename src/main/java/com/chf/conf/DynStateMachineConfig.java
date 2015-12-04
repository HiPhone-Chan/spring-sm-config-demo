package com.chf.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configurers.ExternalTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import com.chf.domain.StateMechineConfigInfo;
import com.chf.domain.TransferConfigInfo;

@Configuration
@EnableStateMachine
public class DynStateMachineConfig
		extends StateMachineConfigurerAdapter<String, String> {

	@Autowired
	private StateMechineConfigInfo stateMechineConfigInfo;

	@Override
	public void configure(
			StateMachineConfigurationConfigurer<String, String> config)
					throws Exception {
		config.withConfiguration().autoStartup(true).listener(listener());
	}

	@Override
	public void configure(StateMachineStateConfigurer<String, String> states)
			throws Exception {
		System.out.println(stateMechineConfigInfo);
		states.withStates().initial(stateMechineConfigInfo.getInitState())
				.states(stateMechineConfigInfo.getAllStates());
	}

	@Override
	public void configure(
			StateMachineTransitionConfigurer<String, String> transitions)
					throws Exception {

		ExternalTransitionConfigurer<String, String> externalTransitionConfig = null;
		// externalTransitionConfig.source("SI").target("S1").event("E1").and()
		// .withExternal().source("S1").target("S2").event("E2");

		for (TransferConfigInfo transferConfigInfo : stateMechineConfigInfo
				.getTransferConfigInfos()) {

			if (externalTransitionConfig == null) {
				externalTransitionConfig = transitions.withExternal();
			} else {
				externalTransitionConfig = externalTransitionConfig.and()
						.withExternal();
			}
			externalTransitionConfig.source(transferConfigInfo.getFrom())
					.target(transferConfigInfo.getTo())
					.event(transferConfigInfo.getEvent());
		}
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
