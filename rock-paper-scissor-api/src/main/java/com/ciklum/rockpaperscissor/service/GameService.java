package com.ciklum.rockpaperscissor.service;

import com.ciklum.rockpaperscissor.api.GameResponseDTO;
import com.ciklum.rockpaperscissor.api.GameStatisticsResponseDTO;
import com.ciklum.rockpaperscissor.api.RoundRequestDTO;

public interface GameService {

    GameResponseDTO newGame();
    GameResponseDTO getGame(String gameId);
    GameResponseDTO resetGame(String gameId);
    GameResponseDTO newRound(String gameId, RoundRequestDTO roundRequest);
    GameStatisticsResponseDTO gameStatistics();
}
