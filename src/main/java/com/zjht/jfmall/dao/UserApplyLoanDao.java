package com.zjht.jfmall.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zjht.jfmall.entity.UserApplyLoan;
import com.zjht.jfmall.entity.param.LayuiAllLoanParam;

import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserApplyLoanDao extends Mapper<UserApplyLoan> {

    /**
     *
     * @param param
     * @return
     */
    List<UserApplyLoan> findPage(@Param("param") LayuiAllLoanParam param);

    List<UserApplyLoan> list(@Param("appUserId") String appUserId);
}