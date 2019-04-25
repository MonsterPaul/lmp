package com.zjht.jfmall.marketing.service;

import com.alibaba.fastjson.JSONObject;

import com.google.gson.Gson;
import com.zjht.jfmall.entity.MobileVerify;
import com.zjht.jfmall.marketing.bean.LoginResp;
import com.zjht.jfmall.marketing.bean.QueryUserRegeistResp;
import com.zjht.jfmall.marketing.bean.ResultDesc;
import com.zjht.jfmall.marketing.bean.TokenValidateResp;
import com.zjht.jfmall.marketing.common.GlobalConfig;
import com.zjht.jfmall.marketing.utils.DESUtil;
import com.zjht.jfmall.marketing.utils.ExStringUtils;
import com.zjht.jfmall.util.MessageDigestHelper;
import com.zjht.jfmall.util.VerifyUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户AUTH系列接口
 * @author lijunjie
 *
 */
public class UserAuthService {
	private static final Logger log = LoggerFactory.getLogger(UserAuthService.class);
	/**
	 * 获取账户验证码接口
	 * （该接口向指定的用户发送短信形式的账户验证码，用于该账户的转账、提现、支付等操作，以及忘记支付密码操作。）
	 * @param mobileNum 手机号码
	 * @return 
	 */
	public static ResultDesc getAccountcode(String mobileNum){
		ResultDesc resultDesc=null;
		// appId 营销账户平台分配
		String appId = GlobalConfig.getConfigValue(GlobalConfig.APPID);
		// appKey 营销账户平台分配
		String appKey = GlobalConfig.getConfigValue(GlobalConfig.APPKEY);
		// 时间戳
		String timeStamp = String.valueOf(new Date().getTime());
		// 随机码
		String nonce = ExStringUtils.getVerifyCode(8);
		
		//（appId,appKey,nonce,timeStamp）参数值排序：
		String[] values = {appId,appKey,nonce,timeStamp};
		Arrays.sort(values);
		String sortStr = ExStringUtils.concat(values,"");

		log.info("（appId,appKey,nonce,timeStamp）值顺序明文："+sortStr);
		String signature = DigestUtils.md5Hex(sortStr);
		log.info("（appId,appKey,nonce,timeStamp）值顺序MD5："+signature);
		
		JSONObject app = new JSONObject();
		app.put("appId", appId);
		app.put("timeStamp", timeStamp);
		app.put("nonce", nonce);
		app.put("signature", signature);
		InterfaceService interfaceService = new InterfaceService();
		//获取账户验证码
		JSONObject jsonMobileMsg=new JSONObject();
		jsonMobileMsg.put("app", app);
		jsonMobileMsg.put("mobileNum", mobileNum);
		String result = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/get_accountcode",jsonMobileMsg);
		System.out.println(result);
		if (StringUtils.isNotBlank(result)) {
			try {
				Gson gson=new Gson();
				resultDesc=gson.fromJson(result, ResultDesc.class);
			} catch (Exception e) {
				resultDesc=new ResultDesc();
				resultDesc.setErrcode(1L);
				resultDesc.setErrmsg("获取账户验证码接口响应值转换json格式错误！");
				log.error("获取账户验证码接口响应值转换json格式错误！异常详情："+e.getMessage());
				return null;
			}
		}
		return resultDesc;
	}
	/**
	 * 获取短信验证码接口
	 * @param mobile 手机号码
	 * @return 调用结果
	 */
	public static ResultDesc sendMobileCode(String mobile){
		ResultDesc resultDesc = new ResultDesc();;
		//异步请求返回结果
		if (!VerifyUtils.isMobile(mobile)) {//判断手机号是否符合规范
			resultDesc.setErrcode(1L);
			resultDesc.setErrmsg("请输入正确的手机号码");
		} else {
			JSONObject requestJson = new JSONObject();
			//拼装app参数
			JSONObject app = MarketingServiceHelper.getAppJsonObject();
			requestJson.put("app", app);
			//拼装手机参数
			requestJson.put("mobileNum", mobile);

			InterfaceService interfaceService = new InterfaceService();
			log.info("调用营销账户平台发送验证码请求参数："+ requestJson.toString());
			//调用接口发送短信
			String result = interfaceService.doProcess(GlobalConfig.getConfigValue("gateway.interface.url")+"/gw/userAuth/get_smscode",requestJson);
			log.info("调用营销账户平台发送验证码返回结果：" + result);
			if (StringUtils.isNotBlank(result)) {
				try {
					Gson gson=new Gson();
					resultDesc=gson.fromJson(result, ResultDesc.class);
				} catch (Exception e) {
					resultDesc=new ResultDesc();
					resultDesc.setErrcode(1L);
					resultDesc.setErrmsg("调用营销账户平台发送验证码接口响应值转换json格式错误！");
					log.error("调用营销账户平台发送验证码接口响应值转换json格式错误！异常详情："+e.getMessage());
					return null;
				}
			}
		}
		return resultDesc;
	}
	/**
	 * 调用营销平台注册接口
	 * @return ResultDesc注册结果
	 */
	public static ResultDesc userRegiste(String mobileNum, String smsCode, String password, String paymentCodeMD5){
		ResultDesc resultDesc=null;
		JSONObject requestJson=new JSONObject();
    	//拼装app参数
    	JSONObject app = MarketingServiceHelper.getAppJsonObject();
    	requestJson.put("app", app);
    	//拼装注册参数
    	JSONObject registe = new JSONObject();
    	registe.put("mobileNum", mobileNum);
    	registe.put("password", password);
    	registe.put("smsCode", smsCode);
    	registe.put("paymentCodeMD5", paymentCodeMD5);    	
    	requestJson.put("login", registe);    
    	log.info("调用营销账户平台--注册--请求参数："+ requestJson.toString());
    	InterfaceService interfaceService = new InterfaceService();
    	//登陆营销账户平台注册
    	String registeResult = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/register",requestJson);
    	log.info("调用营销账户平台--注册--返回结果："+ registeResult);
    	if (StringUtils.isNotBlank(registeResult)) {
			try {
				Gson gson=new Gson();
				resultDesc=gson.fromJson(registeResult, ResultDesc.class);
			} catch (Exception e) {
				resultDesc=new ResultDesc();
				resultDesc.setErrcode(1L);
				resultDesc.setErrmsg("获取调用营销账户平台注册接口响应值转换json格式错误！");
				log.error("获取调用营销账户平台注册接口响应值转换json格式错误！异常详情："+e.getMessage());
				return resultDesc;
			}
		}
		return resultDesc;
	}
	/**
	 * uber用户注册接口
	 * @author huangshipiao
	 * @time   2015-9-17 上午10:05:49 
	 * @param mobileNum
	 * @param smsCode
	 * @param password
	 * @param paymentCodeMD5
	 * @return
	 */
	public static ResultDesc userRegisteForUber(String mobileNum, String smsCode, String password, String paymentCodeMD5){
		ResultDesc resultDesc=null;
		JSONObject requestJson=new JSONObject();
    	//拼装app参数
    	JSONObject app = MarketingServiceHelper.getAppJsonObject();
    	requestJson.put("app", app);
    	//拼装注册参数
    	JSONObject registe = new JSONObject();
    	registe.put("mobileNum", mobileNum);
    	registe.put("password", password);
    	registe.put("smsCode", smsCode);
    	registe.put("paymentCodeMD5", paymentCodeMD5);
    	String[] attrs={"UBER"};
    	registe.put("attrs",attrs);//uber用户注册标识
    	requestJson.put("login", registe);   
    	log.info("调用营销账户平台--注册--请求参数："+ requestJson.toString());
    	InterfaceService interfaceService = new InterfaceService();
    	//登陆营销账户平台注册
    	String registeResult = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/register",requestJson);
    	log.info("调用营销账户平台--注册--返回结果："+ registeResult);
    	if (StringUtils.isNotBlank(registeResult)) {
			try {
				Gson gson=new Gson();
				resultDesc=gson.fromJson(registeResult, ResultDesc.class);
			} catch (Exception e) {
				resultDesc=new ResultDesc();
				resultDesc.setErrcode(1L);
				resultDesc.setErrmsg("获取调用营销账户平台注册接口响应值转换json格式错误！");
				log.error("获取调用营销账户平台注册接口响应值转换json格式错误！异常详情："+e.getMessage());
				return resultDesc;
			}
		}
		return resultDesc;
	}
	
	
	/**
	 * 调用营销平台修改登陆密码
	 * @param mobileNum 手机号
	 * @param passwordOld 旧密码
	 * @param passwordNew 新密码
	 * @return
	 */
	public static ResultDesc resetLoginPWD(String mobileNum, String passwordOld, String passwordNew){
		ResultDesc resultDesc=null;
		JSONObject requestJson=new JSONObject();
    	//拼装app参数
    	JSONObject app = MarketingServiceHelper.getAppJsonObject();
    	requestJson.put("app", app);
    	//拼装注册参数
    	JSONObject login = new JSONObject();
    	login.put("mobileNum", mobileNum);
    	login.put("oldpassword", passwordOld);
    	login.put("newpassword", passwordNew);
		// 时间戳
		String timeStamp = String.valueOf(System.currentTimeMillis());
		// 随机码
		String nonce = ExStringUtils.getVerifyCode(8);
		//appKey
		String appKey = GlobalConfig.getConfigValue(GlobalConfig.APPKEY);
		//(appKey,mobileNum,oldpassword,newpassword,nonce,timeStamp))参数值按字典顺序排序
		String[] values = {appKey,mobileNum,passwordOld,passwordNew,nonce,timeStamp};
		Arrays.sort(values);
		
		String sortStr = ExStringUtils.concat(values,"");
		log.info("login.signature 拼装前参数为 ：" + ExStringUtils.concat(values,"|"));
		String signature = MessageDigestHelper.encodeByMD5(sortStr,null);
    	login.put("timeStamp", timeStamp);
    	login.put("nonce", nonce);
    	login.put("signature", signature);
    	
    	requestJson.put("login", login);
    	
    	log.info("调用营销账户平台--修改登陆密码--请求参数："+ requestJson.toString());
    	InterfaceService interfaceService = new InterfaceService();
    	//调用营销账户平台修改登陆密码
    	String registeResult = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/resetPassword",requestJson);
    	log.info("调用营销账户平台--修改登陆密码--返回结果："+ registeResult);
    	if (StringUtils.isNotBlank(registeResult)) {
			try {
				Gson gson=new Gson();
				resultDesc=gson.fromJson(registeResult, ResultDesc.class);
			} catch (Exception e) {
				resultDesc=new ResultDesc();
				resultDesc.setErrcode(1L);
				resultDesc.setErrmsg("获取调用营销账户平台修改登陆密码接口响应值转换json格式错误！");
				log.error("获取调用营销账户平台修改登陆密码接口响应值转换json格式错误！异常详情："+e.getMessage());
				return resultDesc;
			}
		}
		return resultDesc;
	}
	/**
	 * 重置密码，通过手机验证码
	 * @param mobileNum 手机号
	 * @param smsCode 手机验证码
	 * @param passwordNew 新密码，MD5串
	 * @return
	 */
	public static ResultDesc resetPwdWithSmsCode(String mobileNum, String smsCode, String passwordNew){
		ResultDesc resultDesc=null;
		JSONObject requestJson=new JSONObject();
    	//拼装app参数
    	JSONObject app = MarketingServiceHelper.getAppJsonObject();
    	requestJson.put("app", app);
    	//拼装注册参数
    	JSONObject login = new JSONObject();
    	login.put("mobileNum", mobileNum);
    	login.put("smsCode", smsCode);
    	login.put("newpassword", passwordNew);
		// 时间戳
		String timeStamp = String.valueOf(System.currentTimeMillis());
		// 随机码
		String nonce = ExStringUtils.getVerifyCode(8);
		//appKey
		String appKey = GlobalConfig.getConfigValue(GlobalConfig.APPKEY);
		//(appKey,mobileNum,oldpassword,newpassword,nonce,timeStamp))参数值按字典顺序排序
		String[] values = {appKey,mobileNum,smsCode,passwordNew,nonce,timeStamp};
		Arrays.sort(values);
		
		String sortStr = ExStringUtils.concat(values,"");
		log.info("login.signature 拼装前参数为 ：" + ExStringUtils.concat(values,"|"));
		String signature = MessageDigestHelper.encodeByMD5(sortStr,null);
    	login.put("timeStamp", timeStamp);
    	login.put("nonce", nonce);
    	login.put("signature", signature);
    	
    	requestJson.put("login", login);
    	
    	log.info("调用营销账户平台--重置登陆密码--请求参数："+ requestJson.toString());
    	InterfaceService interfaceService = new InterfaceService();
    	//调用营销账户平台修改登陆密码
    	String registeResult = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/resetPasswordWithSmscode",requestJson);
    	log.info("调用营销账户平台--重置登陆密码--返回结果："+ registeResult);
    	if (StringUtils.isNotBlank(registeResult)) {
			try {
				Gson gson=new Gson();
				resultDesc=gson.fromJson(registeResult, ResultDesc.class);
			} catch (Exception e) {
				resultDesc=new ResultDesc();
				resultDesc.setErrcode(1L);
				resultDesc.setErrmsg("获取调用营销账户平台重置登陆密码接口响应值转换json格式错误！");
				log.error("获取调用营销账户平台重置登陆密码接口响应值转换json格式错误！异常详情："+e.getMessage());
				return resultDesc;
			}
		}
		return resultDesc;
	}

