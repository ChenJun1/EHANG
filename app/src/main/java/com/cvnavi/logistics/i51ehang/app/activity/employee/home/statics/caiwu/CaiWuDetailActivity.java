package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.caiwu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetStowageStatisticsSummaryRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.CaiWuDetailModel;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
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
*创建时间：2017/1/16 下午3:50
*描述：财务明细
************************************************************************************/

public class CaiWuDetailActivity extends BaseActivity {

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
    @BindView(R.id.noData_view)
    FrameLayout noDataView;
    @BindView(R.id.lv)
    PullToRefreshListView lv;
    private String startTime;
    private String endTime;
    private int pageInt = 1;//分页num
    private boolean isRefresh = false;//是否刷新过了
    private DataRequestBase dataRequestBase;//请求类
    private CaiWuDetailAdapter adapter;
    private List<CaiWuDetailModel.DataValueBean> list = new ArrayList<>();


    public static void start(Context context, String startTime, String endTime) {
        Intent starter = new Intent(context, CaiWuDetailActivity.class);
        starter.putExtra("start", startTime);
        starter.putExtra("end", endTime);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_wu_detail);
        ButterKnife.bind(this);
        noDataTv.setTextColor(0xffffffff);
        topBarLine.setVisibility(View.GONE);
        title.setText("财务明细");

        startTime = getIntent().getStringExtra("start");
        endTime = getIntent().getStringExtra("end");

        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(CaiWuDetailActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isRefresh = true;
                pageInt = 1;
                //执行刷新
                getDestinatInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                //执行刷新
                getDestinatInfo();
            }
        });
        getDestinatInfo();
    }

    @OnClick(R.id.back_ll)
    public void onClick() {
        finish();
    }


    /**
     * 请求数据
     */
    private void getDestinatInfo() {
        showLoading();
        if (dataRequestBase == null) {
            dataRequestBase = new DataRequestBase();
        }

        GetStowageStatisticsSummaryRequest request = new GetStowageStatisticsSummaryRequest();
        request.BeginDate = startTime;
        request.EndDate = endTime;
        dataRequestBase.DataValue = request;

        dataRequestBase.Page = pageInt;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        /**
         * 传的请求参数
         * {"User_Key":"660927d2-3ae0-4fa8-9813-ae21fc448f5d",
         * "Org_Code":"101","UserType_Oid":"G","page":"1","limit":"2",
         * "Company_Oid":"210106",
         * "Token":"46414075-23e1-421c-9f89-c97dbff64ff2",
         * "ActionSystem":"APP"}
         */

        MLog.json(GsonUtil.newInstance().toJsonStr(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(EmployeeService.GetStatisticsTicket_Url, EmployeeService.GetStatisticsTicket_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MLog.json(response.toString());
                dissLoading();
                lv.onRefreshComplete();
                CaiWuDetailModel result = GsonUtil.newInstance().fromJson(response, CaiWuDetailModel.class);
                if (result != null && result.isSuccess() && result.getDataValue() != null) {
                    if (isRefresh) {
                        isRefresh = false;
                        list.clear();
                    }
                    if (list != null && list.size() == 0 && result.getDataValue() != null && result.getDataValue().size() == 0) {
                        noDataView.setVisibility(View.VISIBLE);
                        lv.setVisibility(View.GONE);
                    } else {
                        noDataView.setVisibility(View.GONE);
                        lv.setVisibility(View.VISIBLE);
                        list.addAll(result.getDataValue());
                        if (adapter == null) {
                            adapter = new CaiWuDetailAdapter(CaiWuDetailActivity.this, list);
                            lv.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(CaiWuDetailActivity.this, "获取数据失败", new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            finish();
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dissLoading();
                lv.onRefreshComplete();
                DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(CaiWuDetailActivity.this, "网络异常", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        finish();
                    }
                });
            }
        });
    }


}
