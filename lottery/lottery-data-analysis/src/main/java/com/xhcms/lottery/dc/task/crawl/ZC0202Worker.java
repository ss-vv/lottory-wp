/**
 * 
 */
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

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.dc.data.OddsBase;
import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.lang.Constants;

/**
 * @author langhsu
 *
 */
public class ZC0202Worker extends AbstractZCDataWorker {
	private static final String ROOT = "#tblScore .titleScore";
	private static NodeFilter trFilter = new TagNameFilter("tr");
	private static NodeFilter tdFilter = new TagNameFilter("td");
	private static NodeFilter h3Filter = new TagNameFilter("h3");
	private static NodeFilter tabFilter = new TagNameFilter("table");
	
	@Override
	public void crawl(Parser parser) throws ParserException {
		// TODO Auto-generated method stub
		List<ZCOdds> data = new ArrayList<ZCOdds>();
		NodeList nl = parser.parse(new CssSelectorNodeFilter(ROOT));
		
		for (NodeIterator it = nl.elements(); it.hasMoreNodes();) {
			String options = "10,20,21,30,31,32,40,41,42,50,51,52,90," +
									 "00,11,22,33,99," +
									 "01,02,12,03,13,23,04,14,24,05,15,25,09";
			
			ZCOdds zc = new ZCOdds(Constants.PLAY_02_ZC_2, options);
			
			NodeList cells = it.nextNode().getChildren();
			NodeList h3 = cells.extractAllNodesThatMatch(h3Filter);
			NodeList tab = cells.extractAllNodesThatMatch(tabFilter);
			
			try {
				// 抓取赛事
				String h = h3.elementAt(0).toPlainTextString();
				String[] arr = h.split("\\|");
				if (arr.length == 4) {
					String cnCode = arr[0].trim().substring(0, 5);
					zc.setCode(toCode(cnCode));
					String off = arr[3].trim();
					parseOfftime(zc, formater, off.substring(off.length() - 14));
				} else {
					throw new XHRuntimeException("data don't complete, h3 size:" + arr.length);
				}
				fetchOdds(zc, tab.elementAt(0));
				
				data.add(zc);
			} catch (Exception e) {
				warn(log, e);
			}
		}
		if (data.size() < 1) {
			log.warn(" -- [ 02_ZC_2 ] data is empty !");
		}
		storeData("zc_odds", data);
	}
	
	/**
	 * 获取赔率
	 * @param zc
	 * @param nd
	 * @throws ParserException
	 */
	private void fetchOdds(OddsBase zc, Node nd) throws ParserException {
		NodeList tab = nd.getChildren();
		tab.keepAllNodesThatMatch(trFilter);
		
		for (NodeIterator it = tab.elements (); it.hasMoreNodes (); ) {
			Node node = it.nextNode();
			NodeList cells = node.getChildren();
			cells.keepAllNodesThatMatch(tdFilter);
			// 因为有隐藏的TD，所以按样式过滤一把
			cells.keepAllNodesThatMatch(new CssSelectorNodeFilter(".tdC"));
			
			// 因为有隐藏区域，所以把前面三行都过滤了, 这里从0开始抓起
			for(int i = 0; i < cells.size(); i++) {
				Node td = cells.elementAt(i).getLastChild();
				zc.addOdd(td.toPlainTextString().trim());
			}
		}
	}
}
