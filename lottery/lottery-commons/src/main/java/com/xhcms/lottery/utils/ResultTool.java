package com.xhcms.lottery.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.VoucherType;
import com.xhcms.lottery.lang.VoucherUserStatus;

public final class ResultTool {

	private static final Logger logger = LoggerFactory.getLogger(ResultTool.class); 

    private static final char[] opt310 = {'负', '平', ' ', '胜'}; 
    private static final char[] opt21 = {' ', '胜', '负'}; 
    private static final char[] optLC21 = {' ', '大', '小'};
  
	private static String[] opt310_rq={"让球负", "让球平", " ", "让球胜"};
    
    private static char value(char x, char[] opts){
        return opts[x - '0'];
    }
    
    private static String[] toArr(String odds) {
        if(StringUtils.isEmpty(odds)){
            return null;
        }
        return odds.split(",");
    }
    
    /**
     * 乘以百分数
     * @param num
     * @param per
     * @return
     */
    public BigDecimal mulPer(Object num,Object per){
    	return new BigDecimal(num.toString()).multiply(NumberUtils.percent(Integer.parseInt(per.toString()))).setScale(2, RoundingMode.DOWN);
    }
    /**
	 * 相乘
	 * @param a
	 * @param b
	 * @return
	 */
	public static int mul(int a,int b){
		return a*b;
	}
	
	/**
	 * 相除
	 * @param a
	 * @param b
	 * @return
	 */
	public static int div(int a,int b){
		return a/b;
	}
	
	/**
	 * 计算方案进度
	 * @param s
	 * @return
	 */
	public static int  progress(BetScheme s){
		int progress=100;
		if(s.getStatus()==EntityStatus.TICKET_SCHEME_FLOW  || s.getSaleStatus()==EntityStatus.SCHEME_ON_SALE){
			progress=s.getPurchasedAmount()*100/s.getTotalAmount();
		}
		return progress;
	}
	
	public static int  progress(int status, int saleSttus, int purchasedAmount, int totalAmount) {
		int progress=100;
		if(status==EntityStatus.TICKET_SCHEME_FLOW  || saleSttus==EntityStatus.SCHEME_ON_SALE){
			progress=purchasedAmount*100/totalAmount;
		}
		return progress;
	}
	
    /**
     * 该场比赛是否过关
     * @param matchContainsResult 方案中的一场比赛
     * @param scheme 方案
     * @return true 过关
     */
	public static boolean isMatchWin(PlayMatch matchContainsResult, BetScheme scheme) {
    	if (matchContainsResult == null || scheme == null){
    		return false;
    	}
    	return isMatchWin(matchContainsResult, scheme.getPlayId());
    }
    
    public static boolean isResultInBetCodeEveryTwoLetters(String result, String betCode) {
    	if(betCode==null || result==null || betCode.length() == 0){
    		return false;
    	}
    	if(betCode.length()<2 || betCode.length()%2 != 0){
    		logger .error("结果串长度不是2的整数倍。");
    		return false;
    	}
    	for (int i=0; i<betCode.length(); ){
    		String singleBetOption = betCode.substring(i, i+2);
    		if (singleBetOption.equals(result)){
    			return true;
    		}
    		i+=2;
    	}
		return false;
	}

	public static boolean isResultInBetCode(String result, String betCode){
    	if(betCode==null || result==null){
    		return false;
    	}
    	return betCode.contains(result);
    }
    
