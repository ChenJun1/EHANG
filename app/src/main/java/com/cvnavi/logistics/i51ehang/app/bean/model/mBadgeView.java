package com.cvnavi.logistics.i51ehang.app.bean.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fan on 2016/10/10.
 */

public class mBadgeView implements Parcelable {
    public String NoTicketNumber;//	未完成货单数量
    public String NotStartNumber ;//未发车
    public String TransportNumber;// 运输中
    public String DeliveryNumber ;// 派送中

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.NoTicketNumber);
        dest.writeString(this.NotStartNumber);
        dest.writeString(this.TransportNumber);
        dest.writeString(this.DeliveryNumber);
    }

    public mBadgeView() {
    }

    protected mBadgeView(Parcel in) {
        this.NoTicketNumber = in.readString();
        this.NotStartNumber = in.readString();
        this.TransportNumber = in.readString();
        this.DeliveryNumber = in.readString();
    }

    public static final Parcelable.Creator<mBadgeView> CREATOR = new Parcelable.Creator<mBadgeView>() {
        @Override
        public mBadgeView createFromParcel(Parcel source) {
            return new mBadgeView(source);
        }

        @Override
        public mBadgeView[] newArray(int size) {
            return new mBadgeView[size];
        }
    };
}
