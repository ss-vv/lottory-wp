package com.xhcms.lottery.pb.job;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.dao.WithdrawDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.entity.WithdrawPO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.pb.dao.PartnerWithdrawDao;
import com.xhcms.lottery.pb.model.Constants;
import com.xhcms.lottery.pb.model.MESSAGE_TYPE;
import com.xhcms.lottery.pb.model.VerificationModel;
import com.xhcms.lottery.pb.po.PartnerWithdrawPO;
import com.xhcms.lottery.pb.util.DateTimeUtil;
import com.xhcms.lottery.pb.util.HttpClientUtil;
import com.xhcms.lottery.pb.util.JAXBContextUtil;
import com.xhcms.lottery.pb.util.MD5Util;
import com.xhcms.lottery.pb.util.VelocityUtil;
import com.xhcms.lottery.pb.xml.model.Msg;
import com.xhcms.lottery.pb.xml.model.WithdrawResultReq;
import com.xhcms.lottery.pb.xml.model.WithdrawResultResp;

/**
 * 提现结果通知
 * @author haoxiang.jiang
 * 2014年9月12日 下午5:18:54
 */
public class WithdrawInformJob {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	PartnerWithdrawDao partnerWithdrawDao;
	@Autowired
	WithdrawDao withdrawDao;
	@Autowired
	UserDao userDao;
	@Transactional
	public void work() {
		String partnerId = "youyuanwang";
		List<WithdrawResultReq> withdrawResultReqs = getNeedNoticeWithdraw(partnerId);
		if (withdrawResultReqs.size() > 0) {
			// convert 'Need to Notice Ticket List' to 'XML String'
			VelocityContext vContext = new VelocityContext();
			vContext.put("partnerId", partnerId);
			vContext.put("time", DateTimeUtil.getTimeString(new Date()));
			vContext.put("version", Constants.API_VERSION);
			vContext.put("withdrawResultReqs", withdrawResultReqs);
			String contentXml = VelocityUtil.merge(vContext, "../vm/withdraw-result-notice.xml");
			logger.info("提现结果通知请求消息体，contentXml:\n" + contentXml);

//			String responseXml = this.doPush(contentXml,partnerId);
			//test code ---start----
			String responseXml = getTestRespXml(withdrawResultReqs,
					MESSAGE_TYPE.WITHDRAW_RESULT_RESP.getCode(), partnerId);
			//test code ---end----
			if (null == responseXml) {
				logger.info("验证失败！");
				return;
			}
			logger.info("提现结果通知响应消息体，responseXml:\n" + responseXml);

			Msg msg = this.convertToMsg(responseXml);
			String msgType = msg.getHead().getMsg();
			if (null != msgType
					&& MESSAGE_TYPE.WITHDRAW_RESULT_RESP.getCode().equals(msgType)) {
				List<WithdrawResultResp> withdrawResultResps = msg.getBody().getWithdrawResultResp();
				if (null != withdrawResultResps) {
					this.dealWithTicketResps(withdrawResultResps);
				} else {
					logger.info("消息body为空");
				}
			} else {
				logger.info("非法消息类型：{}", msgType);
			}
		} else {
			logger.info("当前出票通知 WithdrawInformJob 无数据需要处理！");
		}
	}
	//TODO  测试用
	private String getTestRespXml(List<WithdrawResultReq> withdrawResultReqs,String msgType,String partnerId){
		String responseXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<msg>\n" +
				"  <head msg=\""+msgType+"\" partnerId=\""+partnerId+"\" version=\"1.0\" time=\"20140901175541\" />\n" +
				"  <body>\n" + 
				"{{replace}}" +
				"  </body>\n" + 
				"</msg>";
		String ticString = "";
		for (WithdrawResultReq w : withdrawResultReqs) {
		ticString += "    <withdrawResultResp transactionId=\"" + w.getTransactionId()
				+ "\" status=\"1\"/>\n";
		}
		responseXml = responseXml.replace("{{replace}}", ticString);
		return responseXml;
	}
	
