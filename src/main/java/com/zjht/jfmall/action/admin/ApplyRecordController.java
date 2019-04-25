package com.zjht.jfmall.action.admin;


import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.*;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.entity.*;
import com.zjht.jfmall.service.*;
import com.zjht.jfmall.util.PageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * <p>
 * 申请贷款记录表  前端控制器
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Controller
public class ApplyRecordController {

    @Value("${num}")
    private Double num;
    @Value("${collection_id}")
    private String collectionId;

    @Autowired
    private ApplyRecordService service;
    @Autowired
    private UserService userService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private AppUserLoanPlatService aulpService;
    @Autowired
    private AppUserBasicService basicService;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private NetLoanService netLoanService;
    @Autowired
    private LogService logService;
    @Autowired
    private UserFollowRecordService recordService;
    @Autowired
    private UserRoleService userRoleService;//用户角色

    @RequestMapping(value = "/apply/cwv_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String cwv_list(HttpServletRequest request, ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "apply/cw_list";
    }

    @RequestMapping(value = "/apply/kfv_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String kfv_list(HttpServletRequest request, ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "customer/apply/kf_list";
    }

    @RequestMapping(value = "/apply/kf_net.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String kf_net(HttpServletRequest request, ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        ApplyRecord record = service.find(bean);
        record.setUsePlat(aulpService.findApplyRecord(record));
        //查询用户手机号码
        AppUser byId = appUserService.findById(record.getAppUserId());
        modelMap.put("account", byId != null ? byId.getPhone() : "");
        modelMap.put("bean", record);
        modelMap.put("nets", netLoanService.getPage(new NetLoan(), 1, 100).getList());
        return "customer/apply/kf_net";
    }


