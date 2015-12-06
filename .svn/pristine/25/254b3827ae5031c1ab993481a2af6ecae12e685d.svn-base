package com.unison.lottery.weibo.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.nosql.HotAndRecommendMatchDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.persist.QTMatchidDao;
import com.unison.lottery.weibo.web.service.Top5GuessMatchService;
import com.unison.lottery.weibo.web.util.MatchWeiboComparator;
import com.xhcms.lottery.commons.data.HotAndRecommendMatch;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
@Service
public class Top5GuessMatchServiceImpl implements Top5GuessMatchService{

	@Autowired
	private BetSchemeDao betSchemeDao;
	@Autowired
	private FBMatchPlayDao fbMatchPlayDao;
	@Autowired
	private BBMatchPlayDao bbMatchPlayDao;
	@Autowired
	private BJDCMatchPlayDao bjdcMatchPlayDao;
	@Autowired
	private FBMatchDao fbMatchDao;
	@Autowired
	private BBMatchDao bbMatchDao;
	@Autowired
	private BJDCMatchDao bjdcMatchDao;
	@Autowired
	private QTMatchidDao qtMatchIdDao;
	@Autowired
	private HotAndRecommendMatchDao hotAndRecommendMatchDao;
	@Override
	@Transactional
	public List<HotAndRecommendMatch> findTop5GuessMatch() {
		List<HotAndRecommendMatch> top5RecommendMatch= new ArrayList<HotAndRecommendMatch>();
		List<String> keys=hotAndRecommendMatchDao.lrange(Keys.HOT_AND_RECOMMEND_MATCH, 0, -1);
		if(keys!=null && keys.size()>0){
			String []arrayKey=(String[])keys.toArray(new String[0]);
			top5RecommendMatch= hotAndRecommendMatchDao.hashList(arrayKey);
		}
		return sortList(top5RecommendMatch);
	}
	//根据scheme 查看所有match
	private List<HotAndRecommendMatch> sortList(List<HotAndRecommendMatch> hotList){
		List<HotAndRecommendMatch> top5Match=new ArrayList<HotAndRecommendMatch>();
		Collections.sort(hotList,new MatchWeiboComparator());
		if(hotList.size()>0 && hotList.size()<=5){
			return hotList;
		}else{
			if(hotList.size()>0){
				for(int i=0;i<5;i++){
					top5Match.add(hotList.get(i));
				}
			}
		}
	    return top5Match;	
	}
}
