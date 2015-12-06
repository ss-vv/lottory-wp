package com.davcai.lottery.dao;

import java.util.List;

import com.unison.lottery.api.redEnvalope.model.EnvalopeGrabHistory;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopeGrabRecordPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopePO;

public interface RedEnvalopeDao {

	long saveEnvalope(RedEnvalopePO redEnvalopePO);

	RedEnvalopePO queryEnvalopeById(Long envalopeId);

	void deleteRedEnvalopeById(long envalopeId);

	void update(RedEnvalopePO redEnvalopePO);

	void save(RedEnvalopeGrabRecordPO record);

	List<RedEnvalopeGrabRecordPO> queryRedEnvalopeGrabHistory(Long envalopeId);

	List<RedEnvalopePO> queryAllInvalidEnvalope();

}
