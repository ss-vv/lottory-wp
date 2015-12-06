package com.xhcms.lottery.commons.persist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BetMatchDao;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.service.WeiboService;

/**
 * 微博调用大V彩功能服务
 * @author Wang Lei
 *
 */
@Service
public class WeiboServiceImpl implements WeiboService {
	
	@Autowired
    protected BetSchemeDao betSchemeDao;
	@Autowired
    protected BetMatchDao betMatchDao;
    @Autowired
    protected FBMatchDao fbMatchDao;
    @Autowired
    protected BBMatchDao bbMatchDao;
	
	@Override
    @Transactional(readOnly=true)
	public BetSchemePO getById(long schemeId){
		return betSchemeDao.get(schemeId);
	}
	
	@Override
    @Transactional(readOnly=true)
	public List<BetMatchPO>  findMatchsById(long schemeId){
		return betMatchDao.findBySchemeId(schemeId);
	}
	
	 @Override
    @Transactional(readOnly=true)
    public FBMatchPO getFBMatchById(long id){
    	return fbMatchDao.get(id);
    }
    
    @Override
    @Transactional(readOnly=true)
    public BBMatchPO getBBMatchById(long id){
    	return bbMatchDao.get(id);
    }
}
