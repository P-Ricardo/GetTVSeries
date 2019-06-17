package com.example.searchlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;

public class SearchView extends FrameLayout {

    private AppCompatImageButton mClearButton;

    public interface OnTextChangeListener{

        void onSuggestion(String suggestion);
        void onSubmitted(String submitted);
        void onCleared();
    }


    private AppCompatEditText mSearchView;
    private String mHint;

    @DrawableRes
    private int mDrawable;

    @ColorInt
    private int mDrawableColor;

    private OnTextChangeListener mListener;


    public SearchView(@NonNull Context context) {
        super(context);
        initView();
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchView);
        mHint = typedArray.getString(R.styleable.SearchView_hint);
        mDrawable = typedArray.getResourceId(R.styleable.SearchView_drawable,R.drawable.ic_search_black_24dp);
        mDrawableColor = typedArray.getColor(R.styleable.SearchView_drawable_color, Color.parseColor("#ffffff"));
        typedArray.recycle();
        initView();


    }

    private void initView() {

        LayoutInflater.from(getContext()).inflate(R.layout.component_search_view, this, true);

        mSearchView = findViewById(R.id.et_search);
        mClearButton = findViewById(R.id.ib_clear);

        if(mHint!=null){
            mSearchView.setHint(mHint);
        }

        Drawable drawableClear =  getResources().getDrawable(R.drawable.ic_close_black_24dp);
        drawableClear.setColorFilter(mDrawableColor, PorterDuff.Mode.SRC_IN);
        mClearButton.setImageDrawable(drawableClear);


        mClearButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchView.setText("");
            }
        });

       setDrawable(mDrawable);

        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mListener!=null){
                    if(s.toString().isEmpty()){
                        mClearButton.setVisibility(GONE);
                        mListener.onCleared();
                    }else{
                        mClearButton.setVisibility(VISIBLE);
                        mListener.onSuggestion(s.toString().trim());
                    }
                }




            }
        });

        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindowToken(), 0);
                    mSearchView.clearFocus();

                    mListener.onSubmitted(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }


    public OnTextChangeListener getOnTextChangeListener() {
        return mListener;
    }

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.mListener = onTextChangeListener;
    }

    public String getHint() {
        return mHint;
    }

    public void setHint(String hint) {
        this.mHint = hint;
        mSearchView.setHint(hint);
    }

    @DrawableRes
    public int getDrawable() {
        return mDrawable;
    }

    public void setDrawable(@DrawableRes int drawable) {
        this.mDrawable = drawable;
        mSearchView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                coloredDrawable(drawable, mDrawableColor),null,null,null);
    }

    @ColorInt
    public int getDrawableColor() {
        return mDrawableColor;
    }

    public void setDrawableColor(@ColorInt int drawableColor) {
        this.mDrawableColor = drawableColor;
        setDrawable(mDrawable);
    }

    private Drawable coloredDrawable(@DrawableRes int drawable, @ColorInt int color) {
        Drawable d =  getResources().getDrawable(drawable);
        d.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return d;
    }
}
