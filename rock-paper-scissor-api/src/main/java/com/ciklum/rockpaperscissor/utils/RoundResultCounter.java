package com.ciklum.rockpaperscissor.utils;

import com.ciklum.rockpaperscissor.persistence.model.HistoricalRound;
import com.ciklum.rockpaperscissor.persistence.model.RoundResultEnum;
import lombok.Data;

@Data
public class RoundResultCounter {

  private long player1Wins;
  private long player2Wins;
  private long draws;

  public void accept(HistoricalRound historicalRound) {
      if(historicalRound.roundResult() == RoundResultEnum.PLAYER1_WINS) {
        this.player1Wins ++;
      } else if (historicalRound.roundResult() == RoundResultEnum.PLAYER2_WINS) {
        this.player2Wins ++;
      } else {
        this.draws ++;
      }
  }

  public void combine(RoundResultCounter roundResultCounter) {
    this.player1Wins += roundResultCounter.getPlayer1Wins();
    this.player2Wins += roundResultCounter.getPlayer2Wins();
    this.draws += roundResultCounter.getDraws();
  }
}
