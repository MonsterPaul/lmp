package com.zjht.jfmall.util;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串帮助类.
 * 
 * @author lijunjie
 */
public class StringHelper {

	/**
	 * 
	 * @return "" 和 null 返回 true, 否则返回 false.
	 */
	public static boolean isBlank(String string) {
		return StringUtils.isBlank(string);
	}

	/**
	 * 
	 * @return "" 和 null 返回 false, 否则返回 true.
	 */
	public static boolean isNotBlank(String string) {
		return StringUtils.isNotBlank(string);
	}

	/**
	 * String trim 如:<br/>
	 * " abc" => "abc"<br/>
	 * null => ""<br/>
	 * "abc " => "abc"<br/>
	 * "" => ""
	 */
	public static String trimToEmpty(String str) {
		return StringUtils.trimToEmpty(str);
	}

	/**
	 * Object trim, 主要为hql返回的Objcet[]服务. 如:<br/>
	 * null => ""<br/>
	 * str 不为null时, trimToEmpty(str.toString())
	 * 
	 * @see com.StringHelper.util.StringHelper#trimToEmpty(String)
	 * @author chenlb 2007-10-21 下午10:33:39
	 */
	public static String trimToEmpty(Object str) {
		if (str == null) {
			return "";
		} else {
			return trimToEmpty(str.toString());
		}
	}

	/**
	 * 如果Object为null返回null
	 * 
	 * @see #trimToEmpty(Object)
	 */
	public static String trim(Object str) {
		if (str == null) {
			return null;
		} else {
			return trimToEmpty(str.toString());
		}
	}

	/**
	 * 判断字符串是否为数字.
	 * <p/>
	 * isNumeric("") => false 区别于 StringUtils.isNumeric("") => true
	 * <p/>
	 * StringHelper.isNumeric(null) = false <br/>
	 * StringHelper.isNumeric("") = false <br/>
	 * StringHelper.isNumeric(" ") = false <br/>
	 * StringHelper.isNumeric("123") = true <br/>
	 * StringHelper.isNumeric("12 3") = false <br/>
	 * StringHelper.isNumeric("ab2c") = false <br/>
	 * StringHelper.isNumeric("12-3") = false <br/>
	 * StringHelper.isNumeric("12.3") = false <br/>
	 * 
	 */
	public static boolean isNumeric(String str) {
		return StringUtils.isNotBlank(str) && StringUtils.isNumeric(str);
	}

	public static boolean isNotNumeric(String str) {
		return !isNumeric(str);
	}

	/**
	 * 判断字符串长度是否等于len.
	 * <p>
	 * 
	 * @return str == null && len == -1 返回true.
	 */
	public static boolean isLengthEqual(String str, int len) {
		if (str == null) {
			if (len == -1) {
				return true;
			} else {
				return false;
			}
		}
		return str.length() == len;
	}

	/**
	 * 输出指定长度的字符串,用ch填充不够长度的内容.
	 * 
	 * @param value
	 *            待填充的字符串,
	 * @param ch
	 *            要填充的字符
	 * @param len
	 *            指定长度.
	 * @return 如value长度 > len直接返回, value == null返回null
	 * @author chenlb 2007-10-26 下午11:12:37
	 */
	public static String fillChar(String value, char ch, int len) {
		if (value == null) {
			return null;
		}
		int length = value.length();
		if (length > len) {
			return value;
		}
		StringBuffer str = new StringBuffer(len);
		for (int i = 0; i < (len - length); i++) {
			str.append(ch);
		}
		str.append(value);
		return str.toString();
	}

	/**
	 * 整数前面补0
	 * 
	 * @see com.StringHelper.util.StringHelper#fillChar(String, char, int)
	 * @author chenlb 2007-10-27 上午11:16:44
	 */
	public static String fillChar(int value, int len) {
		return fillChar("" + value, '0', len);
	}

