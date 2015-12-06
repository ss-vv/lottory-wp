CREATE TABLE `top5_recommend_middle_order_50` (
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
COMMENT='为统计推荐排行榜的50场用到的经过排序的中间表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=6403
;
ALTER TABLE `top5_recommend`
	CHANGE COLUMN `countOfRecommend` `countOfRecommend` INT(11) NOT NULL COMMENT '总数' AFTER `userId`;
ALTER TABLE `top5_recommend`
ADD COLUMN `bonus` DOUBLE NULL COMMENT '奖金数' AFTER `countOfHit`;