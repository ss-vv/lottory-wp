ALTER TABLE `md_bb_match_base`
	CHANGE COLUMN `explainContent` `explainContent` MEDIUMBLOB NULL COMMENT '备注，直播内容' AFTER `tv`;