	/**
	 * 调用营销平台注册接口
	 * @param token 从App带过来的token值
	 * @return
	 */
	public static TokenValidateResp validateAppToken(String token){
		String ymappId =GlobalConfig.getConfigValue("youma.appId");
		String ymappKey = GlobalConfig.getConfigValue("youma.appKey");
		TokenValidateResp resultDesc=null;
		JSONObject requestJson=new JSONObject();
    	//拼装app参数
    	JSONObject app = MarketingServiceHelper.getAppJsonObject();
    	requestJson.put("app", app);
    	//拼装注册参数
    	JSONObject body = new JSONObject();
    	body.put("appId", ymappId);
    	body.put("token", token);
		// 时间戳
		String timeStamp = String.valueOf(System.currentTimeMillis());
		// 随机码
		String nonce = ExStringUtils.getVerifyCode(8);
		String[] values = {ymappId,ymappKey,nonce,timeStamp,token};
		Arrays.sort(values);
		
		String sortStr = ExStringUtils.concat(values,"");
		String signature = MessageDigestHelper.encodeByMD5(sortStr,null);
		body.put("timeStamp", timeStamp);
		body.put("nonce", nonce);
		body.put("signature", signature);
    	
    	requestJson.put("userToken", body);
    	log.info("调用营销账户平台--验证app token--请求参数："+ requestJson.toString());
    	InterfaceService interfaceService = new InterfaceService();
    	String registeResult = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/verify_token",requestJson);
    	log.info("调用营销账户平台---验证app token--返回结果："+ registeResult);
    	if (StringUtils.isNotBlank(registeResult)) {
			try {
				Gson gson=new Gson();
				resultDesc=gson.fromJson(registeResult, TokenValidateResp.class);
			} catch (Exception e) {
				resultDesc=new TokenValidateResp();
				resultDesc.setErrcode(1L);
				resultDesc.setErrmsg("获取调用营销账户平台验证app token接口响应值转换json格式错误！");
				log.error("获取调用营销账户平台验证app token接口响应值转换json格式错误！异常详情："+e.getMessage());
				return resultDesc;
			}
		}
		return resultDesc;
	}
	
