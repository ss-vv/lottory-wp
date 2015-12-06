package com.unison.lottery.weibo.common.nosql.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.common.nosql.AtMeDao;

@Repository
public class AtMeDaoImpl extends BaseDaoImpl<String> implements AtMeDao {
	
	@Override
	public long atUserByPostId(final String[] uids,final String pid){
		long count = 0;
		for(final String uid : uids){
			if(StringUtils.isNotBlank(uid)){
				count += zadd(new Double(pid), Keys.atMeKey(uid), pid);
			}
		}
		return count;
	}
}