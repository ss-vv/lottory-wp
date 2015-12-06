CREATE TABLE `bjdc_match_play` (
	`id` VARCHAR(32) NOT NULL COMMENT '主键',
	`issue_number` VARCHAR(16) NOT NULL COMMENT '期号',
	`match_id` BIGINT(20) NOT NULL COMMENT '赛事ID',
	`play_id` VARCHAR(16) NOT NULL COMMENT '玩法ID',
	`options` VARCHAR(128) NOT NULL COMMENT '包含的选项',
	`odds` VARCHAR(255) NOT NULL COMMENT '选项对应赔率',
	`final_odds` VARCHAR(255) NULL DEFAULT NULL COMMENT '玩法对应最终赔率',
	`concede_points` DECIMAL(5,2) NULL DEFAULT NULL COMMENT '主队让球数',
	`win_option` VARCHAR(16) NULL DEFAULT NULL COMMENT '中奖选项',
	`created_time` DATETIME NOT NULL COMMENT '创建时间',
	`update_time` DATETIME NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `uniq_match_play` (`match_id`, `play_id`)
)
COMMENT='北京单场赛事玩法表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT