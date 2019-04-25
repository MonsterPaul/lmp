package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.AllLoan;
import com.zjht.jfmall.entity.param.LayuiAllLoanParam;

import java.util.List;

/**
 * Created by vip on 2019/1/15.
 */
public interface AllLoanService {

    /**
     * 前端数据查询返回
     * @param param
     * @param pageSize
     * @param pageNum
     * @return
     */
    PageInfo<AllLoan> findPage(LayuiAllLoanParam param, int pageSize, int pageNum);

    /**
     * 新增保存
     * @param param
     */
    void add(AllLoan param);

    /**
     * 更新状态，上下架
     * @param param
     */
    void updateStatus(LayuiAllLoanParam param);

    /**
     * 更新是否置顶
     * @param param
     */
    void updateTop(LayuiAllLoanParam param);

    /**
     * 查询
     * @param id
     * @return
     */
    AllLoan findById(String id);

    /**
     * 编辑保存
     * @param param
     */
    void edit(AllLoan param);

    /**
     * 给对应的记录充值
     * @param param
     */
    void recharge(LayuiAllLoanParam param);


    List<AllLoan> getAllLoanList(AllLoan allLoan, Integer pageNum, Integer pageSize);
    /**
     * 余额小于0时候自动下架
     */
    void soldOut();

}
