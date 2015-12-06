package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.RegistCodePO;

public interface RegistrationCodeDao extends Dao<RegistCodePO>{

	 public RegistCodePO getRegistrationCodeWithCode(String code);

	public List<RegistCodePO> listByPagger(Paging paging, int status, int vid);
}
