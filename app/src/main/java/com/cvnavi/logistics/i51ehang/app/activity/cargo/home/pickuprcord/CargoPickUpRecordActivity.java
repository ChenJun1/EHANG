package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.pickuprcord;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.pickuprecord.CargoPickupRecordAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.GetTakeManifestsDataValue;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.request.GetTakeManifestsRequest;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.response.GetTakeManifestsResponse;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarShift;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.callback.cargo.PIckupCallback;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.trans.OnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.MyInputMethodManager;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.edittext.EditTextWithDel;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 货主 取货记录 页面
 */

public class CargoPickUpRecordActivity extends BaseActivity implements OnClickItemListener,HandlerUtils.OnReceiveMessageListener {

    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.operation_llayout)
    LinearLayout operationLlayout;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.bg_ll)
    LinearLayout bgLl;
    @BindView(R.id.root_ll)
    LinearLayout rootLl;
    @BindView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
    @BindView(R.id.scanning_et)
    EditTextWithDel scanningEt;
    @BindView(R.id.no_data_ll)
    LinearLayout mNoDataLl;

    private List<GetTakeManifestsDataValue> mList;
    private CargoPickupRecordAdapter mAdapter;

    private SweetAlertDialog loadingDialog = null;

    private boolean isRefresh = false;
    private DataRequestBase dataRequestBase;
    private int pageInt = 1;

    public String beginDate = null;
    public String endDate = null;

    private String searchStr;//搜索内容

    private Intent intent;
    private HandlerUtils.HandlerHolder mHandlerHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up_record);
        ButterKnife.bind(this);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        initView();
        MyInputMethodManager.setImeOptions(scanningEt, EditorInfo.IME_ACTION_SEARCH);
        MyInputMethodManager.setOnKeyListener(scanningEt, new MyInputMethodManager.MyInputMethodOnKeyListener() {
            @Override
            public void onInputMethodeOnkey() {
                onSearch();
            }
        });
    }

    private void initView() {
        titleTv.setText("取货记录");
        scanningEt.addTextChangedListener(watcher);
        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        dataRequestBase = new DataRequestBase();

        mList = new ArrayList<>();

        mAdapter = new CargoPickupRecordAdapter(this, mList, this);
        pullRefreshList.setAdapter(mAdapter);

        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(CargoPickUpRecordActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isRefresh = true;
                pageInt = 1;
                dataRequestBase.Page = pageInt;
                searchStr = scanningEt.getText().toString();
                //执行刷新
                getOrderList(beginDate, endDate, false, searchStr);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                dataRequestBase.Page = pageInt;
                searchStr = scanningEt.getText().toString();
                //执行刷新
                getOrderList(beginDate, endDate, false, searchStr);
            }
        });

        intent = getIntent();
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {

            if (s.length() == 0) {

                mList.clear();
                mAdapter.notifyDataSetChanged();
                pageInt = 1;
                dataRequestBase.Page = pageInt;
                searchStr = scanningEt.getText().toString();
                //执行刷新
                getOrderList(beginDate, endDate, false, searchStr);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        isRefresh = true;
        searchStr = scanningEt.getText().toString();
        getOrderList(beginDate, endDate, true, searchStr);
    }

    // 搜索 345455
    private void onSearch() {
        searchStr = scanningEt.getText().toString();
        if (searchStr == null || searchStr.trim().length() <= 0) {
            // 没有内容，不进行搜索，提示请输入内容
            DialogUtils.showWarningToast(Utils.getResourcesString(R.string.please_enter_search_name));
        } else {
            // 关闭输入法
            MyInputMethodManager.closeInputMethod(this, scanningEt);
            isRefresh = true;
            pageInt = 1;
            dataRequestBase.Page = pageInt;
            getOrderList(beginDate, endDate, false, searchStr);
        }
    }

    private void showDia(boolean showDia) {
        if (loadingDialog == null) {
            loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        }
        if (showDia) {
            loadingDialog.show();
        }
    }

    private void dimiss() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @OnClick({R.id.back_llayout, R.id.right_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.right_tv:
                onSearch();
                break;
        }
    }

    @Override
    public void onClick(int position) {
        LogUtil.d("-->> onClick");

        if (intent.getStringExtra("Cargo").equals("Door")) {
            if (mList != null && mList.get(position) != null) {
                GetTakeManifestsDataValue bean = mList.get(position);
                PIckupCallback.getCallback().addAddressBiz(bean);
                finish();
            }
        }
    }

    @Override
    public void onLongClick(mCarShift info, int position) {}

    /**
     * type 0或者不传 搜索为全部
     * 1 未签收
     * 2 部分签收
     * 3 已经签收
     *
     * @param endDate
     * @param beginDate
     * @param showDia
     * @param searchStr
     */
    private void getOrderList(String beginDate, String endDate, boolean showDia, String searchStr) {

        showDia(showDia);

        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();
        GetTakeManifestsRequest datavalue = new GetTakeManifestsRequest();
        datavalue.SendMan_Tel = info.DataValue.User_Tel;
        datavalue.SelectText = searchStr;

        dataRequestBase.DataValue = datavalue;
        dataRequestBase.User_Key = info.DataValue.User_Key;
        dataRequestBase.User_Name = info.DataValue.User_Name;
        dataRequestBase.UserType_Oid = info.DataValue.UserType_Oid;
        dataRequestBase.Token = info.DataValue.Token;
        dataRequestBase.Company_Oid = info.DataValue.Company_Oid;
        dataRequestBase.Org_Code = info.DataValue.Org_Code;
        dataRequestBase.Limit = 10;
        dataRequestBase.Page = pageInt;

        LogUtil.d("-->>request=" + new Gson().toJson(dataRequestBase));
        LoggerUtil.json(new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TMSService.GetTakeManifests_TAG, TMSService.GetTakeManifests_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->> respon=" + response.toString());
                LoggerUtil.json(response.toString());
                GetTakeManifestsResponse getShiftListResponse = GsonUtil.newInstance().fromJson(response, GetTakeManifestsResponse.class);
                Message msg = Message.obtain();
                if (getShiftListResponse != null) {
                    if (getShiftListResponse.Success && getShiftListResponse.DataValue != null) {
                        msg.what = Constants.REQUEST_SUCC;
                        msg.obj = getShiftListResponse.DataValue;
                    } else {
                        if (getShiftListResponse.DataValue == null) {
                            msg.what = Constants.REQUEST_FAIL;
                            msg.obj = Utils.getResourcesString(R.string.list_null);
                        } else {
                            msg.what = Constants.REQUEST_FAIL;
                            msg.obj = getShiftListResponse.ErrorText;
                        }
                    }
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_ERROR;
                message.obj = error.toString();
                mHandlerHolder.sendMessage(message);
            }
        });

    }

    @Override
    public void handlerMessage(Message msg) {
        dimiss();
        pullRefreshList.onRefreshComplete();
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                if (msg.obj != null) {
                    if (isRefresh) {
                        isRefresh = false;
                        mList.clear();
                    }
                    List<GetTakeManifestsDataValue> list = (List<GetTakeManifestsDataValue>) msg.obj;

                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    if (mList.size() > 0) {
                        mNoDataLl.setVisibility(View.GONE);
                    } else {
                        mNoDataLl.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(CargoPickUpRecordActivity.this, Utils.getResourcesString(R.string.request_error));
                break;
        }
    }
}
