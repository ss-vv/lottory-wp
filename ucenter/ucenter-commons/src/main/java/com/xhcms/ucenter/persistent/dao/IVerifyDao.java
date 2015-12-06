package com.xhcms.ucenter.persistent.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.ucenter.persistent.entity.VerifyPO;

/**
 * Title: 
 * 
 * @author jiajiancheng
 * @version 1.0
 */
public interface IVerifyDao extends Dao<VerifyPO>{
	
	VerifyPO get(long uid, int type);
	
	VerifyPO get(String target, int type);
	
	List<VerifyPO> findByRealnameStatus(Paging paging, int realnameStatus);
	
	List<VerifyPO> findByIdnumberStatus(Paging paging, int idnumberStatus);

	VerifyPO findPhoneNumberByVerifyCode(String verifyCode);

	VerifyPO findByVerifyCode(String verifyCode);
}
