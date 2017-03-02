package com.cvnavi.logistics.i51ehang.app.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.jpush.android.api.InstrumentedActivity;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by fan on 2016/11/1.
 */

public class TestJPushActivity extends InstrumentedActivity implements View.OnClickListener{
    public static boolean isForeground = false;
    private EditText msgText ;
    private TextView RegistrationID,APPKey,PackageName,IMEI,Version,DeviceID;
    private Button initJPush,StopJPush,ResumeJPush,Get_Registration_id,gaoji,mSetTag,mSetAlias;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_jpush);

        initView();

        registerMessageReceiver();

//        setTag();
        setAlias();
    }

    private void initView() {
        msgText = (EditText) findViewById(R.id.msg_rec);
        RegistrationID = (TextView) findViewById(R.id.RegistrationID);
        APPKey = (TextView) findViewById(R.id.APPKey);
        PackageName = (TextView) findViewById(R.id.PackageName);
        IMEI = (TextView) findViewById(R.id.IMEI);
        Version = (TextView) findViewById(R.id.Version);
        DeviceID = (TextView) findViewById(R.id.DeviceID);

        initJPush = (Button) findViewById(R.id.initJPush);
        StopJPush = (Button) findViewById(R.id.StopJPush);
        ResumeJPush = (Button) findViewById(R.id.ResumeJPush);
        Get_Registration_id = (Button) findViewById(R.id.Get_Registration_id);
        gaoji = (Button) findViewById(R.id.gaoji);
        mSetTag = (Button)findViewById(R.id.bt_tag);
        mSetAlias = (Button)findViewById(R.id.bt_alias);

        initJPush.setOnClickListener(this);
        StopJPush.setOnClickListener(this);
        ResumeJPush.setOnClickListener(this);
        Get_Registration_id.setOnClickListener(this);
        gaoji.setOnClickListener(this);
        mSetTag.setOnClickListener(this);
        mSetAlias.setOnClickListener(this);


        String udid =  ExampleUtil.getImei(getApplicationContext(), "");
        if (null != udid) IMEI.setText(udid);

        String appKey = ExampleUtil.getAppKey(getApplicationContext());
        if (null == appKey) appKey = "AppKey异常";
        APPKey.setText(appKey);

        PackageName.setText(getPackageName());

        String deviceId = ExampleUtil.getDeviceId(getApplicationContext());
        DeviceID.setText(deviceId);

        String versionName =  ExampleUtil.GetVersion(getApplicationContext());
        Version.setText(versionName);

        String rid = JPushInterface.getRegistrationID(getApplicationContext());
        RegistrationID.setText(rid);

    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.initJPush:
                JPushInterface.init(this);
                break;
            case R.id.StopJPush:
                JPushInterface.stopPush(this);
                break;
            case R.id.ResumeJPush:
                JPushInterface.resumePush(this);
                break;
            case R.id.Get_Registration_id:

                break;
            case R.id.gaoji:

                break;
            case R.id.bt_tag:
                setTag();
                break;
            case R.id.bt_alias:
                setAlias();
                break;
        }
    }



    private void setTag(){
        EditText tagEdit = (EditText) findViewById(R.id.et_tag);
//        String tag = tagEdit.getText().toString().trim();
        String tag = MyApplication.getInstance().getLoginInfo().DataValue.User_Tel;
        tagEdit.setText(tag);
        // 检查 tag 的有效性
        if (TextUtils.isEmpty(tag)) {
            Toast.makeText(TestJPushActivity.this,"tag不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<String>();
        for (String sTagItme : sArray) {
            if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
                Toast.makeText(TestJPushActivity.this,"格式不对", Toast.LENGTH_SHORT).show();
                return;
            }
            tagSet.add(sTagItme);
        }

        //调用JPush API设置Tag
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));

    }

    private void setAlias(){
        EditText aliasEdit = (EditText) findViewById(R.id.et_alias);
//        String alias = aliasEdit.getText().toString().trim();

        String alias = MyApplication.getInstance().getLoginInfo().DataValue.User_Tel;
        aliasEdit.setText(alias);

        if (TextUtils.isEmpty(alias)) {
            Toast.makeText(TestJPushActivity.this,"不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(alias)) {
            Toast.makeText(TestJPushActivity.this,"格式不对", Toast.LENGTH_SHORT).show();
            return;
        }

        //调用JPush API设置Alias
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }


    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("", logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i("", logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i("", "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("", logs);
            }

            ExampleUtil.showToast(logs, getApplicationContext());
        }

    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("", logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i("", logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                        Log.i("", "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("", logs);
            }

            ExampleUtil.showToast(logs, getApplicationContext());
        }

    };

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("", "Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    Log.d("", "Set tags in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
                    Log.i("", "Unhandled msg - " + msg.what);
            }
        }
    };




    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!ExampleUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
                setCostomMsg(showMsg.toString());
            }
        }
    }

    private void setCostomMsg(String msg){
        if (null != msgText) {
            msgText.setText(msg);
            msgText.setVisibility(android.view.View.VISIBLE);
        }
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
