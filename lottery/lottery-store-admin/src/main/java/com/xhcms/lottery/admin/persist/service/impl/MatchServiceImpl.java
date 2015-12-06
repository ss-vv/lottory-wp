package com.xhcms.lottery.admin.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.admin.persist.service.MatchService;
import com.xhcms.lottery.commons.data.BBMatch;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.FBMatch;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;

public class MatchServiceImpl implements MatchService {

    @Autowired
    private FBMatchDao fbMatchDao;
    
    @Autowired
    private BBMatchDao bbMatchDao;
    
    @Autowired
    private CTFBMatchDao cTFBMatchDao;
    
    @Override
    @Transactional
    public void listFBMatch(Paging paging, int status, Date from, Date to) {
        List<FBMatchPO> data = fbMatchDao.find(paging, status, from, to);
        List<FBMatch> results = new ArrayList<FBMatch>(data.size());
        for (FBMatchPO po : data) {
            FBMatch m = new FBMatch();
            BeanUtils.copyProperties(po, m);
            results.add(m);
        }
        paging.setResults(results);
    }

    @Override
    @Transactional
    public void listBBMatch(Paging paging, int status, Date from, Date to) {
        List<BBMatchPO> data = bbMatchDao.find(paging, status, from, to);
        List<BBMatch> results = new ArrayList<BBMatch>(data.size());
        for (BBMatchPO po : data) {
            BBMatch m = new BBMatch();
            BeanUtils.copyProperties(po, m);
            results.add(m);
        }
        paging.setResults(results);

    }

    @Override
    @Transactional
    public void updateFBMatch(Long mid, int status) {
        FBMatchPO po = fbMatchDao.get(mid);
        if(po != null)
            po.setStatus(status);
    }

    @Override
    @Transactional
    public void updateBBMatch(Long mid, int status) {
        BBMatchPO po = bbMatchDao.get(mid);
        if(po != null)
            po.setStatus(status);
    }
    
    @Override
    @Transactional
    public List<CTFBMatch> findByIssueNumberAndPlayId(String issueNumber, String playId) {
    	List<CTFBMatchPO> data = cTFBMatchDao.findByIssueNoAndPlayId(issueNumber, playId);
    	List<CTFBMatch> results = new ArrayList<CTFBMatch>(data.size());
    	for(CTFBMatchPO po : data) {
    		CTFBMatch m = new CTFBMatch();
    		BeanUtils.copyProperties(po, m);
            results.add(m);
    	}
    	return results;
    }

}
