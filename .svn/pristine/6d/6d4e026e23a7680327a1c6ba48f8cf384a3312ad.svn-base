/**首页彩种赛事推荐结果表*/
CREATE TABLE `lt_lottery_home_recommend` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
	`lottery_id` VARCHAR(50) NOT NULL COMMENT '彩种ID',
	`bet_match_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '投注的赛事记录ID',
	`weibo_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '微博ID',
	`status` INT(5) NOT NULL COMMENT '状态（0：上线，-1：下线）',
	`deadline_time` DATETIME NOT NULL COMMENT '推荐截止时间',
	`created_time` DATETIME NOT NULL COMMENT '创建时间',
	`update_time` DATETIME NOT NULL COMMENT '最后更新时间',
	INDEX `id` (`id`)
)
COMMENT='首页彩种赛事推荐结果表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT











