package com.example.anthonyeisenback.videogamelibraryandroidapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoGameAdapter extends RecyclerView.Adapter<VideoGameAdapter.ViewHolder> {

    private AdapterCallback adapterCallback;
    private List<VideoGameCreator> videoGameCreatorList;
    private VideoGameCreator videoGameCreator;

    public VideoGameAdapter(List<VideoGameCreator> videoGameCreatorList, AdapterCallback adapterCallback) {
        this.videoGameCreatorList = videoGameCreatorList;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public VideoGameAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_game, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoGameAdapter.ViewHolder holder, int position) {
        holder.bind(videoGameCreatorList.get(position));

        holder.rowLayout.setOnClickListener(holder.onRowClicked(videoGameCreatorList.get(position)));
        holder.rowLayout.setOnLongClickListener(holder.onRowLongClicked(videoGameCreatorList.get(position)));
    }

    @Override
    public int getItemCount() {

        return videoGameCreatorList.size();
    }

    public void updateList(List<VideoGameCreator> list) {
        videoGameCreatorList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title_textview)
        protected TextView gameTitle;
        @BindView(R.id.item_genre)
        protected TextView genreItem;
        @BindView(R.id.item_date)
        protected TextView gameDate;
        @BindView(R.id.item_row_layout)
        protected ConstraintLayout rowLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindGame(VideoGameCreator videoGame) {
            VideoGameApplication videoGameApplication = new VideoGameApplication();
            Resources res = videoGameApplication.getResources();
            String genre = String.format(res.getString(R.string.genre_item_type), videoGame.getGameGenre());
            gameTitle.setText(videoGame.getGameName());
            genreItem.setText(genre);

        }

        public View.OnClickListener onClick(final VideoGameCreator videoGame) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    adapterCallback.rowClicked(videoGame);
                }
            };
        }

        public View.OnLongClickListener onLongClick(final VideoGameCreator videoGame) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    adapterCallback.rowLongClicked(videoGame);
                    return false;
                }
            };
        }

        public void bind(VideoGameCreator videoGameCreator) {
            gameTitle.setText(videoGameCreator.getGameName());
            genreItem.setText(adapterCallback.getContext().getString(R.string.genre_item_type, videoGameCreator.getGameGenre()));

            if (videoGameCreator.isCheckedOut()) {
                //TODO background red,due date red
                gameDate.setVisibility(View.VISIBLE);
                videoGameCreator.setDate(new Date());
                rowLayout.setBackgroundResource(R.color.colorSpecialRed);
                int numberOfDays = 14;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(videoGameCreator.getDate());
                calendar.add(Calendar.DAY_OF_YEAR, numberOfDays);
                Date date = calendar.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                gameDate.setText(adapterCallback.getContext().getString(R.string.due_date, formatter.format(date)));
                //calculate check back in date
            } else {
                //background green due date invisible
                gameDate.setVisibility(View.INVISIBLE);
                rowLayout.setBackgroundResource(R.color.colorSpecialGreen);

            }
        }

        public View.OnClickListener onRowClicked(final VideoGameCreator videoGameCreator) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), videoGameCreator.getGameName(), Toast.LENGTH_SHORT).show();
                }
            };
        }

        public View.OnLongClickListener onRowLongClicked(final VideoGameCreator videoGameCreator) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(itemView.getContext(), videoGameCreator.getGameGenre(), Toast.LENGTH_SHORT).show();
                    return false;
                }
            };
        }
    }

    public interface AdapterCallback {
        Context getContext();
        void rowClicked(VideoGameCreator videoGame);
        void rowLongClicked(VideoGameCreator videoGame);
    }


}
