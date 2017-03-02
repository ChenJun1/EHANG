/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */

package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetmonitor.algo;


import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetmonitor.Cluster;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetmonitor.ClusterItem;

import java.util.Collection;
import java.util.Set;

/**
 * Logic for computing clusters
 */
public interface Algorithm<T extends ClusterItem> {
    void addItem(T item);

    void addItems(Collection<T> items);

    void clearItems();

    void removeItem(T item);

    Set<? extends Cluster<T>> getClusters(double zoom);

    Collection<T> getItems();
}