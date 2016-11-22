package com.sola.github.solauiproject.test;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.sola.github.solauiproject.R;


/**
 * Created by slove
 * 2016/10/25.
 */
public class FindFriendScanView extends View {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Paint mLinePaint, mLinePaint2;
    // bitmap渲染

    private int mRadius = 100, mRadius1 = 180, mRadius2 = 260, offset = 10;

    private Path mPath;

    private RectF rectF;

    BlurMaskFilter maskFilter = new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER);
    // ===========================================================
    // Constructors
    // ===========================================================

    public FindFriendScanView(Context context) {
        super(context);
        init();
    }

    public FindFriendScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
//        ContextCompat.getColor(getContext(), R.color.color_rader_default)
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setShadowLayer(5, 0, 8, 0x4441019c);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        this.setLayerType(View.LAYER_TYPE_SOFTWARE, mLinePaint);
//        mLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.color_rader_default));
//        mLinePaint2 = new Paint();
//        mLinePaint2.setAntiAlias(true);
//        mLinePaint2.setStyle(Paint.Style.FILL_AND_STROKE);
//        mLinePaint2.setColor(ContextCompat.getColor(getContext(), android.R.color.transparent));

        mPath = new Path();
        rectF = new RectF();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getWidth() >> 1, getHeight());

        mLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.color_scan_default2));
        mPath.reset();
        rectF.set(-mRadius2, -mRadius2, mRadius2, mRadius2);
        mPath.moveTo((float) (-Math.sqrt(3) / 2 * mRadius2), -mRadius2 >> 1);
        mPath.arcTo(rectF, -150, 120);
        int radius = mRadius2 + (offset >> 1);
        rectF.set(-radius, -radius + offset, radius, radius + offset);
        mPath.arcTo(rectF, -30, -120);
        mPath.close();
        canvas.drawPath(mPath, mLinePaint);

        mLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.color_scan_default1));
        mPath.reset();
        rectF.set(-mRadius1, -mRadius1, mRadius1, mRadius1);
        mPath.moveTo((float) (-Math.sqrt(3) / 2 * mRadius1), -mRadius1 >> 1);
        mPath.arcTo(rectF, -150, 120);
        radius = mRadius1 + (offset >> 1);
        rectF.set(-radius, -radius + offset, radius, radius + offset);
        mPath.arcTo(rectF, -30, -120);
        mPath.close();
        canvas.drawPath(mPath, mLinePaint);

        mLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.color_scan_default));
        mPath.reset();
        rectF.set(-mRadius, -mRadius, mRadius, mRadius);
        mPath.moveTo((float) (-Math.sqrt(3) / 2 * mRadius), -mRadius >> 1);
        mPath.arcTo(rectF, -150, 120);
        radius = mRadius + (offset >> 1);
        rectF.set(-radius, -radius + offset, radius, radius + offset);
        mPath.arcTo(rectF, -30, -120);
        mPath.close();
        canvas.drawPath(mPath, mLinePaint);

//        canvas.drawCircle(0, 15, , mLinePaint2);

//        mPath.reset();
//        rectF.set(-mRadius1, -mRadius1, mRadius1, mRadius1);
//        mPath.addArc(rectF, -150, 120);
//        mLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.color_rader_default1));
//        canvas.drawPath(mPath, mLinePaint);
//        canvas.drawCircle(0, 15, mRadius1 + (15 >> 1), mLinePaint2);
//
//        mPath.reset();
//        rectF.set(-mRadius, -mRadius, mRadius, mRadius);
//        mPath.addArc(rectF, -150, 120);
//        mLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.color_rader_default));
//        canvas.drawPath(mPath, mLinePaint);
//        canvas.drawCircle(0, 15, mRadius + (15 >> 1), mLinePaint2);

        canvas.restore();
    }


    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
