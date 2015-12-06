package com.laicai.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * 数学计算的方法
 * @author Wangjie.Zhao 2011-8-30下午3:49:33
 */
public class ComputerUtils {

  /**
   * 竞彩中单关投注信息
   * @param chooseList 每条记录代表一场比赛，每个float[]数组代表这场比赛选中的结果的投注赔率，
   *        没有选中的比赛赔率设为<=0就可以，选中的就设为这个结果的赔率。例如：比赛A，胜sp=3.01,平sp=2.05
   *        负sp=2.56,用户选了胜和平，则传过来的float[]={3.01,2.05,0}
   * @return 长度为3的数组：float[]={注数，最小奖金，最大奖金}
   */
  public static float[] sportsSingleSeceneBetInfo(Map<String, float[]> chooseMap) {
    int betCount = 0;
    float minBonus = 0;
    float maxBonus = 0;
    if (chooseMap != null && chooseMap.keySet().size() > 0) {
      for (String keyId : chooseMap.keySet()) {
        float[] oddsFloat = chooseMap.get(keyId);
        float[] oddsBetInfo = computeBetInfoForOneMatch(oddsFloat);
        betCount += oddsBetInfo[0];
        if (minBonus == 0) {
          minBonus = oddsBetInfo[1];
        }
        if (minBonus > oddsBetInfo[1]) {
          minBonus = oddsBetInfo[1];
        }
        maxBonus += oddsBetInfo[2];
      }
    }
    return new float[] { betCount, minBonus, maxBonus };
  }


