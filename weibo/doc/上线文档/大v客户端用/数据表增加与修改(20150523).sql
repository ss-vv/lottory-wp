CREATE TABLE `lt_red_envalope_grab_record` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`envalopeId` INT(11) NULL DEFAULT NULL COMMENT '红包id',
	`ltUserId` INT(11) NULL DEFAULT NULL COMMENT '用户id',
	`envalopeAmount` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '红包金额',
	`createTime` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `user_envalope_index` (`envalopeId`, `ltUserId`),
	INDEX `envalopeId_index` (`envalopeId`)
)
COMMENT='红包抢夺记录'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `lt_redenvalope` (
	`envalopeId` INT(11) NOT NULL AUTO_INCREMENT COMMENT '红包id',
	`redEnvalopeAmount` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '红包金额(以分为单位）',
	`ltUserId` VARCHAR(20) NOT NULL COMMENT '大v彩用户id',
	`createTime` DATETIME NOT NULL COMMENT '创建时间',
	`envalopeNum` INT(11) NOT NULL COMMENT '红包个数',
	`smallEnvalope` VARCHAR(300) NULL DEFAULT NULL COMMENT '小红包记录序列（ext：\'1,3,43,23...\')',
	`status` VARCHAR(2) NOT NULL COMMENT '红包状态0:有效，1:失效',
	`grabedEnvalopeAmount` INT(11) NULL DEFAULT '0' COMMENT '已被抢金额（以分为单位）',
	`amountReturnUserAccount` INT(11) NULL DEFAULT '0' COMMENT '返还用户账户的金额（以分为单位）',
	`secondOfGrabed` INT(11) NULL DEFAULT '0' COMMENT '红包被抢完的分钟数',
	PRIMARY KEY (`envalopeId`),
	INDEX `ltuserId` (`ltUserId`)
)
COMMENT='红包记录表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=252;
INSERT INTO `lt_return_status` (`system_key`, `status_code_for_client`, `desc_for_client`) VALUES ('grabed_red_envalope', '1', '已抢过该红包');
INSERT INTO `lt_return_status` (`system_key`, `status_code_for_client`, `desc_for_client`) VALUES ('red_envalope_invalid', '3', '红包已失效');
INSERT INTO `lt_return_status` (`system_key`, `status_code_for_client`, `desc_for_client`) VALUES ('red_envalope_grab_success', '0', '红包抢夺成功');
INSERT INTO `lt_return_status` (`system_key`, `status_code_for_client`, `desc_for_client`) VALUES ('red_envalope_not_exist', '2', '红包已抢光');