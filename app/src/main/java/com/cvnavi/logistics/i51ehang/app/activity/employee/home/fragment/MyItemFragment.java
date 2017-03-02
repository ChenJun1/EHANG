package com.cvnavi.logistics.i51ehang.app.activity.employee.home.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.location.DriverCarTreeListActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.location.DriverTransportationListActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.MyFleetActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder.DriverQueryOrderActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverManagerActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter.MyEmployeeRecycleAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.basesetting.BaseSettingActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.gps.EmployeeGpsActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.gps.EmployeeGpsTrackActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.SendCarMonitorActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.StatisticsActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.storehouse.StoreHouseActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.mMainService;
import com.cvnavi.logistics.i51ehang.app.callback.recycleview.OnItemClickLitener;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:13
*描述： 每次添加的item  动态item 设置布局
************************************************************************************/


public class MyItemFragment extends BaseFragment implements OnItemClickLitener {

    private static final String KEY_LIST = "KEY_LIST";
    @BindView(R.id.one_iv_one)
    ImageView oneIvOne;
    @BindView(R.id.one_tv_one)
    TextView oneTvOne;
    @BindView(R.id.one_ll)
    LinearLayout oneLl;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;

    private MyEmployeeRecycleAdapter mAdapter;
    private ArrayList<mMainService> menuList = null;

    public static MyItemFragment newInstance(
            ArrayList<mMainService> menuList) {
        MyItemFragment newFragment = new MyItemFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_LIST, menuList);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuList = (ArrayList<mMainService>) getArguments()
                .getSerializable(KEY_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_fragment_item,
                container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (menuList != null && menuList.size() > 1) {
            if (menuList.size() < 3) {
                //menu菜单数量小于3
                recycleview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            } else {
                //menu菜单数量大于3
                recycleview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
                swap();
            }
            mAdapter = new MyEmployeeRecycleAdapter(getActivity(), menuList);
            mAdapter.setOnItemClickLitener(this);
            recycleview.setAdapter(mAdapter);
            recycleview.setPadding(Utils.dip2px(getActivity(), 20), 0, Utils.dip2px(getActivity(), 20), 0);
            recycleview.setVisibility(View.VISIBLE);
            oneLl.setVisibility(View.GONE);
        } else if (menuList != null && menuList.size() == 1) {
            final mMainService info = menuList.get(0);
            switch (Integer.parseInt(info.ServiceID)) {
                case Constants.EMPLOYEE_SERVICE_ID_GPS:
                    oneIvOne.setImageResource(R.drawable.home_look);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_GPS_TRACK:
                    oneIvOne.setImageResource(R.drawable.home_gps);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_TRANSTION:
                    oneIvOne.setImageResource(R.drawable.home_trans);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_TONGJI:
                    oneIvOne.setImageResource(R.drawable.home_tj);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_CAR_FLEET:
                    oneIvOne.setImageResource(R.drawable.home_cars);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_SETTING:
                    oneIvOne.setImageResource(R.drawable.home_setting);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_SEND_CAR_MONITOR:
                    oneIvOne.setImageResource(R.drawable.car_monitor);
                    break;
                default:
                    oneIvOne.setImageResource(R.drawable.home_look);
                    break;
            }
            oneTvOne.setText(info.ServiceName);

            oneLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectMenu(Integer.parseInt(info.ServiceID));
                }
            });
            oneLl.setVisibility(View.VISIBLE);
            recycleview.setVisibility(View.GONE);
        }
    }

    /**
     * 根据size的不同对列表重新排序,让界面达到效果
     */
    private void swap() {
        if (menuList == null) {
            return;
        }
        switch (menuList.size()) {
            case 4:
                Collections.swap(menuList, 1, 2);
                break;
            case 5:
                Collections.swap(menuList, 1, 2);
                Collections.swap(menuList, 1, 4);
                Collections.swap(menuList, 1, 3);
                break;
            case 6:
                Collections.swap(menuList, 1, 2);
                Collections.swap(menuList, 3, 4);
                Collections.swap(menuList, 1, 4);
                break;
        }
    }


    @Override
    public void onItemClick(View view, final int position) {
        AnimatorSet aSet = new AnimatorSet();
        aSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.05f, 1.0f)
                        .setDuration(300),
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.05f, 1)
                        .setDuration(300));
        aSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                String serviceId = menuList.get(position).ServiceID;
                selectMenu(Integer.parseInt(serviceId));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        aSet.start();
    }


    @Override
    public void onItemLongClick(View view, int position) {
        AnimatorSet aSet2 = new AnimatorSet();
        aSet2.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.05f)
                        .setDuration(550),
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.05f)
                        .setDuration(550));
        aSet2.start();
    }


    private void selectMenu(int serviceId) {
        MLog.print("-->> serviceId");
        switch (serviceId) {
            case Constants.EMPLOYEE_SERVICE_ID_ORDER_SEARCH:
                //员工货单快速查询
                startActivity(new Intent(getActivity(), DriverQueryOrderActivity.class));
                break;
            case Constants.EMPLOYEE_SERVICE_ID_DRIVER_MANAGE:
                //司机管理 我的界面中的我的司机
                showActivity(getActivity(), DriverManagerActivity.class);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_TONGJI:
                //统计界面
                showActivity(getActivity(), StatisticsActivity.class);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_CAR_FLEET:
                //我的车队
                showActivity(getActivity(), MyFleetActivity.class);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_TRANSTION:
                //运输界面
                startActivity(new Intent(getActivity(), DriverTransportationListActivity.class));
                break;
            case Constants.EMPLOYEE_SERVICE_ID_CAR_MANAGE:
                //车辆列表界面
                Intent intent = new Intent(getActivity(), DriverCarTreeListActivity.class);
                intent.putExtra(Constants.HOME, Constants.HOME);
                startActivity(intent);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_GPS:
                //员工 GPS
                startActivity(new Intent(getActivity(), EmployeeGpsActivity.class));
                break;
            case Constants.EMPLOYEE_SERVICE_ID_GPS_TRACK:
                //员工的历史轨迹查询
                startActivity(new Intent(getActivity(), EmployeeGpsTrackActivity.class));
                break;
            case Constants.EMPLOYEE_SERVICE_ID_SETTING:
                //基础设置
                startActivity(new Intent(getActivity(), BaseSettingActivity.class));
                break;
            case Constants.EMPLOYEE_SERVICE_ID_SEND_CAR_MONITOR:
                //发车监控界面
                SendCarMonitorActivity.startActivity(getActivity());
                break;
            case Constants.EMPLOYEE_SERVICE_ID_KU_FANG:
                //库房
                startActivity(new Intent(getActivity(), StoreHouseActivity.class));
                break;
        }

    }

}
