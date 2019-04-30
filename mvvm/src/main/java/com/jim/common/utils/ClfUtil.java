package com.jim.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.*;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 * 
 * @auther chenlf3
 * @date 2015年8月6日-上午9:47:33
 * Copyright (c) 2015点聚信息技术有限公司-版权所有
 */

public class ClfUtil {
	
	private static String TAG = "ClfUtil";
	private static Map<String,String> emailMap = new HashMap<String,String>();
	private boolean flag = false;
	
	static {
		emailMap.put("qq.com", "http://mail.qq.com");
		emailMap.put("gmail.com", "http://mail.google.com");
		emailMap.put("sina.com", "http://mail.sina.com.cn");
		emailMap.put("163.com", "http://mail.163.com");
		emailMap.put("126.com", "http://mail.126.com");
		emailMap.put("yeah.net", "http://www.yeah.net/");
		emailMap.put("sohu.com", "http://mail.sohu.com/");
		emailMap.put("tom.com", "http://mail.tom.com/");
		emailMap.put("sogou.com", "http://mail.sogou.com/");
		emailMap.put("139.com", "http://mail.10086.cn/");
		emailMap.put("hotmail.com", "http://www.hotmail.com");
		emailMap.put("live.com", "http://login.live.com/");
		emailMap.put("live.cn", "http://login.live.cn/");
		emailMap.put("live.com.cn", "http://login.live.com.cn");
		emailMap.put("189.com", "http://webmail16.189.cn/webmail/");
		emailMap.put("yahoo.com.cn", "http://mail.cn.yahoo.com/");
		emailMap.put("yahoo.cn", "http://mail.cn.yahoo.com/");
		emailMap.put("eyou.com", "http://www.eyou.com/");
		emailMap.put("21cn.com", "http://mail.21cn.com/");
		emailMap.put("188.com", "http://www.188.com/");
		emailMap.put("foxmail.coom", "http://www.foxmail.com");
	}
	
	/**
	 * 获取文本类型的图片，该图片可放进文本框
	 * @auther chenlf3
	 * @date 2015年12月1日 下午2:18:03
	 * @return
	 */
	public static SpannableString getSpannableString(Context context, Bitmap bitmap) {
		BitmapDrawable drawable= new BitmapDrawable(context.getResources(), bitmap);
		SpannableString spannable = new SpannableString("c");
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		//要让图片替代指定的文字就要用ImageSpan
		ImageSpan span = new ImageSpan(drawable,ImageSpan.ALIGN_BOTTOM);
		spannable.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		return spannable;
	}
	
	/**
	 * 安装apk
	 * @auther chenlf3
	 * @date 2015年12月1日 下午2:11:22
	 * @param context
	 * @param filename
	 */
	public static void installApk(Context context, String filename) {
        File file = new File(filename);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.fromFile(file), type);
        context.startActivity(intent);
    }
	
	/**
	 * 静默安装apk(需要手机有root权限)
	 * @auther chenlf3
	 * @date 2015年12月1日 下午2:11:22
	 * @param context
	 * @param filename
	 */
	public static boolean clientInstallApk(Context context, String filename) {
		PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.println("chmod 777 "+filename);
            PrintWriter.println("export LD_LIBRARY_PATH=/vendor/lib:/system/lib");
            PrintWriter.println("pm install -r "+filename);
//          PrintWriter.println("exit");
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            if(value == 0) {//成功
            	return true;
            } else if(value == 1) {//失败
            	return false;
            } else {//位置情况
            	return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(process!=null){
                process.destroy();
            }
        }
        return false;
    }
	
	/**
	 * 判断是否有root权限,如果没有则获取root权限
	 * @auther chenlf3
	 * @date 2016年5月26日 下午9:12:32
	 * @return
	 */
	public static boolean hasRootPerssion(){
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            if(value == 0) {//成功
            	return true;
            } else if(value == 1) {//失败
            	return false;
            } else {//位置情况
            	return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(process!=null){
                process.destroy();
            }
        }
        return false;
    }
	
	/**
	 * 获取app版本号
	 * @auther chenlf3
	 * @date 2015年9月21日 上午11:02:05
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public static int getVersionCode(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取app版本名称
	 * @auther chenlf3
	 * @date 2016年2月1日 上午10:11:13
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 判断网络是否可用
	 * @auther chenlf3
	 * @date 2015年9月18日 下午4:53:45
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm != null) {
			//如果仅仅判断网络连接可以使用cm.getActiveNetworkInfo().isAvailable();
			NetworkInfo[] info = cm.getAllNetworkInfo();
			for(int i=0;i<info.length;i++) {
				if(info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 判断wifi是否打开
	 * @auther chenlf3
	 * @date 2015年9月18日 下午5:02:36
	 * @param context
	 * @return
	 */
	public static boolean isWifiEnabled(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
			return true;
		}
		return false;
