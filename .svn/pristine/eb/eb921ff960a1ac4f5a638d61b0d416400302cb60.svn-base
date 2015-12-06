package com.unison.lottery.weibo.dataservice.crawler.parse.realtime;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class u
{
	/**
	 * 联赛id
	 */
  public String a;
  /**
   * 联赛名称
   */
  public String b;
  public String c;
  public String d;
  public String e;
  /**
   * isTop
   */
  public boolean f;
  public int g;
  public String h;
  public String i;
  public String j;
  public String k;
  public String l;
  public String m;
  public String n;
  public int o;
  public String p;
  public String q;
  public String r;
  public int s;
  public List<String> t;
  List<b> u;
  List<f> v;
  List<e> w;
  Map<String, List<c>> x;
  Map<String, List<a>> y;

  public u()
  {
    this.f = true;
    this.g = 0;
    this.u = new ArrayList();
    this.v = new ArrayList();
    this.w = new ArrayList();
  }

  /**
   * 
   * @param paramString1  联赛id
   * @param paramString2  联赛名称
   */
  public u(String paramString1, String paramString2)
  {
    this.f = true;
    this.g = 0;
    this.u = new ArrayList();
    this.v = new ArrayList();
    this.w = new ArrayList();
    this.a = paramString1;
    this.b = paramString2;
  }

  public u(String paramString1, String paramString2, String paramString3)
  {
    this.f = true;
    this.g = 0;
    this.u = new ArrayList();
    this.v = new ArrayList();
    this.w = new ArrayList();
    this.a = paramString1;
    this.b = paramString2;
    if ((paramString3 != null) && (paramString3.length() > 0))
      if (an.b(paramString3) != 1){
    	  this.f = false;
      }
  }

  public u(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.f = true;
    this.g = 0;
    this.u = new ArrayList();
    this.v = new ArrayList();
    this.w = new ArrayList();
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramString3;
    this.h = paramString4;
  }

  public u(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, List<String> paramList)
  {
    this.f = true;
    this.g = 0;
    this.u = new ArrayList();
    this.v = new ArrayList();
    this.w = new ArrayList();
    this.a = paramString1;
    this.b = paramString2;
    this.e = paramString3;
    this.r = paramString4;
    this.s = paramInt;
    this.t = paramList;
  }

  public u(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.f = true;
    this.g = 0;
    this.u = new ArrayList();
    this.v = new ArrayList();
    this.w = new ArrayList();
    this.a = paramString1;
    this.b = paramString2;
    this.i = paramString3;
    this.k = paramString4;
    this.l = paramString5;
  }

  public u(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8)
  {
    this.f = true;
    this.g = 0;
    this.u = new ArrayList();
    this.v = new ArrayList();
    this.w = new ArrayList();
    this.a = paramString1;
    this.b = paramString2;
    this.i = paramString3;
    this.j = paramString4;
    this.k = paramString5;
    this.l = paramString6;
    this.m = paramString7;
    this.n = paramString8;
  }

  public u(String paramString1, String paramString2, boolean paramBoolean, int paramInt)
  {
    this.f = true;
    this.g = 0;
    this.u = new ArrayList();
    this.v = new ArrayList();
    this.w = new ArrayList();
    this.a = paramString1;
    this.b = paramString2;
    this.f = paramBoolean;
    this.g = paramInt;
  }

  public String a()
  {
    return this.d;
  }

  public void a(int paramInt)
  {
    this.o = paramInt;
  }

  public void a(String paramString)
  {
    this.d = paramString;
  }

  public void a(String paramString1, String paramString2)
  {
    if ((this.x == null) || (this.x.size() == 0))
      this.x = new HashMap();
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString1 = paramString2.split("\\^");
    int i1 = 0;
    if (i1 < arrayOfString1.length)
    {
      String[] arrayOfString2 = arrayOfString1[i1].split(",");
      if (arrayOfString2.length < 2);
      while (true)
      {
        i1++;
        
        c localc = new c();
        localc.a(an.b(arrayOfString2[0]));
        localc.a(arrayOfString2[1].toLowerCase().equals("true"));
        localArrayList.add(localc);
      }
    }
    Collections.sort(localArrayList);
    if (this.x.containsKey(paramString1))
      this.x.remove(paramString1);
    this.x.put(paramString1, localArrayList);
  }

  public void a(List<String> paramList)
  {
    this.t = paramList;
  }

  public void a(Map<String, List<c>> paramMap)
  {
    this.x = paramMap;
  }

  public String b()
  {
    return this.i;
  }

  public void b(int paramInt)
  {
    this.s = paramInt;
  }

  public void b(String paramString)
  {
    this.m = paramString;
  }

  public void b(String paramString1, String paramString2)
  {
    if ((this.y == null) || (this.y.size() == 0))
      this.y = new HashMap();
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString1 = paramString2.split("\\!");
    int i1 = 0;
    if (i1 < arrayOfString1.length)
    {
      String[] arrayOfString2 = arrayOfString1[i1].split("\\^");
      if (arrayOfString2.length < 3);
      while (true)
      {
        i1++;
        
        a locala = new a();
        locala.a(an.b(arrayOfString2[0]));
        locala.a(arrayOfString2[1]);
        locala.a(arrayOfString2[2].toLowerCase().equals("true"));
        localArrayList.add(locala);
      }
    }
    if (this.y.containsKey(paramString1))
      this.y.remove(paramString1);
    this.y.put(paramString1, localArrayList);
  }

  public void b(Map<String, List<a>> paramMap)
  {
    this.y = paramMap;
  }

  public String c()
  {
    return this.m;
  }

  public void c(String paramString)
  {
    this.n = paramString;
  }

  public String d()
  {
    return this.n;
  }

  public void d(String paramString)
  {
    this.p = paramString;
  }

  public String e()
  {
    return this.j;
  }

  public void e(String paramString)
  {
    this.q = paramString;
  }

  public String f()
  {
    return this.l;
  }

  public boolean f(String paramString)
  {
    this.u.clear();
    String[] arrayOfString1 = paramString.split("\\!", -1);
    int i1 = 0;
    boolean bool = false;
    while (i1 < arrayOfString1.length)
    {
      String[] arrayOfString2 = arrayOfString1[i1].split("\\^", -1);
      if (arrayOfString2.length >= 2)
//        this.u.add(new b(arrayOfString2[0], arrayOfString2[1].equals("1")));
      if ((arrayOfString2[1].equals("1")) && (arrayOfString2[0].equals("2")))
        bool = true;
      i1++;
    }
    return bool;
  }

  public int g()
  {
    return this.o;
  }

  public String g(String paramString)
  {
    this.v.clear();
    String localObject1 = "";
    String[] arrayOfString1 = paramString.split("\\!", -1);
    int i1 = 0;
    String[] arrayOfString2;
    if (i1 < arrayOfString1.length)
    {
      arrayOfString2 = arrayOfString1[i1].split("\\^", -1);
      if (arrayOfString2.length < 4)
      this.v.add(new f(arrayOfString2[0], arrayOfString2[1], an.b(arrayOfString2[2]), arrayOfString2[3].equals("1")));
      if (!arrayOfString2[3].equals("1"))
    	  for (String localObject2 = arrayOfString2[1]; ; localObject2 = localObject1)
    	    {
    	      i1++;
    	      localObject1 = localObject2;
    	    }
        
    }
    
    return localObject1;
  }

  public String h()
  {
    return this.p;
  }

  public String h(String paramString)
  {
    this.w.clear();
    String str = "";
    String[] arrayOfString1 = paramString.split("\\!", -1);
    for (int i1 = 0; i1 < arrayOfString1.length; i1++)
    {
      String[] arrayOfString2 = arrayOfString1[i1].split("\\^", -1);
      if (arrayOfString2.length >= 2)
        this.w.add(new e(arrayOfString2[0], arrayOfString2[1].equals("1")));
      if (arrayOfString2[1].equals("1"))
        str = arrayOfString2[0];
    }
    return str;
  }

  public String i()
  {
    return this.q;
  }

  public void i(String paramString)
  {
    this.a = paramString;
  }

  public List<b> j()
  {
    return this.u;
  }

  public void j(String paramString)
  {
    this.b = paramString;
  }

  public List<f> k()
  {
    return this.v;
  }

  public void k(String paramString)
  {
    this.c = paramString;
  }

  public List<e> l()
  {
    return this.w;
  }

  public void l(String paramString)
  {
    this.r = paramString;
  }

  public Map<String, List<c>> m()
  {
    return this.x;
  }

  public void m(String paramString)
  {
    this.e = paramString;
  }

  public Map<String, List<a>> n()
  {
    return this.y;
  }

  public void n(String paramString)
  {
    this.h = paramString;
  }

  public void o()
  {
    this.g = (1 + this.g);
  }

  public int p()
  {
    return this.g;
  }

  public String q()
  {
    return this.b;
  }

  public boolean r()
  {
    return this.f;
  }

  public String s()
  {
    return this.a;
  }

  public String t()
  {
    return this.b;
  }

  public String toString()
  {
    return "League [isTop=" + this.f + ", leagueId=" + this.a + ", name=" + this.b + "]";
  }

  public String u()
  {
    return this.c;
  }

  public String v()
  {
    return this.r;
  }

  public int w()
  {
    return this.s;
  }

  public List<String> x()
  {
    return this.t;
  }

  public String y()
  {
    return this.e;
  }

  public String z()
  {
    return this.h;
  }

  public class a
  {
    int a;
    String b;
    boolean c;

    public a()
    {
    }

    public int a()
    {
      return this.a;
    }

    public void a(int paramInt)
    {
      this.a = paramInt;
    }

    public void a(String paramString)
    {
      this.b = paramString;
    }

    public void a(boolean paramBoolean)
    {
      this.c = paramBoolean;
    }

    public String b()
    {
      return this.b;
    }

    public boolean c()
    {
      return this.c;
    }
  }

  public class b
  {
    public String a;
    public boolean b;

//    public b(String paramBoolean, boolean arg3)
//    {
//      this.a = paramBoolean;
//      boolean bool;
//      this.b = bool;
//    }

    public String a()
    {
      return this.a;
    }

    public boolean b()
    {
      return this.b;
    }
  }

  public class c
    implements Comparable<c>
  {
    int a;
    boolean b;

    public c()
    {
    }

    public int a()
    {
      return this.a;
    }

    public int a(c paramc)
    {
      return ("312".indexOf(new StringBuilder().append(this.a).append("").toString()) + "").compareTo("312".indexOf(new StringBuilder().append(paramc.a).append("").toString()) + "");
    }

    public void a(int paramInt)
    {
      this.a = paramInt;
    }

    public void a(boolean paramBoolean)
    {
      this.b = paramBoolean;
    }

    public boolean b()
    {
      return this.b;
    }

	@Override
	public int compareTo(c o) {
		// TODO Auto-generated method stub
		return 0;
	}
  }

  public class d
    implements Comparable<d>
  {
    int a;
    String[] b;

    public d()
    {
    }

    public int a()
    {
      return this.a;
    }

    public int a(d paramd)
    {
      return ("312".indexOf(new StringBuilder().append(this.a).append("").toString()) + "").compareTo("312".indexOf(new StringBuilder().append(paramd.a).append("").toString()) + "");
    }

    public void a(int paramInt)
    {
      this.a = paramInt;
    }

    public void a(String[] paramArrayOfString)
    {
      this.b = paramArrayOfString;
    }

    public String[] b()
    {
      return this.b;
    }

	@Override
	public int compareTo(d o) {
		// TODO Auto-generated method stub
		return 0;
	}
  }

  public class e
  {
    String a;
    boolean b;

    public e(String paramBoolean, boolean arg3)
    {
      this.a = paramBoolean;
      boolean bool = false;
      this.b = bool;
    }

    public String a()
    {
      return this.a;
    }

    public boolean b()
    {
      return this.b;
    }
  }

  public class f
  {
    String a;
    String b;
    int c;
    boolean d;

    public f(String paramString1, String paramInt, int paramBoolean, boolean arg5)
    {
      this.a = paramString1;
      this.b = paramInt;
      this.c = paramBoolean;
      boolean bool = false;
      this.d = bool;
    }

    public String a()
    {
      return this.a;
    }

    public String b()
    {
      return this.b;
    }

    public int c()
    {
      return this.c;
    }

    public boolean d()
    {
      return this.d;
    }
  }
}