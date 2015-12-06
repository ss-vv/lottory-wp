package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.commons.persist.service.CTFBMatchBaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-betscheme-spring.xml")
public class CTFBMatchDaoImplTest {

	@Autowired
	CTFBMatchDao cTFBMatchDao;
	
    
    @Autowired
    CTFBMatchBaseService cTFBMatchBaseService;
	
	@Transactional
	@Test
	public void testCTFBMatch() {
/*		List<CTFBMatchPO> cTFBMatchPOs= cTFBMatchDao.findByIssueNoAndPlayId("12148", "24_ZC_14");
		for(CTFBMatchPO po : cTFBMatchPOs) {
			System.out.println(po.getMatchId());
		}*/
		List<CTFBMatch> cTFBMatchs = cTFBMatchBaseService.findByIssueNoAndPlayId("12148", "24_ZC_14");
		for(CTFBMatch m : cTFBMatchs) {
			System.out.println(m.getMatchId());
		}
	}

}
