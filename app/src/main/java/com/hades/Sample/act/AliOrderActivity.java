package com.hades.Sample.act;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.hades.Sample.AliOrderAdt;
import com.hades.Sample.HListView.HListView;
import com.hades.Sample.R;

public class AliOrderActivity extends AppCompatActivity implements HListView.HScrollListener, AdapterView.OnItemClickListener {


    private HListView _main_lv;

    private RelativeLayout _top_nav;

    private int _A = 255;

    private String[] adapterData;

    private final String TAG = "AliOrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_order);
        initView();
    }

    private void initView() {
        adapterData = new String[] { "Activity","Service","Content Provider","Intent","BroadcastReceiver","ADT","Sqlite3","HttpClient","DDMS","Android Studio","Fragment","Loader", "BroadcastReceiver","ADT","Sqlite3","HttpClient","DDMS","AndroidStudio","Fragment","Loader" };

        View header = getLayoutInflater().inflate(R.layout.header, null);
        _main_lv = (HListView)findViewById(R.id.main_lv);
        _main_lv.setHScrollListener(this);
        // _main_lv.addHeaderView(header, null, false);
        _main_lv.setOnItemClickListener(this);
        // header.setVisibility(View.GONE);
//        _main_lv.setAdapter(new ArrayAdapter<String>(AliOrderActivity.this,
//                android.R.layout.simple_list_item_1, adapterData));
        _main_lv.setAdapter(new AliOrderAdt(this));
        // _main_lv.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        _top_nav = (RelativeLayout)findViewById(R.id.top_nav);
    }

    @Override
    public void onStartHScroll(HListView listView) {
        Log.i(TAG, "onStartHScroll");
        View c = listView.getChildAt(0);
        int top = c.getTop();
        Adapter listAdapter = listView.getAdapter();
        View listItem0 = listAdapter.getView(0, null, listView);
        listItem0.measure(0, 0);
        int item0Height = listItem0.getMeasuredHeight();
        last = item0Height + (-top);
    }

    float last;
    @Override
    public void onHScrolling(HListView listView) {
        // Log.i(TAG, "onHScrolling");
        // Log.i(TAG, "height = " + getScrollY(listView));
        int firstVisiblePos = listView.getFirstVisiblePosition();

        Adapter listAdapter = listView.getAdapter();
        View listItem0 = listAdapter.getView(0, null, listView);
        listItem0.measure(0, 0);
        int item0Height = listItem0.getMeasuredHeight();

        View listItem1 = listAdapter.getView(1, null, listView);
        listItem1.measure(0, 0);
        int item1Height = listItem1.getMeasuredHeight();

        if (firstVisiblePos == 1){

            View c = listView.getChildAt(0);
            int top = c.getTop();

            float cur = item0Height + (-top);
            float total = item0Height + item1Height;
            Log.i(TAG, total + "ddddddddddddd " + cur);
            // int _A = (int)((cur-last)/(total - cur)* 255);
            int _A = (int)((1-cur/total) * 255);


            _top_nav.getBackground().setAlpha(_A);
        }else if(firstVisiblePos > 1) {
            _top_nav.getBackground().setAlpha(0);
        }



    }

    @Override
    public void onHscrolled() {
        Log.i(TAG, "onHscrolled");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("test", position + "");
    }

    private int getScrollingY(ListView listView){
        int divHeight = listView.getDividerHeight();
        View c = listView.getChildAt(0);
        if (c == null){
            return 0;
        }

        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int scrollY = 0;
        for (int i = 0; i<firstVisiblePosition; i++){
            Adapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return 0;
            }
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            scrollY += listItem.getMeasuredHeight();
        }
        int top = c.getTop();
        return -top + scrollY + firstVisiblePosition * divHeight;
    }

    private int getScrollY(ListView listView){
        int divHeight = listView.getDividerHeight();
        int count = listView.getCount();
        Log.i(TAG, "divHeight = "+divHeight);
        int scrollY=0;
        for (int i=0;i<count; i++){
            Adapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return 0;
            }
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            scrollY += listItem.getMeasuredHeight();
        }
        return scrollY + (count -1) * divHeight;
    }
}
