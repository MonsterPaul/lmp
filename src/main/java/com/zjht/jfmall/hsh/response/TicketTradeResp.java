package com.zjht.jfmall.hsh.response;

import java.io.Serializable;
import java.util.List;

public class TicketTradeResp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2842894768920845926L;
	private String respCode;
	private String respMsg;
	private String orderNo;
	private List<TicketTradeRespItem> subList;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<TicketTradeRespItem> getSubList() {
		return subList;
	}
	public void setSubList(List<TicketTradeRespItem> subList) {
		this.subList = subList;
	}
	@Override
	public String toString() {
		return "TicketTradeResp [respCode=" + respCode + ", respMsg=" + respMsg + ", orderNo=" + orderNo + ", subList="
				+ subList + "]";
	}
	
	
	
}
