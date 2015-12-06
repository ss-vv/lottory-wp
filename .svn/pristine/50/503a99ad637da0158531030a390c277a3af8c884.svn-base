package com.unison.lottery.weibo.dataservice.crawler.parse.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.unison.lottery.weibo.dataservice.crawler.parse.realtime.an;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtOddsCompanyModel;

public class analyseCompany {
	enumh a;
	List b;
	List c;
	List d;
	Map e;
	private Set f;
	private Set g;
	private Set h;

	public analyseCompany() {
		a = enumh.yapei;
		b = new ArrayList();
		c = new ArrayList();
		d = new ArrayList();
		e = new HashMap();
		f = new HashSet();
		g = new HashSet();
		h = new HashSet();
	}

	private void a() {
		int i = 0;
		f.clear();
		g.clear();
		h.clear();
		// String s = ScoreApplication.a(context,
		// "ShareKey_ClientSet_Zq_Index_YP_Company", "");
		String s = "";
		if (s.equals("")) {
			for (int i1 = 0; i1 < 4 && i1 < -1 + d.size(); i1++)
				f.add(d.get(i1));

		} else {
			String as[] = s.split(",");
			for (int j = 0; j < as.length; j++) {
				d d3 = (d) e.get(as[j]);
				if (d3 != null)
					f.add(d3);
			}

		}
		// String s1 = ScoreApplication.a(context,
		// "ShareKey_ClientSet_Zq_Index_OP_Company", "");
		String s1 = "";
		if (s1.equals("")) {
			for (int l = 0; l < 4 && l < -1 + b.size(); l++)
				g.add(b.get(l));

		} else {
			String as1[] = s1.split(",");
			for (int k = 0; k < as1.length; k++) {
				d d2 = (d) e.get(as1[k]);
				if (d2 != null)
					g.add(d2);
			}

		}
		// String s2 = ScoreApplication.a(context,
		// "ShareKey_ClientSet_Zq_Index_DX_Company", "");
		String s2 = "";
		if (s2.equals("")) {
			for (; i < 4 && i < -1 + c.size(); i++)
				h.add(c.get(i));

		} else {
			for (String as2[] = s2.split(","); i < as2.length; i++) {
				d d1 = (d) e.get(as2[i]);
				if (d1 != null)
					h.add(d1);
			}

		}
	}

	private void a(d d1) {
		if (d1.e())
			d.add(d1);
		if (d1.f())
			c.add(d1);
		if (d1.g())
			b.add(d1);
	}

	public enumh a(int i) {
		enumh h1;
		if (i == 2)
			h1 = enumh.oupei;
		else if (i == 3)
			h1 = enumh.daxiao;
		else
			h1 = enumh.yapei;
		return h1;
	}

//	public String a(String s) {
//		String s1;
//		if (e.get(s) == null)
//			s1 = "";
//		else
//			s1 = ((d) e.get(s)).d();
//		return s1;
//	}

	//
	// public Set a()
	// {
	// return f;
	// }

	public List<QtOddsCompanyModel> analyseOddsCompany(String response) {
		if (response  == null || response.length() == 0) {
			return null;
		}
		List<QtOddsCompanyModel> dataList = null;
		String as[] = response.split("\\!", -1);
		if (as != null && as.length != 0) {
//			b.clear();
//			c.clear();
//			d.clear();
//			e.clear();
			int i = 0;
			dataList = new ArrayList<QtOddsCompanyModel>();
			while (i < as.length) {
				String as1[] = as[i].split("\\^", -1);
				if (as1.length >= 5) {
					String s1 = as1[0];
					QtOddsCompanyModel companyModel = new QtOddsCompanyModel();
					companyModel.setCorpId(an.b(s1));
					companyModel.setCorpName(as1[1]);
					companyModel.setYpType(an.b(as1[2]));
					companyModel.setOuType(an.b(as1[3]));
					companyModel.setOpType(an.b(as1[4]));
					companyModel.setProcessStatus(0);
					companyModel.setSource(1);
					dataList.add(companyModel);
//					d d1 = new d(s1, as1[1], as1[2], as1[3], as1[4]);
//					a(d1);
//					e.put(s1, d1);
				}
				i++;
			}
		}
//		System.out.println(e);
		return dataList;
	}

	public void a(enumh h1) {
		a = h1;
	}

	public void a(Set set) {
		f = set;
	}

	public Set b() {
		return g;
	}

	public void b(Set set) {
		g = set;
	}

	public Set c() {
		return h;
	}

	public void c(Set set) {
		h = set;
	}

	public enumh d() {
		return a;
	}

	public List e() {
		return d;
	}

	public List f() {
		return c;
	}

	public List g() {
		return b;
	}

}
