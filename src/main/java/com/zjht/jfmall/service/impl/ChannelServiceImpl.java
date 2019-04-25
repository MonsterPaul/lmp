package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.common.dto.ResultFailDto;
import com.zjht.jfmall.common.dto.ResultSuccessDto;
import com.zjht.jfmall.dao.ApiChannelDao;
import com.zjht.jfmall.entity.ApiChannel;
import com.zjht.jfmall.service.ChannelService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 渠道service实现
 *
 * @outhor caozk
 * @create 2017-09-05 19:46
 */
@Transactional
@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    private ApiChannelDao apiChannelDao;
    @Override
    public ApiChannel getById(String id) {
        return apiChannelDao.selectByPrimaryKey(id);
    }

    @Override
    public ResultDto addChannel(ApiChannel channel, String[] serviceIds) {

        ApiChannel channelQuery = new ApiChannel();
        channelQuery.setChannelCode(channel.getChannelCode());
        List<ApiChannel> existCodeList = apiChannelDao.select(channelQuery);
        if(existCodeList!=null && !existCodeList.isEmpty()){
            return new ResultFailDto("渠道编码已经存在");
        }
        //插入渠道
        apiChannelDao.insert(channel);

        return new ResultSuccessDto();
    }

    @Override
    public void update(ApiChannel channel, String[] serviceIds) {
        //更新渠道信息
        apiChannelDao.updateByPrimaryKey(channel);

    }

    @Override
    public ResultDto updateSelective(ApiChannel channel, String[] serviceIds) {

        if(channel.getChannelCode() != null){
            ApiChannel channelQuery = new ApiChannel();
            channelQuery.setChannelCode(channel.getChannelCode());
            List<ApiChannel> existCodeList = apiChannelDao.select(channelQuery);
            if(existCodeList!=null &&
                    (existCodeList.size()>1 ||(existCodeList.size()==1 && !existCodeList.get(0).getId().equals(channel.getId())))){
                return new ResultFailDto("渠道编码已经存在");
            }
        }
        apiChannelDao.updateByPrimaryKeySelective(channel);
        return new ResultSuccessDto();
    }


    @Override
    public void delete(ApiChannel channel) {

        //删除渠道
        apiChannelDao.delete(channel);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (int i=0; i<ids.length; i++){
            apiChannelDao.deleteByPrimaryKey(ids[i]);
        }
    }

    @Override
    public void updateStatus(String[] ids, int status) {
        for (int i=0; i<ids.length; i++){
            ApiChannel channel = new ApiChannel();
            channel.setId(ids[i]);
            channel.setStatus(status);
            apiChannelDao.updateByPrimaryKeySelective(channel);
        }
    }

    @Override
    public List<ApiChannel> getList(ApiChannel channel) {
        return apiChannelDao.select(channel);
    }

    @Override
    public PageInfo<ApiChannel> getPage(ApiChannel channel, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Example example = new Example(ApiChannel.class);
        example.setOrderByClause(channel.getOrderByClause());
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(channel.getChannelName())){
            criteria.andLike("channelName","%" + channel.getChannelName() + "%");
        }
        //已删除状态的不查询
        criteria.andNotEqualTo("status", ApiChannel.Status.DELETED.getStatus());

        List<ApiChannel> list = apiChannelDao.selectByExample(example);

        PageInfo<ApiChannel> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 根据Id获取名字
     *
     * @param id
     * @return
     */
    @Override
    public String getNameById(String id) {
        return apiChannelDao.getNameById(id);
    }

    @Override
    public List<ApiChannel> getListIsNotDelete(int status) {
        return apiChannelDao.getListIsNotDelete(status);
    }
}
