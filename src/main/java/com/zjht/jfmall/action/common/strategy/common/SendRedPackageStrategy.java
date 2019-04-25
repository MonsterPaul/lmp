package com.zjht.jfmall.action.common.strategy.common;

import com.alibaba.fastjson.JSONObject;
import com.zjht.jfmall.action.common.strategy.AbstractStrategy;
import com.zjht.jfmall.dao.OrderExchangeDao;
import com.zjht.jfmall.dao.ProductDao;
import com.zjht.jfmall.entity.OrderExchange;
import com.zjht.jfmall.entity.Product;
import com.zjht.jfmall.enums.OrderExchangeDealStatus;
import com.zjht.jfmall.enums.OrderExchangeStatus;
import com.zjht.jfmall.service.OrderExchangeService;
import com.zjht.jfmall.service.ProductService;
import com.zjht.jfmall.util.DateTimeUtils;
import com.zjht.jfmall.weChat.helper.WXPayConfig;
import com.zjht.jfmall.weChat.request.RedPackageRequest;
import com.zjht.jfmall.weChat.response.RedPackageResponse;
import com.zjht.jfmall.weChat.sevice.WeChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 发送红包
 *
 * @author caozhaokui
 * @create 2018-01-07 11:44
 */
public class SendRedPackageStrategy extends AbstractStrategy {
    @Autowired
    private WeChatService weChatService;
    @Autowired
    private WXPayConfig wxPayConfig;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderExchangeDao orderExchangeDao;

    private Logger log = LoggerFactory.getLogger(SendRedPackageStrategy.class);
    @Override
    public void execute(OrderExchange order) {
        if(order == null){
            log.error("订单为空，不执行策略");
            return ;
        }
        Product product = productDao.selectById(order.getProductId());
        if(product == null){
            log.error("订单产品不存在，不执行策略");
        }
        //订单状态为待发货
        order.setStatus(OrderExchangeStatus.WAIT_SHIPMENTS.getStatus());

        RedPackageRequest sendRequest = new RedPackageRequest();
        sendRequest.setRedpackAccount(wxPayConfig.getRedpackAccount());
        sendRequest.setAmount(product.getSalePrice().multiply(new BigDecimal(100)).setScale(0).toString());
        sendRequest.setOpenId(order.getOpenid());
        sendRequest.setBillno(order.getOrderNo());
        sendRequest.setName("人寿");
        sendRequest.setWishing("恭喜你，领取人寿红包");
        sendRequest.setActName("人寿积分兑换");
        sendRequest.setRemark("人寿积分兑换");
        sendRequest.setNum(String.valueOf(order.getTotal()));
        RedPackageResponse redPackageResponse = (RedPackageResponse)weChatService.sendRedPacket(sendRequest);
        if(redPackageResponse.isSuccess()){
            order.setDealStatus(OrderExchangeDealStatus.PROCESSED.getStatus());
            order.setStatus(OrderExchangeStatus.FINISH.getStatus());
        }else{
            order.setDealStatus(OrderExchangeDealStatus.FAIl.getStatus());
        }
        JSONObject respObj = redPackageResponse.getData();
        //微信返回状态为处理中，将订单状态更新为已处理，等待自动任务回查
        if(respObj != null ){
            if("PROCESSING".equalsIgnoreCase(respObj.getString("err_code"))){
                order.setDealStatus(OrderExchangeDealStatus.PROCESSED.getStatus());
            }
            order.setRespCode(respObj.getString("result_code"));
            order.setRespMsg(respObj.toString());
        }
        order.setDealType(OrderExchange.HONGBAO);
        orderExchangeDao.updateByIdSelective(order);
    }
}
