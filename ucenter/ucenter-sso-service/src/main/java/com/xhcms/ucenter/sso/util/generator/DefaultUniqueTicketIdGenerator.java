/**
 * 
 */
package com.xhcms.ucenter.sso.util.generator;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author bean
 *
 */
public class DefaultUniqueTicketIdGenerator implements UniqueTicketIdGenerator {
	@Autowired
    private NumericGenerator numericGenerator;
	
	@Autowired
    private RandomStringGenerator randomStringGenerator;
	
    private String suffix;
    
    public DefaultUniqueTicketIdGenerator() {
    	suffix = "";
    }
    
    public DefaultUniqueTicketIdGenerator(final String suffix) {
    	numericGenerator = new DefaultLongNumericGenerator(100);
    	randomStringGenerator = new DefaultRandomStringGenerator();
    	
    	if(suffix != null) {
    		this.suffix = "-" + suffix;
    	} else {
    		this.suffix = null;
    	}
    }
    
    public DefaultUniqueTicketIdGenerator(final String suffix, int maxlength) {
    	numericGenerator = new DefaultLongNumericGenerator(100);
    	randomStringGenerator = new DefaultRandomStringGenerator(maxlength);

    	if(suffix != null) {
    		this.suffix = "-" + suffix;
    	} else {
    		this.suffix = null;
    	}
    }
    
	@Override
	public String getNewTicketId(String prefix) {
        final String number = this.numericGenerator.getNextNumberAsString();
        final StringBuilder buffer = new StringBuilder(prefix.length() + 2
            + (this.suffix != null ? this.suffix.length() : 0) + this.randomStringGenerator.getMaxLength()
            + number.length());

        buffer.append(prefix);
        buffer.append("-");
        buffer.append(number);
        buffer.append("-");
        buffer.append(this.randomStringGenerator.getNewString());

        if (this.suffix != null) {
            buffer.append(this.suffix);
        }

        return buffer.toString();
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}	
	
	
}
