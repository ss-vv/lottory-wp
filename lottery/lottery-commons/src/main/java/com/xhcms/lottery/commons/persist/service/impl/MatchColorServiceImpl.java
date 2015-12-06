package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.MatchColor;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.MatchColorDao;
import com.xhcms.lottery.commons.persist.dao.PlayDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.MatchColorPO;
import com.xhcms.lottery.commons.persist.entity.PlayPO;
import com.xhcms.lottery.commons.persist.service.MatchColorService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.utils.PagingUtils;

/**
 * 赛事颜色管理
 * 
 * @author Wang Lei
 * 
 */
public class MatchColorServiceImpl implements MatchColorService {

	@Autowired
	private MatchColorDao matchColorDao;
	@Autowired
	private PlayDao playDao;
	@Autowired
	private FBMatchDao fBMatchDao;
	@Autowired
	private BBMatchDao bBMatchDao;

	@Override
	@Transactional(readOnly = true)
	public List<String> findMatchColorOnSale(String playId) {
		if (StringUtils.isBlank(playId)) {
			return null;
		}
		PlayPO pp = playDao.getWithPassType(playId);
		if (pp == null) {
			return null;
		}
		List<MatchColorPO> mcList = new ArrayList<MatchColorPO>();
		if (pp.getLotteryId().equals(Constants.JCZQ)) {
			mcList = matchColorDao.findFBMatchColorListByStatus(playId,
					EntityStatus.MATCH_ON_SALE);
		} else if (pp.getLotteryId().equals(Constants.JCLQ)) {
			mcList = matchColorDao.findBBMatchColorListByStatus(playId,
					EntityStatus.MATCH_ON_SALE);
		}
		LinkedList<String> colors = new LinkedList<String>();
		for (MatchColorPO color : mcList) {
			colors.add(color.getLeagueName());
			colors.add(color.getColor());
		}

		return colors;
	}

	@Override
	@Transactional(readOnly = true)
	public void listColoured(String lotteryId, Paging paging) {
		if (StringUtils.isBlank(lotteryId)) {
			return;
		}
		PagingUtils.makeCopyAndSetPaging(
				matchColorDao.listColoured(lotteryId, paging), paging,
				MatchColor.class);
	}

	@Override
	@Transactional(readOnly = true)
	public void listNoColor(String lotteryId, Paging paging) {
		if (StringUtils.isBlank(lotteryId)) {
			return;
		}
		if (lotteryId.equals(Constants.JCZQ)) {
			paging.setResults(matchColorDao.listNoColorFBMatchs(paging));
		} else if (lotteryId.equals(Constants.JCLQ)) {
			paging.setResults(matchColorDao.listNoColorBBMatchs(paging));
		}
	}

	@Override
	@Transactional
	public int saveColor(String lotteryId, String color, Long matchId) {
		if (StringUtils.isBlank(lotteryId) || StringUtils.isBlank(color)
				|| matchId == null) {
			return -1;
		}
		String leagueName = null;
		if (lotteryId.equals(Constants.JCZQ)) {
			leagueName = fBMatchDao.get(matchId) == null ? null : fBMatchDao
					.get(matchId).getLeagueName();
		} else if (lotteryId.equals(Constants.JCLQ)) {
			leagueName = bBMatchDao.get(matchId) == null ? null : bBMatchDao
					.get(matchId).getLeagueName();
		} else {
			return -2;
		}
		if (StringUtils.isBlank(leagueName)) {
			return -3;
		}
		MatchColorPO mCPO = new MatchColorPO();
		mCPO.setColor(color);
		mCPO.setLeagueName(leagueName);
		mCPO.setLotteryId(lotteryId);
		matchColorDao.save(mCPO);
		return 0;
	}

