package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.NetLoan;

/**
 * <p>
 * 网贷大全表 服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
public interface NetLoanService {

    PageInfo<NetLoan> getPage(NetLoan ad, int pageNum, int pageSize);

    void insert(NetLoan bean);

    void deleteById(String id);

    NetLoan findById(String id);

    void update(NetLoan bean);

    PageInfo<NetLoan> getNetloanRank(NetLoan ad, int pageNum, int pageSize);
    
}
