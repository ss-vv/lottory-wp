package com.unison.lottery.weibo.data.crawler;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unison.lottery.weibo.data.crawler.service.store.service.CrawlerLqDataStoreService;
import com.unison.lottery.weibo.data.crawler.service.store.service.CrawlerZqDataStoreService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;

/**
 * 数据抓取入口
 * @author 彭保星
 *
 * @since 2014年10月28日上午9:12:53
 */
public class CrawlerFootBallWeiboDataAndStore {
	private static Logger logger = LoggerFactory.getLogger(CrawlerFootBallWeiboDataAndStore.class);
	public static void main(String args[]){
		/**
		 * 运行前创建一个临时文件，即时比分抓取时需要检测当前是否已有进程正在抓取
		 * 如果pid.t文件存在，则表示有java进程在执行即时数据的抓取，停止本次抓取
		 * 该段代码只用于即时数据抓取时的检测，如要运行其他抓取任务，请注掉
		 * 否则请把注释打开
		 */
//		File file = new File(CrawlerFootBallWeiboDataAndStore.class.getResource("/").getPath()+"pid.t");
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
		if(args!=null && args.length>0){
			logger.info("球探网赛事数据抓取开始.....");
			ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"classpath:crawler-service.xml","classpath:spring-db.xml"});
			CrawlerZqDataStoreService crawlerDataStoreService = (CrawlerZqDataStoreService)ctx.getBean("qtCrawlerDataStoreService");
			switch (args[0]) {
			case "fbleagueInfo":
				crawlerDataStoreService.crawlerFbLeagueDataAndStore();
				break;
			case "fbSubSeason":    //抓取当前赛季的子联赛以及对应的赛事并入库,包括杯赛和联赛
				crawlerLeagueAndMatch(crawlerDataStoreService);
				break;
			case "fbMatchInfo": //抓取当前赛季的联赛比赛数据
				crawlerDataStoreService.crawlerFbMatchDataAndStore();
				break;
			case "fbCupGroupInfo":
				crawlerDataStoreService.crawlerFbCupGroupDataAndStore(); //抓取杯赛小组信息
				break;
			case "fbCupMatchInfo": //抓取当前的杯赛比赛数据
				crawlerDataStoreService.crawlerFbCupMatchDataAndStore();
				break;
			case "fbEventsInfo"://抓取比赛事件
				crawlerDataStoreService.crawlerFbMatchEventDataAndStore();
				break;
			case "fbLineupInfo"://抓取比赛阵容
				crawlerDataStoreService.crawlerFbMatchLineupDataAndStore();
				break;
			case "fbLeagueScore": //抓取当前赛季联赛积分榜数据
				crawlerDataStoreService.crawlerFbLeagueScoreDataAndStore();
				break;
			case "fbCupScore": //当前赛季杯赛积分历史数据
				crawlerDataStoreService.crawlerCupScoreDataAndStore();
				break;
			case "qtJishiBiFen": //抓取球探即时比分
				crawlerDataStoreService.crawlerQtJishiBifen();
				break;
			case "qtMatch": //抓取彩客竞彩赛程
				crawlerDataStoreService.crawlerQtMatch();
				break;
			case "cupGroupAndMatch": //抓取杯赛分组和赛程信息
				crawlerCupGroupAndMatch(crawlerDataStoreService);
				break;
			case "eventLineUp": //即时事件和技术统计,和首发阵容
				crawlerEventAndLineUp(crawlerDataStoreService);
				break;
			case "fbOddsCompany": //抓取指数公司
				crawlerDataStoreService.crawlerFbOddsCompany();
				break;
			case "fbMatchOpOdds": //抓取竞彩比赛欧赔
				crawlerDataStoreService.crawlerFbMatchOdds(Qt_fb_match_oddsType.euro);
				break;
			case "fbMatchYpOdds": //抓取竞彩比赛亚赔
				crawlerDataStoreService.crawlerFbMatchOdds(Qt_fb_match_oddsType.asia);
				break;
			case "fbMatchOuOdds": //抓取竞彩比赛大小
				crawlerDataStoreService.crawlerFbMatchOdds(Qt_fb_match_oddsType.ou);
				break;
			case "fbMatchOuOddsDetail"://抓取竞彩比赛大小赔率的详情
				crawlerDataStoreService.crawlerFbMatchOddsDetail(Qt_fb_match_oddsType.ou);
				break;
			case "fbMatchAsianOddsDetail"://抓取竞彩比赛大小赔率的详情
				crawlerDataStoreService.crawlerFbMatchOddsDetail(Qt_fb_match_oddsType.asia);
				break;
			case "fbOdds": //足球未开赛的赛季赔率抓取
				crawlerFbOdds(crawlerDataStoreService);
				break;
			case "fbJincaiJishi": //足球竞彩即时数据抓取
				crawlerDataStoreService.crawlerFbJingcaiJishi();
				break;
			default:
				break;
			}
		}
	}
	
	private static void crawlerFbOdds(CrawlerZqDataStoreService crawlerZqDataStoreService) {
		crawlerZqDataStoreService.crawlerFbMatchOdds(Qt_fb_match_oddsType.euro);
		crawlerZqDataStoreService.crawlerFbMatchOdds(Qt_fb_match_oddsType.asia);
		crawlerZqDataStoreService.crawlerFbMatchOdds(Qt_fb_match_oddsType.ou);

	}
	private static void crawlerEventAndLineUp(
			CrawlerZqDataStoreService crawlerDataStoreService) {
		crawlerDataStoreService.crawlerFbMatchLineupDataAndStore();
		crawlerDataStoreService.crawlerFbMatchEventDataAndStore();
	}
	private static void crawlerCupGroupAndMatch(
			CrawlerZqDataStoreService crawlerDataStoreService) {
		// TODO Auto-generated method stub
		crawlerDataStoreService.crawlerFbCupGroupDataAndStore();
		crawlerDataStoreService.crawlerFbCupMatchDataAndStore();
		
	}
	private static void crawlerLeagueAndMatch(CrawlerZqDataStoreService crawlerDataStoreService) {
		// TODO Auto-generated method stub
		crawlerDataStoreService.crawlerFbLeagueDataAndStore();
		crawlerDataStoreService.crawlerFbSubLeagueSeasonMess();
		crawlerDataStoreService.crawlerFbMatchDataAndStore();
		crawlerDataStoreService.crawlerFbCupGroupDataAndStore();
		crawlerDataStoreService.crawlerFbCupMatchDataAndStore();
	}
}
