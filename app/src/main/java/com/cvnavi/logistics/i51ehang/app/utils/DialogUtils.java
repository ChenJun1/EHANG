package com.cvnavi.logistics.i51ehang.app.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.LoadingDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ActionSheetDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ActionSheetItemInfo;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomNormalToast;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomToast;

import java.util.ArrayList;


/**
 * 对话框和toast的工具类
 */
public class DialogUtils {
    /**
     * 显示对话框(一个确定按钮，默认的title)
     *
     * @param @param activity
     * @param @param msg
     * @return void
     * @throws
     * @Title: showMessageDialog
     * @Description:
     */
    public static void showMessageDialogOfDefaultSingleBtn(Activity activity,
                                                           String msg) {
        if (activity==null||msg == null || msg.trim().length() <= 0) {
            return;
        }

        CustomDialog dialog = new CustomDialog(activity, null);
        dialog.setButtonText(MyApplication.getInstance().getResources().getString(R.string.dia_ok_str),
                null);
        dialog.setCustomTitle(MyApplication.getInstance().getResources().getString(R.string.dia_warm_prompt));
        dialog.setCustomMessage(msg);
        dialog.show();
    }

    /**
     * 显示对话框(一个确定按钮，默认的title,只能确定关闭)
     *
     * @param @param activity
     * @param @param msg
     * @return void
     * @throws
     * @Title: showMessageDialog
     * @Description:
     */
    public static void showMessageDialogOfDefaultSingleBtnNoCancel(
            Activity activity, String msg, CustomDialogListener listener) {
        if (activity==null||msg == null || msg.trim().length() <= 0) {
            return;
        }

        CustomDialog dialog = new CustomDialog(activity, listener);
        dialog.setButtonText(MyApplication.getInstance().getResources().getString(R.string.dia_ok_str),
                null);
        dialog.setCustomTitle(MyApplication.getInstance().getResources().getString(R.string.dia_warm_prompt));
        dialog.setCustomMessage(msg);
        dialog.show();
    }

    /**
     * 显示对话框
     *
     * @param @param activity
     * @param @param msg
     * @return void
     * @throws
     * @Title: showMessageDialog
     * @Description:
     */
    public static void showMessageDialog(Context activity, String title,
                                         String msg, String posBtnText, String negBtnText,
                                         CustomDialogListener listener) {
        if (activity==null||msg == null || msg.trim().length() <= 0) {
            return;
        }

        CustomDialog dialog = new CustomDialog(activity, listener);
        dialog.setCustomTitle(title);
        dialog.setCustomMessage(msg);
        dialog.setButtonText(posBtnText, negBtnText);
        dialog.show();
    }

    private static CustomToast customToast = null;
    private static CustomNormalToast normalToast = null;

    /**
     * 正常的toast
     *
     * @param str
     */
    public static void showNormalToast(String str) {
        if (str == null || str.trim().length() <= 0) {
            return;
        }

        if (normalToast != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                normalToast.cancel();
            }
        } else {
            normalToast = CustomNormalToast.makeText(
                    MyApplication.getInstance(), str,
                    CustomToast.LENGTH_SHORT);
        }

        normalToast.setText(str);
        normalToast.show();
    }

    /**
     * 成功的toast
     *
     * @param str
     */
    public static void showSuccToast(String str) {
        displayToast(R.drawable.toast_succ, str);
    }

    /**
     * 警告的toast
     *
     * @param str
     */
    public static void showWarningToast(String str) {
        displayToast(R.drawable.toast_warning, str);
    }

    /**
     * 失败的toast
     *
     * @param str
     */
    public static void showFailToast(String str) {
        displayToast(R.drawable.toast_fail, str);
    }

    // toast
    private static void displayToast(int iconResId, String str) {
        if (str == null || str.trim().length() <= 0) {
            return;
        }

        if (customToast != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                customToast.cancel();
            }
        } else {
            customToast = CustomToast.makeText(MyApplication.getInstance(),
                    str, CustomToast.LENGTH_SHORT);
        }

        customToast.setIcon(iconResId);
        customToast.setText(str);
        customToast.show();
    }

    public static Toast systemToast = null;

    /**
     * 系统框的TOAST，可以显示图片
     *
     * @param str
     * @param resId
     */
    public static void displayToast(String str, int resId) {
        LayoutInflater inflater = (LayoutInflater) MyApplication
                .getInstance()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View winView = inflater.inflate(R.layout.layout_custom_toast2, null);
        ImageView img = (ImageView) winView.findViewById(R.id.img);
        TextView tv = (TextView) winView.findViewById(R.id.txt);

        if (resId != 0) {
            winView.setPadding(20, 20, 20, 20);
            img.setVisibility(View.VISIBLE);
            img.setBackgroundResource(resId);
        } else {
            img.setVisibility(View.GONE);
        }

        if (str.trim().length() > 0) {
            if (resId != 0) {
                tv.setPadding(0, 5, 0, 0);
            }
            tv.setText(str);
        } else {
            tv.setVisibility(View.GONE);
        }

        if (systemToast == null) {
            systemToast = new Toast(MyApplication.getInstance()); // 建立Toast
            systemToast.setGravity(Gravity.CENTER, 0, 0);
            systemToast.setDuration(Toast.LENGTH_LONG);
        } else {
            // 3.0及以上版本toast.cancel()不能使用
            if (Build.VERSION.SDK_INT < 13) {
                systemToast.cancel();
            }
        }
        systemToast.setView(winView); // 设置其中的View
        systemToast.show(); // 显示Toast
    }

    /**
     * 系统框的TOAST，显示图片
     *
     * @param resId
     */
    public static void displayToastOfImage(int resId) {
        LayoutInflater inflater = (LayoutInflater) MyApplication
                .getInstance()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View winView = inflater.inflate(R.layout.layout_custom_toast3, null);
        ImageView img = (ImageView) winView.findViewById(R.id.img);

        if (resId != 0) {
            winView.setPadding(20, 20, 20, 20);
            img.setVisibility(View.VISIBLE);
            img.setBackgroundResource(resId);
        } else {
            img.setVisibility(View.GONE);
        }

        if (systemToast == null) {
            systemToast = new Toast(MyApplication.getInstance()); // 建立Toast
            systemToast.setGravity(Gravity.CENTER, 0, 0);
            systemToast.setDuration(Toast.LENGTH_LONG);
        } else {
            // 3.0及以上版本toast.cancel()不能使用
            if (Build.VERSION.SDK_INT < 13) {
                systemToast.cancel();
            }
        }
        systemToast.setView(winView); // 设置其中的View
        systemToast.show(); // 显示Toast
    }


    /**
     * 显示loading
     *
     * @param context
     * @param msg
     */
    public static void showLoadingDialog(Context context, String msg) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.setMessage(msg);
        loadingDialog.show();
    }


    /**
     * 显示iosdialog
     *
     * @param activity
     * @param title
     * @param itemInfos
     */
    public static void showActionSheetDialog(Activity activity, String title, ArrayList<ActionSheetItemInfo> itemInfos) {
        if (activity==null||itemInfos == null) {
            return;
        }

        ActionSheetDialog dialog = new ActionSheetDialog(activity);
        dialog.builder();
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        for (int i = 0; i < itemInfos.size(); i++) {
            dialog.addSheetItem(itemInfos.get(i).getItemName(), ActionSheetDialog.SheetItemColor.Blue, itemInfos.get(i).getListener());
        }
        dialog.show();

    }

    private static SweetAlertDialog loadingDialog = null;

    public static void showLoadingDialog(Activity activity) {
        loadingDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        loadingDialog.show();
    }

    public static void dimissLoadingDia() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

}
