package com.zjht.jfmall.util;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CodeBuilder {
	private static final String NUMBERS = "0123456789";
	private static final String LETTERS_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String LETTERS_LOWWER = "abcdefghijklmnopqrstuvwxyz";
	private static final String BASECODE = LETTERS_UPPER + LETTERS_LOWWER + NUMBERS;

	/**
	 * 根据当前时间生成不重复无规则CODE
	 * 
	 * @return
	 */
	public static String numberCode() {
		DateFormat df = new SimpleDateFormat("SSSyyyyHHMMmmddss");
		return String.format("%1$s%2$s", df.format(new Date()), Math.round(Math.random() * 10000));
	}

	/**
	 * 生成当前时间生成字符串，精确到毫秒
	 * 
	 * @return
	 */
	public static String dateCode() {
		return dateCode("yyyyMMddHHmmssSSS");
	}

	/**
	 * 生成当前时间生成字符串，精确到毫秒
	 * 
	 * @return
	 */
	public static String dateCode(String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(new Date());
	}

	/**
	 * 生成一个随机UUID
	 * 
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * MessageDigest(SHA-1) + base64加密
	 * 
	 * @param oriStr
	 * @return
	 */
	public static String encrypt(String oriStr) {
		String eryStr = null;
		oriStr = String.format("158X%1$sL7651", oriStr);
		try {
			MessageDigest digst = MessageDigest.getInstance("SHA-1");
			digst.update(oriStr.getBytes());
			eryStr = new String(Base64.encodeBase64(digst.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return eryStr;
	}

	/**
	 * MessageDigest(MD5) + base64加密
	 * 
	 * @param oriStr
	 * @return
	 */
	public static String encryptMD5(String oriStr) {
		String eryStr = null;
		oriStr = String.format("158X%1$sL7651", oriStr);
		try {
			MessageDigest digst = MessageDigest.getInstance("MD5");
			digst.update(oriStr.getBytes());
			eryStr = new String(Base64.encodeBase64(digst.digest()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return eryStr;
	}

	/**
	 * MD5加密
	 * 
	 * @param oriStr
	 * @return
	 */
	public static String md5(String oriStr) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			// String tmep=new String(s.getBytes("utf-8"),"utf-8");
			// System.out.println(s);
			byte[] strTemp = oriStr.getBytes("utf-8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");// 返回实现MD5摘要算法的
																	// MessageDigest
																	// 对象。
			mdTemp.update(strTemp); // 使用指定的字节更新摘要。
			byte[] md = mdTemp.digest();// 得到md5算法结果 //通过执行诸如填充之类的最终操作完成哈希计算
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 分成高低4位处理
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * base64加密
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Encode(String str) {
		return new String(Base64.encodeBase64(str.getBytes()));
	}

	/**
	 * base64解密
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Decode(String str) {
		return new String(Base64.decodeBase64(str.getBytes()));
	}

	/**
	 * 生成六个数字
	 * 
	 * @return
	 */
	public static String sixNO() {
		String code = "";
		try {
			for (int i = 0; i < 6; i++) {
				code += NUMBERS.charAt((int) Math.floor(Math.random() * 10));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}
}
