package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.myorder.MyOrderDetailAcitivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.Dr_MsgActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.DriverOrderDeatilActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.DrMySendCar;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.DrSendCarDetailActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendgoods.DrMySendGoods;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.DrMyTakeGoods;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder.DriverQueryOrderActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.scanning.MipcaActivityCapture;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.transportation.PlanDetailActivity;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.GetHomePageBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.home.HomePageBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarShift;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.bannerview.CircleFlowIndicator;
import com.cvnavi.logistics.i51ehang.app.widget.bannerview.ImagePagerAdapter;
import com.cvnavi.logistics.i51ehang.app.widget.bannerview.ViewFlow;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 司机端主页信息
 */
public class DrHomeFragment extends BaseFragment implements PullToRefreshScrollView.ScrollListener, HandlerUtils.OnReceiveMessageListener {

    private static final String TAG = "DrHomeFragment";

    @BindView(R.id.back_llayout)
    LinearLayout mBackLlayout;
    @BindView(R.id.scanning_et)
    EditText mScanningEt;
    @BindView(R.id.scan_QR_img)
    ImageView mScanQRImg;
    @BindView(R.id.right_tv)
    TextView mRightTv;
    @BindView(R.id.bg_ll)
    LinearLayout mBgLl;
    @BindView(R.id.root_ll)
    LinearLayout mRootLl;
    @BindView(R.id.viewflow)
    ViewFlow mViewflow;
    @BindView(R.id.viewflowindic)
    CircleFlowIndicator mViewflowindic;
    @BindView(R.id.framelayout)
    FrameLayout mFramelayout;
    @BindView(R.id.pullToRefresh_sv)
    PullToRefreshScrollView mPullToRefreshSv;
    @BindView(R.id.my_take_order)
    LinearLayout mMyTakeOrder;
    @BindView(R.id.my_send_goods)
    LinearLayout mMySendGoods;
    @BindView(R.id.send_car)
    LinearLayout mSendCar;
    @BindView(R.id.Letter_Date_tv)
    TextView mLetterDateTv;
    @BindView(R.id.Letter_State_tv)
    TextView mLetterStateTv;
    @BindView(R.id.CarCode_tv)
    TextView mCarCodeTv;
    @BindView(R.id.BoxCarCode_tv)
    TextView mBoxCarCodeTv;
    @BindView(R.id.Line_Name_tv)
    TextView mLineNameTv;
    @BindView(R.id.Ticket_Count_tv)
    TextView mTicketCountTv;
    @BindView(R.id.Goods_Num_tv)
    TextView mGoodsNumTv;
    @BindView(R.id.GetSendCarBtu_ll)
    LinearLayout mGetSendCarBtuLl;
    @BindView(R.id.DeliveryNum_tv1)
    TextView mDeliveryNumTv1;
    @BindView(R.id.DeliveryNum_tv2)
    TextView mDeliveryNumTv2;
    @BindView(R.id.SendNum_tv1)
    TextView mSendNumTv1;
    @BindView(R.id.SendNum_tv2)
    TextView mSendNumTv2;
    @BindView(R.id.imag)
    ImageView imag;
    @BindView(R.id.right_ll)
    RelativeLayout right_ll;

    @BindView(R.id.badeView)
    CircleFlowIndicator badeView;
    /*********************
     * 广告字段以及变量
     *************************/
    private ViewFlow mViewFlow;

    private GetAppLoginResponse loginData;
    private CircleFlowIndicator mFlowIndicator;
    private List<HomePageBean.AdvertImgListBean> list = new ArrayList<>();
    private ArrayList<String> imageUrlList = new ArrayList<>();
    private ArrayList<String> linkUrlArray = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();

    /*************
     * END
     *************/
    private SweetAlertDialog lodingDialog;
    private DataRequestBase dataRequestBase;
    private HomePageBean.LetterObjBean mLetterObjBean;

