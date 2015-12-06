package com.unison.struts2.result;

import org.apache.struts2.dispatcher.VelocityResult;

/**
 * content-type 为 'text/xml' 的 velocity result type.
 * @author Yang Bo
 *
 */
public class XmlVelocityResult extends VelocityResult {
	private static final long serialVersionUID = 7559564144461735289L;

	@Override
	protected String getContentType(String templateLocation) {
		return "text/xml";
	}
	
}