  /**
   * 过关模式的投注信息
   * @param undetMap
   *        选择的非胆集合，string为这场比赛的Id,float[]为选择的赔率集合，没有选中则是0.例如：比赛A，胜sp=3.01,平sp=
   *        2.05 负sp=2.56,用户选了胜和平，则传过来的float[]={3.01,2.05,0}
   * @param detMap 胆集合，信息与undetMap一致. 例如：比赛A，胜sp=3.01,平sp=2.05
   *        负sp=2.56,用户选了胜和平，则传过来的float[]={3.01,2.05,0}
   * @param n n串1
   * @return 长度为3的数组：float[]={注数，最小奖金，最大奖金}
   */
  public static float[] sportsMultiSceneBetInfo(Map<String, float[]> undetMap,
      Map<String, float[]> detMap, int n) {
    // 备份数据
    Map<String, float[]> copyUndetMap = new HashMap<String, float[]>();
    if (undetMap != null) {
      for (String key : undetMap.keySet()) {
        copyUndetMap.put(key, undetMap.get(key));
      }
    }
    Map<String, float[]> copyDetMap = new HashMap<String, float[]>();
    if (detMap != null) {
      for (String key : detMap.keySet()) {
        copyDetMap.put(key, detMap.get(key));
      }
    }
    int betCount = 0;
    float minBonus = 0;
    float maxBonus = 0;
    if ((copyUndetMap.keySet().size() + copyDetMap.keySet().size()) < n
        || n <= 0 || copyDetMap.keySet().size() > n) {
      return new float[] { 0, 0, 0 };// 集合数小于n，不符合条件，返回的注数为0，赔率都为0
    }
    if (copyDetMap.keySet().size() == 0) {// 无胆情况
      if (n == 1) {// 为有胆的特殊情况处理
        // 计算出每场比赛的选择个数和最大最小赔率
        for (String matchId : copyUndetMap.keySet()) {
          float[] oneMatchOdds =
              computeBetInfoForOneMatch(copyUndetMap.get(matchId));
          betCount += (int) oneMatchOdds[0];
          if (minBonus == 0) {
            minBonus = oneMatchOdds[1];
          }
          if (minBonus > oneMatchOdds[1]) {
            minBonus = oneMatchOdds[1];
          }
          maxBonus += oneMatchOdds[2];
        }
      } else if (n == 2) {// 最小是2串1
        ArrayList<float[]> matchsBetInfoList = new ArrayList<float[]>();
        // 计算出每场比赛的选择个数和最大最小赔率
        for (String matchId : copyUndetMap.keySet()) {
          float[] oneMatchOdds =
              computeBetInfoForOneMatch(copyUndetMap.get(matchId));
          matchsBetInfoList.add(oneMatchOdds);
        }
        for (int i = 0; i < matchsBetInfoList.size() - 1; i++) {
          float[] oneMatchOdds = matchsBetInfoList.get(i);
          for (int j = i + 1; j < matchsBetInfoList.size(); j++) {
            float[] secondMatchOdds = matchsBetInfoList.get(j);
            betCount += (int) oneMatchOdds[0] * (int) secondMatchOdds[0];// 投注数
            float currentMinBonus = oneMatchOdds[1] * secondMatchOdds[1];
            float currentMaxBonus = oneMatchOdds[2] * secondMatchOdds[2];
            if (minBonus == 0) {
              minBonus = currentMinBonus;
            }
            if (minBonus > currentMinBonus) {
              minBonus = currentMinBonus;
            }
            maxBonus += currentMaxBonus;
          }
        }
        matchsBetInfoList.clear();
        matchsBetInfoList = null;
      } else {// n>2
        String referenceId = null;// 参考Id
        for (String keyId : copyUndetMap.keySet()) {
          if (keyId != null && keyId.length() > 0) {
            referenceId = keyId;
            break;
          }
        }
        if (referenceId != null) {
          float[] oddsA = copyUndetMap.get(referenceId);
          copyUndetMap.remove(referenceId);
          // 包含A的(n-1)串1的结果
          float[] relationA =
              sportsMultiSceneBetInfo(copyUndetMap, null, n - 1);
          float[] unrelationA = sportsMultiSceneBetInfo(copyUndetMap, null, n);
          float[] betInfoForMatchA = computeBetInfoForOneMatch(oddsA);// A比赛的投注信息
          // 总的投注个数
          betCount =
              (int) betInfoForMatchA[0] * (int) relationA[0]
                  + (int) unrelationA[0];
          // 最低中奖赔率
          if (unrelationA[1] != 0) {
            minBonus =
                (relationA[1] * betInfoForMatchA[1]) < unrelationA[1] ? (relationA[1] * betInfoForMatchA[1])
                    : unrelationA[1];
          } else {
            minBonus = (relationA[1] * betInfoForMatchA[1]);
          }
          // 最高赔率
          maxBonus = relationA[2] * betInfoForMatchA[2] + unrelationA[2];
        }
      }
    } else {// 包含胆
      ArrayList<float[]> betInfoForDetList = new ArrayList<float[]>();
      for (String keyId : copyDetMap.keySet()) {
        float[] oneMatchOdds = computeBetInfoForOneMatch(copyDetMap.get(keyId));
        betInfoForDetList.add(oneMatchOdds);
      }
      if ((n - betInfoForDetList.size()) > 0) {
        float[] betInfoForUndet =
            sportsMultiSceneBetInfo(copyUndetMap, null,
                n - betInfoForDetList.size());

        betCount = (int) betInfoForUndet[0];
        minBonus = betInfoForUndet[1];
        maxBonus = betInfoForUndet[2];
      } else {
        betCount = 1;
        minBonus = 1;
        maxBonus = 1;
      }
      // 赔率相乘
      for (float[] oneMatchOdds : betInfoForDetList) {
        betCount *= (int) oneMatchOdds[0];
        minBonus *= oneMatchOdds[1];
        maxBonus *= oneMatchOdds[2];
      }
      // 销毁临时数据
      betInfoForDetList.clear();
      betInfoForDetList = null;
    }
    // 销毁备份数据
    copyUndetMap.clear();
    copyDetMap.clear();
    copyUndetMap = null;
    copyUndetMap = null;
    return new float[] { betCount, minBonus, maxBonus };
  }


  /**
   * 计算一组赔率中的赔率个数、最小赔率和最大赔率
   * @param odds 一场比赛的选择结果
   * @return
   */
  private static float[] computeBetInfoForOneMatch(float[] odds) {
    int betCount = 0;
    float minBonus = 0;
    float maxBonus = 0;
    for (float odd : odds) {
      if (odd > 0) {
        betCount++;
        if (minBonus == 0) {
          minBonus = odd;// 初始化最小赔率
        }
        if (minBonus > odd) {
          minBonus = odd;
        }
        if (maxBonus < odd) {
          maxBonus = odd;
        }
      }
    }
    return new float[] { betCount, minBonus, maxBonus };
  }


  /**
   * 获取随机数
   * @param numCount 获取随机数的个数
   * @param startScope 随机数的起始范围
   * @param endScope 随机数的结束范围
   * @param isDuplicateRemoval 是否去重
   * @return
   */
  public static ArrayList<Integer> getRandomRes(int numCount, int startScope,
      int endScope, boolean isDuplicateRemoval) {
    ArrayList<Integer> randomArray = new ArrayList<Integer>();
    // 范围数字的个数
    int count = endScope - startScope + 1;

    Random rand = new Random();
    boolean[] bool = new boolean[count];
    int randInt = 0;
    for (int i = 0; i < numCount; i++) {
      if (isDuplicateRemoval) {
        do {
          randInt = rand.nextInt(count) + startScope;
        }
        while (bool[randInt - startScope]);
        bool[randInt - startScope] = true;
      } else {
        randInt = rand.nextInt(count) + startScope;
      }
      randomArray.add(randInt);
    }
    return randomArray;
  }
  
