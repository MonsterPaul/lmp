//package com.zjht.jfmall.util;
//
//import com.alibaba.fastjson.JSONObject;
//import com.zjhtc.htm.message.serializable.factory.SimpleSmsMessageFactory;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 短信发送工具类
// * @author lijunjie
// *
// */
//public class SmsSenderUtil {
//
//	private static Logger logger = LoggerFactory.getLogger(SmsSenderUtil.class);
//	/**
//	 * 发送成功结果
//	 */
//	public static final String SUCCESS="success";
//	/**
//	 * 发送短信2.0 -- 方法只可以返回发送成功与否状态，不返回发送结果描述
//	 * 正常发送返回0
//	 * 发送失败返回-1
//	 * @param mobile 手机号
//	 * @param content 短信内容
//	 * @param smID 暂无使用-保留兼容1.0短信发送方法
//	 * @return
//	 */
//	public int send(String mobile, String content, long smID) {
//		int i=-1;
//		SimpleSmsMessageFactory factory = SimpleSmsMessageFactory.getFactory();
//		logger.info("给手机"+mobile+",发送短信:" + content);
//		factory.createOnlyMessage(mobile, content);//单个手机号短信发送
//		String respMsg = factory.send();
//		try {
//		    JSONObject respMsgJson= JSONObject.parseObject(respMsg);
//	        if ("00000".equals(respMsgJson.getString("stateCode"))&&respMsgJson.getJSONArray("smsList").size()>0&&"00000".equals(respMsgJson.getJSONArray("smsList").getJSONObject(0).getString("respCode"))) {
//	            i=0;
//	            logger.info("发送成功！");
//	        }else{
//	            logger.error("发送失败:" + respMsg);
//	        }
//        } catch (Exception e) {
//            logger.error("发送短信结果解析异常："+respMsg,e);
//        }
//        return i;
//	}
//	/**
//	 * 发送短信2.0
//	 * 发送成功返回：success 请使用SmsSenderUtil.SUCCESS.equals(result)确定短信是否发送正常
//	 * 发送错误放回错误详情：xxxxx
//	 * @param mobile 手机号
//	 * @param content 短信内容
//	 * @return result ==>正常发送：success 失败：返回错误详情
//	 */
//	public static String send(String mobile, String content){
//	    SimpleSmsMessageFactory factory = SimpleSmsMessageFactory.getFactory();
//        factory.createOnlyMessage(mobile, content);//单个手机号短信发送
//        String respMsg = factory.send();
//        logger.info("给手机"+mobile+",发送短信:" + content +"，发送结果==>"+respMsg);
//        try {
//            JSONObject respMsgJson = JSONObject.parseObject(respMsg);
//			if ("00000".equals(respMsgJson.getString("stateCode"))&&respMsgJson.getJSONArray("smsList").size()>0&&"00000".equals(respMsgJson.getJSONArray("smsList").getJSONObject(0).getString("respCode"))) {
//                return SUCCESS;
//            }else{
//                String m=respMsgJson.getString("stateCodeDesc");
//                if (respMsgJson.getJSONArray("smsList").size()>0&&!"00000".equals(respMsgJson.getJSONArray("smsList").getJSONObject(0).getString("respCode"))) {
//                    m=respMsgJson.getJSONArray("smsList").getJSONObject(0).getString("respMsg");
//                }
//                return StringUtils.isNotBlank(m)?m:"发送失败！";
//            }
//        } catch (Exception e) {
//            return "发送短信结果无法解析！";
//        }
//	}
//	public static void main(String[] args) {
//        String result=SmsSenderUtil.send("13538852364", "您的有油网的短信验证码为 123456 ，感谢您的支持。");
//        System.out.println(SmsSenderUtil.SUCCESS.equals(result));
//    }
//}
