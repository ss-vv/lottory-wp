package com.xhcms.lottery.account.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.xhcms.lottery.account.service.BonusDetailException;
import com.xhcms.lottery.account.service.BonusDetailService;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.bonus.BonusDetail;
import com.xhcms.lottery.commons.data.bonus.SupposeHit;

/**
 * 奖金明细测试 <br/>
 * 
 * 对于胆拖模式，因为大V彩和澳客定义不一样，所以结果不一样，并且不能把澳客的最低场次简单相加作为大V彩最低奖金。
 * 
 * @author Yang Bo
 */
public class BonusDetailServiceImplTest {
	
	private BetScheme fixBetScheme;
	private BonusDetailService bonusDetailService = new BonusDetailServiceImpl();
	
    public void prepareBetScheme() {
        BetScheme s = new BetScheme();
        s.setPlayId("01_ZC_2");
        s.setMultiple(1);
        
        List<BetMatch> matchs = new ArrayList<BetMatch>();
        matchs.add(toBM("400110", "3.20,2.20"));
        matchs.add(toBM("40023", "2.40"));
        matchs.add(toBM("4003310", "8.00,4.40,1.30"));
        matchs.add(toBM("4004310", "2.00,3.10,3.35"));
        matchs.add(toBM("400510", "3.75,4.05"));
        matchs.add(toBM("400630", "1.78,3.85"));
        matchs.add(toBM("400731", "3.10,3.20"));
        matchs.add(toBM("400810", "3.35,1.85"));
        matchs.add(toBM("400931", "2.62,3.15"));
        s.setMatchs(matchs);

        List<String> passTypes = new ArrayList<String>();
        passTypes.add("6@15");
        passTypes.add("5@16");
        passTypes.add("2@1");
        s.setPassTypes(passTypes);
        
        fixBetScheme = s;
    }
    
    private static BetMatch toBM(String code, String odds) {
        BetMatch bm = new BetMatch();

        bm.setCode(code);
        bm.setOdds(odds);

        return bm;
    }
    
	private int compareByScale2(BigDecimal real, String should) {
		BigDecimal rounded = real.setScale(2,  RoundingMode.HALF_UP);
		return rounded.compareTo(new BigDecimal(should));
	}
	
	@Test
	public void testComputeBonusDetail() throws BonusDetailException {
		prepareBetScheme();
		//BonusDetail detail = 
		bonusDetailService.computeBonusDetail(fixBetScheme);
		//System.out.println(detail);
	}

	public void prepareBetScheme2c1(int multiple) {
        BetScheme scheme = new BetScheme();
        scheme.setPlayId("01_ZC_2");
        scheme.setMultiple(multiple);
        
        List<BetMatch> matchs = new ArrayList<BetMatch>();
        matchs.add(toBM("4003310", "8.00,4.40,1.30"));
        matchs.add(toBM("4004310", "2.00,3.10,3.35"));
        scheme.setMatchs(matchs);

        List<String> passTypes = new ArrayList<String>();
        passTypes.add("2@1");
        scheme.setPassTypes(passTypes);
        
        fixBetScheme = scheme;
    }
	
	@Test
	public void test2c1() throws BonusDetailException {
		prepareBetScheme2c1(1);
		BonusDetail detail = bonusDetailService.computeBonusDetail(fixBetScheme);
		
		assertEquals(detail.getSupposeHits().size(), 1);
		
		SupposeHit hit = detail.getSupposeHits().get(0);
		assertEquals(0, compareByScale2(hit.getMinBonus(), "5.20"));
		assertEquals(0, compareByScale2(hit.getMaxBonus(), "53.60"));
	}
	
	@Test
	public void test2c1m2() throws BonusDetailException {
		prepareBetScheme2c1(2);
		BonusDetail detail = bonusDetailService.computeBonusDetail(fixBetScheme);
		
		assertEquals(detail.getSupposeHits().size(), 1);
		
		SupposeHit hit = detail.getSupposeHits().get(0);
		assertEquals(0, compareByScale2(hit.getMinBonus(), "10.40"));
		assertEquals(0, compareByScale2(hit.getMaxBonus(), "107.2"));
	}

	public void prepareBetScheme3c4(int multiple) {
        BetScheme scheme = new BetScheme();
        scheme.setPlayId("01_ZC_2");
        scheme.setMultiple(multiple);
        
        List<BetMatch> matchs = new ArrayList<BetMatch>();
        matchs.add(toBM("203430", "1.55,4.8"));
        matchs.add(toBM("203510", "3.35,5"));
        matchs.add(toBM("203630", "4.5,1.65"));
        scheme.setMatchs(matchs);

        List<String> passTypes = new ArrayList<String>();
        passTypes.add("3@4");
        scheme.setPassTypes(passTypes);
        
        fixBetScheme = scheme;
    }
	
