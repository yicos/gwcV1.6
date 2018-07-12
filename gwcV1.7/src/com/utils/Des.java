package com.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Des {
	/**
	 * ����
	 * 
	 * @param data
	 * @param sKey
	 * @return
	 */
	public static byte[] encrypt(byte[] data, String sKey) {
		try {
			byte[] key = sKey.getBytes();
			// ��ʼ������
			IvParameterSpec iv = new IvParameterSpec(key);
			//IvParameterSpec iv = new IvParameterSpec("uX8yxgwy".getBytes());
			DESKeySpec desKey = new DESKeySpec(key);
			// ����һ���ܳ׹�����Ȼ��������DESKeySpecת����securekey
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher����ʵ����ɼ��ܲ���
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// ���ܳ׳�ʼ��Cipher����
			cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
			// ���ڣ���ȡ���ݲ�����
			// ��ʽִ�м��ܲ���
			return cipher.doFinal(data);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ����
	 * 
	 * @param src
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, String sKey) throws Exception {
		byte[] key = sKey.getBytes();
		// ��ʼ������
		IvParameterSpec iv = new IvParameterSpec(key);
		// ����һ��DESKeySpec����
		DESKeySpec desKey = new DESKeySpec(key);
		// ����һ���ܳ׹���
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// ��DESKeySpec����ת����SecretKey����
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher����ʵ����ɽ��ܲ���
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// ���ܳ׳�ʼ��Cipher����
		cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
		// ������ʼ���ܲ���
		return cipher.doFinal(src);
	}

	/**
	 * ��������ת����16����
	 *
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * ��16����ת��Ϊ������
	 *
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static byte[] encrypt(byte[] data, String sKey, String sIv) {
		try {
			byte[] key = sKey.getBytes();
			// ��ʼ������
			IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());
			//IvParameterSpec iv = new IvParameterSpec("uX8yxgwy".getBytes());
			DESKeySpec desKey = new DESKeySpec(key);
			// ����һ���ܳ׹�����Ȼ��������DESKeySpecת����securekey
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher����ʵ����ɼ��ܲ���
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			// ���ܳ׳�ʼ��Cipher����
			cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
			// ���ڣ���ȡ���ݲ�����
			// ��ʽִ�м��ܲ���
			return cipher.doFinal(data);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	
	}
}
