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
 * 足球 总进球 过关
 * 
 * @author langhsu
 */
public class ZC0302Worker extends AbstractZCDataWorker {
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
			log.warn(" -- [ 03_ZC_2 ] data is empty !");
		}
		storeData("zc_odds", data);
	}
	
	private ZCOdds parseRow(NodeList cells) {
		if (cells.size() == 12) {
			ZCOdds fb = new ZCOdds(Constants.PLAY_03_ZC_2, "0,1,2,3,4,5,6,7");
			try{
				parseMatchCode(fb, cells.elementAt(0));
				parseOfftime(fb, formater, cells.elementAt(3));
				
				for (int i = 4; i <= 11; i++) {
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
