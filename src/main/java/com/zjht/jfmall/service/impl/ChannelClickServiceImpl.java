package com.zjht.jfmall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.zjht.jfmall.dao.ChannelClickMapper;
import com.zjht.jfmall.entity.ChannelClick;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.ChannelClickService;

import tk.mybatis.mapper.entity.Example;

/**
 * <p>
 * 渠道商邀请链接点击表 服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
public class ChannelClickServiceImpl implements ChannelClickService {
    @Autowired
    private ChannelClickMapper channelClickMapper;

    @Override
    public int countByChannelId(User user) {
        return channelClickMapper.countByChannelId(user);
    }
    @Override
    public int insert(String ip, String invitationId) {
        Example          example  = new Example(ChannelClick.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("clickIp", ip);
        criteria.andEqualTo("channelBusinessId",invitationId);
        List<ChannelClick> channelClickList = channelClickMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(channelClickList)) {
            ChannelClick channelClick = new ChannelClick();
            channelClick.setChannelBusinessId(invitationId);
            channelClick.setClickIp(ip);
            channelClick.setId(UUID.randomUUID().toString().replace("-", ""));
            channelClick.setClickTime(new Date());
            channelClickMapper.insertSelective(channelClick);

            return 1;
        }
        return 0;
    }
}
