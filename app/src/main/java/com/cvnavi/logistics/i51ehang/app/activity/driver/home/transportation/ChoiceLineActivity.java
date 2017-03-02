package com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.trans.ChoiceLineListViewAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.mLineInfo;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetLineListResponse;
import com.cvnavi.logistics.i51ehang.app.callback.manager.LineChoiceCallBackManager;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.popupwindow.MyPopWindow;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

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
 * 创建时间：2017/1/17 下午2:08
 * 描述：选择线路
 ************************************************************************************/

public class ChoiceLineActivity extends BaseActivity {
    private static String TAG = ChoiceLineActivity.class.getName();

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.choice_line_lv)
    PullToRefreshListView choiceLineLv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.no_data_tv)
    TextView mNoDataTv;
    @BindView(R.id.noData_view)
    FrameLayout mNoDataView;

    private ListView actualListView;
    private ChoiceLineListViewAdapter adapter;
    private List<mLineInfo> dataList;
    private Context mContext;
    private String checkType = "-1";
    private SweetAlertDialog loading;
    private int pageInt = 1;//分页num
    private boolean isRefresh = false;//是否刷新过了

    /**
     * 启动方法
     *
     * @param context
     * @param type
     */
    public static void start(Context context, int type) {
        Intent starter = new Intent(context, ChoiceLineActivity.class);
        starter.putExtra("type", type);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_line);
        ButterKnife.bind(this);
        mContext = this;
        if (getIntent().getIntExtra("type", 0) == 0) {
            titleTv.setText(R.string.choice_line);
        } else {
            titleTv.setText("我的线路");
        }
        operationBtn.setVisibility(View.VISIBLE);
        operationBtn.setText("全部");
        dataList = new ArrayList<>();
        adapter = new ChoiceLineListViewAdapter(dataList, this);
        choiceLineLv.setMode(PullToRefreshBase.Mode.BOTH);
        choiceLineLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(ChoiceLineActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isRefresh = true;
                pageInt = 1;
                loadData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                loadData();
            }
        });
//获取当前实际的listview实例
        actualListView = choiceLineLv.getRefreshableView();
        actualListView.setAdapter(adapter);
        actualListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getIntent().getIntExtra("type", 0) == 0) {
                    mLineInfo lineInfo = dataList.get(position - 1);
                    LineChoiceCallBackManager.newInstance().fire(lineInfo);
                    ChoiceLineActivity.this.finish();
                } else {

                }
            }
        });
        loadData();
    }

    private void loadData() {
        if (loading == null) {
            loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        }
        loading.show();
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.Page = pageInt;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        DataLine dataLine = new DataLine();
        dataLine.Line_Type_Oid = checkType;
        dataRequestBase.DataValue = dataLine; //JsonUtils.toJsonData(lineListRequest);
        MLog.json(GsonUtil.newInstance().toJsonStr(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TAG, TMSService.GetLineList_Request_Url,
                GsonUtil.newInstance().toJson(dataRequestBase),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MLog.json(response.toString());
                        GetLineListResponse responseBase = GsonUtil.newInstance().fromJson(response, GetLineListResponse.class);

                        Message msg = Message.obtain();
                        if (responseBase.Success) {
                            msg.obj = responseBase.DataValue;
                            msg.what = Constants.REQUEST_SUCC;
                            myHandler.sendMessage(msg);
                        } else {
                            msg.obj = responseBase.ErrorText;
                            msg.what = Constants.REQUEST_FAIL;
                            myHandler.sendMessage(msg);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Message msg = Message.obtain();
                        msg.what = Constants.REQUEST_FAIL;
                        myHandler.sendMessage(msg);
                    }
                });
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loading.dismiss();
            choiceLineLv.onRefreshComplete();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        if (isRefresh) {
                            isRefresh = false;
                            dataList.clear();
                        }
                        List<mLineInfo> list = (List<mLineInfo>) msg.obj;
                        dataList.addAll(list);
                        if (dataList.size() > 0) {
                            mNoDataView.setVisibility(View.GONE);
                        } else {
                            mNoDataView.setVisibility(View.VISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(ChoiceLineActivity.this, "获取数据失败!", new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            finish();
                        }
                    });

                    break;
            }
        }
    };

    @OnClick({R.id.back_llayout, R.id.operation_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.operation_btn:
                ArrayList<String> itemName = new ArrayList<>();
                itemName.add("全部");
                itemName.add("干线");
                itemName.add("支线");
                MyPopWindow myPopWindow = new MyPopWindow(this, itemName);
                myPopWindow.showLocation(R.id.operation_btn);
                myPopWindow.setOnItemClickListener(new MyPopWindow.OnItemClickListener() {
                    @Override
                    public void onClick(int pos) {
                        switch (pos) {
                            case 0:
                                checkType = "-1";
                                operationBtn.setText("全部");

                                break;
                            case 1:
                                checkType = "0";
                                operationBtn.setText("干线");
                                break;
                            case 2:
                                checkType = "1";
                                operationBtn.setText("支线");
                                break;
                        }
                        isRefresh = true;
                        pageInt = 1;
                        loadData();
                    }
                });
                break;
        }
    }


    class DataLine {
        public String Line_Type_Oid;
    }

}
