package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.dao.AdvertisementDao;
import com.zjht.jfmall.dao.UserDao;
import com.zjht.jfmall.entity.Advertisement;
import com.zjht.jfmall.service.AdvertisementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by vip on 2018/11/29.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementDao dao;
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private UserDao userDao;

    @Override
    public PageInfo<Advertisement> getPage(Advertisement ad, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Advertisement.class);
        example.setOrderByClause("operator_date desc");
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(ad.getName())) {
            criteria.andLike("name", "%" + ad.getName() + "%");
        }
        List<Advertisement> list = dao.selectByExample(example);
        list.stream().forEach(adv -> {
            adv.setUser(userDao.selectByPrimaryKey(adv.getOperatorId()));
        });
        return new PageInfo<>(list);
    }

    @Override
    public void insert(Advertisement bean) {
        bean.setId(UUID.randomUUID().toString().replace("-", ""));
        bean.setOperatorId(sessionProvider.getUser().getId());
        bean.setOperatorDate(new Date());
        dao.insert(bean);
    }

    @Override
    public void deleteById(String id) {
        dao.deleteById(id);
    }

    @Override
    public Advertisement findById(String id) {
        return dao.findById(id);
    }

    @Override
    public void update(Advertisement bean) {
        bean.setOperatorId(sessionProvider.getUser().getId());
        bean.setOperatorDate(new Date());
        dao.update(bean);
    }

    @Override
    public void updateStatus(String id, int status) {
        Advertisement bean = dao.findById(id);
        bean.setOperatorId(sessionProvider.getUser().getId());
        bean.setOperatorDate(new Date());
        bean.setStatus(status);
        dao.update(bean);
    }

    @Override
    public List<Advertisement> getAdvertisementList() {
        Example example = new Example(Advertisement.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");
        List<Advertisement> list = dao.selectByExample(example);
        return list;
    }
}
