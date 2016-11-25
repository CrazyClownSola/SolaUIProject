package com.sola.github.solauiproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sola.github.tools.RxBaseActivity;
import com.sola.github.wow.WoWUtil;

import net.qiujuer.genius.blur.StackBlur;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by slove
 * 2016/11/24.
 */
@EActivity(R.layout.acitivity_fuzzy_image)
public class FuzzyImageActivity extends RxBaseActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    @ViewById
    ViewGroup id_include_base;

    @ViewById
    ImageView id_image_test;

    private Bitmap mBitmap;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏显示
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void doAfterViews() {
        id_text_title.setText("ceshi");
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int screenW = WoWUtil.getScreenWidth(mContext.get());
        int screenH = WoWUtil.getScreenHeight(mContext.get());
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_default_fuzzy);
//        mBitmap = getResizedBitmap(mBitmap, screenW * 3, screenH * 3);
//        mBitmap = blur(mBitmap, 25);
        ViewGroup.LayoutParams ly = id_image_test.getLayoutParams();
        ly.width = screenH + 200;
        ly.height = screenH + 200;
        id_image_test.setLayoutParams(ly);
//        id_image_test.setImageBitmap(mBitmap);
        setBitmap(mBitmap);
//        id_include_base.setBackground(new BitmapDrawable(mBitmap));
//        id_include_base.set
    }

    // ===========================================================
    // Methods
    // ===========================================================
    public void setBitmap(Bitmap bitmap) {
        int screenH = WoWUtil.getScreenHeight(mContext.get());

        mBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mBitmap);
        canvas.translate(-id_image_test.getLeft(), -id_image_test.getTop());
        canvas.scale(1, 1);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        // Call Blur
        blur();
    }

    protected Bitmap getNewBitmap() {
        return mBitmap.copy(mBitmap.getConfig(), true);
    }

    protected void blur() {
        if (mBitmap == null)
            return;

        Bitmap bitmap = getNewBitmap();
        int radius = 70;

        long startTime = System.currentTimeMillis();
        final Bitmap ret = blur(bitmap, radius);
        show(ret, System.currentTimeMillis() - startTime);
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                Bitmap bitmap = getNewBitmap();
//                int radius = 70;
//
//                long startTime = System.currentTimeMillis();
//                final Bitmap ret = blur(bitmap, radius);
//                show(ret, System.currentTimeMillis() - startTime);
//            }
//        };
//        thread.start();
    }

    protected Bitmap blur(Bitmap bitmap, int radius) {
        return StackBlur.blurNatively(bitmap, radius, true);
    }

    protected void show(final Bitmap bitmap, final long time) {
        runOnUiThread(() -> id_image_test.setImageBitmap(bitmap));
    }

    private Bitmap getResizedBitmap(Bitmap bm, float newWidth, float newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = 1, scaleHeight = 1;
        if (newWidth != -1) {
            scaleWidth = newWidth / width;
            if (newHeight != -1) {
                scaleHeight = newHeight / height;
            } else {
                scaleHeight = newWidth / width;
            }
        } else {
            if (newHeight != -1) {
                scaleWidth = scaleHeight = newHeight / height;
            }
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

//    private Bitmap blur(Bitmap original, float radius) {
//        Bitmap bitmap = Bitmap.createBitmap(
//                original.getWidth(), original.getHeight(),
//                Bitmap.Config.ARGB_8888);
//
//
//        RenderScript rs = RenderScript.create(this);
//
//        Allocation allocIn = Allocation.createFromBitmap(rs, original);
//        Allocation allocOut = Allocation.createFromBitmap(rs, bitmap);
//
//        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(
//                rs, allocIn.getElement());
//        blur.setInput(allocIn);
//        blur.setRadius(radius);
//        blur.forEach(allocOut);
//
//        allocOut.copyTo(bitmap);
//        rs.destroy();
//        return bitmap;
//    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
