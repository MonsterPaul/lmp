package com.zjht.jfmall.service;

import com.zjht.jfmall.entity.User;

/**
 * <p>
 * 渠道商邀请链接点击表 服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
public interface ChannelClickService {
    /**
     * 新增渠道商邀请链接点击
     * @param ip
     * @param invitationId
     * @return
     */
    int insert(String ip,String invitationId);

    int countByChannelId(User user);

}
