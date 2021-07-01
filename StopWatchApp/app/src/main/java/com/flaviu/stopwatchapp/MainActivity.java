package com.flaviu.stopwatchapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

   private int seconds = 0;
    private boolean running;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runTimer(1000);
    }
    public void onClickStart(View view){
        running = true;
        runTimer(1000);
    }
    public void onClickStop(View view){
        running = false;
    }
    public void onClickReset(View view){
        running = false;
        seconds=0;
    }

    private void runTimer(int delay){
        Handler handler = new Handler();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your Code
                    TextView timeview = (TextView) findViewById(R.id.time_view);
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = seconds % 60;
                    String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                    timeview.setText(time);
                    if (running) {
                        seconds++;
                    }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeview.setText(time);
                    }
                });
            }
        }, delay);
    }
}