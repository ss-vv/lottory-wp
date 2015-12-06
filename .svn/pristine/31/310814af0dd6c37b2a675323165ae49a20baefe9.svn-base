package com.unison.lottery.weibo.data.crawler;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unison.lottery.weibo.data.crawler.service.store.service.CrawlerLqDataStoreService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;


/**
 * 球探篮球数据抓取
 * @author 彭保星
 *
 * @since 2014年11月19日上午11:13:56
 */
public class CrawlerBasketBallWeiboDataAndStore{
	private static Logger logger = LoggerFactory.getLogger(CrawlerBasketBallWeiboDataAndStore.class);
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * 运行前创建一个临时文件，即时比分抓取时需要检测当前是否已有进程正在抓取
		 * 如果pid.t文件存在，则表示有java进程在执行即时数据的抓取，停止本次抓取
		 * 该段代码只用于即时数据抓取时的检测，如要运行其他抓取任务，请注掉
		 * 否则请把注释打开
		 */
//		File file = new File(CrawlerQtBasketBallMessage.class.getResource("/").getPath()+"lqpid.t");
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
		if(args!=null&&args.length>0){
			logger.info("球探网篮球赛事数据抓取开始.....");
			ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:crawler-service.xml","classpath:spring-db.xml"});
			final CrawlerLqDataStoreService crawlerLqDataStoreService = (CrawlerLqDataStoreService)ctx.getBean("qtCrawlerLqDataStoreService");
			switch (args[0]) {
			case "lqJishiMatch":
				crawlerLqDataStoreService.crawlerLqJishiBifen();
				break;
			case "lqLeagSeason": //篮球联赛，赛季信息抓取
				crawlerLqDataStoreService.crawlerLqLeagueSeasonHistoryMess();
				break;
			case "lqLeagueMatchSeasonCawler":
				crawlerLqLeagueMatchNowSeason(crawlerLqDataStoreService);
				break;
			case "lqJingcaiMatch":
				crawlerLqDataStoreService.crawlerLqJingcaijishiBifen();
				break;
			case "lqLeagueMatchNowSeason": //篮球当前赛季联赛赛程
				crawlerLqDataStoreService.crawlerHistoryMatch();
				break;
			case "lqSubCupNowSeason":
				crawlerLqDataStoreService.crawlerHistorySubCup();
				break;
			case "lqSubLeagueNowSeason":
				crawlerLqDataStoreService.crawlerHistorySubLeague();
				break;
			case "lqCupMatchNowSeason":
				crawlerLqDataStoreService.crawlerHistoryCupMatch();
				break;
			case "bbMatchOpOdds": //抓取比赛欧赔
				crawlerLqDataStoreService.crawlerBbMatchOdds(Qt_fb_match_oddsType.euro);
				break;
			case "bbMatchYpOdds": //抓取比赛亚赔
				crawlerLqDataStoreService.crawlerBbMatchOdds(Qt_fb_match_oddsType.asia);
				break;
			case "bbMatchOuOdds": //抓取比赛大小
				crawlerLqDataStoreService.crawlerBbMatchOdds(Qt_fb_match_oddsType.ou);
				break;
			case "lqOdds":
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						logger.info("线程1启动，抓取欧赔");
						crawlerLqDataStoreService.crawlerBbMatchOdds(Qt_fb_match_oddsType.euro);
					}
				},"欧赔线程").start();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						logger.info("线程2启动，抓取亚赔");
						// TODO Auto-generated method stub
						crawlerLqDataStoreService.crawlerBbMatchOdds(Qt_fb_match_oddsType.asia);
					}
				},"亚赔线程").start();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						
					}
				},"大小赔率线程").start();
				//crawlerLqHistoryOdd(crawlerLqDataStoreService);
				break;
			case "lqMatchDetailOverviewAndStatistics":
				crawlerOverviewStatistics(crawlerLqDataStoreService);
				break;
			case "bbMatchDetailOverview": //抓取篮球比赛详情-球队统计
				crawlerLqDataStoreService.crawlerBasketMatchTeamStatistic();
				break;
			case "bbMatchDetailStatistics": //抓取篮球比赛详情-球员统计
				crawlerLqDataStoreService.crawlerBasketMatchPlayerStatistic();
				break;
			case "lqLeagueScoreNowSeason": //篮球历史积分
				crawlerLqDataStoreService.crawlerHistoryLeagueScore();
				break;
			default:
				break;
			}
		}
	}


	private static void crawlerLqLeagueMatchNowSeason(
			CrawlerLqDataStoreService crawlerLqDataStoreService) {
		// TODO Auto-generated method stub
		crawlerLqDataStoreService.crawlerLqLeagueSeasonHistoryMess();
		crawlerLqDataStoreService.crawlerHistorySubLeague();
		crawlerLqDataStoreService.crawlerHistoryMatch();
		crawlerLqDataStoreService.crawlerHistorySubCup();
		crawlerLqDataStoreService.crawlerHistoryCupMatch();
		
	}


	private static void crawlerOverviewStatistics(
			CrawlerLqDataStoreService crawlerLqDataStoreService) {
		// TODO Auto-generated method stub
		crawlerLqDataStoreService.crawlerBasketMatchTeamStatistic();
		crawlerLqDataStoreService.crawlerBasketMatchPlayerStatistic();
		
	}
	
	private static void crawlerLqHistoryOdd(
			CrawlerLqDataStoreService crawlerLqDataStoreService) {
		crawlerLqDataStoreService.crawlerBbMatchOdds(Qt_fb_match_oddsType.euro);
		crawlerLqDataStoreService.crawlerBbMatchOdds(Qt_fb_match_oddsType.asia);
		crawlerLqDataStoreService.crawlerBbMatchOdds(Qt_fb_match_oddsType.ou);
	}

}