	/**
	 * 执行请求，返回响应xml字符串
	 * 
	 * @param contentXml
	 * @return
	 */
	private String doPush(String contentXml,String partnerId) {
		Header[] requestHeard = new Header[3];
		requestHeard[0] = new BasicHeader(Constants.PARTNER_ID,partnerId);
		requestHeard[1] = new BasicHeader(Constants.MSG_TYPE,MESSAGE_TYPE.WITHDRAW_RESULT_REQ.getCode());
		String needSignBody = partnerId + Constants.PARTNER_KEYS.get(partnerId) + contentXml.trim();
		requestHeard[2] = new BasicHeader(Constants.SIGNATURE,MD5Util.md5(needSignBody));
		try {
			logger.info("开奖通知被签名消息体：{}\n"
					+ "长度：{}", needSignBody,
					needSignBody.getBytes("iso-8859-1").length);
			logger.info("签名：{}", MD5Util.md5(needSignBody));
		} catch (UnsupportedEncodingException e) {
			logger.error("开奖通知消息体getBytes('iso-8859-1') 出错", e);
		}
		String url = Constants.PARTNER_URLS.get(partnerId).get(MESSAGE_TYPE.WITHDRAW_RESULT_REQ);
		HttpClientUtil client = new HttpClientUtil(url, requestHeard, contentXml);
		String responseXml = new String();
		Map<String, String> headerMap = new HashMap<String, String>();
		client.exec();
		responseXml = client.getResponseXml();
		headerMap = client.getHeaderMap();
		boolean verifyResult = new VerificationModel(partnerId, responseXml, headerMap).verify();
		return verifyResult ? responseXml : null;
	}
	
	private void dealWithTicketResps(List<WithdrawResultResp> withdrawResultResps) {
		for (WithdrawResultResp w : withdrawResultResps) {
			if (Constants.WITHDRAW_RESULT_REQ_RCV_SUCCESS == w.getStatus()) {
				PartnerWithdrawPO p = partnerWithdrawDao.findByTransactionId(w.getTransactionId());
				if(null != p){
					p.setStatus(Constants.WITHDRAW_RESULT_HAVE_INFORMED);
					p.setWithdrawNoticeTime(new Date());
				}
			}
		}
	}
	
	private Msg convertToMsg(String contentXml) {
		JAXBContext jaxbContext = JAXBContextUtil.getJaxbContext();
		Unmarshaller um;
		Msg msg = null;
		try {
			um = jaxbContext.createUnmarshaller();
			if (null != um) {
				msg = (Msg) um.unmarshal(new StringReader(contentXml));
				return msg;
			} else {
				logger.error("Unmarshaller is null:\n contentXml:{} \n 未完成解析",
						contentXml);
				return null;
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			logger.error("解析错误：{}", contentXml);
			return null;
		}
	}
	
	private List<WithdrawResultReq> getNeedNoticeWithdraw(String partnerId) {
		UserPO user = userDao.getUserByUsername(partnerId);
		List<PartnerWithdrawPO> partnerWithdrawPOs = partnerWithdrawDao.listByStatus(
				Constants.RESULT_MAX_SIZE,user.getId(),
				Constants.WAIT_WITHDRAW_RESULT_INFORM);
		List<WithdrawResultReq> needNoticeWithdraws = new ArrayList<WithdrawResultReq>();
		for (PartnerWithdrawPO partnerWithdrawPO : partnerWithdrawPOs) {
			WithdrawPO wPO = withdrawDao.findById(partnerWithdrawPO.getWithdrawId());
			logger.info("商户的提现申请状态：withdrawId={},status={}",partnerWithdrawPO.getWithdrawId(),
					wPO.getStatus());
			if(wPO.getStatus() == EntityStatus.WITHDRAW_FINISH){
				WithdrawResultReq w = new WithdrawResultReq();
				w.setMoney(wPO.getPayAmount().doubleValue());
				w.setPartnerUserId(partnerWithdrawPO.getPartnerUserId());
				w.setStatus(Constants.WITHDRAW_SUCCESS);
				w.setTransactionId(partnerWithdrawPO.getTransactionId());
				needNoticeWithdraws.add(w);
			} else if (wPO.getStatus() == EntityStatus.WITHDRAW_FAIL
					|| wPO.getStatus() == EntityStatus.WITHDRAW_REJECT){
				WithdrawResultReq w = new WithdrawResultReq();
				w.setMoney(wPO.getPayAmount().doubleValue());
				w.setPartnerUserId(partnerWithdrawPO.getPartnerUserId());
				w.setStatus(Constants.WITHDRAW_FAIL);
				w.setTransactionId(partnerWithdrawPO.getTransactionId());
				needNoticeWithdraws.add(w);
			}
		}
		return needNoticeWithdraws;
	}
}
