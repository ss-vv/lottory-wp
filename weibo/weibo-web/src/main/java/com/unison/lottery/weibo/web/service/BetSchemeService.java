package com.unison.lottery.weibo.web.service;

import java.util.List;

import com.unison.lottery.weibo.data.vo.BetSchemeVo;
import com.xhcms.lottery.commons.data.BetScheme;

public interface BetSchemeService {

	List<BetSchemeVo> getLastBetScheme();
}
