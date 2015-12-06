ALTER TABLE `lottery_platform_priority`
	ADD COLUMN `lottery_platform_alias_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '平台别名' AFTER `lottery_id`;

update lottery_platform_priority
set lottery_platform_alias_name='长春站02856'
where
lottery_platform_id='changchunshitidian2';

update lottery_platform_priority
set lottery_platform_alias_name='天津站04904'
where
lottery_platform_id='changchunshitidian';

update lottery_platform_priority
set lottery_platform_alias_name='长春站60005'
where
lottery_platform_id='changchun60005';

update lottery_platform_priority
set lottery_platform_alias_name='山西19468'
where
lottery_platform_id='shanxi00001';



INSERT INTO `db_lottery`.`sys_permissions` (`permission`, `description`) VALUES ('ticket:allocate', '分票管理');
INSERT INTO `db_lottery`.`sys_roles_permissions` (`role_id`, `permission_id`) VALUES (11, 37);