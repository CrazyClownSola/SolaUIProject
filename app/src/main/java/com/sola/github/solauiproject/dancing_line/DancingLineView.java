package com.sola.github.solauiproject.dancing_line;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sola.github.solauiproject.tools.ETest;

/**
 * Created by zhangluji
 * 2017/2/16.
 */
public class DancingLineView extends View {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    // ===========================================================
    // Constructors
    // ===========================================================
    public DancingLineView(Context context) {
        super(context);
    }

    public DancingLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DancingLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        ETest.DEFAULT
//        init(ETest.DEFAULT);
    }
    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        ViewConfiguration.
//        ViewConfiguration.sc
        return super.onTouchEvent(event);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    public void init(@ETest int test) {
        switch (test) {
            case ETest.DEFAULT:
                break;
            case ETest.FIRST:
                break;
            case ETest.SECOND:
                break;
            default:
                break;
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
