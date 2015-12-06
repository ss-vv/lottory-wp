package com.davcai.lottery.push.common.model;

import java.util.Date;
import java.util.Map;




import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class BasketballMatchMessage extends MatchMessage {
	
	

	private static final String MILDFIELDS = "50"; //



	public BasketballMatchMessage() {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6655998635931198046L;

	private static final String CANCAL = "-4";

	private static final String UNDETERMINED = "-2";

	private static final String INTERUPT = "-3";

	private static final String DELAY = "-5";

	private static final String FINISH = "-1";
	
	private static final String FIRST_SECTION = "1";
	
	private static final String SECOND_SECTION = "2";
	
	private static final String THREE_SECTION = "3";
	
	private static final String FOUR_SECTION = "4";
	
	private static final String UNOPEN="0";//未开
	
	private String remainTime;//小节剩余时间
	
	private int homeOne,guestOne;//主客队一节得分
	
	private int homeTwo,guestTwo;//主客队二节得分
	
	private int homeThree,guestThree;//主客队三节得分
	
	private int homeFour,guestFour;//主客队三节得分
	
	private String explainContent;//直播内容
	
	private int homeAddTimeScore,guestAddTimeScore;//主客队加时得分数

	private String stateDesc;
	
	

	@Override
	public boolean isSame(PushMessage oldPushMessage) {
		boolean result = false;
		boolean superIsSame = super.isSame(oldPushMessage);
		if (oldPushMessage instanceof BasketballMatchMessage) {
			BasketballMatchMessage basketballMatchMessage = (BasketballMatchMessage) oldPushMessage;
			result = (basketballMatchMessage.guestFour == this.guestFour
					&& basketballMatchMessage.guestOne == this.guestOne
					&& basketballMatchMessage.guestThree == this.guestThree
					&& basketballMatchMessage.guestTwo ==this.guestTwo
					&& basketballMatchMessage.homeFour ==this.homeFour
					&& basketballMatchMessage.homeThree==this.homeThree
					&& basketballMatchMessage.homeOne ==this.homeOne
					&& basketballMatchMessage.homeFour==this.homeFour
					&& basketballMatchMessage.homeTwo==this.homeTwo
					&& basketballMatchMessage.homeAddTimeScore==this.homeAddTimeScore
					&& basketballMatchMessage.guestAddTimeScore==this.guestAddTimeScore
					);
		}

		return superIsSame && result;
	}
	@Override
	public boolean isSameForHuanxin(PushMessage oldPushMessage){
		boolean result = false;
		boolean superIsSame = false;
		if (oldPushMessage instanceof MatchMessage) {
				MatchMessage matchMessage = (MatchMessage) oldPushMessage;
				superIsSame = StringUtils.equals(matchMessage.getGuestTeamName(),
						this.getGuestTeamName())
						&& StringUtils.equals(matchMessage.getHomeTeamName(),
								this.getHomeTeamName())
						&& StringUtils.equals(matchMessage.getMatchId(), this.getMatchId())
						&& (
								(
										null != matchMessage.getScheduleMatchTime()
										&& null != this.getScheduleMatchTime() 
										&& matchMessage.getScheduleMatchTime().equals(this.getScheduleMatchTime())	
								)||
								(
										null==matchMessage.getScheduleMatchTime()
										&&
										null==this.getScheduleMatchTime()
								)
								
								
						)
						
						||StringUtils.equals(this.getState(), UNOPEN);
			}
		
			if(oldPushMessage instanceof BasketballMatchMessage){
				BasketballMatchMessage basketballMatchMessage = (BasketballMatchMessage) oldPushMessage;
				result = !(StringUtils.isBlank(this.getRemainTime())
								&&StringUtils.isNotBlank(basketballMatchMessage.getRemainTime())
							)
//						||
//						!(StringUtils.equals(this.getState(), FINISH)
//								&&(!FINISH.equals(basketballMatchMessage.getState())))
						&&(basketballMatchMessage.guestFour == this.guestFour
									&& basketballMatchMessage.guestOne == this.guestOne
									&& basketballMatchMessage.guestThree == this.guestThree
									&& basketballMatchMessage.guestTwo ==this.guestTwo
									&& basketballMatchMessage.homeFour ==this.homeFour
									&& basketballMatchMessage.homeThree==this.homeThree
									&& basketballMatchMessage.homeOne ==this.homeOne
									&& basketballMatchMessage.homeFour==this.homeFour
									&& basketballMatchMessage.homeTwo==this.homeTwo
									&& basketballMatchMessage.homeAddTimeScore==this.homeAddTimeScore
									&& basketballMatchMessage.guestAddTimeScore==this.guestAddTimeScore
								)
						&&(!(StringUtils.isNotBlank(this.explainContent)&& !this.explainContent.equals(basketballMatchMessage.explainContent)))
						|| StringUtils.equals(CANCAL, this.getState())
						|| StringUtils.equals(DELAY, this.getState())
						|| StringUtils.equals(UNDETERMINED, this.getState())
						|| StringUtils.equals(INTERUPT, this.getState())
						;
			}
			return superIsSame&&result;
		
	}
	@Override
	protected void makeExtendDataMap(Map<String, Object> data) {
		super.makeExtendDataMap(data);
		data.put("stateDesc", stateDesc);
		data.put("remainTime", remainTime);
		data.put("homeOne", homeOne);
		data.put("guestOne", guestOne);
		data.put("homeTwo", homeTwo);
		data.put("guestTwo", guestTwo);
		data.put("homeThree", homeThree);
		data.put("guestThree", guestThree);
		data.put("homeFour", homeFour);
		data.put("guestFour", guestFour);
		data.put("explainContent", explainContent);
		data.put("homeAddTimeScore", homeAddTimeScore);
		data.put("guestAddTimeScore", guestAddTimeScore);
	}
	

	@Override
	public MessageType getType() {
		return MessageType.BASKETBALL;
	}

	@Override
	protected String getDefaultState() {
		return UNOPEN;
	}
	@Override
	public void checkAndSetShouldUnsubscribe() {
		if (StringUtils.equals(getState(), CANCAL)
				|| StringUtils.equals(getState(), UNDETERMINED)
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
	public String getStateDesc() {
		String result="";
		String state=getState();
		if(StringUtils.isNotBlank(state)){
			switch(getState()){
			case "0":{result="未开赛";break;}
			case "1":{result="第一节";result = addFinishSuffix(result);break;}
			case "2":{result="第二节";result = addFinishSuffix(result);break;}
			case "3":{result="第三节";result = addFinishSuffix(result);break;}
			case "4":{result="第四节";result = addFinishSuffix(result);break;}
			case "5":{result="加时一";result = addFinishSuffix(result);break;}
			case "6":{result="加时二";result = addFinishSuffix(result);break;}
			case "7":{result="加时三";result = addFinishSuffix(result);break;}
			case MILDFIELDS:{result="中场";break;}
			case "-2":{result="待定";break;}
			case "-3":{result="中断";break;}
			case "-5":{result="推迟";break;}
			case "-1":{result="完场";break;}
			case "-4":{result="取消";break;}
			default:result=null;
			
			}
		}
		
		return result;
	}
	
	public String getHuanxinStateDesc(){
		return this.stateDesc;
	}
	private String addFinishSuffix(String result) {
		if(StringUtils.isBlank(remainTime)){result=result+"结束";}
		return result;
	}

	public String getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(String remainTime) {
		this.remainTime = remainTime;
	}

	public int getHomeOne() {
		return homeOne;
	}

	public void setHomeOne(int homeOne) {
		this.homeOne = homeOne;
	}

	public int getGuestOne() {
		return guestOne;
	}

	public void setGuestOne(int guestOne) {
		this.guestOne = guestOne;
	}

	public int getHomeTwo() {
		return homeTwo;
	}

	public void setHomeTwo(int homeTwo) {
		this.homeTwo = homeTwo;
	}

	public int getGuestTwo() {
		return guestTwo;
	}

	public void setGuestTwo(int guestTwo) {
		this.guestTwo = guestTwo;
	}

	public int getHomeThree() {
		return homeThree;
	}

	public void setHomeThree(int homeThree) {
		this.homeThree = homeThree;
	}

	public int getGuestThree() {
		return guestThree;
	}

	public void setGuestThree(int guestThree) {
		this.guestThree = guestThree;
	}

	public int getHomeFour() {
		return homeFour;
	}

	public void setHomeFour(int homeFour) {
		this.homeFour = homeFour;
	}

	public int getGuestFour() {
		return guestFour;
	}

	public void setGuestFour(int guestFour) {
		this.guestFour = guestFour;
	}

	public String getExplainContent() {
		return explainContent;
	}

	public void setExplainContent(String explainContent) {
		this.explainContent = explainContent;
	}

	public int getHomeAddTimeScore() {
		return homeAddTimeScore;
	}

	public void setHomeAddTimeScore(int homeAddTimeScore) {
		this.homeAddTimeScore = homeAddTimeScore;
	}

	public int getGuestAddTimeScore() {
		return guestAddTimeScore;
	}

	public void setGuestAddTimeScore(int guestAddTimeScore) {
		this.guestAddTimeScore = guestAddTimeScore;
	}
	
	
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public String toSubType() {
		return "0";//竞篮类型
	}
	public void makeStateDesc(PushMessage oldPushMessage) {
		if(oldPushMessage instanceof BasketballMatchMessage){
			BasketballMatchMessage oldBasketBallPushMessage = (BasketballMatchMessage)oldPushMessage;
			if(StringUtils.equals(oldBasketBallPushMessage.getState(), FIRST_SECTION)&&StringUtils.equals(getRemainTime(), "")){
				setStateDesc("第一节结束");
			}else if(StringUtils.equals(oldBasketBallPushMessage.getState(), SECOND_SECTION)&&StringUtils.equals(getRemainTime(), "")){
				setStateDesc("第二节结束");
			}else if(StringUtils.equals(oldBasketBallPushMessage.getState(), THREE_SECTION)&&StringUtils.equals(getRemainTime(), "")){
				setStateDesc("第三节结束");
			}else if(StringUtils.equals(oldBasketBallPushMessage.getState(), FOUR_SECTION)){
				if(StringUtils.equals(getState(), FINISH)){
					setStateDesc("完场");
				}
				setStateDesc("第四节结束");
			}else{
				setStateDesc("完场");
			}
		}
		
	}
	
	private Date matchTime;
	private String matchCode;
	public Date getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}
	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
}
