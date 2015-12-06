package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.lang.Channel;
import com.xhcms.lottery.commons.lang.Partner;
import com.xhcms.lottery.dc.data.CGJTeam;

public class BetScheme implements Serializable {

	private static final long serialVersionUID = 6065424352480209072L;

	private long id;

	private String lotteryId;

	private String playId;
	
	private String playName;

	private String passTypeIds;

	private long sponsorId;

	private String sponsor;

	private int status;

	private int type;

	private int showScheme;
	
	private String showSchemeSlogan;

	private int isPublishShow;

	private Date createdTime;

	private Date offtime;

	private int totalAmount;

	private int shareRatio;

	private int buyAmount;

	private int floorAmount;

	private int purchasedAmount;

	private int betNote;

	private int matchNumber;

	private int multiple;

	private int privacy;

	private int followSchemePrivacy;

	private int winNote;

	private Date awardTime;

	private BigDecimal maxBonus;

	private BigDecimal expectBonus;

	private BigDecimal preTaxBonus;

	private BigDecimal afterTaxBonus = new BigDecimal(0);

	private int ticketNote;

	private int cancelNote;

	private int ticketCount;

	private int partnerCount;

	private int saleStatus;

	private Long followedSchemeId;

	private int followingCount;

	private int followedRatio;

	private int recommendation;

	private int singleAmount;
	
	private Date publicTime;
	
	private Date machineOfftime;

	// 定制渠道
	private String channel = Channel.WWW;

	// 定制渠道的合作伙伴
	private String partner = Partner.DAVCAI_WWW;

	@SuppressWarnings("unused")
	private int afterTaxBonusReturnRatio;

	@SuppressWarnings("unused")
	private int maxBonusReturnRatio;

	private String issueNumber;

	private List<UserScore> userScores;

	private List<String> passTypes;

	// 竞彩投注内容
	private List<BetMatch> matchs;

	// 数字彩投注内容
	private List<DigitalBetContent> digitalBetContents;

	// 传统足彩投注内容
	private List<CTBetContent> ctBetContents;

	// 传统足彩赛事列表
	private List<CTFBMatch> ctFBMatchs;

	// 数字彩投注请求，已经算好注数的投注内容，不对应数据库中的数据。
	private DigitalBetRequest digitalBetRequest;

	// 投注请求，只是为了传递到 BetStrategy 内部，需要重构掉。 
	private CTBetRequest ctBetRequest;
	
	private List<CGJTeam> cgjTeams;
	
	/**
	 * 跟单方案列表
	 */
	private List<BetScheme> followSchemes;

	/**
	 * 合买人列表
	 */
	private List<BetPartner> groupbuyPartners;

	private Play play;

	private int followTotalAmount;

	private BigDecimal followTotalBonus;

	/**
	 * 是否预兑奖 0 否 1 是
	 */
	private int isPresetAward;
	
	private String matchAnnotation;
	
	private String privacyMsg;
	
	private BigDecimal sumBonus;
	
	/**是否是实单方案*/
	private boolean realScheme;
	
	private String ratio;
	
	/**是否显示让球**/
	private boolean showConcede;
	
	private String shareUrl;
	
	private int progress;
	
	private List<String> excludeIndex;
	
	private long betRecordId;
	private long groupBetMount;
	private BigDecimal groupWinAmount;
	
	private Map<Long, BigDecimal> matchScoreMap;
	
	private BigDecimal minBonus=BigDecimal.ZERO;//默认值为0
	
	public long getBetRecordId() {
		return betRecordId;
	}

	public void setBetRecordId(long betRecordId) {
		this.betRecordId = betRecordId;
	}

	public String getPlayName() {
		return playName;
	}
	
	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public int getIsPresetAward() {
		return isPresetAward;
	}

