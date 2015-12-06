package com.unison.lottery.weibo.dataservice.crawler.parse.fenxi_lq;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.Zq_FenXi;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.as;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.e;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.u.c;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.unison.lottery.weibo.dataservice.crawler.util.ValueConvertUtil;

public class Lq_FenXi {

	public static void main(String[] args) {
		Lq_FenXi fenxi = new Lq_FenXi();
		// gaiKuang
		// http://apk.win007.com/phone/LqTeamTechnic.aspx?ID=197132&lang=0&ran=1414043641088
		String gaiKuang = "-1^94^86^26^24^16^28^^18^19^21^28^^4$$38/76(50%)^命中/投篮^33/71(46%)!3/11(27%)^命中/3分球^4/16(25%)!15/20(75%)^命中/罚球^16/22(73%)!39^篮板^33!20^助攻^20!8^抢断^13!5^盖帽^7!20^犯规^22!18^总失误^19!10^快攻^22!32^内线^42!15^最多领先^6";
		// fenxi.analyseGaiKuang(gaiKuang);

		// tongJi
		// http://txt.win007.com/jsData/tech/1/97/197132.js?flesh=1414045316764&ran=1414045316764
		String tongJi = "2014-10-23 10:00^2:05^CitizensBusinessBankArena,Ontario^7174^10^22^32^42^15^6$2701^韦恩-艾灵顿^艾寧頓^Wayne Ellington^0^G^32^4^12^1^3^2^2^0^2^2^2^1^2^1^0^11!727^罗尼-普莱斯^朗尼派斯^Ronnie Price^0^G^23^3^7^2^5^0^0^0^1^1^3^3^2^3^0^8!2909^韦斯利-约翰逊^韋士利·莊遜^Wesley Johnson^0^F^28^4^10^0^2^3^4^0^1^1^1^1^0^1^0^11!47^卡洛斯-布泽尔^保沙^Carlos Boozer^0^F^23^3^8^0^0^2^2^0^4^4^2^2^2^3^0^8!3567^罗伯特-萨克尔^沙斯利^Robert Sacre^0^C^22^4^6^0^0^2^2^3^5^8^4^3^0^2^0^10!2930^艾德-戴维斯^E.戴維斯^Ed Davis^0^ ^25^2^4^0^0^0^0^3^1^4^2^2^0^0^4^4!3052^林书豪^林书豪^Jeremy Shu-How Lin^0^ ^24^5^9^0^0^3^4^0^3^3^5^4^0^6^1^13!4099^朱利叶斯兰德尔^J.兰德尔^Julius Randle^0^ ^24^7^10^0^0^3^4^1^7^8^1^3^1^1^0^17!4142^罗斯科-史密斯^R.史密夫^Roscoe Smith^0^ ^19^3^5^0^0^0^2^3^3^6^0^1^0^0^0^6!4098^乔丹克拉克森^J.克拉克森^Jordan Clarkson^0^ ^15^3^5^0^1^0^0^0^2^2^0^0^1^1^0^6!235^38^76^3^11^15^20^10^29^39^20^20^8^18^5^94!1^50.0^27.3^75.0^0^18$3910^艾伦-克拉布^阿倫卡比^Allen Crabbe^0^G^34^5^9^1^2^0^0^0^6^6^3^1^1^2^1^11!3578^达米恩-利拉德^利拿德^Damian Lillard^0^G^17^3^5^0^1^2^2^0^0^0^2^2^2^5^0^8!3577^托马斯-罗宾逊^湯馬士羅賓遜^Thomas Robinson^0^F^27^4^8^0^1^1^4^0^7^7^0^5^2^1^0^9!478^多雷尔-赖特^D.胡禮^Dorell Wright^0^F^23^1^6^0^3^4^5^0^1^1^2^2^3^1^1^6!211^克里斯-卡曼^卡文^Chris Kaman^0^C^22^5^7^0^0^2^2^0^1^1^3^1^1^0^2^12!3935^CJ.迈考伦^麥高林^C.J. McCollum^0^ ^31^7^13^0^2^3^4^2^0^2^3^2^1^1^0^17!3547^维尔-巴顿^威爾巴頓^Will Barton^0^ ^28^3^8^0^1^4^5^0^6^6^2^1^1^4^1^10!3583^梅耶尔斯-伦纳德^M.李安納特^Meyers Leonard^0^ ^28^5^9^3^4^0^0^0^4^4^2^5^1^1^2^13!3636^维克多-克莱弗^克拉夫^Victor Claver^0^ ^6^0^2^0^1^0^0^2^0^2^0^2^0^2^0^0!1220^乔尔-费兰德^費蘭^Joel Freeland^0^ ^6^0^2^0^0^0^0^2^1^3^0^1^0^1^0^0!3694^迪安特-加莱特^加雷治^Diante Garrett^0^ ^6^0^0^0^0^0^0^0^0^0^1^0^0^1^0^0!3395^达柳斯-莫里斯^達柳斯-莫里斯^Darius Morris^0^ ^6^0^2^0^1^0^0^0^1^1^2^0^1^0^0^0!234^33^71^4^16^16^22^6^27^33^20^22^13^19^7^86!25^46.5^25.0^72.7^0^19";
		// fenxi.analyseTongJi(tongJi);

		// saishiFenxi
		// http://txt.win007.com/phone/basketball/lqanalysis/1/97/cn/197132.htm?ran=1414037047187
		String saiShiFenXi = "T^0^0^0^0.0^0.0^0.0^12^0.0%!H^0^0^0^0.0^0.0^0.0^12^0.0%!A^0^0^0^0.0^0.0^0.0^12^0.0%!N^0^0^0^0.0^0.0^0.0^^0.0%$$T^0^0^0^0.0^0.0^0.0^4^0.0%!H^0^0^0^0.0^0.0^0.0^4^0.0%!A^0^0^0^0.0^0.0^0.0^4^0.0%!N^0^0^0^0.0^0.0^0.0^^0.0%$$20140402103000^NBA^湖人^112-124^开拓者^-8.5^1^25!20140304110000^NBA^开拓者^106-107^湖人^12^25^1!20131202103000^NBA^湖人^108-114^开拓者^-2.5^1^25!20130715063000^夏季联赛^开拓者^63-81^湖人^1.5^25^1!20130411100000^NBA^开拓者^106-113^湖人^-5.5^25^1!20130223113000^NBA^湖人^111-107^开拓者^9^1^25!20121229113000^NBA^湖人^104-87^开拓者^9.5^1^25!20121101103000^NBA^开拓者^116-106^湖人^-3^25^1!20121011100000^NBA^湖人^75-93^开拓者^7^1^25!20120324103000^NBA^湖人^103-96^开拓者^9.5^1^25$$20141022100000^NBA^湖人^108-114^太阳^-4.5^1^26!20141020093000^NBA^湖人^98-91^爵士^-3.5^1^20!20141017100000^NBA^湖人^86-119^爵士^4^1^20!20141013093000^NBA^湖人^75-116^勇士^-4^1^27!20141010103000^NBA^湖人^105-120^勇士^-4.5^1^27!20141007100000^NBA^湖人^98-95^掘金^0^1^23!20140719063000^夏季联赛^湖人^83-77^掘金^-2.5^1^23!20140717103000^夏季联赛^76人^85-63^湖人^3^7^1!20140715100000^夏季联赛^湖人^89-88^勇士^-4.5^1^27!20140714043000^夏季联赛^湖人^73-90^鹈鹕^-3^1^11$$20141022090000^NBA^掘金^75-93^开拓者^-1^23^25!20141018100000^NBA^开拓者^121-74^海法热马^19.5^25^2144!20141013090000^NBA^开拓者^119-114^快船^2.5^25^29!20141010100000^NBA^开拓者^105-109^爵士^6.5^25^20!20141008090000^NBA^爵士^92-73^开拓者^-4.5^20^25!20140719083000^夏季联赛^爵士^75-73^开拓者^-3^20^25!20140718063000^夏季联赛^开拓者^65-88^老鹰^4^25^13!20140716040000^夏季联赛^老鹰^76-91^开拓者^-3.5^13^25!20140714083000^夏季联赛^开拓者^75-67^火箭^2.5^25^21!20140713040000^夏季联赛^开拓者^69-71^尼克斯^-3^25^5$$1^25$$2^0^4^33.3%^2^1^3^33.3%!2^0^4^33.3%^2^1^3^33.3%!0^0^0^0.0%^0^0^0^0.0%!输^赢^输^输^输^赢^大^小^走^小^大^小$$3^0^2^60.0%^3^0^2^60.0%!2^0^1^66.7%^3^0^0^100.0%!1^0^1^50.0%^0^0^2^0.0%!赢^赢^赢^输^输^-^小^大^大^大^小^-$$NBA^20140326103000^湖人^尼克斯^127^96^-6^1^5!NBA^20140322103000^湖人^奇才^107^117^-6^1^8!NBA^20131221113000^湖人^森林狼^104^91^-6^1^19$$NBA^20140326070000^魔术^开拓者^95^85^-6^6^25!NBA^20130220110000^开拓者^太阳^98^102^6^25^26!NBA^20120304110000^开拓者^森林狼^110^122^6^25^19$$20141025100000^NBA^湖人^国王^2^1^24!20141029083000^NBA^湖人^火箭^6^1^21!20141030100000^NBA^太阳^湖人^7^26^1$$20141025103000^NBA^快船^开拓者^2^29^25!20141030100000^NBA^开拓者^雷霆^7^25^28!20141101100000^NBA^国王^开拓者^9^24^25$$2^后卫^ 艾宁顿|24^后卫^ 高比拜仁|5^前锋^ 保沙|27^前锋^ 佐敦希尔|30^前锋^ 兰杜!6^后卫^ 卡克臣|10^后卫^ 拿殊|9^后卫^ 朗尼派斯|1^后卫^ 艾普宁|15^后卫^ J.布朗|21^前锋^ E.戴维斯|3^前锋^ J.泰莱|14^前锋^ R.史密夫|11^前锋^ W.庄逊|4^前锋^ 赖恩基利|50^中锋^ 罗拔沙基!2^后卫^ 马菲斯|0^后卫^ 利拿特|88^前锋^ 巴杜姆|12^前锋^ 艾迪治|42^中锋^ 鲁宾卢比斯!5^后卫^ W.巴顿|17^后卫^ 屈臣|25^后卫^ 比基|00^后卫^ 达里奥斯摩利士|3^后卫^ 麦高林|1^后卫^ D.胡礼|23^后卫^ 阿伦卡比|00^前锋^ 占士辛特兰|18^前锋^ 卡华尔|41^前锋^ 汤马士罗宾逊|19^前锋^ 费兰|00^中锋^ 基文|11^中锋^ M.李安纳特$$球队^球员^位置^原因^日期^备注!洛杉磯湖人^ 历克杨格^后卫^拇指^2014/10/04^!洛杉磯湖人^ 林书豪^后卫^脚部^2014/10/12^!洛杉磯湖人^ X.亨利^后卫^背部^2014/10/12^$$洛杉磯湖人^LLLWL^LLLWL^OUOUO^波特蘭拓荒者^LLWWW^LLWWW^UOOOU^波特蘭拓荒者 ★★★ 洛杉磯湖人 6勝 4負 拓荒者近期整體表現有所回升，而且近2個賽季球隊的整體實力和人員結構都不斷增強，拓荒者今仗中立場值得看高一線。";
		// fenxi.analyseFenXi(saiShiFenXi);

		// ouPei
		// http://apk.win007.com/phone/Lq1x2.aspx?ID=197132&lang=0&ran=1414046758666
		String ouPei = "Bet365^1527688^3.00^1.41^2.50^1.58!Easybets^1527715^2.70^1.50^2.50^1.59!Macauslot^1527742^2.50^1.45^2.50^1.45!Vcbet^1527706^2.50^1.50^2.25^1.571!William Hill^1527727^2.65^1.53^2.50^1.59!Ladbrokes^1527696^2.60^1.52^2.50^1.55";
		// fenxi.analyseOuPei(ouPei,0);

		String index = "2.20^^1.50^20141119135705!2.00^^1.60^20141119135007";
		fenxi.analyseBasketIndex(index, 0, "0");

		// yapei
		// http://apk.win007.com/phone/LqHandicap2.aspx?ID=197132&lang=0&ran=1414048381652
		// daxiao
		// http://apk.win007.com/phone/LqOverUnder.aspx?ID=197132&lang=0&ran=1414048897071
		String yapei = "澳门^388894^0.85^-4.5^0.85^0.90^-4.5^0.80!易胜博^388877^0.92^-4.5^0.92^0.92^-4^0.92!皇冠^388845^0.90^-6^0.90^0.88^-4.5^0.88!bet365^388860^0.90^-5^0.90^0.90^-4^0.90!韦德^388866^0.85^-4^0.85^0.85^-4^0.85";
		String daxiao = "澳门^365255^0.85^198.5^0.85^1.05^198.5^0.65!易胜博^365233^0.92^200^0.92^0.92^196^0.92!皇冠^365206^0.88^201.5^0.88^0.83^196.5^0.83!bet365^365219^0.90^201.5^0.90^0.90^196^0.90!韦德^365238^0.85^201.5^0.85^0.85^196^0.85";
		// fenxi.analyseYaPei_DaXiao(yapei, 1);
		fenxi.analyseYaPei_DaXiao(daxiao, 1);

	}

