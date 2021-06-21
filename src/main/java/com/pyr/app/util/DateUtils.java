package com.pyr.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {

	static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public  static Date getDateFromString(String dString) throws ParseException {
		Date date = (Date) formatter.parse(dString);
		return date;
	}
	
}
