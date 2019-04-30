package com.jim.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author jim
 * @date 2016-7-18
 * @pagename md5
 * @desc md5 加密处理

 *
 */
public class Md5Utils {
	/**
	 * 
	 * @param mess
	 *         要加密的字符串
	 * @return
	 *       md5值   不可逆
	 */
	public static String getMd5(String mess){
		StringBuilder ss = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digest = md.digest(mess.getBytes());
			for (byte b : digest){
				//把byte 转成16进制数
				//Integer.toHexString(hashCode())
				int i = b;// 1byte  4byte 
				//清空掉前三个字节 变为0
				i = 0x000000ff &i;
				String hex = Integer.toHexString(i);
				if (hex.length() == 1) {
					hex = "0" + hex;
				}
				//拼接
				ss.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ss + "";
	}
}
