package com.unison.lottery.weibo.data.service.store.persist.service;

/**
 * 查询接口并保存数据
 */
public interface DataQueryStoreService {

	void queryFBLeagueStore();

	void queryFBTeamStore();

	void queryQTMatchIdAndStore();

	/**
	 * 查询、存储足球欧洲赔率（精选的35家博彩公司）
	 */
	void queryAndStoreFBEuroOdds();

	void queryFBBFDataAndStore();

	void queryAndStoreFBOddsHistory();

	void queryAndStoreFBBjEuropeOdds();
	
	/**
	 * 从球探网导入“今日篮球比赛信息”，用接口三。
	 */
	void importBBMatchToday();
	
	/**
	 * 入库篮球历史赛程赛果
	 */
	void importBBMatchHistory();
	
	/**
	 * 入库篮球即时比赛信息
	 */
	void importBBMatchRealtime();
	
	/**
	 * 查询篮球球队信息并存储到库
	 */
	void queryBBTeamStore();

	/**
	 * 查询篮球联赛信息并入库
	 */
	void queryBBLeagueStore();
	
	/**
	 * 查询并入库篮球相关赔率
	 */
//	void queryBBOdds();

	/**
	 * 入库篮球历史赔率
	 */
	void importBBOddsHistory();

	/**
	 * 入库篮球今日赔率
	 */
	void importBBOdds();

	/**
	 * 入库篮球即时赔率
	 */
	void importBBOddsRealtime();

	/**
	 * 导入10天内的篮球百家欧赔
	 */
	void importBBOddsBjEuro();

	/**
	 * 导入3年的篮球百家欧赔。
	 */
	void importBBOddsBjEuroHistory();
	
	/**
	 * 1、查询并存储彩票赛事与球探网的关联
	 * 2、查询5分钟内最新插入的球探网抓取赛事id
	 * 3、抓取球探网足球赛事id对应的赛事数据，并入库
	 */
	public abstract void qtMatchStore();
}
