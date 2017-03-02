package com.cvnavi.logistics.i51ehang.app.callback.cargo;

import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.DestinationBean;
import com.cvnavi.logistics.i51ehang.app.callback.cargo.biz.IDestinationBiz;

/**
 * Created by fan on 2016/10/27.
 */

public class DestinationCallback {
    private static IDestinationBiz mbiz;

    public static DestinationCallback getCallback() {
        return new DestinationCallback();
    }

    public static void setCallback(IDestinationBiz biz) {
        mbiz = biz;
    }

    //（未完   Object 改为实体类）
    public void addAddressBiz(DestinationBean bean) {
        mbiz.setDestination(bean);
    }

}
