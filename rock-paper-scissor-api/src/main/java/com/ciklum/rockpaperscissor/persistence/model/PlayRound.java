package com.ciklum.rockpaperscissor.persistence.model;

import java.util.UUID;

public record PlayRound(String id, ShapeEnum playerShape1, ShapeEnum playerShape2, RoundResultEnum roundResult) {

    public PlayRound(ShapeEnum playerShape1, ShapeEnum playerShape2, RoundResultEnum roundResult) {
        this(UUID.randomUUID().toString(), playerShape1, playerShape2, roundResult);
    }
}
