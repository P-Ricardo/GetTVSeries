package com.example.gettvseries.View.Search;

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

import com.example.gettvseries.R;

public class SearchView extends FrameLayout {

    private AppCompatImageButton btnClear;

    public interface OnTextChangeListener {

        void onSuggestion(String suggestion);

        void onSubmitted(String submitted);

        void onCleared();
    }

    private AppCompatEditText txtSearch;
    private String txtHint;

    @DrawableRes
    private int drawableRes;

    @ColorInt
    private int drawableColor;

    private OnTextChangeListener listener;


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

        txtHint = typedArray.getString(R.styleable.SearchView_hint);

        drawableRes = typedArray.getResourceId(R.styleable.SearchView_drawable, R.drawable.ic_search_24dp);

        drawableColor = typedArray.getColor(R.styleable.SearchView_drawable_color, Color.parseColor("#ffffff"));

        typedArray.recycle();

        initView();

    }

    private void initView() {

        LayoutInflater.from(getContext()).inflate(R.layout.component_search_view, this, true);

        txtSearch = findViewById(R.id.et_search);
        btnClear = findViewById(R.id.ib_clear);

        if (txtHint != null) {
            txtSearch.setHint(txtHint);
        }

        Drawable drawableClear = getResources().getDrawable(R.drawable.ic_close_white_24dp);
        drawableClear.setColorFilter(drawableColor, PorterDuff.Mode.SRC_IN);
        btnClear.setImageDrawable(drawableClear);


        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSearch.setText("");
            }
        });

        setDrawable(drawableRes);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (listener != null) {
                    if (s.toString().isEmpty()) {
                        btnClear.setVisibility(GONE);
                        listener.onCleared();
                    } else {
                        btnClear.setVisibility(VISIBLE);
                        listener.onSuggestion(s.toString().trim());
                    }
                }
            }
        });

        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindowToken(), 0);
                    txtSearch.clearFocus();

                    listener.onSubmitted(v.getText().toString());
                    return true;
                }
                return false;
            }
        });

    }

    public OnTextChangeListener getOnTextChangeListener() {
        return listener;
    }

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.listener = onTextChangeListener;
    }

    public String getHint() {
        return txtHint;
    }

    public void setHint(String hint) {
        this.txtHint = hint;
        txtSearch.setHint(hint);
    }

    @DrawableRes
    public int getDrawable() {
        return drawableRes;
    }

    public void setDrawable(@DrawableRes int drawable) {
        this.drawableRes = drawable;
        txtSearch.setCompoundDrawablesRelativeWithIntrinsicBounds(
                coloredDrawable(drawable, drawableColor), null, null, null);
    }

    @ColorInt
    public int getDrawableColor() {
        return drawableColor;
    }

    public void setDrawableColor(@ColorInt int drawableColor) {
        this.drawableColor = drawableColor;
        setDrawable(drawableRes);
    }

    private Drawable coloredDrawable(@DrawableRes int drawable, @ColorInt int color) {
        Drawable d = getResources().getDrawable(drawable);
        d.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        return d;
    }
}
