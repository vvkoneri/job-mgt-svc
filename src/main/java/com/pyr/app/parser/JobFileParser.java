package com.pyr.app.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pyr.app.model.Job;

@Component
public class JobFileParser {
	
	
	public List<Job> jobsFileParser(String filePath) throws Exception {
		
		List<Job> jobsArray = new ArrayList<Job>();
		
		File file = new File(filePath);  
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();  
		Document doc = db.parse(file);  
		doc.getDocumentElement().normalize();  
		
		NodeList jobsList = doc.getElementsByTagName("jobs");
		Node ownerNode = jobsList.item(0).getAttributes().getNamedItem("owner");
		
		NodeList jobs = doc.getElementsByTagName("job");
		
		for(int job_idx=0; job_idx < jobs.getLength(); job_idx++) {
			Job job =  new Job();
			Node nJob = jobs.item(job_idx);
			job.setOwner(ownerNode == null ? "UNKNOWN" : ownerNode.getNodeValue());
			job.setName(nJob.getAttributes().getNamedItem("name").getNodeValue());
			job.setEngine(nJob.getAttributes().getNamedItem("engine").getNodeValue());
			job.setCode(nJob.getTextContent());
			job.setPriority(Integer.parseInt(nJob.getAttributes().getNamedItem("priority").getNodeValue()));
			job.setSchedule(nJob.getAttributes().getNamedItem("schedule").getNodeValue());
			jobsArray.add(job);
		}
		
		
		
		return jobsArray;
		
	}

}
