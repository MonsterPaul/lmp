package com.zjht.jfmall.quartz.job;

import com.zjht.jfmall.service.AllLoanService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 贷款大全余额小于0自动下架定时任务
 */
public class AllLoanJob {

    @Autowired
    private AllLoanService allLoanService;

    public void execute() {
        allLoanService.soldOut();
    }

}
