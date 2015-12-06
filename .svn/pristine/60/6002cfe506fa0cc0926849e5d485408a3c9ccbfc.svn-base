package com.unison.lottery.weibo.dataservice.crawler.parse.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.ar;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.u;
import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.w;

public class f
{

	public static final int a = 1;
	 public static final int b = 2;
	 public static final int c = 3;
	 public static final int d = 4;
	 public static final int e = 5;
	 public static final int f = 6;
	 public static final int g = 7;
	 List h;
	 Map i;
	 Set j;
	 String k;
	 String l;
	
 public f()
 {
     h = new ArrayList();
     i = new HashMap();
     j = new HashSet();
 }

 public static int a(String s)
 {
     int i1;
     if(s.equals("常规赛"))
         i1 = 1;
     else
     if(s.equals("季后赛"))
         i1 = 2;
     else
     if(s.equals("季前赛"))
         i1 = 3;
     else
         i1 = 0;
     return i1;
 }

 public static String a(int i1)
 {
     String s = null;
     if(i1 == 0)
         s = "";
     else
     if(i1 == 1)
         s = "������";
//     else
//     if(i1 == 2)
//         s = af.a(ScoreApplication.a(), 0x7f0900a6);
//     else
//     if(i1 == 3)
//         s = af.a(ScoreApplication.a(), 0x7f0900a7);
//     else
//         s = (new StringBuilder()).append("").append(i1).toString();
     return s;
 }

 public u a(String s, String s1)
 {
     return (u)i.get(s);
 }

 public Map a()
 {
     return i;
 }

// public void a(com.bet007.mobile.score.h.i i1, h h1)
// {
//     int j1 = -1 + h.size();
//_L5:
//     if(j1 < 0) goto _L2; else goto _L1
//_L1:
//     u u1 = (u)h.get(j1);
//     if(u1.p() != 0) goto _L4; else goto _L3
//_L3:
//     h.remove(j1);
//_L10:
//     j1--;
//       goto _L5
//_L4:
//     if(h1 != h.a) goto _L7; else goto _L6
//_L6:
//     List list2;
//     int i2;
//     list2 = i1.a();
//     i2 = 0;
//_L11:
//     if(i2 >= list2.size())
//         break MISSING_BLOCK_LABEL_272;
//     if(!((ay)list2.get(i2)).j().equals(u1.s())) goto _L9; else goto _L8
//_L8:
//     boolean flag = true;
//_L12:
//     if(!flag)
//         h.remove(j1);
//       goto _L10
//_L9:
//     i2++;
//       goto _L11
//_L7:
//     List list1;
//     int l1;
//     if(h1 != h.c)
//         break MISSING_BLOCK_LABEL_205;
//     list1 = i1.b();
//     l1 = 0;
//_L13:
//label0:
//     {
//         if(l1 >= list1.size())
//             break MISSING_BLOCK_LABEL_272;
//         if(!((aw)list1.get(l1)).j().equals(u1.s()))
//             break label0;
//         flag = true;
//     }
//       goto _L12
//     l1++;
//       goto _L13
//     List list;
//     int k1;
//     if(h1 != h.b)
//         break MISSING_BLOCK_LABEL_272;
//     list = i1.c();
//     k1 = 0;
//_L14:
//label1:
//     {
//         if(k1 >= list.size())
//             break MISSING_BLOCK_LABEL_272;
//         if(!((ao)list.get(k1)).j().equals(u1.s()))
//             break label1;
//         flag = true;
//     }
//       goto _L12
//     k1++;
//       goto _L14
//_L2:
//     return;
//       goto _L12
//     flag = false;
//       goto _L12
// }

 public void a(u u1)
 {
     if(!i.containsKey(u1.s()))
     {
         h.add(u1);
         i.put(u1.s(), u1);
     }
 }

 public void a(List list)
 {
     h.clear();
     i.clear();
     if(list != null && list.size() != 0)
     {
         int i1 = 0;
         while(i1 < list.size()) 
         {
             ar ar1 = (ar)list.get(i1);
             u u1 = (u)i.get(ar1.p());
             if(u1 != null)
             {
                 u1.o();
             } else
             {
                 u u2 = new u();
                 u2.i(ar1.p());
                 u2.j(ar1.Q());
                 u2.k(ar1.R());
                 u2.o();
                 h.add(u2);
                 i.put(ar1.p(), u2);
             }
             i1++;
         }
     }
 }

