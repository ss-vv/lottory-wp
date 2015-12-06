package com.unison.weibo.admin.service;

import java.util.List;

import com.xhcms.commons.lang.Paging;

public interface RecommendAdminService {

	Paging getRecommendMatch(Paging p,String matchType);
	
	void  putOnRecommend(List<Long> id);
	
	void  getOffRecommend(List<Long> id);
}
