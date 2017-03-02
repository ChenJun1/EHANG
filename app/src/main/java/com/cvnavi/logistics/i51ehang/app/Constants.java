package com.cvnavi.logistics.i51ehang.app;

/**
 * 常量类
 * 控制开关
 */
public class Constants {
    //引导页资源
    public static final int[] GuidResImgs = {R.drawable.guid_one, R.drawable.guid_two, R.drawable.guid_three};
    // 对话框对于屏幕的比例
    public static final float DIALOG_WIDTH_SCALE = 0.85f;

    public static final int REQUEST_SUCC = 0;
    public static final int REQUEST_FAIL = 1;
    public static final int REQUEST_ERROR = 2;
    public static final int DELETE_SUCC = 3;
    public static final int ADD_SUCC = 4;
    public static final int EDIT_SUCC = 5;
    public static final int REQUEST_BADGEVIEW_SUCC = 6;
    public static final int REQUEST_BADGEVIEW_FAIL = 7;
    public static final int DELETE_ALL_SUCC = 8;
    public static final int REQUEST_MSG_COUNT_SUCC = 9;
    public static final int SETMSG_SUCC = 10;
    //=====================回调常量========================
    public static final String CHOOSE_CAR = "CHOOSE_CAR";
    public static final String CHOOSE_MONITOR_CAR = "CHOOSE_MONITOR_CAR";
    public static final String CHOOSE_HISTOR_CAR = "CHOOSE_HISTOR_CAR";
    public static final String MyTASK_CHOICE_TIME = "MyTASK_CHOICE_TIME";

    //=====================页面传参常量=====================
    public static final String TODRIVERMONITORMAPACTIVITY = "TODRIVERMONITORMAPACTIVITY";
    public static final String EDIT_CAR_SCHEDULING = "EDIT_CAR_SCHEDULING";
    public static final String CAR_KEY = "CAR_KEY";
    public static final String OPERATE = "OPERATE";
    public static final String DriverList = "DriverList";
    public static final String DriverInfo = "DriverInfo";
    public static final String TASKINFO = "TASKINFO";
    public static final String DRIVER_LINE_LOOK_PIC = "DRIVER_LINE_LOOK_PIC"; //线路跟踪
    public static final String TaskDetailedOrder = "TaskDetailedOrder";
    public static final String Letter_Oid = "Letter_Oid";
    public static final String TaskBean = "TaskBean";
    public static final String PictureBeanList = "PictureBeanList";
    public static final String POSITION = "POSITION";
    public static final String All_Ticket_No = "All_Ticket_No";
    public static final String ExceptionInfoBean = "ExceptionInfoBean"; //货单异常
    public static final String LogisticsFollowNoteBean = "LogisticsFollowNoteBean";//查看物流
    public static final String DriverStowageStatisticsListActivity = "DriverStowageStatisticsActivity";
    public static final String MyTaskExceptionInfoActivity = "MyTaskExceptionInfoActivity"; //异常信息
    public static final String HOME = "HOME";

    // =================登录帮助Url===================
    public static String HelpManual_URL = "http://app.eh-56.com/help.html";

    //==================首页菜单SeriviceType===============
    public static final int HOME_SERVICE_TYPE_BUSINISS = 1;//业务
    public static final int HOME_SERVICE_TYPE_LOCATION = 2;//定位

    // =================首页菜单SeivrceID===================
    public static final int HOME_LOCATION_JK = 0;//车辆监控
    public static final int HOME_LOCATION_GJ = 1;//车辆轨迹
    public static final int HOME_LOCATION_YS = 2;//调度运输
    public static final int HOME_LOCATION_BJ = 3;//车辆报警
    public static final int HOME_LOCATION_CL = 4;//车辆管理
    public static final int HOME_LOCATION_CLT = 5;//车辆统计
    public static final int HOME_LOCATION_YC = 6;//车辆异常
    public static final int HOME_BUSINISS_HD = 7;//货单查询
    public static final int HOME_BUSINISS_TJ = 8;//统计
    public static final int HOME_BUSINISS_SJG = 9;//司机管理
    public static final int HOME_BUSINISS_RW = 10;//任务
    public static final int HOME_BUSINISS_HDE = 11;//我的货单
    public static final int HOME_BUSINISS_CD = 16;//我的车队
    public static final int HOME_BASE_SETTING = 28;//基础设置
    public static final String HOME_BASE_WWC = "S1";//未完成货单
    public static final String HOME_BASE_LS = "S2";//历史货单
    public static final String HOME_BASE_QH = "S3";//取货记录
    public static final String HOME_BASE_WO = "S4";//我的钱包

