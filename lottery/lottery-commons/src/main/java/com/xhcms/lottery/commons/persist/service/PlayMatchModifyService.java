package com.xhcms.lottery.commons.persist.service;

import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;

public interface PlayMatchModifyService {
	
	/**
	 * 根据fbMatch和fbMathPlay修改playMatch对象的属性
	 * @param po
	 * @param mpo
	 * @param pm
	 */
	public void modifyFBPlayMatch(FBMatchPO po, FBMatchPlayPO mpo, PlayMatch pm) ;
	
	/***
	 * 根据bbMatch和bbMathPlay修改playMatch对象的属性
	 * @param po
	 * @param mpo
	 * @param pm
	 */
	public void  modifyBBPlayMatch(BBMatchPO po, BBMatchPlayPO mpo, PlayMatch pm) ;
}
