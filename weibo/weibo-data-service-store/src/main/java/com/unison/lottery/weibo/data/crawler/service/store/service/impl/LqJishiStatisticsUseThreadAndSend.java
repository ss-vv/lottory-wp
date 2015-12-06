package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.ConnectionPool;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;

/**
 * 篮球竞彩即时球员统计数据
 * @author 彭保星
 *
 * @since 2014年12月15日下午6:10:28
 */
public class LqJishiStatisticsUseThreadAndSend extends AbstractJishiDataUseThread implements Runnable {
	private BasketBallMatchModel matchModel;
	Logger logger = LoggerFactory.getLogger(LqJishiStatisticsUseThreadAndSend.class);
	public LqJishiStatisticsUseThreadAndSend(BasketBallMatchModel matchModel) {
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
					makeHeaderFromFile();
				}
				qtMatchEventStatistics = jishiBifenParseService
						.crawlerBasketMatchPlayerStatisticHasFinishedData(
								header,matchModel.getBsId(),matchModel.getId());
			} while (qtMatchEventStatistics == null&&(++i)<MAX_CRAWLER_COUNT);
			jishiBifenParseService
					.sendBasketMatchPlayerStatisticData(qtMatchEventStatistics);
		}catch(Throwable eThrowable){
			logger.error("抓取篮球球员数据统计出错:",eThrowable);
		}finally{
			ConnectionPool.getInstance().closePool();
		}
		logger.info("抓取竞彩篮球比赛id为{}的即时球员统计完成",matchModel.getBsId());
	}
	private void makeHeaderFromFile() {
		if (header == null) {
			header = new HashMap<String, String>();
		}
		String headerList = SystemPropertiesUtil.getPropsValue("headerList");
		String[] header1 = headerList.split("\\;",-1);
		int size = header1.length;
		Random random = new Random();
		int index = random.nextInt(size);
		if (header1[index] != null) {
			String[] agent = header1[index].split("\\:",-1);
			// 生成ua
			String user_agent = String.format(
					"Score (%s; %s)",
					new Object[] { agent[1],
							agent[0] });
			header.put("User-Agent", user_agent);
		}
	}

}
