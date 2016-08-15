package com.hades.Sample.act;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hades.Sample.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import Utils.ToastUtil;

public class XUtilsViewActivity extends AppCompatActivity {

    private BitmapUtils mBitmapUtils;

    @ViewInject(R.id.content_tv)
    private TextView textView;

    @ViewInject(R.id.bg_iv)
    private ImageView bg_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xutils_view);
        ViewUtils.inject(this);
        initBitmap();
        textView.setText("ssssssssssssssssss");
        // textView.setVisibility(View.GONE);
    }

    private void initBitmap() {
        BitmapLoadCallBack<ImageView> callBack = new DefaultBitmapLoadCallBack<ImageView>(){
            @Override
            public void onLoadStarted(ImageView container, String uri, BitmapDisplayConfig config) {
                super.onLoadStarted(container, uri, config);
            }

            @Override
            public void onLoading(ImageView container, String uri, BitmapDisplayConfig config, long total, long current) {
                super.onLoading(container, uri, config, total, current);
            }

            @Override
            public void onLoadCompleted(ImageView container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {
                super.onLoadCompleted(container, uri, bitmap, config, from);
                Toast.makeText(getApplicationContext(), bitmap.getWidth() + "*" + bitmap.getHeight(), Toast.LENGTH_SHORT).show();
                fadeInDisplay(container, bitmap);
            }
        };

        mBitmapUtils = new BitmapUtils(this);
        mBitmapUtils.configDefaultLoadFailedImage(R.drawable.a);
        mBitmapUtils.configDefaultLoadingImage(R.drawable.a);
        mBitmapUtils.display(bg_iv, getString(R.string.image_url), callBack);

    }

    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(
            R.color.transparent);
    private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {//目前流行的渐变效果
        final TransitionDrawable transitionDrawable = new TransitionDrawable(
                new Drawable[] { TRANSPARENT_DRAWABLE,
                        new BitmapDrawable(imageView.getResources(), bitmap) });
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(2000);
    }


    @OnClick(R.id.save_btn)
    public void onSaveClick(View v){
        String filename = "pic.png";
        // File file = new File(getFilesDir(), filename);
        File file = new File(Environment.getExternalStorageDirectory(), filename);

        BitmapDrawable bmpDrawable = (BitmapDrawable)bg_iv.getDrawable();
        Bitmap bmp = bmpDrawable.getBitmap();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);

            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            ToastUtil.show(this, "LANDSCAPE");
        }else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            ToastUtil.show(this, "PORTRAIT");
        }

    }

    Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private void loadImagesByThread(final String url, final int id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = null;
                try {
                    drawable = Drawable.createFromStream(new URL(url).openStream(), "image.png");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Message msg = mainHandler.obtainMessage();
                msg.what = 2012;
                msg.arg1 = id;
                msg.obj = drawable;
                msg.sendToTarget();
            }
        }).start();


        // new DownloadFilesTask.execute("", "");
    }

    private class DownloadFilesTask extends AsyncTask<String, String, Long>{

        @Override
        protected Long doInBackground(String... params) {
            return null;
        }



        @Override
        protected void onProgressUpdate(String... values) {

        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
