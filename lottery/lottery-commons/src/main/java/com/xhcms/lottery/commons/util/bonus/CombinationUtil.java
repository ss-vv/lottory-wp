package com.xhcms.lottery.commons.util.bonus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombinationUtil {
	
	private static double[] getOddsVals(List<BetContent> betContents){
		double[] val = new double[betContents.size()];
		for (int i = 0; i < betContents.size(); i++) {
			val[i] = betContents.get(i).getOdd().doubleValue();
		}
		return val;
	}
	
	/**
	 * 计算投注数据，返回某一串关玩法组合数据
	 * @param mn
	 * @param ms
	 * @param betContents
	 * @param excludeIndex
	 * @param includeIndex
	 * @param target "max"-求最大值；"min"-求最小值
	 * @return
	 */
	public static MaxAndMinBonusModel calculateBetData(String[] mn, int ms,List<BetContent> betContents, 
			List<Integer[]> excludeIndex,List<Integer[]> includeIndex, String target) {
		MaxAndMinBonusModel model = new MaxAndMinBonusModel();
		int m = Integer.valueOf(mn[0]);
		int n = Integer.valueOf(mn[1]);
        int i = 0, cMAX = m - 1, passMax = ms - m + 1;
		int [] valIndex = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		double returnVal = 0;
		double[] val = getOddsVals(betContents);
        while(valIndex[0] < passMax){
            for(i++; i < m; i++){
                valIndex[i] = valIndex[i - 1] + 1;
            }
            while(valIndex[cMAX] < ms){
            	int[] tmpValIndex = new int[cMAX+1];
            	for (int x = 0; x < cMAX+1; x++) {
            		tmpValIndex[x]=valIndex[x];
				}
            	if(cantainExcludeIndex(excludeIndex,tmpValIndex)==false
            			&& hasIncludeIndex(includeIndex,tmpValIndex,m)){
            		int passNum = m*1000+n;
                	if("min".equals(target)){
                		List<double[]> list = mn(passNum,val,valIndex);
                		double[] vs = calMinInList(list);
                		double v = 1;
                		for (double d : vs) {
							v*=d;
						}
                		if(returnVal == 0 || v < returnVal){
	                		returnVal = v;
	                		model.setMinDetailExpress(vs);
	                	}
                	} else {
                		List<double[]> list = mn(passNum,val,valIndex);
                		model.getDetailExpress().addAll(list);
                		double v = calSumInList(list);
                		returnVal+=v;
                	}
            	}
                valIndex[cMAX]++;
            }
            i = cMAX - 1;
            while(i >= 0){
                if (valIndex[i] + 1 < valIndex[i + 1]) {
                    valIndex[i]++;
                    break;
                }
                i--;
            }
        }
        if("min".equals(target)){
        	model.setMinBonus((float)returnVal);
        } else {
        	model.setMaxBonus((float)returnVal);
        }
		return model;
	}
	
	private static double calSumInList(List<double[]> list){
		double sum=0;
		for (double[] ds : list) {
			double sum1=1;
			for (int i = 0; i < ds.length; i++) {
				sum1 *= ds[i];
			}
			sum+=sum1;
		}
		return sum;
	}
	
	private static double[] calMinInList(List<double[]> list){
		double[] minArr=new double[]{};
		double min=0;
		for (double[] ds : list) {
			double sum=1;
			for (int i = 0; i < ds.length; i++) {
				sum *= ds[i];
			}
			if(min ==0 || sum < min){
				min = sum;
				minArr = ds;
			}
		}
		return minArr;
	}
	
	private static boolean hasIncludeIndex(List<Integer[]> includeIndex,int[] val,int m){
		if(includeIndex.size() < 1){//不需要计算
			return true;
		}
		Map<String, String> valMap = new HashMap<String, String>();
		for (int i = 0; i < m; i++) {
			valMap.put(""+val[i],""+val[i]);
		}
		boolean ret = true;
		for (int i = 0; i < includeIndex.size(); i++) {
			boolean a = false;
			for (int j = 0; j < includeIndex.get(i).length; j++) {
				if(null != valMap.get(""+includeIndex.get(i)[j])){
					a = true;
				}
			}
			if(!a){
				ret = false;
				break;
			}
		}
		return ret;
	}
	
	private static boolean cantainExcludeIndex(List<Integer[]> excludeIndex,int[] val){
		for (int j = 0; j < excludeIndex.size(); j++) {
    		int count=0;
    		for (int i = 0; i < val.length; i++) {
				for (int k = 0; k < excludeIndex.get(j).length; k++) {
					if(excludeIndex.get(j)[k].equals(val[i])){
						count ++;
					}
					if(count > 1){
						return true;
					}
				}
			}
		}
    	return false;
	}
	
	/**
	 * 返回所有组合
	 * @param passType
	 * @param val
	 * @param cur
	 * @return
	 */
    public static final List<double[]> mn(int passType, double[] val, int[] cur){
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
        List<double[]> ret= new ArrayList<>();
        switch(passType){
            case 1001:
            	ret.add(new double[]{a});
                return ret;
            case 2001:
            	ret.add(new double[]{a,b});
            	return ret;
            case 2003:
            	ret.addAll(combine(new double[]{a,b}, 1));
            	ret.addAll(combine(new double[]{a,b}, 2));
            	return ret;
            case 3001:
            	ret.add(new double[]{a,b,c});
            	return ret;
            case 3003:
            	ret.addAll(combine(new double[]{a,b,c}, 2));
            	return ret;
            case 3004:
            	ret.addAll(combine(new double[]{a,b,c}, 2));
            	ret.addAll(combine(new double[]{a,b,c}, 3));
            	return ret;
            case 3007:
            	ret.addAll(combine(new double[]{a,b,c}, 1));
            	ret.addAll(combine(new double[]{a,b,c}, 2));
            	ret.addAll(combine(new double[]{a,b,c}, 3));
            	return ret;
            case 4001:
            	ret.addAll(combine(new double[]{a,b,c,d}, 4));
            	return ret;
            case 4004:
            	ret.addAll(combine(new double[]{a,b,c,d}, 3));
            	return ret;
            case 4005:
            	ret.addAll(combine(new double[]{a,b,c,d}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d}, 4));
            	return ret;
            case 4006:
            	ret.addAll(combine(new double[]{a,b,c,d}, 2));
            	return ret;
            case 4011:
            	ret.addAll(combine(new double[]{a,b,c,d}, 2));
            	ret.addAll(combine(new double[]{a,b,c,d}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d}, 4));
            	return ret;
            case 4015:
            	ret.addAll(combine(new double[]{a,b,c,d}, 1));
            	ret.addAll(combine(new double[]{a,b,c,d}, 2));
            	ret.addAll(combine(new double[]{a,b,c,d}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d}, 4));
            	return ret;
            case 5001:
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 5));
            	return ret;
            case 5005:
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 4));
            	return ret;
            case 5006:
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 4));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 5));
            	return ret;
            case 5010:
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 2));
            	return ret;
            case 5016:
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 4));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 5));
            	return ret;
            case 5020:
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 2));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 3));
            	return ret;
            case 5026:
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 2));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 4));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 5));
            	return ret;
            case 5031:
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 1));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 2));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 4));
            	ret.addAll(combine(new double[]{a,b,c,d,e}, 5));
            	return ret;
            case 6001:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 6));
            	return ret;
            case 6006:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 5));
            	return ret;
            case 6007:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 5));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 6));
            	return ret;
            case 6015:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 2));
            	return ret;
            case 6020:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 3));
            	return ret;
            case 6022:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 4));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 5));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 6));
            	return ret;
            case 6035:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 1));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 2));
            	return ret;
            case 6042:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 4));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 5));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 6));
            	return ret;
            case 6050:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 2));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 4));
            	return ret;
            case 6057:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 2));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 4));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 5));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 6));
            	return ret;
            case 6063:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 1));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 2));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 4));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 5));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f}, 6));
            	return ret;
            case 7001:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 7));
            	return ret;
            case 7007:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 6));
            	return ret;
            case 7008:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 6));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 7));
            	return ret;
            case 7021:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 5));
            	return ret;
            case 7035:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 4));
            	return ret;
            case 7120:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 2));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 4));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 5));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 6));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g}, 7));
            	return ret;
            case 8001:
                ret.add(new double[]{a,b,c,d,e,f,g,h});
            	return ret;
            case 8008:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 7));
            	return ret;
            case 8009:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 7));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 8));
            	return ret;
            case 8028:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 6));
            	return ret;
            case 8056:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 5));
            	return ret;
            case 8070:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 4));
            	return ret;
            case 8247:
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 2));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 3));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 4));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 5));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 6));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 7));
            	ret.addAll(combine(new double[]{a,b,c,d,e,f,g,h}, 8));
            	return ret;
            case 9001:
            	ret.add(new double[]{a,b,c,d,e,f,g,h,i});
             	return ret;
            case 10001:
                ret.add(new double[]{a,b,c,d,e,f,g,h,i,j});
            	return ret;
            case 11001:
            	ret.add(new double[]{a,b,c,d,e,f,g,h,i,j,k});
            	return ret;
            case 12001:
            	ret.add(new double[]{a,b,c,d,e,f,g,h,i,j,k,l});
            	return ret;
            case 13001:
            	ret.add(new double[]{a,b,c,d,e,f,g,h,i,j,k,l,m});
            	return ret;
            case 14001:
            	ret.add(new double[]{a,b,c,d,e,f,g,h,i,j,k,l,m,n});
            	return ret;
            case 15001:
            	ret.add(new double[]{a,b,c,d,e,f,g,h,i,j,k,l,m,n,o});
            	return ret;
            default:
                return ret;
        }
    }
    
    private static final double v(int passType, double[] val, int[] index, int i){
        if(passType > (i + 1) * 1000){
            return val[index[i]];
        }
        return 0d;
    }
    
    /**
	 * val 列表去m个数的组合
	 * @param val 候选列表
	 * @param m 
	 * @return
	 */
	public static List<double[]> combine(double[] val, int m) {
		int n = val.length;
		if (m > n) {
			System.out.println("错误！数组a中只有" + n + "个元素。" + m + "大于" + n + "!!!");
		}

		List<double[]> result= new ArrayList<>();

		int[] bs = new int[n];
		for (int i = 0; i < n; i++) {
			bs[i] = 0;
		}
		for (int i = 0; i < m; i++) {
			bs[i] = 1;
		}
		boolean flag = true;
		boolean tempFlag = false;
		int pos = 0;
		int sum = 0;
		do {
			sum = 0;
			pos = 0;
			tempFlag = true;
			result.add(makeExpress(bs, val, m));

			for (int i = 0; i < n - 1; i++) {
				if (bs[i] == 1 && bs[i + 1] == 0) {
					bs[i] = 0;
					bs[i + 1] = 1;
					pos = i;
					break;
				}
			}
			for (int i = 0; i < pos; i++) {
				if (bs[i] == 1) {
					sum++;
				}
			}
			for (int i = 0; i < pos; i++) {
				if (i < sum) {
					bs[i] = 1;
				} else {
					bs[i] = 0;
				}
			}
			for (int i = n - m; i < n; i++) {
				if (bs[i] == 0) {
					tempFlag = false;
					break;
				}
			}
			if (tempFlag == false) {
				flag = true;
			} else {
				flag = false;
			}

		} while (flag);
		if(n!=m){
			result.add(makeExpress(bs, val, m));
		}
		return result;
	}

	private static double[] makeExpress(int[] bs, double[] a, int m) {
		double[] result = new double[m];
		int pos = 0;
		for (int i = 0; i < bs.length; i++) {
			if (bs[i] == 1) {
				result[pos] = a[i];
				pos++;
			}
		}
		return result;
	}
}
