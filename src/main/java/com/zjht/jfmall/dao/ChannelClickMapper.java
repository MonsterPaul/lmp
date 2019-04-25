package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.ChannelClick;
import com.zjht.jfmall.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * 渠道商邀请链接点击表 Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface ChannelClickMapper extends Mapper<ChannelClick> {

    int countByChannelId(@Param("user") User user);

}
