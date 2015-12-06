package com.xhcms.lottery.dc.platform.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.qiutan.QiutanCTZCIssueInfo;
import com.davcai.lottery.platform.client.qiutan.QiutanCTZCMatch;
import com.davcai.lottery.platform.util.CTZCIssueSplitUtil;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.dc.task.ticket.OpenSaleIntercessor;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.DateUtils;

@Service
public class QiutanCTZCStoreParseImpl implements QiutanCTZCStoreParse{

	@Autowired
	@Qualifier("openSaleIntercessor")
	private OpenSaleIntercessor openSaleIntercessor;
	private int haltSales = 0;
	
	@Override
	public List<CTFBMatch> parseQiuTanCTZTToCTFBMatch(
			LotteryPlatformBaseResponse response, PlayType playType) {
		QiutanCTZCIssueInfo qiutanResponse=(QiutanCTZCIssueInfo)response;
		List<CTFBMatch> ctfbMatchList=new ArrayList<CTFBMatch>();
		String issueNumber=CTZCIssueSplitUtil.splitCTZCIssueNumber(qiutanResponse.getIssuenum());
		String playId=playType.getShortPlayStr();
		if(response!=null && !qiutanResponse.getCtzcMatchs().isEmpty()){
			List<QiutanCTZCMatch> qiutanList=qiutanResponse.getCtzcMatchs();
			for(int i=0;i<qiutanList.size();i++){
				QiutanCTZCMatch qt=qiutanList.get(i);
				CTFBMatch ctfbMatch=new CTFBMatch();
				ctfbMatch.setId(issueNumber+playId+qt.getMatchid());
				ctfbMatch.setIssueNumber(issueNumber);
				ctfbMatch.setPlayId(playId);
				ctfbMatch.setMatchId(Long.parseLong(qt.getMatchid()));
				ctfbMatch.setLeagueName(qt.getSclass());
				ctfbMatch.setHomeTeamName(qt.getHometeam());
				ctfbMatch.setGuestTeamName(qt.getGuestteam());
				ctfbMatch.setOfftime(org.apache.commons.lang.time.DateUtils.addMinutes(DateUtils.converTime(qt.getMatchtime()), -1));
				ctfbMatch.setPlayingTime(DateUtils.converTime(qt.getMatchtime()));
				Calendar playTime = Calendar.getInstance();
				playTime.setTime(ctfbMatch.getPlayingTime());
				playTime.add(Calendar.MINUTE, haltSales);
				Date dealLine=openSaleIntercessor.parseEntrustDeadline(playType.getLotteryId().name(), playTime.getTime());
				ctfbMatch.setEntrustDeadline(dealLine);
				ctfbMatchList.add(ctfbMatch);
			}
		}
		return ctfbMatchList;
	}
	public OpenSaleIntercessor getOpenSaleIntercessor() {
		return openSaleIntercessor;
	}
	public void setOpenSaleIntercessor(OpenSaleIntercessor openSaleIntercessor) {
		this.openSaleIntercessor = openSaleIntercessor;
	}
	public int getHaltSales() {
		return haltSales;
	}
	public void setHaltSales(int haltSales) {
		this.haltSales = haltSales;
	}
}
