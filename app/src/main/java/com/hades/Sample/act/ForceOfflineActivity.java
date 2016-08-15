package com.hades.Sample.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.hades.Sample.R;

public class ForceOfflineActivity extends BaseActivity implements View.OnClickListener {

    private EditText account_et, password_et;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_offline);
        account_et = (EditText)findViewById(R.id.account);
        password_et = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String account = account_et.getText().toString();
        String password = password_et.getText().toString();
        if (account.equals("admin") && password.equals("123456")){
            Intent intent = new Intent(ForceOfflineActivity.this, OfflineMainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
