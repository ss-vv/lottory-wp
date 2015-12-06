package com.unison.lottery.newsextract.dao;

import java.util.List;

import com.unison.lottery.newsextract.data.ExtractNews;
import com.unison.lottery.newsextract.lang.NewsType;
import com.unison.lottery.weibo.data.PageRequest;

public interface NewsDao {
	void saveNews(List<ExtractNews> extractNewsList);
	void saveNews(ExtractNews extractNews);
	
	List<ExtractNews> getUnpublishNews(NewsType newsType);
	List<ExtractNews> getUnpublishNews(NewsType newsType,PageRequest pageRequest);
	
	/**
	 * 从未发布列表中删除指定的新闻id；并添加指定的新闻id至已发布列表
	 * @param extractNews
	 */
	void moveUnpublishToPublish(ExtractNews extractNews);
	void moveUnpublishToPublish(List<ExtractNews> extractNewsList);
	
	boolean updateExtractNews(ExtractNews extractNews);
}
