package com.unison.lottery.weibo.dataservice.crawler.parse.realtime;
import java.awt.Color;
import java.util.Calendar;
import java.util.Date;

/**
 * �����������ʱ�ȷ����ģ��
 * @author guixiangcui
 *
 */

public class ar implements Comparable {
	
//	977480^358^-1^20141015080000^20141015090423^�������Ǿ���^����^2^0^0^0^0^0^1^2^0.25^^^0^10^3^^^0
	static final int a = -1;
	static final int b[];
	long A;
	String B;
	/**
	 * ���� 90����[2-0],���غ�[2-2],����[7-6],�����ѧ��Ӯ
	 */
	String C;
	String D;
	long E;
	String F;
	boolean G;
	boolean H;
	boolean I;
	String J;
	String K;
	String L;
	String M;
	String N;
	String O;
	String P;
	String Q;
	/**
	 * 1,10
	 */
	String R;
	/**
	 * 3,3
	 */
	String S;
	String T;
	String U;
	String V;
	boolean W;
	/**
	 * ���±��1064563
	 */
	public String c;
	/**
	 * 1356 ������
	 */
	String d;
	/**
	 * 3����״̬
	 */
	int e;
	boolean f;
	/**
	 * ����ʱ��time
	 */
	String g;
	/**
	 * ����ʱ��time
	 */
	String h;
	/**
	 * ������ƣ�����
	 */
	public String i; 
	/**
	 * �Ͷ���ƣ��в�
	 */
	public String j;
	/**
	 * ���ӽ���
	 */
	public String k;
	/**
	 * �Ͷӽ���
	 */
	public String l;
	String m;
	String n;
	/**
	 * ���Ӻ���
	 */
	public String o;
	/**
	 * �ͶӺ���
	 */
	public String p;
	/**
	 * ���ӻ���
	 */
	public String q;
	/**
	 * �Ͷӻ���
	 */
	public String r;
	/**
	 * -1.5 ����,0.25
	 */
	String s;
	/**
	 * �������������������
	 */
	String t;
	/**
	 * ������ƣ�������
	 */
	String u;
	String v;
	/**
	 * -1736960
	 */
	int w; 
	boolean x;
	String y;
	boolean z;
	

	public ar() {
		s = "";
		t = null;
		w = -1;
		x = false;
		y = "";
		z = false;
		A = 0L;
		B = "";
		C = "";
		D = "";
		W = false;
	}

	public ar(int i1, String s1, String s2) {
		s = "";
		t = null;
		w = -1;
		x = false;
		y = "";
		z = false;
		A = 0L;
		B = "";
		C = "";
		D = "";
		W = false;
		e = i1;
		g = s1;
		h = s2;
	}

	public ar(String s1, String s2, int i1, String s3, String s4, String s5,
			String s6, String s7) {
		s = "";
		t = null;
		w = -1;
		x = false;
		y = "";
		z = false;
		A = 0L;
		B = "";
		C = "";
		D = "";
		W = false;
		c = s1;
		d = s2;
		e = i1;
		g = s3;
		i = s4;
		j = s5;
		k = s6;
		l = s7;
	}

	public ar(String s1, String s2, int i1, String s3, String s4, String s5,
			String s6, String s7, String s8, String s9, String s10, String s11,
			String s12, String s13, String s14, String s15, String s16,
			String s17, String s18, boolean flag, boolean flag1) {
		s = "";
		t = null;
		w = -1;
		x = false;
		y = "";
		z = false;
		A = 0L;
		B = "";
		C = "";
		D = "";
		W = false;
		c = s1;
		d = s2;
		e = i1;
		g = s3;
		h = s4;
		i = s5;
		j = s6;
		k = s7;
		l = s8;
		m = s9;
		n = s10;
		o = s11;
		p = s12;
		q = s13;
		r = s14;
		R = s15;
		S = s16;
		s = s17;
		F = s18;
		x = flag;
		H = flag1;
	}

