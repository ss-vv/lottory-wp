package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.davcai.lottery.platform.client.AbstractLotteryPlatformOrderTicketClient;
import com.davcai.lottery.platform.client.anruizhiying.CheckResult;
import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.davcai.lottery.platform.client.yuanchengchupiao.constants.PublicParameter;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.MessageIdGenerator;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.YuanChengChuPiaoResponseAdapter;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.lang.PlayType;

public class YuanChengChuPiaoOrderTicketClientImpl extends AbstractLotteryPlatformOrderTicketClient{

	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier("yuanChengChuPiaoOrderTicketClientSupport")
	private AbstractYuanChengChuPiaoClientSupport clientSupport;
	
	@Override
	protected int getMaxCountForOneLoop() {
		return 1;
	}

	@Override
	protected void preProcess(Ticket t) {
		//设置平台id和真实投注格式
		t.setLotteryPlatformId(LotteryPlatformId.YUAN_CHENG_CHU_PIAO);
		Map<String, String> map = t.getPlatformBetCodeMap();
		t.setActualCode(null == map? null : map.get(LotteryPlatformId.YUAN_CHENG_CHU_PIAO));
	}

	@Override
	protected Map<String, List<Ticket>> groupTickets(List<Ticket> tickets) {
		Map<String, List<Ticket>> ts = new HashMap<String, List<Ticket>>();
		ts.put(LotteryPlatformId.YUAN_CHENG_CHU_PIAO, tickets);
		return ts;
	}

	@Override
	public OrderTicketResponse4OneLoop orderTicketForOneLoop(
			List<Ticket> tickets) {
		YuanChengChuPiaoResponse resp = new YuanChengChuPiaoResponse();
		String orderId="";
		if(null!=tickets&&!tickets.isEmpty()){
			CheckResult checkResult = check(tickets);
			if(!checkResult.isLotteryPlatformIdIsRight()){
				resp=new YuanChengChuPiaoResponse("-9998");
				resp.setMessage("票的平台id分配不正确");
				return YuanChengChuPiaoResponseAdapter.toOrderTicketResponse4OneLoop(resp,tickets, orderId);
			}
			if(!checkResult.isPlayIdNotBlank()){
				resp=new YuanChengChuPiaoResponse("-9996");
				resp.setMessage("票的playId为空");
				return YuanChengChuPiaoResponseAdapter.toOrderTicketResponse4OneLoop(resp,tickets, orderId);
			}
			if(!checkResult.isPlayIdAndIssueIsSame()){
				resp=new YuanChengChuPiaoResponse("-9997");
				resp.setMessage("票的playId与期id不一致");
				return YuanChengChuPiaoResponseAdapter.toOrderTicketResponse4OneLoop(resp,tickets, orderId);
			}
			Map<String,Object> params = makeParams(tickets);
			addTicketInfo(tickets);
			orderId=(String)params.get("OrderId");
			log.debug("发送出票请求到远程出票：params={}",params);
			resp=(YuanChengChuPiaoResponse)clientSupport.doPostWithRetry(params);
		} else { 
			resp.setError("-9999");
			resp.setMessage("票为空！");
		}
		return YuanChengChuPiaoResponseAdapter.toOrderTicketResponse4OneLoop(resp, tickets, orderId);
	}
	
	private void addTicketInfo(List<Ticket> ts){
		ts.get(0).setOdds(ts.get(0).getDavcaiOdds());
		ts.get(0).setNumber(UUID.randomUUID().toString());
	}
	
