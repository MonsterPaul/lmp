package com.zjht.jfmall.marketing.bean;


import com.zjht.jfmall.marketing.bean.ResultDesc;

/**
 *用户是否拥有某属性
 */
public class TokenValidateResp extends ResultDesc {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3336675610451390777L;
	/**
	 * token拥有的用户的手机号
	 */
	private String mobileNum;
	/**
	 * 用户姓名
	 */
	private String name;
	
	public String getMobileNum() {
		return mobileNum;
	}
	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
