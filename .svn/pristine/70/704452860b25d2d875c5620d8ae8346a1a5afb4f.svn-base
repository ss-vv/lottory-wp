package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.ConnectionPool;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;

/**
 * 篮球竞彩即时球员统计数据
 * @author 彭保星
 *
 * @since 2014年12月15日下午6:10:28
 */
public class LqJishiStatisticsUseThread extends AbstractJishiDataUseThread implements Runnable {
	private BasketBallMatchModel matchModel;
	Logger logger = LoggerFactory.getLogger(LqJishiStatisticsUseThread.class);
	public LqJishiStatisticsUseThread(BasketBallMatchModel matchModel) {
		super();
		this.matchModel = matchModel;
	}

	@Override
	public void run() {
		logger.info("抓取竞彩篮球比赛id为{}的即时球员统计开始",matchModel.getBsId());
		try{
			List<QtBasketMatchPlayerStatisticModel> qtMatchEventStatistics = null;
			int i=0;
			do {
				if (qtMatchEventStatistics == null) {
					makeHeader();
				}
				qtMatchEventStatistics = jishiBifenParseService
						.crawlerBasketMatchPlayerStatisticHasFinishedData(
								header,matchModel.getBsId(),matchModel.getId());
			} while (qtMatchEventStatistics == null&&(++i)<MAX_CRAWLER_COUNT);
			jishiBifenDataStoreDao
					.storeBasketMatchPlayerStatisticData(qtMatchEventStatistics);
		}catch(Throwable eThrowable){
			logger.error("抓取篮球球员数据统计出错:",eThrowable);
		}finally{
			ConnectionPool.getInstance().closePool();
		}
		logger.info("抓取竞彩篮球比赛id为{}的即时球员统计完成",matchModel.getBsId());
	}

}
