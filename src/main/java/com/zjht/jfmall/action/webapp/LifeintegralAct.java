package com.zjht.jfmall.action.webapp;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.action.common.strategy.util.StrategyConstant;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.common.dto.ResultFailDto;
import com.zjht.jfmall.common.dto.ResultSuccessDto;
import com.zjht.jfmall.common.web.Constants;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.annotation.Secured;
import com.zjht.jfmall.common.web.session.HttpSessionProvider;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.entity.ApiChannel;
import com.zjht.jfmall.entity.OrderExchange;
import com.zjht.jfmall.entity.Product;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.*;
import com.zjht.jfmall.util.RequestUtils;
import com.zjht.jfmall.util.VerifyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;

/**
 * 人寿积分action
 *
 * @author caozhaokui
 * @create 2018-01-02 15:37
 */
@Controller
public class LifeintegralAct {

    @Autowired
    private UserService           userService;
    @Autowired
    private ElectroniccodeService electroniccodeService;
    @Autowired
    private HttpSessionProvider   session;
    @Autowired
    private OrderExchangeService  orderExchangeService;
    @Autowired
    private ProductService        productService;
    @Autowired
    private ChannelService  channelService;
    @Autowired
    private MobileVerifyService   mobileVerifyService;
    @Autowired
    private SessionProvider       sessionProvider;

    private Logger log = LoggerFactory.getLogger(LifeintegralAct.class);

    // 微信商城配置的获取OpenID，子类可以覆盖这个属性
    public String user_name_openId = "JFMALL_OPENID";

    // 获取微信openID接口地址
//    public static String GET_OPENID_URLPATH = PropertyUtil.getPropertyValue("special", "weixin_getOpenid_url");


