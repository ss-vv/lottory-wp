package com.unison.lottery.weibo.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.WinningNewDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.WinningNewService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WinningNew;
import com.unison.lottery.weibo.data.vo.WinningNewVO;

@Service
public class WiningNewServiceImpl extends BaseServiceImpl implements
		WinningNewService {

	@Autowired
	private WinningNewDao winningNewDao;

	@Override
	protected BaseDao<?> getBaseDao() {
		return winningNewDao;
	}

	@Override
	public PageResult<WinningNewVO> query(PageRequest pageRequest) {
		String key = Keys.WINNINGNEWS;
		PageResult<WinningNew> modelResult = listSortedSetDesc(key,
				WinningNew.class, pageRequest);
		PageResult<WinningNewVO> voResult = fillVOPageResult(modelResult,
				WinningNewVO.class);
		return voResult;
	}

	@Override
	public PageResult<WinningNewVO> queryByTime(PageRequest pageRequest,
			RecentDateType recentDateType) {
		String key = Keys.WINNINGNEWS;
		PageResult<WinningNew> modelResult = 
				listSortedSetDescByScore(key, WinningNew.class, pageRequest, recentDateType);
		PageResult<WinningNewVO> voResult = fillVOPageResult(modelResult,
				WinningNewVO.class);
		return voResult;
	}
}
