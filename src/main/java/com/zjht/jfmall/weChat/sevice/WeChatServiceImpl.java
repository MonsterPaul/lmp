package com.zjht.jfmall.weChat.sevice;

import com.alibaba.fastjson.JSONObject;
import com.zjht.jfmall.common.dto.ApiConstants;
import com.zjht.jfmall.common.dto.CommApiException;
import com.zjht.jfmall.weChat.helper.RequestHandler;
import com.zjht.jfmall.weChat.helper.WXPayConfig;
import com.zjht.jfmall.weChat.request.CommRequest;
import com.zjht.jfmall.weChat.request.QueryRedPackageRequest;
import com.zjht.jfmall.weChat.request.RedPackageRequest;
import com.zjht.jfmall.weChat.response.RedPackageResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-01-07 10:20
 */
@Service
public class WeChatServiceImpl implements WeChatService {
    @Autowired
    private WXPayConfig wxPayConfig;

    private Logger log = LoggerFactory.getLogger(WeChatServiceImpl.class);

    @Override
    public RedPackageResponse sendRedPacket(RedPackageRequest request) {

        RedPackageResponse response = new RedPackageResponse();
        try {
            response = doSendRedPacket(request, wxPayConfig.getRedpackUrl());
        } catch (Exception e) {
            log.error("红包发送请求异常：", e);
            response.setStatus("20001");
            response.setMsg("request参数错误，请求未发起");
        }
        return response;
    }

    @Override
    public RedPackageResponse queryRedPacket(QueryRedPackageRequest request) throws CommApiException {
        RedPackageResponse response = new RedPackageResponse();
        try {
            response = doSendRedPacket(request, wxPayConfig.getRedpackQueryUrl());
        } catch (Exception e) {
            log.error("红包查询请求异常：", e);
            response.setStatus("20001");
        }
        return response;
    }

    private RedPackageResponse doSendRedPacket(CommRequest request, String url) {
        RedPackageResponse response = new RedPackageResponse();
        Map map = null;
        try {
            map = BeanUtils.describe(request);
        } catch (Exception e) {
            log.error("参数异常", e);
            response.setStatus(ApiConstants.FAIL);
            response.setMsg("参数异常");
            return response;
        }
        map.remove("class");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = entry.getKey().toString();
            String val = entry.getValue()==null?"":entry.getValue().toString();
            nvps.add(new BasicNameValuePair(key,val));
        }
        //加密
        String sign = RequestHandler.createMd5Sign(wxPayConfig.getRedpackPass(), map);
        log.info("sign:" + sign);
        nvps.add(new BasicNameValuePair("sign",sign));
        CloseableHttpResponse response2 = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps,"UTF-8"));
            response2 = httpclient.execute(httpPost);
            HttpEntity entityResp = response2.getEntity();
            JSONObject jsonObject = JSONObject.parseObject(entityResp.getContent(),JSONObject.class);
            log.info("请求返回结果：" + jsonObject);
            response.setData(jsonObject);
            if(jsonObject!=null){
                String result_code = jsonObject.getString("result_code");
                if("SUCCESS".equals(result_code)){
                    response.setStatus(ApiConstants.SUCCESS);
                }
            }
            EntityUtils.consume(entityResp);
        }catch (IOException e){
            response.setStatus(ApiConstants.FAIL);
            response.setMsg("网络IO异常");
        }finally {
            if(response2 != null){
                try {
                    response2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }
}
