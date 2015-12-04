package com.chf.domain.parser;

import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import com.chf.domain.StateMechineConfigInfo;
import com.chf.domain.TransferConfigInfo;

public class SMFactoryBean implements FactoryBean<StateMechineConfigInfo> {

	private StateMechineConfigInfo stateMechineConfigInfo;

	private List<TransferConfigInfo> transferConfigInfos;

	public void setStateMechineConfigInfo(
			StateMechineConfigInfo stateMechineConfigInfo) {
		this.stateMechineConfigInfo = stateMechineConfigInfo;
	}

	public void setTransferConfigInfos(
			List<TransferConfigInfo> transferConfigInfos) {
		this.transferConfigInfos = transferConfigInfos;
	}

	@Override
	public StateMechineConfigInfo getObject() throws Exception {
		stateMechineConfigInfo.setTransferConfigInfos(transferConfigInfos);
		return stateMechineConfigInfo;
	}

	@Override
	public Class<?> getObjectType() {
		return StateMechineConfigInfo.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
