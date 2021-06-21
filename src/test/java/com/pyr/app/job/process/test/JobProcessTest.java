package com.pyr.app.job.process.test;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.pyr.app.model.Job;
import com.pyr.app.service.JobProcessor;

@SpringBootTest(classes={com.pyr.app.job.process.test.JobProcessTest.class})
@ContextConfiguration(classes = {com.pyr.app.service.JobProcessor.class,com.pyr.app.parser.JobFileParser.class,
		com.pyr.app.service.DataServicer.class,com.pyr.app.util.DbUtils.class,com.pyr.app.db.H2DbClient.class})
class JobProcessTest {
	
	
	private JobProcessor jobPrc;
	
	@Autowired
	public JobProcessTest(JobProcessor jobPrc) {
		this.jobPrc = jobPrc;
	}
	
	@Test
	void testAddJobCommand() throws Exception {
		
		CommandLineParser parser = new DefaultParser();
		String command = "addjob --name job1 --owner venkatesh --engine python --code \"src/main/resources/python/hello.py\" --s immediate";
		String[] commandArr = command.split("\\s+");
		
		Options options = jobPrc.buildOptions("addjob");
		CommandLine commandLine = parser.parse(options, commandArr);
		Job job = jobPrc.buildJobObjectsFromCommandArgs(commandLine);
		
		assert job.getName().equalsIgnoreCase("job1");
		
	}
	
	@Test
	void testAddJobFileCommand() throws Exception {
		
		CommandLineParser parser = new DefaultParser();
		String command = "addjob --file src/main/resources/examples/jobs.xml";
		String[] commandArr = command.split("\\s+");
		
		Options options = jobPrc.buildOptions("addjob");
		CommandLine commandLine = parser.parse(options, commandArr);
		List<Job> jobs = jobPrc.buildJobObjectsFromFile(commandLine);
		
		assert jobs.size()  == 3;
		
	}

}
