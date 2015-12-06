package com.unison.lottery.api.vGroupPublishScheme.data;

import java.io.IOException;
import java.math.BigDecimal;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PublishSchemeClient {

	public String type = "4"; //发方案
	
	public String nickName;
	
	public Long userId;
	
	public BigDecimal rateOfReturn;
	
	public int betAmount;
	
	public BigDecimal maxBonus;
	
	public String lotteryId;
	
	public int betSchemeStatus;
	
	public BigDecimal bounus;
	
	public int betType;
	
	public boolean isDaV = false;
	
	public long schemeId;
	
	public String imageUrl;
	
	public int displayMode;

	public String afterTaxBonus;

	public int singlePrice;
	
	public static String toJsonString(PublishSchemeClient publishScheme) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
//			jsonString = mapper.defaultPrettyPrintingWriter().writeValueAsString(publishScheme);
			jsonString = mapper.writeValueAsString(publishScheme);
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
