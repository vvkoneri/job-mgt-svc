package com.pyr.app.constants;

public class TableNames {
	
	public static final String JOBSQUEUE = "JOBSQUEUE";
	public static final String JOBSQUEUE_COLS = "(job_id,job_name,job_status,job_create_time,job_last_updated_time,job_owner,job_file_name,job_engine,job_action,job_schedule,job_priority)";
	
	public static final String SCHEDULE_LATER = "SCHEDULELATERQUEUE";
	public static final String SCHEDULE_LATER_COLS = "(job_id,job_engine,job_action,job_schedule)";

}
