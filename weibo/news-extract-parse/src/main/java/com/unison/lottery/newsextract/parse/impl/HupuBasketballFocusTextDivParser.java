package com.unison.lottery.newsextract.parse.impl;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.unison.lottery.newsextract.data.ExtractNews;
import com.unison.lottery.newsextract.lang.NewsType;
import com.unison.lottery.newsextract.parse.NewsParser;
import com.unison.lottery.newsextract.util.Crawler;

@Service
public class HupuBasketballFocusTextDivParser implements NewsParser{
	private static final Logger logger = LoggerFactory
			.getLogger(HupuBasketballFocusTextDivParser.class);
	
	@Override
	public List<ExtractNews> parse() {
		String baseUrl = "http://nba.hupu.com/";
		logger.info("开始抽取虎扑篮球新闻:baseUrl={}",baseUrl);
		Crawler crawler = new Crawler(baseUrl);
		crawler.addSelector(".focusText,.text");
		crawler.addSelector("a[href]");
		try {
			Elements elements = crawler.getElementsWithCache();
			return parseWithElements(elements);
		} catch (Exception e) {
			if(e instanceof SocketTimeoutException){
				logger.error("抽取虎扑篮球新闻url={} 失败,网络连接超时！！！",baseUrl,e);
			} else {
				logger.error("抽取虎扑篮球新闻url={} 失败！！！",baseUrl,e);
			}
			return null;
		}
	}
	private List<ExtractNews> parseWithElements(Elements elements) {
		List<ExtractNews> extractNewsList = new ArrayList<ExtractNews>();
		for (int i = 0; i < elements.size(); i++) {
			Element e = elements.get(i);
			String url = e.attr("abs:href");
			ExtractNews extractNews = new ExtractNews();
			extractNews.setUrl(url);
			extractNews.setNewsType(NewsType.BasketBall.name());
			extractNewsList.add(extractNews);
		}
		setContent(extractNewsList);
		return extractNewsList;
	}
	
	private void setContent(List<ExtractNews> extractNewsList){
		for (ExtractNews extractNews : extractNewsList) {
			String url = extractNews.getUrl();
			logger.info("开始抽取新闻:url={}",url);
			if(url.indexOf("bbs.") != -1){
				continue;
			}
			try {
				if(url.indexOf("topic") != -1){
					setTopicContent(extractNews);
				} else {
					setNormalContent(extractNews);
				}
			} catch (Exception e) {
				if(e instanceof SocketTimeoutException){
					logger.error("抽取新闻url={} 失败,网络连接超时！！！",extractNews.getUrl(),e);
				} else {
					logger.error("抽取新闻url={} 失败！！！",extractNews.getUrl(),e);
				}
				continue;
			}
		}
	}
	
	private void setTopicContent(ExtractNews extractNews) throws Exception{
		Crawler crawler = new Crawler(extractNews.getUrl());
		crawler.addSelector(".topic-title");
		crawler.addSelector(".content");
		Elements elements;
		elements = crawler.getElementsWithCache();
		extractNews.setContent(elements.html().trim());
		
		crawler.delSelector();
		crawler.addSelector(".topic-title");
		crawler.addSelector(".name");
		elements = crawler.getElementsWithCache();
		extractNews.setTitle(elements.text().trim());
	}
	private void setNormalContent(ExtractNews extractNews) throws Exception{
		Crawler crawler = new Crawler(extractNews.getUrl());
		crawler.addSelector(".headline");
		Elements elements = crawler.getElementsWithCache();
		extractNews.setTitle(elements.text().trim());
		
		crawler.delSelector();
		crawler.addSelector(".artical-main-content");
		elements = crawler.getElementsWithCache();
		Elements lotteryA = elements.select("a[href]");
		if(lotteryA.size() > 0){
			Elements lotteryP =  elements.select("p");
			lotteryP.remove(lotteryP.size() - 1);
			StringBuilder content = new StringBuilder();
			for (Element element : lotteryP) {
				content .append("<p>" + element.text() + "</p>");
			}
			content.append("<p recommend_info='recommend_info'>" + lotteryA.text() + "</p>");
			extractNews.setContent(content.toString());
		} else {
			extractNews.setContent(elements.html().trim());
		}
		crawler.delSelector();
		crawler.addSelector(".comeFrom");
		elements = crawler.getElementsWithCache();
		extractNews.setSource(elements.html());
	}
}
