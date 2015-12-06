ALTER TABLE `lt_bet_scheme`
	ADD COLUMN `min_bonus` DECIMAL(12,2) NOT NULL DEFAULT '0.00' AFTER `max_bonus`;