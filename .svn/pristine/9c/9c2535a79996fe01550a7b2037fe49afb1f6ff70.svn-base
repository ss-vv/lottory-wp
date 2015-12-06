package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List; 

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.PublishBetScheme;
import com.xhcms.lottery.commons.persist.dao.PublishBetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.PublishBetSchemePO;
import com.xhcms.lottery.commons.persist.service.PublishBetSchemeService;

@Transactional(readOnly=true)
public class PublishBetSchemeServiceImpl implements PublishBetSchemeService  {

	@Autowired
	private PublishBetSchemeDao publishBetSchemeDao;
	
	@Override
	@Transactional
	public boolean savePublishScheme(PublishBetScheme publishBetScheme, List<BetMatchPO> matchPOs) {
		boolean result = false;
		PublishBetSchemePO publishBetSchemePO = null;
		if(matchPOs != null && !matchPOs.isEmpty()){
			for(BetMatchPO betMatchPO : matchPOs){
				if(publishBetScheme != null){
					publishBetSchemePO = new PublishBetSchemePO();
					publishBetSchemePO.setBetSchemeId(publishBetScheme.getBetSchemeId());
					publishBetSchemePO.setGroupid(publishBetScheme.getGroupid());
					publishBetSchemePO.setUserId(publishBetScheme.getUserId());
					publishBetSchemePO.setMatchId(betMatchPO.getMatchId());
					publishBetSchemePO.setNickname(publishBetScheme.getNickName());
					publishBetSchemePO.setImageUrl(publishBetScheme.getImageUrl());
					publishBetSchemePO.setCreateDate(new Date());
					
					publishBetSchemeDao.savePublishScheme(publishBetSchemePO);
				}
			}
			result = true;
		} 
		return result;
	}

	@Override
	public PublishBetScheme getPublishBetSchemeByBetSchemeId(long betSchemeId) {
		PublishBetScheme publishBetScheme = null;
		PublishBetSchemePO publishBetSchemePO = publishBetSchemeDao.getPublishBetSchemePOByBetSchemeId(betSchemeId);
		if(publishBetSchemePO != null){
			publishBetScheme = new PublishBetScheme();
			BeanUtils.copyProperties(publishBetSchemePO, publishBetScheme);
			publishBetScheme.setNickName(publishBetSchemePO.getNickname());
		}
		return publishBetScheme;
	}

	@Override
	public List<PublishBetScheme> getPublishBetSchemeByUserId(long userId) {
		List<PublishBetScheme> publishBetSchemes = new ArrayList<PublishBetScheme>();
		List<PublishBetSchemePO> publishBetSchemePOs = publishBetSchemeDao.getPublishBetSchemePOByUserId(userId);
		if(publishBetSchemePOs != null && !publishBetSchemePOs.isEmpty()){
			for(PublishBetSchemePO po : publishBetSchemePOs){
				PublishBetScheme publishBetScheme = new PublishBetScheme();
				BeanUtils.copyProperties(po, publishBetScheme);
				publishBetScheme.setNickName(po.getNickname());
				publishBetSchemes.add(publishBetScheme);
			}
		}
		return publishBetSchemes;
	}
	
	@Override
	public List<PublishBetScheme> getPublishBetSchemeByUserIdWithPages(long userId, String pages, int maxResult,String groupid) {
		List<PublishBetScheme> publishBetSchemes = new ArrayList<PublishBetScheme>();
		List<PublishBetSchemePO> publishBetSchemePOs = publishBetSchemeDao.getPublishBetSchemePOByUserIdAndPages(userId, pages, maxResult, groupid);
		if(publishBetSchemePOs != null && !publishBetSchemePOs.isEmpty()){
			for(PublishBetSchemePO po : publishBetSchemePOs){
				PublishBetScheme publishBetScheme = new PublishBetScheme();
				BeanUtils.copyProperties(po, publishBetScheme);
				publishBetScheme.setNickName(po.getNickname());
				publishBetSchemes.add(publishBetScheme);
			}
		}
		return publishBetSchemes;
	}
	@Override
	public List<PublishBetScheme> getPubSchemeIsOthersWithPages(long userId, long daVId,String pages,int maxResult,String groupid) {
		List<PublishBetScheme> publishBetSchemes = new ArrayList<PublishBetScheme>();
		List<PublishBetSchemePO> publishBetSchemePOs = publishBetSchemeDao.getPubSchemeIsOthersWithPages(userId, daVId,  pages, maxResult, groupid);
		if(publishBetSchemePOs != null && !publishBetSchemePOs.isEmpty()){
			for(PublishBetSchemePO po : publishBetSchemePOs){
				PublishBetScheme publishBetScheme = new PublishBetScheme();
				BeanUtils.copyProperties(po, publishBetScheme);
				publishBetScheme.setNickName(po.getNickname());
				publishBetSchemes.add(publishBetScheme);
			}
		}
		return publishBetSchemes;
	}

