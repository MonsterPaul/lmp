package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.NetLoan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 * 网贷大全表 Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface NetLoanMapper extends Mapper<NetLoan> {

    void update(@Param("bean") NetLoan bean);

    void deleteById(@Param("id") String id);

    NetLoan findById(@Param("id") String id);

    List<NetLoan> getNetloanRank(@Param("bean") NetLoan bean);
}
