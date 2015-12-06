package com.xhcms.lottery.commons.utils.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.util.BetStrategy;
import com.xhcms.lottery.commons.util.Combination;

public class BJDCBetStrategy implements BetStrategy {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public boolean match(String playId) {
		
		return Pattern.matches("\\d{2}_BJDC_[A-Z]+", playId);
		
	}

	@Override
	public Bet resolve(BetScheme s) {
        Bet bet = createBet(s.getMatchs());
        bet.setPlayId(s.getPlayId());
        int multi = s.getMultiple();
        //-----------------
        PassCaller passCaller = new PassCaller(bet, s.getMatchs());
        for (String passType : s.getPassTypes()) {
        	if(StringUtils.isNotBlank(passType)){
	            LinkedList<Ticket> ts = resolveTicket(bet, passCaller, passType);
	            // 处理投注倍数
	            for(int i = ts.size(); i > 0; i--){
	                checkTicket(ts, multi, passType, s.getPlayId());
	            }
	            try {
            		Translator.translateTicketCodeToPFormat(ts);
				} catch (TranslateException e) {
					String message = String.format("can not translate bet code of ticket for scheme '%s'", 
							s.getId());
					logger.error(message, e);
					throw new RuntimeException(message, e);
				}
	            for(int i=0; i<ts.size(); i++) {
	            	ts.get(i).setIssue(s.getIssueNumber());
	            }
	            bet.addTickets(ts);
        	}
        }
        
        // 按投注倍数计算方案总数据
        bet.setNote(bet.getNote() * multi);
        bet.setMaxBonus(bet.getMaxBonus() * multi * 2*0.65);//北京单场计算最高奖金

        return bet;
	}
	
   private Bet createBet(List<BetMatch> matchs) {
        Bet bet = new Bet();
        
        double[] odds = new double[matchs.size()];
        int[] opts = new int[matchs.size()];
        Pattern p = Pattern.compile(",");
        for (int i = 0; i < odds.length; i++) {
            String[] val = p.split(matchs.get(i).getOdds());
            opts[i] = val.length;
            odds[i] = Double.parseDouble(val[0]);
            
            for(int j = 1; j < val.length; j++) {
                odds[i] = Math.max(odds[i], Double.parseDouble(val[j]));
            }
        }

        bet.setOpts(opts);
        bet.setMaxOdds(odds);

        return bet;
    }
    /**
     * 按规则拆分彩票，保证出票张数最少，且每张票符合单张金额不超过20000元限制
     * @param bet
     * @param passCaller
     * @param passType
     */
    private LinkedList<Ticket> resolveTicket(Bet bet, PassCaller passCaller, String passType) {
        int[] mn = parsePassType(passType);
        if (mn[0] > bet.getOpts().length) {
            throw new IllegalArgumentException("Invalid PassType: " + passType + ", Matchs: " + bet.getOpts().length);
        }
      //m串n的m必须大于等于胆码数量
        if(mn[0] < passCaller.getSeedIndexs().size()){
        	throw new IllegalArgumentException("Invalid PassType: " + passType + ", Seed: " + passCaller.getSeedIndexs().size());
        }

        passCaller.reset(mn[0] * 1000 + mn[1]);
        int[] index = new int[bet.getOpts().length];
        for (int i = 0; i < index.length; i++) {
            index[i] = i;
        }

        Combination.combinate(index, index.length, mn[0], passCaller);
        return passCaller.getTickets();
    }
    // 分解过关方式
    private int[] parsePassType(String passType) {
        String[] mn = passType.split("@");
        if (mn.length != 2) {
            throw new IllegalArgumentException("Unsupport PassType: " + passType);
        }

        return new int[] { Integer.parseInt(mn[0]), Integer.parseInt(mn[1]) };
    }
    private void checkTicket(LinkedList<Ticket> ts, int multi, String passType, String playId){
        Ticket t = ts.poll();
        int max = Math.min(99, 20000 / t.getMoney()); // 该票最大有效倍数
        // 对混合中的所有玩法相同的票，playId是已经设置过了的，不需要再设置。
        if (StringUtils.isNotBlank(t.getPlayId())){
        	playId = t.getPlayId();
        }
        int m = 0;
        while(multi > 0){
            m = (multi < max ? multi : max);
            multi -= max;

            Ticket tk = new Ticket();
            tk.setCode(t.getCode());
            tk.setMultiple(m);
            tk.setNote(t.getNote() * m);
            tk.setMoney(t.getMoney() * m);
            tk.setPassTypeId(passType);
            tk.setPlayId(playId);
            ts.add(tk);
        }
    }
}