	public ar(String s1, String s2, int i1, String s3, String s4, String s5,
			String s6, String s7, String s8, String s9, String s10, String s11,
			String s12, String s13, String s14, String s15, String s16,
			String s17, boolean flag, String s18, String s19, String s20,
			String s21, boolean flag1, boolean flag2) {
		s = "";
		t = null;
		w = -1;
		x = false;
		y = "";
		z = false;
		A = 0L;
		B = "";
		C = "";
		D = "";
		W = false;
		c = s1;
		d = s2;
		e = i1;
		g = s3;
		h = s4;
		i = s5;
		j = s6;
		k = s7;
		l = s8;
		m = s9;
		n = s10;
		o = s11;
		p = s12;
		q = s13;
		r = s14;
		s = s15;
		y = s16;
		B = s17;
		z = flag;
		R = s18;
		S = s19;
		x = flag2;
		D = s21;
		C = s20;
		G = flag1;
	}

	public ar(boolean flag, String s1) {
		s = "";
		t = null;
		w = -1;
		x = false;
		y = "";
		z = false;
		A = 0L;
		B = "";
		C = "";
		D = "";
		W = false;
		f = flag;
		g = s1;
	}

	public ar(boolean flag, String s1, String s2, String s3, String s4,
			String s5, String s6) {
		s = "";
		t = null;
		w = -1;
		x = false;
		y = "";
		z = false;
		A = 0L;
		B = "";
		C = "";
		D = "";
		W = false;
		c = "0";
		I = flag;
		K = s1;
		L = s2;
		J = s3;
		M = s4;
		N = s5;
		O = s6;
	}

	private boolean B(String s1) {
		boolean flag = false;
//		if (s1 != null && s1.length() > 0 && an.b(s1) > 0)
//			flag = true;
//		else
//			flag = false;
		return flag;
	}

	public static ar a(a a1) {
		return new ar(true, a1.f(), a1.e(), a1.i(), a1.g(), a1.h(), a1.j());
	}

	// public static d b(int i1)
	// {
	// i1;
	// JVM INSTR tableswitch -14 4: default 92
	// // -14 105
	// // -13 98
	// // -12 105
	// // -11 105
	// // -10 105
	// // -9 92
	// // -8 92
	// // -7 92
	// // -6 92
	// // -5 92
	// // -4 92
	// // -3 92
	// // -2 92
	// // -1 105
	// // 0 92
	// // 1 98
	// // 2 98
	// // 3 98
	// // 4 98;
	// goto _L1 _L2 _L3 _L2 _L2 _L2 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L2 _L1 _L3
	// _L3 _L3 _L3
	// _L1:
	// d d1 = d.b;
	// _L5:
	// return d1;
	// _L3:
	// d1 = d.c;
	// continue; /* Loop/switch isn't completed */
	// _L2:
	// d1 = d.d;
	// if(true) goto _L5; else goto _L4
	// _L4:
	// }

	// public static String c(int i1)
	// {
	// i1;
	// JVM INSTR tableswitch -14 3: default 88
	// // -14 194
	// // -13 182
	// // -12 170
	// // -11 158
	// // -10 218
	// // -9 88
	// // -8 88
	// // -7 88
	// // -6 88
	// // -5 88
	// // -4 88
	// // -3 88
	// // -2 88
	// // -1 206
	// // 0 110
	// // 1 122
	// // 2 134
	// // 3 146;
	// goto _L1 _L2 _L3 _L4 _L5 _L6 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L7 _L8 _L9
	// _L10 _L11
	// _L1:
	// String s1 = (new StringBuilder()).append(i1).append("").toString();
	// _L13:
	// return s1;
	// _L8:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900ec);
	// continue; /* Loop/switch isn't completed */
	// _L9:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900ee);
	// continue; /* Loop/switch isn't completed */
	// _L10:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900ef);
	// continue; /* Loop/switch isn't completed */
	// _L11:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f0);
	// continue; /* Loop/switch isn't completed */
	// _L5:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f1);
	// continue; /* Loop/switch isn't completed */
	// _L4:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f2);
	// continue; /* Loop/switch isn't completed */
	// _L3:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f3);
	// continue; /* Loop/switch isn't completed */
	// _L2:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f4);
	// continue; /* Loop/switch isn't completed */
	// _L7:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f5);
	// continue; /* Loop/switch isn't completed */
	// _L6:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f6);
	// if(true) goto _L13; else goto _L12
	// _L12:
	// }

	public static int d(int i1) {
		int j1 = i1 % b.length;
		return b[j1];
	}

