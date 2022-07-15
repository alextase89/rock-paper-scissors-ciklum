package com.ciklum.rockpaperscissor.persistence.model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final String id;
    private final List<PlayRound> rounds;

    public Game(String id) {
        this.id = id;
        this.rounds = new ArrayList<>();
    }

    public Game(String id, List<PlayRound> rounds) {
        this.id = id;
        this.rounds = rounds;
    }

    public void addRound(PlayRound round) {
        this.rounds.add(round);
    }

    public String getId() {
        return id;
    }

    public List<PlayRound> getRounds() {
        return List.copyOf(this.rounds);
    }

    public void reset() {
        this.rounds.clear();
    }
}
