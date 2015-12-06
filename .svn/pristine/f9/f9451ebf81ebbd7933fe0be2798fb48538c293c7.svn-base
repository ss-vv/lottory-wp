package com.laicai.lotteryCategory.sports;

import java.util.Map;

import com.laicai.util.ComputerUtils;

/**
 * 单场
 * @author pyz
 */
public class SingleSceneStrategy extends SportsLotteryStrategy {
  private Map<String, float[]> chooseMap;


  /**
   * @param chooseMap string为比赛ID，float[]为比赛的所有赔率数组，
   *        选择了的就改为结果的赔率，不选中就设为0.例如：比赛A，胜sp=3.01,平sp=2.05
   *        负sp=2.56,用户选了胜和平，则传过来的float[]={3.01,2.05,0}
   */
  public SingleSceneStrategy(Map<String, float[]> chooseMap) {
    this.chooseMap = chooseMap;
  }


  @Override
  public int computeBetTotalCount() {
    float[] betInfo = ComputerUtils.sportsSingleSeceneBetInfo(chooseMap);
    return (int) betInfo[0];
  }


  @Override
  public float[] getMaxAndMinPrize() {
    float[] betInfo = ComputerUtils.sportsSingleSeceneBetInfo(chooseMap);
    return new float[] { betInfo[1], betInfo[2] };
  }


  public Map<String, float[]> getChooseMap() {
    return chooseMap;
  }


  public void setChooseMap(Map<String, float[]> chooseMap) {
    this.chooseMap = chooseMap;
  }

}
