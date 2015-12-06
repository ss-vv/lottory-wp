package com.unison.lottery.weibo.web.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.data.vo.MatchRecommendVo;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.service.MatchRecommendService;
import com.xhcms.lottery.account.service.QTBBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.BetMatchRecDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.LotteryHomeRecommendDao;
import com.xhcms.lottery.commons.persist.entity.BetMatchRecPO;
import com.xhcms.lottery.commons.persist.entity.LtLotteryHomeRecommendPO;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

@Service
public class MatchRecommendServiceImpl implements MatchRecommendService{

	@Autowired
	private LotteryHomeRecommendDao lotteryHomeRecommendDao;
	@Autowired
	private BetMatchRecDao betMatchRecDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private FBMatchDao fbMatchDao;
	@Autowired
    private BBMatchDao bbMatchDao;
	@Autowired
	private BJDCMatchDao bjdcMatchDao;
	@Autowired
	private FBMatchPlayDao fbMatchPlayDao;
	@Autowired
	private BBMatchPlayDao bbMatchPlayDao;
	@Autowired
	private BJDCMatchPlayDao bjdcMatchPlayDao;
	@Autowired
	private QTMatchDao qtMatchDao;
	@Autowired
	private QTBBMatchDao qtBBMatchDao;
	@Autowired
	private UserAccountService userAccountService;
	//@Autowired
	//private UserAccountDao userAccountDao;
	@Override
	@Transactional
	public void getMatchRecommend(List<MatchRecommendVo> jczq,
			List<MatchRecommendVo> jclq, List<MatchRecommendVo> bjdc,boolean flag) {
		List<LtLotteryHomeRecommendPO> jczqRecommend=getRecommend(LotteryId.JCZQ,flag);
		List<LtLotteryHomeRecommendPO> jclqRecommend=getRecommend(LotteryId.JCLQ,flag);
		List<LtLotteryHomeRecommendPO> bjdcRecommend=getRecommend(LotteryId.BJDC,flag);
		int jczqSize=jczqRecommend.size();
		int jclqSize=jclqRecommend.size();
		int bjdcSize=bjdcRecommend.size();
		//计算推荐的比赛展示个数
		if(jclqSize==0){
			if(jczqSize>=2&&bjdcSize>=1){
				jczqSize=2;
				bjdcSize=1;
			}else if(jczqSize>2&&bjdcSize==0){
				jczqSize=3;
			}else if(bjdcSize>=2&&jczqSize>=1){
				bjdcSize=2;
				jczqSize=1;
			}else if(bjdcSize>2&&jczqSize==0){
				bjdcSize=3;
			}
		}else if(jczqSize==0){
           if(bjdcSize>=2&&jclqSize>=1){
        	   bjdcSize=2;
        	   jclqSize=1;
           }else if(bjdcSize>2&&jclqSize==0){
        	   bjdcSize=3;
           }else if(jclqSize>=2&&bjdcSize>=1){
        	   jclqSize=2;
        	   bjdcSize=1;
           }else if(jclqSize>2&&bjdcSize==0){
        	   jclqSize=3;
           }
		}else if(bjdcSize==0){
			if(jczqSize>=2&&jclqSize>=1){
				jczqSize=2;
				jclqSize=1;
			}else if(jczqSize>2&&jclqSize==0){
				jczqSize=3;
			}else if(jclqSize>=2&&jczqSize>=1){
				jclqSize=2;
				jczqSize=1;
			}else if(jclqSize>2&&jczqSize==0){
				jclqSize=3;	
			}
		}else{
			jczqSize=1;
			jclqSize=1;
			bjdcSize=1;
		}
		fileList(jczq,jczqRecommend,jczqSize);
		fileList(jclq,jclqRecommend,jclqSize);
		fileList(bjdc,bjdcRecommend,bjdcSize);
	}
	private List<LtLotteryHomeRecommendPO> getRecommend(LotteryId l,boolean flag){
		return lotteryHomeRecommendDao.getLtLotteryHomeRecommendByLotteryId(l,flag);
	}
	private void fileList(List<MatchRecommendVo> recommend,List<LtLotteryHomeRecommendPO> ltrecommendpo,int count){
		if(ltrecommendpo!=null&&ltrecommendpo.size()>0){
			for(int i=0;i<count;i++){
			MatchRecommendVo mvo=new MatchRecommendVo();
			LtLotteryHomeRecommendPO ltpo=ltrecommendpo.get(i);
			mvo.setId(ltpo.getId());
			mvo.setLotteryId(ltpo.getLotteryId());
			mvo.setWeiboId(ltpo.getWeiboId());
			mvo.setBetMatchId(ltpo.getBetMatchId());
			mvo.setBetMatchRecPO(new BetMatchRecPO());
			fillBetMatchPO(mvo.getBetMatchRecPO(),ltpo.getBetMatchId(),mvo);
			//String weiboKey=getWeiboId(ltpo.get);
			WeiboMsgVO wbms=messageDao.getVO(ltpo.getWeiboId()+"");
			WeiboUser wu=userAccountService.findWeiboUserByWeiboUid(wbms.getOwnerId()+"");
			mvo.setComment(Long.valueOf(wbms.getCommentCount()));
			mvo.setSupport(Long.valueOf(wbms.getShareCount()));
			//userAccountDao.querryWeiboUserById();
			if(wu!=null){
				mvo.setWeiboUserId(wu.getWeiboUserId()+"");
				mvo.setSponsor(wu.getNickName());
			}
			recommend.add(mvo);
		}
		}
	}
	private void fillBetMatchPO(BetMatchRecPO po,Long matchId,MatchRecommendVo mvo){
		List<BetMatchRecPO> recpo=betMatchRecDao.findPOById(matchId);
		if(recpo!=null&&recpo.size()>0){
			BetMatchRecPO betrecpo=recpo.get(0);
			BeanUtils.copyProperties(betrecpo, po);
			fillMatchInfo(betrecpo.getMatchId(),betrecpo.getPlayId(),mvo);
			fillMatchOptionAndOdds(betrecpo.getMatchId(),betrecpo.getPlayId(),mvo);
		}
	}
	private void fillMatchInfo(Long matchId,String playType,MatchRecommendVo mvo){
		String lotteryId=PlayType.getLotteryIdByPlayId(playType);
		 Integer matchCount=0;
		 List<Object[]> match=null;
		if(LotteryId.JCZQ.name().equals(lotteryId)){
			match=fbMatchDao.findFBMatchById(matchId);
			matchCount=fbMatchDao.findMatchCount();
		}else if(LotteryId.JCLQ.name().equals(lotteryId)){
			match=bbMatchDao.findMatchById(matchId);
			matchCount=bbMatchDao.findMatchCount();
		}else if(LotteryId.BJDC.name().equals(lotteryId)){
			match=bjdcMatchDao.findMatchById(matchId);
			matchCount=bjdcMatchDao.findMatchCount();
		}
		mvo.setCountMatch(matchCount);
		fillData(match,mvo,lotteryId);
	}
	private void fillData(List<Object[]> obj,MatchRecommendVo mvo,String lotterytype){
		if(obj!=null&&obj.size()>0){
			Object[] o=obj.get(0);
			if(o[0]!=null){
				mvo.setLeagueName(String.valueOf(o[0]));
			}
			if(o[1]!=null){
				mvo.setPlayingTime(StrToDate(o[1].toString()));
			}
			if(o[2]!=null){
				mvo.setEntrustDeadline(StrToDate(o[2].toString()));
			}
			if(o[3]!=null){
				mvo.setHostTeamName(o[3].toString());
			}
			if(o[4]!=null){
				mvo.setGuestTeamName(o[4].toString());
			}
			if(o[5]!=null){
				mvo.setId(Long.parseLong(o[5].toString()));
			}
			String color="";                    
			if(LotteryId.JCLQ.name().equals(lotterytype)){
				color=qtBBMatchDao.getTeamColor(Long.parseLong(o[5].toString()));
			}else if(LotteryId.BJDC.name().equals(lotterytype)){
				color=qtMatchDao.getTeamColor(Long.parseLong(o[5].toString()));
			}else if(LotteryId.JCZQ.name().equals(lotterytype)){
				color=qtMatchDao.getTeamColor(Long.parseLong(o[5].toString()));
			}
			mvo.setColor(color);
		}
	}
	private void fillMatchOptionAndOdds(Long matchId,String playType,MatchRecommendVo mvo){
		String lotteryId=PlayType.getLotteryIdByPlayId(playType);
		List<Object[]> obj=null;
        if(LotteryId.JCZQ.name().equals(lotteryId)){
        	obj=fbMatchPlayDao.findOptionsOddsById(matchId+playType);
        }else if(LotteryId.JCLQ.name().equals(lotteryId)){
        	obj=bbMatchPlayDao.findOptionsOddsById(matchId+playType);
        }else if(LotteryId.BJDC.name().equals(lotteryId)){
        	obj=bjdcMatchPlayDao.findOptionsOddsById(matchId+playType);
        }
        fillOptionsOdds(obj,mvo);
	}
	private void fillOptionsOdds(List<Object[]> obj,MatchRecommendVo mvo){
		if(obj!=null&&obj.size()>0){
			 Object[] o=obj.get(0);
			 if(o[0]!=null){
				 String options=o[0].toString();
				 mvo.setOptions(options);
				 String tmp[]=options.split(",");
				 if(tmp.length==2){
					mvo.getOption()[0]=Integer.parseInt(tmp[0]);
					mvo.getOption()[2]=Integer.parseInt(tmp[1]);
				 }else if(tmp.length==3){
					mvo.getOption()[0]=Integer.parseInt(tmp[0]);
					mvo.getOption()[1]=Integer.parseInt(tmp[1]);
					mvo.getOption()[2]=Integer.parseInt(tmp[2]);
				 }
			 }
			 if(o[1]!=null){
				 String odds=o[1].toString();
				 mvo.setOdds(odds);
				 String tmp[]=odds.split(",");
				 if(tmp.length==2){
						mvo.getOddsArr()[0]=Double.parseDouble(tmp[0]);
						mvo.getOddsArr()[2]=Double.parseDouble(tmp[1]);
				 }else if(tmp.length==3){
						mvo.getOddsArr()[0]=Double.parseDouble(tmp[0]);
						mvo.getOddsArr()[1]=Double.parseDouble(tmp[1]);
						mvo.getOddsArr()[2]=Double.parseDouble(tmp[2]);
			     }
			 }
		}
	}
	private Date StrToDate(String timestamp){
		Date d=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d=sdf.parse(timestamp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	public LotteryHomeRecommendDao getLotteryHomeRecommendDao() {
		return lotteryHomeRecommendDao;
	}
	public void setLotteryHomeRecommendDao(
			LotteryHomeRecommendDao lotteryHomeRecommendDao) {
		this.lotteryHomeRecommendDao = lotteryHomeRecommendDao;
	}
	public BetMatchRecDao getBetMatchRecDao() {
		return betMatchRecDao;
	}
	public void setBetMatchRecDao(BetMatchRecDao betMatchRecDao) {
		this.betMatchRecDao = betMatchRecDao;
	}
	public MessageDao getMessageDao() {
		return messageDao;
	}
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
	public FBMatchDao getFbMatchDao() {
		return fbMatchDao;
	}
	public void setFbMatchDao(FBMatchDao fbMatchDao) {
		this.fbMatchDao = fbMatchDao;
	}
	public BBMatchDao getBbMatchDao() {
		return bbMatchDao;
	}
	public void setBbMatchDao(BBMatchDao bbMatchDao) {
		this.bbMatchDao = bbMatchDao;
	}
	public BJDCMatchDao getBjdcMatchDao() {
		return bjdcMatchDao;
	}
	public void setBjdcMatchDao(BJDCMatchDao bjdcMatchDao) {
		this.bjdcMatchDao = bjdcMatchDao;
	}
	public FBMatchPlayDao getFbMatchPlayDao() {
		return fbMatchPlayDao;
	}
	public void setFbMatchPlayDao(FBMatchPlayDao fbMatchPlayDao) {
		this.fbMatchPlayDao = fbMatchPlayDao;
	}
	public BBMatchPlayDao getBbMatchPlayDao() {
		return bbMatchPlayDao;
	}
	public void setBbMatchPlayDao(BBMatchPlayDao bbMatchPlayDao) {
		this.bbMatchPlayDao = bbMatchPlayDao;
	}
	public BJDCMatchPlayDao getBjdcMatchPlayDao() {
		return bjdcMatchPlayDao;
	}
	public void setBjdcMatchPlayDao(BJDCMatchPlayDao bjdcMatchPlayDao) {
		this.bjdcMatchPlayDao = bjdcMatchPlayDao;
	}
	public QTMatchDao getQtMatchDao() {
		return qtMatchDao;
	}
	public void setQtMatchDao(QTMatchDao qtMatchDao) {
		this.qtMatchDao = qtMatchDao;
	}
	public QTBBMatchDao getQtBBMatchDao() {
		return qtBBMatchDao;
	}
	public void setQtBBMatchDao(QTBBMatchDao qtBBMatchDao) {
		this.qtBBMatchDao = qtBBMatchDao;
	}

}
