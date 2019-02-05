package com.example.babartrihapsoro.getpluspos.view.main.cekpoint.popupcekpoint;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.babartrihapsoro.getpluspos.R;
import com.example.babartrihapsoro.getpluspos.base.BaseActivity;

public class PopUpCekPoint extends BaseActivity {

    private PopupWindow popupWindow;
    LinearLayout layout;

    @Override
    public int getLayout() {
        return R.layout.popup_point;
    }

    @Override
    public void setup() {
        initPopUp();
    }

    void initPopUp() {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));
    }
}
