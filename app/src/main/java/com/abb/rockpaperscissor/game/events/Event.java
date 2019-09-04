package com.abb.rockpaperscissor.game.events;

import com.abb.rockpaperscissor.game.model.Player;

/**
 * Created by Abarajithan
 */
public class Event {

    public static class Ready {
    }

    public static class MakeMove {
    }

    public static class ExitGame {
        private Player winner;

        public ExitGame(Player winner) {
            this.winner = winner;
        }

        public Player getWinner() {
            return winner;
        }
    }

    public static class Score {
        private String player;
        private double score;

        public Score(String player, double score) {
            this.player = player;
            this.score = score;
        }

        public String getPlayer() {
            return player;
        }

        public double getScore() {
            return score;
        }
    }

}
