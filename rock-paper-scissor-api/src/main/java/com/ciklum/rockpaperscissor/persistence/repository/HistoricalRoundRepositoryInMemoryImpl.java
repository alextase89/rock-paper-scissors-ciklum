package com.ciklum.rockpaperscissor.persistence.repository;

import com.ciklum.rockpaperscissor.persistence.model.HistoricalRound;
import java.util.HashSet;
import java.util.Set;

public class HistoricalRoundRepositoryInMemoryImpl implements HistoricalRoundRepository {

  private final Set<HistoricalRound> historicalRounds;

  public HistoricalRoundRepositoryInMemoryImpl() {
    this.historicalRounds = new HashSet<>();
  }

  @Override
  public void save(HistoricalRound historicalRound) {
    this.historicalRounds.add(historicalRound);
  }

  @Override
  public Set<HistoricalRound> findAll() {
    return Set.copyOf(this.historicalRounds);
  }
}
