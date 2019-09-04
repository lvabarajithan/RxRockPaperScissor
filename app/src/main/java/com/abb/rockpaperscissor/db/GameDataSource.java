package com.abb.rockpaperscissor.db;

import com.abb.rockpaperscissor.db.entity.Game;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Abarajithan
 */
public interface GameDataSource {
    Single<Long> insertOrUpdate(Game game);

    Maybe<List<Game>> getAll();

    Single<Game> getById(int id);
}
