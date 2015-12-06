package com.unison.lottery.weibo.msg.service;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;

public interface RealWeiboService{
	PageResult<WeiboMsgVO> findRealWeiboByRecentDays(int recentDays);
	PageResult<WeiboMsgVO> findRealWeiboByRecentDaysAndSort(PageRequest pageRequest,int recentDays,String followCountSort,
			String buyAmountSort,String rateOfReturnSort,String timeSort,String lottery);
}
