package com.laicai.lotteryCategory.sports;

import java.util.Map;

import com.laicai.util.ComputerUtils;

/**
 * 串场
 * @author pyz
 */
public class MultiSceneStrategy extends SportsLotteryStrategy {

  private Map<String, float[]> undetMap;
  private Map<String, float[]> detMap;
  private int multiCount;


  /**
   * 串场
   * @param undetMap 非胆集合。string为比赛ID，float[]为比赛的所有赔率数组，
   *        选择了的就改为结果的赔率，不选中就设为0.例如：比赛A，胜sp=3.01,平sp=2.05
   *        负sp=2.56,用户选了胜和平，则传过来的float[]={3.01,2.05,0}
   * @param detMap 胆集合
   * @param multiCount m串1的m值，例如4串1就为4
   */
  public MultiSceneStrategy(Map<String, float[]> undetMap,
      Map<String, float[]> detMap, int multiCount) {
    this.undetMap = undetMap;
    this.detMap = detMap;
    this.multiCount = multiCount;
  }


  @Override
  public int computeBetTotalCount() {
    float[] multiBetInfo =
        ComputerUtils.sportsMultiSceneBetInfo(undetMap, detMap, multiCount);
    return (int) multiBetInfo[0];
  }


  @Override
  public float[] getMaxAndMinPrize() {
    float[] multiBetInfo =
        ComputerUtils.sportsMultiSceneBetInfo(undetMap, detMap, multiCount);
    return new float[] { singleBetPrice * multiBetInfo[1],
        singleBetPrice * multiBetInfo[2] };
  }


  public Map<String, float[]> getUndetMap() {
    return undetMap;
  }


  public void setUndetMap(Map<String, float[]> undetMap) {
    this.undetMap = undetMap;
  }


  public Map<String, float[]> getDetMap() {
    return detMap;
  }


  public void setDetMap(Map<String, float[]> detMap) {
    this.detMap = detMap;
  }


  public int getMultiCount() {
    return multiCount;
  }


  public void setMultiCount(int multiCount) {
    this.multiCount = multiCount;
  }

}
