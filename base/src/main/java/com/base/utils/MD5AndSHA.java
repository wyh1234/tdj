package com.base.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5AndSHA {

	/**
	 * MD5加密 生成32位md5码
	 * @param intStr 待加密字符串
	 * @return 返回32位md5码
	 * @throws Exception
	 */
	public static String md5Encode(String intStr){
		MessageDigest md5 = null;
		try {
			//创建md5对象
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		//将传入的字符串转换为UTF-8字节数组
		byte[] bytes = new byte[0];
		try {
			bytes = intStr.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//将字节数组转化为md5字节数组	
 		byte[] md5Bytes = md5.digest(bytes);
 		StringBuilder hexValue = new StringBuilder();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int)md5Bytes[i]) & 0xff;
			if(val < 16){
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		
		return hexValue.toString();
	}
	/**
	 *  SHA加密 生成40位SHA码
	 * @param str 待加密字符串
	 * @return 返回40位SHA码
	 * @throws IOException
	 */
	public static String shaEncode(String str) throws IOException{
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		byte[] bytes = str.getBytes("UTF-8");
		byte[] shaBytes = sha.digest(bytes);
		StringBuilder hexValue = new StringBuilder();
		for (int i = 0; i < shaBytes.length; i++) {
			int val = ((int)shaBytes[i]) & 0xff;
			if(val < 16){
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		
		return hexValue.toString();
	}
	

}
