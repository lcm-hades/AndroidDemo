package com.hades.Sample.act;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hades.Sample.R;

public class BroadCastActivity extends AppCompatActivity {
    /**
     * 广播
     * 1.动态注册
     *   代码如下
     * 2.静态注册
     *
     */


    private IntentFilter intentFilter;

    private NetWorkChangeReceiver netWorkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broad_cast);

        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeReceiver = new NetWorkChangeReceiver();
        registerReceiver(netWorkChangeReceiver, intentFilter);
        initCustom();
    }

    private void initCustom() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netWorkChangeReceiver);
    }

    public void sendClick(View v){
        Intent intent = new Intent("com.hades.picture.Receiver.CustomReceiver");
        intent.putExtra("key", "hello");
        sendBroadcast(intent);
        // 发送有序广播
        // sendOrderedBroadcast(intent, null);
    }

    class NetWorkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            // 拦截广播
            // abortBroadcast();

            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()){

            }else {

            }
        }
    }

}
