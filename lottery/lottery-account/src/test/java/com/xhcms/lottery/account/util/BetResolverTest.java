package com.xhcms.lottery.account.util;

import java.util.ArrayList;
import java.util.List;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.util.BetResolver;
import com.xhcms.lottery.commons.util.BetStrategy;
import com.xhcms.lottery.commons.utils.internal.JCBetStrategy;

public class BetResolverTest {

    public static void main(String[] args){
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
        matchs.add(toBM("4007931", "3.10,3.20"));
        matchs.add(toBM("400810", "3.35,1.85"));
        matchs.add(toBM("400931", "2.62,3.15"));
        s.setMatchs(matchs);

        List<String> passTypes = new ArrayList<String>();
        passTypes.add("6@15");
        passTypes.add("5@16");
        passTypes.add("2@1");
        s.setPassTypes(passTypes);

        BetResolver br = new BetResolver();
        br.setStrategies(new BetStrategy[]{new JCBetStrategy()});
        Bet bet = br.resolve(s);

        for(Ticket t: bet.getTickets()){
            System.out.println(t.getPassTypeId() + "\t" + t.getCode() + "\t" + t.getMoney());
        }
    }
    
    private static BetMatch toBM(String code, String odds) {
        BetMatch bm = new BetMatch();

        bm.setCode(code);
        bm.setOdds(odds);

        return bm;
    }
}
