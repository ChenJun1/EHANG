package com.cvnavi.logistics.i51ehang.app.activity.driver.home.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
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
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.statistics.DriverAccountReveivableListAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.GatherFeeStatistics.GatherFeeList;
import com.cvnavi.logistics.i51ehang.app.bean.model.GatherFeeStatistics.GatherFeeStatistics;
import com.cvnavi.logistics.i51ehang.app.bean.model.GatherFeeStatistics.mGatherFeeStatistics;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetReceivablesListRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetReceivablesListResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.popupmenu.DriverAccountsReceiveListPopuMenu;
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
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 下午2:05
*描述：应收款list
************************************************************************************/

public class DriverAccountsReceiveListActivity extends BaseActivity {

    @BindView(R.id.lv)
    PullToRefreshListView lv;

    @BindView(R.id.Fee_text)
    TextView FeeText;
    @BindView(R.id.Gather_Fee_text)
    TextView GatherFeeText;
    @BindView(R.id.Surplus_Gather_Fee_text)
    TextView SurplusGatherFeeText;
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
    @BindView(R.id.empty_tv)
    TextView emptyTv;
    private DriverAccountReveivableListAdapter adapter;

    private mGatherFeeStatistics mGather;
    private ArrayList<GatherFeeStatistics> mGatherFeeStatistics;
    private ArrayList<GatherFeeList> mGatherFeeList;
    GetReceivablesListRequest request;
    private ListView actualListView;
    private SweetAlertDialog sweetAlertDialog = null;
    DataRequestBase requestBase;
    private boolean isRefresh = false;//是否刷新
    private int PageNum = 1;
    private Intent mIntent;

