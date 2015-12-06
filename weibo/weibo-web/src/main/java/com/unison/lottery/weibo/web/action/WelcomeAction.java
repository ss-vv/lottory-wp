package com.unison.lottery.weibo.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
 
import com.unison.lottery.weibo.common.service.HomeMatchRecommendService;
import com.unison.lottery.weibo.data.HomeMatchRecommend;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.WeiboUserStatis;
import com.unison.lottery.weibo.data.vo.BetSchemeVo;
import com.unison.lottery.weibo.data.vo.MDBBMatchResultVo;
import com.unison.lottery.weibo.data.vo.MDFBMatchResultVo;
import com.unison.lottery.weibo.data.vo.MatchRecommendVo;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.msg.service.WinningWeiboService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.service.BetNumService;
import com.unison.lottery.weibo.web.service.BetSchemeService;
import com.unison.lottery.weibo.web.service.MatchRecommendService;
import com.unison.lottery.weibo.web.service.MatchResultService;
import com.unison.lottery.weibo.web.service.RecomendUserService;
import com.unison.lottery.weibo.web.service.ShowBetSchemeService;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.entity.AdvertisementPO;
import com.xhcms.lottery.commons.persist.service.AdvertisementService;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.utils.CombineBetMatchUtil;
import com.unison.lottery.weibo.common.service.SchemeService;
/**
 * @Description:控制不同的登录页面
 * @author 江浩翔
 * @date 2013-11-8 上午10:52:42
 * @version V1.0
 */
