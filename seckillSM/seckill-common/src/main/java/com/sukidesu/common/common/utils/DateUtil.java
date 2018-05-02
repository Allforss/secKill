package com.sukidesu.common.common.utils;

import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author fangqing
 *
 */
public class DateUtil {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	   * 得到现在时间
	   * 
	   * @return 字符串 yyyyMMdd HHmmss
	   */
	public static String getStringToday() {
	   Date currentTime = new Date();
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	   String dateString = formatter.format(currentTime);
	   return dateString;
	}
	
	/**
	 * 获取前天时间
	 * 
	 * @return
	 */
	public static String getBeforeNDayStr(int nDay) {
		Date nowTime = new Date();// 当天时间
		Date dBefore = new Date();// 前一天时间
		String datetime = null;// 获取文件所用到的时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(nowTime);// 把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, nDay); // 设置为前一天
		dBefore = calendar.getTime(); // 得到前一天的时间
		datetime = dateFormat.format(dBefore);

		return datetime;
	}

	public static String dateToString(Date date, String pattern){
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}

	/**
	 * 将"2015-08-31 21:08:06"型字符串转化为Date
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static Date stringToDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = (Date) formatter.parse(str);
		} catch (ParseException e) {
			logger.error("字符串转化为Date异常:" , e);
		}
		return date;
	}

}