	String Z;
	int a;
	c aa;
	// com.bet007.mobile.score.activity.fenxi.c ab;

	boolean am;
	// com.bet007.mobile.score.activity.fenxi.a an;
	String b;
	String c;
	String d;
	int e;
	String f;
	String g;
	String h;
	String i;
	String j;

	// public Lq_FenXi()
	// {
	// a = 0x7f070051;
	// Z = com/bet007/mobile/score/activity/fenxi/Lq_FenXi.getName();
	// am = false;
	// }

	// static void a(Lq_FenXi lq_fenxi)
	// {
	// lq_fenxi.k();
	// }

	private void a(boolean flag, List list, boolean flag1, String s1) {
		String s2;
		String as1[];
		int i1;
		if (flag1)
			s2 = c;
		else
			s2 = d;
		list.add(new b(4, s2));
		if (flag)
			list.add(new b(4, true, false, "全場", "贏盤", "走水", "輸盤", "贏盤率", "大球",
					"走水", "小球", "贏盤率"));
		else
			list.add(new b(4, true, false, "全场", "赢盘", "走水", "输盘", "赢盘率", "大球",
					"走水", "小球", "赢盘率"));
		as1 = s1.split("\\!", -1);
		i1 = 0;
		while (i1 < as1.length) {
			String as2[] = as1[i1].split("\\^", -1);
			if (as2.length >= 8) {
				String s3 = "";
				if (i1 == 0)
					s3 = "总";
				else if (i1 == 1)
					s3 = "主";
				else if (i1 == 2)
					s3 = "客";
				else if (i1 == 3)
					s3 = "近六";
				if (i1 == 3) {
					if (as2.length >= 12)

						list.add(new b(4, false, true, s3, (as2[0] + as2[1]
								+ as2[2] + as2[3] + as2[4] + as2[5])
								.replace(
										"赢",
										"<font color=\"" + an.n("red")
												+ "\">赢</font>")
								.replace(
										"输",
										"<font color=\"" + an.n("blue")
												+ "\">输</font>")
								.replace(
										"贏",
										"<font color=\"" + an.n("red")
												+ "\">贏</font>")
								.replace(
										"輸",
										"<font color=\"" + an.n("blue")
												+ "\">輸</font>"), (as2[6]
								+ as2[7] + as2[8] + as2[9] + as2[10] + as2[11])
								.replace(
										"大",
										"<font color=\"" + an.n("red")
												+ "\">大</font>").replace(
										"小",
										"<font color=\"" + an.n("blue")
												+ "\">小</font>"), "", "", "",
								"", "", ""));
				} else {
					list.add(new b(4, false, false, s3, as2[0], as2[1], as2[2],
							as2[3], as2[4], as2[5], as2[6], as2[7]));
				}
			}
			i1++;
		}
	}

	private void a(boolean flag, List list, boolean flag1, String s1, int i1,
			int j1) {
		String s2;
		String as1[];
		int k1;
		if (flag1)
			s2 = c;
		else
			s2 = d;
		list.add(new b(5, s2));
		if (flag)
			list.add(new b(5, true, 0, "賽事", "時間", "主隊", "比分", "客隊", "分差",
					"盤口", "盤路"));
		else
			list.add(new b(5, true, 0, "赛事", "时间", "主队", "比分", "客队", "分差",
					"盘口", "盘路"));
		as1 = s1.split("\\!", -1);
		k1 = 0;
		while (k1 < as1.length) {

			String as2[] = as1[k1].split("\\^", -1);
			if (as2.length >= 9) {
				int l1 = an.b(as2[4]);
				int i2 = an.b(as2[5]);
				float f1 = an.c(as2[6]);
				int j2 = an.b(as2[7]);
				int k2;
				int l2;
				String s3;
				String s4;
				boolean flag2;
				boolean flag3;
				StringBuilder stringbuilder;
				String s5;
				String s6;
				StringBuilder stringbuilder1;
				String s7;
				String s8;
				if (flag1)
					k2 = i1;
				else
					k2 = j1;
				if (j2 == k2) {
					if (l1 > i2)
						l2 = 1;
					else
						l2 = 2;
				} else if (l1 < i2)
					l2 = 3;
				else
					l2 = 4;
				s3 = (new StringBuilder()).append(l1).append("-").append(i2)
						.toString();
				s4 = (new StringBuilder()).append(l1 - i2).append("")
						.toString();
				if (flag1) {
					if (an.b(as2[7]) == i1)
						flag2 = true;
					else
						flag2 = false;
				} else if (an.b(as2[7]) == j1)
					flag2 = true;
				else
					flag2 = false;
				if ((float) l1 - f1 > (float) i2)
					flag3 = true;
				else
					flag3 = false;
				stringbuilder = (new StringBuilder()).append("<font color=\"")
						.append(an.n("red")).append("\">");
				if (flag)
					s5 = "贏";
				else
					s5 = "赢";
				s6 = stringbuilder.append(s5).append("</font>").toString();
				stringbuilder1 = (new StringBuilder()).append("<font color=\"")
						.append(an.n("blue")).append("\">");
				if (flag)
					s7 = "輸";
				else
					s7 = "输";
				s8 = stringbuilder1.append(s7).append("</font>").toString();
				if ((!flag2 || !flag3) && (flag2 || flag3))
					s6 = s8;
				list.add(new b(5, false, l2, as2[0], an.a(as2[1], "yy/MM/dd"),
						as2[2], s3, as2[3], s4, (new StringBuilder())
								.append(f1).append("").toString(), s6));
			}

			k1++;
		}
	}

