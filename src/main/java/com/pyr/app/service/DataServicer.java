package com.pyr.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyr.app.constants.JobStatus;
import com.pyr.app.constants.TableNames;
import com.pyr.app.db.H2DbClient;
import com.pyr.app.model.ExecutableJob;
import com.pyr.app.model.Job;
import com.pyr.app.util.DateUtils;
import com.pyr.app.util.DbUtils;

@Service
public class DataServicer {

	
	private DbUtils dbUtils;
	private H2DbClient h2Client;
	private Logger logger = LogManager.getLogger(DataServicer.class);
	
	@Autowired
	public DataServicer(DbUtils dbUtils, H2DbClient h2Client) throws Exception{
		this.dbUtils = dbUtils;
		this.h2Client = h2Client;
	}

	public void removeJobFromDb(String jobId) {
		Connection conn = null;
		try {
			conn = h2Client.getConnection();

			String sql = "DELETE FROM " + TableNames.JOBSQUEUE + " WHERE job_id = ?";
			PreparedStatement deleteStmt = conn.prepareStatement(sql);
			deleteStmt.setString(1, jobId);

			deleteStmt.execute();

			logger.info("Job removed from Queue with job id : " + jobId);

		} catch (Exception e) {
			logger.error("Unable to remove job from execution queue : " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				logger.warn("Can't close connection to database : " + e.getMessage());
			}

		}

	}

	public void removeJobFromLaterQueue(String jobId) {
		Connection conn = null;
		try {
			conn = h2Client.getConnection();

			String sql = "DELETE FROM " + TableNames.SCHEDULE_LATER + " WHERE job_id = ?";
			PreparedStatement deleteStmt = conn.prepareStatement(sql);
			deleteStmt.setString(1, jobId);

			deleteStmt.execute();

			logger.info("Job removed from later Queue with job id : " + jobId);

		} catch (Exception e) {
			logger.error("Unable to remove job from later queue : " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				logger.warn("Can't close connection to database : " + e.getMessage());
			}

		}

	}

