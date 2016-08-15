package com.hades.Sample.act;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.hades.Sample.HTabBar;
import com.hades.Sample.MoveImageView.HMoveImageView;
import com.hades.Sample.Network.Network;
import com.hades.Sample.Photo.PickPhoto;
import com.hades.Sample.R;
import com.hades.Sample.Utils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, HTabBar.HTabBarSelectedListener, AdapterView.OnItemClickListener {


    private Button camera, photo, start;
    private ImageView icon;
    public static final int CAMERA_CODE = 127;
    public static final int PHONE_CODE = 128;
    BitmapUtils bitmapUtils;
    Uri uri;
    String CROP_ACTION = "com.android.camera.action.CROP";
    String GET_CONTENT_ACTION = Intent.ACTION_GET_CONTENT;
    HTabBar tabbar;
    HMoveImageView move_iv;
    private ListView main_list;
    private List<String> data;
    ArrayAdapter adt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bitmapUtils = new BitmapUtils(this);
        bitmapUtils.configDefaultLoadingImage(R.drawable.a);
        Network.post();
        initView();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float sizeScale = metrics.scaledDensity; //文字

        float density = metrics.density; // 屏幕密度
        float densityDPI = metrics.densityDpi; // 屏幕密度

        int width = metrics.widthPixels; //屏幕宽度（像素）
        int height = metrics.heightPixels; //屏幕高度（像素）

        Log.i("test", "width = " + width +
                "\nheight = " + height +
                "\ndensityDPI = " + densityDPI);

        Log.i("test", "----  " + Math.sqrt((width / densityDPI) * (width / densityDPI) + (height / densityDPI) * (height / densityDPI)));

    }

    private void initView() {
        camera = (Button)findViewById(R.id.camera);
        camera.setOnClickListener(this);

        photo = (Button)findViewById(R.id.photo);
        photo.setOnClickListener(this);

        icon = (ImageView)findViewById(R.id.icon);

        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("未完");
        list.add("全部");
        list.add("未完");
        list.add("全部");
        list.add("全部");
        list.add("全部");
        list.add("全部");
        list.add("全部");
        list.add("全部");

        tabbar = (HTabBar)findViewById(R.id.tabbar);
        tabbar.setSelectIndex(5);
        tabbar.setTabText(list);
        tabbar.setHTabBarSelectedListener(this);

        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(this);

         move_iv = (HMoveImageView)findViewById(R.id.move_iv);
         move_iv.setOnClickListener(this);

        main_list = (ListView)findViewById(R.id.main_list);
        adt = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData());
        main_list.setAdapter(adt);
        main_list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera:
//                uri = buildUri();
//                // startActivityForResult(buildCamaraIntent(uri), CAMERA_CODE);
//
//                PickPhoto.startCamera(this, uri, PickPhoto.CAMERA);


                data.add("dddddddddddddddddddd");
                // adt.notifyDataSetChanged();
                adt.notifyDataSetInvalidated();
                break;
            case R.id.photo:
                // uri = buildUri();
                // startActivityForResult(buildIntent(uri, Intent.ACTION_PICK), PHONE_CODE);

                PickPhoto.startPhoto(this, PickPhoto.PHOTO);
                break;
            case R.id.start:
                Utils.openCLD("com.xiunaer.xiunaer_android_client", this);
                break;
            case R.id.move_iv:
                Intent intent = new Intent();

                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == PHONE_CODE){
                bitmapUtils.display(icon, uri.getPath());
            }else if (requestCode == CAMERA_CODE){
                startActivityForResult(buildIntent(uri, CROP_ACTION), PHONE_CODE);
            }

            if (requestCode == PickPhoto.CAMERA){
                bitmapUtils.display(icon, uri.getPath().toString());
            }else if (requestCode == PickPhoto.PHOTO){
                // bitmapUtils.display(icon, uri.getPath());
                icon.setImageBitmap(Utils.getCompressBitmap(this, data.getData(), Utils.getScreenWidth(this), 240));
            }
        }
    }

    private Intent buildCamaraIntent(Uri uri){
        return new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT,uri);
    }

    private Intent buildIntent(Uri uri, String action){
        return new Intent(action)
            .setDataAndType(uri, "image/*")
            .setType("image/*")
            .putExtra("crop", "true")
            .putExtra("scale", true)
            .putExtra("aspectX", 1)
            .putExtra("aspectY", 1)
            .putExtra("outputX", 300)
            .putExtra("outputY", 300)
            .putExtra("return-data", false)
            .putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            .putExtra("noFaceDetection", true)
            .putExtra("scaleUpIfNeeded", true)
                .putExtra(MediaStore.EXTRA_OUTPUT, uri);
    }

    public Uri buildUri() {
        return Uri
                .fromFile(Environment.getExternalStorageDirectory())
                .buildUpon()
                .appendPath("xnrclient/" + String.valueOf(System.currentTimeMillis())
                        + ".jpg")
                .build();
    }

    @Override
    public void onTabBarSelected(View v, int index) {
        Log.i("test", ""+index);
    }

    public  List<String> getData() {
        data = new ArrayList<String>();
        data.add("AddressActivity");
        data.add("Mp3Activity");
        data.add("PhotoActivity");
        data.add("CircleProgressActivity");
        data.add("AuthButtonActivity");
        data.add("DBActivity");
        data.add("ExcelActivity");
        data.add("BroadCastActivity");
        data.add("ForceOfflineActivity");
        data.add("CustomCameraActivity");
        data.add("AnimationActivity");
        data.add("AliOrderActivity");
        data.add("GsonActivity");
        data.add("WebViewActivity");
        data.add("XUtilsViewActivity");
        data.add("ListView下拉刷新--上拉加载更多");
        data.add("RecyclerViewActivity");
        data.add("PickImageActivity");
        data.add("PhotoAlbumActivity");

        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                startActivity(new Intent(this, AddressActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, Mp3Activity.class));
                break;
            case 2:
                startActivity(new Intent(this, PhotoActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, CircleProgressActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, AuthButtonActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, DBActivity.class));
                break;
            case 6:
                startActivity(new Intent(this, ExcelActivity.class));
                break;
            case 7:
                startActivity(new Intent(this, BroadCastActivity.class));
                break;
            case 8:
                startActivity(new Intent(this, ForceOfflineActivity.class));
                break;
            case 9:
                startActivity(new Intent(this, CustomCameraActivity.class));
                break;
            case 10:
                startActivity(new Intent(this, AnimationActivity.class));
                break;
            case 11:
                startActivity(new Intent(this, AliOrderActivity.class));
                break;
            case 12:
                startActivity(new Intent(this, GsonActivity.class));
                break;
            case 13:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            case 14:
                startActivity(new Intent(this, XUtilsViewActivity.class));
                break;
            case 15:
                startActivity(new Intent(this, XUtilsViewActivity.class));
                break;
            case 16:
                startActivity(new Intent(this, RecyclerViewActivity.class));
                break;

            case 17:
                startActivity(new Intent(this, PickImageActivity.class));
                break;
            case 18:
                startActivity(new Intent(this, PhotoAlbumActivity.class));
                break;

        }
    }
}
