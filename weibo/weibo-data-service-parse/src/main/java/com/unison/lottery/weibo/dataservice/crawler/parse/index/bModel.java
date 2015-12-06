package com.unison.lottery.weibo.dataservice.crawler.parse.index;

public class bModel
{
  String matchId;
  String LeagueId;
  /**
   * 让分初盘-主队
   */
  String rf_cp_hometeam;
  /**
   * 让分初盘-盘口
   */
  String rf_cp_pk;
  /**
   * 让分初盘-客队
   */
  String rf_cp_guestteam;
  /**
   * 让分即时-主队
   */
  String rf_js_hometeam;
  /**
   * 让分即时-盘口
   */
  String rf_js_pk;
  /**
   * 让分即时-客队
   */
  String rf_js_guestteam;
  /**
   * 总分初盘-主队
   */
  String zf_cp_hometeam;
  /**
   * 总分初盘-盘口
   */
  String zf_cp_pk;
  /**
   * 总分初盘-客队
   */
  String zf_cp_guestteam;
  /**
   * 总分-即时
   */
  String zf_js_hometeam;
  /**
   * 总分-即时-盘口
   */
  String zf_js_pk;
  /**
   * 总分即时-客队
   */
  String zf_js_guestteam;
  /**
   * 欧赔初盘-主队
   */
  String op_cp_hometeam;
  /**
   * 欧赔初盘-客队
   */
  String op_cp_guestteam;
  /**
   * 欧赔即时-主队
   */
  String op_js_hometeam;
  /**
   * 欧赔即时-客队
   */
  String op_js_guestteam;
  int s;
  int t;
  

  public bModel(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18)
  {
    this.matchId = paramString1;
    this.LeagueId = paramString2;
    this.rf_cp_hometeam = paramString3;
    
    
    this.rf_cp_pk = paramString4;
    
    
    this.rf_cp_guestteam = paramString5;
    
    this.rf_js_hometeam = paramString6;
    /**
     * 
     */
    this.rf_js_pk = paramString7;
    /**
     * 
     */
    this.rf_js_guestteam = paramString8;
    /**
     * 
     */
    this.zf_cp_hometeam = paramString9;
    /**
     * 
     */
    this.zf_cp_pk = paramString10;
    /**
     * 
     */
    this.zf_cp_guestteam = paramString11;
    /**
     * 
     */
    this.zf_js_hometeam = paramString12;
    /**
     * 
     */
    this.zf_js_pk = paramString13;
    /**
     * 
     */
    this.zf_js_guestteam = paramString14;
    /**
     * 
     */
    this.op_cp_hometeam = paramString15;
    /**
     * 
     */
    this.op_cp_guestteam = paramString16;
    /**
     * 
     */
    this.op_js_hometeam = paramString17;
    /**
     * 
     */
    this.op_js_guestteam = paramString18;
  }

  public int a()
  {
    return this.s;
  }

  public void a(int paramInt)
  {
    this.s = paramInt;
  }

  public void a(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
      if ((paramInt & this.s) != paramInt)
        this.s = (paramInt + this.s);
    while (true)
    {
//      return;
//      if ((paramInt & this.t) != paramInt)
//        this.t = (paramInt + this.t);
    }
  }

  public void a(String paramString)
  {
    this.matchId = paramString;
  }

  public int b()
  {
    return this.t;
  }

  public void b(int paramInt)
  {
    this.t = paramInt;
  }

  public void b(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
      if ((paramInt & this.s) == paramInt)
        this.s -= paramInt;
    while (true)
    {
//      return;
//      if ((paramInt & this.t) == paramInt)
//        this.t -= paramInt;
    }
  }

  public void b(String paramString)
  {
    this.LeagueId = paramString;
  }

  public String c()
  {
    return this.matchId;
  }

  public void c(String paramString)
  {
    this.rf_cp_hometeam = paramString;
  }

  public String d()
  {
    return this.LeagueId;
  }

  public void d(String paramString)
  {
    this.rf_cp_pk = paramString;
  }

  public String e()
  {
    return this.rf_cp_hometeam;
  }

  public void e(String paramString)
  {
    this.rf_cp_guestteam = paramString;
  }

  public String f()
  {
    return this.rf_cp_pk;
  }

  public void f(String paramString)
  {
    this.rf_js_hometeam = paramString;
  }

  public String g()
  {
    return this.rf_cp_guestteam;
  }

  public void g(String paramString)
  {
    this.rf_js_pk = paramString;
  }

  public String h()
  {
    return this.rf_js_hometeam;
  }

  public void h(String paramString)
  {
    this.rf_js_guestteam = paramString;
  }

  public String i()
  {
    return this.rf_js_pk;
  }

  public void i(String paramString)
  {
    this.zf_cp_hometeam = paramString;
  }

  public String j()
  {
    return this.rf_js_guestteam;
  }

  public void j(String paramString)
  {
    this.zf_cp_pk = paramString;
  }

  public String k()
  {
    return this.zf_cp_hometeam;
  }

  public void k(String paramString)
  {
    this.zf_cp_guestteam = paramString;
  }

  public String l()
  {
    return this.zf_cp_pk;
  }

  public void l(String paramString)
  {
    this.zf_js_hometeam = paramString;
  }

  public String m()
  {
    return this.zf_cp_guestteam;
  }

  public void m(String paramString)
  {
    this.zf_js_pk = paramString;
  }

  public String n()
  {
    return this.zf_js_hometeam;
  }

  public void n(String paramString)
  {
    this.zf_js_guestteam = paramString;
  }

  public String o()
  {
    return this.zf_js_pk;
  }

  public void o(String paramString)
  {
    this.op_cp_hometeam = paramString;
  }

  public String p()
  {
    return this.zf_js_guestteam;
  }

  public void p(String paramString)
  {
    this.op_cp_guestteam = paramString;
  }

  public String q()
  {
    return this.op_cp_hometeam;
  }

  public void q(String paramString)
  {
    this.op_js_hometeam = paramString;
  }

  public String r()
  {
    return this.op_cp_guestteam;
  }

  public void r(String paramString)
  {
    this.op_js_guestteam = paramString;
  }

  public String s()
  {
    return this.op_js_hometeam;
  }

  public String t()
  {
    return this.op_js_guestteam;
  }

  public String toString()
  {
    return "Odds2 [LeagueId=" + this.LeagueId + ", matchId=" + this.matchId + ", op_cp_guestteam=" + this.op_cp_guestteam + ", op_cp_hometeam=" + this.op_cp_hometeam + ", op_js_guestteam=" + this.op_js_guestteam + ", op_js_hometeam=" + this.op_js_hometeam + ", rf_cp_guestteam=" + this.rf_cp_guestteam + ", rf_cp_hometeam=" + this.rf_cp_hometeam + ", rf_cp_pk=" + this.rf_cp_pk + ", rf_js_guestteam=" + this.rf_js_guestteam + ", rf_js_hometeam=" + this.rf_js_hometeam + ", rf_js_pk=" + this.rf_js_pk + ", zf_cp_guestteam=" + this.zf_cp_guestteam + ", zf_cp_hometeam=" + this.zf_cp_hometeam + ", zf_cp_pk=" + this.zf_cp_pk + ", zf_js_guestteam=" + this.zf_js_guestteam + ", zf_js_hometeam=" + this.zf_js_hometeam + ", zf_js_pk=" + this.zf_js_pk + "]";
  }

  public static enum a
  {
    a,b,c,d,e,f,g,h
  }
}
