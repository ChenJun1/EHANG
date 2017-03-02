package com.cvnavi.logistics.i51ehang.app.callback.cargo;

import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.DestinationBean;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.GetTakeManifestsDataValue;
import com.cvnavi.logistics.i51ehang.app.callback.cargo.biz.IPickupBiz;

/**
 * Created by fan on 2016/11/3.
 */

public class PIckupCallback {
    private static IPickupBiz mbiz;

    public static PIckupCallback getCallback() {
        return new PIckupCallback();
    }

    public static void setCallback(IPickupBiz biz) {
        mbiz = biz;
    }

    //（未完   Object 改为实体类）
    public void addAddressBiz(GetTakeManifestsDataValue bean) {
        mbiz.setPickup(bean);
    }
}
