package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.JsonResult;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.User;

import java.util.Map;

/**
 * <p>
 * app用户表 服务类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
public interface AppUserService {
    /**
     * 校验信息是否唯一
     *
     * @param appUser
     * @return
     */
    boolean checkOne(AppUser appUser, String type);


    /**
     * 校验ID是否存在
     *
     * @param id
     * @return
     */
    AppUser checkId(String id);

    /**
     * 更新用户信息
     *
     * @param appUser
     * @return
     */
    int updateAppUser(AppUser appUser);

    /**
     * 用户登录
     *
     * @param phone 手机
     * @param pwd   密码
     * @return
     */
    AppUser userLogin(String phone, String pwd);

    /**
     * 用户注册
     *
     * @param appUser        注册信息
     * @param invitationType 邀请注册类型 1自行注册2APP用户邀请注册3渠道商邀请注册
     * @param invitationId   邀请人ID
     * @param captcha        验证码
     * @return
     */
    JsonResult register(AppUser appUser, String invitationType, String invitationId, String captcha);

    /**
     * 忘记密码
     *
     * @param phone    手机号码
     * @param captcha  验证码
     * @param password 新密码
     * @return
     */
    JsonResult forgetPassword(String phone, String password, String captcha);

    /**
     * 统计渠道商成功交易的客户
     * @param user
     * @return
     */
    int successCount(User user);

    /**
     * 统计所有邀请注册的用户
     * @param user
     * @return
     */
    int countByChannel(User user);

    PageInfo<AppUser> findPage(AppUser bean, int pageNum, int pageSize);

    void updateStatus(String id, int status);

    AppUser findById(String id);

    PageInfo<AppUser> findBeinvitedPage(AppUser bean, int pageNum, int pageSize);

    JsonResult userbasic(Map<String,String> postMap);

    PageInfo<AppUser> findLoanPage(AppUser bean, int pageNum, int pageSize);

    AppUser findLoan(AppUser appUser);

    Integer countByTime(String startTime, String endTime);

    Integer countNumByTime(String startTime, String endTime);

    int countByServiceId(String id);

    int countByServiceIds(User user);

    String findInvitationName(String beinvitedId);

    int countDownloas(User user);
}
