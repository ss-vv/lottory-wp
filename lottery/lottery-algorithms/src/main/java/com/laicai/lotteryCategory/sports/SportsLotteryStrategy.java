package com.laicai.lotteryCategory.sports;

import com.laicai.lotteryCategory.AbstractStrategy;

public abstract class SportsLotteryStrategy extends AbstractStrategy
{
  
  protected int singleBetPrice = 2;
  public abstract int computeBetTotalCount();
  
  public abstract float[] getMaxAndMinPrize();
}
