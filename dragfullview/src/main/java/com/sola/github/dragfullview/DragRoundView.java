package com.sola.github.dragfullview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sola.github.dragfullview.delegate.IEventCallBack;
import com.sola.github.dragfullview.delegate.IRoundTouchListener;


/**
 * Created by 禄骥
 * 2016/8/4.
 */
public class DragRoundView extends View implements IEventCallBack {

    // ===========================================================
    // Constants
    // ===========================================================

//    private static final String TAG = "Sola_Drag";

    // ===========================================================
    // Fields
    // ===========================================================

    private Paint mDrawPaint, textPaint;
    private int mRadius = 16;
    private Rect mBounds = new Rect();
    private String mText;
//    Rect r = new Rect();

    private boolean isDraw = false, isDestroy;

    private IRoundTouchListener listener;

    // ===========================================================
    // Constructors
    // ===========================================================

    public DragRoundView(Context context) {
        this(context, null);
    }

    public DragRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragRoundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    /**
     * @param listener 监听自身控件被ActionDown事件的触发
     */
    @SuppressWarnings("UnusedDeclaration")
    public void setListener(IRoundTouchListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setRadius(int mRadius) {
        this.mRadius = mRadius;
        getLayoutParams().width = 2 * mRadius;
        getLayoutParams().height = 2 * mRadius;
        requestLayout();
        invalidate();
    }

    /**
     * @param text 文字
     */
    @SuppressWarnings("UnusedDeclaration")
    public void refreshText(String text) {
        this.mText = text;
        isDraw = (mText != null && !mText.isEmpty());
        isDestroy = false;
        invalidate();
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getLayoutParams().width = 2 * mRadius;
        getLayoutParams().height = 2 * mRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isDraw) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, createPaint());
            createTextPaint();
            canvas.getClipBounds(mBounds);
            int cHeight = mBounds.height();
            int cWidth = mBounds.width();
            textPaint.getTextBounds(mText, 0, mText.length(), mBounds);
            float x = cWidth / 2f;
            float y = cHeight / 2f + mBounds.height() / 2f - mBounds.bottom;
            canvas.drawText(mText, x, y, textPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed)
            mBounds.set(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isDestroy || mText == null || mText.isEmpty())
                    break;
                isDraw = false;
                int[] location = new int[2];
                this.getLocationInWindow(location);
                if (listener != null)
                    listener.onViewTouch(event, location[0], location[1], this);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void resume() {
        isDraw = true;
        invalidate();
    }

    @Override
    public void destroy() {
        isDestroy = true;
        invalidate();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private Paint createPaint() {
        if (mDrawPaint == null) {
            mDrawPaint = new Paint();
            mDrawPaint.setAntiAlias(true);
            mDrawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mDrawPaint.setStrokeWidth(1);
            mDrawPaint.setColor(ContextCompat.getColor(getContext(), R.color.color_drag_red));
        }
        return mDrawPaint;
    }

    private Paint createTextPaint() {
        if (textPaint == null) {
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            textPaint.setTextSize(14);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setColor(Color.WHITE);
        }
        return textPaint;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