	/**
	 * 模拟从APP登陆
	 * @param userName
	 * @param passwrod
	 * @return
	 */
	public static LoginResp userLoginApp(String userName, String passwrod){
		LoginResp loginResp=new LoginResp();
        JSONObject requestJson=new JSONObject();
        //拼装app参数
		JSONObject app = new JSONObject();
		// 时间戳
		String timeStamp = String.valueOf(System.currentTimeMillis());
		// 随机码
		String nonce = ExStringUtils.getVerifyCode(8);
		
		String ymappId =GlobalConfig.getConfigValue("youma.appId");
		String ymappKey = GlobalConfig.getConfigValue("youma.appKey");
		//（appId,appKey,nonce,timeStamp）参数值排序：
		String[] values = {ymappId,ymappKey,nonce,timeStamp};
		Arrays.sort(values);
		
		String sortStr = ExStringUtils.concat(values,"");
		log.info("app.signature 拼装前参数为 ：" + ExStringUtils.concat(values,"|"));
		String signature = MessageDigestHelper.encodeByMD5(sortStr,null);
		log.info("app.signature MD5为 ："+ signature);
		app.put("appId", ymappId);
		app.put("timeStamp", timeStamp);
		app.put("nonce", nonce);
		app.put("signature", signature);
        
        requestJson.put("app", app);
        //拼装登陆参数
        JSONObject login = new JSONObject();
        login.put("mobileNum", userName);
        passwrod = MessageDigestHelper.encodeByMD5(passwrod,null);
        login.put("password", passwrod);
        requestJson.put("login", login);
        log.info("调用营销账户平台发送APP登录请求参数："+ requestJson.toString());
        InterfaceService interfaceService = new InterfaceService();
        String loginResult = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/logina",requestJson);
        log.info("调用营销账户平台发送APP登录返回结果："+ loginResult);
        if (StringUtils.isNotBlank(loginResult)) {
			try {
				Gson gson=new Gson();
				loginResp=gson.fromJson(loginResult, LoginResp.class);
			} catch (Exception e) {
				loginResp.setErrcode(1L);
				loginResp.setErrmsg("调用营销账户平台发送登录接口响应值转换json格式错误！");
				log.error("调用营销账户平台发送登录接口响应值转换json格式错误！异常详情：",e);
				return loginResp;
			}
		}else{
			loginResp.setErrcode(1L);
			loginResp.setErrmsg("营销平台返回为空");
		}
		return loginResp;
	}
	/**重置用户支付密码DES接口(通过短信验证码)
	 * @param mobileNum 手机号码
	 * @param smsCode 手机验证码
	 * @param paymentPwd 新的支付密码
	 * @return
	 */
	public static ResultDesc resetPaymentCodeBySmsCode(String mobileNum, String smsCode, String paymentPwd){
		ResultDesc resultDesc=null;
		JSONObject requestJson=new JSONObject();
    	//拼装app参数
    	JSONObject app = MarketingServiceHelper.getAppJsonObject();
    	requestJson.put("app", app);
    	//拼装注册参数
    	JSONObject reset = new JSONObject();
    	reset.put("mobileNum", mobileNum);
    	reset.put("smsCode", smsCode);
		// appKey 营销账户平台分配
		String appKey = GlobalConfig.getConfigValue(GlobalConfig.APPKEY);
    	reset.put("newPaymentCodeDES", DESUtil.encryptDESwithCBC(MessageDigestHelper.encodeByMD5(paymentPwd, null), appKey));
    	
    	requestJson.put("reset", reset);    
    	log.info("重置用户支付密码--请求参数："+ requestJson.toString());
    	InterfaceService interfaceService = new InterfaceService();
    	//登陆营销账户平台注册
    	String registeResult = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/resetPaymentCodeBySmsCode",requestJson);
    	log.info("重置用户支付密码--返回结果："+ registeResult);
    	if (StringUtils.isNotBlank(registeResult)) {
			try {
				Gson gson=new Gson();
				resultDesc=gson.fromJson(registeResult, ResultDesc.class);
			} catch (Exception e) {
				resultDesc=new ResultDesc();
				resultDesc.setErrcode(1L);
				resultDesc.setErrmsg("重置用户支付密码接口响应值转换json格式错误！");
				log.error("重置用户支付密码接口响应值转换json格式错误！异常详情："+e.getMessage());
				return resultDesc;
			}
		}
		return resultDesc;
	}
	
