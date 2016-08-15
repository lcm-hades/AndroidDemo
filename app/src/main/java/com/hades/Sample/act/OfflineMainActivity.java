package com.hades.Sample.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hades.Sample.R;

public class OfflineMainActivity extends BaseActivity implements View.OnClickListener {

    private Button force_offline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_main);
        force_offline = (Button)findViewById(R.id.force_offline);
        force_offline.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent("com.hades.picture.broadcastbestpractice.FORCE_OFFLINE");
        sendBroadcast(intent);
    }
}
