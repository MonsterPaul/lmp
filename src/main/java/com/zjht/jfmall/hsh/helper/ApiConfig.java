package com.zjht.jfmall.hsh.helper;


import com.zjht.jfmall.util.MessageDigestHelper;

/**
 * API接口属性文件中Key配置
 * @author lijunjie
 *
 */
public class ApiConfig {
	/**
	 * SHA-1签名
	 */
	public static String ALGORITHM="SHA-1";
	/**
	 * 终端编号
	 */
	public static String APPNO_KEY="esb.zjhtplatform.appNo";
	/**
	 * 终端接入密钥
	 */
	public static String APP_KEY="esb.zjhtplatform.key";
	/**
	 * 接口ip地址（含协议、地址、端口号、上下文）
	 */
	public static String APPURL_KEY="esb.zjhtplatform.ipAddr";
	/**
	 * 图片接口ip地址（含协议、地址、端口号、上下文）
	 */
	public static String APPURL_PIC_KEY="esb.zjhtplatform.pic.ipAddr";
	/**
	 * 版本1.0
	 */
	public static String VER_KEY="esb.zjhtplatform.ver.fir";
	/**
	 * 服务（所有商品信息列表查询）
	 */
	public static String SERVICE_QUERYALLCOUPONS="esb.zjhtplatform.service.queryAllCoupons";
	/**
	 * 服务（广告区商品信息列表查询）
	 */
	public static String SERVICE_QUERYADVERTCOUPONS="esb.zjhtplatform.service.queryAdvertCoupons";
	/**
	 * 服务（商品类别名称查询）
	 */
	public static String SERVICE_QUERYALLCATEGORIES="esb.zjhtplatform.service.queryAllCategories";
	/**
	 * 服务（分类商品信息列表查询）
	 */
	public static String SERVICE_QUERYCATEGORYCOUPONLIST="esb.zjhtplatform.service.queryCategoryCouponList";
	/**
	 * 服务（商品明细查询）
	 */
	public static String SERVICE_QUERYCOUPONDETAIL="esb.zjhtplatform.service.queryCouponDetail";
	/**
	 * 服务（商户门店查询）
	 */
	public static String SERVICE_QUERYSHOP="esb.zjhtplatform.service.queryShop";
	/**
	 * 服务（商品预下单）
	 */
	public static String SERVICE_ORDERCOUPONPRE="esb.zjhtplatform.service.orderCouponPre";
	/**
	 * 服务（订单信息列表查询）
	 */
	public static String SERVICE_QUERYORDERCOUPONLIST="esb.zjhtplatform.service.queryOrderCouponList";
	/**
	 * 服务（商品下单确认）
	 */
	public static String SERVICE_CONFIRMCOUPONORDER="esb.zjhtplatform.service.confirmCouponOrder";
	/*
	 * 服务（二维码信息列表查询）
	 */
	public static String SERVICE_QUERYBARCODELIST="esb.zjhtplatform.service.queryBarCodeList";
	/*
	 * 服务（二维码明细查询）
	 */
	public static String SERVICE_QUERYBARCODEDETAIL="esb.zjhtplatform.service.queryBarCodeDetail";
	/**
	 * 服务（图片查看）
	 */
	public static String SERVICE_VIEWPICFILECONTENT="esb.zjhtplatform.service.viewPicFileContent";
	/**
	 * 服务（用户积分兑换）
	 */
	public static String SERVICE_GETPOINTEXCHANGE="esb.zjhtplatform.service.getPointExchange";
	/**
	 * 服务（短信重发）
	 */
	public static String SERVICE_RESEND="esb.zjhtplatform.service.resend";
	/**
	 * 服务（查询辅助码明细接口(有油网之陶陶居项目)）
	 */
	public static String SERVICE_QUERYASSISTCODEDETAIL="esb.zjhtplatform.service.queryAssistCodeDetail";
	/**
	 * 服务（电子券验码消费接口(有油网之陶陶居项目)）
	 */
	public static String SERVICE_TICKETCONSUME="esb.zjhtplatform.service.ticketConsume";
	
	
	/**
	 * 天猫积分用户积分流水（第三方交易流水）yxy 2014-07-21
	 */
	public static String SERVICE_ADDTHIRDPARTYTRANSACTION="esb.zjhtplatform.service.addThirdPartyTransaction";
	/**
	 * 根据手机号获取手机信息，包括归属地和运营商。yxy 2014-08-6
	 */
	public static String SERVICE_GETMOBILEINFO="esb.zjhtplatform.service.getMobileInfo";
	
	/**
	 * 服务 手机短信发送
	 */
	public static String SERVICE_SENDMOBILESMS="esb.zjhtplatform.service.sendMobileSms";

	/**
	 * 终端接入Epay密钥
	 */
	public static String APP_KEY_EPAY="epay.zjhtplatform.appKey";
	/**
	 * 终端接入Epay appNo
	 */
	public static String APPNO_KEY_EPAY="epay.zjhtplatform.appNo";

	/**
	 * Epay接口ip地址
	 */
	public static String APPURL_KEY_EPAY="epay.zjhtplatform.ipAddr";

	/**
	 * 版本1.0
	 */
	public static String VER_KEY_EPAY="epay.zjhtplatform.ver.fir";
	
	/**
	 * Epay接口统一客户号
	 */
	public static String CUST_NO_EPAY="epay.zjhtplatform.custNo";
	
	/**
	 * EPAY油汇宝卡激活记录查询
	 */
	public static String APP_ESB_CARD_ACTIVE_RECORD_URL="epay.zjhtplatform.card.active.record.ipaddress";
	
	/**
	 * 有油网之上海油站发码接口
	 */
	public static String SERVICE_SEND_OIL_TICKET="esb.zjhtplatform.service.trade.ticketSend";
	
