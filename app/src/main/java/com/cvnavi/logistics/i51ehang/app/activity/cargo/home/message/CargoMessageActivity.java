package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.message;

import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
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
import com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.message.CargoMessageAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.GetMessageContentDataValue;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.request.DelMessageContentRequest;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.request.GetMessageContentRequest;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.response.GetMessageContentResponse;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
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
 * Depict: 货主 消息中心 页面
 */

public class CargoMessageActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener{
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
    @BindView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
    @BindView(R.id.no_data_ll)
    LinearLayout mNoDataLl;

    private SweetAlertDialog loadingDialog = null;

    private DataRequestBase dataRequestBase;

    private ListView actualListView;
    private List<GetMessageContentDataValue> mList;
    private CargoMessageAdapter mAdapter;

    private boolean isRefresh = false;
    private int pageInt = 1;

    public static final int REQUEST_CODE_SEARCH = 0x12;
    public String beginDate = null;
    public String endDate = null;

    private HandlerUtils.HandlerHolder mHandlerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_message);
        ButterKnife.bind(this);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mList.clear();
        mAdapter.notifyDataSetChanged();
        httpRequest(beginDate, endDate);
    }

    private void initView() {
        titleTv.setText("消息中心");
        operationBtn.setText("清空");
        operationBtn.setVisibility(View.VISIBLE);
        dataRequestBase = new DataRequestBase();

        loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        mList = new ArrayList<>();

        mAdapter = new CargoMessageAdapter(this, mList);

        actualListView = pullRefreshList.getRefreshableView();
        actualListView.setAdapter(mAdapter);

        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(CargoMessageActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                onRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                dataRequestBase.Page = pageInt;
                //执行刷新
                httpRequest(beginDate, endDate);
            }
        });

        actualListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final GetMessageContentDataValue bean = (GetMessageContentDataValue) mAdapter.getItem(position - 1);

                DialogUtils.showMessageDialog(CargoMessageActivity.this, "提示", "确定要删除这条消息吗？", "确定", "取消", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        if (CustomDialogListener.BUTTON_NO == closeType) {
                        } else if (CustomDialogListener.BUTTON_OK == closeType) {
                            httpDeleteRequest(bean, "ONE");
                        }
                    }
                });
                return true;
            }
        });
    }

    private void onRefresh() {
        loadingDialog.show();
        isRefresh = true;
        pageInt = 1;
        dataRequestBase.Page = pageInt;
        httpRequest(beginDate, endDate);
    }

    private void httpRequest(String beginDate, String endDate) {

        loadingDialog.show();

        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();
        GetMessageContentRequest datavalue = new GetMessageContentRequest();
        datavalue.User_Oid = info.DataValue.User_Tel;

        dataRequestBase.DataValue = datavalue;
        dataRequestBase.User_Key = info.DataValue.User_Key;
        dataRequestBase.User_Name = info.DataValue.User_Name;
        dataRequestBase.UserType_Oid = info.DataValue.UserType_Oid;
        dataRequestBase.Token = info.DataValue.Token;
        dataRequestBase.Company_Oid = info.DataValue.Company_Oid;
        dataRequestBase.Org_Code = info.DataValue.Org_Code;
        dataRequestBase.Page = pageInt;
        dataRequestBase.Limit = 10;

        LogUtil.d("-->>request=" + new Gson().toJson(dataRequestBase));
        LoggerUtil.d("-->>request=" + new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TMSService.GetMessageContent_TAG, TMSService.GetMessageContent_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->> respon=" + response.toString());
                LoggerUtil.json(response.toString());
                GetMessageContentResponse getShiftListResponse = GsonUtil.newInstance().fromJson(response, GetMessageContentResponse.class);
                Message msg = Message.obtain();
                if (getShiftListResponse != null) {
                    if (getShiftListResponse.Success && getShiftListResponse.DataValue != null) {
                        msg.what = Constants.REQUEST_SUCC;
                        msg.obj = getShiftListResponse.DataValue;
                    } else {
                        if (getShiftListResponse.DataValue == null) {
                            msg.what = Constants.REQUEST_FAIL;
                            msg.obj = getShiftListResponse.ErrorText;
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
                message.what = Constants.REQUEST_FAIL;
                message.obj = error.toString();
                mHandlerHolder.sendMessage(message);
            }
        });

    }

    private void httpDeleteRequest(GetMessageContentDataValue bean, String type) {

        loadingDialog.show();

        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();
        DelMessageContentRequest datavalue = new DelMessageContentRequest();
        if (type.equals("ONE")) {
            datavalue.Serial_Oid = bean.Serial_Oid;
        }
        if (type.equals("ALL")) {
            datavalue.User_Oid = info.DataValue.User_Tel;
        }

        dataRequestBase.DataValue = datavalue;
        dataRequestBase.User_Key = info.DataValue.User_Key;
        dataRequestBase.User_Name = info.DataValue.User_Name;
        dataRequestBase.UserType_Oid = info.DataValue.UserType_Oid;
        dataRequestBase.Token = info.DataValue.Token;
        dataRequestBase.Company_Oid = info.DataValue.Company_Oid;
        dataRequestBase.Org_Code = info.DataValue.Org_Code;

        LogUtil.d("-->>request=" + new Gson().toJson(dataRequestBase));
        LoggerUtil.json(new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TMSService.DelMessageContent_TAG, TMSService.DelMessageContent_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->> respon=" + response.toString());
                LoggerUtil.json(response.toString());
                DataResponseBase dataResponseBase = JsonUtils.parseData(response.toString(), DataResponseBase.class);

                Message msg = Message.obtain();
                if (dataResponseBase != null) {
                    if (dataResponseBase.Success) {
                        msg.what = Constants.DELETE_SUCC;
                    } else {
                        msg.obj = dataResponseBase.ErrorText;
                        msg.what = Constants.REQUEST_FAIL;
                    }
                } else {
                    msg.obj = dataResponseBase.ErrorText;
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

    @OnClick({R.id.back_llayout, R.id.operation_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.operation_btn:
                DialogUtils.showMessageDialog(CargoMessageActivity.this, "提示", "确定要清空消息吗？", "确定", "取消", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {

                        if (CustomDialogListener.BUTTON_NO == closeType) {

                        } else if (CustomDialogListener.BUTTON_OK == closeType) {
                            httpDeleteRequest(null, "ALL");
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        if(loadingDialog!=null)
        loadingDialog.dismiss();
        pullRefreshList.onRefreshComplete();
        switch (msg.what) {
            case Constants.REQUEST_SUCC:

                if (msg.obj != null) {
                    if (isRefresh) {
                        isRefresh = false;
                        mList.clear();
                    }
                    List<GetMessageContentDataValue> list = (List<GetMessageContentDataValue>) msg.obj;
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    if (mList.size() > 0) {
                        mNoDataLl.setVisibility(View.GONE);
                        operationBtn.setTextColor(Utils.getResourcesColor(R.color.color_ffffff));
                        operationBtn.setEnabled(true);
                    } else {
                        mNoDataLl.setVisibility(View.VISIBLE);
                        operationBtn.setTextColor(Utils.getResourcesColor(R.color.color_878787));
                        operationBtn.setEnabled(false);
                    }
                }
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                break;
            case Constants.DELETE_SUCC:
                DialogUtils.showSuccToast(Utils.getResourcesString(R.string.dele_succ));
                onRefresh();
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(CargoMessageActivity.this, Utils.getResourcesString(R.string.request_error));
                break;
        }
    }
}
