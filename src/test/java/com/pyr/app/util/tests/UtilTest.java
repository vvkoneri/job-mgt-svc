package com.pyr.app.util.tests;
import java.util.Date;
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
import com.pyr.app.util.DateUtils;

@SpringBootTest(classes={com.pyr.app.util.tests.UtilTest.class})
class UtilTest {
	
	
	@Test
	void dateConversion() throws Exception {
		
		Date testDate = DateUtils.getDateFromString("2021-06-18 00:00");
		System.out.println(testDate.getTime());
		assert testDate.getTime() == 1623954600000L;
		
	}
	

}
