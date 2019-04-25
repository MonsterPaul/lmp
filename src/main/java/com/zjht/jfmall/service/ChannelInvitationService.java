package com.zjht.jfmall.service;

import com.zjht.jfmall.entity.User;

/**
 * <p>
 * 渠道商邀请注册表 服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
public interface ChannelInvitationService {

    int countByChannelId(User user);

    int countByChannelIds(User user);

    int countAllByChannelId(User user);

    int countAllByChannelIds(User user);

}
