package com.hades.Sample.PopAddress;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.hades.Sample.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hades on 2015/11/9.
 */
public class PopAddress implements View.OnClickListener{

    private AlertDialog ad;

    private Context context;

    private LayoutInflater inflater;

    private Button ok, cancel;

    private PickerView city, area;

    private List<String> cityList;

    private Map<String, List<String>> areaMap;

    private String cur_city, cur_area;

    private PopAddressSelectedListener listener;

    public PopAddress(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        View parent = inflater.inflate(R.layout.address_pop, null);
        ok = (Button)parent.findViewById(R.id.ok);
        ok.setOnClickListener(this);
        cancel = (Button)parent.findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        city = (PickerView)parent.findViewById(R.id.city);
        city.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                area.setData(areaMap.get(text));
                cur_city = text;
                cur_area = areaMap.get(text).get(areaMap.get(text).size()/2);
            }
        });

        area = (PickerView)parent.findViewById(R.id.area);
        area.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                cur_area = text;
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(parent);
        ad = builder.create();
    }

    public void setData(String data){
        translationData(data);
        cur_city = cityList.get(cityList.size() / 2);
        cur_area = areaMap.get(cur_city).get(areaMap.get(cur_city).size() / 2);
        city.setData(cityList);
        area.setData(areaMap.get(cur_city));
    }

    private void translationData(String data) {
        cityList = new ArrayList<>();
        areaMap = new HashMap<>();
        try {
            JSONArray da = new JSONArray(data);
            for (int i=0; i<da.length(); i++){
                List<String> areas = new ArrayList<>();
                JSONObject city = da.optJSONObject(i);
                cityList.add(city.optString("city_name"));
                JSONArray child = city.optJSONArray("child");
                for (int j=0; j < child.length(); j++){
                    areas.add(child.optJSONObject(j).optString("area_name"));
                }
                areaMap.put(city.optString("city_name"), areas);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void show(){
        ad.show();
    }

    @Override
    public void onClick(View v) {
        ad.dismiss();
        switch (v.getId()){
            case R.id.ok:
                if (listener != null){
                    listener.onAddressSelected(cur_city, cur_area);
                }
                break;
            case R.id.cancel:
                break;
        }
    }

    public interface PopAddressSelectedListener{
        void onAddressSelected(String city, String area);
    }

    public void setPopAddressSelectedListener(PopAddressSelectedListener listener){
        this.listener = listener;
    }

}
