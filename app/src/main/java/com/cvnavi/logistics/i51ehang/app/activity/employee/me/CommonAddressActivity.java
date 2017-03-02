package com.cvnavi.logistics.i51ehang.app.activity.employee.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter.CommonAddressAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.employee.me.GetDestinationResponse;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;
import java.util.ArrayList;
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
*创建时间：2017/1/16 下午4:08
*描述：常用目的地界面
************************************************************************************/

public class CommonAddressActivity extends BaseActivity implements CommonAddressAdapter.SelectDesc {
    public static final String INTENT_TYPE = "INTENT_TYPE";
    public static int TYPE_FROM_ME = 1;
    public static int TYPE_FROM_PLAN = 2;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.listview)
    PullToRefreshListView listview;
    @BindView(R.id.no_data_tv)
    TextView mNoDataTv;
    @BindView(R.id.noData_view)
    FrameLayout mNoDataView;
    private SweetAlertDialog dialog;
    private CommonAddressAdapter addressAdapter;//地址适配器
    private int type = TYPE_FROM_PLAN;
    private int pageInt = 1;//分页num
    private boolean isRefresh = false;//是否刷新过了
    private DataRequestBase dataRequestBase;//请求类
    private List<GetDestinationResponse.DataValueBean> list;


    /**
     * 启动方法
     *
     * @param activity
     * @param type
     */
    public static void startActivity(Activity activity, int type) {
        Intent intent = new Intent(activity, CommonAddressActivity.class);
        intent.putExtra(INTENT_TYPE, type);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_address);
        ButterKnife.bind(this);
        titltTv.setText("常用目的地");
        list = new ArrayList<>();
        //获取从哪个界面进来的类型
        type = getIntent().getIntExtra(INTENT_TYPE, TYPE_FROM_ME);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(CommonAddressActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isRefresh = true;
                pageInt = 1;
                //执行刷新
                getDestinatInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                //执行刷新
                getDestinatInfo();
            }
        });
        getDestinatInfo();
    }

    /**
     * 请求数据
     */
    private void getDestinatInfo() {
        if (dialog == null) {
            dialog = new SweetAlertDialog(CommonAddressActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        }

        dialog.show();
        if (dataRequestBase == null) {
            dataRequestBase = new DataRequestBase();
        }
        dataRequestBase.Page = pageInt;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        /**
         * 传的请求参数
         * {"User_Key":"660927d2-3ae0-4fa8-9813-ae21fc448f5d",
         * "Org_Code":"101","UserType_Oid":"G","page":"1","limit":"2",
         * "Company_Oid":"210106",
         * "Token":"46414075-23e1-421c-9f89-c97dbff64ff2",
         * "ActionSystem":"APP"}
         */

        MLog.json(GsonUtil.newInstance().toJsonStr(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(EmployeeService.GetDestination_TAG, EmployeeService.GetDestination_URL, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MLog.json(response.toString());
                dialog.dismiss();
                listview.onRefreshComplete();
                GetDestinationResponse result = GsonUtil.newInstance().fromJson(response, GetDestinationResponse.class);
                if (result != null && result.isSuccess() && result.getDataValue() != null) {
                    result.getDataValue();
                    if (isRefresh) {
                        isRefresh = false;
                        list.clear();
                    }


                    list.addAll(result.getDataValue());
                    if(list.size()>0){
                        mNoDataView.setVisibility(View.GONE);
                    }else{
                        mNoDataView.setVisibility(View.VISIBLE);
                    }
                    if (addressAdapter == null) {
                        addressAdapter = new CommonAddressAdapter(CommonAddressActivity.this, list, CommonAddressActivity.this);
                        listview.setAdapter(addressAdapter);
                    } else {
                        addressAdapter.notifyDataSetChanged();
                    }
                } else {
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(CommonAddressActivity.this, "获取数据失败", new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            finish();
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(CommonAddressActivity.this, "网络异常", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        finish();
                    }
                });
            }
        });
    }

    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }

    @Override
    public void select(GetDestinationResponse.DataValueBean info) {
        if (type == TYPE_FROM_ME) {
            //跳转到点定位界面
            CommonAddressGpsActivity.startActivity(CommonAddressActivity.this, info);
        } else if (type == TYPE_FROM_PLAN) {
            EventBus.getDefault().post(info);
            finish();
        }
    }
}
