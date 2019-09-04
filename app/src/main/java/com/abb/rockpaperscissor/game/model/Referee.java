package com.abb.rockpaperscissor.game.model;

import com.abb.rockpaperscissor.game.GameBus;
import com.abb.rockpaperscissor.game.events.Event;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Abarajithan
 */
public class Referee extends Thread {

    private String player1;
    private String player2;

    private volatile PlayerMove pm1, pm2;

    public Referee(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        Disposable makeMoveEvent = GameBus.listen(Event.MakeMove.class).subscribe(makeMove -> {
            this.pm1 = null;
            this.pm2 = null;
            GameBus.publish(new Event.Ready());
        });
        Disposable playerTurnEvent = Observable.zip(playerMoveOf(player1), playerMoveOf(player2), PlayerTurn::new)
                .subscribe(playerTurn -> {
                    if (pm1 == null && pm2 == null) {
                        pm1 = playerTurn.getPlayerMove1();
                        pm2 = playerTurn.getPlayerMove2();
                        GameBus.publish(playerTurn);
                        decideWinner(pm1, pm2);
                    }
                });
        while (!isInterrupted()) ;
        makeMoveEvent.dispose();
        playerTurnEvent.dispose();
    }

    private void decideWinner(PlayerMove pm1, PlayerMove pm2) {
        Player p1 = pm1.getPlayer();
        Player p2 = pm2.getPlayer();
        int move1 = pm1.getMove();
        int move2 = pm2.getMove();

        p1.incrementType(move1);
        p2.incrementType(move2);

        if (move1 == move2) {
            pm1.getPlayer().addScore(0.5);
            GameBus.publish(new Event.Score(p1.getPlayerName(), p1.getScore()));
            pm2.getPlayer().addScore(0.5);
            GameBus.publish(new Event.Score(p2.getPlayerName(), p2.getScore()));
        } else if (Move.beats(move1) == move2) {
            pm1.getPlayer().addScore(1);
            GameBus.publish(new Event.Score(p1.getPlayerName(), p1.getScore()));
        } else {
            pm2.getPlayer().addScore(1);
            GameBus.publish(new Event.Score(p2.getPlayerName(), p2.getScore()));
        }
        checkGameFinished(p1, p2);
    }

    private void checkGameFinished(Player p1, Player p2) {
        if (p1.getScore() == 10 && p2.getScore() == 10) {
            decideWinner(new PlayerMove(p1, Move.random()), new PlayerMove(p2, Move.random()));
        } else if (p1.getScore() >= 10) {
            publishWinner(p1);
        } else if (p2.getScore() >= 10) {
            publishWinner(p2);
        }
    }

    private void publishWinner(Player winner) {
        GameBus.publish(new Event.ExitGame(winner));
        interrupt();
    }

    private Observable<PlayerMove> playerMoveOf(String player) {
        return GameBus.listen(PlayerMove.class)
                .filter(playerMove -> playerMove.getPlayer().getPlayerName().equals(player));
    }

}
