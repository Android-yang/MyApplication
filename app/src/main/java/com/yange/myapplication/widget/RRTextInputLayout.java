package com.yange.myapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yange.myapplication.R;
import com.yange.myapplication.widget.util.ConvertUtils;

public class RRTextInputLayout extends LinearLayout {

    private FrameLayout mFrameLayout;//用于放EditText
    private AppCompatTextView mErrorView;
    private View mDividerView;
    private EditText mEditText;

    private int mEtDefColor;//EditText的当前颜色

    private boolean mErrorShown;//错误信息是否正在显示
    private boolean mErrorEnable = true;//是否可以显示Error,默认是可以的
    private boolean mErrorDividerEnable = false;//是否显示输入文案跟error之间的分割线，默认不显示
    private boolean mUpdateEditTextColor = true;//是否可以修改文案颜色， 默认是可以的
    private boolean mUpdateEditTextBg = true;//是否可以修改EditText的背景颜色，默认是可以的
    private int mErrorTypeFace;//错误字符类型
    private int mErrorGravity;//错误文案对齐方式，0-left, 1-center, 2-right, 默认是0
    private int mErrorMaxLines = Integer.MAX_VALUE;
    private float mErrorHeight = 0;
    private float mErrorPaddingStart = 0;//根据gravity决定，padding是paddingLeft或者paddingRight

    public RRTextInputLayout(Context context) {
        this(context, null);
    }

    public RRTextInputLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.style.rr_account_text_file_error);
    }

    public RRTextInputLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RRTextInputLayout);
        if (typedArray != null) {
            mErrorEnable = typedArray.getBoolean(R.styleable.RRTextInputLayout_errorEnable, true);
            mUpdateEditTextColor = typedArray.getBoolean(R.styleable.RRTextInputLayout_updateTextColor, true);
            mUpdateEditTextBg = typedArray.getBoolean(R.styleable.RRTextInputLayout_updateEditTextBg, true);
            mErrorTypeFace = typedArray.getResourceId(R.styleable.RRTextInputLayout_errorTypeFace, defStyleAttr);
            mErrorGravity = typedArray.getInt(R.styleable.RRTextInputLayout_errorGravity, 0);
            mErrorMaxLines = typedArray.getInt(R.styleable.RRTextInputLayout_errorMaxLines, Integer.MAX_VALUE);
            if (typedArray.hasValue(R.styleable.RRTextInputLayout_errorHeight)) {
                mErrorHeight = typedArray.getDimension(R.styleable.RRTextInputLayout_errorHeight, 0);
            }
            if (typedArray.hasValue(R.styleable.RRTextInputLayout_errorPaddingStart)) {
                mErrorPaddingStart = typedArray.getDimension(R.styleable.RRTextInputLayout_errorPaddingStart, 0);
            }
            mErrorDividerEnable = typedArray.getBoolean(R.styleable.RRTextInputLayout_errorDividerVisible, false);
        }
        typedArray.recycle();
        setOrientation(VERTICAL);
        mFrameLayout = new FrameLayout(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mFrameLayout, params);
        if (mErrorEnable) {
            setErrorView();
        }
    }

    private void setErrorView() {
        if (mErrorEnable) {
            setDivider();
            if (mErrorView == null) {
                mErrorView = new AppCompatTextView(getContext());
                TextViewCompat.setTextAppearance(mErrorView, mErrorTypeFace);
                int gravity = Gravity.LEFT;
                switch (mErrorGravity) {
                    case 0:
                        gravity = Gravity.LEFT;
                        break;
                    case 1:
                        gravity = Gravity.CENTER;
                        break;
                    case 2:
                        gravity = Gravity.RIGHT;
                        break;
                    default:
                        gravity = Gravity.LEFT;
                }
                mErrorView.setGravity(gravity | Gravity.CENTER_VERTICAL);
                if (mErrorPaddingStart != 0) {
                    if ((mErrorView.getGravity() & Gravity.LEFT) == Gravity.LEFT) {
                        mErrorView.setPadding((int) mErrorPaddingStart, 0, 0, 0);
                    } else if ((mErrorView.getGravity() & Gravity.RIGHT) == Gravity.RIGHT) {
                        mErrorView.setPadding(0, 0, (int) mErrorPaddingStart, 0);
                    }
                }
                mErrorView.setMaxLines(mErrorMaxLines);
            }
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (mErrorHeight != 0) {
                params.height = (int) mErrorHeight;
            }
            addView(mErrorView, params);
            mErrorView.setVisibility(GONE);
        }
    }

    private void setDivider() {
        if (mErrorDividerEnable) {
            if (mDividerView == null) {
                mDividerView = new View(getContext());
                mDividerView.setBackgroundResource(R.color.rr_text_error);
            }
            LayoutParams dividerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  (int) ConvertUtils.dpToPx(getContext(), 1));
            addView(mDividerView, dividerParams);
            mDividerView.setVisibility(GONE);
        }
    }

    public void setError(String error) {
        if (mErrorEnable) {
            if (TextUtils.isEmpty(error)) {
                clearError();
                return;
            }
            if (mErrorView == null) {
                setErrorView();
            }
            mErrorShown = true;
            mErrorView.setText(error);
            mErrorView.setVisibility(VISIBLE);
            if (mDividerView != null) {
                mDividerView.setVisibility(VISIBLE);
            }
            if (mUpdateEditTextBg) {
                updateEditTextBackground();
            }
            if (mUpdateEditTextColor) {
                updateEditTextColor();
            }
        }
    }

    public void clearError() {
        if (mErrorEnable) {
            if (mErrorView == null) {
                setErrorView();
            }
            mErrorShown = false;
            mErrorView.setText(null);
            mErrorView.setVisibility(GONE);
            if (mDividerView != null) {
                mDividerView.setVisibility(GONE);
            }
            if (mUpdateEditTextBg) {
                updateEditTextBackground();
            }
            if (mUpdateEditTextColor) {
                updateEditTextColor();
            }
            postInvalidate();
        }
    }

    private void updateEditTextColor() {
        if (mEditText == null) {
            return;
        }

        if (mEditText.getCurrentTextColor() != mErrorView.getCurrentTextColor()) {
            mEtDefColor = mEditText.getCurrentTextColor();
        }
        if (mErrorShown && mErrorView != null) {
            mEditText.setTextColor(mErrorView.getCurrentTextColor());
        } else {
            mEditText.setTextColor(mEtDefColor);
        }
    }

    private void updateEditTextBackground() {
        if (mEditText == null) {
            return;
        }

        Drawable editTextBackground = mEditText.getBackground();
        if (editTextBackground == null) {
            return;
        }

        if (mErrorShown && mErrorView != null) {
            // Set a color filter of the error color
            editTextBackground.setColorFilter(
                    new PorterDuffColorFilter(
                            mErrorView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
        } else {
            // Else reset the color filter and refresh the drawable state so that the
            // normal tint is used
            DrawableCompat.clearColorFilter(editTextBackground);
            mEditText.refreshDrawableState();
        }
    }

    @Override
    public void addView(View child, int index, final ViewGroup.LayoutParams params) {
        // Carry on adding the View...
        if (child instanceof EditText) {
            mFrameLayout.addView(child);
            setEditText((EditText) child);
        } else {
            super.addView(child, index, params);
        }
    }

    public EditText getmEditText() {
        return mEditText;
    }

    public void setmEditText(EditText mEditText) {
        this.mEditText = mEditText;
    }

    private void setEditText(EditText editText) {
        mEditText = editText;
    }


}