	// public static boolean e(int i1)
	// {
	// boolean flag = false;
	// i1;
	// JVM INSTR tableswitch -14 4: default 92
	// // -14 92
	// // -13 94
	// // -12 92
	// // -11 92
	// // -10 92
	// // -9 92
	// // -8 92
	// // -7 92
	// // -6 92
	// // -5 92
	// // -4 92
	// // -3 92
	// // -2 92
	// // -1 94
	// // 0 92
	// // 1 94
	// // 2 94
	// // 3 94
	// // 4 94;
	// goto _L1 _L1 _L2 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L2 _L1 _L2
	// _L2 _L2 _L2
	// _L1:
	// return flag;
	// _L2:
	// flag = true;
	// if(true) goto _L1; else goto _L3
	// _L3:
	// }

	// public static int f(int i1)
	// {
	// i1;
	// JVM INSTR tableswitch -14 4: default 92
	// // -14 131
	// // -13 113
	// // -12 131
	// // -11 131
	// // -10 131
	// // -9 92
	// // -8 92
	// // -7 92
	// // -6 92
	// // -5 92
	// // -4 92
	// // -3 92
	// // -2 92
	// // -1 131
	// // 0 92
	// // 1 97
	// // 2 113
	// // 3 97
	// // 4 97;
	// goto _L1 _L2 _L3 _L2 _L2 _L2 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L2 _L1 _L4
	// _L3 _L4 _L4
	// _L1:
	// int j1 = 0xff888888;
	// _L6:
	// return j1;
	// _L4:
	// j1 = Color.argb(255, 255, 51, 0);
	// continue; /* Loop/switch isn't completed */
	// _L3:
	// j1 = Color.argb(255, 66, 149, 223);
	// continue; /* Loop/switch isn't completed */
	// _L2:
	// if(com.bet007.mobile.score.common.z.d())
	// j1 = Color.parseColor("#aa0000");
	// else
	// j1 = 0xffff0000;
	// if(true) goto _L6; else goto _L5
	// _L5:
	// }

	// public static int g(int i1)
	// {
	// i1;
	// JVM INSTR tableswitch -14 4: default 92
	// // -14 128
	// // -13 97
	// // -12 146
	// // -11 146
	// // -10 146
	// // -9 92
	// // -8 92
	// // -7 92
	// // -6 92
	// // -5 92
	// // -4 92
	// // -3 92
	// // -2 92
	// // -1 146
	// // 0 92
	// // 1 97
	// // 2 128
	// // 3 97
	// // 4 97;
	// goto _L1 _L2 _L3 _L4 _L4 _L4 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L4 _L1 _L3
	// _L2 _L3 _L3
	// _L1:
	// int j1 = 0xff888888;
	// _L6:
	// return j1;
	// _L3:
	// if(com.bet007.mobile.score.common.z.d())
	// j1 = Color.parseColor("#154000");
	// else
	// j1 = Color.argb(255, 51, 153, 0);
	// continue; /* Loop/switch isn't completed */
	// _L2:
	// j1 = Color.argb(255, 66, 149, 223);
	// continue; /* Loop/switch isn't completed */
	// _L4:
	// if(com.bet007.mobile.score.common.z.d())
	// j1 = Color.parseColor("#880000");
	// else
	// j1 = 0xffff0000;
	// if(true) goto _L6; else goto _L5
	// _L5:
	// }

	// public static boolean h(int i1)
	// {
	// i1;
	// JVM INSTR tableswitch 0 4: default 36
	// // 0 40
	// // 1 40
	// // 2 40
	// // 3 40
	// // 4 40;
	// goto _L1 _L2 _L2 _L2 _L2 _L2
	// _L1:
	// boolean flag = true;
	// _L4:
	// return flag;
	// _L2:
	// flag = false;
	// if(true) goto _L4; else goto _L3
	// _L3:
	// }

	public String A() {
		return r;
	}

	public void A(String s1) {
		u = s1;
	}

	public String B() {
		return s;
	}

	public boolean C() {
		return f;
	}

	public String D() {
		return i;
	}

	public String E() {
		return j;
	}

	public String F() {
		return c;
	}

	public int G() {
		return e;
	}

	public String H() {
		return P;
	}

	public String I() {
		return Q;
	}

	public String J() {
		return R;
	}

	public String K() {
		return S;
	}

	public String L() {
		return T;
	}

