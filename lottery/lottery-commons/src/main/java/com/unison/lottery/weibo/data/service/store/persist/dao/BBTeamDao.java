package com.unison.lottery.weibo.data.service.store.persist.dao;

import java.util.Collection;
import java.util.List;

import com.unison.lottery.weibo.data.service.store.persist.entity.BBTeamPO;
import com.xhcms.commons.persist.Dao;

/**
 * @desc 对于篮球球队信息的数据库操作
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-10
 * @version 1.0
 */
public interface BBTeamDao extends Dao<BBTeamPO> {

	void batchInsert(Collection<BBTeamPO> data);

	void batchUpdate(Collection<BBTeamPO> data);

	List<Long> teamIdList();
	
	long findIdByChineseName(String teamName);
	long findIdByTraditionalName(String teamName);

	BBTeamPO findByTeamId(Long teamId);

	BBTeamPO findByLeagueIdAndChineseName(long leagueId, String teamName);

	BBTeamPO findByChineseName(String teamName);
}