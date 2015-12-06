package com.laicai.lotteryCategory.frequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.laicai.util.ComputerUtils;

public class GroupSelectionStrategy extends FrequenceLotteryStrategy {
  
  /**
   * 组选
   * @param selectNum 需要选择的数字
   */
  public GroupSelectionStrategy(int selectNum){
    super.selectNum = selectNum;
  }
  
  /**
   * 组选方案
   * @param selectNum 需要选择几个数字
   * @param det 至少几个胆码
   * @param collections
   *        类型：均为Set<Integer>,如果没有胆码，则det=0，此时collections的长度为1，第0个为数字集合，当det>0,
   *        则第1个为胆码集合,注意：胆码和数字集合之间没有交集，即两个集合不能有相同的数字，
   *        必须传递是多个Set集合，不能将Set集合封装成ArrayList等类型
   */
  @SuppressWarnings("unchecked")
  public GroupSelectionStrategy(int selectNum, int det,
      Collection<?>... collections) {
    this.selectNum = selectNum;
    this.collection = new ArrayList<Set<Integer>>();
    this.det = det;
    for (Collection<?> collection : collections) {
      Set<Integer> numberSet = (Set<Integer>) collection;
      ((ArrayList<Set<Integer>>) this.collection).add(numberSet);
    }
  }


  @SuppressWarnings("unchecked")
  @Override
  public int computeBetTotalCount() {
    int betCount = 0;
    if (det == 0) {
      betCount =
          ComputerUtils
              .betCountOfArbOrGroupChoose(
                  ((ArrayList<Set<Integer>>) collection).get(0), selectNum,
                  null, 0);
    } else {
      betCount =
          ComputerUtils.betCountOfArbOrGroupChoose(
              ((ArrayList<Set<Integer>>) collection).get(0), selectNum,
              ((ArrayList<Set<Integer>>) collection).get(1), det);
    }

    return betCount;
  }


  @Override
  public ArrayList<ArrayList<Integer>> getRandomRes(int betCount, int startScope, int endScope)
  {
    // TODO Auto-generated method stub
    return ComputerUtils.getCombinationRandomRes(betCount, selectNum, startScope, endScope, true);
  }

}
