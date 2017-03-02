package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.myorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
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
import com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.myorder.MyOrderAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverCarSchedulingSearchActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.utils.DriverDataChooseActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarShift;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrder;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetOrederListRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetOrederListResponse;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.trans.OnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
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
 * Depict:货主 历史货单 页面
 */

public class HistoryOrdersActivity extends BaseActivity implements OnClickItemListener, HandlerUtils.OnReceiveMessageListener {

    @BindView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
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
    @BindView(R.id.no_data_ll)
    LinearLayout mNoDataLl;

    private MyOrderAdapter adapter = null;
    private List<mOrder> dataList;
    private SweetAlertDialog loadingDialog = null;

    private boolean isRefresh = false;
    private DataRequestBase dataRequestBase;
    private int pageInt = 1;

    public static final int REQUEST_CODE_SEARCH = 0x12;
    public String beginDate;
    public String endDate;
    private HandlerUtils.HandlerHolder mHandlerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_order_has_sign);
        ButterKnife.bind(this);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        initView();
    }

    private void initView() {
        titltTv.setText("历史货单");
        rightLl.setVisibility(View.VISIBLE);
        addLl.setVisibility(View.GONE);

        beginDate = DateUtil.getLastNDays(-7);
        endDate = DateUtil.getLastNDays(0);

        dataRequestBase = new DataRequestBase();

        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        dataList = new ArrayList<>();
        adapter = new MyOrderAdapter(this, dataList, this);

        pullRefreshList.setAdapter(adapter);
        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(HistoryOrdersActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isRefresh = true;
                pageInt = 1;
                dataRequestBase.Page = pageInt;
                //执行刷新
                getOrderList(beginDate, endDate, false, "3");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                dataRequestBase.Page = pageInt;
                //执行刷新
                getOrderList(beginDate, endDate, false, "3");
            }
        });
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
        loadingDialog.dismiss();
    }

    @Override
    public void onClick(int position) {
        LogUtil.d("-->> onClick");
        if (dataList != null && dataList.get(position) != null) {
            mOrder bean = dataList.get(position);
            Intent intent = new Intent(this, MyOrderDetailAcitivity.class);
            intent.putExtra(MyOrderDetailAcitivity.ORDER_ID, bean);
            startActivity(intent);
        }
    }

    @Override
    public void onLongClick(mCarShift info, int position) {
    }
    /**
     * type 0或者不传 搜索为全部
     * 1 未签收
     * 2 部分签收
     * 3 已经签收
     *
     * @param endDate
     * @param beginDate
     * @param showDia
     * @param type
     */
    private void getOrderList(String beginDate, String endDate, boolean showDia, String type) {

        showDia(showDia);
        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();
        GetOrederListRequest datavalue = new GetOrederListRequest();
        datavalue.BeginDate = beginDate + " 00:00";
        datavalue.EndDate = endDate + " 23:59";
        datavalue.SendMan_Tel = info.DataValue.User_Tel;
        datavalue.DeliverStatus = type;
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
        VolleyManager.newInstance().PostJsonRequest(TMSService.GetHistoryTicket_TAG, TMSService.GetHistoryTicket_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->> respon=" + response.toString());
                LoggerUtil.json(response.toString());

                GetOrederListResponse getShiftListResponse = GsonUtil.newInstance().fromJson(response, GetOrederListResponse.class);
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
                message.what = Constants.REQUEST_ERROR;
                message.obj = error.toString();
                mHandlerHolder.sendMessage(message);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        isRefresh = true;
        getOrderList(beginDate, endDate, true, "3");
    }

    @OnClick({R.id.search_ll, R.id.back_llayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_ll:
                DriverDataChooseActivity.startActivity(this, REQUEST_CODE_SEARCH, beginDate, endDate);
                break;
            case R.id.back_llayout:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SEARCH) {
            beginDate = data.getStringExtra(DriverCarSchedulingSearchActivity.BEGIN_DATA);
            endDate = data.getStringExtra(DriverCarSchedulingSearchActivity.END_DATA);
        }
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
                        dataList.clear();
                    }
                    List<mOrder> list = (List<mOrder>) msg.obj;
                    dataList.addAll(list);
                    adapter.notifyDataSetChanged();
                    if (dataList.size() > 0) {
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
                DialogUtils.showMessageDialogOfDefaultSingleBtn(HistoryOrdersActivity.this, Utils.getResourcesString(R.string.request_error));
                break;
        }
    }
}
