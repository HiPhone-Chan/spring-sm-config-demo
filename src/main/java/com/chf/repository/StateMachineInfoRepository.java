package com.chf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chf.domain.StateMachineInfo;

public interface StateMachineInfoRepository
		extends JpaRepository<StateMachineInfo, Long> {

}
