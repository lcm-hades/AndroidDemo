package com.hades.Sample.act;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hades.Sample.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import Utils.JsonUtil;

public class GsonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        String result = getFromAssets("city.txt");
        Log.i("test", result);

//        Gson g = new Gson();
//        Type type = new TypeToken<List<CityBean>>(){}.getType();
//        List<CityBean> cityList = g.fromJson(result, type);

        List<CityBean> cityList = JsonUtil.string2List(result, CityBean[].class);
        for (CityBean city : cityList){
            for (CityBean.ChildEntity area : city.getChild()){
                Log.i("test", area.getArea_name());
            }
        }
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


}
