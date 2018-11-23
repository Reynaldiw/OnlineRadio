package com.reynaldiwijaya.onlineradio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import static java.lang.Thread.sleep;

public class SplashScreen extends AppCompatActivity {

    private long ms = 0;
    private long splashTime = 3000;
    private boolean splashActive = true;
    private boolean paused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        Thread myThread = new Thread() {
            public void run() {
                try {
                    while (splashActive && ms < splashTime) {
                        if (!paused) {
                            ms = ms + 40;
                            sleep(100);
                            progressBar.setProgress((int) ms);

                        }
                    }
                } catch (Exception e) {
                } finally {
                    Intent pindah = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(pindah);
                    finish();
                }
            }

        };
        myThread.start();
    }
}
