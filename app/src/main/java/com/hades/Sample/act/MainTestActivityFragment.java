package com.hades.Sample.act;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hades.Sample.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainTestActivityFragment extends Fragment {

    public MainTestActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_test, container, false);
    }
}
