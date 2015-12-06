CREATE TABLE `lt_dav_group` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`groupid` VARCHAR(50) NOT NULL COMMENT '大v群号',
	`clientVersion` VARCHAR(50) NOT NULL COMMENT '客户端版本',
	`vid` VARCHAR(50) NOT NULL COMMENT '每一个大v的id',
	`imageUrl` VARCHAR(50) NULL DEFAULT NULL COMMENT '大v群的头像',
	`desc` VARCHAR(50) NULL DEFAULT NULL COMMENT '是哪个大v的群',
	PRIMARY KEY (`id`),
	INDEX `groupid` (`groupid`)
)
COMMENT='大V群'
COLLATE='utf8_general_ci'
ENGINE=InnoDB 
AUTO_INCREMENT=2;

INSERT INTO `lt_dav_group` (`id`, `groupid`, `clientVersion`, `vid`, `imageUrl`, `desc`) VALUES (1, '1423801386841816', 'android-dav-1.0.0', '14060', '', 'ribokate的大v群');
INSERT INTO `lt_dav_group` (`id`, `groupid`, `clientVersion`, `vid`, `imageUrl`, `desc`) VALUES (2, '1423801386841816', 'ios-dav-1.0.0', '14060', NULL, 'ribokate的大v群');


CREATE TABLE `lt_user_hx` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`nickname` VARCHAR(50) NOT NULL COMMENT '大v彩用户的昵称',
	`hx_username` VARCHAR(50) NOT NULL COMMENT '环信的账户名称，是昵称的MD5',
	`hx_password` VARCHAR(50) NOT NULL COMMENT '环信的账户密码，是昵称+定长随机字符串的MD5',
	`lt_user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '大v彩的用户id',
	`imageUrl` VARCHAR(50) NULL DEFAULT NULL COMMENT '大v彩用户头像',
	PRIMARY KEY (`id`),
	INDEX `FK_lt_user_hx_lt_user` (`lt_user_id`),
	INDEX `hx_username` (`hx_username`),
	CONSTRAINT `FK_lt_user_hx_lt_user` FOREIGN KEY (`lt_user_id`) REFERENCES `lt_user` (`id`)
)
COMMENT='环信的账户信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=58;

----发方案的记录表
CREATE TABLE `lt_bet_client_publish` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`lt_bet_id` BIGINT(20) NOT NULL COMMENT '发的方案id',
	`lt_user_id` BIGINT(20) NOT NULL COMMENT '用户id',
	`groupid` VARCHAR(50) NOT NULL COMMENT '群号',
	`lt_match_id` BIGINT(20) NOT NULL COMMENT '比赛id',
	`nickName` VARCHAR(50) NULL DEFAULT NULL,
	`imageUrl` VARCHAR(50) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	INDEX `lt_bet_id` (`lt_bet_id`),
	INDEX `lt_user_id` (`lt_user_id`)
)
COMMENT='客户端的发方案记录表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=182;


ALTER TABLE `lt_bet_scheme`
	CHANGE COLUMN `partner` `partner` VARCHAR(50) NOT NULL DEFAULT 'lai310www' COMMENT '合作伙伴' AFTER `channel`;
	
	
CREATE TABLE `lt_client_activity` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`imageURL` VARCHAR(250) NOT NULL,
	`type` INT(2) NOT NULL COMMENT '0(活动)|1(晒单)|2(合买)|3(中奖的方案)',
	`activityURL` VARCHAR(250) NULL DEFAULT NULL COMMENT '活动地址',
	`schemeId` VARCHAR(50) NULL DEFAULT NULL COMMENT '方案id',
	`clientVersion` VARCHAR(50) NULL DEFAULT NULL COMMENT '客户端版本',
	PRIMARY KEY (`id`)
)
COMMENT='客户端轮播的内容'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4;

ALTER TABLE `lt_user`
	ADD COLUMN `headImageURL` VARCHAR(255) NULL DEFAULT NULL AFTER `pid`,
	ADD COLUMN `nickName` VARCHAR(50) NULL AFTER `headImageURL`,
	ADD COLUMN `sinaWeiboUid` VARCHAR(80) NULL DEFAULT NULL AFTER `nickName`,
	ADD COLUMN `sinaWeiboToken` VARCHAR(80) NULL DEFAULT NULL AFTER `sinaWeiboUid`,
	ADD COLUMN `qqConnectUid` VARCHAR(80) NULL DEFAULT NULL AFTER `sinaWeiboToken`,
	ADD COLUMN `qqConnectToken` VARCHAR(80) NULL DEFAULT NULL AFTER `qqConnectUid`,
	ADD COLUMN `weixinUid` VARCHAR(80) NULL DEFAULT NULL AFTER `qqConnectToken`,
	ADD COLUMN `weixinToken` VARCHAR(180) NULL DEFAULT NULL AFTER `weixinUid`,
	ADD COLUMN `alipayUid` VARCHAR(80) NULL DEFAULT NULL AFTER `weixinToken`,
	ADD COLUMN `alipayToken` VARCHAR(80) NULL DEFAULT NULL AFTER `alipayUid`;
	
INSERT INTO `lt_return_status` (`id`, `system_key`, `status_code_for_client`, `desc_for_client`) VALUES (108, 'request_stauts_succ', '10000', '客户端的响应状态成功');
INSERT INTO `lt_return_status` (`id`, `system_key`, `status_code_for_client`, `desc_for_client`) VALUES (109, 'request_stauts_fail', '10001', '客户端的响应状态失败');
INSERT INTO `lt_return_status` (`id`, `system_key`, `status_code_for_client`, `desc_for_client`) VALUES (110, 'nickName_Fail', '20021', '昵称被占用');
INSERT INTO `lt_return_status` (`id`, `system_key`, `status_code_for_client`, `desc_for_client`) VALUES (111, 'nickName_illegal', '20022', '昵称不合法');
INSERT INTO `lt_return_status` (`id`, `system_key`, `status_code_for_client`, `desc_for_client`) VALUES (112, 'should_registe', '20011', '用户不存在，需要注册');
INSERT INTO `lt_return_status` (`id`, `system_key`, `status_code_for_client`, `desc_for_client`) VALUES (113, 'security_code_fail', '20023', '注册时验证码错误');
INSERT INTO `lt_return_status` (`id`, `system_key`, `status_code_for_client`, `desc_for_client`) VALUES (114, 'registered', '20024', '该用户已经注册');
INSERT INTO `lt_return_status` (`id`, `system_key`, `status_code_for_client`, `desc_for_client`) VALUES (115, 'data_fail', '10002', '数据异常');





