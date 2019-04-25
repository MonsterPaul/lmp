package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<User> {
    /**
     * 通过用户名和渠道获取唯一用户
     * @param userName
     * @param channelId
     * @return
     */
    User getUserByNameAndChannel(@Param("userName") String userName, @Param("channelId") String channelId);

    /**
     * 通过用户名获取用户列表，对前端用户而言一个用户名（手机号），可以有多个渠道用户
     * @param userName
     * @return
     */
    List<User> getUsersByName(@Param("userName") String userName);
    /**
     * 通过用户名获取唯一用户，例如后台用户名是唯一的
     * @param userName
     * @return
     */
    User getUserOneByName(@Param("userName") String userName);

    /**
     * 通过用户id获取用户权限
     * @param userId
     * @return
     */
    List<String> getPermissionsById(@Param("userId") String userId);

    /**
     * 通过角色id获取用户列表
     * @param roleId
     * @return
     */
    List<User> getUserByRoleId(@Param("roleId") String roleId);

    /**
     * 通过角色id获取没有被禁用的用户列表
     * @param roleId
     * @return
     */
    List<User> getUserByRoleIdAndStatus(@Param("roleId") String roleId);

    void updateStatus(@Param("id") String id, @Param("status") int status);

    List<User> getCommissionRanking(@Param("bean")User user);

    List<User> getLoanRanking(@Param("bean")User user);

    List<User> findUserByRoleId(@Param("bean") User bean, @Param("roleId")  String roleId);

    Integer countByTime(@Param("channelId") String channelId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    String findInvitationName(@Param("beinvitedId") String beinvitedId);

    User get(@Param("id") String id);
    
    int client(@Param("id") String id);

    int distribution(@Param("id") String id);
}