CREATE TABLE `bjdc_match` (
	`id` BIGINT(20) UNSIGNED NOT NULL COMMENT '北京单场赛事ID（期号+赛事场次号）',
	`cn_code` VARCHAR(16) NOT NULL COMMENT '编号（加上周前缀）',
	`code` INT(20) NOT NULL COMMENT '赛事场次号',
	`issue_number` VARCHAR(16) NOT NULL COMMENT '期号',
	`name` VARCHAR(100) NOT NULL COMMENT '对阵双方',
	`league_id` INT(11) UNSIGNED NULL DEFAULT '0' COMMENT '联赛ID',
	`league_name` VARCHAR(64) NOT NULL COMMENT '联赛短名称',
	`long_league_name` VARCHAR(64) NULL DEFAULT NULL COMMENT '联赛长名称',
	`playing_time` DATETIME NOT NULL COMMENT '比赛时间',
	`offtime` DATETIME NOT NULL COMMENT '停售时间',
	`entrust_deadline` DATETIME NOT NULL COMMENT '委托截止时间',
	`home_team_id` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '主队ID',
	`home_team_name` VARCHAR(64) NOT NULL COMMENT '主队名称',
	`guest_team_id` INT(11) UNSIGNED NOT NULL DEFAULT '0' COMMENT '客队ID',
	`guest_team_name` VARCHAR(64) NOT NULL COMMENT '客队名称',
	`status` SMALLINT(5) NOT NULL DEFAULT '0' COMMENT '比赛状态（0:停售,1:在售,2:待售,3:进行中,4:取消,5:已完成）',
	`half_score` VARCHAR(16) NULL DEFAULT NULL COMMENT '半场比分',
	`score` VARCHAR(16) NULL DEFAULT NULL COMMENT '全场比分',
	`note` VARCHAR(255) NULL DEFAULT NULL COMMENT '特别提示',
	`created_time` DATETIME NOT NULL COMMENT '创建时间',
	`update_time` DATETIME NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`)
)
COMMENT='北京单场赛程表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT