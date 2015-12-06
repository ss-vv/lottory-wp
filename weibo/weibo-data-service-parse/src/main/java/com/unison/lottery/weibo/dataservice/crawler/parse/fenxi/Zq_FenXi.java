package com.unison.lottery.weibo.dataservice.crawler.parse.fenxi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.i;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.u.c;
import com.unison.lottery.weibo.dataservice.crawler.service.model.OddsBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchEventModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchLineupModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.util.DateFormateUtil;
import com.unison.lottery.weibo.dataservice.crawler.util.ValueConvertUtil;
import com.unison.lottery.weibo.dataservice.parse.model.FBMatchEuropeOddDetail;
import com.xhcms.lottery.utils.DateUtils;

public class Zq_FenXi {
	
	public static void main(String[] args) {
		Zq_FenXi fenxi = new Zq_FenXi();
//		ouPei  http://apk.win007.com/phone/1x2.aspx?ID=993749&lang=0&ran=1405767700559
		String ouPei = "10BET^42638342^1.41^4.26^6.94^1.43^4.31^6.41^16!12BET^42638411^1.43^4.30^6.75^1.51^3.96^6.05^18!bet 365^42638420^1.40^4.50^8.00^1.44^4.33^7.00^281!BETDAQ^42668057^1.42^4.50^7.20^1.47^4.60^7.00^54!Betfair^42648081^1.39^4.70^8.00^1.50^4.80^7.40^2!bwin^42678533^1.40^4.50^5.75^1.40^4.50^5.75^255!Coral^42703551^1.44^4.00^6.50^1.44^4.00^6.50^88!Eurobet^42709760^1.45^4.20^6.40^1.45^4.20^6.40^71!Expekt^42658244^1.38^4.15^6.75^1.42^4.10^6.00^70!Gamebookers^42678535^1.40^4.50^5.75^1.40^4.50^5.75^158!IBCBET^42671129^1.47^4.14^6.32^1.43^4.30^6.75^649!Interwetten^42668080^1.45^3.90^6.00^1.45^3.90^6.00^104!PinnacleSports^42668092^1.44^4.88^7.50^1.48^4.69^7.01^177!SB^42659680^1.40^4.30^6.50^1.44^4.30^6.35^545!SNAI^42668096^1.45^4.00^6.65^1.45^4.20^6.10^110!Stan James^42668100^1.44^4.00^6.25^1.44^4.00^6.00^100!博天堂^42668098^1.43^4.20^6.50^1.43^4.20^6.50^422!金宝博^42659673^1.40^4.30^6.50^1.44^4.30^6.35^499!立博^42638395^1.40^4.25^7.00^1.44^4.20^6.50^82!利记sbobet^42638321^1.42^4.30^7.00^1.49^4.10^6.00^474!明陞^42638406^1.43^4.30^6.75^1.51^3.96^6.05^517!威廉希尔^42683344^1.40^4.20^6.00^1.44^4.33^6.00^115!伟德^42638351^1.444^4.30^7.50^1.45^4.50^6.50^81!易胜博^42676143^1.45^4.00^6.20^1.45^4.00^6.20^90!盈禾^42659677^1.40^4.30^6.50^1.44^4.30^6.35^659!优胜客^42648105^1.38^4.25^7.25^1.45^4.10^6.00^386";
//		fenxi.analyseOuPei(ouPei);
		
//		panWang http://data.win007.com/GuessMacth.aspx?ran=1405771931892&sid=949281&subversion=1&from=1&version=3
		String panWang = "100##11162$$0.72^1.25^1.21^107^39^0^0^1^1$$0.81^3^1.05^67^10^0^1^1^1";
//		fenxi.analysePanWang(panWang);
		
//		saishiFenxi http://txt.win007.com/phone/analysis/1/05/cn/1051123.htm?ran=1405772358415
		String saiShiFenXi = "总^8^7^1^0^23^8^22^1^88%^0!主^4^4^0^0^11^2^12^1^100%!客^4^3^1^0^12^6^10^1^75%!近^6^5^1^0^18^7^16^^83%$$总^13^8^2^3^21^14^26^3^62%^0!主^7^5^1^1^15^8^16^2^71%!客^6^3^1^2^6^6^10^6^50%!近^6^3^2^1^9^6^11^^50%$$$$$$20141018220000^英超^水晶宫^切尔西^1^2^输^35^24^0^1^-1.5!20141005212000^英超^切尔西^阿森纳^2^0^赢^24^19^1^0^0.75!20141001024500^欧冠杯^里斯本竞技^切尔西^0^1^赢^466^24^0^1^-0.75!20140927220000^英超^切尔西^阿斯顿维拉^3^0^赢^24^20^1^0^1.75!20140925024500^英联杯^切尔西^博尔顿^2^1^输^24^22^1^1^2!20140921230000^英超^曼彻斯特城^切尔西^1^1^赢^26^24^0^0^0.25!20140918024500^欧冠杯^切尔西^沙尔克04^1^1^输^24^151^1^0^1.25!20140913220000^英超^切尔西^斯旺西^4^2^赢^24^1194^1^1^1.75!20140831003000^英超^埃弗顿^切尔西^3^6^赢^31^24^1^2^-0.25!20140823220000^英超^切尔西^莱切斯特城^2^0^赢^24^59^0^0^1.75$$20141019020500^斯亚甲^马里博尔^佩利根^1^2^输^725^696^1^1^1.25!20141015235900^斯亚甲^哥里卡^马里博尔^0^2^赢^615^725^0^1^-0.75!20141005020500^斯亚甲^马里博尔^奥林比查^3^3^输^725^1295^2^3^0.75!20141001024500^欧冠杯^沙尔克04^马里博尔^1^1^赢^151^725^0^1^1.5!20140927220000^斯亚甲^卡尔塞沃多特姆^马里博尔^0^0^输^17376^725^0^0^-1.5!20140925020500^斯亚甲^路达^马里博尔^0^1^赢^2621^725^0^0^-0.5!20140920220000^斯亚甲^科佩尔^马里博尔^1^2^赢^963^725^1^1^-0.5!20140918024500^欧冠杯^马里博尔^里斯本竞技^1^1^赢^725^466^0^0^-0.5!20140913220000^斯亚甲^马里博尔^科瑞卡^2^0^走^725^8756^2^0^2!20140831020500^斯亚甲^多明萨尔^马里博尔^2^0^输^1296^725^1^0^-0.25$$20141026235900^英超^曼彻斯特联^切尔西^4^27^24!20141029034500^英联杯^谢斯伯利^切尔西^7^1196^24!20141101230000^英超^切尔西^女王公园巡游者^10^24^50$$20141025200000^斯亚甲^扎瓦克^马里博尔^3^8754^725!20141102230000^斯亚甲^马里博尔^多明萨尔^11^725^1296!20141106034500^欧冠杯^马里博尔^切尔西^15^725^24$$車路士^DWWWWW^WLWWWL^馬里博爾^WDDDWL^－－W－－－^信心指数 - 車路士 ★★★★★对赛成绩 - 車路士 0勝 0和 0負，車路士整體實力遠勝馬里博爾，加上其主場威力亦向來十足，今仗主場出擊的車路士取勝無懸念。$$24^725$$7^0^1^87.5%^5^62.5%^3^37.5%!4^0^0^100.0%^2^50.0%^2^50.0%!3^0^1^75.0%^3^75.0%^1^25.0%!输^赢^赢^赢^赢^赢^大^小^大^小^大^大^$$4^1^8^30.8%^7^53.8%^6^46.2%!1^1^5^14.3%^5^71.4%^2^28.6%!3^0^3^50.0%^2^33.3%^4^66.7%!输^赢^输^输^赢^赢^大^小^大^小^小^大^$$球会友谊^20130717210000^切尔西^24^2.25^泰国超级联赛明星队^12213^1^0^输!英足总杯^20130217200000^切尔西^24^2.25^布伦特福德^365^4^0^赢!欧冠杯^20121206034500^切尔西^24^2.25^洛斯查兰特^231^6^1^赢!欧冠杯^20111020024500^切尔西^24^2.25^亨克^147^5^0^赢!英足总杯^20110109230000^切尔西^24^2.25^伊普斯维奇^68^7^0^赢!欧冠杯^20101124034500^切尔西^24^2.25^斯利纳^1031^2^1^输$$$$^^$$1^^切尔西^2^1^1^0^2^1^4!2^^沙尔克04^2^0^2^0^2^2^2!3^^马里博尔^2^0^2^0^2^2^2!4^^里斯本竞技^2^0^1^1^1^2^1";
//		fenxi.analyseSaiShiFenXi(saiShiFenXi);
		
//		shijian http://apk.win007.com/phone/ResultDetail.aspx?ID=965129&lang=0&ran=1405775287655
		String shiJian = "1^1^19^福森晃斗!1^3^22^谷口彰悟!0^1^40^六平光成!1^1^44^小林悠!0^1^50^吉田丰!1^3^64^井川祐辅!0^3^76^D.杰克夫!0^3^85^吉田丰!0^1^90^村田和哉$$0^^*!6^5^5!11^2^2!25^*^!26^^*!29^2^3";
		fenxi.analyseShiJian(shiJian,0l);
		
//		yapei http://apk.win007.com/phone/Handicap.aspx?ID=965129&lang=0&ran=1405780561802
//		daxiao http://apk.win007.com/phone/OverUnder.aspx?ID=965129&lang=0&ran=1405780639480
		String yapei = "ＳＢ^4124073^0.80^-0.5^0.96^0.84^-0.5^0.98!Bet365^4124623^0.825^-0.5^0.975^0.875^-0.5^0.925!韦德^4124294^0.80^-0.5^1.04^0.85^-0.5^0.98!易胜^4124570^0.90^-0.5^1.05^1.00^-0.5^0.95!明陞^4124300^0.85^-0.5^0.99^0.86^-0.5^0.98!10BET^4124284^0.81^-0.5^0.96^0.83^-0.5^0.94!金宝博^4124071^0.80^-0.5^0.96^0.86^-0.5^0.98!12bet/大发^4124303^0.85^-0.5^0.99^0.86^-0.5^0.98!利记^4124139^0.85^-0.5^0.99^0.90^-0.5^0.94!盈禾^4124230^0.81^-0.5^0.96^0.86^-0.5^0.97";
		String daxiao = "ＳＢ^3647777^0.96^2.5^0.80^1.05^2.25^0.75!Bet365^3648345^1.05^2.5^0.75^1.025^2.25^0.775!韦德^3648000^1.05^2.5^0.727^1.25^2.5^0.615!易胜^3648281^1.05^2.5^0.75^0.95^2.25^0.85!明陞^3648011^1.07^2.5^0.75^1.42^2.5^0.52!10BET^3647991^1.03^2.5^0.71^0.88^2.25^0.84!金宝博^3647770^0.96^2.5^0.80^1.07^2.25^0.75!12bet/大发^3648007^1.07^2.5^0.75^1.42^2.5^0.52!利记^3647844^1.07^2.5^0.75^1.00^2.25^0.82!盈禾^3647937^1.04^2.5^0.73^1.01^2.25^0.80";
//		fenxi.analyseYaPei_Daxiao(yapei, 1);
		fenxi.analyseYaPei_Daxiao(daxiao, 1);
	}

	c Q;
	i T;
	// com.bet007.mobile.score.activity.fenxi.c R;
	// com.bet007.mobile.score.h.a.h S;
	// com.bet007.mobile.score.activity.fenxi.i U;
	// com.bet007.mobile.score.activity.fenxi.m V;
	// x W;
	// at X;
	// com.bet007.mobile.score.activity.fenxi.e Y;
	String Z;

	String aa;
	String ab;
	String ac;
	String ad;
	String ae;
	boolean af;
	int ag;
	int ah;
	int ai;
	int aj;
	int ak;
	boolean al;
	boolean am;
	String ans;
	final String ao = "Loading";
	List ap;

	public Zq_FenXi() {
		ai = 0;
		aj = 0x7f07014f;
		ak = 0x1332ccf;
		al = false;
		am = false;
		ans = "http://info.win007.com/image/team/";

	}

	private void A() {
		// com.bet007.mobile.score.common.an.f("ShowNoDataTxt");
		// E.setVisibility(8);
		// w.setVisibility(8);
		// K.setVisibility(0);
		// K.setText(a(0x7f09001c));
	}

