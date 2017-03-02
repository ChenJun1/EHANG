package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.weituo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetStowageStatisticsListRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarDetailModel;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
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
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午3:59
 * 描述：委托明细
 ************************************************************************************/


public class WeiTuoStatisticDetail extends BaseActivity implements MyOnClickItemListener {


    @BindView(R.id.back_ll)
    LinearLayout backLl;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.top_bar_line)
    ImageView topBarLine;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.no_data_view)
    FrameLayout noDataView;
    @BindView(R.id.lv)
    PullToRefreshListView lv;
    @BindView(R.id.line)
    ImageView line;
    private WeiTuoAdapter adapter;
    private ArrayList<SendCarDetailModel.DataValueBean> mList;
    private String mStarTime;
    private String mEendTime;
    DataRequestBase requestBase;
    private boolean isRefresh = false;//是否刷新
    private int pageInt = 1;//分页num
    private GetStowageStatisticsListRequest request = null;

    public static void start(Context context, String mStarTime, String mEendTime) {
        Intent starter = new Intent(context, WeiTuoStatisticDetail.class);
        starter.putExtra("mStarTime", mStarTime);
        starter.putExtra("mEendTime", mEendTime);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_car_detail);
        ButterKnife.bind(this);
        title.setText("委托明细");
        init();
    }


    @Override
    public void myOnClickItem(int position, Object data) {
        SendCarDetailModel.DataValueBean info = (SendCarDetailModel.DataValueBean) data;
        if (info != null) {
            WeituoDetailActivity.start(this, info.getLetter_Oid());
        }
    }


    private void init() {
        noDataTv.setTextColor(0xffffffff);
        topBarLine.setVisibility(View.GONE);
        mStarTime = getIntent().getStringExtra("mStarTime");
        mEendTime = getIntent().getStringExtra("mEendTime");
        mList = new ArrayList<>();

        requestBase = new DataRequestBase();
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(WeiTuoStatisticDetail.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isRefresh = true;
                pageInt = 1;
                //执行刷新
                requestHttp();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                //执行刷新
                requestHttp();
            }
        });

        requestHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 请求网络数据
     */
    private void requestHttp() {
        showLoading();

        request = new GetStowageStatisticsListRequest();
        request.BeginDate = mStarTime;
        request.EndDate = mEendTime;

        requestBase.DataValue = request;
        requestBase.Page = pageInt;
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        final JSONObject jsonObject = GsonUtil.newInstance().toJson(requestBase);
        MLog.json(GsonUtil.newInstance().toJson(requestBase).toString());
        VolleyManager.newInstance().PostJsonRequest("Tag", EmployeeService.GetDelegateList_Url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MLog.json(response.toString());
                        SendCarDetailModel result = GsonUtil.newInstance().fromJson(response, SendCarDetailModel.class);
                        Message msg = Message.obtain();
                        if (result.isSuccess()) {
                            msg.what = Constants.REQUEST_SUCC;
                            msg.obj = result.getDataValue();
                            mHandler.sendMessage(msg);
                        } else {
                            msg.what = Constants.REQUEST_FAIL;
                            mHandler.sendMessage(msg);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Message msg = Message.obtain();
                        msg.what = Constants.REQUEST_ERROR;
                        mHandler.sendMessage(msg);
                    }
                });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            lv.onRefreshComplete();
            dissLoading();
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        //转换数据
                        List<SendCarDetailModel.DataValueBean> mGather = (List<SendCarDetailModel.DataValueBean>) msg.obj;
                        if (isRefresh) {
                            mList.clear();
                            isRefresh = false;
                        }
                        //判断是否是第一次加载
                        if (mList != null && mList.size() == 0 && mGather != null && mGather.size() == 0) {
                            Utils.showNoDataView(true, noDataView, lv, null);
                        } else {
                            Utils.showNoDataView(false, noDataView, lv, null);
                            mList.addAll(mGather);
                            if (adapter == null) {
                                adapter = new WeiTuoAdapter(WeiTuoStatisticDetail.this, mList, WeiTuoStatisticDetail.this);
                                lv.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        }

                    } else {
                        Utils.showNoDataView(true, noDataView, lv, null);
                    }

                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(WeiTuoStatisticDetail.this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };

    @OnClick(R.id.back_ll)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;

        }
    }

}
