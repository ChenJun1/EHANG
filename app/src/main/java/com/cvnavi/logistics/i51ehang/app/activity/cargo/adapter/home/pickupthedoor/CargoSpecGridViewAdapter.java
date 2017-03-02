package com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.pickupthedoor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.CargoSpec;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

import java.util.List;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 物品规格
 */

public class CargoSpecGridViewAdapter extends BaseAdapter{
    Context context;
    List<CargoSpec> list;
    String[] spec = null;

    private String Spect="纸箱";

    public CargoSpecGridViewAdapter(Context context, List<CargoSpec> list,String[] spec) {
        this.list = list;
        this.context = context;
        this.spec = spec;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSpec(String Spect){

        this.Spect = Spect;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.activity_cargo_pick_up_the_door_gallery_item, null);
        TextView tvCity = (TextView) convertView.findViewById(R.id.id_index_gallery_item_image);
        LinearLayout cargo_pick_item_LLlayout = (LinearLayout) convertView.findViewById(R.id.cargo_pick_item_LLlayout);

        if (spec[position].equals(Spect)){
            cargo_pick_item_LLlayout.setBackgroundResource(R.drawable.shape_cargo_selected);
            tvCity.setTextColor(Utils.getResourcesColor(R.color.color_ffffff));
        }
//        CargoSpec city = list.get(position);
//        tvCity.setText(city.getCityName());
        tvCity.setText(spec[position]);
        return convertView;
    }
}