	public String M() {
		return U;
	}

	public String N() {
		return V;
	}

	public String O() {
		return F;
	}

	// public String P()
	// {
	// e;
	// JVM INSTR tableswitch -14 4: default 96
	// // -14 204
	// // -13 192
	// // -12 180
	// // -11 168
	// // -10 228
	// // -9 96
	// // -8 96
	// // -7 96
	// // -6 96
	// // -5 96
	// // -4 96
	// // -3 96
	// // -2 96
	// // -1 216
	// // 0 107
	// // 1 119
	// // 2 131
	// // 3 143
	// // 4 155;
	// goto _L1 _L2 _L3 _L4 _L5 _L6 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L1 _L7 _L8 _L9
	// _L10 _L11 _L12
	// _L1:
	// String s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900ec);
	// _L14:
	// return s1;
	// _L8:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900ec);
	// continue; /* Loop/switch isn't completed */
	// _L9:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900ee);
	// continue; /* Loop/switch isn't completed */
	// _L10:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900ef);
	// continue; /* Loop/switch isn't completed */
	// _L11:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f0);
	// continue; /* Loop/switch isn't completed */
	// _L12:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f7);
	// continue; /* Loop/switch isn't completed */
	// _L5:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f1);
	// continue; /* Loop/switch isn't completed */
	// _L4:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f2);
	// continue; /* Loop/switch isn't completed */
	// _L3:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f3);
	// continue; /* Loop/switch isn't completed */
	// _L2:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f4);
	// continue; /* Loop/switch isn't completed */
	// _L7:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f5);
	// continue; /* Loop/switch isn't completed */
	// _L6:
	// s1 = com.bet007.mobile.score.common.af.a(ScoreApplication.a(),
	// 0x7f0900f6);
	// if(true) goto _L14; else goto _L13
	// _L13:
	// }

	public String Q() {
		return u;
	}

	public String R() {
		return v;
	}

	public String S() {
		String s1;
		if (g != null && g.length() >= 14)
			s1 = (new StringBuilder()).append(g.substring(8, 10)).append(":")
					.append(g.substring(10, 12)).toString();
		else
			s1 = "";
		return s1;
	}

	public String T() {
		String s1;
		if (g != null && g.length() >= 14)
			s1 = (new StringBuilder()).append(g.substring(6, 8))
					.append("\u65E5 ").append(g.substring(8, 10)).append(":")
					.append(g.substring(10, 12)).toString();
		else
			s1 = "";
		return s1;
	}

	public boolean U() {
		boolean flag;
		if (m != null && m.length() > 0 && n != null && n.length() > 0)
			flag = true;
		else
			flag = false;
		return flag;
	}

	public boolean V() {
		return B(r);
	}

	public boolean W() {
		return B(q);
	}

	public boolean X() {
		return B(p);
	}

	public boolean Y() {
		return B(o);
	}

	public boolean Z() {
		boolean flag;
		if (s != null && s.length() > 0)
			flag = true;
		else
			flag = false;
		return flag;
	}

	public int a(ar ar1) {
		int i1;
		if (e != ar1.e) {
			boolean flag = al();
			boolean flag1 = ar1.al();
			if (flag && !flag1)
				i1 = 1;
			else if (!flag && flag1)
				i1 = -1;
			else
				i1 = ar1.e - e;
		} else if (g.equals(ar1.g)) {
			if (ab() && ar1.ab())
				i1 = y.compareTo(ar1.y);
			else
				i1 = d.compareTo(ar1.d);
		} else {
			i1 = g.compareTo(ar1.g);
		}
		return i1;
	}

	public void a(int i1) {
		e = i1;
	}

	public void a(long l1) {
		E = l1;
	}

	public void a(String s1) {
		C = s1;
	}

	public void a(boolean flag) {
		G = flag;
	}

