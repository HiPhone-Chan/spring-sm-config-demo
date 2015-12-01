package com.chf.domain.parser;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class StateMechineConfigInfoNamespaceHandler
		extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("smconfig",
				new StateMechineConfigInfoBeanDefinitionParser());
	}
}