    private HandlerUtils.HandlerHolder mHandlerHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginData = MyApplication.getInstance().getLoginInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dr_home_fragment, container, false);
        ButterKnife.bind(this, view);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        initView();
        loadDataRequest(LoginService.GetHomePage_Request_Url);//数据请求
        initBannerView(view);
        initListeners();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static DrHomeFragment getInstance() {
        return new DrHomeFragment();
    }

    private void initView() {
        lodingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        String length = SharedPreferencesTool.getString(SharedPreferencesTool.QUERY_LENGTH, "6");
        mScanningEt.setHint(String.format("请输入%1$s位查询码", length));
        mPullToRefreshSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                loadDataRequest(LoginService.GetHomePage_Request_Url);

            }
        });
    }

    /**************
     * 轮播图广告
     *****************/
    private void initBannerView(View view) {
        mViewFlow = (ViewFlow) view.findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) view.findViewById(R.id.viewflowindic);
    }

    /**
     * 初始化轮播图
     */
    private void initData() {
        imageUrlList.clear();
        linkUrlArray.clear();
        if (list != null && list.size() > 0) {
            for (HomePageBean.AdvertImgListBean imageBanner : list) {
                imageUrlList.add(imageBanner.getImgUrl());
                linkUrlArray.add(imageBanner.getImgAdvert());
            }
        } else {
            //设置默认图片
            imageUrlList.add("http://www.cvnavi.com/");
            imageUrlList.add("http://www.cvnavi.com/");
            imageUrlList.add("http://www.cvnavi.com/");

            linkUrlArray.add("http://www.cvnavi.com/");
            linkUrlArray.add("http://www.cvnavi.com/");
            linkUrlArray.add("http://www.cvnavi.com/");
        }
        if (imageUrlList != null && imageUrlList.size() > 0) {
            initBanner(imageUrlList);
        }
    }

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        LoggerUtil.json(TAG, dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson
                (dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoggerUtil.json(TAG, response.toString());
                GetHomePageBean response1 = GsonUtil.newInstance().fromJson(response.toString(),
                        GetHomePageBean.class);
                Message msg = Message.obtain();
                if (response1 == null) {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                } else {
                    if (response1.Success) {
                        msg.obj = response1.DataValue;
                        msg.what = Constants.REQUEST_SUCC;
                    } else {
                        msg.obj = response1.ErrorText;
                        msg.what = Constants.REQUEST_FAIL;
                    }
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("-->>" + error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });
    }

    private void initBanner(ArrayList<String> imageUrlList) {
        mViewFlow.setAdapter(new ImagePagerAdapter(getActivity(), imageUrlList, linkUrlArray, titleList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(Constants.BANNER_TIME);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
        mViewFlow.setTimeSpan(5000);
    }

    /**************
     * 轮播图广告END
     *****************/
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView()
                    .setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    private void setViewData() {
        if (TextUtils.isEmpty(mLetterObjBean.getCarCode())) {
            mSendCar.setVisibility(View.GONE);
        } else {
            mSendCar.setVisibility(View.VISIBLE);
        }
        SetViewValueUtil.setTextViewValue(mLetterDateTv, mLetterObjBean.getLeave_DateTime());
        SetViewValueUtil.setTextViewValue(mLetterStateTv, mLetterObjBean.getCarCodeStatus());
        SetViewValueUtil.setTextViewValue(mCarCodeTv, mLetterObjBean.getCarCode());
        SetViewValueUtil.setTextViewStr(mBoxCarCodeTv, mLetterObjBean.getBoxCarCode());
        SetViewValueUtil.setTextViewStr(mLineNameTv, mLetterObjBean.getLine_Name());
        SetViewValueUtil.setTextViewValue(mTicketCountTv, mLetterObjBean.getTicket_Count());
        SetViewValueUtil.setTextViewValue(mGoodsNumTv, mLetterObjBean.getGoods_Num());
        SetViewValueUtil.setTextViewValue(mDeliveryNumTv1, mLetterObjBean.getDeliveryNum(), "票");
        SetViewValueUtil.setTextViewValue(mDeliveryNumTv2, "您好！你有" + (mLetterObjBean.getDeliveryNum() == null ? "" : mLetterObjBean.getDeliveryNum()) + "条提货信息");
        SetViewValueUtil.setTextViewValue(mSendNumTv1, mLetterObjBean.getSendNum(), "票");
        SetViewValueUtil.setTextViewValue(mSendNumTv2, "您好！你有" + (mLetterObjBean.getSendNum() == null ? "" : mLetterObjBean.getSendNum()) + "条送货信息");
        //设置消息角标
        if (!TextUtils.isEmpty(mLetterObjBean.getMsgCount()) && !mLetterObjBean.getMsgCount().equals("0")) {
            badeView.setVisibility(View.VISIBLE);
        } else {
            badeView.setVisibility(View.GONE);
        }
    }

    private final static int SCANNIN_GREQUEST_CODE = 1;
    private static final int REQUEST_CODE_SEARCH = 0x11;

    @OnClick({R.id.my_take_order, R.id.right_tv, R.id.my_send_goods, R.id.scanning_et,
            R.id.scan_QR_img, R.id.root_ll, R.id.send_car, R.id.GetSendCarBtu_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_take_order:
                DrMyTakeGoods.start(getActivity());
                break;
            case R.id.my_send_goods:
                DrMySendGoods.start(getActivity());
                break;
            case R.id.send_car:
                if (mLetterObjBean != null) {
                    if (!TextUtils.isEmpty(mLetterObjBean.getCarCodeStatus()) && mLetterObjBean.getCarCodeStatus().equals("进行中")) {
                        DrSendCarDetailActivity.start(getActivity(), mLetterObjBean.getLetter_Oid(), null);
                    } else {
                        PlanDetailActivity.startActivity(getActivity(), new mCarShift(mLetterObjBean.getCarCode(), mLetterObjBean.getLine_Name(), mLetterObjBean.getBoxCarCode(), mLetterObjBean.getCarCode_Date(), mLetterObjBean.getMainDriver(), mLetterObjBean.getMainDriver_Tel(), mLetterObjBean.getSecondDriver(), mLetterObjBean.getSecondDriver_Tel(), mLetterObjBean.getShuttle_Oid(), mLetterObjBean.getShuttle_No(), "0"));
                    }
                }
                break;
            case R.id.scanning_et:
                DriverQueryOrderActivity.start(getActivity());
                break;
            case R.id.GetSendCarBtu_ll:
                DrMySendCar.start(getActivity());
                break;
            case R.id.scan_QR_img:
                Intent intent = new Intent();
                intent.setClass(getActivity(), MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;
            case R.id.root_ll:
                break;
            case R.id.right_tv:
                Dr_MsgActivity.start(getActivity());
                break;
        }
    }

    private int imageHeight;

    /**
     * 获取顶部图片高度后，设置滚动监听
     */
    private void initListeners() {
        ViewTreeObserver vto = mViewFlow.getViewTreeObserver();
        mPullToRefreshSv.setScrollUpListener(this);
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mViewFlow.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                imageHeight = mViewFlow.getHeight();
                mPullToRefreshSv.setScrollUpListener(DrHomeFragment.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == getActivity().RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    if (loginData.DataValue.UserType_Oid.equals("E")) {
                        //货主跳到货单明细
                        Intent intent = new Intent(getActivity(), MyOrderDetailAcitivity.class);
                        intent.putExtra(MyOrderDetailAcitivity.ORDER_ID, Utils.createAllTicketNo(bundle.getString("result")));
                        startActivity(intent);
                    } else {
                        //司机和员工跳到货单详情
                        DriverOrderDeatilActivity.startActivity(getActivity(), REQUEST_CODE_SEARCH, Utils.createAllTicketNo(bundle.getString("result")), null);
                    }
                }
                break;
        }
    }

    @Override
    public void ScrocllToUpAndDown(int pos) {

        if (pos < 0) {   //设置标题的背景颜色
            mRootLl.setBackgroundColor(Color.argb((int) 0, 144, 151, 166));
        } else if (pos >= 0 && pos <= imageHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) pos / imageHeight;
            float alpha = (255 * scale);
//            mFramelayout.setTextColor(Color.argb((int) alpha, 255,255,255));
            mRootLl.setBackgroundColor(Color.argb((int) alpha, 53, 46, 54));
        } else {    //滑动到banner下面设置普通颜色
            mRootLl.setBackgroundColor(Color.argb((int) 255, 53, 46, 54));
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        if (lodingDialog != null) {
            lodingDialog.dismiss();
        }
        mPullToRefreshSv.onRefreshComplete();
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                HomePageBean bean = (HomePageBean) msg.obj;
                mLetterObjBean = bean.getLetterObj();
                if (bean != null) {
                    List<HomePageBean.AdvertImgListBean> datalist;
                    datalist = bean.getAdvertImgList();
                    list.clear();
                    if (datalist != null) {
                        list.addAll(datalist);
                    }
                    initData();
                    if (mLetterObjBean != null) {
                        setViewData();
                    }
                }
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R
                        .string.request_Fill) : msg.obj.toString());
                break;
            case Constants.DELETE_SUCC:
                DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(getActivity(), Utils
                        .getResourcesString(R.string.request_error));
                break;
        }
    }
}
