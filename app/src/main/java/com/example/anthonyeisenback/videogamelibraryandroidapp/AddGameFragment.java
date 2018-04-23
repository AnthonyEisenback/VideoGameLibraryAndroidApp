package com.example.anthonyeisenback.videogamelibraryandroidapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddGameFragment extends android.support.v4.app.Fragment {

    @BindView(R.id.genre_edittext)
    protected EditText genre;

    @BindView(R.id.add_name_to_game_editText)
    protected EditText gameName;
    private VideoGameDatabase videoGameDatabase;
    private ActivityCallback activityCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_game_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        videoGameDatabase = ((VideoGameApplication) getActivity().getApplicationContext()).getDatabase();
    }

    public static AddGameFragment newInstance() {

        Bundle args = new Bundle();

        AddGameFragment fragment = new AddGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.add_game_to_list_floating_button)
    protected void addButtonClicked() {

        if (genre.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Please fill out all the Fields", Toast.LENGTH_LONG).show();
        }else {

            addGameToDataBase(genre.getText().toString(), gameName.getText().toString(),"");
        }
    }
    private void addGameToDataBase(final String gameName, final String genre, final String description) {
        VideoGameCreator videoGame = new VideoGameCreator(gameName, genre, description, false, new Date());
        videoGameDatabase.videoGameDAO().addVideoGame(videoGame);
        activityCallback.addClicked();

    }

    public void attachParent(ActivityCallback activityCallback) {
        this.activityCallback = activityCallback;
    }

    public interface ActivityCallback {
        void addClicked();

    }
}

