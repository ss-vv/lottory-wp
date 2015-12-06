package com.unison.lottery.weibo.dataservice.crawler.parse.fenxi_lq;

public class lq_indexModel
{
  public static int k;
  int a;
  boolean b;
  boolean c;
  String d;
  String e;
  String f;
  String g;
  String h;
  String i;
  String j;

  public lq_indexModel(int paramInt, boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.a = paramInt;
    this.b = paramBoolean1;
    this.c = paramBoolean2;
    this.d = paramString2;
    this.e = paramString1;
    this.f = paramString4;
    this.g = paramString5;
    this.h = paramString3;
    this.i = paramString6;
  }

  public lq_indexModel(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.e = paramString1;
    this.h = paramString2;
    this.i = paramString3;
    this.j = paramString4;
  }

  public static void b(int paramInt)
  {
    if ((paramInt & k) != paramInt)
      k = paramInt + k;
  }

  public static boolean c(int paramInt)
  {
    if ((paramInt & k) == paramInt);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public String a()
  {
    return this.j;
  }

  public void a(int paramInt)
  {
    this.a = paramInt;
  }

  public void a(String paramString)
  {
    this.d = paramString;
  }

  public void b(String paramString)
  {
    this.e = paramString;
  }

  public boolean b()
  {
    return this.c;
  }

  public void c(String paramString)
  {
    this.f = paramString;
  }

  public boolean c()
  {
    return this.b;
  }

  public int d()
  {
    return this.a;
  }

  public void d(String paramString)
  {
    this.g = paramString;
  }

  public String e()
  {
    return this.d;
  }

  public void e(String paramString)
  {
    this.h = paramString;
  }

  public String f()
  {
    return this.e;
  }

  public void f(String paramString)
  {
    this.i = paramString;
  }

  public String g()
  {
    return this.f;
  }

  public String h()
  {
    return this.g;
  }

  public String i()
  {
    return this.h;
  }

  public String j()
  {
    return this.i;
  }

}
