package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics;

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
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.storehouse.StoreSelectOrgActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.employee.home.ArriveCarMonitorModel;
import com.cvnavi.logistics.i51ehang.app.bean.employee.home.statics.StoreSelectOrgModel;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
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
import butterknife.OnClick;
import volley.VolleyManager;


/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午3:59
 * 描述：到车监控
 ************************************************************************************/

public class ArriveCarMonitorActivity extends BaseActivity {
    @BindView(R.id.back_ll)
    LinearLayout backLl;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.data_search_text)
    TextView dataSearchText;
    @BindView(R.id.data_search_img)
    ImageView dataSearchImg;
    @BindView(R.id.data_search_layout)
    LinearLayout dataSearchLayout;
    @BindView(R.id.noData_view)
    FrameLayout noDataView;
    @BindView(R.id.listview)
    PullToRefreshListView listview;
    @BindView(R.id.arrow)
    LinearLayout arrow;
    @BindView(R.id.root_select)
    RelativeLayout rootSelect;
    @BindView(R.id.top_bar_line)
    ImageView topBarLine;
    @BindView(R.id.select_top_line)
    ImageView selectTopLine;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.sum)
    TextView sum;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    private int pageInt = 1;//分页num
    private boolean isRefresh = false;//是否刷新过了
    private DataRequestBase dataRequestBase;//请求类
    private ArriveCarMonitorAdapter addressAdapter;
    private List<ArriveCarMonitorModel.DataValueBean> list;

    private String Org_Code;//机构号
    private String Org_Key;//机构key

    /**
     * 启动方法
     *
     * @param context
     */
    public static void start(Context context) {
        Intent starter = new Intent(context, ArriveCarMonitorActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrive_car_monitor);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        title.setText("到车监控");

        noDataTv.setTextColor(0xffffffff);
        //防止topbar和筛选框之间有间距
        topBarLine.setVisibility(View.GONE);
        selectTopLine.setVisibility(View.VISIBLE);
        //设置筛选框的背景
        rootSelect.setBackgroundColor(0x2b000000);
        //箭头指向
        arrow.setVisibility(View.VISIBLE);
        //选择图片的隐藏
        dataSearchLayout.setVisibility(View.GONE);
        //设置筛选文字的颜色
        dataSearchText.setTextColor(0xffffffff);

        Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        Org_Key = MyApplication.getInstance().getLoginInfo().DataValue.Org_Key;

        dataSearchText.setText(MyApplication.getInstance().getLoginInfo().DataValue.Org_Name);
        list = new ArrayList<>();
        //获取从哪个界面进来的类型
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(ArriveCarMonitorActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
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

    /**
     * 请求数据
     */
    private void getDestinatInfo() {
        showLoading();
        if (dataRequestBase == null) {
            dataRequestBase = new DataRequestBase();
        }
        dataRequestBase.Page = pageInt;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = Org_Code;
        dataRequestBase.Org_Key = Org_Key;
        /**
         * 传的请求参数
         * {"User_Key":"660927d2-3ae0-4fa8-9813-ae21fc448f5d",
         * "Org_Code":"101","UserType_Oid":"G","page":"1","limit":"2",
         * "Company_Oid":"210106",
         * "Token":"46414075-23e1-421c-9f89-c97dbff64ff2",
         * "ActionSystem":"APP"}
         */

        MLog.json(GsonUtil.newInstance().toJsonStr(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(EmployeeService.GetMonitorLineInfo_Tag, EmployeeService.GetMonitorLineInfo_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MLog.json(response.toString());
                dissLoading();
                listview.onRefreshComplete();
                ArriveCarMonitorModel result = GsonUtil.newInstance().fromJson(response, ArriveCarMonitorModel.class);
                if (result != null && result.isSuccess() && result.getDataValue() != null) {
                    if (isRefresh) {
                        isRefresh = false;
                        list.clear();
                    }

                    //判断是否第一条，没有数据显示暂无数据
                    if (list != null && list.size() == 0 && result.getDataValue() != null && result.getDataValue().size() == 0) {
                        noDataView.setVisibility(View.VISIBLE);
                        listview.setVisibility(View.GONE);

                    } else {
                        noDataView.setVisibility(View.GONE);
                        listview.setVisibility(View.VISIBLE);
                        list.addAll(result.getDataValue());
                        if (addressAdapter == null) {
                            addressAdapter = new ArriveCarMonitorAdapter(ArriveCarMonitorActivity.this, list);
                            listview.setAdapter(addressAdapter);
                        } else {
                            addressAdapter.notifyDataSetChanged();
                        }

                        if (list != null && list.size() > 0) {
                            sum.setText("总车辆：" + list.get(0).getSum() + "辆");
                        } else {
                            sum.setText("总车辆：" + "--");
                        }

                    }

                } else {
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(ArriveCarMonitorActivity.this, "获取数据失败", new CustomDialogListener() {
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
                DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(ArriveCarMonitorActivity.this, "网络异常", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        finish();
                    }
                });
            }
        });
    }

    @OnClick({R.id.back_ll, R.id.root_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.root_select:
                //启动选择机构列表
                StoreSelectOrgActivity.startActivity(this, 0x12);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reRresh(StoreSelectOrgModel.DataValueBean data) {
        if (data != null) {
            Org_Code = data.getOrg_Code().trim();
            Org_Key = data.getOrg_Key().trim();
            //重置刷新状态
            isRefresh = true;
            pageInt = 1;
            dataSearchText.setText(data.getOrg_Name());
            getDestinatInfo();
        }


    }

    @OnClick(R.id.root_select)
    public void onClick() {
    }
}
