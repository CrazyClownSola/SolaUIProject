package com.sola.github.solauiproject.tools;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.sola.github.solauiproject.tools.ETest.DEFAULT;
import static com.sola.github.solauiproject.tools.ETest.FIRST;
import static com.sola.github.solauiproject.tools.ETest.SECOND;

/**
 * Created by zhangluji
 * 2017/2/16.
 */
@IntDef({DEFAULT, FIRST, SECOND})
@Retention(RetentionPolicy.SOURCE)
public @interface ETest {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    int DEFAULT = 0;

    int FIRST = 1;

    int SECOND = 2;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================


    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
