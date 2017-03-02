package com.cvnavi.logistics.i51ehang.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.FloatingView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 测试activity
 * activity_main
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.test)
    TextView test;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    //    private FancyCoverFlow mfancyCoverFlow;
//    private MyFancyCoverFlowAdapter mMyFancyCoverFlowAdapter;
    private List<String> mDatas;

    private View lastView;
    private TextView view;
    RadioGroup myRp;
    private int i = 0;

    private FloatingView floatingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        ButterKnife.bind(this);

//        List<Item> mFancyCoverFlows=new ArrayList<>();
//        for(int i=0;i<365;i++){
//            Item item=new Item();
//            item.setName((i+1)+"天");
//            item.setSelected(false);
//            mFancyCoverFlows.add(item);
//        }
//        mfancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);
//        mMyFancyCoverFlowAdapter = new MyFancyCoverFlowAdapter(this, mFancyCoverFlows);
//        mfancyCoverFlow.setAdapter(mMyFancyCoverFlowAdapter);
//        mMyFancyCoverFlowAdapter.notifyDataSetChanged();
//        mfancyCoverFlow.setUnselectedAlpha(0.5f);//通明度
//        mfancyCoverFlow.setUnselectedSaturation(0.5f);//设置选中的饱和度
//        mfancyCoverFlow.setUnselectedScale(0.3f);//设置选中的规模
//        mfancyCoverFlow.setSpacing(0);//设置间距
//        mfancyCoverFlow.setMaxRotation(0);//设置最大旋转
//        mfancyCoverFlow.setScaleDownGravity(0.5f);
//        mfancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
//        int num = Integer.MAX_VALUE / 2 % mFancyCoverFlows.size();
//        int selectPosition = Integer.MAX_VALUE / 2 - num;
//        mfancyCoverFlow.setSelection(selectPosition);
//        mfancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Item homeFancyCoverFlow = (Item) mfancyCoverFlow.getSelectedItem();
//                if (homeFancyCoverFlow != null) {
//                    Toast.makeText(MainActivity.this,homeFancyCoverFlow.getName(),Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }

        PieData mPieData = getPieData(4, 100);
        showChart(pieChart, mPieData);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        pieChart.setHoleRadius(0) ; //实心圆

        pieChart.setDescription("测试饼状图");

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(false);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("Quarterly Revenue");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     *
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        for (int i = 0; i < count; i++) {
            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        }

        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        float quarterly1 = 14;
        float quarterly2 = 14;
        float quarterly3 = 34;
        float quarterly4 = 38;

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));
        yValues.add(new Entry(quarterly4, 3));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "Quarterly Revenue 2014"/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }


}
