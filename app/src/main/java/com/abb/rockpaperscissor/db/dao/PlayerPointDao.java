package com.abb.rockpaperscissor.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abb.rockpaperscissor.db.entity.PlayerPoint;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Abarajithan
 */
@Dao
public interface PlayerPointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertOrUpdate(PlayerPoint... playerPoint);

    @Query("SELECT * FROM player_points WHERE game_id=:gid")
    Single<List<PlayerPoint>> getPlayerPointsById(long gid);

}