	private boolean a(String s1, int i1, int j1, int k1) {
		boolean bool = true;
		String[] arrayOfString = s1.split("\\^", -1);
		if (arrayOfString.length >= i1) {

			while (j1 <= k1) {
				if (arrayOfString[j1].equals("")) {
					bool = false;
					return bool;
				}
				j1++;
			}
		}
		return bool;
	}

	// static void b(Lq_FenXi lq_fenxi)
	// {
	// lq_fenxi.w();
	// }

	private void b(boolean flag, List list, boolean flag1, String s1) {
		String as1[];
		String s2;
		if (flag1)
			s2 = c;
		else
			s2 = d;
		list.add(new b(7, s2));
		if (flag)
			list.add(new b(7, true, "", "", "正選", "", "", "後備"));
		else
			list.add(new b(7, true, "", "", "正选", "", "", "后备"));
		as1 = s1.split("\\!", -1);
		if (as1.length < 4) {
			// goto _L1
			// _L1:
			return;
		} else {
			// goto _L2;
			// _L2:
			String as2[];
			String as3[];
			String s3;
			String s4;
			int j1;
			if (flag1)
				s3 = as1[0];
			else
				s3 = as1[2];
			as2 = s3.split("\\|", -1);
			if (flag1)
				s4 = as1[1];
			else
				s4 = as1[3];
			as3 = s4.split("\\|", -1);
			if (as2.length > as3.length) {
				j1 = 0;
				while (j1 < as2.length) {
					String as7[] = as2[j1].split("\\^", -1);
					if (as7.length < 3) {
						if (as3.length > j1) {
							String as9[] = as3[j1].split("\\^", -1);
							if (as9.length >= 3)
								list.add(new b(7, false, "", "", "", as9[0],
										as9[1], as9[2]));
						}
					} else if (as3.length > j1) {
						String as8[] = as3[j1].split("\\^", -1);
						if (as8.length < 3)
							list.add(new b(7, false, as7[0], as7[1], as7[2],
									"", "", ""));
						else
							list.add(new b(7, false, as7[0], as7[1], as7[2],
									as8[0], as8[1], as8[2]));
					} else {
						list.add(new b(7, false, as7[0], as7[1], as7[2], "",
								"", ""));
					}
					j1++;
				}
			} else {

				int i1 = 0;
				while (i1 < as3.length) {
					String as4[] = as3[i1].split("\\^", -1);
					if (as4.length < 3) {
						if (as2.length > i1) {
							String as6[] = as2[i1].split("\\^", -1);
							if (as6.length >= 3)
								list.add(new b(7, false, as6[0], as6[1],
										as6[2], "", "", ""));
						}
					} else if (as2.length > i1) {
						String as5[] = as2[i1].split("\\^", -1);
						if (as5.length < 3)
							list.add(new b(7, false, "", "", "", as4[0],
									as4[1], as4[2]));
						else
							list.add(new b(7, false, as5[0], as5[1], as5[2],
									as4[0], as4[1], as4[2]));
					} else {
						list.add(new b(7, false, "", "", "", as4[0], as4[1],
								as4[2]));
					}
					i1++;
				}
			}
		}
	}

	private void b(boolean flag, List list, boolean flag1, String s1, int i1,
			int j1) {
		String s2;
		String as1[];
		int k1;
		if (flag1)
			s2 = c;
		else
			s2 = d;
		list.add(new b(6, s2));
		if (flag)
			list.add(new b(6, true, 0, "日期", "賽事", "主隊", "客隊", "相隔"));
		else
			list.add(new b(6, true, 0, "日期", "赛事", "主队", "客队", "相隔"));
		as1 = s1.split("\\!", -1);
		k1 = 0;
		while (k1 < as1.length) {
			String as2[] = as1[k1].split("\\^", -1);
			if (as2.length >= 7) {
				int l1 = an.b(as2[5]);
				int i2;
				int j2;
				if (flag1)
					i2 = i1;
				else
					i2 = j1;
				if (l1 == i2)
					j2 = 1;
				else
					j2 = 2;
				list.add(new b(6, false, j2, as2[0], as2[1], as2[2], as2[3],
						as2[4]));
			}
			k1++;
		}
	}

	// static void c(Lq_FenXi lq_fenxi)
	// {
	// lq_fenxi.x();
	// }

	/**
	 * 概况
	 * 
	 * @param s1
	 * @param customBsid
	 * @return
	 */
	public List<QtBasketMatchTeamStatisticModel> analyseGaiKuang(
			String response, long customBsid) {
		ArrayList arraylist = new ArrayList<>();
		String as1[] = response.split("\\$\\$", -1);

		if (as1.length < 2) {
			if (as1.length == 1 && response.length() > 0
					&& !response.contains("^")) { // 防止被过滤掉
				arraylist = null;
			}

		} else {
			String as2[] = as1[0].split("\\^", -1);
			if (as2.length < 14) {

			} else {
				int i1;
				String as3[] = as1[1].split("\\!", -1);
				i1 = 0;
				while (i1 < as3.length) {
					String as4[] = as3[i1].split("\\^", -1);
					if (as4.length >= 3) {
						QtBasketMatchTeamStatisticModel basketMatchTeamStatisticModel = new QtBasketMatchTeamStatisticModel();
						basketMatchTeamStatisticModel.setBsId((int) customBsid);
						basketMatchTeamStatisticModel.setProcessStatus(0);
						basketMatchTeamStatisticModel.setSource(1);
						basketMatchTeamStatisticModel.setZd(as4[0]);
						basketMatchTeamStatisticModel.setEventType(as4[1]);
						basketMatchTeamStatisticModel.setKd(as4[2]);

						arraylist.add(basketMatchTeamStatisticModel);
					}
					i1++;
				}
			}
		}
		return arraylist;
	}

	/**
	 * 解析亚赔，大小
	 * 
	 * @param s1
	 * @param i1
	 */
	private void analyseYaPei_DaXiao(String s1, int i1) {
		ArrayList arraylist = new ArrayList();
		ArrayList arraylist1 = new ArrayList();
		arraylist.add(new as("", "", "", "", "", "", "", ""));
		String as1[] = s1.split("\\!", -1);
		int j1 = 0;
		while (j1 < as1.length) {
			String as2[] = as1[j1].split("\\^", -1);
			if (as2.length >= 8) {
				arraylist.add(new as(as2[0], as2[1], as2[2], as2[3], as2[4],
						as2[5], as2[6], as2[7]));
				arraylist1.add(new e(as2[1], as2[0]));
			}
			j1++;
		}
		if (arraylist.size() == 1)
			arraylist.add(new as(true));
		// k.setVisibility(0);
		// aq aq1 = new aq(arraylist, this, this, i1, false);
		// k.setAdapter(aq1);
		// aa.b(arraylist1);
		// aa.a(i1);
		System.out.println(arraylist);
	}

