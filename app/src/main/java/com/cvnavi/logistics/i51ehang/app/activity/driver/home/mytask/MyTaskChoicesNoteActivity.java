package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter.MyTaskChoicseAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.LineNoteBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetLineNoteListResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * 版权所有势航网络
 * Created by ${ChenJ} on 2016/7/25.
 */
public class MyTaskChoicesNoteActivity extends BaseActivity {
    private final String TAG = MyTaskChoicesNoteActivity.class.getName();
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.my_task_off_the_stocks_pull_lv)
    PullToRefreshListView myTaskOffTheStocksPullLv;
    @BindView(R.id.empty_tv)
    TextView emptyTv;

    private ListView myListView;
    private SweetAlertDialog lodingDialog;
    private List<LineNoteBean> list;
    private MyTaskChoicseAdapter adapter;
    private TaskBean taskBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices_note);
        ButterKnife.bind(this);
        init();
        loadDataRequest(TMSService.LineNodeChoice_Request_Url);
    }

    private void init() {
        titleTv.setText(Utils.getResourcesString(R.string.choices_note));
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        if (getIntent().getSerializableExtra(Constants.TASKINFO) != null) {
            taskBean = (TaskBean) getIntent().getSerializableExtra(Constants.TASKINFO);
        }
        list = new ArrayList<>();
        adapter = new MyTaskChoicseAdapter(list, this);
        myListView = myTaskOffTheStocksPullLv.getRefreshableView();
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LineNoteBean bean = adapter.getItem(position - 1);
                if (bean != null) {
                    Intent intent = new Intent();
                    intent.putExtra("ORGNAME", bean.Org_Name);
                    intent.putExtra("Node_Key", bean.Node_Key);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.back_llayout)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
        }
    }

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = taskBean.Letter_Oid; //JsonUtils.toJsonData(getDriverListRequest);
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>" + response.toString());
                GetLineNoteListResponse response1 = JsonUtils.parseData(response.toString(), GetLineNoteListResponse.class);
                Message msg = Message.obtain();
                if (response1.Success) {
                    msg.obj = response1.DataValue;
                    msg.what = Constants.REQUEST_SUCC;
                    myHandler.sendMessage(msg);
                } else {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                    myHandler.sendMessage(msg);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("-->>" + error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                myHandler.sendMessage(msg);
            }
        });

    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (lodingDialog != null) {
                lodingDialog.dismiss();
            }
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    List<LineNoteBean> dataList = (List<LineNoteBean>) msg.obj;
                    if (dataList != null) {
                        list.clear();
                        for (int i = 0; i < dataList.size(); i++) {
                            if (!TextUtils.isEmpty(dataList.get(i).Arrive) && dataList.get(i).Arrive.equals("0")) {
                                list.add(dataList.get(i));
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                    myTaskOffTheStocksPullLv.onRefreshComplete();
                    if (list.size() > 0) {
                        emptyTv.setVisibility(View.GONE);
                    } else {
                        emptyTv.setVisibility(View.VISIBLE);
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(MyTaskChoicesNoteActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;


            }
        }
    };
}