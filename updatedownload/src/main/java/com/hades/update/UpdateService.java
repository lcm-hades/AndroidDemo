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

    @Override
    public void onCreate() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        filePath = Environment.getExternalStorageDirectory() + "/imooc/xiunaer.apk";
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent == null){
            notifyUser("下载失败", "下载失败", 0);
        }
        apkURL = intent.getStringExtra("apkUrl");
        notifyUser("下载开始", "下载开始", 0);
        startDownload();
        return super.onStartCommand(intent, flags, startId);
    }

    private void startDownload() {
        UpdateDownloadManager.getInstance().startDowndloads(apkURL, filePath, new UpdateDownloadListener() {
            @Override
            public void onStarted() {
                notifyUser("开始下载", "开始下载", 0);
            }

            @Override
            public void onProgressChanged(int progress, String downloadUrl) {
                notifyUser("正在下载...", "正在下载...", progress);
            }

            @Override
            public void onFinished(int completeSize, String downloadUrl) {
                notifyUser("下载完成", "下载完成...", 100);
                stopSelf();
            }

            @Override
            public void onFailure() {
                notifyUser("下载失败", "下载失败", 0);
                stopSelf();
            }
        });
    }

    private void notifyUser(String result, String reason, int progress) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(getString(R.string.app_name));
        if (progress > 0 && progress < 100){
            builder.setProgress(100, progress, false);
        }else {
            builder.setProgress(0, 0, false);
        }
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentIntent(progress >= 100 ? getContentIntent() : PendingIntent.getActivity(this,
                0,
                new Intent(),
                PendingIntent.FLAG_UPDATE_CURRENT));
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
