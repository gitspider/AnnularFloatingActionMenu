package com.sunlight.annularfloatingmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by SunLight on 5/11/2017.
 *
 */

public class SubActionButton extends RoundIconButton {

    public SubActionButton(Context context, FrameLayout.LayoutParams layoutParams) {
        super(context);

        setLayoutParams(layoutParams);

        int radius = context.getResources().getDimensionPixelSize(R.dimen.sub_action_button_size);
        int pd = (int) (radius * 0.15);
        setRadius(radius);
        setIconPadding(pd, pd, pd, pd);
    }

    /**
     * A builder for {@link SubActionButton} in conventional Java Builder format
     */
    public static class Builder {

        private Context context;
        private FrameLayout.LayoutParams layoutParams;

        public Builder(Context context) {
            this.context = context;

            // Default SubActionButton settings
            int size = context.getResources().getDimensionPixelSize(R.dimen.sub_action_button_size);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size, Gravity.TOP | Gravity.START);
            setLayoutParams(params);
        }

        public Builder setLayoutParams(FrameLayout.LayoutParams params) {
            this.layoutParams = params;
            return this;
        }

        public SubActionButton build() {
            return new SubActionButton(context, layoutParams);
        }
    }
}
