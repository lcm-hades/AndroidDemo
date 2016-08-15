package com.hades.Sample.HListView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by Hades on 2015/12/22.
 */
public class HListView extends ListView implements AbsListView.OnScrollListener {

    private HScrollListener _listener;

    private boolean _isScrolling = false;

    public HListView(Context context) {
        this(context, null);
    }

    public HListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        _isScrolling = false;
        if (_listener != null){
            _listener.onHscrolled();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (_listener != null){
            if (!_isScrolling){
                _isScrolling = true;
                _listener.onStartHScroll(this);
            }
            _listener.onHScrolling(this);
        }
    }


    public void setHScrollListener(HScrollListener listener){
        _listener = listener;
    }

    public interface HScrollListener{
        void onStartHScroll(HListView listView);
        void onHScrolling(HListView listView);
        void onHscrolled();
    }
}
