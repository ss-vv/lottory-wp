package com.xhcms.lottery.dc.task.crawl;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.xhcms.lottery.dc.data.LCOdds;
import com.xhcms.lottery.dc.task.filter.ExcludeImageFilter;
import com.xhcms.lottery.lang.Constants;

/**
 * 篮球 胜分差 单关
 * 
 * @author langhsu
 *
 */
public class LC0802Worker extends AbstractLCDataWorker {
	private static final String ROOT = ".box-search-l table.tbl tr";
	private NodeFilter tdFilter = new TagNameFilter("td");
	private	NodeFilter imgFilter = new ExcludeImageFilter();
    
	@Override
	public void crawl(Parser parser) throws ParserException {
		List<LCOdds> data = new ArrayList<LCOdds>();
		NodeList nl = parser.parse(new CssSelectorNodeFilter(ROOT));
		for (NodeIterator it = nl.elements (); it.hasMoreNodes (); ) {
			Node node = it.nextNode();
			NodeList cells = node.getChildren();
			cells.keepAllNodesThatMatch(tdFilter);
			
			LCOdds lc = parseRow(cells);
			
			if (null != lc) {
				data.add(lc);
			}
		}
		//persist
		if (data.size() < 1) {
			log.warn(" -- [ 08_LC_2 ] data is empty !");
		}
		storeData("lc_odds", data);
	}
	
	private LCOdds parseRow(NodeList cells) {
		if (cells.size() == 11) {
			try {
				// 主胜01、02、03、04、05、06，客胜11、12、13、14、15、16
				LCOdds lc = new LCOdds(Constants.PLAY_08_LC_2, "11,01,12,02,13,03,14,04,15,05,16,06");
				parseMatchCode(lc, cells.elementAt(0));
				parseOfftime(lc, formater, cells.elementAt(3));
	            
				for (int i = 5; i <=10; i++) {
					Node nd = cells.elementAt(i);
					// 过滤图片
					nd.getChildren().keepAllNodesThatMatch(imgFilter);
					lc.addOdd(parseOdd(nd.getFirstChild()));
					lc.addOdd(parseOdd(nd.getLastChild()));
				}
				return lc;
			} catch (Exception e) {
				warn(log, e);
			}
		}
		return null;
	}
	
}
