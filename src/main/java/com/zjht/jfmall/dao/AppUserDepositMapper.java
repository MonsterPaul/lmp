package com.zjht.jfmall.dao;

import com.zjht.jfmall.entity.AppUserDeposit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * <p>
 * app用户提现表  Mapper 接口
 * </p>
 *
 * @author liuyu
 * @since 2018-11-29
 */
@Repository
public interface AppUserDepositMapper extends Mapper<AppUserDeposit> {

    void updateStatus(@Param("id") String id, @Param("status") String status, @Param("oprationId") String oprationId);

    List<AppUserDeposit> getCashWithdrawal(@Param("id") String id,@Param("num")Integer pageNum,@Param("size")Integer pageSize);

    /**
     * 分页查询
     * @param bean
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<AppUserDeposit> findPage(@Param("bean") AppUserDeposit bean, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    int countUser(@Param("id") String appUserId);

    Double sumAmount(@Param("id") String appUserId);

    List<AppUserDeposit> find(@Param("bean") AppUserDeposit bean);
}
