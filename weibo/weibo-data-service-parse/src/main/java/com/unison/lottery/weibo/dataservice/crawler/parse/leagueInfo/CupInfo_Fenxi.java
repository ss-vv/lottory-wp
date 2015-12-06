package com.unison.lottery.weibo.dataservice.crawler.parse.leagueInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Constants;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.u;
import com.unison.lottery.weibo.dataservice.crawler.parse.repository.l;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.f;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.unison.lottery.weibo.dataservice.crawler.util.ValueConvertUtil;

/**
 * 解析 杯赛赛季详细
 * 
 * @author guixiangcui
 *
 */

public class CupInfo_Fenxi {

	public static void main(String[] args) {
		// beiSai
		// http://apk.win007.com/phone/CupSaiCheng.aspx?ID=90&Season=2014-2015&ran=1414142628309
		// String beiSai =
		// "6793^第一圈^False!7763^第二圈^False!7764^二圈准决赛^False!7765^二圈季军^False!7766^二圈决赛^False!7767^第三圈^True$$A组积分!1^2363^#ff0000^紐西蘭^New Zealand^新西兰^6^6^0^0^17^2^15^18^!2^6662^^新喀里多尼亞^New Caledonia^新喀里多尼亚^6^4^0^2^17^6^11^12^!3^8030^^大溪地^Tahiti^塔希提群岛^6^1^0^5^2^12^-10^3^!4^2865^^索羅門群島^Solomon Islands^所罗门群岛^6^1^0^5^5^21^-16^3^!$$A^20120907120000^所罗门群岛^索羅門群島^塔希提群岛^大溪地^-1^2^0^1^0^778446^^153^119!A^20120907150000^新喀里多尼亚^新喀里多尼亞^新西兰^紐西蘭^-1^0^2^0^2^778447^^128^95!A^20120911153500^新西兰^紐西蘭^所罗门群岛^索羅門群島^-1^6^1^2^0^778448^^95^153!A^20120912140000^塔希提群岛^大溪地^新喀里多尼亚^新喀里多尼亞^-1^0^4^0^0^778449^^119^128!A^20121012120000^所罗门群岛^索羅門群島^新喀里多尼亚^新喀里多尼亞^-1^2^6^1^2^778451^^141^113!A^20121013140000^塔希提群岛^大溪地^新西兰^紐西蘭^-1^0^2^0^1^778450^^127^92!A^20121016144000^新西兰^紐西蘭^塔希提群岛^大溪地^-1^3^0^1^0^778453^^92^127!A^20121016163000^新喀里多尼亚^新喀里多尼亞^所罗门群岛^索羅門群島^-1^5^0^4^0^778452^^113^141!A^20130322144500^新西兰^紐西蘭^新喀里多尼亚^新喀里多尼亞^-1^2^1^1^0^778455^^88^103!A^20130323140000^塔希提群岛^大溪地^所罗门群岛^索羅門群島^-1^2^0^1^0^778454^^138^147!A^20130326120000^所罗门群岛^索羅門群島^新西兰^紐西蘭^-1^0^2^0^1^778456^^147^88!A^20130326150000^新喀里多尼亚^新喀里多尼亞^塔希提群岛^大溪地^-1^1^0^0^0^778457^^103^138";
		// // beiSai =
		// //
		// "10223^分组赛^False!10768^准决赛^False!10940^决赛^True$$A组积分!1^3173^#ff0000^車路士女足^Chelsea FC (w)^切尔西女足^5^4^0^1^24^5^19^12^!2^2733^#ff0000^阿仙奴女足^Arsenal (w)^阿森纳女足^5^4^0^1^17^2^15^12^!3^9380^^雷丁女足^Reading (w)^雷丁女足^4^2^1^1^12^4^8^7^!4^7363^^米禾爾女足^Millwall (w)^米禾尔女足^5^1^2^2^3^10^-7^5^!5^6205^^屈福特女足^Watford (w)^屈福特女足^4^1^0^3^5^11^-6^3^!6^7368^^班列特女足^Barnet (w)^班列特女足^5^0^1^4^2^31^-29^1^!B组积分!1^7380^#ff0000^曼城女足^Manchester City (w)^曼彻斯特城女足^5^4^0^1^9^3^6^12^!2^6204^#ff0000^利物浦女足^Liverpool (w)^利物浦女足^5^3^1^1^17^5^12^10^!3^3168^^愛華頓女足^Everton FC (w)^埃弗顿女足^5^3^1^1^8^2^6^10^!4^4132^^唐卡士打貝爾女足^Doncaster Rovers Belles (w)^唐卡士打贝尔女足^5^2^0^3^8^10^-2^6^!5^3176^^新特蘭女足^Sunderland (w)^桑德兰女足^5^2^0^3^5^10^-5^6^!6^18710^^達勒姆女足^Durham Wildcats LFC (w)^达勒姆女足^5^0^0^5^2^19^-17^0^!C组积分!1^22855^#ff0000^諾士郡女足^Notts County Women's^诺士郡女足^5^4^1^0^16^1^15^13^!2^5332^#ff0000^布里斯托學院女足^Bristol Academy (w)^布里斯托学院女足^5^3^2^0^17^4^13^11^!3^4131^^伯明翰女足^Birmingham (w)^伯明翰城女足^5^3^0^2^6^8^-2^9^!4^12939^^伊奧華女足^Yeovil Town (w)^伊奥华女足^5^2^1^2^5^6^-1^7^!5^7377^^阿士東維拉女足^Aston Villa (w)^阿斯顿维拉女足^5^1^0^4^2^11^-9^3^!6^18720^^牛津聯女足^Oxford United (w)^牛津联女足^5^0^0^5^2^18^-16^0^!$$A^20140502024500^阿森纳女足^阿仙奴女足^屈福特女足^屈福特女足^-1^3^0^0^0^979926^^6^5!A^20140502024500^雷丁女足^雷丁女足^切尔西女足^車路士女足^-1^1^2^1^0^979927^^^2!A^20140502030500^班列特女足^班列特女足^米禾尔女足^米禾爾女足^-1^0^0^0^0^979925^^6^!A^20140504190000^屈福特女足^屈福特女足^切尔西女足^車路士女足^-1^1^5^1^4^979929^^5^2!A^20140504210000^米禾尔女足^米禾爾女足^雷丁女足^雷丁女足^-1^1^1^1^0^979928^^^!A^20140515023000^班列特女足^班列特女足^屈福特女足^屈福特女足^-1^1^3^0^2^979931^^6^5!A^20140515023000^切尔西女足^車路士女足^米禾尔女足^米禾爾女足^-1^4^0^2^0^979932^^2^!A^20140516024500^雷丁女足^雷丁女足^阿森纳女足^阿仙奴女足^-1^2^0^2^0^979933^^^3!A^20140706210000^班列特女足^班列特女足^雷丁女足^雷丁女足^-1^1^8^1^1^979934^^8^!A^20140706210000^屈福特女足^屈福特女足^米禾尔女足^米禾爾女足^-1^1^2^0^0^979935^^5^!A^20140706210000^阿森纳女足^阿仙奴女足^切尔西女足^車路士女足^-1^3^0^1^0^979936^^4^2!A^20140711210000^阿森纳女足^阿仙奴女足^班列特女足^班列特女足^-1^7^0^3^0^1001700^^4^8!A^20140713010000^雷丁女足^雷丁女足^屈福特女足^屈福特女足^-14^0^0^^^979937^^^5!A^20140713210000^米禾尔女足^米禾爾女足^阿森纳女足^阿仙奴女足^-1^0^4^0^3^979938^^^4!A^20140713210000^切尔西女足^車路士女足^班列特女足^班列特女足^-1^13^0^7^0^979939^^2^8!B^20140501013000^利物浦女足^利物浦女足^埃弗顿女足^愛華頓女足^-1^0^0^0^0^979940^^4^3!B^20140501023000^桑德兰女足^新特蘭女足^达勒姆女足^達勒姆女足^-1^3^0^1^0^979941^^2^!B^20140502024500^唐卡士打贝尔女足^唐卡士打貝爾女足^曼彻斯特城女足^曼城女足^-1^2^1^1^0^979942^^1^!B^20140504013000^利物浦女足^利物浦女足^桑德兰女足^新特蘭女足^-1^6^0^4^0^979943^^4^2!B^20140504190000^曼彻斯特城女足^曼城女足^埃弗顿女足^愛華頓女足^-1^1^0^0^0^979945^^^3!B^20140504210000^达勒姆女足^達勒姆女足^唐卡士打贝尔女足^唐卡士打貝爾女足^-1^1^3^0^1^979944^^^1!B^20140515023000^曼彻斯特城女足^曼城女足^利物浦女足^利物浦女足^-1^2^1^1^1^979946^^^5!B^20140515023000^埃弗顿女足^愛華頓女足^达勒姆女足^達勒姆女足^-1^3^0^1^0^979947^^1^!B^20140516024500^唐卡士打贝尔女足^唐卡士打貝爾女足^桑德兰女足^新特蘭女足^-1^0^2^0^2^979948^^1^2!B^20140706210000^达勒姆女足^達勒姆女足^利物浦女足^利物浦女足^-1^1^7^0^1^979949^^^8!B^20140706210000^埃弗顿女足^愛華頓女足^唐卡士打贝尔女足^唐卡士打貝爾女足^-1^3^1^2^0^979950^^3^2!B^20140706210000^曼彻斯特城女足^曼城女足^桑德兰女足^新特蘭女足^-1^2^0^0^0^979951^^^1!B^20140713013000^利物浦女足^利物浦女足^唐卡士打贝尔女足^唐卡士打貝爾女足^-1^3^2^3^0^979953^^8^2!B^20140713210000^达勒姆女足^達勒姆女足^曼彻斯特城女足^曼城女足^-1^0^3^0^2^979954^^^!B^20140713210000^桑德兰女足^新特蘭女足^埃弗顿女足^愛華頓女足^-1^0^2^0^1^979952^^1^3!C^20140501023000^诺士郡女足^諾士郡女足^阿斯顿维拉女足^阿士東維拉女足^-1^5^0^4^0^979955^^1^8!C^20140502020000^布里斯托学院女足^布里斯托學院女足^牛津联女足^牛津聯女足^-1^9^2^5^1^979956^^5^!C^20140502020000^伊奥华女足^伊奧華女足^伯明翰城女足^伯明翰女足^-1^1^2^0^2^979957^^^7!C^20140504190000^诺士郡女足^諾士郡女足^布里斯托学院女足^布里斯托學院女足^-1^1^1^1^0^979960^^1^5!C^20140504210000^伊奥华女足^伊奧華女足^阿斯顿维拉女足^阿士東維拉女足^-1^2^0^2^0^979959^^^8!C^20140514024500^阿斯顿维拉女足^阿士東維拉女足^伯明翰城女足^伯明翰女足^-1^0^2^0^2^979961^^8^7!C^20140516020000^布里斯托学院女足^布里斯托學院女足^伊奥华女足^伊奧華女足^-1^0^0^0^0^979962^^6^!C^20140516024500^牛津联女足^牛津聯女足^诺士郡女足^諾士郡女足^-1^0^4^0^1^979963^^^4!C^20140703024500^伯明翰城女足^伯明翰女足^牛津联女足^牛津聯女足^-1^2^0^0^0^979958^^1^!C^20140706010000^布里斯托学院女足^布里斯托學院女足^阿斯顿维拉女足^阿士東維拉女足^-1^2^1^0^1^979964^^5^7!C^20140706210000^牛津联女足^牛津聯女足^伊奥华女足^伊奧華女足^-1^0^2^0^1^979965^^^!C^20140706210000^伯明翰城女足^伯明翰女足^诺士郡女足^諾士郡女足^-1^0^2^0^0^979966^^1^6!C^20140712235900^伊奥华女足^伊奧華女足^诺士郡女足^諾士郡女足^-1^0^4^0^1^979967^^^6!C^20140713210000^阿斯顿维拉女足^阿士東維拉女足^牛津联女足^牛津聯女足^-1^1^0^0^0^979968^^7^!C^20140713210000^伯明翰城女足^伯明翰女足^布里斯托学院女足^布里斯托學院女足^-1^0^5^0^3^979969^^1^5";
		// new CupInfo_Fenxi().a(beiSai);
	}

