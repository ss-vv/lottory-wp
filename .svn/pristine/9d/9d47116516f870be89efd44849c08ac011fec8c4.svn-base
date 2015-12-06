ALTER TABLE `lt_ticket`
	ADD COLUMN `ticket_succ_time` DATETIME NULL DEFAULT NULL COMMENT '出票成功时间' AFTER `final_state_time`;

ALTER TABLE `lt_ticket`
	CHANGE COLUMN `final_state_time` `final_state_time` DATETIME NULL DEFAULT NULL COMMENT '票到达最终状态的时间';

	

ALTER TABLE `fb_match`
	ADD INDEX `idx_cn_code` (`cn_code`),
	ADD INDEX `idx_playing_time` (`playing_time`);

ALTER TABLE `bb_match`
	ADD INDEX `idx_playing_time` (`playing_time`),
	ADD INDEX `idx_cn_code` (`cn_code`);

INSERT INTO `db_lottery`.`lt_system_conf` (`conf_key`, `conf_value`, `desc`) 
VALUES ('interval_for_qt_match_join', '10', '使用尊奥开赛时间去匹配球探数据时，球探赛程开赛时间前后需要增长的分钟数，最大值不超过60');



