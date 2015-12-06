package com.xhcms.lottery.dc.task.filter;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;

/**
 * 过滤 ImageTag、InputTag
 * 
 * @author langhsu
 */
public class ExcludeImageFilter implements NodeFilter {
	private static final long serialVersionUID = 8534098423734568153L;

	@Override
	public boolean accept(Node node) {
		if (node instanceof ImageTag) {
			return false;
		} else if (node instanceof InputTag) {
			return false;
		} else {
			return true;
		}
	}

}
