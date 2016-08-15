package com.hades.Sample.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hades.Sample.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import Utils.IntentUtils;

public class PickImageActivity extends AppCompatActivity {

    @ViewInject(R.id.select_image)
    private Button select_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_image);
         ViewUtils.inject(this);
    }

    @OnClick(R.id.select_image)
    public void onSelectImage(View v){
        IntentUtils.skip(this, ImageGridActivity.class, false);
    }
}
