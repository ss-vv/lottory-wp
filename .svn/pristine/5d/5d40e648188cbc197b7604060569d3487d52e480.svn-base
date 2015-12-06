package com.unison.lottery.weibo.statistic.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.impl.BaseServiceImpl;
import com.unison.lottery.weibo.data.Comment;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RealFollower;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.WeiboType;
import com.unison.lottery.weibo.msg.persist.dao.CommentDao;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.statistic.constant.ExcelConstant;
import com.unison.lottery.weibo.statistic.dao.WeiboStatisticsDao;
import com.unison.lottery.weibo.statistic.data.OfficialId;
import com.unison.lottery.weibo.statistic.data.OfficialUserData;
import com.unison.lottery.weibo.statistic.data.WeiboStatisticsData;
import com.unison.lottery.weibo.statistic.excel.ExcelDocument;
import com.unison.lottery.weibo.statistic.service.WeiboStatisticService;
import com.unison.lottery.weibo.uc.persist.UserAccountDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;

@Service
public class WeiboStatisticServiceImpl extends BaseServiceImpl implements WeiboStatisticService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	MessageService messageService;
	@Autowired
	private WeiboStatisticsDao weiboStatisticsDao;
	@Autowired
	CommentDao commentDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	UserDao userDao;
	/** 保存当前微博数据 */
	private PageResult<WeiboMsg> currentWeibosPageResult;
	private PageRequest pageRequest;
	private long from;
	private long to;
	
	private Map<Long,OfficialUserData> officialUserDataMap;
	private Map<Long,Long> normalUserDataMap;
	private int cishu;
	private WeiboStatisticsData weiboStatisticsData;
	
	@Override
	protected BaseDao<?> getBaseDao() {
		return messageDao;
	}
	
	public WeiboStatisticServiceImpl(){
		init();
	}
	
	@Override
	public void statistic() {
		if(null == pageRequest || from == 0 || to == 0){
			init();
		}
		weiboStatisticsData = new WeiboStatisticsData(from);
		officialUserDataMap = new HashMap<Long, OfficialUserData>();
		normalUserDataMap = new HashMap<Long, Long>();
		cishu = 0;
		logger.info("-------------开始统计{}的数据-------------",new Date(from).toString());
		//分页查询第一页信息
		currentWeibosPageResult =listSortedSetDescByScore(Keys.GLOBAL_TIMELINE,
				WeiboMsg.class, pageRequest, from, to);
		pageRequest = currentWeibosPageResult.getPageRequest();
		PageResult<WeiboMsgVO> voResult = fillVOPageResult(currentWeibosPageResult,
				WeiboMsgVO.class);
		dealwithWeiboData(voResult.getResults());
		//循环处理第二页、第三页……
		int totalPages = currentWeibosPageResult.getPageRequest().getPageCount();
		for (int i = 2; i <= totalPages; i++) {
			pageRequest.setPageIndex(i);
			currentWeibosPageResult =listSortedSetDescByScore(Keys.GLOBAL_TIMELINE,
					WeiboMsg.class, pageRequest, from, to);
			pageRequest = currentWeibosPageResult.getPageRequest();
			voResult = fillVOPageResult(currentWeibosPageResult,
					WeiboMsgVO.class);
			dealwithWeiboData(voResult.getResults());
		}
		statisticDailyReport();
		outputToExcel();
		logger.info("----------cishu:" + cishu +"----------");
		logger.info("----------renshu:" + normalUserDataMap.size() +"----------");
		logger.info("统计完成！！！");
	}
	
	private void outputToExcel(){
		ExcelDocument excelDocument = new ExcelDocument();
		Sheet dailySheet = excelDocument.getWritableSheet(ExcelConstant.DAILY_REPORT_INDEX);
		Sheet detailSheet = excelDocument.getWritableSheet(ExcelConstant.OFFICIAL_USER_DATA_DETAIL_INDEX);
		excelDocument.setSheetName(ExcelConstant.OFFICIAL_USER_DATA_DETAIL_INDEX, 
				new SimpleDateFormat("yyyy-MM-dd").format(new Date(from)));
		DecimalFormat df = new DecimalFormat("#.00");
		Row row = dailySheet.getRow(2);
		if(null == row){
			row = dailySheet.createRow(2);
		}
		for (int i = 0; i < 30; i++) {
			row.createCell(i);
		}
		row.getCell(0).setCellValue(weiboStatisticsData.getStatisticDate());
	    row.getCell(1).setCellValue(weiboStatisticsData.getWeiboCount());
	    row.getCell(2).setCellValue(weiboStatisticsData.getOfficialWeiboCount());
	    row.getCell(3).setCellValue(df.format(1.0*weiboStatisticsData.getOfficialWeiboCount()
	    		/weiboStatisticsData.getWeiboCount() * 100) + "%");
	    row.getCell(4).setCellValue(weiboStatisticsData.getCommentCount());
	    row.getCell(5).setCellValue(weiboStatisticsData.getOfficialUserCommentCount());
	    row.getCell(6).setCellValue(df.format(1.0*weiboStatisticsData.getOfficialUserCommentCount()
	    		/weiboStatisticsData.getCommentCount() * 100) + "%");
	    row.getCell(7).setCellValue(weiboStatisticsData.getOfficialUserBeCommentedCount());
	    row.getCell(8).setCellValue(df.format(1.0*weiboStatisticsData.getOfficialUserBeCommentedCount()
	    		/weiboStatisticsData.getCommentCount() * 100) + "%");
	    row.getCell(9).setCellValue(weiboStatisticsData.getNormalUserCommentCount());
	    row.getCell(10).setCellValue(df.format(1.0*weiboStatisticsData.getNormalUserCommentCount()
	    		/weiboStatisticsData.getCommentCount() * 100) + "%");
	    row.getCell(11).setCellValue(weiboStatisticsData.getNormalUserBeCommentedCount());
	    row.getCell(12).setCellValue(df.format(1.0*weiboStatisticsData.getNormalUserBeCommentedCount()
	    		/weiboStatisticsData.getCommentCount() * 100) + "%");
	    row.getCell(13).setCellValue(weiboStatisticsData.getPraiseCount());
		row.getCell(14).setCellValue(weiboStatisticsData.getOfficialUserPraiseCount());
		row.getCell(15).setCellValue(df.format(1.0*weiboStatisticsData.getOfficialUserPraiseCount()
				/weiboStatisticsData.getPraiseCount() * 100) + "%");
		row.getCell(16).setCellValue(weiboStatisticsData.getOfficialUserBePraisedCount());
		row.getCell(17).setCellValue(df.format(1.0*weiboStatisticsData.getOfficialUserBePraisedCount()
				/weiboStatisticsData.getPraiseCount() * 100) + "%");
		row.getCell(18).setCellValue(weiboStatisticsData.getNormalUserPraiseCount());
		row.getCell(19).setCellValue(df.format(1.0*weiboStatisticsData.getNormalUserPraiseCount()
				/weiboStatisticsData.getPraiseCount() * 100) + "%");
		row.getCell(20).setCellValue(weiboStatisticsData.getNormalUserBePraisedCount());
		row.getCell(21).setCellValue(df.format(1.0*weiboStatisticsData.getNormalUserBePraisedCount()
				/weiboStatisticsData.getPraiseCount() * 100) + "%");
		row.getCell(22).setCellValue(weiboStatisticsData.getBetCount());
		row.getCell(23).setCellValue(weiboStatisticsData.getOfficialUserBeFollowCount());
		row.getCell(24).setCellValue(df.format(1.0*weiboStatisticsData.getOfficialUserBeFollowCount()
				/weiboStatisticsData.getBetCount() * 100) + "%");
		row.getCell(25).setCellValue(weiboStatisticsData.getBetMountCount());
		row.getCell(26).setCellValue(weiboStatisticsData.getOfficialUserBeFollowedMountCount());
		row.getCell(27).setCellValue(df.format(1.0*weiboStatisticsData.getOfficialUserBeFollowedMountCount()
				/weiboStatisticsData.getBetMountCount() * 100) + "%");
		int i = 1;
		for (Long uid : officialUserDataMap.keySet()) {
			OfficialUserData officialUserData = officialUserDataMap.get(uid);
			if(null == officialUserData){
				continue;
			}
			logger.info("统计用户：\"{}\"信息",officialUserData.getNickName());
			row = detailSheet.getRow(i);
			if(null == row){
				row = detailSheet.createRow(i);
			}
			for (int j = 0; j < 20; j++) {
				row.createCell(j);
			}
			row.getCell(0).setCellValue(officialUserData.getNickName());
			row.getCell(1).setCellValue(officialUserData.getWeiboCount());
			row.getCell(2).setCellValue(officialUserData.getWeiboDiscussCount());
			row.getCell(3).setCellValue(officialUserData.getWeiboRealCount());
			row.getCell(4).setCellValue(officialUserData.getWeiboRecoCount());
			row.getCell(5).setCellValue(officialUserData.getWeiboFollowCount());
			row.getCell(6).setCellValue(officialUserData.getBeCommentCount());
			row.getCell(7).setCellValue(officialUserData.getWeiboDiscussBeCommentCount());
			row.getCell(8).setCellValue(officialUserData.getWeiboRealBeCommentCount());
			row.getCell(9).setCellValue(officialUserData.getWeiboRecoBeCommentCount());
			row.getCell(10).setCellValue(officialUserData.getBePraiseCount());
			row.getCell(11).setCellValue(officialUserData.getWeiboDiscussBePraiseCount());
			row.getCell(12).setCellValue(officialUserData.getWeiboRealBePraiseCount());
			row.getCell(13).setCellValue(officialUserData.getRealWeiboBeFollowCount());
			row.getCell(14).setCellValue(officialUserData.getCommentNormalUserCount());
			row.getCell(15).setCellValue(officialUserData.getCommentOfficialUserCount());
			row.getCell(16).setCellValue(officialUserData.getPraiseOfficialUserCount());
			row.getCell(17).setCellValue(officialUserData.getPraiseNormalUserCount());
			row.getCell(18).setCellValue(officialUserData.getFollowOfficialUserRealWeiboCount());
			row.getCell(19).setCellValue(officialUserData.getFollowNormalUserRealWeiboCount());
			i++;
		}
		excelDocument.saveWithDate(from);
	}
	
	/**
	 * 统计各个数据汇总
	 */
	private void statisticDailyReport(){
		//微博总数
		weiboStatisticsData.setWeiboCount(currentWeibosPageResult.getPageRequest().getRecordCount());
		for (Long userId : officialUserDataMap.keySet()) {
			OfficialUserData officialUserData = officialUserDataMap.get(userId);
			//运营用户微博总数
			weiboStatisticsData.setOfficialWeiboCount(
					weiboStatisticsData.getOfficialWeiboCount() + officialUserData.getWeiboCount());
			//运营用户评论总数
			weiboStatisticsData.setOfficialUserCommentCount(
					weiboStatisticsData.getOfficialUserCommentCount() 
					+ officialUserData.getCommentNormalUserCount()
					+ officialUserData.getCommentOfficialUserCount());
			//运营用户被评论总数
			weiboStatisticsData.setOfficialUserBeCommentedCount(
					weiboStatisticsData.getOfficialUserBeCommentedCount()
					+ officialUserData.getBeCommentCount());
			//运营用户点赞总数
			weiboStatisticsData.setOfficialUserPraiseCount(
					weiboStatisticsData.getOfficialUserPraiseCount()
					+ officialUserData.getPraiseNormalUserCount()
					+ officialUserData.getPraiseOfficialUserCount());
			//运营用户被点赞总数
			weiboStatisticsData.setOfficialUserBePraisedCount(
					weiboStatisticsData.getOfficialUserBePraisedCount()
					+ officialUserData.getBePraiseCount());
			//运营用户被跟单总数
			weiboStatisticsData.setOfficialUserBeFollowCount(
					weiboStatisticsData.getOfficialUserBeFollowCount()
					+ officialUserData.getRealWeiboBeFollowCount());
			//运营用户被跟单总金额
			weiboStatisticsData.setOfficialUserBeFollowedMountCount(
					weiboStatisticsData.getOfficialUserBeFollowedMountCount()
					+ officialUserData.getRealWeiboBeFollowMountCount());
		}
	}
	
	private void dealwithWeiboData(List<WeiboMsgVO> weibos) {
		for (WeiboMsgVO weiboMsgVO : weibos) {
			Long weiboUserId = weiboMsgVO.getUser().getWeiboUserId();
			String weiboUserNickname = weiboMsgVO.getUser().getNickName();
			logger.info("处理微博消息：id={},发布者：id={},nickname={}",
					new Object[]{weiboMsgVO.getId(),weiboUserId,weiboUserNickname});
			if(null != OfficialId.get(weiboUserId)){//是官方运营帐号发的微博
				OfficialUserData officialUserData = officialUserDataMap.get(weiboUserId);
				if(null == officialUserData){//不存在Map中
					officialUserData = new OfficialUserData(weiboUserId);
					officialUserData.setNickName(weiboUserNickname);
					officialUserDataMap.put(weiboUserId, officialUserData);
				}
				weiboCount(officialUserData,weiboMsgVO);
			} else {//普通用户发的微博
				weiboStatisticsData.setNormalUserBeCommentedCount(
						weiboStatisticsData.getNormalUserBeCommentedCount()
						+ weiboMsgVO.getCommentCount());
				weiboStatisticsData.setNormalUserBePraisedCount(
						weiboStatisticsData.getNormalUserBePraisedCount()
						+ weiboMsgVO.getLikeCount());
			}
			praiseCount(weiboMsgVO);
			commentCount(weiboMsgVO);
			realWeiboFollowedCount(weiboMsgVO);
			//微博评论总数
			weiboStatisticsData.setCommentCount(
					weiboStatisticsData.getCommentCount() + weiboMsgVO.getCommentCount());
			//总赞数
			weiboStatisticsData.setPraiseCount(
					weiboStatisticsData.getPraiseCount() + weiboMsgVO.getLikeCount());
			//投注总数，包括晒单和跟单
			if(WeiboType.SHOW_SCHEME.getType().equals(weiboMsgVO.getType())
					|| WeiboType.FOLLOW.getType().equals(weiboMsgVO.getType())){
				weiboStatisticsData.setBetCount(
						weiboStatisticsData.getBetCount() + 1);
			}
		}
	}
	
	/**
	 * 实单跟单统计
	 * @param weiboMsgVO
	 */
	private void realWeiboFollowedCount(WeiboMsgVO weiboMsgVO) {
		if(WeiboType.SHOW_SCHEME.getType().equals(weiboMsgVO.getType())){
			messageService.loadWeiboSchemeInfo(weiboMsgVO);
			List<RealFollower> realFollowers = weiboMsgVO.getRealFollowers();
			weiboStatisticsData.setBetMountCount(
					weiboStatisticsData.getBetMountCount()
					+ weiboMsgVO.getBetScheme().getBuyAmount());//累加晒单方案金额
			for (RealFollower realFollower : realFollowers) {//迭代每一个跟单用户
				if(null == realFollower.getId()){
					//为空情况说明该用户是大V彩网旧用户；
					continue;
				}
				weiboStatisticsData.setBetMountCount(
						weiboStatisticsData.getBetMountCount()
						+ realFollower.getBuyAmount());//累加跟单金额
				if(null != OfficialId.get(Long.valueOf(realFollower.getWeiboUserId()))){//运营帐号跟单
					OfficialUserData officialUserData = officialUserDataMap.get(realFollower.getWeiboUserId());
					if(null == officialUserData){
						officialUserData = new OfficialUserData(realFollower.getWeiboUserId());
						officialUserData.setNickName(realFollower.getNickName());
						officialUserDataMap.put(Long.valueOf(realFollower.getWeiboUserId()), officialUserData);
					}
					if(null != OfficialId.get(Long.valueOf(weiboMsgVO.getOwnerId()))){//运营帐号发的微博
						officialUserData.setFollowOfficialUserRealWeiboCount(
								officialUserData.getFollowOfficialUserRealWeiboCount() + 1);
					} else {//不是运营帐号发的微博
						officialUserData.setFollowNormalUserRealWeiboCount(
								officialUserData.getFollowNormalUserRealWeiboCount() + 1);
					}
				}
				if(null != OfficialId.get(Long.valueOf(weiboMsgVO.getOwnerId()))){//运营帐号发的微博
					OfficialUserData officialUserData = officialUserDataMap.get(Long.valueOf(weiboMsgVO.getOwnerId()));
					if(null == officialUserData){
						officialUserData = new OfficialUserData(realFollower.getWeiboUserId());
						officialUserData.setNickName(realFollower.getNickName());
						officialUserDataMap.put(Long.valueOf(realFollower.getWeiboUserId()), officialUserData);
					}
					if(null == OfficialId.get(Long.valueOf(realFollower.getWeiboUserId()))){//普通用户跟运营帐号金额（运营帐号实单被普通用户跟单金额）
						cishu ++;
						if(null == normalUserDataMap.get(realFollower.getWeiboUserId())){
							normalUserDataMap.put(realFollower.getWeiboUserId(), realFollower.getWeiboUserId());
						}
						officialUserData.setRealWeiboBeFollowMountCount(
								officialUserData.getRealWeiboBeFollowMountCount()
								+ realFollower.getBuyAmount());
					}
				} 
			}
		}
	}

	/**
	 * 赞统计
	 * @param weiboMsgVO
	 */
	private void praiseCount(WeiboMsgVO weiboMsgVO) {
		List<WeiboUser> praiseUser = messageService.findLikeWeiboUser(""+weiboMsgVO.getId());
		for (WeiboUser weiboUser : praiseUser) {//迭代每一个点赞用户
			if(null != OfficialId.get(Long.valueOf(weiboUser.getId()))){//运营帐号点赞
				OfficialUserData officialUserData = officialUserDataMap.get(weiboUser.getId());
				if(null == officialUserData){
					officialUserData = new OfficialUserData(weiboUser.getId());
					officialUserData.setNickName(weiboUser.getNickName());
					officialUserDataMap.put(Long.valueOf(weiboUser.getId()), officialUserData);
				}
				if(null != OfficialId.get(Long.valueOf(weiboMsgVO.getOwnerId()))){//运营帐号发的微博
					officialUserData.setPraiseOfficialUserCount(
							officialUserData.getPraiseOfficialUserCount() + 1);
				} else {//不是运营帐号发的微博
					officialUserData.setPraiseNormalUserCount(
							officialUserData.getPraiseNormalUserCount() + 1);
				}
			} else { //普通用户点赞
				weiboStatisticsData.setNormalUserPraiseCount(
						weiboStatisticsData.getNormalUserPraiseCount() + 1);
			}
		}
	}
	/**
	 * 评论统计
	 * @param weiboMsgVO
	 */
	private void commentCount(WeiboMsgVO weiboMsgVO) {
		List<String> commentIds = commentDao.getCommentsOfPost(""+weiboMsgVO.getId());
		List<Comment> comments = commentDao.get(commentIds.iterator());
		for (Comment comment : comments) {//迭代每一条评论，统计都是谁评论的
			if(null != OfficialId.get(Long.valueOf(comment.getAuthorId()))){//运营帐号发的评论
				OfficialUserData officialUserData = officialUserDataMap.get(comment.getAuthorId());
				if(null == officialUserData){
					officialUserData = new OfficialUserData(comment.getAuthorId());
					WeiboUser w = userAccountDao.get(comment.getAuthorId());
					if(null != w){
						officialUserData.setNickName(w.getNickName());
					}
					officialUserDataMap.put(Long.valueOf(comment.getAuthorId()), officialUserData);
				}
				if(null != OfficialId.get(Long.valueOf(weiboMsgVO.getOwnerId()))){//运营帐号发的微博
					officialUserData.setCommentOfficialUserCount(
							officialUserData.getCommentOfficialUserCount() + 1);
				} else {//不是运营帐号发的微博
					officialUserData.setCommentNormalUserCount(
							officialUserData.getCommentNormalUserCount() + 1);
				}
			} else { //普通用户发的评论
				weiboStatisticsData.setNormalUserCommentCount(
						weiboStatisticsData.getNormalUserCommentCount() + 1);
			}
		}
	}

	/**
	 * 计算微博数量相关信息
	 * @param officialUserData
	 * @param weiboMsgVO
	 */
	private void weiboCount(OfficialUserData officialUserData,WeiboMsgVO weiboMsgVO){
		String weiboType = weiboMsgVO.getType();
		//微博数量+1
		officialUserData.setWeiboCount(officialUserData.getWeiboCount()+1);
		//微博被评论总数累加
		officialUserData.setBeCommentCount(officialUserData.getBeCommentCount()+weiboMsgVO.getCommentCount());
		//微博被赞总数累加
		officialUserData.setBePraiseCount(officialUserData.getBePraiseCount()+weiboMsgVO.getLikeCount());
		
		if(WeiboType.FOLLOW.getType().equals(weiboType)){
			officialUserData.setWeiboFollowCount(
					officialUserData.getWeiboFollowCount()+1);
		} else if(WeiboType.GROUP.getType().equals(weiboType)){
			
		} else if(WeiboType.RECOMMEND.getType().equals(weiboType)){
			officialUserData.setWeiboRecoCount(officialUserData.getWeiboRecoCount()+1);
			officialUserData.setWeiboRecoBeCommentCount(officialUserData.getWeiboRecoBeCommentCount()
					+ weiboMsgVO.getCommentCount());
			officialUserData.setWeiboRecoBePraiseCount(officialUserData.getWeiboRecoBePraiseCount()
					+ weiboMsgVO.getLikeCount());
		} else if(WeiboType.SHOW_SCHEME.getType().equals(weiboType)){
			officialUserData.setWeiboRealCount(officialUserData.getWeiboRealCount()+1);
			officialUserData.setWeiboRealBeCommentCount(officialUserData.getWeiboRealBeCommentCount()
					+ weiboMsgVO.getCommentCount());
			officialUserData.setWeiboRealBePraiseCount(officialUserData.getWeiboRealBePraiseCount()
					+ weiboMsgVO.getLikeCount());
			messageService.loadWeiboSchemeInfo(weiboMsgVO);
			int i=0;
			for (RealFollower r : weiboMsgVO.getRealFollowers()) {
				if(null != r.getWeiboUserId()){//davcai用户
					if(null == OfficialId.get(Long.valueOf(r.getWeiboUserId()))){//非运营帐号
						i++;
					}
				} else {//大V彩旧用户
					i++;
				}
			}
			officialUserData.setRealWeiboBeFollowCount(officialUserData.getRealWeiboBeFollowCount() + i);
		} else {
			officialUserData.setWeiboDiscussCount(officialUserData.getWeiboDiscussCount()+1);
			officialUserData.setWeiboDiscussBeCommentCount(officialUserData.getWeiboDiscussBeCommentCount()
					+ weiboMsgVO.getCommentCount());
			officialUserData.setWeiboDiscussBePraiseCount(officialUserData.getWeiboDiscussBePraiseCount()
					+ weiboMsgVO.getLikeCount());
		}
	}
	@Override
	@Transactional
	public List<UserPO> findWeixinUidIsNotNull(){
		List<UserPO> list = userDao.findByFieldIsNotNull("weixinUid");
		return list;
	}
	private long getFrom(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE,-1); //前1天
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime().getTime();
	}
	private long getTo(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE,-1); //前1天
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime().getTime();
	}
	
	/**
	 * 初始化请求数据
	 */
	private void init(){
		initPageRequest(1,100);
		from = getFrom();
		to = getTo();
	}
	private void initPageRequest(int page,int size){
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPageIndex(page);
		pageRequest.setPageSize(size);
		this.pageRequest = pageRequest;
	}

	public PageRequest getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public void setTo(long to) {
		this.to = to;
	}
}
