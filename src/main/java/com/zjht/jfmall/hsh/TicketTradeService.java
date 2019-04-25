package com.zjht.jfmall.hsh;



import com.alibaba.fastjson.JSONException;
import com.zjht.jfmall.hsh.request.TicketTradeReq;
import com.zjht.jfmall.hsh.response.QueryAssistCodeDetailResp;
import com.zjht.jfmall.hsh.response.TicketTradeResp;

import java.io.IOException;

public interface TicketTradeService {
	/**
	 * 统一发码接口
	 * @param ticketTradeReq
	 * @return
	 */
	public TicketTradeResp ticketTrade(TicketTradeReq ticketTradeReq) throws JSONException, IOException;

	/**
	 * 查询券码明细
	 * @param requestType
	 * @param assistCode
	 * @throws JSONException
	 */
	public QueryAssistCodeDetailResp queryAssistCodeDetail(String requestType, String assistCode)throws JSONException, IOException;
}
