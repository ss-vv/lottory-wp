package com.unison.lottery.weibo.dataservice.crawler.parse.leagueInfo;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.View;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.u;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.l;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.f;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreRuleModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;

/**
 * 解析联赛赛季详细
 * 
 * @author guixiangcui
 *
 */
public class LeagueInfo_Fenxi {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {

		LeagueInfo_Fenxi leagueInfo_Fenxi = new LeagueInfo_Fenxi();
		// jiFen
		// http://apk.win007.com/phone/Jifen2.aspx?sclassid=36&Season=2014-2015&ran=1413170750569
		String jiFen = "$$#FF9966^欧冠杯小组赛资格^歐冠盃小組賽資格^UEFA CL qualifying!#a2e76f^欧冠杯附加^歐冠盃附加^UEFA CL play-offs!#00CCFF^欧罗巴联赛杯资格^歐霸盃資格^UEFA EL qualifying!#B1A7A7^降级球队^降級球隊^Degrade Team$$1^24^切尔西^車路士^8^7^1^0^23^8^22^0^0^^!2^26^曼彻斯特城^曼城^8^5^2^1^18^8^17^0^0^^!3^30^南安普敦^修咸頓^8^5^1^2^19^5^16^0^0^^!4^62^西汉姆联^韋斯咸^8^4^1^3^15^11^13^0^1^^!5^25^利物浦^利物浦^8^4^1^3^13^12^13^0^2^^!6^27^曼彻斯特联^曼聯^8^3^3^2^15^12^12^0^-1^^!7^19^阿森纳^阿仙奴^8^2^5^1^13^11^11^0^-1^^!8^1194^斯旺西^史雲斯^8^3^2^3^11^10^11^0^-1^^!9^33^托特纳姆热刺^熱刺^8^3^2^3^10^11^11^0^-1^^!10^58^斯托克城^史篤城^8^3^2^3^8^9^11^0^-1^^!11^384^赫尔城^侯城^8^2^4^2^13^13^10^0^-1^^!12^20^阿斯顿维拉^阿士東維拉^8^3^1^4^4^12^10^0^-1^^!13^31^埃弗顿^愛華頓^8^2^3^3^16^16^9^0^-1^^!14^18^西布罗姆维奇^西布朗^8^2^3^3^10^11^9^0^-1^^!15^59^莱切斯特城^李斯特城^8^2^3^3^11^13^9^0^-1^^!16^35^水晶宫^水晶宮^8^2^2^4^11^14^8^0^-1^^!17^65^桑德兰^新特蘭^8^1^5^2^8^15^8^0^-1^^!18^28^纽卡斯尔联^紐卡素^8^1^4^3^8^14^7^0^3^^!19^46^伯恩利^般尼^8^0^4^4^4^13^4^0^3^^!20^50^女王公园巡游者^昆士柏流浪^8^1^1^6^6^18^4^0^3^^";
		leagueInfo_Fenxi.analyseJiFen(jiFen, null);

		// saiCheng
		// http://apk.win007.com/phone/SaiCheng2.aspx?sclassid=36&Season=2014-2015&Round=3&ran=1414136577604
		String saiCheng = "38^3$$987119^20140830194500^伯恩利^般尼^曼彻斯特联^曼聯^-1^0^0^0^0^19^13^!987122^20140830220000^曼彻斯特城^曼城^斯托克城^史篤城^-1^0^1^0^0^3^15^!987123^20140830220000^纽卡斯尔联^紐卡素^水晶宫^水晶宮^-1^3^3^1^1^17^18^!987124^20140830220000^女王公园巡游者^昆士柏流浪^桑德兰^新特蘭^-1^1^0^1^0^20^11^!987125^20140830220000^斯旺西^史雲斯^西布罗姆维奇^西布朗^-1^3^0^2^0^4^12^!987127^20140830220000^西汉姆联^韋斯咸^南安普敦^修咸頓^-1^1^3^1^1^8^14^!987120^20140831003000^埃弗顿^愛華頓^切尔西^車路士^-1^3^6^1^2^10^2^!987118^20140831203000^阿斯顿维拉^阿士東維拉^赫尔城^侯城^-1^2^1^2^0^8^7^!987126^20140831203000^托特纳姆热刺^熱刺^利物浦^利物浦^-1^0^3^0^1^3^11^!987121^20140831230000^莱切斯特城^李斯特城^阿森纳^阿仙奴^-1^1^1^1^1^18^6^";
		/**
		 * 历史资料库赛程数据
		 */
		leagueInfo_Fenxi.analyseSaiCheng(saiCheng, null);

