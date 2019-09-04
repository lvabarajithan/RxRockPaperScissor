package com.abb.rockpaperscissor.db;

import com.abb.rockpaperscissor.db.dao.GameDao;
import com.abb.rockpaperscissor.db.entity.Game;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Abarajithan
 */
public class LocalGameDataSource implements GameDataSource {

    private GameDao gameDao;

    public LocalGameDataSource(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public Single<Long> insertOrUpdate(Game game) {
        return gameDao.insertOrUpdate(game);
    }

    @Override
    public Maybe<List<Game>> getAll() {
        return gameDao.getAll();
    }

    @Override
    public Single<Game> getById(int id) {
        return gameDao.getById(id);
    }
}
