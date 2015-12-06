CREATE TABLE `lt_printable_ticket` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`ticket_id` BIGINT UNSIGNED NOT NULL COMMENT '关联lt_ticket的id字段',
	`print_bet_content` VARCHAR(1024) NOT NULL COMMENT '高速打印投注内容',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `ticket_id` (`ticket_id`)
)
COMMENT='可高速打印的票'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `lt_printable_file` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`file_name` VARCHAR(100) NOT NULL COMMENT '文件名',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='高速打印文件表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `lt_printable_ticket_file` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
	`ticket_id` BIGINT UNSIGNED NOT NULL COMMENT '票的id',
	`file_id` INT UNSIGNED NOT NULL COMMENT '高速打印文件的id',
	PRIMARY KEY (`id`),
	INDEX `ticket_id` (`ticket_id`),
	INDEX `file_id` (`file_id`)
)
COMMENT='高速打印文件与票关联表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

ALTER TABLE `lt_printable_ticket_file`
	ALTER `ticket_id` DROP DEFAULT;
ALTER TABLE `lt_printable_ticket_file`
	CHANGE COLUMN `ticket_id` `printable_ticket_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '可打印的票的id' AFTER `id`;

ALTER TABLE `lt_printable_file`
	ADD COLUMN `file_path` VARCHAR(200) NOT NULL COMMENT '文件路径' AFTER `create_time`,
	ADD COLUMN `file_url` VARCHAR(200) NOT NULL COMMENT '文件下载url' AFTER `file_path`;
