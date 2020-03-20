package com.yange.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * author : yangke on 2020/3/19
 * weChat : ACE_5200125
 * email  : 211yangke@sina.com
 * desc   :
 */
public class CleanCountEditText extends FrameLayout {
    private EditText mEditText;
    private int mMaxTextCount = 10;
    private boolean mIsAdded = false; //onMeasure会调用多次，防止重复添加视图

    public CleanCountEditText(Context context) { super(context); }
    public CleanCountEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        configData(context, attrs);
    }

    public CleanCountEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        configData(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CleanCountEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        configData(context, attrs);
    }

    private void configData(Context context, @Nullable AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CleanCountEditText);
        mMaxTextCount = typedArray.getInt(R.styleable.CleanCountEditText_txt_size, 10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mIsAdded) {
            updateView();
        }
        mIsAdded = true;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    void updateView() {
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.yb_edit_clean_count, null);
        removeAllViews();
        LayoutParams params = new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        mEditText = layout.findViewById(R.id.yb_apply_yb_edit);
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxTextCount)});

        final View cleanBtn = layout.findViewById(R.id.yb_apply_yb_clean);
        final TextView countTv = layout.findViewById(R.id.yb_apply_yb_txt_count);
        countTv.setText("0/" + mMaxTextCount);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    cleanBtn.setVisibility(View.GONE);
                    countTv.setText("0/" + mMaxTextCount);
                } else {
                    cleanBtn.setVisibility(View.VISIBLE);
                    countTv.setText(s.length() + "/" + mMaxTextCount);
                }
            }
        });

        cleanBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
                countTv.setText("0/" + mMaxTextCount);
                cleanBtn.setVisibility(View.GONE);
            }
        });

        addView(layout, params);
    }

    public String getText() {
        return mEditText == null ? "" : mEditText.getText().toString();
    }

}