	/**
	 * "a，b,c*d。e:f.g;h" => 8个字符,分别是a b c d e f g h
	 * 
	 * @param separatorChars
	 *            如",* :.;，。"作为分词字符.
	 * @return str == null 返回String[0]
	 * @author chenlb 2007-10-28 下午04:32:22
	 */
	public static String[] split(String str, String separatorChars) {
		if (str == null) {
			str = "";
		}
		return StringUtils.split(str, separatorChars);
	}

	/**
	 * 把字符串数组转换成逗号隔开的字符串.
	 * 
	 * @return 逗号隔开的字符串. strs == null || strs.length < 1 返回""
	 * @see com.StringHelper.util.StringHelper#toStringWithChar(String[], char)
	 * @author chenlb 2007-11-10 下午08:45:00
	 */
	public static String toStringWithComma(String[] strs) {
		return toStringWithChar(strs, ',');
	}

	/**
	 * 把字符串数组转换成 ch 隔开的字符串.
	 * 
	 * @param ch
	 *            分隔字符
	 * @return 给定分隔符的字符串. strs == null || strs.length < 1 返回""
	 * @author chenlb 2007-11-10 下午08:49:35
	 */
	public static String toStringWithChar(String[] strs, char ch) {
		if (strs == null || strs.length < 1) {
			return "";
		}

		StringBuffer buf = new StringBuffer();
		buf.append(trimToEmpty(strs[0]));

		for (int i = 1; i < strs.length; i++) {
			buf.append(ch + trimToEmpty(strs[i]));
		}
		return buf.toString();
	}

	/**
	 * 两值选择器
	 * 
	 * @return selector == true 返回 trueStr, 否则返回falseStr.
	 * @author chenlb 2007-11-23 上午12:10:16
	 */
	public static String binarySelector(boolean selector, String trueStr, String falseStr) {
		return selector ? trueStr : falseStr;
	}

	/**
	 * 两值选择器
	 * 
	 * @return one == null && two == null 或""+one.equals(two) 返回trueStr.否则返回falseStr.
	 * @author chenlb 2007-11-23 上午12:18:55
	 */
	public static String binarySelector(String one, String two, String trueStr, String falseStr) {
		if (one == null && two == null) {
			return trueStr;
		}
		if (("" + one).equals(two)) {
			return trueStr;
		}
		return falseStr;
	}

	/**
	 * 
	 * @return 返回字符串长度, str == null返回0
	 * @author chenlb 2007-12-1 下午02:20:57
	 */
	public static int length(String str) {
		if (str == null) {
			return 0;
		}
		return str.length();
	}

	public static String NullToVoid(String str) {
		if (StringHelper.isBlank(str))
			return "";
		else
			return str;
	}

	public static byte[] AcsToBcd(String asc) {

		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0 " + asc;
			len = asc.length();
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;

		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}

			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			//
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	public static String format(String s, int size) {
		// 写上你要怎么处理字符串
		// 这里假定是一个1到4位的整数，不足四位时前面补0
		int len = s.length();
		// 不足四位
		if (len < size) {
			int prefixNum = size - 1;// 计算要补几个0

			// 前面补0
			for (int i = 0; i < prefixNum; i++) {
				s = "0" + s;
			}
		}

		return s;
	}

	public static String other_char_format(String sourcechar, String other_char, int size) {
		// 写上你要怎么处理字符串
		// 这里假定是一个1到4位的整数，不足四位时前面补0
		int len = sourcechar.length();
		// 不足四位
		if (len < size) {
			int prefixNum = size - len;// 计算要补几个0

			// 前面补0
			for (int i = 0; i < prefixNum; i++) {
				sourcechar = sourcechar + other_char;
			}
		}

		return sourcechar;
	}
	
	/**
	 * 获取用户IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");       
		}       
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {       
			ip = request.getHeader("WL-Proxy-Client-IP");       
		}       
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {       
			ip = request.getRemoteAddr();       
		}       
		return ip;       
	}

}
