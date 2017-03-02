package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
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
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.adapter.Dr_MsgAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.MessageContentBean;
import com.cvnavi.logistics.i51ehang.app.bean.driver.request.MessageContentRequest;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.GetMessageContentResponse;
import com.cvnavi.logistics.i51ehang.app.bean.model.mUserInfoBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

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
 * Depict:司机消息中心页面
 */

public class Dr_MsgActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener {

    private static final String TAG = "Dr_MsgActivity";

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
    @BindView(R.id.my_msg_listView)
    PullToRefreshListView mMyMsgListView;
    @BindView(R.id.no_data_ll)
    LinearLayout mNoDataLl;

    private HandlerUtils.HandlerHolder mHandlerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_msg_activity);
        ButterKnife.bind(this);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        initView();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefreshs();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, Dr_MsgActivity.class);
        context.startActivity(starter);
    }

    private SweetAlertDialog lodingDialog;
    private DataRequestBase dataRequestBase;
    private mUserInfoBase mUserInfoBase;
    private Dr_MsgAdapter mAdapter;
    private List<MessageContentBean> mList;
    private ListView mListView;
    private int position;//删除的item

    private void initView() {
        mTitleTv.setText("消息中心");
        mOperationBtn.setVisibility(View.VISIBLE);
        mOperationBtn.setText("清空");
    }

    public void init() {
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        mListView = mMyMsgListView.getRefreshableView();
        mMyMsgListView.setMode(PullToRefreshBase.Mode.BOTH);

        initRefrensh();

        dataRequestBase = new DataRequestBase();
        mUserInfoBase = MyApplication.getInstance().getLoginInfo().DataValue;
        mList = new ArrayList<>();
        mAdapter = new Dr_MsgAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position1, long id) {
                DialogUtils.showMessageDialog(Dr_MsgActivity.this, "提示", "确定删除？", "确定", "取消", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        if (CustomDialogListener.BUTTON_NO == closeType) {
                        } else if (CustomDialogListener.BUTTON_OK == closeType) {
                            MessageContentBean bean = mAdapter.getItem(position1 - 1);
                            position = position1 - 1;
                            DeleteMsgRequest(DriverService.DelMessageContent_Request_Url, bean.getSerial_Oid(), null, Constants.DELETE_SUCC);
                        }
                    }
                });
                return true;
            }
        });
    }

    private int pageInt = 1;
    private boolean isRefresh = true;

    private void initRefrensh() {
        mMyMsgListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                onRefreshs();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                onLoad();
            }
        });
    }

    private void onRefreshs() {
        isRefresh = true;
        pageInt = 1;
        dataRequestBase.Page = pageInt;
        if (mUserInfoBase != null) {
            loadDataRequest(DriverService.GetMessageContent_Request_Url);
        }
    }

    private void onLoad() {
        isRefresh = false;
        pageInt++;
        dataRequestBase.Page = pageInt;
        if (mUserInfoBase != null) {
            loadDataRequest(DriverService.GetMessageContent_Request_Url);
        }
    }

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        dataRequestBase.User_Key = mUserInfoBase.User_Key;
        dataRequestBase.UserType_Oid = mUserInfoBase.UserType_Oid;
        dataRequestBase.Token = mUserInfoBase.Token;
        dataRequestBase.Company_Oid = mUserInfoBase.Company_Oid;

        dataRequestBase.DataValue = new MessageContentRequest(mUserInfoBase.User_Tel); //JsonUtils.toJsonData(getDriverListRequest);
        LoggerUtil.json(TAG, GsonUtil.newInstance().toJsonStr(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(null, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoggerUtil.json(TAG, response.toString());
                GetMessageContentResponse response1 = GsonUtil.newInstance().fromJson(response.toString(), GetMessageContentResponse.class);
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
                LoggerUtil.d("-->>" + error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });

    }

    private void DeleteMsgRequest(final String Url, String Serial_Oid, String Useroid, final int Requestcode) {
        lodingDialog.show();
        dataRequestBase.User_Key = mUserInfoBase.User_Key;
        dataRequestBase.UserType_Oid = mUserInfoBase.UserType_Oid;
        dataRequestBase.Token = mUserInfoBase.Token;
        dataRequestBase.Company_Oid = mUserInfoBase.Company_Oid;

        dataRequestBase.DataValue = new MessageContentBean(Serial_Oid, Useroid); //JsonUtils.toJsonData(getDriverListRequest);
        LoggerUtil.json(GsonUtil.newInstance().toJson(dataRequestBase).toString());
        VolleyManager.newInstance().PostJsonRequest(null, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoggerUtil.json("-->>response" + response.toString());
                GetMessageContentResponse response1 = JsonUtils.parseData(response.toString(), GetMessageContentResponse.class);
                Message msg = Message.obtain();

                if (response1 == null) {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                    mHandlerHolder.sendMessage(msg);
                } else {
                    if (response1.Success) {
                        msg.obj = response1.DataValue;
                        msg.what = Requestcode;
                        mHandlerHolder.sendMessage(msg);
                    } else {
                        msg.obj = response1.ErrorText;
                        msg.what = Constants.REQUEST_FAIL;
                        mHandlerHolder.sendMessage(msg);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LoggerUtil.d("-->>" + error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });
    }


    @OnClick({R.id.back_llayout, R.id.operation_llayout, R.id.operation_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.operation_btn:
                if (isAll) {
                    DialogUtils.showMessageDialog(Dr_MsgActivity.this, "消息清空", "确定清空消息？", "确定", "取消", new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            if (CustomDialogListener.BUTTON_NO == closeType) {
                            } else if (CustomDialogListener.BUTTON_OK == closeType) {
                                DeleteMsgRequest(DriverService.DelMessageContent_Request_Url, null,
                                        mUserInfoBase.User_Tel, Constants.DELETE_ALL_SUCC);
                            }
                        }
                    });
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean isAll = true;//是否清空

    @Override
    public void handlerMessage(Message msg) {
        if (lodingDialog != null) {
            lodingDialog.dismiss();
        }
        mMyMsgListView.onRefreshComplete();
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                if (msg.obj != null) {
                    if (isRefresh) {
                        mList.clear();
                    }
                    List<MessageContentBean> dataList = (List<MessageContentBean>) msg.obj;
                    if (dataList != null) {
                        mList.addAll(dataList);
                    }
                    if (mAdapter.getCount() > 0) {
                        mNoDataLl.setVisibility(View.GONE);
                        isAll = true;
                    } else {
                        mNoDataLl.setVisibility(View.VISIBLE);
                        isAll = false;
                        mOperationBtn.setTextColor(Utils.getResourcesColor(R.color.color_878787));
                    }
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                break;
            case Constants.DELETE_SUCC://删除
                DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                mAdapter.removeItem(position);
                if (mAdapter.getCount() == 0) {
                    isAll = false;
                    mOperationBtn.setTextColor(Utils.getResourcesColor(R.color.color_878787));
                } else {
                    isAll = true;
                }
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(Dr_MsgActivity.this, Utils.getResourcesString(R.string.request_error));
                break;
            case Constants.DELETE_ALL_SUCC://清空
                DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                mAdapter.removeAll();
                if (mAdapter.getCount() == 0) {
                    isAll = false;
                    mOperationBtn.setTextColor(Utils.getResourcesColor(R.color.color_878787));
                } else {
                    isAll = true;
                }
        }
    }
}
