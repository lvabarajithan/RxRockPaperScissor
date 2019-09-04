package com.abb.rockpaperscissor;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.abb.rockpaperscissor.db.GameDataSource;
import com.abb.rockpaperscissor.db.PlayerPointDataSource;
import com.abb.rockpaperscissor.ui.games.GamesViewModel;
import com.abb.rockpaperscissor.ui.main.MainViewModel;

/**
 * Created by Abarajithan
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final GameDataSource gameSource;
    private final PlayerPointDataSource playerPointSource;

    public ViewModelFactory(GameDataSource gameSource, PlayerPointDataSource playerPointSource) {
        this.gameSource = gameSource;
        this.playerPointSource = playerPointSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(gameSource, playerPointSource);
        } else if (modelClass.isAssignableFrom(GamesViewModel.class)) {
            return (T) new GamesViewModel(gameSource, playerPointSource);
        }
        throw new IllegalArgumentException("Unsupported ViewModel class");
    }

}
