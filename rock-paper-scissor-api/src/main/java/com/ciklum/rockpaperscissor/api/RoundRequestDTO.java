package com.ciklum.rockpaperscissor.api;

import com.ciklum.rockpaperscissor.persistence.model.ShapeEnum;

public record RoundRequestDTO(ShapeEnum player1Shape) { }
