package com.zed.hotsport.utils;

public class ByteToString {

	/**
	 * 把字节数组转换成字符串的工具类
	 * 
	 * @param bytes
	 *            字符数组参数
	 * @return
	 */
	public static String Byte2String(byte[] bytes) {

		StringBuilder strBuilder = new StringBuilder();

		for (int i = 0; i < bytes.length; i++) {

			strBuilder.append(bytes[i]);
		}

		return strBuilder.toString();

	}

}
