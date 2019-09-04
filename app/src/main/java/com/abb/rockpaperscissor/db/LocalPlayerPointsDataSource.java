package com.abb.rockpaperscissor.db;

import com.abb.rockpaperscissor.db.dao.PlayerPointDao;
import com.abb.rockpaperscissor.db.entity.PlayerPoint;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Abarajithan
 */
public class LocalPlayerPointsDataSource implements PlayerPointDataSource {

    private PlayerPointDao dao;

    public LocalPlayerPointsDataSource(PlayerPointDao dao) {
        this.dao = dao;
    }

    @Override
    public Single<List<Long>> insertOrUpdate(PlayerPoint... playerPoint) {
        return dao.insertOrUpdate(playerPoint);
    }

    @Override
    public Single<List<PlayerPoint>> getPlayerPointsById(long gid) {
        return dao.getPlayerPointsById(gid);
    }
}
