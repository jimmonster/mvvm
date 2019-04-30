package com.jim.common.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.support.annotation.RequiresApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class DataUtils {
	/**
	 * 读取json串
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String readLocalJson(Context context, String fileName) {
		File file = new File(context.getFilesDir(), fileName);
		if(!file.exists()){
			return "";
		}
		String result = "";
		try {
			FileInputStream fin = context.openFileInput(fileName);
			// 获取文件长度
			int lenght = fin.available();
			byte[] buffer = new byte[lenght];
			fin.read(buffer);
			// 将byte数组转换成指定格式的字符串
			result = new String(buffer, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		// String resultString = "";
		// try {
		// InputStream inputStream = context.getResources().getAssets()
		// .open(fileName);
		// byte[] buffer = new byte[inputStream.available()];
		// inputStream.read(buffer);
		// resultString = new String(buffer, "utf-8");
		// } catch (Exception e) {
		// // handle exception
		// }
		// return resultString;
	}

	/**
	 * 生成json文件
	 * 
	 * @param context
	 */
	public static void createJsonFile(Context context, String s, String userId,
                                      String fileType) {
		String filename = userId + fileType;
		try {
			FileOutputStream outputStream = context.openFileOutput(filename,
					Activity.MODE_PRIVATE);
			outputStream.write(s.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/*
	 * 封装的GSON解析工具类，提供泛型参数
	 */
	// 将Json数据解析成相应的映射对象
	public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
		Gson gson = new Gson();
		T result = gson.fromJson(jsonData, type);
		return result;
	}

	// 将Json数组解析成相应的映射对象列表
	public static <T> List<T> parseJsonArrayWithGson(String jsonData,
                                                     Class<T> type) {
		Gson gson = new Gson();
		TypeToken<List<T>> typeToken = new TypeToken<List<T>>() {
		};
		List<T> result = gson.fromJson(jsonData, typeToken.getType());
		return result;
	}

	/**
	 * 4.4版本以后获得图库地址
	 * @param context
	 * @param uri
	 * @return
	 */
	@RequiresApi(api = Build.VERSION_CODES.KITKAT)
	public static String getPath(final Context context, final Uri uri) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}
			}
			else if (isDownloadsDocument(uri)) {
				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));
				return getDataColumn(context, contentUri, null, null);
			}
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];
				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				final String selection = MediaColumns._ID + "=?";
				final String[] selectionArgs = new String[] { split[1] };
				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			if (isGooglePhotosUri(uri)) {
				return uri.getLastPathSegment();
			}
			return getDataColumn(context, uri, null, null);
		}
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}
		return null;
	}

	public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {
		Cursor cursor = null;
		final String column = MediaColumns.DATA;
		final String[] projection = { column };
		try {
			cursor = context.getContentResolver().query(uri, projection,selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
   /**
     * @param uri
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
    
    /**
     * 获得系统日期
     */
    public static String getCurrentDate(){
    	 Calendar c=Calendar.getInstance();
    	 int year=c.get(Calendar.YEAR); //得到年
    	 int month=c.get(Calendar.MONTH)+1;//month加一 //月
    	 int day=c.get(Calendar.DAY_OF_MONTH);//日
    	 return year+""+month+""+day;
    }

	



}
