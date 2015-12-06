package com.unison.lottery.weibo.common.nosql.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.common.nosql.HotAndRecommendMatchDao;
import com.xhcms.lottery.commons.data.HotAndRecommendMatch;
@Repository
public class HotAndRecommendMatchDaoImpl extends BaseDaoImpl<HotAndRecommendMatch> implements HotAndRecommendMatchDao {

	private int seconds4HotAndRecommendMatchList=24*60*60;//默认一天;

	@Override
	public void addList(List<HotAndRecommendMatch> hotAndRecommendMatchs) {
		if(null!=hotAndRecommendMatchs&&!hotAndRecommendMatchs.isEmpty()){
			String key4List=Keys.HOT_AND_RECOMMEND_MATCH;
			String[] key4Objects=new String[hotAndRecommendMatchs.size()];
			int i=0;
			for(HotAndRecommendMatch hotAndRecommendMatch:hotAndRecommendMatchs){
				String key4Object=Keys.hotAndRecommendMatchKey(hotAndRecommendMatch);
				if(StringUtils.isNotBlank(key4Object)){
					hashAdd(key4Object, hotAndRecommendMatch);
					key4Objects[i]=key4Object;
					i++;
				}
				
			}
			delete(key4List);//把旧列表删除
			lpush(key4List, key4Objects);//插入新列表
			expire(key4List, seconds4HotAndRecommendMatchList);
		}
		
	}

	public int getSeconds4HotAndRecommendMatchList() {
		return seconds4HotAndRecommendMatchList;
	}

	public void setSeconds4HotAndRecommendMatchList(
			int seconds4HotAndRecommendMatchList) {
		this.seconds4HotAndRecommendMatchList = seconds4HotAndRecommendMatchList;
	}

}
