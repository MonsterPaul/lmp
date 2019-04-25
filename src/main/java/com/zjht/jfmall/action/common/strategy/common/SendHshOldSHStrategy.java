package com.zjht.jfmall.action.common.strategy.common;

import com.google.gson.Gson;
import com.zjht.jfmall.action.common.strategy.AbstractStrategy;
import com.zjht.jfmall.common.dto.ApiConstants;
import com.zjht.jfmall.common.dto.CommApiException;
import com.zjht.jfmall.dao.OrderAssistDao;
import com.zjht.jfmall.dao.OrderExchangeDao;
import com.zjht.jfmall.dao.ProductDao;
import com.zjht.jfmall.entity.OrderAssist;
import com.zjht.jfmall.entity.OrderExchange;
import com.zjht.jfmall.entity.Product;
import com.zjht.jfmall.enums.OrderExchangeDealStatus;
import com.zjht.jfmall.enums.OrderExchangeStatus;
import com.zjht.jfmall.hsh.TicketTradeService;
import com.zjht.jfmall.hsh.TicketTradeServiceImpl;
import com.zjht.jfmall.hsh.request.TicketTradeReq;
import com.zjht.jfmall.hsh.request.TicketTradeReqItem;
import com.zjht.jfmall.hsh.response.TicketTradeResp;
import com.zjht.jfmall.hsh.response.TicketTradeRespItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 发送旧汇生活券码策略
 * @author caozhaokui
 *
 */
public class SendHshOldSHStrategy extends AbstractStrategy {

	//旧汇生活发码工具类
	private static TicketTradeService ticketService=new TicketTradeServiceImpl();
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderAssistDao assistDao;
    @Autowired
    private OrderExchangeDao orderExchangeDao;
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
        order.setDealType(OrderExchange.FAMA);
        //订单状态为待发货
        order.setStatus(OrderExchangeStatus.WAIT_SHIPMENTS.getStatus());
        orderExchangeDao.updateByIdSelective(order);
        //旧汇生活发码
        TicketTradeReq ticketTradeReq=new TicketTradeReq();
        ticketTradeReq.setSend("true");
        ticketTradeReq.setRequestType("YOUOIL");
        ticketTradeReq.setOrderNo(order.getOrderNo());
        ticketTradeReq.setQty(String.valueOf(order.getTotal()));
        List<TicketTradeReqItem> ttriList=new ArrayList<TicketTradeReqItem>();
        TicketTradeReqItem ttri=new TicketTradeReqItem(order.getPhone(),product.getOutCode(), String.valueOf(order.getTotal()));
        ttriList.add(ttri);
        ticketTradeReq.setSubList(ttriList);
		try {
	        TicketTradeResp ttr=ticketService.ticketTrade(ticketTradeReq);
            int succ=0;
            String listStr=null;
		    if (ttr!=null&&ttr.getSubList()!=null) {
		        Gson gson=new Gson();
                List<TicketTradeRespItem> ticketList = ttr.getSubList();
                if(ticketList!=null){
                    for (TicketTradeRespItem tti : ticketList) {
                        if ("00000".equals(tti.getRespCode())) {
                            succ++;
                            OrderAssist assist = new OrderAssist();
                            assist.setOrderId(order.getId());
                            assist.setAssistcode(tti.getAssisCode());
                            assist.setCouponname(tti.getCouponName());
                            assist.setCouponcode(tti.getCouponCode());
                            assist.setMobile(tti.getMobile());
                            assist.setBarcode(tti.getOrderSn());
                            assist.setEndtime(tti.getEndTime());
                            assistDao.insert(assist);
                        }else{
                            log.error("旧汇生活发码失败。");
                        }
                    }
                }
		    }
            order.setRespCode(ttr.getRespCode());
            order.setRespMsg(ttr.getRespMsg());
            //成功数等于总数
            if(succ==order.getTotal()){
                order.setDealStatus(OrderExchangeDealStatus.PROCESSED.getStatus());
                order.setStatus(OrderExchangeStatus.FINISH.getStatus());
            }else{
                order.setDealStatus(OrderExchangeDealStatus.FAIl.getStatus());
            }
            orderExchangeDao.updateByIdSelective(order);
		} catch (Exception e) {
			log.error("旧汇生活发码处理异常：", e);
			throw new CommApiException(ApiConstants.FAIL,"旧汇生活发码处理异常");
		}

	}

}
