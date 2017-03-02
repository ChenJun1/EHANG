package com.cvnavi.logistics.i51ehang.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.cvnavi.logistics.i51ehang.app.callback.manager.HttpDelWithListener;
import com.cvnavi.logistics.i51ehang.app.ui.ISkipActivity;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import okhttp3.Call;
import okhttp3.MediaType;

public abstract class BaseFragment extends Fragment implements ISkipActivity {

	/**
	 * skip to @param(cls)，and call @param(aty's) finish() method
	 */
	@Override
	public void skipActivity(Activity aty, Class<?> cls) {
		showActivity(aty, cls);
		aty.finish();
	}

	/**
	 * skip to @param(cls)，and call @param(aty's) finish() method
	 */
	@Override
	public void skipActivity(Activity aty, Intent it) {
		showActivity(aty, it);
		aty.finish();
	}

	/**
	 * skip to @param(cls)，and call @param(aty's) finish() method
	 */
	@Override
	public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
		showActivity(aty, cls, extras);
		aty.finish();
	}

	/**
	 * show to @param(cls)，but can't finish activity
	 */
	@Override
	public void showActivity(Activity aty, Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(aty, cls);
		aty.startActivity(intent);
	}

	/**
	 * show to @param(cls)，but can't finish activity
	 */
	@Override
	public void showActivity(Activity aty, Intent it) {
		aty.startActivity(it);
	}

	/**
	 * show to @param(cls)，but can't finish activity
	 */
	@Override
	public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
		Intent intent = new Intent();
		intent.putExtras(extras);
		intent.setClass(aty, cls);
		aty.startActivity(intent);
	}

	private SweetAlertDialog sweetAlertDialog = null;

	public void showLoading() {
		if (sweetAlertDialog == null) {
			sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
		}
		sweetAlertDialog.show();
	}

	public void dissLoading() {
		if (sweetAlertDialog != null) {
			sweetAlertDialog.dismiss();
		}
	}

	/**
	 * 基类自带的网络请求
	 * @param url
	 * @param dataRequestBase
	 * @param listener
	 */
	public void httpPostString(String url, Object dataRequestBase, final HttpDelWithListener listener) {
		showLoading();
		OkHttpUtils
				.postString()
				.url(url)
				.content(new Gson().toJson(dataRequestBase))
				.mediaType(MediaType.parse("application/json; charset=utf-8"))
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e, int id) {
						dissLoading();
						if (listener != null) {
							listener.httpError(call, e, id);
						}
					}

					@Override
					public void onResponse(String response, int id) {
						dissLoading();
						if (listener != null) {
							listener.httpSucc(response, id);
						}
					}
				});
	}


}
