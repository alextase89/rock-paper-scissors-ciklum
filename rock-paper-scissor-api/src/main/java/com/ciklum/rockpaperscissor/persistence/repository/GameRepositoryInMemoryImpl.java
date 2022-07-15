package com.ciklum.rockpaperscissor.persistence.repository;

import com.ciklum.rockpaperscissor.persistence.model.Game;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class GameRepositoryInMemoryImpl implements GameRepository {

    private final Map<String, Game> gameMap;

    public GameRepositoryInMemoryImpl() {
        this.gameMap = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Game game) {
        this.gameMap.put(game.getId(), game);
    }

    @Override
    public Optional<Game> findById(String gameId) {
        return Optional.ofNullable(this.gameMap.get(gameId));
    }

    @Override
    public List<Game> findAll() {
        return List.copyOf(this.gameMap.values());
    }
}
