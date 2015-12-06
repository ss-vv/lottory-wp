package com.davcai.lottery.push.common.odds.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.FBMatch;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;

/**
 * 
 * @author peng
 *
 * @since 2015年3月3日下午3:59:42
 */
@Repository
public class JCZQOddsMessageDaoImpl extends HibernateDaoSupport implements JCZQOddsMessageDao{

	@Autowired
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<Object[]> queryMatchCorpOdds(String corpId) {
		// TODO Auto-generated method stub
		List<Object[]> matchOpOddsInfoPOs = getSession().createQuery("select a,b.code,b.playingTime from FbMatchOpOddsInfoPO a,FBMatchPO b,FbMatchBaseInfoPO c,FbLotteryCorpBaseInfoPO d"
				+ " where a.corpId=d.euroId and d.corpId=? and a.bsId=c.id and b.cnCode=c.jingcaiId and b.playingTime=c.matchTime and b.status=1").setString(0, corpId).list();
		getSession().close();
		return matchOpOddsInfoPOs;
	}
	
}
