package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.unfinlshedorder;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.unfinishedorder.NoStartCarAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrder;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetOrederListRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetOrederListResponse;
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
import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 货主 未发车货单 页面
 */

public class NoStartCarFragment extends BaseFragment implements HandlerUtils.OnReceiveMessageListener {

    @BindView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
    @BindView(R.id.no_data_ll)
    LinearLayout mNoDataLl;

    public static NoStartCarFragment getInstance() {
        return new NoStartCarFragment();
    }

    private List<mOrder> mList;
    private NoStartCarAdapter mAdapter;
    private SweetAlertDialog sweetAlertDialog;

    private boolean isRefresh = false;
    private DataRequestBase dataRequestBase;
    private int pageInt = 1;

    public static final int REQUEST_CODE_SEARCH = 0x12;
    public String beginDate = null;
    public String endDate = null;
    private HandlerUtils.HandlerHolder mHandlerHolder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_nostart_fragment, container, false);
        ButterKnife.bind(this, view);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        initView();
        getOrderList(beginDate, endDate, true, "0");
        return view;
    }

    private void initView() {

        beginDate = DateUtil.getLastNDays(-30);
        endDate  = DateUtil.getLastNDays(1);

        dataRequestBase = new DataRequestBase();
        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        mList = new ArrayList<>();

        mAdapter = new NoStartCarAdapter(getContext(), mList);
        pullRefreshList.setAdapter(mAdapter);
        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isRefresh = true;
                pageInt = 1;
                dataRequestBase.Page = pageInt;

                //执行刷新
                getOrderList(beginDate, endDate, false, "0");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                dataRequestBase.Page = pageInt;
                //执行刷新
                getOrderList(beginDate, endDate, false, "0");
            }
        });
    }

    private void showDia(boolean showDia) {
        if (sweetAlertDialog == null) {
            sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        }
        if (showDia) {
            sweetAlertDialog.show();

        }
    }

    private void dimiss() {
        if (sweetAlertDialog != null) {
            sweetAlertDialog.dismiss();
        }
    }


    /**
     * type 0或者不传 搜索为全部
     * 1 未签收
     * 2 部分签收
     * 3 已经签收
     * Ticket_State
     * 货单状态
     * “0”：未发车
     * “1”：运输中
     * “2”：派送中
     * 货主查询，必须输入当前货主电话
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

        datavalue.BeginDate = beginDate;
        datavalue.EndDate = endDate;
        datavalue.Ticket_State="0";
        datavalue.SendMan_Tel = info.DataValue.User_Tel;

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
        VolleyManager.newInstance().PostJsonRequest(TMSService.GetReadNoTicket_TAG, TMSService.GetReadNoTicket_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
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
                    List<mOrder> list = (List<mOrder>) msg.obj;
                    mList.addAll(list);
                    mAdapter.notifyDataSetChanged();
                    mNoDataLl.setVisibility(View.VISIBLE);

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
                DialogUtils.showMessageDialogOfDefaultSingleBtn(getActivity(), Utils.getResourcesString(R.string.request_error));
                break;
        }
    }
}
