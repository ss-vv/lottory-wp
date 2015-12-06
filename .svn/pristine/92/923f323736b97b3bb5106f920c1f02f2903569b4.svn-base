package com.xhcms.lottery.dc.platform.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.qiutan.QiutanCTZCIssueInfo;
import com.davcai.lottery.platform.util.CTZCIssueSplitUtil;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.DateUtils;
import com.xhcms.ucenter.service.ReceiverAddress;
import com.xhcms.ucenter.service.mail.IEmailService;

@Service
public class IssueInfoStoreParserImpl implements IssueInfoStoreParser {

	//private int interval=-480;//stop时间和close时间的时间间隔
	@Autowired
	private IEmailService iemailService;
	@Autowired
	private IssueService issueService;
	@Autowired
	private ReceiverAddress receiverAddress;
	@Autowired
	private SystemConf systemConf;
	@Override
	public List<IssueInfo> parseToIssueInfo(LotteryPlatformBaseResponse resp,
			String platformId, PlayType playType) {
		//未使用多平台特性，直接转换成球探响应
		if(resp instanceof QiutanCTZCIssueInfo){
			QiutanCTZCIssueInfo qiutanCTZCIssueInfo = (QiutanCTZCIssueInfo)resp;
			return parseToQiutanCTZCIssueInfo(qiutanCTZCIssueInfo,playType);
		}
		return new ArrayList<IssueInfo>();
	}

	private List<IssueInfo> parseToQiutanCTZCIssueInfo(
			QiutanCTZCIssueInfo qiutanCTZCIssueInfo,PlayType playType) {
		//TODO 把qiutanCTZCIssueInfo按照原有逻辑需要的List<IssueInfo>数据结构进行转换
		List<IssueInfo> issueInfoList=new ArrayList<IssueInfo>();
		if(qiutanCTZCIssueInfo!=null){
			IssueInfo issueInfo=new IssueInfo();
			issueInfo.setIssueNumber(CTZCIssueSplitUtil.splitCTZCIssueNumber(qiutanCTZCIssueInfo.getIssuenum()));
			issueInfo.setLotteryId(playType.getLotteryId().name());
			issueInfo.setPlayId(playType.getShortPlayStr());
			issueInfo.setCloseTime(DateUtils.converTime(qiutanCTZCIssueInfo.getM_stoptime()));
			setCloseTime(issueInfo);
			setIssueInfoStopTime(issueInfo);
			issueInfoList.add(issueInfo);
			/*IssueInfo preIssueInfo=new IssueInfo();
			preIssueInfo.setIssueNumber(CTZCIssueSplitUtil.splitCTZCIssueNumber(qiutanCTZCIssueInfo.getPre_issuenum()));
			preIssueInfo.setBonusInfo(qiutanCTZCIssueInfo.getPre_bonusinfo());
			preIssueInfo.setLotteryId(playType.getLotteryId().name());
			preIssueInfo.setPlayId(playType.getShortPlayStr());
			issueInfoList.add(preIssueInfo);*/
		}
		return issueInfoList;
	}
	private void setIssueInfoStopTime(IssueInfo issueInfo){
		Calendar c=Calendar.getInstance();
		if(null!=issueInfo && issueInfo.getCloseTime()!=null){
			c.setTime(issueInfo.getCloseTime());
			Integer seconds=systemConf.getIntegerValueByKey(SystemConf.CLOSETIME.CTZC_310WIN_STOP_TIME_SECOND);
			c.add(Calendar.SECOND, -seconds);
			issueInfo.setStopTime(c.getTime());
		}
	}

	private void doSendEmail(IssueInfo info){
		Map<String,Object> model=new HashMap<String,Object>();
		List<String> addressList=receiverAddress.getAddresses();
		if(addressList!=null && addressList.size()>0){
			String addresses[]=(String[])addressList.toArray(new String[addressList.size()]);
			String subject="传统足球赛程";
			iemailService.setMailTemplateFile("");
			String content="传统足球第"+info.getIssueNumber()+"期有新赛事数据，请稍后查看！";
			iemailService.setSendMailParam(false, content);
			iemailService.sendBatchEmail(model, addresses, subject);
		}
	}
	@Override
	public void checkIsExistsIssueInfo(List<IssueInfo> infolist) {
		if(infolist!=null && infolist.size()>0){
			IssueInfo info=infolist.get(0);
			boolean isHaveIssueInfo=issueService.isExistsCTZCIssueInfoUnset(info.getLotteryId(), info.getIssueNumber(),false);
			if(isHaveIssueInfo){
				doSendEmail(info);
			}
		}
		
	}
	private void setCloseTime(IssueInfo issueInfo){
		Integer seconds=systemConf.getIntegerValueByKey(SystemConf.CLOSETIME.CZTC_310WIN_CLOSE_TIME_SECOND);
		if(issueInfo.getCloseTime()!=null){
			Calendar c=Calendar.getInstance();
			c.setTime(issueInfo.getCloseTime());
			c.add(Calendar.SECOND, seconds);
			issueInfo.setCloseTime(c.getTime());
		}
	}
	
}