		// rangQiu
		// http://apk.win007.com/phone/Panlu.aspx?ID=36&Season=2014-2015&ran=1414137276219
		String rangQiu = "1^24^切尔西^車路士^8^7^0^1^7^0^1^6!2^62^西汉姆联^韋斯咸^8^2^1^5^6^0^2^4!3^30^南安普敦^修咸頓^8^4^0^4^5^1^2^3!4^26^曼彻斯特城^曼城^8^7^1^0^5^1^2^3!5^18^西布罗姆维奇^西布朗^8^2^0^6^5^0^3^2!6^35^水晶宫^水晶宮^8^2^1^5^5^0^3^2!7^65^桑德兰^新特蘭^8^0^3^5^4^2^2^2!8^58^斯托克城^史篤城^8^3^3^2^4^1^3^1!9^59^莱切斯特城^李斯特城^8^1^1^6^4^0^4^0!10^33^托特纳姆热刺^熱刺^8^5^0^3^4^0^4^0!11^384^赫尔城^侯城^8^3^0^5^4^0^4^0!12^1194^斯旺西^史雲斯^8^4^2^2^3^1^4^-1!13^46^伯恩利^般尼^8^0^2^6^3^1^4^-1!14^31^埃弗顿^愛華頓^8^4^1^3^3^1^4^-1!15^20^阿斯顿维拉^阿士東維拉^8^1^1^6^3^1^4^-1!16^28^纽卡斯尔联^紐卡素^8^3^1^4^2^1^5^-3!17^27^曼彻斯特联^曼聯^8^8^0^0^2^0^6^-4!18^50^女王公园巡游者^昆士柏流浪^8^2^1^5^1^3^4^-3!19^19^阿森纳^阿仙奴^8^5^2^1^1^2^5^-4!20^25^利物浦^利物浦^8^7^0^1^1^1^6^-5";
		// leagueInfo_Fenxi.analyseRangQiu(rangQiu);

		// daXiao
		// http://apk.win007.com/phone/Daxiao.aspx?ID=36&Season=2014-2015&ran=1414137700873
		String daXiao = "1^31^埃弗顿^愛華頓^8^6^0^2^75%^0%^25%!2^62^西汉姆联^韋斯咸^8^6^0^2^75%^0%^25%!3^27^曼彻斯特联^曼聯^8^6^0^2^75%^0%^25%!4^25^利物浦^利物浦^8^5^1^2^62.5%^12.5%^25%!5^30^南安普敦^修咸頓^8^5^0^3^62.5%^0%^37.5%!6^35^水晶宫^水晶宮^8^5^0^3^62.5%^0%^37.5%!7^50^女王公园巡游者^昆士柏流浪^8^5^0^3^62.5%^0%^37.5%!8^19^阿森纳^阿仙奴^8^5^0^3^62.5%^0%^37.5%!9^384^赫尔城^侯城^8^5^0^3^62.5%^0%^37.5%!10^1194^斯旺西^史雲斯^8^5^0^3^62.5%^0%^37.5%!11^24^切尔西^車路士^8^5^0^3^62.5%^0%^37.5%!12^18^西布罗姆维奇^西布朗^8^4^1^3^50%^12.5%^37.5%!13^20^阿斯顿维拉^阿士東維拉^8^4^0^4^50%^0%^50%!14^65^桑德兰^新特蘭^8^4^0^4^50%^0%^50%!15^33^托特纳姆热刺^熱刺^8^4^0^4^50%^0%^50%!16^46^伯恩利^般尼^8^4^0^4^50%^0%^50%!17^26^曼彻斯特城^曼城^8^4^0^4^50%^0%^50%!18^28^纽卡斯尔联^紐卡素^8^4^0^4^50%^0%^50%!19^58^斯托克城^史篤城^8^3^0^5^37.5%^0%^62.5%!20^59^莱切斯特城^李斯特城^8^3^0^5^37.5%^0%^62.5%";
		// leagueInfo_Fenxi.analyseDaXiao(daXiao);