	/**
	 * HTTP服务接入ip地址
	 */
	public static String APPURL_CARD_KEYOFEPAY="epay.zjhtplatform.card.ipAddr";
	
	
	/**
	 * “油我发起”项目epay hprose 接口
	 */
	//epay hprose 开卡接口方法名
	public static String EPAY_HPROSE_OIL_OPEN_REAL_CARD_METHOD="esb.zjhtplatform.oil.openRealCard";
	//油站自主应答-客户号
	public static String EPAY_HPROSE_OIL_STATION_REPLY_CUSTNO="esb.zjhtplatform.oil.stationReply.custNo";
	//系统应答或人工处理-客户号
	public static String EPAY_HPROSE_OIL_NOT_STATION_REPLY_CUSTNO="esb.zjhtplatform.oil.notStationReply.custNo";
	
	/**
	 * 新版（2016）移动积分接口
	 */
	//区分哪个平台
    public static String SERVICE_REQUESTTYPE="esb.zjhtplatform.requestType";
    //web渠道
    public static String SERVICE_WEB_CHANNEL="esb.zjhtplatform.web_channel";
    //app渠道
    public static String SERVICE_APP_CHANNEL="esb.zjhtplatform.app_channel";
    //手机验证码
    public static String SERVICE_MOBILE_VALIDATION="esb.zjhtplatform.preOrder";
    //tcp协议URL
    public static String SERVICE_TCPURL="esb.zjhtplatform.tcpURL";
    //移动积分重发短信
    public static String SERVICE_CLOUDPAYSMSVERIFICATIONCODERESEND="esb.zjhtplatform.cloudPaySmsVerificationCodeResend";
    //广州移动积分兑换（发码）
    public static String SERVICE_CONFIRMEDORDER="esb.zjhtplatform.confirmedOrder";
    //余额查询
    public static String SERVICE_REDPACKETBALANCEQUERY="esb.zjhtplayform.redpacketBalanceQuery";
    //油我发起 秘钥
    public static String SERVICE_YDJF_KEY = "esb.zjhtplatform.ydjf.key";
    //油我发起重发短信 秘钥
    public static String SERVICE_YDJF_RESENDKEY                    = "esb.zjhtplatform.ydjf.resendKey";
    //油我发起 url
    public static String SERVICE_YDJF_URL = "esb.zjhtplatform.ydjf.url";
	// 赠送券码
	public static String SERCICE_YDJF_GIVEASSCODE = "esb.zjhtplatform.ydjf.sendGiveAssCode";

    //油我发起300代金券 赠送滴滴打车券 产品编码
    public static String SERVICE_DDCODE300 = "esb.zjhtplatform.servce.ydjf.ddcode300";
    //油我发起300代金券 赠送滴滴打车券 券码面值 单位为分
    public static String SERVICE_DDCODE300_PRICE = "esb.zjhtplatform.servce.ydjf.ddcode300.price";
    //油我发起500代金券  赠送滴滴打车券 产品编码
    public static String SERVICE_DDCODE500 = "esb.zjhtplatform.servce.ydjf.ddcode500";
    //油我发起300代金券 赠送滴滴打车券 券码面值 单位为分
    public static String SERVICE_DDCODE500_PRICE = "esb.zjhtplatform.servce.ydjf.ddcode500.price";

    //滴滴重发短信
    public static String SERVICE_DIDRESENDSMS = "esb.zjhtplatform.ydjf.didResendSMS";

    //理财广告图片
    public static String LCGG_PIC1 = "esb.zjhtplatform.lcgg.pic1";
    public static String LCGG_PIC2 = "esb.zjhtplatform.lcgg.pic2";
    public static String LCGG_PIC3 = "esb.zjhtplatform.lcgg.pic3";

    //卜蜂莲花发码接口ip地址
    public static String SERVICE_BFLH = "esb.zjhtplatform.service.bflhURL";

    //武汉中油发码key
	public static String WHSEND_KEY = "esb.zjhtplatform.whsend.key";
    //武汉中油发码url
	public static String WHSEND_URL = "esb.zjhtplatform.whsend.url";

	//汇生活交易查询接口请求URL
	public static String TRANSACTION_URL = "transaction_query_url";
	
	//汇生活交易查询接口请求秘钥
	public static String TRANSACTION_KEY = "transaction_query_key";
	//汇生活交易请求编号
	public static String TRANSACTION_NUMBER = "transaction_query_request_number";

    // 服务 汇生活券码查询(查询手机号下所有券码)
    public static String TICKET_QUERY_BY_MOBILE = "epay.zjhtplatform.service.ticketQuery.ticketQueryByMobile";
    // 汇生活券码查询(查询手机号下所有券码) url
    public static String TICKET_QUERY_URL = "epay.zjhtplatform.service.ticketQuery.url";
    // 汇生活券码查询(查询手机号下所有券码) 编码
    public static String TICKET_QUERY_ENCODING = "epay.zjhtplatform.service.ticketQuery.encoding";

    /**
     * 汇生活券码查询服务
     */
    public static String HSH_TICKET_QUERY                          = "esb.zjhtplatform.service.trade.ticketQuery";

    /**
     * 生成签名
     * @param key 终端接入密钥
     * @param msg msg参数（json格式）
     * @return
     */
    /**
	 * Epay接口ip地址
	 */
	public static String ChANGE_PWD_KEY_EPAY="epay.zjhtplatform.pwdIpAddr";
	/**
	 * Epay商户信息修改接口地址
	 */
	public static String CHANGE_SHOPINFO_KEY="epay.zjhtplatform.updateshopIpAddr";
	
	public static String genSign(String key, String msg){
		return MessageDigestHelper.encode(ALGORITHM, key+msg+key, null);
	}
}
