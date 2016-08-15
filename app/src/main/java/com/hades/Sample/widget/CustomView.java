package com.hades.Sample.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by Hades on 2016/8/5.
 */
public class CustomView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private VelocityTracker velocityTracker;

    private GestureDetector mGestureDetector;

    private Scroller mScroller;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        velocityTracker = VelocityTracker.obtain();

        mGestureDetector = new GestureDetector(this);
        mGestureDetector.setIsLongpressEnabled(false);
        mGestureDetector.setOnDoubleTapListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        velocityTracker.addMovement(event);
        velocityTracker.computeCurrentVelocity(10);
        int xVelocity = (int)velocityTracker.getXVelocity();
        int yVelocity = (int) velocityTracker.getYVelocity();
        // Log.i("test", "xVelocity = " + xVelocity + "\nyVelocity = " + yVelocity);
        // return super.onTouchEvent(event);
        return mGestureDetector.onTouchEvent(event);
    }


    /**
     * 手指轻触屏幕的一瞬间，由1个ACTION_DOWN触发
     * 如果返回false onscroll onFling 不触发
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {

        Log.i("test", "onDown");
        return true;
    }

    /**
     * 手指轻触屏幕，尚未松开或拖动，由1个ACTION_DOWN触发
     * 与onDown的区别，他强调的是没有松开或者移动的状态
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {
        Log.i("test", "onShowPress");
    }

    /**
     * 手指（轻轻触摸屏幕后）松开，伴随着一个Motion ACTION_UP而触发，这是单击行为。
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i("test", "onSingleTapUp");
        return false;
    }

    /**
     * 手指按下屏幕并拖动，由1个ACTION_DOWN,多个ACTION_MOVE触发，这是拖动行为。
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i("test", "onScroll");
        return false;
    }

    /**
     * 用户长久按着屏幕不放
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {
        Log.i("test", "onLongPress");
    }

    /**
     * 按下触摸屏、快速滑动后松开。
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("test", "onFling");
        return false;
    }


    /**
     * 严格的单击行为
     * 和onSingleTapUp的区别，如果触发了onSingleTapConfirmed，那么后面不可能在紧跟着另一个单击行为，
     * 即只能是单击，而不可嗯呢该是双击中的一次单击。
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i("test", "onSingleTapConfirmed");
        return false;
    }

    /**
     * 双击，不能与onSingleTapConfirmed 共存
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i("test", "onDoubleTap");
        return false;
    }

    /**
     * 双击行为
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i("test", "onDoubleTapEvent");
        return false;
    }
}