		// sheShouBang
		// http://apk.win007.com/phone/Archer.aspx?ID=36&Season=2014-2015&lang=0&ran=1414032977830
		// String sheShouBang =
		// "1^科斯塔^迪亞高哥斯達^24^切尔西^車路士^9^6^3!2^阿奎罗^阿古路^26^曼彻斯特城^曼城^9^5^4!3^佩莱^G.派利^30^南安普敦^修咸頓^6^5^1!4^贝拉希诺^比拉希奴^18^西布罗姆维奇^西布朗^6^5^1!5^乌洛亚^烏路亞^59^莱切斯特城^李斯特城^5^4^1!6^查德利^N.查迪利^33^托特纳姆热刺^熱刺^4^2^2!7^P-西塞^柏比斯施斯^28^纽卡斯尔联^紐卡素^4^2^2!8^耶拉维奇^謝拉域^384^赫尔城^侯城^4^2^2!9^D.萨科^D.沙高^62^西汉姆联^韋斯咸^4^2^2!10^迪亚梅^迪亞美^384^赫尔城^侯城^4^2^2!11^奈史密斯^賴史密夫^31^埃弗顿^愛華頓^4^2^2!12^坎贝尔^F.甘保^35^水晶宫^水晶宮^3^2^1!13^迪马利亚^迪馬利亞^27^曼彻斯特联^曼聯^3^2^1!14^埃里克森^C.艾歷臣^33^托特纳姆热刺^熱刺^3^1^2!15^卢卡库^盧卡古^31^埃弗顿^愛華頓^3^2^1!16^代尔^N.戴亞^1194^斯旺西^史雲斯^3^3^0!17^鲁尼^朗尼^27^曼彻斯特联^曼聯^3^3^0!18^斯特林^史特寧^25^利物浦^利物浦^3^1^2!19^桑切斯^A.山齊士^19^阿森纳^阿仙奴^3^2^1!20^施奈德林^舒尼達連^30^南安普敦^修咸頓^3^1^2";
		// leagueInfo_Fenxi.analyseSheShouBang(sheShouBang);

	}

	int E;
	int F;

	String a;
	String b;
	int c;
	String d;
	List e;
	List f;
	int j;
	int k;
	int l;
	u m;
	// m n;
	l o;
	f p;

	String g[];
	String h[];
	String i[];

	/**
	 * 射手榜
	 * 
	 * @param s1
	 */

	private void analyseSheShouBang(String s1) {
		ArrayList arraylist = new ArrayList();
		String as1[] = s1.split("\\!", -1);
		int i1 = 0;
		while (i1 < as1.length) {
			String as3[] = as1[i1].split("\\^", -1);
			if (as3.length >= 9)
				arraylist.add(new at(false, as3[0], as3[1], as3[2], as3[3],
						as3[4], as3[5], as3[6], as3[7], as3[8]));
			i1++;
		}
		if (arraylist.size() == 0)
			arraylist.add(new at(true));
		System.out.println(arraylist);
	}

	/**
	 * 大小
	 * 
	 * @param s1
	 */
	private void analyseDaXiao(String s1) {
		ArrayList arraylist = new ArrayList();
		String as1[] = s1.split("\\!", -1);
		int i1 = 0;
		while (i1 < as1.length) {
			String as2[] = as1[i1].split("\\^", -1);
			if (as2.length >= 11)
				arraylist
						.add(new ag(false, as2[0], as2[1], as2[2], as2[3],
								as2[4], as2[5], as2[6], as2[7], as2[8], as2[9],
								as2[10]));
			i1++;
		}
		if (arraylist.size() == 0)
			arraylist.add(new ag(true));
		System.out.println(arraylist);
	}

	/**
	 * 让球
	 * 
	 * @param s1
	 */
	private void analyseRangQiu(String s1) {
		ArrayList arraylist = new ArrayList();
		String as1[] = s1.split("\\!", -1);
		int i1 = 0;
		while (i1 < as1.length) {
			String as2[] = as1[i1].split("\\^", -1);
			if (as2.length >= 12)
				arraylist.add(new al(false, as2[0], as2[1], as2[2], as2[3],
						as2[4], as2[5], as2[6], as2[7], as2[8], as2[9],
						as2[10], as2[11]));
			i1++;
		}
		if (arraylist.size() == 0)
			arraylist.add(new al(true));
		System.out.println(arraylist);
	}

