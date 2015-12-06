package com.xhcms.lottery.commons.utils.internal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.xhcms.commons.lang.Assert;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.util.Caller;
import com.xhcms.lottery.commons.util.Combination;
import com.xhcms.lottery.commons.utils.internal.impl.ZMBetCodeCreatorImpl;
import com.xhcms.lottery.lang.AppCode;

/**
 * 
 * <p>Title: 过关方式分解计算</p>
 * <p>Description: 可用于计算指定过关方式的注数和理论极限赔率</p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged, Wang lei, Yang Bo, Li Lei
 * @version 1.0.0
 */
public class PassCaller implements Caller {
	private Logger log = LoggerFactory.getLogger(getClass());
	private Bet bet;
    private List<BetMatch> matchs;
    private int passType;
    private LinkedList<Ticket> tickets = new LinkedList<Ticket>();
    private Set<Integer> seedIndexs = new HashSet<Integer>();//胆码游标集合
    private List<String> excludeIndex;
    private List<String> includeIndex;

    public PassCaller(Bet bet, List<BetMatch> matchs) {
        this.bet = bet;
        this.matchs = matchs;
        //计算胆码数量和胆码集合
        for(int i=0;i<matchs.size();i++){
        	if(matchs.get(i).isSeed()){
        		this.seedIndexs.add(i);
        	}
        }
    }
    
    public void reset(int passType){
        this.passType = passType;
        tickets.clear();
    }

    @Override
    public void call(int[] cur) {
        int note = Combination.mn(passType, bet.getOpts(), cur);
        // 单票注数过大
        Assert.isTrue(note <= 10000, AppCode.EXCEED_MAX_NOTE);
        Ticket t = create(cur);
        if (t != null) {
            bet.addNote(note);
            double ticketBonus = Combination.mn(passType, bet.getMaxOdds(), cur);
            bet.addMaxBonus(ticketBonus);
        	t.setNote(note);
        	t.setMoney(note * 2);
        	
        	BigDecimal bonusDecimal = new BigDecimal(ticketBonus);
        	bonusDecimal = bonusDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
        	bonusDecimal = bonusDecimal.multiply(new BigDecimal(2));
        	
        	t.setExpectBonus(bonusDecimal);
        	t.setMatches(checkJcOfficialMatchs(cur));
        	tickets.add(t);
        }
    }

    public Ticket create(int[] cur) {
    	PlatformBetCodeCreator pbc = new ZMBetCodeCreatorImpl();
        Ticket ticket = pbc.create(bet.getPlayId(), cur, matchs);
        return ticket;
    }

    private List<PlayMatch> checkJcOfficialMatchs(int[] cur) {
    	List<PlayMatch> ms = new ArrayList<PlayMatch>();
    	if(null != matchs && matchs.size() > 0) {
			for(int i=0; i<cur.length; i++) {
				if(cur[i] >= matchs.size()) {
					log.debug("赛事索引找不到竞彩赛事ID。");
					ms = null;
					break;
				}
				BetMatch m = matchs.get(cur[i]);
				if(m.getJcOfficialMatchId() != null && m.getJcOfficialMatchId() > 0) {
					PlayMatch pm = new PlayMatch();
					BeanUtils.copyProperties(m, pm);
					ms.add(pm);
				}
			}
			//校验投注赛事和竞彩官网赛事ID是否能一一对应上
			if(cur.length != ms.size()) {
				ms = null;
			}
		}
    	return ms;
    }
    
	/**
	 * 复合单关投注内容拆分
	 * @param cur
	 * @return
	 * 返回第一个参数：投注内容
	 * 第二个参数：玩法ID
	 */
	private String[] createCodeForFHSinglePass(int[] cur){
		String[] result = new String[2];
        StringBuilder buf = new StringBuilder();
        
        buf.append(matchs.get(cur[0]).getCode()).append('-');
        BetMatch m = matchs.get(cur[0]);
        buf.deleteCharAt(buf.length() - 1);
        
        result[0] = buf.toString();
        result[1] = m.getPlayId();
        return result;
    }
	
	public LinkedList<Ticket> getTickets() {
        return tickets;
    }

	public Set<Integer> getSeedIndexs() {
		return seedIndexs;
	}

	public void setSeedIndexs(Set<Integer> seedIndexs) {
		this.seedIndexs = seedIndexs;
	}

	@Override
	public Set<Integer> getSeeds() {
		return this.getSeedIndexs();
	}

	@Override
	public List<String> getExcludeIndex() {
		return excludeIndex;
	}

	public void setExcludeIndex(List<String> excludeIndex) {
		this.excludeIndex = excludeIndex;
	}

	@Override
	public List<String> getIncludeIndex() {
		return includeIndex;
	}

	public void setIncludeIndex(List<String> includeIndex) {
		this.includeIndex = includeIndex;
	}

	@Override
	public Bet getBet() { 
		return bet;
	}

	@Override
	public List<BetMatch> getMatchs() {
		return matchs;
	}
}
