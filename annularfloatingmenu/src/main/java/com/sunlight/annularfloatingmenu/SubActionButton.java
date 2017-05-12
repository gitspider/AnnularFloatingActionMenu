package com.sunlight.annularfloatingmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by SunLight on 5/11/2017.
 *
 */

public class SubActionButton extends RoundButton {

    public SubActionButton(Context context, FrameLayout.LayoutParams layoutParams, Drawable backgroundDrawable, View contentView, FrameLayout.LayoutParams contentParams) {
        super(context);

        setLayoutParams(layoutParams);
        setRadius(50);

        if(contentView != null) {
            setContentView(contentView, contentParams);
        }
        setClickable(true);
    }

    /**
     * Sets a content view with custom LayoutParams that will be displayed inside this SubActionButton.
     * @param contentView
     * @param params
     */
    public void setContentView(View contentView, FrameLayout.LayoutParams params) {
        if(params == null) {
            params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            final int margin = getResources().getDimensionPixelSize(R.dimen.sub_action_button_content_margin);
            params.setMargins(margin, margin, margin, margin);
        }

        contentView.setClickable(false);
        this.addView(contentView, params);
    }

    /**
     * Sets a content view with default LayoutParams
     * @param contentView
     */
    public void setContentView(View contentView) {
        setContentView(contentView, null);
    }

    /**
     * A builder for {@link SubActionButton} in conventional Java Builder format
     */
    public static class Builder {

        private Context context;
        private FrameLayout.LayoutParams layoutParams;
        private Drawable backgroundDrawable;
        private View contentView;
        private FrameLayout.LayoutParams contentParams;

        public Builder(Context context) {
            this.context = context;

            // Default SubActionButton settings
            int size = context.getResources().getDimensionPixelSize(R.dimen.sub_action_button_size);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size, Gravity.TOP | Gravity.LEFT);
            setLayoutParams(params);
        }

        public Builder setLayoutParams(FrameLayout.LayoutParams params) {
            this.layoutParams = params;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder setContentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder setContentView(View contentView, FrameLayout.LayoutParams contentParams) {
            this.contentView = contentView;
            this.contentParams = contentParams;
            return this;
        }

        public SubActionButton build() {
            return new SubActionButton(context,
                    layoutParams,
                    backgroundDrawable,
                    contentView,
                    contentParams);
        }
    }
}
