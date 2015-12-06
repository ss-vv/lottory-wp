#猜冠军信息表
CREATE TABLE `cgj_teams` (
	`id` INT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`team_id` INT(20) UNSIGNED NOT NULL COMMENT '队伍场次',
	`match_name` VARCHAR(20) NOT NULL COMMENT '比赛名称',
	`team_name` VARCHAR(80) NOT NULL COMMENT '队伍名称',
	`team_logo` VARCHAR(500) NOT NULL COMMENT '队伍logo',
	`group_name` VARCHAR(20) NOT NULL COMMENT '分组名称',
	`odds` DECIMAL(10,2) NOT NULL COMMENT '赔率',
	`rank` INT(10) UNSIGNED NOT NULL COMMENT 'fifa排名',
	`season` VARCHAR(20) NOT NULL COMMENT '杯赛的年份',
	`current_season` INT(10) UNSIGNED NOT NULL COMMENT '是否为对应玩法的当前赛季（1表示是，0否）',
	`play_type` VARCHAR(20) NOT NULL COMMENT '竞猜类型：比如‘竞猜冠军’，‘竞猜世界杯冠军’，‘竞猜冠亚军’等',
	`sale_status` SMALLINT(5) UNSIGNED NOT NULL COMMENT '销售状态',
	`playing_time` DATETIME NOT NULL COMMENT '开赛时间',
	`offtime` DATETIME NOT NULL COMMENT '截止时间',
	`update_time` DATETIME NOT NULL COMMENT '更新时间',
	`created_time` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `uniq_world_cup` (`team_name`, `group_name`, `season`, `play_type`)
)
COMMENT='猜冠军信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
AUTO_INCREMENT=1

