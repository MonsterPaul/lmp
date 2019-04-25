package com.zjht.jfmall.service;

import com.zjht.jfmall.entity.MobileVerify;

import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-01-09 19:32
 */
public interface MobileVerifyService {
    /**
     * 验证是否可发送
     *
     * @param mobile
     * @param ipAddr
     * @return result:1、通过；2、不通过。msg：提示信息
     */
    public Map<String, String> verifySend(String mobile, String ipAddr);
    /**
     * 发送手机验证码
     *
     * @param mobile
     * @param ipAddr
     * @return
     */
    public MobileVerify sendVerifyCode(String mobile, String ipAddr);

    /**
     * 更新验证码
     * @param mobile
     * @param ipAddr
     * @return
     */
    public MobileVerify updateVerifyInfo(String mobile, String ipAddr);
    /**
     * 验证手机验证码
     *
     * @param mobile
     * @param code
     * @return
     */
    public boolean verifyCode(String mobile, String code);
}
