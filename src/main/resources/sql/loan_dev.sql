/*
Navicat MySQL Data Transfer

Source Server         : loan_dev@localhost
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : loan_dev

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-04-23 20:18:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for advertisement
-- ----------------------------
DROP TABLE IF EXISTS `advertisement`;
CREATE TABLE `advertisement` (
  `id` varchar(50) NOT NULL,
  `url` varchar(500) DEFAULT NULL COMMENT '广告链接',
  `img` text COMMENT '图片链接',
  `content` varchar(1024) DEFAULT NULL COMMENT '内容',
  `status` int(11) DEFAULT '1' COMMENT '1上架，2下架',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `operator_id` varchar(50) DEFAULT NULL COMMENT '操作人Id',
  `operator_date` datetime DEFAULT NULL COMMENT '最后操作时间',
  `name` varchar(50) DEFAULT NULL COMMENT '广告名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='广告';

-- ----------------------------
-- Table structure for all_loan
-- ----------------------------
DROP TABLE IF EXISTS `all_loan`;
CREATE TABLE `all_loan` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '平台名称',
  `logo` varchar(200) DEFAULT NULL COMMENT 'logo',
  `type` int(2) DEFAULT '0' COMMENT '类型：1芝麻分贷,2小额贷,3大额贷,4信用贷,5信用卡贷,6公积金贷,7抵押贷',
  `introduction` varchar(200) DEFAULT NULL COMMENT '介绍',
  `status` int(1) DEFAULT '0' COMMENT '状态：0上架，1下架',
  `amount` int(2) DEFAULT '0' COMMENT '额度：1:1000以下,2：1000-3000,3：3000-1万:4：1万-5万:5：5万以上',
  `deadline` int(2) DEFAULT '0' COMMENT '期限：1：7-14天:2：14-30天:3：30天以上',
  `passing_rate` varchar(10) DEFAULT '0%' COMMENT '通过率',
  `year_rate` varchar(10) DEFAULT '0%' COMMENT '年利率',
  `raiders` varchar(255) DEFAULT NULL COMMENT '下款攻略',
  `apply_condition` varchar(200) DEFAULT NULL COMMENT '申请条件',
  `description` varchar(200) DEFAULT NULL COMMENT '特别说明',
  `apply_num` int(10) DEFAULT '0' COMMENT '已申请人数',
  `regist_link` varchar(255) DEFAULT NULL COMMENT '注册链接',
  `top` int(2) DEFAULT '0' COMMENT '是否置顶 0置顶，1不置顶',
  `pill_way` varchar(10) DEFAULT NULL COMMENT '计费方式',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '价格',
  `settlement_way` varchar(10) DEFAULT NULL COMMENT '结算方式',
  `person` varchar(10) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(32) DEFAULT NULL COMMENT '创建人',
  `operation_id` varchar(32) DEFAULT NULL COMMENT '最后操作人',
  `further_id` varchar(32) DEFAULT NULL COMMENT '运营人员ID',
  `operation_time` datetime DEFAULT NULL COMMENT '最后操作时间',
  `top_time` datetime DEFAULT NULL COMMENT '置顶时候排序，最后操作的时间排前面',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for all_loan_chick
-- ----------------------------
DROP TABLE IF EXISTS `all_loan_chick`;
CREATE TABLE `all_loan_chick` (
  `id` varchar(50) NOT NULL COMMENT '主键ID',
  `all_loan_id` varchar(50) DEFAULT NULL COMMENT '贷超ID',
  `ip` varchar(50) DEFAULT NULL COMMENT '请求ip',
  `app_user_id` varchar(50) DEFAULT NULL COMMENT '请求userId',
  `create_time` datetime DEFAULT NULL COMMENT '点击时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='贷超链接点击';

-- ----------------------------
-- Table structure for apply_record
-- ----------------------------
DROP TABLE IF EXISTS `apply_record`;
CREATE TABLE `apply_record` (
  `id` varchar(50) NOT NULL,
  `app_user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `apply_amount` decimal(10,2) DEFAULT NULL COMMENT '申请贷款金额 ',
  `apply_date` datetime DEFAULT NULL COMMENT '申请时间',
  `user_id` varchar(50) DEFAULT NULL COMMENT '客服id',
  `operation_id` varchar(50) DEFAULT NULL COMMENT '最后操作人ID',
  `operation_time` datetime DEFAULT NULL COMMENT '最后操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请贷款记录表 ';

-- ----------------------------
-- Table structure for app_user
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` varchar(50) NOT NULL COMMENT 'id',
  `name` varchar(30) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `wechat` varchar(30) DEFAULT NULL,
  `qq` varchar(30) DEFAULT NULL,
  `id_card` varchar(30) DEFAULT NULL COMMENT '身份证号码',
  `card_z_url` varchar(255) DEFAULT NULL COMMENT '身份证正面',
  `card_f_url` varchar(255) DEFAULT NULL COMMENT '身份证反面',
  `id_card_url` varchar(255) DEFAULT NULL COMMENT '手持身份证照片',
  `heard_url` varchar(255) DEFAULT NULL COMMENT '头像图片',
  `bank_no` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `bank_address` varchar(255) DEFAULT NULL COMMENT '银行开户行',
  `bank_person` varchar(50) DEFAULT NULL COMMENT '持卡人',
  `bank_phone` varchar(20) DEFAULT NULL COMMENT '银行预留手机号',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '个人余额',
  `invite_link` varchar(255) DEFAULT NULL COMMENT '邀请链接',
  `service_id` varchar(50) DEFAULT NULL COMMENT '专属客服ID',
  `data_status` int(1) DEFAULT '0' COMMENT '个人资料认证状态',
  `iden_status` int(1) DEFAULT '0' COMMENT '通讯录认证状态',
  `operator_status` int(1) DEFAULT '0' COMMENT '运营商认证状态',
  `bank_status` int(1) DEFAULT '0' COMMENT '银行卡认证状态',
  `loans` int(10) DEFAULT '0' COMMENT '贷款额度',
  `loans_status` int(1) DEFAULT '2' COMMENT '是否申请贷款,1已贷款，2未贷款',
  `pwd` varchar(15) DEFAULT NULL COMMENT '密码',
  `status` int(1) DEFAULT '0' COMMENT '0正常1冻结',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `sesame_credit` int(8) DEFAULT '0' COMMENT '芝麻信用分',
  `credit_card` int(10) DEFAULT '0' COMMENT '信用卡额度',
  `is_invited` varchar(1) DEFAULT '0' COMMENT '是否被邀请:0自行注册1app用户邀请2渠道商邀请',
  `accumulation` varchar(3) DEFAULT '0' COMMENT '是否有公积金',
  `back_list` varchar(2) DEFAULT '0' COMMENT '黑名單状态',
  `is_login` varchar(1) DEFAULT '0' COMMENT '是否登录过0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app用户表';

-- ----------------------------
-- Table structure for app_user_basic
-- ----------------------------
DROP TABLE IF EXISTS `app_user_basic`;
CREATE TABLE `app_user_basic` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `app_user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(30) DEFAULT NULL COMMENT '身份证号码',
  `gender` varchar(3) DEFAULT NULL COMMENT '性别',
  `age` varchar(3) DEFAULT NULL COMMENT '年龄',
  `constellation` varchar(10) DEFAULT NULL COMMENT '星座',
  `province` varchar(30) DEFAULT NULL COMMENT '所属省',
  `city` varchar(30) DEFAULT NULL COMMENT '所属市',
  `region` varchar(30) DEFAULT NULL COMMENT '所属县',
  `native_place` varchar(100) DEFAULT NULL COMMENT '籍贯',
  `mobile` varchar(12) DEFAULT NULL COMMENT '手机号',
  `carrier_name` varchar(30) DEFAULT NULL COMMENT '用户姓名',
  `carrier_idcard` varchar(30) DEFAULT NULL COMMENT '用户身份证号码',
  `reg_time` varchar(20) DEFAULT NULL COMMENT '开户时间',
  `in_time` varchar(20) DEFAULT NULL COMMENT '开户时长',
  `email` varchar(30) DEFAULT NULL COMMENT '用户邮箱',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `reliability` varchar(10) DEFAULT NULL COMMENT '是否实名',
  `phone_attribution` varchar(50) DEFAULT NULL COMMENT '用户手机号码归属地',
  `live_address` varchar(100) DEFAULT NULL COMMENT '居住地址',
  `available_balance` varchar(10) DEFAULT NULL COMMENT '余额',
  `package_name` varchar(50) DEFAULT NULL COMMENT '套餐',
  `bill_certification_day` varchar(50) DEFAULT NULL COMMENT '账单认证日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app用户信息表';

-- ----------------------------
-- Table structure for app_user_deposit
-- ----------------------------
DROP TABLE IF EXISTS `app_user_deposit`;
CREATE TABLE `app_user_deposit` (
  `id` varchar(50) NOT NULL,
  `app_user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '提现金额',
  `apply_date` datetime DEFAULT NULL COMMENT '申请时间',
  `deposit_date` datetime DEFAULT NULL COMMENT '提现时间',
  `opertion_id` varchar(50) DEFAULT NULL,
  `status` int(1) DEFAULT NULL COMMENT '提现进度  未处理 处理中 已处理',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app用户提现表 ';

-- ----------------------------
-- Table structure for app_user_loan_plat
-- ----------------------------
DROP TABLE IF EXISTS `app_user_loan_plat`;
CREATE TABLE `app_user_loan_plat` (
  `id` varchar(50) NOT NULL,
  `app_user_id` varchar(50) DEFAULT NULL,
  `platform_id` varchar(50) DEFAULT NULL COMMENT '平台ID 关联网贷大全ID',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `apply_amount` decimal(10,2) DEFAULT '0.00' COMMENT '申请放款数额',
  `use_amount` decimal(10,2) DEFAULT '0.00' COMMENT '已放款数额',
  `apply_comm` decimal(10,2) DEFAULT '0.00' COMMENT '应收佣金(放款数额的百分之十五，配置文件配置)',
  `use_comm` decimal(10,2) DEFAULT '0.00' COMMENT '客服已收佣金',
  `stop_comm` decimal(10,2) DEFAULT '0.00' COMMENT '催收已收佣金',
  `back_loan_status` varchar(1) DEFAULT '0' COMMENT '结清状态0未结清1已结清',
  `coll_status` varchar(1) DEFAULT '0' COMMENT '催收状态 0无需催收1需要催收2催收完成3已申請4拒绝5客户放弃',
  `coll_user_id` varchar(50) DEFAULT NULL COMMENT '催收人ID （催收角色列表选择）',
  `service_user_id` varchar(50) DEFAULT NULL COMMENT '跟进客服ID',
  `coll_date` datetime DEFAULT NULL COMMENT '结清时间',
  `opertion_id` varchar(50) DEFAULT NULL COMMENT '最后操作人',
  `opertion_date` datetime DEFAULT NULL COMMENT '最后操作时间 （保存不展示）',
  `record_id` varchar(50) DEFAULT NULL COMMENT '关联贷款ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `stop_time` datetime DEFAULT NULL COMMENT '转催收时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app用户贷款平台';

-- ----------------------------
-- Table structure for call_contact_detail
-- ----------------------------
DROP TABLE IF EXISTS `call_contact_detail`;
CREATE TABLE `call_contact_detail` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `name` varchar(30) DEFAULT NULL COMMENT '联系人姓名',
  `app_user_id` varchar(50) DEFAULT NULL COMMENT 'app用户ID',
  `peer_num` varchar(20) DEFAULT NULL COMMENT '联系人号码',
  `group_name` varchar(20) DEFAULT NULL COMMENT '号码类型',
  `company_name` varchar(20) DEFAULT NULL COMMENT '号码标识',
  `city` varchar(20) DEFAULT NULL COMMENT '通话地',
  `p_relation` varchar(20) DEFAULT NULL COMMENT '与联系人关系',
  `call_cnt_1w` int(10) DEFAULT NULL COMMENT '近1周（最近7天）通话次数',
  `call_cnt_1m` int(10) DEFAULT NULL COMMENT '近1月（最近30天）通话次数',
  `call_cnt_3m` int(10) DEFAULT NULL COMMENT '近3月（最近0-90天）通话次数',
  `call_cnt_6m` int(10) DEFAULT NULL COMMENT '近6月（最近0-180天）通话次数',
  `call_time_3m` int(10) DEFAULT NULL COMMENT '近3月通话时长',
  `call_time_6m` int(10) DEFAULT NULL COMMENT '近6月通话时长',
  `trans_start` varchar(20) DEFAULT NULL COMMENT '第一次通话时间',
  `trans_end` varchar(20) DEFAULT NULL COMMENT '最后一次通话时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app用户通话记录表';

-- ----------------------------
-- Table structure for recharge_record
-- ----------------------------
DROP TABLE IF EXISTS `recharge_record`;
CREATE TABLE `recharge_record` (
  `id` varchar(32) NOT NULL,
  `loan_id` varchar(32) NOT NULL COMMENT '平台ID',
  `balance` decimal(10,2) DEFAULT '0.00' COMMENT '平台当前余额',
  `recharge_amount` decimal(10,2) DEFAULT '0.00' COMMENT '充值金额',
  `create_id` varchar(32) DEFAULT NULL COMMENT '充值人员',
  `create_time` datetime DEFAULT NULL COMMENT '充值时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='贷款大全充值记录';

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `Name` varchar(32) NOT NULL COMMENT '序列名称',
  `CurrentVal` bigint(20) DEFAULT NULL COMMENT '当前值',
  `IncrementVal` bigint(20) DEFAULT NULL COMMENT '步长',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_api_channel
-- ----------------------------
DROP TABLE IF EXISTS `t_api_channel`;
CREATE TABLE `t_api_channel` (
  `id` varchar(12) NOT NULL,
  `channel_name` varchar(64) DEFAULT NULL COMMENT '渠道名称',
  `channel_code` varchar(32) DEFAULT NULL COMMENT '渠道编码',
  `channel_account` varchar(32) DEFAULT NULL COMMENT '渠道访问我们系统的账号',
  `channel_key` varchar(32) DEFAULT NULL COMMENT '渠道秘钥',
  `status` int(11) DEFAULT NULL COMMENT '渠道状态（1-正常，0-停用，2-已删除）',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='接口访问渠道';

-- ----------------------------
-- Table structure for t_app_user_invitation
-- ----------------------------
DROP TABLE IF EXISTS `t_app_user_invitation`;
CREATE TABLE `t_app_user_invitation` (
  `id` varchar(50) NOT NULL,
  `invitation_id` varchar(50) DEFAULT NULL COMMENT '邀请人ID',
  `beinvited_id` varchar(50) DEFAULT NULL COMMENT '被邀请人ID',
  `invitation_time` datetime DEFAULT NULL COMMENT '邀请成功时间',
  `invitation_money` int(10) DEFAULT '0' COMMENT '奖励金额',
  `data_status` varchar(1) DEFAULT '0' COMMENT '个人资料认证状态0否1是',
  `iden_status` varchar(1) DEFAULT '0' COMMENT '通讯录认证状态0否1是',
  `operator_status` varchar(1) DEFAULT '0' COMMENT '运营商认证状态0否1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP用户邀请记录';

-- ----------------------------
-- Table structure for t_channel_click
-- ----------------------------
DROP TABLE IF EXISTS `t_channel_click`;
CREATE TABLE `t_channel_click` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `channel_business_id` varchar(50) DEFAULT NULL COMMENT '渠道商ID',
  `click_ip` varchar(50) DEFAULT NULL COMMENT '点击ip地址',
  `click_mac` varchar(100) DEFAULT NULL COMMENT '点击mac地址',
  `click_time` datetime DEFAULT NULL COMMENT '点击时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道商邀请链接点击表';

-- ----------------------------
-- Table structure for t_channel_invitation
-- ----------------------------
DROP TABLE IF EXISTS `t_channel_invitation`;
CREATE TABLE `t_channel_invitation` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `channel_business_id` varchar(50) DEFAULT NULL COMMENT '渠道商ID',
  `invitation_user_id` varchar(50) DEFAULT NULL COMMENT '邀请注册用户ID',
  `invitation_time` datetime DEFAULT NULL COMMENT '邀请成功时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道商邀请注册表';

-- ----------------------------
-- Table structure for t_collection_record
-- ----------------------------
DROP TABLE IF EXISTS `t_collection_record`;
CREATE TABLE `t_collection_record` (
  `id` varchar(50) NOT NULL COMMENT '主键ID',
  `collection_id` varchar(50) DEFAULT NULL COMMENT '催收员ID',
  `customer_id` varchar(50) DEFAULT NULL COMMENT '客服ID',
  `app_user_id` varchar(50) DEFAULT NULL COMMENT 'APP用户ID',
  `platform_id` varchar(50) DEFAULT NULL COMMENT 'app用户贷款平台ID',
  `collection_money` decimal(10,2) DEFAULT NULL COMMENT '催收金额',
  `operation_id` varchar(50) DEFAULT NULL COMMENT '操作人',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='催收记录表';

-- ----------------------------
-- Table structure for t_error_code
-- ----------------------------
DROP TABLE IF EXISTS `t_error_code`;
CREATE TABLE `t_error_code` (
  `id` varchar(12) NOT NULL,
  `error_code` varchar(12) DEFAULT NULL COMMENT '错误码',
  `error_msg` varchar(256) DEFAULT NULL COMMENT '错误消息',
  `business_code` varchar(12) DEFAULT NULL COMMENT '所属业务编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `user_id` varchar(12) DEFAULT NULL COMMENT '操作用户id',
  PRIMARY KEY (`id`),
  KEY `AK_uq_error_code` (`error_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='错误码表';

-- ----------------------------
-- Table structure for t_lifeintegral_electroniccode
-- ----------------------------
DROP TABLE IF EXISTS `t_lifeintegral_electroniccode`;
CREATE TABLE `t_lifeintegral_electroniccode` (
  `id` varchar(12) NOT NULL,
  `code` varchar(12) DEFAULT NULL COMMENT '电子码',
  `points` varchar(32) DEFAULT NULL COMMENT '电子码积分数',
  `status` int(11) DEFAULT NULL COMMENT '电子码状态（1-已导出，2-已发送，3-已使用，4-已作废, 5-发送失败）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(12) DEFAULT NULL COMMENT '创建者',
  `exchange_time` datetime DEFAULT NULL COMMENT '兑换时间',
  `mobile` varchar(11) DEFAULT NULL COMMENT '兑换用户手机号',
  `sender` varchar(12) DEFAULT NULL COMMENT '发送者用户id',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `remark` varchar(1024) DEFAULT NULL COMMENT '备注',
  `importor` varchar(12) DEFAULT NULL COMMENT '导入者',
  `import_time` datetime DEFAULT NULL COMMENT '导入时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='人寿积分电子码表';

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` varchar(50) NOT NULL DEFAULT '1',
  `business_code` varchar(12) DEFAULT NULL COMMENT '操作业务编码',
  `content` text COMMENT '日志内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `subject_id` varchar(50) DEFAULT NULL COMMENT '关联对象id',
  `type` int(2) DEFAULT '1' COMMENT '类型（1-后台日志，2-接口日志）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志';

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` varchar(50) NOT NULL COMMENT '菜单标识id',
  `code` varchar(7) DEFAULT NULL COMMENT '菜单编码，一级菜单为m01，二级为m0101，三级为m010101。现在的三级菜单可以理解为.do请求',
  `name` varchar(32) DEFAULT NULL COMMENT '菜单名',
  `parent_id` varchar(50) DEFAULT NULL COMMENT '父菜单id',
  `parent_ids` varchar(100) DEFAULT NULL COMMENT '所有上级id串，以英文逗号隔开',
  `path` varchar(128) DEFAULT NULL COMMENT '菜单路径',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序，数值越小越在前',
  `status` int(11) DEFAULT NULL COMMENT '菜单状态（1-在用，0-停用）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `user_id` varchar(50) DEFAULT NULL COMMENT '操作用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单信息表';

-- ----------------------------
-- Table structure for t_mobile_verify
-- ----------------------------
DROP TABLE IF EXISTS `t_mobile_verify`;
CREATE TABLE `t_mobile_verify` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` char(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `code` char(10) DEFAULT NULL COMMENT '验证码',
  `content` char(250) DEFAULT NULL COMMENT '内容',
  `status` int(1) DEFAULT NULL COMMENT '状态',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  `ipAddr` varchar(255) DEFAULT NULL COMMENT 'ip地址',
  `send_day_count` int(11) DEFAULT NULL COMMENT '当天发送次数',
  `send_hour_count` int(11) DEFAULT NULL COMMENT '每小时内发送次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_net_loan
-- ----------------------------
DROP TABLE IF EXISTS `t_net_loan`;
CREATE TABLE `t_net_loan` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `attribute` varchar(50) DEFAULT NULL COMMENT '属性',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `quota` decimal(10,2) DEFAULT NULL COMMENT '额度',
  `link_url1` varchar(200) DEFAULT NULL COMMENT '网址1',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `link_url5` varchar(200) DEFAULT NULL COMMENT '网址5',
  `link_url2` varchar(200) DEFAULT NULL COMMENT '网址',
  `link_url3` varchar(200) DEFAULT NULL COMMENT '网址3',
  `link_url4` varchar(200) DEFAULT NULL COMMENT '网址4',
  `operation_id` varchar(50) DEFAULT NULL COMMENT '操作人ID',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网贷大全表';

-- ----------------------------
-- Table structure for t_order_assist
-- ----------------------------
DROP TABLE IF EXISTS `t_order_assist`;
CREATE TABLE `t_order_assist` (
  `id` varchar(20) NOT NULL COMMENT '主键id',
  `order_id` varchar(20) NOT NULL COMMENT '主表订单id',
  `assistCode` varchar(32) DEFAULT NULL COMMENT '辅助码',
  `mobile` varchar(32) DEFAULT NULL COMMENT '发送手机号',
  `respCode` varchar(32) DEFAULT NULL COMMENT '返回码',
  `respMsg` varchar(128) DEFAULT NULL COMMENT '返回消息',
  `barCode` varchar(32) DEFAULT NULL COMMENT '关联系统编码',
  `couponCode` varchar(32) DEFAULT NULL COMMENT '券码产品编号',
  `couponName` varchar(128) DEFAULT NULL COMMENT '券码名称',
  `sendTime` datetime DEFAULT NULL COMMENT '发送时间',
  `parPrice` decimal(10,2) DEFAULT NULL COMMENT '券码价格',
  `status` varchar(32) DEFAULT NULL COMMENT '券码状态',
  `endTime` datetime DEFAULT NULL COMMENT '券码失效时间',
  `refundId` varchar(0) DEFAULT NULL COMMENT '退款记录ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单辅助表';

-- ----------------------------
-- Table structure for t_order_exchange
-- ----------------------------
DROP TABLE IF EXISTS `t_order_exchange`;
CREATE TABLE `t_order_exchange` (
  `id` char(12) NOT NULL COMMENT 'ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `product_id` char(12) NOT NULL COMMENT '商品ID',
  `create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `end_time` datetime DEFAULT NULL COMMENT '订单完成时间',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID暂时没有可以为空',
  `phone` varchar(11) NOT NULL COMMENT '兑换登录手机号码',
  `integral` varchar(9) NOT NULL COMMENT '使用积分',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '产品价格',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '总金额',
  `status` int(2) DEFAULT '1' COMMENT '订单状态(0待处理 1审核中 2取消 3待发货 4已发货 5完成 6锁定 7-超时取消 99删除)',
  `remark` varchar(255) DEFAULT NULL COMMENT '订单备注',
  `openId` varchar(32) DEFAULT NULL COMMENT '用户微信OPENID',
  `deal_type` int(2) DEFAULT NULL COMMENT '详情处理类型（1-发码，2-红包）',
  `deal_status` int(2) DEFAULT '0' COMMENT '处理状态(0待处理，1已处理，2处理失败，3状态已回查)',
  `resp_code` varchar(50) DEFAULT NULL COMMENT '请求响应码',
  `resp_msg` varchar(500) DEFAULT NULL COMMENT '请求响应描述',
  `total` bigint(20) NOT NULL COMMENT '购买数量',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no_index` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='兑换订单信息';

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` char(12) NOT NULL,
  `product_code` varchar(50) NOT NULL COMMENT '商品编码',
  `category_code` varchar(30) NOT NULL COMMENT '所属类目编码',
  `product_name` varchar(50) NOT NULL COMMENT '商品名称',
  `status` tinyint(4) NOT NULL COMMENT '状态：0-正常；1-停用；2-删除',
  `gmt_create` datetime NOT NULL COMMENT '生成时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `stock_count` int(11) DEFAULT '0' COMMENT '库存总数',
  `sale_count` int(11) DEFAULT '0' COMMENT '已卖总数',
  `product_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '商品类型：0-加油券；1-商超券',
  `points` int(11) DEFAULT NULL COMMENT '产品所需积分',
  `strategy_name` varchar(255) DEFAULT NULL COMMENT '产品策略名字',
  `out_code` varchar(30) DEFAULT NULL COMMENT '产品外部编码',
  `page_exchange` varchar(255) DEFAULT NULL COMMENT '产品兑换页',
  `page_result` varchar(255) DEFAULT NULL,
  `market_price` decimal(10,2) DEFAULT NULL COMMENT '市场价',
  `sale_price` decimal(10,2) DEFAULT NULL COMMENT '销售价',
  `introduction` longtext COMMENT '商品详情介绍',
  `sort` bigint(11) DEFAULT '0' COMMENT '前端展示排序字段，越大越靠前',
  `day_stock` int(11) DEFAULT NULL COMMENT '日库存',
  `day_user_limit` int(11) DEFAULT NULL COMMENT '每日用户购买限制',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_product_code` (`product_code`) USING BTREE,
  KEY `idx_category_code` (`category_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品信息';

-- ----------------------------
-- Table structure for t_product_img
-- ----------------------------
DROP TABLE IF EXISTS `t_product_img`;
CREATE TABLE `t_product_img` (
  `id` char(12) NOT NULL,
  `product_id` varchar(50) NOT NULL COMMENT '产品id',
  `type` varchar(30) NOT NULL COMMENT '图片类型：1-logo；2-详情',
  `path` varchar(50) NOT NULL COMMENT '图片路径',
  `create_time` datetime DEFAULT NULL COMMENT '上传时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序（数值越大越靠前）',
  `user_id` varchar(12) DEFAULT NULL COMMENT '操作管理员标识',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品图片表';

-- ----------------------------
-- Table structure for t_risk_record
-- ----------------------------
DROP TABLE IF EXISTS `t_risk_record`;
CREATE TABLE `t_risk_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` varchar(50) NOT NULL,
  `trans_id` varchar(32) NOT NULL COMMENT '商户请求订单号',
  `trade_no` varchar(32) NOT NULL COMMENT '新颜交易响应流水号',
  `id_no` varchar(32) NOT NULL COMMENT '身份证号(MD5为32位小写)',
  `id_name` varchar(32) NOT NULL COMMENT '姓名(MD5为32位小写)',
  `success` varchar(6) NOT NULL COMMENT '响应状态(true/false,此字段是通信及请求权限标识，非交易标识，交易是否成功需要查看data来判断)',
  `errorCode` varchar(8) DEFAULT NULL COMMENT '错误码(success为false为接口响应错误代码)',
  `errorMsg` varchar(80) DEFAULT NULL COMMENT '错误信息(success为false为接口响应错误描述)',
  `tran_code` varchar(1) DEFAULT NULL COMMENT '查询结果码(0：查询成功,1：查询未命中,9：其他异常)',
  `tran_data` varchar(2000) DEFAULT NULL COMMENT '业务成功数据(success为true时有值)',
  `tran_desc` varchar(80) DEFAULT NULL COMMENT '查询结果描述',
  `fee` varchar(1) DEFAULT NULL COMMENT '收费标示(Y：收费,N：不收费)',
  `versions` varchar(8) DEFAULT NULL COMMENT '版本号',
  `create_time` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` varchar(50) NOT NULL DEFAULT '' COMMENT '角色标识id',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名',
  `status` int(11) DEFAULT NULL COMMENT '角色状态（0-失效; 1-正常）',
  `remarks` varchar(256) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色id',
  `menu_id` varchar(50) DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单表';

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(50) NOT NULL COMMENT '用户id',
  `nick_name` varchar(18) DEFAULT NULL COMMENT '昵称',
  `password` varchar(256) DEFAULT NULL COMMENT '用户密码密文形式',
  `mobile` varchar(11) DEFAULT NULL COMMENT '用户手机号',
  `username` varchar(64) DEFAULT NULL COMMENT '用户姓名',
  `type` int(11) DEFAULT NULL COMMENT '用户类型1-后台管理用户；2-前端用户',
  `email` varchar(32) DEFAULT NULL COMMENT '用户电子邮箱',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(256) DEFAULT NULL COMMENT '备注',
  `channel_id` varchar(50) DEFAULT NULL COMMENT '用户所属渠道ID',
  `points` int(11) DEFAULT '0' COMMENT '积分数',
  `address` varchar(100) DEFAULT NULL COMMENT '地区',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信号',
  `link_man` varchar(20) DEFAULT NULL COMMENT '联系人',
  `way` varchar(30) DEFAULT NULL COMMENT '计算方式',
  `price` varchar(30) DEFAULT NULL COMMENT '价格',
  `qr_code` varchar(200) DEFAULT NULL COMMENT '二维码',
  `channel_link` varchar(200) DEFAULT NULL COMMENT '渠道链接',
  `channel_per_id` varchar(50) DEFAULT NULL COMMENT '渠道专员Id',
  `status` int(1) DEFAULT '0' COMMENT '是否禁用0否1是',
  `last_login_ip` varchar(100) DEFAULT NULL COMMENT '上次登录IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(10) DEFAULT NULL COMMENT '登录次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_channelId` (`username`,`channel_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

-- ----------------------------
-- Table structure for t_user_feedback
-- ----------------------------
DROP TABLE IF EXISTS `t_user_feedback`;
CREATE TABLE `t_user_feedback` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `feed_back` varchar(300) DEFAULT NULL COMMENT '反馈内容',
  `feed_image_url` text COMMENT '反馈图片(多张,隔开)',
  `processing_state` varchar(2) DEFAULT '1' COMMENT '处理状态 1处理中2处理完成',
  `customer_id` varchar(50) DEFAULT NULL COMMENT '客服ID',
  `operator_id` varchar(50) DEFAULT NULL COMMENT '操作人ID',
  `operator_time` datetime DEFAULT NULL COMMENT '操作时间',
  `process_remark` varchar(200) DEFAULT NULL COMMENT '处理结果',
  `create_time` datetime DEFAULT NULL COMMENT '反馈时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户意见反馈表';

-- ----------------------------
-- Table structure for t_user_mail_list
-- ----------------------------
DROP TABLE IF EXISTS `t_user_mail_list`;
CREATE TABLE `t_user_mail_list` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `app_user_id` varchar(50) DEFAULT NULL COMMENT 'app用户ID',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(30) DEFAULT NULL COMMENT '电话',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app用户通讯录 ';

-- ----------------------------
-- Table structure for t_user_reward
-- ----------------------------
DROP TABLE IF EXISTS `t_user_reward`;
CREATE TABLE `t_user_reward` (
  `id` varchar(50) NOT NULL COMMENT '主键ID',
  `type` int(2) DEFAULT NULL COMMENT '奖励类型1邀请奖励',
  `money` decimal(10,2) DEFAULT NULL COMMENT '奖励金额',
  `operation_id` varchar(50) DEFAULT NULL COMMENT '操作人ID',
  `operation_time` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP用户奖励表';

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` varchar(50) NOT NULL COMMENT '主键id标识',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户id',
  `role_id` varchar(50) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色表';

-- ----------------------------
-- Table structure for user_apply_loan
-- ----------------------------
DROP TABLE IF EXISTS `user_apply_loan`;
CREATE TABLE `user_apply_loan` (
  `id` varchar(50) NOT NULL COMMENT '主键ID',
  `all_loan_id` varchar(50) DEFAULT NULL COMMENT '贷超ID',
  `app_user_id` varchar(50) DEFAULT NULL COMMENT '用户ID',
  `create_time` datetime DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户申请贷超表';

-- ----------------------------
-- Table structure for user_follow_record
-- ----------------------------
DROP TABLE IF EXISTS `user_follow_record`;
CREATE TABLE `user_follow_record` (
  `id` varchar(50) NOT NULL,
  `app_user_id` varchar(50) DEFAULT NULL,
  `follow_date` datetime DEFAULT NULL COMMENT '跟进时间',
  `follow_content` varchar(255) DEFAULT NULL COMMENT '跟进内容',
  `follow_user_id` varchar(50) DEFAULT NULL COMMENT '跟进人ID',
  `record_id` varchar(50) DEFAULT NULL COMMENT '关联用户申贷记录ID',
  `plat_id` varchar(50) DEFAULT NULL COMMENT '平台Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP用户跟进记录';

-- ----------------------------
-- Function structure for currval
-- ----------------------------
DROP FUNCTION IF EXISTS `currval`;
DELIMITER ;;
CREATE DEFINER=`loan_dev`@`localhost` FUNCTION `currval`(`v_seq_name` varchar(50)) RETURNS bigint(20)
BEGIN
 declare value bigint;  
 set value = 0;  
 select currentval into value  
 from sequence 
 where name = v_seq_name;  
 return value;	#Routine body goes here...

	RETURN 0;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for nextval
-- ----------------------------
DROP FUNCTION IF EXISTS `nextval`;
DELIMITER ;;
CREATE DEFINER=`loan_dev`@`localhost` FUNCTION `nextval`(v_seq_name VARCHAR(50)) RETURNS bigint(20)
BEGIN
	#Routine body goes here...
 UPDATE sequence  SET currentval = currentval+incrementval WHERE NAME = v_seq_name;  
 RETURN currval(v_seq_name);
END
;;
DELIMITER ;
