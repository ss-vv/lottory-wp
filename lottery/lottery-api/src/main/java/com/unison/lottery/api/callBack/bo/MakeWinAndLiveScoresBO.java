package com.unison.lottery.api.callBack.bo;

import com.unison.lottery.api.callBack.model.LiveScores;
import com.unison.lottery.api.callBack.model.WinAndLiveScores;

public interface MakeWinAndLiveScoresBO {

   boolean makeWinAndLiveScoresParams2Groups(LiveScores liveScores);

   boolean makeWinParams2Groups(WinAndLiveScores winAndLiveScores, String schemeId);
   
}