	@Test
	public void test3c4m1() throws BonusDetailException {
		prepareBetScheme3c4(1);
		BonusDetail detail = bonusDetailService.computeBonusDetail(fixBetScheme);
		
		assertEquals(detail.getSupposeHits().size(), 2);
		
		//System.out.println(detail);
		
		SupposeHit hit = detail.getSupposeHits().get(0);	// 中2场
		assertEquals(0, compareByScale2(hit.getMinBonus(), "5.12"));
		assertEquals(0, compareByScale2(hit.getMaxBonus(), "48"));

		hit = detail.getSupposeHits().get(1);	// 中3场
		assertEquals(0, compareByScale2(hit.getMinBonus(), "43.69"));
		assertEquals(0, compareByScale2(hit.getMaxBonus(), "352.2"));
	}
	
	/**
	 * 3串4，选了4场
	 * @throws BonusDetailException
	 */
	@Test
	public void test3c4t4() throws BonusDetailException {
		prepareBetScheme3c4t4(new String[]{"3@4"});
		BonusDetail detail = bonusDetailService.computeBonusDetail(fixBetScheme);
		
		assertEquals(detail.getSupposeHits().size(), 3);
		
		//System.out.println(detail);
		
		SupposeHit hit = detail.getSupposeHits().get(0);	// 中2场
		assertEquals(0, compareByScale2(hit.getMinBonus(), "10.23"));
		assertEquals(2, hit.getMinDetails().size());
		assertEquals(0, compareByScale2(hit.getMaxBonus(), "96"));
		assertEquals(2, hit.getMaxDetails().size());

		hit = detail.getSupposeHits().get(1);	// 中3场
		assertEquals(0, compareByScale2(hit.getMinBonus(), "50.54"));
		assertEquals(7, hit.getMinDetails().size());
		assertEquals(0, compareByScale2(hit.getMaxBonus(), "488.4"));
		assertEquals(7, hit.getMaxDetails().size());

		hit = detail.getSupposeHits().get(2);	// 中4场
		assertEquals(0, compareByScale2(hit.getMinBonus(), "188.94"));
		assertEquals(16, hit.getMinDetails().size());
		assertEquals(0, compareByScale2(hit.getMaxBonus(), "1087.94"));
		assertEquals(16, hit.getMaxDetails().size());
	}

	private void prepareBetScheme3c4t4(String[] passes) {
		BetScheme scheme = new BetScheme();
        scheme.setPlayId("01_ZC_2");
        scheme.setMultiple(1);
        
        List<BetMatch> matchs = new ArrayList<BetMatch>();
        matchs.add(toBM("203330", "2.25,3.1"));
        matchs.add(toBM("203430", "1.55,4.8"));
        matchs.add(toBM("203510", "3.35,5"));
        matchs.add(toBM("203630", "4.5,1.65"));
        scheme.setMatchs(matchs);

        List<String> passTypes = new ArrayList<String>();
        passTypes.addAll(Arrays.asList(passes));
        scheme.setPassTypes(passTypes);
        
        fixBetScheme = scheme;
	}
	
	@Test
	public void test3c4And4c11() throws BonusDetailException {
		prepareBetScheme3c4t4(new String[]{"3@4", "4@11"});
		BonusDetail detail = bonusDetailService.computeBonusDetail(fixBetScheme);
		
		assertEquals(detail.getSupposeHits().size(), 3);
		
		System.out.println(detail);
		
		SupposeHit hit = detail.getSupposeHits().get(0);	// 中2场
		assertEquals(0, compareByScale2(hit.getMinBonus(), "15.35"));
		assertEquals(3, hit.getMinDetails().size());
		assertEquals(0, compareByScale2(hit.getMaxBonus(), "144"));
		assertEquals(3, hit.getMaxDetails().size());

		hit = detail.getSupposeHits().get(1);	// 中3场
		assertEquals(0, compareByScale2(hit.getMinBonus(), "81.56"));
		assertEquals(11, hit.getMinDetails().size());
		assertEquals(0, compareByScale2(hit.getMaxBonus(), "840.6"));
		assertEquals(11, hit.getMaxDetails().size());

		hit = detail.getSupposeHits().get(2);	// 中4场
		assertEquals(0, compareByScale2(hit.getMinBonus(), "360.41"));
		assertEquals(27, hit.getMinDetails().size());
		assertEquals(0, compareByScale2(hit.getMaxBonus(), "2620.62"));
		assertEquals(27, hit.getMaxDetails().size());
		
		assertNotNull(hit.getMaxDetails().get(0).getPassType());
	}
}
