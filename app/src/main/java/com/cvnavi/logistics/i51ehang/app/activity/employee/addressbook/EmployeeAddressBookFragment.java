package com.cvnavi.logistics.i51ehang.app.activity.employee.addressbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:03
*描述： 员工通讯录 暂无增加新功能
************************************************************************************/

public class EmployeeAddressBookFragment extends BaseFragment {

    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.test)
    TextView test;

    public static EmployeeAddressBookFragment instantiation() {
        return new EmployeeAddressBookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_addressbook, container, false);
        ButterKnife.bind(this, view);
        titleTv.setText("通讯录");
        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        //防止有重影的现象
        if (this.getView() != null)
            this.getView()
                    .setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }


}
