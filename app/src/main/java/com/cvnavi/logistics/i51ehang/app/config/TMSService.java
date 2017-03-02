package com.cvnavi.logistics.i51ehang.app.config;

/**
 * Created by JohnnyYuan on 2016/7/5.
 */
public class TMSService extends HostAddress {

    public static final String TMSService_Address = Host_Url + "/TMS/TMS/";
    public static final String ShipperService_Address = Host_Url + "/Shipper/Shipper/";
    public static final String Employee_Address = Host_Url + "/Employee/Employee/";
    public static final String DriverService_Address = Host_Url + "/Driver/Driver/";

    // 车辆排班查询
    public static final String GetShiftList_TAG = "GetShiftList";
    public static final String GetShiftList_Request_Url = Employee_Address + GetShiftList_TAG;

    // 车辆排班添加
    public static final String AddCarShift_TAG = "AddCarShift";
    public static final String AddCarShift_Request_Url = Employee_Address + AddCarShift_TAG;

    // 车辆排班修改
    public static final String EditCarShift_TAG = "EditCarShift";
    public static final String EditCarShift_Request_Url = Employee_Address + EditCarShift_TAG;

    // 车辆排班删除
    public static final String DelCarShift_TAG = "DelCarShift";
    public static final String DelCarShift_Request_Url = Employee_Address + DelCarShift_TAG;

    // 车辆排班线路选择
    public static final String GetLineList_TAG = "GetLineList";
    public static final String GetLineList_Request_Url = Employee_Address + GetLineList_TAG;

    // 车辆排班司机选择
    public static final String GetDriverList_TAG = "GetDriverList";
    public static final String GetDriverList_Request_Url = Employee_Address + GetDriverList_TAG;

    //删除司机
    public static final String DelDriver_TAG = "DelDriver";
    public static final String DelDriver_Request_Url = Employee_Address + DelDriver_TAG;

    //添加司机
    public static final String AddDriver_TAG = "AddDriver";
    public static final String AddDriver_Request_Url = Employee_Address + AddDriver_TAG;


    //修改司机
    public static final String UpdateDriver_TAG = "EditDriver";
    public static final String UpdateDriver_Request_Url = Employee_Address + UpdateDriver_TAG;

    //我的任务列表
    public static final String GetDetailedList_TAG = "GetDetailedList";
    public static final String GetDetailedList_Request_Url = TMSService_Address + GetDetailedList_TAG;

    //我的任务 派车单明细
    public static final String DetailedListDetail_TAG = "DetailedListDetail";
    public static final String DetailedListDetail_Request_Url = TMSService_Address + DetailedListDetail_TAG;

    //我的任务 线路跟踪
    public static final String GetCarLineNode_TAG = "GetCarLineNode";
    public static final String GetCarLineNode_Request_Url = Employee_Address + GetCarLineNode_TAG;

    //我的任务 确认到车 节点选择
    public static final String LineNodeChoice_TAG = "LineNodeChoice";
    public static final String LineNodeChoice_Request_Url = TMSService_Address + LineNodeChoice_TAG;

    //我的任务 线路跟踪 查看照片
    public static final String SeeImage_TAG = "SelectImage";
    public static final String SeeImage_Request_Url = ShipperService_Address + SeeImage_TAG;

    //我的任务 到车确认
    public static final String ShuttleBusConfirm_TAG = "ShuttleBusConfirm";
    public static final String ShuttleBusConfirm_Request_Url = DriverService_Address + ShuttleBusConfirm_TAG;

    //我的任务 提货确认
    public static final String DeliveryConfirm_TAG = "DeliveryConfirm";
    public static final String DeliveryConfirm_Request_Url = DriverService_Address + DeliveryConfirm_TAG;

    //货单统计
    public static final String OrederStatistics_TAG = "OrederStatistics";
    public static final String GetOrederStatistics_Request_Url = Employee_Address + OrederStatistics_TAG;


    //货单统计
    public static final String Selectticket_TAG = "Selectticket";
    public static final String Selectticket_Request_Url = TMSService_Address + Selectticket_TAG;//货单统计

    //应收款统计
    public static final String GetReceivableAccount_TAG = "ReceivablesStatistics";
    public static final String GetReceivableAccount_Request_Url = Employee_Address + GetReceivableAccount_TAG;


    //应收款详情
    public static final String GetReceivablesList_TAG = "GetReceivablesList";
    public static final String GetReceivablesList_Request_Url = Employee_Address + GetReceivablesList_TAG;


    //应付款统计
    public static final String PaymentStatistics_TAG = "PaymentStatistics";
    public static final String GetPaymentStatistics_Request_Url = Employee_Address + PaymentStatistics_TAG;


    //应付款明细
    public static final String GetPaymentList_TAG = "GetPaymentList";
    public static final String GetPaymentList_Request_Url = Employee_Address + GetPaymentList_TAG;


    //配载统计
    public static final String StowageStatisticsSummary_TAG = "StowageStatisticsSummary";
    public static final String GetStowageStatisticsSummary_Request_Url = Employee_Address + StowageStatisticsSummary_TAG;


    //配载清单列表
    public static final String GetStowageStatisticsList_TAG = "GetStowageStatisticsList";
    public static final String GetStowageStatisticsList_Request_Url = Employee_Address + GetStowageStatisticsList_TAG;

    //历史货单（列表）
//    public static String GetOrederList_TAG = "GetOrederList";
//    public static String GetOrederList_Request_Url = TMSService_Address + GetOrederList_TAG;

