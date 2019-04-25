package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.dao.ErrorCodeDao;
import com.zjht.jfmall.entity.ErrorCode;
import com.zjht.jfmall.service.ErrorCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ErrorCodeServiceImpl implements ErrorCodeService {

    @Autowired
    private ErrorCodeDao errorCodeDao;
    @Override
    public int insert(ErrorCode record) {
        return errorCodeDao.insert(record);
    }

    @Override
    public List<ErrorCode> selectPage(ErrorCode record, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ErrorCode> list = errorCodeDao.select(record);
        PageInfo pageInfo = (PageInfo)list;
        return errorCodeDao.select(record);
    }
}