	/**
	 * 统计
	 * 
	 * @param s1
	 * @param customeBsid
	 * @return
	 */
	public List<QtBasketMatchPlayerStatisticModel> analyseTongJi(String s1,
			long customeBsid) {
		ArrayList arraylist = new ArrayList();
		String as1[] = s1.split("\\$", -1);
		if (as1.length < 3) {
			if (as1.length == 1 && s1.length() > 0 && !s1.contains("^")) { // 防止被过滤掉
				arraylist = null;
			}
			// y();
		} else {
			boolean flag = false;

			String as2[];
			int i1;
			// 主队数据
			// if(flag)
			// arraylist1.add(new ab(true, "", "球員", "上場", "投籃", "3分", "罰球",
			// "籃板", "助攻", "犯規", "得分"));
			// else
			// arraylist1.add(new ab(true, "", "球员", "上场", "投篮", "3分", "罚球",
			// "篮板", "助攻", "犯规", "得分"));
			as2 = as1[1].split("\\!", -1);
			i1 = 0;
			while (i1 < as2.length) {
				String dataStrArray[] = as2[i1].split("\\^", -1);
				if (dataStrArray.length >= 22) {
					String s4 = dataStrArray[5];
					String s5;
					if (flag)
						s5 = dataStrArray[2];
					else
						s5 = dataStrArray[1];
					// arraylist1.add(new ab(false, s4, s5, dataStrArray[6],
					// (new
					// StringBuilder()).append(dataStrArray[7]).append("-").append(dataStrArray[8]).toString(),
					// (new
					// StringBuilder()).append(dataStrArray[9]).append("-").append(dataStrArray[10]).toString(),
					// (new
					// StringBuilder()).append(dataStrArray[11]).append("-").append(dataStrArray[12]).toString(),
					// dataStrArray[15], dataStrArray[16], dataStrArray[17],
					// dataStrArray[21]));

					QtBasketMatchPlayerStatisticModel itemHostDataModel = new QtBasketMatchPlayerStatisticModel();
					itemHostDataModel.setBsId((int) customeBsid);
					itemHostDataModel.setProcessStatus(0);
					itemHostDataModel.setSource(1);
					itemHostDataModel.setPlayerId(dataStrArray[0]);
					itemHostDataModel.setPlayerName(dataStrArray[1]);
					itemHostDataModel.setPlayerNickName(dataStrArray[2]);
					itemHostDataModel.setPlayerEnglishName(dataStrArray[3]);
					itemHostDataModel.setStarter(dataStrArray[5]);
					itemHostDataModel.setLineUp(an.b(dataStrArray[6]));
					itemHostDataModel.setShootHitNumber(an.b(dataStrArray[7]));
					itemHostDataModel
							.setShootTotalNumber(an.b(dataStrArray[8]));
					itemHostDataModel.setThreeHitNumber(an.b(dataStrArray[9]));
					itemHostDataModel.setThreeTotalNumber(an
							.b(dataStrArray[10]));
					itemHostDataModel.setPenaltyHitNumber(an
							.b(dataStrArray[11]));
					itemHostDataModel.setPenaltyTotalNunber(an
							.b(dataStrArray[12]));
					itemHostDataModel.setBackboardAttackNumber(an
							.b(dataStrArray[13]));
					itemHostDataModel.setBackboardDefenceNumber(an
							.b(dataStrArray[14]));
					itemHostDataModel.setBackboardTotalNumber(an
							.b(dataStrArray[15]));
					itemHostDataModel.setAssist(an.b(dataStrArray[16]));
					itemHostDataModel.setFoul(an.b(dataStrArray[17]));
					itemHostDataModel.setSteal(an.b(dataStrArray[18]));
					itemHostDataModel.setFault(an.b(dataStrArray[19]));
					itemHostDataModel.setBlockedShot(an.b(dataStrArray[20]));
					itemHostDataModel.setScore(an.b(dataStrArray[21]));
					itemHostDataModel.setHomeGuestType(1);// 主队
					arraylist.add(itemHostDataModel);
				}
				i1++;
			}

			String as3[];
			int j1;
			// 客队数据
			// if(flag)
			// arraylist2.add(new ab(true, "", "球員", "上場", "投籃", "3分", "罰球",
			// "籃板", "助攻", "犯規", "得分"));
			// else
			// arraylist2.add(new ab(true, "", "球员", "上场", "投篮", "3分", "罚球",
			// "篮板", "助攻", "犯规", "得分"));
			as3 = as1[2].split("\\!", -1);
			j1 = 0;
			while (j1 < as3.length) {
				String dataStrArray[] = as3[j1].split("\\^", -1);
				if (dataStrArray.length >= 22) {
					String s2 = dataStrArray[5];
					String s3;
					if (flag)
						s3 = dataStrArray[2];
					else
						s3 = dataStrArray[1];
					// arraylist2.add(new ab(false, s2, s3, as4[6], (new
					// StringBuilder()).append(as4[7]).append("-").append(as4[8]).toString(),
					// (new
					// StringBuilder()).append(as4[9]).append("-").append(as4[10]).toString(),
					// (new
					// StringBuilder()).append(as4[11]).append("-").append(as4[12]).toString(),
					// as4[15], as4[16], as4[17], as4[21]));

					QtBasketMatchPlayerStatisticModel itemHostDataModel = new QtBasketMatchPlayerStatisticModel();
					itemHostDataModel.setBsId((int) customeBsid);
					itemHostDataModel.setProcessStatus(0);
					itemHostDataModel.setSource(1);
					itemHostDataModel.setPlayerId(dataStrArray[0]);
					itemHostDataModel.setPlayerName(dataStrArray[1]);
					itemHostDataModel.setPlayerNickName(dataStrArray[2]);
					itemHostDataModel.setPlayerEnglishName(dataStrArray[3]);
					itemHostDataModel.setStarter(dataStrArray[5]);
					itemHostDataModel.setLineUp(an.b(dataStrArray[6]));
					itemHostDataModel.setShootHitNumber(an.b(dataStrArray[7]));
					itemHostDataModel
							.setShootTotalNumber(an.b(dataStrArray[8]));
					itemHostDataModel.setThreeHitNumber(an.b(dataStrArray[9]));
					itemHostDataModel.setThreeTotalNumber(an
							.b(dataStrArray[10]));
					itemHostDataModel.setPenaltyHitNumber(an
							.b(dataStrArray[11]));
					itemHostDataModel.setPenaltyTotalNunber(an
							.b(dataStrArray[12]));
					itemHostDataModel.setBackboardAttackNumber(an
							.b(dataStrArray[13]));
					itemHostDataModel.setBackboardDefenceNumber(an
							.b(dataStrArray[14]));
					itemHostDataModel.setBackboardTotalNumber(an
							.b(dataStrArray[15]));
					itemHostDataModel.setAssist(an.b(dataStrArray[16]));
					itemHostDataModel.setFoul(an.b(dataStrArray[17]));
					itemHostDataModel.setSteal(an.b(dataStrArray[18]));
					itemHostDataModel.setFault(an.b(dataStrArray[19]));
					itemHostDataModel.setBlockedShot(an.b(dataStrArray[20]));
					itemHostDataModel.setScore(an.b(dataStrArray[21]));
					itemHostDataModel.setHomeGuestType(2);// 主队
					arraylist.add(itemHostDataModel);
				}
				j1++;
			}
		}
		// System.out.println(arraylist);
		return arraylist;
	}

	/**
	 * 解析欧赔
	 * 
	 * @param s1
	 * @param i1
	 *            不用传
	 */
	private void analyseOuPei(String s1, int i1) {
		ArrayList arraylist = new ArrayList();
		ArrayList arraylist1 = new ArrayList();
		String as1[] = s1.split("\\!", -1);
		int j1 = 0;
		int k1 = 0;
		float f1 = 0.0F;
		float f2 = 0.0F;
		float f3 = 0.0F;
		float f4 = 0.0F;
		while (j1 < as1.length) {
			String as2[] = as1[j1].split("\\^", -1);
			float f5;
			float f6;
			float f7;
			float f8;
			int l1;
			if (as2.length < 6) {
				l1 = k1;
				f8 = f1;
				f7 = f2;
				f6 = f3;
				f5 = f4;
			} else {
				arraylist.add(new betCode(as2[1], as2[0], "主胜", "客胜", "初盘",
						as2[2], as2[3], "即时", as2[4], as2[5]));
				f5 = f4 + an.c(as2[2]);
				f6 = f3 + an.c(as2[3]);
				f7 = f2 + an.c(as2[4]);
				f8 = f1 + an.c(as2[5]);
				l1 = k1 + 1;
				arraylist1.add(new e(as2[1], as2[0]));
			}
			j1++;
			k1 = l1;
			f1 = f8;
			f2 = f7;
			f3 = f6;
			f4 = f5;
		}
		if (arraylist.size() == 0) {
			// y();
		} else {
			String s2 = "主胜";
			String s3 = "客胜";
			String s4 = "初盘";
			Object aobj[] = new Object[1];
			aobj[0] = Float.valueOf(f4 / (float) k1);
			String s5 = String.format("%.2f", aobj);
			Object aobj1[] = new Object[1];
			aobj1[0] = Float.valueOf(f3 / (float) k1);
			String s6 = String.format("%.2f", aobj1);
			String s7 = "即时";
			Object aobj2[] = new Object[1];
			aobj2[0] = Float.valueOf(f2 / (float) k1);
			String s8 = String.format("%.2f", aobj2);
			Object aobj3[] = new Object[1];
			aobj3[0] = Float.valueOf(f1 / (float) k1);
			arraylist.add(0, new betCode("0", "平均值", s2, s3, s4, s5, s6, s7,
					s8, String.format("%.2f", aobj3)));
			// k.setVisibility(0);
			// t t1 = new t(arraylist, this, this, i1);
			// k.setAdapter(t1);
			// aa.b(arraylist1);
			// aa.a(i1);
		}
		System.out.println(arraylist);
	}

	private void e(String s1, int i1) {
		ArrayList arraylist = new ArrayList();
		String as1[] = s1.split("\\!", -1);
		int j1 = 0;
		while (j1 < as1.length) {
			String as2[] = as1[j1].split("\\^", -1);
			if (i1 == 1 || i1 == 2) {
				if (as2.length >= 4)
					arraylist.add(new bk(as2[0], as2[1], as2[2], as2[3]));
			} else if (as2.length >= 3)
				arraylist.add(new bk(as2[0], "", as2[1], as2[2]));
			j1++;
		}
		// w w1 = new w(arraylist, this, false, i1);
		// p.setAdapter(w1);
		// ab.a(aa.d());
		// ab.notifyDataSetChanged();
	}