#导入2014世界杯数据
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (1, 1, '世界杯冠军', '巴西', 'http://images.davcai.com/team/baxi.png', 'A组', 2.60, 4, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:04');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (25, 2, '世界杯冠军', '德国', 'http://images.davcai.com/team/deguo.png', 'G组', 4.10, 2, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:07');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (21, 3, '世界杯冠军', '阿根廷', 'http://images.davcai.com/team/agenting.png', 'F组', 4.40, 7, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:06');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (5, 4, '世界杯冠军', '西班牙', 'http://images.davcai.com/team/xibanya.png', 'B组', 15.00, 1, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:04');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (29, 5, '世界杯冠军', '比利时', 'http://images.davcai.com/team/bilishi.png', 'H组', 25.00, 12, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:07');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (6, 6, '世界杯冠军', '荷兰', 'http://images.davcai.com/team/helan.png', 'B组', 7.20, 15, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:05');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (13, 7, '世界杯冠军', '意大利', 'http://images.davcai.com/team/yidali.png', 'D组', 13.00, 9, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:05');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (17, 8, '世界杯冠军', '法国', 'http://images.davcai.com/team/faguo.png', 'E组', 14.00, 16, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:06');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (26, 9, '世界杯冠军', '葡萄牙', 'http://images.davcai.com/team/putouya.png', 'G组', 18.00, 3, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:07');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (9, 10, '世界杯冠军', '哥伦比亚', 'http://images.davcai.com/team/eguaduoer.png', 'C组', 29.00, 5, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:05');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (15, 11, '世界杯冠军', '乌拉圭', 'http://images.davcai.com/team/wulagui.png', 'D组', 75.00, 6, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:06');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (14, 12, '世界杯冠军', '英格兰', 'http://images.davcai.com/team/yingelan.png', 'D组', 24.00, 11, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:06');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (7, 13, '世界杯冠军', '智利', 'http://images.davcai.com/team/zhili.png', 'B组', 45.00, 13, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:05');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (30, 14, '世界杯冠军', '俄罗斯', 'http://images.davcai.com/team/eluosi.png', 'H组', 110.00, 18, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:07');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (10, 15, '世界杯冠军', '科特迪瓦', 'http://images.davcai.com/team/ketediwa.png', 'C组', 75.00, 21, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:05');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (18, 16, '世界杯冠军', '瑞士', 'http://images.davcai.com/team/ruishi.png', 'E组', 70.00, 8, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:06');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (11, 17, '世界杯冠军', '日本', 'http://images.davcai.com/team/riben.png', 'C组', 300.00, 47, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:05');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (22, 18, '世界杯冠军', '波黑', 'http://images.davcai.com/team/bohei.png', 'F组', 200.00, 25, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:06');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (3, 19, '世界杯冠军', '克罗地亚', 'http://images.davcai.com/team/keluodiya.png', 'A组', 125.00, 20, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:04');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (19, 20, '世界杯冠军', '厄瓜多尔', 'http://images.davcai.com/team/20140228132707.jpg', 'E组', 500.00, 28, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:06');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (2, 21, '世界杯冠军', '墨西哥', 'http://images.davcai.com/team/moxige.png', 'A组', 100.00, 19, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:04');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (27, 22, '世界杯冠军', '美国', 'http://images.davcai.com/team/meiguo.png', 'G组', 175.00, 14, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:07');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (28, 23, '世界杯冠军', '加纳', 'http://images.davcai.com/team/jiana.png', 'G组', 225.00, 38, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:07');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (23, 24, '世界杯冠军', '尼日利亚', 'http://images.davcai.com/team/niriliya.png', 'F组', 300.00, 44, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:07');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (12, 25, '世界杯冠军', '希腊', 'http://images.davcai.com/team/xila.png', 'C组', 600.00, 10, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:05');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (31, 26, '世界杯冠军', '韩国', 'http://images.davcai.com/team/hanguo.png', 'H组', 300.00, 55, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:08');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (4, 27, '世界杯冠军', '喀麦隆', 'http://images.davcai.com/team/kamailong.png', 'A组', 800.00, 50, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:04');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (8, 28, '世界杯冠军', '澳大利亚', 'http://images.davcai.com/team/aodaliya.png', 'B组', 800.00, 59, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:05');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (16, 29, '世界杯冠军', '哥斯达黎加', 'http://images.davcai.com/team/gesidalijia.png', 'D组', 400.00, 34, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:06');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (20, 30, '世界杯冠军', '洪都拉斯', 'http://images.davcai.com/team/hongdulasi.png', 'E组', 700.00, 30, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:06');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (24, 31, '世界杯冠军', '伊朗', 'http://images.davcai.com/team/yilang.png', 'F组', 700.00, 37, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:07');
INSERT INTO `cgj_teams` (`id`, `team_id`, `match_name`, `team_name`, `team_logo`, `group_name`, `odds`, `rank`, `season`, `current_season`, `play_type`, `sale_status`, `playing_time`, `offtime`, `update_time`, `created_time`) VALUES (32, 32, '世界杯冠军', '阿尔及利亚', 'http://images.davcai.com/team/aerjiliya.png', 'H组', 700.00, 25, '2014', 1, 'jcsjbgj', 1, '2014-07-14 03:00:00', '2014-07-14 02:59:00', '2014-06-16 14:41:16', '2014-05-21 16:56:08');


#增加玩法
INSERT INTO `lt_play` (`id`, `lottery_id`, `name`, `floor_ratio`) VALUES ('jcsjbgj', 'JCZQ', '竞猜世界杯冠军', 0);


#加入世界杯期信息表
CREATE TABLE `cgj_issueinfo` (
	`id` INT(20) UNSIGNED NOT NULL COMMENT '主键',
	`season` VARCHAR(20) NOT NULL COMMENT '赛季',
	`play_type` VARCHAR(20) NOT NULL COMMENT '玩法类型',
	`result` VARCHAR(20) NOT NULL COMMENT '赛果（对应猜冠军队伍表的team_id）',
	`update_time` DATETIME NOT NULL COMMENT '更新时间',
	`created_time` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `season_play_type` (`season`, `play_type`)
)
COMMENT='猜冠军玩法期信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1


#指定2014世界杯冠军得主
INSERT INTO `cgj_issueinfo` (`season`, `play_type`, `result`, `update_time`, `created_time`) 
	VALUES ('2014', 'jcsjbgj', '0', '2014-06-05 14:24:46', '2014-06-05 14:24:47');



#加入在球探表，2014世界杯球队的logoUrl
update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/baxi.png' 
where fb.chineseName = '巴西';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/deguo.png' 
where fb.chineseName = '德国';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/agenting.png' 
where fb.chineseName = '阿根廷';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/xibanya.png' 
where fb.chineseName = '西班牙';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/bilishi.png' 
where fb.chineseName = '比利时';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/helan.png' 
where fb.chineseName = '荷兰';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/yidali.png' 
where fb.chineseName = '意大利';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/faguo.png' 
where fb.chineseName = '法国';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/putouya.png'
where fb.chineseName = '葡萄牙';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/gelunbiya.png'
where fb.chineseName = '哥伦比亚';


update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/wulagui.png'
where fb.chineseName = '乌拉圭';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/yingelan.png'
where fb.chineseName = '英格兰';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/zhili.png'
where fb.chineseName = '智利';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/eluosi.png'
where fb.chineseName = '俄罗斯';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/ketediwa.png'
where fb.chineseName = '科特迪瓦';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/ruishi.png'
where fb.chineseName = '瑞士';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/riben.png'
where fb.chineseName = '日本';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/bohei.png'
where fb.chineseName = '波黑';


update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/keluodiya.png'
where fb.chineseName = '克罗地亚';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/eguaduoer.png'
where fb.chineseName = '厄瓜多尔';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/moxige.png'
where fb.chineseName = '墨西哥';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/meiguo.png'
where fb.chineseName = '美国';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/jiana.png'
where fb.chineseName = '加纳';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/niriliya.png'
where fb.chineseName = '尼日利亚';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/xila.png'
where fb.chineseName = '希腊';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/hanguo.png'
where fb.chineseName = '韩国';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/kamailong.png'
where fb.chineseName = '喀麦隆';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/aodaliya.png'
where fb.chineseName = '澳大利亚';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/gesidalijia.png'
where fb.chineseName = '哥斯达黎加';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/hongdulasi.png'
where fb.chineseName = '洪都拉斯';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/yilang.png'
where fb.chineseName = '伊朗';

update md_fb_team fb 
set fb.logoUrl = 'http://images.davcai.com/team/aerjiliya.png'
where fb.chineseName = '阿尔及利亚';



--更新世界杯球队logo url地址到本地
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/baxi.png' WHERE `team_id`=1 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/deguo.png' WHERE `team_id`=2 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/agenting.png' WHERE `team_id`=3 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/xibanya.png' WHERE `team_id`=4 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/bilishi.png' WHERE `team_id`=5 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/helan.png' WHERE `team_id`=6 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/yidali.png' WHERE `team_id`=7 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/faguo.png' WHERE `team_id`=8 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/putouya.png' WHERE `team_id`=9 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/gelunbiya.png' WHERE `team_id`=10 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/wulagui.png' WHERE `team_id`=11 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/yingelan.png' WHERE `team_id`=12 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/zhili.png' WHERE `team_id`=13 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/eluosi.png' WHERE `team_id`=14 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/ketediwa.png' WHERE `team_id`=15 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/ruishi.png' WHERE `team_id`=16 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/riben.png' WHERE `team_id`=17 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/bohei.png' WHERE `team_id`=18 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/keluodiya.png' WHERE `team_id`=19 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/eguaduoer.png' WHERE `team_id`=20 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/moxige.png' WHERE `team_id`=21 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/meiguo.png' WHERE `team_id`=22 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/jiana.png' WHERE `team_id`=23 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/niriliya.png' WHERE `team_id`=24 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/xila.png' WHERE `team_id`=25 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/hanguo.png' WHERE `team_id`=26 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/kamailong.png' WHERE `team_id`=27 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/aodaliya.png' WHERE `team_id`=28 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/gesidalijia.png' WHERE `team_id`=29 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/hongdulasi.png' WHERE `team_id`=30 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/yilang.png' WHERE `team_id`=31 LIMIT 1;
UPDATE `cgj_teams` SET `team_logo`='http://images.davcai.com/team/aerjiliya.png' WHERE `team_id`=32 LIMIT 1;


#2014世界杯猜冠军赛果（德国）
insert into cgj_issueinfo values(1, 2014, 'jcsjbgj', 2, now(), now());










