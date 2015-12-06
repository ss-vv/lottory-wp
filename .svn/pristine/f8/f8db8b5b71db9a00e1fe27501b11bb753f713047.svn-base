package com.unison.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.CheckMatchService;
import com.xhcms.lottery.commons.util.BetResolver;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.PlayType;

/**
 * 测试方案时用的工具类.
 * 
 * @author Yang Bo
 */
public class SchemeUtils {

	@Autowired
	private BetResolver betResolver;
	@Autowired
	private BetSchemeService betSchemeService;
	@Autowired
	private CheckMatchService checkMatchService;
	
	/**
	 * 生成一个测试用的竞彩足球方案。
	 * @param matches 赛事投注选项，竞彩，形如：201211014002-40023-false,201211024003-40033-false,201211024005-40053-false
	 * @return 测试方案
	 */
	public BetScheme createJCZQScheme(long uid, String matches){
		BetScheme scheme = new BetScheme();
		scheme.setSponsorId(uid);
		
		scheme.setLotteryId(Constants.JCZQ);
		String playId = PlayType.JCZQ_SPF.getPlayIdWithPass(false);
		scheme.setPlayId(playId);
		scheme.setOfftime(DateUtils.addHours(new Date(), 1));	// 1小时后停售
		scheme.setSaleStatus(EntityStatus.SCHEME_STOP);			// 可以出票、未扣冻结
		scheme.setStatus(EntityStatus.TICKET_INIT);				// 未出票
		scheme.setFollowedSchemeId(0L);
        String[] matchArr = matches.split(",");
        Pattern p = Pattern.compile("-");
        List<BetMatch> matchList = new ArrayList<BetMatch>(matchArr.length);
        for (int i = 0; i < matchArr.length; i++) {
            String match = matchArr[i];
            String[] m = p.split(match);
            BetMatch bm = new BetMatch();
            bm.setMatchId(Long.parseLong(m[0]));
            bm.setCode(m[1]);
            //加胆码
            bm.setSeed(Boolean.parseBoolean(m[2]));
            matchList.add(bm);
        }
        int code = checkMatchService.checkMatch(playId, matchList);
        if (code != 0){
        	return null;
        }
        scheme.setMatchNumber(matchList.size());
        scheme.setMatchs(matchList);
        scheme.setMultiple(3);
        // 过关方式
        String passTypes = "2@1,3@1";
        List<String> pts = new ArrayList<String>();
        String[] passTypeArr = passTypes.split(",");
        for (int i = 0; i < passTypeArr.length; i++) {
            pts.add(passTypeArr[i]);
        }
        scheme.setPassTypes(pts);
        scheme.setPassTypeIds("," + passTypes + ",");
        scheme.setPassTypeIds(passTypes.replace(',', ' ').replaceAll("@", "串"));

        Bet bet = betResolver.resolve(scheme);
        scheme.setCreatedTime(new Date());
        
        scheme.setMaxBonus(new BigDecimal(bet.getMaxBonus()));
        scheme.setTotalAmount(bet.getNote() * 2);		// 方案总金额
        scheme.setBetNote(bet.getNote());				// 方案总注数
        scheme.setBuyAmount(scheme.getTotalAmount());	// 发起人认购金额

        scheme.setExpectBonus(new BigDecimal(bet.getMaxBonus()));
        scheme.setPreTaxBonus(BigDecimal.ZERO);
        scheme.setAfterTaxBonus(BigDecimal.ZERO);
        betSchemeService.bet(uid, scheme, bet.getTickets());
		return scheme;
	}
}
