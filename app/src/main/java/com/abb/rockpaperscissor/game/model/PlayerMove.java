package com.abb.rockpaperscissor.game.model;

/**
 * Created by Abarajithan
 */
public class PlayerMove {
    private Player player;
    private int move;

    public PlayerMove(Player player, @Move.Type int move) {
        this.player = player;
        this.move = move;
    }

    public Player getPlayer() {
        return player;
    }

    public int getMove() {
        return move;
    }

}
