package com.unison.lottery.api.protocol.response.model;

import java.awt.Image;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.unison.lottery.api.lang.JAXBContextHolder;
import com.unison.lottery.api.odds.model.JCOdds;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.common.model.ResultList;
import com.unison.lottery.api.redEnvalope.model.EnvalopeGrabHistory;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement
public class Response {

	@XmlAttribute
	public String resultDesc;//添加描述字段
	
	@XmlAttribute
	public String greetings;//添加祝福语
	
	@XmlAttribute
	public String name;

	@XmlAttribute
	public String status;

	@XmlAttribute
	public String desc;

	@XmlAttribute
	public Integer templateId;
	
	@XmlAttribute
	public Integer totalPages;
	
	@XmlAttribute
	public Integer page;

	@XmlElement
	public Result result;

	@XmlElement
	public ResultList resultList;

	@XmlAttribute
	public String modifyPasswordMethodDesc;

	@XmlAttribute
	public String moServiceNumber;

	@XmlAttribute
	public String moContentPrefix;

	@XmlAttribute
	public String version;

	@XmlAttribute
	public String updateUrl;

	@XmlAttribute
	public Integer currentPage;

	@XmlAttribute
	public Integer totalPage;

	@XmlAttribute
	public String userAuthorizePageUrl;

	@XmlAttribute
	public String content;

	@XmlAttribute
	public Integer point;

	@XmlAttribute
	public String snsType;

	@XmlAttribute
	public Integer playListVersion;

	@XmlAttribute
	public String commentUrl;

	@XmlAttribute
	public String resultString;

	@XmlAttribute
	public BigDecimal free;

	@XmlAttribute
	public BigDecimal fund;

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
	public String currentIssueNumber;

	@XmlAttribute
	public String currentSalingIssueCountdownTime;

	@XmlAttribute
	public String countdownTime;
	
	@XmlAttribute
	public String imageUrl;
	@XmlAttribute
	public String nickName;
	@XmlAttribute
	public String phoneNumber;
	
	@XmlAttribute
	public String IDCard;
	
	@XmlAttribute
	public String realName;
	
	@XmlAttribute
	public String provinceOfBank;
	
	@XmlAttribute
	public String cityOfBank;
	
	@XmlAttribute
	public String accountBank;
	
	@XmlAttribute
	public String bankID;
	
	@XmlAttribute
	public String accountUser;
	
	@XmlAttribute
	public boolean isBindMobile;
	
	@XmlAttribute
	public String hxUsername;
	@XmlAttribute
	public String hxPassword;

	@XmlTransient
	public QueryGroupInfoResponse queryGroupInfoResponse;
	@XmlTransient
	public QueryGroupMembersResponse queryGroupMembersResponse;
	@XmlTransient
	public PublishSchemeResponse publishSchemeResponse;
	@XmlTransient
	public QuerySysSchemeResponse querySysSchemeResponse;

	@XmlAttribute
	public Integer matchType;

	public String leagueShortName;
	
	@XmlTransient
	public QueryScoreLiveInfoResponse queryScoreLiveInfoResponse;
	
	@XmlAttribute
	public String oddsData;

	@XmlAttribute
	public String timestamp;

	@XmlTransient
	public String envalopeId;
	@XmlTransient 
	public List<JCOdds> oddsResultList;

	@XmlTransient
	public String resultStatus;

	@XmlTransient
	public String grabResult;

	@XmlTransient
	public Long grabAmount;

	@XmlTransient
	public List<EnvalopeGrabHistory> envalopeGrabHistory;

	@XmlTransient
	public String nickname;

	@XmlTransient
	public Integer envalopeNum;

	@XmlTransient
	public Long redEnvalopeAmount;

	@XmlTransient
	public String minuteOfGrabed;

	@XmlTransient
	public Long grabedAmount;

	@XmlTransient
	public String redFree;

	@XmlTransient
	public String redFund;

	@XmlTransient
	public String redFrozenFund;

	@XmlTransient
	public String redGrant;
	
	
	
	public String toXmlString() {
		String result = "";
		JAXBContext context;
		try {
			context = JAXBContextHolder.getInstance(Response.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			m.marshal(this, sw);
			result = sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}
    
	public String toJsonString(Response response) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(response);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
