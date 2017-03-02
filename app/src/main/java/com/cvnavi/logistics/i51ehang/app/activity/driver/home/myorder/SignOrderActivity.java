package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.ImageInfo;
import com.cvnavi.logistics.i51ehang.app.bean.model.SignSearch;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrderDetail;
import com.cvnavi.logistics.i51ehang.app.bean.request.ConfirmSignRequest;
import com.cvnavi.logistics.i51ehang.app.bean.request.ConfirmSignSearchRequest;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.ConfirmSignSearchResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.BitmapUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
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
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
import com.king.photo.util.Res;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;
/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 下午1:10
*描述：确认签收
************************************************************************************/

public class SignOrderActivity extends BaseActivity {
    public static final String INTENT_INFO = "INTENT_INFO";
    public static final String INTENT_FROM_TAG = "INTENT_FROM_TAG";
    public static final int REQUEST_CODE = 0X14;
    public static final int FROM_TASK_DETAIL = 2;
    public static final int FROM_ORDER_DETAIL = 1;

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
    @BindView(R.id.yingshoukuan_tv)
    TextView yingshoukuanEt;
    @BindView(R.id.sign_time_et)
    TextView signTimeEt;
    @BindView(R.id.sign_name)
    TextView signName;
    @BindView(R.id.sign_name_et)
    EditText signNameEt;
    @BindView(R.id.choice_ll)
    LinearLayout choiceLl;
    @BindView(R.id.imag)
    ImageView imag;
    @BindView(R.id.noScrollgridview)
    GridView noScrollgridview;
    @BindView(R.id.sign_ok)
    TextView signOk;
    @BindView(R.id.sign_num_tv)
    TextView signNumTv;
    @BindView(R.id.jianshu_tv)
    TextView jianshuTv;
    @BindView(R.id.num_ll)
    LinearLayout numLl;
    @BindView(R.id.Letter_Oid_ll)
    LinearLayout LetterOidLl;
    private GridAdapter adapter;
    private View parentView;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    public static Bitmap bimap;
    private mOrderDetail orderInfo;

    private List<SignSearch> searchList;
    private int index = 0;
    private SignSearch search = new SignSearch();
    private int fromTag = FROM_ORDER_DETAIL;

    private SweetAlertDialog loading;

    public static void startActivty(Activity activity, mOrderDetail info, int requestCode, int fromTag) {
        Intent intent = new Intent(activity, SignOrderActivity.class);
        intent.putExtra(INTENT_INFO, info);
        intent.putExtra(INTENT_FROM_TAG, fromTag);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = getLayoutInflater().inflate(R.layout.activity_sign_order, null);
        setContentView(R.layout.activity_sign_order);
        ButterKnife.bind(this);
        initTop();
        initImageComponse();
    }


    private void initTop() {
        fromTag = getIntent().getIntExtra(INTENT_FROM_TAG, FROM_TASK_DETAIL);

        signTimeEt.setText(DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM));
        titltTv.setText("确认签收");
        if (fromTag == FROM_ORDER_DETAIL) {
            //从货单详情进入的
            orderInfo = (mOrderDetail) getIntent().getSerializableExtra(INTENT_INFO);
            getSignInfo();
            LetterOidLl.setVisibility(View.VISIBLE);
        }

