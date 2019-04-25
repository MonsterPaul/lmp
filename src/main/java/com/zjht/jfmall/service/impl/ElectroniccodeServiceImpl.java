package com.zjht.jfmall.service.impl;

import com.zjht.jfmall.common.dto.LayuiResultDto;
import com.zjht.jfmall.common.dto.LayuiResultFailDto;
import com.zjht.jfmall.common.dto.LayuiResultSuccessDto;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.common.dto.ResultFailDto;
import com.zjht.jfmall.common.dto.ResultSuccessDto;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.dao.ApiChannelDao;
import com.zjht.jfmall.dao.ElectroniCodeDao;
import com.zjht.jfmall.dao.UserDao;
import com.zjht.jfmall.entity.ApiChannel;
import com.zjht.jfmall.entity.ElectroniCode;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.ElectroniccodeService;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.util.DateTimeUtils;
import com.zjht.jfmall.util.ExcelImportUtil;
import com.zjht.jfmall.util.PropertyUtil;
//import com.zjht.jfmall.util.SmsSenderUtil;
import com.zjht.jfmall.util.VerifyUtils;
//import jodd.util.RandomStringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 〈人寿积分兑换码ServiceImpl〉
 *
 * @author wangpeng
 * @create 2018/1/4
 * @since 1.0.0
 */
@Service
@Transactional // 开启事务
public class ElectroniccodeServiceImpl implements ElectroniccodeService {

    @Autowired
    private ElectroniCodeDao electroniCodeDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ApiChannelDao apiChannelDao;
//    @Autowired
//    private SmsSenderUtil smsSender;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private LogService logService;
    //人说积分渠道编码
    private String CHANNEL_CODE = "q010101";
//    //渠道注册默认密码
//    private String CHANNEL_USER_PASS = PropertyUtil.getPropertyValue("sms", "sms.rs.register.password");
//    //渠道注册默认密码
//    private String CHANNEL_REGISTER_SMS = PropertyUtil.getPropertyValue("sms", "sms.rs.register.content");
    //电子码的长度
    private static final int CODE_LENGTH = 9;
//    //电子码发送短信内容模板
//    private static String CONTENT = PropertyUtil.getPropertyValue("sms", "sms.rs.code.send");
//    //过期时间
//    private static int EXPIRE_MONTH = Integer.valueOf(PropertyUtil.getPropertyValue("special", "electroniccode_expire_month").trim());

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //查看所有电子码权限编码
    private final String QUERYALL_BIZ_CODE = "j010109";

    @Override
    public List<ElectroniCode> getPage(ElectroniCode electroniCode, Integer pageNum, Integer pageSize) {
        electroniCode.setPageIndex(pageNum);
        electroniCode.setPageSize(pageSize);
        electroniCode.setOrderByClause(" id desc");
        //如果用户不具备查看所有电子码的权限，只查询当前用户创建的电子码
        if(!sessionProvider.getPermissions().contains(QUERYALL_BIZ_CODE)){
            electroniCode.setSelfUserId(sessionProvider.getUser().getId());
        }
        if(StringUtils.isNotBlank(electroniCode.getCreatorName())){
            User creator = new User();
            creator.setUsername(electroniCode.getCreatorName());
            electroniCode.setCreator(creator);
        }
        if(StringUtils.isNotBlank(electroniCode.getSenderName())){
            User sender = new User();
            sender.setUsername(electroniCode.getSenderName());
            electroniCode.setSender(sender);
        }
        if(StringUtils.isNotBlank(electroniCode.getImportorName())){
            User importor = new User();
            importor.setUsername(electroniCode.getImportorName());
            electroniCode.setImportor(importor);
        }
        List<ElectroniCode> list = electroniCodeDao.selectListByObject(electroniCode);
        for (ElectroniCode eu : list) {
            if(eu.getCreator() != null && StringUtils.isNotBlank(eu.getCreator().getChannelId())){
                eu.getCreator().setChannelName(apiChannelDao.getNameById(eu.getCreator().getChannelId()));
            }
        }
        return list;
    }

