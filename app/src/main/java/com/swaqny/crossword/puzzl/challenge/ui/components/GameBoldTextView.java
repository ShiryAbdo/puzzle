package com.swaqny.crossword.puzzl.challenge.ui.components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class GameBoldTextView extends TextView {

    private static Typeface typeface;

    public GameBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeFace(context);
    }

    public GameBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeFace(context);
    }

    public GameBoldTextView(Context context) {
        super(context);
        setTypeFace(context);
    }

    private void setTypeFace(Context context) {
        if (typeface == null)
            typeface = Typeface.createFromAsset(context.getAssets(), "fonts/gothic_bold.otf");
        this.setTypeface(typeface);
    }
}
