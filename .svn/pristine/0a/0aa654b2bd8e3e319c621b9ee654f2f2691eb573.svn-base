package com.unison.lottery.weibo.data.crawler.service.store.service.impl.mq;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.crawler.service.store.mq.CrawlerQtWeiboDataMessageSender;
import com.unison.lottery.weibo.data.crawler.service.store.service.impl.AbstractJishiDataUseThreadAndSend;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.parse.index.daxiaoModel;
import com.unison.lottery.weibo.dataservice.crawler.parse.index.i;
import com.unison.lottery.weibo.dataservice.crawler.service.CrawlerDataParseService;
import com.unison.lottery.weibo.dataservice.crawler.service.model.OddsBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.mq.CrawlerFbZqCompanyOddsHandle;
import com.xhcms.commons.mq.MessageSender;

/**
 * 抓取竞彩足球欧赔数据并发送activemq异步处理
 * 
 * @author baoxing.peng
 *
 * @since 2015年2月3日下午3:20:17
 */
public class CrawlerZqJingcaiOddsUseThreadAndSendActivemq extends
		AbstractJishiDataUseThreadAndSend implements
		Callable<List<OddsBaseModel>> {

	private QtMatchBaseModel qtMatchBaseModel;
	public Logger log = LoggerFactory.getLogger(getClass());
	CrawlerQtWeiboDataMessageSender messageSender;

	public CrawlerZqJingcaiOddsUseThreadAndSendActivemq(
			QtMatchBaseModel matchBaseModel,
			CrawlerQtWeiboDataMessageSender messageSender) {
		this.qtMatchBaseModel = matchBaseModel;
		this.messageSender = messageSender;
	}

	@Override
	public List<OddsBaseModel> call() throws Exception {
		List<OddsBaseModel> oddsBaseModels = null;
		try {
			int i = 0;
			do {
				oddsBaseModels = jishiBifenParseService
						.crawlerZqJingcaiMatchJishiOdds(header,
								qtMatchBaseModel.getBsId(),
								Qt_fb_match_oddsType.euro);

				i++;
			} while (oddsBaseModels == null && i < 10);
			if (oddsBaseModels != null && oddsBaseModels.size() > 0) {
				for (OddsBaseModel oddsBaseModel : oddsBaseModels) {
					messageSender.send(new CrawlerFbZqCompanyOddsHandle(
							qtMatchBaseModel.getId(),
							oddsBaseModel.getOddsId(), oddsBaseModel
									.getCorpId(), qtMatchBaseModel.getBsId()));
					log.info("发送完毕:{}", oddsBaseModel);
				}
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			log.error("抓取竞彩赛程出错{}", e);
		}
		return oddsBaseModels;
	}

}
