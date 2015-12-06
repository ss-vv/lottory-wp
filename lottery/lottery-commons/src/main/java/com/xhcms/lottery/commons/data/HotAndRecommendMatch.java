package com.xhcms.lottery.commons.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 热门推荐赛事  
 * @author Administrator
 *
 */
public class HotAndRecommendMatch implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1327473931687198656L;
	private long id;
	private Long matchId;
    private String lottery;
    private String recommendCount;
    private String hostTeamName;
    private String guestTeamName;
    private String hostTeamPosition;
    private String guestTeamPostion;
    private String leagueName;
	public Long getMatchId() {
		return matchId;
	}
	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	public String getLottery() {
		return lottery;
	}
	public void setLottery(String lottery) {
		this.lottery = lottery;
	}
	public String getRecommendCount() {
		return recommendCount;
	}
	public void setRecommendCount(String recommendCount) {
		this.recommendCount = recommendCount;
	}
	public String getHostTeamName() {
		return hostTeamName;
	}
	public void setHostTeamName(String hostTeamName) {
		this.hostTeamName = hostTeamName;
	}
	public String getGuestTeamName() {
		return guestTeamName;
	}
	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}
	public String getHostTeamPosition() {
		return hostTeamPosition;
	}
	public void setHostTeamPosition(String hostTeamPosition) {
		this.hostTeamPosition = hostTeamPosition;
	}
	public String getGuestTeamPostion() {
		return guestTeamPostion;
	}
	public void setGuestTeamPostion(String guestTeamPostion) {
		this.guestTeamPostion = guestTeamPostion;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
    
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
    
    
}
