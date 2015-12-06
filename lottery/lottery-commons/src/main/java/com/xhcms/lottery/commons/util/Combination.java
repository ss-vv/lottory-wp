package com.xhcms.lottery.commons.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xhcms.lottery.lang.PlayType;

/**
 * 字典法计算排列组合
 * @author change by Wang Lei
 *
 */
public final class Combination {

    /**
     * 字典法计算排列组合
     * @param cursor 参与组合的对象列表的游标数组，caller通过游标可以访问到参与组合的对象
     * @param n 组合的下标 选择赛事场次数
     * @param m 组合的上标 串关数
     * @param caller
     */
    public static void combinate(int[] cursor, int n, int m, Caller caller) {
        int i = 0, MAX = m - 1, max = n - MAX;
        int[] c = new int[m];
        while (c[0] < max) {
            for (i++; i < m; i++) {
                c[i] = c[i - 1] + 1;
            }

            while (c[MAX] < n) {
            	if(!PlayType.isOneMatchMutiPlayMixBet(caller.getBet().getPlayId())) {
            		//计算胆码是否存在与当前组合
            		if(haveSeed(caller, c)){
            			caller.call(c);
            		}
            	} else {
            		List<String> excludeIndex = caller.getExcludeIndex();
                	List<String> includeIndex = caller.getIncludeIndex();
                	if(DigitalCombination.isSkipCombin(caller.getMatchs(), excludeIndex, 
                			includeIndex, c, caller.getSeeds())) {
            			c[MAX]++;
            			continue;
            		} else {
            			caller.call(c);
            		}
            	}
            	c[MAX]++;
            }

            i = MAX - 1;
            while (i >= 0) {
                if (c[i] + 1 < c[i + 1]) {
                    c[i]++;
                    break;
                }
                i--;
            }
        }
    }

    /**
     * 判断当前组合在有胆码和无胆码的时候，是否应该计算
     * @param caller
     * @param c
     * @return boolean
     */
    private static boolean haveSeed(Caller caller,int[] c){
    	boolean haveseed = true ;
    	Set<Integer> seedIndexs = caller.getSeeds();
    	
    	//无胆码时，默认通过
    	if(seedIndexs == null || seedIndexs.size() == 0){
    		return haveseed;
    	}
    	
    	Iterator<Integer> seeds = seedIndexs.iterator();
    	Map<Integer,Integer> curs = new HashMap<Integer,Integer>();
    	for(int i=0;i<c.length;i++){
    		curs.put(c[i], c[i]);
    	}
    	
    	//有胆码，判断当前赛事是否包含胆码
		while(seeds.hasNext()){
			if(curs.get(seeds.next())==null){
				haveseed = false;
			}
		}
		return haveseed;
    }
    
