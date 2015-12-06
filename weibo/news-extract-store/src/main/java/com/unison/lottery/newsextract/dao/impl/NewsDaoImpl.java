package com.unison.lottery.newsextract.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.unison.lottery.newsextract.dao.NewsDao;
import com.unison.lottery.newsextract.data.ExtractNews;
import com.unison.lottery.newsextract.lang.NewsType;
import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.PageRequest;

@Service
public class NewsDaoImpl extends BaseDaoImpl<ExtractNews> implements NewsDao{

	@Override
	public void saveNews(List<ExtractNews> extractNewsList) {
		for (ExtractNews extractNews : extractNewsList) {
			if(null == extractNews || StringUtils.isBlank(extractNews.getContent())){
				continue;	
			} else {
				this.saveNews(extractNews);
			}
			
		}
	}

	@Override
	public void saveNews(ExtractNews extractNews){
		String newsId = Keys.getNewsContentKey(extractNews.getUrl());
		extractNews.setId(newsId);
		String unPublishId = Keys.getUnpublishNewsKey(extractNews.getNewsType());
		String publishedId = Keys.getPublishedNewsKey(extractNews.getNewsType());
		if(null == zrank(unPublishId, newsId) && null == zrank(publishedId, newsId)){
			hashAdd(newsId, extractNews);
			zadd(new Date().getTime(), unPublishId, newsId);
		}
	}
	
	@Override
	public List<ExtractNews> getUnpublishNews(NewsType newsType,
			PageRequest pageRequest) {
		LinkedHashSet<String> newsIdList = zrevrange(Keys.getUnpublishNewsKey(newsType.name()), pageRequest.getOffset(), 
				pageRequest.getOffset() + pageRequest.getPageSize() - 1);
		pageRequest.setRecordCount(zcard(Keys.getUnpublishNewsKey(newsType.name())).intValue());
		return getExtractNewslListByIds(newsIdList);
	}
	@Override
	public List<ExtractNews> getUnpublishNews(NewsType newsType) {
		LinkedHashSet<String> newsIdList = zrevrange(Keys.getUnpublishNewsKey(newsType.name()), 0, -1);
		return getExtractNewslListByIds(newsIdList);
	}
	
	private List<ExtractNews> getExtractNewslListByIds(LinkedHashSet<String> newsIdList){
		List<ExtractNews> extractNewsList = new ArrayList<ExtractNews>();
		for (String newsId : newsIdList) {
			ExtractNews e = hashGet(newsId);
			if(null != e){
				extractNewsList.add(e);	
			}
		}
		return extractNewsList;
	}
	
	@Override
	public void moveUnpublishToPublish(ExtractNews extractNews) {
		String newsId = extractNews.getId();
		zrem(Keys.getUnpublishNewsKey(extractNews.getNewsType()), newsId);
		zadd(new Date().getTime(), Keys.getPublishedNewsKey(extractNews.getNewsType()), newsId);
	}

	@Override
	public void moveUnpublishToPublish(List<ExtractNews> extractNewsList) {
		for (ExtractNews extractNews : extractNewsList) {
			this.moveUnpublishToPublish(extractNews);
		}
	}

	@Override
	public boolean updateExtractNews(ExtractNews extractNews) {
		if(null != extractNews.getId()){
			String newsId = extractNews.getId(); 
			String unPublishId = Keys.getUnpublishNewsKey(extractNews.getNewsType());
			hashAdd(newsId, extractNews);
			Double score = zscore(unPublishId,newsId);
			if(null != score){
				zadd(score, unPublishId, newsId);
			} else {
				zadd(new Date().getTime(), unPublishId, newsId);
			}
			return true;
		} else {
			return false;
		}
	}
}
