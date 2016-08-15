package com.hades.Sample.MoveImageView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Hades on 2015/10/20.
 */
public class HMoveImageView extends ImageView {

    public HMoveImageView(Context context) {
        this(context, null);
    }

    public HMoveImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HMoveImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            setPosition((int)event.getRawX(), (int)event.getRawY());
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void setPosition(int x, int y){
        RelativeLayout.LayoutParams lp =  (RelativeLayout.LayoutParams)getLayoutParams();
        int height = lp.height;
        int width = lp.width;
        lp.leftMargin = x - width / 2;
        lp.topMargin = y - (height) / 2;
        setLayoutParams(lp);
    }

}