    @Override
    public Set<String> insertElectroniccodeByNumAndPoints(int num, Integer points) {
        //所有券码集合
        Set<String> generateCodes = new HashSet<String>();
        User        operateUser   = sessionProvider.getUser();
        Date        operateDate   = new Date();
        for (int i = 0; i < num; i++) {
            ElectroniCode electroniCode = new ElectroniCode();
            String        code          = generateNotExistCode();
            generateCodes.add(code);
            electroniCode.setCode(code);
            electroniCode.setPoints(points);
            electroniCode.setStatus(ElectroniCode.Status.EXPORTED.getStatus());
            electroniCode.setCreateTime(operateDate);
            electroniCode.setCreator(operateUser);
//            electroniCode.setExpireTime(DateUtils.addSeconds(DateUtils.ceiling(DateUtils.addMonths(operateDate, EXPIRE_MONTH), Calendar.DAY_OF_MONTH), -1));
            electroniCodeDao.insertSelective(electroniCode);
        }
        return generateCodes;
    }

    @Override
    public int getPageCount(ElectroniCode electroniCode) {
        return (int) electroniCodeDao.countByObject(electroniCode);
    }

    @Override
    public synchronized ResultDto exchangeCode(String mobile, String code) {

        ElectroniCode electroniCode = electroniCodeDao.findByCode(code);

        if (electroniCode == null) {
            return new ResultFailDto("兑换码不存在");
        }
        if (electroniCode.getStatus() == ElectroniCode.Status.ABANDONED.getStatus()) {
            return new ResultFailDto("兑换码已作废，不能兑换");
        }
        Date now = new Date();
        if (now.after(electroniCode.getExpireTime())) {
            return new ResultFailDto("兑换码过期，不能兑换");
        }
        if (electroniCode.getStatus() == ElectroniCode.Status.ALREADY_USED.getStatus()) {
            if (mobile.equals(electroniCode.getMobile())) {
                User user = userDao.getUserByNameAndChannel(mobile, electroniCode.getCreator().getChannelId());
                ResultDto resultDto = new ResultSuccessDto("该手机号已兑换该兑换码");
                resultDto.setData(user);
                return resultDto;
            } else {
                return new ResultFailDto("该兑换码已被其他手机号兑换");
            }
        }
        if (electroniCode.getStatus() != ElectroniCode.Status.SENDED.getStatus()) {
            return new ResultFailDto("该兑换码尚未匹配手机号");
        }
        if (!mobile.equals(electroniCode.getMobile())) {
            return new ResultFailDto("该兑换码与手机号不匹配");
        }
        electroniCode.setMobile(mobile);
        //兑换时间
        electroniCode.setExchangeTime(new Date());
        electroniCode.setStatus(ElectroniCode.Status.ALREADY_USED.getStatus());
        electroniCodeDao.updateByIdSelective(electroniCode);
        //增加积分,兑换成功才注册
        User user = userDao.getUserByNameAndChannel(mobile, electroniCode.getCreator().getChannelId());
        //用户不存在，或者存在但是是不同渠道，默认注册
        if (user == null) {
            user = new User();
            user.setType(User.Type.FRONT.getType());
            user.setPoints(0);
            user.setMobile(mobile);
            user.setUsername(mobile);
            user.setNickName(mobile);
            user.setCreateTime(new Date());
            if(electroniCode.getCreator() != null){
                user.setChannelId(electroniCode.getCreator().getChannelId());
            }else{
                ApiChannel channelQuery = new ApiChannel();
                channelQuery.setChannelCode(CHANNEL_CODE);
                ApiChannel channel = apiChannelDao.selectOne(channelQuery);
                user.setChannelId(channel.getId());
            }
//            user.setPassword(DigestUtils.md5Hex(CHANNEL_USER_PASS));
            userDao.insert(user);
            //给用户发送注册短信
            new Thread(new Runnable() {
                @Override
                public void run() {
//                    String smsContent = CHANNEL_REGISTER_SMS.replace("{userName}",mobile).replace("{password}", CHANNEL_USER_PASS);
//                    smsSender.send(mobile, smsContent, 12);
                }
            }).start();
        }
        user.setPoints(user.getPoints() + electroniCode.getPoints());
        userDao.updateByPrimaryKeySelective(user);
        ResultDto resultDto = new ResultSuccessDto("兑换成功");
        resultDto.setData(user);
        return resultDto;
    }

