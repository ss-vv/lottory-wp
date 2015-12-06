package com.davcai.lottery.platform.client.qiutan;

import org.apache.commons.lang.StringUtils;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;

public class QiutanJCMatch extends LotteryPlatformBaseResponse{
	private String id;
	private String scheduleid;
	private String polygoal;//让球
	private String matchid;
	private String matchtime;
	private String sellouttime;
	private String sclass;
	private String hometeam;
	private String guestteam;
	private String wl3;
	private String wl1;
	private String wl0;
	private String t0;
	private String t1;
	private String t2;
	private String t3;
	private String t4;
	private String t5;
	private String t6;
	private String t7;
	private String sw10;
	private String sw20;
	private String sw21;
	private String sw30;
	private String sw31;
	private String sw32;
	private String sw40;
	private String sw41;
	private String sw42;
	private String sw50;
	private String sw51;
	private String sw52;
	private String sw5;
	private String sd00;
	private String sd11;
	private String sd22;
	private String sd33;
	private String sd4;
	private String sl01;
	private String sl02;
	private String sl12;
	private String sl03;
	private String sl13;
	private String sl23;
	private String sl04;
	private String sl14;
	private String sl24;
	private String sl05;
	private String sl15;
	private String sl25;
	private String sl5;
	private String ht33;
	private String ht31;
	private String ht30;
	private String ht13;
	private String ht11;
	private String ht10;
	private String ht03;
	private String ht01;
	private String ht00;
	private String sf3;
	private String sf1;
	private String sf0;
	private String color;
	private String buyid;
	private String hidetypeid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScheduleid() {
		return scheduleid;
	}
	public void setScheduleid(String scheduleid) {
		this.scheduleid = scheduleid;
	}
	public String getPolygoal() {
		return polygoal;
	}
	public void setPolygoal(String polygoal) {
		this.polygoal = polygoal;
	}
	public String getMatchid() {
		return matchid;
	}
	public void setMatchid(String matchid) {
		this.matchid = matchid;
	}
	public String getMatchtime() {
		return matchtime;
	}
	public void setMatchtime(String matchtime) {
		this.matchtime = matchtime;
	}
	public String getSellouttime() {
		return sellouttime;
	}
	public void setSellouttime(String sellouttime) {
		this.sellouttime = sellouttime;
	}
	public String getSclass() {
		return sclass;
	}
	public void setSclass(String sclass) {
		this.sclass = sclass;
	}
	public String getHometeam() {
		return hometeam;
	}
	public void setHometeam(String hometeam) {
		this.hometeam = hometeam;
	}
	public String getGuestteam() {
		return guestteam;
	}
	public void setGuestteam(String guestteam) {
		this.guestteam = guestteam;
	}
	public String getWl3() {
		return wl3;
	}
	public void setWl3(String wl3) {
		if(StringUtils.isBlank(wl3)){
			wl3="0";
		}
		this.wl3 = wl3;
	}
	public String getWl1() {
		return wl1;
	}
	public void setWl1(String wl1) {
		if(StringUtils.isBlank(wl1)){
			wl1="0";
		}
		this.wl1 = wl1;
	}
	public String getWl0() {
		return wl0;
	}
	public void setWl0(String wl0) {
		if(StringUtils.isBlank(wl0)){
			wl0="0";
		}
		this.wl0 = wl0;
	}
	public String getT0() {
		return t0;
	}
	public void setT0(String t0) {
		if(StringUtils.isBlank(t0)){
			t0="0";
		}
		this.t0 = t0;
	}
	public String getT1() {
		return t1;
	}
	public void setT1(String t1) {
		if(StringUtils.isBlank(t1)){
			t1="0";
		}
		this.t1 = t1;
	}
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		if(StringUtils.isBlank(t2)){
			t2="0";
		}
		this.t2 = t2;
	}
	public String getT3() {
		return t3;
	}
	public void setT3(String t3) {
		if(StringUtils.isBlank(t3)){
			t3="0";
		}
		this.t3 = t3;
	}
	public String getT4() {
		return t4;
	}
	public void setT4(String t4) {
		if(StringUtils.isBlank(t4)){
			t4="0";
		}
		this.t4 = t4;
	}
	public String getT5() {
		return t5;
	}
	public void setT5(String t5) {
		if(StringUtils.isBlank(t5)){
			t5="0";
		}
		this.t5 = t5;
	}
	public String getT6() {
		return t6;
	}
	public void setT6(String t6) {
		if(StringUtils.isBlank(t6)){
			t6="0";
		}
		this.t6 = t6;
	}
	public String getT7() {
		return t7;
	}
	public void setT7(String t7) {
		if(StringUtils.isBlank(t7)){
			t7="0";
		}
		this.t7 = t7;
	}
	public String getSw10() {
		return sw10;
	}
	public void setSw10(String sw10) {
		if(StringUtils.isBlank(sw10)){
			sw10="0";
		}
		this.sw10 = sw10;
	}
	public String getSw20() {
		return sw20;
	}
	public void setSw20(String sw20) {
		if(StringUtils.isBlank(sw20)){
			sw20="0";
		}
		this.sw20 = sw20;
	}
	public String getSw21() {
		return sw21;
	}
	public void setSw21(String sw21) {
		if(StringUtils.isBlank(sw21)){
			sw21="0";
		}
		this.sw21 = sw21;
	}
	public String getSw30() {
		return sw30;
	}
	public void setSw30(String sw30) {
		if(StringUtils.isBlank(sw30)){
			sw30="0";
		}
		this.sw30 = sw30;
	}
	public String getSw31() {
		return sw31;
	}
	public void setSw31(String sw31) {
		if(StringUtils.isBlank(sw31)){
			sw31="0";
		}
		this.sw31 = sw31;
	}
	public String getSw32() {
		return sw32;
	}
	public void setSw32(String sw32) {
		if(StringUtils.isBlank(sw32)){
			sw32="0";
		}
		this.sw32 = sw32;
	}
	public String getSw40() {
		return sw40;
	}
	public void setSw40(String sw40) {
		if(StringUtils.isBlank(sw40)){
			sw40="0";
		}
		this.sw40 = sw40;
	}
	public String getSw41() {
		return sw41;
	}
	public void setSw41(String sw41) {
		if(StringUtils.isBlank(sw41)){
			sw41="0";
		}
		this.sw41 = sw41;
	}
	public String getSw42() {
		return sw42;
	}
	public void setSw42(String sw42) {
		if(StringUtils.isBlank(sw42)){
			sw42="0";
		}
		this.sw42 = sw42;
	}
	public String getSw50() {
		return sw50;
	}
	public void setSw50(String sw50) {
		if(StringUtils.isBlank(sw50)){
			sw50="0";
		}
		this.sw50 = sw50;
	}
	public String getSw51() {
		return sw51;
	}
	public void setSw51(String sw51) {
		if(StringUtils.isBlank(sw51)){
			sw51="0";
		}
		this.sw51 = sw51;
	}
	public String getSw52() {
		return sw52;
	}
	public void setSw52(String sw52) {
		if(StringUtils.isBlank(sw52)){
			sw52="0";
		}
		this.sw52 = sw52;
	}
	public String getSw5() {
		return sw5;
	}
	public void setSw5(String sw5) {
		if(StringUtils.isBlank(sw5)){
			sw5="0";
		}
		this.sw5 = sw5;
	}
	public String getSd00() {
		return sd00;
	}
	public void setSd00(String sd00) {
		if(StringUtils.isBlank(sd00)){
			sd00="0";
		}
		this.sd00 = sd00;
	}
	public String getSd11() {
		return sd11;
	}
	public void setSd11(String sd11) {
		if(StringUtils.isBlank(sd11)){
			sd11="0";
		}
		this.sd11 = sd11;
	}
	public String getSd22() {
		return sd22;
	}
	public void setSd22(String sd22) {
		if(StringUtils.isBlank(sd22)){
			sd22="0";
		}
		this.sd22 = sd22;
	}
	public String getSd33() {
		return sd33;
	}
	public void setSd33(String sd33) {
		if(StringUtils.isBlank(sd33)){
			sd33="0";
		}
		this.sd33 = sd33;
	}
	public String getSd4() {
		return sd4;
	}
	public void setSd4(String sd4) {
		if(StringUtils.isBlank(sd4)){
			sd4="0";
		}
		this.sd4 = sd4;
	}
	public String getSl01() {
		return sl01;
	}
	public void setSl01(String sl01) {
		if(StringUtils.isBlank(sl01)){
			sl01="0";
		}
		this.sl01 = sl01;
	}
	public String getSl02() {
		return sl02;
	}
	public void setSl02(String sl02) {
		if(StringUtils.isBlank(sl01)){
			sl02="0";
		}
		this.sl02 = sl02;
	}
	public String getSl12() {
		return sl12;
	}
	public void setSl12(String sl12) {
		if(StringUtils.isBlank(sl12)){
			sl12="0";
		}
		this.sl12 = sl12;
	}
	public String getSl03() {
		return sl03;
	}
	public void setSl03(String sl03) {
		if(StringUtils.isBlank(sl03)){
			sl03="0";
		}
		this.sl03 = sl03;
	}
	public String getSl13() {
		return sl13;
	}
	public void setSl13(String sl13) {
		if(StringUtils.isBlank(sl13)){
			sl13="0";
		}
		this.sl13 = sl13;
	}
	public String getSl23() {
		return sl23;
	}
	public void setSl23(String sl23) {
		if(StringUtils.isBlank(sl23)){
			sl23="0";
		}
		this.sl23 = sl23;
	}
	public String getSl04() {
		return sl04;
	}
	public void setSl04(String sl04) {
		if(StringUtils.isBlank(sl04)){
			sl04="0";
		}
		this.sl04 = sl04;
	}
	public String getSl14() {
		return sl14;
	}
	public void setSl14(String sl14) {
		if(StringUtils.isBlank(sl14)){
			sl14="0";
		}
		this.sl14 = sl14;
	}
	public String getSl24() {
		return sl24;
	}
	public void setSl24(String sl24) {
		if(StringUtils.isBlank(sl24)){
			sl24="0";
		}
		this.sl24 = sl24;
	}
	public String getSl05() {
		return sl05;
	}
	public void setSl05(String sl05) {
		if(StringUtils.isBlank(sl05)){
			sl05="0";
		}
		this.sl05 = sl05;
	}
	public String getSl15() {
		return sl15;
	}
	public void setSl15(String sl15) {
		if(StringUtils.isBlank(sl15)){
			sl15="0";
		}
		this.sl15 = sl15;
	}
	public String getSl25() {
		return sl25;
	}
	public void setSl25(String sl25) {
		if(StringUtils.isBlank(sl25)){
			sl25="0";
		}
		this.sl25 = sl25;
	}
	public String getSl5() {
		return sl5;
	}
	public void setSl5(String sl5) {
		if(StringUtils.isBlank(sl5)){
			sl5="0";
		}
		this.sl5 = sl5;
	}
	public String getHt33() {
		return ht33;
	}
	public void setHt33(String ht33) {
		if(StringUtils.isBlank(ht33)){
			ht33="0";
		}
		this.ht33 = ht33;
	}
	public String getHt31() {
		return ht31;
	}
	public void setHt31(String ht31) {
		if(StringUtils.isBlank(ht31)){
			ht31="0";
		}
		this.ht31 = ht31;
	}
	public String getHt30() {
		return ht30;
	}
	public void setHt30(String ht30) {
		if(StringUtils.isBlank(ht30)){
			ht30="0";
		}
		this.ht30 = ht30;
	}
	public String getHt13() {
		return ht13;
	}
	public void setHt13(String ht13) {
		if(StringUtils.isBlank(ht13)){
			ht13="0";
		}
		this.ht13 = ht13;
	}
	public String getHt11() {
		return ht11;
	}
	public void setHt11(String ht11) {
		if(StringUtils.isBlank(ht11)){
			ht11="0";
		}
		this.ht11 = ht11;
	}
	public String getHt10() {
		return ht10;
	}
	public void setHt10(String ht10) {
		if(StringUtils.isBlank(ht10)){
			ht10="0";
		}
		this.ht10 = ht10;
	}
	public String getHt03() {
		return ht03;
	}
	public void setHt03(String ht03) {
		if(StringUtils.isBlank(ht03)){
			ht03="0";
		}
		this.ht03 = ht03;
	}
	public String getHt01() {
		return ht01;
	}
	public void setHt01(String ht01) {
		if(StringUtils.isBlank(ht01)){
			ht01="0";
		}
		this.ht01 = ht01;
	}
	public String getHt00() {
		return ht00;
	}
	public void setHt00(String ht00) {
		if(StringUtils.isBlank(ht00)){
			ht00="0";
		}
		this.ht00 = ht00;
	}
	public String getSf3() {
		return sf3;
	}
	public void setSf3(String sf3) {
		if(StringUtils.isBlank(sf3)){
			sf3="0";
		}
		this.sf3 = sf3;
	}
	public String getSf1() {
		return sf1;
	}
	public void setSf1(String sf1) {
		if(StringUtils.isBlank(sf1)){
			sf1="0";
		}
		this.sf1 = sf1;
	}
	public String getSf0() {
		return sf0;
	}
	public void setSf0(String sf0) {
		if(StringUtils.isBlank(sf0)){
			sf0="0";
		}
		this.sf0 = sf0;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getBuyid() {
		return buyid;
	}
	public void setBuyid(String buyid) {
		this.buyid = buyid;
	}
	public String getHidetypeid() {
		return hidetypeid;
	}
	public void setHidetypeid(String hidetypeid) {
		this.hidetypeid = hidetypeid;
	}
}
