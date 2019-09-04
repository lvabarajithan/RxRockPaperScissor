package com.abb.rockpaperscissor.ui.main;

import com.abb.rockpaperscissor.db.GameDataSource;
import com.abb.rockpaperscissor.db.PlayerPointDataSource;
import com.abb.rockpaperscissor.db.entity.Game;
import com.abb.rockpaperscissor.db.entity.PlayerPoint;
import com.abb.rockpaperscissor.game.GameBus;
import com.abb.rockpaperscissor.game.events.Event;
import com.abb.rockpaperscissor.game.model.Player;
import com.abb.rockpaperscissor.game.model.PlayerTurn;
import com.abb.rockpaperscissor.ui.BaseGameViewModel;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Abarajithan
 */
public class MainViewModel extends BaseGameViewModel {

    public MainViewModel(GameDataSource dataSource, PlayerPointDataSource playerPointSource) {
        super(dataSource, playerPointSource);
    }

    public Completable insertGame(String winner, Player player1, Player player2) {
        return getGameSource().insertOrUpdate(new Game(winner))
                .flatMap(gid -> {
                    PlayerPoint pp1 = new PlayerPoint(gid, player1);
                    PlayerPoint pp2 = new PlayerPoint(gid, player2);
                    return getPlayerPointSource().insertOrUpdate(pp1, pp2);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).ignoreElement();
    }

    public Observable<PlayerTurn> getPlayerTurns() {
        return GameBus.listen(PlayerTurn.class).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Event.Score> getPlayerScores() {
        return GameBus.listen(Event.Score.class).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Event.ExitGame> getExitEvent() {
        return GameBus.listen(Event.ExitGame.class).observeOn(AndroidSchedulers.mainThread());
    }
}
