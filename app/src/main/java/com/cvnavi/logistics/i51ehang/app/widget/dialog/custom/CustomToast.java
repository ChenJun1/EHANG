package com.cvnavi.logistics.i51ehang.app.widget.dialog.custom;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cvnavi.logistics.i51ehang.app.R;


/**
 * @类说明:自定义提示Toast
 * @作者:xiechengfa
 * @创建时间:2014-11-26 下午5:27:55
 */
public class CustomToast extends Toast {
	public CustomToast(Context context) {
		super(context);
	}

	public static CustomToast makeText(Context context, CharSequence text,
									   int duration) {
		CustomToast result = new CustomToast(context);

		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.layout_toast, null);
		TextView tv = (TextView) v.findViewById(R.id.tips_msg);
		tv.setText(text);

		result.setView(v);
		// setGravity方法用于设置位置，此处为垂直居中
		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		result.setDuration(duration);

		return result;
	}

	public void setIcon(int iconResId) {
		if (getView() == null) {
			throw new RuntimeException(
					"This Toast was not created with Toast.makeText()");
		}

		ImageView iv = (ImageView) getView().findViewById(R.id.tips_icon);
		if (iv == null) {
			throw new RuntimeException(
					"This Toast was not created with Toast.makeText()");
		}

		if (iconResId == 0) {
			iv.setVisibility(View.GONE);
		} else {
			iv.setVisibility(View.VISIBLE);
			iv.setImageResource(iconResId);
		}
	}

	@Override
	public void setText(CharSequence s) {
		if (getView() == null) {
			throw new RuntimeException(
					"This Toast was not created with Toast.makeText()");
		}

		TextView tv = (TextView) getView().findViewById(R.id.tips_msg);
		if (tv == null) {
			throw new RuntimeException(
					"This Toast was not created with Toast.makeText()");
		}

		tv.setText(s);
	}
}
