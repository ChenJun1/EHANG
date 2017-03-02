package com.cvnavi.logistics.i51ehang.app.bean.model.MyTask;

/**
 * Created by Administrator on 2016/7/25.
 */
public class ImageBean {

    public String File_Name;   //图片名
    public String File_Type;   //图片类型 .扩展名
    public String File_Size;    //图片大小
    public String Bytes;      //图片   Base64位
    public String All_Ticket_No;//货单全票号

    public ImageBean(String All_Ticket_No,String File_Name,
                     String File_Type,
                     String File_Size,
                     String Bytes) {
        this.All_Ticket_No=All_Ticket_No;
        this.Bytes = Bytes;
        this.File_Name = File_Name;
        this.File_Size = File_Size;
        this.File_Type = File_Type;

    }
}
