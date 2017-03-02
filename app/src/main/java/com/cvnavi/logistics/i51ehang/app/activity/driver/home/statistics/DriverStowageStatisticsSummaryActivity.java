package com.cvnavi.logistics.i51ehang.app.activity.driver.home.statistics;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.statistics.CarPlanStaticsAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverCarSchedulingSearchActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.StowageStatisticsSummary.DetailList;
import com.cvnavi.logistics.i51ehang.app.bean.model.StowageStatisticsSummary.mStowageStatisticsSummary;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetStowageStatisticsSummaryRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetStowageStatisticsSummaryResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.popupwindow.MyPopWindow;
import com.cvnavi.logistics.i51ehang.app.widget.gridview.MyGridView;
import com.cvnavi.logistics.i51ehang.app.widget.segmentview.SegmentView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * Created by fan on 2016/7/21.
 * 发车统计
 */
public class DriverStowageStatisticsSummaryActivity extends BaseActivity {
    private static final int F_REQUEST_CODE = 0x2;
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
    @BindView(R.id.select_top_line)
    ImageView selectTopLine;
    @BindView(R.id.data_search_text)
    TextView dataSearchText;
    @BindView(R.id.data_search_img)
    ImageView dataSearchImg;
    @BindView(R.id.data_search_layout)
    LinearLayout dataSearchLayout;
    @BindView(R.id.arrow)
    LinearLayout arrow;
    @BindView(R.id.root_select)
    RelativeLayout rootSelect;
    @BindView(R.id.mSegmentView)
    SegmentView mSegmentView;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.myGridView)
    MyGridView myGridView;


    private boolean mChoose = true;// 判断是否是条件选择查询
    private int NearDate = 7;// 初始请求一周
    private String mStarTime; //开始时间
    private String mEendTime;//结束时间
    private CarPlanStaticsAdapter myAdapter;
    private mStowageStatisticsSummary sList;
    private SweetAlertDialog sweetAlertDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deiver_stowage_statistics_summary);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sweetAlertDialog.show();
        requestHttp();
    }

    private void init() {
        title.setText("发车统计");
        rightTv.setText("明细");
        rightTv.setVisibility(View.VISIBLE);

        //防止topbar和筛选框之间有间距
        topBarLine.setVisibility(View.GONE);
        selectTopLine.setVisibility(View.VISIBLE);
        //设置筛选框的背景
        rootSelect.setBackgroundColor(0x2b000000);
        //选择图片的隐藏
        dataSearchLayout.setVisibility(View.VISIBLE);
        //设置筛选文字的颜色
        dataSearchText.setTextColor(0xffffffff);

        dataSearchText.setText(DateUtil.getNowTime(DateUtil.FORMAT_YM_CN));
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mStarTime = DateUtil.getStartYMTime(DateUtil.FORMAT_YMDHM, 0);
        mEendTime = DateUtil.getEndYMTime(DateUtil.FORMAT_YMDHM, 0);
        mSegmentView.setOnSegmentViewClickListener(new SegmentView.onSegmentViewClickListener() {

            @Override
            public void onSegmentViewClick(TextView v, int position) {
                switch (position) {
                    case 0:
                        orderNearDate(3);
                        break;
                    case 1:
                        orderNearDate(7);
                        break;
                    case 2:
                        orderNearDate(30);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initChart() {
        lineChart.animateX(0);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGridColor(Utils.getResourcesColor(R.color.blue));
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float v, YAxis yAxis) {
                return new Float(v).intValue() + "";
            }
        });

        lineChart.setDescription("发车统计");
        lineChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
//        lineChart.getXAxis().setGridColor(getResources().getColor(R.color.transparent));//隐藏Y轴线
        lineChart.getXAxis().setGridColor(0x70ffffff);//隐藏Y轴线

        //从X轴进入的动画
        lineChart.animateX(1000);
        lineChart.animateY(1000);   //从Y轴进入的动画
        lineChart.animateXY(1000, 1000);    //从XY轴一起进入的动画
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        lineChart.getAxisLeft().setDrawAxisLine(true);//隐藏轴线只显示数字标签

//        MyMarkView myMarkView = new MyMarkView(this,R.layout.custom_marker_view);
//        myMarkView.getXOffset(-myMarkView.getMeasuredWidth()/2);
//        myMarkView.getYOffset(-myMarkView.getMeasuredHeight());
//        lineChart.setMarkerView(myMarkView);
        ArrayList<String> xValues = new ArrayList<>();
        ArrayList<Entry> yValue = new ArrayList<>();
        LineDataSet dataSet = new LineDataSet(yValue, "合计发车");
        if (sList.DetailList != null && sList.DetailList.size() > 0) {
            for (int i = 0; i < sList.DetailList.size(); i++) {
                DetailList list = sList.DetailList.get(i);
                xValues.add(DateUtil.strOldFormat2NewFormat(list.Letter_Date, DateUtil.FORMAT_YMD, DateUtil.FORMAT_MD));
                yValue.add(new Entry(list.Letter_Count, i));
            }
        }

        dataSet.setCircleSize(4f);
        dataSet.setLineWidth(1.75f); // 线宽
        dataSet.setDrawFilled(true);// 填充
        dataSet.setHighLightColor(Utils.getResourcesColor(R.color.palegreen));

        lineChart.getXAxis().setTextColor(0xffffffff);
        lineChart.setDescriptionColor(0xffffffff);
        lineChart.getLegend().setEnabled(false);
        dataSet.setValueTextColor(0xffffffff);
        yAxis.setTextColor(0xffffffff);
        dataSet.setColor(0xffffffff);
        dataSet.setColor(Utils.getResourcesColor(R.color.chat_line_color));//线的颜色
        dataSet.setFillColor(Utils.getResourcesColor(R.color.chat_fill_line_color));//填充背景的颜色
        dataSet.setCircleColor(Utils.getResourcesColor(R.color.chat_line_color));//点的 颜色


        dataSet.setDrawCircleHole(false);//设置中心不是空心的
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry entry, int i, ViewPortHandler viewPortHandler) {
                return new Float(v).intValue() + "";
            }
        });
        dataSet.setDrawCubic(true);  //设置曲线为圆滑的线
        dataSet.setValueTextSize(7f);//设置标注数据显示的大小
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(dataSet);

        //设置各个方向的偏移量 防止显示不全
        lineChart.setExtraOffsets(0, 0, 15, 0);

        LineData lineData = new LineData(xValues, dataSets);
        lineChart.setData(lineData);

    }

    @OnClick({R.id.back_ll, R.id.right_tv, R.id.data_search_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.right_tv:
                Intent intent = new Intent(DriverStowageStatisticsSummaryActivity.this, DriverStowageStatisticsListActivity.class);
                intent.putExtra("mStarTime", mStarTime);
                intent.putExtra("mEendTime", mEendTime);
                startActivity(intent);
                break;
            case R.id.data_search_layout:
                ArrayList<String> item = new ArrayList<>();
                item.add(DateUtil.strOldFormat2NewFormat(DateUtil.getStartYMTime(DateUtil.FORMAT_YMDHM, -2), DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_YM_CN));
                item.add(DateUtil.strOldFormat2NewFormat(DateUtil.getStartYMTime(DateUtil.FORMAT_YMDHM, -1), DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_YM_CN));
                item.add(DateUtil.strOldFormat2NewFormat(DateUtil.getStartYMTime(DateUtil.FORMAT_YMDHM, -0), DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_YM_CN));
                item.add("自定义");

                MyPopWindow popWindow = new MyPopWindow(DriverStowageStatisticsSummaryActivity.this, item);
                popWindow.showLocation(R.id.data_search_layout);
                popWindow.setOnItemClickListener(new MyPopWindow.OnItemClickListener() {
                    @Override
                    public void onClick(int pos) {
                        switch (pos) {
                            case 0:
                                orderNearDate(0);
                                break;
                            case 1:
                                orderNearDate(1);
                                break;
                            case 2:
                                orderNearDate(2);
                                break;
                            case 3:
                                DriverCarSchedulingSearchActivity.startActivity(DriverStowageStatisticsSummaryActivity.this, F_REQUEST_CODE);
                                break;
                        }

                    }
                });

                break;
        }
    }

    private void orderNearDate(int NearDate) {
        this.NearDate = NearDate;

        if (NearDate == 0) {
            dataSearchText.setText(DateUtil.strOldFormat2NewFormat(DateUtil.getStartYMTime(DateUtil.FORMAT_YMDHM, -2), DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_YM_CN));
            mStarTime = DateUtil.getStartYMTime(DateUtil.FORMAT_YMDHM, -2);
            mEendTime = DateUtil.getEndYMTime(DateUtil.FORMAT_YMDHM, -2);
        } else if (NearDate == 1) {
            dataSearchText.setText(DateUtil.strOldFormat2NewFormat(DateUtil.getStartYMTime(DateUtil.FORMAT_YMDHM, -1), DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_YM_CN));
            mStarTime = DateUtil.getStartYMTime(DateUtil.FORMAT_YMDHM, -1);
            mEendTime = DateUtil.getEndYMTime(DateUtil.FORMAT_YMDHM, -1);
        } else if (NearDate == 2) {
            dataSearchText.setText(DateUtil.strOldFormat2NewFormat(DateUtil.getStartYMTime(DateUtil.FORMAT_YMDHM, -0), DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_YM_CN));
            mStarTime = DateUtil.getStartYMTime(DateUtil.FORMAT_YMDHM, -0);
            mEendTime = DateUtil.getEndYMTime(DateUtil.FORMAT_YMDHM, -0);
        }

        mChoose = true;
        sweetAlertDialog.show();
        requestHttp();
    }


    private void requestHttp() {
        DataRequestBase requestBase = new DataRequestBase();

        final GetStowageStatisticsSummaryRequest request = new GetStowageStatisticsSummaryRequest();
        request.BeginDate = mStarTime;
        request.EndDate = mEendTime;

        requestBase.DataValue = request;
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;

        final JSONObject jsonObject = GsonUtil.newInstance().toJson(requestBase);
        VolleyManager.newInstance().PostJsonRequest("--->", TMSService.GetStowageStatisticsSummary_Request_Url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MLog.json(response.toString());

                GetStowageStatisticsSummaryResponse analysisRequest = JsonUtils.parseData(response.toString(), GetStowageStatisticsSummaryResponse.class);

                Message msg = Message.obtain();
                if (analysisRequest != null) {
                    if (analysisRequest.Success) {
                        if (analysisRequest.DataValue != null) {
                            sList = analysisRequest.DataValue;
                            msg.what = Constants.REQUEST_SUCC;
                            msg.obj = sList;
                            mHandler.sendMessage(msg);
                        }

                    } else {
                        msg.what = Constants.REQUEST_FAIL;
                        mHandler.sendMessage(msg);
                    }
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                    mHandler.sendMessage(msg);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", "ErrorListener============>>" + error.getMessage(), error);
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
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.REQUEST_SUCC:

                    mStowageStatisticsSummary datalist = (mStowageStatisticsSummary) msg.obj;
                    if (datalist != null) {
                        Success(datalist);
                    } else {
                        DialogUtils.showNormalToast("暂无数据");
                    }

                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;

                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(DriverStowageStatisticsSummaryActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };

    public void Success(mStowageStatisticsSummary mList) {

        if (mList != null) {
//            if (img_text != null && img_text.size() > 0) {
//                img_text.clear();
//            }
//
//            if (!TextUtils.isEmpty(mList.Letter_Count)) {
//                img_text.add(mList.Letter_Count);//合计发车
//            }
//            if (!TextUtils.isEmpty(mList.Shuttle_Fee)) {
//                img_text.add(mList.Shuttle_Fee);//合计成本
//            }

//            if (!TextUtils.isEmpty(mList.EntiretyCount)) {
//                img_text.add(mList.EntiretyCount);//整车发车
//            }
//
//            if (!TextUtils.isEmpty(mList.BulkloadCount)) {
//                img_text.add(mList.BulkloadCount);//配货发车
//            }
//            if (!TextUtils.isEmpty(mList.Ticket_Fee)) {
//                img_text.add(mList.Ticket_Fee);//合计收入
//            }
//            if (!TextUtils.isEmpty(mList.Profit_Fee)) {
//                img_text.add(mList.Profit_Fee);//合计利润
//            }
            myAdapter = new CarPlanStaticsAdapter(this, mList.SummaryList);
            myGridView.setAdapter(myAdapter);
            initChart();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == F_REQUEST_CODE) {

            mStarTime = data.getStringExtra(DriverCarSchedulingSearchActivity.BEGIN_DATA);
            mEendTime = data.getStringExtra(DriverCarSchedulingSearchActivity.END_DATA);

            dataSearchText.setText(mStarTime + " -- " + mEendTime);


            mSegmentView.setSegmentTextCooler();
        }

    }
}
