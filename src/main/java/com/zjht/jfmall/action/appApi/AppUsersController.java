package com.zjht.jfmall.action.appApi;

import com.alibaba.fastjson.JSONObject;
import com.zjht.jfmall.common.dto.JsonResult;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.entity.*;
import com.zjht.jfmall.enums.BusiStatus;
import com.zjht.jfmall.service.*;
import com.zjht.jfmall.util.*;
import com.zjht.jfmall.util.lib.SmsSendUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 〈app端用户相关〉
 */
@RestController
@RequestMapping("/appUsers")
public class AppUsersController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(AppUsersController.class);

    @Autowired
    private AppUserService           appUserService;
    @Autowired
    private AppUserDepositService    appUserDepositService;
    @Autowired
    private UserService              userService;
    @Autowired
    private UserFeedbackService      userFeedbackService;
    @Autowired
    private ApplyRecordService       applyRecordService;
    @Autowired
    private AppUserInvitationService appUserInvitationService;
    @Autowired
    private AdvertisementService     advertisementService;
    @Autowired
    private AllLoanService           allLoanService;
    @Autowired
    private UserApplyLoanService     userApplyLoanService;
    @Autowired
    private AllLoanChickService      allLoanChickService;

    /**
     * 发送验证码
     *
     * @param phone 手机号
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/sendMs", method = RequestMethod.POST)
    public JsonResult sendMs(String phone) {
        try {
            if (Valids.hasEmpty(phone)) {
                return new JsonResult(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }

//            if (!Valids.isPhone(phone)) {
//                return new JsonResult(BusiStatus.PHONE_INVALID.toString(), BusiStatus.PHONE_INVALID.getReasonPhrase());
//            }

            Captcha capt = CurrentSeesionUtil.getCaptha(phone);
            if (null != capt && !capt.isExpire()) {
                return new JsonResult(BusiStatus.SMS_IP_TOO_OFTEN.toString(), BusiStatus.SMS_IP_TOO_OFTEN.getReasonPhrase());

            }
            Random random = new Random();
            String result = "";
            for (int i = 0; i < 6; i++) {
                result += random.nextInt(10);
            }
            CurrentSeesionUtil.setCaptcha(phone, result);
            System.out.println(result);
            String msg = "【九信金融】您的验证码是" + result + ",有效时间" + Captcha.MAX_ACTIVE_TIME + "分钟。";
            SmsSendUtil.sendMs(phone, msg);

            return renderSuccess();
        } catch (Exception e) {
            logger.error("AppUsersController sendMs error", e);
            return renderServerError();
        }
    }

    /**
     * APP用户注册
     *
     * @param appUser        用户信息
     * @param invitationType 注册类型
     * @param invitationId   邀请人ID
     * @param captcha        验证码
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResult register(AppUser appUser, String invitationType, String invitationId, String captcha) {
        try {
            return appUserService.register(appUser, invitationType, invitationId, captcha);
        } catch (Exception e) {
            logger.error("AppUsersController register error", e);
            return renderServerError();
        }
    }

    /**
     * 用户忘记密码
     *
     * @param phone    手机号
     * @param password 新密码
     * @param captcha  验证码
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public JsonResult forgetPassword(String phone, String password, String captcha) {
        try {
            return appUserService.forgetPassword(phone, password, captcha);
        } catch (Exception e) {
            logger.error("AppUsersController forgetPassword error", e);
            return renderServerError();
        }
    }


    /**
     * 用户登录
     *
     * @param phone 手机号
     * @param pwd   密码
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public JsonResult userLogin(String phone, String pwd) {
        try {
            if (StringUtils.isBlank(phone) || StringUtils.isBlank(pwd)) {
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }

            AppUser appUser = appUserService.userLogin(phone, pwd);
            if (appUser == null) {
                return renderServerError(BusiStatus.PHONE_OR_PWD_ERROE.toString(), BusiStatus.PHONE_OR_PWD_ERROE.getReasonPhrase());
            }

            if ("1".equals(appUser.getStatus())) {
                return renderServerError(BusiStatus.USER_NOT_LOGIN.toString(), BusiStatus.USER_NOT_LOGIN.getReasonPhrase());
            }

            String              wechat = userService.findById(appUser.getServiceId()).getWechat();
            Map<String, Object> map    = new HashMap<>();
            map.put("serviceWeChat", wechat);
            map.put("appUser", appUser);
            appUser.setPwd(null);
            appUser.setIsLogin("1");
            appUserService.updateAppUser(appUser);

            return renderSuccess(map);
        } catch (Exception e) {
            logger.error("AppUsersController userLogin error", e);
            return renderServerError();
        }

    }

    /**
     * 获取用户信息
     *
     * @param id
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/getUserDetail", method = RequestMethod.POST)
    public JsonResult getUserDetail(String id) {
        try {
            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            AppUser             appUser = JSONObject.parseObject(JSONObject.toJSON(result.getData()).toString(), AppUser.class);
            String              wechat  = userService.findById(appUser.getServiceId()).getWechat();
            Map<String, Object> map     = new HashMap<>();
            appUser.setPwd(null);
            map.put("serviceWeChat", wechat);
            map.put("appUser", appUser);

            return renderSuccess(map);
        } catch (Exception e) {
            logger.error("AppUsersController getUserDetail error", e);
            return renderServerError();
        }

    }

    /**
     * 用户更新密码
     *
     * @param id
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    public JsonResult updatePwd(String id, String pwd, String newPwd) {
        try {
            if (StringUtils.isBlank(newPwd) || StringUtils.isBlank(pwd)) {
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }

            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            AppUser appUser = JSONObject.parseObject(JSONObject.toJSON(result.getData()).toString(), AppUser.class);
            if (!pwd.equals(appUser.getPwd())) {
                return renderServerError(BusiStatus.PASSWORD_ERROR.toString(), BusiStatus.PASSWORD_ERROR.getReasonPhrase());
            }
            appUser.setPwd(newPwd);
            appUserService.updateAppUser(appUser);

            return renderSuccess();
        } catch (Exception e) {
            logger.error("AppUsersController updatePwd error", e);
            return renderServerError();
        }

    }


    /**
     * 更新用户信息
     *
     * @param id 用户ID
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/updateUserDetail", method = RequestMethod.POST)
    public JsonResult updateUserDetail(AppUser appUser) {
        try {
            JsonResult result = checkUser(appUser.getId());
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            //微信唯一校验
            if (StringUtils.isNotBlank(appUser.getWechat())) {
                boolean flag = appUserService.checkOne(appUser, "wechat");
                if (!flag) {
                    return renderServerError(BusiStatus.WECHAT_EXISTS.toString(), BusiStatus.WECHAT_EXISTS.getReasonPhrase());
                }
            }

            //QQ唯一校验
            if (StringUtils.isNotBlank(appUser.getQq())) {
                boolean flag = appUserService.checkOne(appUser, "qq");
                if (!flag) {
                    return renderServerError(BusiStatus.QQ_EXISTS.toString(), BusiStatus.QQ_EXISTS.getReasonPhrase());
                }
            }

            //身份证号码唯一校验
            if (StringUtils.isNotBlank(appUser.getWechat())) {
                boolean flag = appUserService.checkOne(appUser, "idCard");
                if (!flag) {
                    return renderServerError(BusiStatus.IDCARD_EXISTS.toString(), BusiStatus.IDCARD_EXISTS.getReasonPhrase());
                }
            }

            //微信,qq,姓名,身份证号码均填写才可认证个人资料
            if (StringUtils.isNotBlank(appUser.getWechat()) || StringUtils.isNotBlank(appUser.getName()) || StringUtils.isNotBlank(appUser.getIdCard()) || StringUtils.isNotBlank(appUser.getQq())) {
                appUser.setDataStatus("1");
            }
            AppUser appUser1 = JSONObject.parseObject(JSONObject.toJSON(result.getData()).toString(), AppUser.class);
            appUser.setBalance(appUser1.getBalance());
            appUser.setPwd(appUser1.getPwd());
            appUser.setPhone(appUser1.getPhone());
            appUser.setStatus(appUser1.getStatus());
            appUser.setServiceId(appUser1.getServiceId());
            appUserService.updateAppUser(appUser);

            return renderSuccess();
        } catch (Exception e) {
            logger.error("AppUsersController updateUserDetail error", e);
            return renderServerError();
        }

    }

    /**
     * 用户绑定银行卡
     *
     * @param appUser
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/bindBankCard", method = RequestMethod.POST)
    public JsonResult bindBankCard(AppUser appUser) {
        try {
            JsonResult result = checkUser(appUser.getId());
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            if (StringUtils.isBlank(appUser.getBankNo()) || StringUtils.isBlank(appUser.getBankAddress()) || StringUtils.isBlank(appUser.getBankPerson()) || StringUtils.isBlank(appUser.getBankPhone())) {
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }

            //银行卡唯一校验
            boolean flag = appUserService.checkOne(appUser, "bankNo");
            if (!flag) {
                return renderServerError(BusiStatus.BANKNO_EXISTS.toString(), BusiStatus.BANKNO_EXISTS.getReasonPhrase());
            }


            //更新用户银行卡信息以及认证状态
            AppUser appUser1 = JSONObject.parseObject(JSONObject.toJSON(result.getData()).toString(), AppUser.class);
            appUser.setBalance(appUser1.getBalance());
            appUser.setBankStatus("1");
            appUser.setPwd(appUser1.getPwd());
            appUser.setPhone(appUser1.getPhone());
            appUser.setStatus(appUser1.getStatus());
            appUser.setServiceId(appUser1.getServiceId());
            appUserService.updateAppUser(appUser);

            return renderSuccess();
        } catch (Exception e) {
            logger.error("AppUsersController bindBankCard error", e);
            return renderServerError();
        }

    }

    /**
     * 获取用户余额
     *
     * @param id 用户ID
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/getBalance", method = RequestMethod.POST)
    public JsonResult getBalance(String id) {
        try {
            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            AppUser authUser = JSONObject.parseObject(JSONObject.toJSON(result.getData()).toString(), AppUser.class);

            return renderSuccess(authUser.getBalance() == null ? 0 : authUser.getBalance());
        } catch (Exception e) {
            logger.error("AppUsersController getBalance error", e);
            return renderServerError();
        }

    }

    /**
     * 用户申请提现
     *
     * @param id 用户ID
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/cashWithdrawal", method = RequestMethod.POST)
    public JsonResult cashWithdrawal(String id, String money) {
        try {
            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }
            if (StringUtils.isBlank(money) || !VerifyUtils.isNumber(money)) {
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());

            }

            double money1 = Double.valueOf(money);

            if (money1 < 100) {
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }
            AppUser appUser = JSONObject.parseObject(JSONObject.toJSON(result.getData()).toString(), AppUser.class);

            if (appUser.getBankStatus().equals("0")) {
                return renderServerError(BusiStatus.NO_BANK.toString(), BusiStatus.NO_BANK.getReasonPhrase());
            }

            double balance = appUser.getBalance();
            if (100 > balance) {
                return renderServerError(BusiStatus.BALANCE_INSUFFICIENT.toString(), BusiStatus.BALANCE_INSUFFICIENT.getReasonPhrase());
            }

            if (money1 > balance) {
                return renderServerError(BusiStatus.TOO_MONEY.toString(), BusiStatus.TOO_MONEY.getReasonPhrase());
            }
            appUser.setBalance(balance - money1);

            appUserDepositService.insert(appUser, money1);

            return renderSuccess(appUser.getBalance());
        } catch (Exception e) {
            logger.error("AppUsersController cashWithdrawal error", e);
            return renderServerError();
        }

    }

    /**
     * 用户提现信息
     *
     * @param id 用户ID
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/getCashWithdrawal", method = RequestMethod.POST)
    public JsonResult getCashWithdrawal(String id, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            List<AppUserDeposit> appUserDepositList = appUserDepositService.getCashWithdrawal(id, pageNum, pageSize);
            if (CollectionUtils.isEmpty(appUserDepositList)) {
                return renderServerError(BusiStatus.NO_DATA.toString(), BusiStatus.NO_DATA.getReasonPhrase());
            }

            return renderSuccess(appUserDepositList);
        } catch (Exception e) {
            logger.error("AppUsersController getCashWithdrawal error", e);
            return renderServerError();
        }

    }


    /**
     * 新增用户反馈意见
     *
     * @param id 用户ID
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/userFeedBack", method = RequestMethod.POST)
    public JsonResult getBalance(UserFeedback userFeedback) {
        try {
            JsonResult result = checkUser(userFeedback.getUserId());
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            AppUser appUser = JSONObject.parseObject(JSONObject.toJSON(result.getData()).toString(), AppUser.class);

            userFeedback.setId(UUID.randomUUID().toString().replace("-", ""));
            userFeedback.setCreateTime(new Date());
            userFeedback.setProcessingState("1");
            userFeedback.setCustomerId(appUser.getServiceId());
            userFeedbackService.insert(userFeedback);

            return renderSuccess();
        } catch (Exception e) {
            logger.error("AppUsersController userFeedBack error", e);
            return renderServerError();
        }

    }

    /**
     * 用户反馈信息列表
     *
     * @param id 用户ID
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/getUserFeedBack", method = RequestMethod.POST)
    public JsonResult getUserFeedBack(String id, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        try {
            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            List<UserFeedback> userFeedbackList = userFeedbackService.getUserFeedBackList(id, pageNum, pageSize);
            if (CollectionUtils.isEmpty(userFeedbackList)) {
                return renderServerError(BusiStatus.NO_DATA.toString(), BusiStatus.NO_DATA.getReasonPhrase());
            }
            return renderSuccess(userFeedbackList);
        } catch (Exception e) {
            logger.error("AppUsersController getUserFeedBack error", e);
            return renderServerError();
        }

    }

    /**
     * 用户领取贷款额度
     *
     * @param id 用户ID
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/getLoans", method = RequestMethod.POST)
    public JsonResult getLoans(String id) {
        try {
            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            AppUser appUser = JSONObject.parseObject(JSONObject.toJSON(result.getData()).toString(), AppUser.class);


            if ("0".equals(appUser.getDataStatus())) {
                return renderServerError(BusiStatus.NO_USER_DATA.toString(), BusiStatus.NO_USER_DATA.getReasonPhrase());
            }

            if ("0".equals(appUser.getIdenStatus())) {
                return renderServerError(BusiStatus.NO_IDEN.toString(), BusiStatus.NO_IDEN.getReasonPhrase());
            }

            if ("0".equals(appUser.getOperatorStatus())) {
                return renderServerError(BusiStatus.NO_OPERATOR.toString(), BusiStatus.NO_OPERATOR.getReasonPhrase());
            }


            return renderSuccess(appUser.getLoans());
        } catch (Exception e) {
            logger.error("AppUsersController getLoans error", e);
            return renderServerError();
        }

    }

    /**
     * 用户提交贷款信息
     *
     * @param id 用户ID
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/saveLoans", method = RequestMethod.POST)
    public JsonResult saveLoans(String id, String loans) {
        try {
            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }
            if (StringUtils.isBlank(loans) || !VerifyUtils.isNumber(loans)) {
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());

            }
            double  loans1  = Double.valueOf(loans);
            AppUser appUser = JSONObject.parseObject(JSONObject.toJSON(result.getData()).toString(), AppUser.class);


//            if ("0".equals(appUser.getDataStatus())) {
//                return renderServerError(BusiStatus.NO_USER_DATA.toString(), BusiStatus.NO_USER_DATA.getReasonPhrase());
//            }
//
//            if ("0".equals(appUser.getIdenStatus())) {
//                return renderServerError(BusiStatus.NO_IDEN.toString(), BusiStatus.NO_IDEN.getReasonPhrase());
//            }
//
//            if ("0".equals(appUser.getOperatorStatus())) {
//                return renderServerError(BusiStatus.NO_OPERATOR.toString(), BusiStatus.NO_OPERATOR.getReasonPhrase());
//            }
//
//            if (appUser.getLoans() < loans1) {
//                return renderServerError(BusiStatus.TOO_LOANS.toString(), BusiStatus.TOO_LOANS.getReasonPhrase());
//
//            }
            ApplyRecord applyRecord = applyRecordService.insert(appUser, loans1);

            return renderSuccess(applyRecord);
        } catch (Exception e) {
            logger.error("AppUsersController saveLoans error", e);
            return renderServerError();
        }

    }

    /**
     * 获取用户申请贷款信息
     *
     * @param id 用户ID
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/getUserLoansList", method = RequestMethod.POST)
    public JsonResult getUserLoansList(String id, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            List<ApplyRecord> applyRecordList = applyRecordService.getUserLoansList(id, pageNum, pageSize);
            if (CollectionUtils.isEmpty(applyRecordList)) {
                return renderServerError(BusiStatus.NO_DATA.toString(), BusiStatus.NO_DATA.getReasonPhrase());
            }
            return renderSuccess(applyRecordList);
        } catch (Exception e) {
            logger.error("AppUsersController getUserLoansList error", e);
            return renderServerError();
        }

    }

    /**
     * 获取用户邀请注册信息
     *
     * @param id 用户ID
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/getUserInvitationList", method = RequestMethod.POST)
    public JsonResult getUserInvitationList(String id, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }

            List<AppUserInvitation> invitationList = appUserInvitationService.getUserInvitationList(id, pageNum, pageSize);
            if (CollectionUtils.isEmpty(invitationList)) {
                return renderServerError(BusiStatus.NO_DATA.toString(), BusiStatus.NO_DATA.getReasonPhrase());
            }
            return renderSuccess(invitationList);
        } catch (Exception e) {
            logger.error("AppUsersController getUserInvitationList error", e);
            return renderServerError();
        }

    }

    /**
     * 获取广告信息
     *
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/getAdvertisementList", method = RequestMethod.POST)
    public JsonResult getAdvertisementList() {
        try {
            List<Advertisement> advertisementList = advertisementService.getAdvertisementList();
            if (CollectionUtils.isEmpty(advertisementList)) {
                return renderServerError(BusiStatus.NO_DATA.toString(), BusiStatus.NO_DATA.getReasonPhrase());
            }

            return renderSuccess(advertisementList);
        } catch (Exception e) {
            logger.error("AppUsersController getAdvertisementList error", e);
            return renderServerError();
        }

    }


    @NoNeedAuth
    @RequestMapping(value = "/getNum", method = RequestMethod.POST)
    public JsonResult getNum() {
        try {
            return renderSuccess("0");
        } catch (Exception e) {
            logger.error("AppUsersController getNum error", e);
            return renderServerError();
        }

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

    @NoNeedAuth
    @RequestMapping(value = "/updateAndroid", method = RequestMethod.POST)
    public JsonResult updateAndroid() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("content", "优化认证流程，修改提示内容");
            map.put("size", "7.34M");
            map.put("version", "108");
            map.put("url", "http://download.renrenxin.top/app-jxjr.apk");
            return renderSuccess(map);
        } catch (Exception e) {
            logger.error("AppUsersController updateAndroid error", e);
            return renderServerError();
        }

    }

    @NoNeedAuth
    @RequestMapping(value = "/updateIos", method = RequestMethod.POST)
    public JsonResult updateIos() {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("content", "");
            map.put("size", "");
            map.put("version", "");
            map.put("url", "");
            return renderSuccess(map);
        } catch (Exception e) {
            logger.error("AppUsersController updateIos error", e);
            return renderServerError();
        }

    }


    /**
     * 获取贷超列表信息
     *
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/getAllLoanList", method = RequestMethod.POST)
    public JsonResult getAllLoantList(AllLoan allLoan, @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
        try {
            List<AllLoan> allLoanList = allLoanService.getAllLoanList(allLoan, pageNum, pageSize);
            if (CollectionUtils.isEmpty(allLoanList)) {
                return renderServerError(BusiStatus.NO_DATA.toString(), BusiStatus.NO_DATA.getReasonPhrase());
            }

            return renderSuccess(allLoanList);
        } catch (Exception e) {
            logger.error("AppUsersController getAllLoantList error", e);
            return renderServerError();
        }

    }

    /**
     * 获取贷超信息
     *
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/getAllLoanById", method = RequestMethod.POST)
    public JsonResult getAllLoanById(String loanId) {
        try {
            if (StringUtils.isBlank(loanId)) {
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }
            AllLoan allLoan = allLoanService.findById(loanId);
            if (allLoan == null) {
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }
            if(allLoan.getStatus()==1){
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }
            return renderSuccess(allLoan);
        } catch (Exception e) {
            logger.error("AppUsersController getAllLoanById error", e);
            return renderServerError();
        }

    }

    /**
     * 新增贷超浏览信息
     *
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/saveLoanClick", method = RequestMethod.POST)
    public JsonResult saveLoanClick(String id, String loanId) {
        try {
            if (StringUtils.isBlank(loanId)) {
                return renderServerError(BusiStatus.PARAM_ERROR.toString(), BusiStatus.PARAM_ERROR.getReasonPhrase());
            }
            allLoanChickService.insert(id, loanId);
            return renderSuccess();
        } catch (Exception e) {
            logger.error("AppUsersController saveLoanClick error", e);
            return renderServerError();
        }

    }

    /**
     * 新增贷超申请信息
     *
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/saveUserApplyLoan", method = RequestMethod.POST)
    public JsonResult saveUserApplyLoan(String id, String loanId) {
        try {
            JsonResult result = checkUser(id);
            if (!BusiStatus.SUCCESS.toString().equals(result.getStatus())) {
                return result;
            }
            return userApplyLoanService.saveUserApplyLoan(id, loanId);
        } catch (Exception e) {
            logger.error("AppUsersController saveUserApplyLoan error", e);
            return renderServerError();
        }

    }
}