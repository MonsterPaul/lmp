package com.zjht.jfmall.weChat.helper;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息工具类
 * @author 作者 xujianfeng
 * @date 创建时间：2017年3月6日 下午4:45:21
 */
public class MessageUtil {
	/**
	 * 解析xml
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 定义一个文档
			Document document = null;
			//将字符串转换为
			document = DocumentHelper.parseText(xml);
			// 得到xml的根节点(message)
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();
			
			// 遍历所有子节点
			for (Element e : elementList){
				map.put(e.getName(), e.getText());
			}
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 解析微信服务器发来的请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseXml(HttpServletRequest request) {
		// 将解析结果存储在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从request中取得输入流
		InputStream inputStream = null;
		try {
			inputStream = request.getInputStream();
			// 读取输入流
			SAXReader reader = new SAXReader();
			Document document = reader.read(inputStream);
			// 得到xml根元素
			Element root = document.getRootElement();
			// 得到根元素的所有子节点
			List<Element> elementList = root.elements();
			
			// 遍历所有子节点
			for (Element e : elementList){
				map.put(e.getName(), e.getText());
			}
			
			// 释放资源
			inputStream.close();
			inputStream = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

}
