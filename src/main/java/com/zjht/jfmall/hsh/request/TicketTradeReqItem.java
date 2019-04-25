package com.zjht.jfmall.hsh.request;

import java.io.Serializable;

public class TicketTradeReqItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 823328031298174672L;
	public TicketTradeReqItem(){}
	/**
	 * @param mobile
	 * @param couponCode
	 * @param quantity
	 */
	public TicketTradeReqItem(String mobile, String couponCode, String quantity) {
		this.mobile = mobile;
		this.couponCode = couponCode;
		this.quantity = quantity;
	}
	private String mobile;
	private String couponCode; //优惠券代码
	private String quantity; //数量
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
}
