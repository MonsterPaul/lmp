package com.zjht.jfmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zjht.jfmall.common.dto.ResultDto;
import com.zjht.jfmall.common.dto.ResultFailDto;
import com.zjht.jfmall.common.dto.ResultSuccessDto;
import com.zjht.jfmall.common.web.Constants;
import com.zjht.jfmall.common.web.session.HttpSessionProvider;
import com.zjht.jfmall.common.web.session.SessionProvider;
import com.zjht.jfmall.common.web.threadvariable.RequestThread;
import com.zjht.jfmall.dao.AppUserMapper;
import com.zjht.jfmall.dao.UserDao;
import com.zjht.jfmall.dao.UserRoleDao;
import com.zjht.jfmall.entity.AppUser;
import com.zjht.jfmall.entity.User;
import com.zjht.jfmall.entity.UserRole;
import com.zjht.jfmall.service.LogService;
import com.zjht.jfmall.service.UserService;
import com.zjht.jfmall.util.FileNameUtils;
import com.zjht.jfmall.util.QrCodeUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional//开启事物
public class UserServiceImpl implements UserService {

    @Value(value = "${service_ip}")
    private String serviceIp;
    @Value(value = "${channel_id}")
    private String channelId;
    @Value(value = "${file.savePath}")
    private String savePath;

    @Autowired
    private UserDao             userDao;
    @Autowired
    private UserRoleDao         userRoleDao;
    @Autowired
    private SessionProvider     sessionProvider;
    @Autowired
    private HttpSessionProvider session;
    @Autowired
    private LogService          logService;
    @Autowired
    private AppUserMapper appUserMapper;

    @Override
    public int deleteById(String id) {
        /**
         * 1.删除用户和角色关系表
         * 2.删除用户信息
         */
        deleteUserRoleByUserId(id);
        return userDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User user) {
        /**
         * 1.保存用户角色关系
         * 2.保存用户信息
         */
        int result = userDao.insert(user);
        saveUserRole(user);
        return result;
    }

    @Override
    public int update(User user) {
        /**
         * 1.修改用户信息
         * 2.删除用户角色关系
         * 2.1重新保存用户角色关系
         */
        int result = userDao.updateByPrimaryKeySelective(user);//修改
        deleteUserRoleByUserId(user.getId());//删除
        saveUserRole(user);//保存
        return result;
    }

    @Override
    public List<String> getPermissionsById(String userId) {
        return userDao.getPermissionsById(userId);
    }

