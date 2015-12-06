package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.BetMatchRecVo;
import com.xhcms.lottery.commons.persist.entity.BetMatchRecPO;

/**
 * @desc 推荐方案对应赛事信息
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-28
 * @version 1.0
 */
public interface BetMatchRecDao extends Dao<BetMatchRecPO> {


	List<BetMatchRecPO> findBySchemeId(Long id);
	
	List<BetMatchRecPO> findPOById(Long id);
	List<Object[]> getMatchRecList(String type,String playtype[],Paging p);
	
	Integer getMatchCount(String type, String[] playtype);
}