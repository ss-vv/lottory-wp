package com.unison.lottery.weibo.common.solrj;

import java.io.IOException;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public interface SolrJ {
	void add(Collection<SolrInputDocument> docs) throws SolrServerException, IOException;
	SolrDocumentList querryUser(String[] keys) throws SolrServerException;
	SolrDocumentList querryWeiboContent(String[] keys) throws SolrServerException;
	/**
	 * 分页查询
	 * @param keys
	 * @param offset 开始位置
	 * @param pageSize 页大小
	 * @return
	 * @throws SolrServerException
	 */
	SolrDocumentList querryUserByPage(String[] keys, int offset,int pageSize) throws SolrServerException;
	SolrDocumentList querryWeiboContentByPage(String[] keys, int offset,
			int pageSize,String weiboType) throws SolrServerException;
	
	/**
	 * 查询竞彩足球赛事
	 * @param key
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws SolrServerException
	 */
	SolrDocumentList queryJCZQMatchByPage(String key, int offset,int pageSize) throws SolrServerException;
	/**
	 * 查询竞彩篮球赛事
	 * @param key
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws SolrServerException
	 */
	SolrDocumentList queryJCLQMatchByPage(String key, int offset,int pageSize) throws SolrServerException;
	/**
	 * 查询传统足彩赛事
	 * @param key
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws SolrServerException
	 */
	SolrDocumentList queryCTZCMatchByPage(String key, int offset,int pageSize) throws SolrServerException;
	
	/**
	 * 查询全部赛事
	 * @param key
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws SolrServerException
	 */
	SolrDocumentList queryAllMatchByPage(String key, int offset,int pageSize) throws SolrServerException;
	
	void updateIndex(Collection<SolrInputDocument> docs) throws SolrServerException, IOException ;
	void deleteIndexById(String id);
}
