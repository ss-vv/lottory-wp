package com.unison.lottery;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import com.googlecode.aviator.AviatorEvaluator;

public class aviatorTest {

	public static void main(String[] args) {
		double a = 9.01;
		double b = 9.01;
		double c = 8.1;
		BigDecimal ba = new BigDecimal(a);
		BigDecimal bb = new BigDecimal(b);
		BigDecimal bc = new BigDecimal(c);
		
		AviatorEvaluator.setMathContext(MathContext.UNLIMITED);
		BigDecimal  rct = (BigDecimal) AviatorEvaluator.exec("a * b + c", ba, bb, bc);
		double  rtd = (Double) AviatorEvaluator.exec("a * b + c", a, b, c);
		double d = a*b+c;
		
		System.out.println(rct + "  " + rct.getClass());
        System.out.println(rtd + "  double");
        System.out.println(d + "  double");
        System.out.println(ba.multiply(bb).add(bc));
	}

}
