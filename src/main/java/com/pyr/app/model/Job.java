package com.pyr.app.model;

import java.io.Serializable;

public class Job implements Serializable {

	private String name;
	private String owner;
	private String code;
	private String engine;
	private String schedule;
	private int priority;
	
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Job [name=" + name + ", owner=" + owner + ", code=" + code + ", engine=" + engine + ", schedule="
				+ schedule + ", priority=" + priority + "]";
	}
	
	
}
