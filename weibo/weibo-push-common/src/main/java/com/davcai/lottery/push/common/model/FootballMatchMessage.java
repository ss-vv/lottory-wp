package com.davcai.lottery.push.common.model;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.xhcms.lottery.utils.DateUtils;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FootballMatchMessage extends MatchMessage {
	
	public FootballMatchMessage() {
		super();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2349974105570669040L;


	private static final String FINISH = "-1";//完场


	private static final String DELAY = "-14";//延迟


	private static final String INTERUPT = "-13";//中断


	private static final String YAOZHAN = "-12";//腰斩


	private static final String UNDETERMINED = "-11";//待定


	private static final String CANCAL = "-10";//取消


	private static final String UNOPEN = "0";


	private static final String SENCOND_HALF = "3";
	
	private static final String MIDDLE = "2";
	private static final String FIRST_HALF = "1";
	
	
	private int guestTeamRed;
	private int homeTeamRed;
	private int homeTeamYellow;
	private int guestTeamYellow;
	private Date halfStartTime;//上半场为上半场开场时间、下半场为下半场开场时间
	public int getGuestTeamRed() {
		return guestTeamRed;
	}
	public void setGuestTeamRed(int guestTeamRed) {
		this.guestTeamRed = guestTeamRed;
	}
	public int getHomeTeamRed() {
		return homeTeamRed;
	}
	public void setHomeTeamRed(int homeTeamRed) {
		this.homeTeamRed = homeTeamRed;
	}
	public int getHomeTeamYellow() {
		return homeTeamYellow;
	}
	public void setHomeTeamYellow(int homeTeamYellow) {
		this.homeTeamYellow = homeTeamYellow;
	}
	public int getGuestTeamYellow() {
		return guestTeamYellow;
	}
	public void setGuestTeamYellow(int guestTeamYellow) {
		this.guestTeamYellow = guestTeamYellow;
	}
	
	@Override
	public void checkAndSetShouldUnsubscribe() {
		if (StringUtils.equals(getState(), CANCAL)
				|| StringUtils.equals(getState(), UNDETERMINED)
				|| StringUtils.equals(getState(), YAOZHAN)
				|| StringUtils.equals(getState(), INTERUPT)
				|| StringUtils.equals(getState(), DELAY)
				|| StringUtils.equals(getState(), FINISH)) {
			this.setShouldUnsubscribe(true);
		}
		else{
			this.setShouldUnsubscribe(false);
		}
	}

	@Override
	public boolean isSame(PushMessage oldPushMessage) {
		boolean result = false;
		boolean superIsSame = super.isSame(oldPushMessage);
		if (oldPushMessage instanceof FootballMatchMessage) {
			FootballMatchMessage footballMatchMessage = (FootballMatchMessage) oldPushMessage;
			result =  (
							(	null != footballMatchMessage.halfStartTime
								&& null != this.halfStartTime 
								&& footballMatchMessage.halfStartTime.equals(this.halfStartTime)
							)
							||
							(
								null==footballMatchMessage.halfStartTime
								&&null==this.halfStartTime
							)
							||
							((super.getState().equals(MIDDLE)&&
									null==this.halfStartTime&&
									null!=footballMatchMessage.halfStartTime))
							||
							(super.getState().equals(UNOPEN))
						)
					;
		}

		return superIsSame && result;
	}
	@Override
	public MessageType getType() {
		return MessageType.FOOTBALL;
	}
	
	@Override
	protected String getDefaultState() {
		return UNOPEN;
	}
	public Date getHalfStartTime() {
		return halfStartTime;
	}
	public void setHalfStartTime(Date halfStartTime) {
		this.halfStartTime = halfStartTime;
	}
	@Override
	protected void makeExtendDataMap(Map<String, Object> data) {
		super.makeExtendDataMap(data);
		data.put("guestTeamRed", guestTeamRed);
		data.put("homeTeamRed", homeTeamRed);
		data.put("homeTeamYellow", homeTeamYellow);
		data.put("guestTeamYellow", guestTeamYellow);
		data.put("halfStartTime", halfStartTime);
		data.put("stateDesc", getStateDesc());
		Date now=new Date();
		data.put("playingTime", getPlayingTime(now));
	}
	
	
	public  int getPlayingTime(Date now) {
		int result=0;
		String state=getState();
		if(StringUtils.isNotBlank(state)){
			switch(state){
			case "0":{result = handleNotReturnPlayingTime();break;}
			case "1":{
				result = compute(now);
				break;}
			case "2":{result = handleNotReturnPlayingTime();break;}
			case "3":{result=compute(now);break;}
			case "4":{result = handleNotReturnPlayingTime();break;}
			case "-11":{result = handleNotReturnPlayingTime();break;}
			case "-12":{result = handleNotReturnPlayingTime();break;}
			case "-13":{result = handleNotReturnPlayingTime();break;}
			case "-14":{result = handleNotReturnPlayingTime();break;}
			case "-1":{result = handleNotReturnPlayingTime();break;}
			case "-10":{result = handleNotReturnPlayingTime();break;}
			default:result = handleNotReturnPlayingTime();
			
			}
		}
		return result;
	}
	private int compute(Date now) {
		int result=0;
		Date halfStartTime=this.getHalfStartTime();
		if(null!=halfStartTime&&now.after(halfStartTime)){//当前时间大于比赛开赛时间
			long diff = now.getTime()-halfStartTime.getTime();//毫秒数
			if(StringUtils.equals(getState(), FIRST_HALF)){//上半场，进行分钟数=当前时间-开赛时间
				result=(int) diff/1000/60;
			}
			else if(StringUtils.equals(getState(), SENCOND_HALF)){//下半场，进行分钟数=当前时间-下半场开场时间+上半场时间(45分钟)
				result=(int) ((diff+45*60*1000)/1000/60);
			}
			
		}
		
		
		return result;
	}
	private int handleNotReturnPlayingTime() {
		return 0;
	}
	@Override
	public String getStateDesc() {
		String result="";
		String state=getState();
		if(StringUtils.isNotBlank(state)){
			switch(state){
			case "0":{result="未开赛";break;}
			case "1":{result="上半场";break;}
			case "2":{result="中场";break;}
			case "3":{result="下半场";break;}
			case "4":{result="加时";break;}
			case "-11":{result="待定";break;}
			case "-12":{result="腰斩";break;}
			case "-13":{result="中断";break;}
			case "-14":{result="推迟";break;}
			case "-1":{result="完场";break;}
			case "-10":{result="取消";break;}
			default:result=null;
			
			}
		}
		
		return result;
	}
	private String matchTime;
	private String matchCode;
	public String getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	private String liveContent;
	public String getLiveContent() {
		return liveContent;
	}
	public void setLiveContent(String liveContent) {
		this.liveContent = liveContent;
	}
	
}