	String a;
	u b;
	List c;
	int d;
	String e;
	String f;
	List g;
	int h[];
	int i;
	int j;
	boolean k;
	// m n;
	l o;
	f p;

	private void a(String s1) {
		// String as[];
		// as = s1.split("\\$\\$", -1);
		// if (as.length >= 3) {
		// if (!as[2].equals("")) {
		// analyseSaiChengSaiGuo(as[2]);
		// }
		//
		// if (!as[1].equals(""))
		// analyseXiaoZuJiFen(as[1]);
		// }
	}

	public List<FbLeagueScoreModel> analyseXiaoZuJiFen(String responseStr,
			SeasonModel seasonModel) {
		String as[];
		as = responseStr.split("\\$\\$", -1);
		List<FbLeagueScoreModel> fbLeagueScoreModels = null;
		if (responseStr.length() == 0) {
			fbLeagueScoreModels = new ArrayList<>();
		}
		if (as.length >= 3) {
			String[] s2 = as[1].split("\\!", -1);

			if (s2 != null && s2.length > 0) {
				fbLeagueScoreModels = new ArrayList<>();
				String groupName = "";
				for (int i = 0; i < s2.length; i++) {
					String[] s3 = s2[i].split("\\^", -1);
					if (s3.length == 1) {
						groupName = s2[i];
					} else if (s3.length >= 14) {
						FbLeagueScoreModel fbLeagueScoreModel = new FbLeagueScoreModel();
						fbLeagueScoreModel.setTeamId(s3[1]);
						fbLeagueScoreModel.setGroup(groupName);
						fbLeagueScoreModel.setTeamColor(s3[2]);
						fbLeagueScoreModel.setRuleNum(ValueConvertUtil
								.safeInteger(s3[0])); // 序号
						fbLeagueScoreModel.setTeamName(s3[5]);
						fbLeagueScoreModel
								.setTotalMatches(notNullIntegerValue(s3[6]));
						fbLeagueScoreModel
								.setWinMatches(notNullIntegerValue(s3[7]));
						fbLeagueScoreModel
								.setDrawMatches(notNullIntegerValue(s3[8]));
						fbLeagueScoreModel
								.setLoseMatches(notNullIntegerValue(s3[9]));
						fbLeagueScoreModel.setGoal(notNullIntegerValue(s3[10]));
						fbLeagueScoreModel.setMiss(notNullIntegerValue(s3[11]));
						fbLeagueScoreModel.setNet(notNullIntegerValue(s3[12]));
						fbLeagueScoreModel
								.setScore(notNullIntegerValue(s3[13]));
						fbLeagueScoreModel.setSeasonId(seasonModel
								.getSeasonId());
						fbLeagueScoreModel.setSeasonName(seasonModel
								.getSeasonName());
						fbLeagueScoreModel.setLeagueId(seasonModel
								.getLeagueId());
						fbLeagueScoreModel.setSource(1);
						fbLeagueScoreModel.setProcessStatus(0);
						fbLeagueScoreModels.add(fbLeagueScoreModel);
					}
				}
			}
		}
		return fbLeagueScoreModels;
	}

