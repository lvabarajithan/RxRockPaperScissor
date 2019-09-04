package com.abb.rockpaperscissor.ui.games;

import com.abb.rockpaperscissor.db.GameDataSource;
import com.abb.rockpaperscissor.db.PlayerPointDataSource;
import com.abb.rockpaperscissor.db.entity.Game;
import com.abb.rockpaperscissor.db.entity.PlayerPoint;
import com.abb.rockpaperscissor.ui.BaseGameViewModel;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Abarajithan
 */
public class GamesViewModel extends BaseGameViewModel {

    public GamesViewModel(GameDataSource dataSource, PlayerPointDataSource playerPointSource) {
        super(dataSource, playerPointSource);
    }

    public Maybe<List<Game>> getGames() {
        return getGameSource().getAll();
    }

    public Single<List<PlayerPoint>> getPlayerPoints(long gid) {
        return getPlayerPointSource().getPlayerPointsById(gid);
    }
}
