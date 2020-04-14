package com.moviefinder.android.features;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.moviefinder.android.R;

public class ThreadsActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        progressBar = findViewById(R.id.progressbar);
        handler = new Handler();
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            startProgress();
        }
    }

    private void startProgress() {
        new Thread(() -> {
            for (int i=0;i<=30;i++){
                final int currentProgress = i;
                handler.post(() -> progressBar.setProgress(currentProgress));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
