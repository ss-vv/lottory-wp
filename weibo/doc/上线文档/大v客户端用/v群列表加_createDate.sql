ALTER TABLE `lt_bet_client_publish`
	CHANGE COLUMN `createDate` `createDate` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `imageUrl`;