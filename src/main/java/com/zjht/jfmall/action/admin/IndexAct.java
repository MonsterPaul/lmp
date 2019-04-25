package com.zjht.jfmall.action.admin;

import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.common.dto.ResultFailDto;
import com.zjht.jfmall.common.web.Constants;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.common.web.threadvariable.RequestThread;
import com.zjht.jfmall.entity.ElectroniCode;
import com.zjht.jfmall.entity.Menu;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.service.MenuService;
import com.zjht.jfmall.service.RoleService;
import com.zjht.jfmall.service.UserService;
import com.zjht.jfmall.util.ImgCode;
import com.zjht.jfmall.util.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;

@Controller
public class IndexAct {
    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private RoleService roleService;

    //日志记录
    private static Logger logger = LoggerFactory.getLogger(IndexAct.class);

    @NoNeedAuth
    @RequestMapping(value = "/index/first.html")
    public String first(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        model.addAttribute("user", sessionProvider.getUser());
        return "index/first";
    }

    @RequestMapping(value = "/index/index.html")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        return "index/index";
    }

    @NoNeedAuth
    @RequestMapping(value = "/index/login.html")
    public String loginHtml(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        WebSite.setParameters(request, model);
        return "index/login";
    }
    @NoNeedAuth
    @RequestMapping(value = "/index/home.html")
    public String home(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        //1.在session中获取用户的菜单权限列表数据
        List<Menu> menus = (List<Menu>)sessionProvider.getAttribute(Constants.MENU_LIST);
        if (menus == null) {//为空重新查询
            User user = sessionProvider.getUser();
            //1.查询用户所属的角色中是否包含超级管理员角色，如果包含则查询全部菜单即可
            if (roleService.countByUserIdAndName(user.getId(), Constants.ADMINISTRATOR) > 0) {
                menus = menuService.listAllExclusiveBtn();
            } else {//2.如果用户不是超级管理员，则查询对于的菜单
                menus = menuService.listMenuByUserId(user.getId());
            }
        }
        model.put("menus", menus);
        return "index/home";
    }

    @NoNeedAuth
    @RequestMapping(value = "/index/login.do")
    public @ResponseBody
    ResultDto login(String userName, String password, String logincode, ModelMap model) {
        return login(userName, password, logincode);
    }

    @NoNeedAuth
    @RequestMapping(value = "/index/logout.do")
    public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        //退出登录
        sessionProvider.logout();
        return "index/login";
    }
    @NoNeedAuth
    @RequestMapping("/index/getCode.do")
    public void  getCode(HttpServletRequest request,HttpServletResponse response,ModelMap mode) throws Exception{
        ImgCode imgCode=new ImgCode();
        imgCode.getRandcode(request,response);
    }

    @NoNeedAuth
    @RequestMapping(value = "/lifeIntegral/login.html")
    public String lifeIntegralLoginHtml(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        WebSite.setParameters(request, model);
        return "lifeIntegral/login";
    }
    @NoNeedAuth
    @RequestMapping(value = "/lifeIntegral/login.do")
    public @ResponseBody
    ResultDto lifeIntegralLogin(String userName, String password, String logincode, ModelMap model) {
        return login(userName, password, logincode);
    }
    @NoNeedAuth
    @RequestMapping(value = "/lifeIntegral/logout.do")
    public String lifeIntegralLogout(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        //退出登录
        sessionProvider.logout();
        return "lifeIntegral/login";
    }
    @RequestMapping(value = "/lifeIntegral/sendCode.html")
    public String lifeIntegralHome(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        User user = sessionProvider.getUser();
        if(user == null){
            return "lifeIntegral/login";
        }
        model.put("pointsMap", ElectroniCode.PointsMap());
        return "lifeIntegral/sendCode";
    }

    @NoNeedAuth
    @RequestMapping(value = "/lifeIntegral/selectAbandonedCode.html")
    public String lifeIntegralSelectAbandonedCode(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        User user = sessionProvider.getUser();
        if(user == null){
            return "lifeIntegral/login";
        }
        return "lifeIntegral/selectAbandonedCode";
    }

    private ResultDto login(String userName, String password, String logincode) {
        HttpServletRequest request = RequestThread.get();
        String codeInSession=(String) request.getSession(true).getAttribute(ImgCode.RANDOMCODEKEY);
//        if(StringUtils.isBlank(logincode)){
//            return new ResultFailDto("验证码为空");
//        }
//        if(!codeInSession.equalsIgnoreCase(logincode)){
//            return new ResultFailDto("验证码不正确");
//        }
        if(StringUtils.isBlank(userName)){
            return new ResultFailDto("用户名为空");
        }
        if(StringUtils.isBlank(password)){
            return new ResultFailDto("密码为空");
        }
        return userService.loginBack(userName, password, RequestUtils.getClientIP(request));
    }


}