	/**
	 * 修改用户支付密码DES接口(通过短信验证码和登录密码)
	 * @param mobileNum 手机号码
	 * @param smsCode 手机验证码
	 * @param paymentPwd 新的支付密码
	 * @param loginPwd 登录密码
	 * @return
	 */
	public static ResultDesc resetPaymentCodeBySmsCodeAndPassword(String mobileNum, String smsCode, String paymentPwd, String loginPwd){
		ResultDesc resultDesc=null;
		JSONObject requestJson=new JSONObject();
    	//拼装app参数
    	JSONObject app = MarketingServiceHelper.getAppJsonObject();
    	requestJson.put("app", app);
    	//拼装注册参数
    	JSONObject reset = new JSONObject();
    	reset.put("mobileNum", mobileNum);
    	reset.put("smsCode", smsCode);
		// appKey 营销账户平台分配
		String appKey = GlobalConfig.getConfigValue(GlobalConfig.APPKEY);
    	reset.put("newPaymentCodeDES", DESUtil.encryptDESwithCBC(MessageDigestHelper.encodeByMD5(paymentPwd, null), appKey));
//    	reset.put("password", DESUtil.encryptDes(MessageDigestHelper.encodeByMD5(loginPwd, null), appKey));
    	reset.put("password",  DESUtil.encryptDESwithCBC(MessageDigestHelper.encodeByMD5(loginPwd, null), appKey));
    	requestJson.put("reset", reset);    
    	log.info("重置用户支付密码--请求参数："+ requestJson.toString());
    	InterfaceService interfaceService = new InterfaceService();
    	//登陆营销账户平台注册
    	String registeResult = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/resetPaymentCodeBySmsCodeAndPassword",requestJson);
    	log.info("重置用户支付密码--返回结果："+ registeResult);
    	if (StringUtils.isNotBlank(registeResult)) {
			try {
				Gson gson=new Gson();
				resultDesc=gson.fromJson(registeResult, ResultDesc.class);
			} catch (Exception e) {
				resultDesc=new ResultDesc();
				resultDesc.setErrcode(1L);
				resultDesc.setErrmsg("重置用户支付密码接口响应值转换json格式错误！");
				log.error("重置用户支付密码接口响应值转换json格式错误！异常详情："+e.getMessage());
				return resultDesc;
			}
		}
		return resultDesc;
	}
	
