package com.cvnavi.logistics.i51ehang.app.widget.edittext;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.cvnavi.logistics.i51ehang.app.R;


public class EditTextWithDel extends EditText {

    private Drawable deleteUnFocused;
    private Drawable deleteFocused;
    private Context context;

//    public EditTextWithDel(Context context) {
//        super(context);
//    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        this.context = context;
        init();
    }

    private void init() {
        deleteUnFocused = context.getResources().getDrawable(R.drawable.delete_unfocused);
        deleteFocused = context.getResources().getDrawable(R.drawable.delete_focused);

        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });

        setDrawable();
    }

    //设置删除图片
    private void setDrawable() {
        if (length() < 1) setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        else setCompoundDrawablesWithIntrinsicBounds(null, null, deleteUnFocused, null);
    }

    // 处理删除事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (deleteUnFocused != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();

            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 150;
            if (rect.contains(eventX, eventY)) setText("");
        }

        return super.onTouchEvent(event);
    }

//    @Override
//    protected void finalize() throws Throwable {
//        super.finalize();
//    }

}
