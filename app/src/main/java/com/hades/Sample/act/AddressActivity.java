package com.hades.Sample.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hades.Sample.PopAddress.PopAddress;
import com.hades.Sample.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddressActivity extends AppCompatActivity implements View.OnClickListener, PopAddress.PopAddressSelectedListener {

    private View city_rl;

    private TextView city_tv;

    private Button choose_city_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        initView();
    }

    private void initView() {
        city_rl = findViewById(R.id.city_rl);
        city_tv = (TextView)findViewById(R.id.city_tv);
        choose_city_btn = (Button)findViewById(R.id.choose_city_btn);
        choose_city_btn.setOnClickListener(this);
    }

    private String getFromAssets(String fileName){
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null){
                result += line;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        PopAddress pop = new PopAddress(this);
        pop.setData(getFromAssets("city.txt"));
        pop.setPopAddressSelectedListener(this);
        pop.show();
    }

    @Override
    public void onAddressSelected(String city, String area) {
        city_tv.setText(city + " " + area);
    }
}
