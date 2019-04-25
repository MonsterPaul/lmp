package com.zjht.jfmall.action.admin.lifeIntegral;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjht.jfmall.common.dto.*;
import com.zjht.jfmall.common.web.WebSite;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.entity.ApiChannel;
import com.zjht.jfmall.entity.ElectroniCode;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.service.ChannelService;
import com.zjht.jfmall.service.ElectroniccodeService;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.util.DateTimeUtils;
import com.zjht.jfmall.util.ExcelUtils;
import com.zjht.jfmall.util.PageUtil;
import com.zjht.jfmall.util.VerifyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * ${DESCRIPTION}
 *
 * @author caozhaokui
 * @create 2018-01-04 15:01
 */
@Controller
public class ElectronicCodeAct {
    @Autowired
    private ElectroniccodeService electroniccodeService;
    @Autowired
    private LogService            logService;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private ChannelService channelService;

    private final Logger logger = LoggerFactory.getLogger(ElectronicCodeAct.class);

    /**
     * 电子码列表页面
     *
     * @param request
     * @param modelMap
     * @param electroniCode
     * @return
     */
    @RequestMapping(value = "/lifeIntegral/electronicCode/v_list.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_list(HttpServletRequest request, ModelMap modelMap, ElectroniCode electroniCode) {
        WebSite.setParameters(request, modelMap);
        if (electroniCode == null) {
            electroniCode = new ElectroniCode();
        }
        List<ApiChannel> channelList = channelService.getList(null);
        modelMap.addAttribute("channelList", channelList);

        modelMap.put("electroniCode", electroniCode);
        modelMap.put("statusMap", ElectroniCode.Status.StatusMap());
        modelMap.put("pointsMap", ElectroniCode.PointsMap());
        return "lifeIntegral/electronicCode/list";
    }

