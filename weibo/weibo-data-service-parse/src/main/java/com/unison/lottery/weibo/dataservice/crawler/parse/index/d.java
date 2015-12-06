package com.unison.lottery.weibo.dataservice.crawler.parse.index;

public class d
    implements Comparable
{

    public d(String s, String s1)
    {
        i = false;
        a = s;
        b = s1;
    }

    public d(String s, String s1, String s2, String s3)
    {
        i = false;
        a = s;
        b = s1;
        c = s2.equals("1");
        d = s3.equals("1");
    }

    public d(String s, String s1, String s2, String s3, String s4)
    {
        boolean flag = true;
        
        i = false;
        a = s;
        b = s1;
        boolean flag1;
        boolean flag2;
        if(s2.equals("1"))
            flag1 = flag;
        else
            flag1 = false;
        e = flag1;
        if(s3.equals("1"))
            flag2 = flag;
        else
            flag2 = false;
        f = flag2;
        if(!s4.equals("1"))
            flag = false;
        g = flag;
    }

    public int a(d d1)
    {
        StringBuilder stringbuilder = new StringBuilder();
        String s;
        StringBuilder stringbuilder1;
        String s1;
        String s2;
        StringBuilder stringbuilder2;
        String s3;
        StringBuilder stringbuilder3;
        String s4;
        if(h())
            s = "0";
        else
            s = "1";
        stringbuilder1 = stringbuilder.append(s);
        if(i())
            s1 = "0";
        else
            s1 = "1";
        s2 = stringbuilder1.append(s1).append((new StringBuilder()).append(d().charAt(0)).append("").toString().toUpperCase()).toString();
        stringbuilder2 = new StringBuilder();
        if(d1.h())
            s3 = "0";
        else
            s3 = "1";
        stringbuilder3 = stringbuilder2.append(s3);
        if(d1.i())
            s4 = "0";
        else
            s4 = "1";
        return s2.compareTo(stringbuilder3.append(s4).append((new StringBuilder()).append(d1.d().charAt(0)).append("").toString().toUpperCase()).toString());
    }

    public String a()
    {
        return h;
    }

    public void a(String s)
    {
        h = s;
    }

    public void a(boolean flag)
    {
        i = flag;
    }

    public void b(String s)
    {
        a = s;
    }

    public void b(boolean flag)
    {
        e = flag;
    }

    public boolean b()
    {
        return i;
    }

    public String c()
    {
        return a;
    }

    public void c(String s)
    {
        b = s;
    }

    public void c(boolean flag)
    {
        f = flag;
    }

    public int compareTo(Object obj)
    {
        return a((d)obj);
    }

    public String d()
    {
        return b;
    }

    public void d(boolean flag)
    {
        g = flag;
    }

    public void e(boolean flag)
    {
        c = flag;
    }

    public boolean e()
    {
        return e;
    }

    public void f(boolean flag)
    {
        d = flag;
    }

    public boolean f()
    {
        return f;
    }

    public boolean g()
    {
        return g;
    }

    public boolean h()
    {
        return c;
    }

    public boolean i()
    {
        return d;
    }

    String a;
    String b;
    boolean c;
    boolean d;
    boolean e;
    boolean f;
    boolean g;
    String h;
    boolean i;
}