 public void a(Set set)
 {
     j = set;
 }

 public void a(String as[])
 {
     if(as != null && as.length != 0)
     {
         h.clear();
         i.clear();
         int i1 = 0;
         while(i1 < as.length) 
         {
             if(!as[i1].equals(""))
             {
                 String as1[] = as[i1].split("\\^", -1);
                 if(as1.length >= 2)
                 {
                     u u1 = new u(as1[1], as1[0]);
                     h.add(u1);
                     i.put(u1.s(), u1);
                 }
             }
             i1++;
         }
     }
 }

 public void a(String[] paramArrayOfString, int paramInt, boolean paramBoolean)
 {
   if ((paramArrayOfString != null) && (paramArrayOfString.length != 0)){
   int m;
     this.h.clear();
     this.i.clear();
     for (m = 0; m < paramArrayOfString.length; m++){
       if (!paramArrayOfString[m].equals("")){
      String[] arrayOfString = paramArrayOfString[m].split("\\^", -1);
   Object localObject = null;
   
//   ����paramInt�̶�Ϊ2:
	   if (arrayOfString.length >= 3){
	         localObject = new u(arrayOfString[0], arrayOfString[1], arrayOfString[2]);
	         
	  	   this.h.add(localObject);
		     this.i.put(((u)localObject).s(), localObject);
	       }
     }
    }
   }
 }
//   switch (paramInt)
//   {
//   case 5:
	   
//	   this.h.add(localObject);
//	     this.i.put(((u)localObject).s(), localObject);
//	     break;
	     
//   default:
//	   
//	   if (arrayOfString.length < 5)
//	       break;
//	     String str = arrayOfString[1];
//	     if (ScoreApplication.S == 1)
//	       str = arrayOfString[2];
//	     while (true)
//	     {
//	       localObject = new u(arrayOfString[0], str, arrayOfString[4]);
//	       break;
//	       if (ScoreApplication.S == 2)
//	         str = arrayOfString[3];
//	     }
//	     if (ScoreApplication.R == 1)
//	     {
//	       if (arrayOfString.length < 3)
//	         break;
//	       localObject = new u(arrayOfString[1], arrayOfString[0], arrayOfString[2]);
//	       continue;
//	     }
//	     
//   case 6:
//	   
//	   if (ScoreApplication.R == 2)
//	     {
//	       if ((arrayOfString.length < 2) || ((paramBoolean) && (!arrayOfString[1].equals("1"))))
//	         break;
//	       localObject = new u(arrayOfString[1], arrayOfString[0], null);
//	       continue;
//	     }
//   case 1:
//	   if (ScoreApplication.R == 3)
//	     {
//	       if (arrayOfString.length < 4)
//	         break;
//	       localObject = new u(arrayOfString[0], arrayOfString[1], arrayOfString[2], arrayOfString[3]);
//   case 3:
//	   if (arrayOfString.length < 6)
//	         break;
//	       localObject = new u(arrayOfString[0], arrayOfString[2], arrayOfString[3], arrayOfString[1], an.b(arrayOfString[4]), Arrays.asList(arrayOfString[5].split(",")));
//	       continue;
//   case 4:
//	   
//	   if (ScoreApplication.R == 3)
//       {
//         if (arrayOfString.length < 3)
//           break;
//         if (z.b())
//         {
//           localObject = new u(arrayOfString[0], "", arrayOfString[1], arrayOfString[2]);
//         }
//         else
//         {
//           localObject = new u(arrayOfString[0], arrayOfString[1], "", arrayOfString[2]);
//   case 2:
//	   if (ScoreApplication.R == 1)
//       {
        
	   
//   }
//   while (true)
//   {
//     
//     
//     
//    
//       continue;
//      
//      
//           continue;
//           
//           if (ScoreApplication.R == 3)
//           {
//             if (arrayOfString.length < 4)
//               break;
//             localObject = new u(arrayOfString[0], arrayOfString[1], arrayOfString[2], arrayOfString[3]);
//             continue;
//           }
//           if (arrayOfString.length < 2)
//             break;
//           localObject = new u(arrayOfString[1], arrayOfString[0], "0");
//         }
//       }
//     }
//   }
// }

