package com.unison.lottery.weibo.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.AnnounceDao;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.AnnounceService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsg;

@Service
public class AnnounceServiceImpl extends BaseServiceImpl implements
		AnnounceService {

	@Autowired
	private AnnounceDao announceDao;

	@Override
	protected BaseDao<?> getBaseDao() {
		return announceDao;
	}

	@Override
	public PageResult<WeiboMsg> query(PageRequest pageRequest) {
		String key = Keys.ANNOUNCE;
		PageResult<WeiboMsg> modelResult = listSortedSetDesc(key,
				WeiboMsg.class, pageRequest);
		return modelResult;
	}
}
