package com.abb.rockpaperscissor.ui;

import androidx.lifecycle.ViewModel;

import com.abb.rockpaperscissor.db.GameDataSource;
import com.abb.rockpaperscissor.db.PlayerPointDataSource;

/**
 * Created by Abarajithan
 */
public class BaseGameViewModel extends ViewModel {

    private final GameDataSource gameSource;
    private final PlayerPointDataSource playerPointSource;

    public BaseGameViewModel(GameDataSource gameSource, PlayerPointDataSource playerPointSource) {
        this.gameSource = gameSource;
        this.playerPointSource = playerPointSource;
    }

    public GameDataSource getGameSource() {
        return gameSource;
    }

    public PlayerPointDataSource getPlayerPointSource() {
        return playerPointSource;
    }
}
