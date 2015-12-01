package com.chf.domain.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.chf.domain.TransferConfigInfo;

public class StateMechineConfigInfoBeanDefinitionParser
		implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext context) {
		RootBeanDefinition def = new RootBeanDefinition();
		def.setBeanClass(TransferConfigInfo.class);

		String id = element.getAttribute("id");
		String from = element.getAttribute("from");
		String to = element.getAttribute("to");

		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(def, id), context.getRegistry());
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(def, from), context.getRegistry());
		BeanDefinitionReaderUtils.registerBeanDefinition(
				new BeanDefinitionHolder(def, to), context.getRegistry());

		def.getPropertyValues().addPropertyValue("from", from);
	    def.getPropertyValues().addPropertyValue("to", to);
		return def;
	}

}
