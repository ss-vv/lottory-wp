package com.xhcms.lottery.commons.persist.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.dao.ShowSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithAssocPO;
import com.xhcms.lottery.commons.persist.service.ShowSchemeQueryCondition;
import com.xhcms.lottery.lang.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-follow-spring.xml")
public class ShowSchemeDaoImplTest {

	@Autowired
	private ShowSchemeDao showSchemeDao;
	
	@Test
	public void test(){}
	
	//@Test
	//@Transactional
/*	public void testFindOnSaleShowingSchemes() {
		Paging paging = new Paging();
		paging.setMaxResults(30);
		paging.setPageNo(0);
		ShowSchemeQueryCondition condition = new ShowSchemeQueryCondition();
		
		List<BetSchemeWithAssocPO> schemes = null;
		schemes = showSchemeDao.findOnSaleShowingSchemes(paging, condition);
		assertTrue(schemes.size()>0);
		for(BetSchemeWithAssocPO scheme : schemes){
			System.out.println(scheme.getPlay().getLotteryId());
			if (scheme.getUserScores()!=null){
				System.out.println(scheme.getUserScores());
			}
		}
		
		// 测试 user score 为空的情况
		condition.setLotteryId(Constants.JCZQ);
		condition.setRecommend(false);
		schemes = showSchemeDao.findOnSaleShowingSchemes(paging, condition);
		assertTrue(schemes.size()>0);
		for(BetSchemeWithAssocPO scheme : schemes){
			System.out.println(scheme);
			if (scheme.getUserScores()!=null){
				System.out.println(scheme.getUserScores());
			}
		}
	}*/

}
