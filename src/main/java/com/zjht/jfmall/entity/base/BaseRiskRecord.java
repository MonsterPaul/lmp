package com.zjht.jfmall.entity.base;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_risk_record")
public class BaseRiskRecord implements java.io.Serializable{
	
	private static final long serialVersionUID = -3984133406673636834L;

	@Id
    private String id;
	
	@Column(name = "trans_id")
	private String transId;//商户请求订单号
	
	@Column(name = "trade_no")
	private String tradeNo;//新颜交易响应流水号
	
	@Column(name = "userId")
	private String userId;
	
	@Column(name = "id_no")
	private String idNo;
	
	@Column(name = "id_name")
	private String idName;
	
	@Column(name = "success")
	private String success;
	
	@Column(name = "errorCode")
	private String errorCode;
	
	@Column(name = "errorMsg")
	private String errorMsg;
	
	@Column(name = "tran_code")
	private String code;
	
	@Column(name = "tran_data")
	private String data;
	
	@Column(name = "tran_desc")
	private String desc;
	
	@Column(name = "fee")
	private String fee;
	
	@Column(name = "versions")
	private String versions;
	
	@Column(name = "create_time")
	private String createTime; 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getVersions() {
		return versions;
	}
	public void setVersions(String versions) {
		this.versions = versions;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
