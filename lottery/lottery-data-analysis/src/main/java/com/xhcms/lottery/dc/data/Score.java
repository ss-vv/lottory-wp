/**
 * 
 */
package com.xhcms.lottery.dc.data;

/**
 * 让球及让分、预设总分装载对象
 * 
 * @author langhsu
 * 
 */
public class Score {
	private String playId;
	private long matchId;
	private float score;
	
	public Score(String playId, long matchId, float score) {
		this.playId = playId;
		this.matchId = matchId;
		this.score = score;
	}
	
	public String getId() {
		return matchId + playId;
	}
	
	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public long getMatchId() {
		return matchId;
	}

	public void setMatchId(long matchId) {
		this.matchId = matchId;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (matchId ^ (matchId >>> 32));
		result = prime * result + ((playId == null) ? 0 : playId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Score other = (Score) obj;
		if (matchId != other.matchId)
			return false;
		if (playId == null) {
			if (other.playId != null)
				return false;
		} else if (!playId.equals(other.playId))
			return false;
		return true;
	}}
