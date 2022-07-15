package com.ciklum.rockpaperscissor.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ciklum.rockpaperscissor.persistence.model.ShapeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShapeServiceTests {

  private ShapeService shapeService;

  @BeforeEach
  public void setup() {
    this.shapeService = new ShapeServiceImpl();
  }

  @Test
  void whenGetRandomShape_thenReturnNonNullShapeType() {
    var randomShape = this.shapeService.getRandomShape();

    assertNotNull(randomShape);
  }

  @Test
  void givenTwoDifferentShapes_whenShapeBeatsTo_thenReturnTrue() {
    assertTrue(this.shapeService.shapeBeatsTo(ShapeEnum.ROCK, ShapeEnum.SCISSOR));
    assertTrue(this.shapeService.shapeBeatsTo(ShapeEnum.SCISSOR, ShapeEnum.PAPER));
    assertTrue(this.shapeService.shapeBeatsTo(ShapeEnum.PAPER, ShapeEnum.ROCK));
  }

  @Test
  void givenTwoDifferentShapes_whenShapeBeatsTo_thenReturnFalse() {
    assertFalse(this.shapeService.shapeBeatsTo(ShapeEnum.SCISSOR, ShapeEnum.ROCK));
    assertFalse(this.shapeService.shapeBeatsTo(ShapeEnum.PAPER, ShapeEnum.SCISSOR));
    assertFalse(this.shapeService.shapeBeatsTo(ShapeEnum.ROCK, ShapeEnum.PAPER));
  }

  @Test
  void givenTwoEqualsShapes_whenShapeBeatsTo_thenReturnFalse() {
    assertFalse(this.shapeService.shapeBeatsTo(ShapeEnum.SCISSOR, ShapeEnum.SCISSOR));
    assertFalse(this.shapeService.shapeBeatsTo(ShapeEnum.PAPER, ShapeEnum.PAPER));
    assertFalse(this.shapeService.shapeBeatsTo(ShapeEnum.ROCK, ShapeEnum.ROCK));
  }
}
