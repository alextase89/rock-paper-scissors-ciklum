package com.ciklum.rockpaperscissor.persistence.model;

public record HistoricalRound(
    String gameId, String roundId, ShapeEnum playerShape1, ShapeEnum playerShape2, RoundResultEnum roundResult) {

  public HistoricalRound(String gameId, PlayRound playRound) {
    this(gameId, playRound.id(), playRound.playerShape1(), playRound.playerShape2(), playRound.roundResult());
  }
}