    /**
     * 把连续的投注信息按玩法拆分重数组	0010--> 00,10
     * @param playId
     * @param code
     * @return
     */
	public String[] toCodeArray(String playId, String code) {
		String[] arr = new String[code.length()];
		int step = 1;
		if (playId.startsWith(Constants.PLAY_02_ZC) || playId.startsWith(Constants.PLAY_04_ZC)
				|| playId.startsWith(Constants.PLAY_08_LC)) {
			step = 2;
			arr = new String[code.length() / step];
		}
		//北京单场上下单双
		if(playId.startsWith(Constants.PLAY_03_BD_SXDS)){
			
			step = 2;
			arr = new String[code.length() / step];
	
		}

		for (int i = 0, j = 0; i < code.length(); i += step, j++) {
			arr[j] = code.substring(i, i + step);
		}
		return arr;
	}
    
    
    /**
     * 构造显示用的中文选项值和赔率串。
     * TODO: 需要重构，去掉重复的ResultTool类，并加入新玩法竞彩足球不让球胜平负。
     * 
     * @param playId    玩法
     * @param v         选项值
     * @param o         赔率
     * @return
     */
    public static String cn(String playId, String v, String o) {
        if(StringUtils.isEmpty(v)){
            return "";
        }
        StringBuilder sb = new StringBuilder(v.length() * 4);
        
        switch(Integer.parseInt(playId.substring(0, 2))){
            case 1:
                zc01_rq(sb, v, o);
                break;
            case 2:
                zc02(sb, v, o);
                break;
            case 3:
                zc03(sb, v, o);
                break;
            case 4:
                zc04(sb, v, o);
                break;
            case 6:
            	lc06(sb, v, o);
            	break;
            case 7:
                lc07(sb, v, o);
                break;
            case 8:
                lc08(sb, v, o);
                break;
            case 9:
                lc09(sb, v, o);
                break;
            case 24:
            	ct24_25_26(sb, v);
            	break;
            case 25:
            	ct24_25_26(sb, v);
            	break;
            case 26:
            	ct24_25_26(sb, v);
            	break;
            case 27:
            	ct27(sb, v);
            	break;
            case 80:
            	zc01(sb, v, o);
            	break;
            case 91:   //胜负平
            	 zc01(sb, v, o);
            	break;
            case 92:   //进球数
            	zc03(sb, v, o);
            	break;
            case 93:   //上下单上
            	zc95(sb,v,o);
            	break;
            case 94:   //比分
            	zc02(sb, v, o);
            	break;
            case 95:   //半全场
                zc04(sb, v, o);
            	break; 
            case 96:   //胜负
            	zc96(sb,v,o);
            	break;
        }

        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    /**14场胜平负、任9、6场半全*/
    private static void ct24_25_26(StringBuilder sb, String v) {
    	for (int i = 0, j = v.length(); i < j; i++) {
    		if(v.charAt(i) == '*' || v.charAt(i) == '-' ){
    			continue;
    		}
    		sb.append(value(v.charAt(i), opt310) + " ");
    	}
    }
    
    /**4场进球*/
    private static void ct27(StringBuilder sb, String v) {
    	for (int i = 0, j = v.length(); i < j; i++) {
    		sb.append(" " + v.charAt(i) + (v.charAt(i)=='3'?"+球":"球"));
    	}
    }
    
    // 竞彩足球胜平负
    private static void zc01(StringBuilder sb, String v, String o) {
        for (int i = 0, j = v.length(); i < j; i++) {
            sb.append(value(v.charAt(i), opt310));
            appendOdd(sb, toArr(o), i);
        }
    }
   
    // 竞彩足球比分
    private static void zc02(StringBuilder sb, String v, String o) {
        if(v.length() % 2 != 0){
            return;
        }
        char x, y;
        for(int i = 0, j = v.length(); i < j; ){
            x = v.charAt(i++);
            y = v.charAt(i++);
            if(x == '9' || y == '9'){
                if(x == y){
                    sb.append("平其他");
                }else if(x == '9'){
                    sb.append("胜其他");
                }else{
                    sb.append("负其他");
                }
            }else{
                sb.append(x).append(':').append(y);
            }
            appendOdd(sb, toArr(o), (i-1)/2);
        }
    }
    
    // 竞彩足球总进球数
    private static void zc03(StringBuilder sb, String v, String o) {
        char x;
        for(int i = 0, j = v.length(); i < j; ){
            x = v.charAt(i++);
            sb.append(x);
            if(x == '7'){
                sb.append('+');
            }
            sb.append("球");
            appendOdd(sb, toArr(o), i-1);
        }
    }
    
    // 竞彩足球胜负半全
    private static void zc04(StringBuilder sb, String v, String o) {
        if(v.length() % 2 != 0){
            return;
        }
        for(int i = 0, j = v.length(); i < j; ){
            sb.append(value(v.charAt(i++), opt310));
            sb.append(value(v.charAt(i++), opt310));
            appendOdd(sb, toArr(o), (i-1)/2);
        }
    }
    
    // 竞彩篮球让分胜负
    private static void lc06(StringBuilder sb, String v, String o) {
        for(int i = 0, j = v.length(); i < j; i++){
            sb.append("让分主").append(value(v.charAt(i), opt21));
            appendOdd(sb, toArr(o), i);
        }
    }
    
    // 竞彩篮球胜负
    private static void lc07(StringBuilder sb, String v, String o) {
        for(int i = 0, j = v.length(); i < j; i++){
            sb.append("主").append(value(v.charAt(i), opt21));
            appendOdd(sb, toArr(o), i);
        }
    }
    
    // 竞彩篮球胜分差
    private static void lc08(StringBuilder sb, String v, String o) {
        for(int i = 0, j = v.length(); i < j; ){
            if(v.charAt(i++) == '0'){
                sb.append("主胜");
            }else{
                sb.append("客胜");
            }
            switch (v.charAt(i++)) {
                case '1':
                    sb.append("1-5分");
                    break;
                case '2':
                    sb.append("6-10分");
                    break;
                case '3':
                    sb.append("11-15分");
                    break;
                case '4':
                    sb.append("16-20分");
                    break;
                case '5':
                    sb.append("21-25分");
                    break;
                case '6':
                    sb.append("26+分");
                    break;
            }
            appendOdd(sb, toArr(o), (i - 1) / 2);
        }
    }
    
    // 竞彩篮球大小分
    private static void lc09(StringBuilder sb, String v, String o) {
        for(int i = 0, j = v.length(); i < j; i++){
            sb.append(value(v.charAt(i), optLC21));
            appendOdd(sb, toArr(o), i);
        }
    }
    //北京单场上下半场
    private static void zc95(StringBuilder sb, String v, String o){
    	 if(v.length()<= 0){
             return;
         }
    	 char x,y;
         for(int i = 0, j = v.length(); i < j; ){
             x = v.charAt(i++);
             y = v.charAt(i++);
             sb.append(changeTo(x+""+y));
             appendOdd(sb, toArr(o), (i-1)/2);
         }
    	
    }
    //胜负
    private static void zc96(StringBuilder sb, String v, String o){
    	
    	   for (int i = 0, j = v.length(); i < j; i++) {
               sb.append(value(v.charAt(i), opt310));
               appendOdd(sb, toArr(o), i);
           }
    	
    	
    }
    //北京单场单双
    private static String changeTo(String v){
    	
    	String str="";
    	if("01".equals(v)){
    	     str="下单";
    		
    	}else if("02".equals(v)){
    		str="下双";
    		
    	}else if("11".equals(v)){
    		
    		str="上单";
    	}else if("12".equals(v)){
    		
    		str="上双";
    	}
    	return str;
    }
    
    private static void appendOdd(StringBuilder sb, String[] odds, int index){
        if(odds != null){
            sb.append('(').append(odds[index]).append(')');
        }
        sb.append(',');
    }
    
    //根据赛事编号取得是否胆码
    public String ctzcBetSeed(Long matchId, List<CTBetContent> cTBetContents) {
    	String seedStr = "";
    	if(null != cTBetContents && cTBetContents.size() > 0) {
    		String seed = cTBetContents.get(0).getSeed();
    		if(seed != null && seed.length() > 0) {
    			String[] seeds = seed.split(",");
        		Map<String, String> map = new HashMap<String, String>();
        		for(int i=0; i<seeds.length; i++) {
        			map.put(seeds[i], seeds[i]);
        		}
        		if(map.containsKey(String.valueOf(matchId-1))) {
        			seedStr = "胆";
        		}
    		}
    	}
    	return seedStr;
    }
    
    /**
     * 计算目标时间相对于当前时间的剩余时间
     * @param date
     * @return
     */
    public int computeRemainingTime(Date date){
    	// 目标时间
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(date);
    	int targerDay = calendar.get(Calendar.DAY_OF_YEAR);
    	// 当前时间
    	Calendar calendarNow=Calendar.getInstance();
        calendarNow.setTime(new Date());
        int nowDay = calendarNow.get(Calendar.DAY_OF_YEAR);
    	return targerDay-nowDay;
    }
    
    /**
     * 返回优惠劵状态
     * @param value
     * @return
     */
    public String voucherStatus(int value){
    	return VoucherUserStatus.getVoucherName(value);
    }
    
    /**
     * 返回优惠劵类型
     * @param value
     * @return
     */
    public String voucherType(String value){
    	for(VoucherType voucherType:VoucherType.values()){
    		if(voucherType.name().equals(value)){
    			return voucherType.getName();
    		}
    	}
    	return "";
    }
    
    /**
     * 渲染每场比赛的投注内容
     * 格式为从上倒下选项数量递增
     * @param playMatch
     * @return
     */
    public String rendBetMatchContent(BetMatch betMatch){
    	PlayMatch playMatch = new PlayMatch();
     	if(betMatch instanceof PlayMatch){
    		playMatch  = (PlayMatch)betMatch;
    	} else {
    		return "";
    	}
    	String[] odds = playMatch.getOdds().split(",");
    	String[] playIds = playMatch.getPlayId().split(",");
    	String[] options = playMatch.getBetOptions().split(",");
    	if(odds.length != playIds.length || options.length != playIds.length){
    		logger.error("赔率、玩法和选项数量不一致！");
    		return "";
    	}
    	Map<String,Integer> playCount = new HashMap<String, Integer>();
    	Map<String,StringBuilder> playOptionView = new HashMap<String, StringBuilder>();
    	for (int i = 0; i < playIds.length; i++) {
    		String pId = playIds[i];
    		String opt = options[i];
    		String odd = odds[i];
			if(null == playOptionView.get(pId)){
				playOptionView.put(pId, new StringBuilder());
				playCount.put(pId, 1);
				playOptionView.get(pId).append("　"+getOptionViewBybet(pId, opt, odd,playMatch));
			} else {
				Integer count = playCount.get(pId);
				count = count + 1;
				playCount.put(pId, count);
				playOptionView.get(pId).append("　"+getOptionViewBybet(pId, opt, odd,playMatch));
			}
		}
    	playCount = MapUtil.sortByValue(playCount);
    	StringBuilder stringBuilder = new StringBuilder();
    	for (String key : playCount.keySet()) {
    		stringBuilder.append(playOptionView.get(key).toString());
    		stringBuilder.append("<br/>");
		}
    	return stringBuilder.toString();
    }
    
    private String getOptionViewBybet(String pId,String opt,String odd,PlayMatch playMatch){
    	String concedePoints = playMatch.getConcedePoints();
    	String halfscore=playMatch.getScore1();
    	String score=playMatch.getScore();
    	int status=playMatch.getStatus();
    	float defaultScore=playMatch.getDefaultScore();
    	boolean isPass = false;
    	if(StringUtils.isNotBlank(score)){
    		if(-1 != pId.indexOf("06_LC")) {
    			isPass = this.calculateIsPass(pId,opt,score,halfscore,String.valueOf(defaultScore));
    		} else {
    			isPass = this.calculateIsPass(pId,opt,score,halfscore,concedePoints);
    		}
    	}
    	String returnVal="";
    	if(-1 != pId.indexOf("01_ZC") 
    			|| -1 != pId.indexOf("06_LC")
    			|| -1 != pId.indexOf("09_LC")){
    		if(-1 != pId.indexOf("06_LC")){
    			float a;
    			a = defaultScore;
    			returnVal = (a > 0 ? "+"+a:a) + opt + (StringUtils.isNotBlank(odd) ? ("@" + odd):"");
    		} else if(-1 != pId.indexOf("09_LC")){
    			float a;
    			try {
	    			a = Float.parseFloat(concedePoints);
	    			returnVal = (a > 0 ? "+"+a:a) + opt + (StringUtils.isNotBlank(odd) ? ("@" + odd):"");
				} catch (NumberFormatException e) {
					logger.error("格式化数据错误！{}",concedePoints,e);
				}
    		} else {
    			try {
	    			int b = (int)Float.parseFloat(concedePoints);
	    			returnVal = (b > 0 ? "+"+b:b) + opt + (StringUtils.isNotBlank(odd) ? ("@" + odd):"");
    			} catch (NumberFormatException e) {
    				logger.error("格式化数据错误！{}",concedePoints,e);
				}	
    		}
    	} else {
    		returnVal = opt + (StringUtils.isNotBlank(odd) ? ("@" + odd):"");
    	}
    	if(isPass && status==EntityStatus.MATCH_OVER){
    		return "<i style=\"color:red\">"+ returnVal + "</i>";
    	} else {
    		return returnVal;
    	}
    }
    
    private String getSpfOptViewByScore(int score1,int score2){
		if(score1 > score2){
			return "胜";
		} else if(score1 == score2){
			return "平";
    	} else {
    		return "负";
    	}
	}
    
    private boolean calculateIsPass(String pId,String opt,String score,String halfscore,String concedePoints){
    	boolean isPass = false;
    	if(StringUtils.isBlank(score) || score.indexOf(":") == -1){
    		return isPass;
    	}
    	String[] tmpScores = score.split(":");
    	int[] scores = new int[2];
    	scores[0]=Integer.parseInt(tmpScores[0]);
    	scores[1]=Integer.parseInt(tmpScores[1]);
    	
    	String[] tmpHalfscores = halfscore.split(":");
    	int[] halfscores = new int[2];
    	halfscores[0]=Integer.parseInt(tmpHalfscores[0]);
    	halfscores[1]=Integer.parseInt(tmpHalfscores[1]);
    	if(-1 != pId.indexOf("01_ZC") || -1 != pId.indexOf("80_ZC")){
    		if(-1 != pId.indexOf("01_ZC")){
    			scores[0]=scores[0]+(int)Float.parseFloat(concedePoints);
    		} else {
    			scores[0]=scores[0];
    		}
			if(opt.equals(getSpfOptViewByScore(scores[0],scores[1]))){
				isPass=true;
			}
    	} else if(-1 != pId.indexOf("02_ZC")){//比分
    		if(opt.equals(score)){
    			isPass = true;
    		}
    	} else if(-1 != pId.indexOf("03_ZC")){
    		if((opt.charAt(0)+"").equals((scores[0]+scores[1])+"")){
    			isPass = true;
    		}
    	} else if(-1 != pId.indexOf("04_ZC")){
    		String val = getSpfOptViewByScore(halfscores[0],halfscores[1])+
    				  getSpfOptViewByScore(scores[0],scores[1]);
    		
    		if(opt.equals(val)){
    			isPass=true;
    		}
    	} else if(-1 != pId.indexOf("06_LC") ||
    				-1 != pId.indexOf("07_LC")){
    		String preFix = "";
    		float a;
    		if(-1 != pId.indexOf("06_LC")){
    			a=Float.parseFloat(""+scores[1])+Float.parseFloat(concedePoints);
    			preFix = "让分";
    		} else {
    			a=scores[1];
    		}
    		if(opt.equals((preFix+(a>scores[0]?"主胜":"主负")))){
    			isPass=true;
    		}
    	} else if(-1 != pId.indexOf("08_LC")){
    		String val = scores[1]>scores[0]?"主胜":"客胜";
    		int difference = Math.abs(scores[0]-scores[1]);
    		int pre = difference - difference%5 + 1;
    		int next = pre + 4;
    		String result = val+(next>=26?"26+":(pre+"-"+next))+"分";
    		if(opt.equals(result)){
    			isPass=true;
    		}
    	} else if(-1 != pId.indexOf("09_LC")){
    		if(opt.equals(((scores[0]+scores[1]) > Float.parseFloat(concedePoints)?"大":"小"))){
    			isPass=true;
    		}
    	}
    	return isPass;
    }

	public static String cn4Client(String playId, String betCode, String odds, String concedePoints, float defaultScore) {
		if(StringUtils.isEmpty(betCode)){
            return "";
        }
        StringBuilder sb = new StringBuilder(betCode.length() * 4);
        
        switch(Integer.parseInt(playId.substring(0, 2))){
            case 1:
                zc01_rq_client(sb, betCode, odds,concedePoints);
                break;
            case 2:
                zc02_client(sb, betCode, odds);
                break;
            case 3:
                zc03_client(sb, betCode, odds);
                break;
            case 4:
                zc04_client(sb, betCode, odds);
                break;
            case 6:
            	lc06_client(sb, betCode, odds,concedePoints);
            	break;
            case 7:
                lc07_client(sb, betCode, odds);
                break;
            case 8:
                lc08_client(sb, betCode, odds);
                break;
            case 9:
                lc09_client(sb, betCode, odds,defaultScore);
                break;
            case 24:
            	ct24_25_26(sb, betCode);
            	break;
            case 25:
            	ct24_25_26(sb, betCode);
            	break;
            case 26:
            	ct24_25_26(sb, betCode);
            	break;
            case 27:
            	ct27(sb, betCode);
            	break;
            case 80:
            	zc01_client(sb, betCode, odds);
            	break;
            case 91:   //胜负平
            	 zc01_client(sb, betCode, odds);
            	break;
            case 92:   //进球数
            	zc03_client(sb, betCode, odds);
            	break;
            case 93:   //上下单上
            	zc95_client(sb,betCode,odds);
            	break;
            case 94:   //比分
            	zc02_client(sb, betCode, odds);
            	break;
            case 95:   //半全场
                zc04_client(sb, betCode, odds);
            	break; 
            case 96:   //胜负
            	zc96_client(sb,betCode,odds);
            	break;
        }

        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == ',') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
	}

