package com.hades.Sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.hades.Sample.SHTimer.SHTimer;

/**
 * Created by Hades on 2015/10/19.
 */
public class TimerButton extends Button implements View.OnClickListener {

    private final String NORMALTITLE = "验证码";

    private final String TIMERTITLE = "秒后重新获取";

    private int m_deley = 10;

    private int count = m_deley;

    public TimerButton(Context context) {
        this(context, null);
    }

    public TimerButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setText(NORMALTITLE);
        setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        count = m_deley;
        setClickable(false);
        final SHTimer shTimer = new SHTimer();
        shTimer.start(0, 1000, m_deley, new SHTimer.SHTimerCallback() {
            @Override
            public void onTimerCallback() {
                if (count == 0){
                    // shTimer.cancel();
                    setText(NORMALTITLE);
                    setClickable(true);
                }else {
                    setText(count+ TIMERTITLE);
                }
                count --;
            }
        });
    }
}
