package com.pyr.app.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DbUtils {
	
	private Logger logger = LogManager.getLogger(DbUtils.class);

	
	public String getJobId(String info) {
		try {
			return DigestUtils.md5Hex(info);
		} catch (Exception e) {
			logger.warn("Unable to create hash : " + e.getMessage());
		} 
		
		return info;
	}
}
