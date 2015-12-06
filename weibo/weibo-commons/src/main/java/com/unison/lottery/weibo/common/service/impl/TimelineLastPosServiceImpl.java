package com.unison.lottery.weibo.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.nosql.TimelineLastPosDao;
import com.unison.lottery.weibo.common.service.TimelineLastPosService;

@Service
public class TimelineLastPosServiceImpl implements TimelineLastPosService {
	
	@Autowired
	private TimelineLastPosDao timelineLastPosDao;

	@Override
	public Map<String, String> loadAll(List<Long> uids) {
		return null;
	}

}