	@Override
	public List<PublishBetScheme> getPublishBetSchemeByGroupId(String groupid) {
		List<PublishBetScheme> publishBetSchemes = new ArrayList<PublishBetScheme>();
		List<PublishBetSchemePO> publishBetSchemePOs = publishBetSchemeDao.getPublishBetSchemePOByGroupId(groupid);
		if(publishBetSchemePOs != null && !publishBetSchemePOs.isEmpty()){
			for(PublishBetSchemePO po : publishBetSchemePOs){
				PublishBetScheme publishBetScheme = new PublishBetScheme();
				BeanUtils.copyProperties(po, publishBetScheme);
				publishBetScheme.setNickName(po.getNickname());
				publishBetSchemes.add(publishBetScheme);
			}
		}
		return publishBetSchemes;
	}
	
	@Override
	public List<PublishBetScheme> getPublishBetSchemesBySchemeId(long betSchemeId) {
		List<PublishBetScheme> publishBetSchemes = new ArrayList<PublishBetScheme>();
		List<PublishBetSchemePO> publishBetSchemePOs = publishBetSchemeDao.getPublishBetSchemePOsBySchemeId(betSchemeId);
		if(publishBetSchemePOs != null && !publishBetSchemePOs.isEmpty()){
			for(PublishBetSchemePO po : publishBetSchemePOs){
				PublishBetScheme publishBetScheme = new PublishBetScheme();
				BeanUtils.copyProperties(po, publishBetScheme);
				publishBetScheme.setNickName(po.getNickname());
				publishBetSchemes.add(publishBetScheme);
			}
		}
		return publishBetSchemes;
	}

	@Override
	public List<PublishBetScheme> findByMatchId(String matchId) {
		List<PublishBetScheme> publishBetSchemes = new ArrayList<PublishBetScheme>();
		List<PublishBetSchemePO> publishBetSchemePOs = publishBetSchemeDao.getPublishBetSchemePOByMatchId(Long.valueOf(matchId));
		if(publishBetSchemePOs != null && !publishBetSchemePOs.isEmpty()){
			for(PublishBetSchemePO po : publishBetSchemePOs){
				PublishBetScheme publishBetScheme = new PublishBetScheme();
				BeanUtils.copyProperties(po, publishBetScheme);
				publishBetScheme.setNickName(po.getNickname());
				publishBetSchemes.add(publishBetScheme);
			}
		}
		return publishBetSchemes;
	}

	@Override
	@Transactional
	public boolean isExistPublishBetScheme(PublishBetScheme publishBetScheme){
		boolean result = false;
		List<PublishBetSchemePO> publishBetSchemePOs = publishBetSchemeDao.getPublishBetSchemePOByOtherParams(publishBetScheme.getUserId(), publishBetScheme.getGroupid(), publishBetScheme.getBetSchemeId());
		if(publishBetSchemePOs != null && !publishBetSchemePOs.isEmpty()){
			for(PublishBetSchemePO po : publishBetSchemePOs){
				po = publishBetSchemeDao.findById(po.getId());
				if(po != null){
					po.setCreateDate(new Date());
					publishBetSchemeDao.savePublishScheme(po);
				}
			}
			result = true;
		}
		return result;
	}
	
}
