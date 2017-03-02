package com.cvnavi.logistics.i51ehang.app.utils;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/6.
 */
public class SetViewValueUtil {
    public static void setTextViewValue(TextView textView, Object object) {
        if (object == null) {
            textView.setText("");
            return;
        }

        String value = String.valueOf(object);
        if (TextUtils.isEmpty(value)) {
            textView.setText("--");
            return;
        }
        textView.setText(value);
    }

    public static void setTextViewIntValue(TextView textView, Object object) {
        if (object == null) {
            textView.setText("0");
            return;
        }

        String value = String.valueOf(object);
        if (TextUtils.isEmpty(value)) {
            textView.setText("0");
            return;
        }
        textView.setText(value);
    }

    public static void setTextViewValue(TextView textView, Object object, String postfix) {
        if (object == null) {
            textView.setText("" + postfix);
            return;
        }

        String value = String.valueOf(object);
        if (TextUtils.isEmpty(value)) {
            textView.setText("--" + postfix);
            return;
        }
        textView.setText(value + postfix);
    }

    public static void setEditTextValue(EditText editText, Object object) {
        if (object == null) {
            editText.setText("");
            return;
        }

        String value = String.valueOf(object);
        if (TextUtils.isEmpty(value)) {
            editText.setText("--");
            return;
        }
        editText.setText(value);
    }

    public static void  setTextViewStr(TextView textView,Object object){
        if(object==null){
            textView.setText("无");
            return;
        }
        String value = String.valueOf(object);
        if(TextUtils.isEmpty(value)){
            textView.setText("无");
            return;
        }
        textView.setText(value);
    }


}
