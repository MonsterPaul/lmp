package com.zjht.jfmall.service;

import com.zjht.jfmall.entity.ErrorCode;

import java.util.List;

public interface ErrorCodeService {
    int insert(ErrorCode record);

    List<ErrorCode> selectPage(ErrorCode record, int pageNum, int pageSize);
        }
