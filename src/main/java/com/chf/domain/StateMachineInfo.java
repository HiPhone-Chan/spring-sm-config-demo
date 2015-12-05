package com.chf.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "StateMachineInfo")
public class StateMachineInfo implements Serializable {

	private static final long serialVersionUID = -4064988453883646994L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long id;

	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "StateMachineInfo [id=" + id + ", content=" + content + "]";
	}

}
