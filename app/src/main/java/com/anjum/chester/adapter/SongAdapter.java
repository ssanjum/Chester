package com.anjum.chester.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anjum.chester.R;
import com.anjum.chester.model.SongInfoModel;

import java.util.List;

/**
 * Created by ANJUM on 3/7/2018.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    private List<SongInfoModel> songInfoModel;
    private Context mContext;
    private onItemClickListner onItemClickListner;

    public interface onItemClickListner {

        void onRowClick(int pos, SongInfoModel infoModel, View button);

    }

    public void setOnItemClickListner(SongAdapter.onItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }

    public SongAdapter(List<SongInfoModel> songInfoModel, Context mContext) {
        this.songInfoModel = songInfoModel;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SongAdapter.SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.song_item_layout, parent, false);
        return new SongHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongAdapter.SongHolder holder, final int position) {
        final SongInfoModel infoModel = songInfoModel.get(position);
        holder.tvSongName.setText(infoModel.getSongName());
        holder.tvartistName.setText(infoModel.getArtistName());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListner != null) {
                    onItemClickListner.onRowClick(position, infoModel, holder.linearLayout);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return songInfoModel.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        private TextView tvSongName;
        private TextView tvartistName;
        private LinearLayout linearLayout;

        public SongHolder(View itemView) {
            super(itemView);
            tvSongName = itemView.findViewById(R.id.tv_item_song_name);
            tvartistName = itemView.findViewById(R.id.tv_item_artist_name);
            linearLayout=itemView.findViewById(R.id.ll);
        }
    }
}
