package com.unison.lottery.weibo.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.unison.lottery.weibo.common.redis.RedisCallback;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.common.solrj.SolrJ;
import com.unison.lottery.weibo.common.solrj.SolrJImpl;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.lang.SolrConfig;

/**
 * @Description: 加载微博数据的索引至solr
 * @author haoxiang.jiang@unison.net.cn 
 * @date 2014-2-25 下午5:49:57 
 * @version V1.0
 */
public class LoadData {
	private static Logger logger = LoggerFactory.getLogger(LoadData.class); 
	private static SolrJ solrJ = new SolrJImpl();
	/**
	 * 不明确的情况下，请不要执行此程序,需要请联系作者
	 * @param args
	 */
	public static void main(String[] args) {
		//deleteAll(); //删除所有solr上的索引数据
		//delUserIndex();
		//delWeiboIndex();
		//indexUserInfo();
		//indexWeiboContent();
	}
	
	private static void indexWeiboContent(){
		//RedisTemplate redisTemplate = new RedisTemplate("10.171.13.215", 33133);
		RedisTemplate redisTemplate = new RedisTemplate("182.92.191.193", 22122);
		logger.info("开始！！！");
		
		Set<String> uids = (Set<String>)redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrange("global:timeline", 0, -1);
			}
		});
		for (String tmpId : uids) {
			final String id = tmpId;
			redisTemplate.doExecute(new RedisCallback() {
				public Object doInRedis(Jedis jedis) {
					Map<String, String>  weiboContent = jedis.hgetAll("id:WeiboMsg:" + id);
					if(null != weiboContent && 
							weiboContent.size() > 0 && 
							StringUtils.isNotBlank(weiboContent.get("content")) &&
							!Constant.DeleteContent.USER_DELETED.equals(weiboContent.get("content")) &&
							!"此微博已被作者删除！".equals(weiboContent.get("content"))){
						String id = weiboContent.get("id");
						String title = weiboContent.get("title");
						String content = weiboContent.get("content");
						String createTime = weiboContent.get("createTime");
						String weiboType = weiboContent.get("type");
						saveWeiboIndex(id, title, content,createTime,weiboType);
						logger.info("保存微博索引：id={},weiboType={},content={}", new Object[]{
							id,weiboType,content});
					} 
					return null;
				}
			});
		}
		logger.info("结束！！！");
	}
	
	private static void saveWeiboIndex(String id,String title,
			String content,String createTime,String weiboType){
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "id:WeiboMsg:" + id );
		if(StringUtils.isNotBlank(title)){
			doc1.addField("title", title);
		}
	    doc1.addField("weiboContent", content);
	    doc1.addField("createTime", createTime);
	    doc1.addField("contentType", Constant.SolrConfig.SEARCH_TYPE_WEIBO);
	    doc1.addField("weiboType", weiboType == null ? '0' : weiboType);
	    Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
	    docs.add( doc1 );
		try {
			solrJ.add(docs);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void indexUserInfo(){
		//RedisTemplate redisTemplate = new RedisTemplate("10.97.0.107", 33133);
		RedisTemplate redisTemplate = new RedisTemplate("182.92.191.193", 22122);
		logger.info("开始！！！");
		
		Set<String> uids = (Set<String>)redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				return jedis.zrange("weiboUsers", 0, -1);
			}
		});
		for (String tmpId : uids) {
			final String id = tmpId;
			redisTemplate.doExecute(new RedisCallback() {
				public Object doInRedis(Jedis jedis) {
					
					Map<String, String>  user = jedis.hgetAll("user:" + id);
					if(null != user && user.size() > 0){
						String id = user.get("weiboUserId");
						String nickname = user.get("nickName");
						saveUserIndex(id, nickname);
						logger.info("保存用户索引：id={},nickname={}",id,nickname);
					} 
					return null;
				}
			});
		}
		logger.info("结束！！！");
	}
	private static void saveUserIndex(String id,String nickName){
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "user:" + id );
	    doc1.addField("name", nickName);
	    doc1.addField("contentType", Constant.SolrConfig.SEARCH_TYPE_USER);
	    Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
	    docs.add( doc1 );
		try {
			solrJ.add(docs);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteAll() {
		//HttpSolrServer server = new HttpSolrServer("http://localhost:9090/solr/collection1/");
		HttpSolrServer server = new HttpSolrServer(SolrConfig.getSolrUrl());
		server.setMaxRetries(1);
		server.setConnectionTimeout(10000);
		server.setParser(new XMLResponseParser());
		server.setSoTimeout(1000);
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false);
		server.setAllowCompression(true);
		try {
			server.deleteByQuery("*:*");
			server.commit();
			System.out.println("成功删除solr上的所有数据，程序退出！");
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void delWeiboIndex(){
		deleteByQuery("contentType:weiboType");
		System.out.println("删除微博索引完毕");
	}
	public static void delUserIndex(){
		deleteByQuery("contentType:userType");
		System.out.println("删除用户索引完毕");
	}
	
	public static void deleteByQuery(String query){
		HttpSolrServer server = new HttpSolrServer(SolrConfig.getSolrUrl());
		server.setMaxRetries(1);
		server.setConnectionTimeout(10000);
		server.setParser(new XMLResponseParser());
		server.setSoTimeout(1000);
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false);
		server.setAllowCompression(true);
		try {
			server.deleteByQuery(query);
			server.commit();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