    /**
     * 分页查询
     *
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<User> findPage(User user, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        //2.封装查询参数
        //2.1创建条件对象
        Example          example  = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //2.2判断查询条件
        if (StringUtils.isNotBlank(user.getUsername())) {//登录账号
            criteria.andLike("username", "%" + user.getUsername() + "%");
        }
        if (user.getType() != null) {
            criteria.andEqualTo("type", user.getType());
        }
        return new PageInfo<User>(userDao.selectByExample(example));
    }

    /**
     * 渠道商用户分页查询
     *
     * @param bean
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<User> findChannelUserPage(User bean, int pageNum, int pageSize) {
        //1.设置分页查询参数
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(User.class);
        example.setOrderByClause("create_time desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNotNull("channelPerId");//渠道专员Id不为空的就是渠道商
        if (StringUtils.isNotBlank(bean.getNickName())) {
            criteria.andLike("nickName", "%" + bean.getNickName() + "%");
        }
        if (StringUtils.isNotBlank(bean.getUsername())) {
            criteria.andLike("username", "%" + bean.getUsername() + "%");
        }
        if (StringUtils.isNotBlank(bean.getMobile())) {
            criteria.andLike("mobile", "%" + bean.getMobile() + "%");
        }
        if (StringUtils.isNotBlank(bean.getLinkMan())) {
            criteria.andLike("linkMan", "%" + bean.getLinkMan() + "%");
        }
        if (StringUtils.isNotBlank(bean.getId())) {
            criteria.andEqualTo("channelPerId", bean.getId());
        }

        if(StringUtils.isNotBlank(bean.getTgyName())){
            Example example1 = new Example(User.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andLike("nickName", "%" + bean.getTgyName() + "%");
            List<User> userList=userDao.selectByExample(example1);
            List<String> list =new ArrayList<>();
            if(CollectionUtils.isNotEmpty(userList)){
                for(User user:userList){
                    list.add(user.getId());
                }
                criteria.andIn("channelPerId",list);
            }else{
               return new PageInfo<User>();
            }

        }

        return new PageInfo<User>(userDao.selectByExample(example));
    }

    /**
     * 添加渠道商用户
     *
     * @param bean
     */
    @Override
    public void insertChannelUser(User bean) throws IOException {
        bean.setId(UUID.randomUUID().toString().replace("-", ""));
        bean.setCreateTime(new Date());
        bean.setChannelLink(serviceIp + bean.getId());
        bean.setRoleIds(new String[]{channelId});
        bean.setChannelPerId(sessionProvider.getUser().getId());
        bean.setPassword(DigestUtils.md5Hex(bean.getPassword()));//MD5加密
        //二维码信息生成
        String filePath = getSystemUploadDir(savePath + FileNameUtils.genPathName() + Constants.SPT);
        QrCodeUtil.checkPath(filePath);
        bean.setQrCode(savePath + FileNameUtils.genPathName() + Constants.SPT + bean.getId() + ".png");
        QrCodeUtil.encoderQRCode(serviceIp + bean.getId(),
                new FileOutputStream(filePath + bean.getId() + ".png"), "png", 8);
        this.insert(bean);
    }


    /**
     * 设置文件上传目录
     *
     * @param dir
     * @return
     */
    private String getSystemUploadDir(String dir) {
        return getClass().getResource("/").getPath().replace("/WEB-INF/classes/", dir);
    }

    /**
     * 编辑渠道商用户
     *
     * @param bean
     */
    @Override
    public void updateChannelUser(User bean) {
        bean.setUpdateTime(new Date());
        bean.setRoleIds(new String[]{channelId});
        if (StringUtils.isNotBlank(bean.getPassword())) {
            bean.setPassword(DigestUtils.md5Hex(bean.getPassword()));//MD5加密
        } else {
            bean.setPassword(null);
        }
        this.update(bean);
    }

