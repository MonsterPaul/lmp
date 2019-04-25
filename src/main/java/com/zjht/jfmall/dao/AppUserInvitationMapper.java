package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.AppUserInvitation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 * APP用户邀请记录 Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface AppUserInvitationMapper extends Mapper<AppUserInvitation> {

    List<AppUserInvitation> getUserInvitationList(@Param("invitationId")String invitationId, @Param("num")Integer num, @Param("size")Integer size);

    int countByInvitationId(@Param("invitationId") String invitationId);

    Double sumMoney(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
