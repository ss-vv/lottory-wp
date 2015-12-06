package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;

public interface WeiboService {

	BetSchemePO getById(long schemeId);

	List<BetMatchPO> findMatchsById(long schemeId);

	FBMatchPO getFBMatchById(long id);

	BBMatchPO getBBMatchById(long id);

}
