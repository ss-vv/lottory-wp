package com.davcai.lottery.platform.client.anruizhiying.parser;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingQueryPrizeResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.WinInfo;
import com.davcai.lottery.platform.client.anruizhiying.model.WinTickets;

@Service
public class AnRuiZhiYingQueryPrizeParser extends AnRuiZhiYingRespBaseParser {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public AnRuiZhiYingResponse parseResp(String resp){
		logger.debug("resp={}",resp);
		AnRuiZhiYingQueryPrizeResponse anRuiZhiYingQueryPrizeResponse = null;
		if(StringUtils.isNotBlank(resp)){
			anRuiZhiYingQueryPrizeResponse=new AnRuiZhiYingQueryPrizeResponse();
			SAXReader reader = new SAXReader();
	        reader.setEncoding("UTF-8");
			try {
				byte [] buf = resp.getBytes("utf-8");
				Document doc = reader.read(new ByteArrayInputStream(buf));
				Element root = doc.getRootElement();
				parse(root,anRuiZhiYingQueryPrizeResponse);
			} catch (UnsupportedEncodingException e) {
				logger.error("解析安瑞智赢竞彩赛事响应错误",e);
			} catch (DocumentException e) {
				logger.error("解析安瑞智赢竞彩赛事响应错误",e);
			}
		}
		return anRuiZhiYingQueryPrizeResponse;
	}

	private void parse(Element element,
			AnRuiZhiYingQueryPrizeResponse anRuiZhiYingQueryPrizeResponse) {
		if(null!=element){
			
			WinTickets winTickets=new WinTickets();
			winTickets.setChannelId(element.attributeValue("channelid"));
			@SuppressWarnings("unchecked")
			List<Element> winInfoElements = element.elements("wininfo");
			if(null!=winInfoElements&&!winInfoElements.isEmpty()){
				List<WinInfo> winInfos = new ArrayList<WinInfo>();
				for(Element winInfoElement:winInfoElements){
					try{
						WinInfo winInfo=new WinInfo();
						winInfo.setChannelTicketId(Long.parseLong(winInfoElement.attributeValue("channel_ticket_id")));
						winInfo.setLotteryId(Integer.parseInt(winInfoElement.attributeValue("lotteryid")));
						winInfo.setTicketStatus(Integer.parseInt(winInfoElement.attributeValue("ticketstatus")));
						String winamtStr=winInfoElement.attributeValue("winamt");
						if(StringUtils.isBlank(winamtStr)){
							winamtStr="0";
						}
						winInfo.setWinamt(new BigDecimal(winamtStr));
						winInfos.add(winInfo);
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
				winTickets.setWinInfos(winInfos);
			}
			
			
			anRuiZhiYingQueryPrizeResponse.setWinTickets(winTickets);
			
		}
		
	}

}
