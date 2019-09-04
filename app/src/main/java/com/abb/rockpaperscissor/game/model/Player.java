package com.abb.rockpaperscissor.game.model;

import com.abb.rockpaperscissor.game.GameBus;
import com.abb.rockpaperscissor.game.events.Event;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

import static com.abb.rockpaperscissor.game.model.Move.Type.PAPER;
import static com.abb.rockpaperscissor.game.model.Move.Type.ROCK;
import static com.abb.rockpaperscissor.game.model.Move.Type.SCISSOR;

/**
 * Created by Abarajithan
 */
public class Player extends Thread {

    private String playerName;
    private double score;

    private int rocks = 0, papers = 0, scissors = 0;

    public Player(String playerName) {
        this.playerName = playerName;
        this.score = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void run() {
        Disposable readyEvent = GameBus.listen(Event.Ready.class)
                .subscribe(ready -> {
                    int myMove = Move.random();
                    GameBus.publish(new PlayerMove(this, myMove));
                    GameBus.listen(PlayerMove.class)
                            .filter(move -> !move.getPlayer().getPlayerName().equals(playerName))
                            .delay(new Random().nextInt((500 - 100) + 100) + 1, TimeUnit.MILLISECONDS)
                            .take(1)
                            .subscribe(playerMove -> {
                                if (playerMove != null && Move.beats(playerMove.getMove()) != myMove) {
                                    // Cheating
                                    GameBus.publish(new PlayerMove(this, Move.beats(playerMove.getMove())));
                                }
                            });
                });
        Disposable exitEvent = GameBus.listen(Event.ExitGame.class).subscribe(exitGame -> {
            interrupt();
        });
        while (!isInterrupted()) ;
        readyEvent.dispose();
        exitEvent.dispose();
    }

    public void incrementType(@Move.Type int type) {
        switch (type) {
            case ROCK:
                rocks++;
                break;
            case PAPER:
                papers++;
                break;
            case SCISSOR:
                scissors++;
                break;
        }
    }

    public int getRocks() {
        return rocks;
    }

    public int getPapers() {
        return papers;
    }

    public int getScissors() {
        return scissors;
    }

    public void addScore(double point) {
        this.score += point;
    }

    public double getScore() {
        return score;
    }

}
