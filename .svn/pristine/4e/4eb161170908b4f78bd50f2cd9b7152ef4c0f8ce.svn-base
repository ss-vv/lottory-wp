package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.framework.utils.ProtocolUtils;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;
import com.xhcms.lottery.conf.LeagueColorConf;



public class QueryJCZQMatchRequestParser extends
		AbstractRequestParserWithUserParser {
	
	
	@Autowired
 	private LeagueColorConf initLeagueName;

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.PLAY_ID ,methodRequest.playId );
		//转换联赛短名称为长名称
		Map<String,String> shortNameMap = initLeagueName.getShortNameLeagueNameMap();
		List<String> leagueNameList = new ArrayList<String>();
		if(methodRequest.leagueShortName!=null){
			String[] shortNameArray = methodRequest.leagueShortName.split(",");
			for (String shortName : shortNameArray) {
				String leagueName = shortNameMap.get(shortName);
				if(StringUtils.isNotBlank(leagueName)) {
					leagueNameList.add(leagueName);
				} else {
					//主要做容错处理，正常情况客户端传过来的都是联赛短名称，然后转为长名称查询数据
					leagueNameList.add(shortName);
				}
			}
			methodRequest.leagueShortName = ProtocolUtils.listToString(leagueNameList, Constants.COMMA_SEPARATOR);
		}
		
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.LEAGUE_SHORT_NAME ,methodRequest.leagueShortName);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.FIRST_RESULT ,methodRequest.firstResult);
		httpRequest.setAttribute(VONames.QUERY_JCZQ_MATCH_VO_NAME, paramMap);
		return true;
	}

	@Override
	protected boolean decideShouldParseParamList() {
		
		return false;
	}

	@Override
	protected boolean decideShouldParseExtendRequestParams() {
		
		return true;
	}

	@Override
	protected boolean decideShouldParseUser() {
		
		return true;
	}

}