	private String a(boolean flag) {
		String s1 = flag ? "主队" : "客队";
		return s1;
	}

//	private void a(int i1, HashMap hashmap) {
//		String s2;
//		if (i1 == 0x7f07014f) {
//			String s4 = (String) hashmap.get(Integer.valueOf(0x7f07014f));
//			if (s4 == "")
//				s4 = "$$";
//			if (hashmap.containsKey(Integer.valueOf(0x7f070023)))
//				s2 = (new StringBuilder())
//						.append(s4)
//						.append("$$")
//						.append((String) hashmap.get(Integer
//								.valueOf(0x7f070023))).toString();
//			else
//				s2 = (new StringBuilder()).append(s4).append("$$")
//						.append("Loading").toString();
//		} else {
//			String s1 = (String) hashmap.get(Integer.valueOf(0x7f070023));
//			if (hashmap.containsKey(Integer.valueOf(0x7f07014f))) {
//				String s3 = (String) hashmap.get(Integer.valueOf(0x7f07014f));
//				if (s3 == "")
//					s3 = "$$";
//				s2 = (new StringBuilder()).append(s3).append("$$").append(s1)
//						.toString();
//			} else {
//				s2 = (new StringBuilder()).append("Loading$$Loading$$")
//						.append(s1).toString();
//			}
//		}
//		n(s2);
//	}

	// static void a(Zq_FenXi zq_fenxi)
	// {
	// zq_fenxi.k();
	// }

	private boolean a(String s1, int i1, int j1, int k1) {
		boolean flag = true;
		String as1[] = s1.split("\\^", -1);
		if (as1.length >= i1) {
			while (j1 <= k1) {
				if (as1[j1].equals("")) {
					flag = false;
					return flag;
				}
				j1++;
			}
		}
		return flag;

	}

	// static void b(Zq_FenXi zq_fenxi)
	// {
	// zq_fenxi.l();
	// }
	//
	// static void c(Zq_FenXi zq_fenxi)
	// {
	// zq_fenxi.m();
	// }

	/**
	 * 解析亚赔,大小分
	 * 
	 * @param s1
	 * @param i1
	 */
	private void analyseYaPei_Daxiao(String s1, int i1) {
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
		// if(arraylist.size() == 1)
		// arraylist.add(new as(true));
		// aq aq1 = new aq(arraylist, this, this, i1, true);
		// E.setAdapter(aq1);
		// Q.b(arraylist1);
		// Q.a(i1);
		System.out.println(arraylist);
	}

	/**
	 * 赔率（纯赔率，提供公司，对阵id）解析 亚赔，欧赔，大小
	 * @param s1
	 * @param i1
	 * @return 
	 */
	public List<QtMatchOpOddsModel> analyseFootIndex(String response,long customBsId,String companyId) {
//		if (response == null) {
//			return null;
//		}
		List<QtMatchOpOddsModel> arrayList = new ArrayList<QtMatchOpOddsModel>();
		String as1[] = response.split("\\!", -1);
		int j1 = 0;
		if(as1.length==1){  
			if(StringUtils.isNotBlank(response)&&!response.contains("^")){  //被屏蔽，重新抓取
				arrayList = null;
			}
		}
		while (j1 < as1.length) {
			String as2[] = as1[j1].split("\\^", -1);
			
			if (as2.length >= 4){
//				arraylist.add(new bk(as2[0], as2[1], as2[2], as2[3]));
				QtMatchOpOddsModel matchOpOddsModel = new QtMatchOpOddsModel();
				matchOpOddsModel.setBsId((int)customBsId);
				matchOpOddsModel.setCorpId(companyId);
				matchOpOddsModel.setProcessStatus(0);
				matchOpOddsModel.setSource(1);
				matchOpOddsModel.setHomeWinOdds(toConvertNotNullDoubleValue(as2[0]));
				matchOpOddsModel.setDrawOdds(toConvertNotNullDoubleValue(as2[1]));//欧赔：平局赔率
				matchOpOddsModel.setHandicap(toConvertNotNullDoubleValue(as2[1]));//亚赔大小：盘口
				matchOpOddsModel.setGuestWinOdds(toConvertNotNullDoubleValue(as2[2]));
				matchOpOddsModel.setTimestamp(DateFormateUtil.toDate("yyyyMMddHHmmss", as2[3]));
				
				arrayList.add(matchOpOddsModel);
			}
				
			j1++;
		}
		return arrayList;
//		w w1 = new w(arraylist, this, true, i1);
//		G.setAdapter(w1);
//		R.a(Q.d());
//		R.notifyDataSetChanged();
	}
	
	private Double toConvertNotNullDoubleValue(String doubleValue) {
		Double double1 = ValueConvertUtil.safeDouble(doubleValue);
		return double1==null?0.0:double1;
	}

//	 private List f()
//	 {
//	 ArrayList arraylist = new ArrayList();
//	 arraylist.add("\u5168\u90E8\u76D8\u53E3");
//	 arraylist.add("\u5F53\u524D\u76D8\u53E3");
//	 arraylist.add("\u6211\u7684\u6295\u6CE8");
//	 return arraylist;
//	 }

	/**
	 * 解析欧赔
	 * 
	 * @param s1
	 */
	private void analyseOuPei(String s1) {
		HashMap hashmap;
		String s3;
		float f1;
		ArrayList arraylist;
		String as1[];
		ArrayList arraylist1;
		int i1;
		float f2;
		float f3;
		float f4;
		float f5;
		float f6;
		float f7;
		float f8;
		int j1;
		float f9;
		float f10;
		float f11;
		float f12;
		float f13;
		float f14;
		float f15;
		float f16;
		float f17;
		float f18;
//		String s2 = Q.f();
		//打印出来的变量
		String s2 = 
		"2^Betfair(英国)^Betfair^0^1!16^10BET(英国)^10BET^1^0!18^12BET(菲律宾)^12bet.com^0^0!33^Redbet(英国)^Redbet^0^1!54^BETDAQ(爱尔兰)^BETDAQ^0^1!70^Expekt(马耳他)^Expekt^1^0!71^Eurobet(英国)^Eurobet^1^0!80^澳门^Macauslot^1^0!81^伟德(直布罗陀)^Vcbet^1^0!82^立博(英国)^Ladbrokes^1^0!88^Coral(英国)^Coral^1^0!90^易胜博(安提瓜和巴布达)^Easybets^1^0!100^Stan James(直布罗陀)^Stan James^1^0!104^Interwetten(塞浦路斯)^Interwetten^1^0!110^SNAI(意大利)^SNAI^1^0!115^威廉希尔(英国)^William Hill^1^0!158^Gamebookers(安提瓜和巴布达)^gamebookers^1^0!177^PinnacleSports(荷兰)^Pinnacle Sports^1^0!255^bwin(奥地利)^bwin^1^0!281^bet 365(英国)^Bet 365^1^0!352^Matchbook(安提瓜和巴布达)^matchbook^0^1!370^Oddset(德国)^Oddset^1^0!386^优胜客(马耳他)^Unibet^1^0!422^博天堂(瓦努阿图)^Sportingbet^1^0!474^利记sbobet(英国)^sbobet^1^0!499^金宝博(马恩岛)^188bet^1^0!517^明陞(菲律宾)^Mansion88.com^1^0!531^WBX(伦敦)^WBX^0^1!545^SB^Crown^1^0!649^IBCBET(哥斯达黎加)^IBCBET^1^0!659^盈禾(菲律宾)^wewbet^0^0";

		hashmap = new HashMap();
		// 10BET^39705385^2.37^3.16^2.67^1.79^3.35^3.92^16!12BET^39749582^2.05^3.20^3.13^1.82^3.28^3.76^18
		if (s2 != null && s2.length() > 0) {
			String as3[] = s2.split("\\!", -1);
			int j2 = 0;
			while (j2 < as3.length) {
				String as4[] = as3[j2].split("\\^", -1);
				if (as4.length >= 5) {
					String s12;
					if (as4[3].equals("1"))
						s12 = "1";
					else if (as4[4].equals("1"))
						s12 = "2";
					else
						s12 = "0";
					hashmap.put(as4[0], s12);
				}
				j2++;
			}
		}
		//s3这个变量代表用户之前选择了的公司id，存储再数据库中，为空代表选择全部公司
//		s3 = ScoreApplication.a(this, "Key_FenXi_OP_Company", "");
		s3 = "";
		f1 = 0.0F;
		arraylist = new ArrayList();
		as1 = s1.split("\\!", -1);
		arraylist1 = new ArrayList();
		i1 = 0;
		f2 = 0.0F;
		f3 = 0.0F;
		f4 = 0.0F;
		f5 = 0.0F;
		f6 = 0.0F;
		f7 = 0.0F;
		f8 = 0.0F;
		j1 = 0;
		f9 = 0.0F;
		f10 = 0.0F;
		f11 = 0.0F;
		f12 = 0.0F;
		f13 = 0.0F;
		f14 = 0.0F;
		f15 = 0.0F;
		f16 = 0.0F;
		f17 = 0.0F;
		f18 = 0.0F;
		int k1 = as1.length;
		while (j1 < k1) {

			String as2[] = as1[j1].split("\\^", -1);
			int i2;
			float f39;
			float f40;
			float f41;
			float f42;
			float f43;
			float f44;
			float f45;
			float f46;
			float f47;
			float f48;
			float f49;
			float f50;
			float f51;
			float f52;
			float f53;
			float f54;
			float f55;
			float f56;
			if (as2.length < 9) {
				i2 = i1;
				f39 = f2;
				f40 = f3;
				f41 = f4;
				f42 = f5;
				f43 = f6;
				f55 = f17;
				f44 = f18;
				f54 = f11;
				f45 = f12;
				f53 = f9;
				f46 = f10;
				f52 = f16;
				f47 = f15;
				f51 = f8;
				f48 = f7;
				float f58 = f13;
				f50 = f14;
				f49 = f58;
			} else if (!s3.equals("")
					&& !((","+s3+",").contains(","+as2[8]+","))) {
				i2 = i1;
				f39 = f2;
				f40 = f3;
				f41 = f4;
				f42 = f5;
				f43 = f6;
				f55 = f17;
				f44 = f18;
				f54 = f11;
				f45 = f12;
				f53 = f9;
				f46 = f10;
				f52 = f16;
				f47 = f15;
				f51 = f8;
				f48 = f7;
				float f57 = f13;
				f50 = f14;
				f49 = f57;
			} else {
				int l1 = i1 + 1;
				float f19 = com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an.c(as2[2]);
				float f20 = com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an.c(as2[3]);
				float f21 = com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an.c(as2[4]);
				float f22 = com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an.c(as2[5]);
				float f23 = com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an.c(as2[6]);
				float f24 = com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an.c(as2[7]);
				// com.bet007.mobile.score.activity.fenxi.x x1;
				float f25;
				float f26;
				float f27;
				float f28;
				float f29;
				float f30;
				float f31;
				float f32;
				float f33;
				float f34;
				float f35;
				float f36;
				float f37;
				float f38;
				String s4;
				String s5;
				String s6;
				String s7;
				String s8;
				String s9;
				String s10;
				String s11;
				boolean flag;
				boolean flag1;
				if (f17 < f19)
					f25 = f19;
				else
					f25 = f17;
				if (f11 < f20)
					f26 = f20;
				else
					f26 = f11;
				if (f9 < f21)
					f27 = f21;
				else
					f27 = f9;
				if (l1 == 1 || f1 > f19)
					f28 = f19;
				else
					f28 = f1;
				if (l1 == 1 || f13 > f20)
					f29 = f20;
				else
					f29 = f13;
				if (l1 == 1 || f7 > f21)
					f7 = f21;
				if (f16 < f22)
					f30 = f22;
				else
					f30 = f16;
				if (f8 < f23)
					f31 = f23;
				else
					f31 = f8;
				if (f14 < f24)
					f32 = f24;
				else
					f32 = f14;
				if (l1 == 1 || f15 > f22)
					f15 = f22;
				if (l1 == 1 || f10 > f23)
					f10 = f23;
				if (l1 == 1 || f12 > f24)
					f12 = f24;
				f33 = f18 + f19;
				f34 = f6 + f20;
				f35 = f5 + f21;
				f36 = f4 + f22;
				f37 = f3 + f23;
				f38 = f2 + f24;
				s4 = as2[0];
				s5 = as2[1];
				s6 = as2[2];
				s7 = as2[3];
				s8 = as2[4];
				s9 = as2[5];
				s10 = as2[6];
				s11 = as2[7];
				if (hashmap.get(as2[8]) != null
						&& ((String) hashmap.get(as2[8])).equals("1"))
					flag = true;
				else
					flag = false;
				if (hashmap.get(as2[8]) != null
						&& ((String) hashmap.get(as2[8])).equals("2"))
					flag1 = true;
				else
					flag1 = false;
				arraylist.add(new z(false, s4, s5, "", "", "", s6, s7, s8, s9,
						s10, s11, flag, flag1));
				arraylist1.add(new e(as2[1], as2[0]));
				i2 = l1;
				f39 = f38;
				f40 = f37;
				f41 = f36;
				f42 = f35;
				f43 = f34;
				f44 = f33;
				f45 = f12;
				f46 = f10;
				f47 = f15;
				f48 = f7;
				f49 = f29;
				f1 = f28;
				f50 = f32;
				f51 = f31;
				f52 = f30;
				f53 = f27;
				f54 = f26;
				f55 = f25;
			}
			j1++;
			i1 = i2;
			f2 = f39;
			f3 = f40;
			f4 = f41;
			f5 = f42;
			f7 = f48;
			f8 = f51;
			f15 = f47;
			f16 = f52;
			f10 = f46;
			f9 = f53;
			f12 = f45;
			f11 = f54;
			f18 = f44;
			f17 = f55;
			f6 = f43;
			f56 = f50;
			f13 = f49;
			f14 = f56;
		}

		if (arraylist.size() == 0)
			arraylist.add(new z(true));
		else
			arraylist.add(
					0,
					new z(true, "", "", "", "", "", an.a(Float.valueOf(f17)),
							an.a(Float.valueOf(f11)), an.a(Float.valueOf(f9)),
							an.a(Float.valueOf(f16)), an.a(Float.valueOf(f8)),
							an.a(Float.valueOf(f14)), an.a(Float.valueOf(f1)),
							an.a(Float.valueOf(f13)), an.a(Float.valueOf(f7)),
							an.a(Float.valueOf(f15)), an.a(Float.valueOf(f10)),
							an.a(Float.valueOf(f12)),
							an.a(Double.valueOf((double) Math
									.round(100F * (f18 / (float) i1)) / 100D)),
							an.a(Double.valueOf((double) Math
									.round(100F * (f6 / (float) i1)) / 100D)),
							an.a(Double.valueOf((double) Math
									.round(100F * (f5 / (float) i1)) / 100D)),
							an.a(Double.valueOf((double) Math
									.round(100F * (f4 / (float) i1)) / 100D)),
							an.a(Double.valueOf((double) Math
									.round(100F * (f3 / (float) i1)) / 100D)),
							an.a(Double.valueOf((double) Math
									.round(100F * (f2 / (float) i1)) / 100D))));
		// x1 = new com.bet007.mobile.score.activity.fenxi.x(arraylist, this,
		// this);
		// E.setAdapter(x1);
		// Q.b(arraylist1);
		// Q.a(3);
		// return;
		System.out.println(arraylist);
	}

