ALTER TABLE `md_bb_match`
	ADD COLUMN `homeTeamRank` VARCHAR(100) NULL COMMENT '主队排名' AFTER `updateTime`,
	ADD COLUMN `guestTeamRank` VARCHAR(100) NULL COMMENT '客队排名' AFTER `homeTeamRank`;
	
ALTER TABLE `md_fb_team`
	ADD COLUMN `foundDate` VARCHAR(20) NULL COMMENT '球队成立日期' AFTER `updateTime`,
	ADD COLUMN `area` VARCHAR(250) NULL COMMENT '球队所在地' AFTER `foundDate`,
	ADD COLUMN `gym` VARCHAR(250) NULL COMMENT '球队所在场地' AFTER `area`,
	ADD COLUMN `capacity` VARCHAR(9) NULL COMMENT '球场容量' AFTER `gym`,
	ADD COLUMN `flag` VARCHAR(120) NULL COMMENT '队标' AFTER `capacity`,
	ADD COLUMN `url` VARCHAR(250) NULL COMMENT '球队网站' AFTER `flag`,
	ADD COLUMN `master` VARCHAR(100) NULL COMMENT '球队主教练' AFTER `url`;
	
ALTER TABLE `md_fb_team`
	ADD COLUMN `address` VARCHAR(1000) NULL DEFAULT NULL COMMENT '球队地址' AFTER `area`;
ALTER TABLE `md_qt_match`
	CHANGE COLUMN `fbLotteryScoreType` `fbLotteryScoreType` TINYINT(4) NULL DEFAULT NULL COMMENT '是否是足彩比分 0：非足彩，1:胜负彩，2：北京单场，3：胜负彩+北京单场,-1:未知' AFTER `importance`;
ALTER TABLE `md_bb_match`
	CHANGE COLUMN `explain2` `explain2` TEXT NULL COMMENT '赔率信息,可不理' AFTER `explainContent`;
ALTER TABLE `md_bb_match`
	ALTER `guestTeamE` DROP DEFAULT;
ALTER TABLE `md_bb_match`
	CHANGE COLUMN `guestTeamE` `guestTeamE` VARCHAR(255) NULL COMMENT '客队名，英文' AFTER `guestTeamF`;
ALTER TABLE `md_bb_match`
	ALTER `homeTeamE` DROP DEFAULT;
ALTER TABLE `md_bb_match`
	CHANGE COLUMN `homeTeamE` `homeTeamE` VARCHAR(255) NULL COMMENT '主队名，英文' AFTER `homeTeamF`;

ALTER TABLE `md_bb_match`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '记录更新时间' AFTER `createTime`;
ALTER TABLE `md_bb_lottery_corp`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '记录更新时间' AFTER `createTime`;
ALTER TABLE `md_bb_odds_big`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '记录更新时间' AFTER `createTime`;
ALTER TABLE `md_bb_odds_concede`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '记录更新时间' AFTER `createTime`;
ALTER TABLE `md_bb_odds_euro`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '记录更新时间' AFTER `createTime`;
ALTER TABLE `md_bb_team`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL DEFAULT NULL COMMENT '记录更新时间' AFTER `createTime`;
ALTER TABLE `md_fb_cup_score`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '记录更新时间' AFTER `createTime`;
ALTER TABLE `md_fb_league`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '更新时间' AFTER `createTime`;
ALTER TABLE `md_fb_league_score`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '更新时间' AFTER `createTime`;
ALTER TABLE `md_fb_odds_asia`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '更新时间' AFTER `createTime`;

ALTER TABLE `md_fb_odds_euro`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '更新时间' AFTER `createTime`;
ALTER TABLE `md_fb_sub_cup`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '更新时间' AFTER `createTime`;
ALTER TABLE `md_fb_sub_league`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '记录更新时间' AFTER `createTime`;
ALTER TABLE `md_fb_team`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '记录更新时间' AFTER `createTime`;
ALTER TABLE `md_lottery_corp`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '更新时间' AFTER `createTime`;
ALTER TABLE `md_qt_match`
	CHANGE COLUMN `updateTime` `updateTime` DATETIME NULL  COMMENT '更新时间' AFTER `createTime`;

