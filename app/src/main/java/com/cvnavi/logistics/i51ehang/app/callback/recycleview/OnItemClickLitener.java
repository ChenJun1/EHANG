package com.cvnavi.logistics.i51ehang.app.callback.recycleview;

import android.view.View;

/**
 * Created by george on 16/9/21.
 */

public interface OnItemClickLitener {
    void onItemClick(View view, int position);

    void onItemLongClick(View view, int position);
}
