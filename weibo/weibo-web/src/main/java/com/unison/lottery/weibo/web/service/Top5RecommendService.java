package com.unison.lottery.weibo.web.service;

import java.util.List;
import com.xhcms.lottery.commons.data.Top5RecommendVo;


public interface Top5RecommendService {

	public List<Top5RecommendVo> findTop5Rcommend(String key,String topType,String dimension);
}