	public void setIsPresetAward(int isPresetAward) {
		this.isPresetAward = isPresetAward;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getPassTypeIds() {
		return passTypeIds;
	}

	public void setPassTypeIds(String passTypeIds) {
		this.passTypeIds = passTypeIds;
	}

	public long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getOfftime() {
		return offtime;
	}

	public void setOfftime(Date offtime) {
		this.offtime = offtime;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getShareRatio() {
		return shareRatio;
	}

	public void setShareRatio(int shareRatio) {
		this.shareRatio = shareRatio;
	}

	public int getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}

	public int getFloorAmount() {
		return floorAmount;
	}

	public void setFloorAmount(int floorAmount) {
		this.floorAmount = floorAmount;
	}

	public int getPurchasedAmount() {
		return purchasedAmount;
	}

	public void setPurchasedAmount(int purchasedAmount) {
		this.purchasedAmount = purchasedAmount;
	}

	public int getBetNote() {
		return betNote;
	}

	public void setBetNote(int betNote) {
		this.betNote = betNote;
	}

	public int getMatchNumber() {
		return matchNumber;
	}

	public void setMatchNumber(int matchNumber) {
		this.matchNumber = matchNumber;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getPrivacy() {
		return privacy;
	}

	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}

	public int getWinNote() {
		return winNote;
	}

	public void setWinNote(int winNote) {
		this.winNote = winNote;
	}

	public int getFollowSchemePrivacy() {
		return followSchemePrivacy;
	}

	public void setFollowSchemePrivacy(int followSchemePrivacy) {
		this.followSchemePrivacy = followSchemePrivacy;
	}

	public BigDecimal getMaxBonus() {
		return maxBonus;
	}

	public void setMaxBonus(BigDecimal maxBonus) {
		this.maxBonus = maxBonus;
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

	public int getTicketNote() {
		return ticketNote;
	}

	public void setTicketNote(int ticketNote) {
		this.ticketNote = ticketNote;
	}

	public int getCancelNote() {
		return cancelNote;
	}

	public void setCancelNote(int cancelNote) {
		this.cancelNote = cancelNote;
	}

	public int getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(int saleStatus) {
		this.saleStatus = saleStatus;
	}

	public List<String> getPassTypes() {
		return passTypes;
	}

	public void setPassTypes(List<String> passTypes) {
		this.passTypes = passTypes;
	}

	public List<BetMatch> getMatchs() {
		return matchs;
	}

	public void setMatchs(List<BetMatch> matchs) {
		this.matchs = matchs;
	}

	public int getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(int ticketCount) {
		this.ticketCount = ticketCount;
	}

	public int getPartnerCount() {
		return partnerCount;
	}

	public void setPartnerCount(int partnerCount) {
		this.partnerCount = partnerCount;
	}

	public Date getAwardTime() {
		return awardTime;
	}

	public void setAwardTime(Date awardTime) {
		this.awardTime = awardTime;
	}

	public Long getFollowedSchemeId() {
		return followedSchemeId;
	}

	public void setFollowedSchemeId(Long followedSchemeId) {
		this.followedSchemeId = followedSchemeId;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}

	public int getFollowedRatio() {
		return followedRatio;
	}

	public void setFollowedRatio(int followedRatio) {
		this.followedRatio = followedRatio;
	}

	public int getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(int recommendation) {
		this.recommendation = recommendation;
	}

	public int getShowScheme() {
		return showScheme;
	}

	public void setShowScheme(int showScheme) {
		this.showScheme = showScheme;
	}

	public Play getPlay() {
		return play;
	}

	public void setPlay(Play play) {
		this.play = play;
	}

	public List<BetScheme> getFollowSchemes() {
		return followSchemes;
	}

	public void setFollowSchemes(List<BetScheme> followSchemes) {
		this.followSchemes = followSchemes;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public int getSingleAmount() {
		return singleAmount;
	}

	public void setSingleAmount(int singleAmount) {
		this.singleAmount = singleAmount;
	}

	public int getFollowTotalAmount() {
		return followTotalAmount;
	}

	public void setFollowTotalAmount(int followTotalAmount) {
		this.followTotalAmount = followTotalAmount;
	}

	public List<UserScore> getUserScores() {
		return userScores;
	}

	public void setUserScores(List<UserScore> userScores) {
		this.userScores = userScores;
	}

	public List<BetPartner> getGroupbuyPartners() {
		return groupbuyPartners;
	}

	public void setGroupbuyPartners(List<BetPartner> groupbuyPartners) {
		this.groupbuyPartners = groupbuyPartners;
	}

	public BigDecimal getFollowTotalBonus() {
		return followTotalBonus;
	}

	public void setFollowTotalBonus(BigDecimal followTotalBonus) {
		this.followTotalBonus = followTotalBonus;
	}

	public int getAfterTaxBonusReturnRatio() {
		if(totalAmount <= 0){//防止被除数0错误
			return 0;
		}
		return afterTaxBonus.divide(new BigDecimal(totalAmount), 0,
				BigDecimal.ROUND_HALF_UP).intValue();
	}

	public void setAfterTaxBonusReturnRatio(int afterTaxBonusReturnRatio) {
		this.afterTaxBonusReturnRatio = afterTaxBonusReturnRatio;
	}

	public int getMaxBonusReturnRatio() {
		if(totalAmount <= 0){//防止被除数0错误
			return 0;
		}
		return maxBonus.divide(new BigDecimal(totalAmount), 0,
				BigDecimal.ROUND_HALF_UP).intValue();
	}

	public void setMaxBonusReturnRatio(int maxBonusReturnRatio) {
		this.maxBonusReturnRatio = maxBonusReturnRatio;
	}

	public List<DigitalBetContent> getDigitalBetContents() {
		return digitalBetContents;
	}

	public void setDigitalBetContents(List<DigitalBetContent> digitalBetContents) {
		this.digitalBetContents = digitalBetContents;
	}

	public List<CTBetContent> getCtBetContents() {
		return ctBetContents;
	}

	public void setCtBetContents(List<CTBetContent> ctBetContents) {
		this.ctBetContents = ctBetContents;
	}

	public CTBetRequest getCtBetRequest() {
		return ctBetRequest;
	}

	public void setCtBetRequest(CTBetRequest ctBetRequest) {
		this.ctBetRequest = ctBetRequest;
	}

	public List<CTFBMatch> getCtFBMatchs() {
		return ctFBMatchs;
	}

	public void setCtFBMatchs(List<CTFBMatch> ctFBMatchs) {
		this.ctFBMatchs = ctFBMatchs;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public DigitalBetRequest getDigitalBetRequest() {
		return digitalBetRequest;
	}

	public void setDigitalBetRequest(DigitalBetRequest digitalBetRequest) {
		this.digitalBetRequest = digitalBetRequest;
	}

	public int getIsPublishShow() {
		return isPublishShow;
	}

	public void setIsPublishShow(int isPublishShow) {
		this.isPublishShow = isPublishShow;
	}

	public List<CGJTeam> getCgjTeams() {
		return cgjTeams;
	}

	public void setCgjTeams(List<CGJTeam> cgjTeams) {
		this.cgjTeams = cgjTeams;
	}

	public String getMatchAnnotation() {
		return matchAnnotation;
	}

	public void setMatchAnnotation(String matchAnnotation) {
		this.matchAnnotation = matchAnnotation;
	}

	public String getShowSchemeSlogan() {
		return showSchemeSlogan;
	}

	public void setShowSchemeSlogan(String showSchemeSlogan) {
		this.showSchemeSlogan = showSchemeSlogan;
	}

	public Date getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}

	public BigDecimal getReturnRation() {
		if(totalAmount <= 0){//防止被除数0错误
			return new BigDecimal(0);
		}
		BigDecimal returnRation = this.getMaxBonus().divide(
				new BigDecimal(this.getTotalAmount()), 1, RoundingMode.DOWN);
		return returnRation;
	}

	public String getPrivacyMsg() {
		return privacyMsg;
	}

	public void setPrivacyMsg(String privacyMsg) {
		this.privacyMsg = privacyMsg;
	}

	public BigDecimal getSumBonus() {
		return sumBonus;
	}

	public void setSumBonus(BigDecimal sumBonus) {
		this.sumBonus = sumBonus;
	}

	public boolean isRealScheme() {
		return realScheme;
	}

	public void setRealScheme(boolean realScheme) {
		this.realScheme = realScheme;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	public boolean isShowConcede() {
		return showConcede;
	}

	public void setShowConcede(boolean showConcede) {
		this.showConcede = showConcede;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public List<String> getExcludeIndex() {
		return excludeIndex;
	}

	public void setExcludeIndex(List<String> excludeIndex) {
		this.excludeIndex = excludeIndex;
	}

	public long getGroupBetMount() {
		return groupBetMount;
	}

	public void setGroupBetMount(long groupBetMount) {
		this.groupBetMount = groupBetMount;
	}

	public BigDecimal getGroupWinAmount() {
		return groupWinAmount;
	}

	public void setGroupWinAmount(BigDecimal groupWinAmount) {
		this.groupWinAmount = groupWinAmount;
	}

	public Date getMachineOfftime() {
		return machineOfftime;
	}

	public void setMachineOfftime(Date machineOfftime) {
		this.machineOfftime = machineOfftime;
	}

	public Map<Long, BigDecimal> getMatchScoreMap() {
		return matchScoreMap;
	}

	public void setMatchScoreMap(Map<Long, BigDecimal> matchScoreMap) {
		this.matchScoreMap = matchScoreMap;
	}

	public BigDecimal getMinBonus() {
		return minBonus;
	}

	public void setMinBonus(BigDecimal minBonus) {
		this.minBonus = minBonus;
	}

}
