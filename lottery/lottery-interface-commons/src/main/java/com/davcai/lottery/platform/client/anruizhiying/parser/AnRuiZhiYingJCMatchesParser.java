package com.davcai.lottery.platform.client.anruizhiying.parser;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchModel;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchesResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCPlayOddsModel;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingResponse;

@Service
public class AnRuiZhiYingJCMatchesParser extends AnRuiZhiYingRespBaseParser {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public AnRuiZhiYingResponse parseResp(String resp){
		logger.debug("resp={}",resp);
		AnRuiZhiYingJCMatchesResponse jcMatchesResponse = new AnRuiZhiYingJCMatchesResponse();
		SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
		try {
			byte [] buf = resp.getBytes("utf-8");
			Document doc = reader.read(new ByteArrayInputStream(buf));
			Element root = doc.getRootElement();
			parseMatches(root,jcMatchesResponse);
		} catch (UnsupportedEncodingException e) {
			logger.error("解析安瑞智赢竞彩赛事响应错误",e);
		} catch (DocumentException e) {
			logger.error("解析安瑞智赢竞彩赛事响应错误",e);
		}
		return jcMatchesResponse;
	}

	private void parseMatches(Element element,AnRuiZhiYingJCMatchesResponse jcMatchesResponse) {
		List<AnRuiZhiYingJCMatchModel> jcMatchModels = new ArrayList<AnRuiZhiYingJCMatchModel>();
		List<Element> matches = element.elements("Match");
		for (Element m : matches) {
			AnRuiZhiYingJCMatchModel am = new AnRuiZhiYingJCMatchModel();
			am.setMatchId(m.attributeValue(MatchID));
			am.setGameNo(m.attributeValue(GameNo));
			am.setGameName(m.attributeValue(GameName));
			am.setHomeName(m.attributeValue(HomeName));
			am.setAwayName(m.attributeValue(AwayName));
			//接口中的时间，篮球时间格式是GameTime="2015-01-14 08:00" 足球是 GameTime="2015-01-13 15:00:00"，篮球需要补“:00”来统一格式
			if(StringUtils.isNotBlank(m.attributeValue(GameTime)) && m.attributeValue(GameTime).length()<17){
				am.setGameTime(m.attributeValue(GameTime) + ":00");
			} else {
				am.setGameTime(m.attributeValue(GameTime));
			}
			List<Element> playOdds = m.elements("Pv");
			List<AnRuiZhiYingJCPlayOddsModel> apList = new ArrayList<AnRuiZhiYingJCPlayOddsModel>();
			for (Element playOdd : playOdds) {
				AnRuiZhiYingJCPlayOddsModel ap = new AnRuiZhiYingJCPlayOddsModel();
				ap.setLotteryId(playOdd.attributeValue(LotteryID));
				ap.setGgRq(playOdd.attributeValue(Gg_Rq));
				ap.setDgRq(playOdd.attributeValue(Dg_Rq));
				ap.setGgPv(playOdd.attributeValue(Gg_Pv));
				ap.setDgPv(playOdd.attributeValue(Dg_Pv));
				ap.setGgSaleStatus(playOdd.attributeValue(Gg_SaleStatus));
				ap.setDgSaleStatus(playOdd.attributeValue(Dg_SaleStatus));
				ap.setDgGdSaleStatus(playOdd.attributeValue(Dg_Gd_SaleStatus));
				apList.add(ap);
			}
			am.setPlayOdds(apList);
			jcMatchModels.add(am);
		}
		jcMatchesResponse.setMatches(jcMatchModels);
	}
}
