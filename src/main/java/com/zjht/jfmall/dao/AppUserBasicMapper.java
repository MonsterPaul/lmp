package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.AppUserBasic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * app用户信息表 Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-12-04
 */
@Repository
public interface AppUserBasicMapper extends Mapper<AppUserBasic> {

    AppUserBasic findByAppId(@Param("id") String id);
}