public class WelcomeAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String errorMessage;
	@Autowired
	private UserAccountService userAccountService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private HomeMatchRecommendService homeMatchRecommednService;
	@Autowired
	private MatchResultService matchResultService;
	@Autowired
	private MatchRecommendService matchRecommendService;
	@Autowired
	private BetSchemeService betSchemeService;
	@Autowired
	private SchemeService schemeService;
	@Autowired
	private WinningWeiboService winningWeiboService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private RecomendUserService recomendUserService;
	@Autowired
	private AdvertisementService adService;
	@Autowired
	private BetNumService betNumService;
	@Autowired
	private ShowBetSchemeService showBetSchemeService;
	private List<HomeMatchRecommend> jczqMatchRecommend;
	private List<HomeMatchRecommend> jclqMatchRecommend;
	private List<HomeMatchRecommend> bjdcMatchRecommend;
	private List<MatchRecommendVo> jczqMatchRecommendVo = new ArrayList<MatchRecommendVo>();
	private List<MatchRecommendVo> jclqMatchRecommendVo = new ArrayList<MatchRecommendVo>();
	private List<MatchRecommendVo> bjdcMatchRecommendVo = new ArrayList<MatchRecommendVo>();
	private List<MDBBMatchResultVo> bb = new ArrayList<MDBBMatchResultVo>();
	private List<MDFBMatchResultVo> fb = new ArrayList<MDFBMatchResultVo>();
	private List<BetSchemeVo> betScheme;
	private PageResult<WeiboMsgVO> pageResult;
	private List<WeiboUserStatis> guoDanLvUsers = new ArrayList<WeiboUserStatis>();
	private AdvertisementPO ad;
	private List<WeiboUserStatis> bonusUsers = new ArrayList<WeiboUserStatis>();
	private String jczqBetNum;
	private String jclqBetNum;
	private String bjdcBetNum;
	private String ctzqBetNum;
	private String ssqBetNum;
	private Date today=new Date();
	private Integer showSchemeNum;

	public String execute() {
		if (true == loadWeiboUser()) {
			return "to_home";
		}
//		boolean isMobile = HttpRequestDeviceUtils
//				.isMobileDevice(ServletActionContext.getRequest());
//		if (isMobile) {
//			return "notMobile";
//			// return "mobile"; 暂无mobile版本
//		} else {
		
		// 获取推荐
		// getRecommendLottery();
		//matchRecommendService.getMatchRecommend(jczqMatchRecommendVo,jclqMatchRecommendVo, bjdcMatchRecommendVo,true);
		// 球探比赛结果
		//getMatchResult();
		// 获取晒单
		//getBetScheme_();
		// 获奖晒单
		//getWinnerNews();
		// 过单率
		//getGuoDanLv();
		//获取各个彩种的投票人数
		//getBetNum();
		//ad = adService.getAd();
		//showSchemeNum=showBetSchemeService.geteShowBetSchemeNum();
		return "notMobile";
	}

	/** 将Session中的WeiboUser对象放入user */
	private boolean loadWeiboUser() {
		Object o = context.getSession().get(Constant.User.USER);
		if (null == o) {
			return false;
		}
		user = (WeiboUser) o;
		return true;
	}

	// 从redis获取三种推荐比赛
	private void getRecommendLottery() {

		initMatchRecommendList(jczqMatchRecommend);
		initMatchRecommendList(jclqMatchRecommend);
		initMatchRecommendList(bjdcMatchRecommend);
		jczqMatchRecommend = homeMatchRecommednService.queryByLottery(
				LotteryId.JCZQ, 3);
		jclqMatchRecommend = homeMatchRecommednService.queryByLottery(
				LotteryId.JCLQ, 3);
		jclqMatchRecommend = homeMatchRecommednService.queryByLottery(
				LotteryId.BJDC, 3);

	}
	private void getBetNum(){
		
		jczqBetNum=betNumService.getJCZQBetNum();
		jclqBetNum=betNumService.getJCLQBetNum();
		bjdcBetNum=betNumService.getBJDCBetNum();
		ctzqBetNum=betNumService.getCTZQBetNum();
		ssqBetNum=betNumService.getSSQBetNum();
	}

	// 实例化推荐list
	private void initMatchRecommendList(List<HomeMatchRecommend> recommend) {

		if (recommend == null) {

			recommend = new ArrayList<HomeMatchRecommend>();
		}

	}

	private void getMatchResult() {

		matchResultService.FillMatchResult(fb, bb);
	}

	private void getBetScheme_() {
		betScheme = betSchemeService.getLastBetScheme();
	}

	// 获奖晒单
	private void getWinnerNews() {
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageIndex(1);
		pageRequest.setPageSize(4);

		try {

			PageResult<WeiboMsgVO> result = winningWeiboService.queryWinWeibo(
					pageRequest, RecentDateType.MONTH);
			result.setUserId(getUserLaicaiWeiboId());

			pageResult = result;
			for (Object o : pageResult.getResults()) {
				WeiboMsgVO weiboMsgVO = (WeiboMsgVO) o;
				Long weiboid = null;
				if (weiboMsgVO.getSourceWeibo() == null) {

					weiboid = weiboMsgVO.getSchameId();
				} else {
					weiboid = weiboMsgVO.getSourceWeibo().getSchameId();
				}
				weiboMsgVO.setLikeUsers(messageService.findLikeWeiboUser(""
						+ weiboMsgVO.getId()));
				// messageService.loadWeiboSchemeInfo(weiboMsgVO);
				BetScheme bs = schemeService.viewSchemeCache(weiboid, null,
						EntityType.DISPLAY_SHOW);
				//将同一赛事不同玩法合并
				bs = CombineBetMatchUtil.combine(bs);
				weiboMsgVO.setBetScheme(bs);
				messageService.loadSourceWeiboSchemeInfo(weiboMsgVO);
			}
		} catch (Exception e) {
			logger.error("查询喜报列表错误：{}", e);
		}

	}

	private void getGuoDanLv() {
		String currentUserId = getUserLaicaiWeiboId() + "";
		guoDanLvUsers = recomendUserService
				.getGuoDanLvTop10WeiboUser(currentUserId);
		bonusUsers = recomendUserService.getBonusTop10WeiboUser(currentUserId);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public HomeMatchRecommendService getHomeMatchRecommednService() {
		return homeMatchRecommednService;
	}

	public void setHomeMatchRecommednService(
			HomeMatchRecommendService homeMatchRecommednService) {
		this.homeMatchRecommednService = homeMatchRecommednService;
	}

	public List<HomeMatchRecommend> getJczqMatchRecommend() {
		return jczqMatchRecommend;
	}

	public void setJczqMatchRecommend(
			List<HomeMatchRecommend> jczqMatchRecommend) {
		this.jczqMatchRecommend = jczqMatchRecommend;
	}

	public List<HomeMatchRecommend> getJclqMatchRecommend() {
		return jclqMatchRecommend;
	}

	public void setJclqMatchRecommend(
			List<HomeMatchRecommend> jclqMatchRecommend) {
		this.jclqMatchRecommend = jclqMatchRecommend;
	}

	public List<HomeMatchRecommend> getBjdcMatchRecommend() {
		return bjdcMatchRecommend;
	}

	public void setBjdcMatchRecommend(
			List<HomeMatchRecommend> bjdcMatchRecommend) {
		this.bjdcMatchRecommend = bjdcMatchRecommend;
	}

	public List<MatchRecommendVo> getJczqMatchRecommendVo() {
		return jczqMatchRecommendVo;
	}

	public void setJczqMatchRecommendVo(
			List<MatchRecommendVo> jczqMatchRecommendVo) {
		this.jczqMatchRecommendVo = jczqMatchRecommendVo;
	}

	public List<MatchRecommendVo> getJclqMatchRecommendVo() {
		return jclqMatchRecommendVo;
	}

	public void setJclqMatchRecommendVo(
			List<MatchRecommendVo> jclqMatchRecommendVo) {
		this.jclqMatchRecommendVo = jclqMatchRecommendVo;
	}

	public List<MatchRecommendVo> getBjdcMatchRecommendVo() {
		return bjdcMatchRecommendVo;
	}

	public void setBjdcMatchRecommendVo(
			List<MatchRecommendVo> bjdcMatchRecommendVo) {
		this.bjdcMatchRecommendVo = bjdcMatchRecommendVo;
	}

	public List<MDBBMatchResultVo> getBb() {
		return bb;
	}

	public void setBb(List<MDBBMatchResultVo> bb) {
		this.bb = bb;
	}

	public List<MDFBMatchResultVo> getFb() {
		return fb;
	}

	public void setFb(List<MDFBMatchResultVo> fb) {
		this.fb = fb;
	}

	public List<BetSchemeVo> getBetScheme() {
		return betScheme;
	}

	public void setBetScheme(List<BetSchemeVo> betScheme) {
		this.betScheme = betScheme;
	}

	public PageResult<WeiboMsgVO> getPageResult() {
		return pageResult;
	}

	public List<WeiboUserStatis> getGuoDanLvUsers() {
		return guoDanLvUsers;
	}

	public void setGuoDanLvUsers(List<WeiboUserStatis> guoDanLvUsers) {
		this.guoDanLvUsers = guoDanLvUsers;
	}

	public List<WeiboUserStatis> getBonusUsers() {
		return bonusUsers;
	}

	public void setBonusUsers(List<WeiboUserStatis> bonusUsers) {
		this.bonusUsers = bonusUsers;
	}

	public AdvertisementService getAdService() {
		return adService;
	}

	public void setAdService(AdvertisementService adService) {
		this.adService = adService;
	}

	public AdvertisementPO getAd() {
		return ad;
	}

	public void setAd(AdvertisementPO ad) {
		this.ad = ad;
	}

	public String getJczqBetNum() {
		return jczqBetNum;
	}

	public void setJczqBetNum(String jczqBetNum) {
		this.jczqBetNum = jczqBetNum;
	}

	public String getJclqBetNum() {
		return jclqBetNum;
	}

	public void setJclqBetNum(String jclqBetNum) {
		this.jclqBetNum = jclqBetNum;
	}

	public String getBjdcBetNum() {
		return bjdcBetNum;
	}

	public void setBjdcBetNum(String bjdcBetNum) {
		this.bjdcBetNum = bjdcBetNum;
	}

	public String getCtzqBetNum() {
		return ctzqBetNum;
	}

	public void setCtzqBetNum(String ctzqBetNum) {
		this.ctzqBetNum = ctzqBetNum;
	}

	public String getSsqBetNum() {
		return ssqBetNum;
	}

	public void setSsqBetNum(String ssqBetNum) {
		this.ssqBetNum = ssqBetNum;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public Integer getShowSchemeNum() {
		return showSchemeNum;
	}

	public void setShowSchemeNum(Integer showSchemeNum) {
		this.showSchemeNum = showSchemeNum;
	}

	
}
