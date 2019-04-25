package com.zjht.jfmall.marketing.bean;

/**
 * 接口响应通用信息
 * @author lijunjie
 * @since 2015-1-19 11:02:29
 *
 */
public class ResultDesc implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7948423193482279988L;
	
	public ResultDesc(){}
	

	/**
	 * @param errcode
	 * @param errmsg
	 */
	public ResultDesc(Long errcode, String errmsg) {
		super();
		this.errcode = errcode;
		this.errmsg = errmsg;
	}


	private Long errcode;
	
	private String errmsg;

	public Long getErrcode() {
		return errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrcode(Long errcode) {
		this.errcode = errcode;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	@Override
	public String toString() {
		return "ResultDesc [errcode=" + errcode + ", errmsg=" + errmsg + "]";
	}
	
}
