package com.cvnavi.logistics.i51ehang.app.bean.eventbus;

import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;

/**
 * Created by george on 2016/10/10.
 */

public class ItemEvent {
    private int offset;

    public ItemEvent(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
