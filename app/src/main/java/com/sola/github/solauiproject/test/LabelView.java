package com.sola.github.solauiproject.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sola.github.solauiproject.R;

import java.lang.reflect.Field;

/**
 * Created by slove
 * 2016/10/8.
 */
public class LabelView extends TextView {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Paint mPaint;
    private Path clipPath, lightPath, darkPath;

    // ===========================================================
    // Constructors
    // ===========================================================

    public LabelView(Context context) {
        super(context);
        m_TextPaint = this.getPaint();
    }

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_TextPaint = this.getPaint();
    }

    public LabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        m_TextPaint = this.getPaint();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    @Override
    public void setText(CharSequence text, BufferType type) {

        super.setText(text, type);
    }


    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    int mRadius = 15, leftRadius = 10, rightOffset = 30;
    RectF rectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.color_default_red));

        if (clipPath == null)
            clipPath = new Path();
        if (clipPath.isEmpty()) {
            clipPath.moveTo(0, getHeight() / 2);
//            clipPath.addRect(mRadius, 0, getWidth() - 20, getHeight(), Path.Direction.CCW);

            rectF.set(0, 0, 2 * leftRadius, 2 * leftRadius);
//            rectF.set(-(getHeight() / 3 - mRadius), 0, mRadius + getHeight() / 3, getHeight());
            clipPath.arcTo(rectF, 180, 90);
            clipPath.lineTo(getWidth() - rightOffset, 0);
//            clipPath.lineTo(getWidth(), getHeight() / 2);
//            clipPath.lineTo(getWidth() - 20, getHeight());
            rectF.set(getWidth() - rightOffset - mRadius, 0, getWidth() - rightOffset + mRadius, 2 * mRadius);
            clipPath.arcTo(rectF, -90, 60);
//            clipPath.
            rectF.set(getWidth() - 2 * mRadius, getHeight() / 2 - mRadius, getWidth(), getHeight() / 2 + mRadius);
            clipPath.arcTo(rectF, -30, 60);
//            clipPath.lineTo((float) (getWidth() - (mRadius - mRadius * Math.sqrt(3.0f))), getHeight() / 2 - 6);
            rectF.set(getWidth() - rightOffset - mRadius, getHeight() - 2 * mRadius, getWidth() - rightOffset + mRadius, getHeight());
            clipPath.arcTo(rectF, 30, 60);

            rectF.set(0, getHeight() - 2 * leftRadius, 2 * leftRadius, getHeight());
            clipPath.arcTo(rectF, 90, 90);
//            clipPath.addCircle();
//            clipPath.quadTo(getWidth() - 30 + 12, -8, getWidth(), getHeight() / 2);
//            clipPath.quadTo(getWidth() - 30 + 12, getHeight(), getWidth() - 30, getHeight() );
            clipPath.close();
        }
//        canvas.save();
        canvas.clipPath(clipPath);
//        canvas.restore();

        if (lightPath == null)
            lightPath = new Path();
        if (lightPath.isEmpty()) {
            lightPath.moveTo(0, 0);
            lightPath.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CCW);
            lightPath.close();
        }
        canvas.drawPath(lightPath, createPaint(R.color.color_light_red));
        if (darkPath == null)
            darkPath = new Path();
        if (darkPath.isEmpty()) {
            darkPath.moveTo(getWidth(), -5);
            darkPath.lineTo(getWidth(), getHeight());
            darkPath.lineTo(0, getHeight());
            darkPath.close();
        }
        canvas.drawPath(darkPath, createPaint(R.color.color_dark_red));

        canvas.drawCircle(getWidth() - rightOffset, getHeight() / 2, 10, createPaint(R.color.color_text_white));
        canvas.drawCircle(getWidth() - rightOffset, getHeight() / 2, 10, createPaint2(R.color.color_dark_bound_red));

        setTextColorUseReflection(ContextCompat.getColor(getContext(), R.color.color_dark_bound_red));
//        setTextColor(ContextCompat.getColor(getContext(), R.color.color_dark_bound_red));
        m_TextPaint.setStyle(Paint.Style.STROKE); // 描边种类
        m_TextPaint.setStrokeWidth(5); // 描边宽度
        m_TextPaint.setFakeBoldText(true); // 外层text采用粗体
        super.onDraw(canvas);
        setTextColorUseReflection(ContextCompat.getColor(getContext(), R.color.color_text_white));
        m_TextPaint.setStyle(Paint.Style.FILL);
        m_TextPaint.setFakeBoldText(false);
        super.onDraw(canvas);
//        canvas.drawPath(clipPath, createPaint(R.color.color_default_green));

//        tp.setColor(Color.BLACK);
//        tp.setStyle(Paint.Style.FILL);
//        this.setTextColor(Color.BLACK);
//        super.onDraw(canvas);
    }

    TextPaint m_TextPaint;

    /**
     * 使用反射的方法进行字体颜色的设置
     *
     * @param color 传入的色值
     */
    private void setTextColorUseReflection(int color) {
        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        m_TextPaint.setColor(color);
    }
    // ===========================================================
    // Methods
    // ===========================================================

    private Paint createPaint(int color) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setStrokeWidth(0.5f);
        }
        mPaint.setPathEffect(new CornerPathEffect(100));
        mPaint.setColor(ContextCompat.getColor(getContext(), color));
        return mPaint;
    }

    private Paint mPaint2;

    private Paint createPaint2(int color) {
        if (mPaint2 == null) {
            mPaint2 = new Paint();
            mPaint2.setAntiAlias(true);
            mPaint2.setStyle(Paint.Style.STROKE);
            mPaint2.setStrokeWidth(2);
        }
        mPaint2.setTextSize(36);
        mPaint2.setColor(ContextCompat.getColor(getContext(), color));
        return mPaint2;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
