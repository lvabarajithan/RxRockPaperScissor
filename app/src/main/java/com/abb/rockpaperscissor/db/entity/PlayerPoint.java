package com.abb.rockpaperscissor.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.abb.rockpaperscissor.game.model.Player;

/**
 * Created by Abarajithan
 */
@Entity(tableName = "player_points")
public class PlayerPoint {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "game_id")
    private long gameId;

    @ColumnInfo(name = "player_name")
    private String playerName;
    private int rocks;
    private int papers;
    private int scissors;
    private double score;

    public PlayerPoint() {
    }

    @Ignore
    public PlayerPoint(long gameId, Player from) {
        this.gameId = gameId;
        this.playerName = from.getPlayerName();
        this.rocks = from.getRocks();
        this.papers = from.getPapers();
        this.scissors = from.getScissors();
        this.score = from.getScore();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getRocks() {
        return rocks;
    }

    public void setRocks(int rocks) {
        this.rocks = rocks;
    }

    public int getPapers() {
        return papers;
    }

    public void setPapers(int papers) {
        this.papers = papers;
    }

    public int getScissors() {
        return scissors;
    }

    public void setScissors(int scissors) {
        this.scissors = scissors;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