	private static void zc96_client(StringBuilder sb, String betCode,
			String odds) {
		for (int i = 0, j = betCode.length(); i < j; i++) {
            sb.append(value(betCode.charAt(i), opt310));
            appendOddClient(sb, toArr(odds), i);
        }
		
	}

	private static void zc95_client(StringBuilder sb, String betCode,
			String odds) {
		if(betCode.length()<= 0){
            return;
        }
   	 	char x,y;
        for(int i = 0, j = betCode.length(); i < j; ){
            x = betCode.charAt(i++);
            y = betCode.charAt(i++);
            sb.append(changeTo(x+""+y));
            appendOddClient(sb, toArr(odds), (i-1)/2);
        }
		
	}

	private static void zc01_client(StringBuilder sb, String betCode,
			String odds) {
		for (int i = 0, j = betCode.length(); i < j; i++) {
            sb.append(value(betCode.charAt(i), opt310));
            appendOddClient(sb, toArr(odds), i);
        }
		
	}

	private static void lc08_client(StringBuilder sb, String betCode,
			String odds) {
		for(int i = 0, j = betCode.length(); i < j; ){
            if(betCode.charAt(i++) == '0'){
                sb.append("主胜");
            }else{
                sb.append("客胜");
            }
            switch (betCode.charAt(i++)) {
                case '1':
                    sb.append("1-5分");
                    break;
                case '2':
                    sb.append("6-10分");
                    break;
                case '3':
                    sb.append("11-15分");
                    break;
                case '4':
                    sb.append("16-20分");
                    break;
                case '5':
                    sb.append("21-25分");
                    break;
                case '6':
                    sb.append("26+分");
                    break;
            }
            appendOddClient(sb, toArr(odds), (i - 1) / 2);
        }
		
	}

