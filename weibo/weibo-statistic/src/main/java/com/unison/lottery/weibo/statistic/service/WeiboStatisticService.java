package com.unison.lottery.weibo.statistic.service;

import java.util.List;

import com.unison.lottery.weibo.data.PageRequest;
import com.xhcms.lottery.commons.persist.entity.UserPO;

public interface WeiboStatisticService {
	void statistic();
	void setPageRequest(PageRequest pageRequest);
	void setTo(long to);
	void setFrom(long from);
	List<UserPO> findWeixinUidIsNotNull();
}
