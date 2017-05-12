package com.sunlight.annularfloatingmenu;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by SunLight on 5/11/2017.
 *
 */

@SuppressWarnings("JavaDoc")
public class FloatingActionButton extends RoundButton {

    protected  static final int POSITION_CUSTOM = 0;
    public static final int POSITION_TOP_CENTER = 1;
    public static final int POSITION_TOP_RIGHT = 2;
    public static final int POSITION_RIGHT_CENTER = 3;
    public static final int POSITION_BOTTOM_RIGHT = 4;
    public static final int POSITION_BOTTOM_CENTER = 5;
    public static final int POSITION_BOTTOM_LEFT = 6;
    public static final int POSITION_LEFT_CENTER = 7;
    public static final int POSITION_TOP_LEFT = 8;

    private static final int DEFAULT_RADIUS = 50;

    private View contentView;

    private boolean systemOverlay;



    /**
     * Constructor that takes parameters collected using {@link FloatingActionMenu.Builder}
     * @param context a reference to the current context
     * @param layoutParams
     * @param backgroundDrawable
     * @param position
     * @param contentView
     * @param contentParams
     */
    public FloatingActionButton(Context context, ViewGroup.LayoutParams layoutParams,
                                Drawable backgroundDrawable, int position, View contentView,
                                LinearLayout.LayoutParams contentParams,
                                boolean systemOverlay) {
        super(context);
        this.systemOverlay = systemOverlay;

        if(!systemOverlay && !(context instanceof Activity)) {
            throw new RuntimeException("Given context must be an instance of Activity, "
                    +"since this FAB is not a systemOverlay.");
        }

        setPosition(position, layoutParams);

        setRadius(DEFAULT_RADIUS);
        setText("A");

        /*if(contentView != null) {
            setContentView(contentView, contentParams);
        }*/
        setClickable(true);

        attach(layoutParams);
    }

