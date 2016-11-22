package com.sola.github.solauiproject;

import com.sola.github.scan_view.ScanWifiView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by slove
 * 2016/10/25.
 */
@EActivity(R.layout.activity_scan_view)
public class ScanViewActivity extends RxBaseActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    @ViewById
    ScanWifiView id_scan_wifi_view;

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
    protected void doAfterViews() {
        id_scan_wifi_view.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        id_scan_wifi_view.destroy();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
