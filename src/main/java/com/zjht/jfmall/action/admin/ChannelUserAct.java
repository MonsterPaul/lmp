package com.zjht.jfmall.action.admin;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.LayuiResultDto;
import com.zjht.jfmall.common.dto.LayuiResultSuccessDto;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.common.dto.ResultFailDto;
import com.zjht.jfmall.common.dto.ResultSuccessDto;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.AppUserInvitationService;
import com.zjht.jfmall.service.AppUserService;
import com.zjht.jfmall.service.ChannelClickService;
import com.zjht.jfmall.service.ChannelInvitationService;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.service.UserRoleService;
import com.zjht.jfmall.service.UserService;
import com.zjht.jfmall.util.PageUtil;

/**
 * Created by vip on 2018/12/2.
 */
@Controller
public class ChannelUserAct {

    @Autowired
    private UserService service;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private ChannelClickService channelClickService;
    @Autowired
    private ChannelInvitationService invitationService;
    @Autowired
    private AppUserInvitationService appUserInvitationService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private LogService logService;
    @Autowired
    private UserRoleService userRoleService;
    private int pageSize = 100;

    @RequestMapping(value = "/cha/v_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_list(HttpServletRequest request, ModelMap modelMap, User bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "channelUser/list";
    }

    @RequestMapping(value = "/cha/count_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String count_list(HttpServletRequest request, ModelMap modelMap, User bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "channelUser/count_list";
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/cha/o_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, User bean) {
        //只显示渠道专员自己的渠道商
        User user1 = sessionProvider.getUser();
        bean.setId(user1.getId());
        List<String> roleIds = userRoleService.listRoleIdByUserId(user1.getId());
        for (String roleId : roleIds) {
        	if ("000000000001".equals(roleId)) {
        		bean.setId(null);
        	}
        }
        PageInfo<User> pageInfo = service.findChannelUserPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(user -> {
            user.setExchangeTimeBegin(bean.getExchangeTimeBegin());
            user.setExchangeTimeEnd(bean.getExchangeTimeEnd());
            //遍历查询渠道数据统计信息
            countInfo(user);
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/cha/cha_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String cha_list(ModelMap modelMap, User user1, HttpServletRequest request) {
        WebSite.setParameters(request,modelMap);
        User user = service.findById(sessionProvider.getUser().getId());
        user.setExchangeTimeBegin(user1.getExchangeTimeBegin());
        user.setExchangeTimeEnd(user1.getExchangeTimeEnd());
        
        int pageNo = 0;
        int uvCount = 0;
        int registerCount = 0;
        int notRegisterCount = 0;
        List<String> roleIds = userRoleService.listRoleIdByUserId(user.getId());
        for (String roleId : roleIds) {
        	if ("000000000001".equals(roleId)) {
        		user1.setId(null);
        		while (true) {
                    PageInfo<User> pageInfo = service.findChannelUserPage(user1, PageUtil.cpn(pageNo), PageUtil.pageSize(pageSize));
                    for (User temp : pageInfo.getList()) {
                    	int uv = channelClickService.countByChannelId(temp);
                        //注册量(有效的)
                        int register = invitationService.countByChannelId(temp);
                        //注册量(总的)
                        int notRegister = invitationService.countAllByChannelId(temp);
                        
                        uvCount += uv;
                        registerCount += register;
                        notRegisterCount += notRegister;
                    }
                    
        			if (pageInfo == null || pageInfo.getList().size() < pageSize) {
        				break;
        			}
        			pageNo++;
        			pageSize = pageSize * pageNo;
        		}
                user.setUv(uvCount);
                user.setRegister(registerCount);
                user.setNotRegister(notRegisterCount);
                //注册转换率
                user.setRate(user.getUv() == 0 ? 0 : new BigDecimal((Double.valueOf(user.getNotRegister()) / Double.valueOf(user.getUv()))*100).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());
        		
        	} else {
                //遍历查询渠道数据统计信息
                countInfo(user);
        	}
        }

        modelMap.put("bean", user);
        double yxzcl=user.getUv() == 0 ? 0 : new BigDecimal((Double.valueOf(user.getRegister()) / Double.valueOf(user.getUv()))*100).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
        modelMap.put("yxzcl", yxzcl);
        return "channelUser/cha_list";
    }

    @RequestMapping(value = "/cha/v_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_add(HttpServletRequest request,ModelMap modelMap) {
        WebSite.setParameters(request,modelMap);
        return "channelUser/add";
    }

    @ResponseBody
    @RequestMapping(value = "/cha/o_add.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto v_add(HttpServletRequest request, ModelMap modelMap, User bean) {
        WebSite.setParameters(request,modelMap);
        try {
            service.insertChannelUser(bean);
            logService.add("添加渠道商" + bean.getUsername());
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @RequestMapping(value = "/cha/v_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_edit(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request,modelMap);
        modelMap.put("bean", service.findById(id));
        return "channelUser/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/cha/o_edit.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_edit(HttpServletRequest request, ModelMap modelMap, User bean) {
        WebSite.setParameters(request,modelMap);
        try {
            service.updateChannelUser(bean);
            logService.add("编辑渠道商" + bean.getUsername());
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @RequestMapping(value = "/cha/v_see.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_see(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request,modelMap);
        User bean = service.findById(id);
        //统计渠道各种数据信息
        countInfo(bean);
        modelMap.put("bean", bean);
        return "channelUser/see";
    }

    @ResponseBody
    @RequestMapping(value = "/cha/updateStatus.do", method = RequestMethod.POST)
    public Object updateStatus(String id, String status) {
        if (StringUtils.isBlank(id)) {
            return new ResultFailDto("参数错误...");
        }
        try {
            service.updateStatus(id, Integer.valueOf(status) == 0 ? 1 : 0);
            logService.add("修改渠道商状态" + id);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("操作失败");
        }
        return new ResultSuccessDto("操作成功");
    }

    //统计渠道商信息
    private void countInfo(User user) {
        //UV 用户点击率
        user.setUv(channelClickService.countByChannelId(user));
        //注册量(有效的)
        int register = invitationService.countByChannelId(user);
        user.setRegister(register);
        //注册量(总的)
        int notRegister = invitationService.countAllByChannelId(user);
        user.setNotRegister(notRegister);
        //注册转换率
        user.setRate(user.getUv() == 0 ? 0 : new BigDecimal((Double.valueOf(user.getNotRegister()) / Double.valueOf(user.getUv()))*100).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());
        //成交客户数量,有结算单的客户
        user.setSuccessCount(appUserService.successCount(user));
        //成交转换率
        int countByChannel = appUserService.countByChannel(user);

        user.setRateSuccess(countByChannel == 0 ? 0 :   new BigDecimal((Double.valueOf(user.getSuccessCount()) / Double.valueOf((countByChannel))) * 100).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue()) ;
        //统计用户下载数量
        user.setLoginCount(appUserService.countDownloas(user));
        if(StringUtils.isNotBlank(user.getChannelPerId())){
            user.setTgyName(service.findById(user.getChannelPerId()).getNickName());
        }
        
        //分发客户数
        user.setClient(service.client(user.getId()));
        //总分发数
        user.setDistribution(service.distribution(user.getId()));
    }

    @RequestMapping(value = "/cw/cw_deposit_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String deposit_list(HttpServletRequest request, ModelMap modelMap, User bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "deposit/channel_list";
    }

    /**
     * 财务渠道列表
     * @param request
     * @param modelMap
     * @param pageNum
     * @param pageSize
     * @param bean
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cw/cw_deposit_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto deposit_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, User bean) {
        PageInfo<User> pageInfo = service.findChannelUserPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        if(CollectionUtils.isNotEmpty(pageInfo.getList())){
            pageInfo.getList().stream().forEach(user -> {
                user.setExchangeTimeBegin(bean.getExchangeTimeBegin());
                user.setExchangeTimeEnd(bean.getExchangeTimeEnd());
                //遍历查询渠道数据统计信息
                countInfo(user);
            });
        }

        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/cw/cw_deposit_see.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String deposit_see(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request,modelMap);
        User bean = service.findById(id);
        //统计渠道各种数据信息
        countInfo(bean);
        modelMap.put("bean", bean);

        return "deposit/channel_see";
    }
}
