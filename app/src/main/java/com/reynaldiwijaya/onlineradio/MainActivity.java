package com.reynaldiwijaya.onlineradio;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String urlRadio = "http://stream.radioreklama.bg/radio1rock128";
    private ProgressBar progressBar;
    private TextView txtUrlRadio;
    private Button btnPlay, btnStop;
    private MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUIElement();
        initializeMediaPlayer();
    }

    private void initializeUIElement() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(true);
        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(this);

        btnStop = findViewById(R.id.btnStop);
        btnStop.setEnabled(false);
        btnStop.setOnClickListener(this);

        txtUrlRadio = findViewById(R.id.txtUrlRadio);
        txtUrlRadio.setText("Radio URL: " + urlRadio);
    }
    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource(urlRadio);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                progressBar.setIndeterminate(false);
                progressBar.setSecondaryProgress(100);
                Log.i("Buffering", "" + percent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnPlay){
            startPlaying();
        }else if (v == btnStop){
            stopPlaying();
        }
    }



    private void startPlaying() {
        btnStop.setEnabled(true);
        btnPlay.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        player.prepareAsync();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });
    }
    private void stopPlaying() {
        if (player == null) return;
        try {
            if (player.isPlaying())
                player.stop();
            player.release();
            initializeMediaPlayer();
        }
        catch (IllegalStateException e){

        }
        btnPlay.setEnabled(true);
        btnStop.setEnabled(false);

        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.INVISIBLE);
    }
}


