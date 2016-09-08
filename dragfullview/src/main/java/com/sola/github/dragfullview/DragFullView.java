package com.sola.github.dragfullview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sola.github.dragfullview.delegate.IDragFullListener;

/**
 * Created by 禄骥
 * 2016/8/8.
 * 全屏性质的拖动界面
 */
public class DragFullView extends View {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final int mMaxDistance = 120;

    // ===========================================================
    // Fields
    // ===========================================================

    private Paint mPaint;

    private int currentX, currentY, originalX, originalY;
    private float alpha, distance;

    private int radiusMin = 8, radiusMax = 16, currentRadius, destroyType;

    //    private float waveRadiusOne, waveRadiusTwo;
    private float starRange;

    private final RectF mBounds = new RectF();

    private boolean isOutOfCircle;

    private Path quadPath = new Path(), mStarPath = new Path();

    private PointF point1 = new PointF(), point2 = new PointF(), point3 = new PointF(),
            point4 = new PointF(), point5 = new PointF(), point6 = new PointF(),
            pointP = new PointF(), pointP1 = new PointF();

    private AnimatorSet destroyAnim;

    private boolean isAnimRunning = false, isDrawStar = true;

    private IDragFullListener listener;

    private Drawable destroy_One, destroy_Two, destroy_Three;

    // ===========================================================
    // Constructors
    // ===========================================================

    public DragFullView(Context context) {
        this(context, null);
    }

