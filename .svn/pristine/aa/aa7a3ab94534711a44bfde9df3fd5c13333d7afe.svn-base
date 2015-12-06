package com.unison.lottery.api.framework.filter.security;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class EncryptResponseWrapper extends HttpServletResponseWrapper {
	@SuppressWarnings("unused")
	private HttpServletResponse httpServletResponse;
	
	//private String seed;
	
	private EncryptPrintWriterWrapper writerWrapper;

	public EncryptResponseWrapper(HttpServletResponse response) {
		super(response);
		httpServletResponse=response;
		//seed=seed4Encrypt;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if(null!=writerWrapper){
			return writerWrapper;
		}
		
		return new EncryptPrintWriterWrapper(super.getWriter());
	}

	

}
