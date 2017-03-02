package com.cvnavi.logistics.i51ehang.app.cache;

import android.content.Context;
import android.text.TextUtils;

import com.cvnavi.logistics.i51ehang.app.bean.model.Cache.DriverLicense;
import com.cvnavi.logistics.i51ehang.app.bean.model.Cache.DriverLicenseParse;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class BasicDataBuffer {
    private static BasicDataBuffer ms_BasicDataBuffer = new BasicDataBuffer();

    private List<DriverLicense> driverLicensesList;

    public List<DriverLicense> getDriverLicensesList() {
        return driverLicensesList;
    }

    public String getDriverLicenseValue(String driverLicenseCode) {
        String value = "";
        for (DriverLicense driverLicense : driverLicensesList) {
            if (driverLicense.Driver_License_Type_Oid.equals(driverLicenseCode)) {
                value = driverLicense.Driver_License_Type;
                break;
            }
        }
        return value;
    }

    public String getDriverLicenseCode(String driverLicenseValue) {
        String value = "";
        for (DriverLicense driverLicense : driverLicensesList) {
            if (driverLicense.Driver_License_Type.equals(driverLicenseValue)) {
                value = driverLicense.Driver_License_Type_Oid;
                break;
            }
        }
        return value;
    }

    public ArrayList<String> getDrivingLicenseValueList() {
        ArrayList<String> list = new ArrayList<>();
        for (DriverLicense driverLicense : driverLicensesList) {
            list.add(driverLicense.Driver_License_Type);
        }

        return list;
    }


    private BasicDataBuffer() {
        driverLicensesList = new ArrayList<>();
    }

    public static synchronized BasicDataBuffer newInstance() {
        return ms_BasicDataBuffer;
    }

    public void init(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LogUtil.d("-->>driverLicenses begin");

                    InputStream inStream = context.getAssets().open(
                            "basicdata.txt");
                    int size = inStream.available();
                    byte[] buffer = new byte[size];
                    inStream.read(buffer);
                    inStream.close();

                    String jsonStr = new String(buffer);
                    if (TextUtils.isEmpty(jsonStr)) {
                        return;
                    }

                    DriverLicenseParse driverLicenseParse = JsonUtils.parseData(jsonStr, DriverLicenseParse.class);
                    driverLicensesList.addAll(driverLicenseParse.DriverLicense);
                    LogUtil.d("-->>driverLicenses end");
                } catch (IOException e) {
                    LogUtil.e("-->>Driver licenses data parse fail.");
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