	public void a(String as[]) {
		if (as != null && as.length != 0) {
			q(as[2]);
			r(as[3]);
			d(as[5]);
			a(an.b(as[4]));
			x(as[10]);
			y(as[11]);
			s(as[6]);
			t(as[7]);
			u(as[8]);
			v(as[9]);
		}
	}

//	public void a(String[] paramArrayOfString, ae paramae)
//	  {
//	    if (paramArrayOfString.length >= 12)
//	    {
//	      return;
//	      a(an.b(paramArrayOfString[1]));
//	      d(paramArrayOfString[2]);
//	      e(paramArrayOfString[3]);
//	      x(paramArrayOfString[4]);
//	      y(paramArrayOfString[5]);
//	      f(paramArrayOfString[6]);
//	      g(paramArrayOfString[7]);
//	      h(paramArrayOfString[8]);
//	      i(paramArrayOfString[9]);
//	      j(paramArrayOfString[10]);
//	      k(paramArrayOfString[11]);
//	      b(new Date().getTime());
//	      if ((paramae.j == 0) || (paramae.j == 1))
//	        b(true);
//	    }
//	  }

	public boolean a() {
		return H;
	}

//	public boolean a(ae ae1) {
//		boolean flag;
//		if (ae1.j == 3
//				&& (ae1.n == -11 || ae1.n == -12 || ae1.n == -14
//						|| ae1.n == -13 || ae1.n == -10 || ae1.n == 2 || ae1.n == -1))
//			flag = true;
//		else
//			flag = false;
//		return flag;
//	}
//
//	public String aa() {
//		if (t == null)
//			t = com.bet007.mobile.score.common.aa.a(s);
//		return t;
//	}

	public boolean ab() {
		boolean flag;
		if (y != null && y.length() > 0)
			flag = true;
		else
			flag = false;
		return flag;
	}

	public boolean ac() {
		boolean flag;
		if (B != null && B.length() > 0)
			flag = true;
		else
			flag = false;
		return flag;
	}

	public boolean ad() {
		boolean flag;
		if (C != null && C.length() > 0)
			flag = true;
		else
			flag = false;
		return flag;
	}

	public String ae() {
		return y;
	}

	public int af() {
		int j1;
		if (w != -1) {
			j1 = w;
		} else {
			int i1 = an.b(d) % b.length;
			w = b[i1];
			j1 = w;
		}
		return j1;
	}

	public boolean ag() {
		return x;
	}

	public boolean ah() {
		return z;
	}

//	public String ai()
//	  {
//		String localObject = "";
//	    try
//	    {
//	      Date localDate1 = s();
//	      Date localDate2 = new Date();
//	      switch (this.e)
//	      {
//	      case 1:
//	        if (this.h.equals(""))
//	        {
//	          localObject = "";
//	        }
//	        else
//	        {
//	          long l2 = localDate2.getTime() / 1000L - localDate1.getTime() / 1000L + z.c();
//	          if (l2 > 2700L)
//	            localObject = "45+";
//	          else if (l2 / 60L == 0L)
//	            localObject = "1'";
//	          else
//	            localObject = String.valueOf(l2 / 60L).concat("'");
//	        }
//	        break;
//	      case 3:
//	        long l1 = 2700L + (localDate2.getTime() / 1000L - localDate1.getTime() / 1000L + z.c());
//	        if (l1 > 5400L)
//	        {
//	          localObject = "90+";
//	        }
//	        else if (l1 / 60L == 45L)
//	        {
//	          localObject = "46'";
//	        }
//	        else
//	        {
//	          String str = String.valueOf(l1 / 60L).concat("'");
//	          localObject = str;
//	        }
//	        break;
//	      case 2:
//	      }
//	    }
//	    catch (Exception localException)
//	    {
//	      localObject = "";
//	    }
//	    
//	    return localObject;
//	  }

//	public String aj()
//	  {
//		String str = P();
//	    switch (this.e)
//	    {
//	    case 2:
//	    default:
//	    case 1:
//	    	str = ai()
//	    case 3:
//	    }
//	    
//	      return str;
//	  }

	public boolean ak()
    {
        boolean flag = true;
        if(al() && g.length() == 14){
        	 
        	        Date date = an.l(g);
        	        Date date1 = an.l((new StringBuilder()).append(g.substring(0, 8)).append("110000").toString());
        	        Calendar calendar = Calendar.getInstance();
        	        calendar.setTime(date1);
//        	        calendar.add(6, flag);
        	        Date date2 = calendar.getTime();
        	        if(date.getTime() >= date1.getTime())
        	        {
        	            if((new Date()).getTime() < date2.getTime())
        	                flag = false;
        	        } else
        	        if(!((new Date()).getTime() < date1.getTime())){
        	        	flag = false;
        	            return flag;
        	        }
        	            

        } else {
        	
        	flag = false;	
        	
        }

        return flag; 
    }

