package com.abb.rockpaperscissor.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;

import com.abb.rockpaperscissor.R;
import com.abb.rockpaperscissor.ViewModelFactory;
import com.abb.rockpaperscissor.db.GameDataSource;
import com.abb.rockpaperscissor.db.GameDatabase;
import com.abb.rockpaperscissor.db.LocalGameDataSource;
import com.abb.rockpaperscissor.db.LocalPlayerPointsDataSource;
import com.abb.rockpaperscissor.db.PlayerPointDataSource;
import com.abb.rockpaperscissor.game.GamePlay;
import com.abb.rockpaperscissor.game.events.Event;
import com.abb.rockpaperscissor.game.model.Move;
import com.abb.rockpaperscissor.game.model.Player;
import com.abb.rockpaperscissor.game.model.PlayerMove;
import com.abb.rockpaperscissor.ui.games.GamesActivity;
import com.abb.rockpaperscissor.util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private static final String PLAYER_C = "PlayerC";
    private static final String PLAYER_D = "PlayerD";
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.main_play_btn)
    AppCompatButton startGameBtn;
    @BindView(R.id.main_view_games_btn)
    AppCompatButton viewAllGames;

    @BindView(R.id.main_game_state)
    AppCompatTextView gameStateText;

    @BindView(R.id.main_game_board_move_playerc)
    AppCompatImageView playerCMoveImg;
    @BindView(R.id.main_game_board_move_playerd)
    AppCompatImageView playerDMoveImg;

    @BindView(R.id.main_score_board_player_c_score)
    AppCompatTextView playerCScoreView;
    @BindView(R.id.main_score_board_player_d_score)
    AppCompatTextView playerDScoreView;

    private MainViewModel viewModel;

    private CompositeDisposable disposable;

    private GamePlay gamePlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        disposable = new CompositeDisposable();
        initViewModel();
        initViews();

        startGameBtn.setOnClickListener(v -> {
            if (gamePlay == null) {
                gamePlay = new GamePlay(PLAYER_C, PLAYER_D);
                startGameBtn.setText(R.string.main_next_turn_btn);
                gameStateText.setText(R.string.main_game_ready_state);
            }
            gamePlay.nextMove();
        });

        viewAllGames.setOnClickListener(v -> {
            startActivity(new Intent(this, GamesActivity.class));
        });
    }

    private void initViews() {
        startGameBtn.setText(R.string.main_start_game_btn);
        gameStateText.setText(R.string.main_game_initial_state);
        playerCScoreView.setText(String.valueOf(0));
        playerDScoreView.setText(String.valueOf(0));
        playerCMoveImg.setImageResource(0);
        playerDMoveImg.setImageResource(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        subscribeToTurns();
        subscribeToScores();
        subscribeToExitEvent();
    }

    private void subscribeToExitEvent() {
        Disposable d = viewModel.getExitEvent().subscribe(exitGame -> {
            initViews();
            insertIntoDb(exitGame,
                    gamePlay.getPlayer1(),
                    gamePlay.getPlayer2());
            gamePlay = null;
        });
        disposable.add(d);
    }

    private void insertIntoDb(Event.ExitGame exitGame, Player player1, Player player2) {
        Disposable dbEntry = viewModel.insertGame(exitGame.getWinner().getPlayerName(), player1, player2)
                .subscribe(() -> {
                    showSummaryDialog(exitGame);
                }, throwable -> {
                    Log.e(TAG, "Error inserting items into db", throwable);
                    Toast.makeText(this, "Cannot save this game", Toast.LENGTH_SHORT).show();
                });
        disposable.add(dbEntry);
    }

    private void showSummaryDialog(Event.ExitGame exitGame) {
        new AlertDialog.Builder(this)
                .setMessage(exitGame.getWinner().getPlayerName()
                        + " won the game by scoring "
                        + exitGame.getWinner().getScore())
                .setPositiveButton(R.string.dialog_summary_positive_btn, (dialog, which) -> {
                    startActivity(new Intent(this, GamesActivity.class));
                })
                .setNegativeButton(R.string.dialog_summary_negative_btn, (dialog, which) -> {
                    dialog.dismiss();
                })
                .create().show();
    }

    private void subscribeToScores() {
        Disposable d = viewModel.getPlayerScores().subscribe(scoreEvent -> {
            if (scoreEvent.getPlayer().equals(PLAYER_C)) {
                playerCScoreView.setText(String.valueOf(scoreEvent.getScore()));
            } else {
                playerDScoreView.setText(String.valueOf(scoreEvent.getScore()));
            }
        });
        disposable.add(d);
    }

    private void subscribeToTurns() {
        Disposable d = viewModel.getPlayerTurns().subscribe(playerTurn -> {
            PlayerMove player1Move = playerTurn.getPlayerMove1();
            PlayerMove player2Move = playerTurn.getPlayerMove2();
            playerCMoveImg.setImageResource(ImageUtil.getImageRes(player1Move.getMove()));
            playerDMoveImg.setImageResource(ImageUtil.getImageRes(player2Move.getMove()));
            if (player1Move.getMove() == player2Move.getMove()) {
                gameStateText.setText(R.string.main_game_state_tie);
            } else if (Move.beats(player1Move.getMove()) == player2Move.getMove()) {
                gameStateText.setText(getString(R.string.main_game_state_won,
                        player1Move.getPlayer().getPlayerName()));
            } else {
                gameStateText.setText(getString(R.string.main_game_state_won,
                        player2Move.getPlayer().getPlayerName()));
            }
        });
        disposable.add(d);
    }

    private void initViewModel() {
        GameDatabase database = GameDatabase.get(this.getApplicationContext());
        GameDataSource gameSource = new LocalGameDataSource(database.gameDao());
        PlayerPointDataSource ppSource = new LocalPlayerPointsDataSource(database.playerPointDao());
        viewModel = new ViewModelProvider(this, new ViewModelFactory(gameSource, ppSource))
                .get(MainViewModel.class);
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposable.clear();
    }

}
