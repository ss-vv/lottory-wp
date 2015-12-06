package com.xhcms.lottery.commons.persist.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.account.service.QTBBMatchDao;
import com.xhcms.lottery.account.service.QTFBMatchDao;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.persist.service.PlayMatchModifyService;
import com.xhcms.lottery.commons.utils.WinOptionUtils;
import com.xhcms.lottery.utils.ResultTool;

public class PlayMatchModifyServiceImpl implements PlayMatchModifyService {
	
	@Autowired
	private QTFBMatchDao qtfbMatchDao;
	
	@Autowired
	private QTBBMatchDao qtbbMatchDao;

	@Override
	@Transactional(readOnly=true)
	public void modifyFBPlayMatch(FBMatchPO po, FBMatchPlayPO mpo,
			PlayMatch pm) {
		if(null!=po&&null!=mpo&&null!=pm){
			pm.setCnCode(po.getCnCode());
			pm.setCode(po.getCode());
			pm.setName(po.getName());

			pm.setEntrustDeadline(po.getEntrustDeadline());
			pm.setPlayingTime(po.getPlayingTime());
			pm.setResult(makeWinOption(mpo,po));
			pm.setStatus(po.getStatus());
			pm.setLeagueName(po.getLeagueName());
			pm.setScore(po.getScorePreset());//使用编辑人员手工设置的比分值
			pm.setScore1(po.getHalfScorePreset());
			pm.setMatchWin(ResultTool.isMatchWin(pm, mpo.getPlayId()));
			
//			
//			
//			
//			if( EntityStatus.MATCH_OVER != pm.getStatus()){
//	       	 //使用抓取的球探比分
//	           QTMatchPO qtMatchPO =  qtfbMatchDao.findByJingCaiIdAndMatchTime(po.getCnCode(),po.getPlayingTime());
//	           if(null != qtMatchPO){
//	           	if(qtMatchPO.getMatchStatus() == 0 || //未开
//	           			qtMatchPO.getMatchStatus() == -11 || //待定
//	           			qtMatchPO.getMatchStatus() == -12 || //腰斩
//	           			qtMatchPO.getMatchStatus() == -13 || //中断
//	           			qtMatchPO.getMatchStatus() == -14 ){ //推迟 
//	           		pm.setScore("--");
//	           		pm.setScore1("--");
//	           	} else if(qtMatchPO.getMatchStatus() == 1){
//	           		pm.setScore("上" + qtMatchPO.getHalfScore());
//	           	} else if(qtMatchPO.getMatchStatus() == 3){
//	           		pm.setScore("下" + qtMatchPO.getHalfScore());
//	           	} else if(qtMatchPO.getMatchStatus() == -1){ //完场
//	           		pm.setScore(qtMatchPO.getScore());
//	           	} else {
//	           		pm.setScore("--");
//	           		pm.setScore1("--");
//	           	}
//	           }
//	       }
		}
		
		
	}

	private String makeWinOption(FBMatchPlayPO mpo, FBMatchPO po) {
		if(StringUtils.isNotBlank(mpo.getWinOption())){//如果有填好的获胜选项，就采用填好的
			return mpo.getWinOption();
		}
		else{//否则要自己生成获胜选项
			return WinOptionUtils.makeFBWinOptionByMatchResult(po,mpo);
		}
		
	}

	@Override
	@Transactional(readOnly=true)
	public void modifyBBPlayMatch(BBMatchPO po, BBMatchPlayPO mpo, PlayMatch pm) {
		if(null!=po&&null!=mpo&&null!=pm){
			pm.setCnCode(po.getCnCode());
			pm.setCode(po.getCode());
			pm.setName(po.getName());
			pm.setEntrustDeadline(po.getEntrustDeadline());
			pm.setScore1(po.getQuarter1());
			pm.setScore2(po.getQuarter2());
			pm.setScore3(po.getQuarter3());
			pm.setScore4(po.getQuarter4());
			pm.setPlayingTime(po.getPlayingTime());
			pm.setResult(makeWinOption(mpo,po,pm.getConcedePoints()));
			pm.setScore(po.getFinalScorePreset());//使用编辑人员手工填写的比分值
			pm.setStatus(po.getStatus());
			pm.setLeagueName(po.getLeagueName());
			pm.setMatchWin(ResultTool.isMatchWin(pm, mpo.getPlayId()));
//			if( EntityStatus.MATCH_OVER != pm.getStatus()){
//		        //使用抓取的球探比分
//		        BBMatchInfoPO bbMatchInfoPO =  qtbbMatchDao.findByJingCaiIdAndMatchTime(po.getCnCode(),po.getPlayingTime());
//		        if(null != bbMatchInfoPO){
//		        	pm.setScore(bbMatchInfoPO.getGuestScore()+":"+bbMatchInfoPO.getHomeScore());
//		        	pm.setScore1(bbMatchInfoPO.getGuestOne()+":"+bbMatchInfoPO.getHomeOne());
//		        	pm.setScore2(bbMatchInfoPO.getGuestTwo()+":"+bbMatchInfoPO.getHomeTwo());
//		        	pm.setScore3(bbMatchInfoPO.getGuestThree()+":"+bbMatchInfoPO.getHomeThree());
//		        	pm.setScore4(bbMatchInfoPO.getGuestFour()+":"+bbMatchInfoPO.getHomeFour());
//		        }
//	        }
		}
		
	}

	/**
	 * 
	 * @param mpo
	 * @param po
	 * @param defaultScore 投注时的盘口，包括让球数或者预设分值
	 * @return
	 */
	private String makeWinOption(BBMatchPlayPO mpo, BBMatchPO po, String defaultScore) {
		if(StringUtils.isNotBlank(mpo.getWinOption())){//如果有填好的获胜选项，就采用填好的
			return mpo.getWinOption();
		}
		else{//否则要自己生成获胜选项
			return WinOptionUtils.makeBBWinOptionByMatchResult(po,mpo,defaultScore);//需用投注时的盘口计算获胜选项
		}
	}

}