	private static void lc07_client(StringBuilder sb, String betCode,
			String odds) {
		 for(int i = 0, j = betCode.length(); i < j; i++){
	            sb.append("主").append(value(betCode.charAt(i), opt21));
	            appendOddClient(sb, toArr(odds), i);
	        }
		
	}

	private static void zc04_client(StringBuilder sb, String betCode,
			String odds) {
		 if(betCode.length() % 2 != 0){
	            return;
	        }
	        for(int i = 0, j = betCode.length(); i < j; ){
	            sb.append(value(betCode.charAt(i++), opt310));
	            sb.append(value(betCode.charAt(i++), opt310));
	            appendOddClient(sb, toArr(odds), (i-1)/2);
	        }
		
	}

	private static void zc03_client(StringBuilder sb, String betCode,
			String odds) {
		 	char x;
	        for(int i = 0, j = betCode.length(); i < j; ){
	            x = betCode.charAt(i++);
	            sb.append(x);
	            if(x == '7'){
	                sb.append('+');
	            }
	            sb.append("球");
	            appendOddClient(sb, toArr(odds), i-1);
	        }
		
	}

	private static void zc02_client(StringBuilder sb, String betCode,
			String odds) {
		if(betCode.length() % 2 != 0){
            return;
        }
        char x, y;
        for(int i = 0, j = betCode.length(); i < j; ){
            x = betCode.charAt(i++);
            y = betCode.charAt(i++);
            if(x == '9' || y == '9'){
                if(x == y){
                    sb.append("平其他");
                }else if(x == '9'){
                    sb.append("胜其他");
                }else{
                    sb.append("负其他");
                }
            }else{
                sb.append(x).append(':').append(y);
            }
            appendOddClient(sb, toArr(odds), (i-1)/2);
        }
		
	}

