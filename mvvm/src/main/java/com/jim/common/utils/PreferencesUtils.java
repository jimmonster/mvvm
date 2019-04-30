package com.jim.common.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 共享数据工具
 * 为避免版本变更数据类型后，引起数据类型错误，统一存储字符串
 * create by Colin 407888611@qq.com
 */
public class PreferencesUtils {

	/**
	 * 设置数据
	 * @param editor
	 * @param key
	 * @param value
     */
	public static void putValue(SharedPreferences.Editor editor, String key, Object value) {
		editor.putString(key, value == null ? null : value.toString());
	}

	/**
	 * 获取String类型数据
	 * @param sp
	 * @param key
	 * @param defValue
     * @return
     */
	public static String getString(SharedPreferences sp, String key, String defValue) {
		return sp.getString(key, defValue);
	}

	/**
	 * 获取int类型数据
	 * @param sp
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(SharedPreferences sp, String key, int defValue) {
		String value = sp.getString(key, null);
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * 获取long类型数据
	 * @param sp
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static long getLong(SharedPreferences sp, String key, long defValue) {
		String value = sp.getString(key, null);
		try {
			return Long.parseLong(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * 获取float类型数据
	 * @param sp
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static float getFloat(SharedPreferences sp, String key, float defValue) {
		String value = sp.getString(key, null);
		try {
			return Float.parseFloat(value);
		} catch (Exception e) {
			return defValue;
		}
	}

	/**
	 * 获取boolean类型数据
	 * @param sp
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(SharedPreferences sp, String key, boolean defValue) {
		String value = sp.getString(key, null);
		if (TextUtils.isEmpty(value)) {
			return defValue;
		}
		try {
			return Boolean.parseBoolean(value);
		} catch (Exception e) {
			return defValue;
		}
	}
	
}