	/**
	 * 赛程
	 * 
	 * @param s1
	 * @param seasonModel
	 */
	public List<QtMatchBaseModel> analyseSaiCheng(String s1,
			SeasonModel seasonModel) {
		String as1[] = s1.split("\\$\\$", -1);
		ArrayList<QtMatchBaseModel> arraylist = null;
		if (as1.length >= 2) {
			arraylist = new ArrayList<>();
			String as3[] = as1[1].split("\\!", -1);
			int k1 = 0;
			while (k1 < as3.length) {
				String as4[] = as3[k1].split("\\^", -1);
				if (as4.length >= 14) {
					String s2 = an.a(as4[13], as4[2], as4[3], as4[4], as4[5]);
					QtMatchBaseModel qtMatchBaseModel = new QtMatchBaseModel();
					qtMatchBaseModel.setBsId(as4[0]);
					qtMatchBaseModel.setMatchTime(DateFormateUtil.toDate(
							"yyyyMMddHHmmss", as4[1]));
					qtMatchBaseModel.setHomeTeamId(as4[2]);
					qtMatchBaseModel.setGuestTeamId(as4[4]);
					qtMatchBaseModel.setLeagueId(seasonModel.getLeagueId());
					qtMatchBaseModel.setSeason(seasonModel.getSeasonName());
					qtMatchBaseModel.setSeasonId(seasonModel.getSeasonId());
					qtMatchBaseModel.setMatchStatus(safeInteger(as4[6]));
					qtMatchBaseModel.setHomeTeamScore(safeInteger(as4[7]));
					qtMatchBaseModel.setGuestTeamScore(safeInteger(as4[8]));
					qtMatchBaseModel.setHomeTeamHalfScore(safeInteger(as4[9]));
					qtMatchBaseModel
							.setGuestTeamHalfScore(safeInteger(as4[10]));
					qtMatchBaseModel.setHomeTeamPosition(safeInteger(as4[11]));
					qtMatchBaseModel.setGuestTeamPosition(safeInteger(as4[12]));
					qtMatchBaseModel.setSource(1); // 来源为球探
					qtMatchBaseModel.setProcessStatus(0);
					qtMatchBaseModel.setMatchMessage(s2);
					arraylist.add(qtMatchBaseModel);
				}
				k1++;
			}
		}
		return arraylist;
	}

	private Integer safeInteger(String integer) {
		// TODO Auto-generated method stub
		Integer value = null;
		try {
			value = Integer.valueOf(integer);
		} catch (NumberFormatException exception) {
			logger.debug("", exception);
		}
		return value;
	}

	/**
	 * 解析子联赛,及总轮数
	 * 
	 * @param matchProcessData
	 * @param seasonModel
	 * @return
	 */
	public List<SeasonModel> analyseRoundCount(String matchProcessData,
			SeasonModel seasonModel) {
		String as1[] = matchProcessData.split("\\$\\$", -1);
		List<SeasonModel> seasonModels = null;
		if (as1.length >= 2) {
			seasonModels = new ArrayList<>();
			if (as1[0].length() < 10) {
				String as6[] = as1[0].split("\\^", -1);
				SeasonModel seasonModel2 = new SeasonModel();
				seasonModel2.setTotalRound(an.b(as6[0]));
				seasonModel2.setIsSubLeague(1);
				seasonModel2.setIsHaveSubLeague(1);
				seasonModel2.setSource(1);
				seasonModel2.setSeasonId(seasonModel.getSeasonId());
				seasonModel2.setProcessStatus(0);
				seasonModels.add(seasonModel2);

			} else {
				String as2[] = as1[0].split("\\!", -1);
				int i1 = 0;
				while (i1 < as2.length) {
					String as5[] = as2[i1].split("\\^", -1);
					if (as5.length >= 7) {
						SeasonModel seasonModel1 = new SeasonModel();
						seasonModel1.setSubLeagueId(as5[0]);
						seasonModel1.setSubLeagueName(as5[1]);
						seasonModel1.setSubTranditionName(as5[2]);
						seasonModel1.setTotalRound(an.b(as5[4]));
						seasonModel1.setIsHaveSubLeague(1);
						seasonModel1.setIsSubLeague(0);
						seasonModel1.setSource(1);
						seasonModel.setSeasonId(seasonModel.getSeasonId());
						seasonModel1.setLeagueId(seasonModel.getLeagueId());
						seasonModel1.setSeasonName(seasonModel.getSeasonName());
						seasonModel1.setProcessStatus(0);
						seasonModels.add(seasonModel1);

					}
					i1++;
				}
			}
		}
		return seasonModels;
	}