	// private void f()
	// {
	// ae.setText(c);
	// af.setText(d);
	// al.setText(com.bet007.mobile.score.i.w.a("", e));
	// ak.setText(com.bet007.mobile.score.common.an.a(f, "MM-dd HH:mm"));
	// if(g != null && !g.equals("") && h != null && !h.equals(""))
	// {
	// aj.setVisibility(0);
	// ai.setVisibility(8);
	// aj.setText((new
	// StringBuilder()).append(g).append(":").append(h).toString());
	// aj.setTextColor(com.bet007.mobile.score.i.w.d(e));
	// } else
	// {
	// aj.setVisibility(8);
	// ai.setVisibility(0);
	// }
	// if(com.bet007.mobile.score.common.an.b(i) > 0 ||
	// com.bet007.mobile.score.common.an.b(j) > 0)
	// al.setText((new
	// StringBuilder()).append("( ").append(i).append(" - ").append(j).append(" )").toString());
	// }
	//
	// private void g()
	// {
	// if(e == 0)
	// a = 0x7f070053;
	// s = (LinearLayout)findViewById(0x7f070057);
	// t = (TextView)findViewById(0x7f070058);
	// u = (TextView)findViewById(0x7f070059);
	// v = (TextView)findViewById(0x7f07005a);
	// w = (TextView)findViewById(0x7f07005b);
	// x = (TextView)findViewById(0x7f07005c);
	// y = (TextView)findViewById(0x7f07005d);
	// z = (TextView)findViewById(0x7f07005f);
	// A = (TextView)findViewById(0x7f070060);
	// B = (TextView)findViewById(0x7f070061);
	// C = (TextView)findViewById(0x7f070062);
	// D = (TextView)findViewById(0x7f070063);
	// E = (TextView)findViewById(0x7f070064);
	// F = (TextView)findViewById(0x7f070065);
	// G = (TextView)findViewById(0x7f070066);
	// H = (TextView)findViewById(0x7f070067);
	// I = (TextView)findViewById(0x7f070068);
	// J = (TextView)findViewById(0x7f070069);
	// K = (TextView)findViewById(0x7f07006a);
	// L = (TextView)findViewById(0x7f07006b);
	// M = (TextView)findViewById(0x7f07006c);
	// N = (TextView)findViewById(0x7f07006d);
	// O = (TextView)findViewById(0x7f07006e);
	// P = (TextView)findViewById(0x7f07006f);
	// k = (ListView)findViewById(0x7f070071);
	// l = (ExpandableListView)findViewById(0x7f070072);
	// m = (LinearLayout)findViewById(0x7f070073);
	// n = (TextView)findViewById(0x7f070078);
	// o = (ListView)findViewById(0x7f070076);
	// p = (ListView)findViewById(0x7f070077);
	// q = (TextView)findViewById(0x7f070074);
	// r = (TextView)findViewById(0x7f070075);
	// Q = (Button)findViewById(0x7f070051);
	// R = (Button)findViewById(0x7f070052);
	// S = (Button)findViewById(0x7f070053);
	// T = (Button)findViewById(0x7f070054);
	// U = (Button)findViewById(0x7f070055);
	// V = (Button)findViewById(0x7f070056);
	// W = (TextView)findViewById(0x7f07005e);
	// X = (TextView)findViewById(0x7f070070);
	// ac = (ImageView)findViewById(0x7f070046);
	// ad = (ImageView)findViewById(0x7f07004d);
	// ae = (TextView)findViewById(0x7f070047);
	// af = (TextView)findViewById(0x7f07004e);
	// ag = (TextView)findViewById(0x7f070048);
	// ah = (TextView)findViewById(0x7f07004f);
	// ai = (ImageView)findViewById(0x7f070049);
	// aj = (TextView)findViewById(0x7f07004a);
	// ak = (TextView)findViewById(0x7f07004c);
	// al = (TextView)findViewById(0x7f07004b);
	// aa = new c();
	// ab = new com.bet007.mobile.score.activity.fenxi.c(this, this, aa);
	// o.setAdapter(ab);
	// k();
	// h();
	// }
	//
	// private void h()
	// {
	// d(0x7f070051);
	// d(0x7f070052);
	// d(0x7f070053);
	// d(0x7f070054);
	// d(0x7f070055);
	// d(0x7f070056);
	// }
	//
	// private void i()
	// {
	// aa.a(a, this, b);
	// }

