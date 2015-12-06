/**
 * 
 */
package com.xhcms.lottery.dc.task.fetch;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.lang.Constants;

/**
 * @author langhsu
 *
 */
public class ZCMatchWorker extends FetchWorker {
	
	@Override
	@SuppressWarnings("unchecked")
	public void parser(Element root) {
		if (root.hasContent()) {
			List<Match> ret = new ArrayList<Match>();
			for (Iterator<Element> it = root.elements().iterator(); it.hasNext();) {
				try {
					Match match = parseField(it.next());
					if (match != null) {
						ret.add(match);
					}
				} catch (Exception e) {
					warn(log, e);
				}
			}
			storeData("zc_match", ret);
		}
	}
	
	private Match parseField(Element field) throws ParseException {
		if(field.hasContent()) {
			Match m = new Match();
			String cnCode = field.element("MatchNo").getText();
			String time = field.element("MatchTime").getText();
			m.setCode(toCode(cnCode));
			m.setCnCode(cnCode);
			m.setLeague(field.element("League").getText());
			String home = field.element("HomeTeam").getText();
			String guest = field.element("GuestTeam").getText();
			m.setHomeTeam(home);
			m.setGuestTeam(guest);
			m.setName(home + " VS " + guest);
			m.setConcedePoints((int)Float.parseFloat(field.element("LetBall").getText()));
			parseOfftime(Constants.JCZQ, m, time);
			return m;
		}
		return null;
	}
}
