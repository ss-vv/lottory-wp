package com.unison.lottery.weibo.dataservice.crawler.parse.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;

import com.unison.lottery.weibo.dataservice.crawler.parse.repository.h;

public class b
{
	
	h a;
	 List b;
	 List c;
	 List d;
	 Map e;
	 private Set f;
	 private Set g;
	 private Set h;

 public b()
 {
//     a = h.a;
     b = new ArrayList();
     c = new ArrayList();
     d = new ArrayList();
     e = new HashMap();
     f = new HashSet();
     g = new HashSet();
     h = new HashSet();
 }

// private void a(Context context)
// {
//     int i = 0;
//     f.clear();
//     g.clear();
//     h.clear();
//     String s = ScoreApplication.a(context, "ShareKey_ClientSet_Zq_Index_YP_Company", "");
//     if(s.equals(""))
//     {
//         for(int i1 = 0; i1 < 4 && i1 < -1 + d.size(); i1++)
//             f.add(d.get(i1));
//
//     } else
//     {
//         String as[] = s.split(",");
//         for(int j = 0; j < as.length; j++)
//         {
//             d d3 = (d)e.get(as[j]);
//             if(d3 != null)
//                 f.add(d3);
//         }
//
//     }
//     String s1 = ScoreApplication.a(context, "ShareKey_ClientSet_Zq_Index_OP_Company", "");
//     if(s1.equals(""))
//     {
//         for(int l = 0; l < 4 && l < -1 + b.size(); l++)
//             g.add(b.get(l));
//
//     } else
//     {
//         String as1[] = s1.split(",");
//         for(int k = 0; k < as1.length; k++)
//         {
//             d d2 = (d)e.get(as1[k]);
//             if(d2 != null)
//                 g.add(d2);
//         }
//
//     }
//     String s2 = ScoreApplication.a(context, "ShareKey_ClientSet_Zq_Index_DX_Company", "");
//     if(s2.equals(""))
//     {
//         for(; i < 4 && i < -1 + c.size(); i++)
//             h.add(c.get(i));
//
//     } else
//     {
//         for(String as2[] = s2.split(","); i < as2.length; i++)
//         {
//             d d1 = (d)e.get(as2[i]);
//             if(d1 != null)
//                 h.add(d1);
//         }
//
//     }
// }

 private void a(d d1)
 {
     if(d1.e())
         d.add(d1);
     if(d1.f())
         c.add(d1);
     if(d1.g())
         b.add(d1);
 }

 public h a(int i)
 {
     h h1 = null;
//     if(i == 2)
//         h1 = h.b;
//     else
//     if(i == 3)
//         h1 = h.c;
//     else
//         h1 = h.a;
     return h1;
 }

 public String a(String s)
 {
     String s1;
     if(e.get(s) == null)
         s1 = "";
     else
         s1 = ((d)e.get(s)).d();
     return s1;
 }

 public Set a()
 {
     return f;
 }

 public void a(Context context, String as[])
 {
     if(as != null && as.length != 0)
     {
         b.clear();
         c.clear();
         d.clear();
         e.clear();
         int i = 0;
         while(i < as.length) 
         {
             String as1[] = as[i].split("\\^", -1);
             if(as1.length >= 5)
             {
                 String s = as1[0];
                 d d1 = new d(s, as1[1], as1[2], as1[3], as1[4]);
                 a(d1);
                 e.put(s, d1);
             }
             i++;
         }
//         a(context);
     }
 }

 public void a(h h1)
 {
     a = h1;
 }

 public void a(Set set)
 {
     f = set;
 }

 public Set b()
 {
     return g;
 }

 public void b(Set set)
 {
     g = set;
 }

 public Set c()
 {
     return h;
 }

 public void c(Set set)
 {
     h = set;
 }

 public h d()
 {
     return a;
 }

 public List e()
 {
     return d;
 }

 public List f()
 {
     return c;
 }

 public List g()
 {
     return b;
 }

 
}

