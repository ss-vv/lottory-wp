package com.laicai.lotteryCategory.sports;

import com.laicai.lotteryCategory.AbstractStrategy;
import com.laicai.lotteryCategory.LotteryComputer;

public class SportsLotteryComputer extends LotteryComputer
{

  @Override
  public int getBetCount(AbstractStrategy strategy)
  {
    // TODO Auto-generated method stub
    return strategy.computeBetTotalCount();
  } 

}
