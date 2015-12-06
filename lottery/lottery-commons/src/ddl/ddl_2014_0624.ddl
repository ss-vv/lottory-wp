#投注赛事加注释
ALTER TABLE `lt_bet_match`  ADD COLUMN `annotation` VARCHAR(1000) NULL COMMENT '投注赛事的注释' AFTER `concede`;

#加入方案公开时间
ALTER TABLE `lt_bet_scheme`  ADD COLUMN `public_time` DATETIME NULL AFTER `preset_award`;

#推荐注释字段
ALTER TABLE `lt_bet_match_rec`  ADD COLUMN `annotation` VARCHAR(1000) NULL DEFAULT NULL COMMENT '注释内容' AFTER `play_id`;