	// private void k(String s1)
	// {
	// String as1[] = s1.split("\\!", -1);
	// ArrayList arraylist = new ArrayList();
	// int i1 = 0;
	// while(i1 < as1.length)
	// {
	// String as2[] = as1[i1].split("\\^", -1);
	// if(as2.length >= 3)
	// arraylist.add(new v(as2[0], as2[1]));
	// i1++;
	// }
	// if(arraylist.size() == 0)
	// {
	// A();
	// } else
	// {
	// V = new com.bet007.mobile.score.activity.fenxi.m(arraylist, this, this);
	// E.setAdapter(V);
	// V.notifyDataSetChanged();
	// }
	// }

	/**
	 * 解析盘王
	 * 
	 * @param s1
	 */
	private void analysePanWang(String s1) {
		String as1[] = s1.split("\\#", -1);
		// boolean bool = com.bet007.mobile.score.common.z.d();
		boolean bool = false;
		ArrayList arraylist = new ArrayList();
		if (as1.length < 3) {
			A();
		} else {
			String as2[] = as1[2].split("\\$\\$", -1);
			if (as2.length < 3) {
				A();
			} else {

				String s2 = as2[0];
				String as3[] = as2[1].split("\\!", -1);
				String as4[] = as2[2].split("\\!", -1);
				
				arraylist.add(new l("让球竞猜"));
				for (int i1 = 0; i1 < as3.length; i1++) {
					String as7[] = as3[i1].split("\\^", -1);
					if (as7.length >= 7)
						arraylist.add(new l(1, ag, s2, as7[0], as7[1], as7[2],
								as7[3], as7[4], as7[5], as7[6], as7[7]
										.equals("1"), as7[8].equals("1")));
				}

				boolean flag1;
				int j1;

				boolean flag2;
				int i2;
				if (arraylist.size() == 1) {
					arraylist.add(new l(true));
					flag1 = false;
				} else {
					flag1 = true;
				}
				j1 = arraylist.size();
				arraylist.add(new l("大小竞猜"));

				for (int k1 = 0; k1 < as4.length; k1++)

				{

					String as6[] = as4[k1].split("\\^", -1);
					if (as6.length >= 7)
						arraylist.add(new l(2, ag, s2, as6[0], as6[1], as6[2],
								as6[3], as6[4], as6[5], as6[6], as6[7]
										.equals("1"), as6[8].equals("1")));

				}
				if (arraylist.size() == j1 + 1) {
					arraylist.add(new l(true));
					flag2 = false;
				} else {
					flag2 = true;
				}
				i2 = 0;
				while (i2 < arraylist.size()) {
					l l2 = (l) arraylist.get(i2);
					if (l2.n() == 1 || l2.n() == 2) {
						String s3 = l2.l();
						// if(s3.equals("1") || s3.equals("3"))
						// {
						// Q.a(l2, flag, true, "red");
						// Q.a(l2, flag, false, "gray");
						// } else
						// if(s3.equals("2") || s3.equals("4"))
						// {
						// Q.a(l2, flag, true, "gray");
						// Q.a(l2, flag, false, "blue");
						// } else
						// if(l2.d())
						// {
						// Q.a(l2, flag, true, "white");
						// Q.a(l2, flag, false, "white");
						// } else
						// {
						// Q.a(l2, flag, true, "gray");
						// Q.a(l2, flag, false, "gray");
						// }
					}
					i2++;
				}
				if (!flag1 && !flag2) {
					A();
				} else {
					//暂时注释掉
//					if (an.a()) {
//						String as5[] = ScoreApplication.r.split("\\^", -1);
//						if (as5.length >= 6)
//							arraylist.add(0, new l(true, new a(-1, false,
//									false, as5[0], as5[1], as5[2], as5[3],
//									as5[4], as5[5])));
//					}
					
					// Q.a(arraylist);
					// U = new com.bet007.mobile.score.activity.fenxi.i(Q.a(),
					// this, this);
					// E.setAdapter(U);
					// B();
				}
			}
		}
		System.out.println(arraylist);
	}

	// private void m()
	// {
	// Q.b(aj, this, Z);
	// if(aj == 0x7f07014f)
	// (new Handler()).postDelayed(new bd(this), 100L);
	// }

