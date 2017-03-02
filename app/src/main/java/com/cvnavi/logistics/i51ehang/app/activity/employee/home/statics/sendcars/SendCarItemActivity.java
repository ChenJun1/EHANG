package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.sendcars;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.weituo.WeiTuoDetailAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarDetaiItemModel;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

import org.json.JSONObject;

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
 * 创建时间：2017/1/16 下午3:53
 * 描述：派车详情
 ************************************************************************************/

public class SendCarItemActivity extends BaseActivity {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.invis)
    LinearLayout invis;
    @BindView(R.id.activity_weituo_detail)
    RelativeLayout activityWeituoDetail;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    private WeiTuoDetailAdapter adapter;


    private View header;
    DataRequestBase dataRequestBase;
    private String ticketNo;


    public static void start(Context context, String ticketNo) {
        Intent starter = new Intent(context, SendCarItemActivity.class);
        starter.putExtra("tickNo", ticketNo);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weituo_detail);
        ButterKnife.bind(this);
        titltTv.setText("派车详情");
        ticketNo = getIntent().getStringExtra("tickNo");

        header = View.inflate(this, R.layout.head_view_weituo_detail, null);//头部内容
        lv.addHeaderView(header);//添加头部
        lv.addHeaderView(View.inflate(this, R.layout.stick_action_weituo, null));//ListView条目中的悬浮部分 添加到头部
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    invis.setVisibility(View.VISIBLE);
                } else {
                    invis.setVisibility(View.GONE);
                }
            }
        });
        getDestinatInfo();
    }

    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }

    /**
     * 请求数据
     */
    private void getDestinatInfo() {
        showLoading();
        if (dataRequestBase == null) {
            dataRequestBase = new DataRequestBase();
        }

        dataRequestBase.DataValue = ticketNo;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;


        MLog.json(GsonUtil.newInstance().toJsonStr(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(EmployeeService.GetDataCarDetailByOid_Url, EmployeeService.GetDataCarDetailByOid_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MLog.json(response.toString());
                dissLoading();
                SendCarDetaiItemModel result = GsonUtil.newInstance().fromJson(response, SendCarDetaiItemModel.class);
                if (result != null && result.isSuccess() && result.getDataValue() != null) {
                    List<SendCarDetaiItemModel.DataValueBean.StowageListBean> listBeen = result.getDataValue().getStowageList();
                    List<SendCarDetaiItemModel.DataValueBean.MainTicketBean> mainTicketBeen = result.getDataValue().getMainTicket();

                    /**
                     * 设置head
                     */
                    if (listBeen != null && listBeen.size() > 0) {
                        TextView company = (TextView) header.findViewById(R.id.company);
                        TextView connet = (TextView) header.findViewById(R.id.connet);
                        TextView tel = (TextView) header.findViewById(R.id.tel);
                        TextView piao = (TextView) header.findViewById(R.id.piao);
                        TextView pin = (TextView) header.findViewById(R.id.pin);
                        TextView time = (TextView) header.findViewById(R.id.time);

                        final SendCarDetaiItemModel.DataValueBean.StowageListBean info = listBeen.get(0);

                        //设置车牌号
                        if (!TextUtils.isEmpty(info.getCarCode())) {
                            company.setText(info.getCarCode());
                        } else {
                            company.setText("车牌号：" + "--");
                        }

                        //设置联系人
                        if (!TextUtils.isEmpty(info.getDriver())) {
                            connet.setText("联系人:" + info.getDriver());
                        } else {
                            connet.setText("联系人：" + "--");
                        }

                        //设置点击事件
                        if (!TextUtils.isEmpty(info.getDriver_Tel())) {
                            tel.setText(info.getDriver_Tel());
                            tel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ContextUtil.callAlertDialog(info.getDriver_Tel(), SendCarItemActivity.this);
                                }
                            });
                        } else {
                            tel.setText("--");
                        }

                        //设置票数
                        if (!TextUtils.isEmpty(info.getAll_Count())) {
                            piao.setText("票数：" + info.getAll_Count() + "票");
                        } else {
                            piao.setText("票数：" + "--");
                        }

                        //设置物品件数
                        if (!TextUtils.isEmpty(info.getAllBulk_Weight()) && !TextUtils.isEmpty(info.getAllGoods_Num()) && !TextUtils.isEmpty(info.getAllGoods_Weight()) && !TextUtils.isEmpty(info.getAllBulk_Weight())) {
                            pin.setText("物品：" + info.getAllGoods_Num() + "件/" + info.getAllGoods_Weight() + "kg/" + info.getAllBulk_Weight() + "m³");
                        } else {
                            pin.setText("物品：" + "--");
                        }

                        //设置派车单时间
                        if (!TextUtils.isEmpty(info.getOperate_DateTime())) {
                            time.setText("派车时间：" + info.getOperate_DateTime());
                        } else {
                            time.setText("派车时间：" + "--");
                        }
                    }


                    /**
                     *
                     * 设置货单列表
                     */
                    if (adapter == null) {
                        adapter = new WeiTuoDetailAdapter(SendCarItemActivity.this, mainTicketBeen, WeiTuoDetailAdapter.FROM_SEND_CAR);
                        lv.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }


                } else {
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SendCarItemActivity.this, "获取数据失败", new CustomDialogListener() {
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
                dissLoading();
                DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SendCarItemActivity.this, "网络异常", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        finish();
                    }
                });
            }
        });
    }


}
