/**
 * 
 */
package com.xhcms.lottery.dc.task.crawl;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.lang.Constants;

/**
 * 胜负 半全 过关
 * 
 * @author langhsu
 */
public class ZC0402Worker extends AbstractZCDataWorker {
	private static final String ROOT = "#jumpTable tr";
	private NodeFilter tdFilter = new TagNameFilter("td");
	
	@Override
	public void crawl(Parser parser) throws ParserException {
		NodeList nl = parser.parse(new CssSelectorNodeFilter(ROOT));
		List<ZCOdds> data = new ArrayList<ZCOdds>();
		
		for (NodeIterator it = nl.elements(); it.hasMoreNodes();) {
			NodeList cells = it.nextNode().getChildren();
			cells.keepAllNodesThatMatch(tdFilter);
			ZCOdds zc = parseRow(cells);
			if (null != zc) {
				data.add(zc);
			}
		}
		if (data.size() < 1) {
			log.warn(" -- [ 04_ZC_2 ] data is empty !");
		}
		storeData("zc_odds", data);
	}
	
	private ZCOdds parseRow(NodeList cells) {
		if (cells.size() == 13) {
			ZCOdds fb = new ZCOdds(Constants.PLAY_04_ZC_2, "33,31,30,13,11,10,03,01,00");
			try{
				parseMatchCode(fb, cells.elementAt(0));
				parseOfftime(fb, formater, cells.elementAt(3));

				for (int i = 4; i <= 12; i++) {
					fb.addOdd( parseOdd( cells.elementAt(i) ) );
				}
				return fb;
			}catch(Exception e){
				warn(log, e);
			}
		}
		return null;
	}
}
