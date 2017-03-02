package com.cvnavi.logistics.i51ehang.app.jpush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.JPushModel;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.response.GetTakeManifestsResponse;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by fan on 2016/10/26.
 */

public class TestActivity extends Activity {

    private TextView ttitle,ccontent,textt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        initView();
    }

    private void initView() {
        ttitle = (TextView) findViewById(R.id.title);
        ccontent = (TextView) findViewById(R.id.content);
        textt = (TextView) findViewById(R.id.text);

        Intent intent = getIntent();
        if (null!=intent){
            Bundle bundle = getIntent().getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            String text = bundle.getString(JPushInterface.EXTRA_EXTRA);
            JPushModel bean = GsonUtil.newInstance().fromJson(bundle.getString(JPushInterface.EXTRA_EXTRA),JPushModel.class);
            ttitle.setText(title);
            ccontent.setText(content);
            textt.setText("All_Ticket_No:"+bean.All_Ticket_No+"Letter_Oid"+bean.Letter_Oid+"Serial_Oid"+bean.Serial_Oid);

        }
    }
}