	private static void appendOddClient(StringBuilder sb, String[] odds, int index) {
		 if(odds != null){
	            sb.append('@').append(odds[index]);
	        }
	        sb.append(',');
		
	}

	private static void lc09_client(StringBuilder sb, String betCode, String odds,
			float defaultScore) {
		for(int i = 0, j = betCode.length(); i < j; i++){
            sb.append(value(betCode.charAt(i), optLC21));
            appendOddClient(sb, toArr(odds), i,defaultScore);
        }
		
	}

	private static void appendOddClient(StringBuilder sb, String[] odds, int index,
			float defaultScore) {
		 if(odds != null){
	            sb.append('(');
	            if(defaultScore>0){
	            	sb.append("+"+defaultScore);
	            }
	            else{
	            	sb.append(defaultScore);
	            }
	            sb.append(')');
	            sb.append("@");
	            sb.append(odds[index]);
	            
	        }
	        sb.append(',');
		
	}

	private static void lc06_client(StringBuilder sb, String betCode, String odds,
			String concedePoints) {
		 for(int i = 0, j = betCode.length(); i < j; i++){
	            sb.append("让分主").append(value(betCode.charAt(i), opt21));
	            appendOddClient(sb, toArr(odds), i,concedePoints, LotteryId.JCLQ);
	        }
		
	}


