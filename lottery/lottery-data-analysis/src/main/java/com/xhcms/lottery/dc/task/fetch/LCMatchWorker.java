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
public class LCMatchWorker extends FetchWorker {
	
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
			storeData("lc_match", ret);
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
			m.setName(guest + " VS " + home);
			m.setConcedeScore(Float.parseFloat(field.element("LetScore").getText()));
			m.setConcedeScorePass(Float.parseFloat(field.element("LetScoreGG").getText()));
			m.setGuestScore(Float.parseFloat(field.elementText("GuestScore")));
			m.setGuestScorePass(Float.parseFloat(field.element("GuestScoreGG").getText()));
			parseOfftime(Constants.JCLQ, m, time);
			return m;
		}
		return null;
	}
}
