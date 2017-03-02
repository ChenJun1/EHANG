package com.cvnavi.logistics.i51ehang.app.activity.employee.myutils;

/**
 * Created by george on 2016/12/19.
 */

public class BaiduMapUtils {


    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        return s;
    }

    /**
     * 根据两个点获取缩放级别
     * {"10m"(20), "20m"(19), "50m"(18), "100m"(17), "200m"(16), "500m"(15), "1km"(14), "2km"(13), "5km"(12), "10km"(11),
     * "20km"(10), "25km"(9), "50km"(8), "100km"(7), "200km"(6), "500km"(5), "1000km"(4), "2000km"(3)}
     * Level依次为：20、19、18、17、16、15、14、13、12、11、10、9、8、7、6、5、4、3
     *
     * @param distance
     * @return
     */
    public static float getZoomSize(double distance) {
        float zoomSize = 6F;
        if (distance >= 2000) {
            zoomSize = 3F;
        } else if (distance < 2000 && distance >= 1000) {
            zoomSize = 4F;
        } else if (distance < 1000 && distance >= 500) {
            zoomSize = 5F + 3F;
        } else if (distance < 500 && distance >= 200) {
            zoomSize = 6F + 3F;
        } else if (distance < 200 && distance >= 100) {
            zoomSize = 7F + 3F;
        } else if (distance < 100 && distance >= 50) {
            zoomSize = 8F + 3F;
        } else if (distance < 50 && distance >= 25) {
            zoomSize = 9F + 3F;
        } else if (distance < 25 && distance >= 20) {
            zoomSize = 10F;
        } else if (distance < 20 && distance >= 10) {
            zoomSize = 11F + 2F;
        } else if (distance < 10 && distance >= 5) {
            zoomSize = 12F + 2F;
        } else if (distance < 5 && distance >= 2) {
            zoomSize = 13F + 2F;
        } else if (distance < 2 && distance >= 1) {
            zoomSize = 14F + 2F;
        } else if (distance < 1 && distance >= 0.5) {
            zoomSize = 15F + 2F;
        } else if (distance < 0.5 && distance >= 0.2) {
            zoomSize = 16F + 2.5F;
        } else if (distance < 0.2 && distance >= 0.1) {
            zoomSize = 17F + 2.5F;
        } else if (distance < 0.1 && distance >= 0.05) {
            zoomSize = 18F + 2.5F;
        } else if (distance < 0.05 && distance >= 0.02) {
            zoomSize = 19F + 2.5F;
        } else if (distance < 0.02) {
            zoomSize = 20F + 2.5F;
        }
        return zoomSize - 0.5F;
    }


}
