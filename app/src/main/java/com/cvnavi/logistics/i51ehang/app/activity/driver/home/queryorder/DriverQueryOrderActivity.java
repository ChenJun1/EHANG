package com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.myorder.MyOrderDetailAcitivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.queryorder.DriverHomeQueryOrderAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.queryorder.DriverHomeSearchListViewAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.DriverOrderDeatilActivity;
import com.cvnavi.logistics.i51ehang.app.bean.litepal.Inquiries;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarShift;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrder;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetOrederListRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetOrederListResponse;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.order.MyOrderListener;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.trans.OnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.MyInputMethodManager;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
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
*创建时间：2017/1/17 下午2:02
*描述：货单查询Activity
************************************************************************************/

public class DriverQueryOrderActivity extends BaseActivity implements OnClickItemListener, MyOrderListener {
    private static final int REQUEST_CODE_SEARCH = 0x11;
    @BindView(R.id.order_lv)
    ListView orderLv;
    @BindView(R.id.clear_tv)
    TextView clearTv;
    @BindView(R.id.scanning_et)
    EditText scanningEt;
    @BindView(R.id.search_lv)
    ListView searchLv;
    @BindView(R.id.query_rl)
    RelativeLayout queryRl;
    @BindView(R.id.history_num_tv)
    TextView historyNumTv;
    @BindView(R.id.scan_QR_img)
    ImageView scanQRImg;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.bg_ll)
    LinearLayout bgLl;
    @BindView(R.id.root_ll)
    LinearLayout rootLl;

    private List<mOrder> searchList;

    private DriverHomeQueryOrderAdapter adapter = null;
    private DriverHomeSearchListViewAdapter searchAdapter = null;
    private Context context;
    private List<Inquiries> dataList = new ArrayList<Inquiries>();
    private String searchStr;//搜索内容
    private SweetAlertDialog loadingDialog;
    private GetAppLoginResponse loginInfo;
    private String userTel;
    private String Company_Oid;

    public static void start(Context context) {
        Intent starter = new Intent(context, DriverQueryOrderActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home_query_order);
        ButterKnife.bind(this);
        scanQRImg.setVisibility(View.GONE);
        rightTv.setVisibility(View.VISIBLE);
        backLlayout.setVisibility(View.VISIBLE);
        rootLl.setBackgroundColor(Utils.getResourcesColor(R.color.top_bar_title_bg_color));
        context = this;
        initQueryComponent();
        loginInfo = MyApplication.getInstance().getLoginInfo();
        dataList = checkInfoListFromDb();
        if (dataList != null && dataList.size() >= 0) {
            historyNumTv.setText(String.format(Utils.getResourcesString(R.string.my_history_num), dataList.size()));
        } else {
            historyNumTv.setText(String.format(Utils.getResourcesString(R.string.my_history_num), 0));

        }
        adapter = new DriverHomeQueryOrderAdapter(dataList, this, this);
        orderLv.setAdapter(adapter);
        queryRl.setVisibility(View.VISIBLE);
        orderLv.setVisibility(View.VISIBLE);
        searchLv.setVisibility(View.GONE);
    }

    private void initQueryComponent() {
        String length = SharedPreferencesTool.getString(SharedPreferencesTool.QUERY_LENGTH, "6");
        int len = Integer.parseInt(length);
        scanningEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(len)});
        scanningEt.setHint(String.format("请输入%1$s位查询码", length));

        scanningEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    //监听如果没有查询码 自动显示历史记录
                    orderLv.setVisibility(View.VISIBLE);
                    searchLv.setVisibility(View.VISIBLE);
                    queryRl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        scanningEt.setFocusableInTouchMode(true);
        scanningEt.setLongClickable(false);
        scanningEt.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        MyInputMethodManager.setImeOptions(scanningEt, EditorInfo.IME_ACTION_SEARCH);
        MyInputMethodManager.setOnKeyListener(scanningEt, new MyInputMethodManager.MyInputMethodOnKeyListener() {
            @Override
            public void onInputMethodeOnkey() {
                onSearch();
            }
        });
    }

    // 搜索 345455
    private void onSearch() {
        searchStr = scanningEt.getText().toString();
        if (searchStr == null || searchStr.trim().length() <= 0) {
            // 没有内容，不进行搜索，提示请输入内容
            DialogUtils.showWarningToast(Utils.getResourcesString(R.string.please_enter_search_content));
        } else {
            // 关闭输入法
            MyInputMethodManager.closeInputMethod(this, scanningEt);
            String newStr = creatNewSearchCode(searchStr);
            scanningEt.setText(newStr);
            scanningEt.setSelection(newStr.length());
            getInfo(newStr);
        }
    }


    /**
     * 生成一个新的查询码 不足7位前面自动补齐0
     *
     * @param searchStr
     * @return
     */
    private String creatNewSearchCode(String searchStr) {
        String length = SharedPreferencesTool.getString(SharedPreferencesTool.QUERY_LENGTH, "6");
        int len = Integer.parseInt(length);

        if (searchStr.length() < len) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < (len - searchStr.length()); i++) {
                sb.append("0");
            }
            return sb.toString() + searchStr;
        } else {
            return searchStr;
        }

    }


    /**
     * 从数据库获取前十条数据(根据手机号)
     */
    private List<Inquiries> checkInfoListFromDb() {
        if (loginInfo != null && loginInfo.DataValue != null && loginInfo.DataValue.User_Tel != null) {
            userTel = loginInfo.DataValue.User_Tel;
            List<Inquiries> list = DataSupport.where("extend_one = ?", userTel + Company_Oid).limit(10).find(Inquiries.class);
            if (list != null) {
                Collections.reverse(list);
            }
            return list;
        } else {
            return null;
        }
    }


    private void cleanDb() {
        if (userTel != null && dataList != null && dataList.size() > 0) {
            DialogUtils.showMessageDialog(this, "温馨提示", "确认删除历史记录?", "确定", "取消", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    if (closeType == CustomDialogListener.BUTTON_OK) {
                        DataSupport.deleteAll(Inquiries.class, "extend_one = ?", userTel + Company_Oid);
                        historyNumTv.setText(String.format(Utils.getResourcesString(R.string.my_history_num), 0));
                        dataList.clear();
                        adapter.setList(null);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            DialogUtils.showWarningToast("已经为空！");
        }
    }


    private void saveInfoToDb(String bengining, String arriving, String data, String All_Ticket_No, String orderId, String ticketFee, String goodsNum) {
        if (userTel != null) {
            Inquiries inquiries = new Inquiries();
            inquiries.setOrderId(orderId);
            inquiries.setArriving(arriving);
            inquiries.setData(data);
            inquiries.setBengining(bengining);
            inquiries.setOrderId(orderId);
            inquiries.setAll_Ticket_No(All_Ticket_No);
            inquiries.setGoodsNum(goodsNum);
            inquiries.setTickFee(ticketFee);
            inquiries.setUserTel(userTel);
            inquiries.setExtend_one(userTel + Company_Oid);

            inquiries.save();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    private void getInfo(String str) {
        showDialog();
        loginInfo = MyApplication.getInstance().getLoginInfo();
        GetOrederListRequest dataValue = new GetOrederListRequest();
        dataValue.Search_No = str;

        if (loginInfo.DataValue.UserType_Oid.equals("E")) {
            //货主查询货主的
            dataValue.SendMan_Tel = loginInfo.DataValue.User_Tel;
        }

        if (loginInfo.DataValue.UserType_Oid.equals("G")) {
            //司机查询司机的
            dataValue.Driver_Tel = loginInfo.DataValue.User_Tel;
        }

        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = dataValue;
        dataRequestBase.User_Key = loginInfo.DataValue.User_Key;
        dataRequestBase.UserType_Oid = loginInfo.DataValue.UserType_Oid;
        dataRequestBase.Token = loginInfo.DataValue.Token;
        dataRequestBase.Company_Oid = loginInfo.DataValue.Company_Oid;
        dataRequestBase.Org_Code = loginInfo.DataValue.Org_Code;

        LogUtil.d("-->> request:" + GsonUtil.newInstance().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TMSService.GetReadNoTicket_Url, TMSService.GetReadNoTicket_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                GetOrederListResponse info = GsonUtil.newInstance().fromJson(jsonObject, GetOrederListResponse.class);
                LogUtil.d("-->> respon = " + jsonObject.toString());
                Message msg = Message.obtain();
                if (info.Success) {
                    msg.obj = info.DataValue;
                    msg.what = Constants.REQUEST_SUCC;
                } else {
                    msg.obj = info.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                }
                myHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_ERROR;
                myHandler.sendMessage(message);
            }
        });


    }

    private void showDialog() {
        if (loadingDialog == null) {
            loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        }
        loadingDialog.show();
    }

    private void dissDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dissDialog();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    searchList = (List<mOrder>) msg.obj;
                    if (searchList != null && searchList.size() > 0) {
                        searchAdapter = new DriverHomeSearchListViewAdapter(context, searchList, DriverQueryOrderActivity.this);
                        searchLv.setAdapter(searchAdapter);
                        orderLv.setVisibility(View.GONE);
                        queryRl.setVisibility(View.GONE);
                        searchLv.setVisibility(View.VISIBLE);
                    } else {
                        DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverQueryOrderActivity.this, "未搜索到数据！", new CustomDialogListener() {
                            @Override
                            public void onDialogClosed(int closeType) {

                            }
                        });
                        orderLv.setVisibility(View.VISIBLE);
                        queryRl.setVisibility(View.VISIBLE);
                        searchLv.setVisibility(View.GONE);
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    String infoError = (String) msg.obj;
                    if (infoError != null) {
                        DialogUtils.showFailToast(infoError);
                    } else {
                        DialogUtils.showFailToast(Utils.getResourcesString(R.string.get_data_fail));
                    }
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(DriverQueryOrderActivity
                            .this, Utils.getResourcesString(R.string.request_error));

                    break;
            }

        }

    };


    @Override
    public void onClick(int position) {
        if (searchList != null && searchList.get(position) != null) {
            mOrder mOrder = searchList.get(position);
            Inquiries info = new Inquiries();
            info.setBengining(mOrder.SendStation);
            info.setArriving(mOrder.ArrStation + "" + mOrder.ArrStation_Area);
            info.setData(mOrder.Operate_DateTime);
            info.setAll_Ticket_No(mOrder.All_Ticket_No);
            info.setOrderId(mOrder.Ticket_No);
            info.setGoodsNum(mOrder.Goods_Num);
            info.setTickFee(mOrder.Ticket_Fee);

            //保存到数据库，启动新货单详情
            if (!dbHasInfo(info.getAll_Ticket_No())) {
                //数据库中没有该条数据插入数据
                saveInfoToDb(info.getBengining(), info.getArriving(), info.getData(), info.getAll_Ticket_No(), info.getOrderId(), info.getTickFee(), info.getGoodsNum());
            }

            if (loginInfo.DataValue.UserType_Oid.equals("E")) {
                mOrder bean = searchList.get(position);
                //货主跳到货单明细
                Intent intent = new Intent(this, MyOrderDetailAcitivity.class);
                intent.putExtra(MyOrderDetailAcitivity.ORDER_ID, searchList.get(position));
                startActivity(intent);
            } else if (loginInfo.DataValue.UserType_Oid.equals("F")) {
                //员工跳到货单详情
                DriverHomeOrderDeatilActivity.startActivity(this, REQUEST_CODE_SEARCH, searchList.get(position).All_Ticket_No);
            } else {
                DriverOrderDeatilActivity.startActivity(this, REQUEST_CODE_SEARCH, searchList.get(position).All_Ticket_No, null);
            }


        }


    }

    @Override
    public void onLongClick(mCarShift info, int position) {

    }

    @Override
    public void onClickOrder(int position) {
        if (dataList != null && dataList.get(position) != null) {
            if (loginInfo.DataValue.UserType_Oid.equals("E")) {
                //货主跳到货单明细
                Intent intent = new Intent(this, MyOrderDetailAcitivity.class);
                mOrder bean = new mOrder();
                bean.All_Ticket_No = dataList.get(position).getAll_Ticket_No();
                bean.Goods_Num = dataList.get(position).getGoodsNum();
                bean.Ticket_Fee = dataList.get(position).getTickFee();
                intent.putExtra(MyOrderDetailAcitivity.ORDER_ID, bean);
                startActivity(intent);
            } else if (loginInfo.DataValue.UserType_Oid.equals("F")) {
                //员工跳到货单详情
                DriverHomeOrderDeatilActivity.startActivity(this, REQUEST_CODE_SEARCH, dataList.get(position).getAll_Ticket_No());
            } else {
                DriverOrderDeatilActivity.startActivity(this, REQUEST_CODE_SEARCH, dataList.get(position).getAll_Ticket_No(), null);
            }
        }

    }

    /**
     * 检查数据库中是否有该条数据
     *
     * @return
     */
    private boolean dbHasInfo(String All_Ticket_No) {
        List<Inquiries> list = DataSupport.where("extend_one = ?", userTel + Company_Oid).find(Inquiries.class);
        if (list != null && list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getAll_Ticket_No().equals(All_Ticket_No)) {
                    return true;
                }
            }
        }

        return false;
    }


    @OnClick({R.id.back_llayout, R.id.right_tv, R.id.clear_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.right_tv:
                onSearch();
                break;
            case R.id.clear_tv:
                cleanDb();
                break;
        }
    }
}
