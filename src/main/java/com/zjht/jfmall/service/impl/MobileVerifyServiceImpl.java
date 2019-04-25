package com.zjht.jfmall.service.impl;

import com.zjht.jfmall.dao.MobileVerifyDao;
import com.zjht.jfmall.entity.MobileVerify;
import com.zjht.jfmall.service.MobileVerifyService;
import com.zjht.jfmall.util.CodeBuilder;
import com.zjht.jfmall.util.PropertyUtil;
//import com.zjht.jfmall.util.SmsSenderUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @create 2018-01-09 19:50
 */
@Service
@Transactional
public class MobileVerifyServiceImpl implements MobileVerifyService {
//    //重发短信时间间隔
//    private static String RESENDTIME = PropertyUtil.getPropertyValue("sms", "mobile.code.interval");
//    //有效时间
//    private static String EXPIREDTIME = PropertyUtil.getPropertyValue("sms", "mobile.code.expired");
//    //短信内容模板
//    private static String CONTENT = PropertyUtil.getPropertyValue("sms", "mobile.code.content");
//    //短信一小时发送次数
//    private static int HOUR_COUNT = Integer.valueOf(PropertyUtil.getPropertyValue("sms", "sms.send.hour_count"));
//    //短信一天发送次数
//    private static int DAY_COUNT = Integer.valueOf(PropertyUtil.getPropertyValue("sms", "sms.send.day.count"));

//    @Autowired
//    private SmsSenderUtil smsSender;
    @Autowired
    private MobileVerifyDao mobileVerifyDao;
    @Override
    public Map<String, String> verifySend(String mobile, String ipAddr) {
        Map<String, String> data = new HashMap<String, String>();
//        data.put("result", "1");
//        data.put("msg", "可以发送");
////        Long time = Long.valueOf(RESENDTIME);
//        MobileVerify mobileBean = mobileVerifyDao.findByMobile(mobile);
//        if (mobileBean != null) {
//            //一天内发送次数
//            int sdc=mobileBean.getSendDayCount()==null?1:mobileBean.getSendDayCount().intValue();
//            //一小时内发送次数
//            int shc=mobileBean.getSendHourCount()==null?1:mobileBean.getSendHourCount().intValue();
//            Calendar c=Calendar.getInstance();
//            c.setTime(new Date());
//            Calendar l=Calendar.getInstance();
//            l.setTime(mobileBean.getSendTime());
//            //一天内发送次数、一小时内发送次数
//            if (mobileBean.getSendTime() != null && (System.currentTimeMillis() - mobileBean.getSendTime().getTime() < time)) {
//                data.put("result", "2");
//                data.put("msg", "频繁操作，请稍后再试");
//            }else if (l.get(Calendar.YEAR)==c.get(Calendar.YEAR)&&l.get(Calendar.MONDAY)==c.get(Calendar.MONTH)&&l.get(Calendar.DAY_OF_MONTH)==c.get(Calendar.DAY_OF_MONTH)) {
//                if (sdc>=DAY_COUNT) {
//                    data.put("result", "2");
//                    data.put("msg", "一天内发送短信验证码次数不能超过"+ DAY_COUNT +"次，请稍后再试");
//                }else if(l.get(Calendar.HOUR_OF_DAY)==c.get(Calendar.HOUR_OF_DAY)&&shc>=HOUR_COUNT){
//                    data.put("result", "2");
//                    data.put("msg", "一小时内发送短信验证码次数不能超过"+HOUR_COUNT+"次，请稍后再试");
//                }
//            }
//        }
//        if (StringUtils.isNotBlank(ipAddr)) {
//            MobileVerify ipAddrBean = mobileVerifyDao.findByIp(ipAddr);
//            if (ipAddrBean != null) {
//                if (ipAddrBean.getSendTime() != null && (System.currentTimeMillis() - ipAddrBean.getSendTime().getTime() < time)) {
//                    data.put("result", "2");
//                    data.put("msg", "频繁操作，请稍后再试");
//                }
//            }
//        }
        return data;
    }

