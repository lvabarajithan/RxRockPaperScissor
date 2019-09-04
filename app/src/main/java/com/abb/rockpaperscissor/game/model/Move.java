package com.abb.rockpaperscissor.game.model;

import androidx.annotation.IntDef;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.abb.rockpaperscissor.game.model.Move.Type.PAPER;
import static com.abb.rockpaperscissor.game.model.Move.Type.ROCK;
import static com.abb.rockpaperscissor.game.model.Move.Type.SCISSOR;

/**
 * Created by Abarajithan
 */
public class Move {

    @IntDef({ROCK, PAPER, SCISSOR})
    public @interface Type {
        int ROCK = 0;
        int PAPER = 1;
        int SCISSOR = 2;
    }

    private static final List<Integer> allMoves = Arrays.asList(ROCK, PAPER, SCISSOR);
    private static final List<Integer> beats = Arrays.asList(SCISSOR, ROCK, PAPER);

    @Type
    public static int random() {
        return allMoves.get(new Random().nextInt(3));
    }

    @Type
    public static int beats(@Type int move1) {
        return beats.get(move1);
    }

}
