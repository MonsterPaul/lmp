package com.zjht.jfmall.weChat.helper;

import com.zjht.jfmall.util.MessageDigestHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 微信请求参数处理类
 * @author vip
 *
 */
public class RequestHandler {


    /// <summary>
    /// 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名
    /// </summary>
    /// <param name="key">参数名</param>
    /// <param name="value">参数值</param>
    /// key和value通常用于填充最后一组参数
    /// <returns></returns>
    public static String createMd5Sign(String key, Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        List<String> akeys = new ArrayList<String>(parameters.keySet());
        Collections.sort(akeys);
        for (String k : akeys) {
            String v = parameters.get(k);
            if (null != v && "".compareTo(v) != 0
                && "sign".compareTo(k) != 0 && "key".compareTo(k) != 0) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key" + "=" + key);//最后拼接秘钥
        System.out.println("签名拼接的字符串" + sb.toString());
        return MessageDigestHelper.encodeByMD5(sb.toString(),null).toUpperCase();
    }

    /// <summary>
    /// 输出XML
    /// </summary>
    /// <returns></returns>
    public static String parseXML(Map<String,String> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (String k : parameters.keySet()) {
            String v = parameters.get(k);
            sb.append("<" + k + "><![CDATA[" + v + "]]></" + k + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }
}