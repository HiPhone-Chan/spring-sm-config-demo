package com.chf.domain.parser;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.chf.domain.StateMechineConfigInfo;
import com.chf.domain.TransferConfigInfo;

public class StateMechineConfigInfoBeanDefinitionParser
		extends AbstractBeanDefinitionParser {

	@Override
	public AbstractBeanDefinition parseInternal(Element element,
			ParserContext context) {
		BeanDefinitionBuilder factory = BeanDefinitionBuilder
				.rootBeanDefinition(SMFactoryBean.class);

		BeanDefinitionBuilder selfbuilder = BeanDefinitionBuilder
				.rootBeanDefinition(StateMechineConfigInfo.class);

		String initState = element.getAttribute("initState");
		selfbuilder.addPropertyValue("initState", initState);

		List<Element> elements = DomUtils.getChildElementsByTagName(element,
				"transfer");

		int size = elements == null ? 0 : elements.size();

		ManagedList<BeanDefinition> transferList = new ManagedList<BeanDefinition>(
				size);

		if (size > 0) {
			for (Element e : elements) {
				BeanDefinitionBuilder builder = BeanDefinitionBuilder
						.rootBeanDefinition(TransferConfigInfo.class);
				builder.addPropertyValue("from", e.getAttribute("from"));
				builder.addPropertyValue("to", e.getAttribute("to"));
				builder.addPropertyValue("event", e.getAttribute("event"));
				transferList.add(builder.getBeanDefinition());
			}
		}

		factory.addPropertyValue("stateMechineConfigInfo", selfbuilder.getBeanDefinition());
		factory.addPropertyValue("transferConfigInfos", transferList);
		return factory.getBeanDefinition();
	}

}