	public boolean al()
	  {
	    switch (this.e)
	    {
	    default:
	    case 0:
	    case 1:
	    case 2:
	    case 3:
	    case 4:
	    }
	    for (boolean bool = true; ; bool = false)
	      return bool;
	  }

	public void b(long l1) {
		A = l1;
	}

	public void b(String s1) {
		B = s1;
	}

	public void b(boolean flag) {
		W = flag;
	}

	public boolean b() {
		return G;
	}

	public long c() {
		return E;
	}

	public void c(String s1) {
		d = s1;
	}

	public void c(boolean flag) {
		x = flag;
	}

	public int compareTo(Object obj) {
		return a((ar) obj);
	}

	public String d() {
		return D;
	}

	public void d(String s1) {
		g = s1;
	}

	public void d(boolean flag) {
		z = flag;
	}

	public String e() {
		return C;
	}

	public void e(String s1) {
		h = s1;
	}

	public boolean equals(Object obj)
    {
        boolean flag = false;
        if(obj instanceof ar){
        	
        	ar ar1 = (ar)obj;
        	
        	if(c.equals(c) && u.equals(ar1.u) && i.equals(ar1.i) && j.equals(ar1.j))
        		flag = true;
        	
        	
        }else {

        	
        	
        }
        return flag;
    }

	public long f() {
		return A;
	}

	public void f(String s1) {
		m = s1;
	}

	public String g() {
		return B;
	}

	public void g(String s1) {
		n = s1;
	}

	public void h(String s1) {
		o = s1;
	}

	public boolean h() {
		return I;
	}

	public int hashCode() {
		return c.hashCode() + u.hashCode() + i.hashCode() + j.hashCode();
	}

	public String i() {
		return J;
	}

	public void i(String s1) {
		p = s1;
	}

	public String j() {
		return K;
	}

	public void j(String s1) {
		q = s1;
	}

	public String k() {
		return L;
	}

	public void k(String s1) {
		r = s1;
	}

	public String l() {
		return M;
	}

	public void l(String s1) {
		s = s1;
	}

	public String m() {
		return N;
	}

	public void m(String s1) {
		y = s1;
	}

	public String n() {
		return O;
	}

	public void n(String s1) {
		i = s1;
	}

	public void o(String s1) {
		j = s1;
	}

	public boolean o() {
		return W;
	}

	public String p() {
		return d;
	}

	public void p(String s1) {
		c = s1;
	}

	public String q() {
		return g;
	}

	public void q(String s1) {
		P = s1;
	}

	public String r() {
		return h;
	}

	public void r(String s1) {
		Q = s1;
	}

//	public Date s() {
//		return com.bet007.mobile.score.common.al.a(h, "yyyyMMddHHmmss");
//	}

	public void s(String s1) {
		R = s1;
	}

	public String t() {
		return k;
	}

	public void t(String s1) {
		S = s1;
	}

	public String toString() {
		return (new StringBuilder()).append("[matchId=").append(c)
				.append(", home=").append(i).append(", away=").append(j)
				.append("]").toString();
	}

	public String u() {
		return l;
	}

	public void u(String s1) {
		T = s1;
	}

	public String v() {
		return m;
	}

	public void v(String s1) {
		U = s1;
	}

	public String w() {
		return n;
	}

	public void w(String s1) {
		V = s1;
	}

	public String x() {
		return o;
	}

	public void x(String s1) {
		k = s1;
	}

	public String y() {
		return p;
	}

	public void y(String s1) {
		l = s1;
	}

	public String z() {
		return q;
	}

	public void z(String s1) {
		v = s1;
	}


	static {
		int ai1[] = new int[7];
//		ai1[0] = Color.argb(255, 81, 142, 210);
//		ai1[1] = Color.argb(255, 232, 129, 26);
//		ai1[2] = Color.argb(255, 148, 151, 32);
//		ai1[3] = Color.argb(255, 141, 109, 214);
//		ai1[4] = Color.argb(255, 83, 172, 152);
//		ai1[5] = Color.argb(255, 229, 127, 0);
//		ai1[6] = Color.argb(255, 204, 0, 255);
		b = ai1;
	}
}