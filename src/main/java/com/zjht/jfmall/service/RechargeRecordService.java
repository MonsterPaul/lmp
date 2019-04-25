package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.RechargeRecord;

/**
 * Created by vip on 2019/1/17.
 */
public interface RechargeRecordService {

    /**
     * 查询
     * @param loanId
     * @return
     */
    PageInfo<RechargeRecord> find(String loanId);
}
