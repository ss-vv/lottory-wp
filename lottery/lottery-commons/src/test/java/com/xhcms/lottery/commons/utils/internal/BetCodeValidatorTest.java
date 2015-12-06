package com.xhcms.lottery.commons.utils.internal;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;

import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.lang.PlayType;

/**
 * @author Yang Bo
 */
public class BetCodeValidatorTest {

	@Test
	public void testCQSS_DXDS(){
		BetCodeValidator betCodeValidator = new CQSSBetCodeValidator();
		try {
			betCodeValidator.valid("13", PlayType.CQSS_DXDS);
		}
		catch (BetException exception) {
			exception.printStackTrace();
		}
	}
	
	@Test
	public void testCQSS_3X_DS(){
		Pattern p = Pattern.compile("^(\\d{3}(;|;?$))+");
		Matcher m = p.matcher("123;123;123;123;123;123");
		assertTrue(m.matches());
		m = p.matcher("123");
		assertTrue(m.matches());
	}

	@Test
	public void testCQSS_3X_FS(){
		Pattern p = Pattern.compile("^\\d+,\\d+,\\d+$");
		Matcher m = p.matcher("0123456789,0,123");
		assertTrue(m.matches());
		m = p.matcher("9,0,1");
		assertTrue(m.matches());
		m = p.matcher("0123456789,0,123,1");
		assertFalse(m.matches());
	}
	
	@Test
	public void testCQSS_5X_DS(){
		BetCodeValidator betCodeValidator = new CQSSBetCodeValidator();
		try {
			betCodeValidator.valid("32567", PlayType.CQSS_5X_DS);
			betCodeValidator.valid("325678", PlayType.CQSS_5X_DS);
		}
		catch (BetException exception) {
			exception.printStackTrace();
		}
	}
	
	@Test
	public void testCQSS_5X_FS(){
		BetCodeValidator betCodeValidator = new CQSSBetCodeValidator();
		try {
			betCodeValidator.valid("34567890,0,123,1234,12345", PlayType.CQSS_5X_FS);
			betCodeValidator.valid("0,0,123,1234,12345", PlayType.CQSS_5X_FS);
		}
		catch (BetException exception) {
			exception.printStackTrace();
		}
	}
}
