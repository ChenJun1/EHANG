package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.ImageBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskDetailedOrderListBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.DeliveryConfirmRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.utils.BitmapUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.king.photo.activity.AlbumActivity;
import com.king.photo.activity.GalleryActivity;
import com.king.photo.util.Bimp;
import com.king.photo.util.ImageItem;
import com.king.photo.util.Res;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict:确认提货页面
 */
public class Dr_ConfirmPickUpGoodsActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener {

    private final String TAG = Dr_ConfirmPickUpGoodsActivity.class.getName();

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.imag)
    ImageView imag;
    @BindView(R.id.noScrollgridview)
    GridView noScrollgridview;
    @BindView(R.id.arrived_car_date_tv)
    TextView arrivedCarDateTv;
    @BindView(R.id.submit_btn)
    TextView submitBtn;

    private GridAdapter adapter;

    private View parentView;

    private PopupWindow pop = null;

    private LinearLayout ll_popup;

    public Bitmap bimap = null;

    private SweetAlertDialog lodingDialog;

    private String dataTime;

    private TaskDetailedOrderListBean detaiBean;

    private DeliveryConfirmRequest request;

    private String Letter_Oid;

    private Boolean ThreadBool = false;

    private Handler mHandler;

    private HandlerUtils.HandlerHolder mHandlerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_confirm_pick_up_goods_act);
        parentView = getLayoutInflater().inflate(R.layout.activity_confirm_arrived_car, null);
        ButterKnife.bind(this);
        Res.init(this);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
        initView();
        Init();
    }

    @Override
    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bimap != null && bimap.isRecycled()) {
            bimap.recycle();
            bimap = null;
        }
        if (bm != null && bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }
        Bimp.tempSelectBitmap.clear();
        Bimp.max = 0;
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if(trHandler!=null){
            trHandler.removeCallbacksAndMessages(null);
        }
        mHandler=null;
        mRunnable=null;
    }

    public static void start(Context context, TaskDetailedOrderListBean bean, TaskBean taskBean) {
        Intent starter = new Intent(context, Dr_ConfirmPickUpGoodsActivity.class);
        starter.putExtra(Constants.TaskDetailedOrder, bean);
        starter.putExtra(Constants.Letter_Oid, taskBean.Letter_Oid);
        context.startActivity(starter);
    }

    private void initView() {
        titltTv.setText(Utils.getResourcesString(R.string.confirm_pick_up_goods));
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        request = new DeliveryConfirmRequest();
        dataTime = DateUtil.getNowTime();
        arrivedCarDateTv.setText(dataTime);
        if (getIntent().getSerializableExtra(Constants.TaskDetailedOrder) != null && getIntent().getStringExtra(Constants.Letter_Oid) != null) {
            detaiBean = (TaskDetailedOrderListBean) getIntent().getSerializableExtra(Constants.TaskDetailedOrder);
            Letter_Oid = getIntent().getStringExtra(Constants.Letter_Oid);
        }
    }

    /**
     * 初始化图片上传的方法
     */
    public void Init() {

        pop = new PopupWindow(Dr_ConfirmPickUpGoodsActivity.this);

        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Dr_ConfirmPickUpGoodsActivity.this,
                        AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(getApplicationContext());
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(Dr_ConfirmPickUpGoodsActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(Dr_ConfirmPickUpGoodsActivity.this,
                            GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });

    }


    private final int TAKE_PICTURE = 0x000001;//请求码
    private String picFileFullName; //图片文件名

    public void photo() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }

            File outFile = new File(outDir, System.currentTimeMillis()
                    + "driver_license.png");
            picFileFullName = outFile.getAbsolutePath();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, TAKE_PICTURE);
        } else {
            Toast.makeText(this, "请确认是否插入SD卡！", Toast.LENGTH_SHORT).show();
        }
    }

    Bitmap bm = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    if (Bimp.tempSelectBitmap.size() < 3) {

                        try {
                            bm = BitmapFactory.decodeFile(picFileFullName);
                        } catch (OutOfMemoryError e) {
                            e.printStackTrace();
                        }
                        if (bm != null) {
                            bm = BitmapUtil.comp(bm);
                            ImageItem takePhoto = new ImageItem();
                            takePhoto.setBitmap(bm);
                            takePhoto.setImagePath(picFileFullName);
                            Bimp.tempSelectBitmap.add(takePhoto);
                        }
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.back_llayout, R.id.submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.submit_btn:
                checkValue();
                break;
        }
    }

    //提交前检查
    private boolean checkValue() {
        if (!TextUtils.isEmpty(Letter_Oid)) {
            request.Letter_Oid = Letter_Oid;
        } else {
            DialogUtils.showNormalToast("清单号为空！");
            return false;
        }

        if (detaiBean == null) {
            return false;
        }
        request.All_Ticket_No = detaiBean.All_Ticket_No;
        request.Bulk_Weight = detaiBean.Bulk_Weight;
        request.Goods_Num = detaiBean.Goods_Num;
        request.Goods_Weight = detaiBean.Goods_Weight;
        request.Operate_Code = "EA";
        lodingDialog.show();
        if (Bimp.tempSelectBitmap.size() < 1) {
            list = new ArrayList<>();
            submitRequest(DriverService.DeliveryConfirm_Request_Url);
        } else {
            HandlerThread thread = new HandlerThread("MyHandlerThread");
            thread.start();
            trHandler = new Handler(thread.getLooper());
            trHandler.post(mRunnable);
        }

        return true;
    }

    Handler trHandler;
    List<ImageBean> list;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            list = new ArrayList<>();
            for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                String base = BitmapUtil.encodeToBase64(Bimp.tempSelectBitmap.get(i).getBitmap(), Bitmap.CompressFormat.PNG, 100 / 100);
                list.add(new ImageBean(request.All_Ticket_No, request.Letter_Oid + new Random().nextInt(100000) + ".png", ".png", "20000", base));
            }
            submitRequest(DriverService.DeliveryConfirm_Request_Url);
        }
    };

    private void submitRequest(final String Url) {

        DataRequestBase dataRequestBase = new DataRequestBase();
        request.IMGList = list;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;//MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = detaiBean.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.User_Name = MyApplication.getInstance().getLoginInfo().DataValue.User_Name;

        dataRequestBase.DataValue = request; //JsonUtils.toJsonData(getDriverListRequest);
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>" + response.toString());
                DataResponseBase response1 = JsonUtils.parseData(response.toString(), DataResponseBase.class);
                Message msg = Message.obtain();
                if (response1 == null) {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                } else {
                    if (response1.Success) {
                        msg.what = Constants.REQUEST_SUCC;
                    } else {
                        msg.obj = response1.ErrorText;
                        msg.what = Constants.REQUEST_FAIL;
                    }
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("-->>" + error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });

    }

    @Override
    public void handlerMessage(Message msg) {
        if (lodingDialog != null) {
            lodingDialog.dismiss();
        }

        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                DialogUtils.showSuccToast(Utils.getResourcesString(R.string.success_confirm));
                finish();
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(Dr_ConfirmPickUpGoodsActivity.this, Utils.getResourcesString(R.string.request_error));
                break;
        }
    }

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            HandlerThread thread = new HandlerThread("myThread");
            thread.start();
            mHandler = new Handler(thread.getLooper());
            mHandler.post(myRunnable);
            ThreadBool = true;
        }

        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 3) {
                return 3;
            }
            return (Bimp.tempSelectBitmap.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                holder.bt = (Button) convertView
                        .findViewById(R.id.item_grida_bt);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                holder.bt.setVisibility(View.GONE);
                if (position == 3) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
                holder.bt.setVisibility(View.VISIBLE);
                holder.bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bimp.tempSelectBitmap.remove(position);
                        Bimp.max--;
                        notifyDataSetChanged();

                    }
                });
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
            public Button bt;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        ThreadBool = false;
                        mHandler.removeCallbacks(myRunnable);
                        break;
                }
                super.handleMessage(msg);
            }
        };

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                while (ThreadBool) {
                    if (Bimp.max == Bimp.tempSelectBitmap.size()) {
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                        break;
                    } else {
                        Bimp.max += 1;
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }
            }
        };
    }

    /**
     * 点击空白处，软键盘消失
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

    }


}
