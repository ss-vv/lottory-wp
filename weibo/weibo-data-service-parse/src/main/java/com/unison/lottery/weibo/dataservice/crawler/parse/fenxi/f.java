package com.unison.lottery.weibo.dataservice.crawler.parse.fenxi;

import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.a;

/**
 * ���·��� �¼�ģ��
 * @author guixiangcui
 *
 */
class f extends ax
{
  boolean a;
  a b;
  int c;
  /**
   *是否主队
   */
  boolean isHoneTeam;
  boolean e;
  int f;
  /**
   * 事件类型
   */
  String eventType;
  /**
   * 事件时间
   */
  String eventTime;
  /**
   * 事件描述
   */
  String eventDesc;
  /**
   * 技术统计--主队
   */
  String statisticHome;
  /**
   * 技术统计--技术类型
   */
  String statisticType;
  /**
   * 技术统计--客队
   */
  String statisticGuest;
  String m;
  String n;
  String o;
  String p;
  String q;

  public f(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    this.statisticHome = paramString1;
    this.statisticType = paramString2;
    this.statisticGuest = paramString3;
    this.c = paramInt;
  }

  public f(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.m = paramString1;
    this.n = paramString2;
    this.o = paramString3;
    this.p = paramString4;
    this.q = paramString5;
    this.c = paramInt;
  }

  public f(int paramInt, boolean paramBoolean, String paramString1, String paramString2, String paramString3)
  {
    this.isHoneTeam = paramBoolean;
    this.eventType = paramString1;
    this.eventTime = paramString2;
    this.eventDesc = paramString3;
    this.c = paramInt;
  }

  public f(boolean paramBoolean)
  {
    super(paramBoolean);
  }

  public f(boolean paramBoolean, int paramInt)
  {
    this.e = paramBoolean;
    this.f = paramInt;
  }

  public f(boolean paramBoolean, a parama)
  {
    this.a = paramBoolean;
    this.b = parama;
  }

  public a a()
  {
    return this.b;
  }

  public boolean b()
  {
    return this.a;
  }

  public int c()
  {
    return this.f;
  }

  public int d()
  {
    return this.c;
  }

  public boolean e()
  {
    return this.isHoneTeam;
  }

  public boolean f()
  {
    return this.e;
  }

  public String g()
  {
    return this.eventType;
  }

  public String h()
  {
    return this.eventTime;
  }

  public String i()
  {
    return this.eventDesc;
  }

  public String j()
  {
    return this.statisticHome;
  }

  public String k()
  {
    return this.statisticType;
  }

  public String l()
  {
    return this.statisticGuest;
  }

  public String m()
  {
    return this.m;
  }

  public String n()
  {
    return this.n;
  }

  public String o()
  {
    return this.o;
  }

  public String p()
  {
    return this.p;
  }

  public String q()
  {
    return this.q;
  }
}
