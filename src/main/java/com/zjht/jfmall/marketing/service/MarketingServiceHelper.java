package com.zjht.jfmall.marketing.service;

import com.alibaba.fastjson.JSONObject;

import com.zjht.jfmall.marketing.common.GlobalConfig;
import com.zjht.jfmall.marketing.utils.CertificateUtil;
import com.zjht.jfmall.marketing.utils.ExStringUtils;
import com.zjht.jfmall.util.MessageDigestHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class MarketingServiceHelper {
	
    private static final Logger log = LoggerFactory.getLogger(MarketingServiceHelper.class);
    // appId 营销账户平台分配
    private static final String appId = GlobalConfig.getConfigValue(GlobalConfig.APPID);
    // appKey 营销账户平台分配
    private static final String appKey = GlobalConfig.getConfigValue(GlobalConfig.APPKEY);
    /**
     * 生成app的JSONObject对象
     * @return
     */
	public static JSONObject getAppJsonObject() {
		// 时间戳
		String timeStamp = String.valueOf(System.currentTimeMillis());
		// 随机码
		String nonce = ExStringUtils.getVerifyCode(8);
		//（appId,appKey,nonce,timeStamp）参数值排序：
		String[] values = {appId,appKey,nonce,timeStamp};
		Arrays.sort(values);
		
		String sortStr = ExStringUtils.concat(values,"");
		log.info("app.signature 拼装前参数为 ：" + ExStringUtils.concat(values,"|"));
		String signature = MessageDigestHelper.encodeByMD5(sortStr,null);
		log.info("app.signature MD5为 ："+ signature);
		JSONObject app = new JSONObject();
		app.put("appId", appId);
		app.put("timeStamp", timeStamp);
		app.put("nonce", nonce);
		app.put("signature", signature);
		return app;
	}
	/**
	 * 生成 tsig的JSONObject对象
	 * @param orderArray，用于生成MD5签名值的字符串数组
	 * @return
	 */
	public static JSONObject getTsigJsonObject(String[] orderArray) {
		//按照字典顺序排序
		Arrays.sort(orderArray);
		String orderSortStr = ExStringUtils.concat(orderArray,"");
		log.info("tsig.orderMD5  拼装前参数: " + orderSortStr);
		//生成order串的MD5码
		String orderMD5 = MessageDigestHelper.encodeByMD5(orderSortStr,null);
		// 时间戳
		String timeStamp = String.valueOf(System.currentTimeMillis());
		// 随机码
		String nonce = ExStringUtils.getVerifyCode(8);
		//tsig参数拼装
		JSONObject tsigJsonObject =new JSONObject();
		//（appId,appKey,nonce,timeStamp）参数值排序：
		String[] tsigValues = {orderMD5,appId,timeStamp,nonce};
		Arrays.sort(tsigValues);
		String tsigSortStr = ExStringUtils.concat(tsigValues,"");
//		log.info("tsig.signature 拼装前参数: " + ExStringUtils.concat(tsigValues,"|"));
		String tsigSignature = CertificateUtil.encodeMsgByPrivateKey(GlobalConfig.getConfigValue(GlobalConfig.APPSIGNFILE), tsigSortStr , GlobalConfig.getConfigValue(GlobalConfig.APPPWD));
		log.info("tsig.signature 私钥签名后的值为: "+tsigSignature);
		tsigJsonObject.put("orderMD5", orderMD5);
		tsigJsonObject.put("signature", tsigSignature);
		tsigJsonObject.put("timeStamp", timeStamp);
		tsigJsonObject.put("nonce", nonce);
		return tsigJsonObject;
	}
}
