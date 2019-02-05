package com.example.babartrihapsoro.getpluspos.view.main;


import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.example.babartrihapsoro.getpluspos.R;

import me.dm7.barcodescanner.core.DisplayUtils;
import me.dm7.barcodescanner.core.IViewFinder;

/**
 * Created by aldo on 4/5/17.
 */

public class SquareViewFinder extends BaseRichView implements IViewFinder {

    private Rect framingRect;

    public SquareViewFinder(Context context) {
        super(context);
    }

    public SquareViewFinder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareViewFinder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        framingRect = createFramingRect();
    }

    private Rect createFramingRect() {
        Point viewResolution = new Point(this.getWidth(), this.getHeight());
        int orientation = DisplayUtils.getScreenOrientation(this.getContext());
        int width;
        int height;

        if (orientation != 1) {
            height = (int) ((float) this.getHeight() * 0.625F);
            width = height;
        } else {
            width = (int) ((float) this.getWidth() * 0.625F);
            height = width;
        }

        if (width > this.getWidth()) {
            width = this.getWidth() - 50;
        }

        if (height > this.getHeight()) {
            height = this.getHeight() - 50;
        }

        int leftOffset = (viewResolution.x - width) / 2;
        int topOffset = (viewResolution.y - height) / 2;
        return new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
    }

    @Override
    public void setupViewFinder() {
        framingRect = createFramingRect();
        invalidate();
    }

    @Override
    public Rect getFramingRect() {
        return framingRect;
    }

    @Override
    public int getLayout() {
        return R.layout.square_view_finder;
    }
}