package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.MainThread;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.adapter.DrSendCarDetailLineFollowAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder.DriverCarExceptionUpLoadActivity;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.MySendCarDetailBean;
import com.cvnavi.logistics.i51ehang.app.bean.driver.request.SetMsgRequest;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.GetMySendCarDetailResponseBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.Model_LetterTrace_Node;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.popupwindow.MyPopWindow;

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
 * Depict: 发车详情 页面
 */

public class DrSendCarDetailActivity extends BaseActivity implements DrSendCarDetailLineFollowAdapter.TaskLineLookPicListener, HandlerUtils.OnReceiveMessageListener {

    public static final String TAG = "DrSendCarDetailActivity";
    public static final String LETTEROID = "LETTEROID";//货单id
    public static final String MSGID = "MSGID";//消息id

    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.back_llayout)
    LinearLayout mBackLlayout;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.operation_btn)
    Button mOperationBtn;
    @BindView(R.id.operation_llayout)
    LinearLayout mOperationLlayout;

    @BindView(R.id.All_Ticket_Nol_tv)
    TextView mAllTicketNolTv;
    @BindView(R.id.Line_Type_tv)
    TextView mLineTypeTv;
    @BindView(R.id.line_name_tv)
    TextView mLineNameTv;
    @BindView(R.id.gua_car_tv)
    TextView mGuaCarTv;
    @BindView(R.id.Ticket_Count_tv)
    TextView mTicketCountTv;
    @BindView(R.id.Goods_Num_tv)
    TextView mGoodsNumTv;
    @BindView(R.id.Goods_Weight_tv)
    TextView mGoodsWeightTv;
    @BindView(R.id.Bulk_Weight_tv)
    TextView mBulkWeightTv;
    @BindView(R.id.ReceiveMan_Tel_tv)
    TextView mReceiveManTelTv;
    @BindView(R.id.dr_line_follow_list)
    ListView mDrLineFollowList;
    @BindView(R.id.operation_up_btn)
    Button mOperationUpBtn;
    @BindView(R.id.Shuttle_Fee_tv)
    TextView mShuttleFeeTv;
    @BindView(R.id.daohang_bt)
    Button mDaohangBt;
    @BindView(R.id.zheng_tv)
    TextView mZhengTv;
    @BindView(R.id.pei_tv)
    TextView mPeiTv;
    @BindView(R.id.Destination_tv)
    TextView mDestinatioTv;
    @BindView(R.id.line_name_ll)
    LinearLayout mLineNameLl;
    @BindView(R.id.Destination_ll)
    LinearLayout mDestinationLl;

    private String LetterOid;

    private String MsgId;

    private MyPopWindow mMyPopWindow;

    private DrSendCarDetailLineFollowAdapter mAdapter;

    private List<MySendCarDetailBean.LineNodesBean> list;

    private SweetAlertDialog lodingDialog;

    private DataRequestBase dataRequestBase;

    private MySendCarDetailBean dateBean;

    private HandlerUtils.HandlerHolder mHandlerHolder;

    private ArrayList<String> mStringList = new ArrayList<String>() {{
        add("装车清单");
        add("异常信息");
    }};

    public static void start(Context context, String LetterOid, String MsgId) {
        Intent starter = new Intent(context, DrSendCarDetailActivity.class);
        starter.putExtra(LETTEROID, LetterOid);
        starter.putExtra(MSGID, MsgId);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_send_car_detail_activity);
        ButterKnife.bind(this);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        initView();
        init();
    }

    private void init() {
        if (TextUtils.isEmpty(getIntent().getStringExtra(LETTEROID)) == false) {
            LetterOid = getIntent().getStringExtra(LETTEROID);
        }
        dataRequestBase = new DataRequestBase();

        list = new ArrayList<>();
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mAdapter = new DrSendCarDetailLineFollowAdapter(list, this, this);
        mDrLineFollowList.setAdapter(mAdapter);
        loadDataRequest(DriverService.GetLeaveCarCodeDetail_URL);
        if (TextUtils.isEmpty(getIntent().getStringExtra(MSGID)) == false) {
            MsgId = getIntent().getStringExtra(MSGID);
            SetMessageRead(DriverService.SetMessageRead_Request_Url);
        }
    }

    private void initView() {
        mOperationLlayout.setVisibility(View.VISIBLE);
        mOperationBtn.setVisibility(View.VISIBLE);
        mMyPopWindow = new MyPopWindow(this, mStringList);

        mMyPopWindow.setOnItemClickListener(new MyPopWindow.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                switch (pos) {
                    case 0:
                        DrDressCarDetailActivity.start(DrSendCarDetailActivity.this, LetterOid);
                        break;
                    case 1:
                        Dr_Car_ExceptionInfoActivity.startActivity(DrSendCarDetailActivity.this, LetterOid);
                        break;
                }
            }
        });
        mOperationBtn.setText("更多");
        mTitleTv.setText("发车详情");
    }


    @OnClick({R.id.back_llayout, R.id.daohang_bt, R.id.operation_btn, R.id.operation_up_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.operation_btn:
                mMyPopWindow.showLocation(R.id.operation_btn);
                break;
            case R.id.operation_up_btn:
                if (dateBean != null) {
                    DriverCarExceptionUpLoadActivity.start(this, new TaskBean(dateBean.getCarCode(), dateBean.getCarCode_Key(), dateBean.getDriver_Key(), dateBean.getLPSCarCode_Key(), dateBean.getDriver_Key(), dateBean.getLetter_Oid()));
                }
                break;
            case R.id.daohang_bt:
                if (!TextUtils.isEmpty(dateBean.getLPSCarCode_Key())) {
                    DrRoutePlanActivity.start(this, dateBean.getLetter_Oid(), dateBean.getLPSCarCode_Key(), dateBean.getLine_Name(), dateBean.getFullCar_Destination());
                }
                break;
        }
    }

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = LetterOid;
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson
                (dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoggerUtil.json(response.toString());
                GetMySendCarDetailResponseBean response1 = GsonUtil.newInstance().fromJson(response.toString(),
                        GetMySendCarDetailResponseBean.class);
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

    private void SetMessageRead(final String Url) {

        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;

        dataRequestBase.DataValue = new SetMsgRequest(MsgId);
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson
                (dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoggerUtil.json(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("-->>" + error.toString());
            }
        });
    }

    @MainThread
    private void setViewData(MySendCarDetailBean data) {

        SetViewValueUtil.setTextViewValue(mAllTicketNolTv, data.getCarCode());
//        SetViewValueUtil.setTextViewStr(mLineTypeTv, data.getLine_Type());
        SetViewValueUtil.setTextViewStr(mLineNameTv, data.getLine_Name());
        SetViewValueUtil.setTextViewStr(mGuaCarTv, data.getBoxCarCode());
        SetViewValueUtil.setTextViewValue(mTicketCountTv, data.getTicket_Count(), "票");
        SetViewValueUtil.setTextViewValue(mGoodsNumTv, data.getGoods_Num(), "件");
        SetViewValueUtil.setTextViewValue(mGoodsWeightTv, ContextUtil.getDouble(data.getGoods_Weight()), "kg");
        SetViewValueUtil.setTextViewValue(mBulkWeightTv, ContextUtil.getDouble(data.getBulk_Weight()), "m³");
        SetViewValueUtil.setTextViewValue(mReceiveManTelTv, data.getLeave_DateTime());
        SetViewValueUtil.setTextViewValue(mShuttleFeeTv, data.getShuttle_Fee() + "元");
        SetViewValueUtil.setTextViewValue(mDestinatioTv, data.getFullCar_Destination());
        if (!TextUtils.isEmpty(data.getTraffic_Mode()) && data.getTraffic_Mode().equals("整")) {
            mZhengTv.setVisibility(View.VISIBLE);
            mPeiTv.setVisibility(View.GONE);
        } else {
            mZhengTv.setVisibility(View.GONE);
            mPeiTv.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(data.getFullCar_Destination())) {//是否返回有目的地
            mDestinationLl.setVisibility(View.GONE);
        } else {
            mDestinationLl.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(data.getLine_Name())) {//是否有返回线路
            mLineNameLl.setVisibility(View.GONE);
        } else {
            mLineNameLl.setVisibility(View.VISIBLE);
        }

//        //线路和目的地都显示
//        mDestinationLl.setVisibility(View.VISIBLE);
//        mLineNameLl.setVisibility(View.VISIBLE);


        if (!TextUtils.isEmpty(dateBean.getCarCodeStatus()) && dateBean.getCarCodeStatus().equals("已完成")) {//车辆状态
            mOperationUpBtn.setVisibility(View.GONE);
            mDaohangBt.setVisibility(View.GONE);
        } else {
            mOperationUpBtn.setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(dateBean.getLPSCarCode_Key())) {//是否有GPS
                mDaohangBt.setVisibility(View.GONE);
            } else {
                mDaohangBt.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onLookPic(Model_LetterTrace_Node node) {
    }

    @Override
    public void handlerMessage(Message msg) {
        if (lodingDialog != null) {
            lodingDialog.dismiss();
        }
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                dateBean = (MySendCarDetailBean) msg.obj;
                if (dateBean != null) {
                    list.clear();
                    List<MySendCarDetailBean.LineNodesBean> dataList = dateBean.getLine_Nodes();
                    setViewData(dateBean);
                    if (dataList != null && dataList.size() > 0) {
                        list.addAll(dataList);
                    }
                    mAdapter.notifyDataSetChanged();
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
                DialogUtils.showMessageDialogOfDefaultSingleBtn(DrSendCarDetailActivity.this, Utils
                        .getResourcesString(R.string.request_error));
                break;
        }
    }
}