	private static void zc01_rq_client(StringBuilder sb, String betCode,
			String odds, String concedePoints) {
		for (int i = 0, j = betCode.length(); i < j; i++) {
            sb.append(value(betCode.charAt(i), opt310_rq));
            appendOddClient(sb, toArr(odds), i,concedePoints, LotteryId.JCZQ);
        }
		
	}

	private static void appendOddClient(StringBuilder sb, String[] odds, int index,
			String concedePoints, LotteryId lotteryId) {
		 if(odds != null){
	            if(StringUtils.isNotBlank(concedePoints)){
	            	sb.append('(');
	            	Float concedeFloat = Float.valueOf(concedePoints);
	            	if(lotteryId == LotteryId.JCZQ) {
	            		int concedePointsIntValue=concedeFloat.intValue();
	            		concedePoints = concedePointsIntValue + "";
	            	}
	            	if(concedeFloat>0){
	            		sb.append("+");
	            	}
	            	sb.append(concedePoints);
	            	 sb.append(')');
	            }
	            sb.append("@");
	            sb.append(odds[index]);
	           
	        }
	        sb.append(',');
		
	}

	/**
	 * 足彩让球胜平负
	 * @param sb
	 * @param betCode
	 * @param odds
	 * @param concedePoints 
	 */
	private static void zc01_rq(StringBuilder sb, String betCode, String odds) {
		for (int i = 0, j = betCode.length(); i < j; i++) {
            sb.append(value(betCode.charAt(i), opt310_rq));
            appendOdd(sb, toArr(odds), i);
        }
		
	}

