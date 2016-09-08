package com.sola.github.solauiproject;

import android.view.View;

import com.sola.github.edgeloadingview.EdgeLoadingView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by 禄骥
 * 2016/9/7.
 */
@EActivity(R.layout.activity_edge)
public class EdgeActivity extends RxBaseActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    @ViewById
    EdgeLoadingView id_edge_loading, id_edge_loading_2,
            id_edge_loading_3, id_edge_loading_4,
            id_edge_loading_5, id_edge_loading_6,
            id_edge_loading_7;

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
    protected void onDestroy() {
        super.onDestroy();
        id_edge_loading.destroy();
        id_edge_loading_2.destroy();
        id_edge_loading_3.destroy();
        id_edge_loading_4.destroy();
        id_edge_loading_5.destroy();
        id_edge_loading_6.destroy();
        id_edge_loading_7.destroy();
    }

    @Override
    protected void doAfterViews() {
        id_edge_loading_2.setTriangleColors(new int[]{
                com.sola.github.edgeloadingview.R.color.color_edge_loading_1,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_2,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_3,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_4,
        });
        id_edge_loading_3.setTriangleColors(new int[]{
                com.sola.github.edgeloadingview.R.color.color_edge_loading_1,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_2,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_3,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_4,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_5,
        });
        id_edge_loading_4.setTriangleColors(new int[]{
                com.sola.github.edgeloadingview.R.color.color_edge_loading_1,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_2,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_3,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_4,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_5,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_6,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_7,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_8,
        });
        id_edge_loading_5.setTriangleColors(new int[]{
                com.sola.github.edgeloadingview.R.color.color_edge_loading_1,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_2,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_3,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_4,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_5,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_6,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_7,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_8,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_9,
        });
        id_edge_loading_6.setTriangleColors(new int[]{
                com.sola.github.edgeloadingview.R.color.color_edge_loading_1,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_2,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_3,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_4,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_5,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_6,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_7,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_8,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_9,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_10,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_11,
                com.sola.github.edgeloadingview.R.color.color_edge_loading_12,
        });
        id_edge_loading_7.setTriangleColors(
                new int[]{
                        com.sola.github.edgeloadingview.R.color.color_edge_loading_1,
                        com.sola.github.edgeloadingview.R.color.color_edge_loading_2,
                        com.sola.github.edgeloadingview.R.color.color_edge_loading_3,
                        com.sola.github.edgeloadingview.R.color.color_edge_loading_4,
                        com.sola.github.edgeloadingview.R.color.color_edge_loading_5,
                }
        );
    }

    // ===========================================================
    // Methods
    // ===========================================================

    @Click({
            R.id.id_btn_start,
            R.id.id_btn_pause,
            R.id.id_btn_end
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_start:
                id_edge_loading.start();
                id_edge_loading_2.start();
                id_edge_loading_3.start();
                id_edge_loading_4.start();
                id_edge_loading_5.start();
                id_edge_loading_6.start();
                id_edge_loading_7.start();
                break;
            case R.id.id_btn_pause:
                // 暂且未实现

                break;
            case R.id.id_btn_end:
                id_edge_loading.destroy();
                id_edge_loading_2.destroy();
                id_edge_loading_3.destroy();
                id_edge_loading_4.destroy();
                id_edge_loading_5.destroy();
                id_edge_loading_6.destroy();
                id_edge_loading_7.destroy();
                break;
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
