package com.sola.github.solauiproject.wow;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.sola.github.solauiproject.R;
import com.sola.github.wow.eases.AntiLinear;
import com.sola.github.wow.eases.EaseType;

/**
 * Created by slove
 * 2016/11/24.
 */
public class TestView extends View {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Path mPath = new Path();
    private Paint mPaint;

    float mPoint;

    private EaseType type;

    // ===========================================================
    // Constructors
    // ===========================================================

    public TestView(Context context) {
        super(context);
        initAttrs(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray attr = getTypedArray(context, attrs, R.styleable.TestView);
        if (attr == null) return;
        try {
            int typeInt = attr.getInteger(R.styleable.TestView_test_ease_type,
                    0);
            type = EaseType.values()[typeInt];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            attr.recycle();
        }
        init();
    }

    protected TypedArray getTypedArray(Context context, AttributeSet attributeSet, int[] attr) {
        return context.obtainStyledAttributes(attributeSet, attr, 0, 0);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public void setMPoint(float mPoint) {
        this.mPoint = mPoint;
        PointF pointF = type.getPoint(mPoint);
        if (mPath.isEmpty()) {
            mPath.moveTo(pointF.x * 200, -pointF.y * 100);
        } else {
            mPath.lineTo(pointF.x * 200, -pointF.y * 100);
        }
        Log.d("Sola", "setmPoint() called with: point = [" + mPoint + "][" + pointF.toString() + "]");
        invalidate();
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mPath.isEmpty()) {
            canvas.save();
            canvas.translate(0, getHeight() >> 1);
            canvas.drawPath(mPath, createPaint(R.color.color_default_black));
            canvas.restore();
        }
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private Animator animatorSet;

    private void init() {

        animatorSet = ObjectAnimator.ofFloat(this, "mPoint", 0f, 1f);
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }


    private Paint createPaint(int colorRes) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(4);
        }
        mPaint.setColor(ContextCompat.getColor(getContext(), colorRes));
        return mPaint;
    }
    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
