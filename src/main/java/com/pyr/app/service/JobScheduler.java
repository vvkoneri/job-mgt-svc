package com.pyr.app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.pyr.app.constants.JobStatus;
import com.pyr.app.executors.Executor;
import com.pyr.app.model.ExecutableJob;

@Configuration
@EnableAsync
@EnableScheduling
public class JobScheduler {
	
	private Logger logger = LogManager.getLogger(JobScheduler.class);;
	private DataServicer dataSvc;
	private Executor executor;

	@Autowired
	public JobScheduler(DataServicer dataSvc, Executor executor) throws Exception {
		this.dataSvc = dataSvc;
		this.executor = executor;
		
	}
	
	@Scheduled(fixedRateString="${schedule.time}")
	public void executeJob() {
		ExecutableJob job = dataSvc.getJob();
		if(job != null) {
			
			if(job.getJobSchedule().equalsIgnoreCase("immediate")) {
				
				dataSvc.changeJobStatus(job.getJobId(), JobStatus.RUNNING);
				try {
					this.executor.execute(job);
					dataSvc.changeJobStatus(job.getJobId(), JobStatus.SUCCESS);
				} catch (Exception e) {
					logger.warn("job failed due to : " + e.getMessage());
					dataSvc.changeJobStatus(job.getJobId(), JobStatus.FAILED);
				} 
			} else {
				dataSvc.addJobScheduleLaterQueue(job);
			}
		} else {
			logger.info("No jobs to run in the queue");
		}
		
	}
	
	@Scheduled(fixedRateString="${schedule.time}")
	public void executeLaterJob() {
		ExecutableJob job = dataSvc.getJobFromLaterQueue();
		if(job != null) {
			dataSvc.changeJobStatus(job.getJobId(), JobStatus.RUNNING);
			try {
				this.executor.execute(job);
				dataSvc.changeJobStatus(job.getJobId(), JobStatus.SUCCESS);
			} catch (Exception e) {
				logger.warn("job failed due to : " + e.getMessage());
				dataSvc.changeJobStatus(job.getJobId(), JobStatus.FAILED);
			} 
			
			dataSvc.removeJobFromLaterQueue(job.getJobId());
		}
	}
}
