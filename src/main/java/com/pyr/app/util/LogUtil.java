package com.pyr.app.util;

import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class LogUtil {
	
	@Autowired
	private Environment env;
	
	public Level getLogLevel() {
		if(env.getProperty("logger.level").equalsIgnoreCase("info")) {
			return Level.INFO;
		}
		
		if(env.getProperty("logger.level").equalsIgnoreCase("warn")) {
			return Level.WARNING;
		}
		
		if(env.getProperty("logger.level").equalsIgnoreCase("debug")) {
			return Level.CONFIG;
		}
		
		if(env.getProperty("logger.level").equalsIgnoreCase("error")) {
			return Level.SEVERE;
		}
		
		return Level.OFF;
		
	}

}
