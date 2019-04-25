package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.dao.AllLoanDao;
import com.zjht.jfmall.dao.RechargeRecordDao;
import com.zjht.jfmall.entity.Advertisement;
import com.zjht.jfmall.entity.AllLoan;
import com.zjht.jfmall.entity.RechargeRecord;
import com.zjht.jfmall.entity.param.LayuiAllLoanParam;
import com.zjht.jfmall.service.AllLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by vip on 2019/1/15.
 */
@Service
public class AllLoanServiceImpl implements AllLoanService {

    @Autowired
    private AllLoanDao        allLoanDao;
    @Autowired
    private SessionProvider   sessionProvider;
    @Autowired
    private RechargeRecordDao rechargeRecordDao;

    /**
     * 前端数据查询返回
     *
     * @param param
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo<AllLoan> findPage(LayuiAllLoanParam param, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);//设置分页
        return new PageInfo<>(allLoanDao.findPage(param));
    }

    /**
     * 新增保存
     *
     * @param param
     */
    @Override
    public void add(AllLoan param) {
        param.setId(UUID.randomUUID().toString().replace("-", ""));
        param.setCreateTime(new Date());
        param.setCreateId(sessionProvider.getUser().getId());
        param.setOperationTime(new Date());
        param.setOperationId(sessionProvider.getUser().getId());
        param.setFurtherId(sessionProvider.getUser().getId());
        if (param.getTop() == 0) {//如果置顶，设置置顶时间，用于排序
            param.setTopTime(new Date());
        }
        allLoanDao.insert(param);
    }

    /**
     * 更新状态，上下架
     *
     * @param param
     */
    @Override
    public void updateStatus(LayuiAllLoanParam param) {
        param.setOperationTime(new Date());
        param.setOperationId(sessionProvider.getUser().getId());
        allLoanDao.updateStatus(param);
    }

    /**
     * 更新是否置顶
     *
     * @param param
     */
    @Override
    public void updateTop(LayuiAllLoanParam param) {
        param.setOperationTime(new Date());
        param.setOperationId(sessionProvider.getUser().getId());
        allLoanDao.updateTop(param);
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public AllLoan findById(String id) {
        return allLoanDao.findById(id);
    }

    /**
     * 编辑保存
     *
     * @param param
     */
    @Override
    public void edit(AllLoan param) {
        param.setOperationTime(new Date());
        param.setOperationId(sessionProvider.getUser().getId());
        allLoanDao.update(param);
    }

    /**
     * 给对应的记录充值
     *
     * @param param
     */
    @Override
    public void recharge(LayuiAllLoanParam param) {
        allLoanDao.recharge(param);//充值金额
        //添加充值记录
        RechargeRecord rechargeRecord = new RechargeRecord();
        rechargeRecord.setId(UUID.randomUUID().toString().replace("-", ""));
        rechargeRecord.setLoanId(param.getId());
        rechargeRecord.setCreateId(sessionProvider.getUser().getId());
        rechargeRecord.setCreateTime(new Date());
        rechargeRecord.setBalance(param.getBalance());
        rechargeRecord.setRechargeAmount(param.getRecharge());
        rechargeRecordDao.insert(rechargeRecord);
    }

    /**
     * 余额小于0时候自动下架
     */
    @Override
    public void soldOut() {
        allLoanDao.soldOut();
    }

    @Override
    public List<AllLoan> getAllLoanList(AllLoan allLoan, Integer pageNum, Integer pageSize) {

        return allLoanDao.getAllLoanList(allLoan,pageNum,pageSize);
    }
}
