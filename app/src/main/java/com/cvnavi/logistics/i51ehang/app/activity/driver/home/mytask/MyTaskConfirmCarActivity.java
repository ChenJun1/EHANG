package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.ImageBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.CarConfirmRequest;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.BitmapUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.king.photo.activity.AlbumActivity;
import com.king.photo.activity.GalleryActivity;
import com.king.photo.util.Bimp;
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.king.photo.util.Res;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * 版权所有势航网络
 * Created by ${ChenJ} on 2016/7/25.
 */
public class MyTaskConfirmCarActivity extends BaseActivity {

    private final String TAG = MyTaskCarryDetailedActivity.class.getName();
    public final int CHOICES_NOTE = 2;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.Letter_Note_et)
    EditText LetterNoteEt;
    @BindView(R.id.imag)
    ImageView imag;
    @BindView(R.id.noScrollgridview)
    GridView noScrollgridview;
    @BindView(R.id.Arrive_DateTime_tv)
    TextView ArriveDateTimeTv;
    @BindView(R.id.arrived_node_tv)
    TextView arrivedNodeTv;
    @BindView(R.id.arrived_node_rl)
    RelativeLayout arrivedNodeRl;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    private TaskBean taskBean;

    private GridAdapter adapter;
    private View parentView;
    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    public Bitmap bimap=null;
    private SweetAlertDialog lodingDialog;
    private String dataTime;
    private String noteOrgKey;
    private CarConfirmRequest request;
    private Boolean ThreadBool=false;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_arrived_car);
        parentView = getLayoutInflater().inflate(R.layout.activity_confirm_arrived_car, null);
        ButterKnife.bind(this);
        Res.init(this);
        bimap = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.icon_addpic_unfocused);
        init();
        Init();
    }

    private void init() {
        titltTv.setText(Utils.getResourcesString(R.string.confirm_car));
        lodingDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        dataTime=DateUtil.getNowTime();
        ArriveDateTimeTv.setText(dataTime);
        if (getIntent().getSerializableExtra(Constants.TASKINFO) != null) {
            taskBean = (TaskBean) getIntent().getSerializableExtra(Constants.TASKINFO);
        }


    }

    private void submitRequest(final String Url) {

        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid =MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;//MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.User_Name=MyApplication.getInstance().getLoginInfo().DataValue.User_Name;

        dataRequestBase.DataValue =request; //JsonUtils.toJsonData(getDriverListRequest);
        LogUtil.d("-->>"+dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>"+response.toString());
                DataResponseBase response1 = JsonUtils.parseData(response.toString(), DataResponseBase.class);
                Message msg = Message.obtain();
                if (response1.Success) {

                    msg.what = Constants.REQUEST_SUCC;
                    myHandler.sendMessage(msg);
                } else {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                    myHandler.sendMessage(msg);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("-->>"+error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                myHandler.sendMessage(msg);
            }
        });

    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
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
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(MyTaskConfirmCarActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;


            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case CHOICES_NOTE:
                    Bundle bundle=data.getExtras();
                    String orgName=bundle.getString("ORGNAME");
                    noteOrgKey=bundle.getString("Node_Key");
                    SetViewValueUtil.setTextViewValue(arrivedNodeTv,orgName);
                break;
                case TAKE_PICTURE:
                    if (Bimp.tempSelectBitmap.size() < 3) {

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
    }

    @OnClick({R.id.back_llayout, R.id.submit_btn, R.id.arrived_node_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.submit_btn:
                lodingDialog.show();
                if(checkValue()){
                    submitRequest(TMSService.ShuttleBusConfirm_Request_Url);
                }
                break;
            case R.id.arrived_node_rl:
                if (taskBean != null) {
                    Intent intent = new Intent(this, MyTaskChoicesNoteActivity.class);
                    if (taskBean != null) {
                        intent.putExtra(Constants.TASKINFO, taskBean);
                    }
                    startActivityForResult(intent, CHOICES_NOTE);
                }
                break;
        }
    }

    private boolean checkValue() {
        request=new CarConfirmRequest();
        if(taskBean!=null) {
            request.Letter_Oid = taskBean.Letter_Oid;
        }

        request.DFConfirmFlag="D";
        request.Node_Key=noteOrgKey;
        request.Arrive_Confirm_Type_Oid="3";
        request.Arrive_DateTime=dataTime;
        if(!TextUtils.isEmpty(LetterNoteEt.getText())){
            request.Letter_Note=LetterNoteEt.getText().toString();
        }
        if(Bimp.tempSelectBitmap.size()<1){
            lodingDialog.dismiss();
            DialogUtils.showNormalToast("请上传图片！");
            return false;
        }
        if(TextUtils.isEmpty(request.Node_Key)){
            lodingDialog.dismiss();
            DialogUtils.showNormalToast("请选择节点！");
            return false;
        }
        List<ImageBean>  list=new ArrayList<>();
        for(int i=0;i<Bimp.tempSelectBitmap.size();i++){
            String base= BitmapUtil.encodeToBase64(Bimp.tempSelectBitmap.get(i).getBitmap(), Bitmap.CompressFormat.PNG, 100 / 100);
            list.add(new ImageBean("",taskBean.Letter_Oid+new Random().nextInt(100)+".png",".png","20000",base));
        }
        request.IMGList=list;
        return  true;
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
//            loading();
            HandlerThread thread=new HandlerThread("myThread");
            thread.start();
            mHandler=new Handler(thread.getLooper());
            mHandler.post(myRunnable);
            ThreadBool=true;
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
                        ThreadBool=false;
                        mHandler.removeCallbacks(myRunnable);
                        break;
                }
                super.handleMessage(msg);
            }
        };
         Runnable myRunnable=new Runnable() {
                @Override
                public void run() {
                    while (ThreadBool){
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

//            new Thread(new Runnable() {
//                public void run() {
//                    while (true) {
//                        if (Bimp.max == Bimp.tempSelectBitmap.size()) {
//                            Message message = new Message();
//                            message.what = 1;
//                            handler.sendMessage(message);
//                            break;
//                        } else {
//                            Bimp.max += 1;
//                            Message message = new Message();
//                            message.what = 1;
//                            handler.sendMessage(message);
//                        }
//                    }
//                }
//            }).start();
        }



    @Override
    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    private final int TAKE_PICTURE = 0x000001;
    public void photo() {

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(openCameraIntent, TAKE_PICTURE);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bimap.recycle();
        bimap=null;
        Bimp.tempSelectBitmap.clear();
    }

    /**
     * 初始化图片上传的方法
     */
    public void Init() {

        pop = new PopupWindow(MyTaskConfirmCarActivity.this);

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
                Intent intent = new Intent(MyTaskConfirmCarActivity.this,
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

        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
//        adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.tempSelectBitmap.size()) {
                    Log.i("ddddddd", "----------");
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(MyTaskConfirmCarActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(MyTaskConfirmCarActivity.this,
                            GalleryActivity.class);
                    intent.putExtra("position", "1");
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * 点击空白处，软键盘消失
     */
    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

    }
}
