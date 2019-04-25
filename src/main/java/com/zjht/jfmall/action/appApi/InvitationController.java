package com.zjht.jfmall.action.appApi;

import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.service.ChannelClickService;
import com.zjht.jfmall.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈邀请〉
 */
@Controller
@RequestMapping("/invitation")
public class InvitationController {
    private final Logger logger = LoggerFactory.getLogger(InvitationController.class);
    @Autowired
    private ChannelClickService channelClickService;
    //TODO 缺少跳转链接

    /**
     * 注册协议
     *
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/Untitled", method = {RequestMethod.GET})
    public String v_list() {
        return "Untitled-3";
    }

    /**
     * 注册协议
     *
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/appDownload.html", method = {RequestMethod.GET})
    public String list() {
        return "appDownload";
    }
    /**
     * app用户邀请链接跳转
     *
     * @param invitationId
     * @param modelMap
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/user", method = {RequestMethod.GET})
    public String v_list(String invitationId, ModelMap modelMap) {
        modelMap.addAttribute("invitationId", invitationId);
        return "appRegister";
    }
    /**
     * 渠道商邀请链接跳转
     *
     * @param request
     * @param invitationId 渠道商ID
     * @param modelMap
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/channel",method = {RequestMethod.GET})
    public String register(HttpServletRequest request, String invitationId, ModelMap modelMap) throws Exception {
        try {
            if (StringUtils.isNotBlank(invitationId)) {
                channelClickService.insert(RequestUtils.getClientIP(request), invitationId);
            }
            modelMap.addAttribute("invitationId", invitationId);
            return "disRegister";
        } catch (Exception e) {
            logger.error("InvitationController channel error", e);
            return "disRegister";
        }
    }

}