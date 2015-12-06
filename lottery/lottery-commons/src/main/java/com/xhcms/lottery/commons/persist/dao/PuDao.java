package com.xhcms.lottery.commons.persist.dao;

import java.math.BigInteger;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.PuPO;

public interface PuDao extends Dao<PuPO> {

	PuPO getSU();

	PuPO findByUid(long uid);

	List<BigInteger> listUser();
}
