/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetmonitor;


import com.baidu.mapapi.model.LatLng;

import java.util.Collection;

/**
 * A collection of ClusterItems that are nearby each other.
 */
public interface Cluster<T extends com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetmonitor.ClusterItem> {
    public LatLng getPosition();

    Collection<T> getItems();

    int getSize();
}