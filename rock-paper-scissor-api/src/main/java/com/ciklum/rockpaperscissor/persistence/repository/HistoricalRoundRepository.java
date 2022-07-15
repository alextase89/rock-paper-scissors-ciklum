package com.ciklum.rockpaperscissor.persistence.repository;

import com.ciklum.rockpaperscissor.persistence.model.HistoricalRound;
import java.util.Set;

public interface HistoricalRoundRepository {

  void save(HistoricalRound historicalRound);

  Set<HistoricalRound> findAll();
}
