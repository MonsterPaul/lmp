package com.zjht.jfmall.service;

import java.io.IOException;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.entity.User;

public interface UserService {

    /**
     * 根据Id删除
     *
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 新增
     *
     * @param user
     * @return
     */
    int insert(User user);

    /**
     * 更新
     *
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 根据用户id查询所有的权限编码{查询结果去除重复}
     *
     * @param userId
     * @return
     */
    List<String> getPermissionsById(String userId);

    /**
     * 分页查询
     *
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> findPage(User user, int pageNum, int pageSize);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    User findById(String id);

    /**
     * 根据登录名称查询是否存在
     *
     * @param user
     * @return
     */
    long countByUserName(User user);

    /**
     * 根据角色ID查询角色下的User对象
     *
     * @param roleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> getUserByRoleId(String roleId, Integer pageNum, Integer pageSize);

    /**
     * 通过用户名更新密码
     *
     * @param userName
     * @param password
     * @return
     */
    ResultDto updatePasswordByName(String userName, String password);

    /**
     * 前端用户登录
     *
     * @param userName
     * @param password
     * @param channelId
     * @return
     */
    ResultDto loginFront(String userName, String password, String channelId);

    /**
     * 后台用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    ResultDto loginBack(String userName, String password, String ip);

    /**
     * 渠道商用户分页查询
     *
     * @param bean
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> findChannelUserPage(User bean, int pageNum, int pageSize);

    /**
     * 添加渠道商用户
     *
     * @param bean
     */
    void insertChannelUser(User bean) throws IOException;

    /**
     * 编辑渠道商用户
     *
     * @param bean
     */
    void updateChannelUser(User bean);


    void updateStatus(String id, int status);

    List<User> getUserByRoleId(String roleId);

    PageInfo<User> getcCommissionRanking(User user,Integer pageNum,Integer pageSize);

    PageInfo<User> getLoanRanking(User user,Integer pageNum,Integer pageSize);


    PageInfo<User> findUserByRoleId(User bean, String roleId, Integer pageNum, Integer pageSize);

    int countChannelNum(String channelPerId);

    int countChannelNums(User user);

    Integer countByTime(String channelId, String startTime, String endTime);

    int getLoginNum();

    String findInvitationName(String beinvitedId);

    //总计分发客户数
    int client(String id);

    //统计分发数
    int distribution(String id);
}
