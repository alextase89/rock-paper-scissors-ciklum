package com.ciklum.rockpaperscissor.api;

import com.ciklum.rockpaperscissor.persistence.model.RoundResultEnum;
import com.ciklum.rockpaperscissor.persistence.model.ShapeEnum;

public record PlayRoundDTO(ShapeEnum player1Shape, ShapeEnum player2Shape, RoundResultEnum roundResult) { }