	/**
	 * 赛事分析
	 * 
	 * @param s1
	 */
	private void analyseSaiShiFenXi(String s1) {
		String optionsArray[] = s1.split("\\$\\$", -1);
		ArrayList arraylist = new ArrayList();
		if (optionsArray.length < 13) {
			// goto _L1
			// _L1:
			A();
		} else {

			// _L2:
			String as2[] = optionsArray[9].split("\\^", -1);
			if (as2.length != 2) {
				// w.setVisibility(8);
				// K.setText(a(0x7f09001c));
				// K.setVisibility(0);
			}
			int i1 = an.b(as2[0]);
			int j1 = an.b(as2[1]);
			// boolean flag = com.bet007.mobile.score.common.z.b();
			boolean flag = false;
			
			/**
			 * 联赛积分排名
			 */
			ArrayList arrayLianSaiJifenPaiMing = new ArrayList();
			arrayLianSaiJifenPaiMing.add(new au(0, false, a(true)));
			String lianSaiJiFenPaiMingString_host[];

			if (flag)
				arrayLianSaiJifenPaiMing.add(new au(0, true, "全場", "賽", "勝",
						"平", "負", "得", "失", "積分", "排名", "勝率"));
			else
				arrayLianSaiJifenPaiMing.add(new au(0, true, "全场", "赛", "胜",
						"平", "负", "得", "失", "积分", "排名", "胜率"));
			lianSaiJiFenPaiMingString_host = optionsArray[0].split("\\!", -1);
			int k1 = 0;
			int l1 = lianSaiJiFenPaiMingString_host.length;
			// 联赛积分排名
			while (k1 < l1) {
				if (a(lianSaiJiFenPaiMingString_host[k1], 10, 1, 9)) {
					String as29[] = lianSaiJiFenPaiMingString_host[k1].split(
							"\\^", -1);
					arrayLianSaiJifenPaiMing.add(new au(0, false, as29[0],
							as29[1], as29[2], as29[3], as29[4], as29[5],
							as29[6], as29[7], as29[8], as29[9]));
				}
				k1++;
			}
			boolean flag1;
			int i2;
			String lianSaiJiFenPaiMingString_guest[];
			int j2;
			boolean flag2;
			ArrayList arrayBeiSaiJiFenPaiMing;
			ArrayList arrayDuiSaiWangJi;
			String as5[];
			int l2;
			int i3;
			int j3;
			int k3;
			int l3;
			int i4;
			ArrayList arrayJinQiZhanJi;
			String jinQiZhanJi_str_host[];
			int j4;
			int k4;
			int l4;
			int i5;
			int j5;
			int k5;
			int l5;
			int i6;
			int j6;
			int k6;
			boolean flag3;
			int l6;
			String jinQiZhanJi_str_guest[];
			int i7;
			int j7;
			int k7;
			int l7;
			int i8;
			int j8;
			int k8;
			int l8;
			int i9;
			int j9;
			boolean flag4;
			ArrayList arraylist5;
			String lianSaiPanLu_str_host[];
			int k9;
			int l9;
			boolean flag5;
			int i10;
			String lianSaiPanLu_str_guest[];
			int j10;
			int k10;
			boolean flag6;
			ArrayList arrayXiangTongLiShiPanKou;
			String xiangTongLiShiPanKou_str_host[];
			int l10;
			int i11;
			boolean flag7;
			int j11;
			String xiangTongLiShiPanKou_str_guest[];
			int k11;
			int l11;
			boolean flag8;
			ArrayList arrayWeiLaiSanChang;
			String weiLaiSanChang_str_host[];
			int i12;
			boolean flag9;
			int j12;
			String weiLaiSanChang_str_guest[];
			int k12;
			boolean flag10;
			ArrayList arrayMeiTiYuCe;
			String as14[];
			ArrayList arrayAoCaiTuiJian;
			String as15[];
			int l12;
			String as16[];
			String as17[];
			int i13;
			String as18[];
			int j13;
			String as19[];
			String s2;
			int k13;
			float f1;
			float f2;
			String as20[];
			String s3;
			int l13;
			float f3;
			float f4;
			String as21[];
			String s4;
			String as22[];
			String s5;
			String as23[];
			float f5;
			float f6;
			int i14 = 0;
			int j14 = 0;
			int k14 = 0;
			int l14 = 0;
			int i15 = 0;
			int j15;
			String s6;
			String s7;
			String s8;
			String s9;
			String s10;
			String s11;
			String s12;
			String s13;
			String s14;
			String s15;
			int k15 = 0;
			int l15 = 0;
			int i16 = 0;
			int j16 = 0;
			int k16 = 0;
			int l16 = 0;
			int i17;
			int j17;
			int k17;
			int l17;
			String as24[];
			float f7;
			float f8;
			int i18 = 0;
			int j18 = 0;
			int k18 = 0;
			int l18 = 0;
			int i19 = 0;
			int j19;
			String s16;
			String s17;
			String s18;
			String s19;
			String s20;
			String s21;
			String s22;
			String s23;
			String s24;
			String s25;
			int k19 = 0;
			int l19 = 0;
			int i20 = 0;
			int j20 = 0;
			int k20 = 0;
			int l20 = 0;
			int i21;
			int j21;
			int k21;
			int l21;
			int i22;
			String as25[];
			int j22;
			float f9;
			float f10;
			int k22;
			int l22;
			int i23;
			String s26;
			String s27;
			String s28;
			String s29;
			String s30;
			String s31;
			String s32;
			String s33;
			String s34;
			String s35;
			int j23;
			int k23;
			int l23;
			int i24;
			int j24;
			int k24;
			int l24;
			int i25;
			if (arrayLianSaiJifenPaiMing.size() == 2) {
				arrayLianSaiJifenPaiMing.add(new au(true));
				flag1 = false;
			} else {
				flag1 = true;
			}
			i2 = arrayLianSaiJifenPaiMing.size();
			arrayLianSaiJifenPaiMing.add(new au(0, false, a(false)));
			if (flag)
				arrayLianSaiJifenPaiMing.add(new au(0, true, "全場", "賽", "勝",
						"平", "負", "得", "失", "積分", "排名", "勝率"));
			else
				arrayLianSaiJifenPaiMing.add(new au(0, true, "全场", "赛", "胜",
						"平", "负", "得", "失", "积分", "排名", "胜率"));
			lianSaiJiFenPaiMingString_guest = optionsArray[1].split("\\!", -1);
			j2 = 0;
			int k2 = lianSaiJiFenPaiMingString_guest.length;
			while (j2 < k2) {
				if (a(lianSaiJiFenPaiMingString_guest[j2], 10, 1, 9)) {
					String as28[] = lianSaiJiFenPaiMingString_guest[j2].split(
							"\\^", -1);
					arrayLianSaiJifenPaiMing.add(new au(0, false, as28[0],
							as28[1], as28[2], as28[3], as28[4], as28[5],
							as28[6], as28[7], as28[8], as28[9]));
				}
				j2++;
			}
			if (arrayLianSaiJifenPaiMing.size() == i2 + 2) {
				flag2 = false;
				arrayLianSaiJifenPaiMing.add(new au(true));
			} else {
				flag2 = true;
			}
			if (flag1 || flag2)
				arraylist.add(new AwdataStore("联赛积分排名", arrayLianSaiJifenPaiMing));
			
			arrayBeiSaiJiFenPaiMing = new ArrayList();
			// 杯赛积分排名
			if (flag)
				arrayBeiSaiJiFenPaiMing.add(new au(8, true, true, "排名", "球隊",
						"總", "勝", "平", "負", "得", "失", "淨", "積分"));
			else
				arrayBeiSaiJiFenPaiMing.add(new au(8, true, true, "排名", "球队",
						"总", "胜", "平", "负", "得", "失", "净", "积分"));
			if (optionsArray.length > 15) {
				String as26[] = optionsArray[15].split("\\!", -1);
				int j25 = 0;
				int k25 = as26.length;
				while (j25 < k25) {

					if (a(as26[j25], 10, 4, 9)) {
						String as27[] = as26[j25].split("\\^", -1);
						arrayBeiSaiJiFenPaiMing.add(new au(8, false, false,
								as27[0], as27[2], as27[3], as27[4], as27[5],
								as27[6], as27[7], as27[8],
								(new StringBuilder())
										.append(an.b(as27[7]) - an.b(as27[8]))
										.append("").toString(), as27[9]));
					}
					j25++;
				}
				if (arrayBeiSaiJiFenPaiMing.size() > 1)
					arraylist.add(new AwdataStore("杯赛积分排名", arrayBeiSaiJiFenPaiMing));
				
			}
			
			arrayDuiSaiWangJi = new ArrayList();
			// 对往赛季
			if (flag)
				arrayDuiSaiWangJi.add(new au(1, true, 0, "日期", "賽事", "主場",
						"客場", "比分", "", "半場", "", "盤路", true, ""));
			else
				arrayDuiSaiWangJi.add(new au(1, true, 0, "日期", "赛事", "主场",
						"客场", "比分", "", "半场", "", "盘路", true, ""));
			as5 = optionsArray[3].split("\\!", -1);
			l2 = 0;
			i3 = 0;
			j3 = 0;
			k3 = 0;
			l3 = 0;
			i4 = as5.length;
			while (l2 < i4) {
				as25 = as5[l2].split("\\^", -1);
				if (as25.length < 11) {
					i24 = l3;
					j23 = i3;
					l23 = k3;
					k23 = j3;
				} else {
					j22 = 0;
					f9 = an.c(as25[4]);
					f10 = an.c(as25[5]);
					if (an.b(as25[8]) == i1) {
						if (f9 > f10) {
							j22 = 1;
							i25 = l3 + 1;
							k22 = j3;
							l22 = k3;
							i23 = i25;
						} else if (f9 < f10) {
							j22 = 3;
							k22 = j3 + 1;
							l22 = k3;
							i23 = l3;
						} else {
							j22 = 2;
							l24 = k3 + 1;
							k22 = j3;
							l22 = l24;
							i23 = l3;
						}
					} else if (an.b(as25[9]) == i1) {
						if (f9 < f10) {
							j22 = 4;
							k24 = l3 + 1;
							k22 = j3;
							l22 = k3;
							i23 = k24;
						} else if (f9 > f10) {
							j22 = 6;
							k22 = j3 + 1;
							l22 = k3;
							i23 = l3;
						} else {
							j22 = 5;
							j24 = k3 + 1;
							k22 = j3;
							l22 = j24;
							i23 = l3;
						}
					} else {
						k22 = j3;
						l22 = k3;
						i23 = l3;
					}
					s26 = as25[0];
					s27 = as25[1];
					s28 = as25[2];
					s29 = as25[3];
					s30 = as25[4];
					s31 = as25[5];
					s32 = as25[6];
					s33 = as25[7];
					s34 = as25[10];
					if (as25.length > 11)
						s35 = as25[11];
					else
						s35 = "";
					arrayDuiSaiWangJi.add(new au(1, false, j22, s26, s27, s28,
							s29, s30, s31, s32, s33, s34, true, s35));
					j23 = i3 + 1;
					k23 = k22;
					l23 = l22;
					i24 = i23;
				}
				l2++;
				i3 = j23;
				j3 = k23;
				k3 = l23;
				l3 = i24;
				// if(false)
				// break MISSING_BLOCK_LABEL_1718;
				// else
				// break MISSING_BLOCK_LABEL_1227;
			}
			if (arrayDuiSaiWangJi.size() > 1) {
				if (i3 != 0)
					i22 = (l3 * 100) / i3;
				else
					i22 = 0;
				if (flag)

					arrayDuiSaiWangJi.add(new au(1, true, "近" + i3 + "場，"
							+ a(true) + " 勝" + l3 + " 平" + k3 + " 負" + j3
							+ "，勝率" + i22 + "%"));
				else
					arrayDuiSaiWangJi.add(new au(1, true, "近" + i3 + "场，"
							+ a(true) + " 胜" + l3 + " 平" + k3 + " 负" + j3
							+ "，胜率" + i22 + "%"));
				arraylist.add(new AwdataStore("对赛往绩", arrayDuiSaiWangJi));
				
			}
			arrayJinQiZhanJi = new ArrayList();
			arrayJinQiZhanJi.add(new au(2, false, a(true)));
			// 近期战绩
			if (flag)
				arrayJinQiZhanJi.add(new au(2, true, 0, "日期", "賽事", "主隊", "客隊",
						"比分", "", "", "", "盤路", ""));
			else
				arrayJinQiZhanJi.add(new au(2, true, 0, "日期", "赛事", "主队", "客队",
						"比分", "", "", "", "盘路", ""));
			jinQiZhanJi_str_host = optionsArray[4].split("\\!", -1);

			k4 = 0;
			l4 = 0;
			i5 = 0;
			j5 = 0;
			k5 = 0;
			l5 = 0;

			i6 = jinQiZhanJi_str_host.length;
			j4 = 0;
			while (j4 < i6) {
				// if(j4 >= i6)
				// break MISSING_BLOCK_LABEL_2639;

				as24 = jinQiZhanJi_str_host[j4].split("\\^", -1);
				j4++;
				if (as24.length >= 11) {

					l20 = l5;
					k19 = k4;
					k20 = k5;
					j20 = j5;
					i20 = i5;
					l19 = l4;
				} else {

					k4 = k19;
					l4 = l19;
					i5 = i20;
					j5 = j20;
					k5 = k20;
					l5 = l20;
					// if(true) goto _L4; else goto _L3
					// _L4:
					// break MISSING_BLOCK_LABEL_1989;
					// _L3:
					f7 = an.c(as24[4]);
					f8 = an.c(as24[5]);
					if (an.b(as24[7]) == i1) {
						if (f7 > f8) {
							l21 = l5 + 1;
							i18 = j5;
							j18 = k5;
							k18 = l21;
							l18 = 1;
						} else if (f7 < f8) {
							i18 = j5 + 1;
							j18 = k5;
							k18 = l5;
							l18 = 3;
						} else {
							k21 = k5 + 1;
							i18 = j5;
							j18 = k21;
							k18 = l5;
							l18 = 2;
						}
					} else if (an.b(as24[8]) == i1) {
						if (f7 < f8) {
							j21 = l5 + 1;
							i18 = j5;
							j18 = k5;
							k18 = j21;
							l18 = 4;
						} else if (f7 > f8) {
							i18 = j5 + 1;
							j18 = k5;
							k18 = l5;
							l18 = 6;
						} else {
							i21 = k5 + 1;
							i18 = j5;
							j18 = i21;
							k18 = l5;
							l18 = 5;
						}
					} else {
						i18 = j5;
						j18 = k5;
						k18 = l5;
						l18 = 0;
					}
				}

				i5++;
				if (!as24[6].equals("") && !as24[6].equals("贏")
						&& !as24[6].equals("赢"))

					i19 = l4 + 1;
				j19 = i5;
				s16 = as24[0];
				s17 = as24[1];
				s18 = as24[2];
				s19 = as24[3];
				s20 = as24[4];
				s21 = as24[5];
				s22 = as24[9];
				s23 = as24[10];
				s24 = as24[6];
				if (as24.length > 11)
					s25 = as24[11];
				else
					s25 = "";
				arrayJinQiZhanJi.add(new au(2, false, l18, s16, s17, s18, s19,
						s20, s21, s22, s23, s24, s25));
				k19 = k4 + 1;
				l19 = i19;
				i20 = j19;
				j20 = i18;
				k20 = j18;
				l20 = k18;
			}
			// if(true) goto _L6; else goto _L5
			// _L5:
			if (arrayJinQiZhanJi.size() == 2) {
				arrayJinQiZhanJi.add(new au(true));
				flag3 = false;
			} else {
				if (k4 != 0)
					j6 = (l5 * 100) / k4;
				else
					j6 = 0;
				if (i5 != 0)
					k6 = (l4 * 100) / i5;
				else
					k6 = 0;
				if (flag) {

					arrayJinQiZhanJi.add(new au(2, true, "近" + k4 + "場， 勝" + l5
							+ " 平" + k5 + " 負" + j5 + "，勝率" + j6 + "%，贏盤率" + k6
							+ "%"));
					flag3 = true;
				} else {

					arrayJinQiZhanJi.add(new au(2, true, "近" + k4 + "场， 胜" + l5
							+ " 平" + k5 + " 负" + j5 + "，胜率" + j6 + "%，赢盘率" + k6
							+ "%"));
					flag3 = true;
				}
			}
			l6 = arrayJinQiZhanJi.size();
			arrayJinQiZhanJi.add(new au(2, false, a(false)));
			if (flag)
				arrayJinQiZhanJi.add(new au(2, true, 0, "日期", "賽事", "主隊", "客隊",
						"比分", "", "", "", "盤路", ""));
			else
				arrayJinQiZhanJi.add(new au(2, true, 0, "日期", "赛事", "主队", "客队",
						"比分", "", "", "", "盘路", ""));
			jinQiZhanJi_str_guest = optionsArray[5].split("\\!", -1);
			i7 = 0;
			j7 = 0;
			k7 = 0;
			l7 = 0;
			i8 = 0;
			j8 = 0;
			k8 = 0;
			l8 = jinQiZhanJi_str_guest.length;
			while (i7 < l8) {
//				System.out.println("添加一遍"+i7);
				// break MISSING_BLOCK_LABEL_3573;
				as23 = jinQiZhanJi_str_guest[i7].split("\\^", -1);
				i7++;
				if (as23.length >= 11) {
					// break; /* Loop/switch isn't completed */
					l16 = k8;
					k15 = j7;
					k16 = j8;
					j16 = i8;
					i16 = l7;
					l15 = k7;
				} else {

					// _L10:

					j7 = k15;
					k7 = l15;
					l7 = i16;
					i8 = j16;
					j8 = k16;
					k8 = l16;
					// if(true) goto _L8; else goto _L7
					// _L8:
					// break MISSING_BLOCK_LABEL_2783;
					// _L7:
					f5 = an.c(as23[4]);
					f6 = an.c(as23[5]);
					if (an.b(as23[7]) == j1) {
						if (f5 > f6) {
							l17 = k8 + 1;
							i14 = i8;
							j14 = j8;
							k14 = l17;
							l14 = 1;
						} else if (f5 < f6) {
							i14 = i8 + 1;
							j14 = j8;
							k14 = k8;
							l14 = 3;
						} else {
							k17 = j8 + 1;
							i14 = i8;
							j14 = k17;
							k14 = k8;
							l14 = 2;
						}
					} else if (an.b(as23[8]) == j1) {
						if (f5 < f6) {
							j17 = k8 + 1;
							i14 = i8;
							j14 = j8;
							k14 = j17;
							l14 = 4;
						} else if (f5 > f6) {
							i14 = i8 + 1;
							j14 = j8;
							k14 = k8;
							l14 = 6;
						} else {
							i17 = j8 + 1;
							i14 = i8;
							j14 = i17;
							k14 = k8;
							l14 = 5;
						}
					} else {
						i14 = i8;
						j14 = j8;
						k14 = k8;
						l14 = 0;
					}

				}
				l7++;
				if (!as23[6].equals("") && !as23[6].equals("贏")
						&& !as23[6].equals("赢"))
					// break MISSING_BLOCK_LABEL_7679;
					i15 = k7 + 1;
				j15 = l7;
				s6 = as23[0];
				s7 = as23[1];
				s8 = as23[2];
				s9 = as23[3];
				s10 = as23[4];
				s11 = as23[5];
				s12 = as23[9];
				s13 = as23[10];
				s14 = as23[6];
				if (as23.length > 11)
					s15 = as23[11];
				else
					s15 = "";
				arrayJinQiZhanJi.add(new au(2, false, l14, s6, s7, s8, s9, s10,
						s11, s12, s13, s14, s15));
				k15 = j7 + 1;
				l15 = i15;
				i16 = j15;
				j16 = i14;
				k16 = j14;
				l16 = k14;
				// if(true) goto _L10; else goto _L9
				// _L9:
				if (arrayJinQiZhanJi.size() == l6 + 2) {
					flag4 = false;
					arrayJinQiZhanJi.add(new au(true));
				} else {
					if (j7 != 0)
						i9 = (k8 * 100) / j7;
					else
						i9 = 0;
					if (l7 != 0)
						j9 = (k7 * 100) / l7;
					else
						j9 = 0;
					if (flag) {
						arrayJinQiZhanJi.add(new au(2, true, "近" + j7 + "場， 勝"
								+ k8 + " 平" + j8 + " 負" + i8 + "，勝率" + i9
								+ "%，贏盤率" + j9 + "%"));
						flag4 = true;
					} else {
						arrayJinQiZhanJi.add(new au(2, true, "近" + j7 + "场， 胜"
								+ k8 + " 平" + j8 + " 负" + i8 + "，胜率" + j9
								+ "%，赢盘率" + j9 + "%"));
						flag4 = true;
					}
				}
				if (flag3 || flag4)
					arraylist.add(new AwdataStore("近期战绩", arrayJinQiZhanJi));
			}
				arraylist5 = new ArrayList();
				arraylist5.add(new au(3, false, a(true)));
				// 联赛盘路走势
				if (flag)
					arraylist5.add(new au(3, true, false, "", "", "全場", "贏盤",
							"走水", "輸盤", "贏盤率", "大球", "大球率", "小球", "小球率"));
				else
					arraylist5.add(new au(3, true, false, "", "", "全场", "赢盘",
							"走水", "输盘", "赢盘率", "大球", "大球率", "小球", "小球率"));
				lianSaiPanLu_str_host = optionsArray[10].split("\\!", -1);
				k9 = 0;
				l9 = lianSaiPanLu_str_host.length;
				while (k9 < l9) {
					as22 = lianSaiPanLu_str_host[k9].split("\\^", -1);
					if (as22.length >= 8) {
						s5 = "";
						if (k9 == 0)
							s5 = "总";
						else if (k9 == 1)
							s5 = "主";
						else if (k9 == 2)
							s5 = "客";
						else if (k9 == 3)
							s5 = "近六";
						if (k9 == 3) {
							if (as22.length >= 12)

								arraylist5
										.add(new au(
												3,
												false,
												true,
												(as22[0] + as22[1] + as22[2]
														+ as22[3] + as22[4] + as22[5])
														.replace(
																"赢",
																"<font color=\""
																		+ an.n("red")
																		+ "\">赢</font>")
														.replace(
																"输",
																"<font color=\""
																		+ an.n("blue")
																		+ "\">输</font>")
														.replace(
																"贏",
																"<font color=\""
																		+ an.n("red")
																		+ "\">贏</font>")
														.replace(
																"輸",
																"<font color=\""
																		+ an.n("blue")
																		+ "\">輸</font>"),
												(as22[6] + as22[7] + as22[8]
														+ as22[9] + as22[10] + as22[11])
														.replace(
																"大",
																"<font color=\""
																		+ an.n("red")
																		+ "\">大</font>")
														.replace(
																"小",
																"<font color=\""
																		+ an.n("blue")
																		+ "\">小</font>"),
												s5, "", "", "", "", "", "", "",
												""));
						} else {
							arraylist5.add(new au(3, false, false, "", "", s5,
									as22[0], as22[1], as22[2], as22[3],
									as22[4], as22[5], as22[6], as22[7]));
						}
					}
					k9++;
				}
				if (arraylist5.size() == 2) {
					arraylist5.add(new au(true));
					flag5 = false;
				} else {
					flag5 = true;
				}
				i10 = arraylist5.size();
				arraylist5.add(new au(3, false, a(false)));
				if (flag)
					arraylist5.add(new au(3, true, false, "", "", "全場", "贏盤",
							"走水", "輸盤", "贏盤率", "大球", "大球率", "小球", "小球率"));
				else
					arraylist5.add(new au(3, true, false, "", "", "全场", "赢盘",
							"走水", "输盘", "赢盘率", "大球", "大球率", "小球", "小球率"));
				lianSaiPanLu_str_guest = optionsArray[11].split("\\!", -1);
				j10 = 0;
				k10 = lianSaiPanLu_str_guest.length;
				while (j10 < k10) {
					as21 = lianSaiPanLu_str_guest[j10].split("\\^", -1);
					if (as21.length >= 8) {
						s4 = "";
						if (j10 == 0)
							s4 = "总";
						else if (j10 == 1)
							s4 = "主";
						else if (j10 == 2)
							s4 = "客";
						else if (j10 == 3)
							s4 = "近六";
						if (j10 == 3) {
							if (as21.length >= 12)

								arraylist5
										.add(new au(
												3,
												false,
												true,
												(as21[0] + as21[1] + as21[2]
														+ as21[3] + as21[4] + as21[5])
														.replace(
																"赢",
																"<font color=\""
																		+ an.n("red")
																		+ "\">赢</font>")
														.replace(
																"输",
																"<font color=\""
																		+ an.n("blue")
																		+ "\">输</font>")
														.replace(
																"贏",
																"<font color=\""
																		+ an.n("red")
																		+ "\">贏</font>")
														.replace(
																"輸",
																"<font color=\""
																		+ an.n("blue")
																		+ "\">輸</font>"),
												(as21[6] + as21[7] + as21[8]
														+ as21[9] + as21[10] + as21[11])
														.replace(
																"大",
																"<font color=\""
																		+ an.n("red")
																		+ "\">大</font>")
														.replace(
																"小",
																"<font color=\""
																		+ an.n("blue")
																		+ "\">小</font>"),
												s4, "", "", "", "", "", "", "",
												""));
						} else {
							arraylist5.add(new au(3, false, false, "", "", s4,
									as21[0], as21[1], as21[2], as21[3],
									as21[4], as21[5], as21[6], as21[7]));
						}
					}
					j10++;
				}
				if (arraylist5.size() == i10 + 2) {
					flag6 = false;
					arraylist5.add(new au(true));
				} else {
					flag6 = true;
				}
				if (flag5 || flag6)
					arraylist.add(new AwdataStore("联赛盘路走势", arraylist5));
				
				arrayXiangTongLiShiPanKou = new ArrayList();
				arrayXiangTongLiShiPanKou.add(new au(4, false, a(true)));
				// 相同历史盘口
				if (flag)
					arrayXiangTongLiShiPanKou.add(new au(4, true, 0, "賽事",
							"日期", "上盤", "", "初盤", "下盤", "", "比分", "", "盤路", 0));
				else
					arrayXiangTongLiShiPanKou.add(new au(4, true, 0, "赛事",
							"日期", "上盘", "", "初盘", "下盘", "", "比分", "", "盘路", 0));
				xiangTongLiShiPanKou_str_host = optionsArray[12].split("\\!",
						-1);
				l10 = 0;
				i11 = xiangTongLiShiPanKou_str_host.length;
				while (l10 < i11) {

					as20 = xiangTongLiShiPanKou_str_host[l10].split("\\^", -1);
					if (as20.length >= 10) {
						s3 = as20[9]
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
												+ "\">輸</font>");

						l13 = 0;
						f3 = an.c(as20[7]);
						f4 = an.c(as20[8]);
						if (an.b(as20[3]) == i1) {
							if (f3 > f4)
								l13 = 1;
							else if (f3 < f4)
								l13 = 3;
							else
								l13 = 2;
						} else if (an.b(as20[6]) == i1)
							if (f3 < f4)
								l13 = 4;
							else if (f3 > f4)
								l13 = 6;
							else
								l13 = 5;
						arrayXiangTongLiShiPanKou.add(new au(4, false, 0,
								as20[0], as20[1], as20[2], as20[3], as20[4],
								as20[5], as20[6], as20[7], as20[8], s3, l13));
					}
					l10++;
				}
				if (arrayXiangTongLiShiPanKou.size() == 2) {
					arrayXiangTongLiShiPanKou.add(new au(true));
					flag7 = false;
				} else {
					flag7 = true;
				}
				j11 = arrayXiangTongLiShiPanKou.size();
				arrayXiangTongLiShiPanKou.add(new au(4, false, a(false)));
				if (flag)
					arrayXiangTongLiShiPanKou.add(new au(4, true, 0, "賽事",
							"日期", "上盤", "", "初盤", "下盤", "", "比分", "", "盤路", 0));
				else
					arrayXiangTongLiShiPanKou.add(new au(4, true, 0, "赛事",
							"日期", "上盘", "", "初盘", "下盘", "", "比分", "", "盘路", 0));
				xiangTongLiShiPanKou_str_guest = optionsArray[13].split("\\!",
						-1);
				k11 = 0;
				l11 = xiangTongLiShiPanKou_str_guest.length;

				while (k11 < l11) {

					as19 = xiangTongLiShiPanKou_str_guest[k11].split("\\^", -1);
					if (as19.length >= 10) {
						s2 = as19[9]
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
												+ "\">輸</font>");
						k13 = 0;
						f1 = an.c(as19[7]);
						f2 = an.c(as19[8]);
						if (an.b(as19[3]) == j1) {
							if (f1 > f2)
								k13 = 1;
							else if (f1 < f2)
								k13 = 3;
							else
								k13 = 2;
						} else if (an.b(as19[6]) == j1)
							if (f1 < f2)
								k13 = 4;
							else if (f1 > f2)
								k13 = 6;
							else
								k13 = 5;
						arrayXiangTongLiShiPanKou.add(new au(4, false, 0,
								as19[0], as19[1], as19[2], as19[3], as19[4],
								as19[5], as19[6], as19[7], as19[8], s2, k13));
					}
					k11++;
				}
				if (arrayXiangTongLiShiPanKou.size() == j11 + 2) {
					flag8 = false;
					arrayXiangTongLiShiPanKou.add(new au(true));
				} else {
					flag8 = true;
				}
				if (flag7 || flag8)
					arraylist.add(new AwdataStore("相同历史盘口", arrayXiangTongLiShiPanKou));
				
