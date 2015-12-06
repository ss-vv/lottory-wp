package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.ConnectionPool;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;

/**
 * 竞彩篮球即时球队数据统计
 * @author 彭保星
 *
 * @since 2014年12月16日下午12:31:37
 */
public class LqJishiTeamStatisticUseThread extends AbstractJishiDataUseThread
		implements Runnable {
	private BasketBallMatchModel matchBaseInfoPO;
	private Logger log = LoggerFactory
			.getLogger(ZqJishiEventUseThread.class);

	public LqJishiTeamStatisticUseThread(BasketBallMatchModel matchModel) throws SQLException {
		super();
		this.matchBaseInfoPO = matchModel;
	}

	@Override
	public void run() {
		log.info("抓取篮球竞彩比赛id为{}即时球队统计开始",matchBaseInfoPO.getBsId());
		List<QtBasketMatchTeamStatisticModel> qtMatchEventStatistics = null;
		try {
			int i = 0;
			do {
				if (qtMatchEventStatistics == null) {
					makeHeader();
				}
				qtMatchEventStatistics = jishiBifenParseService
						.crawlerBasketMatchTeamStatisticData(header,
								matchBaseInfoPO.getBsId(),
								matchBaseInfoPO.getId());
			} while (qtMatchEventStatistics == null&&(++i )<MAX_CRAWLER_COUNT);
			jishiBifenDataStoreDao
					.storeMatchTeamStatisticData(qtMatchEventStatistics);
		} catch (Throwable e) {
			log.error("抓取篮球竞彩比赛id为{}即时球队统计出错:{}", matchBaseInfoPO.getBsId(), e);
		}
		finally{
			ConnectionPool.getInstance().closePool();
		}
		log.info("抓取篮球竞彩比赛id为{}即时球队统计完成",matchBaseInfoPO.getBsId());
	}

}