	/**
	 * 调用营销平台判断是否已经注册用户，没有注册则给用户注册
	 * errcode":返回10104用户已经存在、 0 注册成功    10104和0都是成功的
	 * @author chenxingwang
	 */
	public static ResultDesc userRegisteByPhoneOnly(String mobileNum){
		ResultDesc resultDesc=null;
		// appId 营销账户平台分配
		String appId = GlobalConfig.getConfigValue(GlobalConfig.APPID);
		// appKey 营销账户平台分配
		String appKey = GlobalConfig.getConfigValue(GlobalConfig.APPKEY);
		// 时间戳
		String timeStamp = String.valueOf(new Date().getTime());
		// 随机码
		String nonce = ExStringUtils.getVerifyCode(8);
		//（appId,appKey,nonce,timeStamp）参数值排序：
		String[] values = {appId,appKey,nonce,timeStamp};
		Arrays.sort(values);
		String sortStr = ExStringUtils.concat(values,"");
		log.info("（appId,appKey,nonce,timeStamp）值顺序明文："+sortStr);
		String signature = DigestUtils.md5Hex(sortStr);
		log.info("（appId,appKey,nonce,timeStamp）值顺序MD5："+signature);
		JSONObject app = new JSONObject();
		app.put("appId", appId);
		app.put("timeStamp", timeStamp);
		app.put("nonce", nonce);
		app.put("signature", signature);
		InterfaceService interfaceService = new InterfaceService();
		//获取账户验证码
		JSONObject jsonMobileMsg=new JSONObject();
		JSONObject regist = new JSONObject();
		regist.put("mobileNum", mobileNum);	
		jsonMobileMsg.put("app", app);
		jsonMobileMsg.put("regist", regist);
		log.info("通过手机号用户注册状态请求参数是：" + jsonMobileMsg.toString());
		String result = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/registerByPhoneOnly",jsonMobileMsg);
		log.info("通过手机号用户注册状态返回结果是：" + result);
		if (StringUtils.isNotBlank(result)) {
			try {
				Gson gson=new Gson();
				resultDesc=gson.fromJson(result, ResultDesc.class);
			} catch (Exception e) {
				resultDesc=new ResultDesc();
				resultDesc.setErrcode(1L);
				resultDesc.setErrmsg("通过手机号用户注册接口响应值转换json格式错误！");
				log.error("通过手机号用户注册接口响应值转换json格式错误！异常详情："+e.getMessage());
				return null;
			}
		}
		return resultDesc;
	}
	
