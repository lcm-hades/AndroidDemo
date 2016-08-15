package com.hades.Sample.act;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hades.Sample.Decoration.DividerGridItemDecoration;
import com.hades.Sample.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    @ViewInject(R.id.recycle_view)
    private RecyclerView recycle_view;

    @ViewInject(R.id.swipe_refresh_widget)
    private SwipeRefreshLayout swipe_refresh_widget;

    private List<String> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ViewUtils.inject(this);
        initData();
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        recycle_view.setHasFixedSize(true);

//        recycle_view.setLayoutManager(new LinearLayoutManager(this));
//        recycle_view.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL_LIST));

        recycle_view.setLayoutManager(new GridLayoutManager(this,4));
        recycle_view.addItemDecoration(new DividerGridItemDecoration(this));
        recycle_view.setAdapter(new RecyclerAdapter());


        swipe_refresh_widget.setColorSchemeResources(R.color.xiunaer);
        swipe_refresh_widget.setOnRefreshListener(this);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for(int i=0; i<20; i++){
            mDatas.add("data" + i);
        }
    }

    @Override
    public void onRefresh() {
        // Log.i("test", "dddddddddddddddddddd");
        // swipe_refresh_widget.setRefreshing(true);
        swipe_refresh_widget.setRefreshing(false);
    }


    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_recycler, viewGroup, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder viewHolder, int i) {
            viewHolder.tv.setText(mDatas.get(i));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.content_tv);
            }
        }
    }

}
