package com.unison.lottery.weibo.common.nosql.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.common.nosql.Top5RecommendDao;
import com.unison.lottery.weibo.data.Top5Recommend;

@Repository
public class Top5RecommendDaoImpl extends BaseDaoImpl<Top5Recommend>  implements Top5RecommendDao{

	private int seconds4Top5RecommendList=24*60*60;//默认一天

	@Override
	public void addList(List<Top5Recommend> top5Recommends) {
		if(null!=top5Recommends&&!top5Recommends.isEmpty()){
			String typeAndDimension=top5Recommends.get(0).getTopType()+":"+top5Recommends.get(0).getDimension();
			String key4List=Keys.top5RecommendListKey(typeAndDimension);
			String[] key4Objects=new String[top5Recommends.size()];
			int i=0;
			for(Top5Recommend top5Recommend:top5Recommends){
				String key4Object=Keys.top5RecommendObjectKey(top5Recommend);
				if(StringUtils.isNotBlank(key4Object)){
					hashAdd(key4Object, top5Recommend);
					key4Objects[i]=key4Object;
					i++;
				}
				
			}
			delete(key4List);//把旧列表删除
			lpush(key4List, key4Objects);//插入新列表
			expire(key4List, seconds4Top5RecommendList);
		}
		
	}

	public int getSeconds4Top5RecommendList() {
		return seconds4Top5RecommendList;
	}

	public void setSeconds4Top5RecommendList(int seconds4Top5RecommendList) {
		this.seconds4Top5RecommendList = seconds4Top5RecommendList;
	}

}
