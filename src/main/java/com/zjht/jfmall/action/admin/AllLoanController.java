package com.zjht.jfmall.action.admin;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.*;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.entity.AllLoan;
import com.zjht.jfmall.entity.UserApplyLoan;
import com.zjht.jfmall.entity.param.LayuiAllLoanParam;
import com.zjht.jfmall.service.AllLoanChickService;
import com.zjht.jfmall.service.AllLoanService;
import com.zjht.jfmall.service.UserApplyLoanService;
import com.zjht.jfmall.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by vip on 2019/1/15.
 */
@Controller
@RequestMapping(value = "/allLoan")
public class AllLoanController {

    @Autowired
    private AllLoanService allLoanService;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private AllLoanChickService allLoanChickService;
    @Autowired
    private UserApplyLoanService userApplyLoanService;

    /**
     * 营运贷款大全列表
     * @param request
     * @param modelMap
     * @param param
     * @return
     */
    @RequestMapping(value = "/yy_list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_yy_list(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", param);
        return "allLoan/yy_list";
    }

    /**
     * 营运贷款大全统计
     * @param request
     * @param modelMap
     * @param param
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/yy_statistics.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_yy_statistcs(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", param);
        return "allLoan/yy_statistics";
    }

    /**
     * 营运贷款大全数据
     *
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/yy_list.json", method = {RequestMethod.GET, RequestMethod.POST})
    public LayuiResultDto o_yy_list(LayuiAllLoanParam param, Integer pageNum, Integer pageSize) {
        param.setFurtherId(sessionProvider.getUser().getId());
        PageInfo<AllLoan> pageInfo = allLoanService.findPage(param, PageUtil.pageSize(pageSize), PageUtil.cpn(pageNum));
        pageInfo.getList().stream().forEach(allLoan -> {
            //统计数据UV.申请人数，转化率
            this.initAllLoan(allLoan);
        });
        return new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 财务和超级管理员贷款大全列表
     * @param request
     * @param modelMap
     * @param param
     * @return
     */
    @RequestMapping(value = "/list.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_list(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", param);
        return "allLoan/list";
    }

    /**
     * 财务和超级管理员贷款大全统计
     * @param request
     * @param modelMap
     * @param param
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/statistics.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_statistcs(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", param);
        return "allLoan/statistics";
    }

    /**
     * 财务和超级管理员贷款大全数据
     *
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/list.json", method = {RequestMethod.GET, RequestMethod.POST})
    public LayuiResultDto o_list(LayuiAllLoanParam param, Integer pageNum, Integer pageSize) {
        PageInfo<AllLoan> pageInfo = allLoanService.findPage(param, PageUtil.pageSize(pageSize), PageUtil.cpn(pageNum));
        pageInfo.getList().stream().forEach(allLoan -> {
            //统计数据UV.申请人数，转化率
            this.initAllLoan(allLoan);
        });
        return new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 状态
     * @param request
     * @param modelMap
     * @param param
     * @return
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/up.json", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultDto up(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        try {
            allLoanService.updateStatus(param);
            return new ResultSuccessDto("处理成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("操作失败");
        }
    }

    /**
     * 置顶和不置顶处理
     * @param request
     * @param modelMap
     * @param param
     * @return
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/top.json", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultDto top(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        try {
            allLoanService.updateTop(param);
            return new ResultSuccessDto("处理成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("操作失败");
        }
    }

    /**
     * 添加
     *
     * @param request
     * @param modelMap
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/add.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_add(HttpServletRequest request, ModelMap modelMap) {
        WebSite.setParameters(request, modelMap);
        return "allLoan/add";
    }

    /**
     * 添加
     *
     * @param request
     * @param modelMap
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/yy_add.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_yy_add(HttpServletRequest request, ModelMap modelMap) {
        WebSite.setParameters(request, modelMap);
        return "allLoan/yy_add";
    }

    /**
     * 保存
     * @param request
     * @param modelMap
     * @param param
     * @return
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/add.json", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultDto o_add(HttpServletRequest request, ModelMap modelMap, AllLoan param) {
        try {
            allLoanService.add(param);
            return new ResultSuccessDto("处理成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("操作失败");
        }
    }

    /**
     * 编辑
     *
     * @param request
     * @param modelMap
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/edit.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_edit(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", allLoanService.findById(param.getId()));
        return "allLoan/edit";
    }

    /**
     * 编辑
     *
     * @param request
     * @param modelMap
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/yy_edit.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_yy_edit(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", allLoanService.findById(param.getId()));
        return "allLoan/yy_edit";
    }

    /**
     * 查看
     *
     * @param request
     * @param modelMap
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/detail.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_detail(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", allLoanService.findById(param.getId()));
        return "allLoan/detail";
    }

    /**
     * 编辑保存
     * @param request
     * @param modelMap
     * @param param
     * @return
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/edit.json", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultDto o_edit(HttpServletRequest request, ModelMap modelMap, AllLoan param) {
        try {
            allLoanService.edit(param);
            return new ResultSuccessDto("处理成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("操作失败");
        }
    }

    /**
     * 充值
     * @param request
     * @param modelMap
     * @param param
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/recharge.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_recharge(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", allLoanService.findById(param.getId()));
        return "allLoan/recharge";
    }

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/recharge.json", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultDto o_recharge(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        try {
            allLoanService.recharge(param);
            return new ResultSuccessDto("处理成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultFailDto("操作失败");
        }
    }

    /**
     * 充值详情
     * @param request
     * @param modelMap
     * @param param
     * @return
     */
    @NoNeedAuth
    @RequestMapping(value = "/record.do", method = {RequestMethod.GET, RequestMethod.POST})
    public String v_record(HttpServletRequest request, ModelMap modelMap, LayuiAllLoanParam param) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("bean", param);
        return "allLoan/record";
    }

    /**
     * 充值详情
     * @param param
     * @param pageNum
     * @param pageSize
     * @return
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/record.json", method = {RequestMethod.GET, RequestMethod.POST})
    public LayuiResultDto o_record(LayuiAllLoanParam param, Integer pageNum, Integer pageSize) {
        PageInfo<UserApplyLoan> pageInfo = userApplyLoanService.findPage(param);
        return new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 用户申请详情
     * @param id
     * @return
     */
    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/userRecord.json", method = {RequestMethod.GET, RequestMethod.POST})
    public LayuiResultDto userRecord(String id) {
        PageInfo<UserApplyLoan> pageInfo = userApplyLoanService.list(id);
        return new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
    }

    //初始化数据
    private void initAllLoan(AllLoan allLoan) {
        //UV
        int uv = allLoanChickService.countByAllLoanId(allLoan.getId());
        allLoan.setUv(uv);
        //申请人数
        int coutnNum = userApplyLoanService.countByAllLoanId(allLoan.getId());
        allLoan.setCountNum(coutnNum);
        //转化率=申请人数/UV*100%
        double conversion = 0;
        if (uv != 0) {
            conversion = (coutnNum / (uv * 1D)) * 100D;
        }
        allLoan.setConversionRate(conversion);
    }

}
