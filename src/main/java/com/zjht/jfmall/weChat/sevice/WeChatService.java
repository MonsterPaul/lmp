package com.zjht.jfmall.weChat.sevice;

import com.zjht.jfmall.common.dto.CommApiException;
import com.zjht.jfmall.weChat.request.CommRequest;
import com.zjht.jfmall.weChat.request.QueryRedPackageRequest;
import com.zjht.jfmall.weChat.request.RedPackageRequest;
import com.zjht.jfmall.weChat.response.CommResponse;
import com.zjht.jfmall.weChat.response.RedPackageResponse;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-01-07 10:17
 */
public interface WeChatService {
    /**
     * 发送微信现金红包
     * @param request
     * @return
     * @throws CommApiException
     */
    public CommResponse sendRedPacket(RedPackageRequest request) throws CommApiException;

    /**
     * 查询微信现金红包信息
     * @param request
     * @return
     * @throws CommApiException
     */
    public RedPackageResponse queryRedPacket(QueryRedPackageRequest request) throws CommApiException;
}