	private Integer notNullIntegerValue(String string) {
		// TODO Auto-generated method stub
		Integer value = ValueConvertUtil.safeInteger(string);
		return value == null ? 0 : value;
	}

	/**
	 * 解析杯赛分组信息
	 * 
	 * @param responseStr
	 * @param seasonModel
	 * @return
	 */
	public List<SeasonModel> analyseCupGroup(String responseStr,
			SeasonModel seasonModel) {
		// TODO Auto-generated method stub
		String as[];
		as = responseStr.split("\\$\\$", -1);
		List<SeasonModel> seasonModels = null;
		if (as.length >= 2) {
			String[] s2 = as[0].split("\\!", -1);
			if (s2 != null && s2.length > 0) {
				seasonModels = new ArrayList<>();
				int i1 = 0;
				while (i1 < s2.length) {
					String[] as2 = s2[i1].split("\\^", -1);
					if (as2.length >= 3) {
						SeasonModel seasonModel2 = new SeasonModel();
						seasonModel2.setSource(1);
						seasonModel2.setIsSubLeague(0);
						seasonModel2.setIsHaveSubLeague(1);
						seasonModel2.setSubLeagueId(as2[0]);
						seasonModel2.setSubLeagueName(as2[1]);
						seasonModel2.setIsNow(as2[2]);
						seasonModel2.setProcessStatus(0);
						seasonModel2.setCrawlerCount(0);
						seasonModel2.setTotalRound(1);
						seasonModel2.setLeagueId(seasonModel.getLeagueId());
						seasonModel2.setSeasonName(seasonModel.getSeasonName());
						seasonModel2.setSeasonId(seasonModel.getSeasonId());
						seasonModels.add(seasonModel2);
					}
					i1++;
				}
			}
		}
		return seasonModels;
	}

