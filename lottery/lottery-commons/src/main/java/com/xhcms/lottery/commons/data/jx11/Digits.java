package com.xhcms.lottery.commons.data.jx11;


/**
 * 用位表示的数字序列，从1到16，提供位运算、拷贝操作。<br/>
 * 是 Immutable 的。
 * 
 * 江西11选5只用到前11位。
 * 
 * @author yangbo
 */
public class Digits {
	private static final int BITS_LEN = 16;
	private short bits = 0;

	/**
	 * 用 number 构造一个 Digits.
	 */
	public Digits(String number) {
		bits = parseNum(number);
	}
	
	/**
	 * 用一组数字串构造一个“位表示的数字序列”。
	 * @param numbers 从1到16的数字字符串。
	 */
	public Digits(String[] numbers){
		for (String numStr : numbers) {
			short num = parseNum(numStr);
			bits |= encodingNum(num);
		}
	}
	
	public Digits(short bits){
		this.bits = bits;
	}
	
	// 解析并确保数字在有效的定义域内
	private short parseNum(String numStr) {
		short num = Short.parseShort(numStr);
		checkNumRange(num);
		return num;
	}

	private void checkNumRange(int num) {
		if (num<1 || num>16){
			throw new IllegalArgumentException("Digits class can only support number in [1,16].");
		}		
	}

	private short encodingNum(short num){
		return (short)(1 << (num-1));
	}
	
	/**
	 * 从 bits 中去掉数字 numStr.
	 */
	public Digits remove(int number) {
		checkNumRange(number);
		short removed = (short) (bits & ~encodingNum((short)number));
		return new Digits(removed);
	}
	
	/**
	 * 去掉 digits 表示的数字。 
	 */
	public Digits remove(Digits digits) {
		short removed = (short) (bits & (bits ^ digits.getBits()));
		return new Digits(removed);
	}
	
	public short getBits(){
		return bits;
	}
	
	/**
	 * 数一数本对象表示了多少数字。
	 */
	public int countNumbers(){
		int count = 0;
		for (int i=0; i<BITS_LEN; i++) {
			int isOne = (bits >> i) & 0x0001;
			if (isOne == 1) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 取得本对象表示的所有数字。
	 */
	public int[] getNumbers() {
		int count = countNumbers();
		int[] numbers = new int[count];
		int numIndex = 0;
		for (int i=0; i<BITS_LEN; i++) {
			int isOne = (bits >> i) & 0x0001;
			if (isOne == 1) {
				numbers[numIndex++] = i+1;
			}
		}
		return numbers;
	}
}
