package com.example.benjo.personal_finance_app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;


public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFloatAsText(float mFloat) {
        if(mFloat < 0) {
            setTextColor(Color.RED);
        } else {
            setTextColor(Color.GREEN);
        }
        setText(String.valueOf(mFloat));
    }

    public void setWelcomeMessage(String username) {
        String welcomeMessage = "Hi " + username + " here is a summary of your current financial situation";
        setText(welcomeMessage);
    }

    @Override
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(@Nullable Drawable start, @Nullable Drawable top, @Nullable Drawable end, @Nullable Drawable bottom) {
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom);
    }
}
