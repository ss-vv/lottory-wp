ALTER TABLE `lt_user`
	ADD COLUMN `allow_see_important_content_in_client` SMALLINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '是否允许查看客户端的重要内容如首页、晒单合买和v群。0为不允许，1为允许' AFTER `alipayToken`;
