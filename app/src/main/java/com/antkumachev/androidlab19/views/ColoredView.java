package com.antkumachev.androidlab19.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public class ColoredView extends View {

    public ColoredView(Context context) {
        super(context);
    }

    public ColoredView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColoredView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setColor(@ColorInt int color) {
        setBackgroundColor(color);
    }
}
