package com.hades.Sample.MoveView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Hades on 2016/2/29.
 */
public class HMoveView extends View {

    private int lastX;

    private int lastY;

    public HMoveView(Context context) {
        super(context);
    }

    public HMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HMoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                layout(getLeft() + offsetX,
                        getTop() + offsetY,
                        getRight() + offsetX,
                        getBottom() + offsetY);
                break;
        }
        return true;
    }
}
