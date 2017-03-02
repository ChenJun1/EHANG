package com.cvnavi.logistics.i51ehang.app.callback.driver.home.location;

import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;

/**
 * Created by Administrator on 2016/7/6.
 */
public interface LocationChooseCarCallback {
     void OnMonitorLoadCarCode(mCarInfo mCarInfo);
     void OnHistorLoadCarCode(mCarInfo mCarInfo);
}
