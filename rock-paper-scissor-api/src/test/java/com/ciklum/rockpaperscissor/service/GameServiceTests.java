package com.ciklum.rockpaperscissor.service;

import static com.ciklum.rockpaperscissor.TestUtils.buildGame;
import static com.ciklum.rockpaperscissor.TestUtils.buildHistoricalRounds;
import static com.ciklum.rockpaperscissor.TestUtils.buildRoundDraw;
import static com.ciklum.rockpaperscissor.TestUtils.buildRoundPlayer1Wins;
import static com.ciklum.rockpaperscissor.TestUtils.buildRoundPlayer2Wins;
import static com.ciklum.rockpaperscissor.TestUtils.randomId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ciklum.rockpaperscissor.exception.GameNotFoundException;
import com.ciklum.rockpaperscissor.api.GameResponseDTO;
import com.ciklum.rockpaperscissor.api.PlayRoundDTO;
import com.ciklum.rockpaperscissor.api.RoundRequestDTO;
import com.ciklum.rockpaperscissor.persistence.model.RoundResultEnum;
import com.ciklum.rockpaperscissor.persistence.model.ShapeEnum;
import com.ciklum.rockpaperscissor.persistence.repository.GameRepository;
import com.ciklum.rockpaperscissor.persistence.repository.HistoricalRoundRepository;
import com.ciklum.rockpaperscissor.utils.RoundResultCounter;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameServiceTests {

  @Mock
  private ShapeService shapeService;
  @Mock
  private GameRepository gameRepository;
  @Mock
  private HistoricalRoundRepository historicalRoundRepository;
  private GameService gameService;

  @BeforeEach
  public void setup() {
    this.gameService = new GameServiceImpl(this.gameRepository, this.historicalRoundRepository, this.shapeService);
  }

  @Test
  void whenNewGame_thenReturnGameWithIdAndEmptyRoundsList() {
    //when
    var gameResponse  = this.gameService.newGame();

    //then
    assertGameResponse(gameResponse);
    assertTrue(gameResponse.rounds().isEmpty());

    verify(this.gameRepository, times(1)).save(any());
  }

  @Test
  void givenNewGame_whenGetGame_thenReturnGameWithSameIdAndNonNullRoundList() {
    //given

    var mockedGame = buildGame();
    String gameId = mockedGame.getId();
    mockedGame.addRound(buildRoundPlayer1Wins());
    mockedGame.addRound(buildRoundDraw());
    when(this.gameRepository.findById(gameId)).thenReturn(Optional.of(mockedGame));

    //when
    var gameResponse = this.gameService.getGame(gameId);

    //then
    assertGameResponse(gameResponse);
    assertEquals(gameId, gameResponse.id());
    assertEquals(2, gameResponse.rounds().size());

    verify(this.gameRepository, times(1)).findById(gameId);
  }

  @Test
  void givenNonPresentGameId_whenGetGame_thenThrowGameNotFoundException() {
    //given
    String gameId = randomId();
    when(this.gameRepository.findById(gameId)).thenReturn(Optional.empty());

    //when
    assertThrows(GameNotFoundException.class, () -> this.gameService.getGame(gameId));
  }

  @Test
  void giveNewGame_whenNewRound_thenRoundResultIsPlayer1Wins() {
    //given
    var mockedGame = buildGame();
    String gameId = mockedGame.getId();
    var newRoundRequest = new RoundRequestDTO(ShapeEnum.ROCK);

    when(this.gameRepository.findById(gameId)).thenReturn(Optional.of(mockedGame));
    when(this.shapeService.getRandomShape()).thenReturn(ShapeEnum.SCISSOR);
    when(this.shapeService.shapeBeatsTo(ShapeEnum.ROCK, ShapeEnum.SCISSOR)).thenReturn(true);

    //when
    var gameResponse = this.gameService.newRound(gameId, newRoundRequest);

    //then
    var round = gameResponse.rounds().get(0);
    this.assertRoundResponse(round);
    assertEquals(RoundResultEnum.PLAYER1_WINS, round.roundResult());

    verify(this.historicalRoundRepository, times(1)).save(any());
  }

  @Test
  void giveNewGame_whenNewRound_thenRoundResultIsPlayer2Wins() {
    //given
    var mockedGame = buildGame();
    String gameId = mockedGame.getId();
    var newRoundRequest = new RoundRequestDTO(ShapeEnum.ROCK);

    when(this.gameRepository.findById(gameId)).thenReturn(Optional.of(mockedGame));
    when(this.shapeService.getRandomShape()).thenReturn(ShapeEnum.PAPER);
    when(this.shapeService.shapeBeatsTo(ShapeEnum.ROCK, ShapeEnum.PAPER)).thenReturn(false);

    //when
    var gameResponse = this.gameService.newRound(gameId, newRoundRequest);

    //then
    var round = gameResponse.rounds().get(0);
    this.assertRoundResponse(round);
    assertEquals(RoundResultEnum.PLAYER2_WINS, round.roundResult());

    verify(this.historicalRoundRepository, times(1)).save(any());
  }

  @Test
  void giveNewGame_whenNewRound_thenRoundResultIsDraw() {
    //given
    var mockedGame = buildGame();
    String gameId = mockedGame.getId();
    var newRoundRequest = new RoundRequestDTO(ShapeEnum.ROCK);

    when(this.gameRepository.findById(gameId)).thenReturn(Optional.of(mockedGame));
    when(this.shapeService.getRandomShape()).thenReturn(ShapeEnum.ROCK);

    //when
    var gameResponse = this.gameService.newRound(gameId, newRoundRequest);

    //then
    var round = gameResponse.rounds().get(0);
    this.assertRoundResponse(round);
    assertEquals(RoundResultEnum.DRAW, round.roundResult());

    verify(this.historicalRoundRepository, times(1)).save(any());
  }

  @Test
  void givenNonPresentGameId_whenNewRound_thenThrowGameNotFoundException() {
    //given
    String gameId = randomId();
    var roundRequest = new RoundRequestDTO(ShapeEnum.ROCK);
    when(this.gameRepository.findById(gameId)).thenReturn(Optional.empty());

    //when
    assertThrows(GameNotFoundException.class, () -> this.gameService.newRound(gameId, roundRequest));

    verify(this.gameRepository, times(1)).findById(gameId);
  }

  @Test
  void givenGameWithRounds_whenResetGame_thenReturnGameWithEmptyRoundList() {
    //given
    var mockedGame = buildGame();
    String gameId = mockedGame.getId();
    mockedGame.addRound(buildRoundPlayer1Wins());
    mockedGame.addRound(buildRoundPlayer2Wins());
    mockedGame.addRound(buildRoundDraw());
    when(this.gameRepository.findById(gameId)).thenReturn(Optional.of(mockedGame));

    //when
    var gameResponse = this.gameService.resetGame(gameId);

    //then
    assertTrue(gameResponse.rounds().isEmpty());
  }

  @Test
  void givenHistoricalRounds_whenGameStatistics_thenReturnGameStatistics() {
    //given
    var historicalRounds = buildHistoricalRounds();
    var counter = historicalRounds.stream()
        .collect(RoundResultCounter::new, RoundResultCounter::accept, RoundResultCounter::combine);
    when(this.historicalRoundRepository.findAll()).thenReturn(historicalRounds);

    //when
    var statisticsResponse = this.gameService.gameStatistics();

    //then
    assertNotNull(statisticsResponse);
    long totalRoundsCalculated = statisticsResponse.player1Wins() + statisticsResponse.player2Wins() + statisticsResponse.draws();
    assertEquals(historicalRounds.size(), totalRoundsCalculated);
    assertEquals(historicalRounds.size(), statisticsResponse.totalRounds());
    assertEquals(counter.getPlayer1Wins(), statisticsResponse.player1Wins());
    assertEquals(counter.getPlayer2Wins(), statisticsResponse.player2Wins());
    assertEquals(counter.getDraws(), statisticsResponse.draws());
  }

  private void assertGameResponse(GameResponseDTO gameResponse) {
    assertNotNull(gameResponse);
    assertNotNull(gameResponse.id());
    assertNotNull(gameResponse.rounds());
  }

  private void assertRoundResponse(PlayRoundDTO playRoundDTO) {
    assertNotNull(playRoundDTO);
    assertNotNull(playRoundDTO.player1Shape());
    assertNotNull(playRoundDTO.player2Shape());
    assertNotNull(playRoundDTO.roundResult());
  }
}
