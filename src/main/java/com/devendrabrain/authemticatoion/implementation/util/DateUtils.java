package com.devendrabrain.authemticatoion.implementation.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static Date addSeconds(Date date, Integer seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
	
	public String toDate(Long dateInMilliseconds) {
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(dateInMilliseconds);

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateInMilliseconds);
		return formatter.format(calendar.getTime()); 
	}
}
