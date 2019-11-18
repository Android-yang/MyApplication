package com.yange.myapplication.widget.recycleview.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.yange.myapplication.R;

public class SimpleRefreshHeaderView extends FrameLayout implements IRefreshHeader {
    private ProgressBar mProgressView;
    private AnimationDrawable frameAnim;

    public SimpleRefreshHeaderView(Context context) {
        this(context, null);
    }

    public SimpleRefreshHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.pull_to_refresh_header, this);
        mProgressView = findViewById(R.id.refresh_progress);
        frameAnim = (AnimationDrawable) mProgressView.getBackground();
    }

    @Override
    public void reset() {
        if (frameAnim.isRunning())
            frameAnim.stop();
    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {
        frameAnim.start();
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, State state) {

    }

    @Override
    public void complete() {
        frameAnim.stop();
    }

}
