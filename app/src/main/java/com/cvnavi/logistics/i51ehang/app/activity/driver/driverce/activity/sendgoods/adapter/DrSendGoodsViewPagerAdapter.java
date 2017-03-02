package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendgoods.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 送货ViewPage
 */
public class DrSendGoodsViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mylist=null;

    public DrSendGoodsViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fList) {
        super(fm);
       this.mylist=fList;
    }

    @Override
    public Fragment getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public int getCount() {
        if(mylist!=null){
          return  mylist.size();
        }
        return 0;
    }
}
