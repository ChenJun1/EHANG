package com.cvnavi.logistics.i51ehang.app.widget.popupmenu;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;

/**
 * Created by fan on 2016/8/22.
 */
public class DriverOrderListPopu extends PopupWindow implements View.OnClickListener{
    private Activity activity;
    private View popView;

    private View v_item1;
    private View v_item2;
    private View v_item3;
    private View v_item4;
    private View v_item5;
    private View v_item6;



    private OnItemClickListener onItemClickListener;

    /**
     *
     * @author ywl5320 枚举，用于区分选择了哪个选项
     */
    public enum MENUITEM {
        ITEM1, ITEM2, ITEM3,ITEM4,ITEM5,ITEM6
    }

    public DriverOrderListPopu(Activity activity) {
        super(activity);
        this.activity = activity;
        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popView = inflater.inflate(R.layout.popup_order_menu, null);// 加载菜单布局文件
        this.setContentView(popView);// 把布局文件添加到popupwindow中
        this.setWidth(dip2px(activity, 120));// 设置菜单的宽度（需要和菜单于右边距的距离搭配，可以自己调到合适的位置）
        this.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);// 获取焦点
        this.setTouchable(true); // 设置PopupWindow可触摸
        this.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        ColorDrawable dw = new ColorDrawable(0x00000000);
        this.setBackgroundDrawable(dw);

        // 获取选项卡
        v_item1 = popView.findViewById(R.id.ly_item1);
        v_item2 = popView.findViewById(R.id.ly_item2);
        v_item3 = popView.findViewById(R.id.ly_item3);
        v_item4 = popView.findViewById(R.id.ly_item4);
        v_item5 = popView.findViewById(R.id.ly_item5);
        v_item6 = popView.findViewById(R.id.ly_item6);

        TextView tx_item1= (TextView) popView.findViewById(R.id.tx_item1);
        TextView tx_item2= (TextView) popView.findViewById(R.id.tx_item2);
        TextView tx_item3= (TextView) popView.findViewById(R.id.tx_item3);
        TextView tx_item4= (TextView) popView.findViewById(R.id.tx_item4);
        TextView tx_item5= (TextView) popView.findViewById(R.id.tx_item5);
        TextView tx_item6= (TextView) popView.findViewById(R.id.tx_item6);

        tx_item1.setText("全部");
        tx_item2.setText("已签收");
        tx_item3.setText("未签收");
        tx_item4.setText("部分签收");
        tx_item5.setText("已作废");
        tx_item6.setText("未作废");
        tx_item6.setVisibility(View.GONE);
        v_item6.setVisibility(View.GONE);


        // 添加监听
        v_item1.setOnClickListener(this);
        v_item2.setOnClickListener(this);
        v_item3.setOnClickListener(this);
        v_item4.setOnClickListener(this);
        v_item5.setOnClickListener(this);
        v_item6.setOnClickListener(this);

    }


    /**
     * 设置显示的位置
     *
     * @param resourId
     *            这里的x,y值自己调整可以
     */
    public void showLocation(int resourId) {
        showAsDropDown(activity.findViewById(resourId), dip2px(activity, 0),
                dip2px(activity, -8));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        MENUITEM menuitem = null;
        String str = "";
        if (v == v_item1) {
            menuitem = MENUITEM.ITEM1;
            str = "";
        } else if (v == v_item2) {
            menuitem = MENUITEM.ITEM2;
            str = "3";
        } else if (v == v_item3) {
            menuitem = MENUITEM.ITEM3;
            str = "1";
        }else if (v == v_item4) {
            menuitem = MENUITEM.ITEM4;
            str = "2";
        }else if (v == v_item5) {
            menuitem = MENUITEM.ITEM5;
            str = "作废";
        }else if (v == v_item6) {
            menuitem = MENUITEM.ITEM6;
            str = "正常";
        }
        if (onItemClickListener != null) {
            onItemClickListener.onClick(menuitem, str);
        }
        dismiss();
    }

    // dip转换为px
    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    // 点击监听接口
    public interface OnItemClickListener {
        public void onClick(MENUITEM item, String str);
    }

    // 设置监听
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
