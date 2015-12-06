package com.laicai.lotteryCategory.frequence;
import java.util.Collection;

import com.laicai.lotteryCategory.AbstractStrategy;



public abstract class FrequenceLotteryStrategy extends AbstractStrategy
{ 

  protected int selectNum;
  
  protected int det;

  protected Collection<?> collection;
  
  /**
   * 获取注数
   * @return
   */
  public abstract  int computeBetTotalCount();

  /**
   * 获取随机结果
   * @param betCount   注数
   * @param startScope 随机数启示范围
   * @param endScope   随机数结束范围
   * @return
   */
  public abstract Collection<?> getRandomRes(int betCount, int startScope, int endScope);
  
  
  public int getSelectNum()
  {
    return selectNum;
  }

  public void setSelectNum(int selectNum)
  {
    this.selectNum = selectNum;
  }

  public Collection<?> getCollection()
  {
    return collection;
  }

  public void setCollection(Collection<?> collection)
  {
    this.collection = collection;
  }
} 
