package com.hades.Sample.JavaSyntaxs;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 2016/6/17.
 */
public class Temple<T> {

    protected List<T> arr = new ArrayList<>();
    public T getT(int i){
        return arr.get(i);
    }

    public void log(){
        Log.i("test", "ddddddddddddddddddddddddd");
    }

}
