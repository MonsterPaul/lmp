package com.zjht.jfmall.action.admin;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.LayuiResultDto;
import com.zjht.jfmall.common.dto.LayuiResultSuccessDto;
import com.zjht.jfmall.common.web.annotation.NoNeedAuth;
import com.zjht.jfmall.entity.RechargeRecord;
import com.zjht.jfmall.service.RechargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by vip on 2019/1/17.
 */
@Controller
@RequestMapping(value = "/recharge")
public class RechargeRecordController {

    @Autowired
    private RechargeRecordService rechargeRecordService;

    @NoNeedAuth
    @ResponseBody
    @RequestMapping(value = "/list.json", method = {RequestMethod.GET, RequestMethod.POST})
    public LayuiResultDto o_list(String loanId) {
        PageInfo<RechargeRecord> pageInfo = rechargeRecordService.find(loanId);
        return new LayuiResultSuccessDto(null, pageInfo.getList(), pageInfo.getTotal());
    }

}
