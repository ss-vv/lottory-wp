ALTER TABLE `fb_match`
	ADD COLUMN `long_league_name` VARCHAR(64)  NULL AFTER `check_status`;
	
ALTER TABLE `bb_match`
	ADD COLUMN `long_league_name` VARCHAR(64)  NULL AFTER `check_status`;
	
update bb_match set long_league_name = league_name;
update fb_match set long_league_name = league_name;