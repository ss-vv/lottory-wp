package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.AccountPO;

public interface AccountDao extends Dao<AccountPO> {

    List<AccountPO> find(Paging paging, String username);
    
    List<AccountPO> findByIds(Collection<Long> ids);

	List<Long> listIds();

	AccountPO get(Long ltUserId, LockMode upgrade);
}