/*		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return ((cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || tm.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);*/
	}
	
	/**
	 * 判断是否3G网络
	 * @auther chenlf3
	 * @date 2015年9月18日 下午5:12:11
	 * @param context
	 * @return
	 */
	public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;   
        }
        return false;   
    }
	
	/**
	 * 判断当前网络是否是wifi
	 * @auther chenlf3
	 * @date 2015年9月18日 下午5:13:47
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;   
        }   
        return false;   
    }
	
	/**
	 * 判断GPS是否打开
	 * @auther chenlf3
	 * @date 2015年9月18日 下午4:59:31
	 * @param context
	 * @return
	 */
	public static boolean isGpsEnabled(Context context) {
		LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		List<String> accessibleProviders = lm.getProviders(true);
		return (accessibleProviders!=null && accessibleProviders.size()>0);
	}
	
	/**
	 * 根据服务器端错误状态码，返回错误信息
	 * @auther chenlf3
	 * @date 2015年9月11日 上午10:54:26
	 * @param code
	 * @return
	 */
	public static String getClientErrorInfoByCode(int code) {
		String errorMsg = null;
		switch(code) {
		case 400:
			errorMsg = "请求错误！";
			break;
		case 401:
			errorMsg = "未授权！";
			break;
		case 402:
			break;
		case 403:
			errorMsg = "禁止访问！";
			break;
		case 404:
			errorMsg = "请求资源错误，请检查URL！";
			break;
		case 405:
			errorMsg = "对于请求所标识的资源，不允许使用请求行中所指定的方法！";
			break;
		case 406:
			errorMsg = "根据此请求中所发送的“接受”标题，此请求所标识的资源只能生成内容特征为“不可接受”的响应实体！";
			break;
		case 407:
			errorMsg = "需要代理身份验证！";
			break;
		case 412:
			errorMsg = "在服务器上测试前提条件时，部分请求标题字段中所给定的前提条件估计为FALSE！";
			break;
		case 414:
			errorMsg = "Request-URI太长！";
			break;
		case 500:
			errorMsg = "服务器的内部错误！";
			break;
		case 501:
			errorMsg = "服务器不支持实现此请求所需的功能！";
			break;
		case 502:
			errorMsg = "网关出错！";
			break;
		default:
			break;
		}
		return errorMsg;
	}
	
	/**
	 * 根据邮箱获取邮箱登录地址
	 * @auther chenlf3
	 * @date 2015年9月7日 下午12:33:56
	 * @param email
	 * @return
	 */
	public static String getEmailLoginAddress(String email) {
		if(email == null || "".equals(email.trim())) return null;
		Set<String> sets = emailMap.keySet();
		for(String key:sets) {
			if(email.endsWith(key)) {
				return emailMap.get(key);
			}
		}
		return null;
	}
	
	/**
	 * 获取code
	 * @auther chenlf3
	 * @date 2015年9月7日 上午10:06:28
	 * @param context
	 * @return
	 * @throws NameNotFoundException
	 */
	public static String getCode(Context context) throws NameNotFoundException {
		StringBuilder res = new StringBuilder("android*");//客户端类型
		res.append(android.os.Build.MODEL).append("*");//终端型号
		res.append(android.os.Build.VERSION.RELEASE).append("*");//操作系统版本
		res.append(context.getPackageManager().getPackageInfo(context.getPackageName(), 0)).append("*");//客户端版本号
		res.append(Secure.getString(context.getContentResolver(), Secure.ANDROID_ID));//硬件序列号
		return res.toString();
	}
	
	/**
	 * 检查参数是否合法
	 * @auther chenlf3
	 * @date 2015年9月6日 下午12:23:17
	 * @param params
	 * @return
	 */
	public static boolean isLegalParams(String...params) {
		if(params == null || params.length==0) {
			return false;
		}
		for(String param:params) {
			if(param == null || "".equals(param.trim())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 将map拼接为一个字符串,用于获取get请求的参数
	 * @auther chenlf3
	 * @date 2015年9月1日 下午6:09:47
	 * @param map
	 * @return
	 */
	public static String getParams(Map<String,String> map) {
		if(map == null) {
			return null;
		}
		Set<String> keys = map.keySet();
		if(keys.size() == 0) {
			return null;
		}
		StringBuilder temp = new StringBuilder("?");
		for(String key:keys) {
			try {
				temp.append(key).append("=").append(map.get(key)==null?"":URLEncoder.encode(map.get(key),"UTF-8")).append("&");
				//temp.append(key).append("=").append(map.get(key)).append("&");
			} catch (UnsupportedEncodingException e) {
				 //TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String res = temp.toString();
		if(res.endsWith("&")) {
			return res.substring(0, res.length()-1);
		} else {
			return res;
		}
	}
	
	/**
	 * 获取存储文件中的值
	 * @auther chenlf3
	 * @date 2015年8月31日 下午3:27:37
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getSPString(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		return sp.getString(key, null);
	}
	
	/**
	 * 获取存储文件中的值
	 * @auther chenlf3
	 * @date 2015年8月31日 下午3:27:37
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getSPInt(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		return sp.getInt(key, 0);
	}
	
	/**
	 * 获取存储文件中的值
	 * @auther chenlf3
	 * @date 2015年8月31日 下午3:27:50
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getSPString(Context context, String key, String defaultValue) {
		SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		return sp.getString(key, defaultValue);
	}
	
	/**
	 * 获取存储文件中的值
	 * @auther chenlf3
	 * @date 2015年8月31日 下午3:27:50
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getSPInt(Context context, String key, int defaultValue) {
		SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}
	
	/**
	 * 添加存储文件中的值
	 * @auther chenlf3
	 * @date 2015年8月31日 下午3:29:57
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void addSP(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * 添加存储文件中的值
	 * @auther chenlf3
	 * @date 2015年8月31日 下午3:29:57
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void addSP(Context context, String key, int value) {
		SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	/**
	 * 添加存储文件中的值
	 * @auther chenlf3
	 * @date 2015年8月31日 下午3:33:58
	 * @param context
	 * @param map
	 */
	public static void addSPSString(Context context, Map<String,String> map) {
		SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		Set<String> keys = map.keySet();
		for(String key:keys) {
			editor.putString(key, map.get(key));
		}
		editor.commit();
	}
	
	/**
	 * 添加存储文件中的值
	 * @auther chenlf3
	 * @date 2015年8月31日 下午3:33:58
	 * @param context
	 * @param map
	 */
	public static void addSPSInt(Context context, Map<String,Integer> map) {
		SharedPreferences sp = context.getSharedPreferences("config", context.MODE_PRIVATE);
		Editor editor = sp.edit();
		Set<String> keys = map.keySet();
		for(String key:keys) {
			editor.putInt(key, map.get(key));
		}
		editor.commit();
	}

	/**
	 * 把输入流内容转化为字符串
	 * @auther chenlf3
	 * @date 2015年8月6日 上午9:51:43
	 * @param is
	 * @return
	 */
	public static String readInputStream(InputStream is) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while((len = is.read(buffer)) != -1) {
				os.write(buffer,0,len);
			}
			is.close();
			os.close();
			byte[] result = os.toByteArray();
			return new String(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String readInputStream(InputStream is, String encode) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while((len = is.read(buffer)) != -1) {
				os.write(buffer,0,len);
			}
			is.close();
			os.close();
			byte[] result = os.toByteArray();
			return new String(result,encode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将文件转化为字节数组
	 * @auther chenlf3
	 * @date 2015年11月10日 下午12:08:34
	 * @param file
	 * @return
	 */
	public static byte[] getBytes(File file) {
		if(file == null || !file.exists()) {
			return null;
		}
		try {
			InputStream is = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int i = -1;
			while((i=is.read(buffer))!=-1) {
				out.write(buffer,0,i);
			}
			is.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 将输入流存储为本地文件,读取pdf流，存储为pdf文件
	 * @auther chenlf3
	 * @date 2015年8月6日 上午9:51:58
	 * @param in
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static boolean saveTo(InputStream in, String path) throws IOException {
		File file = new File(path);
		OutputStream os = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int i = -1;
		while((i=in.read(buffer)) != -1) {
			os.write(buffer,0,i);
		}
		os.flush();
		in.close();
		os.close();
		return true;
	}
	
	/**
	 * 将内容追加到指定文件
	 * @auther chenlf3
	 * @date 2016年5月7日 下午5:28:18
	 * @param content
	 * @return
	 */
	public static boolean saveAppendTo(String content, String dir, String fileName) {
		/** 目录若不存在，创建目录 */
		File dirFile = new File(dir);
		if(!dirFile.exists() || !dirFile.isDirectory()) {
			dirFile.mkdirs();
		}
		/** 文件若不存在创建文件 */
		File file = new File(dir+File.separator+fileName);
		if(!file.exists() || !file.isFile()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		/** 将字符串写入文件 */
		try {
			FileOutputStream fos = new FileOutputStream(file,true);
			fos.write(content.getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 追加
	 * @auther chenlf3
	 * @date 2016年6月2日 上午10:29:10
	 * @param data
	 * @param dir
	 * @param fileName
	 * @return
	 */
	public static boolean saveAppendTo(byte[] data, String dir, String fileName) {
		/** 目录若不存在，创建目录 */
		File dirFile = new File(dir);
		if(!dirFile.exists() || !dirFile.isDirectory()) {
			dirFile.mkdirs();
		}
		/** 文件若不存在创建文件 */
		File file = new File(dir+File.separator+fileName);
		if(!file.exists() || !file.isFile()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		try {
			FileOutputStream out = new FileOutputStream(file,true);
			out.write(data);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 将byte数组保存到文件
	 * @auther chenlf3
	 * @date 2015年8月21日 下午5:39:25
	 * @param data
	 * @param path
	 * @return
	 */
	public static boolean saveTo(byte[] data, String path) {
		try {
			File file = new File(path);
			FileOutputStream out = new FileOutputStream(file);
			out.write(data);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 拷贝文件
	 * @auther chenlf3
	 * @date 2016年4月1日 下午12:14:21
	 */
	public static boolean copyFile(String filePath1, String filePath2) {
		File file1 = new File(filePath1);
		if(file1.exists() && file1.isFile()) {
			try {
				InputStream in = new FileInputStream(file1);;
				boolean res = saveTo(in, filePath2);
				return res;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 检查手机是否存在照相机
	 * @auther chenlf3
	 * @date 2015年8月6日 上午9:52:13
	 * @param context
	 * @return
	 */
	public static boolean checkCameraHardware(Context context) {
		if(context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			//this device has a camera
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * amr音频文件拼接(list里面为暂停录音所产生的几段录音文件的名字，中间几段文件的减去前面的6个字节头文件)
	 * @auther chenlf3
	 * @date 2015年8月11日 下午5:29:45
	 * @param filePaths
	 * @param finalFilePath
	 * @return
	 * @throws IOException
	 */
	public static boolean mergeMusicFile(List<String> filePaths, String finalFilePath) throws IOException {
		if(filePaths == null || filePaths.size() <= 0) {
			System.out.println("filePaths为空！");
			return false;
		}
		/** 检验文件是否全都存在 */
		for(int i=0;i<filePaths.size();i++) {
			File file = new File(filePaths.get(i));
			if(!file.exists()) {
				System.out.println(filePaths.get(i)+"文件不存在！");
				return false;
			}
		}
		OutputStream os = new FileOutputStream(finalFilePath);
		for(int i=0;i<filePaths.size();i++) {
			InputStream is = new FileInputStream(filePaths.get(i));
			byte[] buffer = new byte[is.available()];
			int length = buffer.length;
			if(i==0) {
				while((is.read(buffer)) != -1) {
					os.write(buffer,0,length);
				}
			} else {
				while((is.read(buffer)) != -1) {
					os.write(buffer,6,length-6);
				}
			}
			os.flush();
			is.close();
		}
		os.close();
		return true;
	}
	
	/**
	 * 获取文件夹下的子文件列表
	 * @auther chenlf3
	 * @date 2015年8月21日 下午3:08:36
	 * @param absolutePath
	 * @return
	 */
	public static List<Map<String,String>> getFileDir(List<Map<String,String>> list, String absolutePath) {
		if(list == null) {
			list = new ArrayList<Map<String,String>>();
		}
		File file = new File(absolutePath);
		if(!file.exists() || file.isFile()) {
			return list;
		}
		File[] files = file.listFiles();
		if(files == null || files.length<=0) {
			return list;
		}
		for(File file1:files) {
			if(file1.getName().endsWith(".pdf") || file1.isDirectory()) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("fileName", file1.getName());
				map.put("absolutePath", file1.getAbsolutePath());
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 获取文件夹下文件列表
	 * @auther chenlf3
	 * @date 2017年2月13日 上午10:44:57
	 * @param absolutePath
	 * @return
	 */
	public static List<Map<String,String>> getFileList(String absolutePath) {
		List list = new ArrayList<Map<String,String>>();
		File file = new File(absolutePath);
		if(!file.exists() || file.isFile()) return list;
		File[] files = file.listFiles();
		if(files == null || files.length<=0) return list;
		for(File file1:files) {
			if(file1.isFile() && (file1.getName().endsWith(".pdf") || file1.getName().endsWith(".aip") || file1.getName().endsWith(".ofd"))) {
				Map<String,String> map = new HashMap<String,String>();
				map.put("fileName", file1.getName());
				map.put("absolutePath", file1.getAbsolutePath());
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 将assets下面的某个文件夹的内容(只包含文件不包含文件夹内容)
	 * @auther chenlf3
	 * @date 2015年10月26日 下午5:04:12
	 * @param originPath 如"dj\camera"
	 * @param terminiPath 如Environment.getExternalStorageDirectory()+File.separator+"dj"+File.separator+sqs.aip
	 * @throws IOException
	 */
	public static void copyDJFileFromAssets(Context context, String originPath, String terminiPath) throws IOException {
		File dir = new File(terminiPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		String names[] = context.getAssets().list(originPath);
		if(names != null && names.length>0) {
			for(String name:names) {
				if(name != null && !"".equals(name)) {
					if(name.contains(".")) {//文件
						InputStream in = context.getAssets().open(originPath+File.separator+name);
						/** 拷贝文件 */
						saveTo(in, terminiPath+File.separator+name);
					}
				}
			}
		}
	}
	
	/**
	 * 将assets下面的某个文件夹的内容(包含文件和文件夹内容)
	 * @auther chenlf3
	 * @date 2016年1月19日 下午6:07:33
	 * @param context
	 * @param originPath
	 * @param terminiPath
	 * @throws IOException
	 */
	public static void copyDJDirAndFileAfterAssets(Context context, String originPath, String terminiPath) throws IOException {
		File dir = new File(terminiPath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		String names[] = context.getAssets().list(originPath);
		if(names != null && names.length>0) {
			for(String name:names) {
				if(name != null && !"".equals(name)) {
					if(name.contains(".")) {//文件
						InputStream in = context.getAssets().open(originPath+File.separator+name);
						/** 拷贝文件 */
						saveTo(in, terminiPath+File.separator+name);
					} else {//目录,进行遍历复制
						copyDJDirAndFileAfterAssets(context,originPath+File.separator+name,terminiPath+File.separator+name);
					}
				}
			}
		}
	}
	
	/**
	 * 获取时间
	 * @auther chenlf3
	 * @date 2016年1月19日 下午6:08:16
	 * @param timeType
	 * @return
	 */
	public static String getTime(String timeType) {
		SimpleDateFormat df = new SimpleDateFormat(timeType);//设置日期格式
		return df.format(new Date());
	}
	
	/**
	 * 复制map中的内容
	 * @auther chenlf3
	 * @date 2016年1月19日 下午6:13:59
	 * @param map
	 * @return
	 */
	public static Map getSameContent(Map map) {
		if(map == null) return null;
		Set keys = map.keySet();
		if(keys == null || keys.size()==0) return null;
		Map res = new HashMap();
		for(Object key:keys) {
			res.put(key, map.get(key));
		}
		return res;
	}
	
	/**
	 * 获取MD5签名信息
	 * @auther chenlf3
	 * @date 2016年1月26日 上午11:36:31
	 * @param content
	 * @return
	 */
	public static String getMD5String(String content) {
		if(content == null || "".equals(content.trim())) return null;
		try {
			/** 获取MD5摘要算法的MessageDigest */
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			/** 更新摘要 */
			messageDigest.update(content.getBytes());
			/** 获得密文 */
			byte[] cipherData = messageDigest.digest();
			/** 将密文转为十六进制返回 */
			StringBuilder stringBuilder = new StringBuilder("");
			for (int i = 0; i < cipherData.length; i++) {  
		        int v = cipherData[i] & 0xFF;//1101 oxff
		        String hv = Integer.toHexString(v);
		        if (hv.length() < 2) {  
		            stringBuilder.append(0);  
		        }  
		        stringBuilder.append(hv);
		    }  
		    return stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 删除某个文件
	 * @auther chenlf3
	 * @date 2016年2月1日 下午12:03:48
	 * @param filePath
	 */
	public static boolean delFile(String filePath) {
		File file = new File(filePath);
		if(file.exists() && file.isFile()) {
			file.delete();
			return true;
		}
		return true;
	}
	
	/**
	 * 删除目录
	 * @auther chenlf3
	 * @date 2016年4月1日 上午10:55:12
	 */
	public static boolean delDir(File dirFile) {
		if(!dirFile.exists() || !dirFile.isDirectory()) return true;
		File[] lists = dirFile.listFiles();
		if(lists == null || lists.length==0) {//目录无数据，删除目录
			if(!dirFile.delete()) {//删除失败，停止删除任务
				return false;
			}
		} else {//目录有数据，先删除数据再删除目录
			for(File file1:lists) {
				if(file1.exists() && file1.isFile()) {//删除其中文件
					if(!file1.delete()) {
						return false;
					}
				} else if(file1.exists() && file1.isDirectory()) {//删除其中文件夹
					if(!delDir(file1)) {
						return false;
					}
				} else {
					return false;
				}
			}
			/** 再删除完文件夹内部数据之后，删除该文件夹 */
			if(!dirFile.delete()) {//删除失败，停止删除任务
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 复制目录
	 * @auther chenlf3
	 * @date 2016年4月1日 上午11:45:45
	 * @param originPath 源目录
	 * @param terminiPath 目标目录
	 * @throws IOException
	 */
	public static boolean copyDir(String originPath, String terminiPath) {
		File originFile = new File(originPath);
		if(!originFile.exists() || !originFile.isDirectory()) {
			return false;
		}
		File dir = new File(terminiPath+File.separator+originFile.getName());
		if(!dir.exists()) {
			dir.mkdirs();
		}
		File[] list = originFile.listFiles();
		for(File file1:list) {
			if(file1.isDirectory()) {//目录
				if(!copyDir(file1.getAbsolutePath(),terminiPath+File.separator+originFile.getName())) {
					return false;
				}
			} else {//文件
				if(!copyFile(file1.getAbsolutePath(), terminiPath+File.separator+originFile.getName()+File.separator+file1.getName())) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 *  方法一：
	 *	setCanceledOnTouchOutside(false);调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
	 *	方法二：
	 *	setCancelable(false);调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
	 * 弹出提示框
	 * @auther chenlf3
	 * @date 2016年3月7日 上午11:15:59
	 * @param context
	 * @param title
	 * @param content
	 * @param positiveListener
	 * @param negativeListener
	 * @return
	 */
	public static AlertDialog popDialog(Context context, String title, String content, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
		Builder builder = new Builder(context);
		builder.setTitle(title);//设置提示框的标题
		builder.setMessage(content);//提示内容
		builder.setPositiveButton("确定", positiveListener);
		builder.setNegativeButton("取消", negativeListener);
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	/**
	 *
	 * @auther chenlf3
	 * @date 2016年5月30日 上午10:50:32
	 * @param context
	 * @param title
	 * @param content
	 * @param ok
	 * @param cancel
	 * @param positiveListener
	 * @param negativeListener
	 * @return
	 */
	public static AlertDialog popDialog(Context context, String title, String content, String ok, String cancel, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
		Builder builder = new Builder(context);
		builder.setTitle(title);//设置提示框的标题
		builder.setMessage(content);//提示内容
		builder.setPositiveButton(ok, positiveListener);
		builder.setNegativeButton(cancel, negativeListener);
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	/*
	 * 弹出带布局的弹出框
	 */
	public static AlertDialog popDialog(Context context, String title, String content, String ok, String cancel, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener, View view) {
		Builder builder = new Builder(context);
		if(!TextUtils.isEmpty(title)) {
			builder.setTitle(title);//设置提示框的标题
		}
		if(!TextUtils.isEmpty(content)) {
			builder.setMessage(content);//提示内容
		}
		builder.setView(view);
		builder.setPositiveButton(ok, positiveListener);
		builder.setNegativeButton(cancel, negativeListener);
		AlertDialog dialog = builder.create();
		dialog.show();
		return dialog;
	}

	/**
	 * 弹出吐司
	 * @auther chenlf3
	 * @date 2016年3月7日 上午11:52:18
	 * @param context
	 * @param content
	 */
	public static void popToast(Context context, String content) {
		Toast.makeText(context, content, Toast.LENGTH_LONG).show();
	}

	/**
	 * 清空文件夹下所有文件
	 * @auther chenlf3
	 * @date 2016年3月11日 下午4:23:55
	 * @param dirPath
	 */
	public static void cleanDir(String dirPath) {
		File file = new File(dirPath);
		if(file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			for(File file1:files) {
				if(file1.isFile()) {
					file1.delete();
				}
			}
		}
	}

	/**
	 * 将byte[]转化为字符窜
	 * @auther chenlf3
	 * @date 2016年3月23日 下午2:42:50
	 * @return
	 */
	public static String bytearrayToHexString(byte[] data, int leng) {
		StringBuffer strbuf = new StringBuffer("0x");
		if(leng<0) {
			leng = 0;
		}
		if(leng > data.length) {
			leng = data.length;
		}
		for (int i = 0; i < leng; i++) {
			strbuf.append("0123456789abcdef".charAt(((byte) ((data[i] & 0xf0) >> 4))));
			strbuf.append("0123456789abcdef".charAt((byte) (data[i] & 0x0f)));
		}
		return strbuf.toString();
	}

	/**
	 * 16进制转化为byte
	 * @auther chenlf3
	 * @date 2016年3月23日 下午5:30:07
	 * @param data 必须为0x开头的16进制
	 * @return
	 */
	public static Byte switch16to2(String data) {
		String range = "0123456789abcdef";
		if(data.length() == 4 && data.startsWith("0x")) {
			String mainData = data.substring(2);
			try {
				return Integer.valueOf(mainData,16).byteValue();
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 根据16进制字符串，获取16进制数组
	 * @auther chenlf3
	 * @date 2016年8月6日 下午3:19:51
	 * @param data
	 * @return
	 */
	public static byte[] switch16to2s(String data) {
		String range = "0123456789abcdef";
		if(data != null && (!"".equals(data)) && data.length()%2 == 0) {
			int count = data.length()/2;
			byte[] res = new byte[count];
			for(int i=0;i<count;i++) {
				String ceilData = data.substring(2*i, 2*i+2);
				res[i] = Integer.valueOf(ceilData,16).byteValue();
			}
			return res;
		}
		return null;
	}

	public static String switch2to16s(byte[] data) {
		StringBuffer strbuf = new StringBuffer("");
		if(data == null && data.length<=0) return null;
		for (int i = 0; i < data.length; i++) {
			strbuf.append("0123456789abcdef".charAt(((byte) ((data[i] & 0xf0) >> 4))));
			strbuf.append("0123456789abcdef".charAt((byte) (data[i] & 0x0f)));
		}
		return strbuf.toString();
	}

	public static String switch2to16s(byte data) {
		StringBuffer strbuf = new StringBuffer("");
		strbuf.append("0123456789abcdef".charAt(((byte) ((data & 0xf0) >> 4))));
		strbuf.append("0123456789abcdef".charAt((byte) (data & 0x0f)));
		return strbuf.toString();
	}

	/**
	 * 调整声音大小
	 * @auther chenlf3
	 * @date 2016年3月24日 上午11:55:23
	 * @param context
	 * @param value
	 */
	public static void adjustVolume(Context context, int value) {
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);//音量
		int setValue = 0;
		/** 获取声音最大值 */
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if(value<=0) {
			setValue = 0;
		} else if(value >0 && value<maxVolume) {
			setValue = value;
		} else {
			setValue = maxVolume;
		}
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, setValue, audioManager.FLAG_PLAY_SOUND);
	}

	/**
	 *
	 * @auther chenlf3
	 * @date 2016年3月12日 下午5:54:02
	 * @param context
	 * @param range 总档位
	 * @param value 设定档位，从1档位开始
	 */
	public static void adjustVolume(Context context, int range, int value) {
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);//音量
		int setValue = 0;
		/** 获取声音最大值 */
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		if(value<=0) {
			setValue = 0;
		} else if(value >0 && value<maxVolume) {
			int cell = maxVolume/range;//每个档位大小
			int tempValue = 1;
			if(value <1) {
				tempValue = 1;
			} else if(value>range) {
				tempValue = range;
			} else {
				tempValue = value;
			}
			setValue = tempValue * cell;
		} else {
			setValue = maxVolume;
		}
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, setValue, audioManager.FLAG_PLAY_SOUND);
	}

	/**
	 * 获取可用空间，单位M
	 * @auther chenlf3
	 * @date 2016年3月24日 下午3:15:44
	 * @return
	 */
	public static String getAvailableSpace(Context context) {
		/** 获取sd卡目录文件 */
		File path = Environment.getExternalStorageDirectory();
		//构造sd卡文件系统
		StatFs stat = new StatFs(path.getPath());
		//获取sd卡存储块的大小
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();//获取sd卡可用存储块数目
		long availableSize = availableBlocks * blockSize;//可用空间大小
		long res = availableSize/(1024*1024);
		//String strAvailableSize = Formatter.formatFileSize(context, availableSize);
		return ""+res;
	}

	/**
	 * 获取手机屏幕宽高
	 * @auther chenlf3
	 * @date 2016年4月14日 下午4:45:14
	 * @return
	 */
	public static Point getScreenSize(Activity context) {
		Point point = new Point();
		WindowManager manager = context.getWindowManager();
		int width = manager.getDefaultDisplay().getWidth();
		int height = manager.getDefaultDisplay().getHeight();
		point.set(width, height);
		return point;
	}

	/**
	 * 获取蓝牙状态(1-打开；0-关闭；-1-没有蓝牙模块)
	 * @auther chenlf3
	 * @date 2016年6月3日 下午1:59:45
	 * @return
	 */
	public static String getBlueToothState() {
		BluetoothAdapter mBTAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBTAdapter == null) {
			return "-1";
		}
		if(mBTAdapter.isEnabled()) {
			return "1";
		} else {
			return "0";
		}
	}

	/**
	 * 打开设备蓝牙
	 * @auther chenlf3
	 * @date 2016年6月3日 下午2:06:23
	 */
	public static void openBlueTooth(Activity context) {
		if("0".equals(getBlueToothState())) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			context.startActivityForResult(enableIntent, 3);
		}
	}

	/**
	 * 获取汉字的个数
	 * @auther chenlf3
	 * @date 2016年5月26日 下午5:49:02
	 * @param content
	 * @return
	 */
	public static int getChinCount(String content) {
		if(content == null || content.length()<=0) {
			return 0;
		}
		int count = 0;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(content);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count = count + 1;
			}
		}
		return count;
	}

	/**
	 * 获取android设备唯一标示
	 * @auther chenlf3
	 * @date 2016年7月26日 下午4:46:54
	 * @param context
	 * @return
	 */
	@SuppressLint("MissingPermission")
	public static String getUUID(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
	}
	
	/**
	 * 获取透明色
	 * @auther chenlf3
	 * @date 2017年1月3日 下午6:14:41
	 * @param color
	 * @param alpha
	 * @return
	 */
	public static int getTransparentColor(String color, int alpha) {
		if(TextUtils.isEmpty(color)) {
			return -1;
		}
		String temp = "";
		if(color.startsWith("#") && color.length() == 7) {
			temp = color.substring(1);
		} else if((!color.startsWith("#")) && color.length() == 6) {
			temp = color;
		} else {
			return -1;
		}
		String clfAlpha = Integer.toHexString(alpha);
		if(clfAlpha.length()==1) {
			clfAlpha = "0"+clfAlpha;
		}
		int clfColor = Color.parseColor("#"+clfAlpha+temp);
		return clfColor;
	}
	
	public static long getRandom() {
		long time = System.currentTimeMillis();
		int randNum = (int) (Math.random() * 1000);
		long res = Long.parseLong(""+time+randNum);
		return res;
	}
	
}
