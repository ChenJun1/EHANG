package com.cvnavi.logistics.i51ehang.app.activity.cargo.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.CargoJurisdictionAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.message.CargoMessageActivity;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.mymoney.MyMoneyActivity;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.myorder.HistoryOrdersActivity;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.pickuprcord.CargoPickUpRecordActivity;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.unfinlshedorder.UnfinishedOrdersActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder.DriverQueryOrderActivity;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.request.GetMessageCountRequest;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.response.GetMsgCountResponse;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.GetHomePageBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.home.HomePageBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.mBadgeView;
import com.cvnavi.logistics.i51ehang.app.bean.model.mMainService;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetBadgeViewRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetBadgeViewResponse;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.bannerview.CircleFlowIndicator;
import com.cvnavi.logistics.i51ehang.app.widget.bannerview.ImagePagerAdapter;
import com.cvnavi.logistics.i51ehang.app.widget.bannerview.ViewFlow;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.gridview.MyGridView;
import com.jauker.widget.BadgeView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.badgeview.BGABadgeImageView;
import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 货主 首页
 */
public class CargoHomeFragment extends BaseFragment implements HandlerUtils.OnReceiveMessageListener{
    private static final String TAG = "CargoHomeFragment";


    @BindView(R.id.cargo_menu_gv)
    MyGridView cargoMenuGv;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.scanning_et)
    EditText scanningEt;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.bg_ll)
    LinearLayout bgLl;
    @BindView(R.id.root_ll)
    LinearLayout rootLl;
    @BindView(R.id.yewu_ll)
    LinearLayout yewuLl;
    @BindView(R.id.viewflow)
    ViewFlow viewflow;
    @BindView(R.id.viewflowindic)
    CircleFlowIndicator viewflowindic;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.cargo_home_search_edit)
    EditText cargoHomeSearchEdit;
    @BindView(R.id.cargo_unfinished_layout)
    LinearLayout cargoUnfinishedLayout;
    @BindView(R.id.cargo_record_layout)
    LinearLayout cargoRecordLayout;
    @BindView(R.id.cargo_historyorder_layout)
    LinearLayout cargoHistoryorderLayout;
    @BindView(R.id.cargo_money_layout)
    LinearLayout cargoMoneyLayout;
    @BindView(R.id.cargo_img)
    ImageView cargoImg;
    @BindView(R.id.message_img)
    BGABadgeImageView messageImg;
    @BindView(R.id.scan_QR_img)
    ImageView scanQRImg;
    @BindView(R.id.imageView2)
    ImageView imageView2;

    private DataRequestBase requestBase;
    private BadgeView badgeView;
    private List<mBadgeView> mList = new ArrayList<>();

    private CargoJurisdictionAdapter yeWuAdapter;
    private GetAppLoginResponse loginData;
    private ArrayList<mMainService> businessList;
    private SweetAlertDialog loadingDialog = null;
    /*********************
     * 广告字段以及变量
     *************************/
    private DataRequestBase dataRequestBase;
    private ViewFlow mViewFlow;
    private CircleFlowIndicator mFlowIndicator;
    private List<HomePageBean.AdvertImgListBean> list = new ArrayList<>();
    private ArrayList<String> imageUrlList = new ArrayList<>();
    private ArrayList<String> linkUrlArray = new ArrayList<>();
    private ArrayList<String> titleList = new ArrayList<>();
    private HandlerUtils.HandlerHolder mHandlerHolder;

    private mBadgeView mBadgeView;
    /*************
     * END
     *************/
    public static CargoHomeFragment instantiation() {
        return new CargoHomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginData = MyApplication.getInstance().getLoginInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cargo_home, container, false);
        ButterKnife.bind(this, view);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        rootLl.setBackgroundColor(Utils.getResourcesColor(R.color.top_bar_title_bg_color));
        loadDataRequest(LoginService.GetHomePage_Request_Url);
        initView();
        initBannerView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requestMsgCount();
        requestBadgeViewHttp();
    }

    //获取业务list
    @Nullable
    private ArrayList<mMainService> getBusinessList() {
        if (loginData == null) {
            return null;
        }
        List<mMainService> list = loginData.DataValue.MenuList;
        ArrayList<mMainService> loginDataList = new ArrayList<>();
        if (list != null) {
            //筛选
            for (int i = 0; i < list.size(); i++) {
                if ((list.get(i).ServiceID.equals("S1") || list.get(i).ServiceID.equals("S2") || list.get(i).ServiceID.equals("S3") || list.get(i).ServiceID.equals("S4"))) {
                    loginDataList.add(list.get(i));
                }
            }
        }
        return loginDataList;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView().setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    private void initView() {
        String length = SharedPreferencesTool.getString(SharedPreferencesTool.QUERY_LENGTH, "6");
        scanningEt.setHint(String.format("请输入%1$s位查询码", length));
        cargoHomeSearchEdit.setHint(String.format("请输入%1$s位查询码", length));
        cargoHomeSearchEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DriverQueryOrderActivity.class));
            }
        });


        businessList = getBusinessList();

        yeWuAdapter = new CargoJurisdictionAdapter(getBusinessList(), getActivity());

        cargoMenuGv.setAdapter(yeWuAdapter);

        cargoMenuGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jumpActivity(businessList.get(position).ServiceType, businessList.get(position).ServiceID);
            }
        });
        badgeView = new BadgeView(getContext());
        badgeView.setTargetView(cargoImg);
    }

    private void jumpActivity(String serviceType, String serviceId) {
        if (Integer.parseInt(serviceType) == Constants.HOME_SERVICE_TYPE_BUSINISS) {
            switch (serviceId) {
                case Constants.HOME_BASE_WWC:
                    startActivity(new Intent(getActivity(), UnfinishedOrdersActivity.class));
                    break;
                case Constants.HOME_BASE_LS:
                    startActivity(new Intent(getActivity(), HistoryOrdersActivity.class));
                    break;
                case Constants.HOME_BASE_QH:
                    startActivity(new Intent(getActivity(), HistoryOrdersActivity.class));
                    break;
                case Constants.HOME_BASE_WO:
                    startActivity(new Intent(getActivity(), HistoryOrdersActivity.class));
                    break;
            }
        }
    }

    @OnClick({R.id.scanning_et,
            R.id.cargo_unfinished_layout,
            R.id.cargo_historyorder_layout,
            R.id.cargo_record_layout,
            R.id.cargo_money_layout,
            R.id.message_img
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scanning_et:
                startActivity(new Intent(getActivity(), DriverQueryOrderActivity.class));
                break;
            case R.id.cargo_unfinished_layout:
                 UnfinishedOrdersActivity.start(getActivity(),mBadgeView);
                break;
            case R.id.cargo_historyorder_layout:
                startActivity(new Intent(getActivity(), HistoryOrdersActivity.class));
                break;
            case R.id.cargo_record_layout:
                Intent intent = new Intent();
                intent.setClass(getActivity(), CargoPickUpRecordActivity.class);
                intent.putExtra("Cargo", "HOME");
                startActivity(intent);
                break;
            case R.id.cargo_money_layout:
                startActivity(new Intent(getActivity(), MyMoneyActivity.class));
                break;
            case R.id.message_img:
                startActivity(new Intent(getActivity(), CargoMessageActivity.class));
                break;
        }
    }
    /**************
     * 轮播图广告
     *****************/
    private void initBannerView(View view) {
        mViewFlow = (ViewFlow) view.findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) view.findViewById(R.id.viewflowindic);

    }

    private void initData() {
        if (list != null) {
            for (HomePageBean.AdvertImgListBean imageBanner : list) {
                imageUrlList.add(imageBanner.getImgUrl());
                linkUrlArray.add(imageBanner.getImgAdvert());
            }
        } else {
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
        dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson
                (dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>" + response.toString());
                GetHomePageBean response1 = GsonUtil.newInstance().fromJson(response.toString(), GetHomePageBean.class);
                LoggerUtil.json(response.toString());
                Message msg = Message.obtain();
                if (response1 != null) {
                    if (response1.Success && response1.DataValue != null) {
                        msg.what = Constants.REQUEST_SUCC;
                        msg.obj = response1.DataValue;
                    } else {
                        if (response1.DataValue == null) {
                            msg.what = Constants.REQUEST_FAIL;
                            msg.obj = Utils.getResourcesString(R.string.list_null);
                        } else {
                            msg.what = Constants.REQUEST_FAIL;
                            msg.obj = response1.ErrorText;
                        }
                    }
                } else {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
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
    //角标数据
    private void requestBadgeViewHttp() {
        requestBase = new DataRequestBase();
        final GetBadgeViewRequest request = new GetBadgeViewRequest();

        request.SendMan_Tel = MyApplication.getInstance().getLoginInfo().DataValue.User_Tel;
        requestBase.DataValue = request;
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.User_Name = MyApplication.getInstance().getLoginInfo().DataValue.User_Name;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;

        final JSONObject jsonObject = GsonUtil.newInstance().toJson(requestBase);
        VolleyManager.newInstance().PostJsonRequest("Tag", TMSService.GetReadNoTicketNumber_Url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.d("-->>onResponse============>>" + response.toString());
                        GetBadgeViewResponse badgeViewResponse = JsonUtils.parseData(response.toString(), GetBadgeViewResponse.class);
                        LoggerUtil.json(response.toString());
                        Message msg = Message.obtain();
                        if (badgeViewResponse != null) {
                            if (badgeViewResponse.Success && badgeViewResponse.DataValue != null) {
                                List<mBadgeView> mBadge = badgeViewResponse.DataValue;
                                msg.what = Constants.REQUEST_BADGEVIEW_SUCC;
                                msg.obj = mBadge;
                                mHandlerHolder.sendMessage(msg);
                            } else {
                                if (badgeViewResponse.DataValue == null) {
                                    LoggerUtil.d("badgeViewResponse.DataValue====" + badgeViewResponse.DataValue);
                                } else {
                                    LoggerUtil.d("badgeViewResponse.ErrorText====" + badgeViewResponse.ErrorText);
                                }
                            }
                        } else {
                            LoggerUtil.d("badgeViewResponse====" + badgeViewResponse.toString());
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        LogUtil.i("ErrorListener============>>" + TMSService.GetReadNoTicketNumber_Url);

                    }
                });
    }
    //角标数据
    private void requestMsgCount() {
        requestBase = new DataRequestBase();
        final GetMessageCountRequest request = new GetMessageCountRequest();

        request.User_Oid = MyApplication.getInstance().getLoginInfo().DataValue.User_Tel;
        request.IsRead = "0";
        requestBase.DataValue = request;
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.User_Name = MyApplication.getInstance().getLoginInfo().DataValue.User_Name;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;

        final JSONObject jsonObject = GsonUtil.newInstance().toJson(requestBase);
        LoggerUtil.json(jsonObject.toString());
        VolleyManager.newInstance().PostJsonRequest("Tag", TMSService.GetMessageCount_Url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.d("-->>onResponse============>>" + response.toString());

                        LoggerUtil.json(response.toString());
                        GetMsgCountResponse bean = JsonUtils.parseData(response.toString(), GetMsgCountResponse.class);

                        Message msg = Message.obtain();
                        if (bean != null) {
                            if (bean.Success) {
                                msg.what = Constants.REQUEST_MSG_COUNT_SUCC;
                                msg.obj = bean.DataValue;
                                mHandlerHolder.sendMessage(msg);
                            } else {
                                if (bean.DataValue == null) {
                                    LoggerUtil.d("bean.DataValue====" + bean.DataValue);
                                } else {
                                    LoggerUtil.d("bean.ErrorText====" + bean.ErrorText);
                                }
                            }
                        } else {
                            LoggerUtil.d("badgeViewResponse====" + bean.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtil.i("ErrorListener============>>" + TMSService.GetMessageCount_Url);
                    }
                });
    }

    @Override
    public void handlerMessage(Message msg) {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                HomePageBean bean = (HomePageBean) msg.obj;
                if (bean != null) {
                    List<HomePageBean.AdvertImgListBean> datalist;
                    datalist = bean.getAdvertImgList();
                    if (datalist != null) {
                        list.clear();
                        list.addAll(datalist);
                        initData();
                    }
                }
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                break;
            case Constants.REQUEST_BADGEVIEW_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                break;
            case Constants.REQUEST_BADGEVIEW_SUCC:
                //角标
                List<mBadgeView> datalist = (List<mBadgeView>) msg.obj;
                if (datalist != null) {
                    mList.clear();
                    mList.addAll(datalist);
                }
                for (int i = 0; i < mList.size(); i++) {
                    mBadgeView=mList.get(i);
                    badgeView.setBadgeCount(Integer.valueOf(mList.get(i).NoTicketNumber));
                }
                break;
            case Constants.REQUEST_MSG_COUNT_SUCC:
                String MsgCount = (String) msg.obj;
                //设置消息角标
                if (!TextUtils.isEmpty(MsgCount) && !MsgCount.equals("0")) {
                    messageImg.showCriclePointBadge();
                }
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(getActivity(), Utils
                        .getResourcesString(R.string.request_error));
                break;
        }
    }
}
