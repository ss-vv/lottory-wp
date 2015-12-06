package com.davcai.lottery.platform.client.anruizhiying.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

public class AnRuiZhiYingBetContentUtil {

	public static String makeBetContent(List<Ticket> tickets) {
		String result=null;
		if(null!=tickets&&!tickets.isEmpty()){
			StringBuilder sb=new StringBuilder();
			for(Ticket ticket:tickets){
				if(StringUtils.isNotBlank(ticket.getActualCode())&&null!=ticket.getId()){
					sb.append(makeBetContent(ticket));
					sb.append("@");
					sb.append(ticket.getId());
					sb.append("|");
				}
			}
			
			result=StringUtils.removeEnd(sb.toString(), "|");
		}
		return result;
	}

	private static String makeBetContent(Ticket ticket) {
		StringBuilder sb=new StringBuilder(ticket.getActualCode());
		sb.append("$");
		sb.append(toPlayType(ticket));
		sb.append("~");
		sb.append(ticket.getMultiple());
		sb.append("~");
		sb.append(ticket.getNote());
		return sb.toString();
	}

	public static String toPlayType(Ticket ticket) {
		String playType="";
		if(isJC(ticket)){//是竞彩
			if(StringUtils.isNotBlank(ticket.getPassTypeId())){
				playType=ticket.getPassTypeId().replace("@", "X");
			}
			else{
				playType="单关";
			}
			
		}
		else if(isCTZC(ticket)){//是传统足彩
			playType="普通";
		}
		else if(isBD(ticket)){//是北单
			if(StringUtils.isNotBlank(ticket.getPassTypeId())){
				playType=ticket.getPassTypeId().replace("@", "串");
			}
			else{
				playType="单关";
			}
		}
		else if(isSSQ(ticket)){//是双色球
			if(StringUtils.endsWith(ticket.getPlayId(), "FS")){
				playType="复式";
			}
			else if(StringUtils.endsWith(ticket.getPlayId(), "DS")){
				playType="单式";
			}
			else if(StringUtils.endsWith(ticket.getPlayId(), "DT")){
				playType="胆拖";
			}
		}
		return playType;
	}

	private static boolean isSSQ(Ticket ticket) {
		PlayType lcPlayType = PlayType.valueOfLcPlayId(ticket.getPlayId());
		return lcPlayType.isSSQ();
	}

	private static boolean isBD(Ticket ticket) {
		PlayType lcPlayType = PlayType.valueOfLcPlayId(ticket.getPlayId());
		return lcPlayType.getLotteryId().equals(LotteryId.BJDC);
	}

	private static boolean isCTZC(Ticket ticket) {
		PlayType lcPlayType = PlayType.valueOfLcPlayId(ticket.getPlayId());
		return lcPlayType.getLotteryId().equals(LotteryId.CTZC);
	}

	private static boolean isJC(Ticket ticket) {
		PlayType lcPlayType = PlayType.valueOfLcPlayId(ticket.getPlayId());
		return lcPlayType.getLotteryId().equals(LotteryId.JCLQ)||lcPlayType.getLotteryId().equals(LotteryId.JCZQ);
	}

}
