package com.unison.lottery.weibo.dataservice.crawler.parse.index;

/**
 * ��ʱָ�����ģ�� ����
 * @author guixiangcui
 *
 */
public class at
{
  static int o = 10000;
  String matchId;
  String companyId;
  String oddsId;
  String j;
  public int k = 0;
  public int l = 0;
  public int m = 0;
  public long n = System.currentTimeMillis();
  int p;
  int q;

  public at(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.matchId = paramString1;
    this.companyId = paramString2;
    this.oddsId = paramString3;
    this.j = paramString4;
  }

  public void a(int paramInt)
  {
    this.p = paramInt;
  }

  public void a(int paramInt, boolean paramBoolean)
  {
    if (!paramBoolean){
    	if ((paramInt & this.q) != paramInt)
            this.q = (paramInt + this.q);
    }else{
    	if ((paramInt & this.p) != paramInt)
            this.p = (paramInt + this.p);
    	
    }
  }

  public void b(int paramInt)
  {
    this.q = paramInt;
  }

  public void b(int paramInt, boolean paramBoolean)
  {
    
    if(!paramBoolean){
            if((paramInt & q) == paramInt)
                q = q - paramInt;
    }  else {
          if((paramInt & p) == paramInt)
                p = p - paramInt;	
    }
    

  }

  public int g()
  {
    return this.p;
  }

  public void g(String paramString)
  {
    this.j = paramString;
  }

  public int h()
  {
    return this.q;
  }

  public void h(String paramString)
  {
    this.matchId = paramString;
  }

  public void i(String paramString)
  {
    this.companyId = paramString;
  }

  public boolean i()
  {
    if (System.currentTimeMillis() - this.n <= o);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public String j()
  {
    return this.j;
  }

  public void j(String paramString)
  {
    this.oddsId = paramString;
  }

  public String k()
  {
    return this.matchId;
  }

  public String l()
  {
    return this.companyId;
  }

  public String m()
  {
    return this.oddsId;
  }
}
