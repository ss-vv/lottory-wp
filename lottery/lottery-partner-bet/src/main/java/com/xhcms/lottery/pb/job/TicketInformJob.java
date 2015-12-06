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

import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.pb.dao.PartnerBetDao;
import com.xhcms.lottery.pb.dao.PartnerTicketDao;
import com.xhcms.lottery.pb.model.Constants;
import com.xhcms.lottery.pb.model.MESSAGE_TYPE;
import com.xhcms.lottery.pb.model.VerificationModel;
import com.xhcms.lottery.pb.po.PartnerTicketPO;
import com.xhcms.lottery.pb.service.PartnerBetService;
import com.xhcms.lottery.pb.util.DateTimeUtil;
import com.xhcms.lottery.pb.util.HttpClientUtil;
import com.xhcms.lottery.pb.util.JAXBContextUtil;
import com.xhcms.lottery.pb.util.MD5Util;
import com.xhcms.lottery.pb.util.VelocityUtil;
import com.xhcms.lottery.pb.xml.model.Msg;
import com.xhcms.lottery.pb.xml.model.TicketReq;
import com.xhcms.lottery.pb.xml.model.TicketResp;

/**
 * 出票通知服务
 * @author haoxiang.jiang
 * 2014年9月10日 上午10:57:54
 */
public class TicketInformJob {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	PartnerBetDao partnerBetDao;
	@Autowired
	PartnerTicketDao partnerTicketDao;
	@Autowired
	PartnerBetService partnerBetService;
	@Autowired
	TicketDao ticketDao;
	@Autowired
	UserDao userDao;
	@Transactional
	public void work() {
		String partnerId = "youyuanwang";
		List<TicketReq> ticketReqs = getNeedNoticeTicket(partnerId);
		if (ticketReqs.size() > 0) {
			// convert 'Need to Notice Ticket List' to 'XML String'
			VelocityContext vContext = new VelocityContext();
			vContext.put("partnerId", partnerId);
			vContext.put("time", DateTimeUtil.getTimeString(new Date()));
			vContext.put("version", Constants.API_VERSION);
			vContext.put("ticketReqs", ticketReqs);
			String contentXml = VelocityUtil.merge(vContext, "../vm/draw-notice.xml");
			logger.info("出票通知请求消息体，contentXml:\n" + contentXml);

//			String responseXml = this.doPush(contentXml,partnerId);
			//test code ---start----
			String responseXml = getTestRespXml(ticketReqs,
					MESSAGE_TYPE.DRAW_TICKET_RESP.getCode(), partnerId);
			//test code ---end----
			if (null == responseXml) {
				logger.info("验证失败！");
				return;
			}
			logger.info("出票通知响应消息体，responseXml:\n" + responseXml);

			Msg msg = this.convertToMsg(responseXml);
			String msgType = msg.getHead().getMsg();
			if (null != msgType
					&& MESSAGE_TYPE.DRAW_TICKET_RESP.getCode().equals(msgType)) {
				List<TicketResp> ticketResps = msg.getBody().getTicketResp();
				if (null != ticketResps) {
					this.dealWithTicketResps(ticketResps);
				} else {
					logger.info("消息body为空");
				}
			} else {
				logger.info("非法消息类型：{}", msgType);
			}
		} else {
			logger.info("当前TicketInformJob 无数据需要处理！");
		}
	}
	
	//TODO  测试用
	private String getTestRespXml(List<TicketReq> ticketReqs,String msgType,String partnerId){
		String responseXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<msg>\n" +
				"  <head msg=\""+msgType+"\" partnerId=\""+partnerId+"\" version=\"1.0\" time=\"20140901175541\" />\n" +
				"  <body>\n" + 
				"{{replace}}" +
				"  </body>\n" + 
				"</msg>";
		String ticString = "";
		for (TicketReq ticketReq : ticketReqs) {
		ticString += "    <ticketResp id=\"" + ticketReq.getId() + "\" status=\"1\"/>\n";
		}
		responseXml = responseXml.replace("{{replace}}", ticString);
		return responseXml;
	}
	
