package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;

/**
 * 版权所有势航网络
 * Created by ${ChenJ} on 2016/7/25.
 */
public class MyTaskLookPicActivity extends BaseActivity {
//
//    private final String TAG = MyTaskDetailedActivity.class.getName();
//    @BindView(R.id.title_tv)
//    TextView titleTv;
//    @BindView(R.id.back_llayout)
//    LinearLayout backLlayout;
//
//    @BindView(R.id.pic_gv)
//    public GridView picGv;
//    private SweetAlertDialog lodingDialog;
//
//
//    private Model_LetterTrace_Node node = null;
//
//    private DataRequestBase dataRequestBase;
//
//    private MyTaskLookPicGriderViewAdapter adapter;
//
//    private List<PictureBean> list;
//
//    private ExceptionInfoBean exceptionBean=null;
//
//    private LogisticsFollowNoteBean logisticsFollowNoteBean =null;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_look_pic);
//        ButterKnife.bind(this);
//        init();
//        if (getIntent().getSerializableExtra(Constants.DRIVER_LINE_LOOK_PIC) != null) {
//            node = (Model_LetterTrace_Node) getIntent().getSerializableExtra(Constants.DRIVER_LINE_LOOK_PIC);
//        }else
//        if (getIntent().getSerializableExtra(Constants.ExceptionInfoBean) != null) {
//            exceptionBean = (ExceptionInfoBean) getIntent().getSerializableExtra(Constants.ExceptionInfoBean);
//        }else
//        if (getIntent().getSerializableExtra(Constants.LogisticsFollowNoteBean) != null) {
//            logisticsFollowNoteBean = (LogisticsFollowNoteBean) getIntent().getSerializableExtra(Constants.LogisticsFollowNoteBean);
//        }else if(getIntent().getSerializableExtra(Constants.MyTaskExceptionInfoActivity) != null){
//            List<PictureBean> dataList= (List<PictureBean>) getIntent().getSerializableExtra(Constants.MyTaskExceptionInfoActivity);
//           if(dataList!=null) {
//               list.clear();
//               list.addAll(dataList);
//           }
//            adapter.notifyDataSetChanged();
//        }
//
//        if(exceptionBean!=null){
//            dataRequestBase = new DataRequestBase();
//            dataRequestBase.DataValue=exceptionBean;
//            loadRequest(TMSService.SelectImage_Request_Url);
//        }
//        if(logisticsFollowNoteBean!=null){
//            dataRequestBase = new DataRequestBase();
//            dataRequestBase.DataValue=logisticsFollowNoteBean;
//            loadRequest(TMSService.SelectImage_Request_Url);
//        }
//        if (node != null) {
//            dataRequestBase = new DataRequestBase();
//            dataRequestBase.DataValue=node;
//            loadRequest(TMSService.SeeImage_Request_Url);
//        }
//    }
//
//    private void init() {
//        titleTv.setText(Utils.getResourcesString(R.string.look_pic));
//        lodingDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
//        list = new ArrayList<>();
//        adapter=new MyTaskLookPicGriderViewAdapter(list,this);
//        picGv.setAdapter(adapter);
//    }
//
//    private void loadRequest(final String Url) {
//        lodingDialog.show();
//
//        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
//        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
//        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
//        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
//        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
//        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
////        dataRequestBase.DataValue = node; //JsonUtils.toJsonData(getDriverListRequest);
//        LogUtil.d("-->>" + dataRequestBase.toString());
//        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                LogUtil.d("-->>response" + response.toString());
//                GetPictrueListResponse response1 = JsonUtils.parseData(response.toString(), GetPictrueListResponse.class);
//                Message msg = Message.obtain();
//                if (response1.Success) {
//                    msg.obj = response1.DataValue;
//                    msg.what = Constants.REQUEST_SUCC;
//                    myHandler.sendMessage(msg);
//                } else {
//                    msg.obj = response1.ErrorText;
//                    msg.what = Constants.REQUEST_FAIL;
//                    myHandler.sendMessage(msg);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG, error.toString());
//                Message msg = Message.obtain();
//                msg.obj = error.getMessage();
//                msg.what = Constants.REQUEST_ERROR;
//                myHandler.sendMessage(msg);
//            }
//        });
//
//    }
//
//    private Handler myHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (lodingDialog != null) {
//                lodingDialog.dismiss();
//            }
//            switch (msg.what) {
//                case Constants.REQUEST_SUCC:
//                    if (msg.obj != null) {
//                        List<PictureBean> dataList = (List<PictureBean>) msg.obj;
//                        if (dataList != null) {
//                            list.addAll(dataList);
//                        }
////                        adapter.notifyDataSetChanged();
//                        Intent intent=new Intent(MyTaskLookPicActivity.this, PreviewPicPagerActivity.class);
//                        intent.putExtra(Constants.PictureBeanList, (Serializable) list);
//                        intent.putExtra(Constants.POSITION,0);
//                        startActivity(intent);
//                    }
//
//                    break;
//                case Constants.REQUEST_FAIL:
//                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
//                    break;
//                case Constants.DELETE_SUCC:
//                    DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
//                    break;
//                case Constants.REQUEST_ERROR:
//                    DialogUtils.showMessageDialogOfDefaultSingleBtn(MyTaskLookPicActivity.this, Utils.getResourcesString(R.string.request_error));
//                    break;
//            }
//        }
//    };
//
//    @OnClick(R.id.back_llayout)
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.back_llayout:
//                finish();
//                break;
//        }
//    }
}
