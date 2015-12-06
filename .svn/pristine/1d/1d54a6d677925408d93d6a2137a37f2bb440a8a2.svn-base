CREATE TABLE `lt_partner_bet` (
	`uuid` CHAR(50) NOT NULL COMMENT '请求的UUID',
	`partner_user_id` CHAR(50) NULL DEFAULT NULL COMMENT '商户系统中的用户ID',
	`user_id` BIGINT(20) NULL DEFAULT NULL COMMENT '商户ID；对应大V彩用户ID',
	`scheme_id` BIGINT(20) NULL DEFAULT NULL COMMENT '方案ID；对应大V彩投注方案ID',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '记录创建时间',
	PRIMARY KEY (`uuid`),
	UNIQUE INDEX `ticket_id` (`scheme_id`)
)
COMMENT='商户投注表'
COLLATE='utf8_general_ci'
ENGINE=MyISAM;