	public List<QtMatchBaseModel> analyseSaiChengSaiGuo(String s2,
			SeasonModel seasonModel) {
		String as1[];
		int i1;
		as1 = s2.split("\\$\\$", -1);
		List<QtMatchBaseModel> qtMatchBaseModels = null;
		if (as1.length >= 3) {
			qtMatchBaseModels = new ArrayList<>();
			if (!as1[2].equals("")) {
				String[] as = as1[2].split("\\!", -1);
				i1 = 0;
				int j1 = as.length;
				while (i1 < j1) {
					String as2[] = as[i1].split("\\^", -1);
					if (as2.length >= 15) {
						QtMatchBaseModel qtMatchBaseModel = new QtMatchBaseModel();
						qtMatchBaseModel.setGroupId(as2[0]);
						qtMatchBaseModel.setMatchTime(DateFormateUtil.toDate(
								"yyyyMMddHHmmss", as2[1]));
						qtMatchBaseModel.setHomeTeamId(as2[2]);
						qtMatchBaseModel.setGuestTeamId(as2[4]);
						qtMatchBaseModel.setMatchStatus(ValueConvertUtil
								.safeInteger(as2[6]));
						qtMatchBaseModel.setHomeTeamScore(ValueConvertUtil
								.safeInteger(as2[7]));
						qtMatchBaseModel.setGuestTeamScore(ValueConvertUtil
								.safeInteger(as2[8]));
						qtMatchBaseModel.setHomeTeamHalfScore(ValueConvertUtil
								.safeInteger(as2[9]));
						qtMatchBaseModel.setGuestTeamHalfScore(ValueConvertUtil
								.safeInteger(as2[10]));
						qtMatchBaseModel.setBsId(as2[11]);
						qtMatchBaseModel.setHomeTeamPosition(ValueConvertUtil
								.safeInteger(as2[13]));
						qtMatchBaseModel.setGuestTeamPosition(ValueConvertUtil
								.safeInteger(as2[14]));
						qtMatchBaseModel.setSource(seasonModel.getSource());
						qtMatchBaseModel.setLeagueId(seasonModel.getLeagueId());
						qtMatchBaseModel.setSeason(seasonModel.getSeasonName());
						qtMatchBaseModel.setSeasonId(seasonModel.getSeasonId());
						qtMatchBaseModel.setProcessStatus(0);
						qtMatchBaseModels.add(qtMatchBaseModel);
					}
					i1++;
				}
			}
		}

		return qtMatchBaseModels;
	}