  /**
   * 获取排列随机数
   * @param betCount 注数
   * @param numCount 获取每注随机数的个数
   * @param startScope 随机数的起始范围
   * @param endScope 随机数的结束范围
   * @param isDuplicateRemoval 是否去重
   * @return
   */
  public static ArrayList<ArrayList<Integer>> getPermutationRandomRes(int betCount, int numCount, int startScope,
      int endScope, boolean isDuplicateRemoval) {
//    if(betCount > permutation(endScope-startScope+1,numCount))
//      betCount = permutation(endScope-startScope+1,numCount);
    
    ArrayList<ArrayList<Integer>> returnArray = new ArrayList<ArrayList<Integer>>();
    int i=0;
    while(betCount > i){
      ArrayList<Integer> arrayList = getRandomRes(numCount, startScope, endScope, isDuplicateRemoval);
      if(returnArray.contains(arrayList)){
        if(i < permutation(endScope-startScope+1,numCount))
          continue;
        else
          returnArray.add(arrayList);
      }else{
        returnArray.add(arrayList);
      }
      i++;
    }

    return returnArray;
  }
  
  /**
   * 获取组合随机数
   * @param betCount 注数
   * @param numCount 获取随机数的个数
   * @param startScope 随机数的起始范围
   * @param endScope 随机数的结束范围
   * @param isDuplicateRemoval 是否去重
   * @return
   */
  public static ArrayList<ArrayList<Integer>> getCombinationRandomRes(int betCount, int numCount, int startScope,
      int endScope, boolean isDuplicateRemoval){
//    if(betCount > combination(endScope-startScope+1,numCount))
//      betCount = combination(endScope-startScope+1,numCount);
    
    ArrayList<ArrayList<Integer>> returnArray = new ArrayList<ArrayList<Integer>>();
    int count=0;
    while(betCount > count){
      ArrayList<Integer> arrayList = getRandomRes(numCount, startScope, endScope, isDuplicateRemoval);
      //冒泡排序
      for (int i = 0; i < arrayList.size(); i++) {
        for (int j = 0; j < arrayList.size(); j++) {
          int temp;
          if (arrayList.get(i) < arrayList.get(j)) {
            temp = arrayList.get(j);
            arrayList.set(j, arrayList.get(i));
            arrayList.set(i, temp);
          }
        }
      }
      
      if(returnArray.contains(arrayList)){
        if(count < combination(endScope-startScope+1,numCount))
          continue;
        else
          returnArray.add(arrayList);
      }else{
        returnArray.add(arrayList);
      }
      count++;
    }

    return returnArray;
  }


  /**
   * 任选和组选方案获取的投注数
   * @param numberSet 选择的数字集合，或者是拖码
   * @param arbOrGroupCount 任选和组选的需要选择的数字个数，比如前二组选就是2，任选三就是3
   * @param detSet 胆码集合，胆码必须是numberSet的没有交集,没有就为null
   * @param detLessCount 每一注方案至少含有几个胆码，1=<数字<=detSet.size()（包含全部胆码），没有胆码可设为0
   * @return
   */
  public static int betCountOfArbOrGroupChoose(Set<Integer> numberSet,
      int arbOrGroupCount, Set<Integer> detSet, int detLessCount) {
    long betCount = 0;
    if (numberSet == null
        || numberSet.size() == 0
        || (detSet != null && (detSet.size() + numberSet.size()) < arbOrGroupCount)
        || (detSet == null && numberSet.size() < arbOrGroupCount)
        || (detSet != null && detSet.size() >= arbOrGroupCount)) {
      // 没有选择号码，选择的号码数少于预定个数，或者胆数不少于预定个数，此时均为0注
      betCount = 0;
    } else {
      if (detSet != null && detSet.size() > 0) {
        // 含有胆码
        for (int i = detLessCount; i <= detSet.size(); i++) {
          betCount +=
              (combination(detSet.size(), i) * combination(numberSet.size(),
                  arbOrGroupCount - i));
        }
      } else {
        // 没有胆码
        betCount = combination(numberSet.size(), arbOrGroupCount);
      }

    }
    return (int)betCount;
  }


