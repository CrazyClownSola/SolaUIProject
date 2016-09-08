package com.sola.github.solauiproject.tools;

import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * bundle的创建工厂类
 * <p/>
 * Created by 禄骥
 * 2016/5/4.
 */
public class BundleFactory {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Bundle mBundle;

    private static BundleFactory instance;

    // ===========================================================
    // Constructors
    // ===========================================================

    public BundleFactory() {
        if (mBundle == null)
            mBundle = new Bundle();
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public static BundleFactory getInstance() {
        if (instance == null)
            instance = new BundleFactory();
        return instance;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    public Bundle build() {
        return mBundle;
    }

    public BundleFactory putString(String key, String value) {
        mBundle.putString(key, value);
        return this;
    }

    @SuppressWarnings("unused")
    public BundleFactory putBoolean(String key, Boolean value) {
        mBundle.putBoolean(key, value);
        return this;
    }

    @SuppressWarnings("unused")
    public BundleFactory putBundle(@Nullable String key, Bundle bundle) {
        if (key == null || key.isEmpty())
            mBundle.putAll(bundle);
        else
            mBundle.putBundle(key, bundle);
        return this;
    }

    @SuppressWarnings("unused")
    public BundleFactory putByte(String key, byte... value) {
        if (value != null && value.length == 1)
            mBundle.putByte(key, value[0]);
        else
            mBundle.putByteArray(key, value);
        return this;
    }

    @SuppressWarnings("unused")
    public BundleFactory putFloat(String key, float... value) {
        if (value != null && value.length == 1)
            mBundle.putFloat(key, value[0]);
        else
            mBundle.putFloatArray(key, value);
        return this;
    }

    @SuppressWarnings("unused")
    public BundleFactory putInt(String key, int... value) {
        if (value != null && value.length == 1)
            mBundle.putInt(key, value[0]);
        else
            mBundle.putIntArray(key, value);
        return this;
    }

    @SuppressWarnings("unused")
    public BundleFactory putSerializable(String key, Serializable value) {
        mBundle.putSerializable(key, value);
        return this;
    }

    @SuppressWarnings("unused")
    public BundleFactory putChar(String key, char... value) {
        if (value != null && value.length == 1)
            mBundle.putChar(key, value[0]);
        else
            mBundle.putCharArray(key, value);
        return this;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
