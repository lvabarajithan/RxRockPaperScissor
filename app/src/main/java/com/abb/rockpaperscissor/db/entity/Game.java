package com.abb.rockpaperscissor.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Created by Abarajithan
 */
@Entity(tableName = "games")
public class Game {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String winner;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    public Game() {
    }

    @Ignore
    public Game(String winner) {
        this.winner = winner;
        this.createdAt = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
