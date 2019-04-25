package com.zjht.jfmall.service.impl;

import com.zjht.jfmall.dao.AppUserInvitationMapper;
import com.zjht.jfmall.entity.AppUserInvitation;
import com.zjht.jfmall.service.AppUserInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * APP用户邀请记录 服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
public class AppUserInvitationServiceImpl implements AppUserInvitationService {
    @Autowired
    private AppUserInvitationMapper appUserInvitationMapper;

    @Override
    public List<AppUserInvitation> getUserInvitationList(String invitationId, Integer pageNum, Integer pageSize) {
        return appUserInvitationMapper.getUserInvitationList(invitationId,pageNum,pageSize);
    }

    @Override
    public int countByInvitationId(String invitationId) {
        return appUserInvitationMapper.countByInvitationId(invitationId);
    }

    @Override
    public Double sumMoney(String startTime, String endTime) {
        return appUserInvitationMapper.sumMoney(startTime, endTime);
    }
}
