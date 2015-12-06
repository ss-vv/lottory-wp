package com.unison.lottery.weibo.dataservice.crawler.parse.realtime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 解析联赛数据，管理即时比分对应联赛
 * @author guixiangcui
 *
 */
public class f
{
  public static final int a = 1;
  public static final int b = 2;
  public static final int c = 3;
  public static final int d = 4;
  public static final int e = 5;
  public static final int f = 6;
  public static final int g = 7;
  List<u> h = new ArrayList();
  Map<String, u> i = new HashMap(); //目测为获取联赛编号所对应的联赛名称
  Set<String> j = new HashSet();
  String k;
  String l;

//  public static int a(String paramString)
//  {
//    int m;
//    if (paramString.equals(af.a(ScoreApplication.a(), 2131296421)))
//      m = 1;
//    while (true)
//    {
//      return m;
//      if (paramString.equals(af.a(ScoreApplication.a(), 2131296422)))
//        m = 2;
//      else if (paramString.equals(af.a(ScoreApplication.a(), 2131296423)))
//        m = 3;
//      else
//        m = 0;
//    }
//  }

//  public static String a(int paramInt)
//  {
//    String str;
//    if (paramInt == 0)
//      str = "";
//    while (true)
//    {
//      return str;
//      if (paramInt == 1)
//        str = af.a(ScoreApplication.a(), 2131296421);
//      else if (paramInt == 2)
//        str = af.a(ScoreApplication.a(), 2131296422);
//      else if (paramInt == 3)
//        str = af.a(ScoreApplication.a(), 2131296423);
//      else
//        str = "" + paramInt;
//    }
//  }

  public u a(String paramString1, String paramString2)
  {
    return (u)this.i.get(paramString1);
  }