  /**
   * 获取直选投注方案的注数
   * @param numberSets 选择的数字集合，是几个直选集合的大小就是几个。比如前三直选，则需要三个数据，
   *        分别在0，1，2为（左起）第一位，第二位，第三位
   * @param chooseCount 选几个数字
   * @return
   */
  public static int betCountOfDirectChoose(ArrayList<Set<Integer>> numberSets,
      int chooseCount) {
    int betCount = 0;
    if (numberSets.size() == chooseCount) {
      for (Set<Integer> numbers : numberSets) {
        // 数据中如果有一个的集合为null或者为空，没有注数
        if (numbers == null || numbers.size() == 0) {
          return 0;
        }
      }
      if (chooseCount == 1) {// 直选1
        betCount = permutation(numberSets.get(0).size(), chooseCount);
      } else if (chooseCount == 2) {// 直选2
        int totalCount = 0;// 不考虑重复情况下的所有组合
        int sameCount = 0;// 两两重复的组合
        totalCount = numberSets.get(0).size() * numberSets.get(1).size();
        for (Integer i : numberSets.get(0)) {
          if (numberSets.get(1).contains(i)) {
            sameCount++;
          }
        }
        betCount = totalCount - sameCount;
      } else {// 直选3及以上
        int totalCount = 0;
        int sameCount = 0;
        ArrayList<Set<Integer>> otherSets = new ArrayList<Set<Integer>>();
        for (int i = 1; i < chooseCount; i++) {
          otherSets.add(i - 1, numberSets.get(i));
        }
        // 以numberSets[0]作为参考集合，计算后面集合的直选结果（包括numberSets[0]与其他集合相同的数据）
        totalCount =
            numberSets.get(0).size()
                * betCountOfDirectChoose(otherSets, chooseCount - 1);
        // 去重
        Set<Integer> sameSet = new HashSet<Integer>();// 放置集合numberSets[0]与其他集合相同的数据
        for (int i = 0; i < otherSets.size(); i++) {
          for (Integer j : numberSets.get(0)) {
            if (otherSets.get(i).contains(j)) {
              sameSet.add(j);
            }
          }
          ArrayList<Set<Integer>> copySets = new ArrayList<Set<Integer>>();
          copySets.addAll(otherSets);
          copySets.set(i, sameSet);// 复制一遍后将对应的i更改为相同的集合

          sameCount += betCountOfDirectChoose(copySets, copySets.size());
          copySets = null;
          sameSet.clear();
        }
        betCount = totalCount - sameCount;
      }
    }
    return betCount;
  }


  /**
   * 排列计数
   * @param totalCount 源个数
   * @param arrangeCount 需要排列的个数
   * @return
   */
  public static int permutation(int totalCount, int arrangeCount) {
    int sum = 0;
    if (arrangeCount > totalCount) {
      sum = 0;
    } else {
      sum = (int)(factorial(totalCount) / factorial(totalCount - arrangeCount));
    }

    return sum;
  }


  /**
   * 组合计算
   * @param totalCount
   * @param arrangeCount
   * @return
   */
  public static int combinationByFactorial(int totalCount, int arrangeCount) {
    long sum = 0;
    if (arrangeCount <= 0 || totalCount <= 0) {
      sum = 0;
    } else {
      if (arrangeCount > totalCount) {
        sum = 0;
      } else if (totalCount == arrangeCount) {
        sum = 1;
      } else {
        sum =
            factorial(totalCount)
                / (factorial(arrangeCount) * factorial(totalCount
                    - arrangeCount));
      }
    }

    return (int)sum;
  }


  /**
   * 计算阶乘
   * @param number
   * @return
   */
  public static long factorial(int number) {
    long sum = 1;
    if (number > 1) {
      for (int m = number; m >= 1; m--) {
        sum *= m;
      }
    }
    return sum;
  }
  
  /**
	 * 组合数函数，无阶乘算法。
	 * 
	 * @param m
	 * @param n
	 * @return
	 */
	public static long combination(int m, int n)
	{
		if (n > m || n < 0 || m < 0)
		{
			throw new IllegalArgumentException("传递的参数 m 或 n 为无效的参数!");
		}
		else if (m == 0 || m == 0)
		{
			return 0;
		}
		else
		{
			if (n > m - n)
			{
				n = m - n;
			}

			BigInteger combination = BigInteger.valueOf(1L);
			for (long i = m; i >= m - n + 1; i--)
			{
				combination = combination.multiply(BigInteger.valueOf(i));
			}
			
			for (long i = n; i > 1; i--)
			{
				combination = combination.divide(BigInteger.valueOf(i));
			}
			
			return combination.longValue();
		}
	}
}
