package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map; 

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.lottery.commons.client.translate.MultiPlatformBetCodeUtil;
import com.xhcms.lottery.lang.LotteryId;

public class Ticket implements Serializable {

	private static final long serialVersionUID = -8314644574776562323L;

	private Long id;

	// 接口平台定义的票号，对应中民统一接口中的 palmid。
	private Long platformId;

	private Long schemeId;

	private LotteryId lotteryId; // 只是在中民接口，接收到交易结果时用来做校验用。

	private String playId;

	private String passTypeId;

	// 票的投注内容，大V彩内部格式，对于竞彩来说也是中民第一版的格式。
	private String code;

	// 给出票接口的实际投注内容
	private String actualCode;

	// 赔率，大V彩内部格式，对于竞彩来说也是中民第一版的格式。
	private String odds;

	// 从出票接口取到的原始赔率内容
	private String actualOdds;

	// 总注数
	private int note;

	private int money;

	private int multiple;

	private BigDecimal expectBonus;

	private BigDecimal preTaxBonus;

	private BigDecimal afterTaxBonus;

	private String issue;

	private Date createdTime;

	private String number; // 实物票号

	private String message;

	// 票的当前状态，大V彩内部定义，取自中民第一版
	private int status;

	// 票的当前状态，取自接口。
	private int actualStatus;

	private String lotteryPlatformId;// 表示属于哪个彩票平台，zunao表示尊奥，anruizhiying表示安瑞智赢

	private List<PlayMatch> matches;
	
	private String davcaiOdds;
	
	private Date schemeOfftime;//对应方案的截止时间

	private Date machineOfftime;//对应方案彩机的截止时间

	private Date ticketSuccTime;
	
	private Date minMatchPlayingTime;//票包含的比赛中，最早的开赛时间
	
	/**
	 * 多平台出票投注格式串:key是
	 * 
	 * <pre>
	 * LotteryPlatformId
	 * </pre>
	 * 
	 * 定义的平台ID
	 **/
	private Map<String, String> platformBetCodeMap;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getPassTypeId() {
		return passTypeId;
	}

	public void setPassTypeId(String passTypeId) {
		this.passTypeId = passTypeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOdds() {
		return odds;
	}

	public void setOdds(String odds) {
		this.odds = odds;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public BigDecimal getExpectBonus() {
		return expectBonus;
	}

	public void setExpectBonus(BigDecimal expectBonus) {
		this.expectBonus = expectBonus;
	}

	public BigDecimal getPreTaxBonus() {
		return preTaxBonus;
	}

	public void setPreTaxBonus(BigDecimal preTaxBonus) {
		this.preTaxBonus = preTaxBonus;
	}

	public BigDecimal getAfterTaxBonus() {
		return afterTaxBonus;
	}

	public void setAfterTaxBonus(BigDecimal afterTaxBonus) {
		this.afterTaxBonus = afterTaxBonus;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<PlayMatch> getMatches() {
		return matches;
	}

	public void setMatches(List<PlayMatch> matches) {
		this.matches = matches;
	}

	public String getActualCode() {
		return actualCode;
	}

	public void setActualCode(String actualCode) {
		this.actualCode = actualCode;
	}

	public String getActualOdds() {
		return actualOdds;
	}

	public void setActualOdds(String actualOdds) {
		this.actualOdds = actualOdds;
	}

	public int getActualStatus() {
		return actualStatus;
	}

	public void setActualStatus(int actualStatus) {
		this.actualStatus = actualStatus;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public LotteryId getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(LotteryId lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getLotteryPlatformId() {
		return lotteryPlatformId;
	}

	public void setLotteryPlatformId(String lotteryPlatformId) {
		this.lotteryPlatformId = lotteryPlatformId;
	}

	
	
	public String platformBet() {
		return MultiPlatformBetCodeUtil.format(platformBetCodeMap);
	}

	public Map<String, String> getPlatformBetCodeMap() {
		return platformBetCodeMap;
	}

	public void setPlatformBetCodeMap(Map<String, String> platformBetCodeMap) {
		this.platformBetCodeMap = platformBetCodeMap;
	}

	public String getDavcaiOdds() {
		return davcaiOdds;
	}

	public void setDavcaiOdds(String davcaiOdds) {
		this.davcaiOdds = davcaiOdds;
	}

	public Date getSchemeOfftime() {
		return schemeOfftime;
	}

	public void setSchemeOfftime(Date schemeOfftime) {
		this.schemeOfftime = schemeOfftime;
	}

	public Date getTicketSuccTime() {
		return ticketSuccTime;
	}

	public void setTicketSuccTime(Date ticketSuccTime) {
		this.ticketSuccTime = ticketSuccTime;
	}

	public Date getMachineOfftime() {
		return machineOfftime;
	}

	public void setMachineOfftime(Date machineOfftime) {
		this.machineOfftime = machineOfftime;
	}

	public Date getMinMatchPlayingTime() {
		return minMatchPlayingTime;
	}

	public void setMinMatchPlayingTime(Date minMatchPlayingTime) {
		this.minMatchPlayingTime = minMatchPlayingTime;
	}
}
