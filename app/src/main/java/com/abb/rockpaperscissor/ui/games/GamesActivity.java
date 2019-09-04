package com.abb.rockpaperscissor.ui.games;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abb.rockpaperscissor.R;
import com.abb.rockpaperscissor.ViewModelFactory;
import com.abb.rockpaperscissor.adapter.GameItemClickListener;
import com.abb.rockpaperscissor.adapter.GamesAdapter;
import com.abb.rockpaperscissor.db.GameDataSource;
import com.abb.rockpaperscissor.db.GameDatabase;
import com.abb.rockpaperscissor.db.LocalGameDataSource;
import com.abb.rockpaperscissor.db.LocalPlayerPointsDataSource;
import com.abb.rockpaperscissor.db.PlayerPointDataSource;
import com.abb.rockpaperscissor.db.entity.PlayerPoint;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Abarajithan
 */
public class GamesActivity extends AppCompatActivity implements GameItemClickListener {

    private GamesViewModel viewModel;

    private GamesAdapter adapter;

    @BindView(R.id.games_list)
    RecyclerView gamesList;

    private CompositeDisposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);
        ButterKnife.bind(this);

        disposable = new CompositeDisposable();
        initViewModel();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        subscribeToGames();
    }

    private void subscribeToGames() {
        Disposable d = viewModel.getGames()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(games -> {
                    adapter.setData(games);
                });
        disposable.add(d);
    }

    private void initViews() {
        adapter = new GamesAdapter(this);

        gamesList.setLayoutManager(new LinearLayoutManager(this));
        gamesList.setItemAnimator(new DefaultItemAnimator());
        gamesList.setHasFixedSize(true);
        gamesList.setAdapter(adapter);
    }

    private void initViewModel() {
        GameDatabase database = GameDatabase.get(this.getApplicationContext());
        GameDataSource gameSource = new LocalGameDataSource(database.gameDao());
        PlayerPointDataSource pointDataSource = new LocalPlayerPointsDataSource(database.playerPointDao());
        viewModel = new ViewModelProvider(this, new ViewModelFactory(gameSource, pointDataSource))
                .get(GamesViewModel.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.clear();
    }

    @Override
    public void onGameClick(long gameId) {
        Disposable ppGet = viewModel.getPlayerPoints(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::ShowPlayerPointsDialog);
        disposable.add(ppGet);
    }

    private void ShowPlayerPointsDialog(List<PlayerPoint> playerPoints) {
        new AlertDialog.Builder(this)
                .setTitle("Player points")
                .setMessage(getAsMessage(playerPoints))
                .create().show();
    }

    private String getAsMessage(List<PlayerPoint> playerPoints) {
        StringBuilder builder = new StringBuilder();
        for (PlayerPoint pp : playerPoints) {
            builder.append(pp.getPlayerName()).append(" scored ")
                    .append(pp.getRocks()).append(" rocks, ")
                    .append(pp.getPapers()).append(" papers, ")
                    .append(pp.getScissors()).append(" scissors, and total score of ")
                    .append(pp.getScore()).append("\n\n");
        }
        return builder.toString();
    }
}
