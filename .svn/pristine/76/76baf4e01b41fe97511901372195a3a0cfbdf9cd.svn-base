ALTER TABLE `lt_bet_match_rec`
	ADD INDEX `match_id` (`match_id`);
ALTER TABLE `lt_bet_match_rec`
	ADD INDEX `play_id` (`play_id`);
	
ALTER TABLE `lt_bet_match_rec`
	CHANGE COLUMN `play_id` `play_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '玩法ID（主要是给“混合过关”玩法用）' AFTER `seed`;
	
ALTER TABLE `md_bb_match`
	CHANGE COLUMN `name` `name` VARCHAR(50) NOT NULL COMMENT '联赛名，简体，例如 NBA，WNBA' AFTER `id`;

ALTER TABLE `lt_lottery_home_recommend`
	CHANGE COLUMN `status` `status` INT(5) NOT NULL COMMENT '状态（0：上线，-1：下线）' AFTER `weibo_id`;

	
CREATE TABLE `top5_recommend` (
	`id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`userId` BIGINT(20) NOT NULL COMMENT '用户id',
	`countOfRecommend` INT(11) NOT NULL COMMENT '推荐数',
	`countOfHit` INT(11) NOT NULL COMMENT '命中数',
	`ratio` FLOAT NOT NULL COMMENT '胜率或盈利率，如0.3',
	`topType` VARCHAR(50) NOT NULL COMMENT '排行榜类型（包括shenglv和yinglilv）',
	`dimension` VARCHAR(50) NOT NULL COMMENT '维度（包括7day和50）',
	`sequenceNumber` INT(11) NOT NULL COMMENT '序号，从小到大，越小越靠前',
	PRIMARY KEY (`id`)
)
COMMENT='推荐相关的top5排行榜'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

CREATE TABLE `top5_recommend_middle` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`userId` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户id',
	`lotteryId` VARCHAR(16) NOT NULL COMMENT '彩种id',
	`playId` VARCHAR(16) NOT NULL COMMENT '玩法id',
	`matchId` BIGINT(20) UNSIGNED NOT NULL COMMENT '比赛id',
	`code` VARCHAR(50) NOT NULL COMMENT '投注内容',
	`winOption` VARCHAR(50) NOT NULL COMMENT '赛果',
	`isWin` TINYINT(1) NOT NULL COMMENT '是否赢，0为输，1为赢',
	`revenueRatio` FLOAT NOT NULL COMMENT '盈利率',
	`recommendCreateTime` DATETIME NOT NULL COMMENT '推荐创建时间',
	`odds` VARCHAR(255) NOT NULL COMMENT '赔率',
	`options` VARCHAR(255) NOT NULL COMMENT '可选的投注内容',
	PRIMARY KEY (`id`)
)
COMMENT='为统计推荐排行榜用到的中间表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB;


ALTER TABLE `lt_bet_match`
	CHANGE COLUMN `play_id` `play_id` VARCHAR(50) NULL DEFAULT NULL AFTER `seed`;
	
ALTER TABLE `lt_lottery_home_recommend`
	CHANGE COLUMN `status` `status` INT(5) NOT NULL COMMENT '状态（0：上线，-1：下线）' AFTER `weibo_id`;



CREATE TABLE `advertisement` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`pic_path` VARCHAR(100) NOT NULL,
	`href_link` VARCHAR(100) NOT NULL,
	`created_time` DATETIME NULL DEFAULT NULL,
	`update_time` DATETIME NULL DEFAULT NULL,
	`status` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=8;

/**期信息表增加记录创建和更新字段*/
ALTER TABLE `lt_issueinfo`  ADD COLUMN `created_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间' AFTER `bonus_info`,  ADD COLUMN `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间' AFTER `created_time`;
