package com.zjht.jfmall.proxy;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.xinyan.credit.rsa.RsaCodingUtil;
import com.xinyan.credit.util.HttpUtils;
import com.xinyan.credit.util.MD5Utils;
import com.xinyan.credit.util.SecurityUtil;
import com.xinyan.credit.util.UUIDGenerator;
import com.zjht.jfmall.util.DateTimeUtils;

import net.sf.json.JSONObject;

/**
 * 新颜请求代理客户端
 * @author lqf
 *
 */
@Component
public class RatingClient {
	
	@Value(value = "${url}")
	private  String url ;
	
	@Value(value = "${versions}")
	private  String versions;

	@Value(value = "${member.id}")
	private  String member_id;
	
	@Value(value = "${terminal.id}")
	private  String terminal_id ;
	
	@Value(value = "${pfx.name}")
	private  String pfx_name ;
	
	@Value(value = "${pfx.pwd}")
	private String pfx_pwd ;

//	private  String member_id = PropertyUtil.getPropertyValue("xy", "member.id");
	
	public  String request(HttpServletRequest request,Map<String,String> parameterMap) throws UnsupportedEncodingException{
		Map<String, String> headers = new HashMap<>();
		String PostString = null;
		String id_no = parameterMap.get("id_no");
		String id_name = parameterMap.get("id_name");
		String phone_no = parameterMap.get("phone_no");
		String bankcard_no = parameterMap.get("bankcard_no");
		log(" 原始数据:id_no:" + id_no + ",id_name:" + id_name + ",phone_no:" + phone_no + ",bankcard_no:" + bankcard_no);

		id_no = MD5Utils.encryptMD5(id_no.trim());	
		id_name = MD5Utils.encryptMD5(id_name.trim());
		bankcard_no = MD5Utils.encryptMD5(bankcard_no==null?null:bankcard_no.trim());
		phone_no = MD5Utils.encryptMD5(phone_no.trim());

		log("32位小写MD5加密后数据:id_no:" + id_no + ",id_name:" + id_name + ",phone_no:" + phone_no + ",bankcard_no:"
				+ bankcard_no);

		String trade_date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());// 订单日期
		String trans_id = UUIDGenerator.getUUID();// 必须入库 并且唯一 商户订单号 

		String XmlOrJson = "";
		/** 组装参数 **/
		Map<Object, Object> ArrayData = new HashMap<Object, Object>();
		ArrayData.put("member_id", member_id);
		ArrayData.put("terminal_id", terminal_id);
		ArrayData.put("trade_date", trade_date);
		ArrayData.put("trans_id", trans_id);
		ArrayData.put("phone_no", phone_no);
		ArrayData.put("bankcard_no", bankcard_no);
		ArrayData.put("versions", versions);
		ArrayData.put("encrypt_type", "MD5");// MD5：标准32位小写(推荐) SHA256：标准64位
		
		ArrayData.put("id_no", id_no);
        ArrayData.put("id_name", id_name);

		JSONObject jsonObjectFromMap = JSONObject.fromObject(ArrayData);
		XmlOrJson = jsonObjectFromMap.toString();
		log("====请求明文:" + XmlOrJson);

		/** base64 编码 **/
		String base64str = SecurityUtil.Base64Encode(XmlOrJson);
		base64str=base64str.replaceAll("\r\n", "");//重要 避免出现换行空格符
		log("base64str:" + base64str);
		/** rsa加密 **/
		String path =  "WEB-INF"+File.separator+"classes"+File.separator+"key"+File.separator + pfx_name;// 商户私钥
	    String pfxpath=request.getSession().getServletContext().getRealPath(path);
		File pfxfile = new File(pfxpath); 
		
		if (!pfxfile.exists()) {
			log("私钥文件不存在！");
			throw new RuntimeException("私钥文件不存在！");
		}
		String pfxpwd = pfx_pwd;

		String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str, pfxpath, pfxpwd);// 加密数据
		log("====加密串:" + data_content);
		log("url:" + url);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("member_id", member_id);
		params.put("terminal_id", terminal_id);
		params.put("data_type", "json");
		params.put("data_content", data_content);

		PostString = HttpUtils.doPostByForm(url, headers, params);
		log("请求返回：" + PostString);

		/** ================处理返回结果============= **/
		if (null==PostString || PostString.isEmpty()) {// 判断参数是否为空
			log("=====返回数据为空");
			throw new RuntimeException("返回数据为空");
		}
		return PostString;
	}
	
	public static void log(String msg) {
		System.out.println(DateTimeUtils.getCurrentDateStr("yyyy-MM-dd HH:mm:ss") + "\t: " + msg);
	}
	
}
