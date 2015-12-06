ALTER TABLE `lt_bet_scheme`
	ADD COLUMN `machine_offtime` DATETIME NOT NULL DEFAULT '1111-11-11 00:00:00' AFTER `offtime`;
ALTER TABLE `lt_lottery_open_sale`
	ADD COLUMN `machineOfftime` TIME NOT NULL DEFAULT '00:00:00' AFTER `endTime`;
ALTER TABLE `lt_lottery_open_sale`
	ADD COLUMN `isMachineOfftimeCrossDay` INT NOT NULL DEFAULT 1 AFTER `isEndTimeCrossDay`;

/*
 * 上线后，把lt_bet_scheme的offtime字段的数据update到machineOfftime
 */
	