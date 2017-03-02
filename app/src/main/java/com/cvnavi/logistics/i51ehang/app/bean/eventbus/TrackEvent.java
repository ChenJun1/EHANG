package com.cvnavi.logistics.i51ehang.app.bean.eventbus;

import com.cvnavi.logistics.i51ehang.app.bean.model.mCarHistoryLocusAnalysis;

import java.util.List;

/**
 * Created by george on 2016/10/11.
 */

public class TrackEvent {

    private List<mCarHistoryLocusAnalysis> list;

    public TrackEvent(List<mCarHistoryLocusAnalysis> list) {
        this.list = list;
    }

    public List<mCarHistoryLocusAnalysis> getList() {
        return list;
    }

    public void setList(List<mCarHistoryLocusAnalysis> list) {
        this.list = list;
    }
}
