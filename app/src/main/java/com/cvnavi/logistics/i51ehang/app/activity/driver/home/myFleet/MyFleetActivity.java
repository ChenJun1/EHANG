package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.myFleet.MyFleetAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetmonitor.MyFleetMonitorMapActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverAddCarSchedulingActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.employee.home.carfleet.GetMyFleetRequest;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetMyCarFleetResponse;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.order.MyOrderListener;
import com.cvnavi.logistics.i51ehang.app.config.LPSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.activity.BaseSwipeBackActivity;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.popupwindow.MyPopWindow;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午10:13
*描述：我的车队
************************************************************************************/

public class MyFleetActivity extends BaseSwipeBackActivity implements MyOnClickItemListener, MyOrderListener {

    @BindView(R.id.zaitu_num_tv)
    TextView zaituNumTv;
    @BindView(R.id.kongxian_num_tv)
    TextView kongxianNumTv;
    @BindView(R.id.total_num_tv)
    TextView totalNumTv;
    @BindView(R.id.list)
    PullToRefreshListView lv;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.check_tv)
    TextView checkTv;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.custom_ll)
    LinearLayout customLl;
    @BindView(R.id.ctrol_ll)
    LinearLayout ctrolLl;
    @BindView(R.id.plan_num_tv)
    TextView planNumTv;
    @BindView(R.id.arrive_num_tv)
    TextView arriveNumTv;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;
    @BindView(R.id.rl_zaitu)
    RelativeLayout rlZaitu;
    @BindView(R.id.rl_kongxian)
    RelativeLayout rlKongxian;
    @BindView(R.id.rl_plan)
    RelativeLayout rlPlan;
    @BindView(R.id.rl_arrive)
    RelativeLayout rlArrive;
    @BindView(R.id.rl_all)
    RelativeLayout rlAll;
    @BindView(R.id.tv_zaitu)
    TextView tvZaitu;
    @BindView(R.id.tv_kongxian)
    TextView tvKongxian;
    @BindView(R.id.tv_plan)
    TextView tvPlan;
    @BindView(R.id.tv_arrive)
    TextView tvArrive;
    @BindView(R.id.tv_all)
    TextView tvAll;

    private MyFleetAdapter adapter;
    private String Org_Code = "";
    private SweetAlertDialog loading;
    private GetMyCarFleetResponse allInfo;
    private ArrayList<RelativeLayout> rlLIst = new ArrayList<>();
    private ArrayList<TextView> numList = new ArrayList<>();
    private ArrayList<TextView> tvList = new ArrayList<>();
    private String CarStatus_Oid = "0";
    private int function = 0;

    /**
     * 启动方式
     *
     * @param activity
     * @param function
     */
    public static void startActivity(Activity activity, int function) {
        Intent intent = new Intent(activity, MyFleetActivity.class);
        intent.putExtra("function", function);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fleet);
        ButterKnife.bind(this);
        titltTv.setText("我的车队");
        checkTv.setVisibility(View.INVISIBLE);
        add.setVisibility(View.INVISIBLE);
        customLl.setVisibility(View.VISIBLE);
        addView();
        function = getIntent().getIntExtra("function", 0);
        select(function);

        /**
         * public void setPullLabel(CharSequence pullLabel) {
         mPullLabel = pullLabel;
         }
         public void setRefreshingLabel(CharSequence refreshingLabel) {
         mRefreshingLabel = refreshingLabel;
         }
         public void setReleaseLabel(CharSequence releaseLabel) {
         mReleaseLabel = releaseLabel;
         }
         <string name="pull_to_refresh_pull_label">下拉刷新…</string>
         <string name="pull_to_refresh_release_label">释放刷新…</string>
         <string name="pull_to_refresh_refreshing_label">加载…</string>

         */

        //更改下拉刷新的字体模板
