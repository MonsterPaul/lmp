package com.zjht.jfmall.entity;

import com.alibaba.fastjson.JSONObject;
import com.zjht.jfmall.entity.base.BaseOrderExchange;
import com.zjht.jfmall.enums.OrderExchangeDealStatus;
import com.zjht.jfmall.enums.OrderExchangeStatus;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 扩展的字段写这个类，需要保存到数据库值的字段写BaseOrderExchange
 * Created by vip on 2018/1/5.
 */
public class OrderExchange extends BaseOrderExchange {

    /**
     * 发码
     */
    public static final int FAMA = 1;

    /**
     * 红包
     */
    public static final int HONGBAO = 2;

    /**
     * 产品ID,积分商城一次兑换一个产品
     */
    private Product product;

    /**
     * 排序语句（例如：id desc）
     */
    protected String orderByClause;

    /**
     * 是否去重
     */
    protected boolean distinct;

    /**
     * 订单券码
     */
    private List<OrderAssist> orderAssists;
    /**
     * 查询兑换开始时间
     */
    private String exchangeTimeBegin;
    /**
     * 查询兑换结束日期
     */
    private String exchangeTimeEnd;
    /**
     * 获取订单状态中文字符串
     * @return
     */
    public String getStatusStr() {
        return OrderExchangeStatus.getInstance(this.getStatus() == null ? 100 : this.getStatus()).getText();
    }

    /**
     * 获取订单处理状态中文字符串
     * @return
     */
    public String getDealStatusStr() {
        return OrderExchangeDealStatus.getInstance(this.getDealStatus() == null ? 100 : this.getDealStatus()).getText();
    }
    public String getRedPackageStatusStr() {
        if(this.getDealType()==null || this.getDealType()!=OrderExchange.HONGBAO || this.getDealStatus()==null || this.getDealStatus()==OrderExchangeDealStatus.FAIl.getStatus()){
            return "";
        }
        try {
            JSONObject respObj = JSONObject.parseObject(this.getRespMsg());
            String status = respObj.getString("status");
            if(status==null){
                return respObj.getString("return_msg");
            }else if("SENDING".equalsIgnoreCase(status)){
                return "发放中";
            }else if("SENT".equalsIgnoreCase(status)){
                return "已发放待领取";
            }else if("FAILED".equalsIgnoreCase(status)){
                return "发放失败";
            }else if("RECEIVED".equalsIgnoreCase(status)){
                return "已领取";
            }else if("RFUND_ING".equalsIgnoreCase(status)){
                return "退款中";
            }else if("REFUND".equalsIgnoreCase(status)){
                return "已退款";
            }
        } catch (Exception e) {
            return this.getRespMsg();
        }
        return "";
    }
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<OrderAssist> getOrderAssists() {
        return orderAssists;
    }

    public void setOrderAssists(List<OrderAssist> orderAssists) {
        this.orderAssists = orderAssists;
    }

    public String getExchangeTimeBegin() {
        return exchangeTimeBegin;
    }

    public void setExchangeTimeBegin(String exchangeTimeBegin) {
        this.exchangeTimeBegin = exchangeTimeBegin;
    }

    public String getExchangeTimeEnd() {
        return exchangeTimeEnd;
    }

    public void setExchangeTimeEnd(String exchangeTimeEnd) {
        this.exchangeTimeEnd = exchangeTimeEnd;
    }

}
