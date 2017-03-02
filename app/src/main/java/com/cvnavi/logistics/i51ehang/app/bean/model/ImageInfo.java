package com.cvnavi.logistics.i51ehang.app.bean.model;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/22.
 */
public class ImageInfo {

    public String File_Type;//图片类型
    public String File_Name;//图片名字  string（全票号+几位随机数）.jpg   需要带后缀
    public String File_Size;//图片名字  string（全票号+几位随机数）.jpg   需要带后缀
    public String Bytes;//图片   Base64位
    public String All_Ticket_No;//货单全票号

    public ImageInfo(String all_Ticket_No, String file_Size, String file_Name, String file_Type, String bytes) {
        File_Type = file_Type;
        File_Name = file_Name;
        File_Size = file_Size;
        Bytes = bytes;
        All_Ticket_No = all_Ticket_No;
    }
}
