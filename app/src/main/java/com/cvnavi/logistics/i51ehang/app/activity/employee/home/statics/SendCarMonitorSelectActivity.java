package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
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
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.TimeEvent;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarSelectCarResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarSelectLineResponse;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.callback.recycleview.OnItemClickLitener;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;
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
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午4:01
 * 描述：用于发车监控是筛选 包换线路的筛选和 车辆的筛选
 ************************************************************************************/

public class SendCarMonitorSelectActivity extends BaseActivity implements OnItemClickLitener, MyOnClickItemListener {


    @BindView(R.id.back_ll)
    LinearLayout backLl;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.top_bar_line)
    ImageView topBarLine;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.invis)
    LinearLayout invis;
    @BindView(R.id.activity_weituo_detail)
    RelativeLayout activityWeituoDetail;
    @BindView(R.id.select_top_line)
    ImageView selectTopLine;
    @BindView(R.id.clear)
    TextView clear;
    @BindView(R.id.sure)
    TextView sure;
    @BindView(R.id.top_line_name)
    TextView topLineName;

    private View header;
    private View lineView;

    private RecyclerView recyclerView;
    private boolean isTouchRv = false;
    private boolean isTouchListView = false;

    private SendCarMonitorSelectRvAdapter rvAdapter;
    private SendCarMonitorSelectLineAdapter adapter;

    private View lastCarCodeView;
    private LinearLayout rootHead;
    private TextView newText;
    private TextView oldText;

    private String carCode = "";
    private String lineOid = "";

    private List<SendCarSelectCarResponse.DataValueBean> carList;
    private List<SendCarSelectLineResponse.DataValueBean> lineList;

    private static final int PDDING_TOP = 55;//PX

    public static void start(Context context) {
        Intent starter = new Intent(context, SendCarMonitorSelectActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_car_monitor_select);
        ButterKnife.bind(this);
        title.setText("筛选");

        carList = new ArrayList<>();
        lineList = new ArrayList<>();
        //防止topbar和筛选框之间有间距
        header = View.inflate(this, R.layout.head_send_car_select, null);//头部内容
        lineView = header.findViewById(R.id.head_line_view);
        rootHead = (LinearLayout) header.findViewById(R.id.rootHead);
        recyclerView = (RecyclerView) header.findViewById(R.id.rv_carCode);


        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        rvAdapter = new SendCarMonitorSelectRvAdapter(this, carList);
        rvAdapter.setOnItemClickLitener(this);
        recyclerView.setAdapter(rvAdapter);


        lv.addHeaderView(header);//添加头部
        lv.addHeaderView(View.inflate(this, R.layout.stick_send_car_select, null));//ListView条目中的悬浮部分 添加到头部
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem >= 1) {
                    topBarLine.setVisibility(View.GONE);
                    selectTopLine.setVisibility(View.VISIBLE);
                    invis.setVisibility(View.VISIBLE);
                } else {
                    invis.setVisibility(View.GONE);
                    topBarLine.setVisibility(View.VISIBLE);
                    selectTopLine.setVisibility(View.GONE);
                }
            }
        });

        adapter = new SendCarMonitorSelectLineAdapter(this, lineList, this);
        lv.setAdapter(adapter);
        getCarList();
        getLineList();

    }

    @Override
    public void onItemClick(View view, int position) {
        if (carList == null || (carList != null && carList.size() < 1)) {
            return;
        }

        for (int i = 0; i < carList.size(); i++) {
            SendCarSelectCarResponse.DataValueBean info = carList.get(i);
            if (i == position) {
                carCode = info.getCarCode_Key();
                info.setSelect(true);
            } else {
                info.setSelect(false);
            }
        }

        rvAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }


    /**
     * 车牌号
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
        VolleyManager.newInstance().PostJsonRequest(EmployeeService.GetOnWayCar_Url, EmployeeService.GetOnWayCar_Url, GsonUtil.newInstance().toJson(requestBase), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject jsonObject) {
                dissLoading();
                MLog.json(jsonObject.toString());
                SendCarSelectCarResponse info = GsonUtil.newInstance().fromJson(jsonObject, SendCarSelectCarResponse.class);

                if (info != null && info.isSuccess() && info.getDataValue() != null && info.getDataValue().size() > 0) {
                    carList.addAll(info.getDataValue());
                }

                SendCarSelectCarResponse.DataValueBean fristInfo = new SendCarSelectCarResponse.DataValueBean(true, "", "全部");
                //插入第一条数据
                carList.add(0, fristInfo);

                int line = carList.size() / 3;
                if (line == 0) {
                    line = 1;
                }

                if (carList.size() % 3 > 0) {
                    line = line + 1;
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(SendCarMonitorSelectActivity.this, PDDING_TOP * line));
                recyclerView.setLayoutParams(params);
                rvAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dissLoading();
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
            }
        });
    }


    /**
     * 车牌号
     */
    private void getLineList() {
        showLoading();
        DataRequestBase requestBase = new DataRequestBase();
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        requestBase.Org_Key = MyApplication.getInstance().getLoginInfo().DataValue.Org_Key;
        requestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        VolleyManager.newInstance().PostJsonRequest(EmployeeService.GetLinesName_URL, EmployeeService.GetLinesName_URL, GsonUtil.newInstance().toJson(requestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                dissLoading();
                MLog.json(jsonObject.toString());
                SendCarSelectLineResponse info = GsonUtil.newInstance().fromJson(jsonObject, SendCarSelectLineResponse.class);
                if (info != null && info.isSuccess() && info.getDataValue() != null && info.getDataValue().size() > 0) {
                    lineList.addAll(info.getDataValue());
                    SendCarSelectLineResponse.DataValueBean first = new SendCarSelectLineResponse.DataValueBean(true, "全部", "");
                    //插入第一条数据
                    lineList.add(0, first);
                    adapter.notifyDataSetChanged();
                } else {
                    SendCarSelectLineResponse.DataValueBean first = new SendCarSelectLineResponse.DataValueBean(true, "全部", "");
                    //插入第一条数据
                    lineList.add(0, first);
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dissLoading();
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
            }
        });
    }


    @Override
    public void myOnClickItem(int position, Object data) {
        if (lineList == null || (lineList != null && lineList.size() < 1)) {
            return;
        }

        for (int i = 0; i < lineList.size(); i++) {
            SendCarSelectLineResponse.DataValueBean info = lineList.get(i);
            if (i == position) {
                lineOid = info.getLine_Oid();
                info.setSelect(true);
            } else {
                info.setSelect(false);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.back_ll, R.id.clear, R.id.sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.clear:
                finish();
                break;
            case R.id.sure:
                EventBus.getDefault().post(new TimeEvent(carCode, lineOid));
                finish();
                break;
        }
    }
}