    @NoNeedAuth
    @RequestMapping(value = "/apply/kf_edit_net.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String kf_edit_net(HttpServletRequest request, ModelMap modelMap, String id) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", aulpService.findById(id));
        modelMap.put("nets", netLoanService.getPage(new NetLoan(), 1, 100).getList());
        return "customer/apply/kf_edit_net";
    }

    @ResponseBody
    @RequestMapping(value = "/apply/kf_net.json", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto v_add(HttpServletRequest request, ModelMap modelMap, AppUserLoanPlat bean) {
        WebSite.setParameters(request,modelMap);
        try {
            bean.setOpertionId(sessionProvider.getUser().getId());
            bean.setOpertionDate(new Date());
            bean.setCreateTime(new Date());
            bean.setId(UUID.randomUUID().toString().replace("-", ""));
            if (StringUtils.isNotBlank(bean.getUseAmount())) {
                bean.setApplyComm(String.valueOf(Double.valueOf(bean.getUseAmount()) * num));
            } else {
                bean.setUseAmount("0");
                bean.setApplyComm("0");
            }
            //判断跑单则需要关联催收
            if ("1".equals(bean.getCollStatus())) {
                List<User> users = userService.getUserByRoleId(collectionId);
                if (users.size() == 0) {
                    return new ResultFailDto("没有催收角色人员可用,无法转化为跑单状态");
                }
                int i = new Random().nextInt(users.size());
                bean.setCollUserId(users.get(i).getId());//设置催收人员ID
            }
            if("1".equals(bean.getCollStatus())){
                bean.setStopTime(new Date());
            }
            bean.setBackLoanStatus("0");
            aulpService.insert(bean);
            logService.add("添加平台");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/apply/kf_edit_net.json", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto kf_edit_net(HttpServletRequest request, ModelMap modelMap, AppUserLoanPlat bean) {
        WebSite.setParameters(request,modelMap);
        try {
            bean.setOpertionId(sessionProvider.getUser().getId());
            bean.setOpertionDate(new Date());
            if (StringUtils.isNotBlank(bean.getUseAmount())) {
                bean.setApplyComm(String.valueOf(Double.valueOf(bean.getUseAmount()) * num));
            } else {
                bean.setUseAmount("0");
                bean.setApplyComm("0");
            }
            //判断跑单则需要关联催收
            if ("1".equals(bean.getCollStatus())) {
                List<User> users = userService.getUserByRoleId(collectionId);
                if (users.size() == 0) {
                    return new ResultFailDto("没有催收角色人员可用,无法转化为跑单状态");
                }
                int i = new Random().nextInt(users.size());
                bean.setCollUserId(users.get(i).getId());//设置催收人员ID
            }
            if("1".equals(bean.getCollStatus())){
                bean.setStopTime(new Date());
            }
            aulpService.update(bean);
            logService.add("编辑平台");
            return new ResultSuccessDto();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("处理失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/apply/cwo_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, ApplyRecord bean) {
        PageInfo<ApplyRecord> pageInfo = service.findPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(apply -> {
            //查询用户信息
            //查询跟进人员信息
            apply.setUser(userService.findById(apply.getUserId()));
            apply.setOperUser(userService.findById(apply.getOperationId()));
            apply.setAppUser(appUserService.findById(apply.getAppUserId()));
            AppUserLoanPlat appUserLoanPlat = new AppUserLoanPlat();
            appUserLoanPlat.setRecordId(apply.getId());
            apply.setCountAmount(aulpService.countAmount(appUserLoanPlat));
            apply.setBrokerage(aulpService.sumUseCommByRecordId(apply.getId()));
            apply.setKfComm(aulpService.kfComm(appUserLoanPlat));
            apply.setCsComm(aulpService.sumStopCommByRecordId(apply.getId()));
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @ResponseBody
    @RequestMapping(value = "/apply/kfo_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto kfo_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, ApplyRecord bean) {
    	User user1 = sessionProvider.getUser();
    	bean.setUserId(user1.getId());
    	List<String> roleIds = userRoleService.listRoleIdByUserId(user1.getId());
        for (String roleId : roleIds) {
        	if ("000000000001".equals(roleId)) {
        		bean.setUserId(null);
        	}
        }
        PageInfo<ApplyRecord> pageInfo = service.findPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(apply -> {
            //查询用户信息
            //查询跟进人员信息
            apply.setUser(userService.findById(apply.getUserId()));
            apply.setOperUser(userService.findById(apply.getOperationId()));
            apply.setAppUser(appUserService.findById(apply.getAppUserId()));
            AppUserLoanPlat appUserLoanPlat = new AppUserLoanPlat();
            appUserLoanPlat.setRecordId(apply.getId());
            apply.setCountAmount(aulpService.countAmount(appUserLoanPlat));
            //查询已放款平台
            apply.setUsePlat(aulpService.findUsePlat(apply));
            apply.setKfComm(aulpService.kfComm(appUserLoanPlat));
            apply.setBrokerage(aulpService.brokerage(appUserLoanPlat));
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @RequestMapping(value = "/apply/cw_see.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String cw_see(HttpServletRequest request,ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        ApplyRecord applyRecord = service.find(bean);
        AppUser byId = appUserService.findById(applyRecord.getAppUserId());
        byId.setObj(basicService.findByAppId(applyRecord.getAppUserId()));
        modelMap.put("bean", byId);
        modelMap.put("applyRecord", applyRecord);
        return "apply/cw_see";
    }

    @NoNeedAuth
    @RequestMapping(value = "/apply/csv_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String csv_list(HttpServletRequest request, ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "collection/cs_list";
    }

    @NoNeedAuth
    @RequestMapping(value = "/apply/csv_n_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String cs_n_list(HttpServletRequest request, ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        return "collection/cs_n_list";
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/apply/cso_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto cso_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, ApplyRecord bean) {
        bean.setCollId(sessionProvider.getUser().getId());
        PageInfo<ApplyRecord> pageInfo = service.findCollectionPage(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(apply -> {
            //查询用户信息
            //查询跟进人员信息
            apply.setUser(userService.findById(apply.getUserId()));
            apply.setOperUser(userService.findById(apply.getOperationId()));
            apply.setAppUser(appUserService.findById(apply.getAppUserId()));
            AppUserLoanPlat appUserLoanPlat = new AppUserLoanPlat();
            appUserLoanPlat.setRecordId(apply.getId());
            apply.setCountAmount(aulpService.countAmount(appUserLoanPlat));
            apply.setBrokerage(aulpService.brokerage(appUserLoanPlat));
            apply.setKfComm(aulpService.kfComm(appUserLoanPlat));
            //设置平台记录
            AppUserLoanPlat byId = aulpService.findById(apply.getPlatId());
            apply.setLoanPlat(byId);
            apply.setNet(netLoanService.findById(byId.getPlatformId()));
            //催收次数
            apply.setCommNum(recordService.count(sessionProvider.getUser().getId(), apply.getId(), apply.getPlatId()));
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }

    @NoNeedAuth
    @RequestMapping(value = "/apply/cs_see.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String cs_see(HttpServletRequest request,ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        ApplyRecord applyRecord = service.find(bean);
        AppUser byId = appUserService.findById(applyRecord.getAppUserId());
        byId.setObj(basicService.findByAppId(applyRecord.getAppUserId()));
        modelMap.put("bean", byId);
        modelMap.put("applyRecord", applyRecord);
        modelMap.put("platId", bean.getPlatId());
        return "collection/cs_see";
    }

    @RequestMapping(value = "/apply/kf_see.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String kf_see(HttpServletRequest request,ModelMap modelMap, ApplyRecord bean) {
        WebSite.setParameters(request, modelMap);
        ApplyRecord applyRecord = service.find(bean);
        AppUser byId = appUserService.findById(applyRecord.getAppUserId());
        byId.setObj(basicService.findByAppId(applyRecord.getAppUserId()));
        modelMap.put("bean", byId);
        modelMap.put("applyRecord", applyRecord);
        return "customer/apply/kf_see";
    }

}

