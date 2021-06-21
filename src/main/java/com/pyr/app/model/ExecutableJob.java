package com.pyr.app.model;

public class ExecutableJob {
	
	private String jobId;
	private String jobEngine;
	private String jobAction;
	private String jobSchedule;
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobEngine() {
		return jobEngine;
	}
	public void setJobEngine(String jobEngine) {
		this.jobEngine = jobEngine;
	}
	public String getJobAction() {
		return jobAction;
	}
	public void setJobAction(String jobAction) {
		this.jobAction = jobAction;
	}
	public String getJobSchedule() {
		return jobSchedule;
	}
	public void setJobSchedule(String jobSchedule) {
		this.jobSchedule = jobSchedule;
	}
	
	@Override
	public String toString() {
		return "ExecutableJob [jobId=" + jobId + ", jobEngine=" + jobEngine + ", jobAction=" + jobAction
				+ ", jobSchedule=" + jobSchedule + "]";
	}
	
	
	

}
