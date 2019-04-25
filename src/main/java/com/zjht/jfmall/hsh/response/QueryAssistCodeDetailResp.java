package com.zjht.jfmall.hsh.response;

import java.util.Date;

public class QueryAssistCodeDetailResp {
	
	private String status; //0未使用；1已转让；2已消费；4部分消费；5=早期2014年5月之前商家转让的辅助码标记 6未激活;7冻结;8过期码重发,此状态不可使用。
	private String assistCode;//券码
	private String statusDesc;//状态描述
	private Double parPrice;//面额 元
	private String couponName;//券码名称
	private String couponCode;//产品编码
	private Date endTime;//结束时间
	private String shopList;//券码配置门店
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssistCode() {
		return assistCode;
	}
	public void setAssistCode(String assistCode) {
		this.assistCode = assistCode;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public Double getParPrice() {
		return parPrice;
	}
	public void setParPrice(Double parPrice) {
		this.parPrice = parPrice;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getShopList() {
		return shopList;
	}
	public void setShopList(String shopList) {
		this.shopList = shopList;
	}
	
	
}
