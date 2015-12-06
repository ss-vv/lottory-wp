package com.laicai.lotteryCategory.frequence;

import java.util.Collection;

import com.laicai.lotteryCategory.AbstractStrategy;
import com.laicai.lotteryCategory.LotteryComputer;

public class FrequenceLotterComputer extends LotteryComputer
{

  @Override
  public int getBetCount(AbstractStrategy strategy)
  {
    // TODO Auto-generated method stub
    return strategy.computeBetTotalCount();
  }
  
  /**
   * 获取随机结果
   * @param betCount 随机注数
   * @param startScope 随机数起始范围
   * @param endScope 随机数结束范围
   * @return
   */
  public Collection<?> getRandomRes(FrequenceLotteryStrategy strategy, int betCount, int startScope, int endScope){
    return strategy.getRandomRes(betCount, startScope, endScope);
  }
}
