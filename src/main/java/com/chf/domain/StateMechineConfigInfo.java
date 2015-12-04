package com.chf.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StateMechineConfigInfo {

	private String initState;

	private List<TransferConfigInfo> transferConfigInfos;

	public Set<String> getAllStates() {
		HashSet<String> set = new HashSet<>();
		set.add(initState);

		for (TransferConfigInfo transferConfigInfo : transferConfigInfos) {
			set.add(transferConfigInfo.getFrom());
			set.add(transferConfigInfo.getTo());
		}

		return set;
	}

	public String getInitState() {
		return initState;
	}

	public void setInitState(String initState) {
		this.initState = initState;
	}

	public List<TransferConfigInfo> getTransferConfigInfos() {
		return transferConfigInfos;
	}

	public void setTransferConfigInfos(
			List<TransferConfigInfo> transferConfigInfos) {
		this.transferConfigInfos = transferConfigInfos;
	}

	@Override
	public String toString() {
		return "StateMechineConfigInfo [initState=" + initState
				+ ", transferConfigInfos=" + transferConfigInfos + "]";
	}

}
