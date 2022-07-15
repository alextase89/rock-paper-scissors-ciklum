package com.ciklum.rockpaperscissor.controller;

import com.ciklum.rockpaperscissor.api.GameResponseDTO;
import com.ciklum.rockpaperscissor.api.GameStatisticsResponseDTO;
import com.ciklum.rockpaperscissor.api.RoundRequestDTO;
import com.ciklum.rockpaperscissor.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/games")
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public GameResponseDTO createGame() {
        return this.gameService.newGame();
    }

    @GetMapping("/statistics")
    public GameStatisticsResponseDTO gameStatistics() {
        return this.gameService.gameStatistics();
    }

    @GetMapping("/{gameId}")
    public GameResponseDTO getGame(
        @PathVariable String gameId) {

        return this.gameService.getGame(gameId);
    }

    @PutMapping("/{gameId}/reset")
    public GameResponseDTO resetGame(
        @PathVariable String gameId) {

        return this.gameService.resetGame(gameId);
    }

    @PostMapping("/{gameId}/rounds")
    public GameResponseDTO newRound(
        @PathVariable String gameId,
        @RequestBody RoundRequestDTO roundRequestDTO) {

        return this.gameService.newRound(gameId, roundRequestDTO);
    }
}
