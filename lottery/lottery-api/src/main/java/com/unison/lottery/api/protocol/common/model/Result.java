package com.unison.lottery.api.protocol.common.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List; 

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


public class Result {
	
	@XmlAttribute
	public String imageUrl;
	
	@XmlAttribute
	public String genre;
		
	@XmlAttribute
	public String lanaguage;
	
	@XmlAttribute
	public String country;
	
	@XmlAttribute
	public String sex;
	
	@XmlAttribute
	public String birthday;
	
	
	
	@XmlAttribute
	public String description;
	
	

	@XmlAttribute
	public String name;

	@XmlAttribute
	public String validId;

	@XmlAttribute
	public String updateUrl;

	@XmlAttribute
	public String version;
	
	@XmlAttribute
	public String phoneNumber;
	
	

	@XmlAttribute
	public String updateDescription;

	@XmlAttribute
	public Integer point;


	@XmlElement
	public List<Item> item;

	@XmlElement
	public List<ResultList> resultList;

	

	@XmlAttribute
	public String email;


	@XmlAttribute
	public BigDecimal free;

	

	@XmlAttribute
	public BigDecimal grant;
	
	@XmlAttribute
	public BigDecimal frozenFund;

	@XmlAttribute
	public BigDecimal frozenGrant;

	@XmlAttribute
	public BigDecimal totalRecharge;

	@XmlAttribute
	public BigDecimal totalWithdraw;

	@XmlAttribute
	public BigDecimal totalBet;

	@XmlAttribute
	public BigDecimal totalAward;

	@XmlAttribute
	public BigDecimal totalCommission;

	@XmlAttribute
	public String bankName;

	@XmlAttribute
	public String provinceOfBank;

	@XmlAttribute
	public String cityOfBank;

	@XmlAttribute
	public String bankID;

	@XmlAttribute
	public String accountUser;

	@XmlAttribute
	public Boolean isBindMobile;

	@XmlAttribute
	public String realName;

	@XmlAttribute(name="IDCard")
	public String IDCard;

	@XmlAttribute
	public String accountBank;
	
	//第几页
	@XmlAttribute
	public String page;
	
	//总页数
	@XmlAttribute
	public String sumOfPages;

	@XmlAttribute
	public BigDecimal fund;

	@XmlAttribute
	public String updateType;
	@XmlAttribute
	public Long schemeId;

	@XmlAttribute
	public String issueNumber;
	
	@XmlAttribute
	public Integer schemeStatus;

	@XmlAttribute
	public String sponsor;

	@XmlAttribute
	public Integer multiple;
	
	@XmlAttribute
	public Integer betNote;

	@XmlAttribute
	public String passType;

	@XmlAttribute
	public BigDecimal maxBonus;

	@XmlAttribute
	public BigDecimal blance;

	@XmlAttribute
	public String schemeCreateTime;

	@XmlAttribute
	public String playName;

	@XmlAttribute
	public String lotteryName;

	@XmlAttribute
	public BigDecimal sumBetAmount;

	@XmlAttribute
	public String lotteryId;
	
	@XmlAttribute
	public String playId;

	@XmlAttribute
	public BigDecimal taxBonus;

	@XmlAttribute
	public BigDecimal sumBonus;

	@XmlAttribute
	public BigDecimal sponsorAward;

	@XmlAttribute
	public BigDecimal sponsorCommission;

	@XmlAttribute
	public String ticketNumber;


	@XmlAttribute
	public Integer money;

	@XmlAttribute
	public BigDecimal afterTaxBonus;

	@XmlAttribute
	public Integer status;

	@XmlAttribute
	public Integer pages;

	@XmlAttribute
	public Integer privacy;

	@XmlAttribute
	public Integer followRatio;

	@XmlAttribute
	public Integer totalAmount;
	
	@XmlAttribute
	public String matchResult;
	
	@XmlAttribute
	public String hx_username;
	
	@XmlAttribute
	public String hx_password;
	
	@XmlAttribute
	public String nickname;
	
	@XmlAttribute
	public Double progress;

	@XmlAttribute
	public Double floorAmount;

	@XmlAttribute
	public int surplus;

	@XmlAttribute
	public int pushMoney;

	@XmlAttribute
	public int price;
	
	@XmlAttribute
	public String offtime;

	@XmlAttribute
	public String nickName;

	@XmlAttribute
	public int displayMode = -1;//初始值

	@XmlAttribute
	public int betType;
	
	@XmlAttribute
	public String leagueShortName;

	@XmlAttribute
	public Integer matchType;
}