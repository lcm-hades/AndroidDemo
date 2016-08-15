package com.hades.Sample.act;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.hades.Sample.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import HPhotoAlbum.AlbumHelper;
import HPhotoAlbum.Model.ImageBucket;
import HPhotoAlbum.Model.ImageItem;
import HPhotoAlbum.adt.ImageGridAdapater;
import Utils.ToastUtil;

public class ImageGridActivity extends BaseActivity implements View.OnClickListener {
    public static final int MAX_PHOTO_COUNT = 1;

    private int mSelectBucketIndex = 0;
    private List<ImageItem> allImage = new ArrayList<>();;
    private List<ImageBucket> dataList;
    private GridView gridView;
    private ImageGridAdapater adapter;

    private AlbumHelper helper;
    private TextView image_grid_cancel_tv, bt, bucket_btn;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    ToastUtil.show(ImageGridActivity.this, "最多选择" + MAX_PHOTO_COUNT + "张图片");
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_grid);

        initDatas();
        initView();

        setData();

        for (int i= 0; i<allImage.size(); i++){
            allImage.get(i).isSelected = false;
        }
    }

    private void setData() {
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new ImageGridAdapater(ImageGridActivity.this, allImage,
                mHandler);
        gridView.setAdapter(adapter);
    }

    private void initDatas() {
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        dataList = helper.getImagesBucketList(false);
        getBucketData();
    }

    private void getBucketData(){
        allImage.clear();
        allImage.addAll(dataList.get(mSelectBucketIndex).imageList);
//        for (int i=0;i<dataList.size(); i++){
//            List<ImageItem> childs = dataList.get(i).imageList;
//            for (int j=0;j<childs.size();j++){
//                allImage.add(childs.get(j));
//            }
//        }
    }

    private void initView() {
        image_grid_cancel_tv = (TextView)findViewById(R.id.image_grid_cancel_tv);
        image_grid_cancel_tv.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.image_grid_gridview);
        bt = (TextView)findViewById(R.id.bt);
        bt.setOnClickListener(this);
        bucket_btn = (TextView) findViewById(R.id.bucket_btn);
        bucket_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_grid_cancel_tv:
                finish();
                break;
            case R.id.bt:
                Intent intent = new Intent();
                intent.putExtra("pick", (Serializable) ImageGridAdapater.mSelect);
                setResult(20000 + 1, intent);
                finish();
                break;
            case R.id.bucket_btn:

                break;
        }
    }
}
