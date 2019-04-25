package com.zjht.jfmall.marketing.common;

import com.zjht.jfmall.util.PropertyUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GlobalConfig {

	private static Log log = LogFactory.getLog(GlobalConfig.class);
	/**
	 * appid配置文件中的键值
	 */
	public static final String APPID="gateway.appId";
	/**
	 * appKey配置文件中的键值
	 */
	public static final String APPKEY="gateway.appKey";
	/**
	 * 接口url配置文件中的键值
	 */
	public static final String APPURL="gateway.interface.url";
	
	/**
	 * 查询用户注册状态接口
	 * 接口url配置文件中的键值
	 */
	public static final String QUERYUSERURL="queryUserRegeist.interface.url";
	/**
	 * 接口私钥文件名配置文件中的键值
	 */
	public static final String APPSIGNFILE="gateway.privateKeyFileName";
	/**
	 * 营销平台油我发起的红包产品id列表，多个产品id使用英文逗号隔开
	 */
	public static final String REDENVELOPE_PRODUCT_IDS="redenvelope.product.ids";
	/**
	 * 营销平台油我发起的红包产品最多使用个数
	 */
	public static final String REDENVELOPE_USE_MAX="redenvelope.use.max";
	/**
	 * 接口私钥密码配置文件中的键值
	 */
	public static final String APPPWD="gateway.privateKeyPwd";
	
	public static String getConfigValue(String key) {
		String value=null;
		try {
			value= PropertyUtil.getPropertyValue("marketing", key);
		} catch (Exception e) {
			log.error("获取营销平台配置文件键值失败，异常详情：",e);
		}
		return value;
	}
}
