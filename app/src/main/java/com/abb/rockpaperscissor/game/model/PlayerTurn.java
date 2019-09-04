package com.abb.rockpaperscissor.game.model;

/**
 * Created by Abarajithan
 */
public class PlayerTurn {

    private PlayerMove playerMove1;
    private PlayerMove playerMove2;

    public PlayerTurn(PlayerMove playerMove1, PlayerMove playerMove2) {
        this.playerMove1 = playerMove1;
        this.playerMove2 = playerMove2;
    }

    public PlayerMove getPlayerMove1() {
        return playerMove1;
    }

    public PlayerMove getPlayerMove2() {
        return playerMove2;
    }
}
