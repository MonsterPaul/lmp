package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.CallContactDetail;
import com.zjht.jfmall.entity.UserMailList;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 * app用户通话记录表 Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-12-04
 */
@Repository
public interface CallContactDetailMapper extends Mapper<CallContactDetail> {
    int insertData(List<CallContactDetail> callContactDetailList);
}
