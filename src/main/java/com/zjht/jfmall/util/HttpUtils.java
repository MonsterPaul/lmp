package com.zjht.jfmall.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: http utils
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author LIJUNJIE
 * @version 1.0
 */
public class HttpUtils {

	private static final String URL_PARAM_CONNECT_FLAG = "&";
	private static final int SIZE = 1024 * 1024;
	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);

	private HttpUtils() {}

	/**
	 * GET METHOD
	 * 
	 * @param strUrl
	 *            String
	 * @param map
	 *            Map
	 * @throws IOException
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static String URLGet(String strUrl, Map map) throws IOException {
		String strTotalURL = "";
		StringBuffer result = new StringBuffer();
		String params = getUrl(map);
		if (strUrl.indexOf("?") == -1) {
			strTotalURL = StringUtils.isBlank(params) ? strUrl : (strUrl + "?" + params);
		} else {
			strTotalURL = StringUtils.isBlank(params) ? strUrl : (strUrl + "&" + params);
		}
		log.debug("Get Url:" + strTotalURL);
		URL url = new URL(strTotalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setFollowRedirects(true);
		InputStream is = con.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"), SIZE);
		while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			} else {
				result.append(line);
			}
		}
		if (in != null) {
			in.close();
		}
		if (is != null) {
			is.close();
		}
		if (con != null) {
			con.disconnect();
		}
		return result.toString();
	}

	/**
	 * POST METHOD
	 * 
	 * @param strUrl
	 *            String
	 * @param map
	 *            Map
	 * @throws IOException
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String URLPost(String strUrl, Map map) throws IOException {
		// strUrl="http://localhost:8080/front/ccr/t.do";
		String params = "";
		params = getUrl(map);
		String totalURL = strUrl;
		StringBuffer sbuffer = new StringBuffer();

		// if (strUrl.indexOf("?") == -1) {
		// totalURL = strUrl + (StringUtils.isBlank(params)?"":"?" + params);
		// } else {
		// totalURL = strUrl + (StringUtils.isBlank(params)?"":"&" + params);
		// }
		// log.debug("Post Url:" + totalURL);
		// URL url = new URL(totalURL);
		// HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// con.setDoInput(true);
		// con.setDoOutput(true);
		// con.setAllowUserInteraction(false);
		// con.setUseCaches(false);
		// con.setRequestMethod("POST");
		// con.setConnectTimeout(30000);//设置连接主机超时 三十秒等待时间
		// con.setReadTimeout(30000);//设置从主机读取数据超时 三十秒等待时间
		// con.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		// con.getOutputStream().write(params.getBytes());
		// con.getOutputStream().flush();
		// con.getOutputStream().close();
		// InputStream is=con.getInputStream();
		// BufferedReader bin = new BufferedReader(new
		// InputStreamReader(is,"UTF-8"), SIZE);
		// while (true) {
		// String line = bin.readLine();
		// if (line == null) {
		// break;
		// } else {
		// sbuffer.append(line);
		// }
		// }
		// if (bin!=null) {
		// bin.close();
		// }
		// if (is!=null) {
		// is.close();
		// }
		// if (con!=null) {
		// con.disconnect();
		// }
		String s = sendPost(totalURL, params);
		sbuffer.append(s);
		return sbuffer.toString();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @author yangxiaoyong
	 * @version 创建时间：2014年8月26日 下午5:04:37 参考 www.sql8.net
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			log.error("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}

	/**
	 * 
	 * @param map
	 *            Map
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	private static String getUrl(Map map) {
		if (null == map || map.keySet().size() == 0) {
			return "";
		}
		StringBuffer url = new StringBuffer();
		Set keys = map.keySet();
		for (Iterator i = keys.iterator(); i.hasNext();) {
			String key = String.valueOf(i.next());
			if (map.containsKey(key)) {
				Object val = map.get(key);
				String str = val != null ? val.toString() : "";
				try {
					str = URLEncoder.encode(str, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				// System.out.print(key+":"+val+"\t");
				url.append(key).append("=").append(str).append(URL_PARAM_CONNECT_FLAG);
			}
		}
		String strURL = "";
		strURL = url.toString();
		if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
			strURL = strURL.substring(0, strURL.length() - 1);
		}
		return (strURL);
	}

	/**
	 * 
	 * @param strUrl
	 * @param map
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	public static String URLGetNew(String strUrl, Map map) throws IOException {
		String strTotalURL = "";
		StringBuffer result = new StringBuffer();
		String params = URLEncoder.encode(getUrl(map), "UTF-8");
		//判断URL末尾是不是“?”
		if (!strUrl.endsWith("?")) {
			strTotalURL = StringUtils.isBlank(params) ? strUrl : (strUrl + "?" + params);
		} else {
			strTotalURL = StringUtils.isBlank(params) ? strUrl : (strUrl + URLEncoder.encode(URL_PARAM_CONNECT_FLAG, "UTF-8") + params);
		}
		log.debug("Get Url:" + strTotalURL);
		
		URL url = new URL(strTotalURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setFollowRedirects(true);
		InputStream is = con.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"), SIZE);
		while (true) {
			String line = in.readLine();
			if (line == null) {
				break;
			} else {
				result.append(line);
			}
		}
		if (in != null) {
			in.close();
		}
		if (is != null) {
			is.close();
		}
		if (con != null) {
			con.disconnect();
		}
		return result.toString();
	}



	/**
	 * POST METHOD
	 * 
	 * @param strUrl
	 *            String
	 * @param map
	 *            Map
	 * @throws IOException
	 * @return List
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String URLPostForDt(String strUrl, Map map) throws IOException {
		// strUrl="http://localhost:8080/front/ccr/t.do";
		String params = "";
		params = getUrl(map);
		String totalURL = strUrl;
		StringBuffer sbuffer = new StringBuffer();

		// if (strUrl.indexOf("?") == -1) {
		// totalURL = strUrl + (StringUtils.isBlank(params)?"":"?" + params);
		// } else {
		// totalURL = strUrl + (StringUtils.isBlank(params)?"":"&" + params);
		// }
		// log.debug("Post Url:" + totalURL);
		// URL url = new URL(totalURL);
		// HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// con.setDoInput(true);
		// con.setDoOutput(true);
		// con.setAllowUserInteraction(false);
		// con.setUseCaches(false);
		// con.setRequestMethod("POST");
		// con.setConnectTimeout(30000);//设置连接主机超时 三十秒等待时间
		// con.setReadTimeout(30000);//设置从主机读取数据超时 三十秒等待时间
		// con.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		// con.getOutputStream().write(params.getBytes());
		// con.getOutputStream().flush();
		// con.getOutputStream().close();
		// InputStream is=con.getInputStream();
		// BufferedReader bin = new BufferedReader(new
		// InputStreamReader(is,"UTF-8"), SIZE);
		// while (true) {
		// String line = bin.readLine();
		// if (line == null) {
		// break;
		// } else {
		// sbuffer.append(line);
		// }
		// }
		// if (bin!=null) {
		// bin.close();
		// }
		// if (is!=null) {
		// is.close();
		// }
		// if (con!=null) {
		// con.disconnect();
		// }
		String s = sendPostForDt(totalURL, params);
		sbuffer.append(s);
		return sbuffer.toString();
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 * @author yangxiaoyong
	 * @version 创建时间：2014年8月26日 下午5:04:37 参考 www.sql8.net
	 */
	public static String sendPostForDt(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();

			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			log.error("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}
	
	/**
	 * POST METHOD
	 * 
	 * @param strUrl
	 *            String
	 * @param params
	 *            params
	 * @throws IOException
	 */
	public static String jsonPost(String strUrl, String params) throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setAllowUserInteraction(false);
		con.setUseCaches(false);
		con.setRequestMethod("POST");
		con.setConnectTimeout(30000);//设置连接主机超时 三十秒等待时间
		con.setReadTimeout(30000);//设置从主机读取数据超时 三十秒等待时间
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		con.getOutputStream().write(params.getBytes("utf-8"));
		con.getOutputStream().flush();
		con.getOutputStream().close();
		InputStream is=con.getInputStream();
		BufferedReader bin = new BufferedReader(new InputStreamReader(is,"UTF-8"), SIZE);
		StringBuffer sbuffer=new StringBuffer();
		while (true) {
			String line = bin.readLine();
			if (line == null) {
				break;
			} else {
				sbuffer.append(line);
			}
		}
		if (bin!=null) {
			bin.close();
		}
		if (is!=null) {
			is.close();
		}
		if (con!=null) {
			con.disconnect();
		}
		return sbuffer.toString();
	}

}
