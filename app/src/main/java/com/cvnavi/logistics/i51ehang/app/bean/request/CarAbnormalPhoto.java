package com.cvnavi.logistics.i51ehang.app.bean.request;

import com.cvnavi.logistics.i51ehang.app.bean.model.ImageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/26.
 */
public class CarAbnormalPhoto implements Serializable{

    public String CarCode;//车牌号    string
    public String CarCode_Key;//车Key  string
    public String Driver_Key;//Key   string
    public String Letter_Oid;//清单号string
    public String Exception_DateTime;//2016-07-01 15:19:00	是	异常时间   DateTime
    public String Exception_Content;//异常内容   string
    public String Exception_Type_Oid;//异常码默认string AA：堵车，AB：车辆故障，AC：交通事故司机

    public List<ImageInfo> IMGList;

    public CarAbnormalPhoto(String CarCode,
            String CarCode_Key,
            String Driver_Key,
            String Letter_Oid){
        this.CarCode= CarCode;
        this.CarCode_Key= CarCode_Key;
        this.Driver_Key= Driver_Key;
        this.Letter_Oid= Letter_Oid;

    }
    public CarAbnormalPhoto(){}

}
