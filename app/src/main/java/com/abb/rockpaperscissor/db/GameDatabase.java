package com.abb.rockpaperscissor.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.abb.rockpaperscissor.db.dao.GameDao;
import com.abb.rockpaperscissor.db.dao.PlayerPointDao;
import com.abb.rockpaperscissor.db.entity.Game;
import com.abb.rockpaperscissor.db.entity.PlayerPoint;

/**
 * Created by Abarajithan
 */
@Database(entities = {Game.class, PlayerPoint.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class GameDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "game_db";

    private static GameDatabase INSTANCE = null;

    public static GameDatabase get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, GameDatabase.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    public abstract GameDao gameDao();

    public abstract PlayerPointDao playerPointDao();

}
