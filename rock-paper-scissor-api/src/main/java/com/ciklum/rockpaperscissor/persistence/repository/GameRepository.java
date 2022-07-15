package com.ciklum.rockpaperscissor.persistence.repository;

import com.ciklum.rockpaperscissor.persistence.model.Game;
import java.util.List;
import java.util.Optional;

public interface GameRepository {

    void save(Game game);

    Optional<Game> findById(String gameId);

    List<Game> findAll();
}