    /**
     * Sets the position of the button by calculating its Gravity from the position parameter
     * @param position one of 8 specified positions.
     * @param layoutParams should be either FrameLayout.LayoutParams or WindowManager.LayoutParams
     */
    public void setPosition(int position, ViewGroup.LayoutParams layoutParams) {

        boolean setDefaultMargin = false;

        int gravity;
        switch (position) {
            case POSITION_TOP_CENTER:
                gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                break;
            case POSITION_TOP_RIGHT:
                gravity = Gravity.TOP | Gravity.END;
                break;
            case POSITION_RIGHT_CENTER:
                gravity = Gravity.END | Gravity.CENTER_VERTICAL;
                break;
            case POSITION_BOTTOM_CENTER:
                gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                break;
            case POSITION_BOTTOM_LEFT:
                gravity = Gravity.BOTTOM | Gravity.START;
                break;
            case POSITION_LEFT_CENTER:
                gravity = Gravity.START | Gravity.CENTER_VERTICAL;
                break;
            case POSITION_TOP_LEFT:
                gravity = Gravity.TOP | Gravity.START;
                break;
            case POSITION_BOTTOM_RIGHT:
            default:
                setDefaultMargin = true;
                gravity = Gravity.BOTTOM | Gravity.END;
                break;
        }
        if(!systemOverlay) {
            try {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) layoutParams;
                lp.gravity = gravity;
                setLayoutParams(lp);
            } catch (ClassCastException e) {
                throw new ClassCastException("layoutParams must be an instance of " +
                        "FrameLayout.LayoutParams, since this FAB is not a systemOverlay");
            }
        }
        else {
            try {
                WindowManager.LayoutParams lp = (WindowManager.LayoutParams) layoutParams;
                lp.gravity = gravity;
                if(setDefaultMargin) {
                    int margin =  getContext().getResources().getDimensionPixelSize(R.dimen.action_button_margin);
                    lp.x = margin;
                    lp.y = margin;
                }
                setLayoutParams(lp);
            } catch(ClassCastException e) {
                throw new ClassCastException("layoutParams must be an instance of " +
                        "WindowManager.LayoutParams, since this FAB is a systemOverlay");
            }
        }
    }

    public void setPosition(int xPos, int yPos, ViewGroup.LayoutParams layoutParams) {
        // TODO: implement custom position here
    }

    /**
     * Sets a content view that will be displayed inside this FloatingActionButton.
     * @param contentView
     */
    public void setContentView(View contentView, LinearLayout.LayoutParams contentParams) {
        this.contentView = contentView;
        LinearLayout.LayoutParams params;
        if(contentParams == null ){
            params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            final int margin = getResources().getDimensionPixelSize(R.dimen.action_button_content_margin);
            params.setMargins(margin, margin, margin, margin);
        }
        else {
            params = contentParams;
        }
        params.gravity = Gravity.CENTER;

        contentView.setClickable(false);
        this.addView(contentView, params);
    }

    /**
     * Attaches it to the content view with specified LayoutParams.
     * @param layoutParams
     */
    public void attach(ViewGroup.LayoutParams layoutParams) {
        if(systemOverlay) {
            try {
                getWindowManager().addView(this, layoutParams);
            }
            catch(SecurityException e) {
                throw new SecurityException("Your application must have SYSTEM_ALERT_WINDOW " +
                        "permission to create a system window.");
            }
        }
        else {
            ((ViewGroup) getActivityContentView()).addView(this, layoutParams);
        }
    }

    /**
     * Detaches it from the container view.
     */
    public void detach() {
        if(systemOverlay) {
            getWindowManager().removeView(this);
        }
        else {
            ((ViewGroup) getActivityContentView()).removeView(this);
        }
    }

    /**
     * Finds and returns the main content view from the Activity context.
     * @return the main content view
     */
    public View getActivityContentView() {
        try {
            return ((Activity) getContext()).getWindow().getDecorView().findViewById(android.R.id.content);
        }
        catch(ClassCastException e) {
            throw new ClassCastException("Please provide an Activity context for this FloatingActionButton.");
        }
    }

    public WindowManager getWindowManager() {
        return (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    }

    private void setBackgroundResource(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        }
        else {
            setBackgroundDrawable(drawable);
        }
    }

    /**
     * A builder for {@link FloatingActionButton} in conventional Java Builder format
     */
    public static class Builder {

        private Context context;
        private ViewGroup.LayoutParams layoutParams;
        private Drawable backgroundDrawable;
        private int position;
        private View contentView;
        private LayoutParams contentParams;
        private boolean systemOverlay;

        public Builder(Context context) {
            this.context = context;

            // Default FloatingActionButton settings
            int size = context.getResources().getDimensionPixelSize(R.dimen.action_button_size);
            int margin = context.getResources().getDimensionPixelSize(R.dimen.action_button_margin);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size, Gravity.BOTTOM | Gravity.END);
            layoutParams.setMargins(margin, margin, margin, margin);
            setLayoutParams(layoutParams);
            setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT);
            setSystemOverlay(false);
        }

        public Builder setLayoutParams(ViewGroup.LayoutParams params) {
            this.layoutParams = params;
            return this;
        }

        public Builder setBackgroundDrawable(Drawable backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder setBackgroundDrawable(int drawableId) {
            return setBackgroundDrawable(context.getResources().getDrawable(drawableId));
        }

        public Builder setPosition(int position) {
            this.position = position;
            return this;
        }

        public Builder setContentView(View contentView) {
            return setContentView(contentView, null);
        }

        public Builder setContentView(View contentView, LayoutParams contentParams) {
            this.contentView = contentView;
            this.contentParams = contentParams;
            return this;
        }

        public Builder setSystemOverlay(boolean systemOverlay) {
            this.systemOverlay = systemOverlay;
            return this;
        }

        public FloatingActionButton build() {
            return new FloatingActionButton(context,
                    layoutParams,
                    backgroundDrawable,
                    position,
                    contentView,
                    contentParams,
                    systemOverlay);
        }

        public static WindowManager.LayoutParams getDefaultSystemWindowParams(Context context) {
            int size = context.getResources().getDimensionPixelSize(R.dimen.action_button_size);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    size,
                    size,
                    WindowManager.LayoutParams.TYPE_SYSTEM_ALERT, // z-ordering
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
            params.format = PixelFormat.RGBA_8888;
            params.gravity = Gravity.TOP | Gravity.START;
            return params;
        }
    }
}
