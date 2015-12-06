package com.unison.lottery.weibo.common.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.service.IndexMatchService;
import com.unison.lottery.weibo.common.solrj.SolrJ;
import com.unison.lottery.weibo.lang.Constant;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.lang.PlayType;

@Service
public class IndexMatchServiceImpl implements IndexMatchService {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required=false)
	private FBMatchDao fbMatchDao;
	@Autowired(required=false)
	private CTFBMatchDao ctfbMatchDao;
	@Autowired(required=false)
	private BBMatchDao bbMatchDao;
	@Autowired
	private SolrJ solrJ;
	@Autowired
	private BJDCMatchDao bjdcMatchDao;
	private Date getFrom(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		//c.add(Calendar.YEAR, -3); //前3年
		c.add(Calendar.DATE,-1); //前1天
		return c.getTime();
	}
	private Date getTo(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE,8); //未来7天之内
 		return c.getTime();
	}
	
	@Override
	@Transactional
	public void buildJCZQMatchIndex() {
		Paging paging = new Paging(1, 3000);
		Date from = getFrom();
		Date to = getTo();
		logger.info("开始更新竞彩足球赛事索引 ,时间:from={},to={}",from,to);
		List<FBMatchPO> fbMatchPOs = fbMatchDao.find(paging, -1, from, to);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (FBMatchPO fbMatchPO : fbMatchPOs) {
			SolrInputDocument doc1 = new SolrInputDocument();
			doc1.addField("id", "jczq:" + fbMatchPO.getId());
			doc1.addField("matchName", fbMatchPO.getName());
			doc1.addField("matchCode", fbMatchPO.getCnCode());
			doc1.addField("entrustDeadline",
					sdf.format(fbMatchPO.getEntrustDeadline()));
			doc1.addField("lotteryType", Constant.SolrConfig.SEARCH_TYPE_JCZQ_MATCH);
			doc1.addField("createTime", fbMatchPO.getEntrustDeadline().getTime());
			doc1.addField("matchTime", fbMatchPO.getPlayingTime().getTime());
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			docs.add(doc1);
			try {
				solrJ.add(docs);
				logger.info("增加一条记录：id={},matchName={}", fbMatchPO.getId(),
						fbMatchPO.getName());
			} catch (SolrServerException e) {
				logger.error("失败记录：id={},matchName={}", fbMatchPO.getId(),
						fbMatchPO.getName());
				logger.error("",e);
			} catch (Exception e) {
				logger.error("失败记录：id={},matchName={}", fbMatchPO.getId(),
						fbMatchPO.getName());
				logger.error("",e);
			}
		}
	}

	@Override
	@Transactional
	public void buildJCLQMatchIndex() {
		Paging paging = new Paging(1, 3000);
		Date from = getFrom();
		Date to = getTo();
		logger.info("开始更新竞彩篮球赛事索引 ,时间:from={},to={}",from,to);
		List<BBMatchPO> bbMatchPOs = bbMatchDao.find(paging, -1, from, to);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (BBMatchPO bbMatchPO : bbMatchPOs) {
			SolrInputDocument doc1 = new SolrInputDocument();
			doc1.addField("id", "jclq:" + bbMatchPO.getId());
			doc1.addField("matchName", bbMatchPO.getName());
			doc1.addField("matchCode", bbMatchPO.getCnCode());
			doc1.addField("entrustDeadline",
					sdf.format(bbMatchPO.getEntrustDeadline()));
			doc1.addField("lotteryType", Constant.SolrConfig.SEARCH_TYPE_JCLQ_MATCH);
			doc1.addField("createTime", bbMatchPO.getEntrustDeadline().getTime());
			doc1.addField("matchTime", bbMatchPO.getPlayingTime().getTime());
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			docs.add(doc1);
			try {
				solrJ.add(docs);
				logger.info("增加一条记录：id={},matchName={}", bbMatchPO.getId(),
						bbMatchPO.getName());
			} catch (SolrServerException e) {
				logger.error("失败记录：id={},matchName={}", bbMatchPO.getId(),
						bbMatchPO.getName());
				logger.error("",e);
			} catch (IOException e) {
				logger.error("失败记录：id={},matchName={}", bbMatchPO.getId(),
						bbMatchPO.getName());
				logger.error("",e);
			}
		}
	}

	@Override
	@Transactional
	public void buildCTZCMatchIndex() {
		Paging paging = new Paging(1, 3000);
		Date from = getFrom();
		Date to = getTo();
		logger.info("开始更新传统足彩赛事索引 ,时间:from={},to={}",from,to);
		List<CTFBMatchPO> ctfbMatchPOs = ctfbMatchDao.find(paging, -1, from, to, PlayType.CTZC_14.getPlayId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (CTFBMatchPO ctfbMatchPO : ctfbMatchPOs) {
			SolrInputDocument doc1 = new SolrInputDocument();
			doc1.addField("id", "ctzc:" + ctfbMatchPO.getId());
			doc1.addField("matchName", ctfbMatchPO.getHomeTeamName() + " VS " + ctfbMatchPO.getGuestTeamName());
			doc1.addField("matchCode", ctfbMatchPO.getIssueNumber());
			doc1.addField("matchID", ctfbMatchPO.getMatchId());
			doc1.addField("entrustDeadline",
					sdf.format(ctfbMatchPO.getEntrustDeadline()));
			doc1.addField("lotteryType", Constant.SolrConfig.SEARCH_TYPE_CTZC_MATCH);
			doc1.addField("createTime", ctfbMatchPO.getEntrustDeadline().getTime());
			doc1.addField("matchTime", ctfbMatchPO.getPlayingTime().getTime());
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			docs.add(doc1);
			try {
				solrJ.add(docs);
				logger.info("增加一条记录：id={},matchName={}", ctfbMatchPO.getId(),
						ctfbMatchPO.getHomeTeamName() + " VS " + ctfbMatchPO.getGuestTeamName());
			} catch (SolrServerException e) {
				logger.error("失败记录：id={},matchName={}", ctfbMatchPO.getId(),
						ctfbMatchPO.getHomeTeamName() + " VS " + ctfbMatchPO.getGuestTeamName());
				logger.error("",e);
			} catch (IOException e) {
				logger.error("失败记录：id={},matchName={}", ctfbMatchPO.getId(),
						ctfbMatchPO.getHomeTeamName() + " VS " + ctfbMatchPO.getGuestTeamName());
				logger.error("",e);
			}
		}
	}
	
	@Override
	@Transactional
	public void buildBJDCMatchIndex() {
		Paging paging = new Paging(1, 3000);
		Date from = getFrom();
		Date to = getTo();
		logger.info("开始更新北京单场赛事索引 ,时间:from={},to={}",from,to);
		List<BJDCMatchPO> bjdcMatchPOs = bjdcMatchDao.find(paging, from, to);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (BJDCMatchPO bjdcMatchPO : bjdcMatchPOs) {
			SolrInputDocument doc1 = new SolrInputDocument();
			doc1.addField("id", "bjdc:" + bjdcMatchPO.getId());
			doc1.addField("matchName", bjdcMatchPO.getHomeTeamName() + " VS " + bjdcMatchPO.getGuestTeamName());
			doc1.addField("matchCode", bjdcMatchPO.getIssueNumber());
			doc1.addField("entrustDeadline",
					sdf.format(bjdcMatchPO.getEntrustDeadline()));
			doc1.addField("lotteryType", Constant.SolrConfig.SEARCH_TYPE_BJDC_MATCH);
			doc1.addField("createTime", bjdcMatchPO.getEntrustDeadline().getTime());
			doc1.addField("matchTime", bjdcMatchPO.getPlayingTime().getTime());
			Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			docs.add(doc1);
			try {
				solrJ.add(docs);
				logger.info("增加一条记录：id={},matchName={}", bjdcMatchPO.getId(),
						bjdcMatchPO.getHomeTeamName() + " VS " + bjdcMatchPO.getGuestTeamName());
			} catch (SolrServerException e) {
				logger.error("失败记录：id={},matchName={}", bjdcMatchPO.getId(),
						bjdcMatchPO.getHomeTeamName() + " VS " + bjdcMatchPO.getGuestTeamName());
				logger.error("",e);
			} catch (IOException e) {
				logger.error("失败记录：id={},matchName={}", bjdcMatchPO.getId(),
						bjdcMatchPO.getHomeTeamName() + " VS " + bjdcMatchPO.getGuestTeamName());
				logger.error("",e);
			}
		}
	}
}
