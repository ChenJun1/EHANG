package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.DrMyTakeGoods;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.adapter.DrStayTakeGoodsAdpter;
import com.cvnavi.logistics.i51ehang.app.bean.driver.request.SendGoodsRequest;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.GetTakeGoodsResponseBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
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
import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 未完成 提货页面
 */

public class DrStayTakeGoodsFragment extends Fragment implements HandlerUtils.OnReceiveMessageListener {

    private static final String TAG = "DrAlreadyTakeGoodsFragm";

    @BindView(R.id.my_task_off_the_stocks_pull_lv)
    PullToRefreshListView mMyTaskOffTheStocksPullLv;
    @BindView(R.id.no_data_ll)
    LinearLayout mNoDataLl;

    private ListView myListView;

    private DataRequestBase dataRequestBase;

    private SweetAlertDialog lodingDialog;

    private List<GetTakeGoodsResponseBean.TakeBean> list;

    private boolean isRefresh = false;//是否熟悉

    private int pageInt = 1;//加载页面数

    private DrStayTakeGoodsAdpter adapter;

    private DrMyTakeGoods takeActivity;

    private SendGoodsRequest mSendGoodsRequest;

    private HandlerUtils.HandlerHolder mHandlerHolder;

    public static DrStayTakeGoodsFragment getInstance() {
        return new DrStayTakeGoodsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dr_take_goods_fragment1, container, false);
        ButterKnife.bind(this, view);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        init();
        return view;
    }

    public void init() {
        dataRequestBase = new DataRequestBase();
        mSendGoodsRequest = new SendGoodsRequest();
        lodingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);

        //设置可加载刷新属性
        mMyTaskOffTheStocksPullLv.setMode(PullToRefreshBase.Mode.BOTH);

        mMyTaskOffTheStocksPullLv.setOnRefreshListener(new PullToRefreshBase
                .OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                onRefreshs();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                onLoad();
            }
        });
        list = new ArrayList<>();

        if (adapter == null) {
            adapter = new DrStayTakeGoodsAdpter(list, getActivity());
        }
        myListView = mMyTaskOffTheStocksPullLv.getRefreshableView();
        myListView.setAdapter(adapter);
    }

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        mSendGoodsRequest.BeginDate = getBeginTime();
        mSendGoodsRequest.IsDeliver = "0";//0表示未提货状态
        mSendGoodsRequest.Driver_Tel = MyApplication.getInstance().getLoginInfo().DataValue.User_Tel;
        mSendGoodsRequest.EndDate = getEndTime();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = mSendGoodsRequest; //JsonUtils.toJsonData(getDriverListRequest);
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson
                (dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>" + response.toString());
                GetTakeGoodsResponseBean response1 = GsonUtil.newInstance().fromJson(response.toString(),
                        GetTakeGoodsResponseBean.class);
                Message msg = Message.obtain();
                if (response1 == null) {
                    msg.obj = response1.getErrorText();
                    msg.what = Constants.REQUEST_FAIL;
                } else {
                    if (response1.isSuccess()) {
                        msg.obj = response1.getDataValue();
                        msg.what = Constants.REQUEST_SUCC;
                    } else {
                        msg.obj = response1.getErrorText();
                        msg.what = Constants.REQUEST_FAIL;
                    }
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("-->>" + error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });
    }

    private void onRefreshs() {
        isRefresh = true;
        pageInt = 1;
        dataRequestBase.Page = pageInt;
        loadDataRequest(DriverService.GetTHTicketData_URL);
    }

    private void onLoad() {
        isRefresh = false;
        pageInt++;
        dataRequestBase.Page = pageInt;
        loadDataRequest(DriverService.GetTHTicketData_URL);
    }

    private String getBeginTime() {
        if (takeActivity == null)
            takeActivity = (DrMyTakeGoods) getActivity();
        return takeActivity.getBeginTime();
    }

    private String getEndTime() {
        if (takeActivity == null)
            takeActivity = (DrMyTakeGoods) getActivity();
        return takeActivity.getEndTime();
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefreshs();
    }

    @Override
    public void handlerMessage(Message msg) {
        if (lodingDialog != null) {
            lodingDialog.dismiss();
        }
        mMyTaskOffTheStocksPullLv.onRefreshComplete();
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                List<GetTakeGoodsResponseBean.TakeBean> dataList = (List<GetTakeGoodsResponseBean.TakeBean>) msg.obj;
                if (isRefresh) {
                    list.clear();
                }
                if (dataList != null) {
                    list.addAll(dataList);
                }
                if (list.size() > 0) {
                    mNoDataLl.setVisibility(View.GONE);
                } else {
                    mNoDataLl.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();

                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R
                        .string.request_Fill) : msg.obj.toString());
                break;
            case Constants.DELETE_SUCC:
                DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(getActivity(), Utils
                        .getResourcesString(R.string.request_error));
                break;
        }
    }
}
