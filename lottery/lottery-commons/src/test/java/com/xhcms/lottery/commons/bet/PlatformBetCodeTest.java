package com.xhcms.lottery.commons.bet;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.utils.internal.PlatformBetCodeCreator;
import com.xhcms.lottery.commons.utils.internal.impl.ARZYBetCodeCreatorImpl;
import com.xhcms.lottery.commons.utils.internal.impl.ZMBetCodeCreatorImpl;
import com.xhcms.lottery.lang.PlayType;

/**
 * 平台投注格式转换测试
 * @author lei.li@davcai.com
 */
public class PlatformBetCodeTest {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	int[] cur = new int[] { 0, 1 };
	Bet bet = new Bet();
	List<BetMatch> matchs = new ArrayList<BetMatch>();
	
	@Before
	public void setUp() throws ParseException {
		String playId = PlayType.JCZQ_BRQSPF.getPlayIdWithPass(false);
		bet.setPlayId(playId);
		
		BetMatch bm1 = new BetMatch();
		String bm1Code = "50113";
		long bm1JcOfficalMatchId = 66081L;
		bm1.setPlayId(playId);
		bm1.setCode(bm1Code);
		bm1.setOdds("2.00");
		bm1.setCnCode("周五011");
		bm1.setJcOfficialMatchId(bm1JcOfficalMatchId);
		bm1.setPlayingTime(DateUtils.parseDate("2015-07-19 11:02:00", "yyyy-MM-dd HH:mm:ss"));
		
		BetMatch bm2 = new BetMatch();
		String bm2Code = "50121";
		long bm2JcOfficalMatchId = 66082L;
		bm2.setPlayId(playId);
		bm2.setCode(bm2Code);
		bm2.setOdds("3.20");
		bm2.setCnCode("周五012");
		bm2.setJcOfficialMatchId(bm2JcOfficalMatchId);
		bm1.setPlayingTime(DateUtils.parseDate("2015-07-19 11:00:00", "yyyy-MM-dd HH:mm:ss"));
		
		matchs.add(bm1);
		matchs.add(bm2);
	}
	
	/**
	 * 提前准备给“安瑞智赢”的投注内容
	 * @throws ParseException 
	 */
	@Test
	public void testARZYCreateBetCode() throws ParseException {
		PlatformBetCodeCreator pbc = null;
		//pbc = new ZMBetCodeCreatorImpl();
		pbc = new ARZYBetCodeCreatorImpl();
		
		Ticket ticket = pbc.create(bet.getPlayId(), cur, matchs);
		String code = ticket.getCode();
		logger.info("ticket code=" + code);
		
		Assert.assertNotNull(ticket);
		StringBuilder buf = new StringBuilder();
		for(int i=0; i<matchs.size(); i++) {
			BetMatch bm = matchs.get(i);
			buf.append(bm.getCode());
			if(i != matchs.size()-1) {
				buf.append("-");
			}
		}
		Assert.assertEquals(buf.toString(), code);
		assertTrue(ticket.getMinMatchPlayingTime().equals(DateUtils.parseDate("2015-07-19 11:00:00", "yyyy-MM-dd HH:mm:ss")));
	}

	
	@Test
	public void testZMCreateBetCode() throws ParseException{
		PlatformBetCodeCreator pbc = null;
		pbc = new ZMBetCodeCreatorImpl();
//		pbc = new ARZYBetCodeCreatorImpl();
		
		Ticket ticket = pbc.create(bet.getPlayId(), cur, matchs);
		String code = ticket.getCode();
		logger.info("ticket code=" + code);
		
		Assert.assertNotNull(ticket);
		StringBuilder buf = new StringBuilder();
		for(int i=0; i<matchs.size(); i++) {
			BetMatch bm = matchs.get(i);
			buf.append(bm.getCode());
			if(i != matchs.size()-1) {
				buf.append("-");
			}
		}
		Assert.assertEquals(buf.toString(), code);
		assertTrue(ticket.getMinMatchPlayingTime().equals(DateUtils.parseDate("2015-07-19 11:00:00", "yyyy-MM-dd HH:mm:ss")));
	}
}
