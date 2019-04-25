package com.zjht.jfmall.service;

import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.entity.Advertisement;

import java.util.List;

/**
 * Created by vip on 2018/11/29.
 */
public interface AdvertisementService {


    PageInfo<Advertisement> getPage(Advertisement ad, int pageNum, int pageSize);

    void insert(Advertisement bean);

    void deleteById(String id);

    Advertisement findById(String id);

    void update(Advertisement bean);

    void updateStatus(String id, int status);

    List<Advertisement> getAdvertisementList();
}
