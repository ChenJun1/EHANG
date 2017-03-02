package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * 版权所有势航网络
 * Created by ${ChenJ} on 2016/7/25.
 */
public class MyTaskMainViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mylist=null;

    public MyTaskMainViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fList) {
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
