package com.example.babartrihapsoro.getpluspos.view.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by randiwaranugraha on 8/4/16.
 */
public abstract class BaseRichView extends FrameLayout {

    private Unbinder unbinder;

    public BaseRichView(Context context) {
        super(context);
        init(context, null);
    }

    public BaseRichView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseRichView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseRichView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public abstract int getLayout();

    protected void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(getLayout(), this, true);
        unbinder = ButterKnife.bind(this);
    }

    public void onDestroyView() {
        unbinder.unbind();
    }

}