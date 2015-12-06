/**
 * 北单彩种
 * 新增玩法、每种玩法对应过关方式、每种玩法的玩法选项
 * by lei.li@unison.net.cn
 * */

/**北京单场玩法*/
INSERT INTO `lt_play` (`id`, `lottery_id`, `name`) VALUES ('91_BJDC_SPF', 'BJDC', '胜平负');
INSERT INTO `lt_play` (`id`, `lottery_id`, `name`) VALUES ('92_BJDC_JQS', 'BJDC', '进球数');
INSERT INTO `lt_play` (`id`, `lottery_id`, `name`) VALUES ('93_BJDC_SXDS', 'BJDC', '上下单双');
INSERT INTO `lt_play` (`id`, `lottery_id`, `name`) VALUES ('94_BJDC_BF', 'BJDC', '比分');
INSERT INTO `lt_play` (`id`, `lottery_id`, `name`) VALUES ('95_BJDC_BQC', 'BJDC', '半全场');
INSERT INTO `lt_play` (`id`, `lottery_id`, `name`) VALUES ('96_BJDC_SF', 'BJDC', '胜负');


/**北京单场玩法新增过关方式*/
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('2@3', '2串3', 3, 2003);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('3@7', '3串7', 7, 3007);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('4@15', '4串15', 15, 4015);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('5@31', '5串31', 31, 5031);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('6@63', '6串63', 63, 6063);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('9@1', '9串1', 1, 9001);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('10@1', '10串1', 1, 10001);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('11@1', '11串1', 1, 11001);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('12@1', '12串1', 1, 12001);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('13@1', '13串1', 1, 13001);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('14@1', '14串1', 1, 14001);
INSERT INTO `lt_pass_type` (`id`, `name`, `note`, `index`) VALUES ('15@1', '15串1', 1, 15001);

/**北京单场玩法对应过关方式*/
/**胜平负*/
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '1@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '2@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '2@3');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '3@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '3@4');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '3@7');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '4@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '4@5');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '4@11');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '4@15');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '5@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '5@6');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '5@16');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '5@26');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '5@31');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '6@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '6@7');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '6@22');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '6@42');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '6@57');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '6@63');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '7@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '8@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '9@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '10@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '11@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '12@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '13@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '14@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('91_BJDC_SPF', '15@1');

/**进球数*/
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '1@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '2@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '2@3');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '3@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '3@4');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '3@7');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '4@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '4@5');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '4@11');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '4@15');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '5@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '5@6');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '5@16');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '5@26');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '5@31');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '6@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '6@7');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '6@22');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '6@42');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '6@57');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '6@63');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '7@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '8@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '9@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '10@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '11@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '12@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '13@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '14@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('92_BJDC_JQS', '15@1');

/**上下单双*/
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '1@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '2@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '2@3');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '3@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '3@4');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '3@7');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '4@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '4@5');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '4@11');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '4@15');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '5@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '5@6');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '5@16');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '5@26');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '5@31');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '6@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '6@7');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '6@22');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '6@42');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '6@57');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '6@63');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '7@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '8@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '9@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '10@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '11@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '12@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '13@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '14@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('93_BJDC_SXDS', '15@1');

/**比分*/
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('94_BJDC_BF', '1@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('94_BJDC_BF', '2@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('94_BJDC_BF', '2@3');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('94_BJDC_BF', '3@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('94_BJDC_BF', '3@4');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('94_BJDC_BF', '3@7');

/**半全场*/
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '1@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '2@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '2@3');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '3@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '3@4');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '3@7');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '4@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '4@5');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '4@11');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '4@15');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '5@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '5@6');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '5@16');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '5@26');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '5@31');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '6@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '6@7');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '6@22');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '6@42');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '6@57');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '6@63');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '7@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '8@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '9@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '10@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '11@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '12@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '13@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '14@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('95_BJDC_BQC', '15@1');

/**北京单场胜负过关对应过关方式*/
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '3@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '4@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '4@5');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '5@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '5@6');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '5@16');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '6@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '6@7');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '6@22');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '6@42');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '7@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '8@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '9@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '10@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '11@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '12@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '13@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '14@1');
INSERT INTO `lt_play_pass_type` (`play_id`, `pass_type_id`) VALUES ('96_BJDC_SF', '15@1');


/**北京单场玩法选项定义*/
/**SPF*/
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('91_BJDC_SPF', '3', '胜', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('91_BJDC_SPF', '1', '平', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('91_BJDC_SPF', '0', '负', '');

/**JQS*/
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('92_BJDC_JQS', '0', '0', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('92_BJDC_JQS', '1', '1', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('92_BJDC_JQS', '2', '2', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('92_BJDC_JQS', '3', '3', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('92_BJDC_JQS', '4', '4', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('92_BJDC_JQS', '5', '5', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('92_BJDC_JQS', '6', '6', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('92_BJDC_JQS', '7', '7+', '');

/**SXDS*/
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('93_BJDC_SXDS', '11', '上单', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('93_BJDC_SXDS', '12', '上双', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('93_BJDC_SXDS', '01', '下单', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('93_BJDC_SXDS', '02', '下双', '');

/**BF*/
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '10', '1:0', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '20', '2:0', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '21', '2:1', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '30', '3:0', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '31', '3:1', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '32', '3:2', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '40', '4:0', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '40', '4:0', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '41', '4:1', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '90', '胜其他', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '00', '0:0', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '11', '1:1', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '22', '2:2', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '33', '3:3', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '99', '平其他', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '01', '0:1', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '02', '0:2', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '12', '1:2', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '03', '0:3', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '13', '1:3', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '23', '2:3', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '04', '0:4', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '14', '1:4', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '24', '2:4', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('94_BJDC_BF', '09', '负其他', '');

/**BQC*/
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('95_BJDC_BQC', '33', '胜胜', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('95_BJDC_BQC', '31', '胜平', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('95_BJDC_BQC', '30', '胜负', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('95_BJDC_BQC', '13', '平胜', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('95_BJDC_BQC', '11', '平平', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('95_BJDC_BQC', '10', '平负', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('95_BJDC_BQC', '03', '负胜', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('95_BJDC_BQC', '01', '负平', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('95_BJDC_BQC', '00', '负负', '');


/**SF*/
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('96_BJDC_SF', '3', '胜', '');
INSERT INTO `lt_play_option` (`play_id`, `value`, `name`, `note`) VALUES ('96_BJDC_SF', '0', '负', '');


