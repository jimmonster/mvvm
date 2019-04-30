package com.jim.common.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具
 * create by Colin 407888611@qq.com
 */
public class DateUtils {
	
	/**
	 * 解析时间
	 * @param strDate
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String strDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			Log.e("DateUtils",e.toString());
			return null;
		}
	}
	
	/**
	 * 返回两个日期相差的天数
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getDiffDay(Date start, Date end) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(start);
		c2.setTime(end);
		// 设置时间到0点0时0分0秒
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		// 计算两个日期相差的天数
		long t1 = c1.getTimeInMillis();
		long t2 = c2.getTimeInMillis();
		return (t2 - t1) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 返回两个日期相差的分钟数
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getDiffMinute(Date start, Date end) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(start);
		c2.setTime(end);
		// 设置时间到0秒
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		// 计算两个日期相差的天数
		long t1 = c1.getTimeInMillis();
		long t2 = c2.getTimeInMillis();
		return (t2 - t1) / 1000;
	}
	
}
