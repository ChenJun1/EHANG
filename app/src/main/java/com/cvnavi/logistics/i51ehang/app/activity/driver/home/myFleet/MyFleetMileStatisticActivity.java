package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.myFleet.MyFleetMileStaticstAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.SearchActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverCarSchedulingSearchActivity;
import com.cvnavi.logistics.i51ehang.app.bean.employee.home.statics.MileStaticsResponse;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetMileagesRequest;
import com.cvnavi.logistics.i51ehang.app.config.LPSService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.popupwindow.MyPopWindow;
import com.cvnavi.logistics.i51ehang.app.widget.markview.MyMarkView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
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
*创建时间：2017/1/17 上午10:20
*描述：我的车队的里程统计
************************************************************************************/

public class MyFleetMileStatisticActivity extends BaseActivity implements OnChartValueSelectedListener {
    public static final String Intent_INFO_CARCODE_KEY = "Intent_INFO_CARCODE_KEY";
    public static final String Intent_INFO_CARCODE = "Intent_INFO_CARCODE";
    public static final String Intent_INFO_TSP_CarCode_Key = "Intent_INFO_TSP_CarCode_Key";
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.check_tv)
    TextView checkTv;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.custom_ll)
    LinearLayout customLl;
    @BindView(R.id.data_search_text)
    TextView dataSearchText;
    @BindView(R.id.data_search_img)
    ImageView dataSearchImg;
    @BindView(R.id.data_search_layout)
    LinearLayout dataSearchLayout;
    @BindView(R.id.bar_chart)
    BarChart barChart;
    @BindView(R.id.list)
    PullToRefreshListView list;
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
    @BindView(R.id.total_tv)
    TextView totalTv;

    private String startTime;
    private String endTime;
    private String carCodeKey;
    private String carCode;
    private String TSP_CarCode_Key;

    private MyFleetMileStaticstAdapter adapter;
    private SweetAlertDialog loading;

    /**
     * 启动方法
     * @param activity
     * @param carCode_Key
     * @param carCode
     */
    public static void startActivity(Activity activity, String carCode_Key, String carCode, String TSP_CarCode_Key) {
        Intent intent = new Intent(activity, MyFleetMileStatisticActivity.class);
        intent.putExtra(Intent_INFO_CARCODE_KEY, carCode_Key);
        intent.putExtra(Intent_INFO_CARCODE, carCode);
        intent.putExtra(Intent_INFO_TSP_CarCode_Key, TSP_CarCode_Key);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fleet_mile_statics);
        ButterKnife.bind(this);
        initData();
        initChart();
        requestForData(startTime, endTime, carCodeKey);
    }

    private void initData() {
        rightLl.setVisibility(View.INVISIBLE);
        addLl.setVisibility(View.INVISIBLE);
        searchLl.setVisibility(View.VISIBLE);
        startTime = DateUtil.getLastNDaysForm(-7, DateUtil.FORMAT_YMDHM);
        endTime = DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM);
        dataSearchText.setText("最近一周");
        carCodeKey = getIntent().getStringExtra(Intent_INFO_CARCODE_KEY);
        carCode = getIntent().getStringExtra(Intent_INFO_CARCODE);
        TSP_CarCode_Key = getIntent().getStringExtra(Intent_INFO_TSP_CarCode_Key);
        titltTv.setText(carCode);
    }

    /**
     * 初始化图表
     */
    private void initChart() {
        barChart.setOnChartValueSelectedListener(MyFleetMileStatisticActivity.this);
        barChart.setDescription(""/*mDataSource.getDescription()*/);
        barChart.setDescriptionTextSize(30);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);

        // drawn
