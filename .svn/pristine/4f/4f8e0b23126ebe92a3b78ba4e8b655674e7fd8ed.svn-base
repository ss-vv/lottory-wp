package com.unison.lottery.weibo.data.crawler;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.ConnectionPool;
import com.unison.lottery.weibo.data.crawler.service.store.service.JishiBifenDataStoreService;
import com.unison.lottery.weibo.data.crawler.service.store.service.impl.JishiBifenDataStoreServiceImpl;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;

/**
 * 即时比分采用纯jdbc链接
 * 
 * @author 彭保星
 *
 * @since 2014年12月1日下午5:26:25
 */
public class Crawler_Fb_Bb_JishiDataAndStore {
	public static JishiBifenDataStoreService jishiDataStoreService;
	public static void main(String[] args) {
		/****************************************************************/
		if (args != null && args.length > 0) {
			jishiDataStoreService = new JishiBifenDataStoreServiceImpl();
			switch (args[0]) {
			case "zqjishibifen":
				makeCheckFile("zqjingcai");
				jishiDataStoreService.crawlerQtJishiBifen();
				break;
			case "lqjishibifen":
				makeCheckFile("lqjingcai");
				jishiDataStoreService.crawlerQtLqJishiBifen();
				break;
			case "fbMatchOpOdds": //抓取竞彩比赛欧赔即时
				jishiDataStoreService.crawlerFbMatchOdds(Qt_fb_match_oddsType.euro);
				break;
			case "fbMatchYpOdds": //抓取竞彩比赛亚赔
				jishiDataStoreService.crawlerFbMatchOdds(Qt_fb_match_oddsType.asia);
				break;
			case "fbMatchOuOdds": //抓取竞彩比赛大小
				jishiDataStoreService.crawlerFbMatchOdds(Qt_fb_match_oddsType.ou);
				break;
			case "lqMatchOdds":
				makeCheckFile(Qt_fb_match_oddsType.odds);
				
				jishiDataStoreService.crawlerLqMatchOdds(Qt_fb_match_oddsType.odds);
				break;
			case "fbEventsInfo"://抓取比赛事件
				jishiDataStoreService.crawlerFbMatchEventDataAndStore();
				break;
			case "bbMatchDetailStatistics": //抓取篮球比赛详情-球员统计
				jishiDataStoreService.crawlerBasketMatchPlayerStatistic();
				break;
			case "bbMatchDetailOverview": //抓取篮球比赛详情-球队统计
				jishiDataStoreService.crawlerBasketMatchTeamStatistic();
				break;
			case "zqEuroOddsChange":
				makeCheckFile(Qt_fb_match_oddsType.euro+"change");
				jishiDataStoreService.crawlerOddsChange(Qt_fb_match_oddsType.euro);
				break;
			case "zqAsianOddsChange":
				makeCheckFile(Qt_fb_match_oddsType.asia+"change");
				jishiDataStoreService.crawlerOddsChange(Qt_fb_match_oddsType.asia);
				break;
			case "zqOuOddsChange":
				makeCheckFile(Qt_fb_match_oddsType.ou+"change");
				jishiDataStoreService.crawlerOddsChange(Qt_fb_match_oddsType.ou);
				break;
			case "zqLive":
				makeCheckFile("zqLive");
				jishiDataStoreService.crawlerZqMatchLiveAndStore();
				break;
			default:
				break;
			}
		}
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
	private static void makeCheckFile(Qt_fb_match_oddsType oddsType) {
		String fileName = "";
		if(oddsType==Qt_fb_match_oddsType.euro){
			fileName = "lqJingcaijishiOpOdds.t";
		}else if(oddsType == Qt_fb_match_oddsType.asia){
			fileName = "lqJingcaijishiYpOdds.t";
		}else {
			fileName = "lqJingcaijishiOdds.t";
		}
		File file = new File(CrawlerBasketBallWeiboDataAndStore.class.getResource("/").getPath()+fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			file.deleteOnExit();
		}else {
			System.exit(0);
		}
		
	}
}