	/**
	 * 分析
	 * 
	 * @param s1
	 */
	private void analyseFenXi(String s1) {
		ArrayList arraylist = new ArrayList();
		String as1[] = s1.split("\\$\\$", -1);
		if (as1.length < 15) {
			// goto _L1
			// _L1:
			// y();
		} else {
			// goto _L2;

			// _L4:
			// return;
			// _L2:
			String as2[] = as1[5].split("\\^", -1);
			if (as2.length == 2) {
				int i1 = an.b(as2[0]);
				int j1 = an.b(as2[1]);
				boolean flag = false;
				// l.setVisibility(0);

				ArrayList arrayLianSaiJiFenPaiMing = new ArrayList();
				arrayLianSaiJiFenPaiMing.add(new b(1, c));
				String as3[];
				int k1;

				int l3;
				int i4;

				String as6[];
				int j4;
				int k4;
				int l4;
				int i5;
				int j5;
				int k5;
				int l5;
				int i6;
				int j6;
				String as7[];
				int k6;
				int l6;
				int i7;
				int j7;
				int k7;
				int l7;
				int i8;
				int j8;
				boolean flag1;
				boolean flag2;
				ArrayList arraylist4;
				int k8;
				boolean flag3;
				ArrayList arraylist5;
				int l8;
				boolean flag4;
				boolean flag5;
				ArrayList arraylist6;
				int i9;
				boolean flag6;
				boolean flag7;
				boolean flag8;
				ArrayList arraylist7;
				int j9;
				ArrayList arraylist8;
				String as8[];
				int k9;
				ArrayList arraylist9;
				String as9[];
				String as10[];
				String as11[];
				int l9;
				float f1;
				float f2;
				float f3;
				int i10;
				int j10;
				int k10;
				int l10;
				int i11;
				int j11;
				int k11;
				int l11;
				int i12;
				int j12;
				int k12;
				int l12;
				String as12[];
				int i13;
				float f4;
				float f5;
				float f6;
				int j13;
				int k13;
				int l13;
				int i14;
				int j14;
				int k14;
				int l14;
				int i15;
				int j15;
				int k15;
				int l15;
				int i16;
				int k16;
				int l16;
				int i17;
				int j17;
				int k17;

				// 联赛积分排名
				if (flag)
					arrayLianSaiJiFenPaiMing.add(new b(1, true, "類型", "賽", "勝",
							"負", "得", "失", "淨", "排名", "勝率"));
				else
					arrayLianSaiJiFenPaiMing.add(new b(1, true, "类型", "赛", "胜",
							"负", "得", "失", "净", "排名", "胜率"));
				as3 = as1[0].split("\\!", -1);
				k1 = 0;
				while (k1 < as3.length) {
					if (a(as3[k1], 9, 1, 8)) {
						String as15[] = as3[k1].split("\\^", -1);
						String s4 = as15[0];
						String s5;
						if (flag)
							s5 = s4.replace("T", "總").replace("H", "主場")
									.replace("A", "客場").replace("N", "近10場");
						else
							s5 = s4.replace("T", "总").replace("H", "主场")
									.replace("A", "客场").replace("N", "近10场");
						arrayLianSaiJiFenPaiMing.add(new b(1, false, s5,
								as15[1], as15[2], as15[3], as15[4], as15[5],
								as15[6], as15[7], as15[8]));
					}
					k1++;
				}
				if (arrayLianSaiJiFenPaiMing.size() == 2) {
					arrayLianSaiJiFenPaiMing.clear();
					arrayLianSaiJiFenPaiMing.add(new b(true));
				}
				int l1 = arrayLianSaiJiFenPaiMing.size();
				arrayLianSaiJiFenPaiMing.add(new b(1, d));
				String as4[];
				int i2;
				if (flag)
					arrayLianSaiJiFenPaiMing.add(new b(1, true, "類型", "賽", "勝",
							"負", "得", "失", "淨", "排名", "勝率"));
				else
					arrayLianSaiJiFenPaiMing.add(new b(1, true, "类型", "赛", "胜",
							"负", "得", "失", "净", "排名", "胜率"));
				as4 = as1[1].split("\\!", -1);
				i2 = 0;
				while (i2 < as4.length) {
					if (a(as4[i2], 9, 1, 8)) {
						String as14[] = as4[i2].split("\\^", -1);
						String s2 = as14[0];
						String s3;
						if (flag)
							s3 = s2.replace("T", "總").replace("H", "主場")
									.replace("A", "客場").replace("N", "近10場");
						else
							s3 = s2.replace("T", "总").replace("H", "主场")
									.replace("A", "客场").replace("N", "近10场");
						arrayLianSaiJiFenPaiMing.add(new b(1, false, s3,
								as14[1], as14[2], as14[3], as14[4], as14[5],
								as14[6], as14[7], as14[8]));
					}
					i2++;
				}
				if (arrayLianSaiJiFenPaiMing.size() == l1 + 2) {
					if (arrayLianSaiJiFenPaiMing.size() == 3)
						arrayLianSaiJiFenPaiMing.clear();
					arrayLianSaiJiFenPaiMing.add(new b(true));
				}
				if (arrayLianSaiJiFenPaiMing.size() > 1)
					arraylist.add(new q("联赛积分排名", arrayLianSaiJiFenPaiMing));
				// 对赛往绩
				ArrayList arrayDuiSaiWangJi = new ArrayList();
				String as5[];
				int j2;
				int k2;
				int l2;
				int i3;
				int j3;
				int k3;
				if (flag)
					arrayDuiSaiWangJi.add(new b(2, true, 0, 0, "時間", "聯賽",
							"主隊", "比分", "客隊", "讓分"));
				else
					arrayDuiSaiWangJi.add(new b(2, true, 0, 0, "时间", "联赛",
							"主队", "比分", "客队", "让分"));
				as5 = as1[2].split("\\!", -1);
				j2 = 0;
				k2 = 0;
				l2 = 0;
				i3 = 0;
				j3 = 0;
				k3 = 0;
				while (j2 < as5.length) {
					String as13[] = as5[j2].split("\\^", -1);
					int l17;
					int i18;
					int j18;
					int k18;
					int l18;
					if (as13.length < 8) {
						l18 = k3;
						l17 = k2;
						k18 = j3;
						j18 = i3;
						i18 = l2;
					} else {
						int j16 = 0;
						float f7 = an.c(as13[3].split("\\-", -1)[0]);
						float f8 = an.c(as13[3].split("\\-", -1)[1]);
						float f9 = an.c(as13[5]);

						if (f9 != 0.0F)
							k16 = i3 + 1;
						else
							k16 = i3;
						if (an.b(as13[6]) == i1) {
							int j19;
							if (f7 > f8) {
								j19 = k3 + 1;
								k17 = 1;
							} else {
								j3++;
								j19 = k3;
								k17 = 2;
							}
							if (f7 - f9 > f8) {
								j16 = 1;
								l16 = l2 + 1;
								i17 = j3;
								j17 = j19;
							} else {
								j16 = 2;
								l16 = l2;
								i17 = j3;
								j17 = j19;
							}
						} else if (an.b(as13[7]) == i1) {
							int i19;
							if (f7 < f8) {
								i19 = k3 + 1;
								k17 = 3;
							} else {
								j3++;
								i19 = k3;
								k17 = 4;
							}
							if (f7 - f9 < f8) {
								j16 = 1;
								l16 = l2 + 1;
								i17 = j3;
								j17 = i19;
							} else {
								j16 = 2;
								l16 = l2;
								i17 = j3;
								j17 = i19;
							}
						} else {
							l16 = l2;
							i17 = j3;
							j17 = k3;
							k17 = 0;
						}
						arrayDuiSaiWangJi
								.add(new b(2, false, k17, j16, as13[0].trim(),
										as13[1].trim(), as13[2].trim(), as13[3]
												.trim(), as13[4].trim(),
										as13[5].trim()));
						l17 = k2 + 1;
						i18 = l16;
						j18 = k16;
						k18 = i17;
						l18 = j17;
					}
					j2++;
					k2 = l17;
					l2 = i18;
					i3 = j18;
					j3 = k18;
					k3 = l18;
				}
				if (arrayDuiSaiWangJi.size() == 1) {
					arrayDuiSaiWangJi.clear();
					arrayDuiSaiWangJi.add(new b(true));
				} else {
					if (k2 != 0)
						l3 = (k3 * 100) / k2;
					else
						l3 = 0;
					if (i3 != 0)
						i4 = (l2 * 100) / i3;
					else
						i4 = 0;
					if (flag)

						arrayDuiSaiWangJi.add(new b(2, true, "近" + k2 + "場，"
								+ this.c + " 勝" + k3 + " 負" + j3 + "，勝率" + l3
								+ "%，贏盤率" + i4 + "%"));
					else

						arrayDuiSaiWangJi.add(new b(2, true, "近" + k2 + "场，"
								+ this.c + " 胜" + k3 + " 负" + j3 + "，胜率" + l3
								+ "%，赢盘率" + i4 + "%"));
				}
				if (arrayDuiSaiWangJi.size() > 1)
					arraylist.add(new q("对赛往绩", arrayDuiSaiWangJi));

				ArrayList arrayJinQiZhanJi;
				arrayJinQiZhanJi = new ArrayList();
				arrayJinQiZhanJi.add(new b(3, c));
				if (flag)
					arrayJinQiZhanJi.add(new b(3, true, 0, 0, "時間", "聯賽", "主隊",
							"比分", "客隊", "讓分"));
				else
					arrayJinQiZhanJi.add(new b(3, true, 0, 0, "时间", "联赛", "主队",
							"比分", "客队", "让分"));
				as6 = as1[3].split("\\!", -1);
				j4 = 0;
				k4 = 0;
				l4 = 0;
				i5 = 0;
				j5 = 0;
				k5 = 0;
				while (j4 < as6.length) {
					as12 = as6[j4].split("\\^", -1);
					if (as12.length < 8) {
						k15 = k5;
						k14 = k4;
						j15 = j5;
						i15 = i5;
						l14 = l4;
					} else {
						i13 = 0;
						f4 = an.c(as12[3].split("\\-", -1)[0]);
						f5 = an.c(as12[3].split("\\-", -1)[1]);
						f6 = an.c(as12[5]);
						if (f6 != 0.0F)
							j13 = i5 + 1;
						else
							j13 = i5;
						if (an.b(as12[6]) == i1) {
							if (f4 > f5) {
								i16 = k5 + 1;
								j14 = 1;
							} else {
								j5++;
								i16 = k5;
								j14 = 2;
							}
							if (f4 - f6 > f5) {
								i13 = 1;
								k13 = l4 + 1;
								l13 = j5;
								i14 = i16;
							} else {
								i13 = 2;
								k13 = l4;
								l13 = j5;
								i14 = i16;
							}
						} else if (an.b(as12[7]) == i1) {
							if (f4 < f5) {
								l15 = k5 + 1;
								j14 = 3;
							} else {
								j5++;
								l15 = k5;
								j14 = 4;
							}
							if (f4 - f6 < f5) {
								i13 = 1;
								k13 = l4 + 1;
								l13 = j5;
								i14 = l15;
							} else {
								i13 = 2;
								k13 = l4;
								l13 = j5;
								i14 = l15;
							}
						} else {
							k13 = l4;
							l13 = j5;
							i14 = k5;
							j14 = 0;
						}
						arrayJinQiZhanJi
								.add(new b(3, false, j14, i13, as12[0].trim(),
										as12[1].trim(), as12[2].trim(), as12[3]
												.trim(), as12[4].trim(),
										as12[5].trim()));
						k14 = k4 + 1;
						l14 = k13;
						i15 = j13;
						j15 = l13;
						k15 = i14;
					}
					j4++;
					k4 = k14;
					l4 = l14;
					i5 = i15;
					j5 = j15;
					k5 = k15;
				}
				if (arrayJinQiZhanJi.size() == 2) {
					arrayJinQiZhanJi.clear();
					arrayJinQiZhanJi.add(new b(true));
				} else {
					if (k4 != 0)
						l5 = (k5 * 100) / k4;
					else
						l5 = 0;
					if (i5 != 0)
						i6 = (l4 * 100) / i5;
					else
						i6 = 0;
					if (flag)

						arrayJinQiZhanJi.add(new b(3, true, "近" + k4 + "場，勝"
								+ k5 + " 負" + j5 + "，勝率" + l5 + "%，贏盤率" + i6
								+ "%"));
					else

						arrayJinQiZhanJi.add(new b(3, true, "近" + k4 + "场，胜"
								+ k5 + " 负" + j5 + "，胜率" + l5 + "%，赢盘率" + i6
								+ "%"));
				}
				j6 = arrayJinQiZhanJi.size();
				arrayJinQiZhanJi.add(new b(3, d));
				if (flag)

					arrayJinQiZhanJi.add(new b(3, true, 0, 0, "時間", "聯賽", "主隊",
							"比分", "客隊", "讓分"));
				else

					arrayJinQiZhanJi.add(new b(3, true, 0, 0, "时间", "联赛", "主队",
							"比分", "客队", "让分"));
				as7 = as1[4].split("\\!", -1);
				k6 = 0;
				l6 = 0;
				i7 = 0;
				j7 = 0;
				k7 = 0;
				l7 = 0;
				while (k6 < as7.length) {
					as11 = as7[k6].split("\\^", -1);
					if (as11.length < 8) {
						j12 = l7;
						j11 = l6;
						i12 = k7;
						l11 = j7;
						k11 = i7;
					} else {
						l9 = 0;
						f1 = an.c(as11[3].split("\\-", -1)[0]);
						f2 = an.c(as11[3].split("\\-", -1)[1]);
						f3 = an.c(as11[5]);
						if (f3 != 0.0F)
							i10 = j7 + 1;
						else
							i10 = j7;
						if (an.b(as11[6]) == j1) {
							if (f1 > f2) {
								l12 = l7 + 1;
								i11 = 1;
							} else {
								k7++;
								l12 = l7;
								i11 = 2;
							}
							if (f1 - f3 > f2) {
								l9 = 1;
								j10 = i7 + 1;
								k10 = k7;
								l10 = l12;
							} else {
								l9 = 2;
								j10 = i7;
								k10 = k7;
								l10 = l12;
							}
						} else if (an.b(as11[7]) == j1) {
							if (f1 < f2) {
								k12 = l7 + 1;
								i11 = 3;
							} else {
								k7++;
								k12 = l7;
								i11 = 4;
							}
							if (f1 - f3 < f2) {
								l9 = 1;
								j10 = i7 + 1;
								k10 = k7;
								l10 = k12;
							} else {
								l9 = 2;
								j10 = i7;
								k10 = k7;
								l10 = k12;
							}
						} else {
							j10 = i7;
							k10 = k7;
							l10 = l7;
							i11 = 0;
						}
						arrayJinQiZhanJi
								.add(new b(3, false, i11, l9, as11[0].trim(),
										as11[1].trim(), as11[2].trim(), as11[3]
												.trim(), as11[4].trim(),
										as11[5].trim()));
						j11 = l6 + 1;
						k11 = j10;
						l11 = i10;
						i12 = k10;
						j12 = l10;
					}
					k6++;
					l6 = j11;
					i7 = k11;
					j7 = l11;
					k7 = i12;
					l7 = j12;
				}
				if (arrayJinQiZhanJi.size() == j6 + 2) {
					if (arrayJinQiZhanJi.size() == 3)
						arrayJinQiZhanJi.clear();
					arrayJinQiZhanJi.add(new b(true));
				} else {
					if (l6 != 0)
						i8 = (l7 * 100) / l6;
					else
						i8 = 0;
					if (j7 != 0)
						j8 = (i7 * 100) / j7;
					else
						j8 = 0;
					if (flag)

						arrayJinQiZhanJi.add(new b(3, true, "近" + l6 + "場，勝"
								+ i7 + " 負" + k7 + "，勝率" + i8 + "%，贏盤率" + j8
								+ "%"));
					else

						arrayJinQiZhanJi.add(new b(3, true, "近" + l6 + "场，胜"
								+ i7 + " 负" + k7 + "，胜率" + i8 + "%，赢盘率" + j8
								+ "%"));
				}
				if (arrayJinQiZhanJi.size() > 1)
					arraylist.add(new q("近期战绩", arrayJinQiZhanJi));
				flag1 = true;
				flag2 = true;
				arraylist4 = new ArrayList();
				a(flag, arraylist4, true, as1[6]);
				if (arraylist4.size() == 2) {
					flag1 = false;
					arraylist4.add(new b(true));
				}
				k8 = arraylist4.size();
				a(flag, arraylist4, false, as1[7]);
				if (arraylist4.size() == k8 + 2) {
					flag2 = false;
					arraylist4.add(new b(true));
				}
				if (flag1 || flag2)
					arraylist.add(new q("盘路走势", arraylist4));
				flag3 = true;
				arraylist5 = new ArrayList();
				a(flag, arraylist5, true, as1[8], i1, j1);
				if (arraylist5.size() == 2) {
					arraylist5.add(new b(true));
					flag3 = false;
				}
				l8 = arraylist5.size();
				a(flag, arraylist5, false, as1[9], i1, j1);
				if (arraylist5.size() == l8 + 2) {
					flag4 = false;
					arraylist5.add(new b(true));
				} else {
					flag4 = true;
				}
				if (flag3 || flag4)
					arraylist.add(new q("相同历史盘口", arraylist5));
				flag5 = true;
				arraylist6 = new ArrayList();
				b(flag, arraylist6, true, as1[10], i1, j1);
				if (arraylist6.size() == 2) {
					arraylist6.add(new b(true));
					flag5 = false;
				}
				i9 = arraylist6.size();
				b(flag, arraylist6, false, as1[11], i1, j1);
				if (arraylist6.size() == i9 + 2) {
					flag6 = false;
					arraylist6.add(new b(true));
				} else {
					flag6 = true;
				}
				if (flag5 || flag6)
					arraylist.add(new q("未来三场", arraylist6));
				flag7 = true;
				flag8 = true;
				arraylist7 = new ArrayList();
				b(flag, arraylist7, true, as1[12]);
				if (arraylist7.size() == 2) {
					flag7 = false;
					arraylist7.add(new b(true));
				}
				j9 = arraylist7.size();
				b(flag, arraylist7, false, as1[12]);
				if (arraylist7.size() == j9 + 2) {
					flag8 = false;
					arraylist7.add(new b(true));
				}
				if (flag7 || flag8)
					arraylist.add(new q("预计阵容", arraylist7));
				arraylist8 = new ArrayList();
				if (flag)
					arraylist8.add(new b(8, true, true, "球隊", "球員", "位置", "原因",
							"日期", "備注"));
				else
					arraylist8.add(new b(8, true, true, "球队", "球员", "位置", "原因",
							"日期", "备注"));
				as8 = as1[13].split("\\!", -1);
				k9 = 1;
				while (k9 < as8.length) {
					as10 = as8[k9].split("\\^", -1);
					if (as10.length >= 6)
						arraylist8.add(new b(8, false, true, as10[0].trim(),
								as10[1].trim(), as10[2].trim(), as10[3].trim(),
								as10[4].trim(), as10[5].trim()));
					k9++;
				}
				if (arraylist8.size() > 1)
					arraylist.add(new q("伤停情况", arraylist8));
				arraylist9 = new ArrayList();
				if (flag)
					arraylist9
							.add(new b(9, true, "球隊", "近期走勢", "盤路輸贏", "上下盤盤路"));
				else
					arraylist9
							.add(new b(9, true, "球队", "近期走势", "盘路输赢", "上下盘盘路"));
				as9 = as1[14].split("\\^", -1);
				if (as9.length >= 9) {
					arraylist9.add(new b(9, false, as9[0], as9[1], as9[2],
							as9[3]));
					arraylist9.add(new b(9, false, as9[4], as9[5], as9[6],
							as9[7]));
					arraylist9.add(new b(9, as9[8]));
				}
				if (arraylist9.size() > 1)
					arraylist.add(new q("澳彩推荐", arraylist9));

			}
			// if(true) goto _L4; else goto _L3
			// _L3:
		}
		System.out.println(arraylist);
	}

