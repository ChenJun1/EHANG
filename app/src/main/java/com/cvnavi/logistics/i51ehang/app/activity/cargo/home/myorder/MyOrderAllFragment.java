package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.myorder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.myorder.MyOrderAdapter;
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
 * Depict:
 */
public class MyOrderAllFragment extends BaseFragment implements OnClickItemListener,HandlerUtils.OnReceiveMessageListener{

    @BindView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
    private MyOrderAdapter adapter = null;
    private List<mOrder> dataList;
    private SweetAlertDialog loadingDialog = null;

    private boolean isRefresh = false;
    private DataRequestBase dataRequestBase = new DataRequestBase();
    private int pageInt = 1;
    private HandlerUtils.HandlerHolder mHandlerHolder;
    public static MyOrderAllFragment getInstance() {
        return new MyOrderAllFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order_has_sign, container, false);
        ButterKnife.bind(this, view);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        dataList = new ArrayList<>();
        adapter = new MyOrderAdapter(getActivity(), dataList, this);

        pullRefreshList.setAdapter(adapter);
        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isRefresh = true;
                pageInt = 1;
                dataRequestBase.Page = pageInt;
                //执行刷新
                getOrderList(DateUtil.getLastNDays(-7), DateUtil.getLastNDays(0), false, "0");
            }
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                dataRequestBase.Page = pageInt;
                //执行刷新
                getOrderList(DateUtil.getLastNDays(-7), DateUtil.getLastNDays(0), false, "0");
            }
        });

        return view;
    }

    private void showDia(boolean showDia) {
        if (loadingDialog == null) {
            loadingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        }
        if (showDia) {
            loadingDialog.show();
        }
    }

    private void dimiss() {
        if(loadingDialog!=null)
        loadingDialog.dismiss();
    }

    @Override
    public void onClick(int position) {
        LogUtil.d("-->> onClick");
        if (dataList != null && dataList.get(position) != null) {
            Intent intent = new Intent(getActivity(), MyOrderDetailAcitivity.class);
            intent.putExtra(MyOrderDetailAcitivity.ORDER_ID, dataList.get(position).All_Ticket_No);
            startActivity(intent);
        }
    }

    @Override
    public void onLongClick(mCarShift info, int position) {}
    private void getOrderList(String beginDate, String endDate, boolean showDia, String type) {

        showDia(showDia);

        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();
        GetOrederListRequest datavalue = new GetOrederListRequest();
        datavalue.BeginDate = beginDate;
        datavalue.EndDate = endDate;
        datavalue.DeliverStatus = type;

        if (info.DataValue.UserType_Oid.equals("E")) {
            //货主查询货主的
            datavalue.SendMan_Tel = info.DataValue.User_Tel;
        }

        if (info.DataValue.UserType_Oid.equals("G")) {
            //司机查询司机的
            datavalue.Driver_Tel = info.DataValue.User_Tel;
        }

        dataRequestBase.DataValue = datavalue;
        dataRequestBase.User_Key = info.DataValue.User_Key;
        dataRequestBase.UserType_Oid = info.DataValue.UserType_Oid;
        dataRequestBase.Token = info.DataValue.Token;
        dataRequestBase.Company_Oid = info.DataValue.Company_Oid;
        dataRequestBase.Org_Code = info.DataValue.Org_Code;

        LogUtil.d("-->>request=" + new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TMSService.GetReadNoTicket_Url, TMSService.GetReadNoTicket_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->> respon=" + response.toString());

                GetOrederListResponse getShiftListResponse = GsonUtil.newInstance().fromJson(response, GetOrederListResponse.class);
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
                message.what = Constants.REQUEST_FAIL;
                message.obj = error.toString();
                mHandlerHolder.sendMessage(message);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        MyOrderActivity activity = (MyOrderActivity) getActivity();
        isRefresh = true;
        if (activity.getCurPosition() == 1 && activity.beginDate != null) {
            getOrderList(activity.beginDate, activity.endDate, true, "0");
        } else {
            getOrderList(DateUtil.getLastNDays(-7), DateUtil.getLastNDays(0), true, "0");
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
                }

                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showFailToast(Utils.getResourcesString(R.string.get_data_fail));
                break;
        }
    }
}