    @RequestMapping(value = "/lifeIntegral/index.html")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String openId    = request.getParameter("oid");
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (StringUtils.isBlank(openId) && userAgent.indexOf("micromessenger") > -1) {
            // 重定向去获取oid的值
            String params = getParamValuesStr(request);
            String state  = "";
            if (StringUtils.isNotBlank(params)) {
                state = user_name_openId + "#" + params;
            } else {
                state = user_name_openId;
            }
            try {
                state = URLEncoder.encode(state, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            return ("redirect:" + GET_OPENID_URLPATH + "&state=" + state + "#wechat_redirect");
        }
        session.setAttribute(request, null, StrategyConstant.OPENID_SESSION_KEY, openId);
        return "lifeIntegral/index";
    }

    /**
     * 兑换电子码
     *
     * @param request
     * @param response
     * @param model
     * @param mobile
     * @param code
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/exchangeCode.do")
    public ResultDto checkCode(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                               String mobile, String code, String verifyCode) {
        if (!VerifyUtils.isMobile(mobile)) {
            return new ResultFailDto("请填写正确的手机号");
        }
        if (StringUtils.isBlank(verifyCode)) {
            return new ResultFailDto("手机验证码为空");
        }
        if (!mobileVerifyService.verifyCode(mobile, verifyCode)) {
            return new ResultFailDto("手机验证码错误");
        }
        if (StringUtils.isBlank(code) || code.length() != 9) {
            return new ResultFailDto("请输入9位兑换码");
        }
        //核销电子码
        ResultDto<User> resultDto = electroniccodeService.exchangeCode(mobile, code);
        //将用户放入session
        session.setAttribute(request, response, Constants.MEMBER_SESSION_KEY, resultDto.getData());
        //更新验证码
        String ipAddr = RequestUtils.getClientIP(request);
        mobileVerifyService.updateVerifyInfo(mobile, ipAddr);

        return resultDto;
    }

    @Secured
    @RequestMapping(value = "/lifeIntegral/home.html")
    public String home(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (userAgent.indexOf("micromessenger") > -1) {
            model.addAttribute("isWechat", true);
        }
        //查询商品
        Product product = new Product();
        product.setOrderByClause(" sort desc,id desc");
        product.setStatus((byte) (Product.Status.NORMAL.getStatus()));
        PageInfo<Product> productPageInfo = productService.getProductList(product, 500, 1);
        model.addAttribute("products", productPageInfo.getList());
        return "lifeIntegral/home";
    }

    /**
     * @param request
     * @param response
     * @param model
     * @param productCode 产品编码
     * @return
     */
    @Secured
    @RequestMapping(value = "/lifeIntegral/exchange.html")
    public String exchangeHtml(HttpServletRequest request, HttpServletResponse response, ModelMap model, String productCode) {
        ResultDto<Product> resultDto = productService.productCheck(productCode);
        if (!resultDto.isSuccess()) {
            model.addAttribute("msg", resultDto.getMsg());
            return "common/fail";
        }
        model.addAttribute("product", resultDto.getData());
        return "lifeIntegral/exchange";
    }

    /**
     * 兑换商品
     *
     * @param request
     * @param response
     * @param model
     * @param productCode 商品编码
     * @param buyCount    商品数量
     * @return
     */
    @Secured
    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/exchange.do")
    public ResultDto exchange(HttpServletRequest request, HttpServletResponse response, ModelMap model, String productCode, Integer buyCount) {

        Object openId    = session.getAttribute(request, StrategyConstant.OPENID_SESSION_KEY);
        String userAgent = request.getHeader("user-agent").toLowerCase();
        if (openId == null && userAgent.indexOf("micromessenger") > -1) {
            return new ResultFailDto("00004", "openId为空");
        }
        buyCount = 1;
        if (productCode == null) {
            return new ResultFailDto("产品编码为空");
        }
        //库存验证
        ResultDto<Product> resultDto = productService.stockCheck(productCode, buyCount);
        if (!resultDto.isSuccess()) {
            model.addAttribute("msg", resultDto.getMsg());
            return resultDto;
        }
        Product product = resultDto.getData();
        //积分兑换
        ResultDto<OrderExchange> exchangeDto = orderExchangeService.exchange(product, buyCount);
        return exchangeDto;
    }

    @Secured
    @RequestMapping(value = "/lifeIntegral/result.html")
    public String resultHtml(HttpServletRequest request, HttpServletResponse response, ModelMap model, String orderNo) {
        OrderExchange orderExchange = orderExchangeService.findByOrderNo(orderNo);
        if (orderExchange == null) {
            model.addAttribute("msg", "订单不存在");
            return "common/fail";
        }
        Product product = productService.findById(orderExchange.getProductId());
        model.addAttribute("product", product);
        if (product != null) {
            if (Product.Type.CASH.getType() == product.getProductType()) {
                model.addAttribute("tip", "兑换金额会以微信红包的方式发放，请留意微信公众号。微信红包两个工作日内到账，若未到账，请联系客服4006630666（手机）。");
            } else if (Product.Type.JY.getType() == product.getProductType()) {
                model.addAttribute("tip", "本加油券两个工作日内到账，若未到帐，请联系客服4006630666（手机）。");
            } else {
                model.addAttribute("tip", "本电子券两个工作日内到账，若未到帐，请联系客服4006630666（手机）。");
            }
        }
        model.addAttribute("order", orderExchange);
        return "lifeIntegral/result";
    }

    /**
     * 取得参数串，形如name=xiaohei&age=18
     *
     * @param request
     * @return
     */
    public String getParamValuesStr(HttpServletRequest request) {
        StringBuilder       sb             = new StringBuilder();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = (String) parameterNames.nextElement();
            sb.append(name).append("=").append(request.getParameter(name));
            if (parameterNames.hasMoreElements()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    /**
     * 前端用户登录页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/lifeIntegral/userLogin.html")
    public String userLogin(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        WebSite.setParameters(request, model);
        List<ApiChannel> channelList = channelService.getList(null);
        model.addAttribute("channelList", channelList);
        return "lifeIntegral/userLogin";
    }

    /**
     * 处理前端用户登录
     *
     * @param request
     * @param response
     * @param mobile
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/userLogin.do")
    public ResultDto lifeIntegralLogin(HttpServletRequest request, HttpServletResponse response, String mobile, String password,
                                       String channelId) {
        if (StringUtils.isBlank(mobile)) {
            return new ResultFailDto("手机号码为空");
        }
        if (StringUtils.isBlank(password)) {
            return new ResultFailDto("密码为空");
        }
        if (StringUtils.isBlank(channelId)) {
            return new ResultFailDto("登陆渠道不能为空");
        }
        ResultDto<User> resultDto = userService.loginFront(mobile, password, channelId);
        if(!resultDto.isSuccess()){
            return new ResultFailDto(resultDto.getMsg());
        }
        return new ResultSuccessDto("登陆成功");
    }

    /**
     * 前端用户修改密码页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/lifeIntegral/updatePassword.html")
    public String updatePasswordHtml(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        WebSite.setParameters(request, model);
        return "lifeIntegral/updatePassword";
    }

    /**
     * 处理前端用户修改密码请求
     *
     * @param mobile
     * @param password
     * @param verifyCode
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/updatePassword.do")
    public ResultDto updatePassword(String mobile, String password, String verifyCode) {
        if (StringUtils.isBlank(mobile)) {
            return new ResultFailDto("手机号码为空");
        }
        if (StringUtils.isBlank(password)) {
            return new ResultFailDto("新密码为空");
        }
        if (!mobileVerifyService.verifyCode(mobile, verifyCode)) {
            return new ResultFailDto("手机验证码错误");
        }
        ResultDto resultDto = userService.updatePasswordByName(mobile,password);
        if(!resultDto.isSuccess()){
            return resultDto;
        }
        return new ResultSuccessDto("修改成功");
    }
}