    com.cvnavi.logistics.i51ehang.app.bean.model.ReceivableAccount.GatherFeeStatistics bean;
    private DriverAccountsReceiveListPopuMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accounts_receive_list);
        ButterKnife.bind(this);
        popupMenu = new DriverAccountsReceiveListPopuMenu(this);
        init();
        initView();
    }

    private void init() {
        request = new GetReceivablesListRequest();
        mIntent = getIntent();
        if (mIntent.getStringExtra("ActivityName").equals("DriverAccountReceiveLastDayFragment")) {

            bean = (com.cvnavi.logistics.i51ehang.app.bean.model.ReceivableAccount.GatherFeeStatistics) mIntent.getSerializableExtra("Data");
            request.BeginDate = mIntent.getStringExtra("bengTime");
            request.EndDate = mIntent.getStringExtra("endsTime");
            request.Fee_Name = bean.Fee_Name;

        } else if (mIntent.getStringExtra("ActivityName").equals("DriverAccountReceiveThreeDayFragment")) {
            bean = (com.cvnavi.logistics.i51ehang.app.bean.model.ReceivableAccount.GatherFeeStatistics) mIntent.getSerializableExtra("Data");
            request.BeginDate = mIntent.getStringExtra("bengTime");
            request.EndDate = mIntent.getStringExtra("endsTime");
            request.Fee_Name = bean.Fee_Name;
        } else if (mIntent.getStringExtra("ActivityName").equals("DriverAccountReceiveOneMonthDayFragment")) {
            bean = (com.cvnavi.logistics.i51ehang.app.bean.model.ReceivableAccount.GatherFeeStatistics) mIntent.getSerializableExtra("Data");
            request.BeginDate = mIntent.getStringExtra("bengTime");
            request.EndDate = mIntent.getStringExtra("endsTime");
            request.Fee_Name = bean.Fee_Name;
        }
    }

    private void initView() {
        titleTv.setText("应收款详情");
        operationBtn.setVisibility(View.VISIBLE);
        operationBtn.setText("全部");

        requestBase = new DataRequestBase();
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);

        mGather = new mGatherFeeStatistics();
        mGatherFeeStatistics = new ArrayList<>();
        mGatherFeeList = new ArrayList<>();

        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(DriverAccountsReceiveListActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                onRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                PageNum++;
                requestBase.Page = PageNum;
                requestHttp();
            }
        });
        actualListView = lv.getRefreshableView();
        adapter = new DriverAccountReveivableListAdapter(mGatherFeeList, this);
        actualListView.setAdapter(adapter);
        onRefresh();
    }

    private void onRefresh() {
        isRefresh = true;
        PageNum = 1;
        requestBase.Page = PageNum;
        requestHttp();
    }

    private void requestHttp() {
        requestBase.DataValue = request;
        LogUtil.d("----->>Gather_Status="+request.Gather_Status);
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        requestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        final JSONObject jsonObject = GsonUtil.newInstance().toJson(requestBase);
        VolleyManager.newInstance().PostJsonRequest("Tag", TMSService.GetReceivablesList_Request_Url, jsonObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        LogUtil.d("-->>onResponse============>>" + response.toString());
                        mGatherFeeStatistics carLocusAnaly = null;

                        GetReceivablesListResponse analysisRequest = JsonUtils.parseData(response.toString(), GetReceivablesListResponse.class);

                        Message msg = Message.obtain();
                        if (analysisRequest.Success) {
                            carLocusAnaly = analysisRequest.DataValue;
                            msg.what = Constants.REQUEST_SUCC;
                            msg.obj = carLocusAnaly;
                            mHandler.sendMessage(msg);
                        } else {
                            msg.what = Constants.REQUEST_FAIL;
                            mHandler.sendMessage(msg);

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                        LogUtil.i("ErrorListener============>>" + TMSService.GetReceivableAccount_Request_Url);
                        Message msg = Message.obtain();
                        msg.what = Constants.REQUEST_ERROR;
                        mHandler.sendMessage(msg);
                    }
                });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            sweetAlertDialog.dismiss();
            lv.onRefreshComplete();

            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.REQUEST_SUCC:

                    mGatherFeeStatistics datalist = (mGatherFeeStatistics) msg.obj;
                    if (datalist.GatherFeeStatistics.size()>0&&datalist.GatherFeeList.size()>0){
                        List<GatherFeeStatistics> GatherFee = datalist.GatherFeeStatistics;
                        if (GatherFee != null && GatherFee.size() > 0) {
                            for (int i = 0; i < GatherFee.size(); i++) {
                                SetViewValueUtil.setTextViewValue(FeeText, GatherFee.get(i).Fee);
                                SetViewValueUtil.setTextViewValue(GatherFeeText, GatherFee.get(i).Gather_Fee);
                                SetViewValueUtil.setTextViewValue(SurplusGatherFeeText, GatherFee.get(i).Surplus_Gather_Fee);
                            }
                        }
                        List<GatherFeeList> gatherFeeList = datalist.GatherFeeList;
                        if (gatherFeeList.size() > 0) {
                            if (isRefresh) {
                                mGatherFeeList.clear();
                                isRefresh = false;
                            }
                            emptyTv.setVisibility(View.GONE);
                            mGatherFeeList.addAll(gatherFeeList);
                            adapter.notifyDataSetChanged();
                        }else {
                            emptyTv.setVisibility(View.VISIBLE);
                        }

                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;

                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(DriverAccountsReceiveListActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };

    @OnClick({R.id.back_llayout, R.id.operation_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.operation_btn:
                popupMenu.showLocation(R.id.operation_btn);// 设置弹出菜单弹出的位置
                // 设置回调监听，获取点击事件
                popupMenu.setOnItemClickListener(new DriverAccountsReceiveListPopuMenu.OnItemClickListener() {
                    @Override
                    public void onClick(DriverAccountsReceiveListPopuMenu.MENUITEM item, String str) {
                        sweetAlertDialog.show();
                        if (!str.equals("")) {
                            request.Gather_Status = str;
                            mGatherFeeList.clear();
                            adapter.notifyDataSetChanged();
                            onRefresh();
                        } else {
                            request.BeginDate = mIntent.getStringExtra("bengTime");
                            request.EndDate = mIntent.getStringExtra("endsTime");
                            request.Gather_Status = "";
                            mGatherFeeList.clear();
                            adapter.notifyDataSetChanged();
                            onRefresh();
                        }
                    }
                });
                break;
        }
    }
}
