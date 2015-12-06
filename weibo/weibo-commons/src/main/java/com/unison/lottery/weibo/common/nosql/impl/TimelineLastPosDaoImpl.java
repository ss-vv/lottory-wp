package com.unison.lottery.weibo.common.nosql.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.common.nosql.TimelineLastPosDao;

@Repository
public class TimelineLastPosDaoImpl extends BaseDaoImpl <String> implements TimelineLastPosDao {

	@Override
	public Map<String, String> loadAll(long uid) {
		return null;
	}

}
