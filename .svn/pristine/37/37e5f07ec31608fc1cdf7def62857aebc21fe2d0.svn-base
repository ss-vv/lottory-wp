CREATE TABLE `lt_partner_withdraw` (
	`uuid` CHAR(50) NOT NULL COMMENT '请求的UUID',
	`transaction_id` CHAR(50) NULL DEFAULT NULL COMMENT '提现交易id；相同的提现交易id会在多个环节使用，比如提现请求接口和提现结果通知接口。',
	`partner_user_id` CHAR(50) NULL DEFAULT NULL COMMENT '商户系统中的用户ID',
	`user_id` BIGINT(20) NULL DEFAULT NULL COMMENT '对应大V彩用户ID',
	`withdraw_id` BIGINT(20) NULL DEFAULT NULL COMMENT '对应大V彩提现ID',
	`money` DECIMAL(10,0) NULL DEFAULT NULL COMMENT '提现金额',
	`bank_info` VARCHAR(50) NULL DEFAULT NULL COMMENT '开户银行信息',
	`card_num` CHAR(30) NULL DEFAULT NULL COMMENT '银行卡号',
	`mobile` CHAR(20) NULL DEFAULT NULL COMMENT '手机号码',
	`id_card` CHAR(20) NULL DEFAULT NULL COMMENT '身份证号码',
	`realname` CHAR(20) NULL DEFAULT NULL COMMENT '真实姓名',
	`status` INT(11) NULL DEFAULT NULL COMMENT '0 提现结果待通知；1 提现结果通知成功',
	`withdraw_notice_time` DATETIME NULL DEFAULT NULL COMMENT '提现通知时间',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '记录创建时间',
	PRIMARY KEY (`uuid`),
	UNIQUE INDEX `transaction_id` (`transaction_id`)
)
COMMENT='商户提现表'
COLLATE='utf8_general_ci'
ENGINE=MyISAM;