package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.dao.UserMailListMapper;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.UserMailList;
import com.zjht.jfmall.service.UserMailListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * app用户通讯录  服务实现类
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Service
public class UserMailListServiceImpl implements UserMailListService {
    @Autowired
    private UserMailListMapper userMailListMapper;

    @Override
    public int insert(String userId, List<UserMailList> userMailListList) {
        for (UserMailList userMailList : userMailListList) {
            userMailList.setAppUserId(userId);
            userMailList.setId(UUID.randomUUID().toString().replace("-", ""));
        }
        return userMailListMapper.insertData(userMailListList);
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
    public PageInfo<UserMailList> findPage(AppUser bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询参数
        //2.1创建条件对象
        Example example  = new Example(UserMailList.class);
        Example.Criteria criteria = example.createCriteria();
        //2.2判断查询条件
        criteria.andEqualTo("appUserId", bean.getId());
        return new PageInfo<UserMailList>(userMailListMapper.selectByExample(example));
    }
}
