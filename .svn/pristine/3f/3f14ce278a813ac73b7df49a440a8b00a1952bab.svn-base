package com.xhcms.lottery.commons.persist.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.commons.data.proto.BetSchemeVo;
import com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme;
import com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.BetMatch;
import com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.BetPartner;
import com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.CGJTeam;
import com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.CTBetContent.Builder;
import com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.DigitalBetContentOrBuilder;
import com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.PassType;
import com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.Play;
import com.xhcms.lottery.commons.util.NullValueConver;

public class BetSchemeVoServiceImpl implements
		com.xhcms.lottery.commons.persist.service.BetSchemeVoService {
    /**
     * 解析BetScheme 放入protobuffer
     */
	@Override
	public byte[] getBetSchemeByte(com.xhcms.lottery.commons.data.BetScheme bet) {
	
		BetScheme.Builder bet_=getBetSchemeBuilder_(bet);
		List<com.xhcms.lottery.commons.data.BetScheme> list = bet.getFollowSchemes();
		if(list!=null&&list.size()>0){
			
		   for(int i=0;i<list.size();i++){
			
			  BetScheme.Builder bet1=getBetSchemeBuilder_(list.get(i));
			
			  
			  bet_.addFollowSchemes(i, bet1);
		   }
		}
		
		return bet_.build().toByteArray();
	}

	/**
	 * 把protobuffer 字节数组转换为 BetScheme类
	 */
	@Override
	public com.xhcms.lottery.commons.data.BetScheme getBetScheme(byte[] b) {
		BetScheme bet= getBetScheme_(b);
		com.xhcms.lottery.commons.data.BetScheme betscheme_=getBetScheme_t(bet);
		List<BetScheme> list=null;
		if(bet!=null){
			
			list=bet.getFollowSchemesList();
			if(list!=null){
				List<com.xhcms.lottery.commons.data.BetScheme> bt=new ArrayList<com.xhcms.lottery.commons.data.BetScheme>();
				for(int i=0;i<list.size();i++){
					
					bt.add(getBetScheme_t(list.get(i)));
				}
				betscheme_.setFollowSchemes(bt);
				
			}
			
		}
		
		
		
		return betscheme_;
	
		
	}
	
	public BetScheme getBetScheme_(byte[] b){
		
		try {
			return BetScheme.parseFrom(b);
		} catch (InvalidProtocolBufferException e) {
			return null;
			//e.printStackTrace();
		}
	}
	public com.xhcms.lottery.commons.data.BetScheme getBetScheme_t(BetScheme bt){
		
		com.xhcms.lottery.commons.data.BetScheme nbet=new com.xhcms.lottery.commons.data.BetScheme();
		BetScheme bet=null;
		if(bt!=null){
			
			bet=bt;
		}else{
			
			
			return nbet;
		}
		
		nbet.setId(bet.getId());
		nbet.setLotteryId(bet.getLotteryId());
		nbet.setPlayId(bet.getPlayId());
		nbet.setPassTypeIds(bet.getPassTypeIds());
		nbet.setSponsor(bet.getSponsor());
		nbet.setSponsorId(bet.getSponsorId());
		nbet.setStatus(bet.getStatus());
		nbet.setType(bet.getType());
		nbet.setShowScheme(bet.getShowScheme());
		nbet.setShowSchemeSlogan(bet.getShowSchemeSlogan());
		nbet.setIsPublishShow(bet.getIsPublishShow());
		nbet.setCreatedTime(NullValueConver.longToConver_(bet.getCreatedTime()));
		nbet.setOfftime(NullValueConver.longToConver_(bet.getOfftime()));
		nbet.setTotalAmount(bet.getTotalAmount());
		nbet.setShareRatio(bet.getShareRatio());
		nbet.setBuyAmount(bet.getBuyAmount());
		nbet.setFloorAmount(bet.getFloorAmount());
		nbet.setPurchasedAmount(bet.getPurchasedAmount());
		nbet.setBetNote(bet.getBetNote());
		nbet.setMatchNumber(bet.getMatchNumber());
		nbet.setMultiple(bet.getMultiple());
		nbet.setPrivacy(bet.getPrivacy());
		nbet.setFollowSchemePrivacy(bet.getFollowSchemePrivacy());
		nbet.setWinNote(bet.getWinNote());
		nbet.setAwardTime(NullValueConver.longToConver_(bet.getAwardTime()));
		nbet.setMaxBonus(NullValueConver.bigDecimalToConver(bet.getMaxBonus()));
		nbet.setExpectBonus(NullValueConver.bigDecimalToConver(bet.getExpectBonus()));
		nbet.setPreTaxBonus(NullValueConver.bigDecimalToConver(bet.getPreTaxBonus()));
		nbet.setAfterTaxBonus(NullValueConver.bigDecimalToConver(bet.getAfterTaxBonus()));
		nbet.setTicketNote(bet.getTicketNote());
		nbet.setCancelNote(bet.getCancelNote());
		nbet.setTicketCount(bet.getTicketCount());
		nbet.setPartnerCount(bet.getPartnerCount());
		nbet.setSaleStatus(bet.getSaleStatus());
		nbet.setFollowedSchemeId(bet.getFollowedSchemeId());
		nbet.setFollowedRatio(bet.getFollowedRatio());
		nbet.setRecommendation(bet.getRecommendation());
		nbet.setSingleAmount(bet.getSingleAmount());
		nbet.setPublicTime(NullValueConver.longToConver_(bet.getPublicTime()));
		nbet.setAfterTaxBonusReturnRatio(bet.getAfterTaxBonusReturnRatio());
		nbet.setMaxBonusReturnRatio(bet.getMaxBonusReturnRatio());
		nbet.setIssueNumber(bet.getIssueNumber());
		nbet.setPassTypes(bet.getPassTypesList());
		nbet.setFollowTotalAmount(bet.getFollowTotalAmount());
		nbet.setFollowTotalBonus(NullValueConver.bigDecimalToConver(bet.getFollowTotalBonus()));
		nbet.setIsPresetAward(bet.getIsPresetAward());
		nbet.setMatchAnnotation(bet.getMatchAnnotation());
		nbet.setFollowingCount(bet.getFollowingCount());
	
		if(bet.getMatchsList()!=null){
			List<com.xhcms.lottery.commons.data.BetMatch> blist=new ArrayList<com.xhcms.lottery.commons.data.BetMatch>();
			for(int i=0;i<bet.getMatchsList().size();i++){
				BetMatch b_t=bet.getMatchsList().get(i);
				 com.xhcms.lottery.commons.data.PlayMatch bm=new  com.xhcms.lottery.commons.data.PlayMatch();
				 bm.setId(b_t.getId());
				 bm.setSchemeId(b_t.getSchemeId());
				 bm.setMatchId(b_t.getMatchId());
				 bm.setCode(b_t.getCode());
				 bm.setCnCode(b_t.getCnCode());
				 bm.setOdds(b_t.getOdds());
				 bm.setEntrustDeadline(NullValueConver.longToConver_(b_t.getEntrustDeadline()));
				 bm.setSeed(b_t.getSeed());
				 bm.setDefaultScore(b_t.getDefaultScore());
				 bm.setPlayId(b_t.getPlayId());
				 bm.setAnnotation(b_t.getAnnotation());
				 bm.setName(b_t.getName());
				 bm.setConcedePoints(b_t.getConcedePoints());
				 bm.setScore(b_t.getScore());
				 bm.setScore1(b_t.getScore1());
				 bm.setScore2(b_t.getScore2());
				 bm.setScore3(b_t.getScore3());
				 bm.setScore4(b_t.getScore4());
				 bm.setResult(b_t.getResult().equals("")?null:b_t.getResult());
				 bm.setResultOdd(b_t.getResultOdd());
				 bm.setBetCode(b_t.getBetCode());
				 bm.setPlayingTime(NullValueConver.longToConver_(b_t.getPlayingTime()));
				 bm.setStatus(b_t.getStatus());
				 blist.add(bm);
				
			}
			nbet.setMatchs(blist);
		}
		
		if(bet.getCgjTeamsList()!=null){
			List<com.xhcms.lottery.dc.data.CGJTeam> cglist=new ArrayList<com.xhcms.lottery.dc.data.CGJTeam>();
			for(CGJTeam cg : bet.getCgjTeamsList()){
				com.xhcms.lottery.dc.data.CGJTeam ncgj=new com.xhcms.lottery.dc.data.CGJTeam();
				ncgj.setMatchId(cg.getMatchId());
				ncgj.setCnCode(cg.getCnCode());
				ncgj.setCode(cg.getCode());
				ncgj.setLeague(cg.getLeague());
				ncgj.setName(cg.getName());
				ncgj.setLongLeagueName(cg.getLongLeagueName());
				ncgj.setHomeTeam(cg.getHomeTeam());
				ncgj.setGuestTeam(cg.getGuestTeam());
				ncgj.setHomeTeamId(cg.getHomeTeamId());
				ncgj.setGuestTeamId(cg.getGuestTeamId());
				ncgj.setOfftime(NullValueConver.longToConver_(cg.getOfftime()));
				ncgj.setPlayingTime(NullValueConver.longToConver_(cg.getPlayingTime()));
				ncgj.setEntrustDeadline(NullValueConver.longToConver_(cg.getEntrustDeadline()));
				ncgj.setConcedePoints(cg.getConcedePoints());
				ncgj.setConcedeScorePass(cg.getConcedeScorePass());
				ncgj.setGuestScore(cg.getGuestScore());
				ncgj.setGuestScorePass(cg.getGuestScorePass());
				ncgj.setTeamId(cg.getTeamId());
				ncgj.setTeamName(cg.getTeamName());
				ncgj.setOdds(NullValueConver.bigDecimalToConver(cg.getOdds()));
				ncgj.setGroupName(cg.getGroupName());
				ncgj.setStatus(cg.getStatus());
				ncgj.setTeamLogo(cg.getTeamLogo());
				ncgj.setRank(cg.getRank());
				ncgj.setSeason(cg.getSeason());
				ncgj.setOdd(cg.getOdd());
				ncgj.setResult(cg.getResult());
				cglist.add(ncgj);
	
			}
			nbet.setCgjTeams(cglist);
			
		}
		if(bet.getPlay()!=null){
			Play p=bet.getPlay();
			com.xhcms.lottery.commons.data.Play play=new com.xhcms.lottery.commons.data.Play();
		    play.setId(p.getId());
		    play.setLotteryId(p.getLotteryId());
		    play.setName(p.getName());
		    play.setFloorRatio(p.getFloorRatio());
		    
		    if(bet.getPlay().getPassTypesList()!=null){
		    	com.xhcms.lottery.commons.data.PassType npt=new com.xhcms.lottery.commons.data.PassType();
		    	List<com.xhcms.lottery.commons.data.PassType> ptlist=new ArrayList<com.xhcms.lottery.commons.data.PassType>();
		    	for(PassType pt : bet.getPlay().getPassTypesList()){
		    		npt.setId(pt.getId());
		    		npt.setName(pt.getName());
		    		npt.setNote(pt.getNote());
		    		ptlist.add(npt);
		    	}
		    	play.setPassTypes(ptlist);
		    	
		    }
		    nbet.setPlay(play);
		}
			
		if(bet.getGroupbuyPartnersList() != null) {
			List<BetPartner> list = bet.getGroupbuyPartnersList();
			List<com.xhcms.lottery.commons.data.BetPartner> groupbuyPartners = 
					new ArrayList<com.xhcms.lottery.commons.data.BetPartner>();
			for(int i=0; i<list.size(); i++) {
				BetPartner partner = list.get(i);
				com.xhcms.lottery.commons.data.BetPartner betP = new com.xhcms.lottery.commons.data.BetPartner();
				betP.setId(partner.getId());
				betP.setSchemeId(partner.getSchemeId());
				betP.setUserId(partner.getUserId());
				betP.setUsername(partner.getUsername());
				betP.setBetAmount(partner.getBetAmount());
				betP.setWinAmount(new BigDecimal(partner.getWinAmount()));
				betP.setCreatedTime(new Date(partner.getCreatedTime()));
				betP.setCommission(new BigDecimal(partner.getCommission()));
				groupbuyPartners.add(betP);
			}
			nbet.setGroupbuyPartners(groupbuyPartners);
		}
		
		if(null!=bet.getDigitalBetContentsList()){
			List<com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.DigitalBetContent> list = bet.getDigitalBetContentsList();
			List<DigitalBetContent> digitalBetContents=new ArrayList<DigitalBetContent>();
			for(int i=0;i<list.size();i++){
				com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.DigitalBetContent digitalBetContent=list.get(i);
				DigitalBetContent digitalBetContentNew=new DigitalBetContent();
				digitalBetContentNew.setBonusCode(digitalBetContent.getBonusCode());
				digitalBetContentNew.setChooseType(digitalBetContent.getChooseType());
				digitalBetContentNew.setCode(digitalBetContent.getCode());
				digitalBetContentNew.setId(digitalBetContent.getId());
				digitalBetContentNew.setIssueId(digitalBetContent.getIssueId());
				digitalBetContentNew.setIssueNumber(digitalBetContent.getIssueNumber());
				digitalBetContentNew.setLotteryId(digitalBetContent.getLotteryId());
				digitalBetContentNew.setMoney(digitalBetContent.getMoney());
				digitalBetContentNew.setMultiple(digitalBetContent.getMultiple());
				digitalBetContentNew.setNote(digitalBetContent.getNote());
				digitalBetContentNew.setPlayId(digitalBetContent.getPlayId());
				digitalBetContentNew.setSchemeId(digitalBetContent.getSchemeId());
				digitalBetContents.add(digitalBetContentNew);
			}
			nbet.setDigitalBetContents(digitalBetContents);
			
		}
		
		if(null!=bet.getCtBetContentsList()){
			List<com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.CTBetContent> list = bet.getCtBetContentsList();
			List<CTBetContent> ctBetContents=new ArrayList<CTBetContent>();
			for(int i=0;i<list.size();i++){
				com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.CTBetContent ctBetContent = list.get(i);
				CTBetContent ctBetContentNew=new CTBetContent();
				ctBetContentNew.setChooseType(ctBetContent.getChooseType());
				ctBetContentNew.setCode(ctBetContent.getCode());
				ctBetContentNew.setId(ctBetContent.getId());
				ctBetContentNew.setIssueId(ctBetContent.getIssueId());
				ctBetContentNew.setIssueNumber(ctBetContent.getIssueNumber());
				ctBetContentNew.setLotteryId(ctBetContent.getLotteryId());
				ctBetContentNew.setPlayId(ctBetContent.getPlayId());
				ctBetContentNew.setSchemeId(ctBetContent.getSchemeId());
				ctBetContentNew.setSeed(ctBetContent.getSeed());
				ctBetContents.add(ctBetContentNew);
			}
			nbet.setCtBetContents(ctBetContents);
		}
		
		if(null!=bet.getCtFBMatchsList()){
			List<com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.CTFBMatch> list = bet.getCtFBMatchsList();
			List<CTFBMatch> ctFBMatchs=new ArrayList<CTFBMatch>();
			for(int i=0;i<list.size();i++){
				com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.CTFBMatch ctFBMatch = list.get(i);
				CTFBMatch ctFBMatchNew=new CTFBMatch();
				ctFBMatchNew.setCode(ctFBMatch.getCode());
				ctFBMatchNew.setColor(ctFBMatch.getColor());
				ctFBMatchNew.setDefaultScore(ctFBMatch.getDefaultScore());
				ctFBMatchNew.setEntrustDeadline(NullValueConver.longToConver_(ctFBMatch.getEntrustDeadline()));
				ctFBMatchNew.setGuestTeamName(ctFBMatch.getGuestTeamName());
				ctFBMatchNew.setHalfScore(ctFBMatch.getHalfScore());
				ctFBMatchNew.setHomeTeamName(ctFBMatch.getHomeTeamName());
				ctFBMatchNew.setId(ctFBMatch.getId());
				ctFBMatchNew.setIssueNumber(ctFBMatch.getIssueNumber());
				ctFBMatchNew.setLeagueName(ctFBMatch.getLeagueName());
				ctFBMatchNew.setMatchId(ctFBMatch.getMatchId());
				ctFBMatchNew.setName(ctFBMatch.getName());
				ctFBMatchNew.setOdds(ctFBMatch.getOdds());
				ctFBMatchNew.setOfftime(NullValueConver.longToConver_(ctFBMatch.getOfftime()));
				ctFBMatchNew.setOptions(ctFBMatch.getOptions());
				ctFBMatchNew.setPlayId(ctFBMatch.getPlayId());
				ctFBMatchNew.setPlayingTime(NullValueConver.longToConver_(ctFBMatch.getPlayingTime()));
				ctFBMatchNew.setScore(ctFBMatch.getScore());
				ctFBMatchNew.setStatus(ctFBMatch.getStatus());
				ctFBMatchNew.setWinOption(ctFBMatch.getWinOption());
				ctFBMatchs.add(ctFBMatchNew);
			}
			nbet.setCtFBMatchs(ctFBMatchs);
		}
	    return nbet;
	}
	
	
	public BetScheme.Builder getBetSchemeBuilder_(com.xhcms.lottery.commons.data.BetScheme bet){
		BetScheme.Builder bet_=BetSchemeVo.BetScheme.newBuilder();
		bet_.setId(NullValueConver.longToConver(bet.getId()));
		bet_.setLotteryId(NullValueConver.stringToConver(bet.getLotteryId()));
		bet_.setPlayId(NullValueConver.stringToConver(bet.getPlayId()));
		bet_.setPassTypeIds(NullValueConver.stringToConver(bet.getPassTypeIds()));
		bet_.setSponsorId(NullValueConver.longToConver(bet.getSponsorId()));
		bet_.setSponsor(NullValueConver.stringToConver(bet.getSponsor()));
		bet_.setStatus(NullValueConver.intergerToConver(bet.getStatus()));
		bet_.setType(NullValueConver.intergerToConver(bet.getType()));
		bet_.setShowScheme(NullValueConver.intergerToConver(bet.getShowScheme()));
		bet_.setShowSchemeSlogan(NullValueConver.stringToConver(bet.getShowSchemeSlogan()));
		bet_.setIsPublishShow(NullValueConver.intergerToConver(bet.getIsPublishShow()));
		bet_.setCreatedTime(NullValueConver.dateToConver(bet.getCreatedTime()));
		bet_.setOfftime(NullValueConver.dateToConver(bet.getOfftime()));
		bet_.setTotalAmount(NullValueConver.intergerToConver(bet.getTotalAmount()));
		bet_.setShareRatio(NullValueConver.intergerToConver(bet.getShareRatio()));
		bet_.setBuyAmount(NullValueConver.intergerToConver(bet.getBuyAmount()));
		bet_.setFloorAmount(NullValueConver.intergerToConver(bet.getFloorAmount()));
		bet_.setPurchasedAmount(NullValueConver.intergerToConver(bet.getPurchasedAmount()));
		bet_.setBetNote(NullValueConver.intergerToConver(bet.getBetNote()));
		bet_.setMatchNumber(NullValueConver.intergerToConver(bet.getMatchNumber()));
		bet_.setMultiple(NullValueConver.intergerToConver(bet.getMultiple()));
		bet_.setPrivacy(NullValueConver.intergerToConver(bet.getPrivacy()));
		bet_.setFollowSchemePrivacy(NullValueConver.intergerToConver(bet.getFollowSchemePrivacy()));
		bet_.setWinNote(NullValueConver.intergerToConver(bet.getWinNote()));
		bet_.setAwardTime(NullValueConver.dateToConver(bet.getAwardTime()));
		bet_.setMaxBonus(NullValueConver.bigDecimalToConver(bet.getMaxBonus()));
		bet_.setExpectBonus(NullValueConver.bigDecimalToConver(bet.getExpectBonus()));
		bet_.setPreTaxBonus(NullValueConver.bigDecimalToConver(bet.getPreTaxBonus()));
		bet_.setAfterTaxBonus(NullValueConver.bigDecimalToConver(bet.getAfterTaxBonus()));
		bet_.setTicketNote(NullValueConver.intergerToConver(bet.getTicketNote()));
		bet_.setCancelNote(NullValueConver.intergerToConver(bet.getCancelNote()));
		bet_.setTicketCount(NullValueConver.intergerToConver(bet.getTicketCount()));
		bet_.setPartnerCount(NullValueConver.intergerToConver(bet.getPartnerCount()));
		bet_.setSaleStatus(NullValueConver.intergerToConver(bet.getSaleStatus()));
		bet_.setFollowedSchemeId(NullValueConver.longToConver(bet.getFollowedSchemeId()));
		bet_.setFollowingCount(NullValueConver.intergerToConver(bet.getFollowingCount()));
		bet_.setFollowedRatio(NullValueConver.intergerToConver(bet.getFollowedRatio()));
		bet_.setRecommendation(NullValueConver.intergerToConver(bet.getRecommendation()));
		bet_.setSingleAmount(NullValueConver.intergerToConver(bet.getSingleAmount()));
        bet_.setAfterTaxBonusReturnRatio(NullValueConver.intergerToConver(bet.getAfterTaxBonusReturnRatio()));
        bet_.setMaxBonusReturnRatio(NullValueConver.intergerToConver(bet.getMaxBonusReturnRatio()));
        bet_.setIssueNumber(NullValueConver.stringToConver(bet.getIssueNumber()));
        bet_.setFollowTotalAmount(NullValueConver.intergerToConver(bet.getFollowTotalAmount()));
        bet_.setFollowTotalBonus(NullValueConver.bigDecimalToConver(bet.getFollowTotalBonus()));
        bet_.setIsPresetAward(NullValueConver.intergerToConver(bet.getIsPresetAward()));
        bet_.setMatchAnnotation(NullValueConver.stringToConver(bet.getMatchAnnotation()));
        bet_.setPublicTime(NullValueConver.dateToConver(bet.getPublicTime()));
         
        if(bet.getPassTypes()!=null){
        	
        	for(int i=0;i<bet.getPassTypes().size();i++){
    			
    			bet_.setPassTypes(i,bet.getPassTypes().get(i));
    		}
        	
        }
		
		BetMatch.Builder betMatch=BetMatch.newBuilder();
		if(bet.getMatchs()!=null){
			
			for(int i=0;i<bet.getMatchs().size();i++){
				
				 com.xhcms.lottery.commons.data.PlayMatch betmatch=(com.xhcms.lottery.commons.data.PlayMatch)bet.getMatchs().get(i);
				 
				  betMatch.setId(NullValueConver.longToConver(betmatch.getId()))
				 .setSchemeId(NullValueConver.longToConver(betmatch.getSchemeId()))
				 .setMatchId(NullValueConver.longToConver(betmatch.getMatchId()))
				 .setCode(NullValueConver.stringToConver(betmatch.getCode()))
				 .setCnCode(NullValueConver.stringToConver(betmatch.getCnCode()))
				 .setOdds(NullValueConver.stringToConver(betmatch.getOdds()))
				 .setEntrustDeadline(NullValueConver.dateToConver(betmatch.getEntrustDeadline()))
				 .setSeed(NullValueConver.booleanToConver(betmatch.isSeed()))
				 .setDefaultScore(NullValueConver.floatToConver(betmatch.getDefaultScore()))
				 .setPlayId(NullValueConver.stringToConver(betmatch.getPlayId()))
				 .setAnnotation(NullValueConver.stringToConver(betmatch.getAnnotation()))
				 .setName(NullValueConver.stringToConver(betmatch.getName()))
				 .setConcedePoints(NullValueConver.stringToConver(betmatch.getConcedePoints()))
				 .setScore(NullValueConver.stringToConver(betmatch.getScore()))
				 .setScore1(NullValueConver.stringToConver(betmatch.getScore1()))
				 .setScore2(NullValueConver.stringToConver(betmatch.getScore2()))
				 .setScore3(NullValueConver.stringToConver(betmatch.getScore3()))
				 .setScore4(NullValueConver.stringToConver(betmatch.getScore4()))
				 .setResult(NullValueConver.stringToConver(betmatch.getResult()))
				 .setResultOdd(NullValueConver.stringToConver(betmatch.getResultOdd()))
				 .setBetCode(NullValueConver.stringToConver(betmatch.getBetCode()))
				 .setPlayingTime(NullValueConver.dateToConver(betmatch.getPlayingTime()))
				 .setStatus(NullValueConver.intergerToConver(betmatch.getStatus()));
				 bet_.addMatchs(i, betMatch);
				
			}
			
		}
		
		
		
		CGJTeam.Builder  cgj=CGJTeam.newBuilder();
		if(bet.getCgjTeams()!=null){
			
			for(int i=0;i<bet.getCgjTeams().size();i++){
				  
				   com.xhcms.lottery.dc.data.CGJTeam cgj_=bet.getCgjTeams().get(i);
				   cgj.setMatchId(NullValueConver.longToConver(cgj_.getMatchId()))
				   .setCnCode(NullValueConver.stringToConver(cgj_.getCnCode()))
				   .setCode(NullValueConver.stringToConver(cgj_.getCode()))
				   .setLeague(NullValueConver.stringToConver(cgj_.getLeague()))
				   .setName(NullValueConver.stringToConver(cgj_.getName()))
				   .setLongLeagueName(NullValueConver.stringToConver(cgj_.getLongLeagueName()))
				   .setHomeTeam(NullValueConver.stringToConver(cgj_.getHomeTeam()))
				   .setGuestTeam(NullValueConver.stringToConver(cgj_.getGuestTeam()))
				   .setHomeTeamId(NullValueConver.longToConver(cgj_.getHomeTeamId()))
				   .setGuestTeamId(NullValueConver.longToConver(cgj_.getGuestTeamId()))
				   .setOfftime(NullValueConver.dateToConver(cgj_.getOfftime()))
				   .setPlayingTime(NullValueConver.dateToConver(cgj_.getPlayingTime()))
				   .setEntrustDeadline(NullValueConver.dateToConver(cgj_.getEntrustDeadline()))
				   .setConcedePoints(NullValueConver.intergerToConver(cgj_.getConcedePoints()))
				   .setConcedeScore(NullValueConver.floatToConver(cgj_.getConcedeScore()))
				   .setConcedeScorePass(NullValueConver.floatToConver(cgj_.getConcedeScorePass()))
				   .setGuestScore(NullValueConver.floatToConver(cgj_.getGuestScore()))
				   .setGuestScorePass(NullValueConver.floatToConver(cgj_.getGuestScorePass()))
				   .setTeamId(NullValueConver.longToConver(cgj_.getTeamId()))
				   .setTeamName(NullValueConver.stringToConver(cgj_.getTeamName()))
				   .setOdds(NullValueConver.bigDecimalToConver(cgj_.getOdds()))
				   .setGroupName(NullValueConver.stringToConver(cgj_.getGroupName()))
				   .setStatus(NullValueConver.intergerToConver(cgj_.getStatus()))
				   .setTeamLogo(NullValueConver.stringToConver(cgj_.getTeamLogo()))
				   .setRank(NullValueConver.intergerToConver(cgj_.getRank()))
				   .setSeason(NullValueConver.stringToConver(cgj_.getSeason()))
				   .setOdd(NullValueConver.stringToConver(cgj_.getOdd()))
				   .setResult(NullValueConver.stringToConver(cgj_.getResult()));
				   bet_.addCgjTeams(i, cgj.build());
				
			}
			
		}
		
	
		Play.Builder play=Play.newBuilder();
		PassType.Builder passtype=PassType.newBuilder();
		if(bet.getPlay()!=null){
			
			    
				play.setId(NullValueConver.stringToConver(bet.getPlay().getId()))
				.setLotteryId(NullValueConver.stringToConver(bet.getPlay().getLotteryId()))
				.setName(NullValueConver.stringToConver(bet.getPlay().getName()))
				.setFloorRatio(NullValueConver.intergerToConver(bet.getPlay().getFloorRatio()));
				
			for(int i=0;i<bet.getPlay().getPassTypes().size();i++){
				   
				   com.xhcms.lottery.commons.data.PassType pt=bet.getPlay().getPassTypes().get(i);
				   passtype.setId(NullValueConver.stringToConver(pt.getId()))
				   .setName(NullValueConver.stringToConver(pt.getName()))
				   .setNote(NullValueConver.intergerToConver(pt.getNote()));
				   play.addPassTypes(i, passtype.build());
			}
			
		}		
		bet_.setPlay(play.build());
		
		//增加对于方案合买人的缓存
		BetPartner.Builder partnerBuilder = BetPartner.newBuilder();
		if(bet.getGroupbuyPartners() != null) {
			for(int i=0; i<bet.getGroupbuyPartners().size(); i++) {
				com.xhcms.lottery.commons.data.BetPartner partner = bet.getGroupbuyPartners().get(i);
				partnerBuilder.setId(partner.getId());
				partnerBuilder.setSchemeId(partner.getSchemeId());
				partnerBuilder.setUserId(partner.getUserId());
				partnerBuilder.setUsername(partner.getUsername());
				partnerBuilder.setBetAmount(partner.getBetAmount());
				partnerBuilder.setWinAmount(partner.getWinAmount().toString());
				partnerBuilder.setCreatedTime(partner.getCreatedTime().getTime());
				partnerBuilder.setCommission(partner.getCommission().toString());
				bet_.addGroupbuyPartners(i, partnerBuilder.build());
			}
		}
		
		//增加对于数字彩投注内容的缓存
		if(null!=bet.getDigitalBetContents()){
			com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.DigitalBetContent.Builder digitalBetContentOrBuilder=com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.DigitalBetContent.newBuilder();
			for(int i=0; i<bet.getDigitalBetContents().size(); i++){
				DigitalBetContent digitalBetContent=bet.getDigitalBetContents().get(i);
				digitalBetContentOrBuilder.setBonusCode(NullValueConver.stringToConver(digitalBetContent.getBonusCode()));
				digitalBetContentOrBuilder.setChooseType(NullValueConver.intergerToConver(digitalBetContent.getChooseType()));
				digitalBetContentOrBuilder.setCode(NullValueConver.stringToConver(digitalBetContent.getCode()));
				digitalBetContentOrBuilder.setId(NullValueConver.longToConver(digitalBetContent.getId()));
				digitalBetContentOrBuilder.setIssueId(NullValueConver.longToConver(digitalBetContent.getIssueId()));
				digitalBetContentOrBuilder.setIssueNumber(NullValueConver.stringToConver(digitalBetContent.getIssueNumber()));
				digitalBetContentOrBuilder.setLotteryId(NullValueConver.stringToConver(digitalBetContent.getLotteryId()));
				digitalBetContentOrBuilder.setMoney(NullValueConver.intergerToConver(digitalBetContent.getMoney()));
				digitalBetContentOrBuilder.setMultiple(NullValueConver.intergerToConver(digitalBetContent.getMultiple()));
				digitalBetContentOrBuilder.setNote(NullValueConver.intergerToConver(digitalBetContent.getNote()));
				digitalBetContentOrBuilder.setPlayId(NullValueConver.stringToConver(digitalBetContent.getPlayId()));
				digitalBetContentOrBuilder.setSchemeId(NullValueConver.longToConver(digitalBetContent.getSchemeId()));
				bet_.addDigitalBetContents(i, digitalBetContentOrBuilder.build());
			}
		}
		
		//增加对于传统足彩内容的缓存
		if(null!=bet.getCtBetContents()){
			com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.CTBetContent.Builder ctBetContentBuilder = com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.CTBetContent.newBuilder();
			for(int i=0;i<bet.getCtBetContents().size();i++){
				CTBetContent ctBetContent = bet.getCtBetContents().get(i);
				ctBetContentBuilder.setChooseType(NullValueConver.intergerToConver(ctBetContent.getChooseType()));
				ctBetContentBuilder.setCode(NullValueConver.stringToConver(ctBetContent.getCode()));
				ctBetContentBuilder.setId(NullValueConver.longToConver(ctBetContent.getId()));
				ctBetContentBuilder.setIssueId(NullValueConver.longToConver(ctBetContent.getIssueId()));
				ctBetContentBuilder.setIssueNumber(NullValueConver.stringToConver(ctBetContent.getIssueNumber()));
				ctBetContentBuilder.setLotteryId(NullValueConver.stringToConver(ctBetContent.getLotteryId()));
				ctBetContentBuilder.setPlayId(NullValueConver.stringToConver(ctBetContent.getPlayId()));
				ctBetContentBuilder.setSchemeId(NullValueConver.longToConver(ctBetContent.getSchemeId()));
				ctBetContentBuilder.setSeed(NullValueConver.stringToConver(ctBetContent.getSeed()));
				bet_.addCtBetContents(i,ctBetContentBuilder.build());
			}
		
		}
		
		//增加对于传统足彩比赛的缓存
		if(null!=bet.getCtFBMatchs()){
			com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.CTFBMatch.Builder ctFbMatchBuilder=com.xhcms.lottery.commons.data.proto.BetSchemeVo.BetScheme.CTFBMatch.newBuilder();
			for(int i=0;i<bet.getCtFBMatchs().size();i++){
				CTFBMatch ctFbMatch = bet.getCtFBMatchs().get(i);
				ctFbMatchBuilder.setCode(NullValueConver.stringToConver(ctFbMatch.getCode()));
				ctFbMatchBuilder.setColor(NullValueConver.stringToConver(ctFbMatch.getColor()));
				ctFbMatchBuilder.setDefaultScore(NullValueConver.floatToConver(ctFbMatch.getDefaultScore()));
				ctFbMatchBuilder.setEntrustDeadline(NullValueConver.dateToConver(ctFbMatch.getEntrustDeadline()));
				ctFbMatchBuilder.setGuestTeamName(NullValueConver.stringToConver(ctFbMatch.getGuestTeamName()));
				ctFbMatchBuilder.setHalfScore(NullValueConver.stringToConver(ctFbMatch.getHalfScore()));
				ctFbMatchBuilder.setHomeTeamName(NullValueConver.stringToConver(ctFbMatch.getHomeTeamName()));
				ctFbMatchBuilder.setId(NullValueConver.stringToConver(ctFbMatch.getId()));
				ctFbMatchBuilder.setIssueNumber(NullValueConver.stringToConver(ctFbMatch.getIssueNumber()));
				ctFbMatchBuilder.setLeagueName(NullValueConver.stringToConver(ctFbMatch.getLeagueName()));
				ctFbMatchBuilder.setMatchId(NullValueConver.longToConver(ctFbMatch.getMatchId()));
				ctFbMatchBuilder.setName(NullValueConver.stringToConver(ctFbMatch.getName()));
				ctFbMatchBuilder.setOdds(NullValueConver.stringToConver(ctFbMatch.getOdds()));
				ctFbMatchBuilder.setOfftime(NullValueConver.dateToConver(ctFbMatch.getOfftime()));
				ctFbMatchBuilder.setOptions(NullValueConver.stringToConver(ctFbMatch.getOptions()));
				ctFbMatchBuilder.setPlayId(NullValueConver.stringToConver(ctFbMatch.getPlayId()));
				ctFbMatchBuilder.setPlayingTime(NullValueConver.dateToConver(ctFbMatch.getPlayingTime()));
				ctFbMatchBuilder.setScore(NullValueConver.stringToConver(ctFbMatch.getScore()));
				ctFbMatchBuilder.setStatus(NullValueConver.intergerToConver(ctFbMatch.getStatus()));
				ctFbMatchBuilder.setWinOption(NullValueConver.stringToConver(ctFbMatch.getWinOption()));
				bet_.addCtFBMatchs(i, ctFbMatchBuilder.build());
			}
		}
		return bet_;
	}
}