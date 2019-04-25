package com.zjht.jfmall.marketing.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
* @author lu
* @version 创建时间：2016年5月25日 下午4:17:40 
* 查询用户注册状态返回
*/
public class QueryUserRegeistResp extends ResultDesc{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1345332532453L;

	
	private Integer result;
	
	private String info;

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getInfo() {
		
		
		
		if(this.getResult()==1){
			SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Long time=new Long(info);
			String date = format.format(new Date(time*1000L));
			return date;
		}else{
			return info;
		}
		
		
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "QueryUserRegeistResp [result=" + result + ", info=" + info + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
	
}
