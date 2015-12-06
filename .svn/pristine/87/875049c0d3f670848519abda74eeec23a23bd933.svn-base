package com.unison.lottery.weibo.web.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.solrj.SolrJ;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.vo.PromptMatchVO;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SolrJ solrJ;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private MessageService messageService;
	
	@Override
	public List<PromptMatchVO> querryPromptMatch(String keyString,
			PageRequest pageRequest) {
		if(StringUtils.isBlank(keyString) || null == pageRequest){
			return new ArrayList<PromptMatchVO>();
		}
		ArrayList<PromptMatchVO> promptMatchVOs = new ArrayList<PromptMatchVO>();
		int offset = pageRequest.getOffset();
		int pageSize = pageRequest.getPageSize();
		try {
			SolrDocumentList list = solrJ.queryAllMatchByPage(keyString, offset, pageSize);
			pageRequest.setRecordCount((int)list.getNumFound());
			for (SolrDocument solrDocument : list) {
				PromptMatchVO p = makePromptMatchVO(solrDocument);
				if(null != p){
					long d1 = p.getEntrustDeadline().getTime();
					long d2 = new Date().getTime();
					if(d1 > d2){
						p.setSale(true);
					} else {
						p.setSale(false);
					}
					promptMatchVOs.add(p);
				}
			}
			return promptMatchVOs;
		} catch (SolrServerException e) {
			logger.error("solr查询出错,{}", e);
			return new ArrayList<PromptMatchVO>();
		} catch (ParseException e) {
			logger.error("entrustDeadline 转换错误,{}", e);
			return new ArrayList<PromptMatchVO>();
		}
	}
	
	//SolrDocument to PromptMatchVO
	private PromptMatchVO makePromptMatchVO(SolrDocument solrDocument) throws ParseException{
		PromptMatchVO p = new PromptMatchVO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Object id_ = solrDocument.getFieldValue(Constant.SolrConfig.SOLR_ID);
		Object lotteryType_ = solrDocument.getFieldValue("lotteryType");
		if(null != id_ && null != lotteryType_){
			String id = (String)id_;
			String lotteryType = (String)lotteryType_;
			String matchName = solrDocument.getFieldValue("matchName")==null ? null:(String)solrDocument.getFieldValue("matchName");
			String matchCode = solrDocument.getFieldValue("matchCode")==null ? null:(String)solrDocument.getFieldValue("matchCode");
			String matchID = solrDocument.getFieldValue("matchID")==null ? null:(String)solrDocument.getFieldValue("matchID");
			Float score = solrDocument.getFieldValue("score")==null ? null:(Float)solrDocument.getFieldValue("score");
			Date entrustDeadline = solrDocument.getFieldValue("entrustDeadline")==null ? null: sdf.parse((String)solrDocument.getFieldValue("entrustDeadline"));
			Date matchTime = solrDocument.getFieldValue("matchTime")==null ? null: new Date((Long)solrDocument.getFieldValue("matchTime"));
			
			if(Constant.SolrConfig.SEARCH_TYPE_JCZQ_MATCH.equals(lotteryType)){
				id = id.replace("jczq:", "");
			} else if(Constant.SolrConfig.SEARCH_TYPE_JCLQ_MATCH.equals(lotteryType)){
				id = id.replace("jclq:", "");
			} else if(Constant.SolrConfig.SEARCH_TYPE_CTZC_MATCH.equals(lotteryType)){
				id = id.replace("ctzc:", "");
			} else if(Constant.SolrConfig.SEARCH_TYPE_BJDC_MATCH.equals(lotteryType)){
				id = id.replace("bjdc:", "");
			}
			p.setEntrustDeadline(entrustDeadline);
			p.setId(id);
			p.setLotteryType(lotteryType);
			p.setMatchCode(matchCode);
			p.setMatchName(matchName);
			p.setMatchId(matchID);
			p.setScore(score);
			p.setMatchTime(matchTime);
		}
		return null == p ? null:p;
	}
	@Override
	public List<WeiboUser> querryUser(String keyString,PageRequest pageRequest) {
		if(StringUtils.isBlank(keyString)) {
			return new ArrayList<WeiboUser>();
		}
		
		Set<String> userIds = querryUserId(keyString,pageRequest);
		String[] uids= new String[userIds.size()];
		uids = userIds.toArray(uids);
		return userAccountService.findWeiboUserByWeiboUids(uids);
	}
	
	/**
	 * 返回weiboUserId 
	 * @return
	 */
	private Set<String> querryUserId(String keyString,PageRequest pageRequest){
		Set<String> userIdSet = new HashSet<String>();
		String[] keys = keyString.split(" ");
		int offset = pageRequest.getOffset();
		int pageSize = pageRequest.getPageSize();
		for (int i = 0; i < keys.length; i++) {
			String[] tmpKeys = {keys[i]};
			if(StringUtils.isBlank(tmpKeys[0])) {
				continue;
			} else {
				try {
					SolrDocumentList list = solrJ.querryUserByPage(tmpKeys, offset, pageSize);
					pageRequest.setRecordCount((int)list.getNumFound());
					for (SolrDocument solrDocument : list) {
						Object o = solrDocument.getFieldValue(Constant.SolrConfig.SOLR_ID);
						if(null != o){
							userIdSet.add(((String)o).replaceAll("user:", ""));
						}
					}
				} catch (SolrServerException e) {
					logger.error("solr查询出错,{}", e);
					return userIdSet;
				}
			}
		}
		return userIdSet;
	}

	@Override
	public List<WeiboMsgVO> querryWeiboMsg(String keyString,
			PageRequest pageRequest,String weiboType) {
		if(StringUtils.isBlank(keyString)) {
			return new ArrayList<WeiboMsgVO>();
		}
		Set<String> weiboMsgIds = querryWeiboMsgId(keyString,pageRequest,weiboType);
		List<WeiboMsgVO> weiboMsgs = new ArrayList<WeiboMsgVO>();
		for (String weiboMsgId : weiboMsgIds) {
			try {
				WeiboMsgVO msgVo = messageService.getWeiboVoById(Long.parseLong(weiboMsgId));
				if(null != msgVo){
					weiboMsgs.add(msgVo);
				}
			} catch (NumberFormatException e) {
				logger.error("转换weiboMsgId='{}' to Long ERROR",weiboMsgId,e);
			} catch (Exception e) {
				logger.error("搜索微博内容错误,weiboMsgId='{}'",weiboMsgId, e);
			}
		}
		return weiboMsgs;
	}
	
	private Set<String> querryWeiboMsgId(String keyString,
			PageRequest pageRequest,String weiboType){
		Set<String> weiboMsgIdSet = new HashSet<String>();
		String[] keys = keyString.split(" ");
		int offset = pageRequest.getOffset();
		int pageSize = pageRequest.getPageSize();
		for (int i = 0; i < keys.length; i++) {
			String[] tmpKeys = {keys[i]};
			if(StringUtils.isBlank(tmpKeys[0])) {
				continue;
			} else {
				try {
					SolrDocumentList list = solrJ.querryWeiboContentByPage(tmpKeys, offset, pageSize,weiboType);
					pageRequest.setRecordCount((int)list.getNumFound());
					for (SolrDocument solrDocument : list) {
						Object o = solrDocument.getFieldValue(Constant.SolrConfig.SOLR_ID);
						if(null != o){
							weiboMsgIdSet.add(((String)o).replaceAll("id:WeiboMsg:", ""));
						}
					}
				} catch (SolrServerException e) {
					logger.error("solr查询出错,{}", e);
					return weiboMsgIdSet;
				}
			}
		}
		return weiboMsgIdSet;
	}
	
	@Override
	public boolean indexUser(WeiboUser weiboUser) {
		if(null == weiboUser){
			return false;
		}
		String id = weiboUser.getWeiboUserId() + "";
		String nickname = weiboUser.getNickName();
		if(StringUtils.isBlank(id) || StringUtils.isBlank(nickname)){
			return false;
		}
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "user:" + id );
	    doc1.addField("name", nickname);
	    doc1.addField("contentType", Constant.SolrConfig.SEARCH_TYPE_USER);
	    Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
	    docs.add( doc1 );
		try {
			solrJ.add(docs);
			return true;
		} catch (SolrServerException e) {
			logger.error("建立用户索引失败",e);
			return false;
		} catch (IOException e) {
			logger.error("建立用户索引失败",e);
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean indexWeiboContent(WeiboMsg weiboMsg) {
		if(null == weiboMsg){
			return false;
		}
		String id = weiboMsg.getId() + "";
		String title = weiboMsg.getTitle();
		String content = weiboMsg.getContent();
		if(StringUtils.isBlank(id) || StringUtils.isBlank(content)){
			return false;
		}
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "id:WeiboMsg:" + id );
		if(StringUtils.isNotBlank(title)){
			doc1.addField("title", title);
		}
	    doc1.addField("weiboContent", content);
	    doc1.addField("weiboType", weiboMsg.getType() == null ? '0' : weiboMsg.getType());
	    doc1.addField("contentType", Constant.SolrConfig.SEARCH_TYPE_WEIBO);
	    Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
	    docs.add( doc1 );
		try {
			solrJ.add(docs);
			return true;
		} catch (SolrServerException e) {
			logger.error("建立微博内容索引失败",e);
			return false;
		} catch (IOException e) {
			logger.error("建立微博内容索引失败",e);
			e.printStackTrace();
			return false;
		}
		
	}

}