    /**
     * 电子码列表信息
     *
     * @param electroniCode
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/electronicCode/o_list.json", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_list(ElectroniCode electroniCode, Integer pageNum, Integer pageSize) {
        if (electroniCode == null) {
            electroniCode = new ElectroniCode();

        }
        //1.根据分页参数查询数据
        List<ElectroniCode> pageInfo  = electroniccodeService.getPage(electroniCode, PageUtil.cpn(pageNum), PageUtil.pageSize(pageSize));
        LayuiResultDto      resultDto = new LayuiResultSuccessDto(null, pageInfo, electroniccodeService.getPageCount(electroniCode));
        return resultDto;
    }

    /**
     * 生成导出电子码信息
     *
     * @param modelMap
     * @param request
     * @param num
     * @param points
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/electronicCode/o_export.do", method = {RequestMethod.POST, RequestMethod.GET})
    public void o_exportCodes(ModelMap modelMap, HttpServletRequest request, String num, String points, HttpServletResponse response) {
        if (StringUtils.isBlank(num)) {
            throw new IllegalArgumentException("电子码个数不能为空");
        }
        if (StringUtils.isBlank(points)) {
            throw new IllegalArgumentException("电子码积分数不能为空");
        }
        if (!ElectroniCode.PointsMap().containsKey(Integer.valueOf(points))) {
            throw new IllegalArgumentException("电子码积分数非法");
        }
        WebSite.setParameters(request, modelMap);
        // 生成电子码
        Set<String> codes = electroniccodeService.insertElectroniccodeByNumAndPoints(Integer.valueOf(num), Integer.valueOf(points));

        //导出生成的电子码
        List<List<String>> list = new ArrayList<>();
        for (String code : codes) {
            List<String> a = new ArrayList<>();
            a.add(code);
            a.add(points);
            list.add(a);
        }

        try {
            String[] heads    = {"电子码", "电子码积分数"};
            String   fileName = "电子码积分数" + points + "----" + DateTimeUtils.formatDateStr(new Date(), "yyyyMMddHHmmss") + ".xls";
            ExcelUtils.exportExcel(fileName, request, response, heads, list);
            logService.add("生成导出" + num + "条积分为" + points + "的电子码信息，导出时间：" + DateTimeUtils.formatDateStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } catch (IOException e) {
            logger.error("[method:o_exportCodes,meg:生成导出电子码信息异常，导出时间：" + DateTimeUtils.formatDateStr(new Date(), "yyyy-MM-dd HH:mm:ss") + "]", e);
        }

    }

    /**
     * 电子码列表数据导出
     *
     * @param modelMap
     * @param request
     * @param electroniCode
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/electronicCode/o_exportList.do", method = {RequestMethod.POST, RequestMethod.GET})
    public void o_exportList(ModelMap modelMap, HttpServletRequest request, ElectroniCode electroniCode, HttpServletResponse response) {
        if (electroniCode == null) {
            electroniCode = new ElectroniCode();

        }
        WebSite.setParameters(request, modelMap);

        //查询需要导出的数据信息
        List<ElectroniCode> electroniCodes = electroniccodeService.getPage(electroniCode, 1, 2147483646);

        //导出电子码数据信息
        List<List<String>> list = new ArrayList<>();
        for (ElectroniCode electroniCode1 : electroniCodes) {
            List<String> a = new ArrayList<>();
            a.add(electroniCode1.getCode());
            a.add(electroniCode1.getCreator()==null?"":electroniCode1.getCreator().getChannelName());
            a.add(electroniCode1.getPoints().toString());
            a.add(ElectroniCode.Status.getName(electroniCode1.getStatus()));
            a.add(electroniCode1.getMobile() != null ? electroniCode1.getMobile() : "--");
            a.add(DateTimeUtils.formatDateStr(electroniCode1.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            a.add(electroniCode1.getCreator()==null? "" : electroniCode1.getCreator().getUsername());
            a.add(electroniCode1.getSender()==null?"":electroniCode1.getSender().getUsername());
            a.add(electroniCode1.getSendTime() != null ? DateTimeUtils.formatDateStr(electroniCode1.getSendTime(), "yyyy-MM-dd HH:mm:ss") : "--");
            a.add(electroniCode1.getExchangeTime() != null ? DateTimeUtils.formatDateStr(electroniCode1.getExchangeTime(), "yyyy-MM-dd HH:mm:ss") : "--");
            a.add(electroniCode1.getImportTime() != null ? DateTimeUtils.formatDateStr(electroniCode1.getImportTime(), "yyyy-MM-dd HH:mm:ss") : "--");
            a.add(electroniCode1.getImportor()==null? "" :electroniCode1.getImportor().getUsername());
            a.add(electroniCode1.getExpireTime() != null ? DateTimeUtils.formatDateStr(electroniCode1.getExpireTime(), "yyyy-MM-dd HH:mm:ss") : "--");
            a.add(electroniCode1.getRemark());
            list.add(a);
        }
        try {
            String[] heads    = {"电子码", "所属渠道", "电子码积分数", "状态", "手机号", "创建|导出时间", "创建|导出者",
                    "发送者", "发送时间", "兑换时间", "导入时间", "导入者", "过期时间", "备注"};
            String   fileName = "电子码数据" + "----" + DateTimeUtils.formatDateStr(new Date(), "yyyyMMddHHmmss") + ".xls";
            ExcelUtils.exportExcel(fileName, request, response, heads, list);
            logService.add("导出电子码信息，导出时间：" + DateTimeUtils.formatDateStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        } catch (IOException e) {
            logger.error("[method:o_exportList,meg:导出电子码信息异常，导出时间：" + DateTimeUtils.formatDateStr(new Date(), "yyyy-MM-dd HH:mm:ss") + "]", e);
        }

    }

    /**
     * 导入电子码数据
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/electronicCode/o_importExcel.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_importFile(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {

        return electroniccodeService.importFile(request, response, modelMap);
    }

    /**
     * 电子码列表页面
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/lifeIntegral/electronicCode/v_createAndSend.do", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_createAndsend(HttpServletRequest request, ModelMap modelMap) {
        WebSite.setParameters(request, modelMap);
        modelMap.put("pointsMap", ElectroniCode.PointsMap());
        return "lifeIntegral/electronicCode/createAndSend";
    }

    /**
     * 生成并且发送电子码
     * @param params
     * @param points
     * @param expireTime
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/electronicCode/o_createAndSend.do", method = {RequestMethod.POST, RequestMethod.GET})
    public LayuiResultDto o_createAndSend(@Param("params") String params, String points, String expireTime) {
        if (StringUtils.isBlank(points)) {
            return new LayuiResultFailDto("电子码面值不能为空");
        }
        if (!ElectroniCode.PointsMap().containsKey(Integer.valueOf(points))) {
            return new LayuiResultFailDto("电子码面值非法");
        }
        if (StringUtils.isBlank(expireTime)) {
            return new LayuiResultFailDto("有效期不能为空");
        }
        if (StringUtils.isNotBlank(params)) {
            JSONArray           jsonArr = JSONArray.parseArray(params);
            List<ElectroniCode> electroniCodes = new ArrayList<>();
            for (Iterator it = jsonArr.iterator(); it.hasNext(); ) {
                ElectroniCode electroniCode= new ElectroniCode();
                JSONObject o      = (JSONObject) it.next();
                String     mobile = o.get("mobile") == null ? null : o.get("mobile").toString();
                String     remark = o.get("remark") == null ? null : o.get("remark").toString();
                electroniCode.setMobile(mobile);
                electroniCode.setRemark(remark);
                electroniCodes.add(electroniCode);
            }
           return electroniccodeService.createAndSendCode(points,expireTime,electroniCodes);
        }
        return new LayuiResultSuccessDto();
    }

    /**
     * 电子码重发
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/electronicCode/o_resend.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_resend(String id) {
        if (StringUtils.isBlank(id)) {
            return new ResultFailDto("操作id为空");
        }
        return electroniccodeService.reSendCode(id);
    }

    /**
     * 电子码作废
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/lifeIntegral/electronicCode/o_abandoned.do", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultDto o_abandoned(String [] ids) {
        if (ids ==null ||ids.length<=0) {
            return new ResultFailDto("操作id为空");
        }

        return electroniccodeService.abandonedCode(ids);
    }

    /**
     * 前端电子码作废页面
     *
     * @param request
     * @param modelMap
     * @param mobile
     * @return
     */
    @RequestMapping(value = "/lifeIntegral/electronicCode/v_searchAbandonedCode", method = {RequestMethod.POST, RequestMethod.GET})
    public String v_searchAbandonedCode(HttpServletRequest request, ModelMap modelMap, String mobile) {
        WebSite.setParameters(request, modelMap);
        if (StringUtils.isBlank(mobile)) {
            throw new IllegalArgumentException("电话号码不能为空");
        }
        if (!VerifyUtils.isMobile(mobile)) {
            throw new IllegalArgumentException("请输入正确的手机号码");
        }
        ElectroniCode electroniCode = new ElectroniCode();
        electroniCode.setMobile(mobile);
        List<ElectroniCode> electroniCodes = electroniccodeService.getPage(electroniCode,1,1000000);
        modelMap.put("electroniCodes",electroniCodes);
        modelMap.put("mobile",mobile);
        return "lifeIntegral/abandonedCodeResult";
    }
}