//        ForegroundColorSpan pullcolorSpan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
//        SpannableString pullSpan = new SpannableString("下拉刷新…");
//        pullSpan.setSpan(pullcolorSpan, 0, pullSpan.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//        lv.setPullLabel(pullSpan);
//        SpannableString release = new SpannableString("释放刷新…");
//        release.setSpan(pullcolorSpan, 0, release.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//        lv.setReleaseLabel(release);
//        SpannableString refresh = new SpannableString("加载…");
//        refresh.setSpan(pullcolorSpan, 0, refresh.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//        lv.setReleaseLabel(refresh);
//
//        String label = DateUtils.formatDateTime(MyFleetActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//        SpannableString time = new SpannableString(label);
//        time.setSpan(pullcolorSpan,0,time.length(),Spannable.SPAN_EXCLUSIVE_INCLUSIVE);


        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(MyFleetActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                getMyCarListInfo(CarStatus_Oid);
            }
        });
    }

    private void addView() {
        rlLIst.add(rlZaitu);
        rlLIst.add(rlKongxian);
        rlLIst.add(rlPlan);
        rlLIst.add(rlArrive);
        rlLIst.add(rlAll);
        tvList.add(tvZaitu);
        tvList.add(tvKongxian);
        tvList.add(tvPlan);
        tvList.add(tvArrive);
        tvList.add(tvAll);
        numList.add(zaituNumTv);
        numList.add(kongxianNumTv);
        numList.add(planNumTv);
        numList.add(arriveNumTv);
        numList.add(totalNumTv);
    }

    /**
     * 获取车辆信息
     *
     * @param CarStatus_Oid
     */
    private void getMyCarListInfo(String CarStatus_Oid) {
        showLoading();
        GetMyFleetRequest dataValue = new GetMyFleetRequest();
        dataValue.CarStatus_Oid = CarStatus_Oid;
        this.CarStatus_Oid = CarStatus_Oid;

        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.DataValue = dataValue;
        VolleyManager.newInstance().PostJsonRequest(LPSService.GetMyCarFleet_TAG, LPSService.GetMyCarFleet_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MLog.json(response.toString());
                GetMyCarFleetResponse dataInfo = GsonUtil.newInstance().fromJson(response, GetMyCarFleetResponse.class);
                Message msg = Message.obtain();
                if (dataInfo != null && dataInfo.Success) {
                    msg.what = Constants.REQUEST_SUCC;
                    msg.obj = dataInfo;
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                    msg.obj = dataInfo;
                }
                myHangler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                myHangler.sendMessage(msg);
            }
        });
    }

    private Handler myHangler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dissLoading();
            lv.onRefreshComplete();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        allInfo = (GetMyCarFleetResponse) msg.obj;
                        setView(allInfo);
                    } else {
                        DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetActivity.this, Utils.getResourcesString(R.string.get_data_fail), new CustomDialogListener() {
                            @Override
                            public void onDialogClosed(int closeType) {
                                finish();
                            }
                        });
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetActivity.this, Utils.getResourcesString(R.string.get_data_fail), new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            finish();
                        }
                    });
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetActivity.this, Utils.getResourcesString(R.string.get_data_fail), new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            finish();
                        }
                    });
                    break;
            }


        }
    };


    private void setView(GetMyCarFleetResponse info) {
        zaituNumTv.setText(info.getDataValue().getMoveCarSum());
        kongxianNumTv.setText(info.getDataValue().getFreeCarSum());
        totalNumTv.setText(info.getDataValue().getTotalCarSum());
        planNumTv.setText(info.getDataValue().getAlreadyplanSum());
        arriveNumTv.setText(info.getDataValue().getWillArriveSum());
        adapter = new MyFleetAdapter(MyFleetActivity.this, info.getDataValue().getMCarInfoList(), this, this);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void myOnClickItem(int position, Object data) {
        GetMyCarFleetResponse.DataValueBean.MCarInfoListBean info = (GetMyCarFleetResponse.DataValueBean.MCarInfoListBean) data;
        if (info != null) {
            MyFleetLocationInfoActivity.startActivity(MyFleetActivity.this, info);
        }
    }

    @OnClick({R.id.check_tv, R.id.add, R.id.ctrol_ll, R.id.back_llayout, R.id.rl_zaitu, R.id.rl_kongxian, R.id.rl_plan, R.id.rl_arrive, R.id.rl_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.check_tv:
                MyFleetCarTreeListActivity.startActivity(MyFleetActivity.this, 0x12);
                break;
            case R.id.ctrol_ll:
                //车辆监控
                if (allInfo != null && allInfo.getDataValue() != null && allInfo.getDataValue().getMCarInfoList() != null) {
                    MyFleetMonitorMapActivity.start(MyFleetActivity.this, allInfo.getDataValue().getMCarInfoList());
                }
                break;
            case R.id.add:
                ArrayList<String> item = new ArrayList<>();
                item.add("新建计划");

                MyPopWindow popWindow = new MyPopWindow(MyFleetActivity.this, item);
                popWindow.showLocation(R.id.add);
                popWindow.setOnItemClickListener(new MyPopWindow.OnItemClickListener() {
                    @Override
                    public void onClick(int pos) {
                        startActivity(new Intent(MyFleetActivity.this, DriverAddCarSchedulingActivity.class));
                    }
                });

                break;
            case R.id.rl_zaitu:
                select(0);
                break;
            case R.id.rl_kongxian:
                select(1);
                break;
            case R.id.rl_plan:
                select(2);
                break;
            case R.id.rl_arrive:
                select(3);
                break;
            case R.id.rl_all:
                select(4);
                break;
        }
    }

    private void select(int pos) {
        if (rlLIst != null && rlLIst.size() > 0) {
            rlLIst.get(pos).setBackgroundColor(Utils.getResourcesColor(R.color.color_3A95E7));
            numList.get(pos).setTextColor(Utils.getResourcesColor(R.color.color_ffffff));
            tvList.get(pos).setTextColor(Utils.getResourcesColor(R.color.color_ffffff));
            for (int i = 0; i < rlLIst.size(); i++) {
                if (pos != i) {
                    rlLIst.get(i).setBackgroundColor(Utils.getResourcesColor(R.color.color_ffffff));
                    numList.get(i).setTextColor(Utils.getResourcesColor(R.color.color_3A95E7));
                    tvList.get(i).setTextColor(Utils.getResourcesColor(R.color.color_000000));
                }
            }
        }
        getMyCarListInfo(pos + "");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0x12) {
            Org_Code = data.getStringExtra("Org_Code").trim();
        }

    }

    @Override
    public void onClickOrder(int position) {
        //跳转到车辆预警
        startActivity(new Intent(this, MyFleetCarStatusExplainActivity.class));
    }


}