	/**
	 * 积分排行榜
	 * 
	 * @param s1
	 * @param seasonModel
	 * @return key:value 0:是否做入库操作(1做,2不做),1:规则list,2:积分list
	 */
	public Map<String, Object> analyseJiFen(String s1, SeasonModel seasonModel) {
		String as1[];
		as1 = s1.split("\\$\\$", -1);
		Map<String, Object> jifenRuleMap = null;
		if (s1.length() == 0) {
			jifenRuleMap = new HashMap<>();
		}
		if (as1.length >= 3) {
			jifenRuleMap = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(as1[0])){  //非空表示属于子联赛，如果seasonModel不是子联赛不做入库操作
				if (seasonModel.getIsSubLeague() == 1) {
					jifenRuleMap.put("0", "1");
				}else {
					jifenRuleMap.put("0", "0");
				}
			}else {//空表示不属于子联赛，如果seasonModel是子联赛不做入库操作
				if (seasonModel.getIsSubLeague() == 1) {
					jifenRuleMap.put("0", "0");
				}else {
					jifenRuleMap.put("0", "1");
				}
			}
			if (!as1[1].trim().equals("")) {
				String as5[] = as1[1].split("\\!", -1);
				int j2 = 0;
				List<FbLeagueScoreRuleModel> ruleModels = new ArrayList<>();
				while (j2 < as5.length) {
					String as6[] = as5[j2].split("\\^", -1);
					if (as6.length >= 4) {
						FbLeagueScoreRuleModel ruleModel = new FbLeagueScoreRuleModel();
						ruleModel.setProcessStatus(0);
						ruleModel.setSource(1);
						ruleModel.setSeasonId(seasonModel.getSeasonId());
						ruleModel.setRuleColor(as6[0]);
						ruleModel.setRuleName(as6[1]);
						ruleModel.setRuleNum(j2);
						ruleModels.add(ruleModel);
					}
					j2++;
				}
				jifenRuleMap.put("1", ruleModels);
			}
			String as3[] = as1[2].split("\\!", -1);
			int j1 = 0;
			List<FbLeagueScoreModel> scoreModels = new ArrayList<>();
			while (j1 < as3.length) {
				String as4[] = as3[j1].split("\\^", -1);
				if (as4.length >= 15) {
					FbLeagueScoreModel scoreModel = new FbLeagueScoreModel();
					scoreModel.setRank(safeInteger(as4[0]));
					scoreModel.setTeamName(as4[2]);
					scoreModel.setTeamId(as4[1]);
					scoreModel.setProcessStatus(0);
					scoreModel.setSource(1);
					scoreModel.setTotalMatches(safeInteger(as4[4]));
					scoreModel.setWinMatches(safeInteger(as4[5]));
					scoreModel.setDrawMatches(safeInteger(as4[6]));
					scoreModel.setLoseMatches(safeInteger(as4[7]));
					scoreModel.setGoal(safeInteger(as4[8]));
					scoreModel.setMiss(safeInteger(as4[9]));
					scoreModel.setScore(safeInteger(as4[10]));
					scoreModel.setScoreType(0); // 总积分榜
					scoreModel.setLeagueType(1);
					Integer ruleNum = safeInteger(as4[12]);
					scoreModel.setRuleNum(ruleNum == null ? -1 : ruleNum);
					scoreModel.setSeasonId(seasonModel.getSeasonId());
					scoreModels.add(scoreModel);
				}

				j1++;
			}
			jifenRuleMap.put("2", scoreModels);
		}
		return jifenRuleMap;
	}

	public List<BasketBallLeagueSeasonModel> analyseBasketBallSubLeague(
			String responseStr,
			BasketBallLeagueSeasonModel ballLeagueSeasonModel, int type) {
		String[] as = responseStr.split("\\$\\$", -1);
		List<BasketBallLeagueSeasonModel> ballLeagueSeasonModels = null;
		if (type == 1) {
			if (as.length >= 1) {
				ballLeagueSeasonModels = new ArrayList<>();
				String[] as1 = as[0].split("\\!", -1);
				for (String as2 : as1) {
					String[] as3 = as2.split("\\^", -1);
					if (as3.length >= 2) {
						BasketBallLeagueSeasonModel ballLeagueSeasonModel1 = new BasketBallLeagueSeasonModel();
						ballLeagueSeasonModel1.setKind(as3[0]);
						ballLeagueSeasonModel1
								.setLeagueId(ballLeagueSeasonModel
										.getLeagueId());
						ballLeagueSeasonModel1
								.setSeasonName(ballLeagueSeasonModel
										.getSeasonName());
						ballLeagueSeasonModels.add(ballLeagueSeasonModel1);
					}
				}
			}
		} else {
			// 解析轮数
			if (as.length >= 2) {
				ballLeagueSeasonModels = new ArrayList<>();
				String[] as1 = as[1].split("\\!", -1);
				for (String as2 : as1) {
					String[] as3 = as2.split("\\^", -1);
					if (as3.length >= 4) {
						BasketBallLeagueSeasonModel ballLeagueSeasonModel1 = new BasketBallLeagueSeasonModel();
						ballLeagueSeasonModel1.setSubLeagueId(as3[0]);
						ballLeagueSeasonModel1.setSubLeagueName(as3[1]);
						ballLeagueSeasonModel1.setKind(ballLeagueSeasonModel
								.getKind());
						ballLeagueSeasonModel1
								.setLeagueId(ballLeagueSeasonModel
										.getLeagueId());
						ballLeagueSeasonModel1
								.setSeasonName(ballLeagueSeasonModel
										.getSeasonName());
						ballLeagueSeasonModel1.setSource(1);
						ballLeagueSeasonModel1.setProcessStatus(0);
						ballLeagueSeasonModel1.setIsSubLeague(0);
						ballLeagueSeasonModels.add(ballLeagueSeasonModel1);

					} else if (as3.length >= 2) {
						BasketBallLeagueSeasonModel ballLeagueSeasonModel1 = new BasketBallLeagueSeasonModel();
						ballLeagueSeasonModel1.setSubLeagueId(as3[0]);
						ballLeagueSeasonModel1.setSubLeagueName(as3[0]);
						ballLeagueSeasonModel1.setKind(ballLeagueSeasonModel
								.getKind());
						ballLeagueSeasonModel1
								.setLeagueId(ballLeagueSeasonModel
										.getLeagueId());
						ballLeagueSeasonModel1
								.setSeasonName(ballLeagueSeasonModel
										.getSeasonName());
						ballLeagueSeasonModel1.setSource(1);
						ballLeagueSeasonModel1.setProcessStatus(0);
						ballLeagueSeasonModel1.setIsSubLeague(0);
						ballLeagueSeasonModels.add(ballLeagueSeasonModel1);
					}
				}
			}
		}
		return ballLeagueSeasonModels;
	}

	public List<BasketBallMatchModel> analyseBasketLeagueHistoryMatch(
			String responseStr,
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel) {
		String[] as = responseStr.split("\\$\\$", -1);
		List<BasketBallMatchModel> basketBallMatchModels = null;
		if (as.length >= 3) {
			String[] as1 = as[2].split("\\!", -1);
			basketBallMatchModels = new ArrayList<>();
			for (String as3 : as1) {
				String[] matchArray = as3.split("\\^", -1);
				if (matchArray.length >= 7) {
					BasketBallMatchModel ballMatchModel = new BasketBallMatchModel();
					ballMatchModel.setMatchTime(DateFormateUtil.toDate(
							"yyyyMMddHHmmss", matchArray[0]));
					ballMatchModel.setMatchState(safeInteger(matchArray[1]));
					ballMatchModel.setHomeTeam(matchArray[2]);
					ballMatchModel.setGuestTeam(matchArray[3]);
					ballMatchModel.setHomeScore(notNullSafeIntger(matchArray[4]));
					ballMatchModel
							.setGuestScore(notNullSafeIntger(matchArray[5]));
					String[] bsIds = matchArray[6].split("\\|",-1);  //解析!20110505233000^-1^TTU卡勒夫^塔瓦斯^95^69^99025|卡勒夫^塔尔岩^3^0!格式的数据
					ballMatchModel.setBsId(bsIds[0]);
					ballMatchModel.setSource(1);
					ballMatchModel.setProcessStatus(0);
					ballMatchModel.setLeagueId(basketBallLeagueSeasonModel.getLeagueId());
					ballMatchModel.setSeason(basketBallLeagueSeasonModel.getSeasonName());
					basketBallMatchModels.add(ballMatchModel);
				}
			}
		}
		return basketBallMatchModels;
	}

	private Integer notNullSafeIntger(String value) {
		// TODO Auto-generated method stub
		Integer valueInteger = safeInteger(value);
		return valueInteger == null ? 0 : valueInteger;
	}

}
