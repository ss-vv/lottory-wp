package com.unison.lottery.weibo.dataservice.crawler.parse.fenxi_lq;

import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.ax;



public class bk extends ax{
	String a;
	String b;
	String c;
	String d;
	
	
	public bk(String str1,String str2,String str3,String str4) {
		a = str1;
		b = str2;
		c = str3;
		d = str4;
	}
	
	public bk(boolean bol){
		super(bol);
	}

	public String getA() {
		return a;
	}

	public String getB() {
		return b;
	}

	public String getC() {
		return c;
	}

	public String getD() {
		return d;
	}
}