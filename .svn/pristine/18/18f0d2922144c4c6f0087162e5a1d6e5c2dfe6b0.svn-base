CREATE TABLE `registration_code` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(10) NOT NULL,
	`status` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '是否有效 0 无效 1有效 2过期 3已使用 默认是1',
	`user_id` BIGINT(20) NOT NULL DEFAULT '-1' COMMENT '标识是哪个用户使用的',
	`vid` BIGINT(20) NULL DEFAULT '-1' COMMENT '大Vid',
	`create_time` DATETIME NOT NULL COMMENT '邀请码生成时间',
	`end_time` DATETIME NOT NULL COMMENT '注册码有效期结束时间',
	`used_time` DATETIME NULL DEFAULT NULL COMMENT '邀请码被使用时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `code` (`code`)
)
COMMENT='邀请码表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=40;
