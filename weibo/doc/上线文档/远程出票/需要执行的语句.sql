CREATE TABLE `lt_ticket_remote` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`ticket_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '票id',
	`status` VARCHAR(50) NOT NULL COMMENT '远程出票系统返回的状态码',
	`message` VARCHAR(50) NOT NULL COMMENT '远程出票系统返回的状态码描述',
	`order_id` VARCHAR(50) NOT NULL COMMENT '订单id',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `Index 2` (`ticket_id`, `order_id`)
)
COMMENT='远程出票系统使用的订单和票之间关系表'
ENGINE=InnoDB
;
ALTER TABLE `lt_ticket_remote`
	ADD COLUMN `created_time` DATETIME NOT NULL COMMENT '创建时间' AFTER `order_id`;
	
ALTER TABLE `lt_ticket`
	ADD COLUMN `min_match_playing_time` DATETIME NULL DEFAULT NULL COMMENT '票包含的比赛中，最早的开赛时间' AFTER `ticket_succ_time`;