        if (orderInfo != null && orderInfo.YSK_Fee != null) {
            yingshoukuanEt.setText(orderInfo.YSK_Fee);
            signNameEt.setText(orderInfo.ReceiveMan_Name);
        }
    }

    private void initImageComponse() {
        Res.init(this);
        bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
        PublicWay.activityList.add(this);
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
                        signNameEt.setText("门卫签收");

                    }
                }));
                list.add(new ActionSheetItemInfo("公章签收", new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        signNameEt.setText("公章签收");

                    }
                }));

                DialogUtils.showActionSheetDialog(SignOrderActivity.this, "常用签收人", list);
                break;
            case R.id.sign_ok:
                if (TextUtils.isEmpty(signNumTv.getText().toString())) {
                    DialogUtils.showWarningToast("请填写任务编号！");
                    return;
                }

                if (TextUtils.isEmpty(signNameEt.getText().toString())) {
                    DialogUtils.showWarningToast("请填写签收人！");
                    return;
                }

                if (Bimp.tempSelectBitmap.size() == 0) {
                    DialogUtils.showWarningToast("请上传照片！");
                    return;
                }

                if (search == null) {
                    DialogUtils.showWarningToast("无任务单号！");
                    return;
                }
                upLoad();
                break;
            case R.id.back_llayout:
                finish();
                break;
        }
    }


    /**
     * 进入查询(任务清单编号)
     */
    private void getSignInfo() {
        loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        loading.show();
        ConfirmSignSearchRequest request = new ConfirmSignSearchRequest();
        request.All_Ticket_No = orderInfo.All_Ticket_No;
        request.IsDeliver = "0";//0未签收、2已经签收（不传默认未签收
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = request;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        LogUtil.d("-->>searchRequest= " + new Gson().toJson(dataRequestBase));

        VolleyManager.newInstance().PostJsonRequest(TMSService.ConfirmDevlierSearch_TAG, TMSService.ConfirmDevlierSearch_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>searchRespon = " + response.toString());

                ConfirmSignSearchResponse info = GsonUtil.newInstance().fromJson(response, ConfirmSignSearchResponse.class);
                Message msg = Message.obtain();
                if (info != null) {
                    if (info.Success) {
                        msg.what = Constants.REQUEST_SUCC;
                        msg.obj = info.DataValue;
                    } else {
                        msg.what = Constants.REQUEST_FAIL;
                    }
                } else {
                    msg.what = Constants.REQUEST_ERROR;
                }
                searchHander.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                searchHander.sendMessage(msg);
            }
        });

    }


    private Handler searchHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loading.dismiss();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    searchList = (List<SignSearch>) msg.obj;
                    if (searchList != null && searchList.size() > 0) {
                        //有数据的话取第一条数据
                        search = searchList.get(0);
                        signNumTv.setText(searchList.get(0).Letter_Oid);
                        jianshuTv.setText(searchList.get(0).Goods_Num);
                        signNumTv.setOnClickListener(null);
                    } else {
                        DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SignOrderActivity.this, "无法签收！", new CustomDialogListener() {
                            @Override
                            public void onDialogClosed(int closeType) {
                                SignOrderActivity.this.finish();
                            }
                        });
                    }

                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SignOrderActivity.this, "无法签收！", new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            SignOrderActivity.this.finish();
                        }
                    });
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SignOrderActivity.this, "无法签收！", new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            SignOrderActivity.this.finish();
                        }
                    });
                    break;
            }

        }
    };


    private void upLoad() {
        loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        loading.show();
        ConfirmSignRequest request = new ConfirmSignRequest();
        request.All_Ticket_No = orderInfo.All_Ticket_No;
        if (search.Letter_Oid.equals("自提")) {
            request.Letter_Oid = "";
        } else {
            request.Letter_Oid = search.Letter_Oid;

        }
        request.Deliver_Note = "";
        request.Deliver_Name = signNameEt.getText().toString();
        request.Deliver_DateTime = signTimeEt.getText().toString();
        request.ZD_Org = search.ZD_Org;
        request.Goods_Num = orderInfo.Goods_Num;
        request.Goods_Weight = orderInfo.Goods_Weight;
        request.Bulk_Weight = orderInfo.Bulk_Weight;
        request.QianShouCount = search.Goods_Num;
        request.QianShouZhongLiang = search.Goods_Weight;
        request.QianShouLiFang = search.Bulk_Weight;

        List<ImageInfo> IMGList = new ArrayList<ImageInfo>();

        for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            IMGList.add(new ImageInfo(orderInfo.All_Ticket_No, null, orderInfo.All_Ticket_No + "-" + new Random().nextInt(100) + ".png", ".png", BitmapUtil.encodeToBase64(Bimp.tempSelectBitmap.get(i).getBitmap(), Bitmap.CompressFormat.PNG, 100 / 100)));
        }

        request.IMGList = IMGList;
        final DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = request;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.User_Name = MyApplication.getInstance().getLoginInfo().DataValue.User_Name;

        LogUtil.d("-->>request = " + new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TMSService.ConfirmSign_TAG, TMSService.ConfirmSign_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
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
                myHandler.sendMessage(msg);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                myHandler.sendMessage(msg);

            }
        });

    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loading.dismiss();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SignOrderActivity
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
    };

    /**
     * 点击清单编号
     */
    @OnClick(R.id.num_ll)
    public void onClick() {
        if (searchList != null && searchList.size() > 0) {
            ArrayList<ActionSheetItemInfo> list = new ArrayList<ActionSheetItemInfo>();
            for (int i = 0; i < searchList.size(); i++) {
                index = i;
                list.add(new ActionSheetItemInfo(searchList.get(i).Letter_Oid, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        search = searchList.get(index);
                        signNumTv.setText(searchList.get(index).Letter_Oid);
                        jianshuTv.setText(searchList.get(index).Goods_Num);
                    }
                }));
            }
            DialogUtils.showActionSheetDialog(SignOrderActivity.this, "选择清单编号", list);
        } else {
            //首次进入编号加载失败，重新加载
            DialogUtils.showMessageDialog(SignOrderActivity.this, "温馨提示", "是否加载清单编号", "确定", "取消", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    if (closeType == CustomDialogListener.BUTTON_OK) {
                        DialogUtils.showLoadingDialog(SignOrderActivity.this);
                        getSignInfo();
                    }

                }
            });


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

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
//        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
                            Intent intent = new Intent(SignOrderActivity.this, AlbumActivity.class);
                            startActivity(intent);
                        }
                    }));

                    DialogUtils.showActionSheetDialog(SignOrderActivity.this, "图片选择", list);
                } else {
                    Intent intent = new Intent(SignOrderActivity.this, GalleryActivity.class);
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

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    private static final int TAKE_PICTURE = 0x000001;

    public void photo() {

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(openCameraIntent, TAKE_PICTURE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 3 && resultCode == RESULT_OK) {

                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Bimp.tempSelectBitmap.add(takePhoto);
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
    }
}
