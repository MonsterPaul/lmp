package com.zjht.jfmall.service;

import com.zjht.jfmall.entity.OrderAssist;

import java.util.List;

/**
 * 订单券码Service
 * Created by vip on 2018/1/6.
 */
public interface OrderAssistService {

    /**
     * 根据参数条件查询卷码
     * @param oa
     * @return
     */
    List<OrderAssist> listByOrderAssist(OrderAssist oa);

}
