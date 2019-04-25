package com.zjht.jfmall.util;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.io.Serializable;
import java.util.Date;

/**
 * 验证码,判断有效时间
 * Created by yelo on 2015/10/7.
 */
public class Captcha implements Serializable{

    /**验证码5分之内有效*/
    public static  final int MAX_ACTIVE_TIME = 5;
    /**验证码1分之内只能发一次*/
    private final int MAX_EXPIRE_TIME_SECOND = 60;

    private String captcha;//验证码
    private Date activeDate;//截至有效时间

    public Captcha(String captcha) {
        this.captcha = captcha;
        this.activeDate = DateTime.now().plusMinutes(MAX_ACTIVE_TIME).toDate();//一分钟内有效
    }

    public Captcha() {
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /**
     * 判断验证码是否处于有效时间
     * @return
     */
    public boolean isActive(DateTime dt) {
        return activeDate == null ? false : Seconds.secondsBetween(new DateTime(activeDate), dt).getSeconds() <= 0;
    }

    /**
     * 1分钟内只能发一次
     * @return true 已过期
     */
    public boolean isExpire() {
        if(activeDate == null){
            return true;
        }
        //因为activeDate已经加了MAX_ACTIVE_TIME，所以要减去
        int span = Seconds.secondsBetween(new DateTime(activeDate).plusMinutes(-MAX_ACTIVE_TIME), DateTime.now()).getSeconds();
        return (span > MAX_EXPIRE_TIME_SECOND);
    }

    public boolean isActive() {
        return isActive(DateTime.now());
    }

    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    @Override
    public String toString() {
        return "Captcha{" +
                "activeDate=" + activeDate +
                ", captcha='" + captcha + '\'' +
                '}';
    }
}
