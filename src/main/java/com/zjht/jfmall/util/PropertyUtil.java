package com.zjht.jfmall.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 配置文件处理类 
 * 
 * @author lijunjie
 * 
 * 
 */
public class PropertyUtil {

	/**
	 * 获取配置文件里对应的值
	 * @param dirName config下文件夹的名字 与properties文件名对应
	 * @param key 属性名
	 * @return
	 */
	public static String getPropertyValue(String dirName, String key) {
		InputStream in = PropertyUtil.class.getResourceAsStream("/config/" + dirName +".properties");
		
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p.getProperty(key);
	}

//	public static String getAppProperty(String key) {
//		InputStream in = PropertyUtil.class.getResourceAsStream("/config/app" + "/app.properties");
//
//		Properties p = new Properties();
//		try {
//			p.load(in);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return p.getProperty(key);
//	}

	/**
	 * 获取配置文件中设定的参数（定时任务中查询时间参数）
	 * 
	 * @param name
	 * @return
	 */
//	public static int getDeadline(String name) {
//		int deadline = 0;
//		String deadlineStr = getAppProperty(name);
//		if (StringHelper.isNotBlank(deadlineStr)) {
//			deadline = Integer.parseInt(deadlineStr);
//		}
//		return deadline;
//	}
	/**
	 * 查找/config/messages.properties 的属性文件键值对
	 * @param key
	 * @return
	 */
	public static String getMessagesProperty(String key) {
		InputStream in = PropertyUtil.class.getResourceAsStream("/config/messages.properties");
		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = p.getProperty(key);
		return result;
	}

	public static String getSignTempDirectory() {
		URL url = PropertyUtil.class.getResource("/config/temp");
		if (url != null)
			return url.getPath();
		return "";
	}

	/**
	 * 获取config下dirName文件夹下fileName的配置文件
	 * 
	 * @param dirName 文件夹名
	 * @param fileName 文件名
	 * @return
	 */
	public static Properties getPropertes(String dirName, String fileName) {
		String filePath = "";
		if(StringUtils.isBlank(dirName)){
			filePath = "/config/"+fileName+".properties";
		}else{
			filePath = "/config/" + dirName + "/"+fileName+".properties";
		}
		InputStream in = PropertyUtil.class.getResourceAsStream(filePath);

		Properties p = new Properties();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p;
	}
}