	private void dealWithTicketResps(List<TicketResp> ticketResps) {
		for (TicketResp ticketResp : ticketResps) {
			if (Constants.TICKET_DRAW_NOTIFY_REV_SUCCESS == ticketResp.getStatus()) {
				PartnerTicketPO p = partnerTicketDao.findByTicketId(ticketResp.getId());
				if(null != p){
					p.setStatus(Constants.DRAW_TICKET_HAVE_INFORMED);
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

	/**
	 * 执行请求，返回响应xml字符串
	 * 
	 * @param contentXml
	 * @return
	 */
	private String doPush(String contentXml,String partnerId) {
		Header[] requestHeard = new Header[3];
		requestHeard[0] = new BasicHeader(Constants.PARTNER_ID,partnerId);
		requestHeard[1] = new BasicHeader(Constants.MSG_TYPE,MESSAGE_TYPE.DRAW_TICKET_REQ.getCode());
		String needSignBody = partnerId + Constants.PARTNER_KEYS.get(partnerId) + contentXml.trim();
		requestHeard[2] = new BasicHeader(Constants.SIGNATURE,MD5Util.md5(needSignBody));
		try {
			logger.info("出票通知被签名消息体：{}\n"
					+ "长度：{}", needSignBody,
					needSignBody.getBytes("iso-8859-1").length);
			logger.info("签名：{}", MD5Util.md5(needSignBody));
		} catch (UnsupportedEncodingException e) {
			logger.error("出票通知消息体getBytes('iso-8859-1') 出错", e);
		}
		String url = Constants.PARTNER_URLS.get(partnerId).get(MESSAGE_TYPE.DRAW_TICKET_REQ);
		HttpClientUtil client = new HttpClientUtil(url, requestHeard, contentXml);
		String responseXml = new String();
		Map<String, String> headerMap = new HashMap<String, String>();
		client.exec();
		responseXml = client.getResponseXml();
		headerMap = client.getHeaderMap();
		boolean verifyResult = new VerificationModel(partnerId, responseXml, headerMap).verify();
		return verifyResult ? responseXml : null;
	}

	/**
	 * 获取需要通知的消息 条件：等待出票
	 * 
	 * @return
	 */
	private List<TicketReq> getNeedNoticeTicket(String partnerId) {
		UserPO user = userDao.getUserByUsername(partnerId);
		List<PartnerTicketPO> partnerTicketPOs = partnerTicketDao.listByStatus(
				Constants.RESULT_MAX_SIZE, Constants.WAIT_DRAW_TICKET_INFORM,user.getId());
		List<TicketReq> needNoticeTickets = new ArrayList<TicketReq>();
		for (PartnerTicketPO partnerTicketPO : partnerTicketPOs) {
			TicketPO ticketPO = ticketDao.findById(partnerTicketPO.getTicketId());
			logger.info("来彩票状态：id={},status={}",ticketPO.getId(),ticketPO.getStatus());
			if (EntityStatus.TICKET_BUY_SUCCESS == ticketPO.getStatus()
					|| EntityStatus.TICKET_NOT_WIN == ticketPO.getStatus()
					|| EntityStatus.TICKET_NOT_AWARD == ticketPO.getStatus()
					|| EntityStatus.TICKET_AWARDED == ticketPO.getStatus()) { 
				//出票成功通知
				//未通知出票成功就已经开奖情况，先做出票成功通知处理
				TicketReq ticketReq = new TicketReq();
				ticketReq.setId(ticketPO.getId());
				ticketReq.setStatus(Constants.TICKET_DRAW_SUCCESS);
				needNoticeTickets.add(ticketReq);
			} else if (EntityStatus.TICKET_REQUIRED == ticketPO.getStatus()
					|| EntityStatus.TICKET_ALLOW_BUY == ticketPO.getStatus()
					|| EntityStatus.TICKET_INIT == ticketPO.getStatus()) {
				// 未出票，可出票，已经请求出票，状态不处理
			} else {
				// 其他状态视为出票失败
				TicketReq ticketReq = new TicketReq();
				ticketReq.setId(ticketPO.getId());
				ticketReq.setStatus(Constants.TICKET_DRAW_FAILURE);
				needNoticeTickets.add(ticketReq);
			}
		}
		return needNoticeTickets;
	}
}
