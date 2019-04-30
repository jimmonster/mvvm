package com.jim.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPUtils {
	private static final String FILENAME = "SPUtils";

	public static void putString(Context context, String key, String value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putString(key, value);
		edit.apply();
	}
	
	public static String getString(Context context, String key, String defValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getString(key, defValue);
	}
	
	public static void putBoolean(Context context, String key, boolean value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putBoolean(key, value);
		edit.apply();
	}
	
	public static int getInt(Context context, String key, int defValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getInt(key, defValue);
	}

	public static void putInt(Context context, String key, int value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putInt(key, value);
		edit.apply();
	}

	public static boolean getBoolean(Context context, String key, boolean defValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getBoolean(key, defValue);
	}
	public static void putFloat(Context context, String key, float value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putFloat(key, value);
		edit.apply();
	}
	
	public static float getFloat(Context context, String key, float defValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getFloat(key, defValue);
	}


	public static void putLong(Context context, String key, long value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		Editor edit = sharedPreferences.edit();
		edit.putLong(key, value);
		edit.apply();
	}

	public static long getLong(Context context, String key, long defValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		return sharedPreferences.getLong(key, defValue);
	}
}