				arrayWeiLaiSanChang = new ArrayList();
				arrayWeiLaiSanChang.add(new au(5, false, a(true)));
				// 未来三场
				if (flag)
					arrayWeiLaiSanChang.add(new au(5, true, 0, "日期", "賽事",
							"主隊", "客隊", "間隔"));
				else
					arrayWeiLaiSanChang.add(new au(5, true, 0, "日期", "赛事",
							"主队", "客队", "间隔"));
				weiLaiSanChang_str_host = optionsArray[6].split("\\!", -1);
				i12 = 0;
				while (i12 < weiLaiSanChang_str_host.length) {
					as18 = weiLaiSanChang_str_host[i12].split("\\^", -1);
					if (as18.length >= 5) {
						j13 = 0;
						if (an.b(as18[5]) == i1)
							j13 = 1;
						else if (an.b(as18[6]) == i1)
							j13 = 2;
						arrayWeiLaiSanChang.add(new au(5, false, j13, as18[0],
								as18[1], as18[2], as18[3], as18[4]));
					}
					i12++;
				}
				if (arrayWeiLaiSanChang.size() == 2) {
					arrayWeiLaiSanChang.add(new au(true));
					flag9 = false;
				} else {
					flag9 = true;
				}
				j12 = arrayWeiLaiSanChang.size();
				arrayWeiLaiSanChang.add(new au(5, false, a(false)));
				if (flag)
					arrayWeiLaiSanChang.add(new au(5, true, 0, "日期", "賽事",
							"主隊", "客隊", "間隔"));
				else
					arrayWeiLaiSanChang.add(new au(5, true, 0, "日期", "赛事",
							"主队", "客队", "间隔"));
				weiLaiSanChang_str_guest = optionsArray[7].split("\\!", -1);
				k12 = 0;
				while (k12 < weiLaiSanChang_str_guest.length) {
					as17 = weiLaiSanChang_str_guest[k12].split("\\^", -1);
					if (as17.length >= 5) {
						i13 = 0;
						if (an.b(as17[5]) == j1)
							i13 = 1;
						else if (an.b(as17[6]) == j1)
							i13 = 2;
						arrayWeiLaiSanChang.add(new au(5, false, i13, as17[0],
								as17[1], as17[2], as17[3], as17[4]));
					}
					k12++;
				}
				if (arrayWeiLaiSanChang.size() == j12 + 2) {
					flag10 = false;
					arrayWeiLaiSanChang.add(new au(true));
				} else {
					flag10 = true;
				}
				if (flag9 || flag10)
					arraylist.add(new AwdataStore("未来三场", arrayWeiLaiSanChang));
				if (optionsArray.length > 14) {
					arrayMeiTiYuCe = new ArrayList();
					if (flag)
						arrayMeiTiYuCe.add(new au(7, true, "足彩大贏家", "足彩大富翁",
								"蘋果日報"));
					else
						arrayMeiTiYuCe.add(new au(7, true, "足彩大赢家", "足彩大富翁",
								"苹果日报"));
					as14 = optionsArray[14].split("\\^", -1);
					if (as14.length >= 3
							&& (!as14[0].trim().equals("")
									|| !as14[1].trim().equals("") || !as14[2]
									.trim().equals("")))
						arrayMeiTiYuCe.add(new au(7, false, as14[0], as14[1],
								as14[2]));
					if (arrayMeiTiYuCe.size() > 1)
						arraylist.add(new AwdataStore("媒体预测", arrayMeiTiYuCe));
				}
				arrayAoCaiTuiJian = new ArrayList();
				if (flag)
					arrayAoCaiTuiJian
							.add(new au(6, true, "球隊", "近期走勢", "盤路輸贏"));
				else
					arrayAoCaiTuiJian
							.add(new au(6, true, "球队", "近期走势", "盘路输赢"));
				as15 = optionsArray[8].split("\\!", -1);
				l12 = 0;
				while (l12 < as15.length) {
					as16 = as15[l12].split("\\^", -1);
					if (as16.length >= 7) {
						arrayAoCaiTuiJian.add(new au(6, false, as16[0],
								as16[1], as16[2]));
						arrayAoCaiTuiJian.add(new au(6, false, as16[3],
								as16[4], as16[5]));
						arrayAoCaiTuiJian.add(new au(6, false, as16[6]));
					}
					l12++;
				}
				if (arrayAoCaiTuiJian.size() > 1)
					arraylist.add(new AwdataStore("澳彩推荐", arrayAoCaiTuiJian));
				
			}
			// if(arraylist.size() > 0)
			// {
			// w.setVisibility(0);
			// X = new at(arraylist, this, this);
			// w.setAdapter(X);
			// w();
			// } else
			// {
			// w.setVisibility(8);
			// K.setText(a(0x7f09001c));
			// K.setVisibility(0);
			// }
			// if(true) goto _L12; else goto _L11
			// _L11:
			// i15 = k7;
			// j15 = l7;
			// break MISSING_BLOCK_LABEL_3276;
			// i19 = l4;
			// j19 = i5;
			// break MISSING_BLOCK_LABEL_2342;
