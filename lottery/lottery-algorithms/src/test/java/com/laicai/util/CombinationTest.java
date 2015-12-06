package com.laicai.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class CombinationTest {

//	@Test
	public void test() {
		List<Object> nObjects = Arrays.asList(new Object[]{"01","02","03","04","05","06"});
		List<List<Object>> combines = Combination.listMOutOfNObjects(nObjects, 3);
		for(List<Object> vector : combines){
			System.out.println(vector);
		}
	}

	Visitor v = new Visitor(){
		private int total;
		@Override
		public void visit(int[] combination) {
			for (int i=0; i<combination.length; i++){
				combination[i] += 1;
			}
			System.out.println(Arrays.toString(combination));
			total++;
		}
		
		@Override
		public int getTotal() {
			return total;
		}
		
	};

	@Test
	public void testGenerateWithAlgorithmL(){
		Combination.generateWithAlgorithmL(11, 8, v);
		System.out.println("total count: " + v.getTotal());
		assertEquals(165, v.getTotal());
	}
	@Test
	public void testGenerateWithAlgorithmLWith8From8(){
		Combination.generateWithAlgorithmL(8, 8, v);
		System.out.println("total count: " + v.getTotal());
		assertEquals(1, v.getTotal());
	}
	
	@Test
	public void testPermulationWithLAlgorithm(){
		int[] a = {0,1,2,3,4,5};
		Combination.permutationWithLAlgorithm(a);
	}
	
	@Test
	public void testListCombinations(){
		LinkedList<int[]> digits = new LinkedList<int[]>();
		digits.add(new int[]{1,2,3});
		digits.add(new int[]{4,5});
		digits.add(new int[]{6,7,8});
		List<int[]> numsList = Combination.listCombination(digits);
		System.out.println("---- testListCombinations:");
		for (int[] num : numsList) {
			for (int n : num){
				System.out.print(n+",");
			}
			System.out.println();
		}
		assertEquals(numsList.get(0).length, 3);
		assertEquals(18, numsList.size());
	}
	
	@Test
	public void testComputerUtils(){
		long result = ComputerUtils.combination(13, 6);
		assertEquals(1716, result);
		
		result = ComputerUtils.combination(4, 2);
		assertEquals(6, result);
		
		result = ComputerUtils.combination(33, 16);
		assertEquals(1166803110, result);
		
		result = ComputerUtils.combination(35, 17);
		assertEquals(4537567650L, result);
	}
}
