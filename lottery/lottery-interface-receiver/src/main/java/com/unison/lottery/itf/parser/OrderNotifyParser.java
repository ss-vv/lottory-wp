package com.unison.lottery.itf.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.anruizhiying.util.AnRuiZhiYingSignUtil;
import com.unison.lottery.mc.uni.AnRuiInterfaceConfig;
import com.unison.lottery.mc.uni.parser.ParseException;
import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryIdForAnRui;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.mc.persist.service.TicketService;
import com.xhcms.lottery.utils.DateUtils;

public class OrderNotifyParser implements INotifyParser {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private AnRuiInterfaceConfig anRuiConfig;

	public AnRuiInterfaceConfig getAnRuiConfig() {
		return anRuiConfig;
	}

	public void setAnRuiConfig(AnRuiInterfaceConfig anRuiConfig) {
		this.anRuiConfig = anRuiConfig;
	}

	@Autowired
	private TicketService ticketService;
	
	private Map<String,Object> resultMap = new HashMap<String,Object>();

	@Override
	public Map<String,?> parse(String notify)throws ParseException  {
		String[] content = notify.split("\\^");
		if(null!=content&&content.length==3){
			String channel = content[0];
			String ticketOrderResultNotifyContent=content[1];
			String sign=content[2];
			String toVerifyNotifyContent=channel+"^"+ticketOrderResultNotifyContent;
			if (verify(channel,sign,toVerifyNotifyContent)) {
				List<Ticket> results = new ArrayList<Ticket>();
				String[] tickets = ticketOrderResultNotifyContent.split("\\$");
				for (String ticketResult : tickets) {
					String[] temp = ticketResult.split("~");
					String lotteryId = temp[0];
					String channelTicketId = temp[1];
					String printStatus = temp[2];
					String anRuiReturnOdds = temp[3];
					String printtime = temp[4];
					String ticketno = temp[5];

					try {
						Ticket ticket = new Ticket();
						ticket.setLotteryId(LotteryIdForAnRui.getDavLotteryIdByAnRuiLotteryId(lotteryId));
						ticket.setId(Long.parseLong(channelTicketId));
						ticket.setActualStatus(Integer.parseInt(printStatus));
						int davStatus  = Translator
								.translateAnRuiOrderResultStatusToDavFormat(Integer
										.parseInt(printStatus));
						ticket.setStatus(davStatus);
						ticket.setActualOdds(anRuiReturnOdds);
						String davOdds = translateOddsToLCFormat(anRuiReturnOdds,
								ticket.getId());
						ticket.setOdds(davOdds);
						ticket.setNumber(ticketno);
						ticket.setCreatedTime(DateUtils.getDateByFormatString(
								printtime, "yyyyMMddHHmmss"));
						ticket.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
						ticket.setPlatformId(0l);
						results.add(ticket);
					} catch (NumberFormatException e) {
						
						throw new ParseException(e);
						
					} catch (TranslateException e) {
						
						throw new ParseException(e);
					}
				}
				resultMap.put("tickets", results);
			} else {
				throw new ParseException("校验不通过!");
			}
		}
		else{
			throw new ParseException("安瑞智赢出票通知字符串不符合规定!字符串为:"+notify);
		}
		

		return resultMap;
	}

	private boolean verify(String channel, String sign, String toVerifyNotifyContent) {
		return signIsOk(sign,toVerifyNotifyContent,anRuiConfig.getKey(),anRuiConfig.getChannelId())&&channel.equals(anRuiConfig.getChannelId());
	}

	private boolean signIsOk(String sign,
			String ticketOrderResultNotifyContent, String key, String channelId) {
		return AnRuiZhiYingSignUtil.verifySign4OrderTicketResult(sign,ticketOrderResultNotifyContent,key,channelId);
	}

	private String translateOddsToLCFormat(String actualOdds, Long ticketId)
			throws TranslateException {
		logger.debug("通过票Id查询票信息：ticketId={}", ticketId);
		Ticket ticket = ticketService.getTicket(ticketId);
		String lcPlayId = ticket.getPlayId();
		return Translator.translateAnRuiOddsToDavFormat(actualOdds, lcPlayId);
	}

}