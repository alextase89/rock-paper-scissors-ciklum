package com.ciklum.rockpaperscissor;

import static com.ciklum.rockpaperscissor.persistence.model.RoundResultEnum.DRAW;
import static com.ciklum.rockpaperscissor.persistence.model.RoundResultEnum.PLAYER1_WINS;
import static com.ciklum.rockpaperscissor.persistence.model.RoundResultEnum.PLAYER2_WINS;
import static com.ciklum.rockpaperscissor.persistence.model.ShapeEnum.PAPER;
import static com.ciklum.rockpaperscissor.persistence.model.ShapeEnum.ROCK;
import static com.ciklum.rockpaperscissor.persistence.model.ShapeEnum.SCISSOR;

import com.ciklum.rockpaperscissor.persistence.model.Game;
import com.ciklum.rockpaperscissor.persistence.model.HistoricalRound;
import com.ciklum.rockpaperscissor.persistence.model.PlayRound;
import com.ciklum.rockpaperscissor.persistence.model.ShapeEnum;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class TestUtils {

  private static final Map<ShapeEnum, ShapeEnum> SHAPE_BEATS_MAP;
  private static final Random RANDOM_GENERATOR = new Random();

  static {
    SHAPE_BEATS_MAP = new EnumMap<>(ShapeEnum.class);
    SHAPE_BEATS_MAP.put(PAPER, ROCK);
    SHAPE_BEATS_MAP.put(ROCK, SCISSOR);
    SHAPE_BEATS_MAP.put(SCISSOR, PAPER);
  }

  private TestUtils() {}

  public static String randomId() {
    return UUID.randomUUID().toString();
  }

  public static Game buildGame() {
    return new Game(randomId());
  }

  public static PlayRound buildRoundPlayer1Wins() {
    var player1Shape = randomShapeUtil();
    return new PlayRound(player1Shape, SHAPE_BEATS_MAP.get(player1Shape), PLAYER1_WINS);
  }

  public static PlayRound buildRoundPlayer2Wins() {
    var player2Shape = randomShapeUtil();
    return new PlayRound(SHAPE_BEATS_MAP.get(player2Shape), player2Shape, PLAYER2_WINS);
  }

  public static PlayRound buildRoundDraw() {
    var playerShape = randomShapeUtil();
    return new PlayRound(playerShape, playerShape, DRAW);
  }

  public static Set<HistoricalRound> buildHistoricalRounds() {
    var historicalRounds = new HashSet<HistoricalRound>();
    int randomSize = RANDOM_GENERATOR.nextInt(10, 20);
    for (int i = 0; i < randomSize; i++) {
      int randomResult = RANDOM_GENERATOR.nextInt(3);

      if(randomResult == 1) {
        historicalRounds.add(new HistoricalRound(randomId(), buildRoundPlayer1Wins()));
      } else if (randomResult == 2) {
        historicalRounds.add(new HistoricalRound(randomId(), buildRoundPlayer2Wins()));
      } else {
        historicalRounds.add(new HistoricalRound(randomId(), buildRoundDraw()));
      }
    }
    return historicalRounds;
  }


  private static ShapeEnum randomShapeUtil(){
    int shapesLength = ShapeEnum.values().length;
    return ShapeEnum.values()[RANDOM_GENERATOR.nextInt(shapesLength)];
  }
}
