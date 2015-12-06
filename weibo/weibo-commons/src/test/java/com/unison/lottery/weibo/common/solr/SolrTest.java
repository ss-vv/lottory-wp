package com.unison.lottery.weibo.common.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.unison.lottery.weibo.common.solrj.SolrJ;
import com.unison.lottery.weibo.common.solrj.SolrJImpl;

/**
 * @Description: 
 * @author haoxiang.jiang@unison.net.cn 
 * @date 2014-2-25 下午5:47:17 
 * @version V1.0
 */
public class SolrTest {

	/**
	 * 不明确的情况下，请不要打开下列方法并执行,需要请联系作者
	 * @param args
	 */
	public static void main(String[] args) {
		//addTestData();
		//deleteAll();
		//querryUserData();
		//querryWeiboContentData();
		//deleteById("user:14");
		//deleteByQuery("lotteryType:CTZC");
	}
	
	private static void deleteByQuery(String filter) {
		HttpSolrServer server = new HttpSolrServer("http://58.83.235.132:28080/solr/collection1/");
		server.setMaxRetries(1);
		server.setConnectionTimeout(10000);
		server.setParser(new XMLResponseParser());
		server.setSoTimeout(1000);
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false);
		server.setAllowCompression(true);
		try {
			server.deleteByQuery(filter);
			server.commit();
			System.out.println("删除成功！");
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteById(String id){
		//HttpSolrServer server = new HttpSolrServer("http://localhost/solr/collection1/");
		HttpSolrServer server = new HttpSolrServer("http://58.83.235.132:28080/solr/collection1/");
		server.setMaxRetries(1);
		server.setConnectionTimeout(10000);
		server.setParser(new XMLResponseParser());
		server.setSoTimeout(1000);
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false);
		server.setAllowCompression(true);
		try {
			server.deleteById(id);
			server.commit();
			System.out.println("删除成功！id=" + id);
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}
	}
	public static void querryUserData() {
		SolrJ solrJ = new SolrJImpl();
		String[] keys = { "翔" };
		try {
			SolrDocumentList list = solrJ.querryUser(keys);
			for (SolrDocument solrDocument : list) {
				System.out.println("=======================");
				for (String name : solrDocument.getFieldNames()) {
					System.out.println(name + "---"
							+ solrDocument.getFieldValue(name));
				}
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	public static void querryWeiboContentData() {
		SolrJ solrJ = new SolrJImpl();
		String[] keys = { "翔" };
		try {
			SolrDocumentList list = solrJ.querryWeiboContent(keys);
			for (SolrDocument solrDocument : list) {
				System.out.println("=======================");
				for (String name : solrDocument.getFieldNames()) {
					System.out.println(name + "---"
							+ solrDocument.getFieldValue(name));
				}
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	public static void addTestData() {
		SolrJ solrJ = new SolrJImpl();
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "123123");
		doc1.addField("cat", "江浩翔111123");
		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "345123");
		doc2.addField("cat", "江浩翔111123");
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		docs.add(doc1);
		docs.add(doc2);
		try {
			solrJ.add(docs);
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] keys = { "浩" };
		try {
			SolrDocumentList list = solrJ.querryUser(keys);
			for (SolrDocument solrDocument : list) {
				System.out.println("=======================");
				for (String name : solrDocument.getFieldNames()) {
					System.out.println(name + "---"
							+ solrDocument.getFieldValue(name));
				}
			}

		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}

	public static void deleteAll() {
		HttpSolrServer server = new HttpSolrServer(
				"http://localhost:9090/solr/collection1/");
		// HttpSolrServer server = new
		// HttpSolrServer("http://58.83.235.132:28080/solr/collection1/");
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
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