    public static final int EMPLOYEE_SERVICE_ID_GPS = 0;//Gps
    public static final int EMPLOYEE_SERVICE_ID_GPS_TRACK = 1;//Gps轨迹
    public static final int EMPLOYEE_SERVICE_ID_CAR_FLEET = 2;//我的车队
    public static final int EMPLOYEE_SERVICE_ID_TRANSTION = 3;//调度运输
    public static final int EMPLOYEE_SERVICE_ID_TONGJI = 4;//统计
    public static final int EMPLOYEE_SERVICE_ID_HUODAN_TONGJI = 5;//货单统计
    public static final int EMPLOYEE_SERVICE_ID_PEIZAI_TONGJI = 6;//配载统计
    public static final int EMPLOYEE_SERVICE_ID_YSK_TONGJI = 7;//应收款统计
    public static final int EMPLOYEE_SERVICE_ID_YFK_TONGJI = 8;//应付款统计
    public static final int EMPLOYEE_SERVICE_ID_SETTING = 9;//设置
    public static final int EMPLOYEE_SERVICE_ID_DRIVER_MANAGE = 10;//司机管理
    public static final int EMPLOYEE_SERVICE_ID_CAR_MANAGE = 11;//车辆管理
    public static final int EMPLOYEE_SERVICE_ID_ORDER_SEARCH = 12;//货单查询
    public static final int EMPLOYEE_SERVICE_ID_DELECT_PLAN = 13;//删除计划
    public static final int EMPLOYEE_SERVICE_ID_ADD_PLAN = 14;//添加计划
    public static final int EMPLOYEE_SERVICE_ID_CAR_EXCEPTION_UPLOAD = 15;//车辆异常上报
    public static final int EMPLOYEE_SERVICE_ID_ORDER_EXCEPTION_UPLOAD = 16;//货单异常上报
    public static final int EMPLOYEE_SERVICE_ID_CONFIRM_SIGN = 17;//确认签收
    public static final int EMPLOYEE_SERVICE_ID_CONFIRM_ARRIVE = 18;//确认到车
    public static final int EMPLOYEE_SERVICE_ID_CONFIRM_TIHUO = 19;//确认提货
    public static final int EMPLOYEE_SERVICE_ID_EDIT_DRIVER = 20;//编辑司机
    public static final int EMPLOYEE_SERVICE_ID_ADD_DRIVER = 21;//添加司机
    public static final int EMPLOYEE_SERVICE_ID_DELECT_DRIVER = 22;//删除司机
    public static final int EMPLOYEE_SERVICE_ID_MILES_TONGJI = 23;//里程统计
    public static final int EMPLOYEE_SERVICE_ID_SEND_CAR_MONITOR = 24;//发车监控
    public static final int EMPLOYEE_SERVICE_ID_SEND_CAR_PLAN = 25;//发车计划
    public static final int EMPLOYEE_SERVICE_ID_KU_FANG = 26;//库房
    public static final int EMPLOYEE_SERVICE_ID_KU_CUN = 27;//库存
    public static final int EMPLOYEE_SERVICE_ID_ARRIVE_MONITOR = 28;//到车监控
    public static final int EMPLOYEE_SERVICE_ID_SEND_CAR_STATICS = 29;//派车单统计
    public static final int EMPLOYEE_SERVICE_ID_WEI_TUO_STATICS = 30;//委托统计
    public static final int EMPLOYEE_SERVICE_ID_CAI_WU_STATICS = 31;//财务统计
    public static final int EMPLOYEE_SERVICE_ID_CHECK_SEND_MAN_INFO = 32;//查看发货人信息

    public static final int BANNER_TIME = 1 * 1000;//1秒

    /**
     * 更新日志
     * <p>
     *
     * <更新的次数>(versionCode，versionName,更新的内容)；
     * <1>(versionCode：1，版本号：1.0,更新的内容：包名更改为ehang)；
     * <3>(versionCode：3，版本号：2.0,更新的内容：新增发车监控，修改新建发车计划）；
     * <4>(versionCode：4，版本号：2.1,更新的内容：修改我的界面UI,定位信息界面，历程统计的界面,存在更新的bug）；
     * <5>(versionCode：5，版本号：2.2,更新的内容：e航5期更新内容：
     * 增加【库房】模块
     * 增加【派车单统计】模块
     * 增加【委托统计】模块
     * 增加【财务统计】模块
     * 增加【到车监控】模块
     * 优化部分用户界面
     * 修复已知bug
     * l）；
     * <5>(versionCode：6，版本号：2.3,更新的内容：优化一些界面
     * (version 7,2.3.1 修改UI界面)
     * (version 8,2.3.2
     * 水立方项目优化【
     * 派车单统计】模块
     * 优化【委托统计】模块
     * 优化【财务统计】模块
     * 优化【发车监控】模块
     * 优化部分用户界面
     * 修复已知bug
     * )
     */

    //是否是测试环境
    public static boolean IS_DEBUG = true;

}
