package HRoundProgressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.hades.Sample.R;

/**
 * Created by Hades on 2015/11/25.
 */
public class HRoundProgressBar extends View {

    private Paint _mPaint;

    private int _mRoundColor;

    private int _mRoundProgressColor;

    /**
     * 中间进度百分比的字符串的颜色
     */
    private int _mTextColor;

    /**
     * 中间进度百分比的字符串的字体
     */
    private float _mTextSize;

    /**
     * 圆环的宽度
     */
    private float _mRoundWidth;

    /**
     * 最大进度
     */
    private int _mMax;

    /**
     * 当前进度
     */
    private int _mProgress;
    /**
     * 是否显示中间的进度
     */
    private boolean _mTextIsDisplayable;

    /**
     * 进度的风格，实心或者空心
     */
    private int _mStyle;

    public static final int STROKE = 0;
    public static final int FILL = 1;

    public HRoundProgressBar(Context context) {
        this(context, null);
    }

    public HRoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HRoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        _mPaint = new Paint();

        TypedArray mTypeArray = context.obtainStyledAttributes(attrs,
                R.styleable.HRoundProgressBar);
        _mRoundColor = mTypeArray.getColor(R.styleable.HRoundProgressBar_roundColor, Color.RED);
        _mRoundProgressColor = mTypeArray.getColor(R.styleable.HRoundProgressBar_roundProgressColor, Color.GREEN);


        _mTextColor = mTypeArray.getColor(R.styleable.HRoundProgressBar_textColor, Color.GREEN);
        _mTextSize = mTypeArray.getDimension(R.styleable.HRoundProgressBar_textSize, 15);
        _mRoundWidth = mTypeArray.getDimension(R.styleable.HRoundProgressBar_roundWidth, 5);
        _mMax = mTypeArray.getInteger(R.styleable.HRoundProgressBar_max, 100);
        _mTextIsDisplayable = mTypeArray.getBoolean(R.styleable.HRoundProgressBar_textIsDisplayable, true);
        _mStyle = mTypeArray.getInt(R.styleable.HRoundProgressBar_style, 0);
        mTypeArray.recycle();
    }

    ///// onDraw() start
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 画最外层的大圆环
         */
        int centre = getWidth()/2; // 获取圆心的x坐标
        int radius = (int)(centre - _mRoundWidth/2); // 圆环的半径
        _mPaint.setColor(_mRoundColor); // 设置圆环的颜色
        _mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        _mPaint.setStrokeWidth(_mRoundWidth); // 设置圆环的宽
        _mPaint.setAntiAlias(true); // 设置抗锯齿
        canvas.drawCircle(centre, centre, radius, _mPaint);

        /**
         * 显示数字百分比
         */

        _mPaint.setStrokeWidth(0);
        _mPaint.setColor(_mTextColor);
        _mPaint.setTextSize(_mTextSize);
        _mPaint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体
        int percent = (int)(((float)_mProgress / (float)_mMax) * 100);//中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWidth = _mPaint.measureText(percent + "%"); //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        if (_mTextIsDisplayable && percent != 0 && _mStyle == STROKE){
            canvas.drawText(percent + "%", centre - textWidth / 2, centre + _mTextSize/2, _mPaint);
        }


        /**
         * 画圆弧，画圆环的进度
         */

        _mPaint.setStrokeWidth(_mRoundWidth);
        _mPaint.setColor(_mRoundProgressColor);
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限

        switch (_mStyle){
            case STROKE:
                _mPaint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, 0, 360 * _mProgress / _mMax, false, _mPaint);
                break;
            case FILL:
                _mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (_mProgress != 0)
                    canvas.drawArc(oval, 0, 360 * _mProgress / _mMax, true, _mPaint);
                break;
        }
    }
    ///// onDraw() end

    public synchronized int getMax(){
        return _mMax;
    }

    public synchronized void setMax(int max){
        if (max < 0){
            throw new IllegalArgumentException("max not less than 0");
        }
        this._mMax = max;
    }

    public synchronized int getProgress(){
        return _mProgress;
    }

    public synchronized void setProgress(int progress){
        if (progress < 0){
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > _mMax){
            _mProgress = _mMax;
        }else {
            _mProgress = progress;
        }
        postInvalidate();
    }

    public int getCricleColor(){
        return _mRoundColor;
    }

    public void setCricleColor(int cricleColor){
        this._mRoundColor = cricleColor;
    }

    public int getCricleProgressColor(){
        return _mRoundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor){
        this._mRoundProgressColor = cricleProgressColor;
    }

    public int getTextColor(){
        return _mTextColor;
    }

    public void setTextColor(int textColor){
        this._mTextColor = textColor;
    }

    public float getTextSize(){
        return _mTextSize;
    }

    public void setTextSize(float textSize){
        this._mTextSize = textSize;
    }

    public float getRoundWidth(){
        return _mRoundWidth;
    }

    public void setRoundWidth(float roundWidth){
        this._mRoundWidth = roundWidth;
    }
}
