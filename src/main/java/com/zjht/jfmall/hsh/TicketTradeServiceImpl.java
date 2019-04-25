package com.zjht.jfmall.hsh;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.zjht.jfmall.hsh.helper.ApiConfig;
import com.zjht.jfmall.hsh.request.TicketTradeReq;
import com.zjht.jfmall.hsh.response.QueryAssistCodeDetailResp;
import com.zjht.jfmall.hsh.response.TicketTradeResp;
import com.zjht.jfmall.util.HttpUtils;
import com.zjht.jfmall.util.PropertyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 旧汇生活通用发码请用这个工具类方法发送(湖北地区已确认停用-2017.7.10)-2017-07-05跟汇生活技术确认
 * 不要用TicketForSHService这个工具类方法发送
 * @author lijunjie
 *
 */
public class TicketTradeServiceImpl implements TicketTradeService {
	private static final Properties prop = PropertyUtil.getPropertes("", "esb");
	private static final String appNo = prop.getProperty(ApiConfig.APPNO_KEY);
	private static final String key = prop.getProperty(ApiConfig.APP_KEY);
	private static final String ver = prop.getProperty(ApiConfig.VER_KEY);
	private static final String url = prop.getProperty(ApiConfig.APPURL_KEY);
	private static final Logger log = LoggerFactory.getLogger(TicketTradeServiceImpl.class);
	@Override
	public TicketTradeResp ticketTrade(TicketTradeReq ticketTradeReq)  throws JSONException, IOException {
		TicketTradeResp ticketTradeResp=new TicketTradeResp();
		/** 参数判断 **/
		if (StringUtils.isBlank(ticketTradeReq.getOrderNo())) {
			ticketTradeResp.setRespCode("-1");
			ticketTradeResp.setRespMsg("订单号不能为空");
			return ticketTradeResp;
		}
		if (StringUtils.isBlank(ticketTradeReq.getRequestType())) {
			ticketTradeResp.setRespCode("-1");
			ticketTradeResp.setRespMsg("渠道类型不能为空！");
			return ticketTradeResp;
		}
		if (StringUtils.isBlank(ticketTradeReq.getQty())) {
			ticketTradeResp.setRespCode("-1");
			ticketTradeResp.setRespMsg("商品发码总量不能为空！");
			return ticketTradeResp;
		}
		if (StringUtils.isBlank(ticketTradeReq.getSend())) {
			ticketTradeResp.setRespCode("-1");
			ticketTradeResp.setRespMsg("是否发送短信!");
			return ticketTradeResp;
		}
		if (!(ticketTradeReq.getSubList()!=null&&ticketTradeReq.getSubList().size()>0)) {
			ticketTradeResp.setRespCode("-1");
			ticketTradeResp.setRespMsg("商品属性信息和接收手机号码为空！");
			return ticketTradeResp;
		}
		String service="trade.ticketTrade";//服务名
		JSONObject contentJson = JSONObject.parseObject(JSONObject.toJSONString(ticketTradeReq));
		/** msg参数结构构造 **/
		JSONObject json = new JSONObject();
		json.put("content", contentJson);
		Map<String, String> map = new HashMap<String, String>();
		map.put("appNo", appNo);
		map.put("service", service);
		map.put("ver", ver);
		map.put("msg", json.toString());
		map.put("sign", ApiConfig.genSign(key, json.toString()));
		StringBuffer strUrl = new StringBuffer(url);
		strUrl.append(service).append("/").append(ver).append("/");
		log.info("trade.ticketTrade服务请求参数：" + JSONObject.toJSONString(map));
		String resultJson = HttpUtils.URLPost(strUrl.toString(), map);
		log.info("trade.ticketTrade服务响应内容："+resultJson);
		JSONObject response = JSONObject.parseObject(resultJson);
		ticketTradeResp = JSONObject.parseObject(response.get("content").toString(), TicketTradeResp.class);
		return ticketTradeResp;
	}

	
	@Override
	public QueryAssistCodeDetailResp queryAssistCodeDetail(String requestType, String assistCode) throws JSONException, IOException {
		// TODO Auto-generated method stub
		QueryAssistCodeDetailResp queryAssistCodeDetailResp=new QueryAssistCodeDetailResp();
		if (StringUtils.isBlank(requestType)) {
			queryAssistCodeDetailResp.setStatus("-1");
			queryAssistCodeDetailResp.setStatusDesc("请求通道不能为空");
			return queryAssistCodeDetailResp;
		}
		if (StringUtils.isBlank(assistCode)) {
			queryAssistCodeDetailResp.setStatus("-1");
			queryAssistCodeDetailResp.setStatusDesc("券码不能为空");
			return queryAssistCodeDetailResp;
		}
		String service="trade.queryAssistCodeDetail";//服务名
		JSONObject contentJson = new JSONObject();
        contentJson.put("requestType", requestType);//请求通道 固定
        contentJson.put("assistCode", assistCode);//券码号
		JSONObject json = new JSONObject();
		json.put("content", contentJson);
		Map<String, String> map = new HashMap<String, String>();
		map.put("appNo", appNo);
		map.put("service", service);
		map.put("ver", ver);
		map.put("msg", json.toString());
		map.put("sign", ApiConfig.genSign(key, json.toString()));
		StringBuffer strUrl = new StringBuffer(url);
		strUrl.append(service).append("/").append(ver).append("/");
		log.info("trade.queryAssistCodeDetail服务请求参数：" + JSONObject.toJSONString(map));
		String resultJson = HttpUtils.URLPost(strUrl.toString(), map);
		log.info("trade.queryAssistCodeDetail服务响应内容："+resultJson);
		JSONObject response = JSONObject.parseObject(resultJson);

		if("0000".equals(response.get("state"))){
			queryAssistCodeDetailResp=JSONObject.parseObject(response.get("content").toString(), QueryAssistCodeDetailResp.class);
		}else{
			queryAssistCodeDetailResp.setStatus("-11");
			queryAssistCodeDetailResp.setStatusDesc("网络通讯异常");
		}
		return queryAssistCodeDetailResp;
	}

	

	
}