    @Override
    public LayuiResultDto importFile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile>  fileMaps         = multipartRequest.getFileMap();
        Iterator                    fileItor         = fileMaps.entrySet().iterator();
        User                        operateUser      = sessionProvider.getUser();
        while (fileItor.hasNext()) {
            Map.Entry     entity = (Map.Entry) fileItor.next();
            MultipartFile file   = (MultipartFile) entity.getValue();
            String        ext    = FilenameUtils.getExtension(file.getOriginalFilename());
            if (!"xls".equals(ext) && !"xlsx".equals(ext)) {
                return new LayuiResultFailDto("请选择以.xls或以.xlsx结尾的excel文件");
            }
            try {
                List<List<String>> data         = ExcelImportUtil.resolveExcel(file.getInputStream(), 1, 3, 1);
                StringBuilder      errorCode    = new StringBuilder();
                StringBuilder      errorMobile  = new StringBuilder();
                StringBuilder      alreadyCodes = new StringBuilder();
                int                succSum      = 0;
                int                failSum      = 0;
                if (!CollectionUtils.isEmpty(data)) {
                    for (int i = 0; i < data.size(); i++) {
                        List<String> electroniCodes = data.get(i);
                        if (!CollectionUtils.isEmpty(electroniCodes)) {
                            String mobile = electroniCodes.get(1);
                            if (!VerifyUtils.isMobile(mobile)) {
                                //手机号码不正确
                                errorMobile.append(mobile + ",");
                                failSum++;
                                continue;
                            }

                            String        code          = electroniCodes.get(0);
                            ElectroniCode electroniCode = electroniCodeDao.findByCode(code);
                            if (null == electroniCode) {
                                //电子码不存在于数据库中
                                errorCode.append(code + ",");
                                failSum++;
                            } else if (StringUtils.isNotBlank(electroniCode.getMobile())) {
                                //电子码已经匹配了手机号
                                alreadyCodes.append(code + ",");
                                failSum++;
                            } else {
                                electroniCode.setMobile(mobile);
                                electroniCode.setStatus(ElectroniCode.Status.SENDED.getStatus());
                                electroniCode.setImportor(operateUser);
                                electroniCode.setImportTime(new Date());
                                electroniCodeDao.updateByIdSelective(electroniCode);
                                succSum++;
                            }
                        }
                    }
                    if (errorCode.length() > 0 || errorMobile.length() > 0 || alreadyCodes.length() > 0) {
                        String Msg = "成功导入" + succSum + "条,导入失败" + failSum + "条";
                        if (errorCode.length() > 0) {
                            Msg += ",以下电子码非法【" + errorCode.substring(0, errorCode.length() - 1) + "】";
                        }
                        if (errorMobile.length() > 0) {
                            Msg += ",以下手机号码错误【" + errorMobile.substring(0, errorMobile.length() - 1) + "】";
                        }
                        if (alreadyCodes.length() > 0) {
                            Msg += ",以下电子码已匹配手机号【" + alreadyCodes.substring(0, alreadyCodes.length() - 1) + "】";
                        }
                        return new LayuiResultSuccessDto(Msg);
                    } else {
                        return new LayuiResultSuccessDto("成功导入" + succSum + "条");
                    }
                }

            } catch (Exception e) {
                logger.error("[method:importFile,meg:导入电子码文件异常]", e);
                return new LayuiResultFailDto("导入文件异常");
            }
        }
        return new LayuiResultFailDto("上传文件为空");
    }

    @Override
    public LayuiResultDto createAndSendCode(String points, String expireTime, List<ElectroniCode> electroniCodes) {
        //手机号不正确信息
        StringBuilder mobileErrMsg = new StringBuilder();
        //发送失败信息
        StringBuilder sendErrMsg = new StringBuilder();
        //发送成功条数
        int successNum = 0;
        //发送失败条数
        int  failSum     = 0;
        User operateUser = sessionProvider.getUser();
        Date operateTime = new Date();
        for (int i = 0; i < electroniCodes.size(); i++) {
            ElectroniCode electroniCode = electroniCodes.get(i);
            //检测手机号码是否正确
            if (!VerifyUtils.isMobile(electroniCode.getMobile())) {
                mobileErrMsg.append(electroniCode.getMobile() + ",");
                failSum++;
                continue;
            }
            //生成电子码
            electroniCode.setCode(generateNotExistCode());
            electroniCode.setPoints(Integer.valueOf(points));
            electroniCode.setCreateTime(operateTime);
//            electroniCode.setExpireTime(DateUtils.addSeconds(DateUtils.ceiling(DateUtils.addMonths(operateTime, EXPIRE_MONTH), Calendar.DAY_OF_MONTH), -1));
            electroniCode.setStatus(ElectroniCode.Status.EXPORTED.getStatus());
            electroniCode.setCreator(operateUser);
            electroniCode.setSender(operateUser);
            electroniCode.setImportor(operateUser);
            electroniCode.setImportTime(operateTime);
            electroniCodeDao.insert(electroniCode);
            //发送电子码到手机
//            String content = CONTENT.replace("{code}", electroniCode.getCode()).replace("{date}", DateTimeUtils.formatDateStr(electroniCode.getExpireTime(), "yyyy/MM/dd"));
            String result  = null;
            try {
//                result = smsSender.send(electroniCode.getMobile(), content);
            } catch (Exception e) {
                logger.error("发送兑换码短信失败：", e);
                electroniCode.setStatus(ElectroniCode.Status.SEND_FAIL.getStatus());
            }
//            if (smsSender.SUCCESS.equals(result)) {
//                //发送成功
//                electroniCode.setStatus(ElectroniCode.Status.SENDED.getStatus());
//                electroniCode.setSendTime(new Timestamp(System.currentTimeMillis()));
//                successNum++;
//            } else {
//                //发送失败
//                electroniCode.setStatus(ElectroniCode.Status.SEND_FAIL.getStatus());
//                electroniCode.setSendTime(new Timestamp(System.currentTimeMillis()));
//                sendErrMsg.append(electroniCode.getMobile() + ",");
//                failSum++;
//            }
            electroniCodeDao.updateByIdSelective(electroniCode);
        }
        //组装返回信息
        if (mobileErrMsg.length() > 0 || sendErrMsg.length() > 0) {
            String Msg = "成功发送" + successNum + "条,发送失败" + failSum + "条";
            if (mobileErrMsg.length() > 0) {
                Msg += ",以下手机号码错误【" + mobileErrMsg.substring(0, mobileErrMsg.length() - 1) + "】";
            }
            if (sendErrMsg.length() > 0) {
                Msg += ",以下手机号发送电子码失败【" + sendErrMsg.substring(0, sendErrMsg.length() - 1) + "】";
            }
            Msg += "发送失败的手机号，请勿在此页面再次发送，务必联系管理员从后台重发！";
            return new LayuiResultFailDto(Msg);
        } else {
            return new LayuiResultSuccessDto("成功发送" + successNum + "条");
        }
    }

    @Override
    public ResultDto reSendCode(String id) {
        ElectroniCode electroniCode = electroniCodeDao.selectById(id);
        if (electroniCode == null) {
            return new ResultFailDto("记录不存在，请勿非法操作");
        }
//        String content = CONTENT.replace("{code}", electroniCode.getCode()).replace("{date}", DateTimeUtils.formatDateStr(electroniCode.getExpireTime(), "yyyy/MM/dd"));
        try {
//            String result = smsSender.send(electroniCode.getMobile(), content);
//            if (SmsSenderUtil.SUCCESS.equals(result)) {
//                electroniCode.setStatus(ElectroniCode.Status.SENDED.getStatus());
//            } else {
//                electroniCode.setStatus(ElectroniCode.Status.SEND_FAIL.getStatus());
//            }
        } catch (Exception e) {
            logger.error("发送兑换码短信失败：", e);
            electroniCode.setStatus(ElectroniCode.Status.SEND_FAIL.getStatus());
        }
        User operateUser = sessionProvider.getUser();
        electroniCode.setSendTime(new Date());
        electroniCode.setSender(operateUser);
        electroniCodeDao.updateByIdSelective(electroniCode);
        return new ResultSuccessDto();
    }

    @Override
    public ResultDto abandonedCode(String[] ids) {
        ElectroniCode electroniCode = new ElectroniCode();
        electroniCode.setStatus(ElectroniCode.Status.ABANDONED.getStatus());
        StringBuilder  abandonedIds = new StringBuilder();
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            electroniCode.setId(ids[i]);
            electroniCodeDao.updateByIdSelective(electroniCode);
            abandonedIds.append(id+",");
        }
        logService.add("作废电子码,ID为:"+abandonedIds.substring(0,abandonedIds.length()-1));
        return new ResultSuccessDto("作废成功");
    }

    /**
     * 生成一个数据库中不存在的电子码
     *
     * @return
     */
    private synchronized String generateNotExistCode() {
        String        code          = null;
        ElectroniCode electroniCode = electroniCodeDao.findByCode(code);
        if (electroniCode != null) {
            return generateNotExistCode();
        }
        return code;
    }


}