package com.zjht.jfmall.service.impl;

import com.zjht.jfmall.dao.OrderAssistDao;
import com.zjht.jfmall.entity.OrderAssist;
import com.zjht.jfmall.service.OrderAssistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单券码ServiceImpl
 * Created by vip on 2018/1/6.
 */
@Service
@Transactional
public class OrderAssistServiceImpl implements OrderAssistService {

    @Autowired
    private OrderAssistDao oaDao;

    /**
     * 根据参数条件查询卷码
     *
     * @param oa
     * @return
     */
    @Override
    public List<OrderAssist> listByOrderAssist(OrderAssist oa) {
        return oaDao.selectListByObject(oa);
    }
}
