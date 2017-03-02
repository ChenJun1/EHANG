package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.MyTaskCarryDetailedActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.MyTaskDetailedActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter.MyTaskTotalFragmenAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetTaskListRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetTaskListResponse;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.mytask.DriverChioceTimeCallback;
import com.cvnavi.logistics.i51ehang.app.callback.manager.DriverChioceTimeCallbackManager;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
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
 * Created by Administrator on 2016/7/19.
 * 全部
 */
public class MyTaskTotalFragment extends Fragment implements MyTaskTotalFragmenAdapter.MyTaskTotalListener, DriverChioceTimeCallback {

    private final String TAG = MyTaskOffTheStocksFragment.class.getName();
    @BindView(R.id.my_task_total_pull_lv)
    PullToRefreshListView myTaskTotalPullLv;
    @BindView(R.id.empty_tv)
    TextView emptyTv;

    private ListView myListView;
    private MyTaskTotalFragmenAdapter adapter = null;
    private ArrayList<TaskBean> taskList;
    private Context mContext;
    private DataRequestBase dataRequestBase;
    private SweetAlertDialog lodingDialog;
    private boolean isRefresh = false;
    private String bengTime;
    private String endsTime;
    private int pageInt = 1;

    public static MyTaskTotalFragment getInstance() {
        return new MyTaskTotalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_task_total, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        DriverChioceTimeCallbackManager.newInstance().add(this);
        lodingDialog.show();
        onRefreshs();
    }

    private void init() {
        mContext = getActivity();
        lodingDialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
        dataRequestBase = new DataRequestBase();
        myTaskTotalPullLv.setMode(PullToRefreshBase.Mode.BOTH);
        bengTime = DateUtil.strOldFormat2NewFormat(DateUtil.getLastNDays(-70), DateUtil.FORMAT_YMD, DateUtil.FORMAT_YMD_SN);
        endsTime = DateUtil.strOldFormat2NewFormat(DateUtil.getLastNDays(0), DateUtil.FORMAT_YMD, DateUtil.FORMAT_YMD_SN);

        myTaskTotalPullLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                onRefreshs();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                onLoad();
            }
        });

        taskList = new ArrayList<>();
        if (adapter == null) {
            adapter = new MyTaskTotalFragmenAdapter(taskList, mContext, this);
        }
        myListView = myTaskTotalPullLv.getRefreshableView();
        myListView.setAdapter(adapter);


    }

    public void onRefreshs() {
        isRefresh = true;
        pageInt = 1;
        dataRequestBase.Page = pageInt;
        loadDataRequest(TMSService.GetDetailedList_Request_Url);
    }

    public void onLoad() {
        pageInt++;
        isRefresh = false;
        dataRequestBase.Page = pageInt;
        loadDataRequest(TMSService.GetDetailedList_Request_Url);
    }

    private void loadDataRequest(final String Url) {
        GetTaskListRequest taskReqeustBean = new GetTaskListRequest();
        taskReqeustBean.BeginDate = bengTime;
        taskReqeustBean.DeliverStatus = "0";
//        taskReqeustBean.Driver_Tel = "66666";
        taskReqeustBean.Driver_Tel = MyApplication.getInstance().getLoginInfo().DataValue.User_Tel;
        taskReqeustBean.EndDate = endsTime;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = taskReqeustBean; //JsonUtils.toJsonData(getDriverListRequest);
        Log.d(TAG, dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                GetTaskListResponse response1 = JsonUtils.parseData(response.toString(), GetTaskListResponse.class);
                Message msg = Message.obtain();
                if (response1.Success) {
                    msg.obj = response1.DataValue;
                    msg.what = Constants.REQUEST_SUCC;
                    myHandler.sendMessage(msg);
                } else {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                    myHandler.sendMessage(msg);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                myHandler.sendMessage(msg);
            }
        });

    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (lodingDialog != null) {
                lodingDialog.dismiss();
            }
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    List<TaskBean> dataList = (List<TaskBean>) msg.obj;
                    if (dataList != null) {
                        if (isRefresh) {
                            taskList.clear();
                        }
                        taskList.addAll(dataList);
                        myTaskTotalPullLv.onRefreshComplete();
                        adapter.notifyDataSetChanged();
                        if (taskList.size() > 0) {
                            emptyTv.setVisibility(View.GONE);
                        } else {
                            emptyTv.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;
                case Constants.DELETE_SUCC:
                    DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(getActivity(), Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };

    @Override
    public void itemClick(TaskBean bean) {
        Intent intent;
        if (bean != null) {
            if (!TextUtils.isEmpty(bean.Letter_Type_Oid) && bean.Letter_Type_Oid.equals("A")) {
                intent = new Intent(getActivity(), MyTaskCarryDetailedActivity.class);
            } else {
                intent = new Intent(getActivity(), MyTaskDetailedActivity.class);
            }

            intent.putExtra(Constants.TASKINFO, bean);
            startActivity(intent);
        }
    }

    @Override
    public void OnTimeChioce(String strTime, String endTime, int tag) {
        if (tag == 1) {
            bengTime = DateUtil.strOldFormat2NewFormat(strTime, DateUtil.FORMAT_YMD, DateUtil.FORMAT_YMD_SN);
            endsTime = DateUtil.strOldFormat2NewFormat(endTime, DateUtil.FORMAT_YMD, DateUtil.FORMAT_YMD_SN);
            onRefreshs();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DriverChioceTimeCallbackManager.newInstance().remove(this);
    }
}
