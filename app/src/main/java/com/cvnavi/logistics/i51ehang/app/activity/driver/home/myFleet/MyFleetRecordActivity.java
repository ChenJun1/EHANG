package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.DriverMainViewPagerAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.SearchActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fragment.MyFleetRecordFragment;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.gps.GpsTrackPlayFragment;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.TimeEvent;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarHistoryLocusAnalysis;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.popupwindow.MyPopWindow;
import com.cvnavi.logistics.i51ehang.app.widget.view.CustomViewPager;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 下午1:03
*描述：记录分析
************************************************************************************/

public class MyFleetRecordActivity extends BaseActivity {
    private static final int RECORD = 0;
    private static final int MAP = 1;
    private static final String KEY_CARKEY = "CARKEY";
    private final int Reques_Code = 89;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.record_tv)
    TextView recordTv;
    @BindView(R.id.map_tv)
    TextView mapTv;
    @BindView(R.id.vp)
    CustomViewPager vp;
    @BindView(R.id.search)
    LinearLayout search;
    @BindView(R.id.time)
    TextView time;
    private DriverMainViewPagerAdapter adapter;
    private ArrayList<Fragment> list = new ArrayList<Fragment>();
    private List<mCarHistoryLocusAnalysis> locusList;
    private String startTime = "";
    private String endTime = "";
    private String carKey = "";

    private final String STARTIME = " 00:00";
    private final String ENDTIME = " 23:59";


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCarKey() {
        return carKey;
    }

    public void setCarKey(String carKey) {
        this.carKey = carKey;
    }

    public List<mCarHistoryLocusAnalysis> getLocusList() {
        return locusList;
    }

    public void setLocusList(List<mCarHistoryLocusAnalysis> locusList) {
        this.locusList = locusList;
    }

    private MyFleetRecordFragment recordFragment;
    private GpsTrackPlayFragment locusFragment;

    public static void start(Context context, String Carkey) {
        Intent starter = new Intent(context, MyFleetRecordActivity.class);
        starter.putExtra(KEY_CARKEY, Carkey);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fleet_record);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra(KEY_CARKEY) != null) {
            setCarKey(getIntent().getStringExtra(KEY_CARKEY));
        } else {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(this, Utils.getResourcesString(R.string.get_car_info_fill), new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    finish();
                }
            });
        }
        init();
    }

    /**
     * 初始化一些界面
     * <p>
     * recordFragment 为记录分析界面
     * locusFragment 为地图轨迹的界面
     */
    private void init() {
        if (list == null) {
            list = new ArrayList<Fragment>();
        }
        recordFragment = MyFleetRecordFragment.getInstance();
        locusFragment = GpsTrackPlayFragment.getInstance();

        list.add(recordFragment);
        list.add(locusFragment);
        if (adapter == null) {
            adapter = new DriverMainViewPagerAdapter(getSupportFragmentManager(), list);
        }

        vp.setAdapter(adapter);
        vp.setPagingEnabled(false);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                changeState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(0);
        vp.setOffscreenPageLimit(2);
    }

    private void changeState(int position) {
        if (position == RECORD) {
            recordTv.setTextColor(0xff3A95E7);
            recordTv.setBackgroundResource(R.drawable.map_unselect);
            mapTv.setTextColor(0xffffffff);
            mapTv.setBackgroundResource(R.drawable.record_select);
            recordTv.setText("记录分析");
            mapTv.setText("轨迹地图");
        } else {
            recordTv.setTextColor(0xffffffff);
            recordTv.setBackgroundResource(R.drawable.map_select);
            mapTv.setTextColor(0xff3A95E7);
            mapTv.setBackgroundResource(R.drawable.record_unselect);
            recordTv.setText("记录分析");
            mapTv.setText("轨迹地图");
        }

        vp.setCurrentItem(position);
    }


    @OnClick({R.id.record_tv, R.id.map_tv, R.id.back_llayout, R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_tv:
                changeState(RECORD);
                break;
            case R.id.map_tv:
                changeState(MAP);
                break;
            case R.id.back_llayout:
                finish();
                break;
            case R.id.search:
                MyPopWindow popWindow = new MyPopWindow(MyFleetRecordActivity.this, new ArrayList<String>() {{
                    add("最近一天");
                    add("最近三天");
                    add("自定义查询");
                }});
                popWindow.showLocation(R.id.search);
                popWindow.setOnItemClickListener(new MyPopWindow.OnItemClickListener() {
                    @Override
                    public void onClick(int pos) {
                        switch (pos) {
                            case 0:
                                startTime = DateUtil.getNowTime(DateUtil.FORMAT_YMD) + STARTIME;
                                endTime = DateUtil.getNowTime(DateUtil.FORMAT_YMD) + ENDTIME;
                                refresh();
                                time.setText("最近一天");
                                EventBus.getDefault().post(new TimeEvent(startTime, endTime));
                                break;
                            case 1:
                                startTime = DateUtil.getLastNDaysForm(-3, DateUtil.FORMAT_YMDHM);
                                endTime = DateUtil.getLastNDaysForm(0, DateUtil.FORMAT_YMDHM);
                                refresh();
                                time.setText("最近三天");
                                EventBus.getDefault().post(new TimeEvent(startTime, endTime));
                                break;
                            case 2:
                                SearchActivity.startActivity(MyFleetRecordActivity.this, Reques_Code, DateUtil.FORMAT_YMDHM, 3);
                                break;
                        }

                    }
                });

                break;
        }
    }


    /**
     * 刷新界面
     */
    public void refresh() {
        recordFragment.refresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Reques_Code:
                    Bundle bundle = data.getExtras();
                    startTime = bundle.getString(SearchActivity.BEGIN_DATA);
                    endTime = bundle.getString(SearchActivity.END_DATA);
                    startTime = startTime + ":00";
                    endTime = endTime + ":00";
                    vp.setCurrentItem(0);
                    time.setText(DateUtil.strOldFormat2NewFormat(startTime, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MDHM) + " ~" + DateUtil.strOldFormat2NewFormat(endTime, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MDHM));
                    EventBus.getDefault().post(new TimeEvent(startTime, endTime));
                    break;
            }
        }
    }
}
