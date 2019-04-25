package com.zjht.jfmall.hsh.response;

import java.io.Serializable;
import java.util.Date;

public class TicketTradeRespItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7536333263834403004L;
	private String respCode;
	private String respMsg;
	private String assisCode;
	private String couponCode;
	private String orderSn;
	private String couponName;
	private Double parPrice;
	private String username;
	private String mobile;
	private Date endTime;
	
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getAssisCode() {
		return assisCode;
	}
	public void setAssisCode(String assisCode) {
		this.assisCode = assisCode;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	
	public Double getParPrice() {
		return parPrice;
	}
	public void setParPrice(Double parPrice) {
		this.parPrice = parPrice;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "TicketTradeRespItem{" +
				"respCode='" + respCode + '\'' +
				", respMsg='" + respMsg + '\'' +
				", assisCode='" + assisCode + '\'' +
				", couponCode='" + couponCode + '\'' +
				", orderSn='" + orderSn + '\'' +
				", couponName='" + couponName + '\'' +
				", parPrice=" + parPrice +
				", username='" + username + '\'' +
				", mobile='" + mobile + '\'' +
				'}';
	}
}
