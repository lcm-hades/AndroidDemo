package com.hades.Sample.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by Hades on 2016/8/9.
 */
public class StickyLayout extends LinearLayout {


    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private int mLastX;
    private int mLastY;

    public StickyLayout(Context context) {
        this(context, null);
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());

        mScroller.abortAnimation();
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                scrollBy(0, -deltaY);
                break;
            case MotionEvent.ACTION_UP:
                int scrollY = getScrollY();
                int scrollToChildIndex = scrollY;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocityTracker = mVelocityTracker.getXVelocity();

                // int dx =
                // smoothScrollTo(0, scrollY);
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScrollTo(int dx, int dy){
        // mScroller.startScroll(getScrollX(), 0, dx, 0, 500);

        int scrollX = getScrollX();
        int delta = dx - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, 400);
        invalidate();
    }

    private void smoothScrollBy(int dx, int dy){
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
