package com.jim.common.utils;

import android.content.Context;
import android.text.format.Formatter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 格式工具
 * create by Colin 407888611@qq.com
 */
public class FormatUtils {
	
	/**
	 * 格式化日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
		return sdf.format(date);
	}
	
	/**
	 * 转换为保留指定小数位的小数
	 * @param num
	 * @param scale
	 * @return
	 */
	public static String formatDouble(double num, int scale) {
		return new BigDecimal(num)
			.setScale(scale, BigDecimal.ROUND_HALF_UP)
			.toPlainString();// 返回不带指数的字符串表示形式
	}
	
	/**
	 * 转换为最多保留指定小数位的小数
	 * @param num
	 * @param maxScale
	 * @return
	 */
	public static String formatDoubleForMaxScale(double num, int maxScale) {
		String s = new BigDecimal(num)
			.setScale(maxScale, BigDecimal.ROUND_HALF_UP)
			.stripTrailingZeros()// 去掉小数点后的零，但是去不掉0值小数点后面的零
			.toPlainString();// 返回不带指数的字符串表示形式
		if (s.indexOf('.') > 0) {
			s = s.replaceAll("0+?$", "");
			s = s.replaceAll("[.]$", "");
		}
		return s;
	}

	/**
	 * 转换为金额的字符串形式
	 * 单位元，保留小数点后2位
	 * @param money
	 * @return
	 */
	public static String formatMoneyKeepFen(double money) {
		return new BigDecimal(money)
				.setScale(2, BigDecimal.ROUND_HALF_UP)
				.toPlainString();// 返回不带指数的字符串表示形式
	}
	
	/**
	 * 转换为金额的字符串形式
	 * 单位元，去掉小数点
	 * @param money
	 * @return
	 */
	public static String formatMoneyMaybeKeeyFen(double money) {
		String s = new BigDecimal(money)
			.setScale(2, BigDecimal.ROUND_HALF_UP)
			.stripTrailingZeros()// 去掉小数点后的零，但是去不掉0值小数点后面的零
			.toPlainString();// 返回不带指数的字符串表示形式
		if (s.indexOf('.') > 0) {
			s = s.replaceAll("0+?$", "");
			s = s.replaceAll("[.]$", "");
		}
		return s;
	}

	/**
	 * 格式化文件大小
	 * @param context
	 * @param fileSize
     * @return
     */
	public static String formatFileSize(Context context, long fileSize) {
		return Formatter.formatFileSize(context, fileSize);
	}

}
