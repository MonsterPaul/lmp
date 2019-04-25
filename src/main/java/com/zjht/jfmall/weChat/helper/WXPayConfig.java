package com.zjht.jfmall.weChat.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信支付信息
 * @author 作者xujianfeng 
 * @date 创建时间：2017年1月3日 上午10:07:51
 */
@Component
public class WXPayConfig {
	
	@Value("${wxpay.notify_url}")
	private String notifyUrl;
	@Value("${wxpay.unifiedorder_url}")
	private String unifiedorderUrl;
	@Value("${wxpay.refund_url}")
	private String refundUrl;
	@Value("${wxpay.orderquery_url}")
	private String orderqueryUrl;
	@Value("${wxpay.appid}")
	private String appid;
	@Value("${wxpay.appsecret}")
	private String appsecret;
	@Value("${wxpay.mch_id}")
	private String mchId;
	@Value("${wxpay.key}")
	private String key;
	/** S  微信现金红包接口相关   */
	@Value("${redpack.wxpay.url}")
	private String redpackUrl;
	@Value("${redpack.wxpay.cert}")
	private String redpackCert;
	@Value("${redpack.wxpay.client_ip}")
	private String redpackClientIp;
	/** E  微信现金红包接口相关   */
	/** S 允许调用微信红包接口积分账户和秘钥 */
	@Value("${redpack.rsjf.account}")
	private String redpackAccount;//人寿积分项目账户
	@Value("${redpack.rsjf.pass}")
	private String redpackPass;//人寿积分项目秘钥
	@Value("${redpack.query.wxpay.url}")
	private String redpackQueryUrl;
	/** E 允许调用微信红包接口积分账户和秘钥 */
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getUnifiedorderUrl() {
		return unifiedorderUrl;
	}
	public void setUnifiedorderUrl(String unifiedorderUrl) {
		this.unifiedorderUrl = unifiedorderUrl;
	}
	public String getRefundUrl() {
		return refundUrl;
	}
	public void setRefundUrl(String refundUrl) {
		this.refundUrl = refundUrl;
	}
	public String getOrderqueryUrl() {
		return orderqueryUrl;
	}
	public void setOrderqueryUrl(String orderqueryUrl) {
		this.orderqueryUrl = orderqueryUrl;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
	public String getMchId() {
		return mchId;
	}
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRedpackUrl() {
		return redpackUrl;
	}
	public void setRedpackUrl(String redpackUrl) {
		this.redpackUrl = redpackUrl;
	}
	public String getRedpackCert() {
		return redpackCert;
	}
	public void setRedpackCert(String redpackCert) {
		this.redpackCert = redpackCert;
	}
	public String getRedpackClientIp() {
		return redpackClientIp;
	}
	public void setRedpackClientIp(String redpackClientIp) {
		this.redpackClientIp = redpackClientIp;
	}
	public String getRedpackAccount() {
		return redpackAccount;
	}
	public void setRedpackAccount(String redpackAccount) {
		this.redpackAccount = redpackAccount;
	}
	public String getRedpackPass() {
		return redpackPass;
	}
	public void setRedpackPass(String redpackPass) {
		this.redpackPass = redpackPass;
	}

	public String getRedpackQueryUrl() {
		return redpackQueryUrl;
	}

	public void setRedpackQueryUrl(String redpackQueryUrl) {
		this.redpackQueryUrl = redpackQueryUrl;
	}
}
