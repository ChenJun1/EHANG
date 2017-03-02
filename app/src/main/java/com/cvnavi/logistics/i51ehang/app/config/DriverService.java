package com.cvnavi.logistics.i51ehang.app.config;

import static com.cvnavi.logistics.i51ehang.app.config.HostAddress.Host_Url;

/**
 * Created by ${ChenJ} on 2016/10/9.
 */

public class DriverService {
    public static final String DriverService_Address = Host_Url + "/Driver/Driver/";
    public static final String Employee_Address = Host_Url + "/Employee/Employee/";
    public static final String Shipper_Address = Host_Url + "/Shipper/Shipper/";

    //我的发车
    public static final String GetMyLeaveCarCodeTAG = "GetMyLeaveCarCode";
    public static final String GetMyLeaveCarCode_URL = DriverService_Address + GetMyLeaveCarCodeTAG;


    //我的发车详情
    public static final String GetLeaveCarCodeDetailTAG = "GetLeaveCarCodeDetail";
    public static final String GetLeaveCarCodeDetail_URL = DriverService_Address + GetLeaveCarCodeDetailTAG;

    //发车清单
    public static final String GetLoadingLetterTAG = "GetLoadingLetter";
    public static final String GetLoadingLetter_URL = DriverService_Address + GetLoadingLetterTAG;

    //我的送货
    public static final String GetPSTicketDataTAG = "GetPSTicketData";
    public static final String GetPSTicketData_URL = DriverService_Address + GetPSTicketDataTAG;

    //我的送货
    public static final String GetTHTicketDataTAG = "GetTHTicketData";
    public static final String GetTHTicketData_URL = DriverService_Address + GetTHTicketDataTAG;

    //车辆异常拍照
    public static final String CarAbnormalPhoto_TAG = "CarAbnormalPhoto";
    public static final String CarAbnormalPhoto_Request_Url = DriverService_Address + CarAbnormalPhoto_TAG;


    //．车辆异常信息查询
    public static final String GetCarExceptInfo_TAG = "GetCarExceptInfo";
    public static final String GetCarExceptInfo_Request_Url = DriverService_Address + GetCarExceptInfo_TAG;

    //我的任务 提货确认
    public static final String DeliveryConfirm_TAG = "DeliveryConfirm";
    public static final String DeliveryConfirm_Request_Url = DriverService_Address + DeliveryConfirm_TAG;

    //确认签收
    public static final String ConfirmSign_TAG = "ConfirmSign";
    public static final String ConfirmSign_Request_Url = DriverService_Address + ConfirmSign_TAG;

    //货物异常信息
    public static final String GetExceptInfo_TAG = "GetExceptInfo";
    public static final String GetExceptInfo_Request_Url = Shipper_Address + GetExceptInfo_TAG;

    //车辆异常照片查看
    public static final String GetCarExceptImgInfo_TAG = "GetCarExceptImgInfo";
    public static final String GetCarExceptImgInfo_Request_Url = Employee_Address + GetCarExceptImgInfo_TAG; //车辆异常照片查看

    //1．线路跟踪查询 路线规划
    public static final String GetCarLineNode_TAG = "GetCarLineNode";
    public static final String GetCarLineNode_Request_Url = DriverService_Address + GetCarLineNode_TAG;

    //1．监控消息列表查询
    public static final String GetMessageContent_TAG = "GetMessageContent";
    public static final String GetMessageContent_Request_Url = Shipper_Address + GetMessageContent_TAG; //1．监控消息列表查询

    //2．删除消息
    public static final String DelMessageContent_TAG = "DelMessageContent";
    public static final String DelMessageContent_Request_Url = Shipper_Address + DelMessageContent_TAG;
    //2．设置消息已读
    public static final String SetMessageRead_TAG = "SetMessageRead";
    public static final String SetMessageRead_Request_Url = Shipper_Address + SetMessageRead_TAG;

    //当月统计查询,我的页面
    public static final String GetStartCarStatistics_TAG = "GetStartCarStatistics";
    public static final String GetStartCarStatistics_Request_Url = DriverService_Address + GetStartCarStatistics_TAG;


}
