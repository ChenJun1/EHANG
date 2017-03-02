package com.cvnavi.logistics.i51ehang.app.widget.dialog.popupwindow;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

import java.util.ArrayList;

/**
 * Created by george on 2016/10/5.
 */

public class MyPopWindow extends PopupWindow {

    private Activity activity;
    private View popView;

    private ArrayList<String> itemName;

    private LinearLayout root;

    private MyPopWindow.OnItemClickListener onItemClickListener;


    public MyPopWindow(Activity activity, ArrayList<String> itemName) {
        super(activity);
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.pop_mile_statics_layout, null);// 加载菜单布局文件
        this.setContentView(popView);// 把布局文件添加到popupwindow中
        this.setWidth(Utils.dip2px(activity, 120));// 设置菜单的宽度（需要和菜单于右边距的距离搭配，可以自己调到合适的位置）
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);// 获取焦点
        this.setTouchable(true); // 设置PopupWindow可触摸
        this.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);

        this.itemName = itemName;
        root = (LinearLayout) popView.findViewById(R.id.root_ll);


        for (int i = 0; i < itemName.size(); i++) {
            LinearLayout itemView = new LinearLayout(activity);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(activity, 45)));
            itemView.setGravity(Gravity.CENTER_VERTICAL);
            itemView.setOrientation(LinearLayout.HORIZONTAL);

            TextView itemTV = new TextView(activity);
            itemTV.setText(itemName.get(i));
            itemTV.setTextColor(Utils.getResourcesColor(R.color.color_221f29));
            itemTV.setPadding(Utils.dip2px(activity, 20), 0, 0, 0);
            itemTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            itemView.addView(itemTV);
            final int finalI = i;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(finalI);
                        dismiss();
                    }
                }
            });
            root.addView(itemView);
            View view = new View(activity);
            view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(activity, 1)));
            view.setBackgroundColor(Utils.getResourcesColor(R.color.color_list_view_line));
            root.addView(view);
        }
//        showAsDropDown(activity.findViewById(resourId), Utils.dip2px(activity, 0), Utils.dip2px(activity, -8));

    }


    /**
     * 设置显示的位置
     *
     * @param resourId 这里的x,y值自己调整可以
     */
    public void showLocation(int resourId) {
        showAsDropDown(activity.findViewById(resourId), Utils.dip2px(activity, 0), Utils.dip2px(activity, -8));
    }


    // 点击监听接口
    public interface OnItemClickListener {
        public void onClick(int pos);
    }

    // 设置监听
    public void setOnItemClickListener(MyPopWindow.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}