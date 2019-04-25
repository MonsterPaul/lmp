package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.entity.OrderExchange;
import com.zjht.jfmall.entity.Product;

import java.util.List;

/**
 * 兑换订单Service
 * Created by vip on 2018/1/5.
 */
public interface OrderExchangeService {

    /**
     * 分页查询
     * @param oe
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<OrderExchange> findPage(OrderExchange oe, int pageNum, int pageSize);

    /**
     * 根据条件获取
     * @param oe
     * @return
     */
    OrderExchange get(OrderExchange oe);

    /**
     * 积分兑换
     * @param product
     * @param buyCount
     * @return
     */
    ResultDto<OrderExchange> exchange(Product product, Integer buyCount);
    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    OrderExchange findByOrderNo(String orderNo);

    /**
     * 不分页查询
     * @param oe
     * @return
     */
    List<OrderExchange> listByOrderExchange(OrderExchange oe);

    /**
     * 更新
     * @param oe
     * @return
     */
    ResultDto updateSelective(OrderExchange oe);

}
