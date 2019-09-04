package com.abb.rockpaperscissor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.abb.rockpaperscissor.R;
import com.abb.rockpaperscissor.db.entity.Game;
import com.abb.rockpaperscissor.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abarajithan
 */
public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameHolder> {

    private List<Game> gameList;
    private Context context;

    private GameItemClickListener listener;

    public GamesAdapter(Context context) {
        this.context = context;
        this.listener = (GameItemClickListener) context;
        this.gameList = new ArrayList<>();
    }

    public void setData(List<Game> gameList) {
        this.gameList = gameList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_single_game, parent, false);
        return new GameHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        Game game = gameList.get(position);
        holder.gameId.setText(context.getString(R.string.item_game_id, game.getId()));
        holder.summary.setText(context.getString(R.string.item_game_summary, game.getWinner()));
        holder.playedOn.setText(context.getString(R.string.item_game_played_on, DateUtil.format(game.getCreatedAt())));
    }

    @Override
    public int getItemCount() {
        return (gameList == null) ? 0 : gameList.size();
    }

    class GameHolder extends RecyclerView.ViewHolder {
        AppCompatTextView gameId;
        AppCompatTextView summary;
        AppCompatTextView playedOn;

        GameHolder(@NonNull View itemView, GameItemClickListener listener) {
            super(itemView);
            gameId = itemView.findViewById(R.id.item_game_id);
            summary = itemView.findViewById(R.id.item_game_summary);
            playedOn = itemView.findViewById(R.id.item_game_played_on);
            itemView.findViewById(R.id.item_root).setOnClickListener(v -> {
                listener.onGameClick(gameList.get(getAdapterPosition()).getId());
            });
        }
    }
}