	private void k() {

	}

	private void l() {

	}

	/**
	 * 解析篮球赔率
	 * 
	 * @param response
	 * @param companyId
	 * @param id
	 * @return
	 */
	public List analyseBasketIndex(String response, long customBsId,
			String companyId) {
		// if (response == null) {
		// return null;
		// }
		ArrayList arraylist = new ArrayList();
		String as1[] = response.split("\\!", -1);
		int i1 = 0;
		if (as1.length == 1) {
			if (StringUtils.isNotBlank(response) && !response.contains("^")) { // 被屏蔽，重新抓取
				arraylist = null;
			}
		}
		while (i1 < as1.length) {
			String as2[] = as1[i1].split("\\^", -1);
			if (as2.length >= 4) {
				// lq_indexModel a1 = new lq_indexModel(-1, false, false,
				// as2[0], as2[1], as2[2], as2[3], as2[4], as2[5]);

				QtBasketMatchOddsModel itemBasketMatchOddsModel = new QtBasketMatchOddsModel();
				itemBasketMatchOddsModel.setBsId((int) customBsId);
				itemBasketMatchOddsModel.setCorpId(companyId);
				itemBasketMatchOddsModel.setProcessStatus(0);
				itemBasketMatchOddsModel.setSource(1);
				itemBasketMatchOddsModel
						.setHomeWinOdds(toConvertNotNullDoubleValue(as2[0]));
				itemBasketMatchOddsModel
						.setHandicapOrScore(toConvertNotNullDoubleValue(as2[1]));// 亚赔大小：盘口
				itemBasketMatchOddsModel
						.setGuestWinOdds(toConvertNotNullDoubleValue(as2[2]));
				itemBasketMatchOddsModel.setTimestamp(DateFormateUtil.toDate(
						"yyyyMMddHHmmss", as2[3]));
				arraylist.add(itemBasketMatchOddsModel);
			}
			i1++;
		}
		return arraylist;
	}

	private Double toConvertNotNullDoubleValue(String doubleValue) {
		Double double1 = ValueConvertUtil.safeDouble(doubleValue);
		return double1 == null ? 0.0 : double1;
	}

	// private List m()
	// {
	// ArrayList arraylist = new ArrayList();
	// if(an.a())
	// {
	// String as1[] = ScoreApplication.m.split("\\!", -1);
	// int i1 = 0;
	// while(i1 < as1.length)
	// {
	// String as2[] = as1[i1].split("\\^", -1);
	// if(as2.length >= 6)
	// {
	// a a1 = new a(-1, false, false, as2[0], as2[1], as2[2], as2[3], as2[4],
	// as2[5]);
	// ArrayList arraylist1 = new ArrayList();
	// arraylist1.add(new b(true, a1));
	// arraylist.add(new q("", arraylist1));
	// }
	// i1++;
	// }
	// }
	// return arraylist;
	// }

	public void b(String s1) {
	}

	public void b(String s1, int i1) {
		// am = true;
		// aa.a(this, s1, i1, false);
	}

	public void b(String s1, String s2, String s3) {
	}

