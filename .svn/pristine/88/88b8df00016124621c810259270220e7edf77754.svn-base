ALTER TABLE `lt_client_activity`
	ADD COLUMN `title` VARCHAR(50) NULL DEFAULT NULL COMMENT '标题' AFTER `clientVersion`,
	ADD COLUMN `status` INT(2) NULL DEFAULT '0' COMMENT '活动是否有效 1--无效  0--有效' AFTER `title`,
	ADD COLUMN `lotteryType` VARCHAR(50) NULL DEFAULT NULL COMMENT '0--竞足 1--竞篮 2--重庆时时彩 3--江西11选5 4--14场胜负彩 5--任选9场 6--六场半全场 7--四场进球' AFTER `status`;
