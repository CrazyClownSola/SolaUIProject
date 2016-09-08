package com.sola.github.dragfullview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
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

    private final static int Default_Radius = 16;
    private final static int Default_Text_Size = 10;
    private final static float Default_Offset = 0;

    // ===========================================================
    // Fields
    // ===========================================================

    private Paint mDrawPaint, textPaint;
    //    private int mRadius = 16;
    private Rect mBounds = new Rect();
    private String mText;

    private float mTextSize, mOffset;
    private int mTextColor, mRoundColor, mRadius;

    private boolean isDraw = false, isForced, isDestroy, isBlock, isAnimRun;

    private IRoundTouchListener listener;

    private Drawable ignore_bg;

    // ===========================================================
    // Constructors
    // ===========================================================

    public DragRoundView(Context context) {
        this(context, null);
    }

    public DragRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
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
     * @param text     文字
     * @param isForced 是否强制刷新
     */
    @SuppressWarnings("UnusedDeclaration")
    public void refreshText(String text, boolean isForced) {
        this.mText = text;
        isDraw = isForced || (mText != null && !mText.isEmpty());
        this.isForced = isForced;
        isDestroy = false;
        if (!isAnimRun) {
            invalidate();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void refreshText(String text) {
        refreshText(text, false);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setBlock(boolean block) {
        isBlock = block;
        if (!isAnimRun) {
            invalidate();
        }
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getLayoutParams().width = (int) (2 * mRadius + mOffset);
        getLayoutParams().height = (int) (2 * mRadius + mOffset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawPoint();
        if (isBlock) {
            if (ignore_bg == null)
                ignore_bg = ContextCompat.getDrawable(getContext(), R.drawable.ignore_disturb);
            ignore_bg.setBounds(0, 0, 2 * mRadius, 2 * mRadius);
            ignore_bg.draw(canvas);
            return;
        }
        if (isDraw) {
            if (isForced || !isTextEmpty())
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius - 2, createPaint());
            if (!isTextEmpty())
                drawText(canvas);
        }
    }

    private boolean isTextEmpty() {
        return mText == null || mText.isEmpty();
    }

    private void drawText(Canvas canvas) {
        createTextPaint();
        canvas.getClipBounds(mBounds);
        int cHeight = mBounds.height();
        int cWidth = mBounds.width();
        textPaint.getTextBounds(mText, 0, mText.length(), mBounds);
        float x = cWidth / 2f;
        float y = cHeight / 2f + mBounds.height() / 2f - mBounds.bottom;
        canvas.drawText(mText, x, y, textPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed)
            mBounds.set(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isBlock)
            return super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isDestroy || (!isForced && isTextEmpty()))
                    break;
                isDraw = false;
                isAnimRun = true;
                int[] location = new int[2];
                this.getLocationInWindow(location);
                if (listener != null)
                    listener.onViewTouch(event, location[0] + 20, location[1] + 20, this);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void resume() {
        isDraw = true;
        isAnimRun = false;
        invalidate();
    }

    @Override
    public void destroy() {
        isDestroy = true;
        isAnimRun = false;
        invalidate();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray attr = getTypedArray(context, attrs, R.styleable.DragRoundView);
        if (attr == null) return;
        try {
            mTextSize = attr.getDimension(R.styleable.DragRoundView_dg_text_size, Default_Text_Size);
            mRadius = (int) attr.getDimension(R.styleable.DragRoundView_dg_round_radius, Default_Radius);
            mTextColor = attr.getColor(R.styleable.DragRoundView_dg_text_color,
                    ContextCompat.getColor(getContext(), R.color.color_drag_white));
            mRoundColor = attr.getColor(R.styleable.DragRoundView_dg_round_background,
                    ContextCompat.getColor(getContext(), R.color.color_drag_red));
            mOffset = attr.getDimension(R.styleable.DragRoundView_dg_size_offset,
                    Default_Offset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            attr.recycle();
        }
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    private Paint createPaint() {
        if (mDrawPaint == null) {
            mDrawPaint = new Paint();
            mDrawPaint.setAntiAlias(true);
            mDrawPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mDrawPaint.setStrokeWidth(1);
            mDrawPaint.setColor(mRoundColor);
        }
        return mDrawPaint;
    }

    private Paint createTextPaint() {
        if (textPaint == null) {
            textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            textPaint.setTextAlign(Paint.Align.CENTER);
            textPaint.setTextSize(mTextSize);
            textPaint.setColor(mTextColor);
        }
        return textPaint;
    }

//    @Override
//    public void onDragViewsDestroy() {
//        destroy();
//    }


    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
