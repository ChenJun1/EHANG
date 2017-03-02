package com.cvnavi.logistics.i51ehang.app.widget.dialog.custom;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cvnavi.logistics.i51ehang.app.R;


/**
 * @类说明:自定义提示Toast
 * @作者:xiechengfa
 * @创建时间:2014-11-26 下午5:27:55
 */
public class CustomNormalToast extends Toast {
	public CustomNormalToast(Context context) {
		super(context);
	}

	public static CustomNormalToast makeText(Context context,
											 CharSequence text, int duration) {
		CustomNormalToast result = new CustomNormalToast(context);

		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.layout_toast_normal, null);
		TextView tv = (TextView) v.findViewById(R.id.tips_msg);
		tv.setText(text);

		result.setView(v);
		// setGravity方法用于设置位置，此处为垂直居中
		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		result.setDuration(duration);

		return result;
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
