package com.hades.Sample.act;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.hades.Sample.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView web;

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        web = (WebView)findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setDefaultTextEncodingName("utf-8");
        web.addJavascriptInterface(new WebAppInterface(this), "Android");
        web.loadUrl("file:///android_asset/index.html");
    }

    // android
    public class WebAppInterface{
        Context mContext;
        WebAppInterface(Context context){
            mContext = context;
        }

        @JavascriptInterface
        public void showToast(String toast){
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

    }
}
