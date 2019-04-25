package com.zjht.jfmall.marketing.service;

import com.alibaba.fastjson.JSONObject;
import com.zjht.jfmall.marketing.common.GlobalConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;

/**
 * 接口请求处理工具类
 * @author lijunjie
 * @since 2015-1-19 11:05:38
 *
 */
public class InterfaceService  {
	
	private static final Logger log = LoggerFactory.getLogger(InterfaceService.class);

	
	public InterfaceService(){
		
	}
	
	public String doProcess(String url , JSONObject json){
		
		if(StringUtils.isBlank(url)){
			url = getGatewayInterfaceUrl();
		}
		
		String resultHtml ="";
		try{
			resultHtml = this.httpPost(url, json.toJSONString());
			// 打印返回结果
			log.debug("响应结果--开始");
			log.debug("响应结果resultHtml:" + resultHtml);
			log.debug("响应结果--结束");
		}catch(IOException ex){
			log.error(ex.getMessage(), ex);
		}
		
		return resultHtml;
	}
	
	protected String getGatewayInterfaceUrl(){
		String url = GlobalConfig.getConfigValue(GlobalConfig.APPURL);
		return url;
	}
	
	protected String httpPost(String serviceUrl, String bizData) throws IOException {
		HttpResponse response;
		String responseText ="";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(serviceUrl);
			StringEntity jsonStringEntity = new StringEntity(bizData,"UTF-8");
			jsonStringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, "application/json"));
	        httpPost.setEntity(jsonStringEntity);
				response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode < 200 || statusCode >= 400) {
				//throw new RuntimeException("http error with code " + response.getStatusLine());
			}
			responseText = getResponseText(response);
			//System.out.println(responseText);
			log.debug(responseText);
		} finally {
			if (httpclient != null)
				httpclient.getConnectionManager().shutdown();
		}
		return responseText;
	}
	private String getResponseText(HttpResponse httpResponse) throws IOException {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		HttpEntity entity = httpResponse.getEntity();
		// System.out.println(httpResponse.getStatusLine());
		InputStream inputStream = entity.getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
		String line = reader.readLine();
		while (line != null) {
			// System.out.println(line);
			pw.println(line);
			line = reader.readLine();
		}
		reader.close();
		pw.close();
		String responseText = sw.toString();
		return responseText;
	}
}
