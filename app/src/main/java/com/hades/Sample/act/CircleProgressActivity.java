package com.hades.Sample.act;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hades.Sample.R;
import com.hades.Sample.SHTimer.SHTimer;

import HRoundProgressBar.HRoundProgressBar;

public class CircleProgressActivity extends AppCompatActivity {

    private HRoundProgressBar _mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        _mProgress = (HRoundProgressBar)findViewById(R.id.progress);
        _mProgress.setMax(100);

        SHTimer timer = new SHTimer();
        timer.start(0, 1000, 10, new SHTimer.SHTimerCallback() {
            @Override
            public void onTimerCallback() {
                _mProgress.setProgress(_mProgress.getProgress() + 10);
            }
        });

    }



}
