package com.cvnavi.logistics.i51ehang.app.widget.dialog.custom;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/25.
 */
public class ActionSheetItemInfo {
    private String itemName;
    private ActionSheetDialog.OnSheetItemClickListener listener;

    public ActionSheetItemInfo(String itemName, ActionSheetDialog.OnSheetItemClickListener listener) {
        this.itemName = itemName;
        this.listener = listener;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ActionSheetDialog.OnSheetItemClickListener getListener() {
        return listener;
    }

    public void setListener(ActionSheetDialog.OnSheetItemClickListener listener) {
        this.listener = listener;
    }
}
