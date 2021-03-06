package com.cvnavi.logistics.i51ehang.app.activity.employee.home.storehouse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.employee.home.statics.StoreSelectOrgModel;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

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
*创建时间：2017/1/16 下午4:06
*描述：获取公司号
************************************************************************************/

public class StoreSelectOrgActivity extends BaseActivity implements MyOnClickItemListener {
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.check_tv)
    TextView checkTv;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.custom_ll)
    LinearLayout customLl;
    @BindView(R.id.list_view)
    ListView listView;
    private StoreOrgAdapter adapter;
    private Context context;

    private List<StoreSelectOrgModel.DataValueBean> list;

    public static void startActivity(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, StoreSelectOrgActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_select_org);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        context = this;
        titltTv.setText("选择机构");
        getCarList();
    }

    /**
     * 获取机构列表
     */
    private void getCarList() {
        showLoading();
        DataRequestBase requestBase = new DataRequestBase();
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        requestBase.Org_Key = MyApplication.getInstance().getLoginInfo().DataValue.Org_Key;
        requestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        VolleyManager.newInstance().PostJsonRequest(EmployeeService.GetOrgCodebyCompanOid_Url, EmployeeService.GetOrgCodebyCompanOid_Url, GsonUtil.newInstance().toJson(requestBase), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                MLog.json(jsonObject.toString());
                Message msg = Message.obtain();
                List<StoreSelectOrgModel.DataValueBean> carInfos = null;
                StoreSelectOrgModel carListResponse = GsonUtil.newInstance().fromJson(jsonObject, StoreSelectOrgModel.class);
                if (carListResponse.isSuccess()) {
                    carInfos = carListResponse.getDataValue();
                    msg.what = Constants.REQUEST_SUCC;
                    msg.obj = carInfos;
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                }
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                mHandler.sendMessage(msg);
            }
        });
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dissLoading();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        List<StoreSelectOrgModel.DataValueBean> carInfos = (List<StoreSelectOrgModel.DataValueBean>) msg.obj;
                        list.addAll(carInfos);
                        adapter = new StoreOrgAdapter();
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (list != null && list.size() > 0) {
                                    StoreSelectOrgModel.DataValueBean info = list.get(position);
                                    EventBus.getDefault().post(info);
                                    finish();
                                }
                            }
                        });
                    }

                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(StoreSelectOrgActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void myOnClickItem(int position, Object data) {
        EventBus.getDefault().post((mCarInfo) data);
        finish();
    }

    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }


    class StoreOrgAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (list == null) {
                return 0;
            }
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemView viewHolder;
            if (convertView == null) {
                viewHolder = new ItemView();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_driver_car_list_parent_group, null);
                viewHolder.carCode = (TextView) convertView.findViewById(R.id.car_code);
                viewHolder.parentGroupTV = (TextView) convertView.findViewById(R.id.parentGroupTV);
                viewHolder.rootLl = (RelativeLayout) convertView.findViewById(R.id.root_ll);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ItemView) convertView.getTag();
            }
            final StoreSelectOrgModel.DataValueBean info = list.get(position);
            if (info != null) {
                viewHolder.parentGroupTV.setText(info.getOrg_Name());
            }

            return convertView;
        }

        class ItemView {
            TextView parentGroupTV;
            RelativeLayout rootLl;
            TextView carCode;
        }
    }


}