    /**
     * 计算过关方式m@n的注数
     * 
     * @param passType
     * @param val 投注选项数组
     * @param cur 比赛数组
     */
    public static final int mn(int passType, int[] val, int[] cur){
        int a = v(passType, val, cur, 0);
        int b = v(passType, val, cur, 1);
        int c = v(passType, val, cur, 2);
        int d = v(passType, val, cur, 3);
        int e = v(passType, val, cur, 4);
        int f = v(passType, val, cur, 5);
        int g = v(passType, val, cur, 6);
        int h = v(passType, val, cur, 7);
        int i = v(passType, val, cur, 8);
        int j = v(passType, val, cur, 9);
        int k = v(passType, val, cur, 10);
        int l = v(passType, val, cur, 11);
        int m = v(passType, val, cur, 12);
        int n = v(passType, val, cur, 13);
        int o = v(passType, val, cur, 14);
        
        switch(passType){
            case 1001:
                return a;
            case 2001:
                return a*b;
            case 2003:
                return (a+1)*(b+1)-1;
            case 3001:
                return a*b*c;
            case 3003:
                return a*(b+c)+b*c;
            case 3004:
                return (a+1)*b*c+a*(b+c);
            case 3007:
                return (a+1)*(b+1)*(c+1)-1;
            case 4001:
                return a*b*c*d;
            case 4004:
                return a*b*(c+d)+(a+b)*c*d;
            case 4005:
                return ((a+1)*(b+1)-1)*(c+1)*(d+1)-(a*b+(a+b)*(c+d+1));
            case 4006:
                return a*b+(a+b)*(c+d)+c*d;
            case 4011:
                return ((a+1)*(b+1)*(c+1)-1)*(d+1)-(a+b+c);
            case 4015:
                return (a+1)*(b+1)*(c+1)*(d+1)-1;
            case 5001:
                return a*b*c*d*e;
            case 5005:
                return a*b*c*(d+e)+(a*(b+c)+b*c)*d*e;
            case 5006:
                return a*b*c*(d+e+d*e)+(a*(b+c)+b*c)*d*e;
            case 5010:
                return a*(b+c+d+e)+b*(c+d+e)+c*(d+e)+d*e;
            case 5016:
                return ((a+1)*(b+1)*(c+1)-1)*(d+1)*(e+1)-(a*b+(a+b)*(c+d+e+1)+c*(d+e+1));
            case 5020:
                return (a+1)*b*(c+d+e)+(a+b+1)*c*(d+e)+(a+b+c+1)*d*e+a*(b+c+d+e);
            case 5026:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)-(a+b+c+d+e+1);
            case 5031:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)-1;
            case 6001:
                return a*b*c*d*e*f;
            case 6006:
                return a*b*c*(d*e+d*f+e*f)+(a*b+a*c+b*c)*d*e*f;
            case 6007:
                return a*b*c*(d*e+d*f+e*f+d*e*f)+(a*b+a*c+b*c)*d*e*f;
            case 6015:
                return a*b+(a+b)*(c+d+e+f)+c*d+(c+d)*(e+f)+e*f;
            case 6020:
                return a*b*(c+d+e+f)+(a+b)*c*(d+e+f)+(a+b+c)*d*(e+f)+(a+b+c+d)*e*f;
            case 6022:
                return a*b*c*d*(e+1)*(f+1)+(a*b*(c+d)+(a+b)*c*d)*((e+1)*(f+1)-1)+(a*b+(a+b)*(c+d)+c*d)*e*f;
            case 6035:
                return (a+b)*(c+d+e+f)+c*d+a*b*(c+d+e+f+1)+(a+b)*c*(d+e+f)+((a+b+c+1)*d+c)*(e+f)+(a+b+c+d+1)*e*f;
            case 6042:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a*(b+c+d+e+f+1)+b*(c+d+e+f+1)+c*(d+e+f+1)+d*(e+f+1)+(e+1)*(f+1));
            case 6050:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a+b+c+d+e+f+1)-(a*b*(c*d*(e+f)+(c+d)*e*f)+(a+b+a*b)*c*d*e*f);
            case 6057:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a+b+c+d+e+f+1);
            case 6063:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-1;
            case 7001:
                return a*b*c*d*e*f*g;
            case 7007:
                return a*b*c*(d*(e*(f+g)+f*g)+e*f*g)+(a*b+a*c+b*c)*d*e*f*g;
            case 7008:
                return a*b*c*(d*(e*(f+g)+f*g)+e*f*g)+(a*b+a*c+b*c+a*b*c)*d*e*f*g;
            case 7021:
                return a*b*c*(d*(e+f+g)+e*(f+g)+f*g)+(a*(b+c)+b*c)*d*(e*f+e*g+f*g)+(a*(b+c+d)+b*(c+d)+c*d)*e*f*g;
            case 7035:
                return a*b*c*(d+e+f+g)+(a*b+a*c+b*c)*(d*(e+f+g)+e*(f+g)+f*g)+(a+b+c)*d*(e*(f+g)+f*g)+(a+b+c+d)*e*f*g;
            case 7120:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)*(g+1)-(a+b+c+d+e+f+g+1);
            case 8001:
                return a*b*c*d*e*f*g*h;
            case 8008:
                return a*b*c*d*(e*(f*(g+h)+g*h)+f*g*h)+(a*(b*(c+d)+c*d)+b*c*d)*e*f*g*h;
            case 8009:
                return a*b*c*d*(e*(f*(g+h)+g*h)+(e+1)*f*g*h)+(a*(b*(c+d)+c*d)+b*c*d)*e*f*g*h;
            case 8028:
                return (a*(b+c)+b*c)*d*(e*(f*(g+h)+g*h)+f*g*h)+(a*(b+c+d)+b*(c+d)+c*d)*e*f*g*h+a*b*c*(d*(e*(f+g+h)+f*(g+h)+g*h)+e*(f*(g+h)+g*h)+f*g*h);
            case 8056:
                return a*b*c*(d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a*(b+c)+b*c)*d*(e*(f+g+h)+f*(g+h)+g*h)+(a*(b+c+d)+b*(c+d)+c*d)*e*(f*(g+h)+g*h)+(a*(b+c+d+e)+b*(c+d+e)+c*(d+e)+d*e)*f*g*h;
            case 8070:
                return a*b*(c*(d+e+f+g+h)+d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a+b)*c*(d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a+b+c)*d*(e*(f+g+h)+f*(g+h)+g*h)+(a+b+c+d)*e*(f*(g+h)+g*h)+(a+b+c+d+e)*f*g*h;
            case 8247:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)*(g+1)*(h+1)-(a+b+c+d+e+f+g+h+1);
            case 9001:
                return a*b*c*d*e*f*g*h*i;
            case 10001:
                return a*b*c*d*e*f*g*h*i*j;
            case 11001:
                return a*b*c*d*e*f*g*h*i*j*k;
            case 12001:
                return a*b*c*d*e*f*g*h*i*j*k*l;
            case 13001:
                return a*b*c*d*e*f*g*h*i*j*k*l*m;
            case 14001:
                return a*b*c*d*e*f*g*h*i*j*k*l*m*n;
            case 15001:
                return a*b*c*d*e*f*g*h*i*j*k*l*m*n*o;
            default:
                return 0;
        }
    }
    
    
   /**
    * 计算过关方式m@n的赔率
    * 
    * @param passType
    * @param val 赔率数组
    */
    public static final double mn(int passType, double[] val, int[] cur){
        double a = v(passType, val, cur, 0);
        double b = v(passType, val, cur, 1);
        double c = v(passType, val, cur, 2);
        double d = v(passType, val, cur, 3);
        double e = v(passType, val, cur, 4);
        double f = v(passType, val, cur, 5);
        double g = v(passType, val, cur, 6);
        double h = v(passType, val, cur, 7);
        double i = v(passType, val, cur, 8);
        double j = v(passType, val, cur, 9);
        double k = v(passType, val, cur, 10);
        double l = v(passType, val, cur, 11);
        double m = v(passType, val, cur, 12);
        double n = v(passType, val, cur, 13);
        double o = v(passType, val, cur, 14);
        
        switch(passType){
            case 1001:
                return a;
            case 2001:
                return a*b;
            case 2003:
                return (a+1)*(b+1)-1;
            case 3001:
                return a*b*c;
            case 3003:
                return a*(b+c)+b*c;
            case 3004:
                return (a+1)*b*c+a*(b+c);
            case 3007:
                return (a+1)*(b+1)*(c+1)-1;
            case 4001:
                return a*b*c*d;
            case 4004:
                return a*b*(c+d)+(a+b)*c*d;
            case 4005:
                return ((a+1)*(b+1)-1)*(c+1)*(d+1)-(a*b+(a+b)*(c+d+1));
            case 4006:
                return a*b+(a+b)*(c+d)+c*d;
            case 4011:
                return ((a+1)*(b+1)*(c+1)-1)*(d+1)-(a+b+c);
            case 4015:
                return (a+1)*(b+1)*(c+1)*(d+1)-1;
            case 5001:
                return a*b*c*d*e;
            case 5005:
                return a*b*c*(d+e)+(a*(b+c)+b*c)*d*e;
            case 5006:
                return a*b*c*(d+e+d*e)+(a*(b+c)+b*c)*d*e;
            case 5010:
                return a*(b+c+d+e)+b*(c+d+e)+c*(d+e)+d*e;
            case 5016:
                return ((a+1)*(b+1)*(c+1)-1)*(d+1)*(e+1)-(a*b+(a+b)*(c+d+e+1)+c*(d+e+1));
            case 5020:
                return (a+1)*b*(c+d+e)+(a+b+1)*c*(d+e)+(a+b+c+1)*d*e+a*(b+c+d+e);
            case 5026:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)-(a+b+c+d+e+1);
            case 5031:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)-1;
            case 6001:
                return a*b*c*d*e*f;
            case 6006:
                return a*b*c*(d*e+d*f+e*f)+(a*b+a*c+b*c)*d*e*f;
            case 6007:
                return a*b*c*(d*e+d*f+e*f+d*e*f)+(a*b+a*c+b*c)*d*e*f;
            case 6015:
                return a*b+(a+b)*(c+d+e+f)+c*d+(c+d)*(e+f)+e*f;
            case 6020:
                return a*b*(c+d+e+f)+(a+b)*c*(d+e+f)+(a+b+c)*d*(e+f)+(a+b+c+d)*e*f;
            case 6022:
                return a*b*c*d*(e+1)*(f+1)+(a*b*(c+d)+(a+b)*c*d)*((e+1)*(f+1)-1)+(a*b+(a+b)*(c+d)+c*d)*e*f;
            case 6035:
                return (a+b)*(c+d+e+f)+c*d+a*b*(c+d+e+f+1)+(a+b)*c*(d+e+f)+((a+b+c+1)*d+c)*(e+f)+(a+b+c+d+1)*e*f;
            case 6042:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a*(b+c+d+e+f+1)+b*(c+d+e+f+1)+c*(d+e+f+1)+d*(e+f+1)+(e+1)*(f+1));
            case 6050:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a+b+c+d+e+f+1)-(a*b*(c*d*(e+f)+(c+d)*e*f)+(a+b+a*b)*c*d*e*f);
            case 6057:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-(a+b+c+d+e+f+1);
            case 6063:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)-1;
            case 7001:
                return a*b*c*d*e*f*g;
            case 7007:
                return a*b*c*(d*(e*(f+g)+f*g)+e*f*g)+(a*b+a*c+b*c)*d*e*f*g;
            case 7008:
                return a*b*c*(d*(e*(f+g)+f*g)+e*f*g)+(a*b+a*c+b*c+a*b*c)*d*e*f*g;
            case 7021:
                return a*b*c*(d*(e+f+g)+e*(f+g)+f*g)+(a*(b+c)+b*c)*d*(e*f+e*g+f*g)+(a*(b+c+d)+b*(c+d)+c*d)*e*f*g;
            case 7035:
                return a*b*c*(d+e+f+g)+(a*b+a*c+b*c)*(d*(e+f+g)+e*(f+g)+f*g)+(a+b+c)*d*(e*(f+g)+f*g)+(a+b+c+d)*e*f*g;
            case 7120:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)*(g+1)-(a+b+c+d+e+f+g+1);
            case 8001:
                return a*b*c*d*e*f*g*h;
            case 8008:
                return a*b*c*d*(e*(f*(g+h)+g*h)+f*g*h)+(a*(b*(c+d)+c*d)+b*c*d)*e*f*g*h;
            case 8009:
                return a*b*c*d*(e*(f*(g+h)+g*h)+(e+1)*f*g*h)+(a*(b*(c+d)+c*d)+b*c*d)*e*f*g*h;
            case 8028:
                return (a*(b+c)+b*c)*d*(e*(f*(g+h)+g*h)+f*g*h)+(a*(b+c+d)+b*(c+d)+c*d)*e*f*g*h+a*b*c*(d*(e*(f+g+h)+f*(g+h)+g*h)+e*(f*(g+h)+g*h)+f*g*h);
            case 8056:
                return a*b*c*(d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a*(b+c)+b*c)*d*(e*(f+g+h)+f*(g+h)+g*h)+(a*(b+c+d)+b*(c+d)+c*d)*e*(f*(g+h)+g*h)+(a*(b+c+d+e)+b*(c+d+e)+c*(d+e)+d*e)*f*g*h;
            case 8070:
                return a*b*(c*(d+e+f+g+h)+d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a+b)*c*(d*(e+f+g+h)+e*(f+g+h)+f*(g+h)+g*h)+(a+b+c)*d*(e*(f+g+h)+f*(g+h)+g*h)+(a+b+c+d)*e*(f*(g+h)+g*h)+(a+b+c+d+e)*f*g*h;
            case 8247:
                return (a+1)*(b+1)*(c+1)*(d+1)*(e+1)*(f+1)*(g+1)*(h+1)-(a+b+c+d+e+f+g+h+1);
            case 9001:
                return a*b*c*d*e*f*g*h*i;
            case 10001:
                return a*b*c*d*e*f*g*h*i*j;
            case 11001:
                return a*b*c*d*e*f*g*h*i*j*k;
            case 12001:
                return a*b*c*d*e*f*g*h*i*j*k*l;
            case 13001:
                return a*b*c*d*e*f*g*h*i*j*k*l*m;
            case 14001:
                return a*b*c*d*e*f*g*h*i*j*k*l*m*n;
            case 15001:
                return a*b*c*d*e*f*g*h*i*j*k*l*m*n*o;
            default:
                return 0;
        }
    }
    
    private static final int v(int passType, int[] val, int[] index, int i){
        if(passType > (i + 1) * 1000){
            return val[index[i]];
        }
        return 0;
    }
    
    private static final double v(int passType, double[] val, int[] index, int i){
        if(passType > (i + 1) * 1000){
            return val[index[i]];
        }
        return 0d;
    }
}
