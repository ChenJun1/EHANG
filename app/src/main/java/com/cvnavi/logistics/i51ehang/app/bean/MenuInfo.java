package com.cvnavi.logistics.i51ehang.app.bean;

public class MenuInfo {
    /**
     * 首页菜单详细信息
     */
    private String Popedom_Name;//菜单名称
    private int Popedom_ID;//菜单ID
    private int IsMenu;//是否是菜单
    private int From;//
    private String ImagePath;//图片路径

    public MenuInfo(String popedom_Name, int popedom_ID) {
        Popedom_Name = popedom_Name;
        Popedom_ID = popedom_ID;
    }

    public String getPopedom_Name() {
        return Popedom_Name;
    }

    public void setPopedom_Name(String popedom_Name) {
        Popedom_Name = popedom_Name;
    }

    public int getPopedom_ID() {
        return Popedom_ID;
    }

    public void setPopedom_ID(int popedom_ID) {
        Popedom_ID = popedom_ID;
    }

    public int getIsMenu() {
        return IsMenu;
    }

    public void setIsMenu(int isMenu) {
        IsMenu = isMenu;
    }

    public int getFrom() {
        return From;
    }

    public void setFrom(int from) {
        From = from;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
