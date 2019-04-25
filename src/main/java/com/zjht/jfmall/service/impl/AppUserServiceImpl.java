package com.zjht.jfmall.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.zjht.jfmall.common.dto.JsonResult;
import com.zjht.jfmall.dao.*;
import com.zjht.jfmall.entity.*;
import com.zjht.jfmall.enums.BusiStatus;
import com.zjht.jfmall.service.AppUserService;
import com.zjht.jfmall.service.ApplyRecordService;
import com.zjht.jfmall.util.Captcha;
import com.zjht.jfmall.util.CheckUtils;
import com.zjht.jfmall.util.CurrentSeesionUtil;
import com.zjht.jfmall.util.Valids;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * <p>
 * app用户表 服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {
    private final Logger logger = LoggerFactory.getLogger(AppUserServiceImpl.class);

    @Autowired
    private AppUserMapper           appUserMapper;
    @Autowired
    private UserDao                 userDao;
    @Autowired
    private UserRewardMapper        userRewardMapper;
    @Autowired
    private AppUserInvitationMapper appUserInvitationMapper;
    @Autowired
    private ChannelInvitationMapper channelInvitationMapper;
    @Autowired
    private UserMailListMapper      userMailListMapper;
    @Autowired
    private AppUserBasicMapper      appUserBasicMapper;
    @Autowired
    private CallContactDetailMapper callContactDetailMapper;
    @Autowired
    private ApplyRecordService      applyRecordService;


    @Value(value = "${service_id}")
    private String SERVICE_ID;
    @Value(value = "${invitation_money}")
    private String INVITATION_MONEY;
    @Value(value = "${app_register_url}")
    private String APP_REGISTER_URL;

    @Override
    public boolean checkOne(AppUser appUser, String type) {
        Example          example  = new Example(AppUser.class);
        Example.Criteria criteria = example.createCriteria();
        switch (type) {
            case "phone":
                criteria.andEqualTo("phone", appUser.getPhone());
                break;
            case "wechat":
                criteria.andEqualTo("wechat", appUser.getWechat());
                break;
            case "qq":
                criteria.andEqualTo("qq", appUser.getQq());
                break;
            case "idCard":
                criteria.andEqualTo("idCard", appUser.getIdCard());
                break;
            case "bankNo":
                criteria.andEqualTo("bankNo", appUser.getBankNo());
                break;
        }
        List<AppUser> appUsers = appUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(appUsers)) {
            return true;
        }

        AppUser appUser1 = appUsers.get(0);
        if (appUser1.getId().equals(appUser.getId())) {
            return true;
        }

        return false;
    }

    @Override
    public AppUser checkId(String id) {
        Example          example  = new Example(AppUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        List<AppUser> appUsers = appUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(appUsers)) {
            return null;
        }
        return appUsers.get(0);
    }

    @Override
    public int updateAppUser(AppUser appUser) {
        appUserMapper.updateByPrimaryKeySelective(appUser);
        Example          example  = new Example(AppUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", appUser.getId());
        AppUser appUser1 = appUserMapper.selectByExample(example).get(0);

        //如果用户是被邀请注册的
        if (1 == appUser1.getIsInvited()) {
            Example          example1  = new Example(AppUserInvitation.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("beinvitedId", appUser.getId());
            List<AppUserInvitation> appUserInvitationList = appUserInvitationMapper.selectByExample(example1);
            if (!CollectionUtils.isEmpty(appUserInvitationList)) {
                AppUserInvitation appUserInvitation = appUserInvitationList.get(0);
                if ("1".equals(appUser1.getDataStatus())) {
                    appUserInvitation.setDataStatus("1");
                }
                if ("1".equals(appUser1.getIdenStatus())) {
                    appUserInvitation.setIdenStatus("1");
                }
                if ("1".equals(appUser1.getOperatorStatus())) {
                    appUserInvitation.setOperatorStatus("1");
                }

                //三个认证通过即给邀请者增加余额
                if ("1".equals(appUserInvitation.getDataStatus()) && "1".equals(appUserInvitation.getIdenStatus()) && "1".equals(appUserInvitation.getOperatorStatus())) {
                    if (appUserInvitation.getInvitationMoney() == 0) {
                        appUserInvitation.setInvitationMoney(Integer.valueOf(INVITATION_MONEY));
                        Example          example3  = new Example(AppUser.class);
                        Example.Criteria criteria3 = example3.createCriteria();
                        criteria3.andEqualTo("id", appUserInvitation.getInvitationId());
                        List<AppUser> appUserList = appUserMapper.selectByExample(example3);
                        if (!CollectionUtils.isEmpty(appUserList)) {
                            AppUser appUser2 = appUserList.get(0);
                            System.out.println("****************money"+Double.valueOf(INVITATION_MONEY));
                            appUser2.setBalance(appUser2.getBalance() + Double.valueOf(INVITATION_MONEY));
                            appUserMapper.updateByPrimaryKeySelective(appUser2);
                        }
                    }
                }
                appUserInvitationMapper.updateByPrimaryKeySelective(appUserInvitation);
            }

        }

        return 0;
    }

    @Override
    public AppUser userLogin(String phone, String pwd) {
        Example          example  = new Example(AppUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        criteria.andEqualTo("pwd", pwd);
        List<AppUser> appUsers = appUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(appUsers)) {
            return null;
        }
        return appUsers.get(0);
    }

    @Override
    public JsonResult register(AppUser appUser, String invitationType, String invitationId, String captcha) {
        if (StringUtils.isBlank(invitationType) || !("1".equals(invitationType) || "2".equals(invitationType) || "3".equals(invitationType))) {
            return new JsonResult(BusiStatus.INVITATION_TYPE.toString(), BusiStatus.INVITATION_TYPE.getReasonPhrase());
        }

        String phone = appUser.getPhone();
        String pwd   = appUser.getPwd();
        if (Valids.hasEmpty(phone,pwd, captcha)) {
            return new JsonResult(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
        }
        
        //检验验证码
        Captcha capt = CurrentSeesionUtil.getCaptha(phone);
        if (capt == null || !capt.isActive() || !captcha.equals(capt.getCaptcha())) {
            return new JsonResult(BusiStatus.SMS_CODE_ERROR.toString(), BusiStatus.SMS_CODE_ERROR.getReasonPhrase());
        }

        Example          example  = new Example(AppUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        List<AppUser> appUsers = appUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(appUsers)) {
            String  uuid     = UUID.randomUUID().toString().replace("-", "");
            AppUser appUser1 = new AppUser();
            appUser1.setId(uuid);
            appUser1.setInviteLink(APP_REGISTER_URL+uuid);
            appUser1.setPhone(phone);
            appUser1.setRegisterTime(new Date());
            appUser1.setStatus("0");
            appUser1.setBankStatus("0");
            appUser1.setOperatorStatus("0");
            appUser1.setDataStatus("0");
            appUser1.setIdenStatus("0");
            appUser1.setLoansStatus("0");
            appUser1.setIsInvited(0);
            appUser1.setPwd(pwd);
            List<Integer> list = getLoansList();
            Collections.shuffle(list);
            int loans = list.get(0);
            appUser1.setLoans(loans);
            //随机获取客服ID
            List<User> userList1 = userDao.getUserByRoleIdAndStatus(SERVICE_ID);
            if (!CollectionUtils.isEmpty(userList1)) {
                Collections.shuffle(userList1);
                String serviceId = userList1.get(0).getId();
                appUser1.setServiceId(serviceId);
            }

            //APP用户邀请
            if ("2".equals(invitationType)) {
                appUser1.setIsInvited(1);
                //邀请人信息
                if (StringUtils.isBlank(invitationId)) {
                    return new JsonResult(BusiStatus.ID_ERROR.toString(), BusiStatus.ID_ERROR.getReasonPhrase());
                }

                Example          example1  = new Example(AppUser.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andEqualTo("id", invitationId);
                List<AppUser> appUsers1 = appUserMapper.selectByExample(example1);
                if (CollectionUtils.isEmpty(appUsers1)) {
                    return new JsonResult(BusiStatus.ID_ERROR.toString(), BusiStatus.ID_ERROR.getReasonPhrase());
                }
                AppUser appUser2 = appUsers1.get(0);

                if (appUser2.getStatus().equals("1")) {
                    return new JsonResult(BusiStatus.ID_FROZEN.toString(), BusiStatus.ID_FROZEN.getReasonPhrase());
                }

                //新增app用户邀请记录
                AppUserInvitation appUserInvitation = new AppUserInvitation();
                appUserInvitation.setId(UUID.randomUUID().toString().replace("-", ""));
                appUserInvitation.setBeinvitedId(uuid);
                appUserInvitation.setInvitationId(invitationId);
                appUserInvitation.setInvitationTime(new Date());
                appUserInvitation.setOperatorStatus("0");
                appUserInvitation.setIdenStatus("0");
                appUserInvitation.setDataStatus("0");
                appUserInvitationMapper.insert(appUserInvitation);


            } else if ("3".equals(invitationType)) {
                appUser1.setIsInvited(2);
                //渠道商用户邀请
                if (StringUtils.isBlank(invitationId)) {
                    return new JsonResult(BusiStatus.ID_ERROR.toString(), BusiStatus.ID_ERROR.getReasonPhrase());
                }

                Example          example1  = new Example(User.class);
                Example.Criteria criteria1 = example1.createCriteria();
                criteria1.andEqualTo("id", invitationId);
                List<User> userList = userDao.selectByExample(example1);
                if (CollectionUtils.isEmpty(userList)) {
                    return new JsonResult(BusiStatus.ID_ERROR.toString(), BusiStatus.ID_ERROR.getReasonPhrase());
                }

                User user = userList.get(0);
                if (user.getStatus().equals("1")) {
                    return new JsonResult(BusiStatus.ID_FROZEN.toString(), BusiStatus.ID_FROZEN.getReasonPhrase());
                }

                //新增渠道商邀请记录
                ChannelInvitation channelInvitation = new ChannelInvitation();
                channelInvitation.setId(UUID.randomUUID().toString().replace("-", ""));
                channelInvitation.setInvitationTime(new Date());
                channelInvitation.setChannelBusinessId(invitationId);
                channelInvitation.setInvitationUserId(uuid);
                channelInvitationMapper.insertSelective(channelInvitation);
            }

            CurrentSeesionUtil.deleteCaptcha(phone);

            appUserMapper.insertSelective(appUser1);
            //自动生成订单
            applyRecordService.insert(appUser1, appUser1.getLoans());
            return new JsonResult(BusiStatus.SUCCESS.toString(), BusiStatus.SUCCESS.getReasonPhrase());

        } else {
            return new JsonResult(BusiStatus.PHONE_EXISTS.toString(), BusiStatus.PHONE_EXISTS.getReasonPhrase());
        }


    }

    @Override
    public JsonResult forgetPassword(String phone, String password, String captcha) {
        if (Valids.hasEmpty(phone, password, captcha)) {
            return new JsonResult(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
        }


        //检验验证码
        Captcha capt = CurrentSeesionUtil.getCaptha(phone);
        if (capt == null || !capt.isActive() || !captcha.equals(capt.getCaptcha())) {
            return new JsonResult(BusiStatus.SMS_CODE_ERROR.toString(), BusiStatus.SMS_CODE_ERROR.getReasonPhrase());
        }

        Example          example  = new Example(AppUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        List<AppUser> appUsers = appUserMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(appUsers)) {
            AppUser appUser = appUsers.get(0);
            appUser.setPwd(password);
            appUserMapper.updateByPrimaryKeySelective(appUser);
            CurrentSeesionUtil.deleteCaptcha(phone);
            return new JsonResult(BusiStatus.SUCCESS.toString(), BusiStatus.SUCCESS.getReasonPhrase());

        } else {
            return new JsonResult(BusiStatus.USER_NOT_EXISTS.toString(), BusiStatus.USER_NOT_EXISTS.getReasonPhrase());
        }
    }

    /**
     * 统计渠道商成功交易的客户
     *
     * @param user
     * @return
     */
    @Override
    public int successCount(User user) {
        return appUserMapper.successCount(user);
    }

    /**
     * 统计所有邀请注册的用户
     *
     * @param user
     * @return
     */
    @Override
    public int countByChannel(User user) {
        return appUserMapper.countByChannel(user);
    }

    @Override
    public PageInfo<AppUser> findPage(AppUser bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询参数
        //2.1创建条件对象
        Example          example  = new Example(AppUser.class);
        example.setOrderByClause(" register_time desc");
        Example.Criteria criteria = example.createCriteria();
        //2.2判断查询条件
        if (StringUtils.isNotBlank(bean.getName())) {
            criteria.andLike("name", "%" + bean.getName() + "%");
        }
        if (StringUtils.isNotBlank(bean.getQq())) {
            criteria.andLike("qq", "%" + bean.getQq() + "%");
        }
        if (StringUtils.isNotBlank(bean.getPhone())) {
            criteria.andLike("phone", "%" + bean.getPhone() + "%");
        }
        if (StringUtils.isNotBlank(bean.getWechat())) {
            criteria.andLike("wechat", "%" + bean.getWechat() + "%");
        }
        if (StringUtils.isNotBlank(bean.getExchangeTimeBegin())) {
            criteria.andCondition("register_time >= '" + bean.getExchangeTimeBegin() + " 00:00:00'");
        }
        if (StringUtils.isNotBlank(bean.getExchangeTimeEnd())) {
            criteria.andCondition("register_time <= '" + bean.getExchangeTimeEnd() + " 23:59:59'");
        }
        return new PageInfo<AppUser>(appUserMapper.selectByExample(example));
    }

    @Override
    public void updateStatus(String id, int status) {
        appUserMapper.updateStatus(id, status);
    }

    @Override
    public AppUser findById(String id) {
        return appUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<AppUser> findBeinvitedPage(AppUser bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询参数
        return new PageInfo<AppUser>(appUserMapper.findBeinvitedPage(bean));
    }

    @Override
    public JsonResult userbasic(Map<String, String> postMap) {
        //必填参数校验
        boolean flag = CheckUtils.checkParam(postMap, "mobile", "cell_phone", "user_basic", "call_contact_detail");
        if (!flag) {
            return new JsonResult(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
        }

        String mobile = postMap.get("mobile");
        logger.info("运行商认证手机********mobile"+mobile);
        if (StringUtils.isBlank(mobile)) {
            return new JsonResult(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
        }

        //根据手机号获取用户信息
        Example          example  = new Example(AppUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", mobile);
        List<AppUser> appUsers = appUserMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(appUsers)) {
            return new JsonResult(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
        }
        AppUser appUser   = appUsers.get(0);
        String  appuserId = appUser.getId();

        //运营商已认证
        if ("1".equals(appUser.getOperatorStatus())) {
            return new JsonResult(BusiStatus.SUCCESS.toString(), BusiStatus.SUCCESS.getReasonPhrase());
        }

        //通讯录未认证
        if ("0".equals(appUser.getIdenStatus())) {
            return new JsonResult(BusiStatus.NO_IDEN.toString(), BusiStatus.NO_IDEN.getReasonPhrase());
        }


        //增加用户个人信息
        JSONArray jsonArray  = JSONArray.parseArray(postMap.get("cell_phone"));
        JSONArray jsonArray1 = JSONArray.parseArray(postMap.get("user_basic"));
        JSONArray jsonArray2 = JSONArray.parseArray(postMap.get("call_contact_detail"));
        if (CollectionUtils.isEmpty(jsonArray) || CollectionUtils.isEmpty(jsonArray1) || CollectionUtils.isEmpty(jsonArray2)) {
            return new JsonResult(BusiStatus.NO_DATA.toString(), BusiStatus.NO_DATA.getReasonPhrase());
        }

        if("0".equals(appUser.getDataStatus()))
        jsonArray.addAll(jsonArray1.size(), jsonArray1);

        Example          example10  = new Example(AppUserBasic.class);
        Example.Criteria criteria10 = example10.createCriteria();
        criteria10.andEqualTo("appUserId", appuserId);
        if(CollectionUtils.isEmpty(appUserBasicMapper.selectByExample(example10))){
            AppUserBasic appUserBasic = CheckUtils.mapToBean(jsonArray, AppUserBasic.class);
            appUserBasic.setId(UUID.randomUUID().toString().replace("-", ""));
            appUserBasic.setAppUserId(appuserId);
            appUserBasicMapper.insertSelective(appUserBasic);
        }


        //取跟用户通讯录的前一百条相匹配的数据
        Example          example1  = new Example(UserMailList.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("appUserId", appuserId);
        List<UserMailList>  userMailListList = userMailListMapper.selectByExample(example1);
        Map<String, String> userMailMap      = new HashMap<>();
        for (UserMailList userMailList : userMailListList) {
            userMailMap.put(userMailList.getMobile(), userMailList.getName());
        }

        List<CallContactDetail> callContactDetails = new ArrayList<>();
        int size=jsonArray2.size()>100?100:jsonArray2.size();
        for (int i = 0; i < size; i++) {
            Gson              gson              = new Gson();
            Object            o                 = jsonArray2.get(i);
            String            json              = CheckUtils.underlineToCamel(JSONObject.toJSONString(o));

            //匹配通讯录
            CallContactDetail callContactDetail = gson.fromJson(CheckUtils.underlineToCamel(JSONObject.toJSONString(o)), CallContactDetail.class);
            if (StringUtils.isNotBlank(callContactDetail.getPeerNum()) && userMailMap.containsKey(callContactDetail.getPeerNum())) {
                callContactDetail.setName(userMailMap.get(callContactDetail.getPeerNum()));
            }
            callContactDetail.setAppUserId(appuserId);
            callContactDetail.setId(UUID.randomUUID().toString().replace("-", ""));
            callContactDetails.add(callContactDetail);
        }
        if(!CollectionUtils.isEmpty(callContactDetails)){
            callContactDetailMapper.insertData(callContactDetails);
        }else {
            return new JsonResult(BusiStatus.NO_DATA.toString(), BusiStatus.NO_DATA.getReasonPhrase());
        }


        //更新运营商认证状态
        appUser.setOperatorStatus("1");
        updateAppUser(appUser);

        return new JsonResult(BusiStatus.SUCCESS.toString(), BusiStatus.SUCCESS.getReasonPhrase());
    }

    @Override
    public PageInfo<AppUser> findLoanPage(AppUser bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<AppUser>(appUserMapper.findLoanPage(bean));
    }

    @Override
    public AppUser findLoan(AppUser appUser) {
        return appUserMapper.findLoan(appUser);
    }

    @Override
    public Integer countByTime(String startTime, String endTime) {
        return appUserMapper.countByTime(startTime, endTime);
    }

    @Override
    public Integer countNumByTime(String startTime, String endTime) {
        return appUserMapper.countNumByTime(startTime, endTime);
    }

    @Override
    public int countByServiceId(String id) {
        return appUserMapper.countByServiceId(id);
    }

    @Override
    public int countByServiceIds(User user) {
        return appUserMapper.countByServiceIds(user);
    }

    /**
     * 随机获取贷款额度 10w-30w
     *
     * @return
     */
    private static List<Integer> getLoansList() {
        List<Integer> list = new ArrayList<>();
        for (int i = 10000; i <= 30000; i += 1000) {
            list.add(i);
        }
        return list;
    }

    @Override
    public String findInvitationName(String beinvitedId) {
        return appUserMapper.findInvitationName(beinvitedId);
    }

    @Override
    public int countDownloas(User user) {
        return appUserMapper.countDownloas(user);
    }
}
