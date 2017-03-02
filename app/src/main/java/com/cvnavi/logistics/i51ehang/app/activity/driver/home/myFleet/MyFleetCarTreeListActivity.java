package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.myFleet.MyFleetCarTreeAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetCarListResponse;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.config.LPSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.listview.MyListView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午10:17
*描述：我的车队选择机构
************************************************************************************/

public class MyFleetCarTreeListActivity extends BaseActivity implements MyOnClickItemListener {
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.check_tv)
    TextView checkTv;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.custom_ll)
    LinearLayout customLl;
    @BindView(R.id.list_view)
    MyListView listView;
    private SweetAlertDialog loading;
    private MyFleetCarTreeAdapter adapter;
    private Context context;


    public static void startActivity(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, MyFleetCarTreeListActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fleet_car_tree);
        ButterKnife.bind(this);
        context = this;
        titltTv.setText("选择机构");
        getCarList();
    }


    private void getCarList() {
        loading = new SweetAlertDialog(MyFleetCarTreeListActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        loading.show();
        DataRequestBase requestBase = new DataRequestBase();
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        VolleyManager.newInstance().PostJsonRequest(LPSService.GetCarList_TAG, LPSService.GetCarList_Request_Url, GsonUtil.newInstance().toJson(requestBase), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                MLog.json(jsonObject.toString());
                Message msg = Message.obtain();
                List<mCarInfo> carInfos = null;
                GetCarListResponse carListResponse = JsonUtils.parseData(jsonObject.toString(), GetCarListResponse.class);
                if (carListResponse.Success) {
                    carInfos = carListResponse.DataValue;
                    msg.what = Constants.REQUEST_SUCC;
                    msg.obj = carInfos;
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                }
                mHandler.sendMessage(msg);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                mHandler.sendMessage(msg);
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loading.dismiss();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    List<mCarInfo> carInfos = (List<mCarInfo>) msg.obj;
                    adapter = new MyFleetCarTreeAdapter(carInfos, MyFleetCarTreeListActivity.this, MyFleetCarTreeListActivity.this);
                    listView.setAdapter(adapter);
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(MyFleetCarTreeListActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void myOnClickItem(int position, Object data) {
        EventBus.getDefault().post((mCarInfo) data);
        finish();
    }

    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }
}
