package com.sunlight.annularfloatingmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.FrameLayout;

/**
 * Created by SunLight on 5/11/2017.
 */

public abstract class ActionButton extends FrameLayout {

    public ActionButton(@NonNull Context context) {
        super(context);
    }

    public void setDrawable() {

    }

    public void setButtonColor(int color) {
        setBackgroundColor(color);
    }

    protected void setBackgroundResource(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        }
        else {
            setBackgroundDrawable(drawable);
        }
    }
}
