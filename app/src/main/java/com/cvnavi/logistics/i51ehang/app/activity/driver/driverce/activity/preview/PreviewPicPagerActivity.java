package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyOrder.ExceptionInfoBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyOrder.LogisticsFollowNoteBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.Model_LetterTrace_Node;
import com.cvnavi.logistics.i51ehang.app.bean.model.PictureBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetPictrueListResponse;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ScreenSupport;
import com.cvnavi.logistics.i51ehang.app.widget.view.HackyViewPager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 查看照片页面
 */
public class PreviewPicPagerActivity extends BaseActivity implements OnPageChangeListener {

    private static final String TAG = "PreviewPicPagerActivity";
    public static final String Car_Exception_Pic = "Car_Exception_Pic";//车辆异常照片查看
    @BindView(R.id.viewpager)
    HackyViewPager mViewPager;
    @BindView(R.id.position_tv)
    TextView positionTv;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;

    private PreviewPicPagerAdapter mGuidViewPagerAdapter;
    private List<View> mViews;


    /**
     * 记录当前选中位置
     **/
    private int position = 0;

    private SweetAlertDialog lodingDialog;
    private Model_LetterTrace_Node node = null;
    private DataRequestBase dataRequestBase;
    private List<PictureBean> list = new ArrayList<>();
    private ExceptionInfoBean exceptionBean = null;
    private LogisticsFollowNoteBean logisticsFollowNoteBean = null;

    public static void start(Context context, ExceptionInfoBean exceptionBean, String Type) {
        Intent starter = new Intent(context, PreviewPicPagerActivity.class);
        starter.putExtra(Constants.ExceptionInfoBean, exceptionBean);
        starter.putExtra(Car_Exception_Pic, Type);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_pic);
        ButterKnife.bind(this);

        backLlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViews = new ArrayList<>();
        titleTv.setText("查看照片");
        ScreenSupport.setFullScreen(this, true);//获取当前屏幕的宽高

        if (getIntent().getSerializableExtra(Constants.DRIVER_LINE_LOOK_PIC) != null) {
            node = (Model_LetterTrace_Node) getIntent().getSerializableExtra(Constants.DRIVER_LINE_LOOK_PIC);
        } else if (getIntent().getSerializableExtra(Constants.ExceptionInfoBean) != null) {
            exceptionBean = (ExceptionInfoBean) getIntent().getSerializableExtra(Constants.ExceptionInfoBean);
        } else if (getIntent().getSerializableExtra(Constants.LogisticsFollowNoteBean) != null) {
            logisticsFollowNoteBean = (LogisticsFollowNoteBean) getIntent().getSerializableExtra(Constants.LogisticsFollowNoteBean);
        } else if (getIntent().getSerializableExtra(Constants.MyTaskExceptionInfoActivity) != null) {
            List<PictureBean> dataList = (List<PictureBean>) getIntent().getSerializableExtra(Constants.MyTaskExceptionInfoActivity);
            if (dataList != null) {
                list.clear();
                list.addAll(dataList);
                initview();
            }
        }

        if (exceptionBean != null) {
            dataRequestBase = new DataRequestBase();
            dataRequestBase.DataValue = exceptionBean;
            if (getIntent().getStringExtra(Car_Exception_Pic) != null && getIntent().getStringExtra(Car_Exception_Pic).equals(Car_Exception_Pic)) {
                loadRequest(DriverService.GetCarExceptImgInfo_Request_Url);
            } else {
                loadRequest(TMSService.SelectImage_Request_Url);//数据请求
            }
        }
        //判断哪个页面跳转过来
        if (logisticsFollowNoteBean != null) {
            dataRequestBase = new DataRequestBase();
            dataRequestBase.DataValue = logisticsFollowNoteBean;
            loadRequest(TMSService.SelectImage_Request_Url);
        }
        //判断哪个页面跳转过来
        if (node != null) {
            dataRequestBase = new DataRequestBase();
            dataRequestBase.DataValue = node;
            loadRequest(TMSService.SeeImage_Request_Url);
        }
    }

    /**
     *
     * @param Url
     */
    private void loadRequest(final String Url) {
        lodingDialog = new SweetAlertDialog(PreviewPicPagerActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        lodingDialog.show();

        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest("", Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>response" + response.toString());
                GetPictrueListResponse response1 = JsonUtils.parseData(response.toString(), GetPictrueListResponse.class);
                Message msg = Message.obtain();
                if (response1.Success) {
                    msg.obj = response1.DataValue;
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
                Log.d("", error.toString());
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
                    if (msg.obj != null) {
                        List<PictureBean> dataList = (List<PictureBean>) msg.obj;
                        if (dataList != null) {
                            list.addAll(dataList);
                        }
                        initview();
                    }

                    break;
                case Constants.REQUEST_FAIL:
                    String infoError = (String) msg.obj;
                    if (infoError != null) {
                        DialogUtils.showFailToast(infoError);
                    } else {
                        DialogUtils.showFailToast(Utils.getResourcesString(R.string.get_data_fail));
                    }
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(PreviewPicPagerActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };


    /**
     * 显示图片
     */
    private void initview() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //初始化引导图片列表
        for (int i = 0; i < list.size(); i++) {
            PhotoView imageView = new PhotoView(this);
            imageView.setLayoutParams(layoutParams);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).
                    load(list.get(i).FilePath).
                    crossFade(1000).//淡入淡出,注意:如果设置了这个,则必须要去掉asBitmap
                    placeholder(R.drawable.cry).error(R.drawable.cry).into(imageView);
            mViews.add(imageView);
        }

        positionTv.setText((1) + "/" + mViews.size());
        //初始化adapter
        mGuidViewPagerAdapter = new PreviewPicPagerAdapter(mViews);

        mViewPager.setAdapter(mGuidViewPagerAdapter);
        //绑定回调
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setCurrentItem(position);
    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    //当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        //设置底部小点选中状态
        positionTv.setText((arg0 + 1) + "/" + mViews.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
        myHandler = null;
    }
}
