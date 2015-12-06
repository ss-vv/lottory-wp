package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.PrintableFilePO;

public interface PrintableFileDao extends Dao<PrintableFilePO> {

	List<PrintableFilePO> findByDate(String lotteryPlatformId, Date time);

	PrintableFilePO findById(long fileId);
}
