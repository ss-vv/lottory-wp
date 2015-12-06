package com.unison.lottery.newsextract.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.newsextract.dao.NewsDao;
import com.unison.lottery.newsextract.data.ExtractNews;
import com.unison.lottery.newsextract.lang.NewsType;
import com.unison.lottery.newsextract.parse.NewsParser;
import com.xhcms.commons.job.Job;

/**
 * @Description: 索引足球赛事 
 * @author 江浩翔   
 * @date 2014-1-21 下午1:56:15 
 * @version V1.0
 */
public class ExtractHupuNewsTask extends Job {
	private static final Logger logger = LoggerFactory
			.getLogger(ExtractHupuNewsTask.class);
	
	public ExtractHupuNewsTask() {
		logger.debug("execute task : 抽取红色标题新闻...");
	}
	
	@Autowired
	private NewsParser hupuSoccerFocusTextDivParser;
	@Autowired
	private NewsParser hupuBasketballFocusTextDivParser;
	@Autowired
	private NewsDao newsDaoImpl;
	
	@Override
	protected void execute() {
		List<ExtractNews> extractNewsList = hupuSoccerFocusTextDivParser.parse();
		List<ExtractNews> extractNewsList2 = hupuBasketballFocusTextDivParser.parse();
		newsDaoImpl.saveNews(extractNewsList);
		newsDaoImpl.saveNews(extractNewsList2);
		List<ExtractNews> es = newsDaoImpl.getUnpublishNews(NewsType.FootBall);
		List<ExtractNews> es2 = newsDaoImpl.getUnpublishNews(NewsType.BasketBall);
		for (ExtractNews extractNews : es) {
			logger.info(extractNews.getUrl());
			logger.info(extractNews.getTitle());
			logger.info(extractNews.getContent());
			logger.info(extractNews.getSource());
			logger.info(extractNews.getNewsType());
			logger.info("-------------------------------------");
		}
		for (ExtractNews extractNews : es2) {
			logger.info(extractNews.getUrl());
			logger.info(extractNews.getTitle());
			logger.info(extractNews.getContent());
			logger.info(extractNews.getSource());
			logger.info(extractNews.getNewsType());
			logger.info("-------------------------------------");
		}
	}
}