    @Override
    public void updateStatus(String id, int status) {
        userDao.updateStatus(id, status);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public User findById(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     * 根据登录名称查询是否存在
     *
     * @param user
     * @return
     */
    @Override
    public long countByUserName(User user) {
        //1.封装查询参数
        //1.1创建条件对象
        Example          example  = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        //1.2判断查询条件
        criteria.andEqualTo("username", user.getUsername());//根据登录名称查询
        if (StringUtils.isNotBlank(user.getId())) {//修改不包含自己
            criteria.andNotEqualTo("id", user.getId());
        }
        return userDao.selectCountByExample(example);
    }

    /**
     * 根据用户ID删除用户-角色关系表
     *
     * @param userId
     */
    private void deleteUserRoleByUserId(String userId) {
        Example          example  = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        userRoleDao.deleteByExample(example);
    }

    /**
     * 保存用户-角色关系表
     *
     * @param user
     */
    private void saveUserRole(User user) {
        if (user.getRoleIds() != null && user.getRoleIds().length > 0) {
            for (String roleId : user.getRoleIds()) {
                UserRole ur = new UserRole();
                ur.setRoleId(roleId);
                ur.setUserId(user.getId());
                ur.setId(UUID.randomUUID().toString().replace("-", ""));
                userRoleDao.insert(ur);
            }
        }
    }

    @Override
    public List<User> getUserByRoleId(String roleId) {
        return userDao.getUserByRoleIdAndStatus(roleId);
    }

    @Override
    public PageInfo<User> getcCommissionRanking(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<User>(userDao.getCommissionRanking(user));
    }

    @Override
    public PageInfo<User> getLoanRanking(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<User>(userDao.getLoanRanking(user));
    }

    @Override
    public PageInfo<User> findUserByRoleId(User bean, String roleId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<User>(userDao.findUserByRoleId(bean, roleId));
    }

    @Override
    public int countChannelNum(String channelPerId) {
        Example          example  = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("channelPerId", channelPerId);
        return userDao.selectCountByExample(example);
    }

    @Override
    public int countChannelNums(User user) {
        Example          example  = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("channelPerId", user.getId());
        criteria.andLike("createTime", user.getExchangeTimeBegin() + "%");
        return userDao.selectCountByExample(example);
    }

    @Override
    public Integer countByTime(String channelId, String startTime, String endTime) {
        return userDao.countByTime(channelId, startTime, endTime);
    }

    @Override
    public int getLoginNum() {
        Example          example  = new Example(AppUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isLogin", "1");
        return appUserMapper.selectCountByExample(example);
    }

    @Override
    public String findInvitationName(String beinvitedId) {
        return userDao.findInvitationName(beinvitedId);
    }

    /**
     * 根据角色ID查询角色下的User对象
     *
     * @param roleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<User> getUserByRoleId(String roleId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<User>(userDao.getUserByRoleId(roleId));
    }

    @Override
    public ResultDto updatePasswordByName(String userName, String password) {

        List<User> users = userDao.getUsersByName(userName);
        if (CollectionUtils.isEmpty(users)) {
            return new ResultFailDto("用户不存在");
        }
        for (User user : users) {
            //更新密码
            user.setPassword(DigestUtils.md5Hex(password));
            userDao.updateByPrimaryKeySelective(user);
        }
        return new ResultSuccessDto("更新成功");
    }

    @Override
    public ResultDto loginFront(String userName, String password, String channelId) {

        User user = userDao.getUserByNameAndChannel(userName, channelId);
        if (user == null) {
            return new ResultFailDto("用户不存在");
        }
        if (user != null && user.getType().equals(User.Type.BACK.getType())) {
            return new ResultFailDto("该用户非积分商城前端用户");
        }
        //校验用户名密码
        if (!user.getPassword().equals(DigestUtils.md5Hex(password))) {
            return new ResultFailDto("用户名或密码不正确");
        }
        //将用户放入session
        session.setAttribute(RequestThread.get(), null, Constants.MEMBER_SESSION_KEY, user);
        return new ResultSuccessDto("登录成功");
    }

    @Override
    public ResultDto loginBack(String userName, String password, String ip) {
        User user = userDao.getUserOneByName(userName);
        if (user == null) {
            return new ResultFailDto("用户不存在");
        }
        //校验用户名密码
        if (!user.getPassword().equals(DigestUtils.md5Hex(password))) {
            return new ResultFailDto("用户名或密码不正确");
        }
        if ("1".equals(user.getStatus())) {
            return new ResultFailDto("账号被冻结,无法登陆");
        }
        //用户登录
        sessionProvider.login(user.getUsername(), user.getPassword());
        //将用户信息存入session
        sessionProvider.setAttribute("loginIP", user.getLastLoginIp());
        sessionProvider.setAttribute("loginTime", user.getLastLoginTime());
        sessionProvider.setAttribute("loginCount", user.getLoginCount());
        sessionProvider.setUser(user);
        user.setLastLoginIp(ip);
        user.setLastLoginTime(new Date());
        user.setLoginCount(user.getLoginCount() + 1);
        userDao.updateByPrimaryKeySelective(user);

        logService.add("登陆");
        return new ResultSuccessDto("登录成功");
    }
    
    @Override
    public int client(String id) {
        return userDao.client(id);
    }

    @Override
    public int distribution(String id) {
        return userDao.distribution(id);
    }
}
