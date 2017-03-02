package com.cvnavi.logistics.i51ehang.app.activity.driver.home.scanning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.myorder.MyOrderDetailAcitivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.DriverOrderDeatilActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder.DriverHomeOrderDeatilActivity;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 下午2:04
*描述：扫一扫界面
************************************************************************************/

public class ScanningActivity extends Activity {
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private static final int REQUEST_CODE_SEARCH = 0x11;
    /**
     * 显示扫描结果
     */
    private TextView mTextView;
    /**
     * 显示扫描拍的图片
     */
    private ImageView mImageView;

    private GetAppLoginResponse loginInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);

        mTextView = (TextView) findViewById(R.id.result);
        mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
        loginInfo = MyApplication.getInstance().getLoginInfo();

        //点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
        //扫描完了之后调到该界面
        Button mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ScanningActivity.this, MipcaActivityCapture.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    //显示扫描到的内容
                    mTextView.setText(bundle.getString("result"));
                    //显示
                    if (loginInfo.DataValue.UserType_Oid.equals("E")) {
                        //货主跳到货单明细
                        Intent intent = new Intent(this, MyOrderDetailAcitivity.class);
                        intent.putExtra(MyOrderDetailAcitivity.ORDER_ID, bundle.getString("result"));
                        startActivity(intent);
                    } else if (loginInfo.DataValue.UserType_Oid.equals("F")) {
                        //员工跳到货单详情
                        DriverHomeOrderDeatilActivity.startActivity(this, REQUEST_CODE_SEARCH, bundle.getString("result"));
                    } else {
                        DriverOrderDeatilActivity.startActivity(this, REQUEST_CODE_SEARCH, bundle.getString("result"), null);
                    }

                }
                break;
        }
    }

}
