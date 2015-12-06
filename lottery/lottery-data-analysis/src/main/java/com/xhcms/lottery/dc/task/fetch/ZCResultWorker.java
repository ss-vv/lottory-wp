/**
 * 
 */
package com.xhcms.lottery.dc.task.fetch;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import com.xhcms.lottery.dc.data.ZCResult;

/**
 * 足球赛果
 * @author langhsu
 * 
 */
public class ZCResultWorker extends FetchWorker {

	@Override
	@SuppressWarnings("unchecked")
	public void parser(Element root) {
		if (root.hasContent()) {
			List<ZCResult> ret = new ArrayList<ZCResult>();
			for (Iterator<Element> it = root.elements().iterator(); it.hasNext();) {
				try {
					ZCResult r = parseField(it.next());
					if (r != null) {
						ret.add(r);
					}
				} catch (Exception e) {
					warn(log, e);
				}
			}
			storeData("zc_result", ret);
		}
	}
	
	private ZCResult parseField(Element field) throws ParseException {
		if(field.hasContent()) {
			ZCResult r = new ZCResult();
			String cnCode = field.element("MatchNo").getText();
			r.setCode(toCode(cnCode));
			String time = field.element("MatchTime").getText();
			r.setOfftime(formater.parse(time));
			r.setConcedePoints((int)Float.parseFloat(field.element("LetBall").getText()));
			r.setHalfScore(field.element("HalfScore").getText());
			r.setScore(field.element("AllScore").getText());
			r.setSfpSp(Double.parseDouble(field.element("SfpSp").getText()));
			r.setBfSp(Double.parseDouble(field.element("BfSp").getText()));
			r.setZjqSp(Double.parseDouble(field.element("ZjqSp").getText()));
			r.setBqcSp(Double.parseDouble(field.element("BqcSp").getText()));
			return r;
		}
		return null;
	}

}
