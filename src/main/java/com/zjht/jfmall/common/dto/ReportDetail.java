package com.zjht.jfmall.common.dto;

import java.util.Map;

/**
 * 业务响应参数
 * @author lqf
 *
 */
public class ReportDetail {
	private String code;
	private String   desc; 
	private String   trans_id; 
	private String   trade_no; 
	private String   fee; 
	private String   id_no; 
	private String   id_name;  
	private String   versions;
	private Map result_detail;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTrans_id() {
		return trans_id;
	}

	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getId_no() {
		return id_no;
	}

	public void setId_no(String id_no) {
		this.id_no = id_no;
	}

	public String getId_name() {
		return id_name;
	}

	public void setId_name(String id_name) {
		this.id_name = id_name;
	}

	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}

	public Map getResult_detail() {
		return result_detail;
	}

	public void setResult_detail(Map result_detail) {
		this.result_detail = result_detail;
	}
	
	
	
}