    //货单明细查询
    public static final String OrederDetailed_TAG = "OrederDetailed";
    public static final String OrederDetailed_Request_Url = ShipperService_Address + OrederDetailed_TAG;

    //确认签收
    public static final String ConfirmSign_TAG = "ConfirmSign";
    public static final String ConfirmSign_Request_Url = DriverService_Address + ConfirmSign_TAG;

    public static final String ConfirmDevlierSearch_TAG = "ConfirmDevlierSearch";
    public static final String ConfirmDevlierSearch_Request_Url = DriverService_Address + ConfirmDevlierSearch_TAG;

    //车辆异常拍照
    public static final String CarAbnormalPhoto_TAG = "CarAbnormalPhoto";
    public static final String CarAbnormalPhoto_Request_Url = DriverService_Address + CarAbnormalPhoto_TAG;

    //货单异常拍照
    public static final String OrderAbnormalPhoto_TAG = "OrderAbnormalPhoto";
    public static final String OrderAbnormalPhoto_Request_Url = DriverService_Address + OrderAbnormalPhoto_TAG;

    //物流异常信息查询
    public static final String GetExceptInfo_TAG = "GetExceptInfo";
    public static final String GetExceptInfo_Request_Url = ShipperService_Address + GetExceptInfo_TAG;

    //物流跟踪图片查询
    public static final String SelectImage_TAG = "SelectImage";
    public static final String SelectImage_Request_Url = ShipperService_Address + SelectImage_TAG;

//    //46．司机未开始的任务列表
//    public static final String GetDriverNoStartTask_TAG = "GetDriverNoStartTask";
//    public static final String GetDriverNoStartTask_Request_Url = TMSService_Address + GetDriverNoStartTask_TAG;


//    //46．查询码长度
//    public static final String QuerySimpleCodeLength_TAG = "QuerySimpleCodeLength";
//    public static final String QuerySimpleCodeLength_Request_Url = TMSService_Address + QuerySimpleCodeLength_TAG;//46．查询码长度

    //．车辆异常图片查询
    public static final String GetCarExceptImgInfo_TAG = "GetCarExceptImgInfo";
    public static final String GetCarExceptImgInfo_Request_Url = ShipperService_Address + GetCarExceptImgInfo_TAG;

    //司机首页获取最近未完成的任务
    public static final String GetDriverNoCompleteTask_TAG = "GetDriverNoCompleteTask";
    public static final String GetDriverNoCompleteTask_Url = TMSService_Address + GetDriverNoCompleteTask_TAG;


    //货主首页角标
    public static final String GetReadNoTicketNumber_TAG = "GetReadNoTicketNumber";
    public static final String GetReadNoTicketNumber_Url = ShipperService_Address + GetReadNoTicketNumber_TAG;
    //货主历史货单
    public static final String GetHistoryTicket_TAG = "GetHistoryTicket";
    public static final String GetHistoryTicket_Url = ShipperService_Address + GetHistoryTicket_TAG;
    //货主未完成货单
    public static final String GetReadNoTicket_TAG = "GetReadNoTicket";
    public static final String GetReadNoTicket_Url = ShipperService_Address + GetReadNoTicket_TAG;
    //货主货单明细
    public static final String GetOrederDetailed_TAG = "OrederDetailed";
    public static final String GetOrederDetailed_Url = ShipperService_Address + GetOrederDetailed_TAG;
    //货主物流跟踪
    public static final String GetSelectTicket_TAG = "SelectTicket";
    public static final String GetSelectTicket_Url = ShipperService_Address + GetSelectTicket_TAG;

    //．车辆异常图片查询 (Dr)新
    public static final String GetCarExceptImgInfo_Dr_TAG = "GetCarExceptImgInfo";
    public static final String GetCarExceptImgInfo_Dr_Request_Url = Employee_Address + GetCarExceptImgInfo_Dr_TAG;


    //==========================================七期=====================================
    //货主发货记录
    public static final String GetTakeManifests_TAG = "GetTakeManifests";
    public static final String GetTakeManifests_Url = ShipperService_Address + GetTakeManifests_TAG;
    //货主上门取货
    public static final String SetTakeManifests_TAG = "SetTakeManifests";
    public static final String SetTakeManifests_Url = ShipperService_Address + SetTakeManifests_TAG;
    //消息中心
    public static final String GetMessageContent_TAG = "GetMessageContent";
    public static final String GetMessageContent_Url = ShipperService_Address + GetMessageContent_TAG;
    //删除消息
    public static final String DelMessageContent_TAG = "DelMessageContent";
    public static final String DelMessageContent_Url = ShipperService_Address + DelMessageContent_TAG;
    //获取未读消息数量
    public static final String GetMessageCount_TAG = "GetMessageCount";
    public static final String GetMessageCount_Url = ShipperService_Address + GetMessageCount_TAG;
    //获取已读消息数量
    public static final String SetMessageRead_TAG = "SetMessageRead";
    public static final String SetMessageRead_Url = ShipperService_Address + SetMessageRead_TAG;
    //消息设置查询
    public static final String GetAppNotifySets_TAG = "GetAppNotifySets";
    public static final String GetAppNotifySets_Url = ShipperService_Address + GetAppNotifySets_TAG;
    //消息设置是否通知
    public static final String SetAppNotifySets_TAG = "SetAppNotifySets";
    public static final String SetAppNotifySets_Url = ShipperService_Address + SetAppNotifySets_TAG;
    //1．货主当月统计查询 我的页面
    public static final String GetShipperStatistics_TAG = "GetShipperStatistics";
    public static final String GetShipperStatistics_Url = ShipperService_Address + GetShipperStatistics_TAG;

}
