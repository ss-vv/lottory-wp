package com.xhcms.lottery.commons.persist.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "lt_bet_scheme")
public class BetSchemeWithAssocPO extends BetSchemePOBase {
	private static final long serialVersionUID = 3144656710195198310L;
	
	//TODO 需要优化
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id", referencedColumnName="sponsor_id")
	private Set<UserScorePO> userScores;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="play_id", updatable=false, insertable=false)
	private PlayPO play;

	//TODO 需要优化
	@OneToMany
	@JoinColumn(name="followed_scheme_id", referencedColumnName="id")
	private Set<BetSchemePO> followSchemes;
	
	public BetSchemeWithAssocPO(){
	}

	public Set<UserScorePO> getUserScores() {
		return userScores;
	}

	public void setUserScores(Set<UserScorePO> userScores) {
		this.userScores = userScores;
	}

	public PlayPO getPlay() {
		return play;
	}

	public void setPlay(PlayPO play) {
		this.play = play;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public Set<BetSchemePO> getFollowSchemes() {
		return followSchemes;
	}

	public void setFollowSchemes(Set<BetSchemePO> followSchemes) {
		this.followSchemes = followSchemes;
	}

	
	
}
