package com.cvnavi.logistics.i51ehang.app.activity.employee.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetDriverListRequest;
import com.cvnavi.logistics.i51ehang.app.callback.manager.HttpDelWithListener;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import okhttp3.Call;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/2/5 上午9:54
 * 描述：测试类随时可以调用
 ************************************************************************************/

public class MyTestActivity extends BaseActivity implements HttpDelWithListener {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        btn = (Button) findViewById(R.id.text_getHttp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //参数设置
                DataRequestBase dataRequestBase = new DataRequestBase();
                GetDriverListRequest getDriverListRequest = new GetDriverListRequest();
                dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
                dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
                dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
                dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
                dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
                dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
                dataRequestBase.DataValue = getDriverListRequest;
                //请求网络的基本请求 PostJson 字符串String
                httpPostString(TMSService.GetDriverList_Request_Url, dataRequestBase, MyTestActivity.this);
            }
        });
    }

    @Override
    public void httpError(Call call, Exception e, int id) {
        MLog.print("-->> error Message = " + e.getMessage());
    }

    @Override
    public void httpSucc(String result, int id) {
        MLog.json(result);
        btn.setText(result);
    }

}
