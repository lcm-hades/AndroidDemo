package com.hades.Sample.act;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;

import com.hades.Sample.R;

public class Mp3Activity extends AppCompatActivity {

    public MediaPlayer player; // 定义多媒体对象
    private static final int NOTIFICATION_FLAG = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3);
        player=MediaPlayer.create(this,R.raw.yang);

//        player.setLooping(true);
//        player.start();
        createPush("TickerText:您有新短消息，请注意查收！", "Hello world！！");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mp3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null){
            player.stop();
            player.release();//释放资源
            player = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null){
            player.stop();
            player.release();//释放资源
            player = null;
        }
    }

    private void createPush(String tickerTitle, String content){
        Notification myNotify = new Notification();
        myNotify.icon = R.mipmap.ic_launcher;
        myNotify.tickerText = tickerTitle; // "TickerText:您有新短消息，请注意查收！";
        myNotify.when = System.currentTimeMillis();
        myNotify.flags = Notification.FLAG_NO_CLEAR;// 不能够自动清除
        myNotify.sound = Uri.parse("android.resource://"+ getPackageName() + "/" + R.raw.yang);
        RemoteViews rv = new RemoteViews(getPackageName(),
                R.layout.my_notification);
        rv.setTextViewText(R.id.text_content, content);
        myNotify.contentView = rv;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1,
                intent, 1);
        myNotify.contentIntent = contentIntent;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);;
        manager.notify(NOTIFICATION_FLAG, myNotify);
    }
}