	public List<BasketBallLeagueSeasonModel> analyseBasketBallCupGroup(
			String responseStr, BasketBallLeagueSeasonModel seasonModel) {
		String as[];
		as = responseStr.split("\\$\\$", -1);
		List<BasketBallLeagueSeasonModel> seasonModels = null;
		if (as.length >= 2) {
			String[] s2 = as[0].split("\\!", -1);
			if (s2 != null && s2.length > 0) {
				seasonModels = new ArrayList<>();
				int i1 = 0;
				while (i1 < s2.length) {
					String[] as2 = s2[i1].split("\\^", -1);
					if (as2.length >= 3) {
						BasketBallLeagueSeasonModel seasonModel2 = new BasketBallLeagueSeasonModel();
						seasonModel2.setSource(1);
						seasonModel2.setIsSubLeague(0);
						seasonModel2.setSubLeagueId(as2[0]);
						seasonModel2.setSubLeagueName(as2[1]);
						seasonModel2.setIsNow(as2[2]);
						seasonModel2.setProcessStatus(0);
						seasonModel2.setLeagueId(seasonModel.getLeagueId());
						seasonModel2.setSeasonName(seasonModel.getSeasonName());
						seasonModel2.setSeasonId(seasonModel.getSeasonId());
						seasonModels.add(seasonModel2);
					}
					i1++;
				}
			}
		}
		return seasonModels;
	}

