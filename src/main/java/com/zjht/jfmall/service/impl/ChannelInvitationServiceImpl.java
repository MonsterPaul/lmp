package com.zjht.jfmall.service.impl;

import com.zjht.jfmall.dao.ChannelInvitationMapper;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.ChannelInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 渠道商邀请注册表 服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
public class ChannelInvitationServiceImpl implements ChannelInvitationService {

    @Autowired
    private ChannelInvitationMapper mapper;

    @Override
    public int countByChannelId(User user) {
        return mapper.countByChannelId(user);
    }

    @Override
    public int countByChannelIds(User user) {
        return mapper.countByChannelIds(user);
    }

    @Override
    public int countAllByChannelId(User user) {
        return mapper.countAllByChannelId(user);
    }

    @Override
    public int countAllByChannelIds(User user) {
        return mapper.countAllByChannelIds(user);
    }
}