    @Override
    public MobileVerify sendVerifyCode(String mobile, String ipAddr) {
        MobileVerify bean = mobileVerifyDao.findByMobile(mobile);//根据手机号查找短信发送记录
//        if (bean != null) {//存在发送记录，更新短信发送的验证码和内容
//            bean.setCode(CodeBuilder.sixNO());
//            bean.setContent(CONTENT.replace("{code}", bean.getCode()));
//            // bean.setContent(" 测试:"+bean.getCode());
//            bean.setIpaddr(ipAddr);
//            bean.setStatus(MobileVerify.SENDSTATUS_FAILED);
//
//            //新增同一号码手机一天内发送次数不得超过5次、一小时内不得超过3次
//            Calendar c=Calendar.getInstance();
//            c.setTime(new Date());
//            Calendar l=Calendar.getInstance();
//            l.setTime(bean.getSendTime());
//            //一天内发送次数、一小时内发送次数
//            if (l.get(Calendar.YEAR)==c.get(Calendar.YEAR)&&l.get(Calendar.MONDAY)==c.get(Calendar.MONTH)&&l.get(Calendar.DAY_OF_MONTH)==c.get(Calendar.DAY_OF_MONTH)) {
//                //上次发送短信日期是今天
//                int sendDayCount=bean.getSendDayCount()==null?0:bean.getSendDayCount();
//                sendDayCount+=1;
//                bean.setSendDayCount(sendDayCount);
//                //一小时内发送次数
//                if (l.get(Calendar.HOUR_OF_DAY)==c.get(Calendar.HOUR_OF_DAY)) {
//                    int sendHourCount=bean.getSendHourCount()==null?0:bean.getSendHourCount().intValue();
//                    sendHourCount+=1;
//                    bean.setSendHourCount(sendHourCount);
//                }else{
//                    bean.setSendHourCount(1);
//                }
//            }else{
//                bean.setSendDayCount(1);//一天内发送次数
//                bean.setSendHourCount(1);//一小时内发送次数（上次发送短信日期不是今天）
//            }
//            bean.setSendTime(new Date());
//            mobileVerifyDao.updateByIdSelective(bean);
//        } else {//不存在发送记录，新增短信发送的验证码和内容记录
//            bean = new MobileVerify();
//            bean.setMobile(mobile);
//            bean.setCode(CodeBuilder.sixNO());
//            bean.setContent(CONTENT.replace("{code}", bean.getCode()));
//            // bean.setContent(" 测试:"+bean.getCode());
//            bean.setSendTime(new Date());
//            bean.setIpaddr(ipAddr);
//            bean.setStatus(MobileVerify.SENDSTATUS_FAILED);
//            bean.setSendHourCount(1);
//            bean.setSendDayCount(1);
//            mobileVerifyDao.insert(bean);
//        }
//		/*
//		 * 发送短信（发送接口）
//		 */
////        smsSender.send(bean.getMobile(), bean.getContent(), 12);
//        bean.setStatus(MobileVerify.SENDSTATUS_SUCCESS);//短信发送成功
//        mobileVerifyDao.updateByIdSelective(bean);//更新短信发送状态
        return bean;
    }

    @Override
    public MobileVerify updateVerifyInfo(String mobile, String ipAddr) {
        MobileVerify bean = mobileVerifyDao.findByMobile(mobile);//根据手机号查找短信发送记录
//        if (bean != null) {//存在发送记录，更新短信发送的验证码和内容
//            bean.setCode(CodeBuilder.sixNO());
//            bean.setContent(CONTENT.replace("{code}", bean.getCode()));
//            // bean.setContent(" 测试:"+bean.getCode());
//            bean.setIpaddr(ipAddr);
//            mobileVerifyDao.updateByIdSelective(bean);
//        } else {//不存在发送记录，新增短信发送的验证码和内容记录
//            bean = new MobileVerify();
//            bean.setMobile(mobile);
//            bean.setCode(CodeBuilder.sixNO());
//            bean.setContent(CONTENT.replace("{code}", bean.getCode()));
//            // bean.setContent(" 测试:"+bean.getCode());
//            bean.setSendTime(new Date());
//            bean.setIpaddr(ipAddr);
//            bean.setStatus(MobileVerify.SENDSTATUS_FAILED);
//            bean.setSendDayCount(1);
//            bean.setSendHourCount(1);
//            mobileVerifyDao.insert(bean);
//        }
        return bean;
    }
    @Override
    public boolean verifyCode(String mobile, String code) {
        if (StringUtils.isBlank(mobile)) {
            return false;
        }
        MobileVerify bean = mobileVerifyDao.findByMobile(mobile);
        if (bean == null) {
            return false;
        }
        if (StringUtils.isBlank(code)) {
            return false;
        }
        return bean.getCode().equals(code);
    }
}
