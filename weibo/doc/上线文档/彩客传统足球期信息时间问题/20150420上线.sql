INSERT INTO `db_lottery`.`lt_system_conf` (`conf_key`, `conf_value`, `desc`) VALUES ('ctzc_310win_stop_time_second', '480', '设置stop_time比通过彩客获取的传统足彩期信息close_time提前的时间间隔，单位为秒');
INSERT INTO `db_lottery`.`lt_system_conf` (`conf_key`, `conf_value`, `desc`) VALUES ('ctzc_310win_close_time_second', '300', '设置传统足球本地close_time相对彩客close_time的时间间隔');
update lottery_platform_priority
set lottery_id='JCZQ'
where
interface_name='queryMatchOdds';

update lottery_platform_priority
set lottery_id='JCZQ'
where
interface_name='queryMatch';

insert into lottery_platform_priority(lottery_platform_id,interface_name,lottery_id,priority)
values('zunao','queryMatchOdds','JCLQ',1);

insert into lottery_platform_priority(lottery_platform_id,interface_name,lottery_id,priority)
values('zunao','queryMatch','JCLQ',1);
INSERT INTO `db_lottery`.`lt_system_conf` (`conf_key`, `conf_value`, `desc`) VALUES ('get_ctzc_data_platform', '0', '抓取传统足球数据源   0为310win 1为尊奥');
