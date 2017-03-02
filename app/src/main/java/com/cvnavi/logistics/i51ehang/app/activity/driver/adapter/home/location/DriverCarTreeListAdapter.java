package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;

import java.util.ArrayList;

/**
 * Created by ${ChenJ} on 2016/7/25.
 */
public class DriverCarTreeListAdapter extends BaseExpandableListAdapter {

    private Context mContext;

    private ArrayList<mCarInfo> mParents;

    // 点击子ExpandableListView子项的监听
    private OnChildTreeViewClickListener mTreeViewClickListener;

    public DriverCarTreeListAdapter(Context context, ArrayList<mCarInfo> parents) {
        this.mContext = context;
        this.mParents = parents;
    }

    //获取当前父item的数据数量
    @Override
    public int getGroupCount() {
        return mParents != null ? mParents.size() : 0;
    }

    //获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return mParents.get(groupPosition).CarList != null ? mParents.get(groupPosition).CarList.size() : 0;
    }

    //获取当前父item的数据
    @Override
    public mCarInfo getGroup(int groupPosition) {
        return mParents.get(groupPosition);
    }

    //得到子item需要关联的数据
    @Override
    public mCarInfo getChild(int groupPosition, int childPosition) {
        return mParents.get(groupPosition).CarList.get(childPosition);
    }

    //得到父item的ID
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_driver_car_list_parent_group, null);
            holder = new GroupHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.update(mParents.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ExpandableListView eListView = getExpandableListView();

        ArrayList<mCarInfo> childs = new ArrayList<mCarInfo>();

        final mCarInfo child = getChild(groupPosition, childPosition);

        childs.add(child);

        final DriverChildAdapter childAdapter = new DriverChildAdapter(this.mContext,
                childs);

        eListView.setAdapter(childAdapter);

        eListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupIndex) {
                if (mTreeViewClickListener != null) {

                    mTreeViewClickListener.onClickPosition(child);
                }
            }
        });

        /**
         * @author Apathy、恒
         *
         *         点击子ExpandableListView子项时，调用回调接口
         * */

//        eListView.setOnChildClickListener(new OnChildClickListener() {
//
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupIndex, int childIndex, long id) {
//                if (mTreeViewClickListener != null) {
//
//                    mTreeViewClickListener.onClickPosition(groupPosition,
//                            childPosition, childIndex);
//                }
//                return false;
//            }
//        });


        /**
         * @author Apathy、恒
         *
         *         子ExpandableListView展开时，因为group只有一项，所以子ExpandableListView的总高度=
         *         （子ExpandableListView的child数量 + 1 ）* 每一项的高度
         * */
//        eListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if(child.mCarInfoList!=null&&child.mCarInfoList.size()>0){
//
//                    AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
//                            ViewGroup.LayoutParams.MATCH_PARENT, (child.mCarInfoList.size() + 1)* (int) mContext.getResources().getDimension( R.dimen.dimen_50));
//                    eListView.setLayoutParams(lp);
//                }
//
//            }
//        });

        /**
         * @author Apathy、恒
         *
         *         子ExpandableListView关闭时，此时只剩下group这一项，
         *         所以子ExpandableListView的总高度即为一项的高度
         * */
        eListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

                LayoutParams lp = new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
                        .getResources().getDimension(
                                R.dimen.dimen_50));
                eListView.setLayoutParams(lp);
            }
        });
        return eListView;
    }

    /**
     * @author Apathy、恒
     * <p/>
     * 动态创建子ExpandableListView
     */
    public ExpandableListView getExpandableListView() {
        ExpandableListView mExpandableListView = new ExpandableListView(
                mContext);
        LayoutParams lp = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) mContext
                .getResources().getDimension(
                        R.dimen.dimen_50));
        mExpandableListView.setLayoutParams(lp);
        mExpandableListView.setDividerHeight(0);// 取消group项的分割线
        mExpandableListView.setChildDivider(null);// 取消child项的分割线
        mExpandableListView.setGroupIndicator(null);// 取消展开折叠的指示图标
        return mExpandableListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * @author Apathy、恒
     * <p/>
     * 设置点击子ExpandableListView子项的监听
     */
    public void setOnChildTreeViewClickListener(
            OnChildTreeViewClickListener treeViewClickListener) {
        this.mTreeViewClickListener = treeViewClickListener;
    }

    /**
     * @author Apathy、恒
     *         <p/>
     *         点击子ExpandableListView子项的回调接口
     */
    public interface OnChildTreeViewClickListener {

        //        void onClickPosition(int parentPosition, int groupPosition,
//                             int childPosition);
        void onClickPosition(mCarInfo carBean);
    }


    class GroupHolder {

        private TextView parentGroupTV;

        public GroupHolder(View v) {
            parentGroupTV = (TextView) v.findViewById(R.id.parentGroupTV);
        }

        public void update(mCarInfo model) {
            parentGroupTV.setText(model.Org_Name);
        }
    }
}
