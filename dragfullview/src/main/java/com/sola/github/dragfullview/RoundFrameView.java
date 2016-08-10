package com.sola.github.dragfullview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.sola.github.dragfullview.delegate.IDragFullListener;
import com.sola.github.dragfullview.delegate.IEventCallBack;

/**
 * Created by 禄骥
 * 2016/8/8.
 */
public class RoundFrameView extends FrameLayout implements IDragFullListener {

    // ===========================================================
    // Constants
    // ===========================================================

//    private static final String TAG = "Sola_Drag";

    // ===========================================================
    // Fields
    // ===========================================================

    private boolean startDrawing = false;

    private final RectF mBounds = new RectF();

    private DragFullView dragView;

    private IEventCallBack callBack;

    // ===========================================================
    // Constructors
    // ===========================================================

    public RoundFrameView(Context context) {
        this(context, null);
    }

    public RoundFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundFrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public void setCallBack(IEventCallBack callBack) {
        this.callBack = callBack;
    }

    private void init() {
        dragView = new DragFullView(getContext());
        dragView.setListener(this);
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    /**
     * 消费事件
     *
     * @param event 事件
     * @return 返回True，事件会阻断在这一层，返回false，事件会继续分发下去
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!startDrawing)
            return super.onTouchEvent(event);
        else
            return dragView.onTouchEvent(event);
    }

    /**
     * 拦截操作
     *
     * @param ev 事件
     * @return 返回True 表示不允许事件继续向子View传递，false表示不拦截
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return startDrawing;
    }

    /**
     * 分发事件操作  onTouchEvent 和  onInterceptTouchEvent 都是在这个方法中被调用
     *
     * @param ev 传递事件
     * @return 返回 false 会停止派发此次事件，返回true会继续派发事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mBounds.set(left, top, right, bottom);
        }
    }

    @Override
    public void onDragFinish(boolean isOutOfCircle) {
        if (callBack != null)
            if (isOutOfCircle) {
                callBack.destroy();
            } else
                callBack.resume();
        stopDrawing();
    }


    // ===========================================================
    // Methods
    // ===========================================================

    public void startDrawing(MotionEvent event, int originalX, int originalY, IEventCallBack callBack) {
        setCallBack(callBack);
        this.startDrawing = event != null && event.getAction() == MotionEvent.ACTION_DOWN;
        if (startDrawing) {
            WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
            mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            mParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            if (Build.VERSION.SDK_INT >= 19)
                mParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            mParams.format = PixelFormat.TRANSPARENT;
            dragView.start(originalX, originalY);
            getWindowManage().addView(dragView, mParams);
        } else {
            getWindowManage().removeViewImmediate(dragView);
        }
    }

    public void stopDrawing() {
        startDrawing = false;
        getWindowManage().removeViewImmediate(dragView);
    }

    private WindowManager getWindowManage() {
        return (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
