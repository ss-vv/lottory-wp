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
public class ZqJishiEventUseThreadAndSend extends AbstractJishiDataUseThread implements Runnable{
	
	private FbMatchBaseInfoPO matchBaseInfoPO;
	private Logger log = LoggerFactory
			.getLogger(ZqJishiEventUseThreadAndSend.class);

	public ZqJishiEventUseThreadAndSend(FbMatchBaseInfoPO matchBaseInfoPO) throws SQLException {
		super();
		this.matchBaseInfoPO = matchBaseInfoPO;
	}

	@SuppressWarnings("unchecked")
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

			jishiBifenParseService.sendZqJishiEvent(qtMatchEventStatistics);
			log.info("比赛id为{}的比赛事件及数据统计数据抓取完成..",matchBaseInfoPO.getBsId());
		} catch (Throwable e) {
			log.error("抓取足球即时事件出错:", e);
		}
	}

}
