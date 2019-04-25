package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.AllLoan;
import com.zjht.jfmall.entity.param.LayuiAllLoanParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by vip on 2019/1/15.
 */
@Repository
public interface AllLoanDao extends Mapper<AllLoan> {

    /**
     * 根据条件查询
     * @param param
     * @return
     */
    List<AllLoan> findPage(@Param("param") LayuiAllLoanParam param);

    /**
     * 更改状态
     * @param param
     */
    void updateStatus(@Param("param") LayuiAllLoanParam param);

    /**
     * 更改是否置顶
     * @param param
     */
    void updateTop(@Param("param") LayuiAllLoanParam param);

    /**
     * 查询
     * @param id
     * @return
     */
    AllLoan findById(@Param("id") String id);

    /**
     * 编辑保存
     * @param param
     */
    void update(@Param("param") AllLoan param);

    List<AllLoan> getAllLoanList (@Param("param") AllLoan param,@Param("num") Integer num,@Param("size") Integer size);

    /**
     * 充值
     * @param param
     */
    void recharge(@Param("param") LayuiAllLoanParam param);

    /**
     * 余额下架
     */
    void soldOut();
}