	public void addJobScheduleLaterQueue(ExecutableJob job) {
		Connection conn = null;
		try {
			conn = h2Client.getConnection();

			String sql = "INSERT INTO " + TableNames.SCHEDULE_LATER + TableNames.SCHEDULE_LATER_COLS
					+ " values(?,?,?,?)";

			Date date = DateUtils.getDateFromString(job.getJobSchedule());

			PreparedStatement insertStmt = conn.prepareStatement(sql);
			insertStmt.setString(1, job.getJobId());
			insertStmt.setString(2, job.getJobEngine());
			insertStmt.setString(3, job.getJobAction());
			insertStmt.setTimestamp(4, new Timestamp(date.getTime()));

			insertStmt.execute();

			logger.info("Job added to the Later Queue with job id : " + job.getJobId());

		} catch (Exception e) {
			logger.error("Unable to add job to the later queue : " + e.getMessage());
			changeJobStatus(job.getJobId(), JobStatus.FAILED);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				logger.warn("Can't close connection to database : " + e.getMessage());
			}

		}

	}

	public ExecutableJob getJobFromLaterQueue() {
		Connection conn = null;
		ExecutableJob job = null;
		try {
			conn = h2Client.getConnection();

			String sql = "SELECT job_id, job_engine, job_action FROM " + TableNames.SCHEDULE_LATER
					+ "  WHERE job_schedule <= ? ORDER BY  job_schedule asc limit 1";

			PreparedStatement selectJobStmt = conn.prepareStatement(sql);
			selectJobStmt.setTimestamp(1, new Timestamp(new Date().getTime()));
			ResultSet rs = selectJobStmt.executeQuery();

			while (rs.next()) {
				job = new ExecutableJob();
				job.setJobId(rs.getString(1));
				job.setJobEngine(rs.getString(2));
				job.setJobAction(rs.getString(3));
			}

		} catch (Exception e) {
			logger.error("Unable to get job from queue : " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				logger.warn("Can't close connection to database : " + e.getMessage());
			}

		}

		return job;

	}

	public void addJobToDb(Job job) {
		Connection conn = null;
		try {
			conn = h2Client.getConnection();

			String sql = "INSERT INTO " + TableNames.JOBSQUEUE + TableNames.JOBSQUEUE_COLS
					+ " values(?,?,?,?,?,?,?,?,?,?,?)";

			String jobId = dbUtils.getJobId(job.toString());
			PreparedStatement insertStmt = conn.prepareStatement(sql);
			insertStmt.setString(1, jobId);
			insertStmt.setString(2, job.getName());
			insertStmt.setString(3, JobStatus.QUEUED);
			insertStmt.setTimestamp(4, new Timestamp(new Date().getTime()));
			insertStmt.setTimestamp(5, new Timestamp(new Date().getTime()));
			insertStmt.setString(6, job.getOwner());
			insertStmt.setString(7, "none");
			insertStmt.setString(8, job.getEngine());
			insertStmt.setString(9, job.getCode());
			insertStmt.setString(10, job.getSchedule());
			insertStmt.setInt(11, job.getPriority());

			insertStmt.execute();

			logger.info("Job added to the Queue with job id : " + jobId);

		} catch (Exception e) {
			logger.error("Unable to add job to the execution queue : " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}

		}

	}

	public ExecutableJob getJob() {
		Connection conn = null;
		ExecutableJob job = null;
		try {
			conn = h2Client.getConnection();

			String sql = "SELECT job_id, job_engine, job_action, job_schedule FROM " + TableNames.JOBSQUEUE
					+ " WHERE job_status = 'QUEUED' AND is_processed = false ORDER BY job_priority desc,job_create_time asc limit 1";

			PreparedStatement selectJobStmt = conn.prepareStatement(sql);
			ResultSet rs = selectJobStmt.executeQuery();

			while (rs.next()) {
				job = new ExecutableJob();
				job.setJobId(rs.getString(1));
				job.setJobEngine(rs.getString(2));
				job.setJobAction(rs.getString(3));
				job.setJobSchedule(rs.getString(4));
				toProcessed(job.getJobId());
			}

		} catch (Exception e) {
			logger.error("Unable to get job from queue : " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				logger.warn("Can't close connection to database : " + e.getMessage());
			}

		}

		return job;
	}

	public void toProcessed(String jobId) {
		Connection conn = null;
		ExecutableJob job = null;
		try {
			conn = h2Client.getConnection();
			String sql = "UPDATE " + TableNames.JOBSQUEUE + " SET is_processed = true  WHERE job_id = ?";
			PreparedStatement updateJobStmt = conn.prepareStatement(sql);
			updateJobStmt.setString(1, jobId);

			updateJobStmt.execute();

			logger.info("Job updated in Queue with job id : " + jobId);
		} catch (Exception e) {
			logger.error("Unable to update job in queue : " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				logger.warn("Can't close connection to database : " + e.getMessage());
			}

		}
	}

	public void changeJobStatus(String jobId, String status) {
		Connection conn = null;
		ExecutableJob job = null;
		try {
			conn = h2Client.getConnection();
			String sql = "UPDATE " + TableNames.JOBSQUEUE
					+ " SET job_status = ?,job_last_updated_time=?  WHERE job_id = ?";
			PreparedStatement updateJobStmt = conn.prepareStatement(sql);
			updateJobStmt.setString(1, status);
			updateJobStmt.setTimestamp(2, new Timestamp(new Date().getTime()));
			updateJobStmt.setString(3, jobId);

			updateJobStmt.execute();

			logger.info("Job updated in Queue with job id : " + jobId + " to " + status);
		} catch (Exception e) {
			logger.error("Unable to update job in queue : " + e.getMessage());
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				logger.warn("Can't close connection to database : " + e.getMessage());
			}

		}
	}

}
