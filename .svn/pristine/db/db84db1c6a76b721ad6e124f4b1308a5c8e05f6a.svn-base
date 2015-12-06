/**
 * 
 */
package com.xhcms.lottery.dc.task.fetch;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.xhcms.lottery.dc.data.LCResult;

/**
 * @author langhsu
 *
 */
public class LCResultWorker extends FetchWorker {

	@Override
	@SuppressWarnings("unchecked")
	public void parser(Element root) {
		if (root.hasContent()) {
			List<LCResult> ret = new ArrayList<LCResult>();
			for (Iterator<Element> it = root.elements().iterator(); it.hasNext();) {
				try {
					LCResult r = parseField(it.next());
					if (r != null) {
						ret.add(r);
					}
				} catch (Exception e) {
					warn(log, e);
				}
			}
			storeData("lc_result", ret);
		}
	}
	
	private LCResult parseField(Element field) throws ParseException {
		if(field.hasContent()) {
			LCResult r = new LCResult();
			String cnCode = field.element("MatchNo").getText();
			r.setCode(toCode(cnCode));
			String time = field.element("MatchTime").getText();
			r.setOfftime(formater.parse(time));
			r.setScore(field.element("AllScore").getText());
			r.setConcedeScore(Float.parseFloat(field.element("LetScore").getText()));
			r.setGuessScore(Float.parseFloat(field.element("GuessScore").getText()));
			
			r.setSfSp(Double.parseDouble(field.element("SfSp").getText()));
			r.setYfsfSp(Double.parseDouble(field.element("YfsfSp").getText()));
			r.setSfcSp(Double.parseDouble(field.element("SfcSp").getText()));
			r.setDxfSp(Double.parseDouble(field.element("DxfSp").getText()));
			return r;
		}
		return null;
	}
}
