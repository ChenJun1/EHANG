package com.cvnavi.logistics.i51ehang.app.activity.employee.home.storehouse;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.employee.home.statics.StoreSelectOrgModel;
import com.cvnavi.logistics.i51ehang.app.bean.model.StowageStatisticsSummary.SummaryItem;
import com.cvnavi.logistics.i51ehang.app.bean.model.StowageStatisticsSummary.mStowageStatisticsSummary;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetStowageStatisticsSummaryRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetStowageStatisticsSummaryResponse;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.gridview.MyGridView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
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
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:03
*描述：库存统计界面
************************************************************************************/

public class StockActivity extends BaseActivity {
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
    @BindView(R.id.arrow)
    LinearLayout arrow;
    @BindView(R.id.root_select)
    RelativeLayout rootSelect;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    @BindView(R.id.myGridView)
    MyGridView myGridView;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.top_bar_line)
    ImageView topBarLine;
    @BindView(R.id.select_top_line)
    ImageView selectTopLine;
    private StoreGridViewAdapter myAdapter;
    private mStowageStatisticsSummary sList;
    private String Org_Code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化
     */
    private void init() {
        title.setText("库存");
        rightTv.setText("明细");
        rightTv.setVisibility(View.VISIBLE);

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
        if (!TextUtils.isEmpty(MyApplication.getInstance().getLoginInfo().DataValue.Org_Name)) {
            dataSearchText.setText(MyApplication.getInstance().getLoginInfo().DataValue.Org_Name);
        }

        requestHttp();
    }


    private void requestHttp() {
        showLoading();
        DataRequestBase requestBase = new DataRequestBase();
        final GetStowageStatisticsSummaryRequest request = new GetStowageStatisticsSummaryRequest();
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Org_Code = Org_Code;

        final JSONObject jsonObject = GsonUtil.newInstance().toJson(requestBase);
        MLog.json(jsonObject.toString());
        VolleyManager.newInstance().PostJsonRequest("--->", EmployeeService.GetGoods_Room_Collect_Url, jsonObject, new Response.Listener<JSONObject>() {
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
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                mHandler.sendMessage(msg);
            }
        });

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dissLoading();
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
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(StockActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };


    /**
     * 请求数据
     *
     * @param mList
     */
    public void Success(mStowageStatisticsSummary mList) {
        if (mList != null) {
            myAdapter = new StoreGridViewAdapter(this, mList.SummaryList);
            myGridView.setAdapter(myAdapter);

            final List<SummaryItem> list = mList.SummaryList;

            if (list != null && list.size() > 0) {
                myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        SummaryItem item = list.get(position);
                        PieData mPieData = getPieData(Float.parseFloat(item.SendSum), Float.parseFloat((item.ArriveSum)));
                        showChart(pieChart, mPieData, item.StatisticType);
                    }
                });

                PieData mPieData = getPieData(Float.parseFloat(list.get(0).SendSum), Float.parseFloat((list.get(0).ArriveSum)));
                showChart(pieChart, mPieData, list.get(0).StatisticType);
            }
        }
    }


    /**
     * 绘制地图
     *
     * @param pieChart
     * @param pieData
     * @param descrip
     */
    private void showChart(PieChart pieChart, PieData pieData, String descrip) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(58f);  //半径
        pieChart.setTransparentCircleRadius(61f); // 半透明圈
        pieChart.setTransparentCircleColor(0xffffffff);

        pieChart.setDescription("");

        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        pieChart.setCenterText(descrip);  //饼状图中间的文字
        pieChart.setCenterTextColor(0xffffffff);
        pieChart.setCenterTextSize(20);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        //设置数据
        pieChart.setData(pieData);
        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);  //最右边显示
        mLegend.setForm(Legend.LegendForm.CIRCLE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);
        mLegend.setEnabled(false);
        mLegend.setTextColor(0xffffffff);

//        pieChart.animateXY(1000, 1000);  //设置动画
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);
    }

    /**
     */
    private PieData getPieData(float send, float arrive) {
        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
        xValues.add("发送");
        xValues.add("到达");
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        float quarterly1 = send;
        float quarterly2 = arrive;

        yValues.add(new Entry(quarterly1, 0, "%"));
        yValues.add(new Entry(quarterly2, 1, "%"));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "");
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        pieDataSet.setValueTextColor(0xffffffff);

        // 饼图颜色RGB(25, 158, 215)RGB(204, 204, 204)
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(49, 159, 248));
        colors.add(Color.rgb(87, 212, 110));
        pieDataSet.setColors(colors);

        pieDataSet.setValueTextSize(10);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

//        pieDataSet.setSliceSpace(3f);//设置数据之间有间隔
        pieDataSet.setSelectionShift(5f);

//        PieData pieData = new PieData(xValues, pieDataSet);
        PieData pieData = new PieData(xValues, pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        return pieData;
    }


    @OnClick({R.id.back_ll, R.id.right_tv, R.id.root_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.right_tv:
                //明细
                StockDetailActivity.start(this, Org_Code);
                break;
            case R.id.root_select:
                StoreSelectOrgActivity.startActivity(StockActivity.this, 0x12);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reResh(StoreSelectOrgModel.DataValueBean data) {
        if (data != null) {
            Org_Code = data.getOrg_Code().trim();
            dataSearchText.setText(data.getOrg_Name());
            requestHttp();
        }
    }

}