//		}
		System.out.println("数据个数"+arraylist.size());
	}

	/**
	 * 解析事件
	 * 
	 * @param s1
	 */
//	private void analyseShiJian(String s1) {
//		ArrayList arraylist3 = null;
//		if (!s1.equals("$$$$Loading") && !s1.equals("Loading$$Loading$$")) {
//			String as1[] = s1.split("\\$\\$", -1);
//			if (as1.length >= 3) {
//				// goto _L3
//				// _L3:
//				ArrayList arraylist = new ArrayList();
//				ArrayList arraylist1 = new ArrayList();
//				ArrayList arraylist2 = new ArrayList();
//				boolean flag;
//				boolean flag1;
//				boolean flag2;
//				boolean flag3 = false;
//				flag2 = true;
//				flag1 = true;
//
//				String as3[];
//				int j1;
//				String as4[];
//				int k1;
//				int l1;
//				int i2;
//				int j2;
//				int k2;
//				int l2;
//				String s2;
//				String s3;
//				int i3;
//				int j3;
//				int k3;
//				String as5[];
//				String as6[];
//				int l3;
//
//				if (as1[0].equals("Loading")) {
//					arraylist.add(new fenxi.f(true, 2));
//					arraylist1.add(new fenxi.f(true, 2));
//					
//				} else {
//					// 比赛事件
//					String biSaiShiJian[] = as1[0].split("\\!", -1);
//					int i1 = 0;
//					while (i1 < biSaiShiJian.length) {
//						String as7[] = biSaiShiJian[i1].split("\\^", -1);
//						if (as7.length >= 4) {
//							boolean flag4;
//							if (an.b(as7[0]) == 1)
//								flag4 = true;
//							else
//								flag4 = false;
//							arraylist.add(new f(0, flag4, as7[1], as7[2],
//									as7[3]));
//						}
//						i1++;
//					}
//					if (arraylist.size() == 0) {
//
//						if (ag == 0)
//							flag = false;
//						else
//							flag = true;
//						for (l3 = 0; l3 < 3; l3++)
//							arraylist.add(new f(0, true, "", "", ""));
//
//					} else {
//						flag = true;
//					}
//					arraylist.add(new f(true, 1));
//					// 赛事技术统计
//					String saiShiJiShuTongJi[] = as1[1].split("\\!", -1);
//					int j21 = 0;
//					while (j21 < saiShiJiShuTongJi.length) {
//						String jinQi[] = saiShiJiShuTongJi[j21]
//								.split("\\^", -1);
//						if (jinQi.length >= 3)
//							arraylist1.add(new f(1, jinQi[1], jinQi[0],
//									jinQi[2]));
//						j21++;
//					}
//					if (arraylist1.size() == 0) {
//						arraylist1.add(new f(true));
//						flag1 = false;
//						flag2 = flag;
//					} else {
//						flag1 = true;
//						flag2 = flag;
//					}
//
//				}
//
//				// 半全场胜负统计
//				if (!as1[2].equals("Loading")) {
//
//					String banQuanChang[] = as1[2].split("\\!", -1);
//					int k11 = 0;
//					while (k11 < banQuanChang.length) {
//						String as51[] = banQuanChang[k11].split("\\^", -1);
//						if (as51.length >= 4)
//							arraylist2.add(new f(2, (new StringBuilder())
//									.append(k11).append("").toString(),
//									as51[0], as51[1], as51[2], as51[3]));
//						k11++;
//					}
//					if (arraylist2.size() == 0) {
//						flag3 = false;
//						arraylist2.add(new f(true));
//					} else {
//						l1 = 0;
//						i2 = 0;
//						j2 = 0;
//						k2 = 0;
//						l2 = 0;
//						while (j2 < arraylist2.size()) {
//							i3 = k2 + an.b(((f) arraylist2.get(j2)).n());
//							l2 += an.b(((f) arraylist2.get(j2)).o());
//							j3 = i2 + an.b(((f) arraylist2.get(j2)).p());
//							k3 = l1 + an.b(((f) arraylist2.get(j2)).q());
//							j2++;
//							l1 = k3;
//							i2 = j3;
//							k2 = i3;
//						}
//						s2 = "主场";
//						s3 = "客场";
//
//						arraylist2.add(0, new f(2, "-1", s2 + "(" + k2 + ")",
//								s3 + "(" + l2 + ")", s2 + "(" + i2 + ")", s3
//										+ "(" + l1 + ")"));
//						flag3 = true;
//					}
//				} else {
//
//					// goto _L5
//					if (flag2 || flag1) {
//						arraylist2.add(new f(true, 2));
//						flag3 = true;
//					}
//				}
//
//				if (flag2 || flag1 || flag3) {
//
//					arraylist3 = new ArrayList();
//					if (flag2)
//						arraylist3.add(new aw("比赛事件", arraylist));
//					if (flag1)
//						arraylist3.add(new aw("赛事技术统计", arraylist1));
//					if (flag3)
//						arraylist3.add(new aw("半/全场胜负统计（本赛季）", arraylist2));
//					// Y = new
//					// com.bet007.mobile.score.activity.fenxi.e(arraylist3,
//					// this);
//					// w.setAdapter(Y);
//					// x();
//				}
//
//			} else {
//				// goto _L4;
//
//			}
//
//		}
//			System.out.println(arraylist3);
//	}
	
	/**
	 * 
	 * 解析事件
	 * @param s1
	 * @param customBsId 自定义的比赛id
	 */
	public ArrayList analyseShiJian(String s1,long customBsId)
    {
		 ArrayList arraylist =null;
         ArrayList arraylist1 = null;
         ArrayList arraylist2 = null;
         boolean flag1 =  false;
         boolean flag2 = false;
         boolean flag3 = false;
         ArrayList arraylist3 = null;
         
         boolean flag;
         String as3[];
         int j1;
         String as4[];
         int k1;
         int l1;
         int i2;
         int j2;
         int k2;
         int l2;
         String s2;
         String s3;
         int i3;
         int j3;
         int k3;
         String as5[];
         String as6[];
        if(!s1.equals("$$$$Loading") && !s1.equals("Loading$$Loading$$")){
//        	goto _L2;
        	
        	
//        	_L2:
                String as1[] = s1.split("\\$\\$", -1);
                if(as1.length >=2){                	
//                	goto _L3
                	
//                	_L3:
                       arraylist = new ArrayList<>();
                       arraylist1 = new ArrayList<>();
                       arraylist2 = new ArrayList<>();
                        arraylist3 = new ArrayList<>();
                        
                        if(as1[0].equals("Loading"))
                        {
                            arraylist.add(new f(true, 2));
                            arraylist1.add(new f(true, 2));
                            flag2 = true;
                            flag1 = true;
                        } else
                        {
                            String as2[] = as1[0].split("\\!", -1);
                            int i1 = 0;
                            while(i1 < as2.length) 
                            {
                                String as7[] = as2[i1].split("\\^", -1);
                                if(as7.length >= 4)
                                {
                                    QtMatchEventModel qtMatchEventModel = new QtMatchEventModel();
                                    qtMatchEventModel.setBsId((int)customBsId);
                                    qtMatchEventModel.setTeamType(an.b(as7[0]));
                                    qtMatchEventModel.setEventType(an.b(as7[1]));
                                    qtMatchEventModel.setMinute(as7[2]);
                                    qtMatchEventModel.setEventDetail(as7[3]);
                                    qtMatchEventModel.setProcessStatus(0);
                                    qtMatchEventModel.setSource(1);
                                    arraylist.add(qtMatchEventModel);
                                }
                                i1++;
                            }
                            if(arraylist.size() == 0)
                            {
                                
                                int l3;
                                if(ag == 0)
                                    flag = false;
                                else
                                    flag = true;
//                                for(l3 = 0; l3 < 3; l3++)
//                                    arraylist.add(new f(0, true, "", "", ""));

                            } else
                            {
                                flag = true;
                            }
//                            arraylist.add(new f(true, 1));
                            if (as1.length > 1) {
                            	as3 = as1[1].split("\\!", -1);
                                j1 = 0;
                                while(j1 < as3.length) 
                                {
                                    as6 = as3[j1].split("\\^", -1);
                                    if(as6.length >= 3){
//                                        arraylist1.add(new f(1, as6[1], as6[0], as6[2]));
                                        QtMatchStatisticModel  qtMatchStatisticModel = new QtMatchStatisticModel();
                                        qtMatchStatisticModel.setBsId((int)customBsId);
                                        qtMatchStatisticModel.setEventType(an.b(as6[0]));
                                        qtMatchStatisticModel.setZd(as6[1]);
                                        qtMatchStatisticModel.setKd(as6[2]);
                                        qtMatchStatisticModel.setProcessStatus(0);
                                        qtMatchStatisticModel.setSource(1);
                                        arraylist1.add(qtMatchStatisticModel);
                                    }
                                    j1++;
                                }
                                if(arraylist1.size() == 0)
                                {
//                                    arraylist1.add(new f(true));
                                    flag1 = false;
                                    flag2 = flag;
                                } else
                                {
                                    flag1 = true;
                                    flag2 = flag;
                                }	
							}
                            
                        }
                        if (2< as1.length) {
						
                        if(!as1[2].equals("Loading")){
                                as4 = as1[2].split("\\!", -1);
                                k1 = 0;
                                while(k1 < as4.length) 
                                {
                                    as5 = as4[k1].split("\\^", -1);
                                    if(as5.length >= 4)
                                        arraylist2.add(new f(2, (new StringBuilder()).append(k1).append("").toString(), as5[0], as5[1], as5[2], as5[3]));
                                    k1++;
                                }
                                if(arraylist2.size() == 0)
                                {
                                    flag3 = false;
                                    arraylist2.add(new f(true));
                                } else
                                {
                                    l1 = 0;
                                    i2 = 0;
                                    j2 = 0;
                                    k2 = 0;
                                    l2 = 0;
                                    while(j2 < arraylist2.size()) 
                                    {
                                        i3 = k2 + an.b(((f)arraylist2.get(j2)).n());
                                        l2 += an.b(((f)arraylist2.get(j2)).o());
                                        j3 = i2 + an.b(((f)arraylist2.get(j2)).p());
                                        k3 = l1 + an.b(((f)arraylist2.get(j2)).q());
                                        j2++;
                                        l1 = k3;
                                        i2 = j3;
                                        k2 = i3;
                                    }
                                    s2 = "主场";
                                    s3 = "客场";
                                    arraylist2.add(0, new f(2, "-1", (new StringBuilder()).append(s2).append("(").append(k2).append(")").toString(), (new StringBuilder()).append(s3).append("(").append(l2).append(")").toString(), (new StringBuilder()).append(s2).append("(").append(i2).append(")").toString(), (new StringBuilder()).append(s3).append("(").append(l1).append(")").toString()));
                                    flag3 = true;
                                }
                        	
                        }  else{
                                if(flag2 || flag1){
                                	arraylist2.add(new f(true, 2));
                                    flag3 = true;
                                }
                        } 
                      }
                	
                } 
        }  else{
        } 

        if(flag2 || flag1 || flag3)
        {
            
            if(flag2)
                arraylist3.add(new AwdataStore("比赛事件", arraylist));
            if(flag1)
                arraylist3.add(new AwdataStore("赛事技术统计", arraylist1));
            
        } else
        {
        	
        }
        return arraylist3;
    }
	
	

	/**
	 * 
	 * 解析阵容 1 摩斯^2 穆斯卡特^18 西格蒙德^19 杜伊里^22 杜兰特^4 博尼瓦西亚^8 戈林^13 维达尔^9 尼頓賓斯^10
	 * 麦克格林奇^11 布罗基 $$ 20 刘易斯^5 伯克萨尔^16 芬顿^17 利亚^21 克里什纳 $$ 1 胡哥域^6 久比奇^19
	 * J.瑞斯顿^21 杰米森^23 菲华迪^5 格里菲斯^10 马林高域^11 R.加西亚^17 尼克尔森^7 赫尔西^9 科奥格 $$ 18
	 * 邓肯^13 费雷拉^3 布兰登奥内尔^14 哈罗德^15 麦克拉伦$$$$$$$$
	 * 
	 * @param s1
	 */
	public QtMatchLineupModel analyseZhenRong(String s1,long customBsId) {

//		ArrayList<QtMatchLineupModel> analyseData = new ArrayList<QtMatchLineupModel>();
		QtMatchLineupModel qtMatchLineupModel = new QtMatchLineupModel();
		String as1[] = s1.split("\\$\\$", -1);
		if (as1.length < 4) {
			// A.setVisibility(8);
			// B.setVisibility(8);
			// J.setText(a(0x7f090020));
			// J.setVisibility(0);
			if(s1.length()!=0){
				qtMatchLineupModel = null;
			}
		} else {
			
			qtMatchLineupModel.setBsId(customBsId);
			qtMatchLineupModel.setProcessStatus(0);
			qtMatchLineupModel.setSource(1);
			
			 if(as1.length >= 1 && !as1[0].trim().equals(""))
			 {
				 qtMatchLineupModel.setZdsf(as1[0]);
//			 主队首发阵容
//				 textview.setText(as1[0].replace("^", "\n").trim());
			 }
			 
			 if(as1.length >= 2 && !as1[1].trim().equals(""))
			 {
				 qtMatchLineupModel.setZdtb(as1[1]);
//			 主队替补阵容
//			 textview1.setText(as1[1].replace("^", "\n").trim());
			 }
			 
			 if(as1.length >= 3 && !as1[2].trim().equals(""))
			 {
				 qtMatchLineupModel.setKdsf(as1[2]);
//			 客队首发阵容
//			 textview4.setText(as1[2].replace("^", "\n").trim());
			 }
			 
			 if(as1.length >= 4 && !as1[3].trim().equals(""))
			 {
				 qtMatchLineupModel.setKdtb(as1[3]);
//客队替补阵容
//			 textview5.setText(as1[3].replace("^", "\n").trim());
			 }
			 
			 if(as1.length >= 5 && !as1[4].trim().equals(""))
			 {
				 qtMatchLineupModel.setZds(as1[4]);
				 //主队伤兵
//			 textview2.setText(as1[4].replace("^", "\n").trim());
			 }
			 
			 if(as1.length >= 6 && !as1[5].trim().equals(""))
			 {
				 qtMatchLineupModel.setKds(as1[5]);
				 //客队伤兵
//			 textview6.setText(as1[5].replace("^", "\n").trim());
			 } 
			 
			 if(as1.length >= 7 && !as1[6].trim().equals(""))
			 {
				 qtMatchLineupModel.setZdt(as1[6]);
				 //主队停赛
//			 textview3.setText(as1[6].replace("^", "\n").trim());
			 }
			 
			 if(as1.length >= 8 && !as1[7].trim().equals(""))
			 {
				 qtMatchLineupModel.setKdt(as1[7]);
				//客队停赛
//			 textview7.setText(as1[7].replace("^", "\n").trim());
			 }
		}
		
		return qtMatchLineupModel;
	}

	public List<OddsBaseModel> analyseOddsAndCompanId(String responseStr, Qt_fb_match_oddsType oddsType) {
		List<OddsBaseModel> oddsBaseModels = null;
		
		
		if(StringUtils.isNotBlank(responseStr)&&!responseStr.contains("^")){
			
		}else {
			String[] as = responseStr.split("\\!");
			oddsBaseModels = new ArrayList<>();
			int i=0;
			if (oddsType == Qt_fb_match_oddsType.euro) {
				for (String odss : as) {
					String[] oddsArray = odss.split("\\^");

					if (oddsArray.length >= 9) {
						OddsBaseModel oddsBaseModel = new OddsBaseModel();
						oddsBaseModel.setCorpId(oddsArray[8]);
						oddsBaseModel.setOddsId(oddsArray[1]);
						oddsBaseModels.add(oddsBaseModel);
					}

				}
			} else if (oddsType == Qt_fb_match_oddsType.asia || oddsType == Qt_fb_match_oddsType.ou) {
				for (String odss : as) {
					String[] oddsArray = odss.split("\\^");

					if (oddsArray.length >= 8) {
						OddsBaseModel oddsBaseModel = new OddsBaseModel();
						oddsBaseModel.setOddsId(oddsArray[1]);
						oddsBaseModel.setCorpName(oddsArray[0]);
						oddsBaseModels.add(oddsBaseModel);
					}

				}
			}
		}
		return oddsBaseModels;
	}

	public List<QtMatchOpOddsModel> analyseJishiHistoryOdds(String responseStr) {
		List<QtMatchOpOddsModel> opOddsModels = null;
		if(StringUtils.isNotBlank(responseStr)&&!responseStr.contains("^")){
			
		}else{
			int j1 = 0;
			opOddsModels = new ArrayList<>();
			String as1[] = responseStr.split("\\!");
			while (j1 < as1.length) {
				String as2[] = as1[j1].split("\\^", -1);
				
				if (as2.length >= 4){
//					arraylist.add(new bk(as2[0], as2[1], as2[2], as2[3]));
					QtMatchOpOddsModel matchOpOddsModel = new QtMatchOpOddsModel();
					matchOpOddsModel.setProcessStatus(0);
					matchOpOddsModel.setSource(1);
					matchOpOddsModel.setHomeWinOdds(toConvertNotNullDoubleValue(as2[0]));
					matchOpOddsModel.setDrawOdds(toConvertNotNullDoubleValue(as2[1]));//欧赔：平局赔率
					matchOpOddsModel.setHandicap(toConvertNotNullDoubleValue(as2[1]));//亚赔大小：盘口
					matchOpOddsModel.setGuestWinOdds(toConvertNotNullDoubleValue(as2[2]));
					matchOpOddsModel.setTimestamp(DateFormateUtil.toDate("yyyyMMddHHmmss", as2[3]));
					
					opOddsModels.add(matchOpOddsModel);
				}
					
				j1++;
			}
		}
		return opOddsModels;
	}

	public List<QtMatchOpOddsModel> analyseJishiOdds(String responseStr, Qt_fb_match_oddsType oddsType) {
		List<QtMatchOpOddsModel> opOddsModels = null;
		if(StringUtils.isNotBlank(responseStr)&&!responseStr.contains("^")){
			
		}else{
			opOddsModels = new ArrayList<>();
			//判断该赔率是否已经存在
			String content = getTheLastResponseFromFile(responseStr, oddsType);
			if (!StringUtils.equals(content,responseStr)) {
				int j1 = 0;
				
				String as1[] = responseStr.split("\\!");
				while (j1 < as1.length) {
					String as2[] = as1[j1].split("\\^", -1);

					if (as2.length >= 5) {
						// arraylist.add(new bk(as2[0], as2[1], as2[2],
						// as2[3]));
						QtMatchOpOddsModel matchOpOddsModel = new QtMatchOpOddsModel();
						matchOpOddsModel.setProcessStatus(0);
						matchOpOddsModel.setSource(1);
						matchOpOddsModel.setQtBsId(as2[0]);
						
						matchOpOddsModel.setCorpId(as2[1]);
						
						if(oddsType==Qt_fb_match_oddsType.asia||oddsType==Qt_fb_match_oddsType.ou){
							matchOpOddsModel
								.setHomeWinOdds(toConvertNotNullDoubleValue(as2[3]));
							matchOpOddsModel
								.setDrawOdds(toConvertNotNullDoubleValue(as2[2]));// 欧赔：平局赔率
							matchOpOddsModel
									.setHandicap(toConvertNotNullDoubleValue(as2[2]));// 亚赔大小：盘口
							matchOpOddsModel
									.setGuestWinOdds(toConvertNotNullDoubleValue(as2[4]));
						}else{
							matchOpOddsModel
								.setHomeWinOdds(toConvertNotNullDoubleValue(as2[2]));
							matchOpOddsModel
									.setDrawOdds(toConvertNotNullDoubleValue(as2[3]));// 欧赔：平局赔率
							matchOpOddsModel
									.setHandicap(toConvertNotNullDoubleValue(as2[3]));// 亚赔大小：盘口
							matchOpOddsModel
									.setGuestWinOdds(toConvertNotNullDoubleValue(as2[4]));
						}
						matchOpOddsModel.setTimestamp(new Date());

						opOddsModels.add(matchOpOddsModel);
					}

					j1++;
				}
			}
		}
		return opOddsModels;
	}

	/**
	 * @param responseStr
	 * @param oddsType
	 * @return
	 * @throws IOException
	 */
	private String getTheLastResponseFromFile(String responseStr,
			Qt_fb_match_oddsType oddsType) {
		String fileName = "";
		if(oddsType==Qt_fb_match_oddsType.euro){
			fileName = SystemPropertiesUtil.getPropsValue("euroFile");
		}else if(oddsType==Qt_fb_match_oddsType.asia){
			fileName = SystemPropertiesUtil.getPropsValue("asianFile");
		}else if(oddsType==Qt_fb_match_oddsType.ou){
			fileName = SystemPropertiesUtil.getPropsValue("ouFile");
		}
		File file = FileUtils.getFile(Zq_FenXi.class.getResource("/").getPath()+fileName);
		String content= "";
		try {
			if (file.exists()) {
				content = FileUtils.readFileToString(file);
			} else {
				file.createNewFile();
			}
			if (StringUtils.isNotBlank(responseStr)) {
				FileUtils.write(file, responseStr);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}

	public FbMatchOpOddsInfoPO analyseJishiHistoryOdds(
			String responseStr, Long bsId, String companyId) {
		List<QtMatchOpOddsModel> opOddsModels = null;
		FbMatchOpOddsInfoPO matchOpOddsInfoPO = null;
		if(StringUtils.isNotBlank(responseStr)&&!responseStr.contains("^")){
			
		}else{
			int j1 = 0;
			opOddsModels = new ArrayList<>();
			matchOpOddsInfoPO = new FbMatchOpOddsInfoPO();
			String as1[] = responseStr.split("\\!");
			StringBuilder euroOdds = new StringBuilder("");
			StringBuilder changeTime = new StringBuilder("");
			StringBuilder kellyIndex = new StringBuilder("");
			while (j1 < as1.length) {
				String as2[] = as1[j1].split("\\^", -1);
				if (as2.length >= 8){
					euroOdds.append(as2[0]).append(",").append(as2[1]).append(",").append(as2[2]).append(",").append(as2[4]).append("!");
					changeTime.append(as2[3]+",");
					kellyIndex.append(as2[5]).append(",").append(as2[6]).append(",").append(as2[7]+"!");
				}
					
				j1++;
			}
			matchOpOddsInfoPO.setEuroOdds(euroOdds.toString());
			matchOpOddsInfoPO.setChangeTime(changeTime.toString());
			matchOpOddsInfoPO.setKellyIndex(kellyIndex.toString());
		}
		return matchOpOddsInfoPO;
	}

	public List<QtMatchOpOddsModel> analyseOddsDetails(String responseStr,
			long id, String corpId) {
		List<QtMatchOpOddsModel> opOddsModels = null;
		FbMatchOpOddsInfoPO matchOpOddsInfoPO = null;
		if(StringUtils.isNotBlank(responseStr)&&!responseStr.contains("^")){
			
		}else{
			opOddsModels = new ArrayList<QtMatchOpOddsModel>();
			String[] odds = StringUtils.split(responseStr,"!");
			for(String oddsData:odds){
				String[] data = StringUtils.split(oddsData,"\\^");
				if(data.length>=4){
					QtMatchOpOddsModel qtMatchOpOddsModel = new QtMatchOpOddsModel();
					qtMatchOpOddsModel.setBsId(Integer.valueOf(String.valueOf(id)));
					qtMatchOpOddsModel.setCorpId( corpId);
					qtMatchOpOddsModel.setTimestamp(DateFormateUtil.toDate("yyyyMMddHHmmss", data[3]));
					qtMatchOpOddsModel.setHomeWinOdds(toConvertNotNullDoubleValue(data[0]));
					qtMatchOpOddsModel.setHandicap(toConvertNotNullDoubleValue(data[1]));
					qtMatchOpOddsModel.setDrawOdds(toConvertNotNullDoubleValue(data[1]));
					qtMatchOpOddsModel.setGuestWinOdds(toConvertNotNullDoubleValue(data[2]));
					opOddsModels.add(qtMatchOpOddsModel);
				}
			}
		}
		return opOddsModels;
	}

	// private void x()
	// {
	// int i1 = 0;
	// if(an.a())
	// {
	// String as1[] = ScoreApplication.s.split("\\^", -1);
	// if(as1.length >= 6)
	// {
	// ArrayList arraylist = new ArrayList();
	// arraylist.add(new f(true, new com.bet007.mobile.score.i.a(-1, false,
	// false, as1[0], as1[1], as1[2], as1[3], as1[4], as1[5])));
	// Aw Aw1 = new Aw("", arraylist);
	// Y.f.add(0, Aw1);
	// }
	// }
	// for(; i1 < Y.getGroupCount(); i1++)
	// w.expandGroup(i1);
	//
	// Y.notifyDataSetChanged();
	// }

	// private List y()
	// {
	// ArrayList arraylist = new ArrayList();
	// if(an.a())
	// {
	// String as1[] = ScoreApplication.l.split("\\!", -1);
	// int i1 = 0;
	// while(i1 < as1.length)
	// {
	// String as2[] = as1[i1].split("\\^", -1);
	// if(as2.length >= 6)
	// {
	// com.bet007.mobile.score.i.a a1 = new com.bet007.mobile.score.i.a(-1,
	// false, false, as2[0], as2[1], as2[2], as2[3], as2[4], as2[5]);
	// ArrayList arraylist1 = new ArrayList();
	// arraylist1.add(new au(true, a1));
	// arraylist.add(new aw("", arraylist1));
	// }
	// i1++;
	// }
	// }
	// return arraylist;
	// }

	//
	// public void a(String s1)
	// {
	// if(!s1.equals("SUCCESS")) goto _L2; else goto _L1
	// _L1:
	// String s2 = Q.e();
	// if(s2 != null && !s2.equals("")) goto _L3; else goto _L2
	// _L2:
	// return;
	// _L3:
	// String as1[] = s2.split("\\^", -1);
	// if(as1.length >= 13)
	// {
	// if(!as1[8].equals(""))
	// a(a, (new StringBuilder()).append(an).append(as1[8]).toString());
	// if(!as1[9].equals(""))
	// a(b, (new StringBuilder()).append(an).append(as1[9]).toString());
	// }
	// if(true) goto _L2; else goto _L4
	// _L4:
	// }

	// public void a(String s1, String s2, String s3, int i1, String s4)
	// {
	// o();
	// if(!s1.equals("100")) goto _L2; else goto _L1
	// _L1:
	// g(s2);
	// if(i1 != 13) goto _L4; else goto _L3
	// _L3:
	// String as3[] = s3.split("\\$\\$", -1);
	// if(as3.length == 4)
	// if(s4.equals("1"))
	// {
	// boolean flag = as3[3].equals("1");
	// View view = LayoutInflater.from(this).inflate(0x7f03013b, null);
	// CheckedTextView checkedtextview =
	// (CheckedTextView)view.findViewById(0x7f070438);
	// checkedtextview.setText("\u8BBE\u7F6E\u4E3A\u91CD\u70B9\u7ADE\u731C");
	// checkedtextview.setTextColor(Color.parseColor(com.bet007.mobile.score.common.an.n("tip")));
	// TextView textview = (TextView)view.findViewById(0x7f0702ae);
	// textview.setText("\u60A8\u786E\u5B9A\u63D0\u4EA4\u6B64\u7ADE\u731C\u7ED3\u679C\u5417\uFF1F");
	// textview.setTextColor(Color.parseColor(com.bet007.mobile.score.common.an.n("tip")));
	// checkedtextview.setOnClickListener(new bg(this, checkedtextview));
	// int k1;
	// if(flag)
	// k1 = 0;
	// else
	// k1 = 8;
	// checkedtextview.setVisibility(k1);
	// (new h(this)).a(view).a(a(0x7f0900d1), new bh(this, as3,
	// checkedtextview)).a(a(0x7f0900d2), null).a().show();
	// } else
	// {
	// Q.a(as3[1], as3[2]);
	// U.notifyDataSetChanged();
	// }
	// _L10:
	// return;
	// _L4:
	// if(i1 == 2)
	// {
	// String as1[] = s3.split("\\$\\$", -1);
	// if(as1.length < 2)
	// continue; /* Loop/switch isn't completed */
	// String as2[] = as1[1].split("\\^", -1);
	// if(as2.length < 2)
	// continue; /* Loop/switch isn't completed */
	// if(com.bet007.mobile.score.d.b.a() != null)
	// com.bet007.mobile.score.d.b.a().a(com.bet007.mobile.score.common.an.d(as2[1]));
	// List list = T.b();
	// int j1 = 0;
	// do
	// {
	// label0:
	// {
	// if(j1 < list.size())
	// {
	// s s5 = (s)list.get(j1);
	// if(s5.t() == null || !s5.t().equals(as1[0]))
	// break label0;
	// s5.b(as2[0]);
	// }
	// W.notifyDataSetChanged();
	// continue; /* Loop/switch isn't completed */
	// }
	// j1++;
	// } while(true);
	// } else
	// {
	// if(i1 == 9)
	// {
	// am = true;
	// l();
	// H.setVisibility(0);
	// T.a(s4, s3);
	// W = new x(T.b(), this, this);
	// H.setAdapter(W);
	// W.notifyDataSetChanged();
	// }
	// continue; /* Loop/switch isn't completed */
	// }
	// _L2:
	// if(s1.equals("101"))
	// {
	// a(com/bet007/mobile/score/activity/guess/LoginActivity);
	// continue; /* Loop/switch isn't completed */
	// }
	// if(!s1.equals("105")) goto _L6; else goto _L5
	// _L5:
	// ScoreApplication.b(this, "Key_Guess_ZD_Date",
	// com.bet007.mobile.score.common.an.b((new
	// SimpleDateFormat("yyyyMMdd")).format(new Date())));
	// _L8:
	// b(s1, s2);
	// continue; /* Loop/switch isn't completed */
	// _L6:
	// if(!s1.equals("102")) goto _L8; else goto _L7
	// _L7:
	// h h1 = new h(this);
	// if(s2.equals(""))
	// s2 = null;
	// h1.b(s2).a(a(0x7f0900d1), new bi(this)).a().show();
	// if(true) goto _L10; else goto _L9
	// _L9:
	// }

	// public void b(String s1)
	// {
	// if(ag == 0)
	// i("比赛未开始，暂无视频数据");
	// else
	// if(s1.trim().equals(""))
	// {
	// i("暂无视频数据");
	// } else
	// {
	// Intent intent = new Intent("android.intent.action.VIEW");
	// intent.setData(Uri.parse(s1));
	// startActivity(intent);
	// }
	// }

	// public void b(String s1, int i1)
	// {
	// Q.a(this, s1, i1, true);
	// }

	//
	// public void c(String s1)
	// {
	// if(s1.equals("SUCCESS"))
	// j((String)Q.g().get(Integer.valueOf(0x7f070153)));
	// }

	// 欧赔选择返回
	// protected void onActivityResult(int i1, int j1, Intent intent)
	// {
	// super.onActivityResult(i1, j1, intent);
	// if(i1 == ak)
	// {
	// j((String)Q.g().get(Integer.valueOf(0x7f070153)));
	// Q.a(this, this);
	// }
	// }

}
