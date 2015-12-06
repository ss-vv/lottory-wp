package com.unison.lottery.weibo.dataservice.scraper;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unison.lottery.weibo.dataservice.commons.model.MatchHistory;

public class MatchInfoScraperImplTest  {

	@Test
	public void testScrapeMatchInfo() {
		MatchInfoScraperImpl scraper = new MatchInfoScraperImpl();
		MatchHistory matchInfo = scraper.scrapeMatchInfo("941091");
		System.out.println(matchInfo);
		
		assertNotNull(matchInfo.getMatchId());
		assertTrue(matchInfo.getMatchId().length()>0);
		
		assertNotNull(matchInfo.getSclassID());
		assertTrue(matchInfo.getSclassID().length()>0);

		assertNotNull(matchInfo.getHomeTeamId());
		assertTrue(matchInfo.getHomeTeamId().length()>0);

		assertNotNull(matchInfo.getGuestTeamId());
		assertTrue(matchInfo.getGuestTeamId().length()>0);

		assertNotNull(matchInfo.getHomeTeamName());
		assertTrue(matchInfo.getHomeTeamName().length()>0);

		assertNotNull(matchInfo.getGuestTeamName());
		assertTrue(matchInfo.getGuestTeamName().length()>0);
		
		assertNotNull(matchInfo.getAgainstHistory());
		assertNotNull(matchInfo.getHostNearScore());
		assertNotNull(matchInfo.getGuestNearScore());
		assertNotNull(matchInfo.getOpOdds());
		assertNotNull(matchInfo.getYpOdds());
		assertNotNull(matchInfo.getJcOdds());
	}

}