  public Map<String, u> a()
  {
    return this.i;
  }

//  public void a(i parami, h paramh)
//  {
//    int m = -1 + this.h.size();
//    u localu;
//    if (m >= 0)
//    {
//      localu = (u)this.h.get(m);
//      if (localu.p() == 0)
//        this.h.remove(m);
//    }
//    label272: label276: 
//    while (true)
//    {
//      m--;
//      break;
//      int i3;
//      label73: int n;
//      if (paramh == h.a)
//      {
//        List localList3 = parami.a();
//        i3 = 0;
//        if (i3 >= localList3.size())
//          break label272;
//        if (((ay)localList3.get(i3)).j().equals(localu.s()))
//          n = 1;
//      }
//      while (true)
//      {
//        if (n != 0)
//          break label276;
//        this.h.remove(m);
//        break;
//        i3++;
//        break label73;
//        if (paramh == h.c)
//        {
//          List localList2 = parami.b();
//          for (int i2 = 0; ; i2++)
//          {
//            if (i2 >= localList2.size())
//              break label272;
//            if (((aw)localList2.get(i2)).j().equals(localu.s()))
//            {
//              n = 1;
//              break;
//            }
//          }
//        }
//        if (paramh == h.b)
//        {
//          List localList1 = parami.c();
//          int i1 = 0;
//          while (true)
//            if (i1 < localList1.size())
//            {
//              if (((ao)localList1.get(i1)).j().equals(localu.s()))
//              {
//                n = 1;
//                break;
//              }
//              i1++;
//              continue;
//              return;
//            }
//        }
//        n = 0;
//      }
//    }
//  }
//
  public void a(u paramu)
  {
    if (!this.i.containsKey(paramu.s()))
    {
      this.h.add(paramu);
      this.i.put(paramu.s(), paramu);
    }
  }
//
//  public void a(List<ar> paramList)
//  {
//    this.h.clear();
//    this.i.clear();
//    if ((paramList == null) || (paramList.size() == 0))
//      return;
//    int m = 0;
//    label34: ar localar;
//    if (m < paramList.size())
//    {
//      localar = (ar)paramList.get(m);
//      u localu1 = (u)this.i.get(localar.p());
//      if (localu1 == null)
//        break label89;
//      localu1.o();
//    }
//    while (true)
//    {
//      m++;
//      break label34;
//      break;
//      label89: u localu2 = new u();
//      localu2.i(localar.p());
//      localu2.j(localar.Q());
//      localu2.k(localar.R());
//      localu2.o();
//      this.h.add(localu2);
//      this.i.put(localar.p(), localu2);
//    }
//  }
//
//  public void a(Set<String> paramSet)
//  {
//    this.j = paramSet;
//  }
//
  /**
   * 解析联赛
   * @param paramArrayOfString
   */
  public void a(String[] paramArrayOfString)
  {
    if ((paramArrayOfString != null) && (paramArrayOfString.length != 0)){
    this.h.clear();
    this.i.clear();
    int m = 0;
    while (m < paramArrayOfString.length)
      if (!paramArrayOfString[m].equals(""))
    {
      
      String[] arrayOfString = paramArrayOfString[m].split("\\^", -1);
      if (arrayOfString.length >= 2)
      {
        u localu = new u(arrayOfString[1], arrayOfString[0]);
        this.h.add(localu);
        this.i.put(localu.s(), localu);
      }
      m++;
    }
  }
 }
//
//  public void a(String[] paramArrayOfString, int paramInt, boolean paramBoolean)
//  {
//    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0));
//    int m;
//    while (true)
//    {
//      return;
//      this.h.clear();
//      this.i.clear();
//      for (m = 0; m < paramArrayOfString.length; m++)
//        if (!paramArrayOfString[m].equals(""))
//          break label56;
//    }
//    label56: String[] arrayOfString = paramArrayOfString[m].split("\\^", -1);
//    Object localObject = null;
//    switch (paramInt)
//    {
//    case 5:
//    default:
//    case 6:
//    case 1:
//    case 3:
//    case 4:
//    case 2:
//    }
//    while (true)
//    {
//      this.h.add(localObject);
//      this.i.put(((u)localObject).s(), localObject);
//      break;
//      if (arrayOfString.length < 5)
//        break;
//      String str = arrayOfString[1];
//      if (ScoreApplication.S == 1)
//        str = arrayOfString[2];
//      while (true)
//      {
//        localObject = new u(arrayOfString[0], str, arrayOfString[4]);
//        break;
//        if (ScoreApplication.S == 2)
//          str = arrayOfString[3];
//      }
//      if (ScoreApplication.R == 1)
//      {
//        if (arrayOfString.length < 3)
//          break;
//        localObject = new u(arrayOfString[1], arrayOfString[0], arrayOfString[2]);
//        continue;
//      }
//      if (ScoreApplication.R == 2)
//      {
//        if ((arrayOfString.length < 2) || ((paramBoolean) && (!arrayOfString[1].equals("1"))))
//          break;
//        localObject = new u(arrayOfString[1], arrayOfString[0], null);
//        continue;
//      }
//      if (ScoreApplication.R == 3)
//      {
//        if (arrayOfString.length < 4)
//          break;
//        localObject = new u(arrayOfString[0], arrayOfString[1], arrayOfString[2], arrayOfString[3]);
//        continue;
//        if (arrayOfString.length < 6)
//          break;
//        localObject = new u(arrayOfString[0], arrayOfString[2], arrayOfString[3], arrayOfString[1], an.b(arrayOfString[4]), Arrays.asList(arrayOfString[5].split(",")));
//        continue;
//        if (ScoreApplication.R == 3)
//        {
//          if (arrayOfString.length < 3)
//            break;
//          if (z.b())
//          {
//            localObject = new u(arrayOfString[0], "", arrayOfString[1], arrayOfString[2]);
//          }
//          else
//          {
//            localObject = new u(arrayOfString[0], arrayOfString[1], "", arrayOfString[2]);
//            continue;
//            if (ScoreApplication.R == 1)
//            {
//              if (arrayOfString.length < 3)
//                break;
//              localObject = new u(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
//              continue;
//            }
//            if (ScoreApplication.R == 3)
//            {
//              if (arrayOfString.length < 4)
//                break;
//              localObject = new u(arrayOfString[0], arrayOfString[1], arrayOfString[2], arrayOfString[3]);
//              continue;
//            }
//            if (arrayOfString.length < 2)
//              break;
//            localObject = new u(arrayOfString[1], arrayOfString[0], "0");
//          }
//        }
//      }
//    }
//  }
//
//  public void a(String[] paramArrayOfString, boolean paramBoolean)
//  {
//    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
//      return;
//    this.h.clear();
//    this.i.clear();
//    int m = 0;
//    label30: if (m < paramArrayOfString.length)
//      if (!paramArrayOfString[m].equals(""))
//        break label53;
//    label157: 
//    while (true)
//    {
//      m++;
//      break label30;
//      break;
//      label53: String[] arrayOfString = paramArrayOfString[m].split("\\^", -1);
//      if (arrayOfString.length >= 4)
//      {
//        if (an.b(arrayOfString[2]) == 1);
//        for (boolean bool = true; ; bool = false)
//        {
//          if ((paramBoolean) && (!bool))
//            break label157;
//          u localu = new u(arrayOfString[1], arrayOfString[0], bool, an.b(arrayOfString[3]));
//          this.h.add(localu);
//          this.i.put(localu.s(), localu);
//          break;
//        }
//      }
//    }
//  }
//
  public u b(String paramString)
  {
    return (u)this.i.get(paramString);
  }
//
//  public void b()
//  {
//    this.h.clear();
//    this.i.clear();
//  }
//
//  public void b(String paramString1, String paramString2)
//  {
//    this.k = paramString1;
//    this.l = paramString2;
//  }
//
//  public void b(List<w> paramList)
//  {
//    this.h.clear();
//    this.i.clear();
//    if ((paramList == null) || (paramList.size() == 0))
//      return;
//    int m = 0;
//    label34: w localw;
//    if (m < paramList.size())
//    {
//      localw = (w)paramList.get(m);
//      u localu1 = (u)this.i.get(localw.b());
//      if (localu1 == null)
//        break label89;
//      localu1.o();
//    }
//    while (true)
//    {
//      m++;
//      break label34;
//      break;
//      label89: u localu2 = new u();
//      localu2.i(localw.b());
//      localu2.j(localw.n());
//      localu2.k(localw.v());
//      localu2.o();
//      this.h.add(localu2);
//      this.i.put(localw.b(), localu2);
//    }
//  }
//
//  public List<u> c(String paramString)
//  {
//    ArrayList localArrayList = new ArrayList();
//    if ((this.h == null) || (this.h.size() == 0));
//    for (Object localObject = null; ; localObject = localArrayList)
//    {
//      return localObject;
//      Iterator localIterator = this.h.iterator();
//      while (localIterator.hasNext())
//      {
//        u localu = (u)localIterator.next();
//        if (localu.v().equals(paramString))
//          localArrayList.add(localu);
//      }
//    }
//  }
//
//  public Set<String> c()
//  {
//    return this.j;
//  }
//
//  public void c(List<u> paramList)
//  {
//    this.j.clear();
//    Iterator localIterator = paramList.iterator();
//    while (localIterator.hasNext())
//    {
//      u localu = (u)localIterator.next();
//      this.j.add(localu.a);
//    }
//  }
//
//  public List<u> d()
//  {
//    return this.h;
//  }
//
//  public void d(String paramString)
//  {
//    if ((paramString == null) || (paramString.equals("")))
//      return;
//    this.h.clear();
//    this.i.clear();
//    String[] arrayOfString1 = paramString.split("\\!", -1);
//    int m = 0;
//    label44: String[] arrayOfString2;
//    if (m < arrayOfString1.length)
//    {
//      arrayOfString2 = arrayOfString1[m].split("\\^", -1);
//      if (arrayOfString2.length >= 8)
//        break label76;
//    }
//    while (true)
//    {
//      m++;
//      break label44;
//      break;
//      label76: u localu = new u(arrayOfString2[0], arrayOfString2[1], arrayOfString2[2], arrayOfString2[3], arrayOfString2[4], arrayOfString2[5], arrayOfString2[6], arrayOfString2[7]);
//      this.i.put(arrayOfString2[0], localu);
//      this.h.add(localu);
//    }
//  }
//
//  public void d(List<u> paramList)
//  {
//    this.h = paramList;
//  }
//
//  public List<u> e()
//  {
//    ArrayList localArrayList = new ArrayList();
//    Iterator localIterator = this.h.iterator();
//    while (localIterator.hasNext())
//    {
//      u localu = (u)localIterator.next();
//      if (localu.f)
//        localArrayList.add(localu);
//    }
//    return localArrayList;
//  }
//
//  public List<u> f()
//  {
//    ArrayList localArrayList = new ArrayList();
//    Iterator localIterator = this.h.iterator();
//    while (localIterator.hasNext())
//    {
//      u localu = (u)localIterator.next();
//      if (localu.s == com.bet007.mobile.score.c.i.c.a())
//        localArrayList.add(localu);
//    }
//    return localArrayList;
//  }
//
//  public List<u> g()
//  {
//    ArrayList localArrayList = new ArrayList();
//    Iterator localIterator = this.h.iterator();
//    while (localIterator.hasNext())
//    {
//      u localu = (u)localIterator.next();
//      if (localu.s == com.bet007.mobile.score.c.i.d.a())
//        localArrayList.add(localu);
//    }
//    return localArrayList;
//  }
//
//  public List<u> h()
//  {
//    ArrayList localArrayList = new ArrayList();
//    Iterator localIterator = this.h.iterator();
//    while (localIterator.hasNext())
//    {
//      u localu = (u)localIterator.next();
//      if (localu.s == com.bet007.mobile.score.c.i.e.a())
//        localArrayList.add(localu);
//    }
//    return localArrayList;
//  }
//
//  public String[] i()
//  {
//    String[] arrayOfString = new String[2];
//    arrayOfString[0] = this.k;
//    arrayOfString[1] = this.l;
//    return arrayOfString;
//  }
//
//  public boolean j()
//  {
//    if ((this.h != null) && (this.h.size() > 0));
//    for (boolean bool = true; ; bool = false)
//      return bool;
//  }
}