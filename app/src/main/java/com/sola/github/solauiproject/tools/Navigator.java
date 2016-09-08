package com.sola.github.solauiproject.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sola.github.solauiproject.R;

/**
 * 导航工具类，做各种Activity切换的工具
 * <p/>
 * Created by 禄骥
 * 2016/4/6.
 */
public class Navigator {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private static Navigator instance;

    // ===========================================================
    // Constructors
    // ===========================================================

    public Navigator() {
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public static Navigator getInstance() {
        if (instance == null) instance = new Navigator();
        return instance;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    @SuppressWarnings("unused")
    public void switchActivity(final Context context, Class<?> cls) {
        switchActivity(context, cls, null);
    }

    public void switchActivity(final Context context, Class<?> cls, Bundle bundle) {
        switchActivityForResult(context, -1, cls, bundle);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void switchActivityForResult(final Context context, int requestCode, Class<?> cls) {
        switchActivityForResult(context, requestCode, cls, null);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void switchActivity(final Context context, Intent intent) {
        switchActivityForResult(context, -1, intent);
    }

    public void switchActivityForResult(final Context context, int requestCode, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        if (bundle != null && !bundle.isEmpty())
            intent.putExtras(bundle);
        switchActivityForResult(context, requestCode, intent);
    }

    public void switchActivityForResult(final Context context, int requestCode, Intent intent) {
        switchActivityForResult(context, requestCode, intent, R.anim.fade_in_left, R.anim.activity_back);
    }

    public void switchActivityForResult(final Context context, int requestCode, Intent intent, int targetInAnim, int currentOutAnim) {
        if (context instanceof Activity && requestCode != -1)
            ((Activity) context).startActivityForResult(intent, requestCode);
        else
            context.startActivity(intent);
        if (context instanceof Activity)
            // 第一个参数是目标Activity进入时的动画，第二个参数是当前Activity退出时的动画
            ((Activity) context).overridePendingTransition(targetInAnim, currentOutAnim);
    }

    @SuppressWarnings("unused")
    public void switchActivityForResult(final Context context, int requestCode, Class<?> cls, Bundle bundle, int targetInAnim, int currentOutAnim) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        if (bundle != null && !bundle.isEmpty())
            intent.putExtras(bundle);
        if (context instanceof Activity && requestCode != -1)
            ((Activity) context).startActivityForResult(intent, requestCode);
        else
            context.startActivity(intent);
        if (context instanceof Activity)
            // 第一个参数是目标Activity进入时的动画，第二个参数是当前Activity退出时的动画
            ((Activity) context).overridePendingTransition(targetInAnim, currentOutAnim);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
