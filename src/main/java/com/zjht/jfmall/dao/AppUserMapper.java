package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 * app用户表 Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface AppUserMapper extends Mapper<AppUser> {

    AppUser findById(@Param("id") String id);

    int successCount(@Param("user") User user);

    int countByChannel(@Param("user") User user);

    void updateStatus(@Param("id") String id, @Param("status") int status);

    List<AppUser> findBeinvitedPage(@Param("bean") AppUser bean);

    List<AppUser> findLoanPage(@Param("bean") AppUser bean);

    AppUser findLoan(@Param("bean") AppUser bean);

    Integer countByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Integer countNumByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    int countByServiceId(@Param("serviceId") String id);

    int countByServiceIds(@Param("user") User user);

    String findInvitationName(@Param("beinvitedId") String beinvitedId);

    int countDownloas(@Param("user") User user);
}
