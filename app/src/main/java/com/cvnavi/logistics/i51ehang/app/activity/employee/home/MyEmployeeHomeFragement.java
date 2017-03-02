package com.cvnavi.logistics.i51ehang.app.activity.employee.home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.MyFleetActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder.DriverHomeOrderDeatilActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder.DriverQueryOrderActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.scanning.MipcaActivityCapture;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter.MyViewPagerFragmentAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.fragment.MyItemFragment;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.GetHomePageBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.home.HomePageBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.mMainService;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

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
 * 创建时间：2017/1/16 下午4:08
 * 描述：员工首页界面
 ************************************************************************************/

public class MyEmployeeHomeFragement extends BaseFragment implements MyViewPagerFragmentAdapter.OnExtraPageChangeListener {
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private static final int REQUEST_CODE_SEARCH = 0x11;
    @BindView(R.id.scan_QR_img)
    ImageView scanQRImg;
    @BindView(R.id.scanning_et)
    EditText scanningEt;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.bg_ll)
    LinearLayout bgLl;
    @BindView(R.id.root_ll)
    LinearLayout rootLl;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.num_msg)
    TextView numMsg;
    @BindView(R.id.clear_tv)
    TextView clearTv;
    @BindView(R.id.msg_rl)
    RelativeLayout msgRl;
    @BindView(R.id.vPager)
    ViewPager vPager;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.onWay)
    TextView onWay;
    @BindView(R.id.on)
    TextView on;
    @BindView(R.id.zaitu_rl)
    RelativeLayout zaituRl;
    @BindView(R.id.plan)
    TextView plan;
    @BindView(R.id.onPlan)
    TextView onPlan;
    @BindView(R.id.plan_rl)
    RelativeLayout planRl;
    @BindView(R.id.arrive)
    TextView arrive;
    @BindView(R.id.onArrive)
    TextView onArrive;
    @BindView(R.id.arrive_rl)
    RelativeLayout arriveRl;
    @BindView(R.id.carFleet)
    LinearLayout carFleet;
    @BindView(R.id.llPageSign)
    LinearLayout llPageSign;

    private ArrayList<mMainService> menuList = null; // 菜单数据源列表
    private ArrayList<ArrayList<mMainService>> fragmentDataList = null;// fragment的数据列表
    private ArrayList<Fragment> fragmentsList = null;
    private GetAppLoginResponse loginData;

    public static MyEmployeeHomeFragement instantiation() {
        return new MyEmployeeHomeFragement();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_employee_home, container, false);
        ButterKnife.bind(this, view);
        setData();
        initView();
        return view;
    }

    private void initView() {
        String length = SharedPreferencesTool.getString(SharedPreferencesTool.QUERY_LENGTH, "6");
        scanningEt.setHint(String.format("请输入%1$s位查询码", length));
    }

    /**
     * 获取车队的 在途中,已计划,将到达显示
     * 点击 在途中，已计划，将到达跳转到我的车队相应的界面
     */

    private void getCarFleet() {
        if (loginData == null || (loginData != null && loginData.DataValue == null)) {
            return;
        }
        showCarFleetInfo();
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.Company_Oid = loginData.DataValue.Company_Oid;
        dataRequestBase.UserType_Oid = loginData.DataValue.UserType_Oid;
        dataRequestBase.User_Key = loginData.DataValue.User_Key;
        dataRequestBase.Org_Code = loginData.DataValue.Org_Code;
        dataRequestBase.Token = loginData.DataValue.Token;
        VolleyManager.newInstance().PostJsonRequest(LoginService.GetHomePage_TAG, LoginService.GetHomePage_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                GetHomePageBean result = GsonUtil.newInstance().fromJson(response, GetHomePageBean.class);
                if (result != null && result.Success && result.DataValue != null) {
                    HomePageBean.EmployeePageBean bean = result.DataValue.getEmployeePage();
                    if (bean != null) {
                        onWay.setText(bean.getOnWayNum());
                        plan.setText(bean.getAlreadyPlanNum());
                        arrive.setText(bean.getReachNum());
                        numMsg.setText(bean.getNotifyNum() + "条订单");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showCarFleetInfo();
            }
        });
    }

    @OnClick({R.id.scanning_et, R.id.scan_QR_img, R.id.clear_tv, R.id.zaitu_rl, R.id.plan_rl, R.id.arrive_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scanning_et:
                startActivity(new Intent(getActivity(), DriverQueryOrderActivity.class));
                break;
            case R.id.scan_QR_img:
                Intent intent = new Intent();
                intent.setClass(getActivity(), MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;
            case R.id.clear_tv:
                popMessage();
                break;
            case R.id.zaitu_rl:
                MyFleetActivity.startActivity(getActivity(), 0);
                break;
            case R.id.plan_rl:
                MyFleetActivity.startActivity(getActivity(), 2);
                break;
            case R.id.arrive_rl:
                MyFleetActivity.startActivity(getActivity(), 3);
                break;
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    //获取定位List

    /**
     * 12 13 14 15 分别是货单统计 应收款统计 过滤出来
     *
     * @return
     */
    private ArrayList<mMainService> getLocationList() {
        loginData = MyApplication.getInstance().getLoginInfo();
        if (loginData == null) {
            return null;
        }

        List<mMainService> list = loginData.DataValue.MenuList;
        ArrayList<mMainService> loginDataList = new ArrayList<mMainService>();
        if (list != null) {
            //筛选
            for (int i = 0; i < list.size(); i++) {
                if ((!list.get(i).ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_HUODAN_TONGJI)
                        && !list.get(i).ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_PEIZAI_TONGJI)
                        && !list.get(i).ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_YSK_TONGJI)
                        && !list.get(i).ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_YFK_TONGJI)
                        && !list.get(i).ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_MILES_TONGJI))) {
                    loginDataList.add(list.get(i));
                }
            }
        }

        return loginDataList;
    }

    private void setData() {
        initFragmentDataList();
        initViewPager();
        // 如果只有一页数据，页码指示标记不要显示
        if (fragmentDataList != null) {
            if (fragmentDataList.size() == 1) {
                llPageSign.setVisibility(View.INVISIBLE);
            } else {
                initPageSign();
            }
        }
    }

    // 初始化ViewPager
    private void initViewPager() {
        if (fragmentDataList != null && fragmentDataList.size() > 0) {
            fragmentsList = new ArrayList<Fragment>();
            for (int i = 0; i < fragmentDataList.size(); i++) {
                Fragment fragmentItem = MyItemFragment
                        .newInstance(fragmentDataList.get(i));
                fragmentsList.add(fragmentItem);
            }
            MyViewPagerFragmentAdapter adapter = new MyViewPagerFragmentAdapter(
                    getActivity().getSupportFragmentManager(), vPager,
                    fragmentsList);
            vPager.setCurrentItem(0);
            adapter.setOnExtraPageChangeListener(this);
        }
        popMessage();

    }

    // 初始化fragment的数据列表
    private void initFragmentDataList() {
        menuList = getLocationList();
        if (menuList != null && menuList.size() > 0) {
            fragmentDataList = new ArrayList<ArrayList<mMainService>>();
            ArrayList<mMainService> infoListItem = new ArrayList<mMainService>();
            if (menuList.size() <= 6) {
                //如果返回的菜单数量小于6个，依次读取，加入总的list
                for (int i = 0; i < menuList.size(); i++) {
                    infoListItem.add(menuList.get(i));
                }
                fragmentDataList.add(infoListItem);
            } else {
                for (int i = 0; i < menuList.size(); i++) {
                    //如果list大于6先读取，以6个为一组放入list中
                    infoListItem.add(menuList.get(i));
                    if (infoListItem.size() == 6) {
                        //数量如果刚好是6 不需要再分组
                        fragmentDataList.add(infoListItem);
                        infoListItem = new ArrayList<mMainService>();
                    } else if (i == menuList.size() - 1) {
                        fragmentDataList.add(infoListItem);
                    }
                }
            }
        }
    }

    // 初始化页码只是标记
    private void initPageSign() {
        // 页码指示标记
        for (int i = 0; i <= fragmentDataList.size() - 1; i++) {
            createPageDirection(i);
        }
    }

    // 创建页码指示标记
    private void createPageDirection(int i) {
        ImageView image = new ImageView(getActivity());
        if (i == 0) {
            image.setImageResource(R.drawable.live_more_dialog_uncheck);
        } else {
            image.setImageResource(R.drawable.live_more_dialog_check);
        }
        image.setPadding(Utils.dip2px(getActivity(), 3), 0,
                Utils.dip2px(getActivity(), 3), 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.leftMargin = 1;
        llPageSign.addView(image, layoutParams);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == getActivity().RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //司机和员工跳到货单详情
                    DriverHomeOrderDeatilActivity.startActivity(getActivity(), REQUEST_CODE_SEARCH, Utils.createAllTicketNo(bundle.getString("result")));
                }
                break;
        }
    }

    @Override
    public void onExtraPageScrolled(int pos, float v, int i2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onExtraPageSelected(int pos) {
        // TODO Auto-generated method stub
        for (int i = 0; i < fragmentDataList.size(); i++) {
            ((ImageView) llPageSign.getChildAt(i))
                    .setImageResource(R.drawable.live_more_dialog_uncheck);
            if (pos != i) {
                ((ImageView) llPageSign.getChildAt(i))
                        .setImageResource(R.drawable.live_more_dialog_check);
            }
        }
    }

    @Override
    public void onExtraPageScrollStateChanged(int pos) {
        // TODO Auto-generated method stub
    }

    private void popMessage() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(msgRl, "translationY", 1, 100).setDuration(2000)
                , ObjectAnimator.ofFloat(msgRl, "alpha", 0, 1).setDuration(2000));
        animatorSet.start();
        msgRl.setVisibility(View.GONE);

    }

    private void showCarFleetInfo() {
        carFleet.setVisibility(View.VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(carFleet, "translationY", -100, 0).setDuration(2000)
                , ObjectAnimator.ofFloat(carFleet, "alpha", 0, 1).setDuration(2000));
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        carFleet.setVisibility(View.INVISIBLE);
        getCarFleet();
    }
}
