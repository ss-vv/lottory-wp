package com.xhcms.lottery.commons.data;

import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PlayMatch extends BetMatch {

    private static final long serialVersionUID = -5520524098996151283L;

    private String name;
    private String leagueName;
    
    /**默认比分（让球、预设总分）*/
    private String concedePoints;
    private String score;
    private String score1;
    private String score2;
    private String score3;
    private String score4;
    private String result;
    private String resultOdd;
    private String betCode;
    private Date playingTime;
    private int status;
    
    private String homeName;
    private String guestName;
    private String resultView; 
    
    private String betContent4MixFH;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConcedePoints() {
        return concedePoints;
    }

    public void setConcedePoints(String concedePoints) {
        this.concedePoints = concedePoints;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getScore3() {
        return score3;
    }

    public void setScore3(String score3) {
        this.score3 = score3;
    }

    public String getScore4() {
        return score4;
    }

    public void setScore4(String score4) {
        this.score4 = score4;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultOdd() {
		return resultOdd;
	}

	public void setResultOdd(String resultOdd) {
		this.resultOdd = resultOdd;
	}

	public String getBetCode() {
        return betCode;
    }

    public void setBetCode(String betCode) {
        this.betCode = betCode;
    }

    public Date getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(Date playingTime) {
        this.playingTime = playingTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public String getHomeName() {
		return homeName;
	}

	public void setHomeName(String homeName) {
		this.homeName = homeName;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}


	public String getResultView() {
		return resultView;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getBetContent4MixFH() {
		return betContent4MixFH;
	}

	public void setBetContent4MixFH(String betContent4MixFH) {
		this.betContent4MixFH = betContent4MixFH;
	}
}
