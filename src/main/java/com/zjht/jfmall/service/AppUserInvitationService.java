package com.zjht.jfmall.service;

import com.zjht.jfmall.entity.AppUserInvitation;

import java.util.List;

/**
 * <p>
 * APP用户邀请记录 服务类
 * </p>
 */
public interface AppUserInvitationService {
    /**
     * 获取APP用户邀请记录
     *
     * @param invitationId 用户ID
     * @param pageNum      页数
     * @param pageSize 条数
     * @return
     */
    List<AppUserInvitation> getUserInvitationList(String invitationId, Integer pageNum, Integer pageSize);

    int countByInvitationId(String invitationId);

    Double sumMoney(String startTime, String endTime);
}
