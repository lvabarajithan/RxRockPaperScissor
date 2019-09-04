package com.abb.rockpaperscissor.db;

import com.abb.rockpaperscissor.db.entity.PlayerPoint;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Abarajithan
 */
public interface PlayerPointDataSource {
    Single<List<Long>> insertOrUpdate(PlayerPoint... playerPoint);

    Single<List<PlayerPoint>> getPlayerPointsById(long gid);
}
