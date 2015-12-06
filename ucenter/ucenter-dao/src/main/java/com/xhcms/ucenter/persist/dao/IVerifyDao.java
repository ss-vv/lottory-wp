package com.xhcms.ucenter.persist.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.ucenter.persist.entity.VerifyPO;

/**
 * Title: 
 * 
 * @author jiajiancheng
 * @version 1.0
 */
public interface IVerifyDao extends Dao<VerifyPO>{
	
	VerifyPO get(long uid, int type);
	
	List<VerifyPO> findByRealnameStatus(Paging paging, int realnameStatus);
	
	List<VerifyPO> findByIdnumberStatus(Paging paging, int idnumberStatus);

}
