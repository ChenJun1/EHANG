package com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.trans.ChoiceDriverListViewAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarSchedulingDriver;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetDriverListRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetDriverListResponse;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.transportation.ChoiceDriverLinstener;
import com.cvnavi.logistics.i51ehang.app.callback.manager.DriverChoiceCallBackManager;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
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
*创建时间：2017/1/17 下午2:08
*描述：选择司机
************************************************************************************/

public class ChoiceDriverActivity extends BaseActivity implements ChoiceDriverLinstener {
    private static String TAG = ChoiceDriverActivity.class.getName();

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.choice_driver_lv)
    PullToRefreshListView choiceDriverLv;

    private ListView actualListView;
    private ChoiceDriverListViewAdapter adapter;
    private List<mCarSchedulingDriver> dataList;

    private Context mContext;
    private boolean isRefresh=false;
    DataRequestBase dataRequestBase;
    private int pageInt=1;
    private SweetAlertDialog lodingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_driver);
        ButterKnife.bind(this);
        dataRequestBase=new DataRequestBase();
        mContext = this;
        titleTv.setText(Utils.getResourcesString(R.string.choice_driver));
        lodingDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        dataList = new ArrayList<>();
        adapter = new ChoiceDriverListViewAdapter(dataList, ChoiceDriverActivity.this,this);
        choiceDriverLv = (PullToRefreshListView) findViewById(R.id.choice_driver_lv);
        choiceDriverLv.setMode(PullToRefreshBase.Mode.BOTH);
        choiceDriverLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(mContext, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                onRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                dataRequestBase.Page=pageInt;
                loadData();
            }
        });
        actualListView = choiceDriverLv.getRefreshableView();
        actualListView.setAdapter(adapter);
        onRefresh();
    }
    private void onRefresh(){
        isRefresh=true;
        pageInt=1;
        dataRequestBase.Page=pageInt;
        loadData();
    }

    private void loadData() {
        GetDriverListRequest getDriverListRequest = new GetDriverListRequest();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.DataValue = getDriverListRequest; //JsonUtils.toJsonData(getDriverListRequest);
        LogUtil.d("-->>" + GsonUtil.newInstance().toJson(dataRequestBase).toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, TMSService.GetDriverList_Request_Url,
                GsonUtil.newInstance().toJson(dataRequestBase),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        GetDriverListResponse responseBase = JsonUtils.parseData(response.toString(), GetDriverListResponse.class);
                        Message msg = Message.obtain();
                        if (responseBase.Success) {
                            msg.obj = responseBase.DataValue;
                            msg.what = Constants.REQUEST_SUCC;
                            myHandler.sendMessage(msg);
                        } else {
                            msg.obj = responseBase.ErrorText;
                            msg.what = Constants.REQUEST_FAIL;
                            myHandler.sendMessage(msg);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Message msg = Message.obtain();
                        msg.obj = error.getMessage();
                        msg.what = Constants.REQUEST_FAIL;
                        myHandler.sendMessage(msg);
                    }
                });
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            choiceDriverLv.onRefreshComplete();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        if(isRefresh){
                            dataList.clear();
                            isRefresh=false;
                        }
                        List<mCarSchedulingDriver> list = (List<mCarSchedulingDriver>) msg.obj;
                        dataList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }

                    break;
                case Constants.REQUEST_FAIL:
                    if (String.valueOf(msg.obj).equals("null") || TextUtils.isEmpty(String.valueOf(msg.obj))) {
                        DialogUtils.showFailToast(getResources().getString(R.string.search_fail));
                    } else {
                        DialogUtils.showFailToast(String.valueOf(msg.obj));
                    }
                    break;
            }
        }
    };

    @OnClick(R.id.back_llayout)
    public void onClick() {
        ChoiceDriverActivity.this.finish();
    }

    @Override
    public void onClick(int position) {
        mCarSchedulingDriver driverInfo = dataList.get(position - 1);
        DriverChoiceCallBackManager.newInstance().fire(driverInfo);
        ChoiceDriverActivity.this.finish();
    }
}
