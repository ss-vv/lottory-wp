package com.xhcms.lottery.dc.data;



/**
 * 北京单场赛果
 * @author haohaowu
 *
 */
public class BDResult {

	private long matchId;
	private String issueNumber;//期
	private String halfScore; // 半场比分
	private String score; // 全场比分
	private String result;  //北单胜负赛果
	private double sfpSp;
	private double bfSp;
	private double jqsSp;
	private double bqcSp;
	private double sxdsSp;	//上下单双
	private double sfSp;    //北单胜负 赔率
	private int status;
	public long getMatchId() {
		return matchId;
	}
	public void setMatchId(String matchId) {
		
		Integer md= Integer.parseInt(matchId);
		String tmd="";
		if(md>0&&md<10){
			
			tmd="00"+md;
		}else if(md>=10&&md<100){
			
			tmd="0"+md;
		}else{
			
			tmd=md+"";
		}
		this.matchId=Long.parseLong((getIssueNumber()+tmd));
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public String getHalfScore() {
		return halfScore;
	}
	public void setHalfScore(String halfScore) {
		this.halfScore = halfScore;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public double getSfpSp() {
		return sfpSp;
	}
	public void setSfpSp(double sfpSp) {
		this.sfpSp = sfpSp;
	}
	public double getBfSp() {
		return bfSp;
	}
	public void setBfSp(double bfSp) {
		this.bfSp = bfSp;
	}

	public double getJqsSp() {
		return jqsSp;
	}
	public void setJqsSp(double jqsSp) {
		this.jqsSp = jqsSp;
	}
	public double getBqcSp() {
		return bqcSp;
	}
	public void setBqcSp(double bqcSp) {
		this.bqcSp = bqcSp;
	}

	public double getSxdsSp() {
		return sxdsSp;
	}
	public void setSxdsSp(double sxdsSp) {
		this.sxdsSp = sxdsSp;
	}
	public double getSfSp() {
		return sfSp;
	}
	public void setSfSp(double sfSp) {
		this.sfSp = sfSp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	

}
