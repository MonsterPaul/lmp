package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.dao.AppUserDepositMapper;
import com.zjht.jfmall.dao.AppUserMapper;
import com.zjht.jfmall.dao.UserDao;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.AppUserDeposit;
import com.zjht.jfmall.service.AppUserDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * app用户提现表  服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
@Transactional
public class AppUserDepositServiceImpl implements AppUserDepositService {
    @Autowired
    private AppUserDepositMapper appUserDepositMapper;
    @Autowired
    private AppUserMapper        appUserMapper;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SessionProvider sessionProvider;

    @Override
    public int insert(AppUser appUser, double money) {
        appUserMapper.updateByPrimaryKeySelective(appUser);

        AppUserDeposit appUserDeposit = new AppUserDeposit();
        appUserDeposit.setId(UUID.randomUUID().toString().replace("-", ""));
        appUserDeposit.setApplyDate(new Date());
        appUserDeposit.setStatus("0");
        appUserDeposit.setAmount(money);
        appUserDeposit.setAppUserId(appUser.getId());

        return appUserDepositMapper.insertSelective(appUserDeposit);
    }

    @Override
    public List<AppUserDeposit> getCashWithdrawal(String id, Integer pageNum, Integer pageSize) {
        return  appUserDepositMapper.getCashWithdrawal(id,pageNum,pageSize);
    }

    /**
     * 分页查询
     *
     * @param bean
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<AppUserDeposit> getPage(AppUserDeposit bean, int pageNum, int pageSize) {
        List<AppUserDeposit> list = appUserDepositMapper.findPage(bean, pageNum, pageSize);
        list.stream().forEach(adv -> {
            adv.setAppUser(appUserMapper.findById(adv.getAppUserId()));
            adv.setUser(userDao.selectByPrimaryKey(adv.getOpertionId()));
        });
        return new PageInfo<>(list);
    }

    /**
     * 修改申请记录状态
     *
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(String id, String status) {
        appUserDepositMapper.updateStatus(id, status, sessionProvider.getUser().getId());
    }

    @Override
    public int countUser(String appUserId) {
        return appUserDepositMapper.countUser(appUserId);
    }

    @Override
    public Double sumAmount(String appUserId) {
        return appUserDepositMapper.sumAmount(appUserId);
    }

    @Override
    public List<AppUserDeposit> find(AppUserDeposit bean, int pageNum, int pageSize) {
        List<AppUserDeposit> list = appUserDepositMapper.find(bean);
        list.stream().forEach(adv -> {
            adv.setAppUser(appUserMapper.findById(adv.getAppUserId()));
            adv.setUser(userDao.selectByPrimaryKey(adv.getOpertionId()));
        });
        return list;
    }
}