	private static Object value(char x, String[] opt310_rq2) {
		return opt310_rq2[x - '0'];
	}

	public static boolean isMatchWin(PlayMatch matchContainsResult, String playId) {
		if (matchContainsResult == null || playId == null){
    		return false;
    	}
		if(matchContainsResult.getStatus()==EntityStatus.MATCH_CANCEL){//如果比赛是取消状态，肯定过关
			return true;
		}
    	String result = matchContainsResult.getResult();
    	String betCode = matchContainsResult.getBetCode();
    	String playIdWithoutPassType = playId.substring(0, playId.lastIndexOf('_'));
    	
    	if ( playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_05_ZC) ||
        		playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_10_LC) ){
    		playIdWithoutPassType = matchContainsResult.getPlayId().substring(0, playId.lastIndexOf('_'));
        }
       	
    	if ( playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_01_ZC) ||
       		 playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_03_ZC) ||
    		 playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_06_LC) ||
             playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_07_LC) || 
             playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_09_LC) ||
             playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_80_ZC) ||
             Constants.PLAY_01_BD_SPF.equalsIgnoreCase(playId) ||
             Constants.PLAY_06_BD_SF.equalsIgnoreCase(playId) ||
             Constants.PLAY_02_BD_JQS.equalsIgnoreCase(playId)){
            return isResultInBetCode(result, betCode);
        }
    	
    	if ( playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_02_ZC) ||
       		 playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_04_ZC) ||
       		 playIdWithoutPassType.equalsIgnoreCase(Constants.PLAY_08_LC) ||
       		Constants.PLAY_04_BD_BF.equalsIgnoreCase(playId) ||
       		Constants.PLAY_05_BD_BQC.equalsIgnoreCase(playId) ||
       		Constants.PLAY_03_BD_SXDS.equalsIgnoreCase(playId)){
            return isResultInBetCodeEveryTwoLetters(result, betCode);
        }
    	
    	return false;
	}

}
