package com.unison.lottery.mc.uni.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.mc.uni.parser.util.IDMapper;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.lang.PlayType;


public class QueryIssueResponseParser extends MessageParser{

	private static final long serialVersionUID = -5321502335987244892L;
	
	private SimpleDateFormat issueinfoTimeFormater = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	@Autowired
	private IDMapper idMapper;
	
	/**
	 * 解析期信息，结果写入 status 中。
	 */
	@Override
	public void parseBody(Element body, ParserStatus status) throws ParseException {
		QueryIssueResponseParserStatus queryIssueResponseParserStatus = (QueryIssueResponseParserStatus) status;
		//由于响应结果同时支持多期和一期结果，这里要先判断下是否多期
		Element issueinfos = body.element("issueinfos");
		if(null != issueinfos){
			handleManyIssueinfo(issueinfos,queryIssueResponseParserStatus);
		}
		else{
			handleOneIssueinfo(body,queryIssueResponseParserStatus);
		}
	}

	private void handleOneIssueinfo(Element body, QueryIssueResponseParserStatus status) {
		Element issueinfo=body.element("issueinfo");
		if(null!=issueinfo){
			String lotteryId=issueinfo.attributeValue("lotteryId");
			if(StringUtils.isNotBlank(lotteryId)){//要求必须指明彩种id
				List<IssueInfo> issueinfoResults=new ArrayList<IssueInfo>();
				IssueInfo issueinfoResult=composeIssueinfo(issueinfo, lotteryId);	
				if(null!=issueinfoResult){
					issueinfoResults.add(issueinfoResult);
				}
				else{
					logger.info("issue may be canceled: {}", issueinfo.toString());
				}
				addIssueinfos2Status(status, issueinfoResults);
			}
		}
	}

	private void addIssueinfos2Status(QueryIssueResponseParserStatus status,
			List<IssueInfo> issueinfoResults) {
		if(null!=issueinfoResults&&!issueinfoResults.isEmpty()){
			status.setIssueinfos(issueinfoResults);
		}
	}

	@SuppressWarnings("rawtypes")
	private void handleManyIssueinfo(Element issueinfos, QueryIssueResponseParserStatus status) {
		String lotteryId=issueinfos.attributeValue("lotteryId");
		if(StringUtils.isNotBlank(lotteryId)){//要求必须指明彩种id
			List<IssueInfo> issueinfoResults=new ArrayList<IssueInfo>();
			List issueinfoList = issueinfos.elements("issueinfo");
			Iterator iter = issueinfoList.iterator();
			while(iter.hasNext()) {
				Element issueinfo = (Element) iter.next();
				IssueInfo issueinfoResult=composeIssueinfo(issueinfo, lotteryId);			
				if (null!=issueinfoResult  ){
					issueinfoResults.add(issueinfoResult);
				}else{
					logger.info("issue may be canceled: {}", issueinfo.toString());
				}
			}
			addIssueinfos2Status(status, issueinfoResults);
		}
	}

	private IssueInfo composeIssueinfo(Element issueinfo, String lotteryId)   {
		IssueInfo result=null;
		try{
			if(null!=issueinfo&&StringUtils.isNotBlank(lotteryId)){
				result=new IssueInfo();
				result.setLotteryId(idMapper.getLCLotteryIdFromPlatformLotteryId(lotteryId));
				PlayType playType = idMapper.getLCPlayTypeFromPlatformLotteryId(lotteryId);
				result.setPlayId(playType.getPlayId());
				result.setIssueNumber(issueinfo.attributeValue("issueNumber"));
				Date closeTime=formatDate(issueinfo.attributeValue("closeTime"));
				result.setCloseTime(closeTime);
				Date startTime=formatDate(issueinfo.attributeValue("startTime"));
				result.setStartTime(startTime);
				Date stopTime=formatDate(issueinfo.attributeValue("stopTime"));
				result.setStopTime(stopTime);
				Date prizeTime =  formatDate(issueinfo.attributeValue("prizeTime"));
				result.setPrizeTime(prizeTime);
				String statusStr=issueinfo.attributeValue("status");
				int status=parseIntFromStr(statusStr);
				result.setStatus(status);
				result.setBonusCode(issueinfo.attributeValue("bonusCode"));
				result.setBonusInfo(issueinfo.attributeValue("bonusInfo"));
			}
		}
		catch(Exception e){
			logger.error("compose issueinfo error!", e);
			result = null;
		}
		return result;
	}

	private int parseIntFromStr(String statusStr) {
		int result=-1;
		if(StringUtils.isNotBlank(statusStr)){
			try{
				result=Integer.parseInt(statusStr);
			}
			catch(Exception e){
				e.printStackTrace();
				result=-1;
			}
		}
		return result;
	}

	private Date formatDate(String timeStr) throws ParseException {
		Date date=null;
		if(StringUtils.isNotBlank(timeStr)){
			try {
				date = issueinfoTimeFormater.parse(timeStr);
			} catch (java.text.ParseException e) {
				throw new ParseException("Can not parse  time: " + timeStr);
			}
		}
		return date;
	}
}
