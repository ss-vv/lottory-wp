package com.unison.lottery.api.framework.filter.security;

import java.io.BufferedInputStream;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;


import javax.servlet.ServletInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.framework.utils.DecryptAndEncryptService;

import com.unison.lottery.api.framework.utils.ProtocolUtils;
import com.unison.lottery.api.protocol.common.Constants;

public class DecryptRequestWrapper extends HttpServletRequestWrapper {
	
	private static final String CHARSET = "UTF-8";

	; 
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	private ServletInputStream newStream;
	
	
	

	public DecryptRequestWrapper(HttpServletRequest request) {
		super(request);
		decryptAndMakeNewInputStream(request);
		
	}

	private void  decryptAndMakeNewInputStream(
			HttpServletRequest request) {
		String orignalRequest=null;
		try {
			
			orignalRequest = getOrignalRequest(request);
			//String seed=getSeed(request);
			String decryptedRequest=DecryptAndEncryptService.decrypt(orignalRequest);
			newStream=makeNewServletInputStream(decryptedRequest);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

	@Override
	public ServletInputStream getInputStream()  {
		return newStream;
		
	}
	
	

	private ServletInputStream makeNewServletInputStream(String decryptedRequest) {
		
		byte[] buffer = null;  
        try {  
            buffer = decryptedRequest.toString().getBytes(CHARSET);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        final ByteArrayInputStream bais = new ByteArrayInputStream(buffer);  
  
        ServletInputStream newStream = new ServletInputStream() {  
  
            @Override  
            public int read() throws IOException {  
                return bais.read();  
            }  
        };  
        return newStream;
	}

	private String getSeed(HttpServletRequest request) {
		return request.getHeader(Constants.SEED_PARAMETER_NAME);
	}

	private String getOrignalRequest(HttpServletRequest request) throws IOException {
		ServletInputStream orignalStream=request.getInputStream();
		String orignalRequest;
		BufferedInputStream bis=new BufferedInputStream(orignalStream);
		orignalRequest=ProtocolUtils.readStrings(bis);
		logger.debug("orignalRequest={}",orignalRequest);
		return orignalRequest;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		throw new IOException("should use getInputStream()!");
	}
	
	

}
