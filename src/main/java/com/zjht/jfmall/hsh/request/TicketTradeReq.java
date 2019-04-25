package com.zjht.jfmall.hsh.request;

import java.io.Serializable;
import java.util.List;

public class TicketTradeReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393461257484382239L;
	public TicketTradeReq(){}
	/**
	 * @param send
	 * @param requestType
	 * @param qty
	 * @param orderNo
	 * @param subList
	 */
	public TicketTradeReq(String send, String requestType, String qty,
                          String orderNo, List<TicketTradeReqItem> subList) {
		this.send = send;
		this.requestType = requestType;
		this.qty = qty;
		this.orderNo = orderNo;
		this.subList = subList;
	}
	private String send;
	private String requestType;
	private String qty;
	private String orderNo;
	private List<TicketTradeReqItem> subList;
	public String getSend() {
		return send;
	}
	public void setSend(String send) {
		this.send = send;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<TicketTradeReqItem> getSubList() {
		return subList;
	}
	public void setSubList(List<TicketTradeReqItem> subList) {
		this.subList = subList;
	}
}
