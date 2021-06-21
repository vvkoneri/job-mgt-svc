package com.pyr.app.service;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pyr.app.model.Job;
import com.pyr.app.parser.JobFileParser;

@Service
public class JobProcessor {

	private static CommandLineParser parser = new DefaultParser();
	private static HelpFormatter formatter = new HelpFormatter();
	private DataServicer dataSvc;
	private JobFileParser jobFParser;

	@Autowired
	public JobProcessor(DataServicer dataSvc, JobFileParser jobFParser) {
		this.dataSvc = dataSvc;
		this.jobFParser = jobFParser;
	}

	public void removeJob(CommandLine commandLine, Options options) {
		if (commandLine.hasOption("id") || commandLine.hasOption("i")) {
			dataSvc.removeJobFromDb(commandLine.getOptionValue("id") != null ? commandLine.getOptionValue("id")
					: commandLine.getOptionValue("i"));
		} else {
			formatter.printHelp("removejob", options);
			return;
		}

	}
	
	public List<Job> buildJobObjectsFromFile(CommandLine commandLine) throws Exception {
		
		String filePath = commandLine.getOptionValue("file") != null ? commandLine.getOptionValue("file")
				: commandLine.getOptionValue("f");
		List<Job> jobsArray = jobFParser.jobsFileParser(filePath);
		
		return jobsArray;
	}
	
	public Job buildJobObjectsFromCommandArgs(CommandLine commandLine) throws Exception {
		
		Job job = new Job();

		job.setName(commandLine.getOptionValue("name") != null ? commandLine.getOptionValue("name")
				: commandLine.getOptionValue("n"));
		job.setCode(commandLine.getOptionValue("code") != null ? commandLine.getOptionValue("code")
				: commandLine.getOptionValue("c"));
		job.setSchedule(commandLine.getOptionValue("schedule") != null ? commandLine.getOptionValue("schedule")
				: commandLine.getOptionValue("s"));
		job.setEngine(commandLine.getOptionValue("engine") != null ? commandLine.getOptionValue("engine")
				: commandLine.getOptionValue("e"));

		if (commandLine.hasOption("owner") || commandLine.hasOption("o")) {
			job.setOwner(commandLine.getOptionValue("owner") != null ? commandLine.getOptionValue("owner")
					: commandLine.getOptionValue("o"));
		} else {
			job.setOwner("Unknown");
		}

		if (commandLine.hasOption("priority") || commandLine.hasOption("p")) {
			job.setPriority(Integer.parseInt(
					commandLine.getOptionValue("priority") != null ? commandLine.getOptionValue("priority")
							: commandLine.getOptionValue("p")));
		} else {
			job.setPriority(0);
		}
		
		return job;
	}

	public void addJob(CommandLine commandLine, Options options) throws Exception {

		if (commandLine.hasOption("file") || commandLine.hasOption("f")) {
			List<Job> jobsArray = buildJobObjectsFromFile(commandLine);

			for (Job job : jobsArray) {
				dataSvc.addJobToDb(job);
			}

		} else {
			if ((commandLine.hasOption("name") || commandLine.hasOption("n"))
					&& (commandLine.hasOption("code") || commandLine.hasOption("c"))
					&& (commandLine.hasOption("schedule") || commandLine.hasOption("s"))
					&& (commandLine.hasOption("engine") || commandLine.hasOption("e"))) {

				Job job = buildJobObjectsFromCommandArgs(commandLine);

				dataSvc.addJobToDb(job);

			} else {
				formatter.printHelp("addjob", options);
				return;
			}
		}

	}

	public String runJob(String... args) {
		try {
			if (Arrays.asList(args).contains("addjob")) {
				Options options = buildOptions("addjob");
				CommandLine commandLine = parser.parse(options, args);
				addJob(commandLine, options);
			}

			if (Arrays.asList(args).contains("removejob")) {
				Options options = buildOptions("removejob");
				CommandLine commandLine = parser.parse(options, args);
				removeJob(commandLine, options);
			}
		} catch (Exception e) {
			return "Failed to create job : " + e.getMessage();
		}
		
		return "Done!";

	}

	public static Options buildOptions(String command) {

		Options options = new Options();
		if (command.equalsIgnoreCase("addjob")) {
			options.addOption("n", "name", true, "name of the job");
			options.addOption("o", "owner", true, "owner of the job");
			options.addOption("e", "engine", true, "scripting engine");
			options.addOption("c", "code", true, "code to be executed");
			options.addOption("f", "file", true, "jobs file");
			options.addOption("p", "priority", true, "job priority of execution");
			options.addOption("s", "schedule", true, "schedule");
			return options;
		}

		if (command.equalsIgnoreCase("removejob")) {
			options.addOption("i", "id", true, "job id");
			return options;
		}

		return null;
	}

}