	@Override
	@Transactional
	public int updateColor(String color, Long matchColorId) {
		if (StringUtils.isBlank(color) || matchColorId == null) {
			return -1;
		}
		MatchColorPO mCPO = matchColorDao.get(matchColorId);
		if (mCPO == null) {
			return -2;
		}
		mCPO.setColor(color);
		matchColorDao.update(mCPO);
		return 0;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, String> loadLeagueNameColorMap() {
		return matchColorDao.loadLeagueNameColorMap();
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, String> loadLeagueNameShortName() {
		return matchColorDao.loadLeagueNameShortName();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Long saveMatchColor(String leagueName, String leagueNameForShort,
			String lotteryId, String color) {
		Long result=null;
		if (StringUtils.isBlank(leagueName)
				|| StringUtils.isBlank(leagueNameForShort)
				|| StringUtils.isBlank(lotteryId)) {
			return -1L;
		}
		MatchColorPO mCPO = new MatchColorPO();
		mCPO.setLeagueName(leagueName);
		mCPO.setLeagueShortName(leagueNameForShort);
		mCPO.setLotteryId(lotteryId);
		if (StringUtils.isBlank(color)) {
			color = Constants.DEFAULT_MATCH_COLOR;
		}
		mCPO.setColor(color);
		
		matchColorDao.save(mCPO);
		return mCPO.getId();
		
	}

	@Override
	@Transactional
	public int updateShortName(String shortName, Long matchColorId) {
		if (StringUtils.isBlank(shortName) || matchColorId == null) {
			return -1;
		}
		MatchColorPO mCPO = matchColorDao.get(matchColorId);
		if (mCPO == null) {
			return -2;
		}
		mCPO.setLeagueShortName(shortName);
		matchColorDao.update(mCPO);
		String longLeagueName = mCPO.getLeagueName();
		String lotteryId = mCPO.getLotteryId();
		if ("JCZQ".equals(lotteryId)) {
			List<FBMatchPO> fbMatchPOs = fBMatchDao.findByLeagueName(
					longLeagueName, -1L);
			for (FBMatchPO fbMatchPO : fbMatchPOs) {
				fbMatchPO.setLongLeagueName(longLeagueName);
				fbMatchPO.setLeagueName(shortName);
			}
		} else if ("JCLQ".equals(lotteryId)) {
			List<BBMatchPO> bbMatchPOs = bBMatchDao.findByLeagueName(
					longLeagueName, -1L);
			for (BBMatchPO bbMatchPO : bbMatchPOs) {
				bbMatchPO.setLongLeagueName(longLeagueName);
				bbMatchPO.setLeagueName(shortName);
			}
		} else {

		}
		return 0;
	}

	@Override
	@Transactional
	public Long saveColor(String lotteryId, String color, Long matchId,
			String shortLeagueName) {
		if (StringUtils.isBlank(lotteryId) || StringUtils.isBlank(color)
				|| matchId == null) {
			return -1L;
		}
		String leagueName = null;
		if (lotteryId.equals(Constants.JCZQ)) {
			leagueName = fBMatchDao.get(matchId) == null ? null : fBMatchDao
					.get(matchId).getLongLeagueName();
		} else if (lotteryId.equals(Constants.JCLQ)) {
			leagueName = bBMatchDao.get(matchId) == null ? null : bBMatchDao
					.get(matchId).getLongLeagueName();
		} else {
			return -2L;
		}
		if (StringUtils.isBlank(leagueName)) {
			return -3L;
		}
		MatchColorPO mCPO = new MatchColorPO();
		mCPO.setColor(color);
		mCPO.setLeagueName(leagueName);
		mCPO.setLotteryId(lotteryId);
		mCPO.setLeagueShortName(shortLeagueName);
		matchColorDao.save(mCPO);
		
		String longLeagueName = mCPO.getLeagueName();
		String ltId = mCPO.getLotteryId();
		if (Constants.JCZQ.equals(ltId)) {
			List<FBMatchPO> fbMatchPOs = fBMatchDao.findByLeagueName(
					longLeagueName, -1L);
			for (FBMatchPO fbMatchPO : fbMatchPOs) {
				fbMatchPO.setLeagueName(shortLeagueName);
			}
		} else if (Constants.JCLQ.equals(ltId)) {
			List<BBMatchPO> bbMatchPOs = bBMatchDao.findByLeagueName(
					longLeagueName, -1L);
			for (BBMatchPO bbMatchPO : bbMatchPOs) {
				bbMatchPO.setLeagueName(shortLeagueName);
			}
		} else {

		}
		return 0L;
	}

	@Override
	@Transactional
	public int updateColor(String color, Long matchColorId, String shortLeagueName) {
		if (StringUtils.isBlank(color) || matchColorId == null) {
			return -1;
		}
		MatchColorPO mCPO = matchColorDao.get(matchColorId);
		if (mCPO == null) {
			return -2;
		}
		mCPO.setColor(color);
		mCPO.setLeagueShortName(shortLeagueName);
		matchColorDao.update(mCPO);
		
		String longLeagueName = mCPO.getLeagueName();
		String ltId = mCPO.getLotteryId();
		if (Constants.JCZQ.equals(ltId)) {
			List<FBMatchPO> fbMatchPOs = fBMatchDao.findByLeagueName(
					longLeagueName, -1L);
			for (FBMatchPO fbMatchPO : fbMatchPOs) {
				fbMatchPO.setLeagueName(shortLeagueName);
			}
		} else if (Constants.JCLQ.equals(ltId)) {
			List<BBMatchPO> bbMatchPOs = bBMatchDao.findByLeagueName(
					longLeagueName, -1L);
			for (BBMatchPO bbMatchPO : bbMatchPOs) {
				bbMatchPO.setLeagueName(shortLeagueName);
			}
		} else {

		}
		return 0;
	}
}
