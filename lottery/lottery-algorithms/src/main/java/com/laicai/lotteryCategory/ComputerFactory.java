package com.laicai.lotteryCategory;
import java.util.HashMap;
import java.util.Map;

import com.laicai.lotteryCategory.frequence.FrequenceLotterComputer;
import com.laicai.lotteryCategory.sports.SportsLotteryComputer;




public class ComputerFactory
{
  private static Map<LotteryCategoryEnum, LotteryComputer> computers = new HashMap<LotteryCategoryEnum, LotteryComputer>();
  
  public synchronized static LotteryComputer getComputer(LotteryCategoryEnum lotteryCategory) 
  {
    LotteryComputer computer = computers.get(lotteryCategory);
    if(computer == null)
    {
      switch (lotteryCategory)
      {
        case FREQUENCE:
          computer = new FrequenceLotterComputer();
          computers.put(lotteryCategory, computer);
          break;
        case SPORTS:
          computer = new SportsLotteryComputer();
          computers.put(lotteryCategory, computer);
          break;
        default:
          break;
      }
    }
    
    return computer;
  }
}
