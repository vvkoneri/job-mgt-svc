DROP TABLE IF EXISTS jobsqueue;

CREATE TABLE IF NOT EXISTS jobsqueue (
  job_id VARCHAR(500)  PRIMARY KEY,
  job_name VARCHAR(250) NOT NULL,
  job_status VARCHAR(50) NOT NULL,
  job_create_time DATETIME DEFAULT NULL,
  job_last_updated_time DATETIME DEFAULT NULL,
  job_owner VARCHAR(50) NOT NULL,
  job_file_name VARCHAR(100) NOT NULL,
  job_engine VARCHAR(100) NOT NULL,
  job_action VARCHAR(500),
  job_schedule VARCHAR(100),
  job_priority INT,
  is_processed boolean DEFAULT false
);

DROP TABLE IF EXISTS schedulelaterqueue;

CREATE TABLE IF NOT EXISTS schedulelaterqueue (
  job_id VARCHAR(500)  PRIMARY KEY,
  job_engine VARCHAR(100) NOT NULL,
  job_action VARCHAR(500) NOT NULL,
  job_schedule DATETIME DEFAULT NULL
);

