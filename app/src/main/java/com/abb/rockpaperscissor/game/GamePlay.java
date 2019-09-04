package com.abb.rockpaperscissor.game;

import com.abb.rockpaperscissor.game.events.Event;
import com.abb.rockpaperscissor.game.model.Player;
import com.abb.rockpaperscissor.game.model.Referee;

/**
 * Created by Abarajithan
 */
public class GamePlay {

    private Player player1;
    private Player player2;

    private Referee referee;

    public GamePlay(String playerName1, String playerName2) {
        this.player1 = new Player(playerName1);
        this.player2 = new Player(playerName2);
        this.referee = new Referee(playerName1, playerName2);
        this.player1.start();
        this.player2.start();
        this.referee.start();
    }

    public void nextMove() {
        GameBus.publish(new Event.MakeMove());
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
