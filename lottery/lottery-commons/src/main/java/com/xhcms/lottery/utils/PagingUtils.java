package com.xhcms.lottery.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithAssocPO;

import com.xhcms.lottery.commons.persist.entity.UserScorePO;

/**
 * 
 * @author Yang Bo
 * 
 */
public class PagingUtils {

	private static Logger logger = LoggerFactory.getLogger(PagingUtils.class);

	public static <PO, DO> void makeCopyAndSetPaging(List<PO> list,
			Paging paging, Class<DO> dataObjectClass) {
		List<DO> rets = POUtils.copyBeans(list, dataObjectClass);
		paging.setResults(rets);
	}

	public static <DO, PO> List<DO> makeDataCopy(List<PO> list,
			Class<DO> dataObjectClass) {
		List<DO> rets = new ArrayList<DO>(list.size());
		DO b = null;
		for (PO po : list) {
			try {
				b = dataObjectClass.newInstance();
			} catch (Exception e) {
				logger.error("不能创建DataObject class的实例！class is: {}",
						dataObjectClass, e);
			}
			BeanUtils.copyProperties(po, b);
			rets.add(b);
		}
		return rets;
	}

	/**
	 * 为晒单特设的方法。 应该重构为更通用的方式。
	 * 
	 * @param schemes
	 * @param paging
	 */
	@SuppressWarnings("unchecked")
	public static List<BetScheme> makeCopyAndSetPagingForShowScheme(
			List<BetSchemeWithAssocPO> list) {
		List<BetScheme> rets = new ArrayList<BetScheme>(list.size());
		BetScheme schemeData = null;
		String[] ignoredProperties = new String[] { "userScores", "play",
				"followSchemes" };
		for (BetSchemeWithAssocPO schemePO : list) {
			schemeData = new BetScheme();
			BeanUtils.copyProperties(schemePO, schemeData, ignoredProperties);

			if (schemePO.getUserScores() != null
					&& schemePO.getUserScores().size() > 0) {
				List<UserScore> userScoreList = new ArrayList<UserScore>(
						schemePO.getUserScores().size());
				for (UserScorePO userScorePO : schemePO.getUserScores()) {
					UserScore userScoreDO = new UserScore();
					BeanUtils.copyProperties(userScorePO, userScoreDO);
					userScoreList.add(userScoreDO);
				}
				schemeData.setUserScores(userScoreList);
			} else {
				schemeData.setUserScores(Collections.EMPTY_LIST);
			}
			if (schemePO.getPlay() != null) {
				Play play = new Play();
				BeanUtils.copyProperties(schemePO.getPlay(), play,
						new String[] { "passTypes" });
				schemeData.setPlay(play);
			}
			if (schemePO.getFollowSchemes() != null) {
				List<BetScheme> followSchemeList = new ArrayList<BetScheme>(
						schemePO.getFollowSchemes().size());
				for (BetSchemePO followSchemePO : schemePO.getFollowSchemes()) {
					BetScheme followScheme = new BetScheme();
					BeanUtils.copyProperties(followSchemePO, followScheme);
					followSchemeList.add(followScheme);
				}
				schemeData.setFollowSchemes(followSchemeList);
			} else {
				schemeData.setFollowSchemes(Collections.EMPTY_LIST);
			}
			rets.add(schemeData);
		}
		return rets;
	}
}
