package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.ImageInfo;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskDetailedOrderListBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.SignSearch;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrderDetail;
import com.cvnavi.logistics.i51ehang.app.bean.request.ConfirmSignRequest;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.BitmapUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ActionSheetDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ActionSheetItemInfo;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.google.gson.Gson;
import com.king.photo.activity.AlbumActivity;
import com.king.photo.activity.GalleryActivity;
import com.king.photo.util.Bimp;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
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
 * Depict: 确认签收 页面
 */
public class Dr_SignOrderActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener{
    public static final String INTENT_INFO = "INTENT_INFO";
    public static final String INTENT_FROM_TAG = "INTENT_FROM_TAG";
    public static final int FROM_TASK_DETAIL = 2;
    public static final int FROM_ORDER_DETAIL = 1;
    @BindView(R.id.back_llayout)
    LinearLayout mBackLlayout;
    @BindView(R.id.titlt_tv)
    TextView mTitltTv;
    @BindView(R.id.add_iv)
    ImageView mAddIv;
    @BindView(R.id.add_ll)
    LinearLayout mAddLl;
    @BindView(R.id.search_iv)
    ImageView mSearchIv;
    @BindView(R.id.search_ll)
    LinearLayout mSearchLl;
    @BindView(R.id.right_ll)
    LinearLayout mRightLl;
    @BindView(R.id.right_tv)
    TextView mRightTv;
    @BindView(R.id.content_ll)
    LinearLayout mContentLl;
    @BindView(R.id.check_tv)
    TextView mCheckTv;
    @BindView(R.id.add)
    LinearLayout mAdd;
    @BindView(R.id.custom_ll)
    LinearLayout mCustomLl;
    @BindView(R.id.yingshoukuan_tv)
    TextView mYingshoukuanTv;
    @BindView(R.id.sign_time_et)
    TextView mSignTimeEt;
    @BindView(R.id.sign_name)
    TextView mSignName;
    @BindView(R.id.sign_name_et)
    EditText mSignNameEt;
    @BindView(R.id.choice_ll)
    LinearLayout mChoiceLl;
    @BindView(R.id.imag)
    ImageView mImag;
    @BindView(R.id.noScrollgridview)
    GridView mNoScrollgridview;
    @BindView(R.id.sign_ok)
    TextView mSignOk;


    private GridAdapter adapter;
    public static Bitmap bimap;
    private mOrderDetail orderInfo;

    private SignSearch search = new SignSearch();
    private int fromTag = FROM_ORDER_DETAIL;

