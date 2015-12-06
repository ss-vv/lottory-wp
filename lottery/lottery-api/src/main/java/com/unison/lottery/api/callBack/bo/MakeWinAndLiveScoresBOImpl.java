package com.unison.lottery.api.callBack.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.callBack.exception.BetMatchPOIsBlankException;
import com.unison.lottery.api.callBack.model.Buyer;
import com.unison.lottery.api.callBack.model.LiveScores;
import com.unison.lottery.api.callBack.model.WinAndLiveScores;
import com.unison.lottery.api.login.hx.httpclient.apidemo.HX_sendMassage;
import com.unison.lottery.api.vGroupPublishScheme.exception.BetSchemeIsBlankException;
import com.unison.lottery.api.vGroupPublishScheme.exception.UserIsBlankException;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PublishBetScheme;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.PublishBetSchemeService;
import com.xhcms.ucenter.persistent.service.IUserService;

public class MakeWinAndLiveScoresBOImpl implements MakeWinAndLiveScoresBO {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PublishBetSchemeService publishBetSchemeService;

	@Autowired
	private BetSchemeService betSchemeService;

	@Autowired
	private IUserService userService;

	@Override
	public boolean makeWinAndLiveScoresParams2Groups(LiveScores liveScores) {
		boolean result = false;
		try {
				String matchId = null;
				if(StringUtils.equals("0", liveScores.subType)){ //篮球
					matchId = liveScores.basketballMatchMessage.getMatchId();
				} else { //足球
					matchId = liveScores.footballMatchMessage.getMatchId();
				}
				List<PublishBetScheme> publishBetSchemes = publishBetSchemeService.findByMatchId(matchId);
				if (publishBetSchemes == null || publishBetSchemes.isEmpty()) {
					logger.info("没有此赛事id:{},对应的群方案", matchId);
					throw new BetMatchPOIsBlankException();
				}
				HashSet<PublishBetScheme> hs = new HashSet<PublishBetScheme>(publishBetSchemes);// 去重
				Map<String, List<PublishBetScheme>> map = new HashMap<String, List<PublishBetScheme>>();
				List<PublishBetScheme> puBetSchemes = new ArrayList<PublishBetScheme>();
				// key:groupid ; value:群里的方案
				for (PublishBetScheme publishBetScheme : hs) {
					logger.info("发送的方案id : {} / 群号：{}" , publishBetScheme.getId(), publishBetScheme.getGroupid());
					puBetSchemes.clear();
					if (map.containsKey(publishBetScheme.getGroupid())) {
						map.get(publishBetScheme.getGroupid()).add(publishBetScheme);
					} else {
						puBetSchemes.add(publishBetScheme);
						map.put(publishBetScheme.getGroupid(), puBetSchemes);
					}
				}
				List<Buyer> buyers = null;
				Buyer buyer = null;
				for (String groupid : map.keySet()) {
					puBetSchemes = map.get(groupid);
					buyers = new ArrayList<Buyer>();
					for (PublishBetScheme pScheme : puBetSchemes) {// 制造buy
						buyer = new Buyer();
						User user = userService.getUser(pScheme.getUserId());
						if (user == null) {
							logger.info("用户为null, 此用户id为: {}", pScheme.getUserId());
							throw new UserIsBlankException();
						}
						buyer.nickName = user.getNickName();
						buyers.add(buyer);
					}
					liveScores.buyers = buyers;
					HX_sendMassage hx_sendMassage = new HX_sendMassage();
					ObjectMapper mapper = new ObjectMapper();
					String jsonStr = mapper.writeValueAsString(liveScores);
					if (hx_sendMassage.sendMsg2Group(groupid, jsonStr)) {
						result = true;
					}
				}
		} catch (BetMatchPOIsBlankException e) {
			e.printStackTrace();
		} catch (UserIsBlankException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean makeWinParams2Groups(WinAndLiveScores winAndLiveScores, String schemeId) {
		boolean result = false;
		List<PublishBetScheme> publishBetSchemes = publishBetSchemeService.getPublishBetSchemesBySchemeId(Long.valueOf(schemeId));
		HashSet<PublishBetScheme> hs = new HashSet<PublishBetScheme>(publishBetSchemes);// 去重
		Map<String, List<PublishBetScheme>> map = new HashMap<String, List<PublishBetScheme>>();
		List<PublishBetScheme> puBetSchemes = new ArrayList<PublishBetScheme>();
		// key:groupid ; value:群里的方案
		for (PublishBetScheme publishBetScheme : hs) {
			puBetSchemes.clear();
			if (map.containsKey(publishBetScheme.getGroupid())) {
				map.get(publishBetScheme.getGroupid()).add(publishBetScheme);
			} else {
				puBetSchemes.add(publishBetScheme);
				map.put(publishBetScheme.getGroupid(), puBetSchemes);
			}
		}
		List<Buyer> buyers = null;
		Buyer buyer = null;
		try {
			for (String groupid : map.keySet()) {
				puBetSchemes = map.get(groupid);
				buyers = new ArrayList<Buyer>();
				for (PublishBetScheme pScheme : puBetSchemes) {// 制造buy
					buyer = new Buyer();
					BetScheme betScheme = betSchemeService.getSchemeById(pScheme.getBetSchemeId());
					User user = userService.getUser(pScheme.getUserId());
					if (user == null) {
						logger.info("用户为null, 此用户id为: {}", pScheme.getUserId());
						throw new UserIsBlankException();
					}
					if(betScheme == null){
						logger.info("方案为null, 此方案id为: {}", pScheme.getBetSchemeId());
						throw new BetSchemeIsBlankException();
					}
					buyer.nickName = user.getNickName();
					buyer.money = String.valueOf(betScheme.getTotalAmount());
					buyers.add(buyer);
				}
				winAndLiveScores.buyers = buyers;
				HX_sendMassage hx_sendMassage = new HX_sendMassage();
				ObjectMapper mapper = new ObjectMapper();
				String jsonStr = mapper.writeValueAsString(winAndLiveScores);
				if (hx_sendMassage.sendMsg2Group(groupid, jsonStr)) {
					result = true;
				}
			}
		} catch (UserIsBlankException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
}
