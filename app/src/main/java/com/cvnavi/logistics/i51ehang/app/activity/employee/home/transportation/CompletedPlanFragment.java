package com.cvnavi.logistics.i51ehang.app.activity.employee.home.transportation;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.trans.DriverTransListViewAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.TimeEvent;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarShift;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetShiftListRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetShiftListResponse;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.trans.OnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import volley.VolleyManager;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:07
*描述：发车计划（已完成）
************************************************************************************/

public class CompletedPlanFragment extends BaseFragment implements OnClickItemListener {
    @BindView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
    @BindView(R.id.no_data_view)
    FrameLayout noDataView;
    private DataRequestBase dataRequestBase;
    private DriverTransListViewAdapter adapter = null;
    private List<mCarShift> dataList;
    private SweetAlertDialog loadingDialog = null;
    private mCarShift carInfo;
    private boolean isRefresh = false;
    private int pageInt = 1;
    private boolean isDelect = false;
    private int offset = 0;//偏移量
    private boolean isLoadSucc = false;//第一次加载
    private String Schedule_Status = "2";


    private int START_OFFSET = -1;
    private int END_OFFSET = 0;
    private String freshStartTime = DateUtil.getLastNDays(START_OFFSET + offset);
    private String freshEndTime = DateUtil.getLastNDays(END_OFFSET + offset);

