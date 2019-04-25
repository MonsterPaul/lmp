package com.zjht.jfmall.action.webapp;

import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.common.dto.ResultFailDto;
import com.zjht.jfmall.common.dto.ResultSuccessDto;
import com.zjht.jfmall.dao.MobileVerifyDao;
import com.zjht.jfmall.marketing.bean.ResultDesc;
import com.zjht.jfmall.marketing.service.UserAuthService;
import com.zjht.jfmall.service.MobileVerifyService;
import com.zjht.jfmall.util.RequestUtils;
import com.zjht.jfmall.util.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-01-09 19:28
 */
@Controller
public class IndexAct {
    @Autowired
    private MobileVerifyService mobileVerifyService;

    /**
     * 发送手机验证码
     *
     * @param request
     * @param response
     * @param model
     * @param mobile
     */
    @ResponseBody
    @RequestMapping(value = "/mobile/sendCode.jspx", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultDto sendVerifyCode(HttpServletRequest request, HttpServletResponse response, ModelMap model, String mobile) {
        if (!VerifyUtils.isMobile(mobile)) {
            return new ResultFailDto("请输入正确的手机号");
        } else {
            String ipAddr = RequestUtils.getClientIP(request);
            Map<String, String> data = mobileVerifyService.verifySend(mobile, ipAddr);
            if ("1".equals(data.get("result"))) {
                mobileVerifyService.sendVerifyCode(mobile, ipAddr);
                return new ResultSuccessDto("发送成功");
            } else {
                return new ResultFailDto(data.get("msg"));
            }
        }
    }
    /**
     * 发送营销平台验证码
     * @param request
     * @param response
     * @param model
     * @param mobile
     */
    @ResponseBody
    @RequestMapping(value = "/mobile/sendMarketCode.jspx", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultDto sendMarketCode(HttpServletRequest request, HttpServletResponse response, ModelMap model, String mobile) {
        if (!VerifyUtils.isMobile(mobile)) {
            return new ResultFailDto("请输入正确的手机号");
        } else {
            String ipAddr = RequestUtils.getClientIP(request);
            Map<String, String> data = mobileVerifyService.verifySend(mobile, ipAddr);
            if ("1".equals(data.get("result"))) {
                ResultDesc resultDesc = UserAuthService.sendMobileCode(mobile);
                if(resultDesc==null){
                    return new ResultFailDto(data.get("系统繁忙，请稍后再试！"));
                }else if(0L==(resultDesc.getErrcode())){
                    //发送成功，记录状态
                    mobileVerifyService.updateVerifyInfo(mobile, ipAddr);
                }else{
                    return new ResultFailDto(resultDesc.getErrmsg());
                }
                return new ResultSuccessDto("发送成功");
            } else {
                return new ResultFailDto(data.get("msg"));
            }
        }
    }
    /**
     * 手机验证码验证
     *
     * @param request
     * @param response
     * @param model
     * @param mobile
     */
    @ResponseBody
    @RequestMapping(value = "/mobile/verifyCode.jspx", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultDto verifyCode(HttpServletRequest request, HttpServletResponse response, ModelMap model, String mobile, String code) {
        if (!VerifyUtils.isMobile(mobile)) {
            return new ResultFailDto("请输入正确的手机号");
        } else {
            if (mobileVerifyService.verifyCode(mobile, code)) {
                return new ResultSuccessDto("验证通过");
            } else {
                return new ResultFailDto("验证码错误");
            }
        }
    }
}