    public DragFullView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragFullView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    @SuppressWarnings("UnusedDeclaration")
    public void setDestroyType(int destroyType) {
        this.destroyType = destroyType;
        invalidate();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setListener(IDragFullListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setStarRange(float starRange) {
        this.starRange = starRange;
        invalidate();
    }

//    @SuppressWarnings("UnusedDeclaration")
//    public void setWaveRadiusTwo(float waveRadiusTwo) {
//        this.waveRadiusTwo = waveRadiusTwo;
//        invalidate();
//    }
//
//    @SuppressWarnings("UnusedDeclaration")
//    public void setWaveRadiusOne(float waveRadiusOne) {
//        this.waveRadiusOne = waveRadiusOne;
//        invalidate();
//    }

    @SuppressWarnings("UnusedDeclaration")
    public void setRadiusMax(int radiusMax) {
        this.radiusMax = radiusMax;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setRadiusMin(int radiusMin) {
        this.radiusMin = radiusMin;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setDrawStar(boolean drawStar) {
        isDrawStar = drawStar;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isAnimRunning) {
            calculatePath();
            drawStar(canvas);
            return;
        }
        if (isOutOfCircle) {
            canvas.drawCircle(currentX, currentY, radiusMax, createPaint());
        } else {
            if (isInnerCircle()) {
                canvas.drawCircle(originalX + radiusMax, originalY + radiusMax, radiusMax, createPaint());
                return;
            }
            calculate();
            canvas.save();
            canvas.translate(originalX + radiusMax, originalY + radiusMax);
            canvas.rotate(90 - alpha);
            quadPath.reset();
            quadPath.moveTo(point1.x, point1.y);
            quadPath.quadTo(point5.x, point5.y, point2.x, point2.y);
            quadPath.lineTo(point4.x, point4.y);
            quadPath.quadTo(point6.x, point6.y, point3.x, point3.y);
            quadPath.close();
            canvas.drawPath(quadPath, createPaint());
            canvas.drawCircle(distance, 0, radiusMax, createPaint());
            canvas.drawCircle(0, 0, currentRadius, createPaint());
            canvas.restore();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (isAnimRunning)
                    break;
                if (currentX != -1 && currentY != -1 && !isMoveAccess(event)) {
                    break;
                }
                currentX = (int) event.getRawX();
                currentY = (int) event.getRawY();
                isOutOfCircle = outOfCircle();
                if (isOutOfRound()) {
                    triggerStar();
                    break;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (isAnimRunning)
                    break;
                currentX = (int) event.getRawX();
                currentY = (int) event.getRawY();
                isOutOfCircle = outOfCircle();
                if (isOutOfCircle) {
                    triggerStar();
                } else {
                    if (listener != null)
                        listener.onDragFinish(false);
                    currentX = -1;
                    currentY = -1;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mBounds.set(left, top, right, bottom);
        }
    }


    // ===========================================================
    // Methods
    // ===========================================================

    private void init() {
        // 设定背景透明
        setBackgroundCompat(ContextCompat.getDrawable(getContext(), R.drawable.shape_transplate_drag_bg));
    }

    public void start(int originalX, int originalY) {
        currentRadius = radiusMax;
        this.originalX = originalX;
        this.originalY = originalY;
        mStarPath.reset();
    }

    private void drawStar(Canvas canvas) {
        if (isDrawStar) {
            canvas.save();
            canvas.translate(currentX, currentY);
            canvas.scale(1, -1);
            if (destroyType == 0) {
                if (destroy_One == null)
                    destroy_One = ContextCompat.getDrawable(getContext(), R.drawable.icon_drag_destroy_1);
                destroy_One.setBounds(-50, -50, 50, 50);
                destroy_One.draw(canvas);
            } else if (destroyType == 1) {
                if (destroy_Two == null)
                    destroy_Two = ContextCompat.getDrawable(getContext(), R.drawable.icon_drag_destroy_2);
                destroy_Two.setBounds(-50, -50, 50, 50);
                destroy_Two.draw(canvas);
            } else if (destroyType == 2) {
                if (destroy_Three == null)
                    destroy_Three = ContextCompat.getDrawable(getContext(), R.drawable.icon_drag_destroy_3);
                destroy_Three.setBounds(-50, -50, 50, 50);
                destroy_Three.draw(canvas);
            }
            canvas.restore();
//            canvas.save();
//            canvas.translate(currentX, currentY);
//            canvas.scale(1, -1);
//            if (mStarPath.isEmpty()) {
//                mStarPath.moveTo(pointP.x, pointP.y);
//            } else
//                mStarPath.lineTo(pointP.x, pointP.y);
//            // 三条辅助线
////            canvas.drawCircle(0, 0, radiusMax, createPaint(getColor(R.color.cpb_blue)));
////            canvas.drawCircle(pointP1.x, pointP1.y, 10, createStroke(getColor(R.color.color_default_white)));
////            canvas.drawLine(pointP1.x, pointP1.y, pointP.x, pointP.y, createPaint(getColor(R.color.color_default_black)));
//            canvas.drawPath(mStarPath, createPoint());
//            canvas.restore();
        }
    }

    private void calculate() {
        currentRadius = (int) (radiusMax - (distance / mMaxDistance * (radiusMax - radiusMin)));
        if (currentRadius < radiusMin)
            currentRadius = radiusMin;
        int deltaRadius = Math.abs(radiusMax - currentRadius);
        float deltaS = (float) (pow2(deltaRadius) / (Math.pow(distance, 2) - pow2(deltaRadius)));
        alpha = calculateCanvasRotate(currentY - originalY, currentX - originalX);

        float cacheR1x = (float) Math.sqrt((pow2(currentRadius) * deltaS) / (1 + deltaS));
        float cacheR1y = (float) Math.sqrt(pow2(currentRadius) / (1 + deltaS));
        float cacheR2x = (float) Math.sqrt((pow2(radiusMax) * deltaS) / (1 + deltaS));
        float cacheR2y = (float) Math.sqrt(pow2(radiusMax) / (1 + deltaS));

        // 一组贝塞尔曲线的点
        point1.set(-cacheR1x, cacheR1y);
//        point1.set(originalX, originalY);
        point2.set(distance - cacheR2x, cacheR2y);
        // 另一组贝塞尔曲线的点
        point3.set(-cacheR1x, -cacheR1y);
        point4.set(distance - cacheR2x, -cacheR2y);

        point5.set((point1.x + point4.x) / 2, (point1.y + point4.y) / 2);
        point6.set((point2.x + point3.x) / 2, (point2.y + point3.y) / 2);
    }

    private void calculatePath() {
        float beta = -2 * starRange / 3;
        float quadrant = (float) Math.cos(Math.toRadians(beta));
        float x2, y2, x1, y1;
        float k = (float) Math.sqrt(pow2(radiusMax) / (1 + pow2((float) Math.tan(Math.toRadians(beta)))));
        x1 = (float) (10 * Math.cos(Math.toRadians(starRange)));
        y1 = (float) (10 * Math.sin(Math.toRadians(starRange)));
        if (quadrant == 0)
            if (beta % 360 == 0) {
                k = radiusMax;
            } else {
                k = -radiusMax;
            }
        if (quadrant >= 0) { // 说明在一、四象限
            x2 = x1 + k;
            y2 = (float) (y1 + k * Math.tan(Math.toRadians(beta)));
        } else {
            x2 = x1 - k;
            y2 = (float) (y1 - k * Math.tan(Math.toRadians(beta)));
        }
        pointP1.set(x1, y1);
        pointP.set(x2, y2);
//        Log.d("Sola_Full", "calculatePath: al[" + starRange + "]b[" + beta + "]\n" +
//                "x2[" + x2 + "]y2[" + y2 + "]x1[" + x1 + "]y1[" + y1 + "]\nqu[" + quadrant + "] ");
    }

    // 判断当前位移和历史位移之间的差值是否满足需要变更的条件
    private boolean isMoveAccess(MotionEvent event) {
        return Math.abs(event.getX()) > 0 || Math.abs(event.getY()) > 0;
    }


    private double pow2(float value) {
        return Math.pow(value, 2);
    }

    private int calculateCanvasRotate(int deltaX, int deltaY) {
        double retRotate = Math.atan2(deltaY, deltaX);
        retRotate = (float) (180 * retRotate / Math.PI); // 转换成角度
        return (int) retRotate;
    }

    // 是否超过旋转圆弧
    private boolean outOfCircle() {
        distance = (float) Math.sqrt(Math.pow(currentX - originalX, 2) + Math.pow(currentY - originalY, 2));
        return distance >= mMaxDistance;
    }

    /**
     * @return 判断是否越界
     */
    public boolean isOutOfRound() {
        return currentX < 0 || currentX > mBounds.right || currentY < 0 || currentY > mBounds.bottom;
    }

    private void triggerStar() {
        if (!isDrawStar) {
            isAnimRunning = false;
            destroyAnim = null;
            if (listener != null)
                listener.onDragFinish(true);
            starRange = 0;
        } else {
            doReleasePicChange();
//            if (destroyAnim == null) {
//                destroyAnim = new AnimatorSet();
//                ObjectAnimator aRange = ObjectAnimator.ofFloat(this, "starRange", 0, 1080);
//                destroyAnim.play(aRange);
//                destroyAnim.setDuration(700);
//                destroyAnim.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        isAnimRunning = false;
//                        destroyAnim = null;
//                        if (listener != null)
//                            listener.onDragFinish(true);
//                        starRange = 0;
//                    }
//                });
//            }
//            isAnimRunning = true;
//            destroyAnim.start();
        }
    }

    private void doReleasePicChange() {
        if (destroyAnim == null) {
            destroyAnim = new AnimatorSet();
            ObjectAnimator repeatAnim = ObjectAnimator.ofInt(this, "destroyType", 0, 1, 2, 3);
            destroyAnim.play(repeatAnim);
            destroyAnim.setDuration(700);
            destroyAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isAnimRunning = false;
                    destroyAnim = null;
                    if (listener != null)
                        listener.onDragFinish(true);
                    starRange = 0;
                }
            });
        }
        isAnimRunning = true;
        destroyAnim.start();
    }

    public boolean isInnerCircle() {
        return distance <= radiusMax + radiusMax;
    }


    protected int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }

//    private Paint pointPaint;

    private Paint createPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
        }
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaint.setColor(getColor(com.sola.github.dragfullview.R.color.color_drag_red));
        return mPaint;
    }

//    private Paint createPoint() {
//        if (pointPaint == null) {
//            pointPaint = new Paint();
//            pointPaint.setAntiAlias(true);
//            pointPaint.setStrokeCap(Paint.Cap.ROUND); // 设置画笔为圈形
//        }
//        pointPaint.setStyle(Paint.Style.STROKE);
//        pointPaint.setStrokeWidth(2);
//        pointPaint.setColor(getColor(R.color.color_drag_red));
//        return pointPaint;
//    }


    @SuppressWarnings("UnusedDeclaration")
    private Paint createPaint(int color) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
        }
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        return mPaint;
    }

    @SuppressWarnings("UnusedDeclaration")
    private Paint createStroke(int color) {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        mPaint.setColor(color);
        return mPaint;
    }

    /**
     * Set the View's background. Masks the API changes made in Jelly Bean.
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
