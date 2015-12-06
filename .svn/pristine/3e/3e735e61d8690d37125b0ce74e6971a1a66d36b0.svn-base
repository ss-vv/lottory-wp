package com.laicai.lotteryCategory.frequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import com.laicai.util.ComputerUtils;

public class DirectSelectionStrategy extends FrequenceLotteryStrategy {
  
  /**
   * 直选
   * @param selectNum 需要选择的数字
   */
  public DirectSelectionStrategy(int selectNum){
    super.selectNum = selectNum;
  }
  
  /**
   * 直选方案
   * @param selectNum 需要选择几个数字
   * @param det 至少几个胆码（直选中不用，设为0）
   * @param collections 直选的数字集合，每个集合类型为 Set<Integer>,必须传递是多个Set集合，不能将Set集合封装成ArrayList等类型
   */
  @SuppressWarnings("unchecked")
  public DirectSelectionStrategy(int selectNum, int det,
      Collection<?>... collections) {
    this.selectNum = selectNum;
    collection = new ArrayList<Set<Integer>>();
    this.det = det;
    for (Collection<?> collection : collections) {
      Set<Integer> numberSet = (Set<Integer>) collection;
      ((ArrayList<Set<Integer>>)this.collection).add(numberSet);
    }
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public int computeBetTotalCount() {
    int betCount =
        ComputerUtils.betCountOfDirectChoose(
            (ArrayList<Set<Integer>>) this.collection, selectNum);
    return betCount;
  }
  
  @Override
  public ArrayList<ArrayList<Integer>> getRandomRes(int betCount, int startScope, int endScope)
  {
    // TODO Auto-generated method stub
    return ComputerUtils.getPermutationRandomRes(betCount, selectNum, startScope, endScope, true);
  }

}
