package com.davcai.lottery.weibo.analyse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.davcai.lottery.weibo.analyse.service.MatchAnalyseService;

/**
 * 比赛数据分析及将分析结果添加到redis缓存
 * 
 * @author baoxing.peng
 *
 */
public class WeiboDataAnalyse {
	private static Logger logger = LoggerFactory
			.getLogger(WeiboDataAnalyse.class);

	public static void main(String[] args) {

		if (args != null && args.length > 0) {
			logger.info("微博比赛数据分析开始...");
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "classpath:analyse.xml","classpath:spring-analyse-db.xml" });
			MatchAnalyseService matchAnalyseService = (MatchAnalyseService) ctx
					.getBean("matchAnalyseService");
			switch (args[0]) {
			case "fbleagueScoreRank": // 联赛积分排名分析
				matchAnalyseService.analyseFbLeaguseScoreRank();
				break;
			case "lqLeagueScoreRank": //篮球联赛积分排名
				matchAnalyseService.analyseBbLeagueScoreRank();
				break;
			case "fbLeagueHalfScoreRank":
				matchAnalyseService.analyseFbHalfLeagueScoreRank();
			default:
				break;
			}
		}
	}

}
