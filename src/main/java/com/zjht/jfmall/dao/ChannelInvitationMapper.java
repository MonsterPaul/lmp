package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.ChannelInvitation;
import com.zjht.jfmall.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * <p>
 * 渠道商邀请注册表 Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface ChannelInvitationMapper extends Mapper<ChannelInvitation> {

    int countByChannelId(@Param("user") User user);

    int countByChannelIds(@Param("user") User user);

    int countAllByChannelId(@Param("user") User user);

    int countAllByChannelIds(@Param("user") User user);
}
