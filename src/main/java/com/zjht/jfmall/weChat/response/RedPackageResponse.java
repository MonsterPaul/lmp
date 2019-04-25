package com.zjht.jfmall.weChat.response;

import com.alibaba.fastjson.JSONObject;
import com.zjht.jfmall.common.dto.ApiConstants;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-01-07 10:25
 */
public class RedPackageResponse extends CommResponse<JSONObject> {

    @Override
    public boolean isSuccess() {
        return ApiConstants.SUCCESS.equals(getStatus());
    }
}
