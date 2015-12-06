ALTER TABLE `md_qt_match_base`
	ADD COLUMN `halfEndTime` DATETIME NULL DEFAULT NULL COMMENT '半场结束时间' AFTER `jingcaiId`;
INSERT INTO `db_lottery`.`lt_return_status` (`system_key`, `status_code_for_client`, `desc_for_client`) VALUES ('query_immediate_index_details_succ', '10005', '查询即时指数详情成功');
INSERT INTO `db_lottery`.`lt_return_status` (`system_key`, `status_code_for_client`, `desc_for_client`) VALUES ('query_immediate_index_details_fail', '10006', '查询即时指数详情失败');
INSERT INTO `db_lottery`.`lt_return_status` (`system_key`, `status_code_for_client`, `desc_for_client`) VALUES ('query_immediate_index_details_succ', '10003', '查询即时指数成功');
INSERT INTO `db_lottery`.`lt_return_status` (`system_key`, `status_code_for_client`, `desc_for_client`) VALUES ('query_immediate_index_details_fail', '10004', '查询即时指数失败');

ALTER TABLE `md_qt_match_base`
	CHANGE COLUMN `halfEndTime` `halfEndTime` VARCHAR(50) NULL DEFAULT NULL COMMENT '上半场结束时间' AFTER `jingcaiId`,
	ADD COLUMN `isNow` VARCHAR(2) NULL DEFAULT NULL COMMENT '是否当前比赛0或null否，1是' AFTER `halfEndTime`;
ALTER TABLE `md_bb_match_base`
	ADD COLUMN `isNow` VARCHAR(2) NULL DEFAULT NULL COMMENT '是否当前0或null否，1是' AFTER `updateTime`;
ALTER TABLE `md_qt_match_base`
	CHANGE COLUMN `halfEndTime` `halfEndTime` DATETIME NULL DEFAULT NULL COMMENT '上半场结束时间' AFTER `jingcaiId`;
ALTER TABLE `md_fb_league_base`
	ADD UNIQUE INDEX `unique_name` (`chineseName`);
ALTER TABLE `md_qt_match_base`
	ADD COLUMN `liveContent` VARCHAR(50) NULL DEFAULT NULL COMMENT '直播内容' AFTER `isNow`;
