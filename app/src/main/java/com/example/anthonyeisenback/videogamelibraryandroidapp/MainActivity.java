package com.example.anthonyeisenback.videogamelibraryandroidapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements com.example.anthonyeisenback.videogamelibraryandroidapp.VideoGameAdapter.AdapterCallback, AddGameFragment.ActivityCallback {
    @BindView(R.id.game_list_recyclerview)
    protected RecyclerView recyclerView;

    private AddGameFragment addGameFragment;
    private List<VideoGameCreator> gamesList;
    private VideoGameAdapter adapter;
    private VideoGameDatabase videoGameDatabase;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        videoGameDatabase = ((VideoGameApplication) getApplicationContext()).getDatabase();
        setUpList();
    }

    private void setUpList() {
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new VideoGameAdapter(videoGameDatabase.videoGameDAO().getVideoGames(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();


    }

    @OnClick(R.id.add_game_button)
    protected void addGameOnClick() {
        addGameFragment = AddGameFragment.newInstance();
        addGameFragment.attachParent(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, addGameFragment).addToBackStack("").commit();

    }


    @Override
    public void addClicked() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        }
        adapter.updateList(videoGameDatabase.videoGameDAO().getVideoGames());
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void rowClicked(VideoGameCreator videoGame) {
        if (videoGame.isCheckedOut()) {
            checkGameBackIn(videoGame);

        } else {
            checkGameOut(videoGame);
        }
    }

    @Override
    public void rowLongClicked(final VideoGameCreator videoGame) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_title);
        builder.setMessage(R.string.delete_question).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gamesList.remove(videoGame);
                videoGameDatabase.videoGameDAO().deleteVideoGame(videoGame);
                adapter.updateList(videoGameDatabase.videoGameDAO().getVideoGames());
                adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, R.string.deleted, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkGameOut(final VideoGameCreator videoGame) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage("check out the game?").setTitle("Check out?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                videoGame.setCheckedOut(true);
                videoGameDatabase.videoGameDAO().updateVideoGame(videoGame);
                adapter.updateList(videoGameDatabase.videoGameDAO().getVideoGames());
                Toast.makeText(MainActivity.this, "Game checked out", Toast.LENGTH_SHORT).show();

            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void checkGameBackIn(final VideoGameCreator videoGame) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check-in Game?");
        builder.setMessage(R.string.check_in_yon).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                videoGame.setCheckedOut(false);
                videoGameDatabase.videoGameDAO().updateVideoGame(videoGame);
                adapter.updateList(videoGameDatabase.videoGameDAO().getVideoGames());
                Toast.makeText(MainActivity.this, R.string.checked_in, Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }
}

