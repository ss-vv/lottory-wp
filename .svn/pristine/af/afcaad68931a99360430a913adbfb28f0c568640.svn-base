ALTER TABLE `md_bb_match_base`
	CHANGE COLUMN `homeAddTime1` `homeAddTime1` INT(11) NULL DEFAULT '0' COMMENT '主队1\'ot得分' AFTER `addTime`,
	CHANGE COLUMN `guestAddTime1` `guestAddTime1` INT(11) NULL DEFAULT '0' COMMENT '客队1\'ot得分' AFTER `homeAddTime1`,
	CHANGE COLUMN `homeAddTime2` `homeAddTime2` INT(11) NULL DEFAULT '0' COMMENT '主队2\'ot得分' AFTER `guestAddTime1`,
	CHANGE COLUMN `guestAddTime2` `guestAddTime2` INT(11) NULL DEFAULT '0' COMMENT '客队2\'ot得分' AFTER `homeAddTime2`,
	CHANGE COLUMN `homeAddTime3` `homeAddTime3` INT(11) NULL DEFAULT '0' COMMENT '主队3\'ot得分' AFTER `guestAddTime2`,
	CHANGE COLUMN `guestAddTime3` `guestAddTime3` INT(11) NULL DEFAULT '0' COMMENT '客队3\'ot得分' AFTER `homeAddTime3`,
	CHANGE COLUMN `addTechnic` `addTechnic` INT(11) NULL DEFAULT '0' COMMENT '是否有技术统计 （1有 0无）' AFTER `guestAddTime3`;

ALTER TABLE `md_bb_match_base`
	CHANGE COLUMN `sclassType` `sclassType` INT(11) NULL DEFAULT '4' COMMENT '分几节进行\\r\\n            2:上下半场，4：分4小节' AFTER `nameF`,
	CHANGE COLUMN `cupLeague` `cupLeague` VARCHAR(10) NULL DEFAULT '1' COMMENT '1联赛，2杯赛' AFTER `explain2`;

update md_bb_match_base set homeAddTime1 = 0 where homeAddTime1 is null;
update md_bb_match_base set guestAddTime1 = 0 where guestAddTime1 is null;
update md_bb_match_base set homeAddTime2 = 0 where guestAddTime2 is null;
update md_bb_match_base set guestAddTime2 = 0 where guestAddTime2 is null;
update md_bb_match_base set homeAddTime3 = 0 where homeAddTime3 is null;
update md_bb_match_base set guestAddTime3 = 0 where guestAddTime3 is null;
update md_bb_match_base set processStatus = 0 where processStatus is null;
update md_bb_match_base set sclassType = 4 where sclassType is null;
update md_bb_match_base set cupLeague = 0 where cupLeague is null;

