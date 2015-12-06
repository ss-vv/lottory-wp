package com.unison.lottery.weibo.dataservice.commons.crawler.constants;

/**
 * 球探客户端数据接口名称
 * @author 彭保星
 *
 * @since 2014年10月28日下午2:53:44
 */
public enum CrawlerInterfaceName {
	/**
	 * 资料库中的联赛信息:
	 */
	InfoIndex, //http://apk.win007.com/phone/InfoIndex.aspx?lang=0&ran=1413170433253
	/**
	 * 资料库中赛程数据
	 */
	MatchProcessInZLKu,//:http://apk.win007.com/phone/SaiCheng2.aspx?sclassid=36&Season=2014-2015&Round=3&ran=1414136577604
 
	/**
	 * 资料库杯赛赛事赛程、积分数据
	 */
	CupMatchInfo, //http://apk.win007.com/phone/CupSaiCheng.aspx?ID=649&Season=2011-2013&GroupID=&ran=1414660529952
	/**
	 * 联赛积分
	 */
	LeagueScore, //http://apk.win007.com/phone/Jifen2.aspx?sclassid=36&Season=2014-2015&ran=1413170750569 (联赛id，赛季)
	/**
	 * 赛事分析事件数据
	 */
	MatchEventData, //http://apk.win007.com/phone/ResultDetail.aspx?ID=956976&lang=0&ran=1415011073668
	/**
	 * 即时比分全部赛事接口
	 */
	QtJishiBifen, //http://txt.win007.com/phone/schedule_0_0.txt?ran=1414049968768
	
	/**
	 * 赛事分析阵容数据
	 */
	MatchLineupData, //http://apk.win007.com/phone/Lineup.aspx?ID=994881&lang=0&ran=1415080672981
	
	/**
	 * 指数赔率公司
	 */
	QtOddsCompany, //http://apk.win007.com/Phone/Company.aspx?ran=1416278219704
	
	/**
	 * 赛事欧赔数据
	 */
	QtFbMatchOpOdds, //http://apk.win007.com/Phone/1x2EuroDetail.aspx?CompanyID=3&ScheID=1063817&ran=1415957220275
	/**
	 * 赛事亚赔数据
	 */
	QtFbMatchYpOdds, //http://apk.win007.com/Phone/AsianDetail.aspx?CompanyID=1&ScheID=1068352&ran=1415957130330
	/**
	 * 赛事大小数据
	 */
	QtFbMatchOuOdds, //http://apk.win007.com/Phone/OuDetail.aspx?CompanyID=8&ScheID=1059436&ran=1415957262864
	
	/**
	 * 篮球即时比分
	 */
	QtBasketJishiBifen, //http://txt.win007.com/phone/lqscore/schedule_0_0.txt
	/**
	 * 篮球联赛数据
	 */
	QtBasketLeagueInfo, //http://apk.win007.com/phone/LqInfoIndex.aspx
	
	/**
	 * 篮球 赛事欧赔数据
	 */
	QtBasketMatchOpOdds, //http://apk.win007.com/Phone/Lq1x2EuroDetail.aspx?CompanyID=8&ScheID=201147&ran=1415957356770
	/**
	 * 篮球 赛事亚赔数据
	 */
	QtBasketMatchYpOdds, //http://apk.win007.com/Phone/LqAsianDetail.aspx?CompanyID=8&ScheID=201147&ran=1415957356770
	/**
	 * 篮球 赛事大小数据
	 */
	QtBasketMatchOuOdds,  //http://apk.win007.com/Phone/LqOuDetail.aspx?CompanyID=8&ScheID=201147&ran=1415957356770
	
	/**
	 * 篮球 赛事球队统计
	 */
	QtBasketMatchTeamStatistic, //http://apk.win007.com/phone/LqTeamTechnic.aspx?ID=197132&lang=0&ran=1414043641088
	/**
	 * 篮球 赛事队员统计
	 */
	QtBasketMatchPlayerStatistic, //http://txt.win007.com/jsData/tech/1/97/197132.js?flesh=1414045316764&ran=1414045316764
	/**
	 * 足球竞彩数据
	 */
	QtFootBallJingCaiJishi, //http://txt.win007.com/phone/schedule_0_3.txt?ran=1414045316764
	/**
	 * 篮球竞彩比分
	 */
	QtBasketJingCaiBifen,  //http://apk.win007.com/phone/LqSchedule.aspx?lang=0&type=2&ran=1414744925673
	
	/**
	 * 篮球资料库赛程
	 */
	LqSaicheng,   //http://apk.win007.com/phone/LqSaiCheng2.aspx?sclassid=1&season=14-15&lang=0&kind=1&m=2014-11&ran=1414638947994
	/**
	 * 篮球资料库杯赛赛程
	 */
	BasketCupMatchInfo, //http://apk.win007.com/phone/LqCupSaiCheng.aspx?ID=160&Season=14&GroupID=&lang=0&ran=1414657751199
	/**
	 * 篮球联赛积分榜
	 */
	LqLeagueScore,  //http://apk.win007.com/phone/LqJifen.aspx?id=1&season=14-15&lang=0&ran=1414568112617
	/**
	 * 竞彩赛事欧赔
	 */
	QtFbJingcaiMatchJishiOpOdds, //http://apk.win007.com/phone/1x2.aspx?ID=956976&lang=0&ran=1415011336639
	QtFbJingcaiMatchHistoryOpOdds, //http://apk.win007.com/phone/1x2Detail.aspx?OddsID=43803748&ran=1418117949765
	
	/**
	 * 竞彩赛事亚赔
	 */
	QtFbJingcaiMatchJishiYaOdds, //http://apk.win007.com/phone/Handicap.aspx?ID=956976&lang=0&ran=1415011296996
	QtFbJingcaiMatchHistoryYaOdds, //http://apk.win007.com/phone/HandicapDetail.aspx?OddsID=4370784&ran=1418117918247
	
	/**
	 * 竞彩赛事大小球
	 */
	QtFbJingcaiMatchJishiOuOdds, //http://apk.win007.com/phone/OverUnder.aspx?ID=956976&lang=0&ran=1415011226522
	QtFbJingcaiMatchHistoryOuOdds, //http://apk.win007.com/phone/OverUnderDetail.aspx?OddsID=3906261&ran=1418118066735
	zqMatchOpOddsOneCompany,   //http://apk.win007.com/phone/1x2EuroDetail.aspx?ScheID=956976&OddsID=42921569&ran=1422930436848
	/**
	 * 即时指数详情
	 */
	QtFbJishizhishuOuOddsDetails,  //http://apk.win007.com/Phone/OuDetail.aspx?CompanyID=8&ScheID=1059436&ran=1415957262864
	
	QtFbJishizhishuAisanOddsDetails, //http://apk.win007.com/Phone/AsianDetail.aspx?CompanyID=1&ScheID=1068352&ran=1415957130330
	qtMatch,  //http://client.310win.com/Default.aspx?client=1&transcode=105&msg={%22typeid%22%3A%22101%22%2C%22kind%22%3A%222%22}&version=1.6&key=fdd7059153063182f8be3cd190f9dd30
	caikeBetting, //彩客投注页
}