    private SweetAlertDialog loading;
    private HandlerUtils.HandlerHolder mHandlerHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_send_goods_sign_order);
        ButterKnife.bind(this);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        initTop();
        initImageComponse();
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bimp.tempSelectBitmap.clear();
        Bimp.max = 0;
        if (bimap != null && bimap.isRecycled()) {
            bimap.recycle();
            bimap = null;
        }
        if (bm != null && bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }
        if(trHandler!=null){
            trHandler.removeCallbacksAndMessages(null);
        }
        trHandler=null;
        mRunnable=null;
    }


    private void initTop() {
        fromTag = getIntent().getIntExtra(INTENT_FROM_TAG, FROM_TASK_DETAIL);

        mSignTimeEt.setText(DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM));
        mTitltTv.setText("确认签收");
        if (fromTag == FROM_ORDER_DETAIL) {
            //从货单详情进入的
            orderInfo = (mOrderDetail) getIntent().getSerializableExtra(INTENT_INFO);
        } else if (fromTag == FROM_TASK_DETAIL) {
            //派车清单明细进入的
            TaskDetailedOrderListBean info = (TaskDetailedOrderListBean) getIntent().getSerializableExtra(Constants.TaskDetailedOrder);
            TaskBean bean = (TaskBean) getIntent().getSerializableExtra(Constants.TaskBean);

            if (orderInfo == null) {
                orderInfo = new mOrderDetail();
                orderInfo.All_Ticket_No = info.All_Ticket_No;
                orderInfo.Goods_Num = bean.Goods_Num;
                orderInfo.Goods_Weight = bean.Goods_Weight;
                orderInfo.Bulk_Weight = bean.Bulk_Weight;
                orderInfo.YSK_Fee = info.YSK_Fee;
                orderInfo.ReceiveMan_Name = info.ReceiveMan_Name;
            }

            search.Letter_Oid = bean.Letter_Oid;
            search.Bulk_Weight = info.Bulk_Weight;
            search.Goods_Num = info.Goods_Num;
            search.Goods_Weight = info.Goods_Weight;
            search.ZD_Org = bean.Operate_Org;
        }

        if (orderInfo != null && orderInfo.YSK_Fee != null) {
            mYingshoukuanTv.setText(orderInfo.YSK_Fee);
            mSignNameEt.setText(orderInfo.ReceiveMan_Name);
        }
    }

    private void initImageComponse() {
        Res.init(this);
        bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
        PublicWay.activityList.add(this);

//        Init();//初始化图片上传 的方法
        showImageSelect();
    }

    @OnClick({R.id.choice_ll, R.id.sign_ok, R.id.back_llayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choice_ll:

                ArrayList<ActionSheetItemInfo> list = new ArrayList<ActionSheetItemInfo>();
                list.add(new ActionSheetItemInfo("门卫签收", new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        mSignNameEt.setText("门卫签收");

                    }
                }));
                list.add(new ActionSheetItemInfo("公章签收", new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        mSignNameEt.setText("公章签收");

                    }
                }));

                DialogUtils.showActionSheetDialog(Dr_SignOrderActivity.this, "常用签收人", list);
                break;
            case R.id.sign_ok:
                    if (TextUtils.isEmpty(mSignNameEt.getText().toString())) {
                        DialogUtils.showWarningToast("请填写签收人！");
                        return;
                    }

                    if (Bimp.tempSelectBitmap.size() == 0) {
                        DialogUtils.showWarningToast("请上传照片！");
                        return;
                    }
                loading.show();
                HandlerThread thread = new HandlerThread("MyHandlerThread");
                thread.start();
                trHandler = new Handler(thread.getLooper());
                trHandler.post(mRunnable);
                break;
            case R.id.back_llayout:
                finish();
                break;
        }
    }

    Handler trHandler;
    List<ImageInfo> IMGList;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            IMGList = new ArrayList<>();
            for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                String fileName = DateUtil.getCurDateStr(DateUtil.FORMAT_FULL_SN) + new Random().nextInt(100000);
                IMGList.add(new ImageInfo(orderInfo.All_Ticket_No, "3000", fileName + ".png", ".png", BitmapUtil.encodeToBase64(Bimp.tempSelectBitmap.get(i).getBitmap(), Bitmap.CompressFormat.PNG, 100 / 100)));
            }
            upLoad();
        }
    };
    private void upLoad() {

        ConfirmSignRequest request = new ConfirmSignRequest();
        request.All_Ticket_No = orderInfo.All_Ticket_No;
        if (search.Letter_Oid.equals("自提")) {
            request.Letter_Oid = "";
        } else {
            request.Letter_Oid = search.Letter_Oid;

        }
        request.Deliver_Note = "";
        request.Deliver_Name = mSignNameEt.getText().toString();
        request.Deliver_DateTime = mSignTimeEt.getText().toString();

        request.ZD_Org = search.ZD_Org;
        request.Goods_Num = orderInfo.Goods_Num;
        request.Goods_Weight = orderInfo.Goods_Weight;
        request.Bulk_Weight = orderInfo.Bulk_Weight;
        request.QianShouCount = search.Goods_Num;
        request.QianShouZhongLiang = search.Goods_Weight;
        request.QianShouLiFang = search.Bulk_Weight;
        request.YSK_Fee = orderInfo.YSK_Fee;

        request.IMGList = IMGList;
        final DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = request;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Org_Code = search.ZD_Org;
        dataRequestBase.User_Name = MyApplication.getInstance().getLoginInfo().DataValue.User_Name;

        LogUtil.d("-->>request = " + new Gson().toJson(dataRequestBase));


        VolleyManager.newInstance().PostJsonRequest(DriverService.ConfirmSign_TAG, TMSService.ConfirmSign_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>respo = " + response.toString());
                DataResponseBase dataResponseBase = GsonUtil.newInstance().fromJson(response, DataResponseBase.class);
                Message msg = Message.obtain();
                if (dataResponseBase != null) {
                    if (dataResponseBase.Success) {
                        msg.what = Constants.REQUEST_SUCC;
                    } else {
                        msg.what = Constants.REQUEST_FAIL;
                    }
                } else {
                    msg.what = Constants.REQUEST_ERROR;
                }
                mHandlerHolder.sendMessage(msg);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);

            }
        });

    }

    @Override
    public void handlerMessage(Message msg) {
        if (loading != null) {

            loading.dismiss();
        }
        trHandler.removeCallbacks(mRunnable);
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(Dr_SignOrderActivity
                        .this, "签收成功！", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showFailToast("签收失败！");
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showFailToast(Utils.getResourcesString(R.string.request_error));
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
            loading();
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
                convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                holder.bt = (Button) convertView.findViewById(R.id.item_grida_bt);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.tempSelectBitmap.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
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
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
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
            }).start();
        }
    }


    public void showImageSelect() {

        mNoScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        mNoScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(getApplicationContext());
//        adapter.update();
        mNoScrollgridview.setAdapter(adapter);
        mNoScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    ArrayList<ActionSheetItemInfo> list = new ArrayList<ActionSheetItemInfo>();
                    list.add(new ActionSheetItemInfo("拍照", new ActionSheetDialog.OnSheetItemClickListener() {

                        @Override
                        public void onClick(int which) {
                            photo();
                        }
                    }));
                    list.add(new ActionSheetItemInfo("从相册中选取", new ActionSheetDialog.OnSheetItemClickListener() {

                        @Override
                        public void onClick(int which) {
                            Intent intent = new Intent(Dr_SignOrderActivity.this, AlbumActivity.class);
                            startActivity(intent);
                        }
                    }));

                    DialogUtils.showActionSheetDialog(Dr_SignOrderActivity.this, "图片选择", list);
                } else {
                    Intent intent = new Intent(Dr_SignOrderActivity.this, GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });


    }

    public String getString(String s) {
        String path = null;
        if (s == null) return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    private static final int TAKE_PICTURE = 0x000001;
    private String picFileFullName;

    public void photo() {

//        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(openCameraIntent, TAKE_PICTURE);
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
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 3 && resultCode == RESULT_OK) {

                    try {
                        bm = BitmapFactory.decodeFile(picFileFullName);
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }
                    if(bm!=null) {
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

    /**
     * 点击空白处，软键盘消失
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

    }



}
