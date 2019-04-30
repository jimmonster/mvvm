package com.jim.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具
 * create by Colin 407888611@qq.com
 */
public class ToastUtils {

	private static long LONG_DURATION = 3500;
	private static long SHORT_DURATION = 2000;
	public static long DURATION_DELAY = SHORT_DURATION;
	public static int DURATION = Toast.LENGTH_SHORT;

	/**
	 * 创建消息
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Toast makeText(Context context, int resId) {
		Toast toast = Toast.makeText(context, resId, DURATION);
		setupToast(toast);
		return toast;
	}

	/**
	 * 创建消息
	 * @param context
	 * @param text
	 * @return
	 */
	public static Toast makeText(Context context, CharSequence text) {
		Toast toast = Toast.makeText(context, text, DURATION);
		setupToast(toast);
		return toast;
	}

	/**
	 * 设置消息
	 * @param toast
	 * @return
	 */
	private static Toast setupToast(Toast toast) {
		//toast.setGravity(Gravity.CENTER, 0, 0);
		return toast;
	}
	
}