	public Map<String, Object> analyseBasketBallHistoryMatch(
			String responseStr,
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel) {
		Map<String, Object> cupScoreAndMatchMap = null;
		String[] as = responseStr.split("\\$\\$", -1);
		if (as.length >= 3) {
			cupScoreAndMatchMap = new HashMap<String, Object>();
			List<BasketBallMatchModel> ballMatchModels = new ArrayList<>();
			List<BasketBallLeagueScoreModel> ballLeagueScoreModels = new ArrayList<>();
			if (StringUtils.isNotBlank(as[1])) {
				String[] as1 = as[1].split("\\!", -1);
				String groupName = "";
				for (String score : as1) {
					String[] as2 = score.split("\\^", -1);
					if (as2.length == 1) {
						groupName = score;
					} else if (as2.length >= 8) {
						BasketBallLeagueScoreModel ballLeagueScoreModel = new BasketBallLeagueScoreModel();
						ballLeagueScoreModel.setGroupName(groupName);
						ballLeagueScoreModel.setTeamName(as2[0]);
						ballLeagueScoreModel
								.setTotalMatches(notNullIntegerValue(as2[1]));
						ballLeagueScoreModel
								.setWinMatches(notNullIntegerValue(as2[2]));
						ballLeagueScoreModel
								.setLoseMatches(notNullIntegerValue(as2[3]));
						ballLeagueScoreModel
								.setWinPercent(notNullPercentDoubleValue(as2[4]));
						ballLeagueScoreModel
								.setAverageGoal(notNullDoubleValue(as2[5]));
						ballLeagueScoreModel
								.setAverageLose(notNullDoubleValue(as2[6]));
						ballLeagueScoreModel
								.setWinContinuous(notNullIntegerValue(as2[7]));
						ballLeagueScoreModel.setSource(1);
						ballLeagueScoreModel.setProcessStatus(0);
						ballLeagueScoreModels.add(ballLeagueScoreModel);
					}

				}
			}
			if (StringUtils.isNotBlank(as[2])) {
				String[] as1 = as[2].split("\\!", -1);
				for (String match : as1) {
					String[] as2 = match.split("\\^", -1);
					int s1 = as2.length;
					if (s1 >= 10) {
						BasketBallMatchModel ballMatchModel = new BasketBallMatchModel();
						ballMatchModel.setMatchSubClass(as2[0]);
						ballMatchModel.setMatchTime(DateFormateUtil.toDate(
								"yyyyMMddHHmmss", as2[1]));
						ballMatchModel.setHomeTeam(as2[2]);
						ballMatchModel.setGuestTeam(as2[3]);
						ballMatchModel
								.setMatchState(notNullIntegerValue(as2[4]));
						ballMatchModel
								.setHomeScore(notNullIntegerValue(as2[5]));
						ballMatchModel
								.setGuestScore(notNullIntegerValue(as2[6]));
						ballMatchModel.setHomeOne(notNullIntegerValue(as2[7]));
						ballMatchModel.setGuestOne(notNullIntegerValue(as2[8]));
						ballMatchModel.setSource(1);
						ballMatchModel.setProcessStatus(0);
						ballMatchModel.setBsId(as2[9]);
						ballMatchModels.add(ballMatchModel);

					}
				}
			}
			cupScoreAndMatchMap.put(Constants.SCORE, ballLeagueScoreModels);
			cupScoreAndMatchMap.put(Constants.MATCH, ballMatchModels);
		}
		return cupScoreAndMatchMap;
	}

