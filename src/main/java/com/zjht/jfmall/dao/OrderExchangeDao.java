package com.zjht.jfmall.dao;

import com.zjht.jfmall.dao.common.BaseDao;
import com.zjht.jfmall.entity.OrderExchange;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderExchangeDao extends BaseDao<OrderExchange> {
    OrderExchange findByOrderNo(String orderNo);

    int selectExchangeOrder(@Param(value="param") OrderExchange orderExchange);
}