 public void a(String as[], boolean flag)
 {
     if(as != null && as.length != 0)
     {
         h.clear();
         i.clear();
         int i1 = 0;
         while(i1 < as.length) 
         {
             if(!as[i1].equals(""))
             {
                 String as1[] = as[i1].split("\\^", -1);
                 if(as1.length >= 4)
                 {
                     boolean flag1;
                     if(an.b(as1[2]) == 1)
                         flag1 = true;
                     else
                         flag1 = false;
                     if(!flag || flag1)
                     {
                         u u1 = new u(as1[1], as1[0], flag1, an.b(as1[3]));
                         h.add(u1);
                         i.put(u1.s(), u1);
                     }
                 }
             }
             i1++;
         }
     }
 }

 public u b(String s)
 {
     return (u)i.get(s);
 }

 public void b()
 {
     h.clear();
     i.clear();
 }

 public void b(String s, String s1)
 {
     k = s;
     l = s1;
 }

 public void b(List list)
 {
     h.clear();
     i.clear();
     if(list != null && list.size() != 0)
     {
         int i1 = 0;
         while(i1 < list.size()) 
         {
             w w1 = (w)list.get(i1);
             u u1 = (u)i.get(w1.b());
             if(u1 != null)
             {
                 u1.o();
             } else
             {
                 u u2 = new u();
                 u2.i(w1.b());
                 u2.j(w1.n());
                 u2.k(w1.v());
                 u2.o();
                 h.add(u2);
                 i.put(w1.b(), u2);
             }
             i1++;
         }
     }
 }

 public List c(String s)
 {
     ArrayList arraylist = new ArrayList();
     Object obj;
     if(h == null || h.size() == 0)
     {
         obj = null;
     } else
     {
         Iterator iterator = h.iterator();
         do
         {
             if(!iterator.hasNext())
                 break;
             u u1 = (u)iterator.next();
             if(u1.v().equals(s))
                 arraylist.add(u1);
         } while(true);
         obj = arraylist;
     }
     return ((List) (obj));
 }

 public Set c()
 {
     return j;
 }

 public void c(List list)
 {
     j.clear();
     u u1;
     for(Iterator iterator = list.iterator(); iterator.hasNext(); j.add(u1.a))
         u1 = (u)iterator.next();

 }

 public List d()
 {
     return h;
 }

 public void d(String s)
 {
     if(s != null && !s.equals(""))
     {
         h.clear();
         i.clear();
         String as[] = s.split("\\!", -1);
         int i1 = 0;
         while(i1 < as.length) 
         {
             String as1[] = as[i1].split("\\^", -1);
             if(as1.length >= 8)
             {
                 u u1 = new u(as1[0], as1[1], as1[2], as1[3], as1[4], as1[5], as1[6], as1[7]);
                 i.put(as1[0], u1);
                 h.add(u1);
             }
             i1++;
         }
     }
 }

 public void d(List list)
 {
     h = list;
 }

 public List e()
 {
     ArrayList arraylist = new ArrayList();
     Iterator iterator = h.iterator();
     do
     {
         if(!iterator.hasNext())
             break;
         u u1 = (u)iterator.next();
         if(u1.f)
             arraylist.add(u1);
     } while(true);
     return arraylist;
 }

// public List f()
// {
//     ArrayList arraylist = new ArrayList();
//     Iterator iterator = h.iterator();
//     do
//     {
//         if(!iterator.hasNext())
//             break;
//         u u1 = (u)iterator.next();
//         if(u1.s == i.c.a())
//             arraylist.add(u1);
//     } while(true);
//     return arraylist;
// }

// public List g()
// {
//     ArrayList arraylist = new ArrayList();
//     Iterator iterator = h.iterator();
//     do
//     {
//         if(!iterator.hasNext())
//             break;
//         u u1 = (u)iterator.next();
//         if(u1.s == i.d.a())
//             arraylist.add(u1);
//     } while(true);
//     return arraylist;
// }

// public List h()
// {
//     ArrayList arraylist = new ArrayList();
//     Iterator iterator = h.iterator();
//     do
//     {
//         if(!iterator.hasNext())
//             break;
//         u u1 = (u)iterator.next();
//         if(u1.s == realtime.i.e.a())
//             arraylist.add(u1);
//     } while(true);
//     return arraylist;
// }

 public String[] i()
 {
     String as[] = new String[2];
     as[0] = k;
     as[1] = l;
     return as;
 }

 public boolean j()
 {
     boolean flag;
     if(h != null && h.size() > 0)
         flag = true;
     else
         flag = false;
     return flag;
 }

 
}