	/**
	 * 查询油马APP用户注册状态
	 * @param mobileNum  手机号
	 * @return
	 */
	public static QueryUserRegeistResp queryUserRegeist(String mobileNum){
		@SuppressWarnings("deprecation")
        HttpClient httpClient=new DefaultHttpClient();
		HttpPost post = new HttpPost(GlobalConfig.getConfigValue(GlobalConfig.QUERYUSERURL)+"/Api/Api/isReg.html");
		QueryUserRegeistResp entity = new QueryUserRegeistResp();
		LinkedHashMap<String, String> param = new LinkedHashMap<String, String>();
		param.put("mobileNum", mobileNum);
		Gson gson = new Gson();
		log.info("查询油马APP用户注册状态请求参数是：" + gson.toJson(param));
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : param.entrySet()) {
			nameValuePairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairList, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(post);
			HttpEntity httpEntity = httpResponse.getEntity();
			String responseStr = EntityUtils.toString(httpEntity,"utf-8");
			log.info("查询油马APP用户注册状态返回是" + responseStr);
			
			if (StringUtils.isNotBlank(responseStr)) {
				try {
					
					entity=gson.fromJson(responseStr, QueryUserRegeistResp.class);
				} catch (Exception e) {
					
					entity.setErrcode(1L);
					entity.setErrmsg("查询油马APP用户注册状态转换json格式错误！");
					log.error("查询油马APP用户注册状态接口响应值转换json格式错误！异常详情："+e.getMessage());
					
				}
			}
		} catch (Exception e) {
			entity.setErrcode(2L);
			entity.setErrmsg("查询油马APP用户注册状态接口异常！"+e.getMessage());
			log.error("查询油马APP用户注册状态接口异常！"+e.getMessage());
			e.printStackTrace();
		}
		return entity;
	}
	
	
	/**
	 * 查询泉州通 APP用户注册状态
	 * @param mobileNum  手机号
	 * @return
	 */
	public static QueryUserRegeistResp queryQzAppUserRegeist(String mobileNum){
		@SuppressWarnings("deprecation")
        HttpClient httpClient=new DefaultHttpClient();
		HttpPost post = new HttpPost(GlobalConfig.getConfigValue("quanzhoutongapp.url")+"/Api/Api/isReg.html");
		QueryUserRegeistResp entity = new QueryUserRegeistResp();
		LinkedHashMap<String, String> param = new LinkedHashMap<String, String>();
		param.put("mobileNum", mobileNum);
		Gson gson = new Gson();
		log.info("查询泉州通 APP用户注册状态请求参数是：" + gson.toJson(param));
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : param.entrySet()) {
			nameValuePairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairList, "UTF-8"));
			HttpResponse httpResponse = httpClient.execute(post);
			HttpEntity httpEntity = httpResponse.getEntity();
			String responseStr = EntityUtils.toString(httpEntity,"utf-8");
			log.info("查询泉州通 APP用户注册状态返回是" + responseStr);
			if (StringUtils.isNotBlank(responseStr)) {
				try {
					entity=gson.fromJson(responseStr, QueryUserRegeistResp.class);
				} catch (Exception e) {
					entity.setErrcode(1L);
					entity.setErrmsg("查询泉州通 APP用户注册状态转换json格式错误！");
					log.error("查询泉州通 APP用户注册状态接口响应值转换json格式错误！异常详情："+e.getMessage());
				}
			}
		} catch (Exception e) {
			entity.setErrcode(2L);
			entity.setErrmsg("查询泉州通 APP用户注册状态接口异常！"+e.getMessage());
			log.error("查询泉州通 APP用户注册状态接口异常！"+e.getMessage());
			e.printStackTrace();
		}
		return entity;
	}
	
	public static ResultDesc registerByPhoneOnly(String mobile){
		ResultDesc resultDesc=null;
		JSONObject requestJson=new JSONObject();
    	//拼装app参数
    	JSONObject app = MarketingServiceHelper.getAppJsonObject();
    	requestJson.put("app", app);
    	//拼装注册参数
    	JSONObject regist = new JSONObject();
    	regist.put("mobileNum", mobile);
		// appKey 营销账户平台分配
		requestJson.put("regist", regist);    
    	log.info("新手机注册--请求参数："+ requestJson.toString());
    	InterfaceService interfaceService = new InterfaceService();
    	//登陆营销账户平台注册
    	String registeResult = interfaceService.doProcess(GlobalConfig.getConfigValue(GlobalConfig.APPURL)+"/gw/userAuth/registerByPhoneOnly",requestJson);
    	log.info("新手机注册--返回结果："+ registeResult);
    	if (StringUtils.isNotBlank(registeResult)) {
			try {
				Gson gson=new Gson();
				resultDesc=gson.fromJson(registeResult, ResultDesc.class);
			} catch (Exception e) {
				resultDesc=new ResultDesc();
				resultDesc.setErrcode(1L);
				resultDesc.setErrmsg("新手机注册接口响应值转换json格式错误！");
				log.error("新手机注册接口响应值转换json格式错误！异常详情："+e.getMessage());
				return resultDesc;
			}
		}
		return resultDesc;
	}
	
	public static void main(String[] args){
//		String passwrodOld   = DigestUtils.md5Hex("19870921");
//		String passwrodNew = DigestUtils.md5Hex("123456");
//		ResultDesc resultResp=UserAuthService.resetLoginPWD("13229460282",passwrodOld,passwrodNew);
//		if (resultResp!=null) {
//			System.out.println("errcode:"+resultResp.getErrcode()+"|errmsg:"+resultResp.getErrmsg());
//		}else{
//			System.err.println("ResultDesc is null");
//		}
//		UserAuthService.getAccountcode("13229460282");
//		ResultDesc resultResp2 = UserAuthService.sendMobileCode("13229460282");
//		ResultDesc resultResp = UserAuthService.sendMobileCode("13229460282");
//		String passwrodNew = DigestUtils.md5Hex("123456");
//		ResultDesc resultResp2 = resetPwdWithSmsCode("13229460282","0093",passwrodNew);
//		HasAttrResp hasAttrResp = userHasAttr("13229460282","UBER");
//		TokenValidateResp tokenValidateResp = validateAppToken("6666b380dc2f5b5619900690cd6f111d");
//		System.out.println(resultResp2.toString());
//		resetPaymentCodeBySmsCode("13229460282", "215454", "198870921");
		//ResultDesc resultResp = UserAuthService.sendMobileCode("13538852364");
		//resetPaymentCodeBySmsCodeAndPassword("13538852364", "", "", "123456");
		/*QueryUserRegeistResp queryUserRegeist = queryUserRegeist("18237779640");
		System.out.println(queryUserRegeist.toString());*/
		//cheDuiRegister("15814519237","adc720y44724");
		//login(login_type_mobile,"15814519237","123456");
//		ResultDesc resultDesc = registerByPhoneOnly("18928932624");
//		System.out.println(resultDesc.toString());
		//"errcode":10104 0 都是正常的返回 ("13533134906","604800")
//		ResultDesc resultResp = UserAuthService.resetPwdWithSmsCode("13928931524","5763","123456");
//		System.out.println(resultResp.getErrmsg());
//		
//		QueryUserRegeistResp result=UserAuthService.queryQzAppUserRegeist("13428823905");
//		QueryUserRegeistResp result2=UserAuthService.queryUserRegeist("15811852031");
//		System.out.println(result2.getInfo());
	}
}
