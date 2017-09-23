package com.swaqny.crossword.puzzl.challenge.ui.components;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.swaqny.crossword.puzzl.challenge.R;
import com.swaqny.crossword.puzzl.challenge.utils.DeviceUtils;


public class GameButton extends Button {

    private static Typeface typeface;

    public GameButton(Context context) {
        super(context);
        setTypeFace(context);
        setAppearance();
    }

    public GameButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeFace(context);
        setAppearance();
    }

    public GameButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeFace(context);
        setAppearance();
    }

    private void setAppearance() {
        this.setBackgroundResource(R.drawable.curved_button);
    }

    private void setTypeFace(Context context) {
        if (typeface == null)
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/gothic.ttf");
        this.setTypeface(typeface);
        this.setTextColor(Color.WHITE);
        if (DeviceUtils.isTablet(context))
            this.setTextSize(15);
        else if (!DeviceUtils.isSmallScreen(context))
            this.setTextSize(20);
        else
            this.setTextSize(15);
    }
}
