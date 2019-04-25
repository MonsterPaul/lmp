package com.zjht.jfmall.marketing.utils;


import com.zjht.jfmall.marketing.common.GlobalConfig;
import com.zjht.jfmall.util.Base64Helper;
import com.zjht.jfmall.util.PropertyUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Enumeration;

/**
 * 营销平台证书加密文本工具类
 * @author lijunjie
 *
 */
public class CertificateUtil {
	private static Log log = LogFactory.getLog(CertificateUtil.class);
	/**
	 * 
	 * 通过证书私钥加密文本
	 * @param orgFilePath
	 * @param infoMsg
	 * @param privatePwd
	 * @return
	 */
	public static String encodeMsgByPrivateKey(String orgFilePath, String infoMsg, String privatePwd) {
		String retStr = "";
		try {
			KeyStore ks = KeyStore.getInstance("PKCS12");
			String filePath = "/config/marketing" + "/" + orgFilePath;
			InputStream ksfis = PropertyUtil.class.getResourceAsStream(filePath);
			BufferedInputStream ksbufin = new BufferedInputStream(ksfis);
			char[] keyPwd = privatePwd.toCharArray();
			ks.load(ksbufin, keyPwd);
			ksbufin.close();
			Enumeration<String> enum1 = ks.aliases();
			String keyAlias = null;
			if (enum1.hasMoreElements()) {
				keyAlias = enum1.nextElement();
			}
			// 私钥
			PrivateKey priK = (PrivateKey) ks.getKey(keyAlias, keyPwd);
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(priK);
			signature.update(infoMsg.getBytes("utf-8"));
			retStr= Base64Helper.encodeToString(signature.sign());
			log.info("生成营销平台签名成功");
		} catch (java.lang.Exception e) {
			log.error(e.getMessage());
		}
		return retStr;
	}
	
	public static void main(String[] args){
		String filePath = GlobalConfig.getConfigValue(GlobalConfig.APPSIGNFILE);
		String pwd= GlobalConfig.getConfigValue(GlobalConfig.APPPWD);
		String sign=encodeMsgByPrivateKey(filePath,"{kekeke=keieiei}",pwd);
		System.out.println(sign);
	}
}
