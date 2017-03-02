package com.cvnavi.logistics.i51ehang.app.extra;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;

/**
 * Created by JohnnyYuan on 2016/6/24.
 *
 */
public class MyCountDownTimer extends CountDownTimer {

    private View view;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, View view) {
        super(millisInFuture, countDownInterval);
        this.view = view;
    }

    @Override
    public void onTick(long millisUntilFinished) {
//        view.setClickable(false);
//        textView.setTextSize(13);
        if (view instanceof TextView) {
            ((TextView) view).setText(millisUntilFinished / 1000 + "s");
        } else if (view instanceof Button) {
            ((Button) view).setText(millisUntilFinished / 1000 + "s");
        }
    }

    @Override
    public void onFinish() {
        view.setClickable(true);
//        textView.setTextSize(13);
        if (view instanceof TextView) {
            ((TextView) view).setText(R.string.get_verify_code_name);
        } else if (view instanceof Button) {
            ((Button) view).setText(R.string.get_verify_code_name);
        }
    }

}
