package com.hades.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.demo.hades.updatedownload.R;

import java.io.File;

/**
 * Created by Hades on 2016/8/15.
 */
public class UpdateService extends Service {

    private String apkURL;
    private String filePath;
    private NotificationManager mNotificationManager;

    private Notification mNotification;


    private static final int START = 0;
    private static final int DOWNLOADING = 1;
    private static final int FINISHED = 2;
    private static final int FAILURE = 3;



    @Override
    public void onCreate() {
        Log.i("test", "onCreate");
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        filePath = Environment.getExternalStorageDirectory() + "/imooc/xiunaer.apk";
        initNotification();
    }

    private void initNotification() {

        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(getString(R.string.app_name));


            builder.setProgress(0, 0, false);
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentIntent(PendingIntent.getActivity(this,
                0,
                new Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT));
        mNotification = builder.build();
        mNotificationManager.notify(0, mNotification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null){
            notifyUser(FAILURE, "下载失败", 0);
        }
        apkURL = intent.getStringExtra("apkUrl");
        notifyUser(START, "下载开始", 0);
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        UpdateDownloadManager.getInstance().startDowndloads(apkURL, filePath, new UpdateDownloadListener() {
            @Override
            public void onStarted() {
                notifyUser(START, "开始下载", 0);
            }

            @Override
            public void onProgressChanged(int progress, String downloadUrl) {
                notifyUser(DOWNLOADING, "正在下载...", progress);
            }

            @Override
            public void onFinished(int completeSize, String downloadUrl) {
                notifyUser(FINISHED, "下载完成...", 100);
                stopSelf();
            }

            @Override
            public void onFailure() {
                notifyUser(FAILURE, "下载失败", 0);
                stopSelf();
            }
        });
    }

    NotificationCompat.Builder builder = null;
    private void notifyUser(int result, String reason, int progress) {
        if (progress > 0 && progress <= 100){
            builder.setProgress(100, progress, false);
        }else {
            builder.setProgress(0, 0, false);
        }
        if (result == FINISHED){
            // builder.setProgress(0, 0, false);
            builder.setContentText("已完成, 点击安装");
            builder.setContentIntent(progress >= 100 ? getContentIntent() : PendingIntent.getActivity(this,
                    0,
                    new Intent(),
                    PendingIntent.FLAG_UPDATE_CURRENT));
        }
        builder.setContentInfo(progress + "%");
        mNotification = builder.build();
        mNotificationManager.notify(0, mNotification);

    }

    private PendingIntent getContentIntent() {
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.getAbsolutePath()),
                "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
