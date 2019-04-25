package com.zjht.jfmall.service;


public interface AllLoanChickService {

    /**
     * 统计单个平台的点击率
     * @param allLoanId
     * @return
     */
    int countByAllLoanId(String allLoanId);
    void insert(String id,String loanId);
}