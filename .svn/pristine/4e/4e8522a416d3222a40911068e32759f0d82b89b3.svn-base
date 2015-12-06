package com.unison.lottery.weibo.dataservice.crawler.parse.repository;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.u;



public class l
{
	/**
	 * 洲际
	 */
 public  List<f> a = new ArrayList();
  /**
   * 国家
   */
  public List<g> b = new ArrayList();
  /**
   * 赛事
   */
  public List<u> c = new ArrayList();
  List<h> d = new ArrayList();
//  c e = c.b;
  String f;
  List<String> g = new ArrayList();

  private boolean a(String paramString1, String paramString2)
  {
    boolean bool = false;
    String str1 = paramString1.toLowerCase();
    String str2 = paramString2.toLowerCase();
    int i = 0;
    int j = 0;
    while (i < str1.length())
    {
      if (str2.contains(str1.substring(i, i + 1)))
        j++;
      i++;
    }
    if (j == str1.length())
      bool = true;
    return bool;
  }

  public List<String> a()
  {
    return this.g;
  }

//  public List<u> a(List<u> paramList, String paramString)
//  {
//    if ((paramList == null) || (paramList.size() == 0));
//    ArrayList localArrayList;
//    for (Object localObject = null; ; localObject = localArrayList)
//    {
//      return localObject;
//      String str = paramString.trim();
//      localArrayList = new ArrayList();
//      Iterator localIterator = paramList.iterator();
//      while (localIterator.hasNext())
//      {
//        u localu = (u)localIterator.next();
//        if ((localu.q() != null) && (a(str, localu.q())))
//        {
//          localArrayList.add(localu);
//        }
//        else if ((localu.y() != null) && (a(str, localu.y())))
//        {
//          localArrayList.add(localu);
//        }
//        else
//        {
//          g localg = b(localu.v());
//          if ((localg != null) && (localg.b() != null) && (localg.b().length() != 0) && (a(str, localg.b())))
//            localArrayList.add(localu);
//        }
//      }
//    }
//  }

//  public void a(c paramc)
//  {
//    this.e = paramc;
//  }

  public void a(String paramString)
  {
    this.f = paramString;
  }

  public void a(String as[])
  {
      if(as != null && as.length != 0)
      {
          a.clear();
          int i = 0;
          while(i < as.length) 
          {
              String as1[] = as[i].split("\\^", -1);
              if(as1.length >= 2)
                  a.add(new f(as1[0], as1[1]));
              i++;
          }
      }
  }


//  public g b(String paramString)
//  {
//    Iterator localIterator = this.b.iterator();
//    g localg;
//    do
//    {
//      if (!localIterator.hasNext())
//        break;
//      localg = (g)localIterator.next();
//    }
//    while (!localg.a().endsWith(paramString));
//    while (true)
//    {
//      return localg;
//      localg = null;
//    }
//  }

  public String b()
  {
    return this.f;
  }

  
  public void b(String as[])
  {
      if(as != null && as.length != 0)
      {
          b.clear();
          int i = 0;
          while(i < as.length) 
          {
              String as1[] = as[i].split("\\^", -1);
//              int flag = ScoreApplication.R;
              int flag = 1;
              if((flag != 1 || as1.length >= 3) && (flag != 2 || as1.length >= 2))
                  if(flag == 1)
                      b.add(new g(as1[0], as1[2], as1[1]));
                  else
                      b.add(new g(as1[0], as1[1], ""));
              i++;
          }
      }
  }

  public List<f> c()
  {
    return this.a;
  }

  public void c(String s)
  {
      if(s != null && !s.equals(""))
      {
          g.clear();
          String as[] = s.split("\\^", -1);
          int i = 0;
          while(i < as.length) 
          {
              g.add(as[i]);
              i++;
          }
      }
  }

  public void c(String as[])
  {
      if(as != null && as.length != 0)
      {
          c.clear();
          int i = 0;
          while(i < as.length) 
          {
              String as1[] = as[i].split("\\^", -1);
              if(as1.length >= 6)
                  c.add(new u(as1[0], as1[2], as1[3], as1[1], an.b(as1[4]), Arrays.asList(as1[5].split(","))));
              i++;
          }
      }
  }

  public List<u> d()
  {
    return this.c;
  }

  public void d(String as[])
  {
      if(as != null && as.length != 0)
      {
          d.clear();
          int i = 0;
          while(i < as.length) 
          {
              String as1[] = as[i].split("\\^", -1);
              if(as1.length >= 3)
              {
                  h h1 = new h(as1[0], as1[1], as1[2]);
                  d.add(h1);
              }
              i++;
          }
      }
  }

//  public c e()
//  {
//    return this.e;
//  }

//  public List<g> f()
//  {
//    Object localObject;
//    if ((this.b == null) || (this.b.size() == 0))
//      localObject = null;
//    while (true)
//    {
//      return localObject;
//      if (ScoreApplication.R == 1)
//      {
//        ArrayList localArrayList = new ArrayList();
//        Iterator localIterator = this.b.iterator();
//        while (localIterator.hasNext())
//        {
//          g localg = (g)localIterator.next();
//          if (an.b(localg.c()) == e().a())
//            localArrayList.add(localg);
//        }
//        localObject = localArrayList;
//      }
//      else
//      {
//        localObject = this.b;
//      }
//    }
//  }

  public List<h> g()
  {
    return this.d;
  }
}
