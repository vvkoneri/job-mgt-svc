package com.pyr.app;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pyr.app.service.JobProcessor;

@SpringBootApplication(scanBasePackages = { "com.pyr" })
public class JobMgtSvcApp implements CommandLineRunner {
	
	private JobProcessor jobProcessor;
	
	@Autowired
	public JobMgtSvcApp(JobProcessor jobProcessor) {
		this.jobProcessor = jobProcessor;
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(JobMgtSvcApp.class, args);
	}

	public void run(String... args) throws Exception {
		
		while(true) {
			System.out.println("Enter the command");
			Scanner scanner = new Scanner(System.in);
			String command = scanner.nextLine();
			String[] commandArgs = command.split("\\s+");
			
	
			if(args[0].equalsIgnoreCase("exit")) {
				break;
			}
			String cmdResult = jobProcessor.runJob(commandArgs);
			System.out.println(cmdResult);
			
		}
		
	}
	
	
}
