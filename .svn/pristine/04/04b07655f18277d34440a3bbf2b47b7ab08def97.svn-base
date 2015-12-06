package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.IdCardPO;

public interface IdCardDao extends Dao<IdCardPO>{

    List<IdCardPO> find(Paging paging, String username, int status, int type, Date from, Date to);

    void updateStatus(Collection<Long> id, int status, Long adminId, String admin);

}
