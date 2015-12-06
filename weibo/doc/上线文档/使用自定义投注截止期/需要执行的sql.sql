ALTER TABLE `lt_lottery_open_sale`
	DROP COLUMN `lotteryId`,
	DROP COLUMN `dayOfWeek`;
	
ALTER TABLE `lt_lottery_open_sale`
	ADD COLUMN `dayOfWeek` INT NOT NULL COMMENT '1至7,1代表周日，2代表周一，7代表周六' AFTER `endTime`;
	

ALTER TABLE `lt_lottery_open_sale`
	ADD COLUMN `isEndTimeCrossDay` INT NOT NULL DEFAULT '0' COMMENT '截止时间是否跨天，0是不跨天，1是跨天，默认为不跨天' AFTER `dayOfWeek`;
	
delete from lt_lottery_open_sale;
	
INSERT INTO `lt_lottery_open_sale` VALUES (1, '10:00:00', '23:59:59', 1, 0);
INSERT INTO `lt_lottery_open_sale` VALUES (15, '10:00:00', '23:00:00', 2, 0);
INSERT INTO `lt_lottery_open_sale` VALUES (16, '10:00:00', '23:00:00', 3, 0);
INSERT INTO `lt_lottery_open_sale` VALUES (17, '10:00:00', '23:00:00', 4, 0);
INSERT INTO `lt_lottery_open_sale` VALUES (18, '10:00:00', '23:00:00', 5, 0);
INSERT INTO `lt_lottery_open_sale` VALUES (19, '10:00:00', '23:00:00', 6, 0);
INSERT INTO `lt_lottery_open_sale` VALUES (20, '10:00:00', '23:59:59', 7, 0);


