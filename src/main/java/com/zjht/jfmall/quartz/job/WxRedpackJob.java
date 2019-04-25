package com.zjht.jfmall.quartz.job;

import com.alibaba.fastjson.JSONObject;
import com.zjht.jfmall.dao.OrderExchangeDao;
import com.zjht.jfmall.entity.OrderExchange;
import com.zjht.jfmall.enums.OrderExchangeDealStatus;
import com.zjht.jfmall.enums.OrderExchangeStatus;
import com.zjht.jfmall.weChat.helper.WXPayConfig;
import com.zjht.jfmall.weChat.request.QueryRedPackageRequest;
import com.zjht.jfmall.weChat.response.RedPackageResponse;
import com.zjht.jfmall.weChat.sevice.WeChatService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.util.Iterator;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-02-28 17:12
 */
public class WxRedpackJob {
    @Autowired
    private OrderExchangeDao orderExchangeDao;
    @Autowired
    private WeChatService weChatService;
    @Autowired
    private WXPayConfig wxPayConfig;
    //日志记录
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void queryRedpackInfo(){
        log.info("红包状态回查-----开始");
        //查询需要回查红包状态的订单
        OrderExchange orderExchange = new OrderExchange();
        orderExchange.setDealType(OrderExchange.HONGBAO);
        orderExchange.setDealStatus(OrderExchangeDealStatus.PROCESSED.getStatus());
        List<OrderExchange> orderList = orderExchangeDao.selectListByObject(orderExchange);
        for(Iterator<OrderExchange> iterator = orderList.iterator();iterator.hasNext();){
            OrderExchange order = iterator.next();
            QueryRedPackageRequest queryRedPackageRequest = new QueryRedPackageRequest();
            queryRedPackageRequest.setRedpackAccount(wxPayConfig.getRedpackAccount());
            String billNo = null;
            JSONObject respMsg = JSONObject.parseObject(order.getRespMsg());
            if(StringUtils.isNotBlank(order.getRespMsg())){
                billNo = respMsg.getString("mch_billno");
            }else{
                billNo = order.getOrderNo();
            }
            queryRedPackageRequest.setMchBillno(billNo);
            RedPackageResponse redPackageResponse = weChatService.queryRedPacket(queryRedPackageRequest);
            if(redPackageResponse.isSuccess()){
                JSONObject resultData = redPackageResponse.getData();
                //SENDING:发放中,SENT:已发放待领取,FAILED:发放失败,RECEIVED:已领取,RFUND_ING:退款中,REFUND:已退款
                String status = resultData.getString("status");
                respMsg.put("status", status);
                respMsg.put("detail_id", resultData.getString("detail_id"));
                order.setRespMsg(respMsg.toJSONString());
                //如果是最终状态，就更新为状态已回查
                if("FAILED".equalsIgnoreCase(status)||"RECEIVED".equalsIgnoreCase(status)||"REFUND".equalsIgnoreCase(status)){
                    order.setDealStatus(OrderExchangeDealStatus.CHECKED.getStatus());
                    order.setStatus(OrderExchangeStatus.FINISH.getStatus());
                }
                orderExchangeDao.updateByIdSelective(order);
            }
        }
        log.info("红包状态回查-----结束");
    }
}
