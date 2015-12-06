package com.xhcms.lottery.dc.persist.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.dc.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.dc.persist.service.CTFBMatchService;

/**
 * @author Wang Lei
 */
public class CTFBMatchServiceImpl implements CTFBMatchService {
	
	@Autowired
	private CTFBMatchDao cTFBMatchDao;
	
	@Override
	@Transactional
	public void batchSaveOrUpdateMatch(List<CTFBMatch> data){
		if (data == null || data.size() == 0) {
			return;
		}
		
		Set<String> mpIds = new HashSet<String>();
		List<CTFBMatch> mps = new ArrayList<CTFBMatch>();  // 需要修改的 CTFBMatch
		HashMap<String, CTFBMatch> mpMap = new HashMap<String, CTFBMatch>(); // 需要添加的 CTFBMatch
		
		for (CTFBMatch fb : data) {
		    mpMap.put(fb.getId(), fb);
		    mpIds.add(fb.getId());
		}
		
		// 过滤已经存在的 MatchPlayID
		for(String id : cTFBMatchDao.filterExist(mpIds)){
			CTFBMatch fb = mpMap.remove(id);
			mps.add(fb);
		}
		
		updateMatchs(mps, mpMap.values());
	}
	
	/**
	 * 更新赛事
	 * @param update 需要修改的赛事
	 * @param insert   需要新增的赛事
	 */
	private void updateMatchs(Collection<CTFBMatch> update, Collection<CTFBMatch> insert) {
		if (insert.size() > 0) {
			cTFBMatchDao.batchInsertMatch(insert);
		}
		if (update.size() > 0) {
			cTFBMatchDao.batchUpdateMatch(update);
		}
	}
}
