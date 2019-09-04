package com.abb.rockpaperscissor.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abb.rockpaperscissor.db.entity.Game;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Abarajithan
 */
@Dao
public interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertOrUpdate(Game game);

    @Query("SELECT * FROM games ORDER BY id DESC")
    Maybe<List<Game>> getAll();

    @Query("SELECT * FROM games WHERE id=:id")
    Single<Game> getById(int id);

}
