package com.zjht.jfmall.action.admin;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.LayuiResultDto;
import com.zjht.jfmall.common.dto.LayuiResultSuccessDto;
import com.zjht.jfmall.common.dto.ReportDetail;
import com.zjht.jfmall.common.dto.RiskApplyReportDetail;
import com.zjht.jfmall.common.dto.RiskBehaviorReportDetail;
import com.zjht.jfmall.common.dto.RiskCurrentReportDetail;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.RiskRecord;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.proxy.RatingClient;
import com.zjht.jfmall.service.AppUserLoanPlatService;
import com.zjht.jfmall.service.AppUserService;
import com.zjht.jfmall.service.RiskService;
import com.zjht.jfmall.service.UserService;
import com.zjht.jfmall.util.DateTimeUtils;
import com.zjht.jfmall.util.PageUtil;

@Controller
public class RiskController {
    @Autowired
    private AppUserService service;
	@Autowired
    private RiskService riskService;
	@Autowired
    private AppUserLoanPlatService aulpService;
    @Autowired
    private UserService userService;
    @Autowired
    private RatingClient ratingClient;
	
    @RequestMapping(value = "/risk/go_authenticated_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String go_authenticated_list(HttpServletRequest request, ModelMap modelMap, AppUser bean) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", bean);
        modelMap.put("loginNum",userService.getLoginNum());
        return "risk/authenticated_list";
    }

    @ResponseBody
    @RequestMapping(value = "/risk/authenticated_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto authenticated_list(HttpServletRequest request, ModelMap modelMap, Integer pageNum, Integer pageSize, AppUser bean) {
        PageInfo<AppUser> pageInfo = riskService.findPageForRiskAuth(bean, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        pageInfo.getList().stream().forEach(user -> {
            Map<String, Object> obj = new HashMap<>();
            //已放款平台
            int count = aulpService.count(user.getId());
            obj.put("count", count);
            //已放款金额
            Double comm = aulpService.sumAll(user.getId());
            obj.put("comm", comm == null ? 0D : comm);
            //已收佣金
            Double useComm = aulpService.sumUseCommByUserId(user.getId());
            useComm = useComm == null ? 0D : useComm;
            Double stopComm = aulpService.sumStopCommByUserId(user.getId());
            stopComm = stopComm == null ? 0D : stopComm;
            obj.put("allComm", useComm + stopComm);
            //当前跟进人
            User u = userService.findById(user.getServiceId());
            obj.put("u", u.getNickName());
            
            RiskRecord record = new RiskRecord();
            record.setUserId(user.getId());
            RiskRecord exsitRecord = riskService.findByEntity(record);
            obj.put("riskId",exsitRecord==null?null:exsitRecord.getId());
            //查询用户被邀请类型，查询邀请人信息
            switch (user.getIsInvited()) {
                case 1://app用户邀请
                    user.setInvitationName(service.findInvitationName(user.getId()));
                    break;
                case 2://渠道商邀请
                    user.setInvitationName(userService.findInvitationName(user.getId()));
                    break;
                default:
                    break;
            }
            user.setObj(obj);
        });
        LayuiResultDto resultDto = new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
        return resultDto;
    }
    
    
    /**
     * 获取风控报告，如果系统已有报告直接返回结果，如果没有则去新颜查询再返回结果
     * @param request
     * @param modelMap
     * @param bean
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/risk/getOrViewRisk.do", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String getOrViewRisk(HttpServletRequest request, ModelMap modelMap, AppUser bean) throws UnsupportedEncodingException {
    	WebSite.setParameters(request, modelMap);
    	AppUser user = service.findById(bean.getId());
    	
    	String result = null;
    	RiskRecord exsitRecord = riskService.findRisKRecord(user.getIdCard());
    	//存在则直接取数据
    	if(null!=exsitRecord && !StringUtils.isBlank(exsitRecord.getData())) {
    		result = exsitRecord.getData();
    	}else {
    		Map<String,String> parameterMap = new HashMap<String,String>();
    		//测试数据
//    		parameterMap.put("id_no", "522627199205133333");
//    		parameterMap.put("id_name", "张三");
    		parameterMap.put("id_no", user.getIdCard());
    		parameterMap.put("id_name", user.getName());
    		parameterMap.put("phone_no", user.getPhone());
    		parameterMap.put("bankcard_no", user.getBankNo());
    		result = ratingClient.request(request,parameterMap);
    	}
    	System.out.println(result);
		
		Map resultMap = JSONObject.parseObject(result, HashMap.class);
		ReportDetail rd = JSONObject.parseObject(resultMap.get("data").toString(), ReportDetail.class);
		System.out.println(rd.getCode());
		
		RiskApplyReportDetail rapd = new RiskApplyReportDetail();
		RiskBehaviorReportDetail rbpd = new RiskBehaviorReportDetail();
		RiskCurrentReportDetail rcpd = new RiskCurrentReportDetail();
		if(rd.getResult_detail()!=null) {
			rapd = JSONObject.parseObject(JSONObject.toJSONString(rd.getResult_detail().get("apply_report_detail")), RiskApplyReportDetail.class);
			rbpd = JSONObject.parseObject(JSONObject.toJSONString(rd.getResult_detail().get("behavior_report_detail")), RiskBehaviorReportDetail.class);
			rcpd = JSONObject.parseObject(JSONObject.toJSONString(rd.getResult_detail().get("current_report_detail")), RiskCurrentReportDetail.class);
		}
		bean.setName(user.getName());
		bean.setIdCard(user.getIdCard());
		modelMap.put("success", resultMap.get("success").toString());
		modelMap.put("errorCode", resultMap.get("errorCode"));
		modelMap.put("errorMsg", resultMap.get("errorMsg"));
		modelMap.put("data", rd);
		modelMap.put("bean", bean);
		modelMap.put("rapd", rapd==null? new RiskApplyReportDetail():rapd);
		modelMap.put("rbpd", rbpd==null? new RiskBehaviorReportDetail():rbpd);
		modelMap.put("rcpd", rcpd==null? new RiskCurrentReportDetail():rcpd);
		
		if(null==exsitRecord || StringUtils.isBlank(exsitRecord.getData())) {
			//存数据
			RiskRecord riskRecord = new RiskRecord();
			riskRecord.setSuccess(resultMap.get("success")==null?null:resultMap.get("success").toString()); 
			riskRecord.setErrorCode(resultMap.get("errorCode")==null?null:resultMap.get("errorCode").toString()); 
			riskRecord.setErrorMsg(resultMap.get("errorMsg")==null?null:resultMap.get("errorMsg").toString()); 
			riskRecord.setTransId(rd.getTrans_id());
			riskRecord.setTradeNo(rd.getTrade_no());
			riskRecord.setUserId(bean.getId());
			riskRecord.setIdNo(user.getIdCard()); 
			riskRecord.setIdName(user.getName());
			riskRecord.setCode(rd.getCode()); 
			riskRecord.setData(result);
			riskRecord.setDesc(rd.getDesc());
			riskRecord.setFee(rd.getFee()); 
			riskRecord.setVersions(rd.getVersions());
			riskRecord.setCreateTime(DateTimeUtils.getCurrentDateStr("yyyy-MM-dd HH:mm:ss"));
			riskService.savaRisKRecord(riskRecord);
		}
		
		return "success";
		
    }
    
    
    /**
     * 查询本地风控报告
     * @param request
     * @param modelMap
     * @param bean
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/risk/viewRisk.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String viewRisk(HttpServletRequest request, ModelMap modelMap, AppUser bean) throws UnsupportedEncodingException {
    	WebSite.setParameters(request, modelMap);
    	AppUser user = service.findById(bean.getId());
    	
    	String result = null;
    	RiskRecord exsitRecord = riskService.findRisKRecord(user.getIdCard());
    	//存在则直接取数据
    	if(null!=exsitRecord && !StringUtils.isBlank(exsitRecord.getData())) {
    		result = exsitRecord.getData();
    		Map resultMap = JSONObject.parseObject(result, HashMap.class);
    		ReportDetail rd = JSONObject.parseObject(resultMap.get("data").toString(), ReportDetail.class);
    		
    		RiskApplyReportDetail rapd = new RiskApplyReportDetail();
    		RiskBehaviorReportDetail rbpd = new RiskBehaviorReportDetail();
    		RiskCurrentReportDetail rcpd = new RiskCurrentReportDetail();
    		if(rd.getResult_detail()!=null) {
    			rapd = JSONObject.parseObject(JSONObject.toJSONString(rd.getResult_detail().get("apply_report_detail")), RiskApplyReportDetail.class);
    			rbpd = JSONObject.parseObject(JSONObject.toJSONString(rd.getResult_detail().get("behavior_report_detail")), RiskBehaviorReportDetail.class);
    			rcpd = JSONObject.parseObject(JSONObject.toJSONString(rd.getResult_detail().get("current_report_detail")), RiskCurrentReportDetail.class);
    		}
    		bean.setName(user.getName());
    		bean.setIdCard(user.getIdCard());
    		modelMap.put("success", resultMap.get("success").toString());
    		modelMap.put("errorCode", resultMap.get("errorCode"));
    		modelMap.put("errorMsg", resultMap.get("errorMsg"));
    		modelMap.put("data", rd);
    		modelMap.put("bean", bean);
    		modelMap.put("rapd", rapd==null? new RiskApplyReportDetail():rapd);
    		modelMap.put("rbpd", rbpd==null? new RiskBehaviorReportDetail():rbpd);
    		modelMap.put("rcpd", rcpd==null? new RiskCurrentReportDetail():rcpd);
    	}
    	return "risk/risk_result";
    }
    
    
}