	private Double notNullDoubleValue(String string) {
		// TODO Auto-generated method stub
		Double value = ValueConvertUtil.safeDouble(string);
		return value == null ? 0.0 : value;
	}

	// 转换百分数字符串为小数
	private Double notNullPercentDoubleValue(String percentValue) {
		int index = percentValue.indexOf("%");
		String value = "";
		double result = 0.0;
		if (index != -1) {
			value = percentValue.substring(0, index);
		}
		Double double1 = ValueConvertUtil.safeDouble(value);
		if (double1 != null) {
			result = double1.doubleValue() / 100.0;
		}
		return result;
	}

	public List<BasketBallLeagueScoreModel> analyseBasketBallLeagueScore(String responseStr, String leagueId) {
		String as[] = responseStr.split("\\$\\$",-1);
		List<BasketBallLeagueScoreModel> ballLeagueScoreModels = null;
		if(as.length==1&&StringUtils.isNotBlank(responseStr)&&!responseStr.contains("^")){  //防止被过滤
			
		}else {
			ballLeagueScoreModels = new ArrayList<>();
			int i=0;
			while(i<as.length){
				String[] as1 = as[i].split("\\!",-1);
				int j=1;
				Integer scoreType = null;
				if(i==0){
					scoreType = 1;
				}else{
					scoreType = 2;
				}
				for(String leagueScore:as1){
					String[] score = leagueScore.split("\\^",-1);
					if(score.length>=8){
						BasketBallLeagueScoreModel ballLeagueScoreModel = new BasketBallLeagueScoreModel();
						ballLeagueScoreModel.setTeamName(score[0]);
						ballLeagueScoreModel.setWinMatches(notNullIntegerValue(score[1]));
						ballLeagueScoreModel.setLoseMatches(notNullIntegerValue(score[2]));
						ballLeagueScoreModel.setRank(j++);
						ballLeagueScoreModel.setScoreType(i+1);
						ballLeagueScoreModel.setWinPercent(notNullDoubleValue(score[3]));
						ballLeagueScoreModel.setWinNet(notNullDoubleValue(score[4]));
						ballLeagueScoreModel.setPastTen(score[6]);
						ballLeagueScoreModel.setWinContinuous(notNullIntegerValue(score[7]));
						ballLeagueScoreModel.setLeagueRecord(score[5]);
						ballLeagueScoreModel.setScoreType(scoreType);
						ballLeagueScoreModels.add(ballLeagueScoreModel);
					}
				}
				i++;
			}
		}
		
		return ballLeagueScoreModels;
	}

}
