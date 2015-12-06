package com.unison.lottery.weibo.common.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.HomeMatchRecommendDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.HomeMatchRecommendService;
import com.unison.lottery.weibo.data.HomeMatchRecommend;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.xhcms.lottery.lang.LotteryId;

@Service
public class HomeMatchRecommendServiceImpl extends BaseServiceImpl implements
	HomeMatchRecommendService {
	
	@Override
	protected BaseDao<?> getBaseDao() {
		return matchRecommendDao;
	}
	
	@Autowired
	private HomeMatchRecommendDao matchRecommendDao;
	
	@Override
	public List<HomeMatchRecommend> queryByLottery(LotteryId lotteryId, 
			int size) {
		String key = Keys.lotteryMatchRecommendList(lotteryId.name());
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageIndex(0);
		pageRequest.setPageSize(size);
		PageResult<HomeMatchRecommend> modelResult = listSortedSetAsc(key, 
				HomeMatchRecommend.class, pageRequest);
		List<HomeMatchRecommend> list = modelResult.getResults();
		
		return list;
	}
}
