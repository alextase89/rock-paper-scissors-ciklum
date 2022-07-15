package com.ciklum.rockpaperscissor.service;

import com.ciklum.rockpaperscissor.exception.GameNotFoundException;
import com.ciklum.rockpaperscissor.api.GameResponseDTO;
import com.ciklum.rockpaperscissor.api.GameStatisticsResponseDTO;
import com.ciklum.rockpaperscissor.api.PlayRoundDTO;
import com.ciklum.rockpaperscissor.api.RoundRequestDTO;
import com.ciklum.rockpaperscissor.persistence.model.Game;
import com.ciklum.rockpaperscissor.persistence.model.HistoricalRound;
import com.ciklum.rockpaperscissor.persistence.model.PlayRound;
import com.ciklum.rockpaperscissor.persistence.model.RoundResultEnum;
import com.ciklum.rockpaperscissor.persistence.model.ShapeEnum;
import com.ciklum.rockpaperscissor.persistence.repository.GameRepository;
import com.ciklum.rockpaperscissor.persistence.repository.HistoricalRoundRepository;
import com.ciklum.rockpaperscissor.utils.RoundResultCounter;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final HistoricalRoundRepository historicalRoundRepository;
    private final ShapeService shapeService;

    @Override
    public GameResponseDTO newGame() {
        var newGame = new Game(UUID.randomUUID().toString());
        log.info("#GameServiceImpl# Creating new game {}", newGame);
        this.gameRepository.save(newGame);
        return this.buildGameResponse(newGame);
    }

    @Override
    public GameResponseDTO getGame(String gameId) {
        log.info("#GameServiceImpl# Getting game with id {}", gameId);
        return this.buildGameResponse(this.findById(gameId));
    }

    @Override
    public GameResponseDTO resetGame(String gameId) {
        Game game = this.findById(gameId);
        game.reset();
        return this.buildGameResponse(game);
    }

    @Override
    public GameResponseDTO newRound(String gameId, RoundRequestDTO roundRequest) {
        var game = this.findById(gameId);

        var player1Shape = roundRequest.player1Shape();
        var player2Shape = this.shapeService.getRandomShape();
        var roundResult = this.getRoundResult(player1Shape, player2Shape);

        var newRound = new PlayRound(player1Shape, player2Shape, roundResult);
        this.saveHistoricalRound(game.getId(), newRound);

        game.addRound(newRound);
        this.gameRepository.save(game);
        return this.buildGameResponse(game);
    }

    @Override
    public GameStatisticsResponseDTO gameStatistics() {
        Set<HistoricalRound> allRounds = this.historicalRoundRepository.findAll();

        var counter = allRounds.stream()
            .collect(RoundResultCounter::new, RoundResultCounter::accept, RoundResultCounter::combine);

        return new GameStatisticsResponseDTO((long) allRounds.size(), counter.getPlayer1Wins(),
            counter.getPlayer2Wins(), counter.getDraws());
    }

    private Game findById(String gameId) {
        return this.gameRepository.findById(gameId)
            .orElseThrow(GameNotFoundException::new);
    }

    private void saveHistoricalRound(String gameId, PlayRound playRound) {
        var historicalRound = new HistoricalRound(gameId, playRound);
        this.historicalRoundRepository.save(historicalRound);
    }

    private RoundResultEnum getRoundResult(ShapeEnum player1Shape, ShapeEnum player2Shape) {
        if(player1Shape == player2Shape) {
            return RoundResultEnum.DRAW;
        } else if(this.shapeService.shapeBeatsTo(player1Shape, player2Shape)) {
            return RoundResultEnum.PLAYER1_WINS;
        } else {
            return RoundResultEnum.PLAYER2_WINS;
        }
    }

    private GameResponseDTO buildGameResponse(Game game) {
        var rounds = game.getRounds().stream()
            .map(round ->
                new PlayRoundDTO(
                    round.playerShape1(),
                    round.playerShape2(),
                    round.roundResult())
            )
            .toList();
        return new GameResponseDTO(game.getId(), rounds);
    }
}
