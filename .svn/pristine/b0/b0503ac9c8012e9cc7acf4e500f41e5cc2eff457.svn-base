package com.laicai.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * 组合算法类.<br/>
 * <a href="http://stackoverflow.com/questions/127704/algorithm-to-return-all-combinations-of-k-elements-from-n">参考StackOverflow</a>
 * 和 TAOC by Knuth.
 * @author Yang Bo
 */
public class Combination {

	/**
	 * 从 n 个数中取 t 个数的组合, n >= t > 0, 用 Algorithm L 实现。
	 */
	public static void generateWithAlgorithmL(int n, int t, Visitor visitor){
		// Initialize.
		int[] c = new int[t+2+1]; // c[0] 为了方便废弃
		c[t+1] = n;
		c[t+2] = 0;
		for (int j=1; j<=t; j++){
			c[j] = j-1;
		}
		while(true){
			// Visit.
			int[] v = Arrays.copyOfRange(c, 1, t+1);
			visitor.visit(v);
			// Find j.
			int j=1;
			for (; c[j]+1==c[j+1]; j++){
				c[j] = j-1;
			}
			// Done?
			if (j>t){
				return;
			}
			// Increase cj.
			c[j] = c[j] + 1;
		}
	}
	
	public static void permutationWithLAlgorithm(int[] a){
		int total = 0;
		while(true){
			// Visit.
			System.out.println(Arrays.toString(a));
			total++;
			// Find j.
			int n = a.length-1;
			int j = n - 1;
			while( a[j] >=a [j+1] ){
				j--;
			}
			if (j==0){
				System.out.println("Total: " + total);
				return;
			}
			// Increase j. 找到比 a[j] 大的 a[l]
			int l = n;
			while( a[j] >= a[l] ){
				l--;
			}
			int tmp = a[j];	// interchange
			a[j] = a[l];
			a[l] = tmp;
			// Reverse a(j+1)...a(n).
			int k=j+1; l=n;
			while( k<l ){
				tmp = a[k];
				a[k] = a[l];
				a[l] = tmp;
				k++; l--;
			}
		}
	}
	
	/**
	 * 列出所有可能的数字排列。如: [1,2],[3,4],[5,6] => [1,3,5],[1,3,6],[1,4,5]...
	 * @param digits [1,2],[3,4],[5,6]
	 * @return [1,3,5],[1,3,6],[1,4,5]...
	 */
	public static List<int[]> listCombination(List<int[]> digits){
		if (digits.size() == 1){
			List<int[]> ret = new LinkedList<int[]>();
			for (int n : digits.get(0)){
				ret.add(new int[]{n});
			}
			return ret;
		}
		int[] firstDigits = digits.get(0);
		List<int[]> tailExpanded = listCombination(digits.subList(1, digits.size()));
		int retArrayLen = digits.size();
		List<int[]> retArrayList = new LinkedList<int[]>();
		for (int i=0; i<firstDigits.length; i++){
			int num = firstDigits[i];
			for (int[] tail : tailExpanded){
				int[] retArray = new int[retArrayLen];
				retArray[0] = num;
				for (int j=0; j<retArrayLen-1; j++){
					retArray[j+1] = tail[j];
				}
				retArrayList.add(retArray);
			}
		}
		return retArrayList;
	}
	
	// ============== 下面是试验代码，不能用于生产 =================
	/**
	 * !!试验代码，不能用于生产!!
	 * List M Objects out of N Objects. Using Gray Codes algorithm.
	 * @param nObjects 总对象列表, n > m
	 * @param m 目标对象数量
	 * @return 所有的可能排列
	 */
	public static List<List<Object>> listMOutOfNObjects(List<Object> nObjects, int m){
		if (m == nObjects.size()){
			List<List<Object>> vectorList = new LinkedList<List<Object>>();
//			for (Object obj : nObjects) {
//				LinkedList<Object> vector = new LinkedList<Object>();
//				vector.add(obj);
//				vectorList.add(vector);
//			}
			vectorList.add(nObjects);
			return vectorList;
		}
		if (m == 1){
			List<List<Object>> vectorList = new LinkedList<List<Object>>();
			for (Object obj : nObjects) {
				LinkedList<Object> vector = new LinkedList<Object>();
				vector.add(obj);
				vectorList.add(vector);
			}
			vectorList.add(nObjects);
			return vectorList;
		}
		List<List<Object>> vectorList = new LinkedList<List<Object>>();
		int n = nObjects.size();
		for (int i=0; i<n-m; i++){
			Object firstObj = nObjects.get(i);
			ArrayList<Object> remains = new ArrayList<Object>(nObjects.size());
			remains.addAll(nObjects);
			remains.remove(i);
			List<List<Object>> remainVectorList = listMOutOfNObjects(remains, m-1);
			for (List<Object> vector : vectorList) {
				vector.add(0, firstObj);
			}
			vectorList.addAll(remainVectorList);
			remainVectorList = listMOutOfNObjects(remains, m);
			vectorList.addAll(remainVectorList);
		}
		return vectorList;
	}
}
