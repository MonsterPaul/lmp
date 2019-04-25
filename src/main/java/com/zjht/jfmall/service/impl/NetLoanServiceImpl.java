package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.dao.NetLoanMapper;
import com.zjht.jfmall.dao.UserDao;
import com.zjht.jfmall.entity.NetLoan;
import com.zjht.jfmall.service.NetLoanService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 网贷大全表 服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
public class NetLoanServiceImpl implements NetLoanService {

    @Autowired
    private NetLoanMapper dao;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo<NetLoan> getPage(NetLoan bean, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(NetLoan.class);
        example.setOrderByClause("operation_time desc");
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(bean.getName())) {
            criteria.andLike("name", "%" + bean.getName() + "%");
        }
        List<NetLoan> list = dao.selectByExample(example);
        list.stream().forEach(adv -> {
            adv.setUser(userDao.selectByPrimaryKey(adv.getOperationId()));
        });
        return new PageInfo<>(list);
    }

    @Override
    public void insert(NetLoan bean) {
        bean.setId(UUID.randomUUID().toString().replace("-", ""));
        bean.setOperationId(sessionProvider.getUser().getId());
        bean.setOperationTime(new Date());
        dao.insert(bean);
    }

    @Override
    public void deleteById(String id) {
        dao.deleteById(id);
    }

    @Override
    public NetLoan findById(String id) {
        return dao.findById(id);
    }

    @Override
    public void update(NetLoan bean) {
        bean.setOperationId(sessionProvider.getUser().getId());
        bean.setOperationTime(new Date());
        dao.update(bean);
    }

    @Override
    public PageInfo<NetLoan> getNetloanRank(NetLoan ad, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(dao.getNetloanRank(ad));
    }

}
