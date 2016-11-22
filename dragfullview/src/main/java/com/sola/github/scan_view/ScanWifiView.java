package com.sola.github.scan_view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.sola.github.dragfullview.R;

/**
 * Created by slove
 * 2016/10/25.
 * 仿wifi的扫描界面
 */
public class ScanWifiView extends View {

    // ===========================================================
    // Constants
    // ===========================================================

    private final static int DEFAULT_RADIUS = 100;

    // ===========================================================
    // Fields
    // ===========================================================

    private Paint mPaint;

    private Path arcPath;

    private RectF pathRect;

    private int mRadius_Bottom;
    private int mRadius_Middle;
    private int mRadius_TOP;
    private int timeline;

    private ObjectAnimator mAnimator;

    // ===========================================================
    // Constructors
    // ===========================================================

    public ScanWifiView(Context context) {
        super(context);
        init();
    }

    public ScanWifiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScanWifiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    @SuppressWarnings("unused")
    public void setTimeline(int timeline) {
        this.timeline = timeline;
        invalidate();
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() >> 1, getHeight());
        if (timeline >= mRadius_Bottom)  // 绘制底线
            drawCrescent(canvas, mRadius_Bottom, R.color.color_scan_default);
        if (timeline >= mRadius_Middle) // 绘制中线
            drawCrescent(canvas, mRadius_Middle, R.color.color_scan_default1);
        if (timeline >= mRadius_TOP)  // 绘制最上方
            drawCrescent(canvas, mRadius_TOP, R.color.color_scan_default2);
        canvas.restore();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public void start() {
        if (mAnimator != null && !mAnimator.isRunning())
            mAnimator.start();
    }

    @SuppressWarnings("unused")
    public void destroy() {
        if (mAnimator != null && mAnimator.isRunning())
            mAnimator.cancel();
        clean();
        invalidate();
    }

    private void init() {
        pathRect = new RectF();
        arcPath = new Path();
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mRadius_Bottom = DEFAULT_RADIUS;
        mRadius_Middle = mRadius_Bottom + 80;
        mRadius_TOP = mRadius_Middle + 80;
        initAnimator();
    }

    private void initAnimator() {
        mAnimator = ObjectAnimator.ofInt(this, "timeline", 0, 4 * DEFAULT_RADIUS);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(5000);
        mAnimator.setInterpolator(new LinearInterpolator());
    }

    private void clean() {
        if (pathRect != null)
            pathRect.setEmpty();
        if (arcPath != null)
            arcPath.reset();
    }

    /**
     * 绘制月牙
     *
     * @param canvas 画布
     */
    private void drawCrescent(Canvas canvas, int radius, int colorRes) {
        pathRect.set(-radius, -radius, radius, radius);
        arcPath.reset();
        int degree = 60;
        arcPath.moveTo(
                (float) (-radius * Math.sin(Math.toRadians(degree))),
                (float) (-radius * Math.cos(Math.toRadians(degree))));
        arcPath.arcTo(pathRect, -(90 + degree), 2 * degree);
        int offset = 10;
        int offsetRadius = radius + (offset >> 1);
        pathRect.set(-offsetRadius, -offsetRadius + offset, offsetRadius, offsetRadius + offset);
        arcPath.arcTo(pathRect, -(90 - degree), -2 * degree);
        arcPath.close();
        canvas.drawPath(arcPath, createPaint(colorRes));
    }

    private Paint createPaint(int colorRes) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setShadowLayer(5, 0, 8, 0x4441019c);
        }
        mPaint.setColor(ContextCompat.getColor(getContext(), colorRes));
        return mPaint;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
