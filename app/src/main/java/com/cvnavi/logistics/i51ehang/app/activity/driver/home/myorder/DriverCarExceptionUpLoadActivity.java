package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder;

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
import android.widget.RelativeLayout;
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
import com.cvnavi.logistics.i51ehang.app.bean.request.CarAbnormalPhoto;
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
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/26.
 * <p/>
 * 车辆异常上报
 */
public class DriverCarExceptionUpLoadActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener {
    private static final String TAG = "DriverCarExceptionUpLoa";
    private static final String CarAbnormalPhoto_TAG = "CarAbnormalPhoto";
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.noScrollgridview)
    GridView noScrollgridview;
    @BindView(R.id.select_tv)
    TextView selectTv;
    @BindView(R.id.ok_btn)
    TextView okBtn;
    @BindView(R.id.type_rl)
    RelativeLayout typeRl;
    @BindView(R.id.note_et)
    EditText noteEt;
    private GridAdapter adapter;

    public Bitmap bimap;
    private String Exception_Type_Oid;
    private TaskBean taskBean;
    private SweetAlertDialog loading;
    CarAbnormalPhoto request;
    private HandlerUtils.HandlerHolder mHandlerHolder;

    public static void start(Context context, CarAbnormalPhoto car) {
        Intent starter = new Intent(context, DriverCarExceptionUpLoadActivity.class);
        starter.putExtra(CarAbnormalPhoto_TAG, car);
        context.startActivity(starter);
    }

    public static void start(Context context, TaskBean taskBean) {
        Intent starter = new Intent(context, DriverCarExceptionUpLoadActivity.class);
        starter.putExtra(Constants.TASKINFO, taskBean);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_upload_photo);
        ButterKnife.bind(this);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        request = new CarAbnormalPhoto();
        taskBean = (TaskBean) getIntent().getSerializableExtra(Constants.TASKINFO);

        titltTv.setText("异常上报");
        loading = new SweetAlertDialog(DriverCarExceptionUpLoadActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        Res.init(this);
        bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
        PublicWay.activityList.add(this);
        showBottomWinDow();
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (TextUtils.isEmpty(noteEt.getText().toString())) {
                    DialogUtils.showWarningToast("请填写拍照记事！");
                    return;
                }

                if (Bimp.tempSelectBitmap.size() == 0) {
                    DialogUtils.showWarningToast("请上传照片！");
                    return;
                }

                if (TextUtils.isEmpty(selectTv.getText().toString())) {
                    DialogUtils.showWarningToast("请选择异常类型！");
                    return;
                }

                loading.show();
                HandlerThread thread = new HandlerThread("MyHandlerThread");
                thread.start();
                trHandler = new Handler(thread.getLooper());
                trHandler.post(mRunnable);
            }
        });

        backLlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private Handler trHandler;
    private List<ImageInfo> IMGList;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            IMGList = new ArrayList<>();
            for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
                String fileName = DateUtil.getCurDateStr(DateUtil.FORMAT_FULL_SN) + new Random().nextInt(100000);
                IMGList.add(new ImageInfo(taskBean.All_Ticket_No, "3000", fileName + ".png", ".png", BitmapUtil.encodeToBase64(Bimp.tempSelectBitmap.get(i).getBitmap(), Bitmap.CompressFormat.PNG, 100 / 100)));
            }
            upLoad();
        }
    };

    private void upLoad() {

        if (taskBean != null) {
            request.CarCode = taskBean.CarCode;
            request.CarCode_Key = taskBean.CarCode_Key;
            request.Driver_Key = taskBean.DriverSerial_Oid;
            request.Letter_Oid = taskBean.Letter_Oid;
        } else {
            request = (CarAbnormalPhoto) getIntent().getSerializableExtra(CarAbnormalPhoto_TAG);
        }

        request.Exception_Content = noteEt.getText().toString();
        request.Exception_DateTime = DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHMS);
        request.Exception_Type_Oid = Exception_Type_Oid;


        request.IMGList = IMGList;
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = request;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.User_Name = MyApplication.getInstance().getLoginInfo().DataValue.User_Name;
        VolleyManager.newInstance().PostJsonRequest(DriverService.CarAbnormalPhoto_TAG, TMSService.CarAbnormalPhoto_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>respo = " + response.toString());
                DataResponseBase dataResponseBase = GsonUtil.newInstance().fromJson(response, DataResponseBase.class);
                Message msg = Message.obtain();
                if (dataResponseBase != null) {
                    if (dataResponseBase.Success) {
                        msg.what = Constants.REQUEST_SUCC;
                    } else {
                        msg.obj = dataResponseBase.ErrorText;
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


    @OnClick(R.id.type_rl)
    public void onClick() {

        ArrayList<ActionSheetItemInfo> list = new ArrayList<ActionSheetItemInfo>();
        list.add(new ActionSheetItemInfo("堵车", new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                Exception_Type_Oid = "AA";
                selectTv.setText("堵车");

            }
        }));
        list.add(new ActionSheetItemInfo("车辆故障", new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                Exception_Type_Oid = "AB";
                selectTv.setText("车辆故障");
            }
        }));
        list.add(new ActionSheetItemInfo("交通事故", new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                Exception_Type_Oid = "AC";
                selectTv.setText("交通事故");
            }
        }));

        DialogUtils.showActionSheetDialog(this, "车辆异常", list);
    }

    @Override
    public void handlerMessage(Message msg) {
        if (loading != null) {
            loading.dismiss();
        }
        trHandler.removeCallbacks(mRunnable);
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverCarExceptionUpLoadActivity.this, "上传成功！", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        finish();
                    }
                });
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? "上传失败！" : msg.obj.toString());
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showFailToast(Utils.getResourcesString(R.string.request_error));
                break;
        }
    }


    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private Context mContext;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            this.mContext=context;
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

    /**
     *
     */
    public void showBottomWinDow() {
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(getApplicationContext());
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    new ActionSheetDialog(DriverCarExceptionUpLoadActivity.this).builder().setTitle("图片类型").setCancelable(false).setCanceledOnTouchOutside(false).addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            photo();
                        }
                    }).addSheetItem("从相册中选取", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            Intent intent = new Intent(DriverCarExceptionUpLoadActivity.this, AlbumActivity.class);
                            startActivity(intent);
                        }
                    }).show();


                } else {
                    Intent intent = new Intent(DriverCarExceptionUpLoadActivity.this, GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });


    }


    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    private static final int TAKE_PICTURE = 0x000001;
    private String picFileFullName;

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 3 && resultCode == RESULT_OK) {

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

    /**
     * 点击空白处，软键盘消失
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bimp.tempSelectBitmap.clear();
        Bimp.max = 0;
        if (bimap != null && !bimap.isRecycled()) {
            bimap.recycle();
            bimap = null;
        }
        if (bm != null && !bm.isRecycled()) {
            bm.recycle();
            bm = null;
        }
        if (trHandler != null) {
            trHandler.removeCallbacksAndMessages(null);
        }
        trHandler = null;
        mRunnable = null;
    }


}