    public static CompletedPlanFragment getInstance() {
        return new CompletedPlanFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isLoadSucc) {
            //执行刷新
            getSchedulingList(freshStartTime, freshEndTime);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_home_trans_last_day, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataRequestBase = new DataRequestBase();

        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        dataList = new ArrayList<>();
        adapter = new DriverTransListViewAdapter(dataList, getActivity(), CompletedPlanFragment.this);
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
                getSchedulingList(freshStartTime, freshEndTime);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                dataRequestBase.Page = pageInt;
                //执行刷新
                getSchedulingList(freshStartTime, freshEndTime);
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPlan(TimeEvent timeEvent) {
        isRefresh = true;
        this.freshStartTime = timeEvent.getStartTime();
        this.freshEndTime = timeEvent.getEndTime();
        getSchedulingList(timeEvent.getStartTime(), timeEvent.getEndTime());
    }


    /**
     * 获取已经完成的发车计划列表
     * @param beginDate
     * @param endDate
     */
    private void getSchedulingList(String beginDate, String endDate) {
        showDia();
        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();
        final GetShiftListRequest getShiftListRequest = new GetShiftListRequest();
        getShiftListRequest.BeginDate = beginDate;
        getShiftListRequest.EndDate = endDate;
        getShiftListRequest.Schedule_Status = Schedule_Status;
        dataRequestBase.DataValue = getShiftListRequest;
        dataRequestBase.User_Key = info.DataValue.User_Key;
        dataRequestBase.UserType_Oid = info.DataValue.UserType_Oid;
        dataRequestBase.Token = info.DataValue.Token;
        dataRequestBase.Company_Oid = info.DataValue.Company_Oid;

        VolleyManager.newInstance().PostJsonRequest(TMSService.GetShiftList_TAG, TMSService.GetShiftList_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>Last respon=" + response.toString());
                GetShiftListResponse getShiftListResponse = GsonUtil.newInstance().fromJson(response, GetShiftListResponse.class);
                Message msg = Message.obtain();
                if (getShiftListResponse != null && getShiftListResponse.Success) {
                    msg.what = Constants.REQUEST_SUCC;
                    msg.obj = getShiftListResponse.DataValue;
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                }
                handler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_FAIL;
                message.obj = error.toString();
                handler.sendMessage(message);
            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dimiss();
            pullRefreshList.onRefreshComplete();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        if (isRefresh) {
                            isRefresh = false;
                            dataList.clear();
                        }

                        if (isDelect) {
                            isDelect = false;
                            dataList.clear();
                        }
                        List<mCarShift> list = (List<mCarShift>) msg.obj;
                        if (dataList != null && dataList.size() == 0 && list != null && list.size() == 0) {
                            Utils.showNoDataView(true, noDataView, pullRefreshList, null);
                        } else {
                            Utils.showNoDataView(false, noDataView, pullRefreshList, null);
                            dataList.addAll(list);
                            adapter.notifyDataSetChanged();
                            isLoadSucc = true;
                        }
                    } else {
                        Utils.showNoDataView(true, noDataView, pullRefreshList, null);
                        isLoadSucc = false;
                    }

                    break;
                case Constants.REQUEST_FAIL:
                    Utils.showNoDataView(true, noDataView, pullRefreshList, null);
                    isLoadSucc = false;
                    break;
            }

        }
    };

    private void delDia(boolean showDia) {
        if (loadingDialog == null) {
            loadingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        }
        if (showDia) {
            loadingDialog.show();

        }
    }

    private void showDia() {
        if (loadingDialog == null) {
            loadingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        }
        loadingDialog.show();
    }

    private void dimiss() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }

    }


    @Override
    public void onClick(int position) {
        if (dataList != null && dataList.get(position) != null) {
            PlanDetailActivity.startActivity(getActivity(), dataList.get(position));
        }
    }

    @Override
    public void onLongClick(mCarShift info, final int position) {
//        if (Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_DELECT_PLAN + "")) {
//            carInfo = info;
//            DialogUtils.showMessageDialog(getActivity(), "删除数据", "是否删除该条数据？", "确定", "取消", new CustomDialogListener() {
//                @Override
//                public void onDialogClosed(int closeType) {
//                    if (closeType == CustomDialogListener.BUTTON_OK) {
//                        delDia(true);
//                        GetAppLoginResponse loginInfo = MyApplication.getInstance().getLoginInfo();
//                        DelCarShiftRequest datavalue = new DelCarShiftRequest();
//                        datavalue.Serial_Oid = carInfo.Serial_Oid;
//
//                        DataRequestBase dataRequestBase = new DataRequestBase();
//                        dataRequestBase.DataValue = datavalue;
//                        dataRequestBase.User_Key = loginInfo.DataValue.User_Key;
//                        dataRequestBase.UserType_Oid = loginInfo.DataValue.UserType_Oid;
//                        dataRequestBase.Token = loginInfo.DataValue.Token;
//                        dataRequestBase.Company_Oid = loginInfo.DataValue.Company_Oid;
//
//                        LogUtil.d("-->> delect request= " + new Gson().toJson(dataRequestBase));
//
//                        VolleyManager.newInstance().PostJsonRequest(TMSService.DelCarShift_TAG, TMSService.DelCarShift_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                LogUtil.d("-->> dele = " + response.toString());
//                                DelCarShiftResponse dataResponseBase = GsonUtil.newInstance().fromJson(response, DelCarShiftResponse.class);
//                                Message msg = Message.obtain();
//
//                                if (dataResponseBase != null) {
//                                    if (dataResponseBase.Success) {
//                                        msg.what = Constants.REQUEST_SUCC;
//                                        msg.obj = position;
//                                    } else {
//                                        msg.what = Constants.REQUEST_FAIL;
//                                    }
//                                } else {
//                                    msg.what = Constants.REQUEST_FAIL;
//                                }
//                                deletHandler.sendMessage(msg);
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Message message = Message.obtain();
//                                message.what = Constants.REQUEST_ERROR;
//                                deletHandler.sendMessage(message);
//                            }
//                        });
//
//                    } else {
//
//                    }
//
//                }
//            });
//        } else {
//            DialogUtils.showWarningToast("您没有该权限");
//        }
    }

    private Handler deletHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dimiss();
            pullRefreshList.onRefreshComplete();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    isDelect = true;
                    getSchedulingList(freshStartTime, freshEndTime);
                    DialogUtils.showSuccToast(Utils.getResourcesString(R.string.dele_succ));
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showFailToast(Utils.getResourcesString(R.string.request_Fill));
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showFailToast(Utils.getResourcesString(R.string.request_error));
                    break;
            }

        }
    };

}
