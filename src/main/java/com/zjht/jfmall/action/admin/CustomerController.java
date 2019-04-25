package com.zjht.jfmall.action.admin;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.LayuiResultDto;
import com.zjht.jfmall.common.dto.LayuiResultSuccessDto;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.NetLoan;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.AppUserBasicService;
import com.zjht.jfmall.service.AppUserLoanPlatService;
import com.zjht.jfmall.service.AppUserService;
import com.zjht.jfmall.service.UserService;
import com.zjht.jfmall.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈客服管理〉
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Value("${service_id}")
    private String serviceId;

    @Autowired
    private SessionProvider        sessionProvider;
    @Autowired
    private AppUserLoanPlatService service;
    @Autowired
    private AppUserService         appUserService;
    @Autowired
    private AppUserBasicService    appUserBasicService;
    @Autowired
    private UserService            userService;

    @RequestMapping(value = "/loan_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_list(HttpServletRequest request, ModelMap modelMap, AppUser bean) {
        WebSite.setParameters(request, modelMap);
        User user = sessionProvider.getUser();
        bean.setServiceId(user.getId());
        List<AppUser> appUserList   = appUserService.findLoanPage(bean, 1, 10000).getList();
        double        commissionSum = 0;
        double        loanSum       = 0;
        double        applySum      = 0;
        if (!CollectionUtils.isEmpty(appUserList)) {
            commissionSum = appUserList.stream().collect(Collectors.summingDouble(AppUser::getSunCommission));
            loanSum = appUserList.stream().collect(Collectors.summingDouble(AppUser::getSunLoan));
            applySum = appUserList.stream().collect(Collectors.summingDouble(AppUser::getApplyCommSum));
        }
        modelMap.put("commissionSum", commissionSum);
        modelMap.put("loanSum", loanSum);
        modelMap.put("applySum", applySum);
        modelMap.put("userSum", appUserList.size());
        modelMap.put("bean", bean);
        return "customer/loan/list";
    }

    @ResponseBody
    @RequestMapping(value = "/loan_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, AppUser appUser) {
        User user = sessionProvider.getUser();
        appUser.setServiceId(user.getId());
        PageInfo<AppUser> pageInfo  = appUserService.findLoanPage(appUser, pageNum, pageSize);
        LayuiResultDto    resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/userDetail.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String userDetail(HttpServletRequest request, ModelMap modelMap, AppUser bean) {
        WebSite.setParameters(request, modelMap);
        User user = sessionProvider.getUser();
        bean.setServiceId(user.getId());
        AppUser byId = appUserService.findLoan(bean);
        byId.setObj(appUserBasicService.findByAppId(bean.getId()));
        modelMap.put("bean", byId);
        return "customer/loan/see";
    }

    @RequestMapping(value = "/commission_ranking.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String commissionRankingList(HttpServletRequest request, ModelMap modelMap, User bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "customer/rank/commissionList";
    }

    @ResponseBody
    @RequestMapping(value = "/commission_ranking.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto commissionRanking(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, User user) {
        if (StringUtils.isEmpty(user.getExchangeTimeBegin())) {
            user.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }
        PageInfo<User> pageInfo  = userService.getcCommissionRanking(user, pageNum, pageSize);
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/loan_ranking.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String loanRankingList(HttpServletRequest request, ModelMap modelMap, User bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "customer/rank/loanList";
    }

    @ResponseBody
    @RequestMapping(value = "/loan_ranking.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto loanRanking(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, User user) {
        if (StringUtils.isEmpty(user.getExchangeTimeBegin())) {
            user.setExchangeTimeBegin(DateTimeUtils.getCurrentDateString());
        }
        PageInfo<User> pageInfo  = userService.getLoanRanking(user, pageNum, pageSize);
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }
}