package com.xhcms.lottery.commons.utils.internal;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;

/**
 * 测试竞彩拆票。
 * 
 * @author Yang Bo
 */
public class JCBetStrategyTest {
	
	/**
	 * 测试混合过关方式拆票后的投注内容是否正确。
	 */
	@Test
	public void mixPlayTypeCodeTest(){
		JCBetStrategy s = new JCBetStrategy();
		BetScheme scheme = setupScheme();
		Bet b = s.resolve(scheme);
		assertEquals(1, b.getTickets().size());
		assertEquals(2, b.getNote());
		Ticket t = b.getTickets().get(0);
		assertEquals(2, t.getNote());
		assertEquals(2, t.getMultiple());
		assertEquals(4, t.getMoney());
	}
	
	private BetScheme setupScheme(){
		BetScheme scheme = new BetScheme();
		scheme.setLotteryId(LotteryId.JCZQ.name());
        scheme.setPlayId(PlayType.JCZQ_HH.getPlayIdWithPass(false));
        scheme.setMultiple(2);
        scheme.setMatchs(parseMatches("201211024003-40031-false-spf,201211024005-400533-false-bqc"));
        scheme.setMatchNumber(scheme.getMatchs().size());
        scheme.setOfftime(DateUtils.addHours(new Date(), 1));
        List<String> pts = new ArrayList<String>();
        pts.add("2@1");
        scheme.setPassTypes(pts);
		return scheme;
	}
	
	private List<BetMatch> parseMatches(String matchs){
		String[] matchArr = matchs.split(",");
        Pattern p = Pattern.compile("-");
        List<BetMatch> matchList = new ArrayList<BetMatch>(matchArr.length);
        for (int i = 0; i < matchArr.length; i++) {
            String match = matchArr[i];
            String[] m = p.split(match);
            BetMatch bm = new BetMatch();
            bm.setMatchId(Long.parseLong(m[0]));
            bm.setCode(m[1]);
            bm.setOdds("3.3");
            bm.setDefaultScore(-1);
            //加胆码
            bm.setSeed(Boolean.parseBoolean(m[2]));
            if(m.length>3){
            	MixPlayType mp = MixPlayType.valueOfPlayName(m[3]);
            	bm.setPlayId(mp.getPlayId());
            }
            matchList.add(bm);
        }
        return matchList;
	}
	
	/**
	 * 测试混合过关方式拆票时不能过滤掉所有只有一种玩法的票，而要修改该票的playId。
	 */
	@Test
	public void shouldFilterOnlyOneMixTypeTicket(){
		JCBetStrategy s = new JCBetStrategy();
		BetScheme scheme = setupSchemeForFilter();
		Bet b = s.resolve(scheme);
		assertEquals(3, b.getTickets().size());
		assertEquals(3, b.getNote());
		Ticket t = b.getTickets().get(0);
		assertEquals(1, t.getNote());
		assertEquals(1, t.getMultiple());
		assertEquals(2, t.getMoney());
	}

	private BetScheme setupSchemeForFilter(){
		BetScheme scheme = new BetScheme();
		scheme.setLotteryId(LotteryId.JCZQ.name());
        scheme.setPlayId(PlayType.JCZQ_HH.getPlayIdWithPass(false));
        scheme.setMultiple(1);
        scheme.setMatchs(parseMatches("201211024003-40031-false-spf,201211024005-400533-false-bqc,201211024005-400633-false-bqc"));
        scheme.setMatchNumber(scheme.getMatchs().size());
        scheme.setOfftime(DateUtils.addHours(new Date(), 1));
        List<String> pts = new ArrayList<String>();
        pts.add("2@1");
        scheme.setPassTypes(pts);
		return scheme;
	}
}