//        barChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setExtraOffsets(0, 0, 15, 0);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);


        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(5f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(5f);
        rightAxis.setDrawLabels(false);
        rightAxis.setEnabled(false);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        barChart.setDoubleTapToZoomEnabled(false);//屏蔽双击放大
        barChart.setVisibleXRangeMaximum(8);


        MyMarkView myMarkView = new MyMarkView(this, R.layout.custom_marker_view, null);
        myMarkView.getXOffset(-myMarkView.getMeasuredWidth() / 2);
        myMarkView.getYOffset(-myMarkView.getMeasuredHeight());
        barChart.setMarkerView(myMarkView);

        /**
         * 最下方的标注
         */
        Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

    }

    /**
     * 请求数据
     * @param starTime
     * @param endTime
     * @param carCode_Key
     */
    private void requestForData(String starTime, String endTime, String carCode_Key) {
        if (TextUtils.isEmpty(carCodeKey)) {
            return;
        }
        if (TextUtils.isEmpty(starTime) || TextUtils.isEmpty(endTime)) {
            return;
        }
        loading = new SweetAlertDialog(MyFleetMileStatisticActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        loading.show();
        GetMileagesRequest dataValue = new GetMileagesRequest();
        dataValue.CarCode_Key = carCode_Key;
        dataValue.StartTime = starTime;
        dataValue.EndTime = endTime;
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = dataValue;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;

        VolleyManager.newInstance().PostJsonRequest(LPSService.GetMileages_TAG, LPSService.GetMileages_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->> response = " + response.toString());
                MileStaticsResponse info = GsonUtil.newInstance().fromJson(response, MileStaticsResponse.class);
                Message msg = Message.obtain();
                if (info != null && info.isSuccess()) {
                    msg.what = Constants.REQUEST_SUCC;
                    msg.obj = info;
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                }
                handler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                handler.sendMessage(msg);
            }
        });


    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            loading.dismiss();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    MileStaticsResponse info = (MileStaticsResponse) msg.obj;
                    if (info != null && info.getDataValue() != null && info.getDataValue().size() > 0) {
                        totalTv.setText(info.getDataValue().get(0).getDistanceGPS() + "公里");
                        adapter = new MyFleetMileStaticstAdapter(info.getDataValue().get(0).getDetail(), MyFleetMileStatisticActivity.this);
                        list.setAdapter(adapter);
                        refreshChart(info.getDataValue().get(0).getDetail());
                    } else {
                        if (info.getDataValue() != null) {
                            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetMileStatisticActivity.this, Utils.getResourcesString(R.string.mile_list_null), new CustomDialogListener() {
                                @Override
                                public void onDialogClosed(int closeType) {
                                    if (closeType == CustomDialogListener.BUTTON_OK) {
                                        finish();
                                    }
                                }
                            });
                        } else {
                            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetMileStatisticActivity.this, Utils.getResourcesString(R.string.get_data_fail), new CustomDialogListener() {
                                @Override
                                public void onDialogClosed(int closeType) {
                                    if (closeType == CustomDialogListener.BUTTON_OK) {
                                        finish();
                                    }
                                }
                            });
                        }
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetMileStatisticActivity.this, Utils.getResourcesString(R.string.mile_list_error), new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            if (closeType == CustomDialogListener.BUTTON_OK) {
                                finish();
                            }

                        }
                    });
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetMileStatisticActivity.this, Utils.getResourcesString(R.string.request_error), new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            if (closeType == CustomDialogListener.BUTTON_OK) {
                                finish();
                            }

                        }
                    });
                    break;
            }
        }
    };

    /**
     * 刷新图表
     * 每次请求网络重绘界面
     *
     * @param listMileage
     */
    private void refreshChart(List<MileStaticsResponse.DataValueBean.DetailBean> listMileage) {
        // 加载数据
        setData(listMileage);
        //从X轴进入的动画
        barChart.animateX(2000);

        //设置最小的缩放
        barChart.setScaleMinima(0.5f, 1f);
        //设置视口
        // barChart.centerViewPort(10, 50);

        //设置图最下面显示的类型
        // get the legend (only possible after setting data)
//        Legend l = barChart.getLegend();
//        l.setForm(Legend.LegendForm.LINE);
//        l.setTextSize(30);
//        l.setTextColor(Color.rgb(244, 117, 117));
//        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
//        l.setYOffset(660);
//        l.setFormSize(20f); // set the size of the legend forms/shapes

        // 刷新图表
        barChart.invalidate();
    }


    private void setData(List<MileStaticsResponse.DataValueBean.DetailBean> listMileage) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < listMileage.size(); i++) {
            xVals.add(listMileage.get(i).getStartTime());
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < listMileage.size(); i++) {
            float y = Float.parseFloat(listMileage.get(i).getDistanceGPS());
            yVals1.add(new BarEntry(y, i));//填充数据
        }

        BarDataSet set1;
        barChart.animateY(2000);//设置动画
        set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);
        set1.setColors(ColorTemplate.LIBERTY_COLORS);


        BarDataSet dataSets = new BarDataSet(yVals1, "里程统计");
        List<Integer> list = new ArrayList<Integer>();
        list.add(Color.rgb(179, 48, 80));//设置颜色
        list.add(Color.rgb(106, 167, 134));
        list.add(Color.rgb(53, 194, 209));
        list.add(Color.rgb(118, 174, 175));
        list.add(Color.rgb(42, 109, 130));
        list.add(Color.rgb(106, 150, 31));
        list.add(Color.rgb(179, 100, 53));
        list.add(Color.rgb(193, 37, 82));
        list.add(Color.rgb(255, 102, 0));
        list.add(Color.rgb(217, 80, 138));
        list.add(Color.rgb(254, 149, 7));
        list.add(Color.rgb(254, 247, 120));
        dataSets.setColors(list);
        //不显示y值
        dataSets.setDrawValues(false);
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        barChart.setData(data);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SearchActivity.REQUEST_CODE) {
            startTime = data.getStringExtra(DriverCarSchedulingSearchActivity.BEGIN_DATA);
            endTime = data.getStringExtra(DriverCarSchedulingSearchActivity.END_DATA);
            dataSearchText.setText(startTime + "~" + endTime);
            requestForData(startTime, endTime, carCodeKey);
        } else {
            startTime = null;
            endTime = null;
        }
    }

    @OnClick({R.id.back_llayout, R.id.data_search_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.data_search_layout:
                MyPopWindow popWindow = new MyPopWindow(MyFleetMileStatisticActivity.this, new ArrayList<String>() {{
                    add("最近一天");
                    add("最近三天");
                    add("最近一周");
                    add("自定义查询");
                }});

                popWindow.showLocation(R.id.data_search_img);
                popWindow.setOnItemClickListener(new MyPopWindow.OnItemClickListener() {
                    @Override
                    public void onClick(int pos) {
                        switch (pos) {
                            case 0:
                                dataSearchText.setText("最近一天");
                                requestForData(DateUtil.getLastNDaysForm(-1, DateUtil.FORMAT_YMDHM), DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM), carCodeKey);
                                break;
                            case 1:
                                dataSearchText.setText("最近三天");
                                requestForData(DateUtil.getLastNDaysForm(-3, DateUtil.FORMAT_YMDHM), DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM), carCodeKey);
                                break;
                            case 2:
                                dataSearchText.setText("最近一周");
                                requestForData(DateUtil.getLastNDaysForm(-7, DateUtil.FORMAT_YMDHM), DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM), carCodeKey);
                                break;
                            case 3:
                                SearchActivity.startActivity(MyFleetMileStatisticActivity.this, SearchActivity.REQUEST_CODE, DateUtil.FORMAT_YMDHM, 30);
                                break;
                        }

                    }
                });
                break;
        }
    }

    @Override
    public void onValueSelected(Entry entry, int i, Highlight highlight) {

    }

    @Override
    public void onNothingSelected() {

        /**
         * android 隐式启动 隐式的意图 需要设置intent-filter 过滤器 设置 action（具体要的动作），category(启动类别)，或者date（scheme）
         * android 显示启动 指定要打开的活动，意图明显 不需要设置intent-filter 的action  category
         *
         * android 启动模式  有四种 singletop singleinstance singletask stand
         *
         * singletop ; 返回栈中 如果有在栈顶不需要创建，
         *
         *1当前栈中已有该Activity的实例并且该实例位于栈顶时，不会新建实例，而是复用栈顶的实例，并且会将Intent对象传入，回调onNewIntent方法
         *2当前栈中已有该Activity的实例但是该实例不在栈顶时，其行为和standard启动模式一样，依然会创建一个新的实例
         *3当前栈中不存在该Activity的实例时，其行为同standard启动模式
         *
         * singleTask
         * 1如果存在实例，则将它上面的Activity实例都出栈，然后回调启动的Activity实例的onNewIntent方法
         * 2如果不存在该实例，则新建Activity，并入栈
         *
         *
         * singleinstance
         *
         * 这种模式下的Activity 单独有一个栈
         *
         *
         *
         *
         *
         *
         *
         *
         */

    }

}