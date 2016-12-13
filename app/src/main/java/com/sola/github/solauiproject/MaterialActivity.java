package com.sola.github.solauiproject;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Outline;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.sola.github.tools.RxBaseActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * Created by slove
 * 2016/12/9.
 */
@EActivity(R.layout.activity_material)
public class MaterialActivity extends RxBaseActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    @ViewById
    TextView id_text_block, id_image_palette;

    @ViewById
    MultiAutoCompleteTextView id_auto_complete_text;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void doAfterViews() {

        // 一种昂贵的裁剪布局
        id_text_block.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(0, 0, 100, 100);
            }
        });
        id_text_block.setClipToOutline(true);

        buildAutoEditText();

    }


    // ===========================================================
    // Methods
    // ===========================================================

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Click
    // 一个对于剪裁
    public void id_btn_change_block() {
        // get the center for the clipping circle
//        int cx = (id_text_block.getLeft() + id_text_block.getRight()) / 2;
//        int cy = (id_text_block.getTop() + id_text_block.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(id_text_block.getWidth(), id_text_block.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim =
                ViewAnimationUtils.createCircularReveal(id_text_block, 0, 0, 100, 2 * finalRadius);
//        ViewAnimationUtils

        // make the view visible and start the animation
        id_text_block.setVisibility(View.VISIBLE);
        anim.start();
    }

    @UiThread
    public void onUiRefresh() {

    }

    int i = 0;

    @Click
    public void id_btn_change_palette() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_default_fuzzy);
        Palette.Builder builder = Palette.from(bitmap);
        int color = ContextCompat.getColor(mContext.get(), android.R.color.transparent);
        switch (i % 6) {
            case 0:
                // 充满活力的
                color = builder.generate().getVibrantColor(color);
                break;
            case 1:
                // 充满活力的亮
                color = builder.generate().getLightVibrantColor(color);
                break;
            case 2:
                // 充满活力的黑
                color = builder.generate().getDarkVibrantColor(color);
                break;
            case 3:
                // 柔和的
                color = builder.generate().getMutedColor(color);
                break;
            case 4:
                // 柔和的亮色
                color = builder.generate().getLightMutedColor(color);
                break;
            case 5:
                // 柔和的黑
                color = builder.generate().getDarkMutedColor(color);
                break;
        }
        if (++i >= 6) {
            i = 0;
        }
        id_image_palette.setBackgroundColor(color);
    }

    /**
     * 配置自动补全的EditText所需要的各种参数
     */
    private void buildAutoEditText() {
        String[] countries = getResources().getStringArray(R.array.string_array_countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext.get(),
                R.layout.adapter_item_auto_edit_test,
                R.id.id_text_detail, countries);
        id_auto_complete_text.setAdapter(adapter);
        id_auto_complete_text.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
