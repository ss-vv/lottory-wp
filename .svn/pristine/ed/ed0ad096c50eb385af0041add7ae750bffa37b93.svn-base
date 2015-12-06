package com.unison.lottery.api.framework.filter.security;

import java.io.PrintWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.framework.utils.DecryptAndEncryptService;

public class EncryptPrintWriterWrapper extends PrintWriter {
	
	//private String seed4Encrypt;
	
	Logger logger=LoggerFactory.getLogger(getClass());

	public EncryptPrintWriterWrapper(Writer arg0) {
		super(arg0);
		//seed4Encrypt=seed;
	}

	@Override
	public void print(String str) {
		
		String encryptionStr=DecryptAndEncryptService.encrypt(str);
		logger.debug("encryptionStr={}",encryptionStr);
		super.print(encryptionStr);
	}

}
