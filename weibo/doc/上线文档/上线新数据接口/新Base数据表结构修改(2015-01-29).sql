ALTER TABLE `md_qt_match_base`
	ADD INDEX `index_homeTeamId` (`homeTeamId`),
	ADD INDEX `index_guestTeamId` (`guestTeamId`);
ALTER TABLE `md_fb_league_score_base`
	ADD INDEX `leagueId` (`leagueId`);
ALTER TABLE `md_fb_rule_detail_base`
	ADD INDEX `leagueId` (`leagueId`);
ALTER TABLE `md_fb_rule_detail_base`
	ADD INDEX `season` (`seasonName`);
	
	