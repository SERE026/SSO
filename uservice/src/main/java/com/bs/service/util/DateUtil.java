package com.bs.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	private static Logger LOG = LoggerFactory.getLogger(DateUtil.class);
	
	public static Date parseDate(Long time) {
		return new Date(time);
	}
	
	public static Date parseDate(String time) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(time);
		} catch (ParseException e) {
			//date = new Date();
			LOG.error("日期解析错误：{}",e.getMessage());
		}
		
		return date;
	}
	
	public static String fmtDate(Date date) {
		String date_new = null;
		try {
			date_new = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(date);
		} catch (Exception e) {
			LOG.error("日期解析错误：{}",e.getMessage());
		}
		
		return date_new;
	}
	
	public static void main(String[] args) {
		//2015-10-10 16:00:19.382
		Long t = 1444464019382L;
		System.out.println(parseDate(t));
	}
}
