package com.zjht.jfmall.service;


import com.zjht.jfmall.common.dto.JsonResult;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.UserApplyLoan;
import com.zjht.jfmall.entity.param.LayuiAllLoanParam;

public interface UserApplyLoanService {

    JsonResult saveUserApplyLoan(String id,String loanId);
    /**
     * 根据平台ID统计申请数量
     * @param allLoanId
     * @return
     */
    int countByAllLoanId(String allLoanId);

    /**
     * 查询
     * @param param
     * @return
     */
    PageInfo<UserApplyLoan> findPage(LayuiAllLoanParam param);

    /**
     * 根据用户id获取用户申请的平台信息
     * @param appUserId
     * @return
     */
    PageInfo<UserApplyLoan> list(String appUserId);
}