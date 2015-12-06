package com.unison.lottery.weibo.common.solrj;

import java.io.IOException;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.lang.SolrConfig;

@Service
public class SolrJImpl implements SolrJ {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String solrServerUrl = SolrConfig.getSolrUrl();
	private HttpSolrServer getSolrServer(){
		HttpSolrServer server = new HttpSolrServer(solrServerUrl);
		server.setMaxRetries(1);
		server.setConnectionTimeout(10000);
		server.setParser(new XMLResponseParser());
		server.setSoTimeout(10000);
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false);
		server.setAllowCompression(true);
		return server;
	}
	@Override
	public void add(Collection<SolrInputDocument> docs) throws SolrServerException, IOException {
		HttpSolrServer server = getSolrServer();
		server.add(docs);
		server.commit();
	}
	@Override
	public SolrDocumentList querryUser(String[] keys) throws SolrServerException {
		HttpSolrServer server = getSolrServer();
		SolrQuery query = new SolrQuery();
	    query.setQuery(ClientUtils.escapeQueryChars(keys[0]));
	    query.setFilterQueries("contentType:" + Constant.SolrConfig.SEARCH_TYPE_USER);
	    QueryResponse rsp = server.query(query);
		return rsp.getResults();
	}
	
	@Override
	public SolrDocumentList querryWeiboContent(String[] keys) throws SolrServerException {
		HttpSolrServer server = getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(ClientUtils.escapeQueryChars(keys[0]));
		query.setFilterQueries("contentType:" + Constant.SolrConfig.SEARCH_TYPE_WEIBO);
		query.setParam("df", "weiboContent");
		QueryResponse rsp = server.query(query);
		return rsp.getResults();
	}
	@Override
	public SolrDocumentList querryUserByPage(String[] keys, int offset,
			int pageSize) throws SolrServerException {
		HttpSolrServer server = getSolrServer();
		SolrQuery query = new SolrQuery();
	    query.setQuery(ClientUtils.escapeQueryChars(keys[0]));
	    query.setFilterQueries("contentType:" + Constant.SolrConfig.SEARCH_TYPE_USER);
	    query.setStart(offset);
	    query.setRows(pageSize);
	    QueryResponse rsp = server.query(query);
		return rsp.getResults();
	}
	@Override
	public SolrDocumentList querryWeiboContentByPage(String[] keys, int offset,
			int pageSize,String weiboType) throws SolrServerException {
		HttpSolrServer server = getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setQuery(ClientUtils.escapeQueryChars(keys[0]));
		query.setFilterQueries("weiboType:" + (null == weiboType? '0':weiboType ));
		query.setStart(offset);
		query.setRows(pageSize);
		query.setSort("createTime", SolrQuery.ORDER.desc);
		query.setParam("df", "weiboContent");//Specifies a default field, overriding the definition of a default field in the schema.xml file.
		QueryResponse rsp = server.query(query);
		return rsp.getResults();
	}
	@Override
	public void updateIndex(Collection<SolrInputDocument> docs) throws SolrServerException, IOException {
		this.add(docs);
	}
	
	@Override
	public SolrDocumentList queryJCZQMatchByPage(String key, int offset,
			int pageSize) throws SolrServerException {
		return queryMatch(key, offset, pageSize, Constant.SolrConfig.SEARCH_TYPE_JCZQ_MATCH);
	}
	@Override
	public SolrDocumentList queryJCLQMatchByPage(String key, int offset,
			int pageSize) throws SolrServerException {
		return queryMatch(key, offset, pageSize, Constant.SolrConfig.SEARCH_TYPE_JCLQ_MATCH);
	}
	@Override
	public SolrDocumentList queryCTZCMatchByPage(String key, int offset,
			int pageSize) throws SolrServerException {
		return queryMatch(key, offset, pageSize, Constant.SolrConfig.SEARCH_TYPE_CTZC_MATCH);
	}
	
	private SolrDocumentList queryMatch(String key, int offset,
			int pageSize,String lotteryType) throws SolrServerException{
		HttpSolrServer server = getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setFields("*","score");
		query.setQuery(key);
		query.setFilterQueries("lotteryType:" + lotteryType);
		query.setStart(offset);
		query.setRows(pageSize);
		SortClause score = new SortClause("score", SolrQuery.ORDER.desc);
		SortClause createTime = new SortClause("createTime", SolrQuery.ORDER.desc); 
		query.addSort(score);
		query.addSort(createTime);
		QueryResponse rsp = server.query(query);
		return rsp.getResults();
	}
	
	@Override
	public SolrDocumentList queryAllMatchByPage(String key, int offset,
			int pageSize) throws SolrServerException {
		HttpSolrServer server = getSolrServer();
		SolrQuery query = new SolrQuery();
		query.setFields("*","score");
		query.setQuery(key);
		query.setFilterQueries(
				"lotteryType:(" + Constant.SolrConfig.SEARCH_TYPE_CTZC_MATCH + " OR " + 
								  Constant.SolrConfig.SEARCH_TYPE_JCZQ_MATCH + " OR " +
								  Constant.SolrConfig.SEARCH_TYPE_BJDC_MATCH + " OR " +
								  Constant.SolrConfig.SEARCH_TYPE_JCLQ_MATCH + ")");
		query.setStart(offset);
		query.setRows(pageSize);
		SortClause score = new SortClause("score", SolrQuery.ORDER.desc);
		SortClause createTime = new SortClause("createTime", SolrQuery.ORDER.desc); 
		query.addSort(score);
		query.addSort(createTime);
		QueryResponse rsp = server.query(query);
		return rsp.getResults();
	}
	@Override
	public void deleteIndexById(String id) {
		HttpSolrServer server = getSolrServer();
		try {
			server.deleteById(id);
			server.commit();
			logger.info("solr删除索引成功！id=" + id);
		} catch (SolrServerException | IOException e) {
			logger.error("solr删除索引失败！id={}" + id,e);
		}
	}
}
