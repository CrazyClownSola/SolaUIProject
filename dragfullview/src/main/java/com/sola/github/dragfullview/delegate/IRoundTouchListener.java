package com.sola.github.dragfullview.delegate;

import android.view.MotionEvent;

/**
 * Created by 禄骥
 * 2016/8/8.
 */
public interface IRoundTouchListener {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

//    void viewTouch(MotionEvent event, int originalX, int originalY, int type, IEventCallBack callBack);

    void onViewTouch(MotionEvent event, int originalX, int originalY, IEventCallBack callBack);

//    void fullViewFinish(boolean isOutOfSize);
}
