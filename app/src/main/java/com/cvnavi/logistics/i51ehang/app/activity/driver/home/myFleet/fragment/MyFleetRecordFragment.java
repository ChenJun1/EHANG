package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.MyFleetRecordActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fragment.adapter.MyFleetRecordAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.MyTaskDetailedActivity;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.TimeEvent;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.TrackEvent;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarHistoryLocusAnalysis;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetHistoryLocusAnalysisRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetHistoryLocusAnalysisResponse;
import com.cvnavi.logistics.i51ehang.app.config.LPSService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
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
import volley.VolleyManager;

/**
 * Created by ${ChenJ} on 2016/8/9.
 */
public class MyFleetRecordFragment extends BaseFragment {
    private final String TAG = MyTaskDetailedActivity.class.getName();
    private final String STARTIME = " 00:00:00";
    private final String ENDTIME = " 23:59:59";
    @BindView(R.id.empty_tv)
    TextView emptyTv;
    @BindView(R.id.lv)
    PullToRefreshListView lv;
    private SweetAlertDialog lodingDialog;
    private DataRequestBase dataRequestBase;
    private GetHistoryLocusAnalysisRequest requestBean;
    private MyFleetRecordAdapter adapter;
    private ListView myListView;
    private List<mCarHistoryLocusAnalysis> list;
    private int pageInt = 1;
    private String startTime;
    private String endTime;
    private String carCodeKey;
    private MyFleetRecordActivity context;

    public static MyFleetRecordFragment getInstance() {
        return new MyFleetRecordFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_fleet_record, container, false);
        ButterKnife.bind(this, view);
        init();
        if (!TextUtils.isEmpty(context.getCarKey())) {
            carCodeKey = context.getCarKey();
        }
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        onRefreshs();
    }

    private void init() {
        startTime = DateUtil.getNowTime(DateUtil.FORMAT_YMD) + STARTIME;
        endTime = DateUtil.getNowTime(DateUtil.FORMAT_YMD) + ENDTIME;
        context = (MyFleetRecordActivity) getActivity();
        lodingDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dataRequestBase = new DataRequestBase();
        requestBean = new GetHistoryLocusAnalysisRequest();
        list = new ArrayList<>();
        adapter = new MyFleetRecordAdapter(list, context);

        lv.setMode(PullToRefreshBase.Mode.DISABLED);
        myListView = lv.getRefreshableView();
        myListView.setAdapter(adapter);
    }

    private void onRefreshs() {
        if (!TextUtils.isEmpty(context.getStartTime()) && !TextUtils.isEmpty(context.getEndTime())) {
            startTime = context.getStartTime();
            endTime = context.getEndTime();
        }
        requestData(LPSService.GetHistoryLocusAnalysis_Request_Url);
    }

    private void onLoad() {
        pageInt++;
        dataRequestBase.Page = pageInt;
        requestData(LPSService.GetHistoryLocusAnalysis_Request_Url);
    }

    private void requestData(String Url) {
        lodingDialog.show();
        requestBean.StarTime = startTime;//时间格式为"2016-07-2 17:01"
        requestBean.EndTime = endTime;
        requestBean.KeyList = carCodeKey;
//        requestBean.KeyList="ee5af185-e5f5-4f45-bec0-d5a96b877a07";
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = requestBean; //JsonUtils.toJsonData(getDriverListRequest);
        LogUtil.d("-->>" + GsonUtil.newInstance().toJsonStr(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>response" + response.toString());
                GetHistoryLocusAnalysisResponse response1 = JsonUtils.parseData(response.toString(), GetHistoryLocusAnalysisResponse.class);
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
            lv.onRefreshComplete();
            switch (msg.what) {

                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        List<mCarHistoryLocusAnalysis> dataList = (List<mCarHistoryLocusAnalysis>) msg.obj;
                        list.clear();
                        if (dataList != null) {
                            list.addAll(dataList);
                        }
                        if (list.size() != 0) {
                            emptyTv.setVisibility(View.GONE);
                        } else {
                            emptyTv.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                        context.setLocusList(list);
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;
                case Constants.DELETE_SUCC:
                    DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(context, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };


    public void refresh() {
        onRefreshs();
    }


}
