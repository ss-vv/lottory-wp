package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.ConnectionPool;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;

/**
 * 竞彩篮球即时球队数据统计
 * @author 彭保星
 *
 * @since 2014年12月16日下午12:31:37
 */
public class LqJishiTeamStatisticUseThreadAndSend extends AbstractJishiDataUseThreadAndSend
		implements Runnable {
	private BasketBallMatchModel matchBaseInfoPO;
	private Logger log = LoggerFactory
			.getLogger(ZqJishiEventUseThread.class);

	public LqJishiTeamStatisticUseThreadAndSend(BasketBallMatchModel matchModel) throws SQLException {
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
					makeHeaderFromFile();
				}
				qtMatchEventStatistics = jishiBifenParseService
						.crawlerBasketMatchTeamStatisticData(header,
								matchBaseInfoPO.getBsId(),
								matchBaseInfoPO.getId());
			} while (qtMatchEventStatistics == null&&(++i )<MAX_CRAWLER_COUNT);
			jishiBifenParseService
					.sendMatchTeamStatisticData(qtMatchEventStatistics);
		} catch (Throwable e) {
			log.error("抓取篮球竞彩比赛id为{}即时球队统计出错:{}", matchBaseInfoPO.getBsId(), e);
		}
		finally{
			ConnectionPool.getInstance().closePool();
		}
		log.info("抓取篮球竞彩比赛id为{}即时球队统计完成",matchBaseInfoPO.getBsId());
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
			log.debug("user_agent",agent.length);
			header.put("User-Agent", user_agent);
		}
	}

}
