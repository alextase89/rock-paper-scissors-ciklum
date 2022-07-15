package com.ciklum.rockpaperscissor.service;

import com.ciklum.rockpaperscissor.persistence.model.ShapeEnum;

public interface ShapeService {

  ShapeEnum getRandomShape();

  boolean shapeBeatsTo(ShapeEnum shape, ShapeEnum shapeToBeat);
}