	public List<QtBasketMatchOddsModel> analyseBasketJishiOdds(String response,
			Qt_fb_match_oddsType oddsType, String lastReponse) {
		ArrayList arraylist = new ArrayList();
		String as1[] = response.split("\\!", -1);
		int i1 = 0;
		if (as1.length == 1 && StringUtils.isNotBlank(response)
				&& !response.contains("^")) { // 被屏蔽，重新抓取
			arraylist = null;
		} else {
			writeResponseToFileEnd(response, oddsType); // 追加本次内容到文件末尾
			if (!StringUtils.equals(lastReponse, response)) {
				while (i1 < as1.length) {
					// 检测赔率是否有变化
					if (lastReponse != null && !lastReponse.contains(as1[i1])) {
						String as2[] = as1[i1].split("\\^", -1);
						if (as2.length >= 6
								&& oddsType == Qt_fb_match_oddsType.euro) {
							// lq_indexModel a1 = new lq_indexModel(-1, false,
							// false,
							// as2[0], as2[1], as2[2], as2[3], as2[4], as2[5]);

							QtBasketMatchOddsModel itemBasketMatchOddsModel = new QtBasketMatchOddsModel();// 初盘
							itemBasketMatchOddsModel.setCorpName(as2[0]);
							itemBasketMatchOddsModel.setProcessStatus(0);

							itemBasketMatchOddsModel.setSource(1);
							itemBasketMatchOddsModel
									.setHomeWinOdds(toConvertNotNullDoubleValue(as2[2]));
							itemBasketMatchOddsModel.setTimestamp(new Date());
							itemBasketMatchOddsModel
									.setGuestWinOdds(toConvertNotNullDoubleValue(as2[3]));
							itemBasketMatchOddsModel.setQtOddsId(as2[1]);

							QtBasketMatchOddsModel basketMatchOddsModel = new QtBasketMatchOddsModel(); // 即时盘
							basketMatchOddsModel
									.setHomeWinOdds(toConvertNotNullDoubleValue(as2[4]));
							basketMatchOddsModel
									.setGuestWinOdds(toConvertNotNullDoubleValue(as2[5]));
							basketMatchOddsModel.setCorpName(as2[0]);

							basketMatchOddsModel.setSource(1);
							basketMatchOddsModel.setProcessStatus(0);
							basketMatchOddsModel.setTimestamp(new Date());

							basketMatchOddsModel
									.setQtBasketInitOddsModel(itemBasketMatchOddsModel);
							arraylist.add(basketMatchOddsModel);
						}
						if (as2.length >= 8
								&& (oddsType == Qt_fb_match_oddsType.ou || oddsType == Qt_fb_match_oddsType.asia)) {
							QtBasketMatchOddsModel itemBasketMatchOddsModel = new QtBasketMatchOddsModel();// 初盘
							itemBasketMatchOddsModel.setCorpName(as2[0]);
							itemBasketMatchOddsModel.setProcessStatus(0);

							itemBasketMatchOddsModel.setSource(1);
							itemBasketMatchOddsModel
									.setHomeWinOdds(toConvertNotNullDoubleValue(as2[2]));
							itemBasketMatchOddsModel
									.setHandicapOrScore(toConvertNotNullDoubleValue(as2[3]));
							itemBasketMatchOddsModel.setTimestamp(new Date());
							itemBasketMatchOddsModel
									.setGuestWinOdds(toConvertNotNullDoubleValue(as2[4]));
							itemBasketMatchOddsModel.setQtOddsId(as2[1]);

							QtBasketMatchOddsModel basketMatchOddsModel = new QtBasketMatchOddsModel();// 即时盘
							basketMatchOddsModel
									.setHomeWinOdds(toConvertNotNullDoubleValue(as2[5]));
							basketMatchOddsModel.setCorpName(as2[0]);

							basketMatchOddsModel
									.setHandicapOrScore(toConvertNotNullDoubleValue(as2[6]));
							basketMatchOddsModel
									.setGuestWinOdds(toConvertNotNullDoubleValue(as2[7]));
							basketMatchOddsModel.setSource(1);
							basketMatchOddsModel.setProcessStatus(0);
							basketMatchOddsModel.setTimestamp(new Date());

							basketMatchOddsModel
									.setQtBasketInitOddsModel(itemBasketMatchOddsModel);
							arraylist.add(basketMatchOddsModel);
						}
					}
					i1++;
				}
			}
		}
		return arraylist;
	}

	public List<QtBasketMatchOddsModel> analyseBasketJishiOdds(String response,
			String lastReponse, String corpId) {
		ArrayList arraylist = new ArrayList();
		String as1[] = response.split("\\$\\$", -1);
		int i1 = 0;
		if (as1.length == 1 && StringUtils.isNotBlank(response)
				&& !response.contains("^")) { // 被屏蔽，重新抓取
			arraylist = null;
		} else if (!response.equals("")) {
			writeResponseToFileEnd(response, Qt_fb_match_oddsType.odds); // 追加本次内容到文件末尾
			if (!StringUtils.equals(lastReponse, response)) {
				if (as1.length >= 2) {
					String[] asMess = as1[1].split("\\!");
					while (i1 < asMess.length) {
						// 检测赔率是否有变化
//						if (lastReponse == null
//								|| lastReponse.contains(asMess[i1])) {
							String[] oddsMess = asMess[i1].split("\\^",-1);
							if (oddsMess.length >= 24) {
								if (StringUtils.isNotBlank(oddsMess[8])) {
									QtBasketMatchOddsModel basketMatchOddsModel = new QtBasketMatchOddsModel(); // 即时亚盘
									basketMatchOddsModel
											.setHomeWinOdds(toConvertNotNullDoubleValue(oddsMess[11]));
									basketMatchOddsModel
											.setGuestWinOdds(toConvertNotNullDoubleValue(oddsMess[13]));
									basketMatchOddsModel
											.setHandicapOrScore(toConvertNotNullDoubleValue(oddsMess[12]));
									basketMatchOddsModel.setCorpId(corpId);
									basketMatchOddsModel.setQtBsId(oddsMess[0]);

									basketMatchOddsModel.setOddsType(Qt_fb_match_oddsType.asia);
									basketMatchOddsModel.setSource(1);
									basketMatchOddsModel.setProcessStatus(0);
									basketMatchOddsModel
											.setTimestamp(new Date());
									arraylist.add(basketMatchOddsModel);
								}
								if(StringUtils.isNotBlank(oddsMess[17])){
									QtBasketMatchOddsModel basketMatchOddsModel = new QtBasketMatchOddsModel(); // 即时大小盘
									basketMatchOddsModel
											.setHomeWinOdds(toConvertNotNullDoubleValue(oddsMess[17]));
									basketMatchOddsModel.setBsId(Integer.valueOf(oddsMess[0]));
									basketMatchOddsModel
											.setGuestWinOdds(toConvertNotNullDoubleValue(oddsMess[19]));
									basketMatchOddsModel
											.setHandicapOrScore(toConvertNotNullDoubleValue(oddsMess[18]));
									basketMatchOddsModel.setOddsType(Qt_fb_match_oddsType.ou);
									basketMatchOddsModel.setCorpId(corpId);

									basketMatchOddsModel.setSource(1);
									basketMatchOddsModel.setProcessStatus(0);
									basketMatchOddsModel.setQtBsId(oddsMess[0]);
									basketMatchOddsModel
											.setTimestamp(new Date());
									arraylist.add(basketMatchOddsModel);
								}
								if(StringUtils.isNotBlank(oddsMess[22])){
									QtBasketMatchOddsModel basketMatchOddsModel = new QtBasketMatchOddsModel(); // 即时欧赔
									basketMatchOddsModel
											.setHomeWinOdds(toConvertNotNullDoubleValue(oddsMess[22]));
									basketMatchOddsModel.setBsId(Integer.valueOf(oddsMess[0]));
									basketMatchOddsModel
											.setGuestWinOdds(toConvertNotNullDoubleValue(oddsMess[23]));
									basketMatchOddsModel.setOddsType(Qt_fb_match_oddsType.euro);
									basketMatchOddsModel.setCorpId(corpId);

									basketMatchOddsModel.setSource(1);
									basketMatchOddsModel.setProcessStatus(0);
									basketMatchOddsModel.setQtBsId(oddsMess[0]);
									basketMatchOddsModel
											.setTimestamp(new Date());
									arraylist.add(basketMatchOddsModel);
								}
							}

//						}
						i1++;
					}
				}
			}
		}
		return arraylist;
	}

	/**
	 * @param responseStr
	 * @param oddsType
	 * @return
	 * @throws IOException
	 */
	private synchronized void writeResponseToFileEnd(String responseStr,
			Qt_fb_match_oddsType oddsType) {
		String fileName = "";
		if (oddsType == Qt_fb_match_oddsType.euro) {
			fileName = SystemPropertiesUtil.getPropsValue("lqEuroFile");
		} else if (oddsType == Qt_fb_match_oddsType.asia) {
			fileName = SystemPropertiesUtil.getPropsValue("lqAsianFile");
		} else if (oddsType == Qt_fb_match_oddsType.ou) {
			fileName = SystemPropertiesUtil.getPropsValue("lqOuFile");
		} else {
			fileName = SystemPropertiesUtil.getPropsValue("lqOddsFile");
		}
		File file = FileUtils.getFile(Lq_FenXi.class.getResource("/").getPath()
				+ fileName);
		String content = "";

		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			if (StringUtils.isNotBlank(responseStr)) {
				FileUtils.write(file, responseStr, true);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
