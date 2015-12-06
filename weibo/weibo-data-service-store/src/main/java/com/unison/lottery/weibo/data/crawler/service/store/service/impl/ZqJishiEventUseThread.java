package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.ConnectionPool;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.AwdataStore;
/**
 * 足球竞彩即时事件
 * @author 彭保星
 *
 * @since 2014年12月15日下午6:10:40
 */
public class ZqJishiEventUseThread extends AbstractJishiDataUseThread implements Runnable{
	
	private FbMatchBaseInfoPO matchBaseInfoPO;
	private Logger log = LoggerFactory
			.getLogger(ZqJishiEventUseThread.class);

	public ZqJishiEventUseThread(FbMatchBaseInfoPO matchBaseInfoPO) throws SQLException {
		super();
		this.matchBaseInfoPO = matchBaseInfoPO;
	}

	@Override
	public void run() {
		int i=0;
		List qtMatchEventStatistics = null;
		try {
			log.info("比赛id为{}的比赛事件及数据统计数据抓取开始..",matchBaseInfoPO.getBsId());
			do {
				if (qtMatchEventStatistics == null) {
					makeHeader();
				}
				qtMatchEventStatistics = jishiBifenParseService
						.crawlerMatchEventHasFinishedData(header,
								matchBaseInfoPO.getBsId(),
								matchBaseInfoPO.getId());

			} while (qtMatchEventStatistics == null && i <= MAX_CRAWLER_COUNT);

			AwdataStore matchEvents = qtMatchEventStatistics.size() > 0 ? (AwdataStore) qtMatchEventStatistics
					.get(0) : null;

			if (matchEvents != null) {
				if (matchEvents.a.equals("比赛事件")) {
					jishiBifenDataStoreDao.storeMatchEventData(matchEvents.b);
				} else if (matchEvents.a.equals("赛事技术统计")) {
					jishiBifenDataStoreDao
							.storeMatchStatisticData(matchEvents.b);
				}
			}

			AwdataStore matchStatistics = qtMatchEventStatistics.size() > 1 ? (AwdataStore) qtMatchEventStatistics
					.get(1) : null;

			if (matchStatistics != null && matchStatistics.a.equals("赛事技术统计")) {
				jishiBifenDataStoreDao
						.storeMatchStatisticData(matchStatistics.b);
			}
			log.info("比赛id为{}的比赛事件及数据统计数据抓取完成..",matchBaseInfoPO.getBsId());
		} catch (Throwable e) {
			log.error("抓取足球即时事件出错:", e);
		}finally{
			
			ConnectionPool.getInstance().closePool();
		}
	}

}
