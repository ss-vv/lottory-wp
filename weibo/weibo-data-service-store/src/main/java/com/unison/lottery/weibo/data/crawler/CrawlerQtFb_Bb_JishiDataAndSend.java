package com.unison.lottery.weibo.data.crawler;

import java.io.File;
import java.io.IOException;

import javax.persistence.criteria.From;

import com.unison.lottery.weibo.data.crawler.service.store.service.JishiBifenDataStoreService;
import com.unison.lottery.weibo.data.crawler.service.store.service.impl.JishiBifenDataStoreServiceImpl;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;

/**
 * 抓取球探数据并发送
 * @author baoxing.peng
 *
 */
public class CrawlerQtFb_Bb_JishiDataAndSend {
	public static JishiBifenDataStoreService jishiDataStoreService;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args != null && args.length > 0) {
			/**
			 * 运行前创建一个临时文件，即时比分抓取时需要检测当前是否已有进程正在抓取
			 * 如果pid.t文件存在，则表示有java进程在执行即时数据的抓取，停止本次抓取
			 * 该段代码只用于即时数据抓取时的检测，如要运行其他抓取任务，请注掉
			 * 否则请把注释打开
			 * //zqJingJishiEvent.t //lqJingJishiPlayerStatistics.t
			 */
			jishiDataStoreService = new JishiBifenDataStoreServiceImpl("");
			switch (args[0]) {
			case "zqjingcaijishibifen":
				makeCheckFile("zqjingcaipid.t");
				jishiDataStoreService.crawlerQtFbJishiBifenAndSend();
				break;
			case "lqjingcaiJishiBifen":
				makeCheckFile("lqjingcaipid.t");
				jishiDataStoreService.crawlerQtLqJingcaiJishiBifenAndSend();
				break;
			case "zqEuroOddsChange":
				makeCheckFile("zqeurooddspid.t");
				jishiDataStoreService.crawlerOddsChangeAndSend(Qt_fb_match_oddsType.euro);
				break;
			case "zqYpOddsChange":
				makeCheckFile("zqypoddspid.t");
				jishiDataStoreService.crawlerOddsChangeAndSend(Qt_fb_match_oddsType.asia);
				break;
			case "zqOuOddsChange":
				makeCheckFile("zqouoddspid.t");
				jishiDataStoreService.crawlerOddsChangeAndSend(Qt_fb_match_oddsType.ou);
				break;
			case "fbEventsInfo":
				makeCheckFile("lqjingcaipid.t");
				jishiDataStoreService.crawlerFbJishiEventsAndSend();
				break;
			case "bbMatchDetailStatistics": //抓取篮球比赛详情-球员统计
				makeCheckFile("lqMatchPlayerStaitics.t");
				jishiDataStoreService.crawlerBasketMatchPlayerStatisticAndSend();
				break;
			case "bbMatchDetailOverview": //抓取篮球比赛详情-球队统计
				makeCheckFile("lqMatchTeamStaitics.t");
				jishiDataStoreService.crawlerBasketMatchTeamStatisticAndSend();
				break;
			case "lqMatchOpOdds":
				makeCheckFile(Qt_fb_match_oddsType.euro);
				jishiDataStoreService.crawlerLqMatchOddsAndSend(Qt_fb_match_oddsType.euro);
				break;
			case "lqMatchYpOdds":
				makeCheckFile(Qt_fb_match_oddsType.asia);
				jishiDataStoreService.crawlerLqMatchOddsAndSend(Qt_fb_match_oddsType.asia);
				break;
			case "lqMatchOuOdds":
				makeCheckFile(Qt_fb_match_oddsType.ou);
				jishiDataStoreService.crawlerLqMatchOddsAndSend(Qt_fb_match_oddsType.ou);
				break;
			case "zqLive":
				makeCheckFile("zqLive");
				jishiDataStoreService.crawlerZqMatchLiveAndSend();
				break;
			default:
				break;
//			case "":
				
			}
		}
	}
	/**
	 * 
	 */
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
			fileName = "lqJingcaijishiYpOdds.t";
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
		}else{
			System.exit(0);
		}
		
	}

}
