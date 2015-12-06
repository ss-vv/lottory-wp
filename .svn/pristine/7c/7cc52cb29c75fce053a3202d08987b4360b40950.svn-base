package com.unison.lottery.weibo.data.crawler;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unison.lottery.weibo.data.crawler.service.store.service.CrawlerLqDataSendService;
import com.unison.lottery.weibo.data.crawler.service.store.service.CrawlerZqDataSendService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
/**
 * 抓取微博数据并发送至本地服务器
 * 
 * @author baoxing.peng
 *
 * @since 2015年1月8日下午3:02:15
 */
public class CrawlerQtFb_Bb_WeiboDataAndSend {
	private static Logger logger = LoggerFactory
			.getLogger(CrawlerQtFb_Bb_WeiboDataAndSend.class);

	public static void main(String args[]) {
		/**
		 * 运行前创建一个临时文件，即时比分抓取时需要检测当前是否已有进程正在抓取
		 * 如果pid.t文件存在，则表示有java进程在执行即时数据的抓取，停止本次抓取
		 * 该段代码只用于即时数据抓取时的检测，如要运行其他抓取任务，请注掉 否则请把注释打开
		 */
//		File file = new File(CrawlerFootBallWeiboDataAndStore.class
//				.getResource("/").getPath() + "pid.t");
//		if (!file.exists()) {
//			try {
//				file.createNewFile();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		file.deleteOnExit();
		/****************************************************************/
		if (args != null && args.length > 0) {
			logger.info("球探网赛事数据抓取开始.....");
			ApplicationContext ctx = new ClassPathXmlApplicationContext(
					new String[] { "classpath:crawler-service.xml"});
			CrawlerZqDataSendService crawlerDataStoreService = (CrawlerZqDataSendService) ctx
					.getBean("crawlerZqDataSendService");
			CrawlerLqDataSendService crawlerLqDataStoreService = (CrawlerLqDataSendService) ctx
					.getBean("crawlerLqDataSendService");
			switch (args[0]) {
			case "fbleagueInfo":
				crawlerDataStoreService.crawlerFbLeagueDataAndSend();
				break;
			case "fbSubSeason": // 抓取当前赛季的子联赛以及对应的赛事并入库,包括杯赛和联赛
				crawlerLeagueAndMatch(crawlerDataStoreService);
				break;
			case "fbSubLeague":
				crawlerDataStoreService.crawlerFbSubLeagueSeasonMessAndSend();
				break;
			case "fbMatchInfo": // 抓取当前赛季的联赛比赛数据
				crawlerDataStoreService.crawlerFbMatchDataAndSend();
				break;
			case "fbCupGroupInfo":
				crawlerDataStoreService.crawlerFbCupGroupDataAndSend(); // 抓取杯赛小组信息
				break;
			case "fbCupMatchInfo": // 抓取当前的杯赛比赛数据
				crawlerDataStoreService.crawlerFbCupMatchDataAndSend();
				break;
			case "fbLineupInfo":// 抓取比赛阵容
				crawlerDataStoreService.crawlerFbMatchLineupDataAndSend();
				break;
			case "fbLeagueScore": // 抓取当前赛季联赛积分榜数据
				crawlerDataStoreService.crawlerFbLeagueScoreDataAndSend();
				break;
			case "fbCupScore": // 当前赛季杯赛积分历史数据
				crawlerDataStoreService.crawlerCupScoreDataAndSend();
				break;
			case "qtJishiBiFen": // 抓取球探即时比分
				crawlerDataStoreService.crawlerQtJishiBifenAndSend();
				break;
			case "lqJishiMatch":
				crawlerLqDataStoreService.crawlerLqJishiBifenAndSend();
				break;
			case "lqLeagSeason": //篮球联赛，赛季信息抓取
				crawlerLqDataStoreService.crawlerLqLeagueNowSeasonMessAndSend();
				break;
			case "lqLeagueMatchNowSeason": //篮球当前赛季联赛赛程
				crawlerLqDataStoreService.crawlerHistoryMatchAndSend();
				break;
			case "lqSubCupNowSeason":
				crawlerLqDataStoreService.crawlerHistorySubCupAndSend();
				break;
			case "lqSubLeagueNowSeason":
				crawlerLqDataStoreService.crawlerHistorySubLeagueAndSend();
				break;
			case "lqCupMatchNowSeason":
				crawlerLqDataStoreService.crawlerHistoryCupMatchAndSend();
				break;
			case "lqLeagueMatchSeasonCawler":
				crawlerLqLeagueMatchNowSeason(crawlerLqDataStoreService);
				break;
			case "zqEuroOddsNew": //足球欧赔新接口，增加返还率和凯利指数
				makeCheckFile("euroOddsNew");
				crawlerDataStoreService.crawlerFbEuroOddsNewAndSend(Qt_fb_match_oddsType.euro);
				break;
			default:
				break;
			}
		}
	}
	
	private static void crawlerLqLeagueMatchNowSeason(
			CrawlerLqDataSendService crawlerLqDataStoreService) {
		// TODO Auto-generated method stub
		crawlerLqDataStoreService.crawlerLqLeagueNowSeasonMessAndSend();
		crawlerLqDataStoreService.crawlerHistorySubLeagueAndSend();
		crawlerLqDataStoreService.crawlerHistoryMatchAndSend();
		crawlerLqDataStoreService.crawlerHistorySubCupAndSend();
		crawlerLqDataStoreService.crawlerHistoryCupMatchAndSend();
		
	}

	private static void crawlerLeagueAndMatch(CrawlerZqDataSendService crawlerDataStoreService) {
		// TODO Auto-generated method stub
		crawlerDataStoreService.crawlerFbLeagueDataAndSend();
		crawlerDataStoreService.crawlerFbSubLeagueSeasonMessAndSend();
		crawlerDataStoreService.crawlerFbMatchDataAndSend();
		crawlerDataStoreService.crawlerFbCupGroupDataAndSend();
		crawlerDataStoreService.crawlerFbCupMatchDataAndSend();
	}
	
	private static void makeCheckFile(String fileName) {
		File file = new File(Crawler_Fb_Bb_JishiDataAndStore.class.getResource("/").getPath()+fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			file.deleteOnExit();
		}else{
			//有临时文件表名上次任务执行并未结束，停止执行本次抓取
			System.exit(0);
		}
		
	}
}
