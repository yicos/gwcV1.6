package com.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;

public class CommUtils {
	/**
	 * md5加密
	 * 
	 * @param code
	 * @param charset
	 * @return
	 */
	public static String Md5Encode(String code, Charset charset) {
		try {
			// MD5实例
			MessageDigest md = MessageDigest.getInstance("MD5");
			// BASE64Encoder base64Encoder = new BASE64Encoder();
			// return base64Encoder.encode(md.digest(code.getBytes(charset)));
			byte[] messageByte = code.getBytes("UTF-8");
			byte[] md5Byte = md.digest(messageByte);
			return bytesToHex(md5Byte);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字节数组转成16进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer hexStr = new StringBuffer();
		int num;
		for (int i = 0; i < bytes.length; i++) {
			num = bytes[i];
			if (num < 0) {
				num += 256;
			}
			if (num < 16) {
				hexStr.append("0");
			}
			hexStr.append(Integer.toHexString(num));
		}
		// return hexStr.toString().toUpperCase();
		return hexStr.toString().toLowerCase();
	}

	/**
	 * des加密
	 * 
	 * @param srcStr
	 * @param charset
	 * @param sKey
	 * @return
	 */
	public static String encrypt(String srcStr, Charset charset, String sKey) {
		byte[] src = srcStr.getBytes(charset);
		byte[] buf = Des.encrypt(src, sKey);
		return Des.parseByte2HexStr(buf);
	}

	/***
	 * des加密
	 * 
	 * @param srcStr
	 * @param charset
	 * @param sKey
	 * @param iv
	 * @return
	 */
	public static String encrypt(String srcStr, Charset charset, String sKey, String iv) {
		byte[] src = srcStr.getBytes(charset);
		byte[] buf = Des.encrypt(src, sKey, iv);
		return Des.parseByte2HexStr(buf);
	}

	/**
	 * des解密
	 * 
	 * @param hexStr
	 * @param charset
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String hexStr, Charset charset, String sKey) throws Exception {
		byte[] src = Des.parseHexStr2Byte(hexStr);
		byte[] buf = Des.decrypt(src, sKey);
		return new String(buf, charset);
	}

}