	/**
	 * 当前远程出票一次仅支持一张票
	 * @param tickets
	 * @return
	 */
	private Map<String, Object> makeParams(List<Ticket> tickets) {
		Ticket t = tickets.get(0);
		String playId=t.getPlayId();
		String content = t.getPlatformBetCodeMap().get(getTargetLotteryPlatformId());
		Map<String, Object> params=new HashMap<String,Object>();
		//
		params.put("OrderId", MessageIdGenerator.generateId(PublicParameter.getApiId()));
		params.put("Content", content);
		String gameType=makeYuanChengResult(PlayType.valueOfLcPlayId(playId));
		params.put("Game_type", gameType);
		params.put("Times", t.getMultiple());
		params.put("Invest_Count", t.getNote());//calMatchCount(content,gameType);
		params.put("Cross_Type", t.getPassTypeId().replace("@", "0"));
		params.put("Pay", t.getMoney()+"00");
		params.put("Issued", getNowDate());//期号
		params.put("Deadline", getDeadLine(t));//最早的一场比赛开始时间 Unix时间戳
		return params;
	}
	
	/**
	 * 最早一场比赛开始时间
	 * @param t
	 * @return
	 */
	private String getDeadLine(Ticket t)
	{
		//获取票的最早一场开始时间
		Date date = t.getMinMatchPlayingTime();
		int dateUnix = (int) (date.getTime() / 1000);
		return dateUnix+"";
	}
	
	/**
	 * 当天时间作为期号
	 * @return
	 */
	private String getNowDate()
	{
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}
	
	
	private Integer calMatchCount(String content,String gameType){
		if("19".equals(gameType)){
			return content.split("\\^").length/4;
		} else {
			return content.split("\\^").length/3;
		}
	}
	
	private String makeYuanChengResult(PlayType p){
		switch (p) {
		//竞彩足球
		case JCZQ_BRQSPF:
			return "Z51";
		case JCZQ_BF:
			return "Z52";
		case JCZQ_ZJQS:
			return "Z53";
		case JCZQ_BQC:
			return "Z54";
		case JCZQ_SPF:
			return "Z56";
		case JCZQ_HH:
			return "Z59";
		//竞彩篮球
		case JCLQ_RFSF:
			return "L61";	
		case JCLQ_SF:
			return "L62";
		case JCLQ_SFC:
			return "L63";
		case JCLQ_DXF:
			return "L64";
		case JCLQ_HH:
			return "Z69";
		
		default:
			return "19";
		}
	}
	public AbstractYuanChengChuPiaoClientSupport getClientSupport() {
		return clientSupport;
	}

	public void setClientSupport(AbstractYuanChengChuPiaoClientSupport clientSupport) {
		this.clientSupport = clientSupport;
	}
	
	/**
	 * 检查所有票的彩票平台id是否为指定的值，有一个不一致，就返回false;
	 * 检查所有票的playId与期id是否一致,不一致返回false
	 * 检查所有票的playid必须不为空，否则返回false
	 * @param tickets
	 * @return
	 */
	private CheckResult check(List<Ticket> tickets) {
		CheckResult result=new CheckResult();
		boolean lotteryPlatformIdIsRight=false;
		boolean playIdAndIssueIsSame=false;
		boolean playIdNotBlank=false;
		Set<String> playIdAndIssueSet=new HashSet<String>();
		for(Ticket ticket:tickets){
			if(StringUtils.isNotBlank(getTargetLotteryPlatformId())&&StringUtils.equals(ticket.getLotteryPlatformId(), getTargetLotteryPlatformId())){
				lotteryPlatformIdIsRight=true;
				if(StringUtils.isNotBlank(ticket.getPlayId())){
					playIdNotBlank=true;
					playIdAndIssueSet.add(ticket.getPlayId()+"_"+ticket.getIssue());
				}
				else{
					playIdNotBlank=false;
					break;
				}
				
			}
			else{
				lotteryPlatformIdIsRight=false;
				break;
			}
		}
		if(playIdAndIssueSet.size()==1){
			playIdAndIssueIsSame=true;
		}
		result.setLotteryPlatformIdIsRight(lotteryPlatformIdIsRight);
		result.setPlayIdAndIssueIsSame(playIdAndIssueIsSame);
		result.setPlayIdNotBlank(playIdNotBlank);
		return result;
	}

	private String getTargetLotteryPlatformId() {
		return LotteryPlatformId.YUAN_CHENG_CHU_PIAO;
	}
}
