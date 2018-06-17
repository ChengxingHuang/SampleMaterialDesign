package com.huang.samplematerialdesign;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by huang on 2018/6/7.
 */

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration{
    private Drawable mDivider;
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public RecyclerViewItemDecoration(Context context){
        final TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
            mDivider = typedArray.getDrawable(0);
            typedArray.recycle();
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final int left = child.getLeft();
            final int right = child.getRight();
            final int top = child.getBottom() - mDivider.getIntrinsicHeight();
            final int bottom = child.getBottom();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}