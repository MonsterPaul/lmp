package com.zjht.jfmall.marketing.bean;


/**
 *用户登陆返回结果
 */
public class LoginResp extends ResultDesc {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2627573562752286323L;
	
	//登陆返回的token
	private String token;
	//token的有效期限
	private int expireIn;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getExpireIn() {
		return expireIn;
	}
	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}
	
	
	
}
