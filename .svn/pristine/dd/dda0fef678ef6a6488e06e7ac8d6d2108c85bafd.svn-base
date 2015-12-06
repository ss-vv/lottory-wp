package com.xhcms.lottery.commons.persist.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.service.ShowSchemeQueryCondition;
import com.xhcms.lottery.commons.persist.service.ShowSchemeService;
import com.xhcms.lottery.lang.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-follow-spring.xml")
public class ShowSchemeServiceImplTest {

	@Autowired
	private ShowSchemeService showSchemeService;
	
	@Test
	public void test(){}
	
//	@Test
/*	@SuppressWarnings("unchecked")
	public void testFindOnSaleShowingSchemes() {
		Paging paging = new Paging();
		paging.setMaxResults(30);
		paging.setPageNo(0);
		ShowSchemeQueryCondition condition = new ShowSchemeQueryCondition();
		
		showSchemeService.findOnSaleShowingSchemes(paging, condition);
		assertTrue(paging.getResults().size()>0);
		for(BetScheme scheme : (List<BetScheme>)paging.getResults()){
			System.out.println(scheme);
			if ( scheme.getUserScores() != null ){
				System.out.println(scheme.getUserScores());
			}
		}
	}*/
	
//	@SuppressWarnings("unchecked")
//	@Test
/*	public void testFindOnSaleShowingBascketballSchemes(){
		Paging paging = new Paging();
		paging.setMaxResults(4);
		paging.setPageNo(0);
		ShowSchemeQueryCondition condition = new ShowSchemeQueryCondition();
		condition.setLotteryId(Constants.JCZQ);
		condition.setRecommend(false);
		showSchemeService.findOnSaleShowingSchemes(paging, condition);
		System.out.println("count: " + paging.getResults().size());
		assertTrue(paging.getResults().size()>0);
		for(BetScheme scheme : (List<BetScheme>)paging.getResults()){
			//System.out.println(scheme);
			if ( scheme.getUserScores() != null ){
				//System.out.println(scheme.getUserScores());
			}
			List<BetScheme> followedSchemes = scheme.getFollowSchemes();
			System.out.println("follow schemes: " + followedSchemes.size());
		}

		assertTrue(paging.getResults().size()==5);
	}*/


}
